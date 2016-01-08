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
import com.liferay.configuration.admin.ExtendedMetaTypeService;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.AttributeDefinitionUtil;
import com.liferay.configuration.admin.web.util.ConfigurationModelRetriever;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	public void deactivate() {
		_bundleContext = null;
	}

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		if (!(resourceResponse instanceof MimeResponse)) {
			return false;
		}

		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");

		String pid = ParamUtil.getString(resourceRequest, "pid", factoryPid);

		try {
			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, pid + ".cfg",
				getPropertiesAsBytes(resourceRequest, resourceResponse),
				ContentTypes.TEXT_XML_UTF8);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
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

		String pid = ParamUtil.getString(resourceRequest, "pid", factoryPid);

		ConfigurationModel configurationModel = configurationModels.get(pid);

		if ((configurationModel == null) && Validator.isNotNull(factoryPid)) {
			configurationModel = configurationModels.get(factoryPid);
		}

		if (configurationModel != null) {
			ExtendedAttributeDefinition[] attributeDefinitions =
				configurationModel.getAttributeDefinitions(
					ConfigurationModel.ALL);

			for (AttributeDefinition attributeDefinition :
					attributeDefinitions) {

				Configuration configuration =
					configurationModel.getConfiguration();

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
		}

		return properties;
	}

	protected byte[] getPropertiesAsBytes(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Properties properties = getProperties(
			resourceRequest, resourceResponse);

		String propertiesString = PropertiesUtil.toString(properties);

		propertiesString =
			"##\n## Deploy this file to a Liferay installation to apply the " +
				"configuration.\n##\n"+ propertiesString;

		return propertiesString.getBytes();
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setConfigurationModelRetriever(
		ConfigurationModelRetriever configurationModelRetriever) {

		_configurationModelRetriever = configurationModelRetriever;
	}

	@Reference(unbind = "-")
	protected void setExtendedMetaTypeService(
		ExtendedMetaTypeService extendedMetaTypeService) {

		_extendedMetaTypeService = extendedMetaTypeService;
	}

	private BundleContext _bundleContext;
	private volatile ConfigurationAdmin _configurationAdmin;
	private volatile ConfigurationModelRetriever _configurationModelRetriever;
	private volatile ExtendedMetaTypeService _extendedMetaTypeService;

}