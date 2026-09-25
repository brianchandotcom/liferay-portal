/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
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
public class RoleBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		Role role = _roleLocalService.getOrAddEmptyRole(
			RandomTestUtil.randomString(), _getCompanyId(groupId), userId, null,
			0, RandomTestUtil.randomString(), RoleConstants.TYPE_REGULAR);

		return role.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		Role role = _roleLocalService.addRole(
			RandomTestUtil.randomString(), userId, null, 0,
			RandomTestUtil.randomString(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			null, RoleConstants.TYPE_REGULAR, null,
			ServiceContextTestUtil.getServiceContext(groupId, userId));

		role.setModifiedDate(dateModified);

		role = _roleLocalService.updateRole(role);

		return role.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_roleLocalService.deleteRole(_getRole(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		Role role = _getRole(groupId, externalReferenceCode);

		return role.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		Role role = _getRole(groupId, externalReferenceCode);

		return role.getTitle(LocaleUtil.getDefault());
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			RoleResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_roleLocalService.getRoles(_getCompanyId(groupId)),
			Role::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		Role role = _getRole(groupId, externalReferenceCode);

		return role.getRoleId();
	}

	@Override
	protected Scope getScope() {
		return Scope.COMPANY;
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		Role role = _getRole(groupId, externalReferenceCode);

		return role.getStatus();
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

		Role role = _getRole(groupId, externalReferenceCode);

		_roleLocalService.updateRole(
			role.getExternalReferenceCode(), role.getRoleId(), role.getName(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			role.getDescriptionMap(), role.getSubtype(),
			ServiceContextTestUtil.getServiceContext(
				_getCompanyId(groupId), groupId, role.getUserId()));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private Role _getRole(long groupId, String externalReferenceCode)
		throws Exception {

		return _roleLocalService.fetchRoleByExternalReferenceCode(
			externalReferenceCode, _getCompanyId(groupId));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}