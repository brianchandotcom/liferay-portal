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

import com.liferay.headless.admin.user.client.dto.v1_0.AccountGroup;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountGroupResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.AccountGroupSerDes;
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
public abstract class BaseAccountGroupResourceTestCase {

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

		_accountGroupResource.setContextCompany(testCompany);

		AccountGroupResource.Builder builder = AccountGroupResource.builder();

		accountGroupResource = builder.authentication(
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

		AccountGroup accountGroup1 = randomAccountGroup();

		String json = objectMapper.writeValueAsString(accountGroup1);

		AccountGroup accountGroup2 = AccountGroupSerDes.toDTO(json);

		Assert.assertTrue(equals(accountGroup1, accountGroup2));
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

		AccountGroup accountGroup = randomAccountGroup();

		String json1 = objectMapper.writeValueAsString(accountGroup);
		String json2 = AccountGroupSerDes.toJSON(accountGroup);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountGroup accountGroup = randomAccountGroup();

		accountGroup.setDescription(regex);
		accountGroup.setExternalReferenceCode(regex);
		accountGroup.setName(regex);

		String json = AccountGroupSerDes.toJSON(accountGroup);

		Assert.assertFalse(json.contains(regex));

		accountGroup = AccountGroupSerDes.toDTO(json);

		Assert.assertEquals(regex, accountGroup.getDescription());
		Assert.assertEquals(regex, accountGroup.getExternalReferenceCode());
		Assert.assertEquals(regex, accountGroup.getName());
	}

