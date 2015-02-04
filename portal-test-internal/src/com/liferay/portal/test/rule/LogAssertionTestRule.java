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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.rule.executor.LogAssertionExecutor;
import com.liferay.portal.test.rule.executor.LogAssertionExecutorImpl;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class LogAssertionTestRule
	extends BaseTestRule<CaptureAppender, CaptureAppender> {

	public static final LogAssertionTestRule INSTANCE =
		new LogAssertionTestRule();

	@Override
	protected void afterClass(
		Description description, CaptureAppender captureAppender) {

		ExpectedLogs expectedLogs = description.getAnnotation(
			ExpectedLogs.class);

		_logAssertionExecutor.endAssert(expectedLogs, captureAppender);
	}

	@Override
	protected void afterMethod(
		Description description, CaptureAppender captureAppender) {

		afterClass(description, captureAppender);
	}

	@Override
	protected CaptureAppender beforeClass(Description description) {
		ExpectedLogs expectedLogs = description.getAnnotation(
			ExpectedLogs.class);

		return _logAssertionExecutor.startAssert(expectedLogs);
	}

	@Override
	protected CaptureAppender beforeMethod(Description description) {
		return beforeClass(description);
	}

	private LogAssertionTestRule() {
	}

	private static final LogAssertionExecutor _logAssertionExecutor =
		new LogAssertionExecutorImpl();

}