/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.internal.util;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;

import org.osgi.framework.BundleContext;

/**
 * @author Thiago Buarque
 */
public class ConfigurationScreenUtil {

	public static ServiceTrackerMap<String, ConfigurationScreen>
		createServiceTracker(
			BundleContext bundleContext,
			ExtendedObjectClassDefinition.Scope scope) {

		return ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ConfigurationScreen.class, null,
			(serviceReference, emitter) -> {
				ConfigurationScreen configurationScreen =
					bundleContext.getService(serviceReference);

				if (!StringUtil.equals(
						configurationScreen.getScope(), scope.getValue())) {

					return;
				}

				emitter.emit(configurationScreen.getKey());
			});
	}

	public static Map<String, Object> getProperties(
			ConfigurationExportImportProcessor
				configurationExportImportProcessor,
			ConfigurationScreen configurationScreen,
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws Exception {

		Dictionary<String, Object> properties =
			configurationScreen.exportProperties(scopePK);

		if ((properties == null) || properties.isEmpty()) {
			return Collections.emptyMap();
		}

		if (scope.getPropertyKey() != null) {
			properties.put(scope.getPropertyKey(), scopePK);
		}

		configurationExportImportProcessor.prepareForExport(
			configurationScreen.getKey(), properties);

		return HashMapBuilder.<String, Object>putAll(
			properties
		).build();
	}

	public static void importProperties(
			ConfigurationExportImportProcessor
				configurationExportImportProcessor,
			ConfigurationScreen configurationScreen,
			Dictionary<String, Object> properties,
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws Exception {

		String portablePropertyKey = scope.getPortablePropertyKey();

		if ((portablePropertyKey != null) &&
			(properties.get(portablePropertyKey) == null)) {

			properties.put(scope.getPropertyKey(), scopePK);

			configurationExportImportProcessor.prepareForExport(
				configurationScreen.getKey(), properties);
		}

		configurationExportImportProcessor.prepareForImport(
			configurationScreen.getKey(), properties);

		configurationScreen.importProperties(properties, scopePK);
	}

}