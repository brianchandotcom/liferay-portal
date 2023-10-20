/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_8;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.constants.DLFileEntryConfigurationConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marco Galluzzi
 */
public class DLFileEntryConfigurationUpgradeProcess extends UpgradeProcess {

	public DLFileEntryConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin,
		ConfigurationProvider configurationProvider) {

		_configurationAdmin = configurationAdmin;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long systemPreviewableProcessorMaxSize = _updateSystemConfiguration();

		_updateScopedConfigurations(systemPreviewableProcessorMaxSize);

		_deleteConfigurations(_CLASS_NAME_PDFPREVIEW_CONFIGURATION);
	}

	private HashMapDictionary<String, Object> _createDictionary(
		int maxNumberOfPages, long previewableProcessorMaxSize) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"maxNumberOfPages", maxNumberOfPages
		).put(
			"previewableProcessorMaxSize", previewableProcessorMaxSize
		).build();
	}

	private void _deleteConfigurations(String className) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format("(%s=%s*)", Constants.SERVICE_PID, className));

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			configuration.delete();
		}
	}

	private <T> T _getAttributeValue(
		Configuration configuration, String attributeName, T defaultValue) {

		if (configuration == null) {
			return defaultValue;
		}

		Dictionary<String, Object> dictionary = configuration.getProperties();

		if (dictionary == null) {
			return defaultValue;
		}

		T value = (T)dictionary.get(attributeName);

		if (value == null) {
			return defaultValue;
		}

		return value;
	}

	private Configuration[] _getScopedConfigurations(String className)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(%s=%s.scoped)", ConfigurationAdmin.SERVICE_FACTORYPID,
				className));

		if (configurations == null) {
			return new Configuration[0];
		}

		return configurations;
	}

	private Configuration _getSystemConfiguration(String className)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format("(%s=%s)", Constants.SERVICE_PID, className));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private void _updateScopedConfigurations(
			long systemPreviewableProcessorMaxSize)
		throws Exception {

		Configuration[] configurations = _getScopedConfigurations(
			_CLASS_NAME_PDFPREVIEW_CONFIGURATION);

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> dictionary = _createDictionary(
				_getAttributeValue(
					configuration, "maxNumberOfPages",
					DLFileEntryConfigurationConstants.
						MAX_NUMBER_OF_PAGES_DEFAULT),
				Math.max(
					DLFileEntryConfigurationConstants.
						PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT,
					systemPreviewableProcessorMaxSize));

			long companyId = _getAttributeValue(
				configuration,
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				0L);

			if (companyId != 0) {
				_configurationProvider.saveCompanyConfiguration(
					DLFileEntryConfiguration.class, companyId, dictionary);

				continue;
			}

			long groupId = _getAttributeValue(
				configuration,
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey(), 0L);

			if (groupId != 0) {
				_configurationProvider.saveGroupConfiguration(
					DLFileEntryConfiguration.class, groupId, dictionary);
			}
		}
	}

	private long _updateSystemConfiguration() throws Exception {
		Configuration dlFileEntryConfiguration = _getSystemConfiguration(
			_CLASS_NAME_DLFILE_ENTRY_CONFIGURATION);
		Configuration pdfPreviewConfiguration = _getSystemConfiguration(
			_CLASS_NAME_PDFPREVIEW_CONFIGURATION);

		if ((dlFileEntryConfiguration != null) ||
			(pdfPreviewConfiguration != null)) {

			int maxNumberOfPages = _getAttributeValue(
				pdfPreviewConfiguration, "maxNumberOfPages",
				DLFileEntryConfigurationConstants.MAX_NUMBER_OF_PAGES_DEFAULT);

			long previewableProcessorMaxSize = _getAttributeValue(
				dlFileEntryConfiguration, "previewableProcessorMaxSize",
				DLFileEntryConfigurationConstants.
					PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT);

			_configurationProvider.saveSystemConfiguration(
				DLFileEntryConfiguration.class,
				_createDictionary(
					maxNumberOfPages, previewableProcessorMaxSize));

			return previewableProcessorMaxSize;
		}

		return DLFileEntryConfigurationConstants.
			PREVIEWABLE_PROCESSOR_MAX_SIZE_DEFAULT;
	}

	private static final String _CLASS_NAME_DLFILE_ENTRY_CONFIGURATION =
		"com.liferay.document.library.configuration.DLFileEntryConfiguration";

	private static final String _CLASS_NAME_PDFPREVIEW_CONFIGURATION =
		"com.liferay.document.library.preview.pdf.internal.configuration." +
			"PDFPreviewConfiguration";

	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationProvider _configurationProvider;

}