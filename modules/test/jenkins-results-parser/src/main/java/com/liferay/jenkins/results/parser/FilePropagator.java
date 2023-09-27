/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * @author Peter Yoo
 */
public class FilePropagator {

	public FilePropagator(
		String[] fileNames, String sourceDirName, String targetDirName,
		List<String> targetSlaves) {

		this(fileNames, sourceDirName, targetDirName, null, targetSlaves);
	}

	public FilePropagator(
		String[] fileNames, String sourceDirName, String targetDirName,
		String primaryTargetSlave, List<String> targetSlaves) {

		this(
			fileNames, sourceDirName, targetDirName, primaryTargetSlave,
			targetSlaves, _TIMEOUT_DEFAULT);
	}

	public FilePropagator(
		String[] fileNames, String sourceDirName, String targetDirName,
		String primaryTargetSlave, List<String> targetSlaves, long timeout) {

		_timeout = timeout;

		for (String fileName : fileNames) {
			_filePropagatorTasks.add(
				new FilePropagatorTask(
					sourceDirName + "/" + fileName,
					targetDirName + "/" + fileName));
		}

		if (primaryTargetSlave != null) {
			targetSlaves.remove(primaryTargetSlave);

			_targetSlaves.add(primaryTargetSlave);
		}

		_targetSlaves.addAll(targetSlaves);
	}

	public long getAverageThreadDuration() {
		if (_threadsCompletedCount == 0) {
			return 0;
		}

		return _threadsDurationTotal / _threadsCompletedCount;
	}

	public List<String> getErrorSlaves() {
		return _errorSlaves;
	}

	public void setCleanUpCommand(String cleanUpCommand) {
		_cleanUpCommand = cleanUpCommand;
	}

	public void start(int threadCount) {
		_copyFromSource();

		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);

		System.out.println(
			"File propagation starting with " + threadCount + " threads.");

		try {
			String previousString = null;
			long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

			while (!_targetSlaves.isEmpty() || !_busySlaves.isEmpty()) {
				synchronized (this) {
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
				sb.append(
					JenkinsResultsParserUtil.toDurationString(
						getAverageThreadDuration()));
				sb.append("\nBusy slaves:");
				sb.append(_busySlaves.size());
				sb.append("\nMirror slaves:");
				sb.append(_mirrorSlaves.size());
				sb.append("\nTarget slaves:");
				sb.append(_targetSlaves.size());

				String currentString = sb.toString();

				if (Objects.equals(previousString, currentString)) {
					continue;
				}

				sb.append("\nTotal duration: ");

				long currentTime =
					JenkinsResultsParserUtil.getCurrentTimeMillis();

				sb.append(
					JenkinsResultsParserUtil.toDurationString(
						currentTime - start));

				sb.append("\n");

				System.out.println(sb.toString());

				previousString = currentString;

				JenkinsResultsParserUtil.sleep(5000);
			}

			long duration =
				JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"File propagation completed in ",
					JenkinsResultsParserUtil.toDurationString(duration), "."));

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
		if (_filePropagatorTasks.isEmpty() || _targetSlaves.isEmpty()) {
			return;
		}

		List<String> commands = new ArrayList<>();

		for (FilePropagatorTask filePropagatorTask : _filePropagatorTasks) {
			String sourceFileName = filePropagatorTask._sourceFileName;

			System.out.println("Copying from source " + sourceFileName);

			String targetFileName = filePropagatorTask._targetFileName;

			commands.add(_getMkdirCommand(targetFileName));

			if (sourceFileName.startsWith("http")) {
				StringBuilder sb = new StringBuilder();

				sb.append("curl ");

				try {
					if (sourceFileName.startsWith(
							"https://release.liferay.com")) {

						sb.append(" -u ");
						sb.append(
							JenkinsResultsParserUtil.getBuildProperty(
								"jenkins.admin.user.name"));
						sb.append(":");
						sb.append(
							JenkinsResultsParserUtil.getBuildProperty(
								"jenkins.admin.user.password"));
					}
				}
				catch (IOException ioException) {
					throw new RuntimeException(
						"Unable to get jenkins-admin user credentials",
						ioException);
				}

				sb.append("-o ");
				sb.append(targetFileName);
				sb.append(" ");
				sb.append(sourceFileName);

				commands.add(sb.toString());
			}
			else {
				commands.add(
					"rsync -Iqs " + sourceFileName + " " + targetFileName);
			}

			String targetDirName = targetFileName.substring(
				0, targetFileName.lastIndexOf("/"));

			commands.add("ls -al " + targetDirName);
		}

		String targetSlave = _targetSlaves.remove(0);

		try {
			if (_executeBashCommands(commands, targetSlave) != 0) {
				_errorSlaves.add(targetSlave);

				_copyFromSource();
			}
			else {
				_mirrorSlaves.add(targetSlave);
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to copy from source. Executed: " + commands, exception);
		}

		System.out.println("Finished copying from source.");
	}

	private int _executeBashCommands(List<String> commands, String targetSlave)
		throws IOException, TimeoutException {

		StringBuffer sb = new StringBuffer();

		sb.append("ssh -o ConnectTimeout=");
		sb.append(_timeout / (60 * 1000));
		sb.append(" -o NumberOfPasswordPrompts=0 ");
		sb.append(targetSlave);
		sb.append(" '");

		if ((_cleanUpCommand != null) && !_cleanUpCommand.isEmpty()) {
			sb.append(_cleanUpCommand);
			sb.append("; ");
		}

		for (int i = 0; i < commands.size(); i++) {
			sb.append(commands.get(i));

			if (i < (commands.size() - 1)) {
				sb.append(" && ");
			}
		}

		sb.append("'");

		Process process = JenkinsResultsParserUtil.executeBashCommands(
			_timeout, sb.toString());

		return process.exitValue();
	}

	private String _getMkdirCommand(String fileName) {
		String dirName = fileName.substring(0, fileName.lastIndexOf("/") + 1);

		return "mkdir -p " + dirName;
	}

	private static final long _TIMEOUT_DEFAULT = 15 * 60 * 1000;

	private final List<String> _busySlaves = new ArrayList<>();
	private String _cleanUpCommand;
	private final List<String> _errorSlaves = new ArrayList<>();
	private final List<FilePropagatorTask> _filePropagatorTasks =
		new ArrayList<>();
	private final List<String> _mirrorSlaves = new ArrayList<>();
	private final List<String> _targetSlaves = new ArrayList<>();
	private int _threadsCompletedCount;
	private long _threadsDurationTotal;
	private final long _timeout;

	private static class FilePropagatorTask {

		private FilePropagatorTask(
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

	private static class FilePropagatorThread implements Runnable {

		@Override
		public void run() {
			long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

			List<FilePropagatorTask> filePropagatorTasks =
				_filePropagator._filePropagatorTasks;

			List<String> commands = new ArrayList<>(filePropagatorTasks.size());

			for (FilePropagatorTask filePropagatorTask : filePropagatorTasks) {
				commands.add(
					_filePropagator._getMkdirCommand(
						filePropagatorTask._targetFileName));

				commands.add(
					"rsync -Iqs " + _mirrorSlave + ":" +
						filePropagatorTask._targetFileName + " " +
							filePropagatorTask._targetFileName);
			}

			try {
				int value = _filePropagator._executeBashCommands(
					commands, _targetSlave);

				_successful = value == 0;
			}
			catch (Exception exception) {
				_successful = false;
			}

			_duration = JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

			synchronized (_filePropagator) {
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