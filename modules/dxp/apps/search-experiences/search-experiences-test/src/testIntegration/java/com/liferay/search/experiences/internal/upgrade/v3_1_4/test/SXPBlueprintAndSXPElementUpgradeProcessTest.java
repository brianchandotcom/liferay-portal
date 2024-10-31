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

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
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
		User user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, user.getUserId());
	}

	@Test
	public void testUpgrade() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			companyGroup.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			companyGroup.getGroupId(), assetVocabulary.getVocabularyId());
		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			companyGroup.getGroupId(), assetVocabulary.getVocabularyId());

		_addSXPBlueprint(
			_getElementInstancesJSON(assetCategory1, assetCategory2));

		for (String elementExternalReferenceCode :
				_ELEMENT_EXTERNAL_REFERENCE_CODES) {

			_addOldElement(elementExternalReferenceCode);
		}

		_runUpgrade();

		_assertSXPBlueprint(
			_getExpectedInstancesJSON(
				assetCategory1, assetCategory2,
				companyGroup.getExternalReferenceCode()),
			_sxpBlueprint.getSXPBlueprintId());

		for (String elementExternalReferenceCode :
				_ELEMENT_EXTERNAL_REFERENCE_CODES) {

			_assertElementUpgraded(elementExternalReferenceCode);
		}
	}

	private void _addOldElement(String externalReferenceCode) throws Exception {
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

	private void _addSXPBlueprint(String elementInstancesJSON)
		throws Exception {

		_sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, TestPropsValues.getUserId(),
			StringUtil.read(
				_clazz,
				StringBundler.concat(
					"dependencies/", _clazz.getSimpleName(),
					".configurationJSON.json")),
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
			StringUtil.read(
				_clazz,
				StringBundler.concat(
					"dependencies/", _clazz.getSimpleName(), StringPool.PERIOD,
					StringUtil.toLowerCase(externalReferenceCode), ".json")),
			sxpElement.getElementDefinitionJSON(), JSONCompareMode.STRICT);
	}

	private void _assertSXPBlueprint(
		String expectedInstancesJSON, long sxpBlueprintId) {

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprintId);

		JSONAssert.assertEquals(
			expectedInstancesJSON, sxpBlueprint.getElementInstancesJSON(),
			JSONCompareMode.STRICT);
	}

	private String _createAssetCategoryExternalReferenceCode(
		String assetCategoryExternalReferenceCode,
		String companyGroupExternalReferenceCode) {

		return companyGroupExternalReferenceCode + "&&" +
			assetCategoryExternalReferenceCode;
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

	private String _getElementInstancesJSON(
		AssetCategory assetCategory1, AssetCategory assetCategory2) {

		String elementInstancesJSON = StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(),
				".elementInstances.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$ASSET_CATEGORY_ID_1$]",
			String.valueOf(assetCategory1.getCategoryId()));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$ASSET_CATEGORY_ID_2$]",
			String.valueOf(assetCategory2.getCategoryId()));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$ASSET_CATEGORY_LABEL_1$]",
			_createAssetCategoryIDLabel(assetCategory1));

		return StringUtil.replace(
			elementInstancesJSON, "[$ASSET_CATEGORY_LABEL_2$]",
			_createAssetCategoryIDLabel(assetCategory2));
	}

	private String _getExpectedInstancesJSON(
		AssetCategory assetCategory1, AssetCategory assetCategory2,
		String companyGroupExternalReferenceCode) {

		String elementInstancesJSON = StringUtil.read(
			_clazz,
			StringBundler.concat(
				"dependencies/", _clazz.getSimpleName(),
				".elementInstancesUpdated.json"));

		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON,
			"[$ASSET_CATEGORY_EXTERNAL_REFERENCE_CODE_1$]",
			_createAssetCategoryExternalReferenceCode(
				assetCategory1.getExternalReferenceCode(),
				companyGroupExternalReferenceCode));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON,
			"[$ASSET_CATEGORY_EXTERNAL_REFERENCE_CODE_2$]",
			_createAssetCategoryExternalReferenceCode(
				assetCategory2.getExternalReferenceCode(),
				companyGroupExternalReferenceCode));
		elementInstancesJSON = StringUtil.replace(
			elementInstancesJSON, "[$ASSET_CATEGORY_LABEL_1$]",
			_createAssetCategoryExternalReferenceCodeLabel(assetCategory1));

		return StringUtil.replace(
			elementInstancesJSON, "[$ASSET_CATEGORY_LABEL_2$]",
			_createAssetCategoryExternalReferenceCodeLabel(assetCategory2));
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

	private static ServiceContext _serviceContext;

	@Inject(
		filter = "(&(component.name=com.liferay.search.experiences.internal.upgrade.registry.SXPServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private final Class<?> _clazz = getClass();

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Inject
	private SXPElementLocalService _sxpElementLocalService;

}