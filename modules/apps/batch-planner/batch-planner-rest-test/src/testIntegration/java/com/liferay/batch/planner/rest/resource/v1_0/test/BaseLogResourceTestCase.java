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

package com.liferay.batch.planner.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.batch.planner.rest.client.dto.v1_0.Log;
import com.liferay.batch.planner.rest.client.http.HttpInvoker;
import com.liferay.batch.planner.rest.client.pagination.Page;
import com.liferay.batch.planner.rest.client.pagination.Pagination;
import com.liferay.batch.planner.rest.client.resource.v1_0.LogResource;
import com.liferay.batch.planner.rest.client.serdes.v1_0.LogSerDes;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public abstract class BaseLogResourceTestCase {

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

		_logResource.setContextCompany(testCompany);

		LogResource.Builder builder = LogResource.builder();

		logResource = builder.authentication(
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

		Log log1 = randomLog();

		String json = objectMapper.writeValueAsString(log1);

		Log log2 = LogSerDes.toDTO(json);

		Assert.assertTrue(equals(log1, log2));
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

		Log log = randomLog();

		String json1 = objectMapper.writeValueAsString(log);
		String json2 = LogSerDes.toJSON(log);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Log log = randomLog();

		log.setDispatchTriggerExternalReferenceCode(regex);
		log.setExportTaskExternalReferenceCode(regex);
		log.setImportTaskExternalReferenceCode(regex);

		String json = LogSerDes.toJSON(log);

		Assert.assertFalse(json.contains(regex));

		log = LogSerDes.toDTO(json);

		Assert.assertEquals(
			regex, log.getDispatchTriggerExternalReferenceCode());
		Assert.assertEquals(regex, log.getExportTaskExternalReferenceCode());
		Assert.assertEquals(regex, log.getImportTaskExternalReferenceCode());
	}

	@Test
	public void testGetPlanLogsPage() throws Exception {
		Long planId = testGetPlanLogsPage_getPlanId();
		Long irrelevantPlanId = testGetPlanLogsPage_getIrrelevantPlanId();

		Page<Log> page = logResource.getPlanLogsPage(
			planId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantPlanId != null) {
			Log irrelevantLog = testGetPlanLogsPage_addLog(
				irrelevantPlanId, randomIrrelevantLog());

			page = logResource.getPlanLogsPage(
				irrelevantPlanId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantLog), (List<Log>)page.getItems());
			assertValid(page);
		}

		Log log1 = testGetPlanLogsPage_addLog(planId, randomLog());

		Log log2 = testGetPlanLogsPage_addLog(planId, randomLog());

		page = logResource.getPlanLogsPage(planId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(log1, log2), (List<Log>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetPlanLogsPageWithPagination() throws Exception {
		Long planId = testGetPlanLogsPage_getPlanId();

		Log log1 = testGetPlanLogsPage_addLog(planId, randomLog());

		Log log2 = testGetPlanLogsPage_addLog(planId, randomLog());

		Log log3 = testGetPlanLogsPage_addLog(planId, randomLog());

		Page<Log> page1 = logResource.getPlanLogsPage(
			planId, Pagination.of(1, 2));

		List<Log> logs1 = (List<Log>)page1.getItems();

		Assert.assertEquals(logs1.toString(), 2, logs1.size());

		Page<Log> page2 = logResource.getPlanLogsPage(
			planId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Log> logs2 = (List<Log>)page2.getItems();

		Assert.assertEquals(logs2.toString(), 1, logs2.size());

		Page<Log> page3 = logResource.getPlanLogsPage(
			planId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(log1, log2, log3), (List<Log>)page3.getItems());
	}

	protected Log testGetPlanLogsPage_addLog(Long planId, Log log)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPlanLogsPage_getPlanId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPlanLogsPage_getIrrelevantPlanId() throws Exception {
		return null;
	}

	protected Log testGraphQLLog_addLog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Log log, List<Log> logs) {
		boolean contains = false;

		for (Log item : logs) {
			if (equals(log, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(logs + " does not contain " + log, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Log log1, Log log2) {
		Assert.assertTrue(log1 + " does not equal " + log2, equals(log1, log2));
	}

	protected void assertEquals(List<Log> logs1, List<Log> logs2) {
		Assert.assertEquals(logs1.size(), logs2.size());

		for (int i = 0; i < logs1.size(); i++) {
			Log log1 = logs1.get(i);
			Log log2 = logs2.get(i);

			assertEquals(log1, log2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<Log> logs1, List<Log> logs2) {
		Assert.assertEquals(logs1.size(), logs2.size());

		for (Log log1 : logs1) {
			boolean contains = false;

			for (Log log2 : logs2) {
				if (equals(log1, log2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(logs2 + " does not contain " + log1, contains);
		}
	}

	protected void assertValid(Log log) throws Exception {
		boolean valid = true;

		if (log.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dispatchTriggerExternalReferenceCode",
					additionalAssertFieldName)) {

				if (log.getDispatchTriggerExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"exportTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (log.getExportTaskExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"importTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (log.getImportTaskExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("planId", additionalAssertFieldName)) {
				if (log.getPlanId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("size", additionalAssertFieldName)) {
				if (log.getSize() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (log.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("total", additionalAssertFieldName)) {
				if (log.getTotal() == null) {
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

	protected void assertValid(Page<Log> page) {
		boolean valid = false;

		java.util.Collection<Log> logs = page.getItems();

		int size = logs.size();

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
					com.liferay.batch.planner.rest.dto.v1_0.Log.class)) {

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

	protected boolean equals(Log log1, Log log2) {
		if (log1 == log2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dispatchTriggerExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						log1.getDispatchTriggerExternalReferenceCode(),
						log2.getDispatchTriggerExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"exportTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						log1.getExportTaskExternalReferenceCode(),
						log2.getExportTaskExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(log1.getId(), log2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"importTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						log1.getImportTaskExternalReferenceCode(),
						log2.getImportTaskExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("planId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(log1.getPlanId(), log2.getPlanId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("size", additionalAssertFieldName)) {
				if (!Objects.deepEquals(log1.getSize(), log2.getSize())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(log1.getStatus(), log2.getStatus())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("total", additionalAssertFieldName)) {
				if (!Objects.deepEquals(log1.getTotal(), log2.getTotal())) {
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

		if (!(_logResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_logResource;

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
		EntityField entityField, String operator, Log log) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dispatchTriggerExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(log.getDispatchTriggerExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("exportTaskExternalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(log.getExportTaskExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("importTaskExternalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(log.getImportTaskExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("planId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("size")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("total")) {
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

	protected Log randomLog() throws Exception {
		return new Log() {
			{
				dispatchTriggerExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				exportTaskExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				importTaskExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				planId = RandomTestUtil.randomLong();
				size = RandomTestUtil.randomInt();
				status = RandomTestUtil.randomInt();
				total = RandomTestUtil.randomInt();
			}
		};
	}

	protected Log randomIrrelevantLog() throws Exception {
		Log randomIrrelevantLog = randomLog();

		return randomIrrelevantLog;
	}

	protected Log randomPatchLog() throws Exception {
		return randomLog();
	}

	protected LogResource logResource;
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
		LogFactoryUtil.getLog(BaseLogResourceTestCase.class);

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
	private com.liferay.batch.planner.rest.resource.v1_0.LogResource
		_logResource;

}