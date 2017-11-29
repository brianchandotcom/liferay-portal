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

package com.liferay.configuration.admin.custom.view.sample.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=saveConfiguration"
	},
	service = MVCActionCommand.class
)
public class SaveConfigurationMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			String pid = ParamUtil.getString(actionRequest, "pid");
			boolean sampleCheckbox = ParamUtil.getBoolean(
				actionRequest, "sampleCheckbox");
			int sampleNumber = ParamUtil.getInteger(
				actionRequest, "sampleNumber");
			String sampleText = ParamUtil.getString(
				actionRequest, "sampleText");

			Configuration configuration = _configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION);

			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				properties = new Hashtable<>();
			}

			properties.put("sampleCheckbox", sampleCheckbox);
			properties.put("sampleNumber", sampleNumber);
			properties.put("sampleText", sampleText);

			configuration.update(properties);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}