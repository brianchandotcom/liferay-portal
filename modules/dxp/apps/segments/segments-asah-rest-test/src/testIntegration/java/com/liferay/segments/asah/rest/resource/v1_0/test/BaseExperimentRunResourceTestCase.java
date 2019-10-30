/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
import com.liferay.segments.asah.rest.client.dto.v1_0.ExperimentRun;
import com.liferay.segments.asah.rest.client.http.HttpInvoker;
import com.liferay.segments.asah.rest.client.pagination.Page;
import com.liferay.segments.asah.rest.client.resource.v1_0.ExperimentRunResource;
import com.liferay.segments.asah.rest.client.serdes.v1_0.ExperimentRunSerDes;

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
public abstract class BaseExperimentRunResourceTestCase {

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

		_experimentRunResource.setContextCompany(testCompany);

		ExperimentRunResource.Builder builder = ExperimentRunResource.builder();

		experimentRunResource = builder.locale(
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

		ExperimentRun experimentRun1 = randomExperimentRun();

		String json = objectMapper.writeValueAsString(experimentRun1);

		ExperimentRun experimentRun2 = ExperimentRunSerDes.toDTO(json);

		Assert.assertTrue(equals(experimentRun1, experimentRun2));
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

		ExperimentRun experimentRun = randomExperimentRun();

		String json1 = objectMapper.writeValueAsString(experimentRun);
		String json2 = ExperimentRunSerDes.toJSON(experimentRun);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ExperimentRun experimentRun = randomExperimentRun();

		experimentRun.setStatus(regex);

		String json = ExperimentRunSerDes.toJSON(experimentRun);

		Assert.assertFalse(json.contains(regex));

		experimentRun = ExperimentRunSerDes.toDTO(json);

		Assert.assertEquals(regex, experimentRun.getStatus());
	}

	@Test
	public void testPostExperimentRun() throws Exception {
		ExperimentRun randomExperimentRun = randomExperimentRun();

		ExperimentRun postExperimentRun =
			testPostExperimentRun_addExperimentRun(randomExperimentRun);

		assertEquals(randomExperimentRun, postExperimentRun);
		assertValid(postExperimentRun);
	}

	protected ExperimentRun testPostExperimentRun_addExperimentRun(
			ExperimentRun experimentRun)
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
		ExperimentRun experimentRun1, ExperimentRun experimentRun2) {

		Assert.assertTrue(
			experimentRun1 + " does not equal " + experimentRun2,
			equals(experimentRun1, experimentRun2));
	}

	protected void assertEquals(
		List<ExperimentRun> experimentRuns1,
		List<ExperimentRun> experimentRuns2) {

		Assert.assertEquals(experimentRuns1.size(), experimentRuns2.size());

		for (int i = 0; i < experimentRuns1.size(); i++) {
			ExperimentRun experimentRun1 = experimentRuns1.get(i);
			ExperimentRun experimentRun2 = experimentRuns2.get(i);

			assertEquals(experimentRun1, experimentRun2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ExperimentRun> experimentRuns1,
		List<ExperimentRun> experimentRuns2) {

		Assert.assertEquals(experimentRuns1.size(), experimentRuns2.size());

		for (ExperimentRun experimentRun1 : experimentRuns1) {
			boolean contains = false;

			for (ExperimentRun experimentRun2 : experimentRuns2) {
				if (equals(experimentRun1, experimentRun2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				experimentRuns2 + " does not contain " + experimentRun1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<ExperimentRun> experimentRuns, JSONArray jsonArray) {

		for (ExperimentRun experimentRun : experimentRuns) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(experimentRun, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + experimentRun, contains);
		}
	}

	protected void assertValid(ExperimentRun experimentRun) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("confidenceLevel", additionalAssertFieldName)) {
				if (experimentRun.getConfidenceLevel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"experimentVariants", additionalAssertFieldName)) {

				if (experimentRun.getExperimentVariants() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (experimentRun.getStatus() == null) {
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

	protected void assertValid(Page<ExperimentRun> page) {
		boolean valid = false;

		java.util.Collection<ExperimentRun> experimentRuns = page.getItems();

		int size = experimentRuns.size();

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
		ExperimentRun experimentRun1, ExperimentRun experimentRun2) {

		if (experimentRun1 == experimentRun2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("confidenceLevel", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experimentRun1.getConfidenceLevel(),
						experimentRun2.getConfidenceLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"experimentVariants", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						experimentRun1.getExperimentVariants(),
						experimentRun2.getExperimentVariants())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experimentRun1.getStatus(),
						experimentRun2.getStatus())) {

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
		ExperimentRun experimentRun, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("confidenceLevel", fieldName)) {
				if (!Objects.deepEquals(
						experimentRun.getConfidenceLevel(),
						jsonObject.getDouble("confidenceLevel"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", fieldName)) {
				if (!Objects.deepEquals(
						experimentRun.getStatus(),
						jsonObject.getString("status"))) {

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

		if (!(_experimentRunResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_experimentRunResource;

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
		EntityField entityField, String operator, ExperimentRun experimentRun) {

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

		if (entityFieldName.equals("experimentVariants")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			sb.append("'");
			sb.append(String.valueOf(experimentRun.getStatus()));
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

	protected ExperimentRun randomExperimentRun() throws Exception {
		return new ExperimentRun() {
			{
				confidenceLevel = RandomTestUtil.randomDouble();
				status = RandomTestUtil.randomString();
			}
		};
	}

	protected ExperimentRun randomIrrelevantExperimentRun() throws Exception {
		ExperimentRun randomIrrelevantExperimentRun = randomExperimentRun();

		return randomIrrelevantExperimentRun;
	}

	protected ExperimentRun randomPatchExperimentRun() throws Exception {
		return randomExperimentRun();
	}

	protected ExperimentRunResource experimentRunResource;
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

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
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

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExperimentRunResourceTestCase.class);

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
	private com.liferay.segments.asah.rest.resource.v1_0.ExperimentRunResource
		_experimentRunResource;

}