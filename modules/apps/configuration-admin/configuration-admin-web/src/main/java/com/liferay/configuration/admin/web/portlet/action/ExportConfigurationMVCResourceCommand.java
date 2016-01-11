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

import com.liferay.configuration.admin.ExtendedAttributeDefinition;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.AttributeDefinitionUtil;
import com.liferay.configuration.admin.web.util.ConfigurationModelRetriever;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;
import java.util.Properties;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.AttributeDefinition;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 * @author Eduardo Garcia
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" +
			ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
		"mvc.command.name=export"
	},
	service = MVCResourceCommand.class
)
public class ExportConfigurationMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		if (!(resourceResponse instanceof MimeResponse)) {
			return false;
		}

		String fileName = getFileName(resourceRequest);

		try {
			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, fileName,
				getPropertiesAsBytes(resourceRequest, resourceResponse),
				ContentTypes.TEXT_XML_UTF8);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

	protected String getFileName(ResourceRequest resourceRequest) {
		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");
		String pid = ParamUtil.getString(resourceRequest, "pid");

		String fileName = pid;

		if (Validator.isNotNull(factoryPid) && !factoryPid.equals(pid)) {
			String factoryInstanceId = pid.substring(factoryPid.length() + 1);

			fileName = factoryPid + StringPool.DASH + factoryInstanceId;
		}

		return fileName + ".cfg";
	}

	protected Properties getProperties(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Properties properties = new Properties();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId());

		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");
		String pid = ParamUtil.getString(resourceRequest, "pid");

		ConfigurationModel configurationModel = configurationModels.get(pid);

		if ((configurationModel == null) && Validator.isNotNull(factoryPid)) {
			configurationModel = configurationModels.get(factoryPid);
		}

		if (configurationModel == null) {
			return properties;
		}

		Configuration configuration =
			_configurationModelRetriever.getConfiguration(pid);

		if (configuration == null) {
			return properties;
		}

		ExtendedAttributeDefinition[] attributeDefinitions =
			configurationModel.getAttributeDefinitions(ConfigurationModel.ALL);

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			String[] values = AttributeDefinitionUtil.getProperty(
				attributeDefinition, configuration);

			String value = null;

			// See http://goo.gl/JhYK7g

			if (values.length == 1) {
				value = values[0];
			}
			else if (values.length > 1) {
				value = StringUtil.merge(values, "\n");
			}

			properties.setProperty(attributeDefinition.getID(), value);
		}

		return properties;
	}

	protected byte[] getPropertiesAsBytes(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("##\n## To apply the configuration, deploy this file to a ");
		sb.append("Liferay installation with the file name ");
		sb.append(getFileName(resourceRequest));
		sb.append(".\n##\n\n");

		Properties properties = getProperties(
			resourceRequest, resourceResponse);

		sb.append(PropertiesUtil.toString(properties));

		String propertiesString = sb.toString();

		return propertiesString.getBytes();
	}

	@Reference(unbind = "-")
	protected void setConfigurationModelRetriever(
		ConfigurationModelRetriever configurationModelRetriever) {

		_configurationModelRetriever = configurationModelRetriever;
	}

	private ConfigurationModelRetriever _configurationModelRetriever;

}