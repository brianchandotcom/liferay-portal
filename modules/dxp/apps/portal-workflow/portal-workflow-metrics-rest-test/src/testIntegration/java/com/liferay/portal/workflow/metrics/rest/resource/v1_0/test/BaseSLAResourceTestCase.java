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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.SLAResource;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.SLASerDes;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
public abstract class BaseSLAResourceTestCase {

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

		_slaResource.setContextCompany(testCompany);

		SLAResource.Builder builder = SLAResource.builder();

		slaResource = builder.locale(
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

		SLA sla1 = randomSLA();

		String json = objectMapper.writeValueAsString(sla1);

		SLA sla2 = SLASerDes.toDTO(json);

		Assert.assertTrue(equals(sla1, sla2));
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

		SLA sla = randomSLA();

		String json1 = objectMapper.writeValueAsString(sla);
		String json2 = SLASerDes.toJSON(sla);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		SLA sla = randomSLA();

		sla.setCalendarKey(regex);
		sla.setDescription(regex);
		sla.setName(regex);

		String json = SLASerDes.toJSON(sla);

		Assert.assertFalse(json.contains(regex));

		sla = SLASerDes.toDTO(json);

		Assert.assertEquals(regex, sla.getCalendarKey());
		Assert.assertEquals(regex, sla.getDescription());
		Assert.assertEquals(regex, sla.getName());
	}

	@Test
	public void testGetProcessSLAsPage() throws Exception {
		Page<SLA> page = slaResource.getProcessSLAsPage(
			testGetProcessSLAsPage_getProcessId(), null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long processId = testGetProcessSLAsPage_getProcessId();
		Long irrelevantProcessId =
			testGetProcessSLAsPage_getIrrelevantProcessId();

		if ((irrelevantProcessId != null)) {
			SLA irrelevantSLA = testGetProcessSLAsPage_addSLA(
				irrelevantProcessId, randomIrrelevantSLA());

			page = slaResource.getProcessSLAsPage(
				irrelevantProcessId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSLA), (List<SLA>)page.getItems());
			assertValid(page);
		}

		SLA sla1 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		SLA sla2 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		page = slaResource.getProcessSLAsPage(
			processId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(sla1, sla2), (List<SLA>)page.getItems());
		assertValid(page);

		slaResource.deleteSLA(sla1.getId());

		slaResource.deleteSLA(sla2.getId());
	}