	@Test
	public void testGetAccountGroupsPage() throws Exception {
		Page<AccountGroup> page = accountGroupResource.getAccountGroupsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		AccountGroup accountGroup1 = testGetAccountGroupsPage_addAccountGroup(
			randomAccountGroup());

		AccountGroup accountGroup2 = testGetAccountGroupsPage_addAccountGroup(
			randomAccountGroup());

		page = accountGroupResource.getAccountGroupsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(accountGroup1, (List<AccountGroup>)page.getItems());
		assertContains(accountGroup2, (List<AccountGroup>)page.getItems());
		assertValid(page, testGetAccountGroupsPage_getExpectedActions());

		accountGroupResource.deleteAccountGroup(accountGroup1.getId());

		accountGroupResource.deleteAccountGroup(accountGroup2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAccountGroupsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountGroupsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		AccountGroup accountGroup1 = randomAccountGroup();

		accountGroup1 = testGetAccountGroupsPage_addAccountGroup(accountGroup1);

		for (EntityField entityField : entityFields) {
			Page<AccountGroup> page = accountGroupResource.getAccountGroupsPage(
				null, getFilterString(entityField, "between", accountGroup1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountGroup1),
				(List<AccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetAccountGroupsPageWithFilterDoubleEquals()
		throws Exception {

		testGetAccountGroupsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAccountGroupsPageWithFilterStringContains()
		throws Exception {

		testGetAccountGroupsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountGroupsPageWithFilterStringEquals()
		throws Exception {

		testGetAccountGroupsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountGroupsPageWithFilterStringStartsWith()
		throws Exception {

		testGetAccountGroupsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetAccountGroupsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		AccountGroup accountGroup1 = testGetAccountGroupsPage_addAccountGroup(
			randomAccountGroup());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountGroup accountGroup2 = testGetAccountGroupsPage_addAccountGroup(
			randomAccountGroup());

		for (EntityField entityField : entityFields) {
			Page<AccountGroup> page = accountGroupResource.getAccountGroupsPage(
				null, getFilterString(entityField, operator, accountGroup1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountGroup1),
				(List<AccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetAccountGroupsPageWithPagination() throws Exception {
		Page<AccountGroup> totalPage =
			accountGroupResource.getAccountGroupsPage(null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		AccountGroup accountGroup1 = testGetAccountGroupsPage_addAccountGroup(
			randomAccountGroup());

		AccountGroup accountGroup2 = testGetAccountGroupsPage_addAccountGroup(
			randomAccountGroup());

		AccountGroup accountGroup3 = testGetAccountGroupsPage_addAccountGroup(
			randomAccountGroup());

		Page<AccountGroup> page1 = accountGroupResource.getAccountGroupsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<AccountGroup> accountGroups1 =
			(List<AccountGroup>)page1.getItems();

		Assert.assertEquals(
			accountGroups1.toString(), totalCount + 2, accountGroups1.size());

		Page<AccountGroup> page2 = accountGroupResource.getAccountGroupsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<AccountGroup> accountGroups2 =
			(List<AccountGroup>)page2.getItems();

		Assert.assertEquals(
			accountGroups2.toString(), 1, accountGroups2.size());

		Page<AccountGroup> page3 = accountGroupResource.getAccountGroupsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(accountGroup1, (List<AccountGroup>)page3.getItems());
		assertContains(accountGroup2, (List<AccountGroup>)page3.getItems());
		assertContains(accountGroup3, (List<AccountGroup>)page3.getItems());
	}

	@Test
	public void testGetAccountGroupsPageWithSortDateTime() throws Exception {
		testGetAccountGroupsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, accountGroup1, accountGroup2) -> {
				BeanTestUtil.setProperty(
					accountGroup1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountGroupsPageWithSortDouble() throws Exception {
		testGetAccountGroupsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, accountGroup1, accountGroup2) -> {
				BeanTestUtil.setProperty(
					accountGroup1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					accountGroup2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAccountGroupsPageWithSortInteger() throws Exception {
		testGetAccountGroupsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, accountGroup1, accountGroup2) -> {
				BeanTestUtil.setProperty(
					accountGroup1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					accountGroup2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountGroupsPageWithSortString() throws Exception {
		testGetAccountGroupsPageWithSort(
			EntityField.Type.STRING,
			(entityField, accountGroup1, accountGroup2) -> {
				Class<?> clazz = accountGroup1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						accountGroup1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						accountGroup2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						accountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						accountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						accountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						accountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountGroupsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, AccountGroup, AccountGroup, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		AccountGroup accountGroup1 = randomAccountGroup();
		AccountGroup accountGroup2 = randomAccountGroup();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, accountGroup1, accountGroup2);
		}

		accountGroup1 = testGetAccountGroupsPage_addAccountGroup(accountGroup1);

		accountGroup2 = testGetAccountGroupsPage_addAccountGroup(accountGroup2);

		for (EntityField entityField : entityFields) {
			Page<AccountGroup> ascPage =
				accountGroupResource.getAccountGroupsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(accountGroup1, accountGroup2),
				(List<AccountGroup>)ascPage.getItems());

			Page<AccountGroup> descPage =
				accountGroupResource.getAccountGroupsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(accountGroup2, accountGroup1),
				(List<AccountGroup>)descPage.getItems());
		}
	}

	protected AccountGroup testGetAccountGroupsPage_addAccountGroup(
			AccountGroup accountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountGroupsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"accountGroups",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject accountGroupsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/accountGroups");

		long totalCount = accountGroupsJSONObject.getLong("totalCount");

		AccountGroup accountGroup1 =
			testGraphQLGetAccountGroupsPage_addAccountGroup();
		AccountGroup accountGroup2 =
			testGraphQLGetAccountGroupsPage_addAccountGroup();

		accountGroupsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/accountGroups");

		Assert.assertEquals(
			totalCount + 2, accountGroupsJSONObject.getLong("totalCount"));

		assertContains(
			accountGroup1,
			Arrays.asList(
				AccountGroupSerDes.toDTOs(
					accountGroupsJSONObject.getString("items"))));
		assertContains(
			accountGroup2,
			Arrays.asList(
				AccountGroupSerDes.toDTOs(
					accountGroupsJSONObject.getString("items"))));
	}

	protected AccountGroup testGraphQLGetAccountGroupsPage_addAccountGroup()
		throws Exception {

		return testGraphQLAccountGroup_addAccountGroup();
	}

	@Test
	public void testPostAccountGroup() throws Exception {
		AccountGroup randomAccountGroup = randomAccountGroup();

		AccountGroup postAccountGroup = testPostAccountGroup_addAccountGroup(
			randomAccountGroup);

		assertEquals(randomAccountGroup, postAccountGroup);
		assertValid(postAccountGroup);
	}

	protected AccountGroup testPostAccountGroup_addAccountGroup(
			AccountGroup accountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountGroup accountGroup =
			testDeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_addAccountGroup();

		assertHttpResponseStatusCode(
			204,
			accountGroupResource.
				deleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCodeHttpResponse(
					testDeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_getAccountExternalReferenceCode(),
					accountGroup.getExternalReferenceCode()));
	}

	protected String
			testDeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_getAccountExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected AccountGroup
			testDeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountGroup accountGroup =
			testPostAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_addAccountGroup();

		assertHttpResponseStatusCode(
			204,
			accountGroupResource.
				postAccountGroupByExternalReferenceCodeAccountByExternalReferenceCodeHttpResponse(
					null, accountGroup.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			accountGroupResource.
				postAccountGroupByExternalReferenceCodeAccountByExternalReferenceCodeHttpResponse(
					null, accountGroup.getExternalReferenceCode()));
	}

	protected AccountGroup
			testPostAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountGroupByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountGroup accountGroup =
			testDeleteAccountGroupByExternalReferenceCode_addAccountGroup();

		assertHttpResponseStatusCode(
			204,
			accountGroupResource.
				deleteAccountGroupByExternalReferenceCodeHttpResponse(
					accountGroup.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			accountGroupResource.
				getAccountGroupByExternalReferenceCodeHttpResponse(
					accountGroup.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			accountGroupResource.
				getAccountGroupByExternalReferenceCodeHttpResponse(
					accountGroup.getExternalReferenceCode()));
	}

	protected AccountGroup
			testDeleteAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountGroupByExternalReferenceCode() throws Exception {
		AccountGroup postAccountGroup =
			testGetAccountGroupByExternalReferenceCode_addAccountGroup();

		AccountGroup getAccountGroup =
			accountGroupResource.getAccountGroupByExternalReferenceCode(
				postAccountGroup.getExternalReferenceCode());

		assertEquals(postAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);
	}

	protected AccountGroup
			testGetAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountGroupByExternalReferenceCode()
		throws Exception {

		AccountGroup accountGroup =
			testGraphQLGetAccountGroupByExternalReferenceCode_addAccountGroup();

		Assert.assertTrue(
			equals(
				accountGroup,
				AccountGroupSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"accountGroupByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												accountGroup.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/accountGroupByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetAccountGroupByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountGroupByExternalReferenceCode",
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

	protected AccountGroup
			testGraphQLGetAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		return testGraphQLAccountGroup_addAccountGroup();
	}

	@Test
	public void testPatchAccountGroupByExternalReferenceCode()
		throws Exception {

		AccountGroup postAccountGroup =
			testPatchAccountGroupByExternalReferenceCode_addAccountGroup();

		AccountGroup randomPatchAccountGroup = randomPatchAccountGroup();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountGroup patchAccountGroup =
			accountGroupResource.patchAccountGroupByExternalReferenceCode(
				postAccountGroup.getExternalReferenceCode(),
				randomPatchAccountGroup);

		AccountGroup expectedPatchAccountGroup = postAccountGroup.clone();

		BeanTestUtil.copyProperties(
			randomPatchAccountGroup, expectedPatchAccountGroup);

		AccountGroup getAccountGroup =
			accountGroupResource.getAccountGroupByExternalReferenceCode(
				patchAccountGroup.getExternalReferenceCode());

		assertEquals(expectedPatchAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);
	}

	protected AccountGroup
			testPatchAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAccountGroupByExternalReferenceCode() throws Exception {
		AccountGroup postAccountGroup =
			testPutAccountGroupByExternalReferenceCode_addAccountGroup();

		AccountGroup randomAccountGroup = randomAccountGroup();

		AccountGroup putAccountGroup =
			accountGroupResource.putAccountGroupByExternalReferenceCode(
				postAccountGroup.getExternalReferenceCode(),
				randomAccountGroup);

		assertEquals(randomAccountGroup, putAccountGroup);
		assertValid(putAccountGroup);

		AccountGroup getAccountGroup =
			accountGroupResource.getAccountGroupByExternalReferenceCode(
				putAccountGroup.getExternalReferenceCode());

		assertEquals(randomAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);

		AccountGroup newAccountGroup =
			testPutAccountGroupByExternalReferenceCode_createAccountGroup();

		putAccountGroup =
			accountGroupResource.putAccountGroupByExternalReferenceCode(
				newAccountGroup.getExternalReferenceCode(), newAccountGroup);

		assertEquals(newAccountGroup, putAccountGroup);
		assertValid(putAccountGroup);

		getAccountGroup =
			accountGroupResource.getAccountGroupByExternalReferenceCode(
				putAccountGroup.getExternalReferenceCode());

		assertEquals(newAccountGroup, getAccountGroup);

		Assert.assertEquals(
			newAccountGroup.getExternalReferenceCode(),
			putAccountGroup.getExternalReferenceCode());
	}

	protected AccountGroup
			testPutAccountGroupByExternalReferenceCode_createAccountGroup()
		throws Exception {

		return randomAccountGroup();
	}

	protected AccountGroup
			testPutAccountGroupByExternalReferenceCode_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountGroup() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountGroup accountGroup = testDeleteAccountGroup_addAccountGroup();

		assertHttpResponseStatusCode(
			204,
			accountGroupResource.deleteAccountGroupHttpResponse(
				accountGroup.getId()));

		assertHttpResponseStatusCode(
			404,
			accountGroupResource.getAccountGroupHttpResponse(
				accountGroup.getId()));

		assertHttpResponseStatusCode(
			404, accountGroupResource.getAccountGroupHttpResponse(0L));
	}

	protected AccountGroup testDeleteAccountGroup_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteAccountGroup() throws Exception {
		AccountGroup accountGroup =
			testGraphQLDeleteAccountGroup_addAccountGroup();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteAccountGroup",
						new HashMap<String, Object>() {
							{
								put("accountGroupId", accountGroup.getId());
							}
						})),
				"JSONObject/data", "Object/deleteAccountGroup"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"accountGroup",
					new HashMap<String, Object>() {
						{
							put("accountGroupId", accountGroup.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected AccountGroup testGraphQLDeleteAccountGroup_addAccountGroup()
		throws Exception {

		return testGraphQLAccountGroup_addAccountGroup();
	}

	@Test
	public void testGetAccountGroup() throws Exception {
		AccountGroup postAccountGroup = testGetAccountGroup_addAccountGroup();

		AccountGroup getAccountGroup = accountGroupResource.getAccountGroup(
			postAccountGroup.getId());

		assertEquals(postAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);
	}

	protected AccountGroup testGetAccountGroup_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountGroup() throws Exception {
		AccountGroup accountGroup =
			testGraphQLGetAccountGroup_addAccountGroup();

		Assert.assertTrue(
			equals(
				accountGroup,
				AccountGroupSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"accountGroup",
								new HashMap<String, Object>() {
									{
										put(
											"accountGroupId",
											accountGroup.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/accountGroup"))));
	}

	@Test
	public void testGraphQLGetAccountGroupNotFound() throws Exception {
		Long irrelevantAccountGroupId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountGroup",
						new HashMap<String, Object>() {
							{
								put("accountGroupId", irrelevantAccountGroupId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected AccountGroup testGraphQLGetAccountGroup_addAccountGroup()
		throws Exception {

		return testGraphQLAccountGroup_addAccountGroup();
	}

	@Test
	public void testPatchAccountGroup() throws Exception {
		AccountGroup postAccountGroup = testPatchAccountGroup_addAccountGroup();

		AccountGroup randomPatchAccountGroup = randomPatchAccountGroup();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountGroup patchAccountGroup = accountGroupResource.patchAccountGroup(
			postAccountGroup.getId(), randomPatchAccountGroup);

		AccountGroup expectedPatchAccountGroup = postAccountGroup.clone();

		BeanTestUtil.copyProperties(
			randomPatchAccountGroup, expectedPatchAccountGroup);

		AccountGroup getAccountGroup = accountGroupResource.getAccountGroup(
			patchAccountGroup.getId());

		assertEquals(expectedPatchAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);
	}

	protected AccountGroup testPatchAccountGroup_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAccountGroup() throws Exception {
		AccountGroup postAccountGroup = testPutAccountGroup_addAccountGroup();

		AccountGroup randomAccountGroup = randomAccountGroup();

		AccountGroup putAccountGroup = accountGroupResource.putAccountGroup(
			postAccountGroup.getId(), randomAccountGroup);

		assertEquals(randomAccountGroup, putAccountGroup);
		assertValid(putAccountGroup);

		AccountGroup getAccountGroup = accountGroupResource.getAccountGroup(
			putAccountGroup.getId());

		assertEquals(randomAccountGroup, getAccountGroup);
		assertValid(getAccountGroup);
	}

	protected AccountGroup testPutAccountGroup_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage()
		throws Exception {

		String accountExternalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getAccountExternalReferenceCode();
		String irrelevantAccountExternalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getIrrelevantAccountExternalReferenceCode();

		Page<AccountGroup> page =
			accountGroupResource.
				getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
					accountExternalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantAccountExternalReferenceCode != null) {
			AccountGroup irrelevantAccountGroup =
				testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
					irrelevantAccountExternalReferenceCode,
					randomIrrelevantAccountGroup());

			page =
				accountGroupResource.
					getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
						irrelevantAccountExternalReferenceCode,
						Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountGroup),
				(List<AccountGroup>)page.getItems());
			assertValid(
				page,
				testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getExpectedActions(
					irrelevantAccountExternalReferenceCode));
		}

		AccountGroup accountGroup1 =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
				accountExternalReferenceCode, randomAccountGroup());

		AccountGroup accountGroup2 =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
				accountExternalReferenceCode, randomAccountGroup());

		page =
			accountGroupResource.
				getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
					accountExternalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountGroup1, accountGroup2),
			(List<AccountGroup>)page.getItems());
		assertValid(
			page,
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getExpectedActions(
				accountExternalReferenceCode));

		accountGroupResource.deleteAccountGroup(accountGroup1.getId());

		accountGroupResource.deleteAccountGroup(accountGroup2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getExpectedActions(
				String accountExternalReferenceCode)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPageWithPagination()
		throws Exception {

		String accountExternalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getAccountExternalReferenceCode();

		AccountGroup accountGroup1 =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
				accountExternalReferenceCode, randomAccountGroup());

		AccountGroup accountGroup2 =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
				accountExternalReferenceCode, randomAccountGroup());

		AccountGroup accountGroup3 =
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
				accountExternalReferenceCode, randomAccountGroup());

		Page<AccountGroup> page1 =
			accountGroupResource.
				getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
					accountExternalReferenceCode, Pagination.of(1, 2));

		List<AccountGroup> accountGroups1 =
			(List<AccountGroup>)page1.getItems();

		Assert.assertEquals(
			accountGroups1.toString(), 2, accountGroups1.size());

		Page<AccountGroup> page2 =
			accountGroupResource.
				getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
					accountExternalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountGroup> accountGroups2 =
			(List<AccountGroup>)page2.getItems();

		Assert.assertEquals(
			accountGroups2.toString(), 1, accountGroups2.size());

		Page<AccountGroup> page3 =
			accountGroupResource.
				getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
					accountExternalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(accountGroup1, accountGroup2, accountGroup3),
			(List<AccountGroup>)page3.getItems());
	}

	protected AccountGroup
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_addAccountGroup(
				String accountExternalReferenceCode, AccountGroup accountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getAccountExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage_getIrrelevantAccountExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testGetAccountAccountGroupsPage() throws Exception {
		Long accountId = testGetAccountAccountGroupsPage_getAccountId();
		Long irrelevantAccountId =
			testGetAccountAccountGroupsPage_getIrrelevantAccountId();

		Page<AccountGroup> page =
			accountGroupResource.getAccountAccountGroupsPage(
				accountId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantAccountId != null) {
			AccountGroup irrelevantAccountGroup =
				testGetAccountAccountGroupsPage_addAccountGroup(
					irrelevantAccountId, randomIrrelevantAccountGroup());

			page = accountGroupResource.getAccountAccountGroupsPage(
				irrelevantAccountId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountGroup),
				(List<AccountGroup>)page.getItems());
			assertValid(
				page,
				testGetAccountAccountGroupsPage_getExpectedActions(
					irrelevantAccountId));
		}

		AccountGroup accountGroup1 =
			testGetAccountAccountGroupsPage_addAccountGroup(
				accountId, randomAccountGroup());

		AccountGroup accountGroup2 =
			testGetAccountAccountGroupsPage_addAccountGroup(
				accountId, randomAccountGroup());

		page = accountGroupResource.getAccountAccountGroupsPage(
			accountId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountGroup1, accountGroup2),
			(List<AccountGroup>)page.getItems());
		assertValid(
			page,
			testGetAccountAccountGroupsPage_getExpectedActions(accountId));

		accountGroupResource.deleteAccountGroup(accountGroup1.getId());

		accountGroupResource.deleteAccountGroup(accountGroup2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAccountAccountGroupsPage_getExpectedActions(Long accountId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountAccountGroupsPageWithPagination()
		throws Exception {

		Long accountId = testGetAccountAccountGroupsPage_getAccountId();

		AccountGroup accountGroup1 =
			testGetAccountAccountGroupsPage_addAccountGroup(
				accountId, randomAccountGroup());

		AccountGroup accountGroup2 =
			testGetAccountAccountGroupsPage_addAccountGroup(
				accountId, randomAccountGroup());

		AccountGroup accountGroup3 =
			testGetAccountAccountGroupsPage_addAccountGroup(
				accountId, randomAccountGroup());

		Page<AccountGroup> page1 =
			accountGroupResource.getAccountAccountGroupsPage(
				accountId, Pagination.of(1, 2));

		List<AccountGroup> accountGroups1 =
			(List<AccountGroup>)page1.getItems();

		Assert.assertEquals(
			accountGroups1.toString(), 2, accountGroups1.size());

		Page<AccountGroup> page2 =
			accountGroupResource.getAccountAccountGroupsPage(
				accountId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountGroup> accountGroups2 =
			(List<AccountGroup>)page2.getItems();

		Assert.assertEquals(
			accountGroups2.toString(), 1, accountGroups2.size());

		Page<AccountGroup> page3 =
			accountGroupResource.getAccountAccountGroupsPage(
				accountId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(accountGroup1, accountGroup2, accountGroup3),
			(List<AccountGroup>)page3.getItems());
	}

	protected AccountGroup testGetAccountAccountGroupsPage_addAccountGroup(
			Long accountId, AccountGroup accountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountAccountGroupsPage_getAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountAccountGroupsPage_getIrrelevantAccountId()
		throws Exception {

		return null;
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected AccountGroup testGraphQLAccountGroup_addAccountGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		AccountGroup accountGroup, List<AccountGroup> accountGroups) {

		boolean contains = false;

		for (AccountGroup item : accountGroups) {
			if (equals(accountGroup, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			accountGroups + " does not contain " + accountGroup, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AccountGroup accountGroup1, AccountGroup accountGroup2) {

		Assert.assertTrue(
			accountGroup1 + " does not equal " + accountGroup2,
			equals(accountGroup1, accountGroup2));
	}

	protected void assertEquals(
		List<AccountGroup> accountGroups1, List<AccountGroup> accountGroups2) {

		Assert.assertEquals(accountGroups1.size(), accountGroups2.size());

		for (int i = 0; i < accountGroups1.size(); i++) {
			AccountGroup accountGroup1 = accountGroups1.get(i);
			AccountGroup accountGroup2 = accountGroups2.get(i);

			assertEquals(accountGroup1, accountGroup2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountGroup> accountGroups1, List<AccountGroup> accountGroups2) {

		Assert.assertEquals(accountGroups1.size(), accountGroups2.size());

		for (AccountGroup accountGroup1 : accountGroups1) {
			boolean contains = false;

			for (AccountGroup accountGroup2 : accountGroups2) {
				if (equals(accountGroup1, accountGroup2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountGroups2 + " does not contain " + accountGroup1,
				contains);
		}
	}

	protected void assertValid(AccountGroup accountGroup) throws Exception {
		boolean valid = true;

		if (accountGroup.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (accountGroup.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (accountGroup.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (accountGroup.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (accountGroup.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (accountGroup.getName() == null) {
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

	protected void assertValid(Page<AccountGroup> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<AccountGroup> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<AccountGroup> accountGroups = page.getItems();

		int size = accountGroups.size();

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
					com.liferay.headless.admin.user.dto.v1_0.AccountGroup.
						class)) {

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

	protected boolean equals(
		AccountGroup accountGroup1, AccountGroup accountGroup2) {

		if (accountGroup1 == accountGroup2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)accountGroup1.getActions(),
						(Map)accountGroup2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountGroup1.getCustomFields(),
						accountGroup2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountGroup1.getDescription(),
						accountGroup2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						accountGroup1.getExternalReferenceCode(),
						accountGroup2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountGroup1.getId(), accountGroup2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountGroup1.getName(), accountGroup2.getName())) {

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

		if (!(_accountGroupResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountGroupResource;

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
		EntityField entityField, String operator, AccountGroup accountGroup) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			Object object = accountGroup.getDescription();

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

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = accountGroup.getExternalReferenceCode();

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

		if (entityFieldName.equals("name")) {
			Object object = accountGroup.getName();

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

	protected AccountGroup randomAccountGroup() throws Exception {
		return new AccountGroup() {
			{
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected AccountGroup randomIrrelevantAccountGroup() throws Exception {
		AccountGroup randomIrrelevantAccountGroup = randomAccountGroup();

		return randomIrrelevantAccountGroup;
	}

	protected AccountGroup randomPatchAccountGroup() throws Exception {
		return randomAccountGroup();
	}

	protected AccountGroupResource accountGroupResource;
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
		LogFactoryUtil.getLog(BaseAccountGroupResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.admin.user.resource.v1_0.AccountGroupResource
		_accountGroupResource;

}