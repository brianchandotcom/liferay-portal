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

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordCollectionPermission;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataRecordCollectionSerDes;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
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
public abstract class BaseDataRecordCollectionResourceTestCase {

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

		DataRecordCollection dataRecordCollection1 =
			randomDataRecordCollection();

		String json = objectMapper.writeValueAsString(dataRecordCollection1);

		DataRecordCollection dataRecordCollection2 =
			DataRecordCollectionSerDes.toDTO(json);

		Assert.assertTrue(equals(dataRecordCollection1, dataRecordCollection2));
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

		DataRecordCollection dataRecordCollection =
			randomDataRecordCollection();

		String json1 = objectMapper.writeValueAsString(dataRecordCollection);
		String json2 = DataRecordCollectionSerDes.toJSON(dataRecordCollection);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetDataDefinitionDataRecordCollectionsPage()
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getIrrelevantDataDefinitionId();

		if ((irrelevantDataDefinitionId != null)) {
			DataRecordCollection irrelevantDataRecordCollection =
				testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
					irrelevantDataDefinitionId,
					randomIrrelevantDataRecordCollection());

			Page<DataRecordCollection> page =
				invokeGetDataDefinitionDataRecordCollectionsPage(
					irrelevantDataDefinitionId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataRecordCollection),
				(List<DataRecordCollection>)page.getItems());
			assertValid(page);
		}

		DataRecordCollection dataRecordCollection1 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection2 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		Page<DataRecordCollection> page =
			invokeGetDataDefinitionDataRecordCollectionsPage(
				dataDefinitionId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecordCollection1, dataRecordCollection2),
			(List<DataRecordCollection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDataDefinitionDataRecordCollectionsPageWithPagination()
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId();

		DataRecordCollection dataRecordCollection1 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection2 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection3 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		Page<DataRecordCollection> page1 =
			invokeGetDataDefinitionDataRecordCollectionsPage(
				dataDefinitionId, null, Pagination.of(1, 2));

		List<DataRecordCollection> dataRecordCollections1 =
			(List<DataRecordCollection>)page1.getItems();

		Assert.assertEquals(
			dataRecordCollections1.toString(), 2,
			dataRecordCollections1.size());

		Page<DataRecordCollection> page2 =
			invokeGetDataDefinitionDataRecordCollectionsPage(
				dataDefinitionId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataRecordCollection> dataRecordCollections2 =
			(List<DataRecordCollection>)page2.getItems();

		Assert.assertEquals(
			dataRecordCollections2.toString(), 1,
			dataRecordCollections2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				dataRecordCollection1, dataRecordCollection2,
				dataRecordCollection3),
			new ArrayList<DataRecordCollection>() {
				{
					addAll(dataRecordCollections1);
					addAll(dataRecordCollections2);
				}
			});
	}

	protected DataRecordCollection
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				Long dataDefinitionId,
				DataRecordCollection dataRecordCollection)
		throws Exception {

		return invokePostDataDefinitionDataRecordCollection(
			dataDefinitionId, dataRecordCollection);
	}

	protected Long
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataDefinitionDataRecordCollectionsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return null;
	}

	protected Page<DataRecordCollection>
			invokeGetDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}/data-record-collections",
					dataDefinitionId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, DataRecordCollectionSerDes::toDTO);
	}

	protected Http.Response
			invokeGetDataDefinitionDataRecordCollectionsPageResponse(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}/data-record-collections",
					dataDefinitionId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostDataDefinitionDataRecordCollection() throws Exception {
		DataRecordCollection randomDataRecordCollection =
			randomDataRecordCollection();

		DataRecordCollection postDataRecordCollection =
			testPostDataDefinitionDataRecordCollection_addDataRecordCollection(
				randomDataRecordCollection);

		assertEquals(randomDataRecordCollection, postDataRecordCollection);
		assertValid(postDataRecordCollection);
	}

	protected DataRecordCollection
			testPostDataDefinitionDataRecordCollection_addDataRecordCollection(
				DataRecordCollection dataRecordCollection)
		throws Exception {

		return invokePostDataDefinitionDataRecordCollection(
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId(),
			dataRecordCollection);
	}

	protected DataRecordCollection invokePostDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordCollectionSerDes.toJSON(dataRecordCollection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}/data-record-collections",
					dataDefinitionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataRecordCollectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response
			invokePostDataDefinitionDataRecordCollectionResponse(
				Long dataDefinitionId,
				DataRecordCollection dataRecordCollection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordCollectionSerDes.toJSON(dataRecordCollection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-definitions/{dataDefinitionId}/data-record-collections",
					dataDefinitionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteDataRecordCollection() throws Exception {
		DataRecordCollection dataRecordCollection =
			testDeleteDataRecordCollection_addDataRecordCollection();

		assertResponseCode(
			204,
			invokeDeleteDataRecordCollectionResponse(
				dataRecordCollection.getId()));

		assertResponseCode(
			404,
			invokeGetDataRecordCollectionResponse(
				dataRecordCollection.getId()));
	}

	protected DataRecordCollection
			testDeleteDataRecordCollection_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}",
					dataRecordCollectionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDataRecordCollectionResponse(
			Long dataRecordCollectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}",
					dataRecordCollectionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDataRecordCollection() throws Exception {
		DataRecordCollection postDataRecordCollection =
			testGetDataRecordCollection_addDataRecordCollection();

		DataRecordCollection getDataRecordCollection =
			invokeGetDataRecordCollection(postDataRecordCollection.getId());

		assertEquals(postDataRecordCollection, getDataRecordCollection);
		assertValid(getDataRecordCollection);
	}

	protected DataRecordCollection
			testGetDataRecordCollection_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataRecordCollection invokeGetDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}",
					dataRecordCollectionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataRecordCollectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetDataRecordCollectionResponse(
			Long dataRecordCollectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}",
					dataRecordCollectionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDataRecordCollection() throws Exception {
		DataRecordCollection postDataRecordCollection =
			testPutDataRecordCollection_addDataRecordCollection();

		DataRecordCollection randomDataRecordCollection =
			randomDataRecordCollection();

		DataRecordCollection putDataRecordCollection =
			invokePutDataRecordCollection(
				postDataRecordCollection.getId(), randomDataRecordCollection);

		assertEquals(randomDataRecordCollection, putDataRecordCollection);
		assertValid(putDataRecordCollection);

		DataRecordCollection getDataRecordCollection =
			invokeGetDataRecordCollection(putDataRecordCollection.getId());

		assertEquals(randomDataRecordCollection, getDataRecordCollection);
		assertValid(getDataRecordCollection);
	}

	protected DataRecordCollection
			testPutDataRecordCollection_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataRecordCollection invokePutDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordCollectionSerDes.toJSON(dataRecordCollection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}",
					dataRecordCollectionId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataRecordCollectionSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutDataRecordCollectionResponse(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordCollectionSerDes.toJSON(dataRecordCollection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}",
					dataRecordCollectionId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostDataRecordCollectionDataRecordCollectionPermission()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected void invokePostDataRecordCollectionDataRecordCollectionPermission(
			Long dataRecordCollectionId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-record-collection-permissions",
					dataRecordCollectionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response
			invokePostDataRecordCollectionDataRecordCollectionPermissionResponse(
				Long dataRecordCollectionId, String operation,
				DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-record-collection-permissions",
					dataRecordCollectionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostSiteDataRecordCollectionPermission() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokePostSiteDataRecordCollectionPermission(
			Long siteId, String operation,
			DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/data-record-collection-permissions",
					siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response
			invokePostSiteDataRecordCollectionPermissionResponse(
				Long siteId, String operation,
				DataRecordCollectionPermission dataRecordCollectionPermission)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/data-record-collection-permissions",
					siteId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetSiteDataRecordCollectionsPage() throws Exception {
		Long siteId = testGetSiteDataRecordCollectionsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteDataRecordCollectionsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			DataRecordCollection irrelevantDataRecordCollection =
				testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
					irrelevantSiteId, randomIrrelevantDataRecordCollection());

			Page<DataRecordCollection> page =
				invokeGetSiteDataRecordCollectionsPage(
					irrelevantSiteId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataRecordCollection),
				(List<DataRecordCollection>)page.getItems());
			assertValid(page);
		}

		DataRecordCollection dataRecordCollection1 =
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				siteId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection2 =
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				siteId, randomDataRecordCollection());

		Page<DataRecordCollection> page =
			invokeGetSiteDataRecordCollectionsPage(
				siteId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecordCollection1, dataRecordCollection2),
			(List<DataRecordCollection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteDataRecordCollectionsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteDataRecordCollectionsPage_getSiteId();

		DataRecordCollection dataRecordCollection1 =
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				siteId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection2 =
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				siteId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection3 =
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				siteId, randomDataRecordCollection());

		Page<DataRecordCollection> page1 =
			invokeGetSiteDataRecordCollectionsPage(
				siteId, null, Pagination.of(1, 2));

		List<DataRecordCollection> dataRecordCollections1 =
			(List<DataRecordCollection>)page1.getItems();

		Assert.assertEquals(
			dataRecordCollections1.toString(), 2,
			dataRecordCollections1.size());

		Page<DataRecordCollection> page2 =
			invokeGetSiteDataRecordCollectionsPage(
				siteId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataRecordCollection> dataRecordCollections2 =
			(List<DataRecordCollection>)page2.getItems();

		Assert.assertEquals(
			dataRecordCollections2.toString(), 1,
			dataRecordCollections2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				dataRecordCollection1, dataRecordCollection2,
				dataRecordCollection3),
			new ArrayList<DataRecordCollection>() {
				{
					addAll(dataRecordCollections1);
					addAll(dataRecordCollections2);
				}
			});
	}

	protected DataRecordCollection
			testGetSiteDataRecordCollectionsPage_addDataRecordCollection(
				Long siteId, DataRecordCollection dataRecordCollection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteDataRecordCollectionsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteDataRecordCollectionsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<DataRecordCollection> invokeGetSiteDataRecordCollectionsPage(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/data-record-collections", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, DataRecordCollectionSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteDataRecordCollectionsPageResponse(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/data-record-collections", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		DataRecordCollection dataRecordCollection1,
		DataRecordCollection dataRecordCollection2) {

		Assert.assertTrue(
			dataRecordCollection1 + " does not equal " + dataRecordCollection2,
			equals(dataRecordCollection1, dataRecordCollection2));
	}

	protected void assertEquals(
		List<DataRecordCollection> dataRecordCollections1,
		List<DataRecordCollection> dataRecordCollections2) {

		Assert.assertEquals(
			dataRecordCollections1.size(), dataRecordCollections2.size());

		for (int i = 0; i < dataRecordCollections1.size(); i++) {
			DataRecordCollection dataRecordCollection1 =
				dataRecordCollections1.get(i);
			DataRecordCollection dataRecordCollection2 =
				dataRecordCollections2.get(i);

			assertEquals(dataRecordCollection1, dataRecordCollection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataRecordCollection> dataRecordCollections1,
		List<DataRecordCollection> dataRecordCollections2) {

		Assert.assertEquals(
			dataRecordCollections1.size(), dataRecordCollections2.size());

		for (DataRecordCollection dataRecordCollection1 :
				dataRecordCollections1) {

			boolean contains = false;

			for (DataRecordCollection dataRecordCollection2 :
					dataRecordCollections2) {

				if (equals(dataRecordCollection1, dataRecordCollection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataRecordCollections2 + " does not contain " +
					dataRecordCollection1,
				contains);
		}
	}

	protected void assertValid(DataRecordCollection dataRecordCollection) {
		boolean valid = true;

		if (dataRecordCollection.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (dataRecordCollection.getDataDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (dataRecordCollection.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataRecordCollection.getName() == null) {
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

	protected void assertValid(Page<DataRecordCollection> page) {
		boolean valid = false;

		Collection<DataRecordCollection> dataRecordCollections =
			page.getItems();

		int size = dataRecordCollections.size();

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
		DataRecordCollection dataRecordCollection1,
		DataRecordCollection dataRecordCollection2) {

		if (dataRecordCollection1 == dataRecordCollection2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getDataDefinitionId(),
						dataRecordCollection2.getDataDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getDescription(),
						dataRecordCollection2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getId(),
						dataRecordCollection2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getName(),
						dataRecordCollection2.getName())) {

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
		if (!(_dataRecordCollectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataRecordCollectionResource;

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
		DataRecordCollection dataRecordCollection) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected DataRecordCollection randomDataRecordCollection() {
		return new DataRecordCollection() {
			{
				dataDefinitionId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataRecordCollection randomIrrelevantDataRecordCollection() {
		DataRecordCollection randomIrrelevantDataRecordCollection =
			randomDataRecordCollection();

		return randomIrrelevantDataRecordCollection;
	}

	protected DataRecordCollection randomPatchDataRecordCollection() {
		return randomDataRecordCollection();
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
		BaseDataRecordCollectionResourceTestCase.class);

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
	private DataRecordCollectionResource _dataRecordCollectionResource;

	private URL _resourceURL;

}