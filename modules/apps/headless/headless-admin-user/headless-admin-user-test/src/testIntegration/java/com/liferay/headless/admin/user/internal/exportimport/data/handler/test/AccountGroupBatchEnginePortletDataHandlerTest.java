/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.exportimport.data.handler.test;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.user.resource.v1_0.AccountGroupResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
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
public class AccountGroupBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		AccountGroup accountGroup =
			_accountGroupLocalService.getOrAddEmptyAccountGroup(
				RandomTestUtil.randomString(), _getCompanyId(groupId), userId,
				RandomTestUtil.randomString());

		return accountGroup.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		AccountGroup accountGroup = _accountGroupLocalService.addAccountGroup(
			RandomTestUtil.randomString(), userId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_getCompanyId(groupId), groupId, userId));

		accountGroup.setModifiedDate(dateModified);

		accountGroup = _accountGroupLocalService.updateAccountGroup(
			accountGroup);

		return accountGroup.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_accountGroupLocalService.deleteAccountGroup(
			_getAccountGroup(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		AccountGroup accountGroup = _getAccountGroup(
			groupId, externalReferenceCode);

		return accountGroup.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		AccountGroup accountGroup = _getAccountGroup(
			groupId, externalReferenceCode);

		return accountGroup.getDescription();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			AccountGroupResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_accountGroupLocalService.getAccountGroups(
				_getCompanyId(groupId), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null),
			AccountGroup::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		AccountGroup accountGroup = _getAccountGroup(
			groupId, externalReferenceCode);

		return accountGroup.getAccountGroupId();
	}

	@Override
	protected Scope getScope() {
		return Scope.COMPANY;
	}

	@Override
	protected int getStatus(long groupId, String externalReferenceCode)
		throws Exception {

		AccountGroup accountGroup = _getAccountGroup(
			groupId, externalReferenceCode);

		return accountGroup.getStatus();
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

		AccountGroup accountGroup = _getAccountGroup(
			groupId, externalReferenceCode);

		_accountGroupLocalService.updateAccountGroup(
			accountGroup.getExternalReferenceCode(),
			accountGroup.getAccountGroupId(), RandomTestUtil.randomString(),
			accountGroup.getName(),
			ServiceContextTestUtil.getServiceContext(
				_getCompanyId(groupId), groupId, accountGroup.getUserId()));
	}

	private AccountGroup _getAccountGroup(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _accountGroupLocalService.
			fetchAccountGroupByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}