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

package com.liferay.headless.admin.list.type.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.client.http.HttpInvoker;
import com.liferay.headless.admin.list.type.client.pagination.Page;
import com.liferay.headless.admin.list.type.client.pagination.Pagination;
import com.liferay.headless.admin.list.type.client.resource.v1_0.ListTypeEntryResource;
import com.liferay.headless.admin.list.type.client.serdes.v1_0.ListTypeEntrySerDes;
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
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public abstract class BaseListTypeEntryResourceTestCase {

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

		_listTypeEntryResource.setContextCompany(testCompany);

		ListTypeEntryResource.Builder builder = ListTypeEntryResource.builder();

		listTypeEntryResource = builder.authentication(
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

		ListTypeEntry listTypeEntry1 = randomListTypeEntry();

		String json = objectMapper.writeValueAsString(listTypeEntry1);

		ListTypeEntry listTypeEntry2 = ListTypeEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(listTypeEntry1, listTypeEntry2));
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

		ListTypeEntry listTypeEntry = randomListTypeEntry();

		String json1 = objectMapper.writeValueAsString(listTypeEntry);
		String json2 = ListTypeEntrySerDes.toJSON(listTypeEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ListTypeEntry listTypeEntry = randomListTypeEntry();

		listTypeEntry.setKey(regex);
		listTypeEntry.setType(regex);

		String json = ListTypeEntrySerDes.toJSON(listTypeEntry);

		Assert.assertFalse(json.contains(regex));

		listTypeEntry = ListTypeEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, listTypeEntry.getKey());
		Assert.assertEquals(regex, listTypeEntry.getType());
	}

	@Test
	public void testGetListTypeDefinitionListTypeEntriesPage()
		throws Exception {

		Page<ListTypeEntry> page =
			listTypeEntryResource.getListTypeDefinitionListTypeEntriesPage(
				testGetListTypeDefinitionListTypeEntriesPage_getListTypeDefinitionId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long listTypeDefinitionId =
			testGetListTypeDefinitionListTypeEntriesPage_getListTypeDefinitionId();
		Long irrelevantListTypeDefinitionId =
			testGetListTypeDefinitionListTypeEntriesPage_getIrrelevantListTypeDefinitionId();

		if (irrelevantListTypeDefinitionId != null) {
			ListTypeEntry irrelevantListTypeEntry =
				testGetListTypeDefinitionListTypeEntriesPage_addListTypeEntry(
					irrelevantListTypeDefinitionId,
					randomIrrelevantListTypeEntry());

			page =
				listTypeEntryResource.getListTypeDefinitionListTypeEntriesPage(
					irrelevantListTypeDefinitionId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantListTypeEntry),
				(List<ListTypeEntry>)page.getItems());
			assertValid(page);
		}

		ListTypeEntry listTypeEntry1 =
			testGetListTypeDefinitionListTypeEntriesPage_addListTypeEntry(
				listTypeDefinitionId, randomListTypeEntry());

		ListTypeEntry listTypeEntry2 =
			testGetListTypeDefinitionListTypeEntriesPage_addListTypeEntry(
				listTypeDefinitionId, randomListTypeEntry());

		page = listTypeEntryResource.getListTypeDefinitionListTypeEntriesPage(
			listTypeDefinitionId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(listTypeEntry1, listTypeEntry2),
			(List<ListTypeEntry>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetListTypeDefinitionListTypeEntriesPageWithPagination()
		throws Exception {

		Long listTypeDefinitionId =
			testGetListTypeDefinitionListTypeEntriesPage_getListTypeDefinitionId();

		ListTypeEntry listTypeEntry1 =
			testGetListTypeDefinitionListTypeEntriesPage_addListTypeEntry(
				listTypeDefinitionId, randomListTypeEntry());

		ListTypeEntry listTypeEntry2 =
			testGetListTypeDefinitionListTypeEntriesPage_addListTypeEntry(
				listTypeDefinitionId, randomListTypeEntry());

		ListTypeEntry listTypeEntry3 =
			testGetListTypeDefinitionListTypeEntriesPage_addListTypeEntry(
				listTypeDefinitionId, randomListTypeEntry());

		Page<ListTypeEntry> page1 =
			listTypeEntryResource.getListTypeDefinitionListTypeEntriesPage(
				listTypeDefinitionId, Pagination.of(1, 2));

		List<ListTypeEntry> listTypeEntries1 =
			(List<ListTypeEntry>)page1.getItems();

		Assert.assertEquals(
			listTypeEntries1.toString(), 2, listTypeEntries1.size());

		Page<ListTypeEntry> page2 =
			listTypeEntryResource.getListTypeDefinitionListTypeEntriesPage(
				listTypeDefinitionId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ListTypeEntry> listTypeEntries2 =
			(List<ListTypeEntry>)page2.getItems();

		Assert.assertEquals(
			listTypeEntries2.toString(), 1, listTypeEntries2.size());

		Page<ListTypeEntry> page3 =
			listTypeEntryResource.getListTypeDefinitionListTypeEntriesPage(
				listTypeDefinitionId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(listTypeEntry1, listTypeEntry2, listTypeEntry3),
			(List<ListTypeEntry>)page3.getItems());
	}

	protected ListTypeEntry
			testGetListTypeDefinitionListTypeEntriesPage_addListTypeEntry(
				Long listTypeDefinitionId, ListTypeEntry listTypeEntry)
		throws Exception {

		return listTypeEntryResource.postListTypeDefinitionListTypeEntry(
			listTypeDefinitionId, listTypeEntry);
	}

	protected Long
			testGetListTypeDefinitionListTypeEntriesPage_getListTypeDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetListTypeDefinitionListTypeEntriesPage_getIrrelevantListTypeDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostListTypeDefinitionListTypeEntry() throws Exception {
		ListTypeEntry randomListTypeEntry = randomListTypeEntry();

		ListTypeEntry postListTypeEntry =
			testPostListTypeDefinitionListTypeEntry_addListTypeEntry(
				randomListTypeEntry);

		assertEquals(randomListTypeEntry, postListTypeEntry);
		assertValid(postListTypeEntry);
	}

	protected ListTypeEntry
			testPostListTypeDefinitionListTypeEntry_addListTypeEntry(
				ListTypeEntry listTypeEntry)
		throws Exception {

		return listTypeEntryResource.postListTypeDefinitionListTypeEntry(
			testGetListTypeDefinitionListTypeEntriesPage_getListTypeDefinitionId(),
			listTypeEntry);
	}

	protected ListTypeEntry testGraphQLListTypeEntry_addListTypeEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ListTypeEntry listTypeEntry1, ListTypeEntry listTypeEntry2) {

		Assert.assertTrue(
			listTypeEntry1 + " does not equal " + listTypeEntry2,
			equals(listTypeEntry1, listTypeEntry2));
	}

	protected void assertEquals(
		List<ListTypeEntry> listTypeEntries1,
		List<ListTypeEntry> listTypeEntries2) {

		Assert.assertEquals(listTypeEntries1.size(), listTypeEntries2.size());

		for (int i = 0; i < listTypeEntries1.size(); i++) {
			ListTypeEntry listTypeEntry1 = listTypeEntries1.get(i);
			ListTypeEntry listTypeEntry2 = listTypeEntries2.get(i);

			assertEquals(listTypeEntry1, listTypeEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ListTypeEntry> listTypeEntries1,
		List<ListTypeEntry> listTypeEntries2) {

		Assert.assertEquals(listTypeEntries1.size(), listTypeEntries2.size());

		for (ListTypeEntry listTypeEntry1 : listTypeEntries1) {
			boolean contains = false;

			for (ListTypeEntry listTypeEntry2 : listTypeEntries2) {
				if (equals(listTypeEntry1, listTypeEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				listTypeEntries2 + " does not contain " + listTypeEntry1,
				contains);
		}
	}

	protected void assertValid(ListTypeEntry listTypeEntry) throws Exception {
		boolean valid = true;

		if (listTypeEntry.getDateCreated() == null) {
			valid = false;
		}

		if (listTypeEntry.getDateModified() == null) {
			valid = false;
		}

		if (listTypeEntry.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (listTypeEntry.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (listTypeEntry.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (listTypeEntry.getType() == null) {
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

	protected void assertValid(Page<ListTypeEntry> page) {
		boolean valid = false;

		java.util.Collection<ListTypeEntry> listTypeEntries = page.getItems();

		int size = listTypeEntries.size();

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
				getDeclaredFields(
					com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry.
						class)) {

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
		ListTypeEntry listTypeEntry1, ListTypeEntry listTypeEntry2) {

		if (listTypeEntry1 == listTypeEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeEntry1.getDateCreated(),
						listTypeEntry2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeEntry1.getDateModified(),
						listTypeEntry2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeEntry1.getId(), listTypeEntry2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeEntry1.getKey(), listTypeEntry2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)listTypeEntry1.getName(),
						(Map)listTypeEntry2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeEntry1.getType(), listTypeEntry2.getType())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_listTypeEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_listTypeEntryResource;

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
		EntityField entityField, String operator, ListTypeEntry listTypeEntry) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							listTypeEntry.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							listTypeEntry.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(listTypeEntry.getDateCreated()));
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
						DateUtils.addSeconds(
							listTypeEntry.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							listTypeEntry.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(listTypeEntry.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(listTypeEntry.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(listTypeEntry.getType()));
			sb.append("'");

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

	protected ListTypeEntry randomListTypeEntry() throws Exception {
		return new ListTypeEntry() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected ListTypeEntry randomIrrelevantListTypeEntry() throws Exception {
		ListTypeEntry randomIrrelevantListTypeEntry = randomListTypeEntry();

		return randomIrrelevantListTypeEntry;
	}

	protected ListTypeEntry randomPatchListTypeEntry() throws Exception {
		return randomListTypeEntry();
	}

	protected ListTypeEntryResource listTypeEntryResource;
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
		LogFactoryUtil.getLog(BaseListTypeEntryResourceTestCase.class);

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
	private
		com.liferay.headless.admin.list.type.resource.v1_0.ListTypeEntryResource
			_listTypeEntryResource;

}