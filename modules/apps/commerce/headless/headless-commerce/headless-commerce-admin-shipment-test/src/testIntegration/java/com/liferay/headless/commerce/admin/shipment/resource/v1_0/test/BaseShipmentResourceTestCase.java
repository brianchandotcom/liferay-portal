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

package com.liferay.headless.commerce.admin.shipment.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.shipment.client.dto.v1_0.Shipment;
import com.liferay.headless.commerce.admin.shipment.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.shipment.client.pagination.Page;
import com.liferay.headless.commerce.admin.shipment.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.shipment.client.resource.v1_0.ShipmentResource;
import com.liferay.headless.commerce.admin.shipment.client.serdes.v1_0.ShipmentSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.util.GetterUtil;
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
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public abstract class BaseShipmentResourceTestCase {

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

		_shipmentResource.setContextCompany(testCompany);

		ShipmentResource.Builder builder = ShipmentResource.builder();

		shipmentResource = builder.authentication(
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

		Shipment shipment1 = randomShipment();

		String json = objectMapper.writeValueAsString(shipment1);

		Shipment shipment2 = ShipmentSerDes.toDTO(json);

		Assert.assertTrue(equals(shipment1, shipment2));
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

		Shipment shipment = randomShipment();

		String json1 = objectMapper.writeValueAsString(shipment);
		String json2 = ShipmentSerDes.toJSON(shipment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Shipment shipment = randomShipment();

		shipment.setCarrier(regex);
		shipment.setExternalReferenceCode(regex);
		shipment.setShippingOptionName(regex);
		shipment.setTrackingNumber(regex);
		shipment.setUserName(regex);

		String json = ShipmentSerDes.toJSON(shipment);

		Assert.assertFalse(json.contains(regex));

		shipment = ShipmentSerDes.toDTO(json);

		Assert.assertEquals(regex, shipment.getCarrier());
		Assert.assertEquals(regex, shipment.getExternalReferenceCode());
		Assert.assertEquals(regex, shipment.getShippingOptionName());
		Assert.assertEquals(regex, shipment.getTrackingNumber());
		Assert.assertEquals(regex, shipment.getUserName());
	}

	@Test
	public void testGetShipmentsPage() throws Exception {
		Page<Shipment> page = shipmentResource.getShipmentsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Shipment shipment1 = testGetShipmentsPage_addShipment(randomShipment());

		Shipment shipment2 = testGetShipmentsPage_addShipment(randomShipment());

		page = shipmentResource.getShipmentsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(shipment1, (List<Shipment>)page.getItems());
		assertContains(shipment2, (List<Shipment>)page.getItems());
		assertValid(page);

		shipmentResource.deleteShipment(shipment1.getId());

		shipmentResource.deleteShipment(shipment2.getId());
	}

	@Test
	public void testGetShipmentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Shipment shipment1 = randomShipment();

		shipment1 = testGetShipmentsPage_addShipment(shipment1);

		for (EntityField entityField : entityFields) {
			Page<Shipment> page = shipmentResource.getShipmentsPage(
				null, getFilterString(entityField, "between", shipment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(shipment1),
				(List<Shipment>)page.getItems());
		}
	}

	@Test
	public void testGetShipmentsPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Shipment shipment1 = testGetShipmentsPage_addShipment(randomShipment());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Shipment shipment2 = testGetShipmentsPage_addShipment(randomShipment());

		for (EntityField entityField : entityFields) {
			Page<Shipment> page = shipmentResource.getShipmentsPage(
				null, getFilterString(entityField, "eq", shipment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(shipment1),
				(List<Shipment>)page.getItems());
		}
	}

	@Test
	public void testGetShipmentsPageWithPagination() throws Exception {
		Page<Shipment> totalPage = shipmentResource.getShipmentsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Shipment shipment1 = testGetShipmentsPage_addShipment(randomShipment());

		Shipment shipment2 = testGetShipmentsPage_addShipment(randomShipment());

		Shipment shipment3 = testGetShipmentsPage_addShipment(randomShipment());

		Page<Shipment> page1 = shipmentResource.getShipmentsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Shipment> shipments1 = (List<Shipment>)page1.getItems();

		Assert.assertEquals(
			shipments1.toString(), totalCount + 2, shipments1.size());

		Page<Shipment> page2 = shipmentResource.getShipmentsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Shipment> shipments2 = (List<Shipment>)page2.getItems();

		Assert.assertEquals(shipments2.toString(), 1, shipments2.size());

		Page<Shipment> page3 = shipmentResource.getShipmentsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(shipment1, (List<Shipment>)page3.getItems());
		assertContains(shipment2, (List<Shipment>)page3.getItems());
		assertContains(shipment3, (List<Shipment>)page3.getItems());
	}

	@Test
	public void testGetShipmentsPageWithSortDateTime() throws Exception {
		testGetShipmentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, shipment1, shipment2) -> {
				BeanUtils.setProperty(
					shipment1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetShipmentsPageWithSortInteger() throws Exception {
		testGetShipmentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, shipment1, shipment2) -> {
				BeanUtils.setProperty(shipment1, entityField.getName(), 0);
				BeanUtils.setProperty(shipment2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetShipmentsPageWithSortString() throws Exception {
		testGetShipmentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, shipment1, shipment2) -> {
				Class<?> clazz = shipment1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						shipment1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						shipment2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						shipment1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						shipment2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						shipment1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						shipment2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetShipmentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Shipment, Shipment, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Shipment shipment1 = randomShipment();
		Shipment shipment2 = randomShipment();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, shipment1, shipment2);
		}

		shipment1 = testGetShipmentsPage_addShipment(shipment1);

		shipment2 = testGetShipmentsPage_addShipment(shipment2);

		for (EntityField entityField : entityFields) {
			Page<Shipment> ascPage = shipmentResource.getShipmentsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(shipment1, shipment2),
				(List<Shipment>)ascPage.getItems());

			Page<Shipment> descPage = shipmentResource.getShipmentsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(shipment2, shipment1),
				(List<Shipment>)descPage.getItems());
		}
	}

	protected Shipment testGetShipmentsPage_addShipment(Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetShipmentsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"shipments",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject shipmentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/shipments");

		long totalCount = shipmentsJSONObject.getLong("totalCount");

		Shipment shipment1 = testGraphQLShipment_addShipment();
		Shipment shipment2 = testGraphQLShipment_addShipment();

		shipmentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/shipments");

		Assert.assertEquals(
			totalCount + 2, shipmentsJSONObject.getLong("totalCount"));

		assertContains(
			shipment1,
			Arrays.asList(
				ShipmentSerDes.toDTOs(shipmentsJSONObject.getString("items"))));
		assertContains(
			shipment2,
			Arrays.asList(
				ShipmentSerDes.toDTOs(shipmentsJSONObject.getString("items"))));
	}

	@Test
	public void testPostShipment() throws Exception {
		Shipment randomShipment = randomShipment();

		Shipment postShipment = testPostShipment_addShipment(randomShipment);

		assertEquals(randomShipment, postShipment);
		assertValid(postShipment);
	}

	protected Shipment testPostShipment_addShipment(Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteShipmentByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Shipment shipment =
			testDeleteShipmentByExternalReferenceCode_addShipment();

		assertHttpResponseStatusCode(
			204,
			shipmentResource.deleteShipmentByExternalReferenceCodeHttpResponse(
				shipment.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			shipmentResource.getShipmentByExternalReferenceCodeHttpResponse(
				shipment.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			shipmentResource.getShipmentByExternalReferenceCodeHttpResponse(
				shipment.getExternalReferenceCode()));
	}

	protected Shipment testDeleteShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetShipmentByExternalReferenceCode() throws Exception {
		Shipment postShipment =
			testGetShipmentByExternalReferenceCode_addShipment();

		Shipment getShipment =
			shipmentResource.getShipmentByExternalReferenceCode(
				postShipment.getExternalReferenceCode());

		assertEquals(postShipment, getShipment);
		assertValid(getShipment);
	}

	protected Shipment testGetShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetShipmentByExternalReferenceCode()
		throws Exception {

		Shipment shipment = testGraphQLShipment_addShipment();

		Assert.assertTrue(
			equals(
				shipment,
				ShipmentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"shipmentByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												shipment.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/shipmentByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetShipmentByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"shipmentByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchShipmentByExternalReferenceCode() throws Exception {
		Shipment postShipment =
			testPatchShipmentByExternalReferenceCode_addShipment();

		Shipment randomPatchShipment = randomPatchShipment();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Shipment patchShipment =
			shipmentResource.patchShipmentByExternalReferenceCode(
				postShipment.getExternalReferenceCode(), randomPatchShipment);

		Shipment expectedPatchShipment = postShipment.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchShipment, randomPatchShipment);

		Shipment getShipment =
			shipmentResource.getShipmentByExternalReferenceCode(
				patchShipment.getExternalReferenceCode());

		assertEquals(expectedPatchShipment, getShipment);
		assertValid(getShipment);
	}

	protected Shipment testPatchShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutShipmentByExternalReferenceCode() throws Exception {
		Shipment postShipment =
			testPutShipmentByExternalReferenceCode_addShipment();

		Shipment randomShipment = randomShipment();

		Shipment putShipment =
			shipmentResource.putShipmentByExternalReferenceCode(
				postShipment.getExternalReferenceCode(), randomShipment);

		assertEquals(randomShipment, putShipment);
		assertValid(putShipment);

		Shipment getShipment =
			shipmentResource.getShipmentByExternalReferenceCode(
				putShipment.getExternalReferenceCode());

		assertEquals(randomShipment, getShipment);
		assertValid(getShipment);

		Shipment newShipment =
			testPutShipmentByExternalReferenceCode_createShipment();

		putShipment = shipmentResource.putShipmentByExternalReferenceCode(
			newShipment.getExternalReferenceCode(), newShipment);

		assertEquals(newShipment, putShipment);
		assertValid(putShipment);

		getShipment = shipmentResource.getShipmentByExternalReferenceCode(
			putShipment.getExternalReferenceCode());

		assertEquals(newShipment, getShipment);

		Assert.assertEquals(
			newShipment.getExternalReferenceCode(),
			putShipment.getExternalReferenceCode());
	}

	protected Shipment testPutShipmentByExternalReferenceCode_createShipment()
		throws Exception {

		return randomShipment();
	}

	protected Shipment testPutShipmentByExternalReferenceCode_addShipment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostShipmentByExternalReferenceCodeStatusDelivered()
		throws Exception {

		Shipment randomShipment = randomShipment();

		Shipment postShipment =
			testPostShipmentByExternalReferenceCodeStatusDelivered_addShipment(
				randomShipment);

		assertEquals(randomShipment, postShipment);
		assertValid(postShipment);
	}

	protected Shipment
			testPostShipmentByExternalReferenceCodeStatusDelivered_addShipment(
				Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostShipmentByExternalReferenceCodeStatusFinishProcessing()
		throws Exception {

		Shipment randomShipment = randomShipment();

		Shipment postShipment =
			testPostShipmentByExternalReferenceCodeStatusFinishProcessing_addShipment(
				randomShipment);

		assertEquals(randomShipment, postShipment);
		assertValid(postShipment);
	}

	protected Shipment
			testPostShipmentByExternalReferenceCodeStatusFinishProcessing_addShipment(
				Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostShipmentByExternalReferenceCodeStatusShipped()
		throws Exception {

		Shipment randomShipment = randomShipment();

		Shipment postShipment =
			testPostShipmentByExternalReferenceCodeStatusShipped_addShipment(
				randomShipment);

		assertEquals(randomShipment, postShipment);
		assertValid(postShipment);
	}

	protected Shipment
			testPostShipmentByExternalReferenceCodeStatusShipped_addShipment(
				Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteShipment() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Shipment shipment = testDeleteShipment_addShipment();

		assertHttpResponseStatusCode(
			204, shipmentResource.deleteShipmentHttpResponse(shipment.getId()));

		assertHttpResponseStatusCode(
			404, shipmentResource.getShipmentHttpResponse(shipment.getId()));

		assertHttpResponseStatusCode(
			404, shipmentResource.getShipmentHttpResponse(0L));
	}

	protected Shipment testDeleteShipment_addShipment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteShipment() throws Exception {
		Shipment shipment = testGraphQLShipment_addShipment();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteShipment",
						new HashMap<String, Object>() {
							{
								put("shipmentId", shipment.getId());
							}
						})),
				"JSONObject/data", "Object/deleteShipment"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"shipment",
					new HashMap<String, Object>() {
						{
							put("shipmentId", shipment.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetShipment() throws Exception {
		Shipment postShipment = testGetShipment_addShipment();

		Shipment getShipment = shipmentResource.getShipment(
			postShipment.getId());

		assertEquals(postShipment, getShipment);
		assertValid(getShipment);
	}

	protected Shipment testGetShipment_addShipment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetShipment() throws Exception {
		Shipment shipment = testGraphQLShipment_addShipment();

		Assert.assertTrue(
			equals(
				shipment,
				ShipmentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"shipment",
								new HashMap<String, Object>() {
									{
										put("shipmentId", shipment.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/shipment"))));
	}

	@Test
	public void testGraphQLGetShipmentNotFound() throws Exception {
		Long irrelevantShipmentId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"shipment",
						new HashMap<String, Object>() {
							{
								put("shipmentId", irrelevantShipmentId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchShipment() throws Exception {
		Shipment postShipment = testPatchShipment_addShipment();

		Shipment randomPatchShipment = randomPatchShipment();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Shipment patchShipment = shipmentResource.patchShipment(
			postShipment.getId(), randomPatchShipment);

		Shipment expectedPatchShipment = postShipment.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchShipment, randomPatchShipment);

		Shipment getShipment = shipmentResource.getShipment(
			patchShipment.getId());

		assertEquals(expectedPatchShipment, getShipment);
		assertValid(getShipment);
	}

	protected Shipment testPatchShipment_addShipment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostShipmentStatusDelivered() throws Exception {
		Shipment randomShipment = randomShipment();

		Shipment postShipment = testPostShipmentStatusDelivered_addShipment(
			randomShipment);

		assertEquals(randomShipment, postShipment);
		assertValid(postShipment);
	}

	protected Shipment testPostShipmentStatusDelivered_addShipment(
			Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostShipmentStatusFinishProcessing() throws Exception {
		Shipment randomShipment = randomShipment();

		Shipment postShipment =
			testPostShipmentStatusFinishProcessing_addShipment(randomShipment);

		assertEquals(randomShipment, postShipment);
		assertValid(postShipment);
	}

	protected Shipment testPostShipmentStatusFinishProcessing_addShipment(
			Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostShipmentStatusShipped() throws Exception {
		Shipment randomShipment = randomShipment();

		Shipment postShipment = testPostShipmentStatusShipped_addShipment(
			randomShipment);

		assertEquals(randomShipment, postShipment);
		assertValid(postShipment);
	}

	protected Shipment testPostShipmentStatusShipped_addShipment(
			Shipment shipment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Shipment testGraphQLShipment_addShipment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Shipment shipment, List<Shipment> shipments) {
		boolean contains = false;

		for (Shipment item : shipments) {
			if (equals(shipment, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			shipments + " does not contain " + shipment, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Shipment shipment1, Shipment shipment2) {
		Assert.assertTrue(
			shipment1 + " does not equal " + shipment2,
			equals(shipment1, shipment2));
	}

	protected void assertEquals(
		List<Shipment> shipments1, List<Shipment> shipments2) {

		Assert.assertEquals(shipments1.size(), shipments2.size());

		for (int i = 0; i < shipments1.size(); i++) {
			Shipment shipment1 = shipments1.get(i);
			Shipment shipment2 = shipments2.get(i);

			assertEquals(shipment1, shipment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Shipment> shipments1, List<Shipment> shipments2) {

		Assert.assertEquals(shipments1.size(), shipments2.size());

		for (Shipment shipment1 : shipments1) {
			boolean contains = false;

			for (Shipment shipment2 : shipments2) {
				if (equals(shipment1, shipment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				shipments2 + " does not contain " + shipment1, contains);
		}
	}

	protected void assertValid(Shipment shipment) throws Exception {
		boolean valid = true;

		if (shipment.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (shipment.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (shipment.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("carrier", additionalAssertFieldName)) {
				if (shipment.getCarrier() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (shipment.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expectedDate", additionalAssertFieldName)) {
				if (shipment.getExpectedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (shipment.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (shipment.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (shipment.getOrderId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shipmentItems", additionalAssertFieldName)) {
				if (shipment.getShipmentItems() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (shipment.getShippingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (shipment.getShippingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingDate", additionalAssertFieldName)) {
				if (shipment.getShippingDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingMethodId", additionalAssertFieldName)) {
				if (shipment.getShippingMethodId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingOptionName", additionalAssertFieldName)) {

				if (shipment.getShippingOptionName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (shipment.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("trackingNumber", additionalAssertFieldName)) {
				if (shipment.getTrackingNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (shipment.getUserName() == null) {
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

	protected void assertValid(Page<Shipment> page) {
		boolean valid = false;

		java.util.Collection<Shipment> shipments = page.getItems();

		int size = shipments.size();

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
					com.liferay.headless.commerce.admin.shipment.dto.v1_0.
						Shipment.class)) {

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

	protected boolean equals(Shipment shipment1, Shipment shipment2) {
		if (shipment1 == shipment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getAccountId(), shipment2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)shipment1.getActions(),
						(Map)shipment2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("carrier", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getCarrier(), shipment2.getCarrier())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getCreateDate(), shipment2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expectedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getExpectedDate(),
						shipment2.getExpectedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						shipment1.getExternalReferenceCode(),
						shipment2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(shipment1.getId(), shipment2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getModifiedDate(),
						shipment2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getOrderId(), shipment2.getOrderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shipmentItems", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getShipmentItems(),
						shipment2.getShipmentItems())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getShippingAddress(),
						shipment2.getShippingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						shipment1.getShippingAddressId(),
						shipment2.getShippingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getShippingDate(),
						shipment2.getShippingDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingMethodId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getShippingMethodId(),
						shipment2.getShippingMethodId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingOptionName", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						shipment1.getShippingOptionName(),
						shipment2.getShippingOptionName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getStatus(), shipment2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("trackingNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getTrackingNumber(),
						shipment2.getTrackingNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipment1.getUserName(), shipment2.getUserName())) {

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

		if (!(_shipmentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_shipmentResource;

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
		EntityField entityField, String operator, Shipment shipment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("carrier")) {
			sb.append("'");
			sb.append(String.valueOf(shipment.getCarrier()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(shipment.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("expectedDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getExpectedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getExpectedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(shipment.getExpectedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(shipment.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modifiedDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(shipment.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("orderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shipmentItems")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingAddress")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getShippingDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipment.getShippingDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(shipment.getShippingDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("shippingMethodId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingOptionName")) {
			sb.append("'");
			sb.append(String.valueOf(shipment.getShippingOptionName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("trackingNumber")) {
			sb.append("'");
			sb.append(String.valueOf(shipment.getTrackingNumber()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(shipment.getUserName()));
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

	protected Shipment randomShipment() throws Exception {
		return new Shipment() {
			{
				accountId = RandomTestUtil.randomLong();
				carrier = StringUtil.toLowerCase(RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				expectedDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				orderId = RandomTestUtil.randomLong();
				shippingAddressId = RandomTestUtil.randomLong();
				shippingDate = RandomTestUtil.nextDate();
				shippingMethodId = RandomTestUtil.randomLong();
				shippingOptionName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				trackingNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				userName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected Shipment randomIrrelevantShipment() throws Exception {
		Shipment randomIrrelevantShipment = randomShipment();

		return randomIrrelevantShipment;
	}

	protected Shipment randomPatchShipment() throws Exception {
		return randomShipment();
	}

	protected ShipmentResource shipmentResource;
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
		LogFactoryUtil.getLog(BaseShipmentResourceTestCase.class);

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
		com.liferay.headless.commerce.admin.shipment.resource.v1_0.
			ShipmentResource _shipmentResource;

}