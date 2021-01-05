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

package com.liferay.headless.commerce.delivery.cart.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartComment;
import com.liferay.headless.commerce.delivery.cart.client.http.HttpInvoker;
import com.liferay.headless.commerce.delivery.cart.client.pagination.Page;
import com.liferay.headless.commerce.delivery.cart.client.pagination.Pagination;
import com.liferay.headless.commerce.delivery.cart.client.resource.v1_0.CartCommentResource;
import com.liferay.headless.commerce.delivery.cart.client.serdes.v1_0.CartCommentSerDes;
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
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public abstract class BaseCartCommentResourceTestCase {

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

		_cartCommentResource.setContextCompany(testCompany);

		CartCommentResource.Builder builder = CartCommentResource.builder();

		cartCommentResource = builder.authentication(
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

		CartComment cartComment1 = randomCartComment();

		String json = objectMapper.writeValueAsString(cartComment1);

		CartComment cartComment2 = CartCommentSerDes.toDTO(json);

		Assert.assertTrue(equals(cartComment1, cartComment2));
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

		CartComment cartComment = randomCartComment();

		String json1 = objectMapper.writeValueAsString(cartComment);
		String json2 = CartCommentSerDes.toJSON(cartComment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		CartComment cartComment = randomCartComment();

		cartComment.setAuthor(regex);
		cartComment.setContent(regex);

		String json = CartCommentSerDes.toJSON(cartComment);

		Assert.assertFalse(json.contains(regex));

		cartComment = CartCommentSerDes.toDTO(json);

		Assert.assertEquals(regex, cartComment.getAuthor());
		Assert.assertEquals(regex, cartComment.getContent());
	}

	@Test
	public void testDeleteCartComment() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		CartComment cartComment = testDeleteCartComment_addCartComment();

		assertHttpResponseStatusCode(
			204,
			cartCommentResource.deleteCartCommentHttpResponse(
				cartComment.getId()));

		assertHttpResponseStatusCode(
			404,
			cartCommentResource.getCartCommentHttpResponse(
				cartComment.getId()));

		assertHttpResponseStatusCode(
			404, cartCommentResource.getCartCommentHttpResponse(0L));
	}

	protected CartComment testDeleteCartComment_addCartComment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteCartComment() throws Exception {
		CartComment cartComment = testGraphQLCartComment_addCartComment();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteCartComment",
						new HashMap<String, Object>() {
							{
								put("cartCommentId", cartComment.getId());
							}
						})),
				"JSONObject/data", "Object/deleteCartComment"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"cartComment",
						new HashMap<String, Object>() {
							{
								put("cartCommentId", cartComment.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetCartComment() throws Exception {
		CartComment postCartComment = testGetCartComment_addCartComment();

		CartComment getCartComment = cartCommentResource.getCartComment(
			postCartComment.getId());

		assertEquals(postCartComment, getCartComment);
		assertValid(getCartComment);
	}

	protected CartComment testGetCartComment_addCartComment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCartComment() throws Exception {
		CartComment cartComment = testGraphQLCartComment_addCartComment();

		Assert.assertTrue(
			equals(
				cartComment,
				CartCommentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"cartComment",
								new HashMap<String, Object>() {
									{
										put(
											"cartCommentId",
											cartComment.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/cartComment"))));
	}

	@Test
	public void testGraphQLGetCartCommentNotFound() throws Exception {
		Long irrelevantCartCommentId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"cartComment",
						new HashMap<String, Object>() {
							{
								put("cartCommentId", irrelevantCartCommentId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchCartComment() throws Exception {
		CartComment postCartComment = testPatchCartComment_addCartComment();

		CartComment randomPatchCartComment = randomPatchCartComment();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		CartComment patchCartComment = cartCommentResource.patchCartComment(
			postCartComment.getId(), randomPatchCartComment);

		CartComment expectedPatchCartComment = postCartComment.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchCartComment, randomPatchCartComment);

		CartComment getCartComment = cartCommentResource.getCartComment(
			patchCartComment.getId());

		assertEquals(expectedPatchCartComment, getCartComment);
		assertValid(getCartComment);
	}

	protected CartComment testPatchCartComment_addCartComment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutCartComment() throws Exception {
		CartComment postCartComment = testPutCartComment_addCartComment();

		CartComment randomCartComment = randomCartComment();

		CartComment putCartComment = cartCommentResource.putCartComment(
			postCartComment.getId(), randomCartComment);

		assertEquals(randomCartComment, putCartComment);
		assertValid(putCartComment);

		CartComment getCartComment = cartCommentResource.getCartComment(
			putCartComment.getId());

		assertEquals(randomCartComment, getCartComment);
		assertValid(getCartComment);
	}

	protected CartComment testPutCartComment_addCartComment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetCartCommentsPage() throws Exception {
		Page<CartComment> page = cartCommentResource.getCartCommentsPage(
			testGetCartCommentsPage_getCartId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long cartId = testGetCartCommentsPage_getCartId();
		Long irrelevantCartId = testGetCartCommentsPage_getIrrelevantCartId();

		if ((irrelevantCartId != null)) {
			CartComment irrelevantCartComment =
				testGetCartCommentsPage_addCartComment(
					irrelevantCartId, randomIrrelevantCartComment());

			page = cartCommentResource.getCartCommentsPage(
				irrelevantCartId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantCartComment),
				(List<CartComment>)page.getItems());
			assertValid(page);
		}

		CartComment cartComment1 = testGetCartCommentsPage_addCartComment(
			cartId, randomCartComment());

		CartComment cartComment2 = testGetCartCommentsPage_addCartComment(
			cartId, randomCartComment());

		page = cartCommentResource.getCartCommentsPage(
			cartId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(cartComment1, cartComment2),
			(List<CartComment>)page.getItems());
		assertValid(page);

		cartCommentResource.deleteCartComment(cartComment1.getId());

		cartCommentResource.deleteCartComment(cartComment2.getId());
	}

	@Test
	public void testGetCartCommentsPageWithPagination() throws Exception {
		Long cartId = testGetCartCommentsPage_getCartId();

		CartComment cartComment1 = testGetCartCommentsPage_addCartComment(
			cartId, randomCartComment());

		CartComment cartComment2 = testGetCartCommentsPage_addCartComment(
			cartId, randomCartComment());

		CartComment cartComment3 = testGetCartCommentsPage_addCartComment(
			cartId, randomCartComment());

		Page<CartComment> page1 = cartCommentResource.getCartCommentsPage(
			cartId, Pagination.of(1, 2));

		List<CartComment> cartComments1 = (List<CartComment>)page1.getItems();

		Assert.assertEquals(cartComments1.toString(), 2, cartComments1.size());

		Page<CartComment> page2 = cartCommentResource.getCartCommentsPage(
			cartId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<CartComment> cartComments2 = (List<CartComment>)page2.getItems();

		Assert.assertEquals(cartComments2.toString(), 1, cartComments2.size());

		Page<CartComment> page3 = cartCommentResource.getCartCommentsPage(
			cartId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(cartComment1, cartComment2, cartComment3),
			(List<CartComment>)page3.getItems());
	}

	protected CartComment testGetCartCommentsPage_addCartComment(
			Long cartId, CartComment cartComment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCartCommentsPage_getCartId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCartCommentsPage_getIrrelevantCartId()
		throws Exception {

		return null;
	}

	@Test
	public void testGraphQLGetCartCommentsPage() throws Exception {
		Long cartId = testGetCartCommentsPage_getCartId();

		GraphQLField graphQLField = new GraphQLField(
			"cartComments",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("cartId", cartId);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject cartCommentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/cartComments");

		Assert.assertEquals(0, cartCommentsJSONObject.get("totalCount"));

		CartComment cartComment1 = testGraphQLCartComment_addCartComment();
		CartComment cartComment2 = testGraphQLCartComment_addCartComment();

		cartCommentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/cartComments");

		Assert.assertEquals(2, cartCommentsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(cartComment1, cartComment2),
			Arrays.asList(
				CartCommentSerDes.toDTOs(
					cartCommentsJSONObject.getString("items"))));
	}

	@Test
	public void testPostCartComment() throws Exception {
		CartComment randomCartComment = randomCartComment();

		CartComment postCartComment = testPostCartComment_addCartComment(
			randomCartComment);

		assertEquals(randomCartComment, postCartComment);
		assertValid(postCartComment);
	}

	protected CartComment testPostCartComment_addCartComment(
			CartComment cartComment)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected CartComment testGraphQLCartComment_addCartComment()
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
		CartComment cartComment1, CartComment cartComment2) {

		Assert.assertTrue(
			cartComment1 + " does not equal " + cartComment2,
			equals(cartComment1, cartComment2));
	}

	protected void assertEquals(
		List<CartComment> cartComments1, List<CartComment> cartComments2) {

		Assert.assertEquals(cartComments1.size(), cartComments2.size());

		for (int i = 0; i < cartComments1.size(); i++) {
			CartComment cartComment1 = cartComments1.get(i);
			CartComment cartComment2 = cartComments2.get(i);

			assertEquals(cartComment1, cartComment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<CartComment> cartComments1, List<CartComment> cartComments2) {

		Assert.assertEquals(cartComments1.size(), cartComments2.size());

		for (CartComment cartComment1 : cartComments1) {
			boolean contains = false;

			for (CartComment cartComment2 : cartComments2) {
				if (equals(cartComment1, cartComment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				cartComments2 + " does not contain " + cartComment1, contains);
		}
	}

	protected void assertValid(CartComment cartComment) throws Exception {
		boolean valid = true;

		if (cartComment.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (cartComment.getAuthor() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (cartComment.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (cartComment.getOrderId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("restricted", additionalAssertFieldName)) {
				if (cartComment.getRestricted() == null) {
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

	protected void assertValid(Page<CartComment> page) {
		boolean valid = false;

		java.util.Collection<CartComment> cartComments = page.getItems();

		int size = cartComments.size();

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
					com.liferay.headless.commerce.delivery.cart.dto.v1_0.
						CartComment.class)) {

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
		CartComment cartComment1, CartComment cartComment2) {

		if (cartComment1 == cartComment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartComment1.getAuthor(), cartComment2.getAuthor())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartComment1.getContent(), cartComment2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartComment1.getId(), cartComment2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartComment1.getOrderId(), cartComment2.getOrderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("restricted", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartComment1.getRestricted(),
						cartComment2.getRestricted())) {

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

		if (!(_cartCommentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_cartCommentResource;

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
		EntityField entityField, String operator, CartComment cartComment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("author")) {
			sb.append("'");
			sb.append(String.valueOf(cartComment.getAuthor()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("content")) {
			sb.append("'");
			sb.append(String.valueOf(cartComment.getContent()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected CartComment randomCartComment() throws Exception {
		return new CartComment() {
			{
				author = StringUtil.toLowerCase(RandomTestUtil.randomString());
				content = StringUtil.toLowerCase(RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				orderId = RandomTestUtil.randomLong();
				restricted = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected CartComment randomIrrelevantCartComment() throws Exception {
		CartComment randomIrrelevantCartComment = randomCartComment();

		return randomIrrelevantCartComment;
	}

	protected CartComment randomPatchCartComment() throws Exception {
		return randomCartComment();
	}

	protected CartCommentResource cartCommentResource;
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
		BaseCartCommentResourceTestCase.class);

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
	private com.liferay.headless.commerce.delivery.cart.resource.v1_0.
		CartCommentResource _cartCommentResource;

}