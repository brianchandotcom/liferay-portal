/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.upgrade.v2_1_2;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.site.constants.LegacySitemapIndexPropsKeys;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Jonathan McCann
 */
public class XMLSitemapIndexEnabledConfigurationUpgradeProcess
	extends UpgradeProcess {

	public XMLSitemapIndexEnabledConfigurationUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String xmlSitemapIndexEnabled = PropsUtil.get(
			LegacySitemapIndexPropsKeys.XML_SITEMAP_INDEX_ENABLED);

		if (xmlSitemapIndexEnabled == null) {
			return;
		}

		_companyLocalService.forEachCompanyId(
			companyId -> _updateCompanyConfiguration(
				GetterUtil.getBoolean(xmlSitemapIndexEnabled), companyId));
	}

	private void _updateCompanyConfiguration(
			boolean xmlSitemapIndexEnabled, long companyId)
		throws Exception {

		Configuration configuration = null;
		Dictionary<String, Object> properties = null;

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))", _PID + ".scoped",
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				companyId));

		if (configurations == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				_PID + ".scoped", StringPool.QUESTION);
			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				companyId
			).build();
		}
		else {
			configuration = configurations[0];

			properties = configuration.getProperties();
		}

		properties.put("xmlSitemapIndexEnabled", xmlSitemapIndexEnabled);

		configuration.update(properties);
	}

	private static final String _PID =
		"com.liferay.site.internal.configuration.SitemapCompanyConfiguration";

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;

}