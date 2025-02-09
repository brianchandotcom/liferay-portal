/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.internal.batch.engine.action.test.util.ExportImportTaskResourceTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class TaxonomyCategoryImportTaskPreActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_localGroup = GroupTestUtil.addGroup();
		_targetGroup = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_localAssetVocabulary = _assetVocabularyLocalService.addVocabulary(
			_user.getUserId(), _localGroup.getGroupId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
		_targetAssetVocabulary = _assetVocabularyLocalService.addVocabulary(
			_user.getUserId(), _targetGroup.getGroupId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testImportWithInsertAndKeepCreator() throws Exception {
		AssetCategory assetCategory = _getAssetCategory(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId(),
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_localAssetVocabulary.getVocabularyId())
			).build());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"KEEP_CREATOR", json,
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_targetAssetVocabulary.getVocabularyId())
			).build());

		_assertAssetCategory(
			assetCategory.getExternalReferenceCode(), _targetGroup.getGroupId(),
			_user.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserDoesNotExist()
		throws Exception {

		AssetCategory assetCategory = _getAssetCategory(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId(),
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_localAssetVocabulary.getVocabularyId())
			).build());

		_userLocalService.deleteUser(_user);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"KEEP_CREATOR", json,
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_targetAssetVocabulary.getVocabularyId())
			).build());

		_assertAssetCategory(
			assetCategory.getExternalReferenceCode(), _targetGroup.getGroupId(),
			TestPropsValues.getUserId());
	}

	@Test
	public void testImportWithInsertAndOverwriteCreator() throws Exception {
		AssetCategory assetCategory = _getAssetCategory(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId(),
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_localAssetVocabulary.getVocabularyId())
			).build());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"OVERWRITE_CREATOR", json,
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_targetAssetVocabulary.getVocabularyId())
			).build());

		_assertAssetCategory(
			assetCategory.getExternalReferenceCode(), _targetGroup.getGroupId(),
			TestPropsValues.getUserId());
	}

	@Test
	public void testImportWithUpsertAndKeepCreator() throws Exception {
		AssetCategory assetCategory = _getAssetCategory(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId(),
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_localAssetVocabulary.getVocabularyId())
			).build());

		_assetCategoryLocalService.deleteVocabularyCategories(
			_localAssetVocabulary.getVocabularyId());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "UPSERT", _localGroup.getGroupId(),
			"KEEP_CREATOR", json,
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_localAssetVocabulary.getVocabularyId())
			).build());

		_assertAssetCategory(
			assetCategory.getExternalReferenceCode(), _localGroup.getGroupId(),
			_user.getUserId());
	}

	@Test
	public void testImportWithUpsertAndOverwriteCreator() throws Exception {
		AssetCategory assetCategory = _getAssetCategory(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId(),
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_localAssetVocabulary.getVocabularyId())
			).build());

		_assetCategoryLocalService.deleteVocabularyCategories(
			_localAssetVocabulary.getVocabularyId());

		_userLocalService.deleteUser(_user);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "UPSERT", _localGroup.getGroupId(),
			"OVERWRITE_CREATOR", json,
			HashMapBuilder.put(
				"taxonomyVocabularyId",
				String.valueOf(_localAssetVocabulary.getVocabularyId())
			).build());

		_assertAssetCategory(
			assetCategory.getExternalReferenceCode(), _localGroup.getGroupId(),
			TestPropsValues.getUserId());
	}

	private void _assertAssetCategory(
			String externalReferenceCode, long groupId, long userId)
		throws Exception {

		AssetCategory assetCategory =
			_assetCategoryLocalService.getAssetCategoryByExternalReferenceCode(
				externalReferenceCode, groupId);

		Assert.assertEquals(userId, assetCategory.getUserId());

		_assetCategoryLocalService.deleteCategory(assetCategory);
	}

	private AssetCategory _getAssetCategory(long userId) throws Exception {
		return _assetCategoryLocalService.addCategory(
			userId, _localGroup.getGroupId(), RandomTestUtil.randomString(),
			_localAssetVocabulary.getVocabularyId(),
			ServiceContextTestUtil.getServiceContext(_localGroup.getGroupId()));
	}

	private static final String _ITEM_CLASS_NAME =
		TaxonomyCategory.class.getName();

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private AssetVocabulary _localAssetVocabulary;

	@DeleteAfterTestRun
	private Group _localGroup;

	private AssetVocabulary _targetAssetVocabulary;

	@DeleteAfterTestRun
	private Group _targetGroup;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}