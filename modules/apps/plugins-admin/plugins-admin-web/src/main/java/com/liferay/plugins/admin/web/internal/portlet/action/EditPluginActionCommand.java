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

package com.liferay.plugins.admin.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.plugins.admin.web.internal.constants.PluginsAdminPortletKeys;
import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.PluginSettingService;
import com.liferay.portal.kernel.service.PortletService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + PluginsAdminPortletKeys.PLUGINS_ADMIN,
		"mvc.command.name=/plugins_admin/edit_plugin"
	},
	service = MVCActionCommand.class
)
public class EditPluginActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updatePluginSetting(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				sendRedirect(actionRequest, actionResponse, "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setPluginSettingService(
		PluginSettingService pluginSettingService) {

		_pluginSettingService = pluginSettingService;
	}

	@Reference(unbind = "-")
	protected void setPortletService(PortletService portletService) {
		_portletService = portletService;
	}

	protected void updatePluginSetting(ActionRequest actionRequest)
		throws Exception {

		long companyId = _portal.getCompanyId(actionRequest);
		String pluginId = ParamUtil.getString(actionRequest, "pluginId");
		String pluginType = ParamUtil.getString(actionRequest, "pluginType");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (pluginType.equals(Plugin.TYPE_PORTLET)) {
			String portletId = pluginId;

			_portletService.updatePortlet(
				companyId, portletId, StringPool.BLANK, active);
		}
		else {
			String[] rolesArray = StringUtil.split(
				ParamUtil.getString(actionRequest, "roles"), '\n');

			Arrays.sort(rolesArray);

			String roles = StringUtil.merge(rolesArray);

			_pluginSettingService.updatePluginSetting(
				companyId, pluginId, pluginType, roles, active);
		}
	}

	private PluginSettingService _pluginSettingService;

	@Reference
	private Portal _portal;

	private PortletService _portletService;

}