/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.sync.dir;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	public void setThreadCount(Integer threadCount) {
		_threadCount = threadCount;
	}

	public void setToDir(File toDir) {
		_toDir = toDir;
	}

	private List<SyncFileCallable> _buildSyncFileCallables(
		File dir, File toDir, List<SyncFileCallable> syncFileCallables) {

		toDir.mkdirs();

		for (File fromFile : dir.listFiles()) {
			File toFile = new File(toDir, fromFile.getName());

			if (fromFile.isDirectory()) {
				_buildSyncFileCallables(fromFile, toFile, syncFileCallables);
			}
			else if (!toFile.exists()) {
				SyncFileCallable syncFileCallable = new SyncFileCallable(
					fromFile, toFile, _symbolic);

				syncFileCallables.add(syncFileCallable);
			}
		}

		return syncFileCallables;
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
		List<SyncFileCallable> syncFileCallables = _buildSyncFileCallables(
			_dir, _toDir, new ArrayList<SyncFileCallable>());

		ExecutorService executorService = Executors.newFixedThreadPool(
			_threadCount);

		List<Future<Integer>> futures;

		try {
			futures = executorService.invokeAll(syncFileCallables);

			executorService.shutdown();

			int syncronizedFileCount = 0;

			for (Future<Integer> future : futures) {
				syncronizedFileCount += future.get();
			}

			return syncronizedFileCount;
		}
		catch (Exception exception) {
			exception.printStackTrace();

			throw new RuntimeException(exception);
		}
	}

	private File _dir;
	private boolean _symbolic = true;
	private int _threadCount = 10;
	private File _toDir;

	private static class SyncFileCallable implements Callable<Integer> {

		public SyncFileCallable(File file, File toFile, boolean symbolic) {
			_file = file;
			_toFile = toFile;
			_symbolic = symbolic;
		}

		@Override
		public Integer call() {
			try {
				if (_symbolic) {
					Files.createSymbolicLink(
						Paths.get(_toFile.toURI()), Paths.get(_file.toURI()));
				}
				else {
					Files.copy(
						Paths.get(_file.toURI()), Paths.get(_toFile.toURI()));
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to sync " + _file + " into " + _toFile,
					ioException);
			}

			return 1;
		}

		private final File _file;
		private boolean _symbolic;
		private final File _toFile;

	}

}