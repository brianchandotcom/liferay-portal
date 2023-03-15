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

package com.liferay.friendly.url.internal.configuration.helper;

import com.liferay.friendly.url.configuration.helper.FriendlyURLRedirectionConfigurationHelper;
import com.liferay.friendly.url.internal.configuration.FriendlyURLRedirectionConfiguration;
import com.liferay.friendly.url.internal.configuration.admin.service.FriendlyURLRedirectionManagedServiceFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Mijarra
 */
@Component(
	configurationPid = "com.liferay.friendly.url.internal.configuration.FriendlyURLRedirectionConfiguration",
	service = FriendlyURLRedirectionConfigurationHelper.class
)
public class FriendlyURLRedirectionConfigurationHelperImpl
	implements FriendlyURLRedirectionConfigurationHelper {

	@Override
	public String getRedirectionType(long companyId) {
		FriendlyURLRedirectionConfiguration
			friendlyURLRedirectionConfiguration =
				friendlyURLRedirectionManagedServiceFactory.
					getCompanyFriendlyURLConfiguration(companyId);

		return friendlyURLRedirectionConfiguration.redirectionType();
	}

	@Reference
	protected FriendlyURLRedirectionManagedServiceFactory
		friendlyURLRedirectionManagedServiceFactory;

}