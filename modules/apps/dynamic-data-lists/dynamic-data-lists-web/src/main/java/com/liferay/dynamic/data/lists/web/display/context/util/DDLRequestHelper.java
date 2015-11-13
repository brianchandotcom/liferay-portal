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

package com.liferay.dynamic.data.lists.web.display.context.util;

import com.liferay.dynamic.data.lists.configuration.DDLServiceConfiguration;
import com.liferay.dynamic.data.lists.constants.DDLConstants;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.util.JavaConstants;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DDLRequestHelper extends BaseRequestHelper {

	public DDLRequestHelper(HttpServletRequest request) {
		super(request);

		_renderRequest = (RenderRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		_portletPreferences = _renderRequest.getPreferences();
	}

	public DDLServiceConfiguration getDDLServiceConfiguration() {
		if (_ddlServiceConfiguration != null) {
			return _ddlServiceConfiguration;
		}

		try {
			if (getPortletResource() != null) {
				HttpServletRequest request = getRequest();

				_ddlServiceConfiguration =
					ConfigurationFactoryUtil.getConfiguration(
						DDLServiceConfiguration.class,
						new ParameterMapSettingsLocator(
							request.getParameterMap(),
							new GroupServiceSettingsLocator(
								getSiteGroupId(), DDLConstants.SERVICE_NAME)));
			}
			else {
				_ddlServiceConfiguration =
					ConfigurationFactoryUtil.getConfiguration(
						DDLServiceConfiguration.class,
						new GroupServiceSettingsLocator(
							getSiteGroupId(), DDLConstants.SERVICE_NAME));
			}

			return _ddlServiceConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public PortletPreferences getPortletPreferences() {
		return _portletPreferences;
	}

	public RenderRequest getRenderRequest() {
		return _renderRequest;
	}

	private DDLServiceConfiguration _ddlServiceConfiguration;
	private final PortletPreferences _portletPreferences;
	private final RenderRequest _renderRequest;

}