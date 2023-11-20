/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * @author Peter Yoo
 */
public class ParallelExecutor<T> {

	public ParallelExecutor(
		Collection<Callable<T>> callables, boolean excludeNulls,
		ExecutorService executorService, boolean failOnError,
		String description) {

		synchronized (_nextId) {
			_id = _nextId++;
		}

		_callables = callables;
		_excludeNulls = excludeNulls;
		_executorService = executorService;
		_failOnError = failOnError;
		_description = description;

		if (executorService == null) {
			_disposeExecutor = true;
			_executorService = Executors.newSingleThreadExecutor();
		}
		else {
			_disposeExecutor = false;
		}
	}

	public ParallelExecutor(
		Collection<Callable<T>> callables, boolean excludeNulls,
		ExecutorService executorService, String purpose) {

		this(callables, excludeNulls, executorService, false, purpose);
	}

	public ParallelExecutor(
		Collection<Callable<T>> callables, ExecutorService executorService,
		String purpose) {

		this(callables, false, executorService, purpose);
	}

	public List<T> execute() throws TimeoutException {
		return execute(null);
	}

	public List<T> execute(Long timeoutSeconds) throws TimeoutException {
		start();

		return waitFor(timeoutSeconds);
	}

	public String getDescription() {
		return _description;
	}

	public String getID() {
		return String.valueOf(_id);
	}

	public void shutdownNow() {
		_executorService.shutdownNow();
	}

	public synchronized void start() {
		_taskRunnable = new TaskRunnable<>(_callables, this);

		_thread = new Thread(_taskRunnable);

		_thread.start();
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.combine(
			"ParallelExecutor ", String.valueOf(getID()), " - ",
			getDescription());
	}

	public List<T> waitFor() throws TimeoutException {
		return waitFor(null);
	}

	public List<T> waitFor(Long timeoutSeconds) throws TimeoutException {
		if (timeoutSeconds == null) {
			timeoutSeconds = 1000 * 60 * 60 * 2L;
		}

		if ((_taskRunnable == null) || (_thread == null)) {
			return null;
		}

		try {
			while (_thread.isAlive()) {
				if (_taskRunnable.getDurationMillis() >
						(1000 * timeoutSeconds)) {

					_thread.interrupt();

					String durationString =
						JenkinsResultsParserUtil.toDurationString(
							_taskRunnable.getDurationMillis());

					throw new TimeoutException(
						JenkinsResultsParserUtil.combine(
							toString(), " timed out after ", durationString));
				}

				JenkinsResultsParserUtil.sleep(100);
			}

			return _taskRunnable.getResults();
		}
		finally {
			if (_disposeExecutor) {
				_executorService.shutdownNow();

				while (!_executorService.isShutdown()) {
					JenkinsResultsParserUtil.sleep(100);
				}

				_executorService = null;
			}
		}
	}

	public abstract static class SequentialCallable<T> implements Callable<T> {

		public SequentialCallable() {
			this(_PARALLEL_QUEUE_NAME);
		}

		public SequentialCallable(String queueName) {
			_queueName = queueName;
		}

		public abstract T call() throws Exception;

		public String getQueueName() {
			return _queueName;
		}

		private String _queueName;

	}

	private static final String _PARALLEL_QUEUE_NAME =
		"PARALLEL_EXECUTOR:PARALLEL_QUEUE";

	private static Integer _nextId = 1;

	private final Collection<Callable<T>> _callables;
	private String _description;
	private final boolean _disposeExecutor;
	private boolean _excludeNulls;
	private ExecutorService _executorService;
	private boolean _failOnError;
	private int _id;
	private TaskRunnable<T> _taskRunnable;
	private Thread _thread;

	private static class TaskRunnable<T> implements Runnable {

		public TaskRunnable(
			Collection<Callable<T>> callables,
			ParallelExecutor<T> parallelExecutor) {

			if (parallelExecutor == null) {
				throw new IllegalArgumentException(
					"parallelExecutor is required");
			}

			_totalTaskCount = callables.size();

			_parallelExecutor = parallelExecutor;

			_callablesMap = _toCallablesMap(callables);

			_executorService = _parallelExecutor._executorService;
		}

