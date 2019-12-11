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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.Segment;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.SegmentResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.SegmentSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_segmentResource.setContextCompany(testCompany);

		SegmentResource.Builder builder = SegmentResource.builder();

		segmentResource = builder.locale(
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

		Segment segment1 = randomSegment();

		String json = objectMapper.writeValueAsString(segment1);

		Segment segment2 = SegmentSerDes.toDTO(json);

		Assert.assertTrue(equals(segment1, segment2));
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

		Segment segment = randomSegment();

		String json1 = objectMapper.writeValueAsString(segment);
		String json2 = SegmentSerDes.toJSON(segment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Segment segment = randomSegment();

		segment.setCriteria(regex);
		segment.setName(regex);
		segment.setSource(regex);

		String json = SegmentSerDes.toJSON(segment);

		Assert.assertFalse(json.contains(regex));

		segment = SegmentSerDes.toDTO(json);

		Assert.assertEquals(regex, segment.getCriteria());
		Assert.assertEquals(regex, segment.getName());
		Assert.assertEquals(regex, segment.getSource());
	}

	@Test
	public void testGetSiteSegmentsPage() throws Exception {
		Page<Segment> page = segmentResource.getSiteSegmentsPage(
			testGetSiteSegmentsPage_getSiteId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteSegmentsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteSegmentsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Segment irrelevantSegment = testGetSiteSegmentsPage_addSegment(
				irrelevantSiteId, randomIrrelevantSegment());

			page = segmentResource.getSiteSegmentsPage(
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

		page = segmentResource.getSiteSegmentsPage(siteId, Pagination.of(1, 2));

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

		Page<Segment> page1 = segmentResource.getSiteSegmentsPage(
			siteId, Pagination.of(1, 2));

		List<Segment> segments1 = (List<Segment>)page1.getItems();

		Assert.assertEquals(segments1.toString(), 2, segments1.size());

		Page<Segment> page2 = segmentResource.getSiteSegmentsPage(
			siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Segment> segments2 = (List<Segment>)page2.getItems();

		Assert.assertEquals(segments2.toString(), 1, segments2.size());

		Page<Segment> page3 = segmentResource.getSiteSegmentsPage(
			siteId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2, segment3),
			(List<Segment>)page3.getItems());
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

	@Test
	public void testGraphQLGetSiteSegmentsPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"segments",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteId", testGroup.getGroupId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject segmentsJSONObject = dataJSONObject.getJSONObject(
			"segments");

		Assert.assertEquals(0, segmentsJSONObject.get("totalCount"));

		Segment segment1 = testGraphQLSegment_addSegment();
		Segment segment2 = testGraphQLSegment_addSegment();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		segmentsJSONObject = dataJSONObject.getJSONObject("segments");

		Assert.assertEquals(2, segmentsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(segment1, segment2),
			segmentsJSONObject.getJSONArray("items"));
	}

	@Test
	public void testGetSiteUserAccountSegmentsPage() throws Exception {
		Page<Segment> page = segmentResource.getSiteUserAccountSegmentsPage(
			testGetSiteUserAccountSegmentsPage_getSiteId(),
			testGetSiteUserAccountSegmentsPage_getUserAccountId());

		Assert.assertEquals(0, page.getTotalCount());

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

			page = segmentResource.getSiteUserAccountSegmentsPage(
				irrelevantSiteId, irrelevantUserAccountId);

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

		page = segmentResource.getSiteUserAccountSegmentsPage(
			siteId, userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(segment1, segment2), (List<Segment>)page.getItems());
		assertValid(page);
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

	protected Segment testGraphQLSegment_addSegment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
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

	protected void assertEqualsJSONArray(
		List<Segment> segments, JSONArray jsonArray) {

		for (Segment segment : segments) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(segment, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + segment, contains);
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

		java.util.Collection<Segment> segments = page.getItems();

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

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
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

	protected boolean equalsJSONObject(Segment segment, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("active", fieldName)) {
				if (!Objects.deepEquals(
						segment.getActive(), jsonObject.getBoolean("active"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("criteria", fieldName)) {
				if (!Objects.deepEquals(
						segment.getCriteria(),
						jsonObject.getString("criteria"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						segment.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						segment.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("source", fieldName)) {
				if (!Objects.deepEquals(
						segment.getSource(), jsonObject.getString("source"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(segment.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(segment.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(segment.getDateCreated()));
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
						DateUtils.addSeconds(segment.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(segment.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(segment.getDateModified()));
			}

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

	protected Segment randomSegment() throws Exception {
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

	protected Segment randomIrrelevantSegment() throws Exception {
		Segment randomIrrelevantSegment = randomSegment();

		randomIrrelevantSegment.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantSegment;
	}

	protected Segment randomPatchSegment() throws Exception {
		return randomSegment();
	}

	protected SegmentResource segmentResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

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
	private com.liferay.headless.admin.user.resource.v1_0.SegmentResource
		_segmentResource;

}