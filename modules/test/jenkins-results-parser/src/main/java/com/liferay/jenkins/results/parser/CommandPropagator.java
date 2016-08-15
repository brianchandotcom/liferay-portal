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

		_targetSlaves.addAll(targetSlaves);
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

	private int _executeBashCommands(List<String> commands, String targetSlave)
		throws InterruptedException, IOException {

		StringBuffer sb = new StringBuffer("ssh ");

		sb.append(targetSlave);
		sb.append(" '");

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

	private final List<String> _busySlaves = new ArrayList<>();
	private final List<String> _errorSlaves = new ArrayList<>();
	private final List<String> _mirrorSlaves = new ArrayList<>();
	private final List<String> _targetSlaves = new ArrayList<>();
	private int _threadsCompletedCount;
	private long _threadsDurationTotal;


	private static class CommandPropagatorThread implements Runnable {

		@Override
		public void run() {
			long start = System.currentTimeMillis();

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