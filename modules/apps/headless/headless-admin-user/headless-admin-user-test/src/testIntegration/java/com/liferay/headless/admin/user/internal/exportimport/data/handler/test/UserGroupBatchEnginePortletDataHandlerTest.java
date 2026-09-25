/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.user.resource.v1_0.UserGroupResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class UserGroupBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		UserGroup userGroup = _userGroupLocalService.getOrAddEmptyUserGroup(
			RandomTestUtil.randomString(), _getCompanyId(groupId), userId,
			RandomTestUtil.randomString());

		return userGroup.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		long companyId = _getCompanyId(groupId);

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			RandomTestUtil.randomString(), userId, companyId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				companyId, groupId, userId));

		userGroup.setModifiedDate(dateModified);

		userGroup = _userGroupLocalService.updateUserGroup(userGroup);

		return userGroup.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_userGroupLocalService.deleteUserGroup(
			_getUserGroup(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		UserGroup userGroup = _getUserGroup(groupId, externalReferenceCode);

		return userGroup.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		UserGroup userGroup = _getUserGroup(groupId, externalReferenceCode);

		return userGroup.getDescription();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			UserGroupResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_userGroupLocalService.getUserGroups(_getCompanyId(groupId)),
			UserGroup::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		UserGroup userGroup = _getUserGroup(groupId, externalReferenceCode);

		return userGroup.getUserGroupId();
	}

	@Override
	protected Scope getScope() {
		return Scope.COMPANY;
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		UserGroup userGroup = _getUserGroup(groupId, externalReferenceCode);

		return userGroup.getStatus();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return true;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		UserGroup userGroup = _getUserGroup(groupId, externalReferenceCode);

		long companyId = _getCompanyId(groupId);

		_userGroupLocalService.updateUserGroup(
			userGroup.getExternalReferenceCode(), companyId,
			userGroup.getUserGroupId(), userGroup.getName(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				companyId, groupId, userGroup.getUserId()));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private UserGroup _getUserGroup(long groupId, String externalReferenceCode)
		throws Exception {

		return _userGroupLocalService.fetchUserGroupByExternalReferenceCode(
			externalReferenceCode, _getCompanyId(groupId));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}