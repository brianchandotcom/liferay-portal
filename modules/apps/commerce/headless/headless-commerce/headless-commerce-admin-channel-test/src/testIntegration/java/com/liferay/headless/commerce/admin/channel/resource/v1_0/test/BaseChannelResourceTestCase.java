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

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.channel.client.pagination.Page;
import com.liferay.headless.commerce.admin.channel.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.channel.client.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.channel.client.serdes.v1_0.ChannelSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public abstract class BaseChannelResourceTestCase {

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

		_channelResource.setContextCompany(testCompany);

		ChannelResource.Builder builder = ChannelResource.builder();

		channelResource = builder.authentication(
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

		Channel channel1 = randomChannel();

		String json = objectMapper.writeValueAsString(channel1);

		Channel channel2 = ChannelSerDes.toDTO(json);

		Assert.assertTrue(equals(channel1, channel2));
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

		Channel channel = randomChannel();

		String json1 = objectMapper.writeValueAsString(channel);
		String json2 = ChannelSerDes.toJSON(channel);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Channel channel = randomChannel();

		channel.setCurrencyCode(regex);
		channel.setExternalReferenceCode(regex);
		channel.setName(regex);
		channel.setType(regex);

		String json = ChannelSerDes.toJSON(channel);

		Assert.assertFalse(json.contains(regex));

		channel = ChannelSerDes.toDTO(json);

		Assert.assertEquals(regex, channel.getCurrencyCode());
		Assert.assertEquals(regex, channel.getExternalReferenceCode());
		Assert.assertEquals(regex, channel.getName());
		Assert.assertEquals(regex, channel.getType());
	}

	@Test
	public void testGetChannelsPage() throws Exception {
		Page<Channel> page = channelResource.getChannelsPage(
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Channel channel1 = testGetChannelsPage_addChannel(randomChannel());

		Channel channel2 = testGetChannelsPage_addChannel(randomChannel());

		page = channelResource.getChannelsPage(
			null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(channel1, channel2), (List<Channel>)page.getItems());
		assertValid(page);

		channelResource.deleteChannel(channel1.getId());

		channelResource.deleteChannel(channel2.getId());
	}

	@Test
	public void testGetChannelsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Channel channel1 = randomChannel();

		channel1 = testGetChannelsPage_addChannel(channel1);

		for (EntityField entityField : entityFields) {
			Page<Channel> page = channelResource.getChannelsPage(
				null, getFilterString(entityField, "between", channel1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(channel1),
				(List<Channel>)page.getItems());
		}
	}

	@Test
	public void testGetChannelsPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Channel channel1 = testGetChannelsPage_addChannel(randomChannel());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Channel channel2 = testGetChannelsPage_addChannel(randomChannel());

		for (EntityField entityField : entityFields) {
			Page<Channel> page = channelResource.getChannelsPage(
				null, getFilterString(entityField, "eq", channel1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(channel1),
				(List<Channel>)page.getItems());
		}
	}

	@Test
	public void testGetChannelsPageWithPagination() throws Exception {
		Channel channel1 = testGetChannelsPage_addChannel(randomChannel());

		Channel channel2 = testGetChannelsPage_addChannel(randomChannel());

		Channel channel3 = testGetChannelsPage_addChannel(randomChannel());

		Page<Channel> page1 = channelResource.getChannelsPage(
			null, null, Pagination.of(1, 2), null);

		List<Channel> channels1 = (List<Channel>)page1.getItems();

		Assert.assertEquals(channels1.toString(), 2, channels1.size());

		Page<Channel> page2 = channelResource.getChannelsPage(
			null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Channel> channels2 = (List<Channel>)page2.getItems();

		Assert.assertEquals(channels2.toString(), 1, channels2.size());

		Page<Channel> page3 = channelResource.getChannelsPage(
			null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(channel1, channel2, channel3),
			(List<Channel>)page3.getItems());
	}

	@Test
	public void testGetChannelsPageWithSortDateTime() throws Exception {
		testGetChannelsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, channel1, channel2) -> {
				BeanUtils.setProperty(
					channel1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetChannelsPageWithSortInteger() throws Exception {
		testGetChannelsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, channel1, channel2) -> {
				BeanUtils.setProperty(channel1, entityField.getName(), 0);
				BeanUtils.setProperty(channel2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetChannelsPageWithSortString() throws Exception {
		testGetChannelsPageWithSort(
			EntityField.Type.STRING,
			(entityField, channel1, channel2) -> {
				Class<?> clazz = channel1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						channel1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						channel2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						channel1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						channel2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						channel1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						channel2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetChannelsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Channel, Channel, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Channel channel1 = randomChannel();
		Channel channel2 = randomChannel();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, channel1, channel2);
		}

		channel1 = testGetChannelsPage_addChannel(channel1);

		channel2 = testGetChannelsPage_addChannel(channel2);

		for (EntityField entityField : entityFields) {
			Page<Channel> ascPage = channelResource.getChannelsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(channel1, channel2),
				(List<Channel>)ascPage.getItems());

			Page<Channel> descPage = channelResource.getChannelsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(channel2, channel1),
				(List<Channel>)descPage.getItems());
		}
	}

	protected Channel testGetChannelsPage_addChannel(Channel channel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetChannelsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"channels",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject channelsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/channels");

		Assert.assertEquals(0, channelsJSONObject.get("totalCount"));

		Channel channel1 = testGraphQLChannel_addChannel();
		Channel channel2 = testGraphQLChannel_addChannel();

		channelsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/channels");

		Assert.assertEquals(2, channelsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(channel1, channel2),
			Arrays.asList(
				ChannelSerDes.toDTOs(channelsJSONObject.getString("items"))));
	}

	@Test
	public void testPostChannel() throws Exception {
		Channel randomChannel = randomChannel();

		Channel postChannel = testPostChannel_addChannel(randomChannel);

		assertEquals(randomChannel, postChannel);
		assertValid(postChannel);
	}

	protected Channel testPostChannel_addChannel(Channel channel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteChannel() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Channel channel = testDeleteChannel_addChannel();

		assertHttpResponseStatusCode(
			204, channelResource.deleteChannelHttpResponse(channel.getId()));

		assertHttpResponseStatusCode(
			404, channelResource.getChannelHttpResponse(channel.getId()));

		assertHttpResponseStatusCode(
			404, channelResource.getChannelHttpResponse(0L));
	}

	protected Channel testDeleteChannel_addChannel() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteChannel() throws Exception {
		Channel channel = testGraphQLChannel_addChannel();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteChannel",
						new HashMap<String, Object>() {
							{
								put("channelId", channel.getId());
							}
						})),
				"JSONObject/data", "Object/deleteChannel"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"channel",
						new HashMap<String, Object>() {
							{
								put("channelId", channel.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetChannel() throws Exception {
		Channel postChannel = testGetChannel_addChannel();

		Channel getChannel = channelResource.getChannel(postChannel.getId());

		assertEquals(postChannel, getChannel);
		assertValid(getChannel);
	}

	protected Channel testGetChannel_addChannel() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetChannel() throws Exception {
		Channel channel = testGraphQLChannel_addChannel();

		Assert.assertTrue(
			equals(
				channel,
				ChannelSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"channel",
								new HashMap<String, Object>() {
									{
										put("channelId", channel.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/channel"))));
	}

	@Test
	public void testGraphQLGetChannelNotFound() throws Exception {
		Long irrelevantChannelId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"channel",
						new HashMap<String, Object>() {
							{
								put("channelId", irrelevantChannelId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchChannel() throws Exception {
		Channel postChannel = testPatchChannel_addChannel();

		Channel randomPatchChannel = randomPatchChannel();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Channel patchChannel = channelResource.patchChannel(
			postChannel.getId(), randomPatchChannel);

		Channel expectedPatchChannel = postChannel.clone();

		_beanUtilsBean.copyProperties(expectedPatchChannel, randomPatchChannel);

		Channel getChannel = channelResource.getChannel(patchChannel.getId());

		assertEquals(expectedPatchChannel, getChannel);
		assertValid(getChannel);
	}

	protected Channel testPatchChannel_addChannel() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutChannel() throws Exception {
		Channel postChannel = testPutChannel_addChannel();

		Channel randomChannel = randomChannel();

		Channel putChannel = channelResource.putChannel(
			postChannel.getId(), randomChannel);

		assertEquals(randomChannel, putChannel);
		assertValid(putChannel);

		Channel getChannel = channelResource.getChannel(putChannel.getId());

		assertEquals(randomChannel, getChannel);
		assertValid(getChannel);
	}

	protected Channel testPutChannel_addChannel() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Channel testGraphQLChannel_addChannel() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Channel channel1, Channel channel2) {
		Assert.assertTrue(
			channel1 + " does not equal " + channel2,
			equals(channel1, channel2));
	}

	protected void assertEquals(
		List<Channel> channels1, List<Channel> channels2) {

		Assert.assertEquals(channels1.size(), channels2.size());

		for (int i = 0; i < channels1.size(); i++) {
			Channel channel1 = channels1.get(i);
			Channel channel2 = channels2.get(i);

			assertEquals(channel1, channel2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Channel> channels1, List<Channel> channels2) {

		Assert.assertEquals(channels1.size(), channels2.size());

		for (Channel channel1 : channels1) {
			boolean contains = false;

			for (Channel channel2 : channels2) {
				if (equals(channel1, channel2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				channels2 + " does not contain " + channel1, contains);
		}
	}

	protected void assertValid(Channel channel) throws Exception {
		boolean valid = true;

		if (channel.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (channel.getCurrencyCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (channel.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (channel.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("siteGroupId", additionalAssertFieldName)) {
				if (channel.getSiteGroupId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (channel.getType() == null) {
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

	protected void assertValid(Page<Channel> page) {
		boolean valid = false;

		java.util.Collection<Channel> channels = page.getItems();

		int size = channels.size();

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
					com.liferay.headless.commerce.admin.channel.dto.v1_0.
						Channel.class)) {

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

	protected boolean equals(Channel channel1, Channel channel2) {
		if (channel1 == channel2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						channel1.getCurrencyCode(),
						channel2.getCurrencyCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						channel1.getExternalReferenceCode(),
						channel2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(channel1.getId(), channel2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						channel1.getName(), channel2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteGroupId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						channel1.getSiteGroupId(), channel2.getSiteGroupId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						channel1.getType(), channel2.getType())) {

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

			return true;
		}

		return false;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_channelResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_channelResource;

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
		EntityField entityField, String operator, Channel channel) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("currencyCode")) {
			sb.append("'");
			sb.append(String.valueOf(channel.getCurrencyCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(channel.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(channel.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteGroupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(channel.getType()));
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

	protected Channel randomChannel() throws Exception {
		return new Channel() {
			{
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				siteGroupId = RandomTestUtil.randomLong();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Channel randomIrrelevantChannel() throws Exception {
		Channel randomIrrelevantChannel = randomChannel();

		return randomIrrelevantChannel;
	}

	protected Channel randomPatchChannel() throws Exception {
		return randomChannel();
	}

	protected ChannelResource channelResource;
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
		BaseChannelResourceTestCase.class);

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
	private
		com.liferay.headless.commerce.admin.channel.resource.v1_0.
			ChannelResource _channelResource;

}