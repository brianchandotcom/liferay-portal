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

package com.liferay.arquillian.protocol.osgi;

import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentPackager;
import org.jboss.arquillian.protocol.jmx.AbstractJMXProtocol;

/**
 * @author Shuyang Zhou
 */
public class JMXOSGiProtocol extends AbstractJMXProtocol {

	@Override
	public DeploymentPackager getPackager() {
		return new OSGiDeploymentPackager();
	}

	@Override
	public String getProtocolName() {
		return "jmx-osgi";
	}

}