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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.delivery.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.resource.v1_0.ContentStructureResource;
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
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseContentStructureResourceTestCase {

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

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceContentStructuresPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceContentStructuresPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceContentStructuresPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			ContentStructure irrelevantContentStructure =
				testGetContentSpaceContentStructuresPage_addContentStructure(
					irrelevantContentSpaceId,
					randomIrrelevantContentStructure());

			Page<ContentStructure> page =
				invokeGetContentSpaceContentStructuresPage(
					irrelevantContentSpaceId, null, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentStructure),
				(List<ContentStructure>)page.getItems());
			assertValid(page);
		}

		ContentStructure contentStructure1 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, randomContentStructure());

		Page<ContentStructure> page =
			invokeGetContentSpaceContentStructuresPage(
				contentSpaceId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentStructure1, contentStructure2),
			(List<ContentStructure>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceContentStructuresPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceContentStructuresPage_getContentSpaceId();

		ContentStructure contentStructure1 = randomContentStructure();
		ContentStructure contentStructure2 = randomContentStructure();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				contentStructure1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		contentStructure1 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, contentStructure1);

		Thread.sleep(1000);

		contentStructure2 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, contentStructure2);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				invokeGetContentSpaceContentStructuresPage(
					contentSpaceId, null,
					getFilterString(entityField, "eq", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceContentStructuresPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceContentStructuresPage_getContentSpaceId();

		ContentStructure contentStructure1 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, randomContentStructure());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentStructure contentStructure2 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, randomContentStructure());

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				invokeGetContentSpaceContentStructuresPage(
					contentSpaceId, null,
					getFilterString(entityField, "eq", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceContentStructuresPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceContentStructuresPage_getContentSpaceId();

		ContentStructure contentStructure1 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, randomContentStructure());

		ContentStructure contentStructure3 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, randomContentStructure());

		Page<ContentStructure> page1 =
			invokeGetContentSpaceContentStructuresPage(
				contentSpaceId, null, null, Pagination.of(1, 2), null);

		List<ContentStructure> contentStructures1 =
			(List<ContentStructure>)page1.getItems();

		Assert.assertEquals(
			contentStructures1.toString(), 2, contentStructures1.size());

		Page<ContentStructure> page2 =
			invokeGetContentSpaceContentStructuresPage(
				contentSpaceId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentStructure> contentStructures2 =
			(List<ContentStructure>)page2.getItems();

		Assert.assertEquals(
			contentStructures2.toString(), 1, contentStructures2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentStructure1, contentStructure2, contentStructure3),
			new ArrayList<ContentStructure>() {
				{
					addAll(contentStructures1);
					addAll(contentStructures2);
				}
			});
	}

	@Test
	public void testGetContentSpaceContentStructuresPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceContentStructuresPage_getContentSpaceId();

		ContentStructure contentStructure1 = randomContentStructure();
		ContentStructure contentStructure2 = randomContentStructure();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				contentStructure1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		contentStructure1 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, contentStructure1);

		Thread.sleep(1000);

		contentStructure2 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, contentStructure2);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> ascPage =
				invokeGetContentSpaceContentStructuresPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentStructure1, contentStructure2),
				(List<ContentStructure>)ascPage.getItems());

			Page<ContentStructure> descPage =
				invokeGetContentSpaceContentStructuresPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentStructure2, contentStructure1),
				(List<ContentStructure>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceContentStructuresPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceContentStructuresPage_getContentSpaceId();

		ContentStructure contentStructure1 = randomContentStructure();
		ContentStructure contentStructure2 = randomContentStructure();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				contentStructure1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				contentStructure2, entityField.getName(), "Bbb");
		}

		contentStructure1 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, contentStructure1);

		contentStructure2 =
			testGetContentSpaceContentStructuresPage_addContentStructure(
				contentSpaceId, contentStructure2);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> ascPage =
				invokeGetContentSpaceContentStructuresPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentStructure1, contentStructure2),
				(List<ContentStructure>)ascPage.getItems());

			Page<ContentStructure> descPage =
				invokeGetContentSpaceContentStructuresPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentStructure2, contentStructure1),
				(List<ContentStructure>)descPage.getItems());
		}
	}

	protected ContentStructure
			testGetContentSpaceContentStructuresPage_addContentStructure(
				Long contentSpaceId, ContentStructure contentStructure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceContentStructuresPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceContentStructuresPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<ContentStructure> invokeGetContentSpaceContentStructuresPage(
			Long contentSpaceId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/content-structures",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<ContentStructure>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceContentStructuresPageResponse(
			Long contentSpaceId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/content-structures",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetContentStructure() throws Exception {
		ContentStructure postContentStructure =
			testGetContentStructure_addContentStructure();

		ContentStructure getContentStructure = invokeGetContentStructure(
			postContentStructure.getId());

		assertEquals(postContentStructure, getContentStructure);
		assertValid(getContentStructure);
	}

	protected ContentStructure testGetContentStructure_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ContentStructure invokeGetContentStructure(
			Long contentStructureId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-structures/{contentStructureId}",
					contentStructureId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, ContentStructure.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetContentStructureResponse(
			Long contentStructureId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-structures/{contentStructureId}",
					contentStructureId);

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
		ContentStructure contentStructure1,
		ContentStructure contentStructure2) {

		Assert.assertTrue(
			contentStructure1 + " does not equal " + contentStructure2,
			equals(contentStructure1, contentStructure2));
	}

	protected void assertEquals(
		List<ContentStructure> contentStructures1,
		List<ContentStructure> contentStructures2) {

		Assert.assertEquals(
			contentStructures1.size(), contentStructures2.size());

		for (int i = 0; i < contentStructures1.size(); i++) {
			ContentStructure contentStructure1 = contentStructures1.get(i);
			ContentStructure contentStructure2 = contentStructures2.get(i);

			assertEquals(contentStructure1, contentStructure2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentStructure> contentStructures1,
		List<ContentStructure> contentStructures2) {

		Assert.assertEquals(
			contentStructures1.size(), contentStructures2.size());

		for (ContentStructure contentStructure1 : contentStructures1) {
			boolean contains = false;

			for (ContentStructure contentStructure2 : contentStructures2) {
				if (equals(contentStructure1, contentStructure2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentStructures2 + " does not contain " + contentStructure1,
				contains);
		}
	}

	protected void assertValid(ContentStructure contentStructure) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<ContentStructure> page) {
		boolean valid = false;

		Collection<ContentStructure> contentStructures = page.getItems();

		int size = contentStructures.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		ContentStructure contentStructure1,
		ContentStructure contentStructure2) {

		if (contentStructure1 == contentStructure2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_contentStructureResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentStructureResource;

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
		ContentStructure contentStructure) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentStructureFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(contentStructure.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(contentStructure.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(contentStructure.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(contentStructure.getName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected ContentStructure randomContentStructure() {
		return new ContentStructure() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected ContentStructure randomIrrelevantContentStructure() {
		return randomContentStructure();
	}

	protected ContentStructure randomPatchContentStructure() {
		return randomContentStructure();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
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
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
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

	protected String contentType = "application/json";
	protected Group irrelevantGroup;
	protected Group testGroup;
	protected String userNameAndPassword = "test@liferay.com:test";

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

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", contentType);

		return options;
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
		BaseContentStructureResourceTestCase.class);

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
	private ContentStructureResource _contentStructureResource;

	private URL _resourceURL;

}