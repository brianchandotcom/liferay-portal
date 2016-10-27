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

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.test.rule.BaseTestRule.StatementWrapper;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class TimeoutTestRule implements TestRule {

	public static final TimeoutTestRule INSTANCE = new TimeoutTestRule(
		TestPropsValues.CI_TEST_TIMEOUT_TIME);

	public static final int TIMEOUT_EXIT_CODE = 200;

	public TimeoutTestRule(long timeout) {
		_timeout = timeout;
	}

	@Override
	public Statement apply(Statement statement, Description description) {
		String methodName = description.getMethodName();

		if ((methodName == null) ||
			(!OSDetector.isLinux() && !OSDetector.isUnix())) {

			return statement;
		}

		return new StatementWrapper(statement) {

			@Override
			public void evaluate() throws Throwable {
				FutureTask<Void> futureTask = new FutureTask<>(

					new Callable<Void>() {

						@Override
						public Void call() throws InterruptedException {
							Thread.sleep(_timeout);

							StringBundler sb = new StringBundler(6);

							sb.append("Thread dump for ");
							sb.append(description.toString());
							sb.append(" timeout after waited ");
							sb.append(_timeout);
							sb.append("ms:");
							sb.append(ThreadUtil.threadDump());

							System.out.println(sb.toString());

							System.exit(TIMEOUT_EXIT_CODE);

							return null;
						}

					});

				Thread timeoutMonitoringThread = new Thread(
					futureTask,
					"Timeout monitoring thread for " + description.toString());

				timeoutMonitoringThread.start();

				try {
					statement.evaluate();
				}
				finally {
					futureTask.cancel(true);

					timeoutMonitoringThread.join();
				}
			}

		};
	}

	private final long _timeout;

}