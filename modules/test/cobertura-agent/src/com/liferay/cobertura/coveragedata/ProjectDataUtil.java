/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.cobertura.coveragedata;

import com.liferay.cobertura.agent.InstrumentationAgent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import java.lang.reflect.Field;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Shuyang Zhou
 */
public class ProjectDataUtil {

	public static void addMergeHook() {
		Set<Runnable> mergeHooks = _getFieldValue("_mergeHooks");

		mergeHooks.add(_mergeHookRunnable);
	}

	public static ProjectData captureProjectData(boolean saveSessionData) {
		String className = ProjectDataUtil.class.getName();

		synchronized (className.intern()) {
			FileLock fileLock = _lockFile();

			ProjectData masterProjectData = new ProjectData();

			for (ProjectData projectData : _projectDatas.values()) {
				masterProjectData.merge(projectData);
			}

			masterProjectData = _runMergeHooks(masterProjectData);

			try {
				File dataFile = new File(
					System.getProperty("net.sourceforge.cobertura.datafile"));

				if (dataFile.exists()) {
					masterProjectData.merge(_readProjectData(dataFile));

					dataFile.delete();
				}

				if (saveSessionData) {
					_writeProjectData(masterProjectData, dataFile);
				}

				return masterProjectData;
			}
			finally {
				_unlockFile(fileLock);
			}
		}
	}

	public static ProjectData getOrCreateProjectData(ClassLoader classLoader) {
		ProjectData projectData = _projectDatas.get(classLoader);

		if (projectData == null) {
			projectData = new ProjectData();

			ProjectData previousProjectData = _projectDatas.putIfAbsent(
				classLoader, projectData);

			if (previousProjectData != null) {
				projectData = previousProjectData;
			}
		}

		return projectData;
	}

	private static <T> T _getFieldValue(String fieldName) {
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		try {
			Class<?> clazz = systemClassLoader.loadClass(
				ProjectDataUtil.class.getName());

			Field field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			return (T)field.get(null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static ProjectData _getThreadLocalProjectData() {
		ThreadLocal<byte[]> sessionDataThreadLocal = _getFieldValue(
			"_sessionDataThreadLocal");

		try (ObjectInputStream objectInputStream = new ObjectInputStream(
			new ByteArrayInputStream(sessionDataThreadLocal.get()))) {

			return (ProjectData)objectInputStream.readObject();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static FileLock _lockFile() {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(
				InstrumentationAgent.getLockFile(), "rw");

			FileChannel fileChannel = randomAccessFile.getChannel();

			return fileChannel.lock();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static ProjectData _readProjectData(File dataFile) {
		for (int i = 0; i < _RETRY_TIMES; i++) {
			try (FileInputStream fileInputStream = new FileInputStream(
					dataFile);
				ObjectInputStream objectInputStream = new ObjectInputStream(
					fileInputStream)) {

				return (ProjectData)objectInputStream.readObject();
			}
			catch (IOException ioe) {
				continue;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		throw new IllegalStateException(
			"Unable to load project data after retry for " + _RETRY_TIMES +
				" times");
	}

	private static void _removeThreadLocalProjectData() {
		ThreadLocal<byte[]> sessionDataThreadLocal = _getFieldValue(
			"_sessionDataThreadLocal");

		sessionDataThreadLocal.remove();
	}

	private static ProjectData _runMergeHooks(ProjectData projectData) {
		try {
			_setThreadLocalProjectData(projectData);

			for (Runnable runnable :
					ProjectDataUtil.<Set<Runnable>>_getFieldValue(
						"_mergeHooks")) {

				runnable.run();
			}

			return _getThreadLocalProjectData();
		}
		finally {
			_removeThreadLocalProjectData();
		}
	}

	private static void _setThreadLocalProjectData(ProjectData projectData) {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			byteArrayOutputStream)) {

			objectOutputStream.writeObject(projectData);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		ThreadLocal<byte[]> sessionDataThreadLocal = _getFieldValue(
			"_sessionDataThreadLocal");

		sessionDataThreadLocal.set(byteArrayOutputStream.toByteArray());
	}

	private static void _unlockFile(FileLock fileLock) {
		try {
			fileLock.release();

			FileChannel fileChannel = fileLock.channel();

			fileChannel.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static void _writeProjectData(
		ProjectData projectData, File dataFile) {

		try (FileOutputStream fileOutputStream = new FileOutputStream(dataFile);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				fileOutputStream)) {

			objectOutputStream.writeObject(projectData);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final int _RETRY_TIMES = 10;

	private static final Runnable _mergeHookRunnable = new Runnable() {

			@Override
			public void run() {
				ProjectData projectData = _getThreadLocalProjectData();

				TouchCollector.applyTouchesOnProjectData(projectData);

				_setThreadLocalProjectData(projectData);

				if (ProjectDataUtil.class.getClassLoader() !=
						ClassLoader.getSystemClassLoader()) {

					Set<Runnable> mergeHooks = _getFieldValue("_mergeHooks");

					mergeHooks.remove(this);
				}
			}

		};

	@SuppressWarnings("unused")
	private static final Set<Runnable> _mergeHooks =
		new CopyOnWriteArraySet<>();

	private static final ConcurrentMap<ClassLoader, ProjectData> _projectDatas =
		new ConcurrentHashMap<>();

	@SuppressWarnings("unused")
	private static final ThreadLocal<byte[]> _sessionDataThreadLocal =
		new ThreadLocal<>();

}