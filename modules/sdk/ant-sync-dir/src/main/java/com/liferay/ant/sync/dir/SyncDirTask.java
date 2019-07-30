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
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 * @author Minhchau Dang
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

	public void setSymbolic(Boolean symbolic) {
		_symbolic = symbolic;
	}

	public void setToDir(File toDir) {
		_toDir = toDir;
	}

	private List<SyncFileRunnable> _buildSyncFileRunnables(
		File dir, File toDir, List<SyncFileRunnable> syncFileRunnables,
		AtomicInteger atomicInteger) {

		toDir.mkdirs();

		for (File fromFile : dir.listFiles()) {
			String name = fromFile.getName();

			File toFile = new File(toDir, name);

			if (fromFile.isDirectory()) {
				_buildSyncFileRunnables(
					fromFile, toFile, syncFileRunnables, atomicInteger);
			}
			else if (!toFile.exists()) {
				SyncFileRunnable syncFileRunnable = new SyncFileRunnable(
					fromFile, toFile, atomicInteger, _symbolic);

				syncFileRunnables.add(syncFileRunnable);
			}
		}

		return syncFileRunnables;
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

		List<SyncFileRunnable> syncFileRunnables = _buildSyncFileRunnables(
			_dir, _toDir, new ArrayList<SyncFileRunnable>(), atomicInteger);

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		for (SyncFileRunnable syncFileRunnable : syncFileRunnables) {
			executorService.execute(syncFileRunnable);
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

	private File _dir;
	private boolean _symbolic = true;
	private File _toDir;

	private static class SyncFileRunnable implements Runnable {

		public SyncFileRunnable(
			File file, File toFile, AtomicInteger atomicInteger,
			boolean symbolic) {

			_file = file;
			_toFile = toFile;
			_atomicInteger = atomicInteger;
			_symbolic = symbolic;
		}

		public void run() {
			if (_symbolic) {
				try {
					Files.createSymbolicLink(
						Paths.get(_toFile.toURI()), Paths.get(_file.toURI()));
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to sync " + _file + " into " + _toFile, ioe);
				}
			}
			else {
				try {
					Files.copy(
						Paths.get(_file.toURI()), Paths.get(_toFile.toURI()));
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to sync " + _file + " into " + _toFile, ioe);
				}
			}

			_atomicInteger.incrementAndGet();
		}

		private final AtomicInteger _atomicInteger;
		private final File _file;
		private boolean _symbolic;
		private final File _toFile;

	}

}