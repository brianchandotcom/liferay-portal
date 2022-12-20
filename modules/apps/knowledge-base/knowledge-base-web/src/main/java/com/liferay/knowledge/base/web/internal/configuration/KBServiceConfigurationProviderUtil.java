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

package com.liferay.knowledge.base.web.internal.configuration;

import com.liferay.knowledge.base.configuration.KBServiceConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = {})
public class KBServiceConfigurationProviderUtil {

	public static int getExpirationDateNotificationDateWeeks(long companyId)
		throws ConfigurationException {

		return _kbServiceConfigurationProvider.
			getExpirationDateNotificationDateWeeks(companyId);
	}

	@Reference(unbind = "-")
	protected void setKBServiceConfigurationProvider(
		KBServiceConfigurationProvider kbServiceConfigurationProvider) {

		_kbServiceConfigurationProvider = kbServiceConfigurationProvider;
	}

	private static KBServiceConfigurationProvider
		_kbServiceConfigurationProvider;

}