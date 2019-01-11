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

package com.liferay.arquillian.extension.junit.bridge.protocol.osgi;

import java.util.Collection;

import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentPackager;
import org.jboss.arquillian.container.test.spi.client.deployment.ProtocolArchiveProcessor;
import org.jboss.arquillian.protocol.jmx.AbstractJMXProtocol;

/**
 * @author Matthew Tambara
 */
public class JMXOSGiProtocol extends AbstractJMXProtocol {

	@Override
	public DeploymentPackager getPackager() {
		return (TestDeployment testDeployment,
				Collection<ProtocolArchiveProcessor> processors) ->
				testDeployment.getApplicationArchive();
	}

	@Override
	public String getProtocolName() {
		return _PROTOCOL_NAME;
	}

	private static final String _PROTOCOL_NAME = "jmx-osgi";

}