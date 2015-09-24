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

package com.liferay.dynamic.data.mapping.web.context.util;

import com.liferay.dynamic.data.mapping.configuration.DDMServiceConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lino Alves
 */
public class DDMWebRequestHelper extends BaseRequestHelper {

	public DDMWebRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public DDMServiceConfiguration getDDMServiceConfiguration() {
		try {
			if (_ddmServiceConfiguration == null) {
				if (Validator.isNotNull(getPortletResource())) {
					_ddmServiceConfiguration =
						ConfigurationFactoryUtil.getConfiguration(
							DDMServiceConfiguration.class,
						new ParameterMapSettingsLocator(
							getRequest().getParameterMap(),
							new GroupServiceSettingsLocator(
								getSiteGroupId(), DDMConstants.SERVICE_NAME)));
				}
				else {
					_ddmServiceConfiguration =
						ConfigurationFactoryUtil.getConfiguration(
							DDMServiceConfiguration.class,
							new GroupServiceSettingsLocator(
								getSiteGroupId(), DDMConstants.SERVICE_NAME));
				}
			}

			return _ddmServiceConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private DDMServiceConfiguration _ddmServiceConfiguration;

}