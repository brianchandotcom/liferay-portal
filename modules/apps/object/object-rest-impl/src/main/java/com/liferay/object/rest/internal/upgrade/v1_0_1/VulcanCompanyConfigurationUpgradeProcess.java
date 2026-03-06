/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.upgrade.v1_0_1;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;
import java.util.List;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Vendel Toreki
 */
public class VulcanCompanyConfigurationUpgradeProcess extends UpgradeProcess {

	public VulcanCompanyConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin,
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		_configurationAdmin = configurationAdmin;
		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_upgradeObjectDefinitionConfiguration(objectDefinition);
		}
	}

	private void _upgradeObjectDefinitionConfiguration(
			ObjectDefinition objectDefinition)
		throws Exception {

		if (objectDefinition.isUnmodifiableSystemObject()) {
			return;
		}

		String factoryPid =
			"com.liferay.portal.vulcan.internal.configuration." +
				"VulcanCompanyConfiguration";

		String path = objectDefinition.getRESTContextPath();

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(",
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				"=", objectDefinition.getCompanyId(), ")(path=", path,
				")(service.factoryPid=", factoryPid, "))"));

		if ((configurations == null) || (configurations.length != 1)) {
			return;
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> dictionary = configuration.getProperties();

		if ((dictionary == null) || dictionary.isEmpty()) {
			return;
		}

		Object value = dictionary.get(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey());

		if ((value == null) || (value instanceof Long)) {
			return;
		}

		dictionary.put(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			GetterUtil.getLong(value));

		configuration.update(dictionary);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;

}