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

package com.liferay.arquillian.extension.junit.bridge.remote.executor;

import java.lang.reflect.Method;

import java.util.Collection;

import org.jboss.arquillian.container.test.impl.execution.event.LocalExecutionEvent;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.test.spi.TestEnricher;
import org.jboss.arquillian.test.spi.TestMethodExecutor;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.annotation.TestScoped;

/**
 * @author Matthew Tambara
 */
public class LocalTestExecuter {

	public void execute(@Observes LocalExecutionEvent event) throws Exception {
		TestResult result = new TestResult();

		TestMethodExecutor testMethodExecutor = event.getExecutor();

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		try {
			testMethodExecutor.invoke(
				_enrichArguments(
					testMethodExecutor.getMethod(),
					serviceLoader.all(TestEnricher.class)));

			result.setStatus(TestResult.Status.PASSED);
		}
		catch (Throwable e) {
			result.setStatus(TestResult.Status.FAILED);
			result.setThrowable(e);
		}
		finally {
			result.setEnd(System.currentTimeMillis());
		}

		_testResultInstanceProducer.set(result);
	}

	private Object[] _enrichArguments(
		Method method, Collection<TestEnricher> enrichers) {

		Class<?>[] parameterTypes = method.getParameterTypes();

		Object[] values = new Object[parameterTypes.length];

		if (parameterTypes.length == 0) {
			return values;
		}

		for (TestEnricher enricher : enrichers) {
			_mergeValues(values, enricher.resolve(method));
		}

		return values;
	}

	private void _mergeValues(Object[] values, Object[] resolvedValues) {
		if ((resolvedValues == null) || (resolvedValues.length == 0)) {
			return;
		}

		if (values.length != resolvedValues.length) {
			throw new IllegalStateException(
				"TestEnricher resolved wrong argument count, expected " +
					values.length + " returned " + resolvedValues.length);
		}

		for (int i = 0; i < resolvedValues.length; i++) {
			 Object resolvedValue = resolvedValues[i];

			if ((resolvedValue != null) && (values[i] == null)) {
				values[i] = resolvedValue;
			}
		}
	}

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

	@Inject
	@TestScoped
	private InstanceProducer<TestResult> _testResultInstanceProducer;

}