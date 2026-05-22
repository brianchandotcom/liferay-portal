/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class SXPBlueprintCollectionProviderUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		String originalConfiguration = _readJSON("configuration");

		_sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(), originalConfiguration,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			StringPool.BLANK, _OLD_SCHEMA_VERSION,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.search.experiences.internal.upgrade.v3_2_0." +
				"SXPBlueprintCollectionProviderUpgradeProcess");

		upgradeProcess.upgrade();

		_multiVMPool.clear();

		_sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			_sxpBlueprint.getSXPBlueprintId());

		JSONObject configurationJSONObject = JSONFactoryUtil.createJSONObject(
			_sxpBlueprint.getConfigurationJSON());

		JSONObject generalConfigurationJSONObject =
			configurationJSONObject.getJSONObject("generalConfiguration");

		Assert.assertTrue(
			generalConfigurationJSONObject.getBoolean("collectionProvider"));
		Assert.assertEquals(
			"com.liferay.asset.kernel.model.AssetEntry",
			generalConfigurationJSONObject.getString("collectionProviderType"));
		Assert.assertTrue(
			generalConfigurationJSONObject.getBoolean(
				"legacyAssetCollectionProvider"));

		Assert.assertEquals(
			_NEW_SCHEMA_VERSION, _sxpBlueprint.getSchemaVersion());
	}

	private String _readJSON(String name) {
		return StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
				name, ".json"));
	}

	private static final String _NEW_SCHEMA_VERSION = "1.2";

	private static final String _OLD_SCHEMA_VERSION = "1.1";

	private final Class<?> _clazz = getClass();

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.search.experiences.internal.upgrade.registry.SXPServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}