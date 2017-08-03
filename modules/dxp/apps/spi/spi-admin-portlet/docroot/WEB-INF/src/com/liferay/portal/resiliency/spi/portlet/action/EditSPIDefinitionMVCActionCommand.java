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

package com.liferay.portal.resiliency.spi.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Michael C. Han
 */
public class EditSPIDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long spiDefinitionId = ParamUtil.getLong(
			actionRequest, "spiDefinitionId");

		int connectorPort = ParamUtil.getInteger(
			actionRequest, "connectorPort");
		String description = ParamUtil.getString(actionRequest, "description");
		String jvmArguments = ParamUtil.getString(
			actionRequest, "jvmArguments");
		String portletIds = ParamUtil.getString(actionRequest, "portletIds");
		String servletContextNames = ParamUtil.getString(
			actionRequest, "servletContextNames");

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		boolean useDefaultNotificationOptions = ParamUtil.getBoolean(
			actionRequest, "useDefaultNotificationOptions", true);

		if (useDefaultNotificationOptions) {
			typeSettingsProperties.remove("notification-recipients");
		}

		boolean useDefaultRestartOptions = ParamUtil.getBoolean(
			actionRequest, "useDefaultRestartOptions", true);

		if (useDefaultRestartOptions) {
			typeSettingsProperties.remove("max-restart-attempts");
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SPIDefinition.class.getName(), actionRequest);

		SPIDefinitionServiceUtil.updateSPIDefinition(
			spiDefinitionId, "localhost", connectorPort, description,
			jvmArguments, portletIds, servletContextNames,
			typeSettingsProperties.toString(), serviceContext);
	}

}