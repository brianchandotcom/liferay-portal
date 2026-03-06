/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.upgrade.v1_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class VulcanCompanyConfigurationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeConfigurationCompanyIds() throws Exception {
		List<ObjectField> objectFields = Collections.singletonList(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
				RandomTestUtil.randomString(), "textField", false));

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(), objectFields,
				ObjectDefinitionConstants.SCOPE_COMPANY);

		String path = objectDefinition.getRESTContextPath();

		String factoryPid =
			"com.liferay.portal.vulcan.internal.configuration." +
				"VulcanCompanyConfiguration";

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				factoryPid, StringPool.QUESTION);

		long companyId = TestPropsValues.getCompanyId();

		configuration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				String.valueOf(companyId)
			).put(
				"path", path
			).build());

		_runUpgrade();

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(",
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				"=", companyId, ")(path=", path, ")(service.factoryPid=",
				factoryPid, "))"));

		Assert.assertNotNull(configurations);

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		configuration = configurations[0];

		Dictionary<String, Object> dictionary = configuration.getProperties();

		Object value = dictionary.get(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey());

		Assert.assertNotNull(value);

		Assert.assertTrue(value instanceof Long);

		Assert.assertEquals(companyId, value);
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 0, 1));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		CacheRegistryUtil.clear();
	}

	@Inject(
		filter = "(&(component.name=com.liferay.object.rest.internal.upgrade.registry.ObjectRESTImplUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

}