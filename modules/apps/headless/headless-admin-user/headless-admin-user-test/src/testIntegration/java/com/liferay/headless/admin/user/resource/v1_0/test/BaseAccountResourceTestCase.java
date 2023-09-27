/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.AccountSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseAccountResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_accountResource.setContextCompany(testCompany);

		AccountResource.Builder builder = AccountResource.builder();

		accountResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Account account1 = randomAccount();

		String json = objectMapper.writeValueAsString(account1);

		Account account2 = AccountSerDes.toDTO(json);

		Assert.assertTrue(equals(account1, account2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Account account = randomAccount();

		String json1 = objectMapper.writeValueAsString(account);
		String json2 = AccountSerDes.toJSON(account);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Account account = randomAccount();

		account.setDescription(regex);
		account.setExternalReferenceCode(regex);
		account.setLogoURL(regex);
		account.setName(regex);
		account.setTaxId(regex);

		String json = AccountSerDes.toJSON(account);

		Assert.assertFalse(json.contains(regex));

		account = AccountSerDes.toDTO(json);

		Assert.assertEquals(regex, account.getDescription());
		Assert.assertEquals(regex, account.getExternalReferenceCode());
		Assert.assertEquals(regex, account.getLogoURL());
		Assert.assertEquals(regex, account.getName());
		Assert.assertEquals(regex, account.getTaxId());
	}

	@Test
	public void testGetAccountsPage() throws Exception {
		Page<Account> page = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		page = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(account1, (List<Account>)page.getItems());
		assertContains(account2, (List<Account>)page.getItems());
		assertValid(page, testGetAccountsPage_getExpectedActions());

		accountResource.deleteAccount(account1.getId());

		accountResource.deleteAccount(account2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAccountsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = randomAccount();

		account1 = testGetAccountsPage_addAccount(account1);

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getAccountsPage(
				null, getFilterString(entityField, "between", account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetAccountsPageWithFilterDoubleEquals() throws Exception {
		testGetAccountsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAccountsPageWithFilterStringContains() throws Exception {
		testGetAccountsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountsPageWithFilterStringEquals() throws Exception {
		testGetAccountsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountsPageWithFilterStringStartsWith()
		throws Exception {

		testGetAccountsPageWithFilter("startswith", EntityField.Type.STRING);
	}

	protected void testGetAccountsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getAccountsPage(
				null, getFilterString(entityField, operator, account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetAccountsPageWithPagination() throws Exception {
		Page<Account> totalPage = accountResource.getAccountsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		Account account3 = testGetAccountsPage_addAccount(randomAccount());

		Page<Account> page1 = accountResource.getAccountsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(
			accounts1.toString(), totalCount + 2, accounts1.size());

		Page<Account> page2 = accountResource.getAccountsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 = accountResource.getAccountsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(account1, (List<Account>)page3.getItems());
		assertContains(account2, (List<Account>)page3.getItems());
		assertContains(account3, (List<Account>)page3.getItems());
	}

	@Test
	public void testGetAccountsPageWithSortDateTime() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(
					account1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountsPageWithSortDouble() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(account1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(account2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAccountsPageWithSortInteger() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(account1, entityField.getName(), 0);
				BeanTestUtil.setProperty(account2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountsPageWithSortString() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, account1, account2) -> {
				Class<?> clazz = account1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Account, Account, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = randomAccount();
		Account account2 = randomAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, account1, account2);
		}

		account1 = testGetAccountsPage_addAccount(account1);

		account2 = testGetAccountsPage_addAccount(account2);

		for (EntityField entityField : entityFields) {
			Page<Account> ascPage = accountResource.getAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(account1, account2),
				(List<Account>)ascPage.getItems());

			Page<Account> descPage = accountResource.getAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(account2, account1),
				(List<Account>)descPage.getItems());
		}
	}

	protected Account testGetAccountsPage_addAccount(Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"accounts",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject accountsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/accounts");

		long totalCount = accountsJSONObject.getLong("totalCount");

		Account account1 = testGraphQLGetAccountsPage_addAccount();
		Account account2 = testGraphQLGetAccountsPage_addAccount();

		accountsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/accounts");

		Assert.assertEquals(
			totalCount + 2, accountsJSONObject.getLong("totalCount"));

		assertContains(
			account1,
			Arrays.asList(
				AccountSerDes.toDTOs(accountsJSONObject.getString("items"))));
		assertContains(
			account2,
			Arrays.asList(
				AccountSerDes.toDTOs(accountsJSONObject.getString("items"))));
	}

	protected Account testGraphQLGetAccountsPage_addAccount() throws Exception {
		return testGraphQLAccount_addAccount();
	}

	@Test
	public void testPostAccount() throws Exception {
		Account randomAccount = randomAccount();

		Account postAccount = testPostAccount_addAccount(randomAccount);

		assertEquals(randomAccount, postAccount);
		assertValid(postAccount);
	}

	protected Account testPostAccount_addAccount(Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account = testDeleteAccountByExternalReferenceCode_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.deleteAccountByExternalReferenceCodeHttpResponse(
				account.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			accountResource.getAccountByExternalReferenceCodeHttpResponse(
				account.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			accountResource.getAccountByExternalReferenceCodeHttpResponse(
				account.getExternalReferenceCode()));
	}

	protected Account testDeleteAccountByExternalReferenceCode_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalReferenceCode() throws Exception {
		Account postAccount =
			testGetAccountByExternalReferenceCode_addAccount();

		Account getAccount = accountResource.getAccountByExternalReferenceCode(
			postAccount.getExternalReferenceCode());

		assertEquals(postAccount, getAccount);
		assertValid(getAccount);
	}

	protected Account testGetAccountByExternalReferenceCode_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCode()
		throws Exception {

		Account account =
			testGraphQLGetAccountByExternalReferenceCode_addAccount();

		Assert.assertTrue(
			equals(
				account,
				AccountSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"accountByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												account.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/accountByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Account testGraphQLGetAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return testGraphQLAccount_addAccount();
	}

	@Test
	public void testPatchAccountByExternalReferenceCode() throws Exception {
		Account postAccount =
			testPatchAccountByExternalReferenceCode_addAccount();

		Account randomPatchAccount = randomPatchAccount();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account patchAccount =
			accountResource.patchAccountByExternalReferenceCode(
				postAccount.getExternalReferenceCode(), randomPatchAccount);

		Account expectedPatchAccount = postAccount.clone();

		BeanTestUtil.copyProperties(randomPatchAccount, expectedPatchAccount);

		Account getAccount = accountResource.getAccountByExternalReferenceCode(
			patchAccount.getExternalReferenceCode());

		assertEquals(expectedPatchAccount, getAccount);
		assertValid(getAccount);
	}

	protected Account testPatchAccountByExternalReferenceCode_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAccountByExternalReferenceCode() throws Exception {
		Account postAccount =
			testPutAccountByExternalReferenceCode_addAccount();

		Account randomAccount = randomAccount();

		Account putAccount = accountResource.putAccountByExternalReferenceCode(
			postAccount.getExternalReferenceCode(), randomAccount);

		assertEquals(randomAccount, putAccount);
		assertValid(putAccount);

		Account getAccount = accountResource.getAccountByExternalReferenceCode(
			putAccount.getExternalReferenceCode());

		assertEquals(randomAccount, getAccount);
		assertValid(getAccount);

		Account newAccount =
			testPutAccountByExternalReferenceCode_createAccount();

		putAccount = accountResource.putAccountByExternalReferenceCode(
			newAccount.getExternalReferenceCode(), newAccount);

		assertEquals(newAccount, putAccount);
		assertValid(putAccount);

		getAccount = accountResource.getAccountByExternalReferenceCode(
			putAccount.getExternalReferenceCode());

		assertEquals(newAccount, getAccount);

		Assert.assertEquals(
			newAccount.getExternalReferenceCode(),
			putAccount.getExternalReferenceCode());
	}

	protected Account testPutAccountByExternalReferenceCode_createAccount()
		throws Exception {

		return randomAccount();
	}

	protected Account testPutAccountByExternalReferenceCode_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccount() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account = testDeleteAccount_addAccount();

		assertHttpResponseStatusCode(
			204, accountResource.deleteAccountHttpResponse(account.getId()));

		assertHttpResponseStatusCode(
			404, accountResource.getAccountHttpResponse(account.getId()));

		assertHttpResponseStatusCode(
			404, accountResource.getAccountHttpResponse(0L));
	}

	protected Account testDeleteAccount_addAccount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteAccount() throws Exception {
		Account account = testGraphQLDeleteAccount_addAccount();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteAccount",
						new HashMap<String, Object>() {
							{
								put("accountId", account.getId());
							}
						})),
				"JSONObject/data", "Object/deleteAccount"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"account",
					new HashMap<String, Object>() {
						{
							put("accountId", account.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected Account testGraphQLDeleteAccount_addAccount() throws Exception {
		return testGraphQLAccount_addAccount();
	}

	@Test
	public void testGetAccount() throws Exception {
		Account postAccount = testGetAccount_addAccount();

		Account getAccount = accountResource.getAccount(postAccount.getId());

		assertEquals(postAccount, getAccount);
		assertValid(getAccount);
	}

	protected Account testGetAccount_addAccount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccount() throws Exception {
		Account account = testGraphQLGetAccount_addAccount();

		Assert.assertTrue(
			equals(
				account,
				AccountSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"account",
								new HashMap<String, Object>() {
									{
										put("accountId", account.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/account"))));
	}

	@Test
	public void testGraphQLGetAccountNotFound() throws Exception {
		Long irrelevantAccountId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"account",
						new HashMap<String, Object>() {
							{
								put("accountId", irrelevantAccountId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Account testGraphQLGetAccount_addAccount() throws Exception {
		return testGraphQLAccount_addAccount();
	}

	@Test
	public void testPatchAccount() throws Exception {
		Account postAccount = testPatchAccount_addAccount();

		Account randomPatchAccount = randomPatchAccount();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account patchAccount = accountResource.patchAccount(
			postAccount.getId(), randomPatchAccount);

		Account expectedPatchAccount = postAccount.clone();

		BeanTestUtil.copyProperties(randomPatchAccount, expectedPatchAccount);

		Account getAccount = accountResource.getAccount(patchAccount.getId());

		assertEquals(expectedPatchAccount, getAccount);
		assertValid(getAccount);
	}

	protected Account testPatchAccount_addAccount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAccount() throws Exception {
		Account postAccount = testPutAccount_addAccount();

		Account randomAccount = randomAccount();

		Account putAccount = accountResource.putAccount(
			postAccount.getId(), randomAccount);

		assertEquals(randomAccount, putAccount);
		assertValid(putAccount);

		Account getAccount = accountResource.getAccount(putAccount.getId());

		assertEquals(randomAccount, getAccount);
		assertValid(getAccount);
	}

	protected Account testPutAccount_addAccount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchOrganizationMoveAccounts() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account = testPatchOrganizationMoveAccounts_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.patchOrganizationMoveAccountsHttpResponse(
				null, null, null));

		assertHttpResponseStatusCode(
			404,
			accountResource.patchOrganizationMoveAccountsHttpResponse(
				null, null, null));
	}

	protected Account testPatchOrganizationMoveAccounts_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchOrganizationMoveAccountsByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account =
			testPatchOrganizationMoveAccountsByExternalReferenceCode_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.
				patchOrganizationMoveAccountsByExternalReferenceCodeHttpResponse(
					null, null, null));

		assertHttpResponseStatusCode(
			404,
			accountResource.
				patchOrganizationMoveAccountsByExternalReferenceCodeHttpResponse(
					null, null, null));
	}

	protected Account
			testPatchOrganizationMoveAccountsByExternalReferenceCode_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteOrganizationAccounts() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account = testDeleteOrganizationAccounts_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.deleteOrganizationAccountsHttpResponse(
				testDeleteOrganizationAccounts_getOrganizationId(), null));
	}

	protected Long testDeleteOrganizationAccounts_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Account testDeleteOrganizationAccounts_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrganizationAccountsPage() throws Exception {
		String organizationId =
			testGetOrganizationAccountsPage_getOrganizationId();
		String irrelevantOrganizationId =
			testGetOrganizationAccountsPage_getIrrelevantOrganizationId();

		Page<Account> page = accountResource.getOrganizationAccountsPage(
			organizationId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantOrganizationId != null) {
			Account irrelevantAccount =
				testGetOrganizationAccountsPage_addAccount(
					irrelevantOrganizationId, randomIrrelevantAccount());

			page = accountResource.getOrganizationAccountsPage(
				irrelevantOrganizationId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccount),
				(List<Account>)page.getItems());
			assertValid(
				page,
				testGetOrganizationAccountsPage_getExpectedActions(
					irrelevantOrganizationId));
		}

		Account account1 = testGetOrganizationAccountsPage_addAccount(
			organizationId, randomAccount());

		Account account2 = testGetOrganizationAccountsPage_addAccount(
			organizationId, randomAccount());

		page = accountResource.getOrganizationAccountsPage(
			organizationId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2), (List<Account>)page.getItems());
		assertValid(
			page,
			testGetOrganizationAccountsPage_getExpectedActions(organizationId));

		accountResource.deleteAccount(account1.getId());

		accountResource.deleteAccount(account2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetOrganizationAccountsPage_getExpectedActions(
				String organizationId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetOrganizationAccountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String organizationId =
			testGetOrganizationAccountsPage_getOrganizationId();

		Account account1 = randomAccount();

		account1 = testGetOrganizationAccountsPage_addAccount(
			organizationId, account1);

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getOrganizationAccountsPage(
				organizationId, null,
				getFilterString(entityField, "between", account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationAccountsPageWithFilterDoubleEquals()
		throws Exception {

		testGetOrganizationAccountsPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetOrganizationAccountsPageWithFilterStringContains()
		throws Exception {

		testGetOrganizationAccountsPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationAccountsPageWithFilterStringEquals()
		throws Exception {

		testGetOrganizationAccountsPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationAccountsPageWithFilterStringStartsWith()
		throws Exception {

		testGetOrganizationAccountsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetOrganizationAccountsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String organizationId =
			testGetOrganizationAccountsPage_getOrganizationId();

		Account account1 = testGetOrganizationAccountsPage_addAccount(
			organizationId, randomAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account2 = testGetOrganizationAccountsPage_addAccount(
			organizationId, randomAccount());

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getOrganizationAccountsPage(
				organizationId, null,
				getFilterString(entityField, operator, account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationAccountsPageWithPagination()
		throws Exception {

		String organizationId =
			testGetOrganizationAccountsPage_getOrganizationId();

		Account account1 = testGetOrganizationAccountsPage_addAccount(
			organizationId, randomAccount());

		Account account2 = testGetOrganizationAccountsPage_addAccount(
			organizationId, randomAccount());

		Account account3 = testGetOrganizationAccountsPage_addAccount(
			organizationId, randomAccount());

		Page<Account> page1 = accountResource.getOrganizationAccountsPage(
			organizationId, null, null, Pagination.of(1, 2), null);

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(accounts1.toString(), 2, accounts1.size());

		Page<Account> page2 = accountResource.getOrganizationAccountsPage(
			organizationId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 = accountResource.getOrganizationAccountsPage(
			organizationId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2, account3),
			(List<Account>)page3.getItems());
	}

	@Test
	public void testGetOrganizationAccountsPageWithSortDateTime()
		throws Exception {

		testGetOrganizationAccountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(
					account1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrganizationAccountsPageWithSortDouble()
		throws Exception {

		testGetOrganizationAccountsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(account1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(account2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetOrganizationAccountsPageWithSortInteger()
		throws Exception {

		testGetOrganizationAccountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(account1, entityField.getName(), 0);
				BeanTestUtil.setProperty(account2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrganizationAccountsPageWithSortString()
		throws Exception {

		testGetOrganizationAccountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, account1, account2) -> {
				Class<?> clazz = account1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrganizationAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Account, Account, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String organizationId =
			testGetOrganizationAccountsPage_getOrganizationId();

		Account account1 = randomAccount();
		Account account2 = randomAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, account1, account2);
		}

		account1 = testGetOrganizationAccountsPage_addAccount(
			organizationId, account1);

		account2 = testGetOrganizationAccountsPage_addAccount(
			organizationId, account2);

		for (EntityField entityField : entityFields) {
			Page<Account> ascPage = accountResource.getOrganizationAccountsPage(
				organizationId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(account1, account2),
				(List<Account>)ascPage.getItems());

			Page<Account> descPage =
				accountResource.getOrganizationAccountsPage(
					organizationId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(account2, account1),
				(List<Account>)descPage.getItems());
		}
	}

	protected Account testGetOrganizationAccountsPage_addAccount(
			String organizationId, Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetOrganizationAccountsPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrganizationAccountsPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrganizationAccounts() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account = testPostOrganizationAccounts_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.postOrganizationAccountsHttpResponse(null, null));

		assertHttpResponseStatusCode(
			404,
			accountResource.postOrganizationAccountsHttpResponse(null, null));
	}

	protected Account testPostOrganizationAccounts_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteOrganizationAccountsByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account =
			testDeleteOrganizationAccountsByExternalReferenceCode_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.
				deleteOrganizationAccountsByExternalReferenceCodeHttpResponse(
					testDeleteOrganizationAccountsByExternalReferenceCode_getOrganizationId(),
					null));
	}

	protected Long
			testDeleteOrganizationAccountsByExternalReferenceCode_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Account
			testDeleteOrganizationAccountsByExternalReferenceCode_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostOrganizationAccountsByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account =
			testPostOrganizationAccountsByExternalReferenceCode_addAccount();

		assertHttpResponseStatusCode(
			204,
			accountResource.
				postOrganizationAccountsByExternalReferenceCodeHttpResponse(
					null, null));

		assertHttpResponseStatusCode(
			404,
			accountResource.
				postOrganizationAccountsByExternalReferenceCodeHttpResponse(
					null, null));
	}

	protected Account
			testPostOrganizationAccountsByExternalReferenceCode_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Account testGraphQLAccount_addAccount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Account account, List<Account> accounts) {
		boolean contains = false;

		for (Account item : accounts) {
			if (equals(account, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(accounts + " does not contain " + account, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Account account1, Account account2) {
		Assert.assertTrue(
			account1 + " does not equal " + account2,
			equals(account1, account2));
	}

	protected void assertEquals(
		List<Account> accounts1, List<Account> accounts2) {

		Assert.assertEquals(accounts1.size(), accounts2.size());

		for (int i = 0; i < accounts1.size(); i++) {
			Account account1 = accounts1.get(i);
			Account account2 = accounts2.get(i);

			assertEquals(account1, account2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Account> accounts1, List<Account> accounts2) {

		Assert.assertEquals(accounts1.size(), accounts2.size());

		for (Account account1 : accounts1) {
			boolean contains = false;

			for (Account account2 : accounts2) {
				if (equals(account1, account2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accounts2 + " does not contain " + account1, contains);
		}
	}

	protected void assertValid(Account account) throws Exception {
		boolean valid = true;

		if (account.getDateCreated() == null) {
			valid = false;
		}

		if (account.getDateModified() == null) {
			valid = false;
		}

		if (account.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"accountUserAccounts", additionalAssertFieldName)) {

				if (account.getAccountUserAccounts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (account.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (account.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultBillingAddressId", additionalAssertFieldName)) {

				if (account.getDefaultBillingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultShippingAddressId", additionalAssertFieldName)) {

				if (account.getDefaultShippingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (account.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("domains", additionalAssertFieldName)) {
				if (account.getDomains() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (account.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("logoId", additionalAssertFieldName)) {
				if (account.getLogoId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("logoURL", additionalAssertFieldName)) {
				if (account.getLogoURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (account.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfUsers", additionalAssertFieldName)) {
				if (account.getNumberOfUsers() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("organizationIds", additionalAssertFieldName)) {
				if (account.getOrganizationIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("parentAccountId", additionalAssertFieldName)) {
				if (account.getParentAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("postalAddresses", additionalAssertFieldName)) {
				if (account.getPostalAddresses() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (account.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taxId", additionalAssertFieldName)) {
				if (account.getTaxId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (account.getType() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Account> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Account> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Account> accounts = page.getItems();

		int size = accounts.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.admin.user.dto.v1_0.Account.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Account account1, Account account2) {
		if (account1 == account2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"accountUserAccounts", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getAccountUserAccounts(),
						account2.getAccountUserAccounts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)account1.getActions(),
						(Map)account2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getCustomFields(),
						account2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDateCreated(), account2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDateModified(),
						account2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultBillingAddressId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getDefaultBillingAddressId(),
						account2.getDefaultBillingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultShippingAddressId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getDefaultShippingAddressId(),
						account2.getDefaultShippingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDescription(), account2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("domains", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDomains(), account2.getDomains())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getExternalReferenceCode(),
						account2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(account1.getId(), account2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("logoId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getLogoId(), account2.getLogoId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("logoURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getLogoURL(), account2.getLogoURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getName(), account2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfUsers", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getNumberOfUsers(),
						account2.getNumberOfUsers())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("organizationIds", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getOrganizationIds(),
						account2.getOrganizationIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentAccountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getParentAccountId(),
						account2.getParentAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("postalAddresses", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getPostalAddresses(),
						account2.getPostalAddresses())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getStatus(), account2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taxId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getTaxId(), account2.getTaxId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getType(), account2.getType())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_accountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, Account account) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountUserAccounts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(account.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(account.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultBillingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("defaultShippingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			Object object = account.getDescription();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("domains")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = account.getExternalReferenceCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("logoId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("logoURL")) {
			Object object = account.getLogoURL();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			Object object = account.getName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfUsers")) {
			sb.append(String.valueOf(account.getNumberOfUsers()));

			return sb.toString();
		}

		if (entityFieldName.equals("organizationIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentAccountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("postalAddresses")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			sb.append(String.valueOf(account.getStatus()));

			return sb.toString();
		}

		if (entityFieldName.equals("taxId")) {
			Object object = account.getTaxId();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("type")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected Account randomAccount() throws Exception {
		return new Account() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultBillingAddressId = RandomTestUtil.randomLong();
				defaultShippingAddressId = RandomTestUtil.randomLong();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				logoId = RandomTestUtil.randomLong();
				logoURL = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfUsers = RandomTestUtil.randomInt();
				parentAccountId = RandomTestUtil.randomLong();
				status = RandomTestUtil.randomInt();
				taxId = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Account randomIrrelevantAccount() throws Exception {
		Account randomIrrelevantAccount = randomAccount();

		return randomIrrelevantAccount;
	}

	protected Account randomPatchAccount() throws Exception {
		return randomAccount();
	}

	protected AccountResource accountResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseAccountResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.admin.user.resource.v1_0.AccountResource
		_accountResource;

}