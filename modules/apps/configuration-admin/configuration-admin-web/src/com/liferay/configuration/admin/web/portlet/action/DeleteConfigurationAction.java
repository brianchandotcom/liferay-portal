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

package com.liferay.configuration.admin.web.portlet.action;

import com.liferay.configuration.admin.web.util.ConfigurationHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.MetaTypeService;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
@Component(
	immediate = true, service = ActionCommand.class,
	property = {
		"action.command.name=deleteConfiguration",
		"javax.portlet.name=com_liferay_configuration_admin_web_portlet_" +
			"ConfigurationAdminPortlet"
	}
)
public class DeleteConfigurationAction implements ActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		String pid = ParamUtil.getString(portletRequest, "pid");

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting configuration for service: " + pid);
		}

		try {
			ConfigurationHelper configurationHelper = new ConfigurationHelper(
				_bundleContext, _configurationAdmin, _metaTypeService, pid);

			Configuration configuration = configurationHelper.getConfiguration(
				pid);

			configuration.delete();
		}
		catch (IOException e) {
			throw new PortletException(e);
		}

		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(unbind = "-")
	protected void setConfigAdminService(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setMetaTypeService(MetaTypeService metaTypeService) {
		_metaTypeService = metaTypeService;
	}

	protected BundleContext _bundleContext;
	protected ConfigurationAdmin _configurationAdmin;
	protected MetaTypeService _metaTypeService;

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteConfigurationAction.class);

}