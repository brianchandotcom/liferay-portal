/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.configuration.persistence.listener;

import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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

		Object companyId = properties.get(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey());

		Object groupId = properties.get(
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey());

		if ((companyId == null) && (groupId != null)) {
			_log.error(
				StringBundler.concat(
					"Configuration ", pid,
					" will not work properly because it is missing the ",
					"required property \"companyId\""));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationImportGlobalConfigurationModelListener.class);

	@Reference
	private ConfigurationExportImportProcessor
		_configurationExportImportProcessor;

}