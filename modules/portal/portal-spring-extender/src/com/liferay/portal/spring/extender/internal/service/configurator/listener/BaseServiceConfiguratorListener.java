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

package com.liferay.portal.spring.extender.internal.service.configurator.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.extender.internal.service.configurator.ServiceConfigurator;

import org.springframework.context.ApplicationContext;

/**
 * @author Miguel Pastor
 */
public abstract class BaseServiceConfiguratorListener {

	public void setServiceConfigurator(
		ServiceConfigurator serviceConfigurator) {

		_serviceConfigurator = serviceConfigurator;
	}

	protected void doDestroy(ApplicationContext applicationContext) {
		try {
			_serviceConfigurator.destroy();
		}
		catch (Exception e) {
			_log.error(
				"Unable to destroy " + applicationContext.getDisplayName(), e);
		}
	}

	protected void doInit(ApplicationContext applicationContext) {
		try {
			_serviceConfigurator.init();
		}
		catch (Exception e) {
			_log.error(
				"Unable initialize " + applicationContext.getDisplayName(), e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseServiceConfiguratorListener.class);

	private ServiceConfigurator _serviceConfigurator;

}