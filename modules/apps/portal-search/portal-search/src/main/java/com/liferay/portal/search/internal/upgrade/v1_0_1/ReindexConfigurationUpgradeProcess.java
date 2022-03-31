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

package com.liferay.portal.search.internal.upgrade.v1_0_1;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.configuration.ReindexConfiguration;

import java.util.Dictionary;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Michael Bowerman
 */
public class ReindexConfigurationUpgradeProcess extends UpgradeProcess {

	public ReindexConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin, PrefsProps prefsProps) {

		_configurationAdmin = configurationAdmin;
		_prefsProps = prefsProps;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=",
			ReindexConfiguration.class.getName(), ")");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		int value = _prefsProps.getInteger(
			_DL_FILE_INDEXING_INTERVAL, _DEFAULT_VALUE);

		if (ArrayUtil.isEmpty(configurations) && (value != _DEFAULT_VALUE)) {
			_addNewConfiguration(value);
		}
		else if (ArrayUtil.isNotEmpty(configurations)) {
			for (Configuration configuration : configurations) {
				_upgradeExistingConfiguration(configuration, value);
			}
		}
	}

	private void _addNewConfiguration(int value) throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			ReindexConfiguration.class.getName(), StringPool.QUESTION);

		configuration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				_METHOD_NAME,
				new String[] {
					StringBundler.concat(
						DLFileEntry.class.getName(), "=", value)
				}
			).build());
	}

	private boolean _isDLFileEntryConfigurationLine(String line) {
		String[] pair = StringUtil.split(line, StringPool.EQUAL);

		if ((pair.length == 2) &&
			Objects.equals(pair[0], DLFileEntry.class.getName())) {

			return true;
		}

		return false;
	}

	private void _upgradeExistingConfiguration(
			Configuration configuration, int value)
		throws Exception {

		Dictionary<String, Object> properties = configuration.getProperties();

		String[] existingValue = null;

		Object existingValueObject = properties.get(_METHOD_NAME);

		if (existingValueObject != null) {
			existingValue = (String[])existingValueObject;
		}
		else {
			existingValue = new String[0];
		}

		for (String line : existingValue) {
			if (_isDLFileEntryConfigurationLine(line)) {
				return;
			}
		}

		properties.put(
			_METHOD_NAME,
			ArrayUtil.append(
				existingValue,
				StringBundler.concat(DLFileEntry.class.getName(), "=", value)));

		configuration.update(properties);
	}

	private static final int _DEFAULT_VALUE = 500;

	private static final String _DL_FILE_INDEXING_INTERVAL =
		"dl.file.indexing.interval";

	private static final String _METHOD_NAME = "indexingBatchSizes";

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}