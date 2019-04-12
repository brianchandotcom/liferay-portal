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

import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.ClassTestRule;
import com.liferay.portal.kernel.test.rule.CompanyProviderClassTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRunMethodTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.TimeoutTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;

import org.junit.rules.TestRule;
import org.junit.runner.Description;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Shuyang Zhou
 */
public class LiferayIntegrationTestRule extends AggregateTestRule {

	public LiferayIntegrationTestRule() {
		super(false, _getTestRules());
	}

	private static TestRule[] _getTestRules() {
		List<TestRule> testRules = new ArrayList<>();

		if (System.getenv("JENKINS_HOME") != null) {
			testRules.add(TimeoutTestRule.INSTANCE);
		}

		testRules.add(LogAssertionTestRule.INSTANCE);
		testRules.add(_springInitializationClassTestRule);
		testRules.add(SybaseDumpTransactionLogTestRule.INSTANCE);
		testRules.add(ClearThreadLocalClassTestRule.INSTANCE);
		testRules.add(UniqueStringRandomizerBumperClassTestRule.INSTANCE);
		testRules.add(MainServletClassTestRule.INSTANCE);
		testRules.add(DestinationAwaitClassTestRule.INSTANCE);
		testRules.add(CompanyProviderClassTestRule.INSTANCE);
		testRules.add(DeleteAfterTestRunMethodTestRule.INSTANCE);
		testRules.add(SynchronousDestinationTestRule.INSTANCE);
		testRules.add(InjectTestRule.INSTANCE);

		return testRules.toArray(new TestRule[testRules.size()]);
	}

	private static final TestRule _springInitializationClassTestRule =
		new ClassTestRule<Void>() {

			@Override
			protected void afterClass(Description description, Void v) {
			}

			@Override
			protected Void beforeClass(Description description) {
				if (!InitUtil.isInitialized()) {
					List<String> configLocations = ListUtil.fromArray(
						PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

					boolean configureLog4j = false;

					if (GetterUtil.getBoolean(
							SystemProperties.get("log4j.configure.on.startup"),
							true)) {

						SystemProperties.set(
							"log4j.configure.on.startup", "false");

						configureLog4j = true;
					}

					Log4JUtil.setLevel(
						DialectDetector.class.getName(), Level.INFO.toString(),
						false);

					ClassPathUtil.initializeClassPaths(
						new MockServletContext());
					PortalClassPathUtil.initializeClassPaths(
						new MockServletContext());

					InitUtil.initWithSpring(configLocations, true, true);

					if (configureLog4j) {
						Log4JUtil.configureLog4J(
							InitUtil.class.getClassLoader());

						LogAssertionTestRule.startAssert(
							Collections.<ExpectedLogs>emptyList());
					}

					if (System.getProperty("external-properties") == null) {
						System.setProperty(
							"external-properties", "portal-test.properties");
					}
				}

				return null;
			}

		};

}