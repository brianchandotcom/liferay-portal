/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.upgrade.v1_0_1;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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

		int interval = _prefsProps.getInteger(
			"dl.file.indexing.interval", _DL_FILE_INDEXING_INTERVAL);

		if (ArrayUtil.isEmpty(configurations) &&
			(interval != _DL_FILE_INDEXING_INTERVAL)) {

			_addNewConfiguration(interval);
		}
		else if (ArrayUtil.isNotEmpty(configurations)) {
			for (Configuration configuration : configurations) {
				_upgradeExistingConfiguration(configuration, interval);
			}
		}
	}

	private void _addNewConfiguration(int interval) throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			ReindexConfiguration.class.getName(), StringPool.QUESTION);

		configuration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				"indexingBatchSizes",
				new String[] {
					StringBundler.concat(
						DLFileEntry.class.getName(), "=", interval)
				}
			).build());
	}

	private boolean _isDLFileEntryConfigurationEntry(String value) {
		String[] valueParts = StringUtil.split(value, StringPool.EQUAL);

		if ((valueParts.length == 2) &&
			Objects.equals(valueParts[0], DLFileEntry.class.getName())) {

			return true;
		}

		return false;
	}

	private void _upgradeExistingConfiguration(
			Configuration configuration, int interval)
		throws Exception {

		Dictionary<String, Object> properties = configuration.getProperties();

		String[] values = GetterUtil.getStringValues(
			properties.get("indexingBatchSizes"));

		for (String value : values) {
			if (_isDLFileEntryConfigurationEntry(value)) {
				return;
			}
		}

		properties.put(
			"indexingBatchSizes",
			ArrayUtil.append(
				values, DLFileEntry.class.getName() + "=" + interval));

		configuration.update(properties);
	}

	private static final int _DL_FILE_INDEXING_INTERVAL = 500;

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}