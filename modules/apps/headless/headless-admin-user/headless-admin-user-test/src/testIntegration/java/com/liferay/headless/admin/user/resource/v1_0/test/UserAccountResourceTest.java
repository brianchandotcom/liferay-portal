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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccountContactInformation;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.serdes.v1_0.EmailAddressSerDes;
import com.liferay.headless.admin.user.client.serdes.v1_0.PhoneSerDes;
import com.liferay.headless.admin.user.client.serdes.v1_0.PostalAddressSerDes;
import com.liferay.headless.admin.user.client.serdes.v1_0.UserAccountSerDes;
import com.liferay.headless.admin.user.client.serdes.v1_0.WebUrlSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.beanutils.BeanUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class UserAccountResourceTest extends BaseUserAccountResourceTestCase {

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

		Page<UserAccount> page = userAccountResource.getUserAccountsPage(
			null, null, Pagination.of(1, 3), null);

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			(List<UserAccount>)page.getItems());
		assertValid(page);
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
		UserAccount userAccount = userAccountResource.getUserAccount(
			_testUser.getUserId());

		Assert.assertTrue(
			equals(
				userAccount,
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
			"emailAddresses", "emailAddress", EmailAddressSerDes::toDTO);
		_assertUserAccountContactInformation(
			userAccountContactInformation1, userAccountContactInformation2,
			"postalAddresses", "streetAddressLine1",
			PostalAddressSerDes::toDTO);
		_assertUserAccountContactInformation(
			userAccountContactInformation1, userAccountContactInformation2,
			"telephones", "phoneNumber", PhoneSerDes::toDTO);
		_assertUserAccountContactInformation(
			userAccountContactInformation1, userAccountContactInformation2,
			"webUrls", "url", WebUrlSerDes::toDTO);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"alternateName", "familyName", "givenName"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"alternateName", "emailAddress"};
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
	protected UserAccount testDeleteUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
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
	protected UserAccount testPostUserAccount_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), userAccount);
	}

	@Override
	protected UserAccount testPutUserAccount_addUserAccount() throws Exception {
		return _addUserAccount(testGroup.getGroupId(), randomUserAccount());
	}

	private UserAccount _addUserAccount(long siteId, UserAccount userAccount)
		throws Exception {

		userAccount = userAccountResource.postUserAccount(userAccount);

		_userLocalService.addGroupUser(siteId, userAccount.getId());

		return userAccount;
	}

	private void _assertUserAccountContactInformation(
		UserAccountContactInformation userAccountContactInformation1,
		UserAccountContactInformation userAccountContactInformation2,
		String fieldName, String subfieldName,
		Function<String, ?> deserializerFunction) {

		try {
			String[] jsons1 = BeanUtils.getArrayProperty(
				userAccountContactInformation1, fieldName);
			String[] jsons2 = BeanUtils.getArrayProperty(
				userAccountContactInformation2, fieldName);

			Assert.assertEquals(
				Arrays.toString(jsons1), jsons1.length, jsons2.length);

			Comparator<String> comparator = Comparator.comparing(
				json -> {
					try {
						return BeanUtils.getProperty(
							deserializerFunction.apply(json), subfieldName);
					}
					catch (Exception exception) {
						return null;
					}
				});

			Arrays.sort(jsons1, comparator);
			Arrays.sort(jsons2, comparator);

			for (int i = 0; i < jsons1.length; i++) {
				Assert.assertEquals(
					BeanUtils.getProperty(
						deserializerFunction.apply(jsons1[i]), subfieldName),
					BeanUtils.getProperty(
						deserializerFunction.apply(jsons2[i]), subfieldName));
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
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

	private Organization _organization;
	private User _testUser;

	@Inject
	private UserLocalService _userLocalService;

}