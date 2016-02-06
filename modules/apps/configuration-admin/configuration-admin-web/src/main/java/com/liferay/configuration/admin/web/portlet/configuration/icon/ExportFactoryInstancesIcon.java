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

package com.liferay.configuration.admin.web.portlet.configuration.icon;

import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminWebKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.ConfigurationModelIterator;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;

/**
 * @author Jorge Ferrer
 */
public class ExportFactoryInstancesIcon extends BasePortletConfigurationIcon {

	public ExportFactoryInstancesIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "export-entries";
	}

	@Override
	public String getMethod() {
		return "GET";
	}

	@Override
	public String getURL() {
		LiferayPortletURL liferayPortletURL =
			(LiferayPortletURL)PortalUtil.getControlPanelPortletURL(
				portletRequest, ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
				PortletRequest.RESOURCE_PHASE);

		ConfigurationModel factoryConfigurationModel =
			(ConfigurationModel)portletRequest.getAttribute(
				ConfigurationAdminWebKeys.FACTORY_CONFIGURATION_MODEL);

		liferayPortletURL.setParameter(
			"factoryPid", factoryConfigurationModel.getFactoryPid());

		liferayPortletURL.setResourceID("export");

		return liferayPortletURL.toString();
	}

	@Override
	public boolean isShow() {
		ConfigurationModelIterator configurationModelIterator =
			(ConfigurationModelIterator)portletRequest.getAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR);

		if (configurationModelIterator.getTotal() > 0) {
			return true;
		}

		return false;
	}

}