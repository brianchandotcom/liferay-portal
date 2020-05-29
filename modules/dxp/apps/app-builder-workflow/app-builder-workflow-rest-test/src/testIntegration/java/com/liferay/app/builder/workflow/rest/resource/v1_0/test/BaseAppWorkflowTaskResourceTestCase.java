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

package com.liferay.app.builder.workflow.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.client.http.HttpInvoker;
import com.liferay.app.builder.workflow.rest.client.pagination.Page;
import com.liferay.app.builder.workflow.rest.client.resource.v1_0.AppWorkflowTaskResource;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowTaskSerDes;
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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseAppWorkflowTaskResourceTestCase {

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

		_appWorkflowTaskResource.setContextCompany(testCompany);

		AppWorkflowTaskResource.Builder builder =
			AppWorkflowTaskResource.builder();

		appWorkflowTaskResource = builder.locale(
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

		AppWorkflowTask appWorkflowTask1 = randomAppWorkflowTask();

		String json = objectMapper.writeValueAsString(appWorkflowTask1);

		AppWorkflowTask appWorkflowTask2 = AppWorkflowTaskSerDes.toDTO(json);

		Assert.assertTrue(equals(appWorkflowTask1, appWorkflowTask2));
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

		AppWorkflowTask appWorkflowTask = randomAppWorkflowTask();

		String json1 = objectMapper.writeValueAsString(appWorkflowTask);
		String json2 = AppWorkflowTaskSerDes.toJSON(appWorkflowTask);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AppWorkflowTask appWorkflowTask = randomAppWorkflowTask();

		appWorkflowTask.setName(regex);

		String json = AppWorkflowTaskSerDes.toJSON(appWorkflowTask);

		Assert.assertFalse(json.contains(regex));

		appWorkflowTask = AppWorkflowTaskSerDes.toDTO(json);

		Assert.assertEquals(regex, appWorkflowTask.getName());
	}

	@Test
	public void testGetAppWorkflowTasksPage() throws Exception {
		Page<AppWorkflowTask> page =
			appWorkflowTaskResource.getAppWorkflowTasksPage(
				testGetAppWorkflowTasksPage_getAppId());

		Assert.assertEquals(0, page.getTotalCount());

		Long appId = testGetAppWorkflowTasksPage_getAppId();
		Long irrelevantAppId = testGetAppWorkflowTasksPage_getIrrelevantAppId();

		if ((irrelevantAppId != null)) {
			AppWorkflowTask irrelevantAppWorkflowTask =
				testGetAppWorkflowTasksPage_addAppWorkflowTask(
					irrelevantAppId, randomIrrelevantAppWorkflowTask());

			page = appWorkflowTaskResource.getAppWorkflowTasksPage(
				irrelevantAppId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAppWorkflowTask),
				(List<AppWorkflowTask>)page.getItems());
			assertValid(page);
		}

		AppWorkflowTask appWorkflowTask1 =
			testGetAppWorkflowTasksPage_addAppWorkflowTask(
				appId, randomAppWorkflowTask());

		AppWorkflowTask appWorkflowTask2 =
			testGetAppWorkflowTasksPage_addAppWorkflowTask(
				appId, randomAppWorkflowTask());

		page = appWorkflowTaskResource.getAppWorkflowTasksPage(appId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(appWorkflowTask1, appWorkflowTask2),
			(List<AppWorkflowTask>)page.getItems());
		assertValid(page);
	}

	protected AppWorkflowTask testGetAppWorkflowTasksPage_addAppWorkflowTask(
			Long appId, AppWorkflowTask appWorkflowTask)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAppWorkflowTasksPage_getAppId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAppWorkflowTasksPage_getIrrelevantAppId()
		throws Exception {

		return null;
	}

	@Test
	public void testGraphQLGetAppWorkflowTasksPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPostAppWorkflowTasks() throws Exception {
		Assert.assertTrue(false);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AppWorkflowTask appWorkflowTask1, AppWorkflowTask appWorkflowTask2) {

		Assert.assertTrue(
			appWorkflowTask1 + " does not equal " + appWorkflowTask2,
			equals(appWorkflowTask1, appWorkflowTask2));
	}

	protected void assertEquals(
		List<AppWorkflowTask> appWorkflowTasks1,
		List<AppWorkflowTask> appWorkflowTasks2) {

		Assert.assertEquals(appWorkflowTasks1.size(), appWorkflowTasks2.size());

		for (int i = 0; i < appWorkflowTasks1.size(); i++) {
			AppWorkflowTask appWorkflowTask1 = appWorkflowTasks1.get(i);
			AppWorkflowTask appWorkflowTask2 = appWorkflowTasks2.get(i);

			assertEquals(appWorkflowTask1, appWorkflowTask2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AppWorkflowTask> appWorkflowTasks1,
		List<AppWorkflowTask> appWorkflowTasks2) {

		Assert.assertEquals(appWorkflowTasks1.size(), appWorkflowTasks2.size());

		for (AppWorkflowTask appWorkflowTask1 : appWorkflowTasks1) {
			boolean contains = false;

			for (AppWorkflowTask appWorkflowTask2 : appWorkflowTasks2) {
				if (equals(appWorkflowTask1, appWorkflowTask2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				appWorkflowTasks2 + " does not contain " + appWorkflowTask1,
				contains);
		}
	}

	protected void assertValid(AppWorkflowTask appWorkflowTask) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("appId", additionalAssertFieldName)) {
				if (appWorkflowTask.getAppId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"appWorkflowActions", additionalAssertFieldName)) {

				if (appWorkflowTask.getAppWorkflowActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutIds", additionalAssertFieldName)) {
				if (appWorkflowTask.getDataLayoutIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (appWorkflowTask.getName() == null) {
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

	protected void assertValid(Page<AppWorkflowTask> page) {
		boolean valid = false;

		java.util.Collection<AppWorkflowTask> appWorkflowTasks =
			page.getItems();

		int size = appWorkflowTasks.size();

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
					com.liferay.app.builder.workflow.rest.dto.v1_0.
						AppWorkflowTask.class)) {

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
		AppWorkflowTask appWorkflowTask1, AppWorkflowTask appWorkflowTask2) {

		if (appWorkflowTask1 == appWorkflowTask2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("appId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						appWorkflowTask1.getAppId(),
						appWorkflowTask2.getAppId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"appWorkflowActions", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						appWorkflowTask1.getAppWorkflowActions(),
						appWorkflowTask2.getAppWorkflowActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutIds", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						appWorkflowTask1.getDataLayoutIds(),
						appWorkflowTask2.getDataLayoutIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						appWorkflowTask1.getName(),
						appWorkflowTask2.getName())) {

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

		if (!(_appWorkflowTaskResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_appWorkflowTaskResource;

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
		AppWorkflowTask appWorkflowTask) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("appId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("appWorkflowActions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataLayoutIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(appWorkflowTask.getName()));
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

	protected AppWorkflowTask randomAppWorkflowTask() throws Exception {
		return new AppWorkflowTask() {
			{
				appId = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected AppWorkflowTask randomIrrelevantAppWorkflowTask()
		throws Exception {

		AppWorkflowTask randomIrrelevantAppWorkflowTask =
			randomAppWorkflowTask();

		return randomIrrelevantAppWorkflowTask;
	}

	protected AppWorkflowTask randomPatchAppWorkflowTask() throws Exception {
		return randomAppWorkflowTask();
	}

	protected AppWorkflowTaskResource appWorkflowTaskResource;
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
		BaseAppWorkflowTaskResourceTestCase.class);

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
		com.liferay.app.builder.workflow.rest.resource.v1_0.
			AppWorkflowTaskResource _appWorkflowTaskResource;

}