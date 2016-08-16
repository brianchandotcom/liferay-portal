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
public class RemoteExecutor {

	public void addCommand(String command) {
		_commands.add(command);
	}

	public void start(int threadCount, List<String> targetSlaves) {
		_targetSlaves.clear();

		_targetSlaves.addAll(targetSlaves);

		_threadsDurationTotal = 0;

		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);

		System.out.println(
			"Remote execution starting with " + threadCount + " threads.");

		try {
			_start = System.currentTimeMillis();

			for (String targetSlave : _targetSlaves) {
				executorService.execute(
					new RemoteExecutorThread(this, targetSlave));
			}

			while ((_finishedSlaves.size() + _errorSlaves.size()) <
						_targetSlaves.size()) {

				JenkinsResultsParserUtil.sleep(1000);
			}

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

	private long _getAverageThreadDuration() {
		int threadsCompletedCount =
			_finishedSlaves.size() + _errorSlaves.size();

		if (threadsCompletedCount == 0) {
			return 0;
		}

		return _threadsDurationTotal / threadsCompletedCount;
	}

	private synchronized void _onThreadComplete(
		RemoteExecutorThread remoteExecutorThread) {

		_busySlaves.remove(remoteExecutorThread._targetSlave);

		if (!remoteExecutorThread._error) {
			_finishedSlaves.add(remoteExecutorThread._targetSlave);
		}
		else {
			_errorSlaves.add(remoteExecutorThread._targetSlave);
		}

		_threadsDurationTotal += remoteExecutorThread._duration;

		StringBuffer sb = new StringBuffer();

		sb.append("Average thread duration: ");
		sb.append(_getAverageThreadDuration());
		sb.append("ms\nBusy slaves:");
		sb.append(_busySlaves.size());
		sb.append("\nFinished slaves:");
		sb.append(_finishedSlaves.size());
		sb.append("\nTarget slaves:");
		sb.append(_targetSlaves.size());
		sb.append("\nTotal duration: ");
		sb.append(System.currentTimeMillis() - _start);
		sb.append("\n");

		System.out.println(sb.toString());

		if ((_finishedSlaves.size() + _errorSlaves.size()) ==
				_targetSlaves.size()) {

			System.out.println(
				"Remote execution completed in " +
					(System.currentTimeMillis() - _start) + "ms.");
		}
	}

	private synchronized void _onThreadStart(
		RemoteExecutorThread remoteExecutorThread) {

		_busySlaves.add(remoteExecutorThread._targetSlave);
	}

	private final List<String> _busySlaves = new ArrayList<>();
	private final List<String> _commands = new ArrayList<>();
	private final List<String> _errorSlaves = new ArrayList<>();
	private final List<String> _finishedSlaves = new ArrayList<>();
	private long _start;
	private final List<String> _targetSlaves = new ArrayList<>();
	private long _threadsDurationTotal;

	private static class RemoteExecutorThread implements Runnable {

		@Override
		public void run() {
			_remoteExecutor._onThreadStart(this);

			_error = false;

			long start = System.currentTimeMillis();

			try {
				int returnCode = _executeBashCommands();

				_duration = System.currentTimeMillis() - start;

				if (returnCode != 0) {
					_handleError(null);
				}
			}
			catch (Exception ioe) {
				_handleError(ioe.getMessage());
			}
			finally {
				_remoteExecutor._onThreadComplete(this);
			}
		}

		private RemoteExecutorThread(
			RemoteExecutor remoteExecutor, String targetSlave) {

			_remoteExecutor = remoteExecutor;

			_targetSlave = targetSlave;
		}

		private int _executeBashCommands()
			throws InterruptedException, IOException {

			StringBuffer sb = new StringBuffer("ssh ");

			sb.append(_targetSlave);
			sb.append(" '");

			List<String> commands = _remoteExecutor._commands;

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
			_error = true;

			System.out.println(
				"Remote execution failed on target slave: " + _targetSlave +
					".\n");

			if ((errorMessage != null) && !errorMessage.isEmpty()) {
				System.out.println(errorMessage);
			}
		}

		private long _duration;
		private boolean _error;
		private final RemoteExecutor _remoteExecutor;
		private boolean _successful;
		private final String _targetSlave;

	}

}