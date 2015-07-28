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

package com.liferay.social.activity.web.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portlet.social.constants.SocialConstants;
import com.liferay.social.activity.configuration.SocialActivityGroupServiceConfiguration;

/**
 * @author Roberto Díaz
 */
public class SocialActivityUtil {

	public static SocialActivityGroupServiceConfiguration
		getSocialActivityGroupServiceConfiguration(
			long companyId)
		throws PortalException {

		SettingsFactory settingsFactory =
			SettingsFactoryUtil.getSettingsFactory();

		return settingsFactory.getSettings(
			SocialActivityGroupServiceConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId, SocialConstants.SERVICE_NAME));
	}

}