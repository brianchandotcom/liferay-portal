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

package com.liferay.headless.commerce.admin.account.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.account.client.dto.v1_0.AccountMember;
import com.liferay.headless.commerce.admin.account.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.account.client.pagination.Page;
import com.liferay.headless.commerce.admin.account.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.account.client.resource.v1_0.AccountMemberResource;
import com.liferay.headless.commerce.admin.account.client.serdes.v1_0.AccountMemberSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public abstract class BaseAccountMemberResourceTestCase {

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

		_accountMemberResource.setContextCompany(testCompany);

		AccountMemberResource.Builder builder = AccountMemberResource.builder();

		accountMemberResource = builder.authentication(
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

		AccountMember accountMember1 = randomAccountMember();

		String json = objectMapper.writeValueAsString(accountMember1);

		AccountMember accountMember2 = AccountMemberSerDes.toDTO(json);

		Assert.assertTrue(equals(accountMember1, accountMember2));
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

		AccountMember accountMember = randomAccountMember();

		String json1 = objectMapper.writeValueAsString(accountMember);
		String json2 = AccountMemberSerDes.toJSON(accountMember);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountMember accountMember = randomAccountMember();

		accountMember.setEmail(regex);
		accountMember.setExternalReferenceCode(regex);
		accountMember.setName(regex);
		accountMember.setUserExternalReferenceCode(regex);

		String json = AccountMemberSerDes.toJSON(accountMember);

		Assert.assertFalse(json.contains(regex));

		accountMember = AccountMemberSerDes.toDTO(json);

		Assert.assertEquals(regex, accountMember.getEmail());
		Assert.assertEquals(regex, accountMember.getExternalReferenceCode());
		Assert.assertEquals(regex, accountMember.getName());
		Assert.assertEquals(
			regex, accountMember.getUserExternalReferenceCode());
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountMembersPage()
		throws Exception {

		Page<AccountMember> page =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMembersPage(
					testGetAccountByExternalReferenceCodeAccountMembersPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountMembersPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountMembersPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			AccountMember irrelevantAccountMember =
				testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
					irrelevantExternalReferenceCode,
					randomIrrelevantAccountMember());

			page =
				accountMemberResource.
					getAccountByExternalReferenceCodeAccountMembersPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountMember),
				(List<AccountMember>)page.getItems());
			assertValid(page);
		}

		AccountMember accountMember1 =
			testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
				externalReferenceCode, randomAccountMember());

		AccountMember accountMember2 =
			testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
				externalReferenceCode, randomAccountMember());

		page =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMembersPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountMember1, accountMember2),
			(List<AccountMember>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountMembersPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountMembersPage_getExternalReferenceCode();

		AccountMember accountMember1 =
			testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
				externalReferenceCode, randomAccountMember());

		AccountMember accountMember2 =
			testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
				externalReferenceCode, randomAccountMember());

		AccountMember accountMember3 =
			testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
				externalReferenceCode, randomAccountMember());

		Page<AccountMember> page1 =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMembersPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<AccountMember> accountMembers1 =
			(List<AccountMember>)page1.getItems();

		Assert.assertEquals(
			accountMembers1.toString(), 2, accountMembers1.size());

		Page<AccountMember> page2 =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMembersPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountMember> accountMembers2 =
			(List<AccountMember>)page2.getItems();

		Assert.assertEquals(
			accountMembers2.toString(), 1, accountMembers2.size());

		Page<AccountMember> page3 =
			accountMemberResource.
				getAccountByExternalReferenceCodeAccountMembersPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(accountMember1, accountMember2, accountMember3),
			(List<AccountMember>)page3.getItems());
	}

	protected AccountMember
			testGetAccountByExternalReferenceCodeAccountMembersPage_addAccountMember(
				String externalReferenceCode, AccountMember accountMember)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountMembersPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountMembersPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		AccountMember randomAccountMember = randomAccountMember();

		AccountMember postAccountMember =
			testPostAccountByExternalReferenceCodeAccountMember_addAccountMember(
				randomAccountMember);

		assertEquals(randomAccountMember, postAccountMember);
		assertValid(postAccountMember);
	}

	protected AccountMember
			testPostAccountByExternalReferenceCodeAccountMember_addAccountMember(
				AccountMember accountMember)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeAccountMemberNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testPatchAccountByExternalReferenceCodeAccountMember()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetAccountIdAccountMembersPage() throws Exception {
		Page<AccountMember> page =
			accountMemberResource.getAccountIdAccountMembersPage(
				testGetAccountIdAccountMembersPage_getId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetAccountIdAccountMembersPage_getId();
		Long irrelevantId =
			testGetAccountIdAccountMembersPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			AccountMember irrelevantAccountMember =
				testGetAccountIdAccountMembersPage_addAccountMember(
					irrelevantId, randomIrrelevantAccountMember());

			page = accountMemberResource.getAccountIdAccountMembersPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountMember),
				(List<AccountMember>)page.getItems());
			assertValid(page);
		}

		AccountMember accountMember1 =
			testGetAccountIdAccountMembersPage_addAccountMember(
				id, randomAccountMember());

		AccountMember accountMember2 =
			testGetAccountIdAccountMembersPage_addAccountMember(
				id, randomAccountMember());

		page = accountMemberResource.getAccountIdAccountMembersPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountMember1, accountMember2),
			(List<AccountMember>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountIdAccountMembersPageWithPagination()
		throws Exception {

		Long id = testGetAccountIdAccountMembersPage_getId();

		AccountMember accountMember1 =
			testGetAccountIdAccountMembersPage_addAccountMember(
				id, randomAccountMember());

		AccountMember accountMember2 =
			testGetAccountIdAccountMembersPage_addAccountMember(
				id, randomAccountMember());

		AccountMember accountMember3 =
			testGetAccountIdAccountMembersPage_addAccountMember(
				id, randomAccountMember());

		Page<AccountMember> page1 =
			accountMemberResource.getAccountIdAccountMembersPage(
				id, Pagination.of(1, 2));

		List<AccountMember> accountMembers1 =
			(List<AccountMember>)page1.getItems();

		Assert.assertEquals(
			accountMembers1.toString(), 2, accountMembers1.size());

		Page<AccountMember> page2 =
			accountMemberResource.getAccountIdAccountMembersPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountMember> accountMembers2 =
			(List<AccountMember>)page2.getItems();

		Assert.assertEquals(
			accountMembers2.toString(), 1, accountMembers2.size());

		Page<AccountMember> page3 =
			accountMemberResource.getAccountIdAccountMembersPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(accountMember1, accountMember2, accountMember3),
			(List<AccountMember>)page3.getItems());
	}

	protected AccountMember testGetAccountIdAccountMembersPage_addAccountMember(
			Long id, AccountMember accountMember)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountIdAccountMembersPage_getId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountIdAccountMembersPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountIdAccountMember() throws Exception {
		AccountMember randomAccountMember = randomAccountMember();

		AccountMember postAccountMember =
			testPostAccountIdAccountMember_addAccountMember(
				randomAccountMember);

		assertEquals(randomAccountMember, postAccountMember);
		assertValid(postAccountMember);
	}

	protected AccountMember testPostAccountIdAccountMember_addAccountMember(
			AccountMember accountMember)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountIdAccountMember() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetAccountIdAccountMember() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetAccountIdAccountMember() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetAccountIdAccountMemberNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testPatchAccountIdAccountMember() throws Exception {
		Assert.assertTrue(false);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AccountMember accountMember1, AccountMember accountMember2) {

		Assert.assertTrue(
			accountMember1 + " does not equal " + accountMember2,
			equals(accountMember1, accountMember2));
	}

	protected void assertEquals(
		List<AccountMember> accountMembers1,
		List<AccountMember> accountMembers2) {

		Assert.assertEquals(accountMembers1.size(), accountMembers2.size());

		for (int i = 0; i < accountMembers1.size(); i++) {
			AccountMember accountMember1 = accountMembers1.get(i);
			AccountMember accountMember2 = accountMembers2.get(i);

			assertEquals(accountMember1, accountMember2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountMember> accountMembers1,
		List<AccountMember> accountMembers2) {

		Assert.assertEquals(accountMembers1.size(), accountMembers2.size());

		for (AccountMember accountMember1 : accountMembers1) {
			boolean contains = false;

			for (AccountMember accountMember2 : accountMembers2) {
				if (equals(accountMember1, accountMember2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountMembers2 + " does not contain " + accountMember1,
				contains);
		}
	}

	protected void assertValid(AccountMember accountMember) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (accountMember.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountRoles", additionalAssertFieldName)) {
				if (accountMember.getAccountRoles() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (accountMember.getEmail() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (accountMember.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (accountMember.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"userExternalReferenceCode", additionalAssertFieldName)) {

				if (accountMember.getUserExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (accountMember.getUserId() == null) {
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

	protected void assertValid(Page<AccountMember> page) {
		boolean valid = false;

		java.util.Collection<AccountMember> accountMembers = page.getItems();

		int size = accountMembers.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.commerce.admin.account.dto.v1_0.
						AccountMember.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
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
					ReflectionUtil.getDeclaredFields(clazz));

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
		AccountMember accountMember1, AccountMember accountMember2) {

		if (accountMember1 == accountMember2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountMember1.getAccountId(),
						accountMember2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountRoles", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountMember1.getAccountRoles(),
						accountMember2.getAccountRoles())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountMember1.getEmail(), accountMember2.getEmail())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						accountMember1.getExternalReferenceCode(),
						accountMember2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountMember1.getName(), accountMember2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"userExternalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						accountMember1.getUserExternalReferenceCode(),
						accountMember2.getUserExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountMember1.getUserId(),
						accountMember2.getUserId())) {

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
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_accountMemberResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountMemberResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, AccountMember accountMember) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("accountRoles")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("email")) {
			sb.append("'");
			sb.append(String.valueOf(accountMember.getEmail()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(accountMember.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(accountMember.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(accountMember.getUserExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userId")) {
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

	protected AccountMember randomAccountMember() throws Exception {
		return new AccountMember() {
			{
				accountId = RandomTestUtil.randomLong();
				email =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				userExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected AccountMember randomIrrelevantAccountMember() throws Exception {
		AccountMember randomIrrelevantAccountMember = randomAccountMember();

		return randomIrrelevantAccountMember;
	}

	protected AccountMember randomPatchAccountMember() throws Exception {
		return randomAccountMember();
	}

	protected AccountMemberResource accountMemberResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAccountMemberResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.account.resource.v1_0.
		AccountMemberResource _accountMemberResource;

}