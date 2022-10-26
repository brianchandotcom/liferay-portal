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