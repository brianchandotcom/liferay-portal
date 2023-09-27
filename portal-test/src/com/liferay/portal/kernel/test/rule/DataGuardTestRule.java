/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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