		public String generateStatusMessage() {
			StringBuilder sb = new StringBuilder();

			sb.append(_parallelExecutor.toString());

			if ((getRemainingTaskCount() + getRunningTaskCount()) == 0) {
				sb.append(" completed in ");
			}
			else {
				sb.append(" has been running for ");
			}

			sb.append(
				JenkinsResultsParserUtil.toDurationString(getDurationMillis()));
			sb.append("\n Completed: ");
			sb.append(getCompletedTaskCount());
			sb.append(" | Running: ");
			sb.append(getRunningTaskCount());
			sb.append(" | Remaining: ");
			sb.append(getRemainingTaskCount());
			sb.append(" | Total: ");
			sb.append(getTotalTaskCount());
			sb.append("\n Average task duration: ");
			sb.append(
				JenkinsResultsParserUtil.toDurationString(
					getAverageDurationMillis()));

			return sb.toString();
		}

		public long getAverageDurationMillis() {
			if (_completedTasks.isEmpty()) {
				return 0L;
			}

			long totalDuration = 0;

			for (Task<T> completedTask : _completedTasks) {
				TaskCallable<T> taskCallable = completedTask.getCallable();

				totalDuration += taskCallable.getDuration();
			}

			return totalDuration / _completedTasks.size();
		}

		public int getCompletedTaskCount() {
			return _completedTasks.size();
		}

		public long getDurationMillis() {
			if (_startTimeMillis == null) {
				return 0L;
			}

			return System.currentTimeMillis() - _startTimeMillis;
		}

		public int getRemainingTaskCount() {
			return getTotalTaskCount() - getRunningTaskCount() -
				getCompletedTaskCount();
		}

		public List<T> getResults() {
			if (!isComplete()) {
				return null;
			}

			List<T> results = new ArrayList<>(_completedTasks.size());

			for (Task<T> completedTask : _completedTasks) {
				Future<T> future = completedTask.getFuture();

				try {
					T result = future.get();

					if ((result == null) && _parallelExecutor._excludeNulls) {
						continue;
					}

					results.add(future.get());
				}
				catch (ExecutionException | InterruptedException exception) {
					throw new RuntimeException(exception);
				}
			}

			return results;
		}

		public int getRunningTaskCount() {
			int runningTaskCount = 0;

			for (Task<T> runningTask : _runningTasks) {
				TaskCallable<T> taskCallable = runningTask.getCallable();

				if (taskCallable.isRunning()) {
					runningTaskCount++;
				}
			}

			return runningTaskCount;
		}

		public int getTotalTaskCount() {
			return _totalTaskCount;
		}

		public boolean isComplete() {
			if (getCompletedTaskCount() == getTotalTaskCount()) {
				return true;
			}

			return false;
		}

		@Override
		public void run() {
			if (_callablesMap.isEmpty()) {
				return;
			}

			Set<Map.Entry<String, Collection<Callable<T>>>> entries =
				_callablesMap.entrySet();

			_startTimeMillis = System.currentTimeMillis();

			long lastOutputTimeMillis = _startTimeMillis;

			for (Map.Entry<String, Collection<Callable<T>>> entry : entries) {
				if (Objects.equals(entry.getKey(), _PARALLEL_QUEUE_NAME)) {
					continue;
				}

				Collection<Callable<T>> callables = entry.getValue();

				Iterator<Callable<T>> iterator = callables.iterator();

				_runningTasks.add(_processCallable(iterator.next(), iterator));
			}

			Collection<Callable<T>> callables = _callablesMap.get(
				_PARALLEL_QUEUE_NAME);

			if ((callables != null) && !callables.isEmpty()) {
				for (Callable<T> callable : callables) {
					_runningTasks.add(_processCallable(callable, null));
				}
			}

			try {
				while (!_runningTasks.isEmpty()) {
					List<Task<T>> newProcessorTasks = new ArrayList<>();
					List<Task<T>> latestCompletedProcessorTasks =
						new ArrayList<>();

					for (Task<T> processorTask : _runningTasks) {
						if (Thread.interrupted()) {
							throw new RuntimeException(
								_parallelExecutor + " has been aborted");
						}

						Future<T> future = processorTask.getFuture();

						if (future.isDone()) {
							try {
								_results.add(future.get());

								latestCompletedProcessorTasks.add(
									processorTask);
							}
							catch (ExecutionException | InterruptedException
										exception) {

								if (_parallelExecutor._failOnError) {
									throw new RuntimeException(exception);
								}
							}

							Iterator<Callable<T>> iterator =
								processorTask.getIterator();

							if ((iterator == null) || !iterator.hasNext()) {
								continue;
							}

							newProcessorTasks.add(
								_processCallable(iterator.next(), iterator));
						}
					}

					_runningTasks.removeAll(latestCompletedProcessorTasks);

					_runningTasks.addAll(newProcessorTasks);

					_completedTasks.addAll(latestCompletedProcessorTasks);

					long millisSinceLastOutput =
						System.currentTimeMillis() - lastOutputTimeMillis;

					if (millisSinceLastOutput > (1000 * 60 * 3)) {
						System.out.println(generateStatusMessage());

						lastOutputTimeMillis = System.currentTimeMillis();
					}

					if (!_runningTasks.isEmpty()) {
						JenkinsResultsParserUtil.sleep(100);
					}
				}
			}
			catch (Exception exception) {
				for (Task<T> processorTask : _runningTasks) {
					Future<T> future = processorTask.getFuture();

					if ((future != null) && !future.isCancelled()) {
						if (future.isDone()) {
							_completedTasks.add(processorTask);
						}
						else {
							future.cancel(true);
						}
					}
				}

				throw exception;
			}
			finally {
			}

			System.out.println(
				JenkinsResultsParserUtil.combine(
					_parallelExecutor.toString(), " completed ",
					String.valueOf(getCompletedTaskCount()), " tasks in ",
					JenkinsResultsParserUtil.toDurationString(
						System.currentTimeMillis() - _startTimeMillis)));
		}

