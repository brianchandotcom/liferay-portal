/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class SXPBlueprintAndSXPElementUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _user.getUserId());
	}

	@Test
	public void testSXPBlueprintUpgradeProcess() throws Exception {
		_addAssetCategories();

		SXPBlueprint sxpBlueprint = _addSXPBlueprint();

		_runUpgrade();

		_assertSXPBlueprint(sxpBlueprint.getSXPBlueprintId());
	}

	@Test
	public void testSXPElementUpgradeProcess() throws Exception {
		for (String elementExternalReferenceCode :
				_ELEMENT_EXTERNAL_REFERENCE_CODES) {

			_createOldElement(elementExternalReferenceCode);
		}

		_runUpgrade();

		for (String elementExternalReferenceCode :
				_ELEMENT_EXTERNAL_REFERENCE_CODES) {

			_assertElementUpgraded(elementExternalReferenceCode);
		}
	}

	@Rule
	public TestName testName = new TestName();

	private void _addAssetCategories() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_globalGroup = company.getGroup();

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_globalGroup.getGroupId());

		_assetCategory1 = AssetTestUtil.addCategory(
			_globalGroup.getGroupId(), assetVocabulary.getVocabularyId());

		_assetCategory2 = AssetTestUtil.addCategory(
			_globalGroup.getGroupId(), assetVocabulary.getVocabularyId());
	}

	private SXPBlueprint _addSXPBlueprint() throws Exception {
		Class<?> clazz = getClass();

		String configurationJSON = StringUtil.read(
			clazz,
			StringBundler.concat(
				"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".configurationJSON.json"));

		String elementInstancesJSON = StringUtil.read(
			clazz,
			StringBundler.concat(
				"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".before.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_ID_1$",
			String.valueOf(_assetCategory1.getCategoryId()));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_ID_2$",
			String.valueOf(_assetCategory2.getCategoryId()));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_LABEL_1$",
			_createAssetCategoryIDLabel(_assetCategory1));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_LABEL_2$",
			_createAssetCategoryIDLabel(_assetCategory2));

		return _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(), configurationJSON,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			elementInstancesJSON, "1.1",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);
	}

	private void _assertElementUpgraded(String externalReferenceCode)
		throws Exception {

		SXPElement sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				externalReferenceCode, TestPropsValues.getCompanyId());

		JSONAssert.assertEquals(
			sxpElement.getElementDefinitionJSON(),
			_getExpectedElementDefinitionJSON(externalReferenceCode),
			JSONCompareMode.STRICT);
	}

	private void _assertSXPBlueprint(long sxpBlueprintId) throws Exception {
		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprintId);

		Assert.assertNotNull(sxpBlueprint);

		JSONAssert.assertEquals(
			_getExpectedInstancesJSON(), sxpBlueprint.getElementInstancesJSON(),
			JSONCompareMode.STRICT);
	}

	private String _createAssetCategoryExternalReferenceCodeLabel(
		AssetCategory assetCategory) {

		return StringBundler.concat(
			assetCategory.getName(), " (ERC: ",
			assetCategory.getExternalReferenceCode(), ")");
	}

	private String _createAssetCategoryIDLabel(AssetCategory assetCategory) {
		return StringBundler.concat(
			assetCategory.getName(), " (ID: ", assetCategory.getCategoryId(),
			")");
	}

	private void _createOldElement(String externalReferenceCode)
		throws Exception {

		SXPElement sxpElement =
			_sxpElementLocalService.fetchSXPElementByExternalReferenceCode(
				externalReferenceCode, TestPropsValues.getCompanyId());

		if (sxpElement != null) {
			sxpElement.setElementDefinitionJSON(RandomTestUtil.randomString());

			_sxpElementLocalService.updateSXPElement(sxpElement);
		}
		else {
			_sxpElementLocalService.addSXPElement(
				externalReferenceCode, TestPropsValues.getUserId(),
				Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
				RandomTestUtil.randomString(), StringPool.BLANK,
				StringPool.BLANK, true, StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				0,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getCompanyId(),
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
		}
	}

	private String _getExpectedElementDefinitionJSON(
		String externalReferenceCode) {

		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz,
			StringBundler.concat(
				"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), StringPool.PERIOD,
				StringUtil.toLowerCase(externalReferenceCode), ".json"));
	}

	private String _getExpectedInstancesJSON() {
		Class<?> clazz = getClass();

		String elementInstancesJSON = StringUtil.read(
			clazz,
			StringBundler.concat(
				"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".after.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_EXTERNAL_REFERENCE_CODE_1$",
			_globalGroup.getExternalReferenceCode() + "&&" +
				_assetCategory1.getExternalReferenceCode());
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_EXTERNAL_REFERENCE_CODE_2$",
			_globalGroup.getExternalReferenceCode() + "&&" +
				_assetCategory2.getExternalReferenceCode());
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_LABEL_1$",
			_createAssetCategoryExternalReferenceCodeLabel(_assetCategory1));

		return StringUtil.replace(
			elementInstancesJSON, "$ASSET_CATEGORY_LABEL_2$",
			_createAssetCategoryExternalReferenceCodeLabel(_assetCategory2));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.search.experiences.internal.upgrade.v3_1_4." +
				"SXPBlueprintAndSXPElementUpgradeProcess");

		upgradeProcess.upgrade();

		_multiVMPool.clear();
	}

	private static final String[] _ELEMENT_EXTERNAL_REFERENCE_CODES = {
		"BOOST_CONTENTS_IN_A_CATEGORY",
		"BOOST_CONTENTS_IN_A_CATEGORY_BY_KEYWORD_MATCH",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_A_PERIOD_OF_TIME",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_NEW_USER_ACCOUNTS",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_THE_TIME_OF_DAY",
		"BOOST_CONTENTS_IN_A_CATEGORY_FOR_USER_SEGMENTS",
		"HIDE_CONTENTS_IN_A_CATEGORY",
		"HIDE_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS"
	};

	@Inject(
		filter = "(&(component.name=com.liferay.search.experiences.internal.upgrade.registry.SXPServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private AssetCategory _assetCategory1;
	private AssetCategory _assetCategory2;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Group _globalGroup;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	private ServiceContext _serviceContext;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

	private User _user;

}