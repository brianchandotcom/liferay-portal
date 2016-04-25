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
		String[] fileNames, String originDirPath, String dirPath,
		List<String> targetSlaves) {

		for (String fileName : fileNames) {
			_filePropagatorTasks.add(
				new FilePropagatorTask(
					dirPath + "/" + fileName, originDirPath + "/" + fileName));
		}

		_targetSlaves.addAll(targetSlaves);

		_copyFromOrigin();
	}

	public long getAverageDuration() {
		if (_filePropogatorThreadCompletedCount == 0) {
			return 0;
		}

		return _totalFilePropogatorThreadsDuration /
			_filePropogatorThreadCompletedCount;
	}

	public void start(int threadCount) {
		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);
		System.out.println(
			"File Propagation starting with " + threadCount + " threads.");

		try {
			long start = System.currentTimeMillis();

			while (!_targetSlaves.isEmpty() || !_busySlaves.isEmpty()) {
				synchronized(this) {
					for (String sourceSlave : _sourceSlaves) {
						if (_targetSlaves.isEmpty()) {
							break;
						}

						String targetSlave = _targetSlaves.remove(0);

						executorService.execute(
							new FilePropagatorThread(
								this, sourceSlave, targetSlave));

						_busySlaves.add(sourceSlave);
						_busySlaves.add(targetSlave);
					}

					_sourceSlaves.removeAll(_busySlaves);
				}

				StringBuffer sb = new StringBuffer();

				sb.append("Busy slaves:");
				sb.append(Integer.toString(_busySlaves.size()));
				sb.append("\nSource slaves:");
				sb.append(Integer.toString(_sourceSlaves.size()));
				sb.append("\nTarget slaves:");
				sb.append(Integer.toString(_targetSlaves.size()));
				sb.append("\nTotal duration: ");
				sb.append(Long.toString((System.currentTimeMillis() - start)));
				sb.append("\nAverage duration: ");
				sb.append(Long.toString(getAverageDuration()));
				sb.append("ms\n");

				System.out.println(sb.toString());

				JenkinsResultsParserUtil.sleep(5000);
			}

			StringBuffer sb = new StringBuffer();

			sb.append("File Propagation complete.\nTotal duration: ");
			sb.append(Long.toString(System.currentTimeMillis() - start));
			sb.append("ms\n");
			sb.append("Average duration: ");
			sb.append(Long.toString(getAverageDuration()));
			sb.append("ms\n");
			sb.append(Integer.toString(_errorSlaves.size()));
			sb.append(" failures occurred.\n");

			if (_errorSlaves.size() > 0) {
				sb.append("Slaves that failed to respond:\n");
				sb.append(_errorSlaves.toString());
			}

			System.out.println(sb.toString());
		}
		finally {
			executorService.shutdown();
		}
	}

	private static String _readInputStream(InputStream inputStream) {
		try {
			byte[] inputBytes = new byte[1024];

			int size = inputStream.read(inputBytes);

			StringBuffer sb = new StringBuffer();

			while (size > 0) {
				sb.append(new String(Arrays.copyOf(inputBytes, size)));

				size = inputStream.read(inputBytes);
			}

			return sb.toString();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private void _copyFromOrigin() {
		List<String> commands = new ArrayList<>();

		String targetSlave = null;

		for (FilePropagatorTask filePropagatorTask : _filePropagatorTasks) {
			String originFilePath = filePropagatorTask._originFilePath;

			System.out.println("Copying from origin: " + originFilePath);

			targetSlave = _targetSlaves.get(0);

			String filePath = filePropagatorTask._filePath;

			commands.add(_getMkdirCommand(filePath));

			if (originFilePath.startsWith("http")) {
				commands.add(" curl -o " + filePath + " " + originFilePath);
			}
			else {
				commands.add("rsync -vI " + originFilePath + " " + filePath);
			}

			String fileDirPath = filePath.substring(
				0, filePath.lastIndexOf("/"));

			commands.add("ls -al " + fileDirPath);
		}

		try {
			if (_executeCommands(commands, true, targetSlave) != 0) {
				throw new RuntimeException(
					"Copy from origin failed. Commands executed: " + commands);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Copy from origin failed. Commands executed: " + commands, e);
		}

		if (targetSlave != null) {
			_targetSlaves.remove(targetSlave);

			_sourceSlaves.add(targetSlave);
		}

		System.out.println("Copy from origin complete.");
	}

	private int _executeCommands(
			List<String> commands, boolean printOutput, String targetSlave)
		throws IOException {

		File shellFile = _writeShellFile(commands, targetSlave);

		try {
			FileSystem fileSystem = FileSystems.getDefault();

			System.out.println("Executing commands:\n" +
				new String(
					Files.readAllBytes(
						fileSystem.getPath(shellFile.getAbsolutePath()))));

			String errorText = null;
			Integer exitCode = null;
			Process process = null;

			long start = System.currentTimeMillis();

			while (exitCode == null) {
				try {
					process = Runtime.getRuntime().exec(
						"./" + shellFile.getName());
				}
				catch (IOException ioe) {
					if (ioe.getMessage().contains("Text file busy") &&
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

	private String _getMkdirCommand(String filePath) {
		String directoryPath = filePath.substring(
			0, (filePath.lastIndexOf("/") + 1));
		return "mkdir -pv " + directoryPath;
	}

	private void _recordFilePropagatorThreadCompletion(
		FilePropagatorThread filePropagatorThread) {

		synchronized(this) {
			_filePropogatorThreadCompletedCount++;

			_totalFilePropogatorThreadsDuration +=
				filePropagatorThread._duration;

			_busySlaves.remove(filePropagatorThread._sourceSlave);
			_busySlaves.remove(filePropagatorThread._targetSlave);

			_sourceSlaves.add(filePropagatorThread._sourceSlave);

			if (!filePropagatorThread._successful) {
				_errorSlaves.add(filePropagatorThread._targetSlave);

				return;
			}

			_sourceSlaves.add(filePropagatorThread._targetSlave);
		}
	}

	private File _writeShellFile(List<String> commands, String targetSlave)
		throws IOException {

		String shellFileName = Integer.toString(commands.hashCode());

		File shellFile = new File(shellFileName + ".sh");

		StringBuffer sb = new StringBuffer("ssh ");

		sb.append(targetSlave);
		sb.append(" '");

		for (int i = 0; i < commands.size(); i++) {
			sb.append(commands.get(i));

			if (i < (commands.size() -1)) {
				sb.append("; ");
			}
		}

		sb.append("'\n");

		try {
			try (FileWriter shellFileWriter = new FileWriter(shellFile)) {
				shellFileWriter.write(sb.toString());
			}

			shellFile.setExecutable(true);

			return shellFile;
		}
		catch (IOException ioe) {
			if ((shellFile != null) && shellFile.exists()) {
				shellFile.delete();
			}

			throw ioe;
		}
	}

	private final List<String> _busySlaves = new ArrayList<>();
	private final List<String> _errorSlaves = new ArrayList<>();
	private final List<FilePropagatorTask> _filePropagatorTasks =
		new ArrayList<>();
	private int _filePropogatorThreadCompletedCount;
	private final List<String> _sourceSlaves = new ArrayList<>();
	private final List<String> _targetSlaves = new ArrayList<>();
	private long _totalFilePropogatorThreadsDuration = 0;

	private static class FilePropagatorTask {

		private FilePropagatorTask(String filePath, String originFilePath) {
			_filePath = filePath;
			_originFilePath = originFilePath;
		}

		private final String _filePath;
		private final String _originFilePath;

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
						filePropagatorTask._filePath));

				commands.add(
					"rsync -vI " + _sourceSlave + ":" +
						filePropagatorTask._filePath + " " +
							filePropagatorTask._filePath);
			}

			try {
				_successful = _filePropagator._executeCommands(
					commands, false, _targetSlave) == 0;
			}
			catch (Exception e) {
				_successful = false;
			}

			_duration = System.currentTimeMillis() - start;

			_filePropagator._recordFilePropagatorThreadCompletion(this);
		}

		private FilePropagatorThread(
			FilePropagator filePropagator, String sourceSlave,
			String targetSlave) {

			_filePropagator = filePropagator;
			_sourceSlave = sourceSlave;
			_targetSlave = targetSlave;
		}

		private long _duration = 0;
		private final FilePropagator _filePropagator;
		private final String _sourceSlave;
		private boolean _successful;
		private final String _targetSlave;

	}

}