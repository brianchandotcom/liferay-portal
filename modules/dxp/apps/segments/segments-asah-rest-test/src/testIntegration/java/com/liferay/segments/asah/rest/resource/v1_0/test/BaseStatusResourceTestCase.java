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
import com.liferay.segments.asah.rest.client.dto.v1_0.Experiment;
import com.liferay.segments.asah.rest.client.dto.v1_0.Status;
import com.liferay.segments.asah.rest.client.http.HttpInvoker;
import com.liferay.segments.asah.rest.client.pagination.Page;
import com.liferay.segments.asah.rest.client.resource.v1_0.StatusResource;
import com.liferay.segments.asah.rest.client.serdes.v1_0.StatusSerDes;

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
public abstract class BaseStatusResourceTestCase {

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

		_statusResource.setContextCompany(testCompany);

		StatusResource.Builder builder = StatusResource.builder();

		statusResource = builder.locale(
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

		Status status1 = randomStatus();

		String json = objectMapper.writeValueAsString(status1);

		Status status2 = StatusSerDes.toDTO(json);

		Assert.assertTrue(equals(status1, status2));
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

		Status status = randomStatus();

		String json1 = objectMapper.writeValueAsString(status);
		String json2 = StatusSerDes.toJSON(status);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Status status = randomStatus();

		status.setStatus(regex);
		status.setWinnerVariantId(regex);

		String json = StatusSerDes.toJSON(status);

		Assert.assertFalse(json.contains(regex));

		status = StatusSerDes.toDTO(json);

		Assert.assertEquals(regex, status.getStatus());
		Assert.assertEquals(regex, status.getWinnerVariantId());
	}

	@Test
	public void testPostExperimentStatus() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Status status1, Status status2) {
		Assert.assertTrue(
			status1 + " does not equal " + status2, equals(status1, status2));
	}

	protected void assertEquals(
		List<Status> statuses1, List<Status> statuses2) {

		Assert.assertEquals(statuses1.size(), statuses2.size());

		for (int i = 0; i < statuses1.size(); i++) {
			Status status1 = statuses1.get(i);
			Status status2 = statuses2.get(i);

			assertEquals(status1, status2);
		}
	}

	protected void assertEquals(
		Experiment experiment1, Experiment experiment2) {

		Assert.assertTrue(
			experiment1 + " does not equal " + experiment2,
			equals(experiment1, experiment2));
	}

	protected void assertEqualsIgnoringOrder(
		List<Status> statuses1, List<Status> statuses2) {

		Assert.assertEquals(statuses1.size(), statuses2.size());

		for (Status status1 : statuses1) {
			boolean contains = false;

			for (Status status2 : statuses2) {
				if (equals(status1, status2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				statuses2 + " does not contain " + status1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Status> statuses, JSONArray jsonArray) {

		for (Status status : statuses) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(status, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + status, contains);
		}
	}

	protected void assertValid(Status status) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (status.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("winnerVariantId", additionalAssertFieldName)) {
				if (status.getWinnerVariantId() == null) {
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

	protected void assertValid(Page<Status> page) {
		boolean valid = false;

		java.util.Collection<Status> statuses = page.getItems();

		int size = statuses.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Experiment experiment) {
		boolean valid = true;

		if (experiment.getDateCreated() == null) {
			valid = false;
		}

		if (experiment.getDateModified() == null) {
			valid = false;
		}

		if (experiment.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(experiment.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalExperimentAssertFieldNames()) {

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (experiment.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (experiment.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (experiment.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("winnerVariantId", additionalAssertFieldName)) {
				if (experiment.getWinnerVariantId() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalExperimentAssertFieldNames() {
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

	protected boolean equals(Status status1, Status status2) {
		if (status1 == status2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						status1.getStatus(), status2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("winnerVariantId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						status1.getWinnerVariantId(),
						status2.getWinnerVariantId())) {

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

	protected boolean equals(Experiment experiment1, Experiment experiment2) {
		if (experiment1 == experiment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalExperimentAssertFieldNames()) {

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getDateCreated(),
						experiment2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getDateModified(),
						experiment2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getDescription(),
						experiment2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getId(), experiment2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getName(), experiment2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getSiteId(), experiment2.getSiteId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getStatus(), experiment2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("winnerVariantId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						experiment1.getWinnerVariantId(),
						experiment2.getWinnerVariantId())) {

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

	protected boolean equalsJSONObject(Status status, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("status", fieldName)) {
				if (!Objects.deepEquals(
						status.getStatus(), jsonObject.getString("status"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("winnerVariantId", fieldName)) {
				if (!Objects.deepEquals(
						status.getWinnerVariantId(),
						jsonObject.getString("winnerVariantId"))) {

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

		if (!(_statusResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_statusResource;

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
		EntityField entityField, String operator, Status status) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("status")) {
			sb.append("'");
			sb.append(String.valueOf(status.getStatus()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("winnerVariantId")) {
			sb.append("'");
			sb.append(String.valueOf(status.getWinnerVariantId()));
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

	protected Status randomStatus() throws Exception {
		return new Status() {
			{
				status = RandomTestUtil.randomString();
				winnerVariantId = RandomTestUtil.randomString();
			}
		};
	}

	protected Status randomIrrelevantStatus() throws Exception {
		Status randomIrrelevantStatus = randomStatus();

		return randomIrrelevantStatus;
	}

	protected Status randomPatchStatus() throws Exception {
		return randomStatus();
	}

	protected Experiment randomExperiment() throws Exception {
		return new Experiment() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				siteId = RandomTestUtil.randomLong();
				status = RandomTestUtil.randomString();
				winnerVariantId = RandomTestUtil.randomLong();
			}
		};
	}

	protected StatusResource statusResource;
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
		BaseStatusResourceTestCase.class);

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
	private com.liferay.segments.asah.rest.resource.v1_0.StatusResource
		_statusResource;

}