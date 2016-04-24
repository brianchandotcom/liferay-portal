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

	private int _executeCommandsStub(List<String> commands, String targetSlave) throws IOException {
		StringBuffer sb = new StringBuffer("ssh ");
		sb.append(targetSlave);
		sb.append(" '");

		for (String command : commands) {
			sb.append(command);
			if (commands.indexOf(command) < (commands.size() - 1)) {
				sb.append("; ");
			}
		}
		sb.append("'\n");
		
		System.out.println("Executing commands:\n" + sb.toString());
		
		JenkinsResultsParserUtil.sleep(3000);

		return 0;
	}

	private File _writeShellFile(List<String> commands, String targetSlave) throws IOException {
		String fileName = Integer.toString(commands.hashCode());

		File shellFile = new File(fileName + ".sh");

		StringBuffer sb = new StringBuffer("ssh ");

		sb.append(targetSlave);
		sb.append(" '");

		for (String command : commands) {
			sb.append(command);
			if (commands.indexOf(command) < (commands.size() - 1)) {
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
		catch(IOException ioe) {
			if (shellFile.exists()) {
				shellFile.delete();
			}

			throw ioe;
		}
	}
	
	private int _executeCommands(List<String> commands, String targetSlave) throws IOException {
		File shellFile = _writeShellFile(commands, targetSlave);

		try {
			FileSystem fileSystem = FileSystems.getDefault();

			System.out.println("Executing commands:\n" +
				new String(Files.readAllBytes(fileSystem.getPath(
					shellFile.getAbsolutePath()))));

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
			if (shellFile.exists()) {
				shellFile.delete();
			}
		}
	}
	
	private String _generateMkdirCommand(String filePath) {
		String directoryPath = filePath.substring(0, (filePath.lastIndexOf("/") + 1));
		return "mkdir -pv " + directoryPath;
	}
	
	public FilePropagator(String[] fileNames, String originPath,
		String slavePath, List<String> targetSlaves) {

		_filePropagatorTasks = new ArrayList<>(fileNames.length);
		
		for(String fileName : fileNames) {
			_filePropagatorTasks.add(new FilePropagatorTask(
				slavePath + "/" + fileName, originPath + "/" + fileName));
		}
		_targetSlaves = targetSlaves;
		
		_copyFromOrigin();
	}
	
	public FilePropagator(FilePropagatorTask[] filePropagatorTasks,
		List<String> targetSlaves) {
		
		_filePropagatorTasks = new ArrayList<>(filePropagatorTasks.length);

		for (FilePropagatorTask filePropagatorTask : filePropagatorTasks) {
			_filePropagatorTasks.add(filePropagatorTask);
		}

		_targetSlaves = targetSlaves;
		
		_copyFromOrigin();
	}
	
	private void _copyFromOrigin() {

		List<String> commands = new ArrayList<>();

		String targetSlave = null;

		for (FilePropagatorTask filePropagatorTask : _filePropagatorTasks) {
			
			System.out.println("Copying from origin: " + filePropagatorTask.originPath);
			
			targetSlave = _targetSlaves.get(0);
			
			commands.add(_generateMkdirCommand(filePropagatorTask.filePath));

			commands.add("rsync -vI " + filePropagatorTask.originPath + " " + filePropagatorTask.filePath);
		}

		try {
			_executeCommands(commands, targetSlave);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (targetSlave != null) {
			_targetSlaves.remove(targetSlave);

			_sourceSlaves.add(targetSlave);
		}

		System.out.println("Copy from origin complete.");
		
		
	}
	
	public long getAverageDuration() {
		if (_completedCount == 0) {
			return 0;
		}

		return _totalTaskDuration / _completedCount;
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

	public void onTaskCompletion(FilePropagatorThread filePropagatorThread) {
		
		//System.out.println("onTaskCompletion called.");
		
		synchronized(this) {
			_completedCount++;
			_totalTaskDuration += filePropagatorThread.getDuration();

			_busySlaves.remove(filePropagatorThread.getSource());
			_busySlaves.remove(filePropagatorThread.getTarget());
		
			_sourceSlaves.add(filePropagatorThread.getSource());
	
			if (filePropagatorThread.isSuccessful()) {
				_sourceSlaves.add(filePropagatorThread.getTarget());
			}
			else {
				_errorSlaves.add(filePropagatorThread.getTarget());
			}
		}
	}

	public void start(int threadCount) {
		ExecutorService executorService = Executors.newFixedThreadPool(
			threadCount);
		System.out.println("Replication starting with " + threadCount + " threads.");
		
		try {
			long start = System.currentTimeMillis();
			while ((_sourceSlaves.size() + _errorSlaves.size()) < _slaveCount) {
				List<String> usedSourceSlaves = new ArrayList<>(_slaveCount);
				synchronized(this) {
					for (String sourceSlave : _sourceSlaves) {
						if (_targetSlaves.isEmpty()) {
							break;
						}

						String targetSlave = _targetSlaves.get(0);
	
						usedSourceSlaves.add(sourceSlave);
						_targetSlaves.remove(0);
						
						executorService.execute(
							new FilePropagatorThread(this, sourceSlave,targetSlave));

						_busySlaves.add(sourceSlave);
						_busySlaves.add(targetSlave);
					}

					_sourceSlaves.removeAll(usedSourceSlaves);

					usedSourceSlaves.clear();
				}
				
				StringBuffer sb = new StringBuffer();

				sb.append("Busy slaves:");
				sb.append(Integer.toString(_busySlaves.size()));
				sb.append("\nSource slaves:");
				sb.append(Integer.toString(_sourceSlaves.size()));
				sb.append("\nTarget slaves:");
				sb.append(Integer.toString(_targetSlaves.size()));
				sb.append("\nTotal Time: ");
				sb.append(Long.toString((System.currentTimeMillis() - start)));
				sb.append("\nAverage Duration: ");
				sb.append(Long.toString(getAverageDuration()));
				sb.append("ms\n");

				System.out.println(sb.toString());

				JenkinsResultsParserUtil.sleep(5000);
			}
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("Replication complete.\nTotal Time: ");
			sb.append(Long.toString(System.currentTimeMillis() - start));
			sb.append("ms\n");
			sb.append("Average Duration: ");
			sb.append(Long.toString(getAverageDuration()));
			sb.append("ms\n");
			sb.append(Integer.toString(_errorSlaves.size()));
			sb.append(" failures occurred.\n");
			sb.append("Slaves that failed to respond:\n");
			sb.append(_errorSlaves.toString());
			
			System.out.println(sb.toString());

		}
		finally {
			executorService.shutdown();
		}
	}
	
	public static void main(String[] args) throws Exception {

		String bundleFileName = "liferay-portal-tomcat-7.0-nightly-20160323015850923.zip";
		String destinationRootPath = "/root/.liferay/mirrors/release-1/1/";
		String originRootPath ="/mnt/mfs-ssd1-10.0.10/live/opt/java/jenkins/";
		String sqlFileName = "liferay-portal-sql-7.0-nightly-20160323015850923.zip";

		String destinationPath = destinationRootPath + "userContent/liferay-release-tool/7.0.x/20160323015850923/portal/";
		String originPath = originRootPath +"userContent/liferay-release-tool/7.0.x/20160323015850923/portal/";
		
		String slaveBundleFilePath = destinationPath + bundleFileName;
		String slaveSqlFilePath = destinationPath + sqlFileName;
		String originBundleFilePath = originPath + bundleFileName;
		String originSqlFilePath = originPath + sqlFileName;

		FilePropagatorTask[] filePropagatorTasks = new FilePropagatorTask[] {
			new FilePropagatorTask(slaveBundleFilePath, originBundleFilePath),
			new FilePropagatorTask(slaveSqlFilePath, originSqlFilePath)
		};
		List<String> slaveList = JenkinsResultsParserUtil.getSlaveList("test-1-1");
		
		System.out.println("slave list size: " + slaveList.size());

		/*
		FilePropagator filePropagator = new FilePropagator(
			filePropagatorTasks, 
			JenkinsResultsParserUtil.getSlaveList("test-1-1"));

		filePropagator.start(20);
		*/
	}
	
	public static class FilePropagatorTask {
		public FilePropagatorTask(String filePath, String originPath) {
			this.filePath = filePath;
			this.originPath = originPath;
		}
		
		public String filePath;
		public String originPath;
	}

	private List<String> _busySlaves = new ArrayList<>();
	private List<String> _errorSlaves = new ArrayList<>();
	private List<String> _sourceSlaves = new ArrayList<>();
	private List<String> _targetSlaves = new ArrayList<>();

	private int _completedCount = 0;

	private List<FilePropagatorTask> _filePropagatorTasks = new ArrayList<>();
	private long _totalTaskDuration = 0;

	private static class FilePropagatorThread implements Runnable {
		
		private FilePropagatorThread(FilePropagator filePropagator, String sourceSlave, String targetSlave) {
			_filePropagator = filePropagator;
			_sourceSlave = sourceSlave;
			_targetSlave = targetSlave;
		}

		@Override
		public void run() {

			long start = System.currentTimeMillis();
			
			List<String> commands =
				new ArrayList<>(_filePropagator._filePropagatorTasks.size());

			for (FilePropagatorTask filePropagatorTask : _filePropagator._filePropagatorTasks) {
				commands.add(_filePropagator._generateMkdirCommand(filePropagatorTask.filePath));
				
				commands.add("rsync -vI " +	_sourceSlave + ":" + filePropagatorTask.filePath + " " + filePropagatorTask.filePath);
			}
			try {
				_isSuccessful = _filePropagator._executeCommands(commands, _targetSlave) == 0;
			}
			catch (Exception e) {
				_isSuccessful = false;
			}
			_duration = System.currentTimeMillis() - start;

			_filePropagator.onTaskCompletion(this);
		}
		
		public Boolean isSuccessful() {
			return _isSuccessful;
		}
		
		public long getDuration() {
			return _duration;
		}
		
		public String getSource() {
			return _sourceSlave;
		}
		
		public String getTarget() {
			return _targetSlave;
		}
		
		private long _duration = -1;
		private FilePropagator _filePropagator;
		private String _sourceSlave;
		private String _targetSlave;
		private Boolean _isSuccessful;
	}
}
