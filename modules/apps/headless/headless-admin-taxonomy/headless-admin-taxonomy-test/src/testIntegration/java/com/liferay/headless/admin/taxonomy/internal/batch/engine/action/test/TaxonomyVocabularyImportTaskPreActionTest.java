/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
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
public class TaxonomyVocabularyImportTaskPreActionTest {

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
	}

	@Test
	public void testImportWithInsertAndKeepCreator() throws Exception {
		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"KEEP_CREATOR", json);

		_assertAssetVocabulary(
			assetVocabulary.getExternalReferenceCode(),
			_targetGroup.getGroupId(), _user.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserDoesNotExist()
		throws Exception {

		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		_userLocalService.deleteUser(_user);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"KEEP_CREATOR", json);

		_assertAssetVocabulary(
			assetVocabulary.getExternalReferenceCode(),
			_targetGroup.getGroupId(), TestPropsValues.getUserId());
	}

	@Test
	public void testImportWithInsertAndOverwriteCreator() throws Exception {
		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"OVERWRITE_CREATOR", json);

		_assertAssetVocabulary(
			assetVocabulary.getExternalReferenceCode(),
			_targetGroup.getGroupId(), TestPropsValues.getUserId());
	}

	@Test
	public void testImportWithUpsertAndKeepCreator() throws Exception {
		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "UPSERT", _localGroup.getGroupId(),
			"KEEP_CREATOR", json);

		_assertAssetVocabulary(
			assetVocabulary.getExternalReferenceCode(),
			_localGroup.getGroupId(), _user.getUserId());
	}

	@Test
	public void testImportWithUpsertAndOverwriteCreator() throws Exception {
		AssetVocabulary assetVocabulary = _getAssetVocabulary(
			_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);

		_userLocalService.deleteUser(_user);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "UPSERT", _localGroup.getGroupId(),
			"OVERWRITE_CREATOR", json);

		_assertAssetVocabulary(
			assetVocabulary.getExternalReferenceCode(),
			_localGroup.getGroupId(), TestPropsValues.getUserId());
	}

	private void _assertAssetVocabulary(
			String externalReferenceCode, long groupId, long userId)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.
				getAssetVocabularyByExternalReferenceCode(
					externalReferenceCode, groupId);

		Assert.assertEquals(userId, assetVocabulary.getUserId());
	}

	private AssetVocabulary _getAssetVocabulary(long userId) throws Exception {
		return _assetVocabularyLocalService.addVocabulary(
			userId, _localGroup.getGroupId(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_localGroup.getGroupId()));
	}

	private static final String _ITEM_CLASS_NAME =
		TaxonomyVocabulary.class.getName();

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private Group _localGroup;

	@DeleteAfterTestRun
	private Group _targetGroup;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}