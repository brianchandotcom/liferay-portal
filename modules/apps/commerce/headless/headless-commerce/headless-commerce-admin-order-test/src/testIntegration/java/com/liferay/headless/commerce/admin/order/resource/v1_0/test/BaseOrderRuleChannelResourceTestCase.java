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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleChannel;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderRuleChannelResource;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderRuleChannelSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public abstract class BaseOrderRuleChannelResourceTestCase {

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

		_orderRuleChannelResource.setContextCompany(testCompany);

		OrderRuleChannelResource.Builder builder =
			OrderRuleChannelResource.builder();

		orderRuleChannelResource = builder.authentication(
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

		OrderRuleChannel orderRuleChannel1 = randomOrderRuleChannel();

		String json = objectMapper.writeValueAsString(orderRuleChannel1);

		OrderRuleChannel orderRuleChannel2 = OrderRuleChannelSerDes.toDTO(json);

		Assert.assertTrue(equals(orderRuleChannel1, orderRuleChannel2));
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

		OrderRuleChannel orderRuleChannel = randomOrderRuleChannel();

		String json1 = objectMapper.writeValueAsString(orderRuleChannel);
		String json2 = OrderRuleChannelSerDes.toJSON(orderRuleChannel);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		OrderRuleChannel orderRuleChannel = randomOrderRuleChannel();

		orderRuleChannel.setChannelExternalReferenceCode(regex);
		orderRuleChannel.setOrderRuleExternalReferenceCode(regex);

		String json = OrderRuleChannelSerDes.toJSON(orderRuleChannel);

		Assert.assertFalse(json.contains(regex));

		orderRuleChannel = OrderRuleChannelSerDes.toDTO(json);

		Assert.assertEquals(
			regex, orderRuleChannel.getChannelExternalReferenceCode());
		Assert.assertEquals(
			regex, orderRuleChannel.getOrderRuleExternalReferenceCode());
	}

	@Test
	public void testDeleteOrderRuleChannel() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteOrderRuleChannel() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage()
		throws Exception {

		String externalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_getIrrelevantExternalReferenceCode();

		Page<OrderRuleChannel> page =
			orderRuleChannelResource.
				getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			OrderRuleChannel irrelevantOrderRuleChannel =
				testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
					irrelevantExternalReferenceCode,
					randomIrrelevantOrderRuleChannel());

			page =
				orderRuleChannelResource.
					getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderRuleChannel),
				(List<OrderRuleChannel>)page.getItems());
			assertValid(page);
		}

		OrderRuleChannel orderRuleChannel1 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
				externalReferenceCode, randomOrderRuleChannel());

		OrderRuleChannel orderRuleChannel2 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
				externalReferenceCode, randomOrderRuleChannel());

		page =
			orderRuleChannelResource.
				getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderRuleChannel1, orderRuleChannel2),
			(List<OrderRuleChannel>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_getExternalReferenceCode();

		OrderRuleChannel orderRuleChannel1 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
				externalReferenceCode, randomOrderRuleChannel());

		OrderRuleChannel orderRuleChannel2 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
				externalReferenceCode, randomOrderRuleChannel());

		OrderRuleChannel orderRuleChannel3 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
				externalReferenceCode, randomOrderRuleChannel());

		Page<OrderRuleChannel> page1 =
			orderRuleChannelResource.
				getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<OrderRuleChannel> orderRuleChannels1 =
			(List<OrderRuleChannel>)page1.getItems();

		Assert.assertEquals(
			orderRuleChannels1.toString(), 2, orderRuleChannels1.size());

		Page<OrderRuleChannel> page2 =
			orderRuleChannelResource.
				getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderRuleChannel> orderRuleChannels2 =
			(List<OrderRuleChannel>)page2.getItems();

		Assert.assertEquals(
			orderRuleChannels2.toString(), 1, orderRuleChannels2.size());

		Page<OrderRuleChannel> page3 =
			orderRuleChannelResource.
				getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				orderRuleChannel1, orderRuleChannel2, orderRuleChannel3),
			(List<OrderRuleChannel>)page3.getItems());
	}

	protected OrderRuleChannel
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_addOrderRuleChannel(
				String externalReferenceCode, OrderRuleChannel orderRuleChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderRuleByExternalReferenceCodeOrderRuleChannel()
		throws Exception {

		OrderRuleChannel randomOrderRuleChannel = randomOrderRuleChannel();

		OrderRuleChannel postOrderRuleChannel =
			testPostOrderRuleByExternalReferenceCodeOrderRuleChannel_addOrderRuleChannel(
				randomOrderRuleChannel);

		assertEquals(randomOrderRuleChannel, postOrderRuleChannel);
		assertValid(postOrderRuleChannel);
	}

	protected OrderRuleChannel
			testPostOrderRuleByExternalReferenceCodeOrderRuleChannel_addOrderRuleChannel(
				OrderRuleChannel orderRuleChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderRuleIdOrderRuleChannelsPage() throws Exception {
		Long id = testGetOrderRuleIdOrderRuleChannelsPage_getId();
		Long irrelevantId =
			testGetOrderRuleIdOrderRuleChannelsPage_getIrrelevantId();

		Page<OrderRuleChannel> page =
			orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
				id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			OrderRuleChannel irrelevantOrderRuleChannel =
				testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
					irrelevantId, randomIrrelevantOrderRuleChannel());

			page = orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderRuleChannel),
				(List<OrderRuleChannel>)page.getItems());
			assertValid(page);
		}

		OrderRuleChannel orderRuleChannel1 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, randomOrderRuleChannel());

		OrderRuleChannel orderRuleChannel2 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, randomOrderRuleChannel());

		page = orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
			id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderRuleChannel1, orderRuleChannel2),
			(List<OrderRuleChannel>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrderRuleIdOrderRuleChannelsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleChannelsPage_getId();

		OrderRuleChannel orderRuleChannel1 = randomOrderRuleChannel();

		orderRuleChannel1 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, orderRuleChannel1);

		for (EntityField entityField : entityFields) {
			Page<OrderRuleChannel> page =
				orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
					id, null,
					getFilterString(entityField, "between", orderRuleChannel1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRuleChannel1),
				(List<OrderRuleChannel>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRuleIdOrderRuleChannelsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleChannelsPage_getId();

		OrderRuleChannel orderRuleChannel1 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, randomOrderRuleChannel());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRuleChannel orderRuleChannel2 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, randomOrderRuleChannel());

		for (EntityField entityField : entityFields) {
			Page<OrderRuleChannel> page =
				orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
					id, null,
					getFilterString(entityField, "eq", orderRuleChannel1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRuleChannel1),
				(List<OrderRuleChannel>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRuleIdOrderRuleChannelsPageWithPagination()
		throws Exception {

		Long id = testGetOrderRuleIdOrderRuleChannelsPage_getId();

		OrderRuleChannel orderRuleChannel1 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, randomOrderRuleChannel());

		OrderRuleChannel orderRuleChannel2 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, randomOrderRuleChannel());

		OrderRuleChannel orderRuleChannel3 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, randomOrderRuleChannel());

		Page<OrderRuleChannel> page1 =
			orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
				id, null, null, Pagination.of(1, 2), null);

		List<OrderRuleChannel> orderRuleChannels1 =
			(List<OrderRuleChannel>)page1.getItems();

		Assert.assertEquals(
			orderRuleChannels1.toString(), 2, orderRuleChannels1.size());

		Page<OrderRuleChannel> page2 =
			orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderRuleChannel> orderRuleChannels2 =
			(List<OrderRuleChannel>)page2.getItems();

		Assert.assertEquals(
			orderRuleChannels2.toString(), 1, orderRuleChannels2.size());

		Page<OrderRuleChannel> page3 =
			orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				orderRuleChannel1, orderRuleChannel2, orderRuleChannel3),
			(List<OrderRuleChannel>)page3.getItems());
	}

	@Test
	public void testGetOrderRuleIdOrderRuleChannelsPageWithSortDateTime()
		throws Exception {

		testGetOrderRuleIdOrderRuleChannelsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, orderRuleChannel1, orderRuleChannel2) -> {
				BeanUtils.setProperty(
					orderRuleChannel1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrderRuleIdOrderRuleChannelsPageWithSortInteger()
		throws Exception {

		testGetOrderRuleIdOrderRuleChannelsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, orderRuleChannel1, orderRuleChannel2) -> {
				BeanUtils.setProperty(
					orderRuleChannel1, entityField.getName(), 0);
				BeanUtils.setProperty(
					orderRuleChannel2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrderRuleIdOrderRuleChannelsPageWithSortString()
		throws Exception {

		testGetOrderRuleIdOrderRuleChannelsPageWithSort(
			EntityField.Type.STRING,
			(entityField, orderRuleChannel1, orderRuleChannel2) -> {
				Class<?> clazz = orderRuleChannel1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						orderRuleChannel1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						orderRuleChannel2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						orderRuleChannel1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						orderRuleChannel2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						orderRuleChannel1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						orderRuleChannel2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrderRuleIdOrderRuleChannelsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, OrderRuleChannel, OrderRuleChannel, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleChannelsPage_getId();

		OrderRuleChannel orderRuleChannel1 = randomOrderRuleChannel();
		OrderRuleChannel orderRuleChannel2 = randomOrderRuleChannel();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, orderRuleChannel1, orderRuleChannel2);
		}

		orderRuleChannel1 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, orderRuleChannel1);

		orderRuleChannel2 =
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				id, orderRuleChannel2);

		for (EntityField entityField : entityFields) {
			Page<OrderRuleChannel> ascPage =
				orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(orderRuleChannel1, orderRuleChannel2),
				(List<OrderRuleChannel>)ascPage.getItems());

			Page<OrderRuleChannel> descPage =
				orderRuleChannelResource.getOrderRuleIdOrderRuleChannelsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(orderRuleChannel2, orderRuleChannel1),
				(List<OrderRuleChannel>)descPage.getItems());
		}
	}

	protected OrderRuleChannel
			testGetOrderRuleIdOrderRuleChannelsPage_addOrderRuleChannel(
				Long id, OrderRuleChannel orderRuleChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderRuleIdOrderRuleChannelsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderRuleIdOrderRuleChannelsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderRuleIdOrderRuleChannel() throws Exception {
		OrderRuleChannel randomOrderRuleChannel = randomOrderRuleChannel();

		OrderRuleChannel postOrderRuleChannel =
			testPostOrderRuleIdOrderRuleChannel_addOrderRuleChannel(
				randomOrderRuleChannel);

		assertEquals(randomOrderRuleChannel, postOrderRuleChannel);
		assertValid(postOrderRuleChannel);
	}

	protected OrderRuleChannel
			testPostOrderRuleIdOrderRuleChannel_addOrderRuleChannel(
				OrderRuleChannel orderRuleChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(
		OrderRuleChannel orderRuleChannel,
		List<OrderRuleChannel> orderRuleChannels) {

		boolean contains = false;

		for (OrderRuleChannel item : orderRuleChannels) {
			if (equals(orderRuleChannel, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			orderRuleChannels + " does not contain " + orderRuleChannel,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		OrderRuleChannel orderRuleChannel1,
		OrderRuleChannel orderRuleChannel2) {

		Assert.assertTrue(
			orderRuleChannel1 + " does not equal " + orderRuleChannel2,
			equals(orderRuleChannel1, orderRuleChannel2));
	}

	protected void assertEquals(
		List<OrderRuleChannel> orderRuleChannels1,
		List<OrderRuleChannel> orderRuleChannels2) {

		Assert.assertEquals(
			orderRuleChannels1.size(), orderRuleChannels2.size());

		for (int i = 0; i < orderRuleChannels1.size(); i++) {
			OrderRuleChannel orderRuleChannel1 = orderRuleChannels1.get(i);
			OrderRuleChannel orderRuleChannel2 = orderRuleChannels2.get(i);

			assertEquals(orderRuleChannel1, orderRuleChannel2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<OrderRuleChannel> orderRuleChannels1,
		List<OrderRuleChannel> orderRuleChannels2) {

		Assert.assertEquals(
			orderRuleChannels1.size(), orderRuleChannels2.size());

		for (OrderRuleChannel orderRuleChannel1 : orderRuleChannels1) {
			boolean contains = false;

			for (OrderRuleChannel orderRuleChannel2 : orderRuleChannels2) {
				if (equals(orderRuleChannel1, orderRuleChannel2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				orderRuleChannels2 + " does not contain " + orderRuleChannel1,
				contains);
		}
	}

	protected void assertValid(OrderRuleChannel orderRuleChannel)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (orderRuleChannel.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channel", additionalAssertFieldName)) {
				if (orderRuleChannel.getChannel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"channelExternalReferenceCode",
					additionalAssertFieldName)) {

				if (orderRuleChannel.getChannelExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (orderRuleChannel.getChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleChannelId", additionalAssertFieldName)) {

				if (orderRuleChannel.getOrderRuleChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleExternalReferenceCode",
					additionalAssertFieldName)) {

				if (orderRuleChannel.getOrderRuleExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderRuleId", additionalAssertFieldName)) {
				if (orderRuleChannel.getOrderRuleId() == null) {
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

	protected void assertValid(Page<OrderRuleChannel> page) {
		boolean valid = false;

		java.util.Collection<OrderRuleChannel> orderRuleChannels =
			page.getItems();

		int size = orderRuleChannels.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.admin.order.dto.v1_0.
						OrderRuleChannel.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
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
					getDeclaredFields(clazz));

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
		OrderRuleChannel orderRuleChannel1,
		OrderRuleChannel orderRuleChannel2) {

		if (orderRuleChannel1 == orderRuleChannel2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)orderRuleChannel1.getActions(),
						(Map)orderRuleChannel2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channel", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleChannel1.getChannel(),
						orderRuleChannel2.getChannel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"channelExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleChannel1.getChannelExternalReferenceCode(),
						orderRuleChannel2.getChannelExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleChannel1.getChannelId(),
						orderRuleChannel2.getChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleChannelId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleChannel1.getOrderRuleChannelId(),
						orderRuleChannel2.getOrderRuleChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleChannel1.getOrderRuleExternalReferenceCode(),
						orderRuleChannel2.
							getOrderRuleExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderRuleId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleChannel1.getOrderRuleId(),
						orderRuleChannel2.getOrderRuleId())) {

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

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_orderRuleChannelResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_orderRuleChannelResource;

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
		OrderRuleChannel orderRuleChannel) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("channel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("channelExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					orderRuleChannel.getChannelExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("channelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleChannelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					orderRuleChannel.getOrderRuleExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("orderRuleId")) {
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

	protected OrderRuleChannel randomOrderRuleChannel() throws Exception {
		return new OrderRuleChannel() {
			{
				channelExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				channelId = RandomTestUtil.randomLong();
				orderRuleChannelId = RandomTestUtil.randomLong();
				orderRuleExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderRuleId = RandomTestUtil.randomLong();
			}
		};
	}

	protected OrderRuleChannel randomIrrelevantOrderRuleChannel()
		throws Exception {

		OrderRuleChannel randomIrrelevantOrderRuleChannel =
			randomOrderRuleChannel();

		return randomIrrelevantOrderRuleChannel;
	}

	protected OrderRuleChannel randomPatchOrderRuleChannel() throws Exception {
		return randomOrderRuleChannel();
	}

	protected OrderRuleChannelResource orderRuleChannelResource;
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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseOrderRuleChannelResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.order.resource.v1_0.
		OrderRuleChannelResource _orderRuleChannelResource;

}