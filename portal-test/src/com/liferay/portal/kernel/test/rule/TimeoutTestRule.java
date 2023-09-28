/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.rule;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.OSDetector;
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

	public static final TimeoutTestRule INSTANCE = new TimeoutTestRule();

	public static final int TIMEOUT_EXIT_CODE = 200;

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
							Thread.sleep(TestPropsValues.CI_TEST_TIMEOUT_TIME);

							System.out.println(
								StringBundler.concat(
									"Thread dump for ", description,
									" timeout after waited ",
									TestPropsValues.CI_TEST_TIMEOUT_TIME, "ms:",
									ThreadUtil.threadDump()));

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

}