		private Task<T> _processCallable(
			Callable<T> callable, Iterator<Callable<T>> iterator) {

			TaskCallable<T> taskCallable = new TaskCallable<>(callable);

			Future<T> future = _executorService.submit(taskCallable);

			return new Task<>(iterator, taskCallable, future);
		}

		private Map<String, Collection<Callable<T>>> _toCallablesMap(
			Collection<Callable<T>> callables) {

			Map<String, Collection<Callable<T>>> callablesMap = new HashMap<>();

			for (Callable<T> callable : callables) {
				String queueName = null;

				if (callable instanceof SequentialCallable) {
					SequentialCallable<T> groupedCallable =
						(SequentialCallable<T>)callable;

					queueName = groupedCallable.getQueueName();
				}
				else {
					queueName = _PARALLEL_QUEUE_NAME;
				}

				if (JenkinsResultsParserUtil.isNullOrEmpty(queueName)) {
					queueName = _PARALLEL_QUEUE_NAME;
				}

				if (!callablesMap.containsKey(queueName)) {
					callablesMap.put(queueName, new ArrayList<Callable<T>>());
				}

				Collection<Callable<T>> callablesCollection = callablesMap.get(
					queueName);

				callablesCollection.add(callable);

				callablesMap.put(queueName, callablesCollection);
			}

			return callablesMap;
		}

		private final Map<String, Collection<Callable<T>>> _callablesMap;
		private List<Task<T>> _completedTasks = new ArrayList<>();
		private ExecutorService _executorService;
		private final ParallelExecutor<T> _parallelExecutor;
		private List<T> _results = new ArrayList<>();
		private List<TaskRunnable.Task<T>> _runningTasks = new ArrayList<>();
		private Long _startTimeMillis;
		private final int _totalTaskCount;

		private static class Task<T> {

			public Task(
				Iterator<Callable<T>> iterator,
				TaskCallable<T> processorCallable, Future<T> future) {

				_iterator = iterator;
				_processorCallable = processorCallable;
				_future = future;
			}

			public TaskCallable<T> getCallable() {
				return _processorCallable;
			}

			public Future<T> getFuture() {
				return _future;
			}

			public Iterator<Callable<T>> getIterator() {
				return _iterator;
			}

			private final Future<T> _future;
			private final Iterator<Callable<T>> _iterator;
			private final TaskCallable<T> _processorCallable;

		}

		private static class TaskCallable<T> implements Callable<T> {

			public TaskCallable(Callable<T> callable) {
				_callable = callable;

				_startTimeMillis = null;
			}

			@Override
			public T call() throws Exception {
				_startTimeMillis = System.currentTimeMillis();

				try {
					return _callable.call();
				}
				finally {
					_durationMillis =
						System.currentTimeMillis() - _startTimeMillis;
				}
			}

			public Long getDuration() {
				if (isRunning()) {
					return System.currentTimeMillis() - _startTimeMillis;
				}

				if (isDone()) {
					return _durationMillis;
				}

				return null;
			}

			public boolean isDone() {
				if (_durationMillis == null) {
					return false;
				}

				return true;
			}

			public boolean isRunning() {
				if ((_startTimeMillis != null) && !isDone()) {
					return true;
				}

				return false;
			}

			private final Callable<T> _callable;
			private Long _durationMillis;
			private Long _startTimeMillis;

		}

	}

}