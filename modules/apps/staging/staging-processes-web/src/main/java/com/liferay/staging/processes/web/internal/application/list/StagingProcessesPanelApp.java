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

package com.liferay.staging.processes.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.staging.constants.StagingProcessesPortletKeys;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 */
@Component(
	configurationPid = "com.liferay.change.tracking.configuration.CTSettingsConfiguration",
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_PUBLISHING
	},
	service = PanelApp.class
)
public class StagingProcessesPanelApp extends BasePanelApp {

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public String getPortletId() {
		return StagingProcessesPortletKeys.STAGING_PROCESSES;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		CTSettingsConfiguration ctSettingsConfiguration =
			_getCTSettingsConfiguration(group.getCompanyId());

		if (ctSettingsConfiguration.enabled()) {
			return false;
		}

		return super.isShow(permissionChecker, group);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultCTSettingsConfiguration = ConfigurableUtil.createConfigurable(
			CTSettingsConfiguration.class, properties);
	}

	private CTSettingsConfiguration _getCTSettingsConfiguration(
		long companyId) {

		CTSettingsConfiguration ctSettingsConfiguration =
			ConfigurableUtil.createConfigurable(
				CTSettingsConfiguration.class, Collections.emptyMap());

		try {
			ctSettingsConfiguration =
				_configurationProvider.getCompanyConfiguration(
					CTSettingsConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return ctSettingsConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingProcessesPanelApp.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile CTSettingsConfiguration _defaultCTSettingsConfiguration;

	@Reference(
		target = "(javax.portlet.name=" + StagingProcessesPortletKeys.STAGING_PROCESSES + ")"
	)
	private Portlet _portlet;

}