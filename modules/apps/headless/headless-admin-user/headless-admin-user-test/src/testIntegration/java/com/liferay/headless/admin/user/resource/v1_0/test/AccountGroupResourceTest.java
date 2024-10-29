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
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountGroup;
import com.liferay.headless.admin.user.client.dto.v1_0.CustomField;
import com.liferay.headless.admin.user.client.dto.v1_0.CustomValue;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.problem.Problem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
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

	@Override
	@Test
	public void testGetAccountGroupsPage() throws Exception {
		super.testGetAccountGroupsPage();

		_testGetAccountGroupsPageWithCustomFields();
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

	@Override
	@Test
	public void testPatchAccountGroup() throws Exception {
		super.testPatchAccountGroup();

		_testPatchAccountGroupWithoutName();
	}

	@Override
	@Test
	public void testPatchAccountGroupByExternalReferenceCode()
		throws Exception {

		super.testPatchAccountGroupByExternalReferenceCode();

		_testPatchAccountGroupByExternalReferenceCodeWithoutName();
	}

	@Ignore
	@Override
	@Test
	public void testPostAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode()
		throws Exception {
	}

	@Override
	@Test
	public void testPutAccountGroup() throws Exception {
		super.testPutAccountGroup();

		_testPutAccountGroupWithoutName();
	}

	@Override
	@Test
	public void testPutAccountGroupByExternalReferenceCode() throws Exception {
		super.testPutAccountGroupByExternalReferenceCode();

		_testPutAccountGroupByExternalReferenceWithoutName();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "externalReferenceCode", "name"};
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

	private void _testGetAccountGroupsPageWithCustomFields() throws Exception {
		ExpandoTable expandoTable = _expandoTableLocalService.addTable(
			testGroup.getCompanyId(),
			_classNameLocalService.getClassNameId(
				com.liferay.account.model.AccountGroup.class),
			"CUSTOM_FIELDS");

		ExpandoColumn expandoColumn = _expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), "A" + RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE,
			String.valueOf(ExpandoColumnConstants.INDEX_TYPE_KEYWORD));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		_expandoColumnLocalService.updateExpandoColumn(expandoColumn);

		AccountGroup accountGroup = randomAccountGroup();

		String value = RandomTestUtil.randomString();

		accountGroup.setCustomFields(
			() -> new CustomField[] {
				new CustomField() {
					{
						customValue = new CustomValue() {
							{
								data = value;
							}
						};
						dataType = "Text";
						name = expandoColumn.getName();
					}
				}
			});

		accountGroup = testGetAccountGroupsPage_addAccountGroup(accountGroup);

		Page<AccountGroup> page = accountGroupResource.getAccountGroupsPage(
			null,
			StringBundler.concat(
				"(customFields/", expandoColumn.getName(), " eq '",
				RandomTestUtil.randomString(), "')"),
			Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		page = accountGroupResource.getAccountGroupsPage(
			null,
			StringBundler.concat(
				"(customFields/", expandoColumn.getName(), " eq '", value,
				"')"),
			Pagination.of(1, 2), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Collections.singletonList(accountGroup),
			(List<AccountGroup>)page.getItems());
	}

	private void _testPatchAccountGroupByExternalReferenceCodeWithoutName()
		throws Exception {

		AccountGroup postAccountGroup = _postAccountGroup(randomAccountGroup());

		AccountGroup randomPatchAccountGroup = randomPatchAccountGroup();

		randomPatchAccountGroup.setName(() -> null);

		AccountGroup patchAccountGroup =
			accountGroupResource.patchAccountGroupByExternalReferenceCode(
				postAccountGroup.getExternalReferenceCode(),
				randomPatchAccountGroup);

		AccountGroup expectedPatchAccountGroup = postAccountGroup.clone();

		BeanTestUtil.copyProperties(
			randomPatchAccountGroup, expectedPatchAccountGroup);

		expectedPatchAccountGroup.setName(postAccountGroup.getName());

		AccountGroup getAccountGroup = accountGroupResource.getAccountGroup(
			patchAccountGroup.getId());

		assertEquals(expectedPatchAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);
	}

	private void _testPatchAccountGroupWithoutName() throws Exception {
		AccountGroup postAccountGroup = _postAccountGroup(randomAccountGroup());

		AccountGroup randomPatchAccountGroup = randomPatchAccountGroup();

		randomPatchAccountGroup.setName(() -> null);

		AccountGroup patchAccountGroup = accountGroupResource.patchAccountGroup(
			postAccountGroup.getId(), randomPatchAccountGroup);

		AccountGroup expectedPatchAccountGroup = postAccountGroup.clone();

		BeanTestUtil.copyProperties(
			randomPatchAccountGroup, expectedPatchAccountGroup);

		expectedPatchAccountGroup.setName(postAccountGroup.getName());

		AccountGroup getAccountGroup = accountGroupResource.getAccountGroup(
			patchAccountGroup.getId());

		assertEquals(expectedPatchAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);
	}

	private void _testPutAccountGroupByExternalReferenceWithoutName()
		throws Exception {

		AccountGroup postAccountGroup = _postAccountGroup(randomAccountGroup());

		AccountGroup randomAccountGroup = randomAccountGroup();

		randomAccountGroup.setName(() -> null);

		try {
			accountGroupResource.putAccountGroupByExternalReferenceCode(
				postAccountGroup.getExternalReferenceCode(),
				randomAccountGroup);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(
				"The account group name is invalid", problem.getTitle());
		}
	}

	private void _testPutAccountGroupWithoutName() throws Exception {
		AccountGroup postAccountGroup = _postAccountGroup(randomAccountGroup());

		AccountGroup randomAccountGroup = randomAccountGroup();

		randomAccountGroup.setName(() -> null);

		try {
			accountGroupResource.putAccountGroup(
				postAccountGroup.getId(), randomAccountGroup);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(
				"The account group name is invalid", problem.getTitle());
		}
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	private ServiceContext _serviceContext;

}