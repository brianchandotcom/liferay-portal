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

package com.liferay.dynamic.data.lists.form.web.display.context.util;

import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormConstants;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lino Alves
 */
public class DDLFormWebRequestHelper extends BaseRequestHelper {

	public DDLFormWebRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public DDLFormWebConfiguration getDDLFormWebConfiguration() {
		if (_ddlFormWebConfiguration != null) {
			return _ddlFormWebConfiguration;
		}

		try {
			if (getPortletResource() != null) {
				HttpServletRequest request = getRequest();

				_ddlFormWebConfiguration =
					ConfigurationFactoryUtil.getConfiguration(
						DDLFormWebConfiguration.class,
						new ParameterMapSettingsLocator(
							request.getParameterMap(),
							new GroupServiceSettingsLocator(
								getSiteGroupId(),
								DDLFormConstants.SERVICE_NAME)));
			}
			else {
				_ddlFormWebConfiguration =
					ConfigurationFactoryUtil.getConfiguration(
						DDLFormWebConfiguration.class,
						new GroupServiceSettingsLocator(
							getSiteGroupId(), DDLFormConstants.SERVICE_NAME));
			}

			return _ddlFormWebConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private DDLFormWebConfiguration _ddlFormWebConfiguration;

}