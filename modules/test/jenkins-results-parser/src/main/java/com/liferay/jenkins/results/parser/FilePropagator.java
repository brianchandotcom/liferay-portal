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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Peter Yoo
 */
public class FilePropagator {

	public FilePropagator(
		String[] fileNames, String sourceDirName, String targetDirName,
		List<String> targetSlaves) {

		for (String fileName : fileNames) {
			_filePropagatorTasks.add(
				new FilePropagatorTask(
					sourceDirName + "/" + fileName,
					targetDirName + "/" + fileName));
		}

		_targetSlaves.addAll(targetSlaves);

		_copyFromSource();
	}

	public long getAverageThreadDuration() {
		if (_threadsCompletedCount == 0) {
			return 0;
		}

		return _threadsDurationTotal / _threadsCompletedCount;
	}

	public void start(int threadCount) {
		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);

		System.out.println(
			"File propagation starting with " + threadCount + " threads.");

		try {
			long start = System.currentTimeMillis();

			while (!_targetSlaves.isEmpty() || !_busySlaves.isEmpty()) {
				synchronized(this) {
					for (String mirrorSlave : _mirrorSlaves) {
						if (_targetSlaves.isEmpty()) {
							break;
						}

						String targetSlave = _targetSlaves.remove(0);

						executorService.execute(
							new FilePropagatorThread(
								this, mirrorSlave, targetSlave));

						_busySlaves.add(mirrorSlave);
						_busySlaves.add(targetSlave);
					}

					_mirrorSlaves.removeAll(_busySlaves);
				}

				StringBuffer sb = new StringBuffer();

				sb.append("Average thread duration: ");
				sb.append(getAverageThreadDuration());
				sb.append("ms\nBusy slaves:");
				sb.append(_busySlaves.size());
				sb.append("\nMirror slaves:");
				sb.append(_mirrorSlaves.size());
				sb.append("\nTarget slaves:");
				sb.append(_targetSlaves.size());
				sb.append("\nTotal duration: ");
				sb.append(System.currentTimeMillis() - start);
				sb.append("\n");

				System.out.println(sb.toString());

				JenkinsResultsParserUtil.sleep(5000);
			}

			System.out.println(
				"File propagation completed in " +
					(System.currentTimeMillis() - start) + "ms.");

			if (!_errorSlaves.isEmpty()) {
				System.out.println(
					_errorSlaves.size() + " slaves failed to respond:\n" +
						_errorSlaves);
			}
		}
		finally {
			executorService.shutdown();
		}
	}

	private static String _readInputStream(InputStream inputStream) {
		try {
			StringBuffer sb = new StringBuffer();

			byte[] bytes = new byte[1024];

			int size = inputStream.read(bytes);

			while (size > 0) {
				sb.append(new String(Arrays.copyOf(bytes, size)));

				size = inputStream.read(bytes);
			}

			return sb.toString();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _copyFromSource() {
		List<String> commands = new ArrayList<>();

		String targetSlave = null;

		for (FilePropagatorTask filePropagatorTask : _filePropagatorTasks) {
			targetSlave = _targetSlaves.get(0);

			String sourceFileName = filePropagatorTask._sourceFileName;

			System.out.println("Copying from source " + sourceFileName);

			String targetFileName = filePropagatorTask._targetFileName;

			commands.add(_getMkdirCommand(targetFileName));

			if (sourceFileName.startsWith("http")) {
				commands.add(
					" curl -o " + targetFileName + " " + sourceFileName);
			}
			else {
				commands.add(
					"rsync -vI " + sourceFileName + " " + targetFileName);
			}

			String targetDirName = targetFileName.substring(
				0, targetFileName.lastIndexOf("/"));

			commands.add("ls -al " + targetDirName);
		}

		try {
			if (_executeCommands(commands, true, targetSlave) != 0) {
				throw new RuntimeException(
					"Unable to copy from source. Executed: " + commands);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to copy from source. Executed: " + commands, e);
		}

		if (targetSlave != null) {
			_mirrorSlaves.add(targetSlave);

			_targetSlaves.remove(targetSlave);
		}

		System.out.println("Finished copying from source.");
	}

	private int _executeCommands(
			List<String> commands, boolean printOutput, String targetSlave)
		throws IOException {

		File shellFile = _writeShellFile(commands, targetSlave);

		try {
			FileSystem fileSystem = FileSystems.getDefault();

			System.out.println(
				"Executing commands:\n" +
					new String(
						Files.readAllBytes(
							fileSystem.getPath(shellFile.getAbsolutePath()))));

			String errorText = null;
			Integer exitCode = null;
			Process process = null;

			long start = System.currentTimeMillis();

			while (exitCode == null) {
				try {
					Runtime runtime = Runtime.getRuntime();

					process = runtime.exec("./" + shellFile.getName());
				}
				catch (IOException ioe) {
					String message = ioe.getMessage();

					if (message.contains("Text file busy") &&
						(System.currentTimeMillis() - start) < 2000) {

						JenkinsResultsParserUtil.sleep(100);

						continue;
					}

					throw ioe;
				}

				if (printOutput) {
					System.out.println(
						_readInputStream(process.getInputStream()));
				}

				errorText = _readInputStream(process.getErrorStream());

				if (errorText.contains("No buffer space available") ||
					errorText.contains(
						"Temporary failure in name resolution")) {

					System.out.println(errorText + "\nRetry in 1 minute.");

					JenkinsResultsParserUtil.sleep(1000 * 60);

					continue;
				}

				try {
					exitCode = process.waitFor();
				}
				catch (InterruptedException ie) {
					throw new RuntimeException(ie);
				}

				process.destroy();
			}

			errorText = errorText.trim();

			if (!errorText.isEmpty()) {
				System.out.println(errorText);
			}

			return exitCode;
		}
		finally {
			if ((shellFile != null) && shellFile.exists()) {
				shellFile.delete();
			}
		}
	}

	private String _getMkdirCommand(String fileName) {
		String dirName = fileName.substring(0, fileName.lastIndexOf("/") + 1);

		return "mkdir -pv " + dirName;
	}

	private File _writeShellFile(List<String> commands, String targetSlave)
		throws IOException {

		File file = new File(commands.hashCode() + ".sh");

		StringBuffer sb = new StringBuffer("ssh ");

		sb.append(targetSlave);
		sb.append(" '");

		for (int i = 0; i < commands.size(); i++) {
			sb.append(commands.get(i));

			if (i < (commands.size() -1)) {
				sb.append(" && ");
			}
		}

		sb.append("'\n");

		try {
			try (FileWriter fileWriter = new FileWriter(file)) {
				fileWriter.write(sb.toString());
			}

			file.setExecutable(true);

			return file;
		}
		catch (IOException ioe) {
			if ((file != null) && file.exists()) {
				file.delete();
			}

			throw ioe;
		}
	}

	private final List<String> _busySlaves = new ArrayList<>();
	private final List<String> _errorSlaves = new ArrayList<>();
	private final List<FilePropagatorTask> _filePropagatorTasks =
		new ArrayList<>();
	private final List<String> _mirrorSlaves = new ArrayList<>();
	private final List<String> _targetSlaves = new ArrayList<>();
	private int _threadsCompletedCount;
	private long _threadsDurationTotal;

	private static class FilePropagatorTask {

		private FilePropagatorTask(
			String sourceFileName, String targetFileName) {

			_sourceFileName = sourceFileName;
			_targetFileName = targetFileName;
		}

		private final String _sourceFileName;
		private final String _targetFileName;

	}

	private static class FilePropagatorThread implements Runnable {

		@Override
		public void run() {
			long start = System.currentTimeMillis();

			List<String> commands = new ArrayList<>(
				_filePropagator._filePropagatorTasks.size());

			for (FilePropagatorTask filePropagatorTask :
					_filePropagator._filePropagatorTasks) {

				commands.add(
					_filePropagator._getMkdirCommand(
						filePropagatorTask._targetFileName));

				commands.add(
					"rsync -vI " + _mirrorSlave + ":" +
						filePropagatorTask._targetFileName + " " +
							filePropagatorTask._targetFileName);
			}

			try {
				_successful = _filePropagator._executeCommands(
					commands, false, _targetSlave) == 0;
			}
			catch (Exception e) {
				_successful = false;
			}

			_duration = System.currentTimeMillis() - start;

			synchronized(_filePropagator) {
				_filePropagator._busySlaves.remove(_mirrorSlave);
				_filePropagator._busySlaves.remove(_targetSlave);
				_filePropagator._mirrorSlaves.add(_mirrorSlave);
				_filePropagator._threadsCompletedCount++;
				_filePropagator._threadsDurationTotal += _duration;

				if (!_successful) {
					_filePropagator._errorSlaves.add(_targetSlave);

					return;
				}

				_filePropagator._mirrorSlaves.add(_targetSlave);
			}
		}

		private FilePropagatorThread(
			FilePropagator filePropagator, String mirrorSlave,
			String targetSlave) {

			_filePropagator = filePropagator;
			_mirrorSlave = mirrorSlave;
			_targetSlave = targetSlave;
		}

		private long _duration;
		private final FilePropagator _filePropagator;
		private final String _mirrorSlave;
		private boolean _successful;
		private final String _targetSlave;

	}

}