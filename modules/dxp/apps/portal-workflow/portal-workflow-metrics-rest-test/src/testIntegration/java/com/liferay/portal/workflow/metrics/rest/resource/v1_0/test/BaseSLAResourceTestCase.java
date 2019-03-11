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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/portal-workflow-metrics/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetProcessSlasPage() throws Exception {
		Long processId = testGetProcessSlasPage_getProcessId();

		SLA sLA1 = testGetProcessSlasPage_addSLA(processId, randomSLA());
		SLA sLA2 = testGetProcessSlasPage_addSLA(processId, randomSLA());

		Page<SLA> page = invokeGetProcessSlasPage(
			processId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(sLA1, sLA2), (List<SLA>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessSlasPageWithPagination() throws Exception {
		Long processId = testGetProcessSlasPage_getProcessId();

		SLA sLA1 = testGetProcessSlasPage_addSLA(processId, randomSLA());
		SLA sLA2 = testGetProcessSlasPage_addSLA(processId, randomSLA());
		SLA sLA3 = testGetProcessSlasPage_addSLA(processId, randomSLA());

		Page<SLA> page1 = invokeGetProcessSlasPage(
			processId, Pagination.of(1, 2));

		List<SLA> sLAs1 = (List<SLA>)page1.getItems();

		Assert.assertEquals(sLAs1.toString(), 2, sLAs1.size());

		Page<SLA> page2 = invokeGetProcessSlasPage(
			processId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<SLA> sLAs2 = (List<SLA>)page2.getItems();

		Assert.assertEquals(sLAs2.toString(), 1, sLAs2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(sLA1, sLA2, sLA3),
			new ArrayList<SLA>() {
				{
					addAll(sLAs1);
					addAll(sLAs2);
				}
			});
	}

	protected SLA testGetProcessSlasPage_addSLA(Long processId, SLA sLA)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessSlasPage_getProcessId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<SLA> invokeGetProcessSlasPage(
			Long processId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/processes/{process-id}/slas", processId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<SLA>>() {
			});
	}

	protected Http.Response invokeGetProcessSlasPageResponse(
			Long processId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/processes/{process-id}/slas", processId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostProcessSla() throws Exception {
		SLA randomSLA = randomSLA();

		SLA postSLA = testPostProcessSla_addSLA(randomSLA);

		assertEquals(randomSLA, postSLA);
		assertValid(postSLA);
	}

	protected SLA testPostProcessSla_addSLA(SLA sLA) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected SLA invokePostProcessSla(Long processId, SLA sLA)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(sLA),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/processes/{process-id}/slas", processId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), SLA.class);
	}

	protected Http.Response invokePostProcessSlaResponse(
			Long processId, SLA sLA)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(sLA),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/processes/{process-id}/slas", processId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteProcessSla() throws Exception {
		SLA sLA = testDeleteProcessSla_addSLA();

		assertResponseCode(200, invokeDeleteProcessSlaResponse(
			sLA.getProcessId(), sLA.getId()));

		assertResponseCode(404, invokeGetProcessSlaResponse(
			sLA.getProcessId(), sLA.getId()));
	}

	protected SLA testDeleteProcessSla_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean invokeDeleteProcessSla(Long processId, Long slaId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/processes/{process-id}/slas/{sla-id}", processId);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteProcessSlaResponse(
			Long processId, Long slaId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/processes/{process-id}/slas/{sla-id}", processId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetProcessSla() throws Exception {
		SLA postSLA = testGetProcessSla_addSLA();

		SLA getSLA = invokeGetProcessSla(
			postSLA.getProcessId(), postSLA.getId());

		assertEquals(postSLA, getSLA);
		assertValid(getSLA);
	}

	protected SLA testGetProcessSla_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected SLA invokeGetProcessSla(Long processId, Long slaId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/processes/{process-id}/slas/{sla-id}", processId);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), SLA.class);
	}

	protected Http.Response invokeGetProcessSlaResponse(
			Long processId, Long slaId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/processes/{process-id}/slas/{sla-id}", processId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPutProcessSla() throws Exception {
		SLA postSLA = testPutProcessSla_addSLA();

		SLA randomSLA = randomSLA();

		SLA putSLA = invokePutProcessSla(
			postSLA.getProcessId(), postSLA.getId(), randomSLA);

		assertEquals(randomSLA, putSLA);
		assertValid(putSLA);

		SLA getSLA = invokeGetProcessSla(
			postSLA.getProcessId(), putSLA.getId());

		assertEquals(randomSLA, getSLA);
		assertValid(getSLA);
	}

	protected SLA testPutProcessSla_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected SLA invokePutProcessSla(Long processId, Long slaId, SLA sLA)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(sLA),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/processes/{process-id}/slas/{sla-id}", processId);

		options.setLocation(location);

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), SLA.class);
	}

	protected Http.Response invokePutProcessSlaResponse(
			Long processId, Long slaId, SLA sLA)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(sLA),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/processes/{process-id}/slas/{sla-id}", processId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(SLA sLA1, SLA sLA2) {
		Assert.assertTrue(sLA1 + " does not equal " + sLA2, equals(sLA1, sLA2));
	}

	protected void assertEquals(List<SLA> sLAs1, List<SLA> sLAs2) {
		Assert.assertEquals(sLAs1.size(), sLAs2.size());

		for (int i = 0; i < sLAs1.size(); i++) {
			SLA sLA1 = sLAs1.get(i);
			SLA sLA2 = sLAs2.get(i);

			assertEquals(sLA1, sLA2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<SLA> sLAs1, List<SLA> sLAs2) {
		Assert.assertEquals(sLAs1.size(), sLAs2.size());

		for (SLA sLA1 : sLAs1) {
			boolean contains = false;

			for (SLA sLA2 : sLAs2) {
				if (equals(sLA1, sLA2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(sLAs2 + " does not contain " + sLA1, contains);
		}
	}

	protected void assertValid(SLA sLA) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<SLA> page) {
		boolean valid = false;

		Collection<SLA> sLAs = page.getItems();

		int size = sLAs.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(SLA sLA1, SLA sLA2) {
		if (sLA1 == sLA2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_sLAResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_sLAResource;

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
		EntityField entityField, String operator, SLA sLA) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(sLA.getDescription()));
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
			sb.append(String.valueOf(sLA.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("pauseNodeNames")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("startNodeNames")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("stopNodeNames")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected SLA randomSLA() {
		return new SLA() {
			{
				description = RandomTestUtil.randomString();
				duration = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				processId = RandomTestUtil.randomLong();
			}
		};
	}

	protected SLA randomPatchSLA() {
		return randomSLA();
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

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
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	@Inject
	private SLAResource _sLAResource;

	private URL _resourceURL;

}