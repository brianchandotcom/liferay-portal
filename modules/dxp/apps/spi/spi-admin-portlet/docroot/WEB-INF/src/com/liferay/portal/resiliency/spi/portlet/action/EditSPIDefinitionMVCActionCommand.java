/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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