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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class AccountGroupResourceTest extends BaseAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_serviceContext = ServiceContextTestUtil.getServiceContext();

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_serviceContext.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
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
	public void testGraphQLGetAccountGroupsPage() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testPostAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode()
		throws Exception {
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
	protected AccountGroup
			testDeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected String
			testDeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_getAccountExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected AccountGroup testGetAccountAccountGroupsPage_addAccountGroup(
			Long accountId, AccountGroup accountGroup)
		throws Exception {

		AccountGroup randomAccountGroup = _postAccountGroup(accountGroup);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			randomAccountGroup.getId(), AccountEntry.class.getName(),
			accountId);

		return randomAccountGroup;
	}

	@Override
	protected Long testGetAccountAccountGroupsPage_getAccountId()
		throws Exception {

		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected AccountGroup
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
				String accountExternalReferenceCode, AccountGroup accountGroup)
		throws Exception {

		AccountGroup randomAccountGroup = _postAccountGroup(accountGroup);

		AccountGroupRelLocalServiceUtil.addAccountGroupRel(
			randomAccountGroup.getId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId());

		return randomAccountGroup;
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getAccountExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
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
	protected AccountGroup testPatchAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup
			testPatchAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup testPostAccountGroup_addAccountGroup(
			AccountGroup accountGroup)
		throws Exception {

		return _postAccountGroup(accountGroup);
	}

	@Override
	protected AccountGroup
			testPostAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup testPutAccountGroup_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	@Override
	protected AccountGroup
			testPutAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return _postAccountGroup(randomAccountGroup());
	}

	private AccountGroup _postAccountGroup(AccountGroup accountGroup)
		throws Exception {

		return accountGroupResource.postAccountGroup(accountGroup);
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	private ServiceContext _serviceContext;

}