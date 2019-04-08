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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.foundation.dto.v1_0.Segment;
import com.liferay.headless.foundation.resource.v1_0.SegmentResource;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseSegmentResourceTestCase {

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
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceSegmentsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceSegmentsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceSegmentsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			Segment irrelevantSegment =
				testGetContentSpaceSegmentsPage_addSegment(
					irrelevantContentSpaceId, randomIrrelevantSegment());

			Page<Segment> page = invokeGetContentSpaceSegmentsPage(
				irrelevantContentSpaceId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSegment),
				(List<Segment>)page.getItems());
			assertValid(page);
		}

		Segment segment1 = testGetContentSpaceSegmentsPage_addSegment(
			contentSpaceId, randomSegment());

		Segment segment2 = testGetContentSpaceSegmentsPage_addSegment(
			contentSpaceId, randomSegment());

		Page<Segment> page = invokeGetContentSpaceSegmentsPage(
			contentSpaceId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2), (List<Segment>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceSegmentsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceSegmentsPage_getContentSpaceId();

		Segment segment1 = testGetContentSpaceSegmentsPage_addSegment(
			contentSpaceId, randomSegment());

		Segment segment2 = testGetContentSpaceSegmentsPage_addSegment(
			contentSpaceId, randomSegment());

		Segment segment3 = testGetContentSpaceSegmentsPage_addSegment(
			contentSpaceId, randomSegment());

		Page<Segment> page1 = invokeGetContentSpaceSegmentsPage(
			contentSpaceId, Pagination.of(1, 2));

		List<Segment> segments1 = (List<Segment>)page1.getItems();

		Assert.assertEquals(segments1.toString(), 2, segments1.size());

		Page<Segment> page2 = invokeGetContentSpaceSegmentsPage(
			contentSpaceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Segment> segments2 = (List<Segment>)page2.getItems();

		Assert.assertEquals(segments2.toString(), 1, segments2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2, segment3),
			new ArrayList<Segment>() {
				{
					addAll(segments1);
					addAll(segments2);
				}
			});
	}

	protected Segment testGetContentSpaceSegmentsPage_addSegment(
			Long contentSpaceId, Segment segment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceSegmentsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetContentSpaceSegmentsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<Segment> invokeGetContentSpaceSegmentsPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/segments",
					contentSpaceId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Segment>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceSegmentsPageResponse(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/segments",
					contentSpaceId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetContentSpaceUserAccountSegmentsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceUserAccountSegmentsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceUserAccountSegmentsPage_getIrrelevantContentSpaceId();
		Long userAccountId =
			testGetContentSpaceUserAccountSegmentsPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetContentSpaceUserAccountSegmentsPage_getIrrelevantUserAccountId();

		if ((irrelevantContentSpaceId != null) &&
			(irrelevantUserAccountId != null)) {

			Segment irrelevantSegment =
				testGetContentSpaceUserAccountSegmentsPage_addSegment(
					irrelevantContentSpaceId, irrelevantUserAccountId,
					randomIrrelevantSegment());

			Page<Segment> page = invokeGetContentSpaceUserAccountSegmentsPage(
				irrelevantContentSpaceId, irrelevantUserAccountId,
				Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSegment),
				(List<Segment>)page.getItems());
			assertValid(page);
		}

		Segment segment1 =
			testGetContentSpaceUserAccountSegmentsPage_addSegment(
				contentSpaceId, userAccountId, randomSegment());

		Segment segment2 =
			testGetContentSpaceUserAccountSegmentsPage_addSegment(
				contentSpaceId, userAccountId, randomSegment());

		Page<Segment> page = invokeGetContentSpaceUserAccountSegmentsPage(
			contentSpaceId, userAccountId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2), (List<Segment>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceUserAccountSegmentsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceUserAccountSegmentsPage_getContentSpaceId();
		Long userAccountId =
			testGetContentSpaceUserAccountSegmentsPage_getUserAccountId();

		Segment segment1 =
			testGetContentSpaceUserAccountSegmentsPage_addSegment(
				contentSpaceId, userAccountId, randomSegment());

		Segment segment2 =
			testGetContentSpaceUserAccountSegmentsPage_addSegment(
				contentSpaceId, userAccountId, randomSegment());

		Segment segment3 =
			testGetContentSpaceUserAccountSegmentsPage_addSegment(
				contentSpaceId, userAccountId, randomSegment());

		Page<Segment> page1 = invokeGetContentSpaceUserAccountSegmentsPage(
			contentSpaceId, userAccountId, Pagination.of(1, 2));

		List<Segment> segments1 = (List<Segment>)page1.getItems();

		Assert.assertEquals(segments1.toString(), 2, segments1.size());

		Page<Segment> page2 = invokeGetContentSpaceUserAccountSegmentsPage(
			contentSpaceId, userAccountId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Segment> segments2 = (List<Segment>)page2.getItems();

		Assert.assertEquals(segments2.toString(), 1, segments2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2, segment3),
			new ArrayList<Segment>() {
				{
					addAll(segments1);
					addAll(segments2);
				}
			});
	}

	protected Segment testGetContentSpaceUserAccountSegmentsPage_addSegment(
			Long contentSpaceId, Long userAccountId, Segment segment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSpaceUserAccountSegmentsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceUserAccountSegmentsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Long testGetContentSpaceUserAccountSegmentsPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSpaceUserAccountSegmentsPage_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	protected Page<Segment> invokeGetContentSpaceUserAccountSegmentsPage(
			Long contentSpaceId, Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/user-accounts/{userAccountId}/segments",
					contentSpaceId, userAccountId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Segment>>() {
			});
	}

	protected Http.Response
			invokeGetContentSpaceUserAccountSegmentsPageResponse(
				Long contentSpaceId, Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/user-accounts/{userAccountId}/segments",
					contentSpaceId, userAccountId);

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

	protected void assertEquals(Segment segment1, Segment segment2) {
		Assert.assertTrue(
			segment1 + " does not equal " + segment2,
			equals(segment1, segment2));
	}

	protected void assertEquals(
		List<Segment> segments1, List<Segment> segments2) {

		Assert.assertEquals(segments1.size(), segments2.size());

		for (int i = 0; i < segments1.size(); i++) {
			Segment segment1 = segments1.get(i);
			Segment segment2 = segments2.get(i);

			assertEquals(segment1, segment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Segment> segments1, List<Segment> segments2) {

		Assert.assertEquals(segments1.size(), segments2.size());

		for (Segment segment1 : segments1) {
			boolean contains = false;

			for (Segment segment2 : segments2) {
				if (equals(segment1, segment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				segments2 + " does not contain " + segment1, contains);
		}
	}

	protected void assertValid(Segment segment) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Segment> page) {
		boolean valid = false;

		Collection<Segment> segments = page.getItems();

		int size = segments.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(Segment segment1, Segment segment2) {
		if (segment1 == segment2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_segmentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_segmentResource;

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
		EntityField entityField, String operator, Segment segment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("criteria")) {
			sb.append("'");
			sb.append(String.valueOf(segment.getCriteria()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(segment.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(segment.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(segment.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("source")) {
			sb.append("'");
			sb.append(String.valueOf(segment.getSource()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Segment randomSegment() {
		return new Segment() {
			{
				active = RandomTestUtil.randomBoolean();
				contentSpaceId = RandomTestUtil.randomLong();
				criteria = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				source = RandomTestUtil.randomString();
			}
		};
	}

	protected Segment randomIrrelevantSegment() {
		return randomSegment();
	}

	protected Segment randomPatchSegment() {
		return randomSegment();
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
		BaseSegmentResourceTestCase.class);

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
	private SegmentResource _segmentResource;

	private URL _resourceURL;

}