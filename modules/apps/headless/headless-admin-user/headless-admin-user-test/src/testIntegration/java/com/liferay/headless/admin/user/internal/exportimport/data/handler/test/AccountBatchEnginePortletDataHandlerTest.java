/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.exportimport.data.handler.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.rule.ExportImportScopes;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.user.resource.v1_0.AccountResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
@ExportImportScopes(Scope.COMPANY)
@RunWith(Arquillian.class)
public class AccountBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			RandomTestUtil.randomString(), userId,
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_getCompanyId(groupId), groupId, userId));

		accountEntry.setModifiedDate(dateModified);

		accountEntry = _accountEntryLocalService.updateAccountEntry(
			accountEntry);

		return accountEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_accountEntryLocalService.deleteAccountEntry(
			_getAccountEntry(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry = _getAccountEntry(
			groupId, externalReferenceCode);

		return accountEntry.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry = _getAccountEntry(
			groupId, externalReferenceCode);

		return accountEntry.getDescription();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			AccountResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_accountEntryLocalService.getAccountEntries(
				_getCompanyId(groupId), WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
			AccountEntry::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry = _getAccountEntry(
			groupId, externalReferenceCode);

		return accountEntry.getAccountEntryId();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry = _getAccountEntry(
			groupId, externalReferenceCode);

		_accountEntryLocalService.updateAccountEntry(
			accountEntry.getExternalReferenceCode(),
			accountEntry.getAccountEntryId(),
			accountEntry.getParentAccountEntryId(), accountEntry.getName(),
			RandomTestUtil.randomString(), false,
			accountEntry.getDomainsArray(), accountEntry.getEmailAddress(),
			null, accountEntry.getTaxIdNumber(), accountEntry.getStatus(),
			ServiceContextTestUtil.getServiceContext(
				_getCompanyId(groupId), groupId, accountEntry.getUserId()));
	}

	private AccountEntry _getAccountEntry(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _accountEntryLocalService.
			fetchAccountEntryByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}