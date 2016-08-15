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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Peter Yoo
 */
public class CommandPropagator {

	public void addCommand(String command) {
		_commands.add(command);
	}

	public void addErrorSlave(String errorSlave) {
		_targetSlaves.remove(errorSlave);

		_errorSlaves.add(errorSlave);
	}

	public long getAverageThreadDuration() {
		if (_threadsCompletedCount == 0) {
			return 0;
		}

		return _threadsDurationTotal / _threadsCompletedCount;
	}

	public void start(int threadCount, List<String> targetSlaves) {
		_targetSlaves.clear();

		_targetSlaves.addAll(targetSlaves);

		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);

		System.out.println(
			"Command propagation starting with " + threadCount + " threads.");

		try {
			long start = System.currentTimeMillis();

			for (String targetSlave : _targetSlaves) {
				executorService.execute(
					new CommandPropagatorThread(this, targetSlave));
			}

			while ((_finishedSlaves.size() + _errorSlaves.size()) <
						_targetSlaves.size()) {

				StringBuffer sb = new StringBuffer();

				sb.append("Average thread duration: ");
				sb.append(getAverageThreadDuration());
				sb.append("ms\nBusy slaves:");
				sb.append(_busySlaves.size());
				sb.append("\nFinished slaves:");
				sb.append(_finishedSlaves.size());
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

	private final List<String> _busySlaves = Collections.synchronizedList(
		new ArrayList<String>());
	private final List<String> _commands = new ArrayList<>();
	private final List<String> _errorSlaves = Collections.synchronizedList(
		new ArrayList<String>());
	private final List<String> _finishedSlaves = Collections.synchronizedList(
		new ArrayList<String>());
	private final List<String> _targetSlaves = Collections.synchronizedList(
		new ArrayList<String>());
	private int _threadsCompletedCount;
	private long _threadsDurationTotal;

	private static class CommandPropagatorThread implements Runnable {

		@Override
		public void run() {
			List<String> busySlaves = _commandPropagator._busySlaves;

			busySlaves.add(_targetSlave);

			long start = System.currentTimeMillis();

			try {
				int returnCode = _executeBashCommands();

				_duration = System.currentTimeMillis() - start;

				if (returnCode == 0) {
					_successful = true;

					List<String> finishedSlaves =
						_commandPropagator._finishedSlaves;

					finishedSlaves.add(_targetSlave);
				}
				else {
					_handleError(null);
				}
			}
			catch (Exception ioe) {
				_handleError(ioe.getMessage());
			}
			finally {
				busySlaves.remove(_targetSlave);

				synchronized(_commandPropagator) {
					_commandPropagator._threadsCompletedCount++;

					_commandPropagator._threadsDurationTotal += _duration;
				}
			}
		}

		private CommandPropagatorThread(
			CommandPropagator commandPropagator, String targetSlave) {

			_commandPropagator = commandPropagator;

			_targetSlave = targetSlave;
		}

		private int _executeBashCommands()
			throws InterruptedException, IOException {

			StringBuffer sb = new StringBuffer("ssh ");

			sb.append(_targetSlave);
			sb.append(" '");

			List<String> commands = _commandPropagator._commands;

			for (int i = 0; i < commands.size(); i++) {
				sb.append(commands.get(i));

				if (i < (commands.size() -1)) {
					sb.append(" ; ");
				}
			}

			sb.append("'");

			Process process = JenkinsResultsParserUtil.executeBashCommands(
				sb.toString());

			return process.exitValue();
		}

		private void _handleError(String errorMessage) {
			_successful = false;

			List<String> errorSlaves = _commandPropagator._errorSlaves;

			errorSlaves.add(_targetSlave);

			System.out.println(
				"Command execution failed on target slave: " + _targetSlave +
					".\n");

			if ((errorMessage != null) && !errorMessage.isEmpty()) {
				System.out.println(errorMessage);
			}
		}

		private final CommandPropagator _commandPropagator;
		private long _duration;
		private boolean _successful;
		private final String _targetSlave;

	}

}