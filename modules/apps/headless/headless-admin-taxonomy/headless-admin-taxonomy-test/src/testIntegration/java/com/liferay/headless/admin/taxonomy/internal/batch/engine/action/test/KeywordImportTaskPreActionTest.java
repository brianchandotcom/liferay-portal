/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.internal.batch.engine.action.test.util.ExportImportTaskResourceTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
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
import com.liferay.portal.util.PortalInstances;

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
public class KeywordImportTaskPreActionTest {

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
		AssetTag assetTag = _getAssetTag(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"KEEP_CREATOR", json);

		_assertAssetTag(
			assetTag.getExternalReferenceCode(), _targetGroup.getGroupId(),
			_user.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserDoesNotExist()
		throws Exception {

		AssetTag assetTag = _getAssetTag(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		_userLocalService.deleteUser(_user);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"KEEP_CREATOR", json);

		_assertAssetTag(
			assetTag.getExternalReferenceCode(), _targetGroup.getGroupId(),
			TestPropsValues.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserFromDifferentCompany()
		throws Exception {

		AssetTag assetTag = _getAssetTag(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		Company company = CompanyLocalServiceUtil.addCompany(
			null, _VIRTUAL_HOST_NAME, _VIRTUAL_HOST_NAME, _VIRTUAL_HOST_NAME, 0,
			true, true, null, null, null, null, null, null);

		PortalInstances.initCompany(company);

		Group group = GroupTestUtil.addGroupToCompany(company.getCompanyId());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", group.getGroupId(), _VIRTUAL_HOST_NAME,
			"KEEP_CREATOR", json, null);

		_assertAssetTag(
			assetTag.getExternalReferenceCode(), group.getGroupId(),
			group.getCreatorUserId());
	}

	@Test
	public void testImportWithInsertAndOverwriteCreator() throws Exception {
		AssetTag assetTag = _getAssetTag(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "INSERT", _targetGroup.getGroupId(),
			"OVERWRITE_CREATOR", json);

		_assertAssetTag(
			assetTag.getExternalReferenceCode(), _targetGroup.getGroupId(),
			TestPropsValues.getUserId());
	}

	@Test
	public void testImportWithUpsertAndKeepCreator() throws Exception {
		AssetTag assetTag = _getAssetTag(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		_assetTagLocalService.deleteTag(assetTag);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "UPSERT", _localGroup.getGroupId(),
			"KEEP_CREATOR", json);

		_assertAssetTag(
			assetTag.getExternalReferenceCode(), _localGroup.getGroupId(),
			_user.getUserId());
	}

	@Test
	public void testImportWithUpsertAndOverwriteCreator() throws Exception {
		AssetTag assetTag = _getAssetTag(_user.getUserId());

		String json = ExportImportTaskResourceTestUtil.executeExportTask(
			_ITEM_CLASS_NAME, _localGroup.getGroupId());

		_assetTagLocalService.deleteTag(assetTag);

		_userLocalService.deleteUser(_user);

		ExportImportTaskResourceTestUtil.executeImportTask(
			_ITEM_CLASS_NAME, "UPSERT", _localGroup.getGroupId(),
			"OVERWRITE_CREATOR", json);

		_assertAssetTag(
			assetTag.getExternalReferenceCode(), _localGroup.getGroupId(),
			TestPropsValues.getUserId());
	}

	private void _assertAssetTag(
			String externalReferenceCode, long groupId, long userId)
		throws Exception {

		AssetTag assetTag =
			_assetTagLocalService.getAssetTagByExternalReferenceCode(
				externalReferenceCode, groupId);

		Assert.assertEquals(userId, assetTag.getUserId());
	}

	private AssetTag _getAssetTag(long userId) throws Exception {
		return _assetTagLocalService.addTag(
			null, userId, _localGroup.getGroupId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_localGroup.getGroupId()));
	}

	private static final String _ITEM_CLASS_NAME = Keyword.class.getName();

	private static final String _VIRTUAL_HOST_NAME = "www.able.com";

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@DeleteAfterTestRun
	private Group _localGroup;

	@DeleteAfterTestRun
	private Group _targetGroup;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}