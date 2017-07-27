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

package com.liferay.ant.sync.dir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class SyncDirTask extends Task {

	@Override
	public void execute() throws BuildException {
		_checkConfiguration();

		log("Synchronizing " + _dir + " into " + _toDir);

		long start = System.currentTimeMillis();

		int count = _syncDirectory();

		log(
			count + " files synchronized in " +
				(System.currentTimeMillis() - start) + "ms");
	}

	public void setDir(File dir) {
		_dir = dir;
	}

	public void setToDir(File toDir) {
		_toDir = toDir;
	}

	private void _checkConfiguration() throws BuildException {
		if (_dir == null) {
			throw new BuildException("No directory specified", getLocation());
		}

		if (!_dir.exists()) {
			throw new BuildException("Directory " + _dir + " does not exist");
		}

		if (!_dir.isDirectory()) {
			throw new BuildException(_dir + " is not a directory");
		}

		if (_toDir == null) {
			throw new BuildException(
				"No destination directory specified", getLocation());
		}
	}

	private int _syncDirectory() {
		AtomicInteger atomicInteger = new AtomicInteger();

		List<SyncFileCallable> copyTasks = _syncDirectory(
			_dir, _toDir, new ArrayList<SyncFileCallable>(), atomicInteger);

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		for (SyncFileCallable copyTask : copyTasks) {
			executorService.submit(copyTask);
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(
				Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		return atomicInteger.intValue();
	}

	private List<SyncFileCallable> _syncDirectory(
		File dir, File toDir, List<SyncFileCallable> copyTasks,
		AtomicInteger atomicInteger) {

		toDir.mkdirs();

		for (File fromFile : dir.listFiles()) {
			String name = fromFile.getName();

			File toFile = new File(toDir, name);

			if (fromFile.isDirectory()) {
				_syncDirectory(fromFile, toFile, copyTasks, atomicInteger);
			}
			else if (!toFile.exists()) {
				SyncFileCallable copyTask = new SyncFileCallable(
					fromFile, toFile, atomicInteger);

				copyTasks.add(copyTask);
			}
		}

		return copyTasks;
	}

	private File _dir;
	private File _toDir;

	private static class SyncFileCallable implements Callable<Boolean> {

		public SyncFileCallable(
			File file, File toFile, AtomicInteger atomicInteger) {

			_file = file;
			_toFile = toFile;
			_atomicInteger = atomicInteger;
		}

		public Boolean call() throws IOException {
			FileInputStream inputStream = new FileInputStream(_file);
			FileOutputStream outputStream = new FileOutputStream(_toFile);

			try {
				byte[] buffer = new byte[8192];

				int count;

				while ((count = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, count);
				}
			}
			finally {
				_atomicInteger.incrementAndGet();

				inputStream.close();

				outputStream.close();
			}

			return Boolean.TRUE;
		}

		private final AtomicInteger _atomicInteger;
		private final File _file;
		private final File _toFile;

	}

}