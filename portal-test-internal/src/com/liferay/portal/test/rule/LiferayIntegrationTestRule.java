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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.kernel.test.rule.BaseTestRule.StatementWrapper;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRunTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.callback.ClearThreadLocalTestCallback;
import com.liferay.portal.test.rule.callback.LogAssertionTestCallback;
import com.liferay.portal.test.rule.callback.UniqueStringRandomizerBumperTestCallback;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.log4j.Log4JUtil;

import java.util.Collections;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class LiferayIntegrationTestRule extends AggregateTestRule {

	public LiferayIntegrationTestRule() {
		super(
			false, CITimeoutTestRule.INSTANCE, LogAssertionTestRule.INSTANCE,
			_springInitializationTestRule,
			SybaseDumpTransactionLogTestRule.INSTANCE,
			_clearThreadLocalTestRule, _uniqueStringRandomizerBumperTestRule,
			new DeleteAfterTestRunTestRule());
	}

	private static final TestRule _clearThreadLocalTestRule =
		new BaseTestRule<>(ClearThreadLocalTestCallback.INSTANCE);

	private static final TestRule _springInitializationTestRule =
		new TestRule() {

			@Override
			public Statement apply(
				Statement statement, Description description) {

				return new StatementWrapper(statement) {

					@Override
					public void evaluate() throws Throwable {
						if (!InitUtil.isInitialized()) {
							ServerDetector.init(ServerDetector.TOMCAT_ID);

							List<String> configLocations = ListUtil.fromArray(
								PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

							boolean configureLog4j = false;

							if (GetterUtil.getBoolean(
									SystemProperties.get(
										"log4j.configure.on.startup"),
									true)) {

								SystemProperties.set(
									"log4j.configure.on.startup", "false");

								configureLog4j = true;
							}

							InitUtil.initWithSpring(configLocations, true);

							if (configureLog4j) {
								Log4JUtil.configureLog4J(
									InitUtil.class.getClassLoader());

								LogAssertionTestCallback.startAssert(
									Collections.<ExpectedLogs>emptyList());
							}

							if (System.getProperty("external-properties") ==
									null) {

								System.setProperty(
									"external-properties",
									"portal-test.properties");
							}
						}

						statement.evaluate();
					}

				};
			}

		};

	private static final TestRule _uniqueStringRandomizerBumperTestRule =
		new BaseTestRule<>(UniqueStringRandomizerBumperTestCallback.INSTANCE);

}