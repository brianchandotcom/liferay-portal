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

package com.liferay.arquillian.extension.junit.bridge.executor;

import com.liferay.arquillian.extension.junit.bridge.protocol.jmx.JMXMethodExecutor;

import javax.management.MBeanServerConnection;

import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.annotation.TestScoped;
import org.jboss.arquillian.test.spi.event.suite.Test;

/**
 * @author Matthew Tambara
 */
public class RemoteTestExecuter {

	public void execute(@Observes Test test) {
		ContainerMethodExecutor containerMethodExecutor = new JMXMethodExecutor(
			_mBeanServerConnectionInstance.get());

		_testResultInstanceProducer.set(
			containerMethodExecutor.invoke(test.getTestMethodExecutor()));
	}

	@Inject
	private Instance<MBeanServerConnection> _mBeanServerConnectionInstance;

	@Inject
	@TestScoped
	private InstanceProducer<TestResult> _testResultInstanceProducer;

}