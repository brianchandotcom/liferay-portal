/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.account.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.commerce.admin.account.client.dto.v1_0.AdminAccountGroup;
import com.liferay.headless.commerce.admin.account.client.pagination.Page;
import com.liferay.headless.commerce.admin.account.client.pagination.Pagination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@Ignore
@RunWith(Arquillian.class)
public class AdminAccountGroupResourceTest
	extends BaseAdminAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

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
	public void testGetAccountByExternalReferenceCodeAccountGroupsPage()
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			_serviceContext.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		AccountGroup accountGroup1 = _accountGroupLocalService.addAccountGroup(
			_serviceContext.getUserId(), null, RandomTestUtil.randomString(),
			_serviceContext);

		accountGroup1.setExternalReferenceCode(null);
		accountGroup1.setDefaultAccountGroup(false);
		accountGroup1.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup1.setExpandoBridgeAttributes(_serviceContext);

		accountGroup1 = _accountGroupLocalService.updateAccountGroup(
			accountGroup1);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup1.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		AccountGroup accountGroup2 = _accountGroupLocalService.addAccountGroup(
			_serviceContext.getUserId(), null, RandomTestUtil.randomString(),
			_serviceContext);

		accountGroup2.setExternalReferenceCode(null);
		accountGroup2.setDefaultAccountGroup(false);
		accountGroup2.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup2.setExpandoBridgeAttributes(_serviceContext);

		accountGroup2 = _accountGroupLocalService.updateAccountGroup(
			accountGroup2);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup2.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		Page<AdminAccountGroup> page =
			adminAccountGroupResource.
				getAccountByExternalReferenceCodeAccountGroupsPage(
					accountEntry.getExternalReferenceCode(),
					Pagination.of(1, 20));

		Assert.assertEquals(2, page.getTotalCount());

		List<Long> accountGroupIds = new ArrayList<>();

		accountGroupIds.add(accountGroup1.getAccountGroupId());
		accountGroupIds.add(accountGroup2.getAccountGroupId());

		for (AdminAccountGroup adminAccountGroup : page.getItems()) {
			Assert.assertTrue(
				accountGroupIds.contains(adminAccountGroup.getId()));
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountByExternalReferenceCodeAccountGroupsPageWithPagination()
		throws Exception {
	}

	@Override
	@Test
	public void testGetAccountIdAccountGroupsPage() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			_serviceContext.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		AccountGroup accountGroup1 = _accountGroupLocalService.addAccountGroup(
			_serviceContext.getUserId(), null, RandomTestUtil.randomString(),
			_serviceContext);

		accountGroup1.setExternalReferenceCode(null);
		accountGroup1.setDefaultAccountGroup(false);
		accountGroup1.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup1.setExpandoBridgeAttributes(_serviceContext);

		accountGroup1 = _accountGroupLocalService.updateAccountGroup(
			accountGroup1);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup1.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		AccountGroup accountGroup2 = _accountGroupLocalService.addAccountGroup(
			_serviceContext.getUserId(), null, RandomTestUtil.randomString(),
			_serviceContext);

		accountGroup2.setExternalReferenceCode(null);
		accountGroup2.setDefaultAccountGroup(false);
		accountGroup2.setType(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC);
		accountGroup2.setExpandoBridgeAttributes(_serviceContext);

		accountGroup2 = _accountGroupLocalService.updateAccountGroup(
			accountGroup2);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			accountGroup2.getAccountGroupId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		Page<AdminAccountGroup> page =
			adminAccountGroupResource.getAccountIdAccountGroupsPage(
				accountEntry.getAccountEntryId(), Pagination.of(1, 20));

		Assert.assertEquals(2, page.getTotalCount());

		List<Long> accountGroupsIds = new ArrayList<>();

		accountGroupsIds.add(accountGroup1.getAccountGroupId());
		accountGroupsIds.add(accountGroup2.getAccountGroupId());

		for (AdminAccountGroup adminAccountGroup : page.getItems()) {
			Assert.assertTrue(
				accountGroupsIds.contains(adminAccountGroup.getId()));
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetAccountIdAccountGroupsPageWithPagination()
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
	protected AdminAccountGroup testDeleteAccountGroup_addAdminAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAdminAccountGroup());
	}

	@Override
	protected AdminAccountGroup
			testDeleteAccountGroupByExternalReferenceCode_addAdminAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAdminAccountGroup());
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountGroupsPage_getExternalReferenceCode()
		throws Exception {

		AdminAccountGroup adminAccountGroup = _postAccountGroup(
			randomAdminAccountGroup());

		return adminAccountGroup.getExternalReferenceCode();
	}

	@Override
	protected AdminAccountGroup testGetAccountGroup_addAdminAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAdminAccountGroup());
	}

	@Override
	protected AdminAccountGroup
			testGetAccountGroupByExternalReferenceCode_addAdminAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAdminAccountGroup());
	}

	@Override
	protected AdminAccountGroup testGetAccountGroupsPage_addAdminAccountGroup(
			AdminAccountGroup adminAccountGroup)
		throws Exception {

		return _postAccountGroup(adminAccountGroup);
	}

	@Override
	protected Long testGetAccountIdAccountGroupsPage_getId() throws Exception {
		AdminAccountGroup adminAccountGroup = _postAccountGroup(
			randomAdminAccountGroup());

		return adminAccountGroup.getId();
	}

	@Override
	protected AdminAccountGroup
			testGraphQLAdminAccountGroup_addAdminAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAdminAccountGroup());
	}

	@Override
	protected AdminAccountGroup testPostAccountGroup_addAdminAccountGroup(
			AdminAccountGroup adminAccountGroup)
		throws Exception {

		return _postAccountGroup(adminAccountGroup);
	}

	private AdminAccountGroup _postAccountGroup(
			AdminAccountGroup adminAccountGroup)
		throws Exception {

		return adminAccountGroupResource.postAccountGroup(adminAccountGroup);
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}