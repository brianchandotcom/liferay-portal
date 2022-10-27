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

package com.liferay.analytics.settings.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.analytics.settings.rest.client.dto.v1_0.ContactUserGroup;
import com.liferay.analytics.settings.rest.client.http.HttpInvoker;
import com.liferay.analytics.settings.rest.client.pagination.Page;
import com.liferay.analytics.settings.rest.client.pagination.Pagination;
import com.liferay.analytics.settings.rest.client.resource.v1_0.ContactUserGroupResource;
import com.liferay.analytics.settings.rest.client.serdes.v1_0.ContactUserGroupSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public abstract class BaseContactUserGroupResourceTestCase {

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

		_contactUserGroupResource.setContextCompany(testCompany);

		ContactUserGroupResource.Builder builder =
			ContactUserGroupResource.builder();

		contactUserGroupResource = builder.authentication(
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

		ContactUserGroup contactUserGroup1 = randomContactUserGroup();

		String json = objectMapper.writeValueAsString(contactUserGroup1);

		ContactUserGroup contactUserGroup2 = ContactUserGroupSerDes.toDTO(json);

		Assert.assertTrue(equals(contactUserGroup1, contactUserGroup2));
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

		ContactUserGroup contactUserGroup = randomContactUserGroup();

		String json1 = objectMapper.writeValueAsString(contactUserGroup);
		String json2 = ContactUserGroupSerDes.toJSON(contactUserGroup);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ContactUserGroup contactUserGroup = randomContactUserGroup();

		contactUserGroup.setName(regex);

		String json = ContactUserGroupSerDes.toJSON(contactUserGroup);

		Assert.assertFalse(json.contains(regex));

		contactUserGroup = ContactUserGroupSerDes.toDTO(json);

		Assert.assertEquals(regex, contactUserGroup.getName());
	}

	@Test
	public void testGetContactUserGroupsPage() throws Exception {
		Page<ContactUserGroup> page =
			contactUserGroupResource.getContactUserGroupsPage(
				RandomTestUtil.randomString(), Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		ContactUserGroup contactUserGroup1 =
			testGetContactUserGroupsPage_addContactUserGroup(
				randomContactUserGroup());

		ContactUserGroup contactUserGroup2 =
			testGetContactUserGroupsPage_addContactUserGroup(
				randomContactUserGroup());

		page = contactUserGroupResource.getContactUserGroupsPage(
			null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			contactUserGroup1, (List<ContactUserGroup>)page.getItems());
		assertContains(
			contactUserGroup2, (List<ContactUserGroup>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContactUserGroupsPageWithPagination() throws Exception {
		Page<ContactUserGroup> totalPage =
			contactUserGroupResource.getContactUserGroupsPage(null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		ContactUserGroup contactUserGroup1 =
			testGetContactUserGroupsPage_addContactUserGroup(
				randomContactUserGroup());

		ContactUserGroup contactUserGroup2 =
			testGetContactUserGroupsPage_addContactUserGroup(
				randomContactUserGroup());

		ContactUserGroup contactUserGroup3 =
			testGetContactUserGroupsPage_addContactUserGroup(
				randomContactUserGroup());

		Page<ContactUserGroup> page1 =
			contactUserGroupResource.getContactUserGroupsPage(
				null, Pagination.of(1, totalCount + 2), null);

		List<ContactUserGroup> contactUserGroups1 =
			(List<ContactUserGroup>)page1.getItems();

		Assert.assertEquals(
			contactUserGroups1.toString(), totalCount + 2,
			contactUserGroups1.size());

		Page<ContactUserGroup> page2 =
			contactUserGroupResource.getContactUserGroupsPage(
				null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<ContactUserGroup> contactUserGroups2 =
			(List<ContactUserGroup>)page2.getItems();

		Assert.assertEquals(
			contactUserGroups2.toString(), 1, contactUserGroups2.size());

		Page<ContactUserGroup> page3 =
			contactUserGroupResource.getContactUserGroupsPage(
				null, Pagination.of(1, totalCount + 3), null);

		assertContains(
			contactUserGroup1, (List<ContactUserGroup>)page3.getItems());
		assertContains(
			contactUserGroup2, (List<ContactUserGroup>)page3.getItems());
		assertContains(
			contactUserGroup3, (List<ContactUserGroup>)page3.getItems());
	}

	@Test
	public void testGetContactUserGroupsPageWithSortDateTime()
		throws Exception {

		testGetContactUserGroupsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, contactUserGroup1, contactUserGroup2) -> {
				BeanTestUtil.setProperty(
					contactUserGroup1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetContactUserGroupsPageWithSortDouble() throws Exception {
		testGetContactUserGroupsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, contactUserGroup1, contactUserGroup2) -> {
				BeanTestUtil.setProperty(
					contactUserGroup1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					contactUserGroup2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetContactUserGroupsPageWithSortInteger() throws Exception {
		testGetContactUserGroupsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, contactUserGroup1, contactUserGroup2) -> {
				BeanTestUtil.setProperty(
					contactUserGroup1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					contactUserGroup2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetContactUserGroupsPageWithSortString() throws Exception {
		testGetContactUserGroupsPageWithSort(
			EntityField.Type.STRING,
			(entityField, contactUserGroup1, contactUserGroup2) -> {
				Class<?> clazz = contactUserGroup1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						contactUserGroup1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						contactUserGroup2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						contactUserGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						contactUserGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						contactUserGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						contactUserGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetContactUserGroupsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, ContactUserGroup, ContactUserGroup, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		ContactUserGroup contactUserGroup1 = randomContactUserGroup();
		ContactUserGroup contactUserGroup2 = randomContactUserGroup();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, contactUserGroup1, contactUserGroup2);
		}

		contactUserGroup1 = testGetContactUserGroupsPage_addContactUserGroup(
			contactUserGroup1);

		contactUserGroup2 = testGetContactUserGroupsPage_addContactUserGroup(
			contactUserGroup2);

		for (EntityField entityField : entityFields) {
			Page<ContactUserGroup> ascPage =
				contactUserGroupResource.getContactUserGroupsPage(
					null, Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contactUserGroup1, contactUserGroup2),
				(List<ContactUserGroup>)ascPage.getItems());

			Page<ContactUserGroup> descPage =
				contactUserGroupResource.getContactUserGroupsPage(
					null, Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contactUserGroup2, contactUserGroup1),
				(List<ContactUserGroup>)descPage.getItems());
		}
	}

	protected ContactUserGroup testGetContactUserGroupsPage_addContactUserGroup(
			ContactUserGroup contactUserGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetContactUserGroupsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"contactUserGroups",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject contactUserGroupsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contactUserGroups");

		long totalCount = contactUserGroupsJSONObject.getLong("totalCount");

		ContactUserGroup contactUserGroup1 =
			testGraphQLGetContactUserGroupsPage_addContactUserGroup();
		ContactUserGroup contactUserGroup2 =
			testGraphQLGetContactUserGroupsPage_addContactUserGroup();

		contactUserGroupsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contactUserGroups");

		Assert.assertEquals(
			totalCount + 2, contactUserGroupsJSONObject.getLong("totalCount"));

		assertContains(
			contactUserGroup1,
			Arrays.asList(
				ContactUserGroupSerDes.toDTOs(
					contactUserGroupsJSONObject.getString("items"))));
		assertContains(
			contactUserGroup2,
			Arrays.asList(
				ContactUserGroupSerDes.toDTOs(
					contactUserGroupsJSONObject.getString("items"))));
	}

	protected ContactUserGroup
			testGraphQLGetContactUserGroupsPage_addContactUserGroup()
		throws Exception {

		return testGraphQLContactUserGroup_addContactUserGroup();
	}

	protected ContactUserGroup testGraphQLContactUserGroup_addContactUserGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		ContactUserGroup contactUserGroup,
		List<ContactUserGroup> contactUserGroups) {

		boolean contains = false;

		for (ContactUserGroup item : contactUserGroups) {
			if (equals(contactUserGroup, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			contactUserGroups + " does not contain " + contactUserGroup,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ContactUserGroup contactUserGroup1,
		ContactUserGroup contactUserGroup2) {

		Assert.assertTrue(
			contactUserGroup1 + " does not equal " + contactUserGroup2,
			equals(contactUserGroup1, contactUserGroup2));
	}

	protected void assertEquals(
		List<ContactUserGroup> contactUserGroups1,
		List<ContactUserGroup> contactUserGroups2) {

		Assert.assertEquals(
			contactUserGroups1.size(), contactUserGroups2.size());

		for (int i = 0; i < contactUserGroups1.size(); i++) {
			ContactUserGroup contactUserGroup1 = contactUserGroups1.get(i);
			ContactUserGroup contactUserGroup2 = contactUserGroups2.get(i);

			assertEquals(contactUserGroup1, contactUserGroup2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContactUserGroup> contactUserGroups1,
		List<ContactUserGroup> contactUserGroups2) {

		Assert.assertEquals(
			contactUserGroups1.size(), contactUserGroups2.size());

		for (ContactUserGroup contactUserGroup1 : contactUserGroups1) {
			boolean contains = false;

			for (ContactUserGroup contactUserGroup2 : contactUserGroups2) {
				if (equals(contactUserGroup1, contactUserGroup2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contactUserGroups2 + " does not contain " + contactUserGroup1,
				contains);
		}
	}

	protected void assertValid(ContactUserGroup contactUserGroup)
		throws Exception {

		boolean valid = true;

		if (contactUserGroup.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (contactUserGroup.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("selected", additionalAssertFieldName)) {
				if (contactUserGroup.getSelected() == null) {
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

	protected void assertValid(Page<ContactUserGroup> page) {
		boolean valid = false;

		java.util.Collection<ContactUserGroup> contactUserGroups =
			page.getItems();

		int size = contactUserGroups.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.analytics.settings.rest.dto.v1_0.
						ContactUserGroup.class)) {

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
		ContactUserGroup contactUserGroup1,
		ContactUserGroup contactUserGroup2) {

		if (contactUserGroup1 == contactUserGroup2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contactUserGroup1.getId(), contactUserGroup2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contactUserGroup1.getName(),
						contactUserGroup2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("selected", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contactUserGroup1.getSelected(),
						contactUserGroup2.getSelected())) {

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

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_contactUserGroupResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contactUserGroupResource;

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
		EntityField entityField, String operator,
		ContactUserGroup contactUserGroup) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(contactUserGroup.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("selected")) {
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

	protected ContactUserGroup randomContactUserGroup() throws Exception {
		return new ContactUserGroup() {
			{
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				selected = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected ContactUserGroup randomIrrelevantContactUserGroup()
		throws Exception {

		ContactUserGroup randomIrrelevantContactUserGroup =
			randomContactUserGroup();

		return randomIrrelevantContactUserGroup;
	}

	protected ContactUserGroup randomPatchContactUserGroup() throws Exception {
		return randomContactUserGroup();
	}

	protected ContactUserGroupResource contactUserGroupResource;
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
		LogFactoryUtil.getLog(BaseContactUserGroupResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.analytics.settings.rest.resource.v1_0.
			ContactUserGroupResource _contactUserGroupResource;

}