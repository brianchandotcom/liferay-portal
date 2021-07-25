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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderNote;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderNoteResource;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderNoteSerDes;
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
public abstract class BaseOrderNoteResourceTestCase {

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

		_orderNoteResource.setContextCompany(testCompany);

		OrderNoteResource.Builder builder = OrderNoteResource.builder();

		orderNoteResource = builder.authentication(
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

		OrderNote orderNote1 = randomOrderNote();

		String json = objectMapper.writeValueAsString(orderNote1);

		OrderNote orderNote2 = OrderNoteSerDes.toDTO(json);

		Assert.assertTrue(equals(orderNote1, orderNote2));
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

		OrderNote orderNote = randomOrderNote();

		String json1 = objectMapper.writeValueAsString(orderNote);
		String json2 = OrderNoteSerDes.toJSON(orderNote);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		OrderNote orderNote = randomOrderNote();

		orderNote.setAuthor(regex);
		orderNote.setContent(regex);
		orderNote.setExternalReferenceCode(regex);
		orderNote.setOrderExternalReferenceCode(regex);

		String json = OrderNoteSerDes.toJSON(orderNote);

		Assert.assertFalse(json.contains(regex));

		orderNote = OrderNoteSerDes.toDTO(json);

		Assert.assertEquals(regex, orderNote.getAuthor());
		Assert.assertEquals(regex, orderNote.getContent());
		Assert.assertEquals(regex, orderNote.getExternalReferenceCode());
		Assert.assertEquals(regex, orderNote.getOrderExternalReferenceCode());
	}

	@Test
	public void testDeleteOrderNoteByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderNote orderNote =
			testDeleteOrderNoteByExternalReferenceCode_addOrderNote();

