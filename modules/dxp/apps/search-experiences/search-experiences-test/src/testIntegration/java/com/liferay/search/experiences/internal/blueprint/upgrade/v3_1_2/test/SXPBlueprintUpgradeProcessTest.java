/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.upgrade.v3_1_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.util.ConfigurationUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class SXPBlueprintUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		Class<?> clazz = getClass();

		_sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(),
			StringUtil.read(
				clazz,
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".json")),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			"", "1.1",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);

		_sxpBlueprint.setSchemaVersion("1.0");

		_sxpBlueprint = _sxpBlueprintLocalService.updateSXPBlueprint(
			_sxpBlueprint);
	}

	@Test
	public void testUpgradeBlueprintDisableAllContributors() throws Exception {
		_runUpgrade();

		_assertGeneralConfiguration(_WILDCARD_ARRAY, new String[0]);
	}

	@Test
	public void testUpgradeBlueprintEnableAllContributors() throws Exception {
		_runUpgrade();

		_assertGeneralConfiguration(new String[0], _WILDCARD_ARRAY);
	}

	@Test
	public void testUpgradeBlueprintEnableSomeContributors() throws Exception {
		_runUpgrade();

		String[] expectedIncludes = {
			"com.liferay.journal.internal.search.spi.model.query.contributor." +
				"JournalArticleKeywordQueryContributor",
			"com.liferay.journal.internal.search.spi.model.query.contributor." +
				"JournalFolderKeywordQueryContributor"
		};

		_assertGeneralConfiguration(new String[0], expectedIncludes);
	}

	@Rule
	public TestName testName = new TestName();

	private void _assertGeneralConfiguration(
			String[] expectedExcludes, String[] expectedIncludes)
		throws Exception {

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.getSXPBlueprint(
			_sxpBlueprint.getSXPBlueprintId());

		Configuration configuration = ConfigurationUtil.toConfiguration(
			sxpBlueprint.getConfigurationJSON());

		GeneralConfiguration generalConfiguration =
			configuration.getGeneralConfiguration();

		Assert.assertArrayEquals(
			expectedExcludes,
			generalConfiguration.getClauseContributorsExcludes());

		Assert.assertArrayEquals(
			expectedIncludes,
			generalConfiguration.getClauseContributorsIncludes());
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.search.experiences.internal.upgrade.v3_1_2." +
			"SXPBlueprintUpgradeProcess";

	private static final String[] _WILDCARD_ARRAY = {"*"};

	@Inject(
		filter = "(&(component.name=com.liferay.search.experiences.internal.upgrade.registry.SXPServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}