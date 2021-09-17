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

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class DataGuardTestRule
	extends AbstractTestRule
		<DataGuardTestRuleUtil.DataBag, DataGuardTestRuleUtil.DataBag> {

	public static final DataGuardTestRule INSTANCE = new DataGuardTestRule();

	@Override
	protected void afterClass(
			Description description, DataGuardTestRuleUtil.DataBag dataBag)
		throws Throwable {

		if (dataBag == null) {
			return;
		}

		DataGuardTestRuleUtil.afterClass(
			dataBag, description.getClassName(), _autoDelete(description));
	}

	@Override
	protected void afterMethod(
			Description description, DataGuardTestRuleUtil.DataBag dataBag,
			Object target)
		throws Throwable {

		if (dataBag == null) {
			return;
		}

		DataGuardTestRuleUtil.afterMethod(
			dataBag, description.getClassName(), _autoDelete(description));
	}

	@Override
	protected DataGuardTestRuleUtil.DataBag beforeClass(
		Description description) {

		DataGuard dataGuard = description.getAnnotation(DataGuard.class);

		if ((dataGuard != null) &&
			(dataGuard.scope() == DataGuard.Scope.NONE)) {

			return null;
		}

		return DataGuardTestRuleUtil.beforeClass();
	}

	@Override
	protected DataGuardTestRuleUtil.DataBag beforeMethod(
		Description description, Object target) {

		Class<?> testClass = description.getTestClass();

		DataGuard dataGuard = testClass.getAnnotation(DataGuard.class);

		if ((dataGuard == null) ||
			(dataGuard.scope() != DataGuard.Scope.METHOD)) {

			return null;
		}

		return DataGuardTestRuleUtil.beforeMethod();
	}

	private DataGuardTestRule() {
	}

	private boolean _autoDelete(Description description) {
		Class<?> testClass = description.getTestClass();

		DataGuard dataGuard = testClass.getAnnotation(DataGuard.class);

		if (dataGuard == null) {
			return true;
		}

		return dataGuard.autoDelete();
	}

}