/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package net.sourceforge.cobertura.instrument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import java.security.ProtectionDomain;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.cobertura.coveragedata.CoverageDataFileHandler;
import net.sourceforge.cobertura.coveragedata.ProjectData;
import net.sourceforge.cobertura.coveragedata.TouchCollector;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Shuyang Zhou
 */
public class InstrumentationAgent implements ClassFileTransformer {

	public static void initialize() {

		// Shutdown hook for each ClassLoader to persistent touch data

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				ProjectData projectData = new ProjectData();

				// Mute TouchCollector.applyTouchesOnProjectData()'s System.out
				// to collect touch data

				PrintStream printStream = new PrintStream(
					new ByteArrayOutputStream());

				synchronized (FileDescriptor.out) {
					PrintStream stdOut = System.out;

					System.setOut(printStream);

					try {
						TouchCollector.applyTouchesOnProjectData(projectData);
					}
					finally {
						System.setOut(stdOut);
					}
				}

				// Merge persistent touch data

				File dataFile = CoverageDataFileHandler.getDefaultDataFile();

				synchronized (InstrumentationAgent.class.getName().intern()) {
					FileLock fileLock = _lockFile(_lockFile);

					try {
						ProjectData masterProjectData;

						if (dataFile.exists()) {
							masterProjectData = _readProjectData(dataFile);

							masterProjectData.merge(projectData);
						}
						else {
							masterProjectData = projectData;
						}

						_writeProjectData(masterProjectData, dataFile);
					}
					finally {
						_unlockFile(fileLock);
					}
				}
			}

		});
	}

	public static void premain(
		String agentArgs, Instrumentation instrumentation) {

		String[] args = agentArgs.split(";");

		String[] includes = args[0].split(",");
		String[] excludes = args[1].split(",");

		InstrumentationAgent instrumentationAgent = new InstrumentationAgent(
			includes, excludes);

		instrumentation.addTransformer(instrumentationAgent);
	}

	public InstrumentationAgent(String[] includes, String[] excludes) {
		_includePatterns = new Pattern[includes.length];

		for (int i = 0; i < includes.length; i++) {
			Pattern pattern = Pattern.compile(includes[i]);

			_includePatterns[i] = pattern;
		}

		_excludePatterns = new Pattern[excludes.length];

		for (int i = 0; i < excludes.length; i++) {
			Pattern pattern = Pattern.compile(excludes[i]);

			_excludePatterns[i] = pattern;
		}

		// Shutdown hook to persistent instrument data

		Runtime.getRuntime().addShutdownHook(
			new Thread() {

				@Override
				public void run() {
					File dataFile =
						CoverageDataFileHandler.getDefaultDataFile();

					synchronized (
						InstrumentationAgent.class.getName().intern()) {

						FileLock fileLock = _lockFile(_lockFile);

						try {
							ProjectData masterProjectData;

							if (dataFile.exists()) {
								masterProjectData = _readProjectData(dataFile);
							}
							else {
								masterProjectData = new ProjectData();
							}

							for (ProjectData projectData :
									_projectDatas.values()) {

								masterProjectData.merge(projectData);
							}

							_projectDatas.clear();

							_writeProjectData(masterProjectData, dataFile);
						}
						finally {
							_unlockFile(fileLock);
						}
					}
				}

			});
	}

	public byte[] transform(
			ClassLoader classLoader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer)
		throws IllegalClassFormatException {

		if (_matches(className)) {
			ProjectData projectData = _projectDatas.get(classLoader);

			if (projectData == null) {
				projectData = new ProjectData();

				ProjectData previousProjectData = _projectDatas.putIfAbsent(
					classLoader, projectData);

				if (previousProjectData != null) {
					projectData = previousProjectData;
				}
			}

			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

			ClassVisitor classVisitor = new ClassInstrumenter(
				projectData, classWriter, Collections.emptyList(),
				Collections.emptyList());

			ClassReader classReader = new ClassReader(classfileBuffer);

			synchronized (projectData) {
				classReader.accept(classVisitor, 0);
			}

			classfileBuffer = classWriter.toByteArray();
		}

		// Modify TouchCollector's static initialization block,
		// redirect ProjectData.initialize() to
		// InstrumentationAgent.initialize()

		if (className.equals(
				"net/sourceforge/cobertura/coveragedata/TouchCollector")) {

			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

			ClassVisitor classVisitor = new TouchCollectorClassVisitor(
				classWriter);

			ClassReader classReader = new ClassReader(classfileBuffer);
			classReader.accept(classVisitor, 0);

			classfileBuffer = classWriter.toByteArray();
		}

		return classfileBuffer;
	}

	private static FileLock _lockFile(File file) {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(
				file, "rw");

			FileChannel fileChannel = randomAccessFile.getChannel();

			return fileChannel.lock();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static ProjectData _readProjectData(File dataFile) {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		try {
			fileInputStream = new FileInputStream(dataFile);

			objectInputStream = new ObjectInputStream(fileInputStream);

			return (ProjectData)objectInputStream.readObject();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				}
				catch (IOException ioe) {
				}
			}

			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				}
				catch (IOException ioe) {
				}
			}
		}
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

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(dataFile);

			objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(projectData);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				}
				catch (IOException ioe) {
				}
			}

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	private boolean _matches(String className) {
		if (_excludePatterns.length != 0) {
			for (Pattern excludePattern : _excludePatterns) {

				Matcher matcher = excludePattern.matcher(className);

				if (matcher.matches()) {
					return false;
				}
			}
		}

		if (_includePatterns.length != 0) {
			for (Pattern includePattern : _includePatterns) {

				Matcher matcher = includePattern.matcher(className);

				if (matcher.matches()) {
					return true;
				}
			}
		}

		return false;
	}

	private static final File _lockFile;

	static {
		File dataFile = CoverageDataFileHandler.getDefaultDataFile();

		File parentFolder = dataFile.getParentFile();

		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}

		// OS wide lock file, created by the first started process where we know
		// for sure there is no race condition. All data file accessing needs to
		// acquire exclusive lock on this lock file to prevent lost update.

		_lockFile = new File(parentFolder, "lock");

		try {
			_lockFile.createNewFile();
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	private Pattern[] _excludePatterns;
	private Pattern[] _includePatterns;
	private ConcurrentMap<ClassLoader, ProjectData> _projectDatas =
		new ConcurrentHashMap<ClassLoader, ProjectData>();

	private static class TouchCollectorClassVisitor extends ClassAdapter {

		public TouchCollectorClassVisitor(ClassVisitor classVisitor) {
			super(classVisitor);
		}

		@Override
		public MethodVisitor visitMethod(
			int access, String name, String desc, String signature,
			String[] exceptions) {

			MethodVisitor methodVisitor = cv.visitMethod(
				access, name, desc, signature, exceptions);

			if ((methodVisitor != null) && name.equals("<clinit>")) {

				methodVisitor = new TouchCollectorCLINITVisitor(methodVisitor);
			}

			return methodVisitor;
		}

	}

	private static class TouchCollectorCLINITVisitor extends MethodAdapter {

		public TouchCollectorCLINITVisitor(MethodVisitor methodVisitor) {
			super(methodVisitor);
		}

		@Override
		public void visitMethodInsn(
			int opcode, String owner, String name, String desc) {

			if ((opcode == Opcodes.INVOKESTATIC) &&
				owner.equals(
					"net/sourceforge/cobertura/coveragedata/ProjectData") &&
				name.equals("initialize") && desc.equals("()V")) {

				owner =
					"net/sourceforge/cobertura/instrument/InstrumentationAgent";
			}

			super.visitMethodInsn(opcode, owner, name, desc);
		}

	}

}