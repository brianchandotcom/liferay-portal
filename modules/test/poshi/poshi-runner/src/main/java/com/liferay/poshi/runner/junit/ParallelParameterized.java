/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.junit;

import com.liferay.poshi.core.PoshiProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

/**
 * @author Kenji Heigel
 */
public class ParallelParameterized extends Parameterized {

	public ParallelParameterized(Class<?> clazz) throws Throwable {
		super(clazz);

		setScheduler(new ThreadPoolScheduler());
	}

	private static class ThreadPoolScheduler implements RunnerScheduler {

		public ThreadPoolScheduler() {
			PoshiProperties poshiProperties =
				PoshiProperties.getPoshiProperties();

			_executorService = Executors.newFixedThreadPool(
				poshiProperties.testRunThreadPoolSize);
		}

		@Override
		public void finished() {
			_executorService.shutdown();

			try {
				_executorService.awaitTermination(180, TimeUnit.MINUTES);
			}
			catch (InterruptedException interruptedException) {
				throw new RuntimeException(interruptedException);
			}
		}

		@Override
		public void schedule(Runnable childStatement) {
			_executorService.submit(childStatement);
		}

		private final ExecutorService _executorService;

	}

}