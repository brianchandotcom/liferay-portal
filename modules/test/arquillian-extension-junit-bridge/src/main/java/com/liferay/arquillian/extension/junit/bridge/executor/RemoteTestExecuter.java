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
import com.liferay.arquillian.extension.junit.bridge.protocol.osgi.JMXOSGiProtocol;

import java.util.Collection;
import java.util.Iterator;

import org.jboss.arquillian.container.spi.client.protocol.metadata.JMXContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.test.impl.domain.ProtocolDefinition;
import org.jboss.arquillian.container.test.impl.execution.event.RemoteExecutionEvent;
import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.container.test.spi.client.protocol.ProtocolConfiguration;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.annotation.TestScoped;

/**
 * @author Matthew Tambara
 */
public class RemoteTestExecuter {

	public void execute(@Observes RemoteExecutionEvent event) throws Exception {
		ContainerMethodExecutor executor = getContainerMethodExecutor(
			new ProtocolDefinition(new JMXOSGiProtocol()), null);

		_testResultInstanceProducer.set(executor.invoke(event.getExecutor()));
	}

	public ContainerMethodExecutor getContainerMethodExecutor(
		ProtocolDefinition protocol,
		ProtocolConfiguration protocolConfiguration) {

		ProtocolMetaData protocolMetaData = _protocolMetadataInstance.get();

		Collection<JMXContext> jmxContexts = protocolMetaData.getContexts(
			JMXContext.class);

		if (jmxContexts.isEmpty()) {
			throw new IllegalStateException(
				"No " + JMXContext.class.getName() + " was found in " +
					ProtocolMetaData.class.getName());
		}

		Iterator<JMXContext> iterator = jmxContexts.iterator();

		JMXContext jmxContext = iterator.next();

		return new JMXMethodExecutor(jmxContext.getConnection());
	}

	@Inject
	private Instance<ProtocolMetaData> _protocolMetadataInstance;

	@Inject
	@TestScoped
	private InstanceProducer<TestResult> _testResultInstanceProducer;

}