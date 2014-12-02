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

package com.liferay.portal.wab.extender.ws.axis;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class AxisConfigurationFactory
	implements org.apache.axis.EngineConfigurationFactory {

	public AxisConfigurationFactory() {
		_engineConfigurationFactory =
			org.apache.axis.configuration.EngineConfigurationFactoryDefault.
				newFactory(null);
	}

	@Override
	public org.apache.axis.EngineConfiguration getClientEngineConfig() {
		return _engineConfigurationFactory.getClientEngineConfig();
	}

	@Override
	public org.apache.axis.EngineConfiguration getServerEngineConfig() {
		return _engineConfigurationFactory.getServerEngineConfig();
	}

	private final org.apache.axis.EngineConfigurationFactory
		_engineConfigurationFactory;

}