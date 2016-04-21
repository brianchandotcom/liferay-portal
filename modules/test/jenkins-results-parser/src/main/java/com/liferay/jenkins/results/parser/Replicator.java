package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Replicator {
	
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
		try {
			Thread.sleep(3000);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException (ie);
		}

		return 0;
	}
	
	private int _executeCommands(List<String> commands, String targetSlave) throws IOException {
		String commandHash = Integer.toString(commands.hashCode());
		File shellFile = new File(commandHash + ".sh");
		try {
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

			try (FileWriter shellFileWriter = new FileWriter(shellFile)) {
				shellFileWriter.write(sb.toString());
			}

			shellFile.setExecutable(true);

			Integer exitCode = null;
			Process process = null;
			long start = System.currentTimeMillis();
			String output = null;

			while (exitCode == null) {
				try {
					process = Runtime.getRuntime().exec("./" + shellFile.getName());
				}
				catch (IOException ioe) {
					if (ioe.getMessage().contains("Text file busy") &&
						(System.currentTimeMillis() - start) < 2000) {

						try {
							Thread.sleep(100);
						}
						catch (InterruptedException ie) {
							throw new RuntimeException(ie);
						}

						continue;
					}
					throw ioe;
				}

				output = _getOutput(process.getErrorStream());

				if (output.contains("No buffer space available") ||
					output.contains("Temporary failure in name resolution")) {

					System.out.println("Out of buffer space OR Temporary DNS failure - Retry in 1 minute.");

					try {
						Thread.sleep(1000 * 60);
					}
					catch (InterruptedException ie) {
						throw new RuntimeException(ie);
					}

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

			System.out.println(output);

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

	public Replicator(ReplicatorTask[] replicatorTasks, List<String> targetSlaves) {
		
		_replicatorTasks = new ArrayList<>(replicatorTasks.length);

		for (ReplicatorTask replicatorTask : replicatorTasks) {
			_replicatorTasks.add(replicatorTask);
		}

		_targetSlaves = targetSlaves;
		
		_slaveCount = _targetSlaves.size();

		_busySlaves = new ArrayList<>(_slaveCount);
		_errorSlaves = new ArrayList<>(_slaveCount);
		_sourceSlaves = new ArrayList<>(_slaveCount);

		List<String> commands = new ArrayList<>();

		String targetSlave = null;

		for (ReplicatorTask replicatorTask : _replicatorTasks) {
			
			System.out.println("Copying from origin: " + replicatorTask.originPath);
			
			targetSlave = _targetSlaves.get(0);
			
			commands.add(_generateMkdirCommand(replicatorTask.filePath));

			commands.add("rsync -vI " + replicatorTask.originPath + " " + replicatorTask.filePath);
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
	
	private static String _getOutput(InputStream inputStream) {
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

	public void onTaskCompletion(ReplicatorThread replicatorThread) {
		
		//System.out.println("onTaskCompletion called.");
		
		synchronized(this) {
			_completedCount++;
			_totalTaskDuration += replicatorThread.getDuration();

			_busySlaves.remove(replicatorThread.getSource());
			_busySlaves.remove(replicatorThread.getTarget());
		
			_sourceSlaves.add(replicatorThread.getSource());
	
			if (replicatorThread.isSuccessful()) {
				_sourceSlaves.add(replicatorThread.getTarget());
			}
			else {
				_errorSlaves.add(replicatorThread.getTarget());
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
							new ReplicatorThread(this, sourceSlave,targetSlave));

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

				try {
					Thread.sleep(5000);
				}
				catch (InterruptedException ie) {
					throw new RuntimeException(ie);
				}
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
	
	public static void main(String[] args) {

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

		ReplicatorTask[] replicatorTasks = new ReplicatorTask[] {
			new ReplicatorTask(slaveBundleFilePath, originBundleFilePath),
			new ReplicatorTask(slaveSqlFilePath, originSqlFilePath)
		};
		
		Replicator replicator = new Replicator(
			replicatorTasks, 
			SlaveListGeneratorUtil.getSlaveList(
				"test-1-1", "file:/Users/pyoo/dev/jenkins-ee/build.properties",
				false));

		replicator.start(1);
	}
	
	public static class ReplicatorTask {
		public ReplicatorTask(String filePath, String originPath) {
			this.filePath = filePath;
			this.originPath = originPath;
		}
		
		public String filePath;
		public String originPath;
	}

	private List<String> _busySlaves;
	private List<String> _errorSlaves;
	private List<String> _sourceSlaves;
	private List<String> _targetSlaves;

	private int _completedCount = 0;
	private final Integer _slaveCount;

	private List<ReplicatorTask> _replicatorTasks = new ArrayList<>();
	private long _totalTaskDuration = 0;

	private static class ReplicatorThread implements Runnable {
		
		private ReplicatorThread(Replicator replicator, String sourceSlave, String targetSlave) {
			_replicator = replicator;
			_sourceSlave = sourceSlave;
			_targetSlave = targetSlave;
		}

		@Override
		public void run() {

			long start = System.currentTimeMillis();
			
			List<String> commands =
				new ArrayList<>(_replicator._replicatorTasks.size());

			for (ReplicatorTask replicatorTask : _replicator._replicatorTasks) {
				commands.add(_replicator._generateMkdirCommand(replicatorTask.filePath));
				
				commands.add("rsync -vI " +	_sourceSlave + ":" + replicatorTask.filePath + " " + replicatorTask.filePath);
			}
			try {
				_isSuccessful = _replicator._executeCommands(commands, _targetSlave) == 0;
			}
			catch (Exception e) {
				_isSuccessful = false;
			}
			_duration = System.currentTimeMillis() - start;

			_replicator.onTaskCompletion(this);
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
		private Replicator _replicator;
		private String _sourceSlave;
		private String _targetSlave;
		private Boolean _isSuccessful;
	}
}
