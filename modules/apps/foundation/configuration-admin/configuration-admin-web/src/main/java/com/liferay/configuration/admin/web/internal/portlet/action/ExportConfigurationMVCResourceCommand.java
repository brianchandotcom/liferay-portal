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

package com.liferay.configuration.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.web.internal.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.AttributeDefinitionUtil;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;

import java.io.FileInputStream;

import java.util.List;
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
			ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
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

		String pid = ParamUtil.getString(resourceRequest, "pid");
		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");

		try {
			if (Validator.isNotNull(pid)) {
				exportPid(resourceRequest, resourceResponse);
			}
			else if (Validator.isNotNull(factoryPid)) {
				exportFactoryPid(resourceRequest, resourceResponse);
			}
			else {
				exportAll(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

	protected void exportAll(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId());

		for (ConfigurationModel configurationModel :
				configurationModels.values()) {

			if (configurationModel.isFactory()) {
				String curFactoryPid = configurationModel.getFactoryPid();

				List<ConfigurationModel> factoryInstances =
					_configurationModelRetriever.getFactoryInstances(
						configurationModel);

				for (ConfigurationModel factoryInstance : factoryInstances) {
					String curPid = factoryInstance.getID();
					String curFileName = getFileName(curFactoryPid, curPid);

					zipWriter.addEntry(
						curFileName,
						getPropertiesAsBytes(
							languageId, curFactoryPid, curPid));
				}
			}
			else if (configurationModel.hasConfiguration()) {
				String curPid = configurationModel.getID();
				String curFileName = getFileName(null, curPid);

				zipWriter.addEntry(
					curFileName,
					getPropertiesAsBytes(languageId, curPid, curPid));
			}
		}

		String fileName = "liferay-system-settings.zip";

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName,
			new FileInputStream(zipWriter.getFile()),
			ContentTypes.TEXT_XML_UTF8);
	}

	protected void exportFactoryPid(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId());

		ConfigurationModel factoryConfigurationModel = configurationModels.get(
			factoryPid);

		List<ConfigurationModel> factoryInstances =
			_configurationModelRetriever.getFactoryInstances(
				factoryConfigurationModel);

		for (ConfigurationModel factoryInstance : factoryInstances) {
			String curPid = factoryInstance.getID();
			String curFileName = getFileName(null, curPid);

			zipWriter.addEntry(
				curFileName,
				getPropertiesAsBytes(languageId, factoryPid, curPid));
		}

		String fileName =
			"liferay-system-settings-" +
				factoryConfigurationModel.getFactoryPid() + ".zip";

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName,
			new FileInputStream(zipWriter.getFile()),
			ContentTypes.TEXT_XML_UTF8);
	}

	protected void exportPid(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String factoryPid = ParamUtil.getString(resourceRequest, "factoryPid");
		String pid = ParamUtil.getString(resourceRequest, "pid");

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		String fileName = getFileName(factoryPid, pid);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName,
			getPropertiesAsBytes(languageId, factoryPid, pid),
			ContentTypes.TEXT_XML_UTF8);
	}

	protected String getFileName(String factoryPid, String pid) {
		String fileName = pid;

		if (Validator.isNotNull(factoryPid) && !factoryPid.equals(pid)) {
			String factoryInstanceId = pid.substring(factoryPid.length() + 1);

			fileName = factoryPid + StringPool.DASH + factoryInstanceId;
		}

		return fileName + ".cfg";
	}

	protected Properties getProperties(
			String languageId, String factoryPid, String pid)
		throws Exception {

		Properties properties = new Properties();

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(languageId);

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

			if (value == null) {
				value = StringPool.BLANK;
			}

			properties.setProperty(attributeDefinition.getID(), value);
		}

		return properties;
	}

	protected byte[] getPropertiesAsBytes(
			String languageId, String factoryPid, String pid)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("##\n## To apply the configuration, place this file in the ");
		sb.append("Liferay installation's osgi/modules folder. Make sure it ");
		sb.append("is named ");
		sb.append(getFileName(factoryPid, pid));
		sb.append(".\n##\n\n");

		Properties properties = getProperties(languageId, factoryPid, pid);

		sb.append(PropertiesUtil.toString(properties));

		String propertiesString = sb.toString();

		return propertiesString.getBytes();
	}

	@Reference
	private ConfigurationModelRetriever _configurationModelRetriever;

}