/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service.impl.test;

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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
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
public class SXPBlueprintImportUpgradeContributorStorageSchemaTest {

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

		_sxpBlueprintV10 = _addSXPBlueprint("1.0");
		_sxpBlueprintV11 = _addSXPBlueprint("1.0");
	}

	@Test
	public void testImportSXPBlueprintWithDisableAllContributors()
		throws Exception {

		SXPBlueprint sxpBlueprintV10 =
			_sxpBlueprintLocalService.getSXPBlueprint(
				_sxpBlueprintV10.getSXPBlueprintId());

		_assertGeneralConfiguration(
			_WILDCARD_ARRAY, new String[0], sxpBlueprintV10);

		SXPBlueprint sxpBlueprintV11 =
			_sxpBlueprintLocalService.getSXPBlueprint(
				_sxpBlueprintV11.getSXPBlueprintId());

		_assertGeneralConfiguration(
			_getExpectedExcludes(_sxpBlueprintV11),
			_getExpectedIncludes(_sxpBlueprintV11), sxpBlueprintV11);
	}

	@Test
	public void testImportSXPBlueprintWithEnableAllContributors()
		throws Exception {

		SXPBlueprint sxpBlueprintV10 =
			_sxpBlueprintLocalService.getSXPBlueprint(
				_sxpBlueprintV10.getSXPBlueprintId());

		_assertGeneralConfiguration(
			new String[0], _WILDCARD_ARRAY, sxpBlueprintV10);

		SXPBlueprint sxpBlueprintV11 =
			_sxpBlueprintLocalService.getSXPBlueprint(
				_sxpBlueprintV11.getSXPBlueprintId());

		_assertGeneralConfiguration(
			_getExpectedExcludes(_sxpBlueprintV11),
			_getExpectedIncludes(_sxpBlueprintV11), sxpBlueprintV11);
	}

	@Test
	public void testImportSXPBlueprintWithEnableSomeContributors()
		throws Exception {

		String[] expectedIncludes = {
			"com.liferay.journal.internal.search.spi.model.query.contributor." +
				"JournalArticleKeywordQueryContributor",
			"com.liferay.journal.internal.search.spi.model.query.contributor." +
				"JournalFolderKeywordQueryContributor"
		};

		SXPBlueprint sxpBlueprintV10 =
			_sxpBlueprintLocalService.getSXPBlueprint(
				_sxpBlueprintV10.getSXPBlueprintId());

		_assertGeneralConfiguration(
			new String[0], expectedIncludes, sxpBlueprintV10);

		SXPBlueprint sxpBlueprintV11 =
			_sxpBlueprintLocalService.getSXPBlueprint(
				_sxpBlueprintV11.getSXPBlueprintId());

		_assertGeneralConfiguration(
			_getExpectedExcludes(_sxpBlueprintV11),
			_getExpectedIncludes(_sxpBlueprintV11), sxpBlueprintV11);
	}

	@Rule
	public TestName testName = new TestName();

	private SXPBlueprint _addSXPBlueprint(String schemaVersion)
		throws Exception {

		Class<?> clazz = getClass();

		return _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(),
			StringUtil.read(
				clazz,
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".json")),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			"", schemaVersion,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);
	}

	private void _assertGeneralConfiguration(
			String[] expectedExcludes, String[] expectedIncludes,
			SXPBlueprint sxpBlueprint)
		throws Exception {

		GeneralConfiguration generalConfiguration = _getGeneralConfiguration(
			sxpBlueprint);

		Assert.assertArrayEquals(
			expectedExcludes,
			generalConfiguration.getClauseContributorsExcludes());

		Assert.assertArrayEquals(
			expectedIncludes,
			generalConfiguration.getClauseContributorsIncludes());
	}

	private String[] _getExpectedExcludes(SXPBlueprint sxpBlueprint) {
		GeneralConfiguration generalConfiguration = _getGeneralConfiguration(
			sxpBlueprint);

		return generalConfiguration.getClauseContributorsExcludes();
	}

	private String[] _getExpectedIncludes(SXPBlueprint sxpBlueprint) {
		GeneralConfiguration generalConfiguration = _getGeneralConfiguration(
			sxpBlueprint);

		return generalConfiguration.getClauseContributorsIncludes();
	}

	private GeneralConfiguration _getGeneralConfiguration(
		SXPBlueprint sxpBlueprint) {

		Configuration configuration = ConfigurationUtil.toConfiguration(
			sxpBlueprint.getConfigurationJSON());

		return configuration.getGeneralConfiguration();
	}

	private static final String[] _WILDCARD_ARRAY = {"*"};

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprintV10;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprintV11;

}