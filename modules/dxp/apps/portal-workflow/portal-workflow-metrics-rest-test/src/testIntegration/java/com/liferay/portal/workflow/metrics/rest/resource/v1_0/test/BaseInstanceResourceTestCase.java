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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.InstanceResource;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.InstanceSerDes;

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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseInstanceResourceTestCase {

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

		_instanceResource.setContextCompany(testCompany);

		InstanceResource.Builder builder = InstanceResource.builder();

		instanceResource = builder.authentication(
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

		Instance instance1 = randomInstance();

		String json = objectMapper.writeValueAsString(instance1);

		Instance instance2 = InstanceSerDes.toDTO(json);

		Assert.assertTrue(equals(instance1, instance2));
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

		Instance instance = randomInstance();

		String json1 = objectMapper.writeValueAsString(instance);
		String json2 = InstanceSerDes.toJSON(instance);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Instance instance = randomInstance();

		instance.setAssetTitle(regex);
		instance.setAssetType(regex);
		instance.setClassName(regex);
		instance.setProcessVersion(regex);

		String json = InstanceSerDes.toJSON(instance);

		Assert.assertFalse(json.contains(regex));

		instance = InstanceSerDes.toDTO(json);

		Assert.assertEquals(regex, instance.getAssetTitle());
		Assert.assertEquals(regex, instance.getAssetType());
		Assert.assertEquals(regex, instance.getClassName());
		Assert.assertEquals(regex, instance.getProcessVersion());
	}

	@Test
	public void testGetProcessInstancesPage() throws Exception {
		Page<Instance> page = instanceResource.getProcessInstancesPage(
			testGetProcessInstancesPage_getProcessId(), null, null,
			RandomTestUtil.nextDate(), RandomTestUtil.nextDate(), null, null,
			null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long processId = testGetProcessInstancesPage_getProcessId();
		Long irrelevantProcessId =
			testGetProcessInstancesPage_getIrrelevantProcessId();

		if (irrelevantProcessId != null) {
			Instance irrelevantInstance =
				testGetProcessInstancesPage_addInstance(
					irrelevantProcessId, randomIrrelevantInstance());

			page = instanceResource.getProcessInstancesPage(
				irrelevantProcessId, null, null, null, null, null, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantInstance),
				(List<Instance>)page.getItems());
			assertValid(page);
		}

		Instance instance1 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Instance instance2 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		page = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, null,
			Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(instance1, instance2),
			(List<Instance>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessInstancesPageWithPagination() throws Exception {
		Long processId = testGetProcessInstancesPage_getProcessId();

		Instance instance1 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Instance instance2 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Instance instance3 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Page<Instance> page1 = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, null,
			Pagination.of(1, 2), null);

		List<Instance> instances1 = (List<Instance>)page1.getItems();

		Assert.assertEquals(instances1.toString(), 2, instances1.size());

		Page<Instance> page2 = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, null,
			Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Instance> instances2 = (List<Instance>)page2.getItems();

		Assert.assertEquals(instances2.toString(), 1, instances2.size());

		Page<Instance> page3 = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, null,
			Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(instance1, instance2, instance3),
			(List<Instance>)page3.getItems());
	}

	@Test
	public void testGetProcessInstancesPageWithSortDateTime() throws Exception {
		testGetProcessInstancesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, instance1, instance2) -> {
				BeanUtils.setProperty(
					instance1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetProcessInstancesPageWithSortInteger() throws Exception {
		testGetProcessInstancesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, instance1, instance2) -> {
				BeanUtils.setProperty(instance1, entityField.getName(), 0);
				BeanUtils.setProperty(instance2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetProcessInstancesPageWithSortString() throws Exception {
		testGetProcessInstancesPageWithSort(
			EntityField.Type.STRING,
			(entityField, instance1, instance2) -> {
				Class<?> clazz = instance1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						instance1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						instance2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						instance1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						instance2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						instance1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						instance2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetProcessInstancesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Instance, Instance, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long processId = testGetProcessInstancesPage_getProcessId();

		Instance instance1 = randomInstance();
		Instance instance2 = randomInstance();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, instance1, instance2);
		}

		instance1 = testGetProcessInstancesPage_addInstance(
			processId, instance1);

		instance2 = testGetProcessInstancesPage_addInstance(
			processId, instance2);

		for (EntityField entityField : entityFields) {
			Page<Instance> ascPage = instanceResource.getProcessInstancesPage(
				processId, null, null, null, null, null, null, null,
				Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(instance1, instance2),
				(List<Instance>)ascPage.getItems());

			Page<Instance> descPage = instanceResource.getProcessInstancesPage(
				processId, null, null, null, null, null, null, null,
				Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(instance2, instance1),
				(List<Instance>)descPage.getItems());
		}
	}

	protected Instance testGetProcessInstancesPage_addInstance(
			Long processId, Instance instance)
		throws Exception {

		return instanceResource.postProcessInstance(processId, instance);
	}

	protected Long testGetProcessInstancesPage_getProcessId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessInstancesPage_getIrrelevantProcessId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProcessInstance() throws Exception {
		Instance randomInstance = randomInstance();

		Instance postInstance = testPostProcessInstance_addInstance(
			randomInstance);

		assertEquals(randomInstance, postInstance);
		assertValid(postInstance);
	}

	protected Instance testPostProcessInstance_addInstance(Instance instance)
		throws Exception {

		return instanceResource.postProcessInstance(
			testGetProcessInstancesPage_getProcessId(), instance);
	}

	@Test
	public void testDeleteProcessInstance() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Instance instance = testDeleteProcessInstance_addInstance();

		assertHttpResponseStatusCode(
			204,
			instanceResource.deleteProcessInstanceHttpResponse(
				instance.getProcessId(), instance.getId()));

		assertHttpResponseStatusCode(
			404,
			instanceResource.getProcessInstanceHttpResponse(
				instance.getProcessId(), instance.getId()));

		assertHttpResponseStatusCode(
			404,
			instanceResource.getProcessInstanceHttpResponse(
				instance.getProcessId(), 0L));
	}

	protected Instance testDeleteProcessInstance_addInstance()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetProcessInstance() throws Exception {
		Instance postInstance = testGetProcessInstance_addInstance();

		Instance getInstance = instanceResource.getProcessInstance(
			postInstance.getProcessId(), postInstance.getId());

		assertEquals(postInstance, getInstance);
		assertValid(getInstance);
	}

	protected Instance testGetProcessInstance_addInstance() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProcessInstance() throws Exception {
		Instance instance = testGraphQLInstance_addInstance();

		Assert.assertTrue(
			equals(
				instance,
				InstanceSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"processInstance",
								new HashMap<String, Object>() {
									{
										put(
											"processId",
											instance.getProcessId());
										put("instanceId", instance.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/processInstance"))));
	}

	@Test
	public void testGraphQLGetProcessInstanceNotFound() throws Exception {
		Long irrelevantProcessId = RandomTestUtil.randomLong();
		Long irrelevantInstanceId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"processInstance",
						new HashMap<String, Object>() {
							{
								put("processId", irrelevantProcessId);
								put("instanceId", irrelevantInstanceId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchProcessInstance() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Instance instance = testPatchProcessInstance_addInstance();

		assertHttpResponseStatusCode(
			204,
			instanceResource.patchProcessInstanceHttpResponse(
				instance.getProcessId(), instance.getId(), instance));

		assertHttpResponseStatusCode(
			404,
			instanceResource.patchProcessInstanceHttpResponse(
				instance.getProcessId(), 0L, instance));
	}

	protected Instance testPatchProcessInstance_addInstance() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchProcessInstanceComplete() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Instance instance = testPatchProcessInstanceComplete_addInstance();

		assertHttpResponseStatusCode(
			204,
			instanceResource.patchProcessInstanceCompleteHttpResponse(
				instance.getProcessId(), instance.getId(), instance));

		assertHttpResponseStatusCode(
			404,
			instanceResource.patchProcessInstanceCompleteHttpResponse(
				instance.getProcessId(), 0L, instance));
	}

	protected Instance testPatchProcessInstanceComplete_addInstance()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Instance testGraphQLInstance_addInstance() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Instance instance1, Instance instance2) {
		Assert.assertTrue(
			instance1 + " does not equal " + instance2,
			equals(instance1, instance2));
	}

	protected void assertEquals(
		List<Instance> instances1, List<Instance> instances2) {

		Assert.assertEquals(instances1.size(), instances2.size());

		for (int i = 0; i < instances1.size(); i++) {
			Instance instance1 = instances1.get(i);
			Instance instance2 = instances2.get(i);

			assertEquals(instance1, instance2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Instance> instances1, List<Instance> instances2) {

		Assert.assertEquals(instances1.size(), instances2.size());

		for (Instance instance1 : instances1) {
			boolean contains = false;

			for (Instance instance2 : instances2) {
				if (equals(instance1, instance2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				instances2 + " does not contain " + instance1, contains);
		}
	}

	protected void assertValid(Instance instance) throws Exception {
		boolean valid = true;

		if (instance.getDateCreated() == null) {
			valid = false;
		}

		if (instance.getDateModified() == null) {
			valid = false;
		}

		if (instance.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assetTitle", additionalAssertFieldName)) {
				if (instance.getAssetTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetTitle_i18n", additionalAssertFieldName)) {
				if (instance.getAssetTitle_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetType", additionalAssertFieldName)) {
				if (instance.getAssetType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetType_i18n", additionalAssertFieldName)) {
				if (instance.getAssetType_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assignees", additionalAssertFieldName)) {
				if (instance.getAssignees() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("className", additionalAssertFieldName)) {
				if (instance.getClassName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("classPK", additionalAssertFieldName)) {
				if (instance.getClassPK() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (instance.getCompleted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (instance.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (instance.getDateCompletion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("duration", additionalAssertFieldName)) {
				if (instance.getDuration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (instance.getProcessId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processVersion", additionalAssertFieldName)) {
				if (instance.getProcessVersion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("slaResults", additionalAssertFieldName)) {
				if (instance.getSlaResults() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("slaStatus", additionalAssertFieldName)) {
				if (instance.getSLAStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taskNames", additionalAssertFieldName)) {
				if (instance.getTaskNames() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (instance.getTransitions() == null) {
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

	protected void assertValid(Page<Instance> page) {
		boolean valid = false;

		java.util.Collection<Instance> instances = page.getItems();

		int size = instances.size();

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
					com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance.
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

	protected boolean equals(Instance instance1, Instance instance2) {
		if (instance1 == instance2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assetTitle", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getAssetTitle(), instance2.getAssetTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assetTitle_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)instance1.getAssetTitle_i18n(),
						(Map)instance2.getAssetTitle_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assetType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getAssetType(), instance2.getAssetType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assetType_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)instance1.getAssetType_i18n(),
						(Map)instance2.getAssetType_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assignees", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getAssignees(), instance2.getAssignees())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("className", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getClassName(), instance2.getClassName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("classPK", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getClassPK(), instance2.getClassPK())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getCompleted(), instance2.getCompleted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getCreator(), instance2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getDateCompletion(),
						instance2.getDateCompletion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getDateCreated(),
						instance2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getDateModified(),
						instance2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("duration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getDuration(), instance2.getDuration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(instance1.getId(), instance2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getProcessId(), instance2.getProcessId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("processVersion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getProcessVersion(),
						instance2.getProcessVersion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("slaResults", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getSlaResults(), instance2.getSlaResults())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("slaStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getSLAStatus(), instance2.getSLAStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskNames", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getTaskNames(), instance2.getTaskNames())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getTransitions(),
						instance2.getTransitions())) {

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

		if (!(_instanceResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_instanceResource;

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
		EntityField entityField, String operator, Instance instance) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("assetTitle")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getAssetTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("assetTitle_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("assetType")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getAssetType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("assetType_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("assignees")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("className")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getClassName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("classPK")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("completed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCompletion")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							instance.getDateCompletion(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(instance.getDateCompletion(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(instance.getDateCompletion()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(instance.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(instance.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(instance.getDateCreated()));
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
						DateUtils.addSeconds(instance.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(instance.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(instance.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("duration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processVersion")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getProcessVersion()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("slaResults")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("slaStatus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taskNames")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("transitions")) {
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

	protected Instance randomInstance() throws Exception {
		return new Instance() {
			{
				assetTitle = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				assetType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				className = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				classPK = RandomTestUtil.randomLong();
				completed = RandomTestUtil.randomBoolean();
				dateCompletion = RandomTestUtil.nextDate();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				duration = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				processId = RandomTestUtil.randomLong();
				processVersion = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected Instance randomIrrelevantInstance() throws Exception {
		Instance randomIrrelevantInstance = randomInstance();

		return randomIrrelevantInstance;
	}

	protected Instance randomPatchInstance() throws Exception {
		return randomInstance();
	}

	protected InstanceResource instanceResource;
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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseInstanceResourceTestCase.class);

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
		com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource
			_instanceResource;

}