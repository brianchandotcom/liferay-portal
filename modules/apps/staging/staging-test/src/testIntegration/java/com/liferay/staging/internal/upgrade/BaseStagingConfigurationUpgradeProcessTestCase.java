/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.upgrade;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.staging.configuration.StagingConfiguration;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public abstract class BaseStagingConfigurationUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Configuration originalConfiguration = _getConfiguration(companyId);

		Dictionary<String, Object> originalProperties = null;

		if (originalConfiguration != null) {
			originalProperties = originalConfiguration.getProperties();
		}

		try {
			_configurationProvider.saveCompanyConfiguration(
				StagingConfiguration.class, companyId,
				HashMapDictionaryBuilder.<String, Object>put(
					getPropertyName(), true
				).build());

			Configuration configuration = _getConfiguration(companyId);

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertTrue(
				GetterUtil.getBoolean(properties.get(getPropertyName())));

			_runUpgrade();

			configuration = _getConfiguration(companyId);

			properties = configuration.getProperties();

			Assert.assertNull(properties.get(getPropertyName()));
		}
		finally {
			if (originalConfiguration != null) {
				originalConfiguration.update(originalProperties);
			}
			else {
				_configurationProvider.deleteCompanyConfiguration(
					StagingConfiguration.class, companyId);
			}
		}
	}

	protected abstract String getPropertyName();

	protected abstract Version getUpgradeStepVersion();

	private Configuration _getConfiguration(long companyId) throws Exception {
		String filterString = StringBundler.concat(
			"(&(service.factoryPid=com.liferay.staging.configuration.",
			"StagingConfiguration.scoped)(",
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(), "=",
			companyId, "))");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, getUpgradeStepVersion());

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}
	}

	@Inject(
		filter = "(&(component.name=com.liferay.staging.internal.upgrade.registry.StagingImplUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private ConfigurationProvider _configurationProvider;

}