	@Test
	public void testGetProcessSLAsPageWithPagination() throws Exception {
		Long processId = testGetProcessSLAsPage_getProcessId();

		SLA sla1 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		SLA sla2 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		SLA sla3 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		Page<SLA> page1 = slaResource.getProcessSLAsPage(
			processId, null, Pagination.of(1, 2));

		List<SLA> slas1 = (List<SLA>)page1.getItems();

		Assert.assertEquals(slas1.toString(), 2, slas1.size());

		Page<SLA> page2 = slaResource.getProcessSLAsPage(
			processId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<SLA> slas2 = (List<SLA>)page2.getItems();

		Assert.assertEquals(slas2.toString(), 1, slas2.size());

		Page<SLA> page3 = slaResource.getProcessSLAsPage(
			processId, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(sla1, sla2, sla3), (List<SLA>)page3.getItems());
	}

	protected SLA testGetProcessSLAsPage_addSLA(Long processId, SLA sla)
		throws Exception {

		return slaResource.postProcessSLA(processId, sla);
	}

	protected Long testGetProcessSLAsPage_getProcessId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessSLAsPage_getIrrelevantProcessId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProcessSLA() throws Exception {
		SLA randomSLA = randomSLA();

		SLA postSLA = testPostProcessSLA_addSLA(randomSLA);

		assertEquals(randomSLA, postSLA);
		assertValid(postSLA);
	}

	protected SLA testPostProcessSLA_addSLA(SLA sla) throws Exception {
		return slaResource.postProcessSLA(
			testGetProcessSLAsPage_getProcessId(), sla);
	}

	@Test
	public void testDeleteSLA() throws Exception {
		SLA sla = testDeleteSLA_addSLA();

		assertHttpResponseStatusCode(
			204, slaResource.deleteSLAHttpResponse(sla.getId()));

		assertHttpResponseStatusCode(
			404, slaResource.getSLAHttpResponse(sla.getId()));

		assertHttpResponseStatusCode(404, slaResource.getSLAHttpResponse(0L));
	}

	protected SLA testDeleteSLA_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSLA() throws Exception {
		SLA postSLA = testGetSLA_addSLA();

		SLA getSLA = slaResource.getSLA(postSLA.getId());

		assertEquals(postSLA, getSLA);
		assertValid(getSLA);
	}

	protected SLA testGetSLA_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutSLA() throws Exception {
		SLA postSLA = testPutSLA_addSLA();

		SLA randomSLA = randomSLA();

		SLA putSLA = slaResource.putSLA(postSLA.getId(), randomSLA);

		assertEquals(randomSLA, putSLA);
		assertValid(putSLA);

		SLA getSLA = slaResource.getSLA(putSLA.getId());

		assertEquals(randomSLA, getSLA);
		assertValid(getSLA);
	}

	protected SLA testPutSLA_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(SLA sla1, SLA sla2) {
		Assert.assertTrue(sla1 + " does not equal " + sla2, equals(sla1, sla2));
	}

	protected void assertEquals(List<SLA> slas1, List<SLA> slas2) {
		Assert.assertEquals(slas1.size(), slas2.size());

		for (int i = 0; i < slas1.size(); i++) {
			SLA sla1 = slas1.get(i);
			SLA sla2 = slas2.get(i);

			assertEquals(sla1, sla2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<SLA> slas1, List<SLA> slas2) {
		Assert.assertEquals(slas1.size(), slas2.size());

		for (SLA sla1 : slas1) {
			boolean contains = false;

			for (SLA sla2 : slas2) {
				if (equals(sla1, sla2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(slas2 + " does not contain " + sla1, contains);
		}
	}

	protected void assertValid(SLA sla) {
		boolean valid = true;

		if (sla.getDateModified() == null) {
			valid = false;
		}

		if (sla.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("calendarKey", additionalAssertFieldName)) {
				if (sla.getCalendarKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (sla.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("duration", additionalAssertFieldName)) {
				if (sla.getDuration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (sla.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pauseNodeKeys", additionalAssertFieldName)) {
				if (sla.getPauseNodeKeys() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (sla.getProcessId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("startNodeKeys", additionalAssertFieldName)) {
				if (sla.getStartNodeKeys() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (sla.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("stopNodeKeys", additionalAssertFieldName)) {
				if (sla.getStopNodeKeys() == null) {
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

	protected void assertValid(Page<SLA> page) {
		boolean valid = false;

		java.util.Collection<SLA> slas = page.getItems();

		int size = slas.size();

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

	protected boolean equals(SLA sla1, SLA sla2) {
		if (sla1 == sla2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("calendarKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getCalendarKey(), sla2.getCalendarKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getDateModified(), sla2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getDescription(), sla2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("duration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getDuration(), sla2.getDuration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(sla1.getId(), sla2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(sla1.getName(), sla2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("pauseNodeKeys", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getPauseNodeKeys(), sla2.getPauseNodeKeys())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getProcessId(), sla2.getProcessId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("startNodeKeys", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getStartNodeKeys(), sla2.getStartNodeKeys())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(sla1.getStatus(), sla2.getStatus())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("stopNodeKeys", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getStopNodeKeys(), sla2.getStopNodeKeys())) {

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

		if (!(_slaResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_slaResource;

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
		EntityField entityField, String operator, SLA sla) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("calendarKey")) {
			sb.append("'");
			sb.append(String.valueOf(sla.getCalendarKey()));
			sb.append("'");

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
						DateUtils.addSeconds(sla.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sla.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sla.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(sla.getDescription()));
			sb.append("'");

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

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(sla.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("pauseNodeKeys")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("startNodeKeys")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("stopNodeKeys")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected SLA randomSLA() throws Exception {
		return new SLA() {
			{
				calendarKey = RandomTestUtil.randomString();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				duration = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				processId = RandomTestUtil.randomLong();
			}
		};
	}

	protected SLA randomIrrelevantSLA() throws Exception {
		SLA randomIrrelevantSLA = randomSLA();

		return randomIrrelevantSLA;
	}

	protected SLA randomPatchSLA() throws Exception {
		return randomSLA();
	}

	protected SLAResource slaResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSLAResourceTestCase.class);

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
	private com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource
		_slaResource;

}