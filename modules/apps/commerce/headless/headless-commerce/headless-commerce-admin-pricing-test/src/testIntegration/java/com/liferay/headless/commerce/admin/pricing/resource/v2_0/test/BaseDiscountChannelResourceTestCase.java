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

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountChannel;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountChannelResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountChannelSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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
import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseDiscountChannelResourceTestCase {

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

		_discountChannelResource.setContextCompany(testCompany);

		DiscountChannelResource.Builder builder =
			DiscountChannelResource.builder();

		discountChannelResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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

		DiscountChannel discountChannel1 = randomDiscountChannel();

		String json = objectMapper.writeValueAsString(discountChannel1);

		DiscountChannel discountChannel2 = DiscountChannelSerDes.toDTO(json);

		Assert.assertTrue(equals(discountChannel1, discountChannel2));
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

		DiscountChannel discountChannel = randomDiscountChannel();

		String json1 = objectMapper.writeValueAsString(discountChannel);
		String json2 = DiscountChannelSerDes.toJSON(discountChannel);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DiscountChannel discountChannel = randomDiscountChannel();

		discountChannel.setChannelExternalReferenceCode(regex);
		discountChannel.setChannelName(regex);
		discountChannel.setDiscountExternalReferenceCode(regex);

		String json = DiscountChannelSerDes.toJSON(discountChannel);

		Assert.assertFalse(json.contains(regex));

		discountChannel = DiscountChannelSerDes.toDTO(json);

		Assert.assertEquals(
			regex, discountChannel.getChannelExternalReferenceCode());
		Assert.assertEquals(regex, discountChannel.getChannelName());
		Assert.assertEquals(
			regex, discountChannel.getDiscountExternalReferenceCode());
	}

	@Test
	public void testDeleteDiscountChannel() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountChannel discountChannel =
			testDeleteDiscountChannel_addDiscountChannel();

		assertHttpResponseStatusCode(
			204,
			discountChannelResource.deleteDiscountChannelHttpResponse(
				discountChannel.getId()));
	}

	protected DiscountChannel testDeleteDiscountChannel_addDiscountChannel()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDiscountChannel() throws Exception {
		DiscountChannel discountChannel =
			testGraphQLDiscountChannel_addDiscountChannel();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDiscountChannel",
						new HashMap<String, Object>() {
							{
								put(
									"discountChannelId",
									discountChannel.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDiscountChannel"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"discountChannel",
						new HashMap<String, Object>() {
							{
								put(
									"discountChannelId",
									discountChannel.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountChannelsPage()
		throws Exception {

		Page<DiscountChannel> page =
			discountChannelResource.
				getDiscountByExternalReferenceCodeDiscountChannelsPage(
					testGetDiscountByExternalReferenceCodeDiscountChannelsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			DiscountChannel irrelevantDiscountChannel =
				testGetDiscountByExternalReferenceCodeDiscountChannelsPage_addDiscountChannel(
					irrelevantExternalReferenceCode,
					randomIrrelevantDiscountChannel());

			page =
				discountChannelResource.
					getDiscountByExternalReferenceCodeDiscountChannelsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountChannel),
				(List<DiscountChannel>)page.getItems());
			assertValid(page);
		}

		DiscountChannel discountChannel1 =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_addDiscountChannel(
				externalReferenceCode, randomDiscountChannel());

		DiscountChannel discountChannel2 =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_addDiscountChannel(
				externalReferenceCode, randomDiscountChannel());

		page =
			discountChannelResource.
				getDiscountByExternalReferenceCodeDiscountChannelsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountChannel1, discountChannel2),
			(List<DiscountChannel>)page.getItems());
		assertValid(page);

		discountChannelResource.deleteDiscountChannel(null);

		discountChannelResource.deleteDiscountChannel(null);
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountChannelsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_getExternalReferenceCode();

		DiscountChannel discountChannel1 =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_addDiscountChannel(
				externalReferenceCode, randomDiscountChannel());

		DiscountChannel discountChannel2 =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_addDiscountChannel(
				externalReferenceCode, randomDiscountChannel());

		DiscountChannel discountChannel3 =
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_addDiscountChannel(
				externalReferenceCode, randomDiscountChannel());

		Page<DiscountChannel> page1 =
			discountChannelResource.
				getDiscountByExternalReferenceCodeDiscountChannelsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<DiscountChannel> discountChannels1 =
			(List<DiscountChannel>)page1.getItems();

		Assert.assertEquals(
			discountChannels1.toString(), 2, discountChannels1.size());

		Page<DiscountChannel> page2 =
			discountChannelResource.
				getDiscountByExternalReferenceCodeDiscountChannelsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountChannel> discountChannels2 =
			(List<DiscountChannel>)page2.getItems();

		Assert.assertEquals(
			discountChannels2.toString(), 1, discountChannels2.size());

		Page<DiscountChannel> page3 =
			discountChannelResource.
				getDiscountByExternalReferenceCodeDiscountChannelsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(discountChannel1, discountChannel2, discountChannel3),
			(List<DiscountChannel>)page3.getItems());
	}

	protected DiscountChannel
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_addDiscountChannel(
				String externalReferenceCode, DiscountChannel discountChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountChannelsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountByExternalReferenceCodeDiscountChannel()
		throws Exception {

		DiscountChannel randomDiscountChannel = randomDiscountChannel();

		DiscountChannel postDiscountChannel =
			testPostDiscountByExternalReferenceCodeDiscountChannel_addDiscountChannel(
				randomDiscountChannel);

		assertEquals(randomDiscountChannel, postDiscountChannel);
		assertValid(postDiscountChannel);
	}

	protected DiscountChannel
			testPostDiscountByExternalReferenceCodeDiscountChannel_addDiscountChannel(
				DiscountChannel discountChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountIdDiscountChannelsPage() throws Exception {
		Page<DiscountChannel> page =
			discountChannelResource.getDiscountIdDiscountChannelsPage(
				testGetDiscountIdDiscountChannelsPage_getId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetDiscountIdDiscountChannelsPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountChannelsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			DiscountChannel irrelevantDiscountChannel =
				testGetDiscountIdDiscountChannelsPage_addDiscountChannel(
					irrelevantId, randomIrrelevantDiscountChannel());

			page = discountChannelResource.getDiscountIdDiscountChannelsPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountChannel),
				(List<DiscountChannel>)page.getItems());
			assertValid(page);
		}

		DiscountChannel discountChannel1 =
			testGetDiscountIdDiscountChannelsPage_addDiscountChannel(
				id, randomDiscountChannel());

		DiscountChannel discountChannel2 =
			testGetDiscountIdDiscountChannelsPage_addDiscountChannel(
				id, randomDiscountChannel());

		page = discountChannelResource.getDiscountIdDiscountChannelsPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountChannel1, discountChannel2),
			(List<DiscountChannel>)page.getItems());
		assertValid(page);

		discountChannelResource.deleteDiscountChannel(null);

		discountChannelResource.deleteDiscountChannel(null);
	}

	@Test
	public void testGetDiscountIdDiscountChannelsPageWithPagination()
		throws Exception {

		Long id = testGetDiscountIdDiscountChannelsPage_getId();

		DiscountChannel discountChannel1 =
			testGetDiscountIdDiscountChannelsPage_addDiscountChannel(
				id, randomDiscountChannel());

		DiscountChannel discountChannel2 =
			testGetDiscountIdDiscountChannelsPage_addDiscountChannel(
				id, randomDiscountChannel());

		DiscountChannel discountChannel3 =
			testGetDiscountIdDiscountChannelsPage_addDiscountChannel(
				id, randomDiscountChannel());

		Page<DiscountChannel> page1 =
			discountChannelResource.getDiscountIdDiscountChannelsPage(
				id, Pagination.of(1, 2));

		List<DiscountChannel> discountChannels1 =
			(List<DiscountChannel>)page1.getItems();

		Assert.assertEquals(
			discountChannels1.toString(), 2, discountChannels1.size());

		Page<DiscountChannel> page2 =
			discountChannelResource.getDiscountIdDiscountChannelsPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountChannel> discountChannels2 =
			(List<DiscountChannel>)page2.getItems();

		Assert.assertEquals(
			discountChannels2.toString(), 1, discountChannels2.size());

		Page<DiscountChannel> page3 =
			discountChannelResource.getDiscountIdDiscountChannelsPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(discountChannel1, discountChannel2, discountChannel3),
			(List<DiscountChannel>)page3.getItems());
	}

	protected DiscountChannel
			testGetDiscountIdDiscountChannelsPage_addDiscountChannel(
				Long id, DiscountChannel discountChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountChannelsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountChannelsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountIdDiscountChannel() throws Exception {
		DiscountChannel randomDiscountChannel = randomDiscountChannel();

		DiscountChannel postDiscountChannel =
			testPostDiscountIdDiscountChannel_addDiscountChannel(
				randomDiscountChannel);

		assertEquals(randomDiscountChannel, postDiscountChannel);
		assertValid(postDiscountChannel);
	}

	protected DiscountChannel
			testPostDiscountIdDiscountChannel_addDiscountChannel(
				DiscountChannel discountChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscountChannel testGraphQLDiscountChannel_addDiscountChannel()
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

	protected void assertEquals(
		DiscountChannel discountChannel1, DiscountChannel discountChannel2) {

		Assert.assertTrue(
			discountChannel1 + " does not equal " + discountChannel2,
			equals(discountChannel1, discountChannel2));
	}

	protected void assertEquals(
		List<DiscountChannel> discountChannels1,
		List<DiscountChannel> discountChannels2) {

		Assert.assertEquals(discountChannels1.size(), discountChannels2.size());

		for (int i = 0; i < discountChannels1.size(); i++) {
			DiscountChannel discountChannel1 = discountChannels1.get(i);
			DiscountChannel discountChannel2 = discountChannels2.get(i);

			assertEquals(discountChannel1, discountChannel2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscountChannel> discountChannels1,
		List<DiscountChannel> discountChannels2) {

		Assert.assertEquals(discountChannels1.size(), discountChannels2.size());

		for (DiscountChannel discountChannel1 : discountChannels1) {
			boolean contains = false;

			for (DiscountChannel discountChannel2 : discountChannels2) {
				if (equals(discountChannel1, discountChannel2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discountChannels2 + " does not contain " + discountChannel1,
				contains);
		}
	}

	protected void assertValid(DiscountChannel discountChannel) {
		boolean valid = true;

		if (discountChannel.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"channelExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountChannel.getChannelExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (discountChannel.getChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelName", additionalAssertFieldName)) {
				if (discountChannel.getChannelName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountChannel.getDiscountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountChannel.getDiscountId() == null) {
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

	protected void assertValid(Page<DiscountChannel> page) {
		boolean valid = false;

		java.util.Collection<DiscountChannel> discountChannels =
			page.getItems();

		int size = discountChannels.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						DiscountChannel.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		DiscountChannel discountChannel1, DiscountChannel discountChannel2) {

		if (discountChannel1 == discountChannel2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"channelExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountChannel1.getChannelExternalReferenceCode(),
						discountChannel2.getChannelExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountChannel1.getChannelId(),
						discountChannel2.getChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountChannel1.getChannelName(),
						discountChannel2.getChannelName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountChannel1.getDiscountExternalReferenceCode(),
						discountChannel2.getDiscountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountChannel1.getDiscountId(),
						discountChannel2.getDiscountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountChannel1.getId(), discountChannel2.getId())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_discountChannelResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountChannelResource;

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
		EntityField entityField, String operator,
		DiscountChannel discountChannel) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("channelExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountChannel.getChannelExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("channelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("channelName")) {
			sb.append("'");
			sb.append(String.valueOf(discountChannel.getChannelName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountChannel.getDiscountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected DiscountChannel randomDiscountChannel() throws Exception {
		return new DiscountChannel() {
			{
				channelExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				channelId = RandomTestUtil.randomLong();
				channelName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected DiscountChannel randomIrrelevantDiscountChannel()
		throws Exception {

		DiscountChannel randomIrrelevantDiscountChannel =
			randomDiscountChannel();

		return randomIrrelevantDiscountChannel;
	}

	protected DiscountChannel randomPatchDiscountChannel() throws Exception {
		return randomDiscountChannel();
	}

	protected DiscountChannelResource discountChannelResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

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

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDiscountChannelResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.pricing.resource.v2_0.
		DiscountChannelResource _discountChannelResource;

}