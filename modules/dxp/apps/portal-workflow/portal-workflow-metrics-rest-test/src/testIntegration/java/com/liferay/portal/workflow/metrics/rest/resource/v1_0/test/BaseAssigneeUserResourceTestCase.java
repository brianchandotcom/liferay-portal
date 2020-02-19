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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.AssigneeUserResource;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.AssigneeUserSerDes;

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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseAssigneeUserResourceTestCase {

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

		_assigneeUserResource.setContextCompany(testCompany);

		AssigneeUserResource.Builder builder = AssigneeUserResource.builder();

		assigneeUserResource = builder.locale(
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

		AssigneeUser assigneeUser1 = randomAssigneeUser();

		String json = objectMapper.writeValueAsString(assigneeUser1);

		AssigneeUser assigneeUser2 = AssigneeUserSerDes.toDTO(json);

		Assert.assertTrue(equals(assigneeUser1, assigneeUser2));
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

		AssigneeUser assigneeUser = randomAssigneeUser();

		String json1 = objectMapper.writeValueAsString(assigneeUser);
		String json2 = AssigneeUserSerDes.toJSON(assigneeUser);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AssigneeUser assigneeUser = randomAssigneeUser();

		assigneeUser.setImage(regex);
		assigneeUser.setName(regex);

		String json = AssigneeUserSerDes.toJSON(assigneeUser);

		Assert.assertFalse(json.contains(regex));

		assigneeUser = AssigneeUserSerDes.toDTO(json);

		Assert.assertEquals(regex, assigneeUser.getImage());
		Assert.assertEquals(regex, assigneeUser.getName());
	}

	@Test
	public void testGetProcessAssigneeUsersPage() throws Exception {
		Page<AssigneeUser> page =
			assigneeUserResource.getProcessAssigneeUsersPage(
				testGetProcessAssigneeUsersPage_getProcessId(), null,
				RandomTestUtil.nextDate(), RandomTestUtil.nextDate(),
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long processId = testGetProcessAssigneeUsersPage_getProcessId();
		Long irrelevantProcessId =
			testGetProcessAssigneeUsersPage_getIrrelevantProcessId();

		if ((irrelevantProcessId != null)) {
			AssigneeUser irrelevantAssigneeUser =
				testGetProcessAssigneeUsersPage_addAssigneeUser(
					irrelevantProcessId, randomIrrelevantAssigneeUser());

			page = assigneeUserResource.getProcessAssigneeUsersPage(
				irrelevantProcessId, null, null, null, null, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAssigneeUser),
				(List<AssigneeUser>)page.getItems());
			assertValid(page);
		}

		AssigneeUser assigneeUser1 =
			testGetProcessAssigneeUsersPage_addAssigneeUser(
				processId, randomAssigneeUser());

		AssigneeUser assigneeUser2 =
			testGetProcessAssigneeUsersPage_addAssigneeUser(
				processId, randomAssigneeUser());

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			processId, null, null, null, null, null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(assigneeUser1, assigneeUser2),
			(List<AssigneeUser>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessAssigneeUsersPageWithPagination()
		throws Exception {

		Long processId = testGetProcessAssigneeUsersPage_getProcessId();

		AssigneeUser assigneeUser1 =
			testGetProcessAssigneeUsersPage_addAssigneeUser(
				processId, randomAssigneeUser());

		AssigneeUser assigneeUser2 =
			testGetProcessAssigneeUsersPage_addAssigneeUser(
				processId, randomAssigneeUser());

		AssigneeUser assigneeUser3 =
			testGetProcessAssigneeUsersPage_addAssigneeUser(
				processId, randomAssigneeUser());

		Page<AssigneeUser> page1 =
			assigneeUserResource.getProcessAssigneeUsersPage(
				processId, null, null, null, null, null, null,
				Pagination.of(1, 2), null);

		List<AssigneeUser> assigneeUsers1 =
			(List<AssigneeUser>)page1.getItems();

		Assert.assertEquals(
			assigneeUsers1.toString(), 2, assigneeUsers1.size());

		Page<AssigneeUser> page2 =
			assigneeUserResource.getProcessAssigneeUsersPage(
				processId, null, null, null, null, null, null,
				Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<AssigneeUser> assigneeUsers2 =
			(List<AssigneeUser>)page2.getItems();

		Assert.assertEquals(
			assigneeUsers2.toString(), 1, assigneeUsers2.size());

		Page<AssigneeUser> page3 =
			assigneeUserResource.getProcessAssigneeUsersPage(
				processId, null, null, null, null, null, null,
				Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(assigneeUser1, assigneeUser2, assigneeUser3),
			(List<AssigneeUser>)page3.getItems());
	}

	@Test
	public void testGetProcessAssigneeUsersPageWithSortDateTime()
		throws Exception {

		testGetProcessAssigneeUsersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, assigneeUser1, assigneeUser2) -> {
				BeanUtils.setProperty(
					assigneeUser1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetProcessAssigneeUsersPageWithSortInteger()
		throws Exception {

		testGetProcessAssigneeUsersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, assigneeUser1, assigneeUser2) -> {
				BeanUtils.setProperty(assigneeUser1, entityField.getName(), 0);
				BeanUtils.setProperty(assigneeUser2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetProcessAssigneeUsersPageWithSortString()
		throws Exception {

		testGetProcessAssigneeUsersPageWithSort(
			EntityField.Type.STRING,
			(entityField, assigneeUser1, assigneeUser2) -> {
				Class<?> clazz = assigneeUser1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						assigneeUser1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						assigneeUser2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						assigneeUser1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						assigneeUser2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetProcessAssigneeUsersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, AssigneeUser, AssigneeUser, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long processId = testGetProcessAssigneeUsersPage_getProcessId();

		AssigneeUser assigneeUser1 = randomAssigneeUser();
		AssigneeUser assigneeUser2 = randomAssigneeUser();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, assigneeUser1, assigneeUser2);
		}

		assigneeUser1 = testGetProcessAssigneeUsersPage_addAssigneeUser(
			processId, assigneeUser1);

		assigneeUser2 = testGetProcessAssigneeUsersPage_addAssigneeUser(
			processId, assigneeUser2);

		for (EntityField entityField : entityFields) {
			Page<AssigneeUser> ascPage =
				assigneeUserResource.getProcessAssigneeUsersPage(
					processId, null, null, null, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(assigneeUser1, assigneeUser2),
				(List<AssigneeUser>)ascPage.getItems());

			Page<AssigneeUser> descPage =
				assigneeUserResource.getProcessAssigneeUsersPage(
					processId, null, null, null, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(assigneeUser2, assigneeUser1),
				(List<AssigneeUser>)descPage.getItems());
		}
	}

	protected AssigneeUser testGetProcessAssigneeUsersPage_addAssigneeUser(
			Long processId, AssigneeUser assigneeUser)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessAssigneeUsersPage_getProcessId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessAssigneeUsersPage_getIrrelevantProcessId()
		throws Exception {

		return null;
	}

	protected AssigneeUser testGraphQLAssigneeUser_addAssigneeUser()
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
		AssigneeUser assigneeUser1, AssigneeUser assigneeUser2) {

		Assert.assertTrue(
			assigneeUser1 + " does not equal " + assigneeUser2,
			equals(assigneeUser1, assigneeUser2));
	}

	protected void assertEquals(
		List<AssigneeUser> assigneeUsers1, List<AssigneeUser> assigneeUsers2) {

		Assert.assertEquals(assigneeUsers1.size(), assigneeUsers2.size());

		for (int i = 0; i < assigneeUsers1.size(); i++) {
			AssigneeUser assigneeUser1 = assigneeUsers1.get(i);
			AssigneeUser assigneeUser2 = assigneeUsers2.get(i);

			assertEquals(assigneeUser1, assigneeUser2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AssigneeUser> assigneeUsers1, List<AssigneeUser> assigneeUsers2) {

		Assert.assertEquals(assigneeUsers1.size(), assigneeUsers2.size());

		for (AssigneeUser assigneeUser1 : assigneeUsers1) {
			boolean contains = false;

			for (AssigneeUser assigneeUser2 : assigneeUsers2) {
				if (equals(assigneeUser1, assigneeUser2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				assigneeUsers2 + " does not contain " + assigneeUser1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<AssigneeUser> assigneeUsers, JSONArray jsonArray) {

		for (AssigneeUser assigneeUser : assigneeUsers) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(assigneeUser, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + assigneeUser, contains);
		}
	}

	protected void assertValid(AssigneeUser assigneeUser) {
		boolean valid = true;

		if (assigneeUser.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("durationTaskAvg", additionalAssertFieldName)) {
				if (assigneeUser.getDurationTaskAvg() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (assigneeUser.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (assigneeUser.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("onTimeTaskCount", additionalAssertFieldName)) {
				if (assigneeUser.getOnTimeTaskCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("overdueTaskCount", additionalAssertFieldName)) {
				if (assigneeUser.getOverdueTaskCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taskCount", additionalAssertFieldName)) {
				if (assigneeUser.getTaskCount() == null) {
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

	protected void assertValid(Page<AssigneeUser> page) {
		boolean valid = false;

		java.util.Collection<AssigneeUser> assigneeUsers = page.getItems();

		int size = assigneeUsers.size();

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
		AssigneeUser assigneeUser1, AssigneeUser assigneeUser2) {

		if (assigneeUser1 == assigneeUser2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("durationTaskAvg", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assigneeUser1.getDurationTaskAvg(),
						assigneeUser2.getDurationTaskAvg())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assigneeUser1.getId(), assigneeUser2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assigneeUser1.getImage(), assigneeUser2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assigneeUser1.getName(), assigneeUser2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("onTimeTaskCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assigneeUser1.getOnTimeTaskCount(),
						assigneeUser2.getOnTimeTaskCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("overdueTaskCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assigneeUser1.getOverdueTaskCount(),
						assigneeUser2.getOverdueTaskCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assigneeUser1.getTaskCount(),
						assigneeUser2.getTaskCount())) {

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
		AssigneeUser assigneeUser, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("durationTaskAvg", fieldName)) {
				if (!Objects.deepEquals(
						assigneeUser.getDurationTaskAvg(),
						jsonObject.getLong("durationTaskAvg"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						assigneeUser.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", fieldName)) {
				if (!Objects.deepEquals(
						assigneeUser.getImage(),
						jsonObject.getString("image"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						assigneeUser.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("onTimeTaskCount", fieldName)) {
				if (!Objects.deepEquals(
						assigneeUser.getOnTimeTaskCount(),
						jsonObject.getLong("onTimeTaskCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("overdueTaskCount", fieldName)) {
				if (!Objects.deepEquals(
						assigneeUser.getOverdueTaskCount(),
						jsonObject.getLong("overdueTaskCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskCount", fieldName)) {
				if (!Objects.deepEquals(
						assigneeUser.getTaskCount(),
						jsonObject.getLong("taskCount"))) {

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

		if (!(_assigneeUserResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_assigneeUserResource;

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
		EntityField entityField, String operator, AssigneeUser assigneeUser) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("durationTaskAvg")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("image")) {
			sb.append("'");
			sb.append(String.valueOf(assigneeUser.getImage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(assigneeUser.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("onTimeTaskCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("overdueTaskCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taskCount")) {
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

	protected AssigneeUser randomAssigneeUser() throws Exception {
		return new AssigneeUser() {
			{
				durationTaskAvg = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				onTimeTaskCount = RandomTestUtil.randomLong();
				overdueTaskCount = RandomTestUtil.randomLong();
				taskCount = RandomTestUtil.randomLong();
			}
		};
	}

	protected AssigneeUser randomIrrelevantAssigneeUser() throws Exception {
		AssigneeUser randomIrrelevantAssigneeUser = randomAssigneeUser();

		return randomIrrelevantAssigneeUser;
	}

	protected AssigneeUser randomPatchAssigneeUser() throws Exception {
		return randomAssigneeUser();
	}

	protected AssigneeUserResource assigneeUserResource;
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
		BaseAssigneeUserResourceTestCase.class);

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
		com.liferay.portal.workflow.metrics.rest.resource.v1_0.
			AssigneeUserResource _assigneeUserResource;

}