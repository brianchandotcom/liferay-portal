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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.InstanceSerDes;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/portal-workflow-metrics/v1.0");
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
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		Instance instance = randomInstance();

		String json1 = objectMapper.writeValueAsString(instance);
		String json2 = InstanceSerDes.toJSON(instance);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetProcessInstancesPage() throws Exception {
		Long processId = testGetProcessInstancesPage_getProcessId();
		Long irrelevantProcessId =
			testGetProcessInstancesPage_getIrrelevantProcessId();

		if ((irrelevantProcessId != null)) {
			Instance irrelevantInstance =
				testGetProcessInstancesPage_addInstance(
					irrelevantProcessId, randomIrrelevantInstance());

			Page<Instance> page = invokeGetProcessInstancesPage(
				irrelevantProcessId, null, null, null, null,
				Pagination.of(1, 2));

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

		Page<Instance> page = invokeGetProcessInstancesPage(
			processId, null, null, null, null, Pagination.of(1, 2));

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

		Page<Instance> page1 = invokeGetProcessInstancesPage(
			processId, null, null, null, null, Pagination.of(1, 2));

		List<Instance> instances1 = (List<Instance>)page1.getItems();

		Assert.assertEquals(instances1.toString(), 2, instances1.size());

		Page<Instance> page2 = invokeGetProcessInstancesPage(
			processId, null, null, null, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Instance> instances2 = (List<Instance>)page2.getItems();

		Assert.assertEquals(instances2.toString(), 1, instances2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(instance1, instance2, instance3),
			new ArrayList<Instance>() {
				{
					addAll(instances1);
					addAll(instances2);
				}
			});
	}

	protected Instance testGetProcessInstancesPage_addInstance(
			Long processId, Instance instance)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessInstancesPage_getProcessId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessInstancesPage_getIrrelevantProcessId()
		throws Exception {

		return null;
	}

	protected Page<Instance> invokeGetProcessInstancesPage(
			Long processId, String[] slaStatuses, String[] statuses,
			String[] taskKeys, Integer timeRange, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/processes/{processId}/instances", processId);

		if (slaStatuses != null) {
			location = HttpUtil.addParameter(
				location, "slaStatuses", slaStatuses);
		}

		if (statuses != null) {
			location = HttpUtil.addParameter(location, "statuses", statuses);
		}

		if (taskKeys != null) {
			location = HttpUtil.addParameter(location, "taskKeys", taskKeys);
		}

		if (timeRange != null) {
			location = HttpUtil.addParameter(location, "timeRange", timeRange);
		}

		if (pagination != null) {
			location = HttpUtil.addParameter(
				location, "page", pagination.getPage());
			location = HttpUtil.addParameter(
				location, "pageSize", pagination.getPageSize());
		}

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, InstanceSerDes::toDTO);
	}

	protected Http.Response invokeGetProcessInstancesPageResponse(
			Long processId, String[] slaStatuses, String[] statuses,
			String[] taskKeys, Integer timeRange, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/processes/{processId}/instances", processId);

		if (slaStatuses != null) {
			location = HttpUtil.addParameter(
				location, "slaStatuses", slaStatuses);
		}

		if (statuses != null) {
			location = HttpUtil.addParameter(location, "statuses", statuses);
		}

		if (taskKeys != null) {
			location = HttpUtil.addParameter(location, "taskKeys", taskKeys);
		}

		if (timeRange != null) {
			location = HttpUtil.addParameter(location, "timeRange", timeRange);
		}

		if (pagination != null) {
			location = HttpUtil.addParameter(
				location, "page", pagination.getPage());
			location = HttpUtil.addParameter(
				location, "pageSize", pagination.getPageSize());
		}

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetProcessInstance() throws Exception {
		Instance postInstance = testGetProcessInstance_addInstance();

		Instance getInstance = invokeGetProcessInstance(
			postInstance.getProcessId(), postInstance.getId());

		assertEquals(postInstance, getInstance);
		assertValid(getInstance);
	}

	protected Instance testGetProcessInstance_addInstance() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Instance invokeGetProcessInstance(Long processId, Long instanceId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/processes/{processId}/instances/{instanceId}", processId,
					instanceId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return InstanceSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetProcessInstanceResponse(
			Long processId, Long instanceId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/processes/{processId}/instances/{instanceId}", processId,
					instanceId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
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

	protected void assertValid(Instance instance) {
		boolean valid = true;

		if (instance.getDateCreated() == null) {
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

			if (Objects.equals("assetType", additionalAssertFieldName)) {
				if (instance.getAssetType() == null) {
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

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (instance.getProcessId() == null) {
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

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (instance.getStatus() == null) {
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

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (instance.getUserName() == null) {
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

		Collection<Instance> instances = page.getItems();

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

			if (Objects.equals("assetType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getAssetType(), instance2.getAssetType())) {

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

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getStatus(), instance2.getStatus())) {

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

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getUserName(), instance2.getUserName())) {

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

	protected Collection<EntityField> getEntityFields() throws Exception {
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

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
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

		if (entityFieldName.equals("assetType")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getAssetType()));
			sb.append("'");

			return sb.toString();
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

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("slaResults")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("slaStatus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taskNames")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getUserName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Instance randomInstance() throws Exception {
		return new Instance() {
			{
				assetTitle = RandomTestUtil.randomString();
				assetType = RandomTestUtil.randomString();
				dateCompletion = RandomTestUtil.nextDate();
				dateCreated = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				processId = RandomTestUtil.randomLong();
				userName = RandomTestUtil.randomString();
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

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
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
	private InstanceResource _instanceResource;

	private URL _resourceURL;

}