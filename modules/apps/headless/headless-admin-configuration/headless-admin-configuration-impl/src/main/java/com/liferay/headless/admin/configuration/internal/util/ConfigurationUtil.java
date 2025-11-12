/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.internal.util;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.configuration.admin.util.ConfigurationPidUtil;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsValues;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;

import org.osgi.service.cm.Configuration;

/**
 * @author Thiago Buarque
 */
public class ConfigurationUtil {

	public static Map<String, Object> getProperties(
			Configuration configuration,
			ConfigurationExportImportProcessor
				configurationExportImportProcessor,
			SettingsLocatorHelper settingsLocatorHelper)
		throws Exception {

		ConfigurationPidMapping configurationPidMapping =
			settingsLocatorHelper.getConfigurationPidMapping(
				ConfigurationPidUtil.getRawPid(configuration.getPid()));

		if (configurationPidMapping == null) {
			return Collections.emptyMap();
		}

		Dictionary<String, Object> properties = configuration.getProperties();

		configurationExportImportProcessor.prepareForExport(
			ConfigurationPidUtil.getRawPid(configuration.getPid()), properties);

		if (PropsValues.MODULE_FRAMEWORK_EXPORT_PASSWORD_ATTRIBUTES) {
			return HashMapBuilder.<String, Object>putAll(
				properties
			).build();
		}

		Class<?> configurationBeanClass =
			configurationPidMapping.getConfigurationBeanClass();

		for (Method method : configurationBeanClass.getMethods()) {
			Meta.AD ad = method.getAnnotation(Meta.AD.class);

			if ((ad != null) && (ad.type() == Meta.Type.Password)) {
				properties.remove(method.getName());
			}
		}

		return HashMapBuilder.<String, Object>putAll(
			properties
		).build();
	}

}