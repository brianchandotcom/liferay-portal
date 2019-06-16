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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.dto.v1_0.Process;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.ProcessResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.ProcessSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
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

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.Date;
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
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public abstract class BaseProcessResourceTestCase {

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

		_processResource.setContextCompany(testCompany);

		ProcessResource.Builder builder = ProcessResource.builder();

		processResource = builder.locale(
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

		Process process1 = randomProcess();

		String json = objectMapper.writeValueAsString(process1);

		Process process2 = ProcessSerDes.toDTO(json);

		Assert.assertTrue(equals(process1, process2));
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

		Process process = randomProcess();

		String json1 = objectMapper.writeValueAsString(process);
		String json2 = ProcessSerDes.toJSON(process);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Process process = randomProcess();

		process.setStatus(regex);
		process.setUserInitials(regex);
		process.setUserName(regex);
		process.setUserPortraitURL(regex);

		String json = ProcessSerDes.toJSON(process);

		Assert.assertFalse(json.contains(regex));

		process = ProcessSerDes.toDTO(json);

		Assert.assertEquals(regex, process.getStatus());
		Assert.assertEquals(regex, process.getUserInitials());
		Assert.assertEquals(regex, process.getUserName());
		Assert.assertEquals(regex, process.getUserPortraitURL());
	}

	@Test
	public void testGetProccessProcess() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetProcessesPage() throws Exception {
		Page<Process> page = processResource.getProcessesPage(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Process process1 = testGetProcessesPage_addProcess(randomProcess());

		Process process2 = testGetProcessesPage_addProcess(randomProcess());

		page = processResource.getProcessesPage(
			null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(process1, process2), (List<Process>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessesPageWithPagination() throws Exception {
		Process process1 = testGetProcessesPage_addProcess(randomProcess());

		Process process2 = testGetProcessesPage_addProcess(randomProcess());

		Process process3 = testGetProcessesPage_addProcess(randomProcess());

		Page<Process> page1 = processResource.getProcessesPage(
			null, null, null, null, Pagination.of(1, 2), null);

		List<Process> processes1 = (List<Process>)page1.getItems();

		Assert.assertEquals(processes1.toString(), 2, processes1.size());

		Page<Process> page2 = processResource.getProcessesPage(
			null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Process> processes2 = (List<Process>)page2.getItems();

		Assert.assertEquals(processes2.toString(), 1, processes2.size());

		Page<Process> page3 = processResource.getProcessesPage(
			null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(process1, process2, process3),
			(List<Process>)page3.getItems());
	}

	@Test
	public void testGetProcessesPageWithSortDateTime() throws Exception {
		testGetProcessesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, process1, process2) -> {
				BeanUtils.setProperty(
					process1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetProcessesPageWithSortInteger() throws Exception {
		testGetProcessesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, process1, process2) -> {
				BeanUtils.setProperty(process1, entityField.getName(), 0);
				BeanUtils.setProperty(process2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetProcessesPageWithSortString() throws Exception {
		testGetProcessesPageWithSort(
			EntityField.Type.STRING,
			(entityField, process1, process2) -> {
				BeanUtils.setProperty(process1, entityField.getName(), "Aaa");
				BeanUtils.setProperty(process2, entityField.getName(), "Bbb");
			});
	}

	protected void testGetProcessesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Process, Process, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Process process1 = randomProcess();
		Process process2 = randomProcess();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, process1, process2);
		}

		process1 = testGetProcessesPage_addProcess(process1);

		process2 = testGetProcessesPage_addProcess(process2);

		for (EntityField entityField : entityFields) {
			Page<Process> ascPage = processResource.getProcessesPage(
				null, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(process1, process2),
				(List<Process>)ascPage.getItems());

			Page<Process> descPage = processResource.getProcessesPage(
				null, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(process2, process1),
				(List<Process>)descPage.getItems());
		}
	}

	protected Process testGetProcessesPage_addProcess(Process process)
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

	protected void assertEquals(Process process1, Process process2) {
		Assert.assertTrue(
			process1 + " does not equal " + process2,
			equals(process1, process2));
	}

	protected void assertEquals(
		List<Process> processes1, List<Process> processes2) {

		Assert.assertEquals(processes1.size(), processes2.size());

		for (int i = 0; i < processes1.size(); i++) {
			Process process1 = processes1.get(i);
			Process process2 = processes2.get(i);

			assertEquals(process1, process2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Process> processes1, List<Process> processes2) {

		Assert.assertEquals(processes1.size(), processes2.size());

		for (Process process1 : processes1) {
			boolean contains = false;

			for (Process process2 : processes2) {
				if (equals(process1, process2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				processes2 + " does not contain " + process1, contains);
		}
	}

	protected void assertValid(Process process) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("collection", additionalAssertFieldName)) {
				if (process.getCollection() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (process.getCompanyId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("date", additionalAssertFieldName)) {
				if (process.getDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentage", additionalAssertFieldName)) {
				if (process.getPercentage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (process.getProcessId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (process.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userInitials", additionalAssertFieldName)) {
				if (process.getUserInitials() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (process.getUserName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userPortraitURL", additionalAssertFieldName)) {
				if (process.getUserPortraitURL() == null) {
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

	protected void assertValid(Page<Process> page) {
		boolean valid = false;

		java.util.Collection<Process> processes = page.getItems();

		int size = processes.size();

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

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Process process1, Process process2) {
		if (process1 == process2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("collection", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getCollection(), process2.getCollection())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getCompanyId(), process2.getCompanyId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("date", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getDate(), process2.getDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getPercentage(), process2.getPercentage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getProcessId(), process2.getProcessId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getStatus(), process2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userInitials", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getUserInitials(),
						process2.getUserInitials())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getUserName(), process2.getUserName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userPortraitURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getUserPortraitURL(),
						process2.getUserPortraitURL())) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_processResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_processResource;

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
		EntityField entityField, String operator, Process process) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("collection")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("companyId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("date")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(process.getDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(process.getDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(process.getDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("percentage")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			sb.append("'");
			sb.append(String.valueOf(process.getStatus()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userInitials")) {
			sb.append("'");
			sb.append(String.valueOf(process.getUserInitials()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(process.getUserName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userPortraitURL")) {
			sb.append("'");
			sb.append(String.valueOf(process.getUserPortraitURL()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Process randomProcess() throws Exception {
		return new Process() {
			{
				companyId = RandomTestUtil.randomLong();
				date = RandomTestUtil.nextDate();
				processId = RandomTestUtil.randomLong();
				status = RandomTestUtil.randomString();
				userInitials = RandomTestUtil.randomString();
				userName = RandomTestUtil.randomString();
				userPortraitURL = RandomTestUtil.randomString();
			}
		};
	}

	protected Process randomIrrelevantProcess() throws Exception {
		Process randomIrrelevantProcess = randomProcess();

		return randomIrrelevantProcess;
	}

	protected Process randomPatchProcess() throws Exception {
		return randomProcess();
	}

	protected ProcessResource processResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseProcessResourceTestCase.class);

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
	private com.liferay.change.tracking.rest.resource.v1_0.ProcessResource
		_processResource;

}