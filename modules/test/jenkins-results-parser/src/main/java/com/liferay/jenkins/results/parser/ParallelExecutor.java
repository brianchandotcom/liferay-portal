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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Peter Yoo
 */
public class ParallelExecutor<T> {

	public ParallelExecutor(
		Collection<Callable<T>> callables, ExecutorService executorService) {

		_callables = callables;
		_executorService = executorService;

		if (_executorService == null) {
			_disposeExecutor = true;
			_executorService = Executors.newSingleThreadExecutor();
		}
		else {
			_disposeExecutor = false;
		}
	}

	public List<T> execute() {
		try {
			ArrayList<Future<T>> futures = new ArrayList<>(_callables.size());

			for (Callable<T> callable : _callables) {
				futures.add(_executorService.submit(callable));
			}

			List<T> results = new ArrayList<>(_callables.size());

			for (Future<T> future : futures) {
				try {
					results.add(future.get());
				}
				catch (ExecutionException | InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			return results;
		}
		finally {
			if (_disposeExecutor) {
				_executorService.shutdown();

				while (!_executorService.isShutdown()) {
					JenkinsResultsParserUtil.sleep(100);
				}

				_executorService = null;
			}
		}
	}

	private final Collection<Callable<T>> _callables;
	private final boolean _disposeExecutor;
	private ExecutorService _executorService;

}