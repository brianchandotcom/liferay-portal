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

package com.liferay.arquillian.extension.junit.bridge.remote.context.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.context.ClassContext;
import org.jboss.arquillian.test.spi.context.SuiteContext;
import org.jboss.arquillian.test.spi.context.TestContext;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.ClassEvent;
import org.jboss.arquillian.test.spi.event.suite.SuiteEvent;
import org.jboss.arquillian.test.spi.event.suite.TestEvent;

/**
 * @author Matthew Tambara
 */
public class TestContextHandler {

	public void createClassContext(
		@Observes(precedence = 100) EventContext<ClassEvent>
			classEventContext) {

		ClassContext classContext = _classContextInstance.get();

		ClassEvent classEvent = classEventContext.getEvent();

		TestClass testClass = classEvent.getTestClass();

		Class<?> javaClass = testClass.getJavaClass();

		try {
			classContext.activate(javaClass);

			_testClassInstanceProducer.set(testClass);

			classEventContext.proceed();
		}
		finally {
			classContext.deactivate();

			if (classEvent instanceof AfterClass) {
				Set<Object> instances = _activatedTestContexts.get(javaClass);

				if (instances != null) {
					TestContext testContext = _testContextInstance.get();

					for (Object instance : instances) {
						testContext.destroy(instance);
					}

					_activatedTestContexts.remove(javaClass);
				}

				classContext.destroy(javaClass);
			}
		}
	}

	public void createSuiteContext(
		@Observes(precedence = 100) EventContext<SuiteEvent>
			suiteEventContext) {

		SuiteContext suiteContext = _suiteContextInstance.get();

		SuiteEvent suiteEvent = suiteEventContext.getEvent();

		try {
			suiteContext.activate();

			suiteEventContext.proceed();
		}
		finally {
			suiteContext.deactivate();

			if (suiteEvent instanceof AfterSuite) {
				suiteContext.destroy();
			}
		}
	}

	public void createTestContext(
		@Observes(precedence = 100) EventContext<TestEvent> testEventContext) {

		TestContext testContext = _testContextInstance.get();

		TestEvent testEvent = testEventContext.getEvent();

		TestClass testClass = testEvent.getTestClass();

		Class<?> javaClass = testClass.getJavaClass();

		try {
			testContext.activate(testEvent.getTestInstance());

			Set<Object> instances = _activatedTestContexts.get(javaClass);

			if (instances == null) {
				instances = new HashSet<>();

				_activatedTestContexts.put(javaClass, instances);
			}

			instances.add(testEvent.getTestInstance());

			testEventContext.proceed();
		}
		finally {
			testContext.deactivate();
		}
	}

	private final Map<Class<?>, Set<Object>> _activatedTestContexts =
		new HashMap<>();

	@Inject
	private Instance<ClassContext> _classContextInstance;

	@Inject
	private Instance<SuiteContext> _suiteContextInstance;

	@ApplicationScoped
	@Inject
	private InstanceProducer<TestClass> _testClassInstanceProducer;

	@Inject
	private Instance<TestContext> _testContextInstance;

}