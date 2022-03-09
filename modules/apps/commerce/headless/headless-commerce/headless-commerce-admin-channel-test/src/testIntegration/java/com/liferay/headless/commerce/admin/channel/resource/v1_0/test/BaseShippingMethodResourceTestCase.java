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

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.ShippingMethod;
import com.liferay.headless.commerce.admin.channel.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.channel.client.pagination.Page;
import com.liferay.headless.commerce.admin.channel.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.channel.client.resource.v1_0.ShippingMethodResource;
import com.liferay.headless.commerce.admin.channel.client.serdes.v1_0.ShippingMethodSerDes;
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
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public abstract class BaseShippingMethodResourceTestCase {

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

		_shippingMethodResource.setContextCompany(testCompany);

		ShippingMethodResource.Builder builder =
			ShippingMethodResource.builder();

		shippingMethodResource = builder.authentication(
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

		ShippingMethod shippingMethod1 = randomShippingMethod();

		String json = objectMapper.writeValueAsString(shippingMethod1);

		ShippingMethod shippingMethod2 = ShippingMethodSerDes.toDTO(json);

		Assert.assertTrue(equals(shippingMethod1, shippingMethod2));
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

		ShippingMethod shippingMethod = randomShippingMethod();

		String json1 = objectMapper.writeValueAsString(shippingMethod);
		String json2 = ShippingMethodSerDes.toJSON(shippingMethod);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ShippingMethod shippingMethod = randomShippingMethod();

		shippingMethod.setEngineKey(regex);

		String json = ShippingMethodSerDes.toJSON(shippingMethod);

		Assert.assertFalse(json.contains(regex));

		shippingMethod = ShippingMethodSerDes.toDTO(json);

		Assert.assertEquals(regex, shippingMethod.getEngineKey());
	}

	@Test
	public void testGetChannelShippingMethodsPage() throws Exception {
		Long channelId = testGetChannelShippingMethodsPage_getChannelId();
		Long irrelevantChannelId =
			testGetChannelShippingMethodsPage_getIrrelevantChannelId();

		Page<ShippingMethod> page =
			shippingMethodResource.getChannelShippingMethodsPage(
				channelId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantChannelId != null) {
			ShippingMethod irrelevantShippingMethod =
				testGetChannelShippingMethodsPage_addShippingMethod(
					irrelevantChannelId, randomIrrelevantShippingMethod());

			page = shippingMethodResource.getChannelShippingMethodsPage(
				irrelevantChannelId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantShippingMethod),
				(List<ShippingMethod>)page.getItems());
			assertValid(page);
		}

		ShippingMethod shippingMethod1 =
			testGetChannelShippingMethodsPage_addShippingMethod(
				channelId, randomShippingMethod());

		ShippingMethod shippingMethod2 =
			testGetChannelShippingMethodsPage_addShippingMethod(
				channelId, randomShippingMethod());

		page = shippingMethodResource.getChannelShippingMethodsPage(
			channelId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(shippingMethod1, shippingMethod2),
			(List<ShippingMethod>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetChannelShippingMethodsPageWithPagination()
		throws Exception {

		Long channelId = testGetChannelShippingMethodsPage_getChannelId();

		ShippingMethod shippingMethod1 =
			testGetChannelShippingMethodsPage_addShippingMethod(
				channelId, randomShippingMethod());

		ShippingMethod shippingMethod2 =
			testGetChannelShippingMethodsPage_addShippingMethod(
				channelId, randomShippingMethod());

		ShippingMethod shippingMethod3 =
			testGetChannelShippingMethodsPage_addShippingMethod(
				channelId, randomShippingMethod());

		Page<ShippingMethod> page1 =
			shippingMethodResource.getChannelShippingMethodsPage(
				channelId, Pagination.of(1, 2));

		List<ShippingMethod> shippingMethods1 =
			(List<ShippingMethod>)page1.getItems();

		Assert.assertEquals(
			shippingMethods1.toString(), 2, shippingMethods1.size());

		Page<ShippingMethod> page2 =
			shippingMethodResource.getChannelShippingMethodsPage(
				channelId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ShippingMethod> shippingMethods2 =
			(List<ShippingMethod>)page2.getItems();

		Assert.assertEquals(
			shippingMethods2.toString(), 1, shippingMethods2.size());

		Page<ShippingMethod> page3 =
			shippingMethodResource.getChannelShippingMethodsPage(
				channelId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(shippingMethod1, shippingMethod2, shippingMethod3),
			(List<ShippingMethod>)page3.getItems());
	}

	protected ShippingMethod
			testGetChannelShippingMethodsPage_addShippingMethod(
				Long channelId, ShippingMethod shippingMethod)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetChannelShippingMethodsPage_getChannelId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetChannelShippingMethodsPage_getIrrelevantChannelId()
		throws Exception {

		return null;
	}

	protected ShippingMethod testGraphQLShippingMethod_addShippingMethod()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		ShippingMethod shippingMethod, List<ShippingMethod> shippingMethods) {

		boolean contains = false;

		for (ShippingMethod item : shippingMethods) {
			if (equals(shippingMethod, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			shippingMethods + " does not contain " + shippingMethod, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ShippingMethod shippingMethod1, ShippingMethod shippingMethod2) {

		Assert.assertTrue(
			shippingMethod1 + " does not equal " + shippingMethod2,
			equals(shippingMethod1, shippingMethod2));
	}

	protected void assertEquals(
		List<ShippingMethod> shippingMethods1,
		List<ShippingMethod> shippingMethods2) {

		Assert.assertEquals(shippingMethods1.size(), shippingMethods2.size());

		for (int i = 0; i < shippingMethods1.size(); i++) {
			ShippingMethod shippingMethod1 = shippingMethods1.get(i);
			ShippingMethod shippingMethod2 = shippingMethods2.get(i);

			assertEquals(shippingMethod1, shippingMethod2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ShippingMethod> shippingMethods1,
		List<ShippingMethod> shippingMethods2) {

		Assert.assertEquals(shippingMethods1.size(), shippingMethods2.size());

		for (ShippingMethod shippingMethod1 : shippingMethods1) {
			boolean contains = false;

			for (ShippingMethod shippingMethod2 : shippingMethods2) {
				if (equals(shippingMethod1, shippingMethod2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				shippingMethods2 + " does not contain " + shippingMethod1,
				contains);
		}
	}

	protected void assertValid(ShippingMethod shippingMethod) throws Exception {
		boolean valid = true;

		if (shippingMethod.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (shippingMethod.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (shippingMethod.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("engineKey", additionalAssertFieldName)) {
				if (shippingMethod.getEngineKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (shippingMethod.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (shippingMethod.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingOptions", additionalAssertFieldName)) {
				if (shippingMethod.getShippingOptions() == null) {
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

	protected void assertValid(Page<ShippingMethod> page) {
		boolean valid = false;

		java.util.Collection<ShippingMethod> shippingMethods = page.getItems();

		int size = shippingMethods.size();

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
					com.liferay.headless.commerce.admin.channel.dto.v1_0.
						ShippingMethod.class)) {

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
		ShippingMethod shippingMethod1, ShippingMethod shippingMethod2) {

		if (shippingMethod1 == shippingMethod2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shippingMethod1.getActive(),
						shippingMethod2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!equals(
						(Map)shippingMethod1.getDescription(),
						(Map)shippingMethod2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("engineKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shippingMethod1.getEngineKey(),
						shippingMethod2.getEngineKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shippingMethod1.getId(), shippingMethod2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)shippingMethod1.getName(),
						(Map)shippingMethod2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shippingMethod1.getPriority(),
						shippingMethod2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingOptions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shippingMethod1.getShippingOptions(),
						shippingMethod2.getShippingOptions())) {

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

		if (!(_shippingMethodResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_shippingMethodResource;

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
		ShippingMethod shippingMethod) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("engineKey")) {
			sb.append("'");
			sb.append(String.valueOf(shippingMethod.getEngineKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			sb.append(String.valueOf(shippingMethod.getPriority()));

			return sb.toString();
		}

		if (entityFieldName.equals("shippingOptions")) {
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

	protected ShippingMethod randomShippingMethod() throws Exception {
		return new ShippingMethod() {
			{
				active = RandomTestUtil.randomBoolean();
				engineKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	protected ShippingMethod randomIrrelevantShippingMethod() throws Exception {
		ShippingMethod randomIrrelevantShippingMethod = randomShippingMethod();

		return randomIrrelevantShippingMethod;
	}

	protected ShippingMethod randomPatchShippingMethod() throws Exception {
		return randomShippingMethod();
	}

	protected ShippingMethodResource shippingMethodResource;
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
		LogFactoryUtil.getLog(BaseShippingMethodResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.channel.resource.v1_0.
		ShippingMethodResource _shippingMethodResource;

}