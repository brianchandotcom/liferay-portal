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

package com.liferay.headless.admin.content.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.content.client.dto.v1_0.StructuredContent;
import com.liferay.headless.admin.content.client.http.HttpInvoker;
import com.liferay.headless.admin.content.client.pagination.Page;
import com.liferay.headless.admin.content.client.pagination.Pagination;
import com.liferay.headless.admin.content.client.resource.v1_0.StructuredContentResource;
import com.liferay.headless.admin.content.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseStructuredContentResourceTestCase {

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

		_structuredContentResource.setContextCompany(testCompany);

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		structuredContentResource = builder.authentication(
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

		StructuredContent structuredContent1 = randomStructuredContent();

		String json = objectMapper.writeValueAsString(structuredContent1);

		StructuredContent structuredContent2 = StructuredContentSerDes.toDTO(
			json);

		Assert.assertTrue(equals(structuredContent1, structuredContent2));
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

		StructuredContent structuredContent = randomStructuredContent();

		String json1 = objectMapper.writeValueAsString(structuredContent);
		String json2 = StructuredContentSerDes.toJSON(structuredContent);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		StructuredContent structuredContent = randomStructuredContent();

		String json = StructuredContentSerDes.toJSON(structuredContent);

		Assert.assertFalse(json.contains(regex));

		structuredContent = StructuredContentSerDes.toDTO(json);
	}

	@Test
	public void testGetSiteStructuredContentsPage() throws Exception {
		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGetSiteStructuredContentsPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteStructuredContentsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetSiteStructuredContentsPage_addStructuredContent(
					irrelevantSiteId, randomIrrelevantStructuredContent());

			page = structuredContentResource.getSiteStructuredContentsPage(
				irrelevantSiteId, null, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		page = structuredContentResource.getSiteStructuredContentsPage(
			siteId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null,
					getFilterString(entityField, "between", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		Page<StructuredContent> page1 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortString()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetSiteStructuredContentsPage_addStructuredContent(
				Long siteId, StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteStructuredContentsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteStructuredContentsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteStructuredContentsPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		Assert.assertTrue(
			structuredContent1 + " does not equal " + structuredContent2,
			equals(structuredContent1, structuredContent2));
	}

	protected void assertEquals(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (int i = 0; i < structuredContents1.size(); i++) {
			StructuredContent structuredContent1 = structuredContents1.get(i);
			StructuredContent structuredContent2 = structuredContents2.get(i);

			assertEquals(structuredContent1, structuredContent2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (StructuredContent structuredContent1 : structuredContents1) {
			boolean contains = false;

			for (StructuredContent structuredContent2 : structuredContents2) {
				if (equals(structuredContent1, structuredContent2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				structuredContents2 + " does not contain " + structuredContent1,
				contains);
		}
	}

	protected void assertValid(StructuredContent structuredContent)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<StructuredContent> page) {
		boolean valid = false;

		java.util.Collection<StructuredContent> structuredContents =
			page.getItems();

		int size = structuredContents.size();

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
					com.liferay.headless.delivery.dto.v1_0.StructuredContent.
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
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		if (structuredContent1 == structuredContent2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_structuredContentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_structuredContentResource;

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
		StructuredContent structuredContent) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

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

	protected StructuredContent randomStructuredContent() throws Exception {
		return new StructuredContent() {
			{
			}
		};
	}

	protected StructuredContent randomIrrelevantStructuredContent()
		throws Exception {

		StructuredContent randomIrrelevantStructuredContent =
			randomStructuredContent();

		return randomIrrelevantStructuredContent;
	}

	protected StructuredContent randomPatchStructuredContent()
		throws Exception {

		return randomStructuredContent();
	}

	protected StructuredContentResource structuredContentResource;
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
		BaseStructuredContentResourceTestCase.class);

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
		com.liferay.headless.admin.content.resource.v1_0.
			StructuredContentResource _structuredContentResource;

}