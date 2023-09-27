/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.configuration.persistence.listener;

import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "model.class.name=*", service = ConfigurationModelListener.class
)
public class ConfigurationImportGlobalConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			_configurationExportImportProcessor.prepareForImport(
				pid, properties);
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception, Object.class,
				ConfigurationImportGlobalConfigurationModelListener.class,
				properties);
		}
	}

	@Reference
	private ConfigurationExportImportProcessor
		_configurationExportImportProcessor;

}