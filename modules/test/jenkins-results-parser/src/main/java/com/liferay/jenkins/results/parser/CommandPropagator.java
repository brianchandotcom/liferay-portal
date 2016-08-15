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

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Peter Yoo
 */
public class CommandPropagator {

	public CommandPropagator(
		String[] fileNames, String sourceDirName, String targetDirName,
		List<String> targetSlaves) {

		for (String fileName : fileNames) {
			_commandPropagatorTasks.add(
				new CommandPropagatorTask(
					sourceDirName + "/" + fileName,
					targetDirName + "/" + fileName));
		}

		_targetSlaves.addAll(targetSlaves);
	}

	public long getAverageThreadDuration() {
		if (_threadsCompletedCount == 0) {
			return 0;
		}

		return _threadsDurationTotal / _threadsCompletedCount;
	}

	public void setCleanUpCommand(String cleanUpCommand) {
		_cleanUpCommand = cleanUpCommand;
	}

	public void start(int threadCount) {
		_copyFromSource();

		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);

		System.out.println(
			"Command propagation starting with " + threadCount + " threads.");

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
							new CommandPropagatorThread(
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
				"Command propagation completed in " +
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

	private void _copyFromSource() {
		List<String> commands = new ArrayList<>();

		String targetSlave = null;

		for (CommandPropagatorTask commandPropagatorTask : _commandPropagatorTasks) {
			targetSlave = _targetSlaves.get(0);

			String sourceFileName = commandPropagatorTask._sourceFileName;

			System.out.println("Copying from source " + sourceFileName);

			String targetFileName = commandPropagatorTask._targetFileName;

			commands.add(_getMkdirCommand(targetFileName));

			if (sourceFileName.startsWith("http")) {
				commands.add(
					"curl -o " + targetFileName + " " + sourceFileName);
			}
			else {
				commands.add(
					"rsync -svI " + sourceFileName + " " + targetFileName);
			}

			String targetDirName = targetFileName.substring(
				0, targetFileName.lastIndexOf("/"));

			commands.add("ls -al " + targetDirName);
		}

		try {
			if (_executeBashCommands(commands, targetSlave) != 0) {
				_errorSlaves.add(targetSlave);
				_targetSlaves.remove(targetSlave);

				_copyFromSource();

				targetSlave = null;
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

	private int _executeBashCommands(List<String> commands, String targetSlave)
		throws InterruptedException, IOException {

		StringBuffer sb = new StringBuffer("ssh ");

		sb.append(targetSlave);
		sb.append(" '");

		if ((_cleanUpCommand != null) && !_cleanUpCommand.isEmpty()) {
			sb.append(_cleanUpCommand);
			sb.append("; ");
		}

		for (int i = 0; i < commands.size(); i++) {
			sb.append(commands.get(i));

			if (i < (commands.size() -1)) {
				sb.append(" && ");
			}
		}

		sb.append("'");

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			sb.toString());

		return process.exitValue();
	}

	private String _getMkdirCommand(String fileName) {
		String dirName = fileName.substring(0, fileName.lastIndexOf("/") + 1);

		return "mkdir -pv " + dirName;
	}

	private final List<String> _busySlaves = new ArrayList<>();
	private String _cleanUpCommand;
	private final List<String> _errorSlaves = new ArrayList<>();
	private final List<CommandPropagatorTask> _commandPropagatorTasks =
		new ArrayList<>();
	private final List<String> _mirrorSlaves = new ArrayList<>();
	private final List<String> _targetSlaves = new ArrayList<>();
	private int _threadsCompletedCount;
	private long _threadsDurationTotal;

	private static class CommandPropagatorTask {

		private CommandPropagatorTask(
			String sourceFileName, String targetFileName) {

			_sourceFileName = _escapeParentheses(sourceFileName);
			_targetFileName = _escapeParentheses(targetFileName);
		}

		private String _escapeParentheses(String fileName) {
			fileName = fileName.replace(")", "\\)");
			fileName = fileName.replace("(", "\\(");

			return fileName;
		}

		private final String _sourceFileName;
		private final String _targetFileName;

	}

	private static class CommandPropagatorThread implements Runnable {

		@Override
		public void run() {
			long start = System.currentTimeMillis();

			List<CommandPropagatorTask> commandPropagatorTasks =
				_commandPropagator._commandPropagatorTasks;

			List<String> commands = new ArrayList<>(commandPropagatorTasks.size());

			for (CommandPropagatorTask commandPropagatorTask : commandPropagatorTasks) {
				commands.add(
					_commandPropagator._getMkdirCommand(
						commandPropagatorTask._targetFileName));

				commands.add(
					"rsync -svI " + _mirrorSlave + ":" +
						commandPropagatorTask._targetFileName + " " +
							commandPropagatorTask._targetFileName);
			}

			try {
				_successful = _commandPropagator._executeBashCommands(
					commands, _targetSlave) == 0;
			}
			catch (Exception e) {
				_successful = false;
			}

			_duration = System.currentTimeMillis() - start;

			synchronized(_commandPropagator) {
				_commandPropagator._busySlaves.remove(_mirrorSlave);
				_commandPropagator._busySlaves.remove(_targetSlave);
				_commandPropagator._mirrorSlaves.add(_mirrorSlave);
				_commandPropagator._threadsCompletedCount++;
				_commandPropagator._threadsDurationTotal += _duration;

				if (!_successful) {
					_commandPropagator._errorSlaves.add(_targetSlave);

					return;
				}

				_commandPropagator._mirrorSlaves.add(_targetSlave);
			}
		}

		private CommandPropagatorThread(
			CommandPropagator commandPropagator, String mirrorSlave,
			String targetSlave) {

			_commandPropagator = commandPropagator;
			_mirrorSlave = mirrorSlave;
			_targetSlave = targetSlave;
		}

		private long _duration;
		private final CommandPropagator _commandPropagator;
		private final String _mirrorSlave;
		private boolean _successful;
		private final String _targetSlave;

	}

}