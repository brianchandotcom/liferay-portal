/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccountContactInformation;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.problem.Problem;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.UserAccountSerDes;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class UserAccountResourceTest extends BaseUserAccountResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_organization = OrganizationTestUtil.addOrganization();

		_testUser = _userLocalService.getUserByEmailAddress(
			testGroup.getCompanyId(), "test@liferay.com");

		_userLocalService.deleteGroupUser(
			testGroup.getGroupId(), _testUser.getUserId());

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			_testUser.getModelClassName());

		indexer.reindex(_testUser);

		_accountEntry = _getAccountEntry();
	}

	@Override
	@Test
	public void testDeleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode()
		throws Exception {

		UserAccount userAccount =
			userAccountResource.putUserAccountByExternalReferenceCode(
				StringUtil.toLowerCase(RandomTestUtil.randomString()),
				randomUserAccount());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), userAccount.getId());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), userAccount.getId()));

		userAccountResource.
			deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeHttpResponse(
				_accountEntry.getExternalReferenceCode(),
				userAccount.getExternalReferenceCode());

		Assert.assertNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), userAccount.getId()));
	}

	@Override
	@Test
	public void testDeleteAccountUserAccountByEmailAddress() throws Exception {
		User user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));

		userAccountResource.deleteAccountUserAccountByEmailAddress(
			_accountEntry.getAccountEntryId(), user.getEmailAddress());

		Assert.assertNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));
	}

	@Override
	@Test
	public void testDeleteAccountUserAccountByExternalReferenceCodeByEmailAddress()
		throws Exception {

		User user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), user.getUserId());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));

		userAccountResource.
			deleteAccountUserAccountByExternalReferenceCodeByEmailAddress(
				_accountEntry.getExternalReferenceCode(),
				user.getEmailAddress());

		Assert.assertNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));
	}

	@Override
	@Test
	public void testDeleteAccountUserAccountsByEmailAddress() throws Exception {
		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			_accountEntry.getAccountEntryId(), _toUserIds(users));

		for (User user : users) {
			Assert.assertNotNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}

		List<User> removeUsers = users.subList(0, 2);

		userAccountResource.deleteAccountUserAccountsByEmailAddress(
			_accountEntry.getAccountEntryId(), _toEmailAddresses(removeUsers));

		for (User user : removeUsers) {
			Assert.assertNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}

		List<User> keepUsers = users.subList(2, 4);

		for (User user : keepUsers) {
			Assert.assertNotNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}
	}

	@Override
	@Test
	public void testDeleteAccountUserAccountsByExternalReferenceCodeByEmailAddress()
		throws Exception {

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			_accountEntry.getAccountEntryId(), _toUserIds(users));

		for (User user : users) {
			Assert.assertNotNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}

		List<User> removeUsers = users.subList(0, 2);

		userAccountResource.
			deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress(
				_accountEntry.getExternalReferenceCode(),
				_toEmailAddresses(removeUsers));

		for (User user : removeUsers) {
			Assert.assertNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}

		List<User> keepUsers = users.subList(2, 4);

		for (User user : keepUsers) {
			Assert.assertNotNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}
	}

	@Override
	@Test
	public void testGetSiteUserAccountsPage() throws Exception {
		Page<UserAccount> page = userAccountResource.getSiteUserAccountsPage(
			testGetSiteUserAccountsPage_getSiteId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteUserAccountsPage_getSiteId();

		UserAccount userAccount1 = testGetSiteUserAccountsPage_addUserAccount(
			siteId, randomUserAccount());
		UserAccount userAccount2 = testGetSiteUserAccountsPage_addUserAccount(
			siteId, randomUserAccount());

		page = userAccountResource.getSiteUserAccountsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetUserAccountsPage() throws Exception {
		UserAccount userAccount1 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount2 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount3 = userAccountResource.getUserAccount(
			_testUser.getUserId());

		String idFilterString = String.format(
			"id in ('%s','%s','%s')", userAccount1.getId(),
			userAccount2.getId(), userAccount3.getId());

		_testGetUserAccountsPage(
			idFilterString, userAccount1, userAccount2, userAccount3);

		_userLocalService.updateLastLogin(userAccount2.getId(), null);
		_userLocalService.updateLastLogin(userAccount3.getId(), null);

		_testGetUserAccountsPage(
			String.format(
				"%s and %s", idFilterString,
				"lastLoginDate gt 1900-01-01T01:01:28Z"),
			userAccount2, userAccount3);
		_testGetUserAccountsPage(
			String.format("%s and %s", idFilterString, "lastLoginDate ne null"),
			userAccount2, userAccount3);
		_testGetUserAccountsPage(
			String.format(
				"%s and %s", idFilterString,
				"not (lastLoginDate gt 1900-01-01T01:01:28Z)"),
			userAccount1);
		_testGetUserAccountsPage(
			String.format("%s and %s", idFilterString, "lastLoginDate eq null"),
			userAccount1);

		_testGetUserAccountsPage(
			String.format("name eq '%s'", userAccount1.getName()),
			userAccount1);

		String familyName = RandomTestUtil.randomString();

		UserAccount userAccount4 = randomUserAccount();

		userAccount4.setFamilyName(familyName);

		userAccount4 = testGetUserAccountsPage_addUserAccount(userAccount4);

		UserAccount userAccount5 = randomUserAccount();

		userAccount5.setFamilyName(familyName);

		userAccount5 = testGetUserAccountsPage_addUserAccount(userAccount5);

		_testGetUserAccountsPage(
			String.format("contains(name, '%s')", familyName), userAccount4,
			userAccount5);

		String roleName = "Test role " + RandomTestUtil.randomString();

		Role role = RoleTestUtil.addRole(roleName, RoleConstants.TYPE_REGULAR);

		_userLocalService.addRoleUser(role.getRoleId(), userAccount1.getId());

		_testGetUserAccountsPage(
			String.format("roleNames/any(f:f eq '%s')", roleName),
			userAccount1);
		_testGetUserAccountsPage(
			"roleNames/any(f:contains(f, 'Test role '))", userAccount1);
		_testGetUserAccountsPage("roleNames/any(f:f eq 'Test Role')");

		String groupRoleName =
			"Test group role " + RandomTestUtil.randomString();

		Role groupRole = RoleTestUtil.addRole(
			groupRoleName, RoleConstants.TYPE_SITE);

		UserGroupRoleLocalServiceUtil.addUserGroupRole(
			userAccount2.getId(), TestPropsValues.getGroupId(),
			groupRole.getRoleId());

		_testGetUserAccountsPage(
			String.format("userGroupRoleNames/any(f:f eq '%s')", groupRoleName),
			userAccount2);
		_testGetUserAccountsPage(
			"userGroupRoleNames/any(f:contains(f, 'Test group role '))",
			userAccount2);
		_testGetUserAccountsPage("userGroupRoleNames/any(f:f eq 'Test Role')");
	}

	@Override
	@Test
	public void testGetUserAccountsPageWithPagination() throws Exception {
		UserAccount userAccount1 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount2 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount3 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount4 = userAccountResource.getUserAccount(
			_testUser.getUserId());

		Page<UserAccount> page1 = userAccountResource.getUserAccountsPage(
			null, null, Pagination.of(1, 2), null);

		List<UserAccount> userAccounts1 = (List<UserAccount>)page1.getItems();

		Assert.assertEquals(userAccounts1.toString(), 2, userAccounts1.size());

		Page<UserAccount> page2 = userAccountResource.getUserAccountsPage(
			null, null, Pagination.of(1, 4), null);

		Assert.assertEquals(4, page2.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				userAccount1, userAccount2, userAccount3, userAccount4),
			(List<UserAccount>)page2.getItems());
	}

	@Override
	public void testGetUserAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, UserAccount, UserAccount, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, userAccount1, userAccount2);
		}

		userAccount1 = testGetUserAccountsPage_addUserAccount(userAccount1);
		userAccount2 = testGetUserAccountsPage_addUserAccount(userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> descPage =
				userAccountResource.getUserAccountsPage(
					null, String.format("id ne '%s'", _testUser.getUserId()),
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	@Override
	@Test
	public void testGraphQLGetMyUserAccount() throws Exception {
		Assert.assertTrue(
			equals(
				userAccountResource.getUserAccount(_testUser.getUserId()),
				UserAccountSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"myUserAccount", getGraphQLFields())),
						"JSONObject/data", "JSONObject/myUserAccount"))));
	}

	@Override
	@Test
	public void testGraphQLGetUserAccountsPage() throws Exception {
		UserAccount userAccount1 = testGraphQLUserAccount_addUserAccount();
		UserAccount userAccount2 = testGraphQLUserAccount_addUserAccount();
		UserAccount userAccount3 = userAccountResource.getUserAccount(
			_testUser.getUserId());

		JSONObject userAccountsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(
				new GraphQLField(
					"userAccounts",
					HashMapBuilder.<String, Object>put(
						"page", 1
					).put(
						"pageSize", 3
					).build(),
					new GraphQLField("items", getGraphQLFields()),
					new GraphQLField("page"), new GraphQLField("totalCount"))),
			"JSONObject/data", "JSONObject/userAccounts");

		Assert.assertEquals(3, userAccountsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			Arrays.asList(
				UserAccountSerDes.toDTOs(
					userAccountsJSONObject.getString("items"))));
	}

	@Override
	@Test
	public void testPatchUserAccount() throws Exception {
		super.testPatchUserAccount();

		User user = UserTestUtil.addUser();

		long portraitId = RandomTestUtil.randomLong();

		user.setPortraitId(portraitId);

		user = _userLocalService.updateUser(user);

		UserAccount userAccount = new UserAccount();

		userAccount.setJobTitle(RandomTestUtil.randomString());

		userAccount = userAccountResource.patchUserAccount(
			user.getUserId(), userAccount);

		user = _userLocalService.getUser(userAccount.getId());

		Assert.assertEquals(portraitId, user.getPortraitId());

		String newPassword = RandomTestUtil.randomString();
		UserAccount patchUserAccount = testPatchUserAccount_addUserAccount();

		_assertAuthenticationResult(
			Authenticator.FAILURE, patchUserAccount.getEmailAddress(),
			newPassword);

		userAccountResource.patchUserAccount(
			patchUserAccount.getId(),
			new UserAccount() {
				{
					password = newPassword;
				}
			});

		_assertAuthenticationResult(
			Authenticator.SUCCESS, patchUserAccount.getEmailAddress(),
			newPassword);

		_setUpTestUserAccountResource();

		_assertProblem(
			UserPasswordException.MustMatchCurrentPassword.class,
			() -> _regularUserAccountResource.patchUserAccountHttpResponse(
				_regularUserAccount.getId(),
				new UserAccount() {
					{
						password = newPassword;
					}
				}));

		_assertAuthenticationResult(
			Authenticator.FAILURE, _regularUserAccount.getEmailAddress(),
			newPassword);

		_regularUserAccountResource.patchUserAccount(
			_regularUserAccount.getId(),
			new UserAccount() {
				{
					currentPassword = _regularUserAccountCurrentPassword;
					password = newPassword;
				}
			});

		_assertAuthenticationResult(
			Authenticator.SUCCESS, _regularUserAccount.getEmailAddress(),
			newPassword);

		_setUpTestUserAccountResource();

		_regularUserAccountResource.patchUserAccount(
			_regularUserAccount.getId(),
			new UserAccount() {
				{
					givenName = RandomTestUtil.randomString();
				}
			});
	}

	@Override
	@Test
	public void testPostAccountByExternalReferenceCodeUserAccountByExternalReferenceCode()
		throws Exception {

		UserAccount userAccount =
			userAccountResource.putUserAccountByExternalReferenceCode(
				StringUtil.toLowerCase(RandomTestUtil.randomString()),
				randomUserAccount());

		Assert.assertNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), userAccount.getId()));

		userAccountResource.
			postAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeHttpResponse(
				_accountEntry.getExternalReferenceCode(),
				userAccount.getExternalReferenceCode());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), userAccount.getId()));
	}

	@Override
	@Test
	public void testPostAccountUserAccount() throws Exception {
		super.testPostAccountUserAccount();

		UserAccount randomUserAccount = randomUserAccount();

		Assert.assertNull(
			_userLocalService.fetchUserByEmailAddress(
				TestPropsValues.getCompanyId(),
				randomUserAccount.getEmailAddress()));

		randomUserAccount = testPostAccountUserAccount_addUserAccount(
			randomUserAccount);

		Assert.assertNotNull(
			_userLocalService.fetchUserByEmailAddress(
				TestPropsValues.getCompanyId(),
				randomUserAccount.getEmailAddress()));

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_getAccountEntryId(), randomUserAccount.getId()));
	}

	@Override
	@Test
	public void testPostAccountUserAccountByEmailAddress() throws Exception {
		User user = UserTestUtil.addUser();

		Assert.assertNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));

		userAccountResource.postAccountUserAccountByEmailAddress(
			_accountEntry.getAccountEntryId(), user.getEmailAddress());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));
	}

	@Override
	@Test
	public void testPostAccountUserAccountByExternalReferenceCodeByEmailAddress()
		throws Exception {

		User user = UserTestUtil.addUser();

		Assert.assertNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));

		userAccountResource.
			postAccountUserAccountByExternalReferenceCodeByEmailAddress(
				_accountEntry.getExternalReferenceCode(),
				user.getEmailAddress());

		Assert.assertNotNull(
			_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), user.getUserId()));
	}

	@Override
	@Test
	public void testPostAccountUserAccountsByEmailAddress() throws Exception {
		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		for (User user : users) {
			Assert.assertNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}

		userAccountResource.postAccountUserAccountsByEmailAddress(
			_accountEntry.getAccountEntryId(), null, _toEmailAddresses(users));

		for (User user : users) {
			Assert.assertNotNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}
	}

	@Override
	@Test
	public void testPostAccountUserAccountsByExternalReferenceCodeByEmailAddress()
		throws Exception {

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		for (User user : users) {
			Assert.assertNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}

		userAccountResource.
			postAccountUserAccountsByExternalReferenceCodeByEmailAddress(
				_accountEntry.getExternalReferenceCode(),
				_toEmailAddresses(users));

		for (User user : users) {
			Assert.assertNotNull(
				_accountEntryUserRelLocalService.fetchAccountEntryUserRel(
					_accountEntry.getAccountEntryId(), user.getUserId()));
		}
	}

	@Override
	@Test
	public void testPostUserAccount() throws Exception {
		UserAccount userAccount = randomUserAccount();

		String password = RandomTestUtil.randomString();

		userAccount.setPassword(password);

		UserAccount postUserAccount = userAccountResource.postUserAccount(
			userAccount);

		assertEquals(userAccount, postUserAccount);
		assertValid(postUserAccount);

		_assertAuthenticationResult(
			Authenticator.SUCCESS, postUserAccount.getEmailAddress(), password);

		SAPEntry sapEntry = _sapEntryLocalService.addSAPEntry(
			TestPropsValues.getUserId(),
			"com.liferay.headless.admin.user.internal.resource.v1_0." +
				"UserAccountResourceImpl#postUserAccount",
			true, true, "Guest",
			HashMapBuilder.put(
				LocaleUtil.getDefault(), "Guest"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_testPostUserAccount(new TestSimpleCaptchaImpl(Assert::fail), false);
		_testPostUserAccount(
			new TestSimpleCaptchaImpl(
				() -> {
				}),
			true);

		try {
			_testPostUserAccount(
				new TestSimpleCaptchaImpl(
					() -> {
						throw new CaptchaException();
					}),
				true);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(
				CaptchaException.class.getName(), problem.getType());
		}

		_sapEntryLocalService.deleteSAPEntry(sapEntry);
	}

	@Override
	@Test
	public void testPutUserAccount() throws Exception {
		super.testPutUserAccount();

		String newPassword = RandomTestUtil.randomString();
		UserAccount putUserAccount = testPutUserAccount_addUserAccount();

		_assertAuthenticationResult(
			Authenticator.FAILURE, putUserAccount.getEmailAddress(),
			newPassword);

		putUserAccount.setPassword(newPassword);

		userAccountResource.putUserAccount(
			putUserAccount.getId(), putUserAccount);

		_assertAuthenticationResult(
			Authenticator.SUCCESS, putUserAccount.getEmailAddress(),
			newPassword);

		_setUpTestUserAccountResource();

		_regularUserAccount.setPassword(newPassword);

		_assertProblem(
			UserPasswordException.MustMatchCurrentPassword.class,
			() -> _regularUserAccountResource.putUserAccountHttpResponse(
				_regularUserAccount.getId(), _regularUserAccount));

		_assertAuthenticationResult(
			Authenticator.FAILURE, _regularUserAccount.getEmailAddress(),
			newPassword);

		_regularUserAccount.setCurrentPassword(
			_regularUserAccountCurrentPassword);

		_regularUserAccountResource.putUserAccount(
			_regularUserAccount.getId(), _regularUserAccount);

		_assertAuthenticationResult(
			Authenticator.SUCCESS, _regularUserAccount.getEmailAddress(),
			newPassword);

		_setUpTestUserAccountResource();

		_regularUserAccountResource.putUserAccount(
			_regularUserAccount.getId(),
			_randomUserAccount(
				userAccount -> {
					userAccount.setCurrentPassword(() -> null);
					userAccount.setPassword(() -> null);
				}));
	}

	@Override
	@Test
	public void testPutUserAccountByExternalReferenceCode() throws Exception {
		super.testPutUserAccountByExternalReferenceCode();

		String newPassword = RandomTestUtil.randomString();
		UserAccount putUserAccount =
			testPutUserAccountByExternalReferenceCode_addUserAccount();

		_assertAuthenticationResult(
			Authenticator.FAILURE, putUserAccount.getEmailAddress(),
			newPassword);

		putUserAccount.setPassword(newPassword);

		userAccountResource.putUserAccountByExternalReferenceCode(
			putUserAccount.getExternalReferenceCode(), putUserAccount);

		_assertAuthenticationResult(
			Authenticator.SUCCESS, putUserAccount.getEmailAddress(),
			newPassword);

		_setUpTestUserAccountResource();

		_regularUserAccount.setPassword(newPassword);

		_assertProblem(
			UserPasswordException.MustMatchCurrentPassword.class,
			() ->
				_regularUserAccountResource.
					putUserAccountByExternalReferenceCodeHttpResponse(
						_regularUserAccount.getExternalReferenceCode(),
						_regularUserAccount));

		_assertAuthenticationResult(
			Authenticator.FAILURE, _regularUserAccount.getEmailAddress(),
			newPassword);

		_regularUserAccount.setCurrentPassword(
			_regularUserAccountCurrentPassword);

		_regularUserAccountResource.putUserAccountByExternalReferenceCode(
			_regularUserAccount.getExternalReferenceCode(),
			_regularUserAccount);

		_assertAuthenticationResult(
			Authenticator.SUCCESS, _regularUserAccount.getEmailAddress(),
			newPassword);

		_setUpTestUserAccountResource();

		_regularUserAccountResource.putUserAccountByExternalReferenceCode(
			_regularUserAccount.getExternalReferenceCode(),
			_randomUserAccount(
				userAccount -> {
					userAccount.setCurrentPassword(() -> null);
					userAccount.setPassword(() -> null);
				}));
	}

	@Override
	protected void assertEquals(
		UserAccount userAccount1, UserAccount userAccount2) {

		super.assertEquals(userAccount1, userAccount2);

		UserAccountContactInformation userAccountContactInformation1 =
			userAccount1.getUserAccountContactInformation();
		UserAccountContactInformation userAccountContactInformation2 =
			userAccount2.getUserAccountContactInformation();

		Assert.assertEquals(
			StringUtil.lowerCase(userAccountContactInformation1.getFacebook()),
			StringUtil.lowerCase(userAccountContactInformation2.getFacebook()));
		Assert.assertEquals(
			StringUtil.lowerCase(userAccountContactInformation1.getJabber()),
			StringUtil.lowerCase(userAccountContactInformation2.getJabber()));
		Assert.assertEquals(
			StringUtil.lowerCase(userAccountContactInformation1.getSkype()),
			StringUtil.lowerCase(userAccountContactInformation2.getSkype()));
		Assert.assertEquals(
			StringUtil.lowerCase(userAccountContactInformation1.getSms()),
			StringUtil.lowerCase(userAccountContactInformation2.getSms()));
		Assert.assertEquals(
			StringUtil.lowerCase(userAccountContactInformation1.getTwitter()),
			StringUtil.lowerCase(userAccountContactInformation2.getTwitter()));

		_assertUserAccountContactInformation(
			userAccountContactInformation1, userAccountContactInformation2,
			"emailAddresses", "emailAddress");
		_assertUserAccountContactInformation(
			userAccountContactInformation1, userAccountContactInformation2,
			"postalAddresses", "streetAddressLine1");
		_assertUserAccountContactInformation(
			userAccountContactInformation1, userAccountContactInformation2,
			"telephones", "phoneNumber");
		_assertUserAccountContactInformation(
			userAccountContactInformation1, userAccountContactInformation2,
			"webUrls", "url");
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"alternateName", "familyName", "givenName"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"alternateName", "emailAddress", "lastLoginDate", "name"
		};
	}

	@Override
	protected UserAccount randomUserAccount() throws Exception {
		UserAccount userAccount = super.randomUserAccount();

		userAccount.setBirthDate(
			() -> {
				Calendar calendar = CalendarFactoryUtil.getCalendar();

				calendar.setTime(RandomTestUtil.nextDate());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);

				return calendar.getTime();
			});
		userAccount.setUserAccountContactInformation(
			_randomUserAccountContactInformation());

		return userAccount;
	}

	@Override
	protected UserAccount
			testDeleteAccountUserAccountByEmailAddress_addUserAccount()
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
	}

	@Override
	protected UserAccount
			testDeleteAccountUserAccountByExternalReferenceCodeByEmailAddress_addUserAccount()
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
	}

	@Override
	protected UserAccount
			testDeleteAccountUserAccountsByEmailAddress_addUserAccount()
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
	}

	@Override
	protected UserAccount testDeleteUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
	}

	@Override
	protected UserAccount
			testDeleteUserAccountByExternalReferenceCode_addUserAccount()
		throws Exception {

		return userAccountResource.putUserAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomUserAccount());
	}

	@Override
	protected UserAccount
			testGetAccountUserAccountsByExternalReferenceCodePage_addUserAccount(
				String externalReferenceCode, UserAccount userAccount)
		throws Exception {

		return userAccountResource.
			postAccountUserAccountByExternalReferenceCode(
				externalReferenceCode, userAccount);
	}

	@Override
	protected String
		testGetAccountUserAccountsByExternalReferenceCodePage_getExternalReferenceCode() {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected UserAccount testGetAccountUserAccountsPage_addUserAccount(
			Long accountId, UserAccount userAccount)
		throws Exception {

		return userAccountResource.postAccountUserAccount(
			accountId, userAccount);
	}

	@Override
	protected Long testGetAccountUserAccountsPage_getAccountId() {
		return _getAccountEntryId();
	}

	@Override
	protected UserAccount testGetMyUserAccount_addUserAccount()
		throws Exception {

		return userAccountResource.getUserAccount(_testUser.getUserId());
	}

	@Override
	protected UserAccount testGetOrganizationUserAccountsPage_addUserAccount(
			String organizationId, UserAccount userAccount)
		throws Exception {

		userAccount = _addUserAccount(
			testGetSiteUserAccountsPage_getSiteId(), userAccount);

		_userLocalService.addOrganizationUser(
			GetterUtil.getLong(organizationId), userAccount.getId());

		return userAccount;
	}

	@Override
	protected String testGetOrganizationUserAccountsPage_getOrganizationId() {
		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected UserAccount testGetSiteUserAccountsPage_addUserAccount(
			Long siteId, UserAccount userAccount)
		throws Exception {

		return _addUserAccount(siteId, userAccount);
	}

	@Override
	protected Long testGetSiteUserAccountsPage_getSiteId() {
		return testGroup.getGroupId();
	}

	@Override
	protected UserAccount testGetUserAccount_addUserAccount() throws Exception {
		return _addUserAccount(
			testGetSiteUserAccountsPage_getSiteId(), randomUserAccount());
	}

	@Override
	protected UserAccount
			testGetUserAccountByExternalReferenceCode_addUserAccount()
		throws Exception {

		return userAccountResource.putUserAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomUserAccount());
	}

	@Override
	protected UserAccount testGetUserAccountsPage_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), userAccount);
	}

	@Override
	protected UserAccount testGraphQLUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount(
			testGetSiteUserAccountsPage_getSiteId(), randomUserAccount());
	}

	@Override
	protected UserAccount testPatchUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
	}

	@Override
	protected UserAccount testPostAccountUserAccount_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addAccountUserAccount(_getAccountEntryId(), userAccount);
	}

	@Override
	protected UserAccount
			testPostAccountUserAccountByExternalReferenceCode_addUserAccount(
				UserAccount userAccount)
		throws Exception {

		return userAccountResource.
			postAccountUserAccountByExternalReferenceCode(
				_accountEntry.getExternalReferenceCode(), userAccount);
	}

	@Override
	protected UserAccount testPostUserAccount_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), userAccount);
	}

	@Override
	protected UserAccount testPutUserAccount_addUserAccount() throws Exception {
		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
	}

	@Override
	protected UserAccount
			testPutUserAccountByExternalReferenceCode_addUserAccount()
		throws Exception {

		return userAccountResource.putUserAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomUserAccount());
	}

	private UserAccount _addAccountUserAccount(
			Long accountId, UserAccount userAccount)
		throws Exception {

		return userAccountResource.postAccountUserAccount(
			accountId, userAccount);
	}

	private UserAccount _addUserAccount(long siteId, UserAccount userAccount)
		throws Exception {

		userAccount = userAccountResource.postUserAccount(userAccount);

		_userLocalService.addGroupUser(siteId, userAccount.getId());

		return userAccount;
	}

	private void _assertAuthenticationResult(
			int authenticatorResult, String emailAddress, String password)
		throws Exception {

		Assert.assertEquals(
			authenticatorResult,
			_userLocalService.authenticateByEmailAddress(
				testCompany.getCompanyId(), emailAddress, password,
				Collections.emptyMap(), Collections.emptyMap(),
				new HashMap<>()));
	}

	private <T extends Exception> void _assertProblem(
			Class<T> exceptionClass,
			UnsafeSupplier<HttpInvoker.HttpResponse, Exception>
				httpResponseUnsafeSupplier)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				BaseExceptionMapper.class.getName(), LoggerTestUtil.OFF)) {

			HttpInvoker.HttpResponse httpResponse =
				httpResponseUnsafeSupplier.get();

			Assert.assertEquals(
				Response.Status.BAD_REQUEST.getStatusCode(),
				httpResponse.getStatusCode());

			if (exceptionClass != null) {
				JSONObject jsonObject = _jsonFactory.createJSONObject(
					httpResponse.getContent());

				Assert.assertEquals(
					exceptionClass.getSimpleName(), jsonObject.get("type"));
			}
		}
	}

	private void _assertUserAccountContactInformation(
		UserAccountContactInformation userAccountContactInformation1,
		UserAccountContactInformation userAccountContactInformation2,
		String fieldName, String subfieldName) {

		try {
			Object[] objects1 = ReflectionTestUtil.getFieldValue(
				userAccountContactInformation1, fieldName);
			Object[] objects2 = ReflectionTestUtil.getFieldValue(
				userAccountContactInformation2, fieldName);

			Assert.assertEquals(
				Arrays.toString(objects1), objects1.length, objects2.length);

			Comparator<Object> comparator = Comparator.comparing(
				object -> ReflectionTestUtil.getFieldValue(
					object, subfieldName));

			Arrays.sort(objects1, comparator);
			Arrays.sort(objects2, comparator);

			for (int i = 0; i < objects1.length; i++) {
				Assert.assertEquals(
					(String)ReflectionTestUtil.getFieldValue(
						objects1[i], subfieldName),
					(String)ReflectionTestUtil.getFieldValue(
						objects2[i], subfieldName));
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private AccountEntry _getAccountEntry() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		accountEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		_accountEntryLocalService.updateAccountEntry(accountEntry);

		return accountEntry;
	}

	private Long _getAccountEntryId() {
		return _accountEntry.getAccountEntryId();
	}

	private EmailAddress _randomEmailAddress() throws Exception {
		return new EmailAddress() {
			{
				setEmailAddress(RandomTestUtil.randomString() + "@liferay.com");
				setPrimary(true);
				setType("email-address");
			}
		};
	}

	private Phone _randomPhone() throws Exception {
		return new Phone() {
			{
				setExtension(String.valueOf(RandomTestUtil.randomInt()));
				setPhoneNumber(String.valueOf(RandomTestUtil.randomInt()));
				setPhoneType("personal");
				setPrimary(true);
			}
		};
	}

	private PostalAddress _randomPostalAddress() throws Exception {
		return new PostalAddress() {
			{
				setAddressCountry("united-states");
				setAddressLocality("Diamond Bar");
				setAddressRegion("California");
				setAddressType("personal");
				setPostalCode("91765");
				setPrimary(true);
				setStreetAddressLine1(RandomTestUtil.randomString());
				setStreetAddressLine2(RandomTestUtil.randomString());
				setStreetAddressLine3(RandomTestUtil.randomString());
			}
		};
	}

	private UserAccount _randomUserAccount(
			Consumer<UserAccount> userAccountConsumer)
		throws Exception {

		UserAccount randomUserAccount = randomUserAccount();

		userAccountConsumer.accept(randomUserAccount);

		return randomUserAccount;
	}

	private UserAccountContactInformation _randomUserAccountContactInformation()
		throws Exception {

		return new UserAccountContactInformation() {
			{
				setEmailAddresses(new EmailAddress[] {_randomEmailAddress()});
				setFacebook(RandomTestUtil.randomString());
				setJabber(RandomTestUtil.randomString());
				setPostalAddresses(
					new PostalAddress[] {_randomPostalAddress()});
				setSkype(RandomTestUtil.randomString());
				setSms(RandomTestUtil.randomString() + "@liferay.com");
				setTelephones(new Phone[] {_randomPhone()});
				setTwitter(RandomTestUtil.randomString());
				setWebUrls(new WebUrl[] {_randomWebUrl()});
			}
		};
	}

	private WebUrl _randomWebUrl() throws Exception {
		return new WebUrl() {
			{
				setPrimary(true);
				setUrl("https://" + RandomTestUtil.randomString() + ".com");
				setUrlType("personal");
			}
		};
	}

	private void _setUpTestUserAccountResource() throws Exception {
		_regularUserAccountCurrentPassword = RandomTestUtil.randomString();

		_regularUserAccount = _addUserAccount(
			testGroup.getGroupId(), randomUserAccount());

		_userLocalService.updatePassword(
			_regularUserAccount.getId(), _regularUserAccountCurrentPassword,
			_regularUserAccountCurrentPassword, false, true);

		UserAccountResource.Builder builder = UserAccountResource.builder();

		_regularUserAccountResource = builder.authentication(
			_regularUserAccount.getEmailAddress(),
			_regularUserAccountCurrentPassword
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	private void _testGetUserAccountsPage(
			String filterString, UserAccount... expectedUserAccounts)
		throws Exception {

		Page<UserAccount> page = userAccountResource.getUserAccountsPage(
			null, filterString,
			Pagination.of(1, expectedUserAccounts.length + 1), null);

		Assert.assertEquals(expectedUserAccounts.length, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(expectedUserAccounts),
			(List<UserAccount>)page.getItems());

		if (expectedUserAccounts.length > 0) {
			assertValid(page);
		}
	}

	private void _testPostUserAccount(Captcha captcha, boolean enableCaptcha)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(UserAccountResourceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				Captcha.class, captcha,
				HashMapDictionaryBuilder.put(
					"captcha.engine.impl", TestSimpleCaptchaImpl.class.getName()
				).build());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.captcha.configuration.CaptchaConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"captchaEngine", TestSimpleCaptchaImpl.class.getName()
					).put(
						"createAccountCaptchaEnabled", enableCaptcha
					).build())) {

			UserAccount userAccount = randomUserAccount();

			Assert.assertNull(
				_userLocalService.fetchUserByEmailAddress(
					TestPropsValues.getCompanyId(),
					userAccount.getEmailAddress()));

			UserAccountResource.Builder builder = UserAccountResource.builder();

			userAccountResource = builder.locale(
				LocaleUtil.getDefault()
			).build();

			userAccountResource.postUserAccount(userAccount);

			Assert.assertNotNull(
				_userLocalService.fetchUserByEmailAddress(
					TestPropsValues.getCompanyId(),
					userAccount.getEmailAddress()));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private String[] _toEmailAddresses(List<User> users) {
		return TransformUtil.transformToArray(
			users, User::getEmailAddress, String.class);
	}

	private long[] _toUserIds(List<User> users) {
		return ListUtil.toLongArray(users, User.USER_ID_ACCESSOR);
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	private Organization _organization;
	private UserAccount _regularUserAccount;
	private String _regularUserAccountCurrentPassword;
	private UserAccountResource _regularUserAccountResource;

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

	private User _testUser;

	@Inject
	private UserLocalService _userLocalService;

	private class TestSimpleCaptchaImpl extends SimpleCaptchaImpl {

		public TestSimpleCaptchaImpl(
			UnsafeRunnable<CaptchaException> unsafeRunnable) {

			_unsafeRunnable = unsafeRunnable;
		}

		@Override
		public void check(HttpServletRequest httpServletRequest)
			throws CaptchaException {

			_unsafeRunnable.run();
		}

		private final UnsafeRunnable<CaptchaException> _unsafeRunnable;

	}

}