		assertHttpResponseStatusCode(
			204,
			orderNoteResource.
				deleteOrderNoteByExternalReferenceCodeHttpResponse(
					orderNote.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderNoteResource.getOrderNoteByExternalReferenceCodeHttpResponse(
				orderNote.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderNoteResource.getOrderNoteByExternalReferenceCodeHttpResponse(
				orderNote.getExternalReferenceCode()));
	}

	protected OrderNote
			testDeleteOrderNoteByExternalReferenceCode_addOrderNote()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderNoteByExternalReferenceCode() throws Exception {
		OrderNote postOrderNote =
			testGetOrderNoteByExternalReferenceCode_addOrderNote();

		OrderNote getOrderNote =
			orderNoteResource.getOrderNoteByExternalReferenceCode(
				postOrderNote.getExternalReferenceCode());

		assertEquals(postOrderNote, getOrderNote);
		assertValid(getOrderNote);
	}

	protected OrderNote testGetOrderNoteByExternalReferenceCode_addOrderNote()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderNoteByExternalReferenceCode()
		throws Exception {

		OrderNote orderNote = testGraphQLOrderNote_addOrderNote();

		Assert.assertTrue(
			equals(
				orderNote,
				OrderNoteSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderNoteByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												orderNote.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderNoteByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetOrderNoteByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"orderNoteByExternalReferenceCode",
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
	public void testPatchOrderNoteByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteOrderNote() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderNote orderNote = testDeleteOrderNote_addOrderNote();

		assertHttpResponseStatusCode(
			204,
			orderNoteResource.deleteOrderNoteHttpResponse(orderNote.getId()));

		assertHttpResponseStatusCode(
			404, orderNoteResource.getOrderNoteHttpResponse(orderNote.getId()));

		assertHttpResponseStatusCode(
			404, orderNoteResource.getOrderNoteHttpResponse(orderNote.getId()));
	}

	protected OrderNote testDeleteOrderNote_addOrderNote() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteOrderNote() throws Exception {
		OrderNote orderNote = testGraphQLOrderNote_addOrderNote();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrderNote",
						new HashMap<String, Object>() {
							{
								put("id", orderNote.getId());
							}
						})),
				"JSONObject/data", "Object/deleteOrderNote"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"orderNote",
					new HashMap<String, Object>() {
						{
							put("id", orderNote.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetOrderNote() throws Exception {
		OrderNote postOrderNote = testGetOrderNote_addOrderNote();

		OrderNote getOrderNote = orderNoteResource.getOrderNote(
			postOrderNote.getId());

		assertEquals(postOrderNote, getOrderNote);
		assertValid(getOrderNote);
	}

	protected OrderNote testGetOrderNote_addOrderNote() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderNote() throws Exception {
		OrderNote orderNote = testGraphQLOrderNote_addOrderNote();

		Assert.assertTrue(
			equals(
				orderNote,
				OrderNoteSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderNote",
								new HashMap<String, Object>() {
									{
										put("id", orderNote.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/orderNote"))));
	}

	@Test
	public void testGraphQLGetOrderNoteNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"orderNote",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchOrderNote() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetOrderByExternalReferenceCodeOrderNotesPage()
		throws Exception {

		Page<OrderNote> page =
			orderNoteResource.getOrderByExternalReferenceCodeOrderNotesPage(
				testGetOrderByExternalReferenceCodeOrderNotesPage_getExternalReferenceCode(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetOrderByExternalReferenceCodeOrderNotesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetOrderByExternalReferenceCodeOrderNotesPage_getIrrelevantExternalReferenceCode();

		if (irrelevantExternalReferenceCode != null) {
			OrderNote irrelevantOrderNote =
				testGetOrderByExternalReferenceCodeOrderNotesPage_addOrderNote(
					irrelevantExternalReferenceCode,
					randomIrrelevantOrderNote());

			page =
				orderNoteResource.getOrderByExternalReferenceCodeOrderNotesPage(
					irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderNote),
				(List<OrderNote>)page.getItems());
			assertValid(page);
		}

		OrderNote orderNote1 =
			testGetOrderByExternalReferenceCodeOrderNotesPage_addOrderNote(
				externalReferenceCode, randomOrderNote());

		OrderNote orderNote2 =
			testGetOrderByExternalReferenceCodeOrderNotesPage_addOrderNote(
				externalReferenceCode, randomOrderNote());

		page = orderNoteResource.getOrderByExternalReferenceCodeOrderNotesPage(
			externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderNote1, orderNote2),
			(List<OrderNote>)page.getItems());
		assertValid(page);

		orderNoteResource.deleteOrderNote(orderNote1.getId());

		orderNoteResource.deleteOrderNote(orderNote2.getId());
	}

	@Test
	public void testGetOrderByExternalReferenceCodeOrderNotesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetOrderByExternalReferenceCodeOrderNotesPage_getExternalReferenceCode();

		OrderNote orderNote1 =
			testGetOrderByExternalReferenceCodeOrderNotesPage_addOrderNote(
				externalReferenceCode, randomOrderNote());

		OrderNote orderNote2 =
			testGetOrderByExternalReferenceCodeOrderNotesPage_addOrderNote(
				externalReferenceCode, randomOrderNote());

		OrderNote orderNote3 =
			testGetOrderByExternalReferenceCodeOrderNotesPage_addOrderNote(
				externalReferenceCode, randomOrderNote());

		Page<OrderNote> page1 =
			orderNoteResource.getOrderByExternalReferenceCodeOrderNotesPage(
				externalReferenceCode, Pagination.of(1, 2));

		List<OrderNote> orderNotes1 = (List<OrderNote>)page1.getItems();

		Assert.assertEquals(orderNotes1.toString(), 2, orderNotes1.size());

		Page<OrderNote> page2 =
			orderNoteResource.getOrderByExternalReferenceCodeOrderNotesPage(
				externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderNote> orderNotes2 = (List<OrderNote>)page2.getItems();

		Assert.assertEquals(orderNotes2.toString(), 1, orderNotes2.size());

		Page<OrderNote> page3 =
			orderNoteResource.getOrderByExternalReferenceCodeOrderNotesPage(
				externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(orderNote1, orderNote2, orderNote3),
			(List<OrderNote>)page3.getItems());
	}

	protected OrderNote
			testGetOrderByExternalReferenceCodeOrderNotesPage_addOrderNote(
				String externalReferenceCode, OrderNote orderNote)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderByExternalReferenceCodeOrderNotesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderByExternalReferenceCodeOrderNotesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderByExternalReferenceCodeOrderNote()
		throws Exception {

		OrderNote randomOrderNote = randomOrderNote();

		OrderNote postOrderNote =
			testPostOrderByExternalReferenceCodeOrderNote_addOrderNote(
				randomOrderNote);

		assertEquals(randomOrderNote, postOrderNote);
		assertValid(postOrderNote);
	}

	protected OrderNote
			testPostOrderByExternalReferenceCodeOrderNote_addOrderNote(
				OrderNote orderNote)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderIdOrderNotesPage() throws Exception {
		Page<OrderNote> page = orderNoteResource.getOrderIdOrderNotesPage(
			testGetOrderIdOrderNotesPage_getId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetOrderIdOrderNotesPage_getId();
		Long irrelevantId = testGetOrderIdOrderNotesPage_getIrrelevantId();

		if (irrelevantId != null) {
			OrderNote irrelevantOrderNote =
				testGetOrderIdOrderNotesPage_addOrderNote(
					irrelevantId, randomIrrelevantOrderNote());

			page = orderNoteResource.getOrderIdOrderNotesPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderNote),
				(List<OrderNote>)page.getItems());
			assertValid(page);
		}

		OrderNote orderNote1 = testGetOrderIdOrderNotesPage_addOrderNote(
			id, randomOrderNote());

		OrderNote orderNote2 = testGetOrderIdOrderNotesPage_addOrderNote(
			id, randomOrderNote());

		page = orderNoteResource.getOrderIdOrderNotesPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderNote1, orderNote2),
			(List<OrderNote>)page.getItems());
		assertValid(page);

		orderNoteResource.deleteOrderNote(orderNote1.getId());

		orderNoteResource.deleteOrderNote(orderNote2.getId());
	}

	@Test
	public void testGetOrderIdOrderNotesPageWithPagination() throws Exception {
		Long id = testGetOrderIdOrderNotesPage_getId();

		OrderNote orderNote1 = testGetOrderIdOrderNotesPage_addOrderNote(
			id, randomOrderNote());

		OrderNote orderNote2 = testGetOrderIdOrderNotesPage_addOrderNote(
			id, randomOrderNote());

		OrderNote orderNote3 = testGetOrderIdOrderNotesPage_addOrderNote(
			id, randomOrderNote());

		Page<OrderNote> page1 = orderNoteResource.getOrderIdOrderNotesPage(
			id, Pagination.of(1, 2));

		List<OrderNote> orderNotes1 = (List<OrderNote>)page1.getItems();

		Assert.assertEquals(orderNotes1.toString(), 2, orderNotes1.size());

		Page<OrderNote> page2 = orderNoteResource.getOrderIdOrderNotesPage(
			id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderNote> orderNotes2 = (List<OrderNote>)page2.getItems();

		Assert.assertEquals(orderNotes2.toString(), 1, orderNotes2.size());

		Page<OrderNote> page3 = orderNoteResource.getOrderIdOrderNotesPage(
			id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(orderNote1, orderNote2, orderNote3),
			(List<OrderNote>)page3.getItems());
	}

	protected OrderNote testGetOrderIdOrderNotesPage_addOrderNote(
			Long id, OrderNote orderNote)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderIdOrderNotesPage_getId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderIdOrderNotesPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderIdOrderNote() throws Exception {
		OrderNote randomOrderNote = randomOrderNote();

		OrderNote postOrderNote = testPostOrderIdOrderNote_addOrderNote(
			randomOrderNote);

		assertEquals(randomOrderNote, postOrderNote);
		assertValid(postOrderNote);
	}

	protected OrderNote testPostOrderIdOrderNote_addOrderNote(
			OrderNote orderNote)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected OrderNote testGraphQLOrderNote_addOrderNote() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(OrderNote orderNote1, OrderNote orderNote2) {
		Assert.assertTrue(
			orderNote1 + " does not equal " + orderNote2,
			equals(orderNote1, orderNote2));
	}

	protected void assertEquals(
		List<OrderNote> orderNotes1, List<OrderNote> orderNotes2) {

		Assert.assertEquals(orderNotes1.size(), orderNotes2.size());

		for (int i = 0; i < orderNotes1.size(); i++) {
			OrderNote orderNote1 = orderNotes1.get(i);
			OrderNote orderNote2 = orderNotes2.get(i);

			assertEquals(orderNote1, orderNote2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<OrderNote> orderNotes1, List<OrderNote> orderNotes2) {

		Assert.assertEquals(orderNotes1.size(), orderNotes2.size());

		for (OrderNote orderNote1 : orderNotes1) {
			boolean contains = false;

			for (OrderNote orderNote2 : orderNotes2) {
				if (equals(orderNote1, orderNote2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				orderNotes2 + " does not contain " + orderNote1, contains);
		}
	}

	protected void assertValid(OrderNote orderNote) throws Exception {
		boolean valid = true;

		if (orderNote.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (orderNote.getAuthor() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (orderNote.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (orderNote.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderExternalReferenceCode", additionalAssertFieldName)) {

				if (orderNote.getOrderExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (orderNote.getOrderId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("restricted", additionalAssertFieldName)) {
				if (orderNote.getRestricted() == null) {
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

	protected void assertValid(Page<OrderNote> page) {
		boolean valid = false;

		java.util.Collection<OrderNote> orderNotes = page.getItems();

		int size = orderNotes.size();

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
				getDeclaredFields(
					com.liferay.headless.commerce.admin.order.dto.v1_0.
						OrderNote.class)) {

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

	protected boolean equals(OrderNote orderNote1, OrderNote orderNote2) {
		if (orderNote1 == orderNote2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderNote1.getAuthor(), orderNote2.getAuthor())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderNote1.getContent(), orderNote2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderNote1.getExternalReferenceCode(),
						orderNote2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderNote1.getId(), orderNote2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderExternalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderNote1.getOrderExternalReferenceCode(),
						orderNote2.getOrderExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderNote1.getOrderId(), orderNote2.getOrderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("restricted", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderNote1.getRestricted(),
						orderNote2.getRestricted())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_orderNoteResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_orderNoteResource;

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
		EntityField entityField, String operator, OrderNote orderNote) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("author")) {
			sb.append("'");
			sb.append(String.valueOf(orderNote.getAuthor()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("content")) {
			sb.append("'");
			sb.append(String.valueOf(orderNote.getContent()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(orderNote.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(orderNote.getOrderExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("orderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("restricted")) {
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

	protected OrderNote randomOrderNote() throws Exception {
		return new OrderNote() {
			{
				author = StringUtil.toLowerCase(RandomTestUtil.randomString());
				content = StringUtil.toLowerCase(RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				orderExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderId = RandomTestUtil.randomLong();
				restricted = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected OrderNote randomIrrelevantOrderNote() throws Exception {
		OrderNote randomIrrelevantOrderNote = randomOrderNote();

		return randomIrrelevantOrderNote;
	}

	protected OrderNote randomPatchOrderNote() throws Exception {
		return randomOrderNote();
	}

	protected OrderNoteResource orderNoteResource;
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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseOrderNoteResourceTestCase.class);

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
		com.liferay.headless.commerce.admin.order.resource.v1_0.
			OrderNoteResource _orderNoteResource;

}