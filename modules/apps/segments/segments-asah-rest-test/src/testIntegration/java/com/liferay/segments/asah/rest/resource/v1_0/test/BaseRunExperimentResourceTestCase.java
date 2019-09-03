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

package com.liferay.segments.asah.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.segments.asah.rest.client.dto.v1_0.RunExperiment;
import com.liferay.segments.asah.rest.client.http.HttpInvoker;
import com.liferay.segments.asah.rest.client.pagination.Page;
import com.liferay.segments.asah.rest.client.resource.v1_0.RunExperimentResource;
import com.liferay.segments.asah.rest.client.serdes.v1_0.RunExperimentSerDes;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseRunExperimentResourceTestCase {

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

		_runExperimentResource.setContextCompany(testCompany);

		RunExperimentResource.Builder builder = RunExperimentResource.builder();

		runExperimentResource = builder.locale(
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

		RunExperiment runExperiment1 = randomRunExperiment();

		String json = objectMapper.writeValueAsString(runExperiment1);

		RunExperiment runExperiment2 = RunExperimentSerDes.toDTO(json);

		Assert.assertTrue(equals(runExperiment1, runExperiment2));
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

		RunExperiment runExperiment = randomRunExperiment();

		String json1 = objectMapper.writeValueAsString(runExperiment);
		String json2 = RunExperimentSerDes.toJSON(runExperiment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		RunExperiment runExperiment = randomRunExperiment();

		runExperiment.setStatus(regex);

		String json = RunExperimentSerDes.toJSON(runExperiment);

		Assert.assertFalse(json.contains(regex));

		runExperiment = RunExperimentSerDes.toDTO(json);

		Assert.assertEquals(regex, runExperiment.getStatus());
	}

	@Test
	public void testPostExperimentRun() throws Exception {
		RunExperiment randomRunExperiment = randomRunExperiment();

		RunExperiment postRunExperiment =
			testPostExperimentRun_addRunExperiment(randomRunExperiment);

		assertEquals(randomRunExperiment, postRunExperiment);
		assertValid(postRunExperiment);
	}

	protected RunExperiment testPostExperimentRun_addRunExperiment(
			RunExperiment runExperiment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected RunExperiment testGraphQLRunExperiment_addRunExperiment()
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
		RunExperiment runExperiment1, RunExperiment runExperiment2) {

		Assert.assertTrue(
			runExperiment1 + " does not equal " + runExperiment2,
			equals(runExperiment1, runExperiment2));
	}

	protected void assertEquals(
		List<RunExperiment> runExperiments1,
		List<RunExperiment> runExperiments2) {

		Assert.assertEquals(runExperiments1.size(), runExperiments2.size());

		for (int i = 0; i < runExperiments1.size(); i++) {
			RunExperiment runExperiment1 = runExperiments1.get(i);
			RunExperiment runExperiment2 = runExperiments2.get(i);

			assertEquals(runExperiment1, runExperiment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<RunExperiment> runExperiments1,
		List<RunExperiment> runExperiments2) {

		Assert.assertEquals(runExperiments1.size(), runExperiments2.size());

		for (RunExperiment runExperiment1 : runExperiments1) {
			boolean contains = false;

			for (RunExperiment runExperiment2 : runExperiments2) {
				if (equals(runExperiment1, runExperiment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				runExperiments2 + " does not contain " + runExperiment1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<RunExperiment> runExperiments, JSONArray jsonArray) {

		for (RunExperiment runExperiment : runExperiments) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(runExperiment, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + runExperiment, contains);
		}
	}

	protected void assertValid(RunExperiment runExperiment) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("confidenceLevel", additionalAssertFieldName)) {
				if (runExperiment.getConfidenceLevel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (runExperiment.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("variants", additionalAssertFieldName)) {
				if (runExperiment.getVariants() == null) {
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

	protected void assertValid(Page<RunExperiment> page) {
		boolean valid = false;

		java.util.Collection<RunExperiment> runExperiments = page.getItems();

		int size = runExperiments.size();

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

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		RunExperiment runExperiment1, RunExperiment runExperiment2) {

		if (runExperiment1 == runExperiment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("confidenceLevel", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						runExperiment1.getConfidenceLevel(),
						runExperiment2.getConfidenceLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						runExperiment1.getStatus(),
						runExperiment2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("variants", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						runExperiment1.getVariants(),
						runExperiment2.getVariants())) {

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

	protected boolean equalsJSONObject(
		RunExperiment runExperiment, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("confidenceLevel", fieldName)) {
				if (!Objects.equals(
						runExperiment.getConfidenceLevel(),
						(Double)jsonObject.getDouble("confidenceLevel"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", fieldName)) {
				if (!Objects.equals(
						runExperiment.getStatus(),
						(String)jsonObject.getString("status"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_runExperimentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_runExperimentResource;

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
		EntityField entityField, String operator, RunExperiment runExperiment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("confidenceLevel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			sb.append("'");
			sb.append(String.valueOf(runExperiment.getStatus()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("variants")) {
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

	protected RunExperiment randomRunExperiment() throws Exception {
		return new RunExperiment() {
			{
				confidenceLevel = RandomTestUtil.randomDouble();
				status = RandomTestUtil.randomString();
			}
		};
	}

	protected RunExperiment randomIrrelevantRunExperiment() throws Exception {
		RunExperiment randomIrrelevantRunExperiment = randomRunExperiment();

		return randomIrrelevantRunExperiment;
	}

	protected RunExperiment randomPatchRunExperiment() throws Exception {
		return randomRunExperiment();
	}

	protected RunExperimentResource runExperimentResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

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

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRunExperimentResourceTestCase.class);

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
	private com.liferay.segments.asah.rest.resource.v1_0.RunExperimentResource
		_runExperimentResource;

}