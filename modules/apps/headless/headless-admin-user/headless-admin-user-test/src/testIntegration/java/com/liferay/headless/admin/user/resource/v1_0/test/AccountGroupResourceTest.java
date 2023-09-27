/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountGroup;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@Ignore
@RunWith(Arquillian.class)
public class AccountGroupResourceTest extends BaseAccountGroupResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testDeleteAccountGroup() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testDeleteAccountGroupByExternalReferenceCode()
		throws Exception {
	}

	@Override
	@Test
	public void testGetAccountAccountGroupsPage() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			_serviceContext.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		com.liferay.account.model.AccountGroup accountGroup1 =
			_accountGroupLocalService.addAccountGroup(
				_serviceContext.getUserId(), null,
				RandomTestUtil.randomString(), _serviceContext);

		accountGroup1.setExternalReferenceCode(null);
		accountGroup1.setDefaultAccountGroup(false);
		accountGroup1.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup1.setExpandoBridgeAttributes(_serviceContext);

		accountGroup1 = _accountGroupLocalService.updateAccountGroup(
			accountGroup1);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup1.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		com.liferay.account.model.AccountGroup accountGroup2 =
			_accountGroupLocalService.addAccountGroup(
				_serviceContext.getUserId(), null,
				RandomTestUtil.randomString(), _serviceContext);

		accountGroup2.setExternalReferenceCode(null);
		accountGroup2.setDefaultAccountGroup(false);
		accountGroup2.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup2.setExpandoBridgeAttributes(_serviceContext);

		accountGroup2 = _accountGroupLocalService.updateAccountGroup(
			accountGroup2);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup2.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		Page<AccountGroup> page =
			accountGroupResource.getAccountAccountGroupsPage(
				accountEntry.getAccountEntryId(), Pagination.of(1, 20));

		Assert.assertEquals(2, page.getTotalCount());

		List<Long> accountGroupsIds = new ArrayList<>();

		accountGroupsIds.add(accountGroup1.getAccountGroupId());
		accountGroupsIds.add(accountGroup2.getAccountGroupId());

		for (AccountGroup accountGroup : page.getItems()) {
			Assert.assertTrue(accountGroupsIds.contains(accountGroup.getId()));
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountAccountGroupsPageWithPagination()
		throws Exception {
	}

	@Override
	@Test
	public void testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage()
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			_serviceContext.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		com.liferay.account.model.AccountGroup accountGroup1 =
			_accountGroupLocalService.addAccountGroup(
				_serviceContext.getUserId(), null,
				RandomTestUtil.randomString(), _serviceContext);

		accountGroup1.setExternalReferenceCode(null);
		accountGroup1.setDefaultAccountGroup(false);
		accountGroup1.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup1.setExpandoBridgeAttributes(_serviceContext);

		accountGroup1 = _accountGroupLocalService.updateAccountGroup(
			accountGroup1);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup1.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		com.liferay.account.model.AccountGroup accountGroup2 =
			_accountGroupLocalService.addAccountGroup(
				_serviceContext.getUserId(), null,
				RandomTestUtil.randomString(), _serviceContext);

		accountGroup2.setExternalReferenceCode(null);
		accountGroup2.setDefaultAccountGroup(false);
		accountGroup2.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup2.setExpandoBridgeAttributes(_serviceContext);

		accountGroup2 = _accountGroupLocalService.updateAccountGroup(
			accountGroup2);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup2.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		Page<AccountGroup> page =
			accountGroupResource.
				getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
					accountEntry.getExternalReferenceCode(),
					Pagination.of(1, 20));

		Assert.assertEquals(2, page.getTotalCount());

		List<Long> accountGroupIds = new ArrayList<>();

		accountGroupIds.add(accountGroup1.getAccountGroupId());
		accountGroupIds.add(accountGroup2.getAccountGroupId());

		for (AccountGroup accountGroup : page.getItems()) {
			Assert.assertTrue(accountGroupIds.contains(accountGroup.getId()));
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPageWithPagination()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroup() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroupByExternalReferenceCode()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroupByExternalReferenceCodeNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountGroupNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testPatchAccountGroup() throws Exception {
		Assert.assertTrue(false);
	}

	@Ignore
	@Override
	@Test
	public void testPatchAccountGroupByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Override
	protected AccountGroup testDeleteAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup
			testDeleteAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup testGetAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup
			testGetAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup testGetAccountGroupsPage_addAccountGroup(
			AccountGroup accountGroup)
		throws Exception {

		return _postAccountGroup(accountGroup);
	}

	@Override
	protected AccountGroup testGraphQLAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup testPostAccountGroup_addAccountGroup(
			AccountGroup accountGroup)
		throws Exception {

		return _postAccountGroup(accountGroup);
	}

	private AccountGroup _postAccountGroup(AccountGroup accountGroup)
		throws Exception {

		return accountGroupResource.postAccountGroup(accountGroup);
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	private ServiceContext _serviceContext;

}