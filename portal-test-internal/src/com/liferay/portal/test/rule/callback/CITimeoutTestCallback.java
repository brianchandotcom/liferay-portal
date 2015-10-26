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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;

import org.junit.Assert;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

/**
 * @author Shuyang Zhou
 */
public class CITimeoutTestCallback extends BaseTestCallback<Long, Object> {

	public static final CITimeoutTestCallback INSTANCE =
		new CITimeoutTestCallback();

	@Override
	public void doAfterClass(Description description, Long startTime) {
		long testTime = System.currentTimeMillis() - startTime;

		if (testTime <= TestPropsValues.CI_TEST_TIMEOUT_TIME) {
			return;
		}

		String message =
			description.getClassName() + " spent " + testTime +
				"ms and surpassed the timeout threshold " +
					TestPropsValues.CI_TEST_TIMEOUT_TIME + "ms.";

		System.setProperty(_CI_TIMEOUT_TEST_CLASS_MESSAGE, message);

		Assert.fail(
			message + " Marked it as failed and aborting subsequent tests.");
	}

	@Override
	public Long doBeforeClass(Description description) {
		String message = System.getProperty(_CI_TIMEOUT_TEST_CLASS_MESSAGE);

		if (message != null) {
			Assert.fail(
				"Abort running " + description.getClassName() + " due to : " +
					message);
		}

		if (!_isArquillianTest(description)) {
			return System.currentTimeMillis();
		}

		String startTimeKey =
			_CI_TIMEOUT_ARQUILLIAN_TEST_START_TIME + description.getClassName();

		String startTimeValue = System.getProperty(startTimeKey);

		if (startTimeValue == null) {
			long startTime = System.currentTimeMillis();

			System.setProperty(startTimeKey, String.valueOf(startTime));

			return startTime;
		}

		return GetterUtil.getLongStrict(startTimeValue);
	}

	private CITimeoutTestCallback() {
	}

	private boolean _isArquillianTest(Description description) {
		RunWith runWith = description.getAnnotation(RunWith.class);

		if (runWith == null) {
			return false;
		}

		Class<? extends Runner> runnerClass = runWith.value();

		String runnerClassName = runnerClass.getName();

		if (runnerClassName.equals(
				"com.liferay.arquillian.extension.junit.bridge.junit." +
					"Arquillian")) {

			return true;
		}

		return false;
	}

	private static final String _CI_TIMEOUT_ARQUILLIAN_TEST_START_TIME =
		"CI_TIMEOUT_ARQUILLIAN_TEST_START_TIME_";

	private static final String _CI_TIMEOUT_TEST_CLASS_MESSAGE =
		"CI_TIMEOUT_TEST_CLASS_MESSAGE";

}