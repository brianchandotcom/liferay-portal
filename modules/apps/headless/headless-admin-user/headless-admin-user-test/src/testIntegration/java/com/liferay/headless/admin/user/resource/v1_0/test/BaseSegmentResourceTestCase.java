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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.admin.user.dto.v1_0.Segment;
import com.liferay.headless.admin.user.resource.v1_0.SegmentResource;
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
import java.util.Locale;
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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-admin-user/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetSiteSegmentsPage() throws Exception {
		Long siteId = testGetSiteSegmentsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteSegmentsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Segment irrelevantSegment = testGetSiteSegmentsPage_addSegment(
				irrelevantSiteId, randomIrrelevantSegment());

			Page<Segment> page = invokeGetSiteSegmentsPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSegment),
				(List<Segment>)page.getItems());
			assertValid(page);
		}

		Segment segment1 = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		Segment segment2 = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		Page<Segment> page = invokeGetSiteSegmentsPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2), (List<Segment>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteSegmentsPageWithPagination() throws Exception {
		Long siteId = testGetSiteSegmentsPage_getSiteId();

		Segment segment1 = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		Segment segment2 = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		Segment segment3 = testGetSiteSegmentsPage_addSegment(
			siteId, randomSegment());

		Page<Segment> page1 = invokeGetSiteSegmentsPage(
			siteId, Pagination.of(1, 2));

		List<Segment> segments1 = (List<Segment>)page1.getItems();

		Assert.assertEquals(segments1.toString(), 2, segments1.size());

		Page<Segment> page2 = invokeGetSiteSegmentsPage(
			siteId, Pagination.of(2, 2));

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

	protected Segment testGetSiteSegmentsPage_addSegment(
			Long siteId, Segment segment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteSegmentsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteSegmentsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<Segment> invokeGetSiteSegmentsPage(
			Long siteId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/segments", siteId);

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

	protected Http.Response invokeGetSiteSegmentsPageResponse(
			Long siteId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/segments", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetSiteUserAccountSegmentsPage() throws Exception {
		Long siteId = testGetSiteUserAccountSegmentsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteUserAccountSegmentsPage_getIrrelevantSiteId();
		Long userAccountId =
			testGetSiteUserAccountSegmentsPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetSiteUserAccountSegmentsPage_getIrrelevantUserAccountId();

		if ((irrelevantSiteId != null) && (irrelevantUserAccountId != null)) {
			Segment irrelevantSegment =
				testGetSiteUserAccountSegmentsPage_addSegment(
					irrelevantSiteId, irrelevantUserAccountId,
					randomIrrelevantSegment());

			Page<Segment> page = invokeGetSiteUserAccountSegmentsPage(
				irrelevantSiteId, irrelevantUserAccountId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSegment),
				(List<Segment>)page.getItems());
			assertValid(page);
		}

		Segment segment1 = testGetSiteUserAccountSegmentsPage_addSegment(
			siteId, userAccountId, randomSegment());

		Segment segment2 = testGetSiteUserAccountSegmentsPage_addSegment(
			siteId, userAccountId, randomSegment());

		Page<Segment> page = invokeGetSiteUserAccountSegmentsPage(
			siteId, userAccountId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2), (List<Segment>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteUserAccountSegmentsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteUserAccountSegmentsPage_getSiteId();
		Long userAccountId =
			testGetSiteUserAccountSegmentsPage_getUserAccountId();

		Segment segment1 = testGetSiteUserAccountSegmentsPage_addSegment(
			siteId, userAccountId, randomSegment());

		Segment segment2 = testGetSiteUserAccountSegmentsPage_addSegment(
			siteId, userAccountId, randomSegment());

		Segment segment3 = testGetSiteUserAccountSegmentsPage_addSegment(
			siteId, userAccountId, randomSegment());

		Page<Segment> page1 = invokeGetSiteUserAccountSegmentsPage(
			siteId, userAccountId, Pagination.of(1, 2));

		List<Segment> segments1 = (List<Segment>)page1.getItems();

		Assert.assertEquals(segments1.toString(), 2, segments1.size());

		Page<Segment> page2 = invokeGetSiteUserAccountSegmentsPage(
			siteId, userAccountId, Pagination.of(2, 2));

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

	protected Segment testGetSiteUserAccountSegmentsPage_addSegment(
			Long siteId, Long userAccountId, Segment segment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteUserAccountSegmentsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteUserAccountSegmentsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Long testGetSiteUserAccountSegmentsPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetSiteUserAccountSegmentsPage_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	protected Page<Segment> invokeGetSiteUserAccountSegmentsPage(
			Long siteId, Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/user-accounts/{userAccountId}/segments",
					siteId, userAccountId);

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

	protected Http.Response invokeGetSiteUserAccountSegmentsPageResponse(
			Long siteId, Long userAccountId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/user-accounts/{userAccountId}/segments",
					siteId, userAccountId);

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
		boolean valid = true;

		if (segment.getDateCreated() == null) {
			valid = false;
		}

		if (segment.getDateModified() == null) {
			valid = false;
		}

		if (segment.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(segment.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (segment.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("criteria", additionalAssertFieldName)) {
				if (segment.getCriteria() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (segment.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("source", additionalAssertFieldName)) {
				if (segment.getSource() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(Segment segment1, Segment segment2) {
		if (segment1 == segment2) {
			return true;
		}

		if (!Objects.equals(segment1.getSiteId(), segment2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segment1.getActive(), segment2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("criteria", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segment1.getCriteria(), segment2.getCriteria())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segment1.getDateCreated(), segment2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segment1.getDateModified(),
						segment2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(segment1.getId(), segment2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segment1.getName(), segment2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("source", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segment1.getSource(), segment2.getSource())) {

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

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
				criteria = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				source = RandomTestUtil.randomString();
			}
		};
	}

	protected Segment randomIrrelevantSegment() {
		Segment randomIrrelevantSegment = randomSegment();

		randomIrrelevantSegment.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantSegment;
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

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

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
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

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