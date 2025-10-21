/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.asset.library.client.dto.v1_0.UserAccount;
import com.liferay.headless.asset.library.client.pagination.Page;
import com.liferay.headless.asset.library.client.pagination.Pagination;
import com.liferay.headless.asset.library.client.resource.v1_0.UserAccountResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-32050")}
)
@RunWith(Arquillian.class)
public class UserAccountResourceTest extends BaseUserAccountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_testUser = UserTestUtil.addUser();
	}

	@Ignore
	@Override
	@Test
	public void testBatchEngineDeleteImportTask() {
	}

	@Override
	@Test
	public void testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode()
		throws Exception {

		super.
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode();

		_testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode(
			_getAssetLibraryMemberUserAccountResource(""));
		_testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode(
			_getCMSAdministratorUserAccountResource(""));
	}

	@Override
	@Test
	public void testGetAssetLibraryByExternalReferenceCodeUserAccountsPage()
		throws Exception {

		super.testGetAssetLibraryByExternalReferenceCodeUserAccountsPage();

		_testGetAssetLibraryByExternalReferenceCodeUserAccountsPage(
			_getAssetLibraryMemberUserAccountResource(""));
		_testGetAssetLibraryByExternalReferenceCodeUserAccountsPage(
			_getAssetLibraryMemberUserAccountResource("roles"));
		_testGetAssetLibraryByExternalReferenceCodeUserAccountsPage(
			_getCMSAdministratorUserAccountResource(""));
		_testGetAssetLibraryByExternalReferenceCodeUserAccountsPage(
			_getCMSAdministratorUserAccountResource("roles"));
		_testGetAssetLibraryByExternalReferenceCodeUserAccountsPageWithSortId();
	}

	@Override
	@Test
	public void testGetAssetLibraryUserAccount() throws Exception {
		super.testGetAssetLibraryUserAccount();

		_testGetAssetLibraryUserAccount(
			_getAssetLibraryMemberUserAccountResource(""));
		_testGetAssetLibraryUserAccount(
			_getCMSAdministratorUserAccountResource(""));
	}

	@Override
	@Test
	public void testGetAssetLibraryUserAccountsPage() throws Exception {
		super.testGetAssetLibraryUserAccountsPage();

		_testGetAssetLibraryUserAccountsPage(
			_getAssetLibraryMemberUserAccountResource(""));
		_testGetAssetLibraryUserAccountsPage(
			_getAssetLibraryMemberUserAccountResource("roles"));
		_testGetAssetLibraryUserAccountsPage(
			_getCMSAdministratorUserAccountResource(""));
		_testGetAssetLibraryUserAccountsPage(
			_getCMSAdministratorUserAccountResource("roles"));
		_testGetAssetLibraryUserAccountsPageWithSortId();
	}

	@Override
	protected Collection<EntityField> getEntityFields() {
		return List.of(
			new StringEntityField(
				"externalReferenceCode", locale -> "externalReferenceCode"),
			new StringEntityField("name", locale -> "name"),
			new IdEntityField("id", locale -> "id", locale -> "id"));
	}

	@Override
	protected UserAccount randomUserAccount() throws Exception {
		User user = UserTestUtil.addUser();

		return new UserAccount() {
			{
				externalReferenceCode = user.getExternalReferenceCode();
				id = user.getUserId();
				name = user.getFullName();
			}
		};
	}

	@Override
	protected UserAccount
			testDeleteAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_addUserAccount()
		throws Exception {

		return _addUserAccount();
	}

	@Override
	protected String
			testDeleteAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return _getGroupExternalReferenceCode();
	}

	@Override
	protected UserAccount testDeleteAssetLibraryUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount();
	}

	@Override
	protected Long testDeleteAssetLibraryUserAccount_getAssetLibraryId() {
		return testDepotEntry.getGroupId();
	}

	@Override
	protected UserAccount
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_addUserAccount()
		throws Exception {

		return _addUserAccount();
	}

	@Override
	protected String
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return _getGroupExternalReferenceCode();
	}

	@Override
	protected UserAccount
			testGetAssetLibraryByExternalReferenceCodeUserAccountsPage_addUserAccount(
				String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		return userAccountResource.putAssetLibraryUserAccount(
			testDepotEntry.getGroupId(), userAccount.getId());
	}

	@Override
	protected String
			testGetAssetLibraryByExternalReferenceCodeUserAccountsPage_getExternalReferenceCode()
		throws Exception {

		return _getGroupExternalReferenceCode();
	}

	@Override
	protected UserAccount testGetAssetLibraryUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount();
	}

	@Override
	protected Long testGetAssetLibraryUserAccount_getAssetLibraryId() {
		return testDepotEntry.getGroupId();
	}

	@Override
	protected UserAccount testGetAssetLibraryUserAccountsPage_addUserAccount(
			Long assetLibraryId, UserAccount userAccount)
		throws Exception {

		return userAccountResource.putAssetLibraryUserAccount(
			assetLibraryId, userAccount.getId());
	}

	@Override
	protected Long testGetAssetLibraryUserAccountsPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getGroupId();
	}

	@Override
	protected Long
			testGetAssetLibraryUserAccountsPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return irrelevantDepotEntry.getGroupId();
	}

	@Override
	protected UserAccount
			testPutAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_addUserAccount()
		throws Exception {

		return _addUserAccount();
	}

	@Override
	protected String
			testPutAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return _getGroupExternalReferenceCode();
	}

	@Override
	protected UserAccount testPutAssetLibraryUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount();
	}

	@Override
	protected Long testPutAssetLibraryUserAccount_getAssetLibraryId() {
		return testDepotEntry.getGroupId();
	}

	private UserAccount _addUserAccount() throws Exception {
		return userAccountResource.putAssetLibraryUserAccount(
			testDepotEntry.getGroupId(), _testUser.getUserId());
	}

	private UserAccountResource _getAssetLibraryMemberUserAccountResource(
			String nestedFields)
		throws Exception {

		String password = RandomTestUtil.randomString();

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {
				testGetAssetLibraryUserAccountsPage_getAssetLibraryId()
			},
			ServiceContextTestUtil.getServiceContext());

		return UserAccountResource.builder(
		).authentication(
			user.getEmailAddress(), password
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameter(
			"nestedFields", nestedFields
		).build();
	}

	private UserAccountResource _getCMSAdministratorUserAccountResource(
			String nestedFields)
		throws Exception {

		String password = RandomTestUtil.randomString();

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.CMS_ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), user);

		return UserAccountResource.builder(
		).authentication(
			user.getEmailAddress(), password
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameter(
			"nestedFields", nestedFields
		).build();
	}

	private String _getGroupExternalReferenceCode() throws Exception {
		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	private void
			_testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode(
				UserAccountResource userAccountResource)
		throws Exception {

		UserAccount postUserAccount =
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_addUserAccount();

		UserAccount getUserAccount =
			userAccountResource.
				getAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode(
					testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCode_getAssetLibraryExternalReferenceCode(),
					postUserAccount.getExternalReferenceCode());

		assertEquals(postUserAccount, getUserAccount);
		assertValid(getUserAccount);
	}

	private void _testGetAssetLibraryByExternalReferenceCodeUserAccountsPage(
			UserAccountResource userAccountResource)
		throws Exception {

		String externalReferenceCode =
			testGetAssetLibraryByExternalReferenceCodeUserAccountsPage_getExternalReferenceCode();

		Page<UserAccount> page =
			userAccountResource.
				getAssetLibraryByExternalReferenceCodeUserAccountsPage(
					externalReferenceCode, null, null, Pagination.of(1, 10),
					null);

		long totalCount = page.getTotalCount();

		UserAccount userAccount1 =
			testGetAssetLibraryByExternalReferenceCodeUserAccountsPage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		UserAccount userAccount2 =
			testGetAssetLibraryByExternalReferenceCodeUserAccountsPage_addUserAccount(
				externalReferenceCode, randomUserAccount());

		page =
			userAccountResource.
				getAssetLibraryByExternalReferenceCodeUserAccountsPage(
					externalReferenceCode, null, null, Pagination.of(1, 10),
					null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(userAccount1, (List<UserAccount>)page.getItems());
		assertContains(userAccount2, (List<UserAccount>)page.getItems());
		assertValid(
			page,
			testGetAssetLibraryByExternalReferenceCodeUserAccountsPage_getExpectedActions(
				externalReferenceCode));
	}

	private void _testGetAssetLibraryByExternalReferenceCodeUserAccountsPageWithSortId()
		throws Exception {

		testGetAssetLibraryByExternalReferenceCodeUserAccountsPageWithSort(
			EntityField.Type.ID,
			(entityField, userAccount1, userAccount2) -> {
			});
	}

	private void _testGetAssetLibraryUserAccount(
			UserAccountResource userAccountResource)
		throws Exception {

		UserAccount postUserAccount =
			testGetAssetLibraryUserAccount_addUserAccount();

		UserAccount getUserAccount =
			userAccountResource.getAssetLibraryUserAccount(
				testGetAssetLibraryUserAccount_getAssetLibraryId(),
				postUserAccount.getId());

		assertEquals(postUserAccount, getUserAccount);
		assertValid(getUserAccount);
	}

	private void _testGetAssetLibraryUserAccountsPage(
			UserAccountResource userAccountResource)
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryUserAccountsPage_getAssetLibraryId();

		Page<UserAccount> page =
			userAccountResource.getAssetLibraryUserAccountsPage(
				assetLibraryId, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		UserAccount userAccount1 =
			testGetAssetLibraryUserAccountsPage_addUserAccount(
				assetLibraryId, randomUserAccount());

		UserAccount userAccount2 =
			testGetAssetLibraryUserAccountsPage_addUserAccount(
				assetLibraryId, randomUserAccount());

		page = userAccountResource.getAssetLibraryUserAccountsPage(
			assetLibraryId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(userAccount1, (List<UserAccount>)page.getItems());
		assertContains(userAccount2, (List<UserAccount>)page.getItems());
		assertValid(
			page,
			testGetAssetLibraryUserAccountsPage_getExpectedActions(
				assetLibraryId));
	}

	private void _testGetAssetLibraryUserAccountsPageWithSortId()
		throws Exception {

		testGetAssetLibraryUserAccountsPageWithSort(
			EntityField.Type.ID,
			(entityField, userAccount1, userAccount2) -> {
			});
	}

	@Inject
	private RoleLocalService _roleLocalService;

	private User _testUser;

	@Inject
	private UserLocalService _userLocalService;

}