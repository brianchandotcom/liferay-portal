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

package com.liferay.data.engine.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataDefinitionSerDes;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataDefinitionResourceTestCase {

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

		_resourceURL = new URL("http://localhost:8080/o/data-engine/v1.0");
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
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		DataDefinition dataDefinition1 = randomDataDefinition();

		String json = objectMapper.writeValueAsString(dataDefinition1);

		DataDefinition dataDefinition2 = DataDefinitionSerDes.toDTO(json);

		Assert.assertTrue(equals(dataDefinition1, dataDefinition2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		DataDefinition dataDefinition = randomDataDefinition();

		String json1 = objectMapper.writeValueAsString(dataDefinition);
		String json2 = DataDefinitionSerDes.toJSON(dataDefinition);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testDeleteDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testDeleteDataDefinition_addDataDefinition();

		assertResponseCode(
			204, invokeDeleteDataDefinitionResponse(dataDefinition.getId()));

		assertResponseCode(
			404, invokeGetDataDefinitionResponse(dataDefinition.getId()));
	}

	protected DataDefinition testDeleteDataDefinition_addDataDefinition()
		throws Exception {

		return invokePostSiteDataDefinition(
			testGroup.getGroupId(), randomDataDefinition());
	}

	protected void invokeDeleteDataDefinition(Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}", dataDefinitionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDataDefinitionResponse(
			Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}", dataDefinitionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testGetDataDefinition_addDataDefinition();

		DataDefinition getDataDefinition = invokeGetDataDefinition(
			postDataDefinition.getId());

		assertEquals(postDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition testGetDataDefinition_addDataDefinition()
		throws Exception {

		return invokePostSiteDataDefinition(
			testGroup.getGroupId(), randomDataDefinition());
	}

	protected DataDefinition invokeGetDataDefinition(Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}", dataDefinitionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataDefinitionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetDataDefinitionResponse(
			Long dataDefinitionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}", dataDefinitionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testPutDataDefinition_addDataDefinition();

		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition putDataDefinition = invokePutDataDefinition(
			postDataDefinition.getId(), randomDataDefinition);

		assertEquals(randomDataDefinition, putDataDefinition);
		assertValid(putDataDefinition);

		DataDefinition getDataDefinition = invokeGetDataDefinition(
			putDataDefinition.getId());

		assertEquals(randomDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition testPutDataDefinition_addDataDefinition()
		throws Exception {

		return invokePostSiteDataDefinition(
			testGroup.getGroupId(), randomDataDefinition());
	}

	protected DataDefinition invokePutDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataDefinitionSerDes.toJSON(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}", dataDefinitionId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataDefinitionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutDataDefinitionResponse(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataDefinitionSerDes.toJSON(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}", dataDefinitionId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostDataDefinitionDataDefinitionPermission()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected void invokePostDataDefinitionDataDefinitionPermission(
			Long dataDefinitionId, String operation,
			DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}/data-definition-permissions",
					dataDefinitionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response
			invokePostDataDefinitionDataDefinitionPermissionResponse(
				Long dataDefinitionId, String operation,
				DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}/data-definition-permissions",
					dataDefinitionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostSiteDataDefinitionPermission() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokePostSiteDataDefinitionPermission(
			Long siteId, String operation,
			DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/data-definition-permissions", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokePostSiteDataDefinitionPermissionResponse(
			Long siteId, String operation,
			DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/data-definition-permissions", siteId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetSiteDataDefinitionsPage() throws Exception {
		Long siteId = testGetSiteDataDefinitionsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteDataDefinitionsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			DataDefinition irrelevantDataDefinition =
				testGetSiteDataDefinitionsPage_addDataDefinition(
					irrelevantSiteId, randomIrrelevantDataDefinition());

			Page<DataDefinition> page = invokeGetSiteDataDefinitionsPage(
				irrelevantSiteId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataDefinition),
				(List<DataDefinition>)page.getItems());
			assertValid(page);
		}

		DataDefinition dataDefinition1 =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				siteId, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				siteId, randomDataDefinition());

		Page<DataDefinition> page = invokeGetSiteDataDefinitionsPage(
			siteId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2),
			(List<DataDefinition>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteDataDefinitionsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteDataDefinitionsPage_getSiteId();

		DataDefinition dataDefinition1 =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				siteId, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				siteId, randomDataDefinition());

		DataDefinition dataDefinition3 =
			testGetSiteDataDefinitionsPage_addDataDefinition(
				siteId, randomDataDefinition());

		Page<DataDefinition> page1 = invokeGetSiteDataDefinitionsPage(
			siteId, null, Pagination.of(1, 2));

		List<DataDefinition> dataDefinitions1 =
			(List<DataDefinition>)page1.getItems();

		Assert.assertEquals(
			dataDefinitions1.toString(), 2, dataDefinitions1.size());

		Page<DataDefinition> page2 = invokeGetSiteDataDefinitionsPage(
			siteId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataDefinition> dataDefinitions2 =
			(List<DataDefinition>)page2.getItems();

		Assert.assertEquals(
			dataDefinitions2.toString(), 1, dataDefinitions2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2, dataDefinition3),
			new ArrayList<DataDefinition>() {
				{
					addAll(dataDefinitions1);
					addAll(dataDefinitions2);
				}
			});
	}

	protected DataDefinition testGetSiteDataDefinitionsPage_addDataDefinition(
			Long siteId, DataDefinition dataDefinition)
		throws Exception {

		return invokePostSiteDataDefinition(siteId, dataDefinition);
	}

	protected Long testGetSiteDataDefinitionsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteDataDefinitionsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<DataDefinition> invokeGetSiteDataDefinitionsPage(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/data-definitions", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, DataDefinitionSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteDataDefinitionsPageResponse(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/data-definitions", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostSiteDataDefinition() throws Exception {
		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition postDataDefinition =
			testPostSiteDataDefinition_addDataDefinition(randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	protected DataDefinition testPostSiteDataDefinition_addDataDefinition(
			DataDefinition dataDefinition)
		throws Exception {

		return invokePostSiteDataDefinition(
			testGetSiteDataDefinitionsPage_getSiteId(), dataDefinition);
	}

	protected DataDefinition invokePostSiteDataDefinition(
			Long siteId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataDefinitionSerDes.toJSON(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/data-definitions", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataDefinitionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostSiteDataDefinitionResponse(
			Long siteId, DataDefinition dataDefinition)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataDefinitionSerDes.toJSON(dataDefinition),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/data-definitions", siteId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		Assert.assertTrue(
			dataDefinition1 + " does not equal " + dataDefinition2,
			equals(dataDefinition1, dataDefinition2));
	}

	protected void assertEquals(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (int i = 0; i < dataDefinitions1.size(); i++) {
			DataDefinition dataDefinition1 = dataDefinitions1.get(i);
			DataDefinition dataDefinition2 = dataDefinitions2.get(i);

			assertEquals(dataDefinition1, dataDefinition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (DataDefinition dataDefinition1 : dataDefinitions1) {
			boolean contains = false;

			for (DataDefinition dataDefinition2 : dataDefinitions2) {
				if (equals(dataDefinition1, dataDefinition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataDefinitions2 + " does not contain " + dataDefinition1,
				contains);
		}
	}

	protected void assertValid(DataDefinition dataDefinition) {
		boolean valid = true;

		if (dataDefinition.getDateCreated() == null) {
			valid = false;
		}

		if (dataDefinition.getDateModified() == null) {
			valid = false;
		}

		if (dataDefinition.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				dataDefinition.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dataDefinitionFields", additionalAssertFieldName)) {

				if (dataDefinition.getDataDefinitionFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionRules", additionalAssertFieldName)) {

				if (dataDefinition.getDataDefinitionRules() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (dataDefinition.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataDefinition.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("storageType", additionalAssertFieldName)) {
				if (dataDefinition.getStorageType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (dataDefinition.getUserId() == null) {
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

	protected void assertValid(Page<DataDefinition> page) {
		boolean valid = false;

		Collection<DataDefinition> dataDefinitions = page.getItems();

		int size = dataDefinitions.size();

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

	protected boolean equals(
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		if (dataDefinition1 == dataDefinition2) {
			return true;
		}

		if (!Objects.equals(
				dataDefinition1.getSiteId(), dataDefinition2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dataDefinitionFields", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDataDefinitionFields(),
						dataDefinition2.getDataDefinitionFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionRules", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDataDefinitionRules(),
						dataDefinition2.getDataDefinitionRules())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDateCreated(),
						dataDefinition2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDateModified(),
						dataDefinition2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDescription(),
						dataDefinition2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getId(), dataDefinition2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getName(), dataDefinition2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("storageType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getStorageType(),
						dataDefinition2.getStorageType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getUserId(),
						dataDefinition2.getUserId())) {

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
		if (!(_dataDefinitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataDefinitionResource;

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
		EntityField entityField, String operator,
		DataDefinition dataDefinition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataDefinitionFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataDefinitionRules")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							dataDefinition.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							dataDefinition.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataDefinition.getDateCreated()));
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
						DateUtils.addSeconds(
							dataDefinition.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							dataDefinition.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataDefinition.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("storageType")) {
			sb.append("'");
			sb.append(String.valueOf(dataDefinition.getStorageType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected DataDefinition randomDataDefinition() {
		return new DataDefinition() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				storageType = RandomTestUtil.randomString();
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataDefinition randomIrrelevantDataDefinition() {
		DataDefinition randomIrrelevantDataDefinition = randomDataDefinition();

		randomIrrelevantDataDefinition.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDataDefinition;
	}

	protected DataDefinition randomPatchDataDefinition() {
		return randomDataDefinition();
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
		BaseDataDefinitionResourceTestCase.class);

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
	private DataDefinitionResource _dataDefinitionResource;

	private URL _resourceURL;

}