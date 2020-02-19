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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.Comment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.CommentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.CommentSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseCommentResourceTestCase {

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

		_commentResource.setContextCompany(testCompany);

		CommentResource.Builder builder = CommentResource.builder();

		commentResource = builder.locale(
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

		Comment comment1 = randomComment();

		String json = objectMapper.writeValueAsString(comment1);

		Comment comment2 = CommentSerDes.toDTO(json);

		Assert.assertTrue(equals(comment1, comment2));
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

		Comment comment = randomComment();

		String json1 = objectMapper.writeValueAsString(comment);
		String json2 = CommentSerDes.toJSON(comment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Comment comment = randomComment();

		comment.setText(regex);

		String json = CommentSerDes.toJSON(comment);

		Assert.assertFalse(json.contains(regex));

		comment = CommentSerDes.toDTO(json);

		Assert.assertEquals(regex, comment.getText());
	}

	@Test
	public void testGetBlogPostingCommentsPage() throws Exception {
		Page<Comment> page = commentResource.getBlogPostingCommentsPage(
			testGetBlogPostingCommentsPage_getBlogPostingId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long blogPostingId = testGetBlogPostingCommentsPage_getBlogPostingId();
		Long irrelevantBlogPostingId =
			testGetBlogPostingCommentsPage_getIrrelevantBlogPostingId();

		if ((irrelevantBlogPostingId != null)) {
			Comment irrelevantComment =
				testGetBlogPostingCommentsPage_addComment(
					irrelevantBlogPostingId, randomIrrelevantComment());

			page = commentResource.getBlogPostingCommentsPage(
				irrelevantBlogPostingId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantComment),
				(List<Comment>)page.getItems());
			assertValid(page);
		}

		Comment comment1 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, randomComment());

		Comment comment2 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, randomComment());

		page = commentResource.getBlogPostingCommentsPage(
			blogPostingId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2), (List<Comment>)page.getItems());
		assertValid(page);

		commentResource.deleteComment(comment1.getId());

		commentResource.deleteComment(comment2.getId());
	}

	@Test
	public void testGetBlogPostingCommentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long blogPostingId = testGetBlogPostingCommentsPage_getBlogPostingId();

		Comment comment1 = randomComment();

		comment1 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, comment1);

		for (EntityField entityField : entityFields) {
			Page<Comment> page = commentResource.getBlogPostingCommentsPage(
				blogPostingId, null,
				getFilterString(entityField, "between", comment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetBlogPostingCommentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long blogPostingId = testGetBlogPostingCommentsPage_getBlogPostingId();

		Comment comment1 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, randomComment());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Comment comment2 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, randomComment());

		for (EntityField entityField : entityFields) {
			Page<Comment> page = commentResource.getBlogPostingCommentsPage(
				blogPostingId, null,
				getFilterString(entityField, "eq", comment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetBlogPostingCommentsPageWithPagination()
		throws Exception {

		Long blogPostingId = testGetBlogPostingCommentsPage_getBlogPostingId();

		Comment comment1 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, randomComment());

		Comment comment2 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, randomComment());

		Comment comment3 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, randomComment());

		Page<Comment> page1 = commentResource.getBlogPostingCommentsPage(
			blogPostingId, null, null, Pagination.of(1, 2), null);

		List<Comment> comments1 = (List<Comment>)page1.getItems();

		Assert.assertEquals(comments1.toString(), 2, comments1.size());

		Page<Comment> page2 = commentResource.getBlogPostingCommentsPage(
			blogPostingId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Comment> comments2 = (List<Comment>)page2.getItems();

		Assert.assertEquals(comments2.toString(), 1, comments2.size());

		Page<Comment> page3 = commentResource.getBlogPostingCommentsPage(
			blogPostingId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2, comment3),
			(List<Comment>)page3.getItems());
	}

	@Test
	public void testGetBlogPostingCommentsPageWithSortDateTime()
		throws Exception {

		testGetBlogPostingCommentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(
					comment1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetBlogPostingCommentsPageWithSortInteger()
		throws Exception {

		testGetBlogPostingCommentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(comment1, entityField.getName(), 0);
				BeanUtils.setProperty(comment2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetBlogPostingCommentsPageWithSortString()
		throws Exception {

		testGetBlogPostingCommentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, comment1, comment2) -> {
				Class<?> clazz = comment1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetBlogPostingCommentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Comment, Comment, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long blogPostingId = testGetBlogPostingCommentsPage_getBlogPostingId();

		Comment comment1 = randomComment();
		Comment comment2 = randomComment();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, comment1, comment2);
		}

		comment1 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, comment1);

		comment2 = testGetBlogPostingCommentsPage_addComment(
			blogPostingId, comment2);

		for (EntityField entityField : entityFields) {
			Page<Comment> ascPage = commentResource.getBlogPostingCommentsPage(
				blogPostingId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(comment1, comment2),
				(List<Comment>)ascPage.getItems());

			Page<Comment> descPage = commentResource.getBlogPostingCommentsPage(
				blogPostingId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(comment2, comment1),
				(List<Comment>)descPage.getItems());
		}
	}

	protected Comment testGetBlogPostingCommentsPage_addComment(
			Long blogPostingId, Comment comment)
		throws Exception {

		return commentResource.postBlogPostingComment(blogPostingId, comment);
	}

	protected Long testGetBlogPostingCommentsPage_getBlogPostingId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetBlogPostingCommentsPage_getIrrelevantBlogPostingId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostBlogPostingComment() throws Exception {
		Comment randomComment = randomComment();

		Comment postComment = testPostBlogPostingComment_addComment(
			randomComment);

		assertEquals(randomComment, postComment);
		assertValid(postComment);
	}

	protected Comment testPostBlogPostingComment_addComment(Comment comment)
		throws Exception {

		return commentResource.postBlogPostingComment(
			testGetBlogPostingCommentsPage_getBlogPostingId(), comment);
	}

	@Test
	public void testDeleteComment() throws Exception {
		Comment comment = testDeleteComment_addComment();

		assertHttpResponseStatusCode(
			204, commentResource.deleteCommentHttpResponse(comment.getId()));

		assertHttpResponseStatusCode(
			404, commentResource.getCommentHttpResponse(comment.getId()));

		assertHttpResponseStatusCode(
			404, commentResource.getCommentHttpResponse(0L));
	}

	protected Comment testDeleteComment_addComment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteComment() throws Exception {
		Comment comment = testGraphQLComment_addComment();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteComment",
				new HashMap<String, Object>() {
					{
						put("commentId", comment.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteComment"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"comment",
					new HashMap<String, Object>() {
						{
							put("commentId", comment.getId());
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetComment() throws Exception {
		Comment postComment = testGetComment_addComment();

		Comment getComment = commentResource.getComment(postComment.getId());

		assertEquals(postComment, getComment);
		assertValid(getComment);
	}

	protected Comment testGetComment_addComment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetComment() throws Exception {
		Comment comment = testGraphQLComment_addComment();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"comment",
				new HashMap<String, Object>() {
					{
						put("commentId", comment.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(comment, dataJSONObject.getJSONObject("comment")));
	}

	@Test
	public void testPutComment() throws Exception {
		Comment postComment = testPutComment_addComment();

		Comment randomComment = randomComment();

		Comment putComment = commentResource.putComment(
			postComment.getId(), randomComment);

		assertEquals(randomComment, putComment);
		assertValid(putComment);

		Comment getComment = commentResource.getComment(putComment.getId());

		assertEquals(randomComment, getComment);
		assertValid(getComment);
	}

	protected Comment testPutComment_addComment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetCommentCommentsPage() throws Exception {
		Page<Comment> page = commentResource.getCommentCommentsPage(
			testGetCommentCommentsPage_getParentCommentId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long parentCommentId = testGetCommentCommentsPage_getParentCommentId();
		Long irrelevantParentCommentId =
			testGetCommentCommentsPage_getIrrelevantParentCommentId();

		if ((irrelevantParentCommentId != null)) {
			Comment irrelevantComment = testGetCommentCommentsPage_addComment(
				irrelevantParentCommentId, randomIrrelevantComment());

			page = commentResource.getCommentCommentsPage(
				irrelevantParentCommentId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantComment),
				(List<Comment>)page.getItems());
			assertValid(page);
		}

		Comment comment1 = testGetCommentCommentsPage_addComment(
			parentCommentId, randomComment());

		Comment comment2 = testGetCommentCommentsPage_addComment(
			parentCommentId, randomComment());

		page = commentResource.getCommentCommentsPage(
			parentCommentId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2), (List<Comment>)page.getItems());
		assertValid(page);

		commentResource.deleteComment(comment1.getId());

		commentResource.deleteComment(comment2.getId());
	}

	@Test
	public void testGetCommentCommentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentCommentId = testGetCommentCommentsPage_getParentCommentId();

		Comment comment1 = randomComment();

		comment1 = testGetCommentCommentsPage_addComment(
			parentCommentId, comment1);

		for (EntityField entityField : entityFields) {
			Page<Comment> page = commentResource.getCommentCommentsPage(
				parentCommentId, null,
				getFilterString(entityField, "between", comment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetCommentCommentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentCommentId = testGetCommentCommentsPage_getParentCommentId();

		Comment comment1 = testGetCommentCommentsPage_addComment(
			parentCommentId, randomComment());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Comment comment2 = testGetCommentCommentsPage_addComment(
			parentCommentId, randomComment());

		for (EntityField entityField : entityFields) {
			Page<Comment> page = commentResource.getCommentCommentsPage(
				parentCommentId, null,
				getFilterString(entityField, "eq", comment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetCommentCommentsPageWithPagination() throws Exception {
		Long parentCommentId = testGetCommentCommentsPage_getParentCommentId();

		Comment comment1 = testGetCommentCommentsPage_addComment(
			parentCommentId, randomComment());

		Comment comment2 = testGetCommentCommentsPage_addComment(
			parentCommentId, randomComment());

		Comment comment3 = testGetCommentCommentsPage_addComment(
			parentCommentId, randomComment());

		Page<Comment> page1 = commentResource.getCommentCommentsPage(
			parentCommentId, null, null, Pagination.of(1, 2), null);

		List<Comment> comments1 = (List<Comment>)page1.getItems();

		Assert.assertEquals(comments1.toString(), 2, comments1.size());

		Page<Comment> page2 = commentResource.getCommentCommentsPage(
			parentCommentId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Comment> comments2 = (List<Comment>)page2.getItems();

		Assert.assertEquals(comments2.toString(), 1, comments2.size());

		Page<Comment> page3 = commentResource.getCommentCommentsPage(
			parentCommentId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2, comment3),
			(List<Comment>)page3.getItems());
	}

	@Test
	public void testGetCommentCommentsPageWithSortDateTime() throws Exception {
		testGetCommentCommentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(
					comment1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCommentCommentsPageWithSortInteger() throws Exception {
		testGetCommentCommentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(comment1, entityField.getName(), 0);
				BeanUtils.setProperty(comment2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCommentCommentsPageWithSortString() throws Exception {
		testGetCommentCommentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, comment1, comment2) -> {
				Class<?> clazz = comment1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetCommentCommentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Comment, Comment, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentCommentId = testGetCommentCommentsPage_getParentCommentId();

		Comment comment1 = randomComment();
		Comment comment2 = randomComment();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, comment1, comment2);
		}

		comment1 = testGetCommentCommentsPage_addComment(
			parentCommentId, comment1);

		comment2 = testGetCommentCommentsPage_addComment(
			parentCommentId, comment2);

		for (EntityField entityField : entityFields) {
			Page<Comment> ascPage = commentResource.getCommentCommentsPage(
				parentCommentId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(comment1, comment2),
				(List<Comment>)ascPage.getItems());

			Page<Comment> descPage = commentResource.getCommentCommentsPage(
				parentCommentId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(comment2, comment1),
				(List<Comment>)descPage.getItems());
		}
	}

	protected Comment testGetCommentCommentsPage_addComment(
			Long parentCommentId, Comment comment)
		throws Exception {

		return commentResource.postCommentComment(parentCommentId, comment);
	}

	protected Long testGetCommentCommentsPage_getParentCommentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCommentCommentsPage_getIrrelevantParentCommentId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostCommentComment() throws Exception {
		Comment randomComment = randomComment();

		Comment postComment = testPostCommentComment_addComment(randomComment);

		assertEquals(randomComment, postComment);
		assertValid(postComment);
	}

	protected Comment testPostCommentComment_addComment(Comment comment)
		throws Exception {

		return commentResource.postCommentComment(
			testGetCommentCommentsPage_getParentCommentId(), comment);
	}

	@Test
	public void testGetDocumentCommentsPage() throws Exception {
		Page<Comment> page = commentResource.getDocumentCommentsPage(
			testGetDocumentCommentsPage_getDocumentId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long documentId = testGetDocumentCommentsPage_getDocumentId();
		Long irrelevantDocumentId =
			testGetDocumentCommentsPage_getIrrelevantDocumentId();

		if ((irrelevantDocumentId != null)) {
			Comment irrelevantComment = testGetDocumentCommentsPage_addComment(
				irrelevantDocumentId, randomIrrelevantComment());

			page = commentResource.getDocumentCommentsPage(
				irrelevantDocumentId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantComment),
				(List<Comment>)page.getItems());
			assertValid(page);
		}

		Comment comment1 = testGetDocumentCommentsPage_addComment(
			documentId, randomComment());

		Comment comment2 = testGetDocumentCommentsPage_addComment(
			documentId, randomComment());

		page = commentResource.getDocumentCommentsPage(
			documentId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2), (List<Comment>)page.getItems());
		assertValid(page);

		commentResource.deleteComment(comment1.getId());

		commentResource.deleteComment(comment2.getId());
	}

	@Test
	public void testGetDocumentCommentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentId = testGetDocumentCommentsPage_getDocumentId();

		Comment comment1 = randomComment();

		comment1 = testGetDocumentCommentsPage_addComment(documentId, comment1);

		for (EntityField entityField : entityFields) {
			Page<Comment> page = commentResource.getDocumentCommentsPage(
				documentId, null,
				getFilterString(entityField, "between", comment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetDocumentCommentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentId = testGetDocumentCommentsPage_getDocumentId();

		Comment comment1 = testGetDocumentCommentsPage_addComment(
			documentId, randomComment());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Comment comment2 = testGetDocumentCommentsPage_addComment(
			documentId, randomComment());

		for (EntityField entityField : entityFields) {
			Page<Comment> page = commentResource.getDocumentCommentsPage(
				documentId, null, getFilterString(entityField, "eq", comment1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetDocumentCommentsPageWithPagination() throws Exception {
		Long documentId = testGetDocumentCommentsPage_getDocumentId();

		Comment comment1 = testGetDocumentCommentsPage_addComment(
			documentId, randomComment());

		Comment comment2 = testGetDocumentCommentsPage_addComment(
			documentId, randomComment());

		Comment comment3 = testGetDocumentCommentsPage_addComment(
			documentId, randomComment());

		Page<Comment> page1 = commentResource.getDocumentCommentsPage(
			documentId, null, null, Pagination.of(1, 2), null);

		List<Comment> comments1 = (List<Comment>)page1.getItems();

		Assert.assertEquals(comments1.toString(), 2, comments1.size());

		Page<Comment> page2 = commentResource.getDocumentCommentsPage(
			documentId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Comment> comments2 = (List<Comment>)page2.getItems();

		Assert.assertEquals(comments2.toString(), 1, comments2.size());

		Page<Comment> page3 = commentResource.getDocumentCommentsPage(
			documentId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2, comment3),
			(List<Comment>)page3.getItems());
	}

	@Test
	public void testGetDocumentCommentsPageWithSortDateTime() throws Exception {
		testGetDocumentCommentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(
					comment1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDocumentCommentsPageWithSortInteger() throws Exception {
		testGetDocumentCommentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(comment1, entityField.getName(), 0);
				BeanUtils.setProperty(comment2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDocumentCommentsPageWithSortString() throws Exception {
		testGetDocumentCommentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, comment1, comment2) -> {
				Class<?> clazz = comment1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetDocumentCommentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Comment, Comment, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentId = testGetDocumentCommentsPage_getDocumentId();

		Comment comment1 = randomComment();
		Comment comment2 = randomComment();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, comment1, comment2);
		}

		comment1 = testGetDocumentCommentsPage_addComment(documentId, comment1);

		comment2 = testGetDocumentCommentsPage_addComment(documentId, comment2);

		for (EntityField entityField : entityFields) {
			Page<Comment> ascPage = commentResource.getDocumentCommentsPage(
				documentId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(comment1, comment2),
				(List<Comment>)ascPage.getItems());

			Page<Comment> descPage = commentResource.getDocumentCommentsPage(
				documentId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(comment2, comment1),
				(List<Comment>)descPage.getItems());
		}
	}

	protected Comment testGetDocumentCommentsPage_addComment(
			Long documentId, Comment comment)
		throws Exception {

		return commentResource.postDocumentComment(documentId, comment);
	}

	protected Long testGetDocumentCommentsPage_getDocumentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDocumentCommentsPage_getIrrelevantDocumentId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDocumentComment() throws Exception {
		Comment randomComment = randomComment();

		Comment postComment = testPostDocumentComment_addComment(randomComment);

		assertEquals(randomComment, postComment);
		assertValid(postComment);
	}

	protected Comment testPostDocumentComment_addComment(Comment comment)
		throws Exception {

		return commentResource.postDocumentComment(
			testGetDocumentCommentsPage_getDocumentId(), comment);
	}

	@Test
	public void testGetStructuredContentCommentsPage() throws Exception {
		Page<Comment> page = commentResource.getStructuredContentCommentsPage(
			testGetStructuredContentCommentsPage_getStructuredContentId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long structuredContentId =
			testGetStructuredContentCommentsPage_getStructuredContentId();
		Long irrelevantStructuredContentId =
			testGetStructuredContentCommentsPage_getIrrelevantStructuredContentId();

		if ((irrelevantStructuredContentId != null)) {
			Comment irrelevantComment =
				testGetStructuredContentCommentsPage_addComment(
					irrelevantStructuredContentId, randomIrrelevantComment());

			page = commentResource.getStructuredContentCommentsPage(
				irrelevantStructuredContentId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantComment),
				(List<Comment>)page.getItems());
			assertValid(page);
		}

		Comment comment1 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, randomComment());

		Comment comment2 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, randomComment());

		page = commentResource.getStructuredContentCommentsPage(
			structuredContentId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2), (List<Comment>)page.getItems());
		assertValid(page);

		commentResource.deleteComment(comment1.getId());

		commentResource.deleteComment(comment2.getId());
	}

	@Test
	public void testGetStructuredContentCommentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentId =
			testGetStructuredContentCommentsPage_getStructuredContentId();

		Comment comment1 = randomComment();

		comment1 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, comment1);

		for (EntityField entityField : entityFields) {
			Page<Comment> page =
				commentResource.getStructuredContentCommentsPage(
					structuredContentId, null,
					getFilterString(entityField, "between", comment1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentCommentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentId =
			testGetStructuredContentCommentsPage_getStructuredContentId();

		Comment comment1 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, randomComment());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Comment comment2 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, randomComment());

		for (EntityField entityField : entityFields) {
			Page<Comment> page =
				commentResource.getStructuredContentCommentsPage(
					structuredContentId, null,
					getFilterString(entityField, "eq", comment1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(comment1),
				(List<Comment>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentCommentsPageWithPagination()
		throws Exception {

		Long structuredContentId =
			testGetStructuredContentCommentsPage_getStructuredContentId();

		Comment comment1 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, randomComment());

		Comment comment2 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, randomComment());

		Comment comment3 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, randomComment());

		Page<Comment> page1 = commentResource.getStructuredContentCommentsPage(
			structuredContentId, null, null, Pagination.of(1, 2), null);

		List<Comment> comments1 = (List<Comment>)page1.getItems();

		Assert.assertEquals(comments1.toString(), 2, comments1.size());

		Page<Comment> page2 = commentResource.getStructuredContentCommentsPage(
			structuredContentId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Comment> comments2 = (List<Comment>)page2.getItems();

		Assert.assertEquals(comments2.toString(), 1, comments2.size());

		Page<Comment> page3 = commentResource.getStructuredContentCommentsPage(
			structuredContentId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(comment1, comment2, comment3),
			(List<Comment>)page3.getItems());
	}

	@Test
	public void testGetStructuredContentCommentsPageWithSortDateTime()
		throws Exception {

		testGetStructuredContentCommentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(
					comment1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetStructuredContentCommentsPageWithSortInteger()
		throws Exception {

		testGetStructuredContentCommentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, comment1, comment2) -> {
				BeanUtils.setProperty(comment1, entityField.getName(), 0);
				BeanUtils.setProperty(comment2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetStructuredContentCommentsPageWithSortString()
		throws Exception {

		testGetStructuredContentCommentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, comment1, comment2) -> {
				Class<?> clazz = comment1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						comment1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						comment2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetStructuredContentCommentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Comment, Comment, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentId =
			testGetStructuredContentCommentsPage_getStructuredContentId();

		Comment comment1 = randomComment();
		Comment comment2 = randomComment();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, comment1, comment2);
		}

		comment1 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, comment1);

		comment2 = testGetStructuredContentCommentsPage_addComment(
			structuredContentId, comment2);

		for (EntityField entityField : entityFields) {
			Page<Comment> ascPage =
				commentResource.getStructuredContentCommentsPage(
					structuredContentId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(comment1, comment2),
				(List<Comment>)ascPage.getItems());

			Page<Comment> descPage =
				commentResource.getStructuredContentCommentsPage(
					structuredContentId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(comment2, comment1),
				(List<Comment>)descPage.getItems());
		}
	}

	protected Comment testGetStructuredContentCommentsPage_addComment(
			Long structuredContentId, Comment comment)
		throws Exception {

		return commentResource.postStructuredContentComment(
			structuredContentId, comment);
	}

	protected Long testGetStructuredContentCommentsPage_getStructuredContentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentCommentsPage_getIrrelevantStructuredContentId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostStructuredContentComment() throws Exception {
		Comment randomComment = randomComment();

		Comment postComment = testPostStructuredContentComment_addComment(
			randomComment);

		assertEquals(randomComment, postComment);
		assertValid(postComment);
	}

	protected Comment testPostStructuredContentComment_addComment(
			Comment comment)
		throws Exception {

		return commentResource.postStructuredContentComment(
			testGetStructuredContentCommentsPage_getStructuredContentId(),
			comment);
	}

	protected Comment testGraphQLComment_addComment() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Comment comment1, Comment comment2) {
		Assert.assertTrue(
			comment1 + " does not equal " + comment2,
			equals(comment1, comment2));
	}

	protected void assertEquals(
		List<Comment> comments1, List<Comment> comments2) {

		Assert.assertEquals(comments1.size(), comments2.size());

		for (int i = 0; i < comments1.size(); i++) {
			Comment comment1 = comments1.get(i);
			Comment comment2 = comments2.get(i);

			assertEquals(comment1, comment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Comment> comments1, List<Comment> comments2) {

		Assert.assertEquals(comments1.size(), comments2.size());

		for (Comment comment1 : comments1) {
			boolean contains = false;

			for (Comment comment2 : comments2) {
				if (equals(comment1, comment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				comments2 + " does not contain " + comment1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Comment> comments, JSONArray jsonArray) {

		for (Comment comment : comments) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(comment, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + comment, contains);
		}
	}

	protected void assertValid(Comment comment) {
		boolean valid = true;

		if (comment.getDateCreated() == null) {
			valid = false;
		}

		if (comment.getDateModified() == null) {
			valid = false;
		}

		if (comment.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (comment.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (comment.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (comment.getNumberOfComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("parentCommentId", additionalAssertFieldName)) {
				if (comment.getParentCommentId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("text", additionalAssertFieldName)) {
				if (comment.getText() == null) {
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

	protected void assertValid(Page<Comment> page) {
		boolean valid = false;

		java.util.Collection<Comment> comments = page.getItems();

		int size = comments.size();

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

	protected boolean equals(Comment comment1, Comment comment2) {
		if (comment1 == comment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getActions(), comment2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getCreator(), comment2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getDateCreated(), comment2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getDateModified(),
						comment2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(comment1.getId(), comment2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getNumberOfComments(),
						comment2.getNumberOfComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentCommentId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getParentCommentId(),
						comment2.getParentCommentId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("text", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getText(), comment2.getText())) {

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

	protected boolean equalsJSONObject(Comment comment, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						comment.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", fieldName)) {
				if (!Objects.deepEquals(
						comment.getNumberOfComments(),
						jsonObject.getInt("numberOfComments"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentCommentId", fieldName)) {
				if (!Objects.deepEquals(
						comment.getParentCommentId(),
						jsonObject.getLong("parentCommentId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("text", fieldName)) {
				if (!Objects.deepEquals(
						comment.getText(), jsonObject.getString("text"))) {

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

		if (!(_commentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_commentResource;

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
		EntityField entityField, String operator, Comment comment) {

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

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(comment.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(comment.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(comment.getDateCreated()));
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
						DateUtils.addSeconds(comment.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(comment.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(comment.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentCommentId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("text")) {
			sb.append("'");
			sb.append(String.valueOf(comment.getText()));
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

	protected Comment randomComment() throws Exception {
		return new Comment() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				numberOfComments = RandomTestUtil.randomInt();
				parentCommentId = RandomTestUtil.randomLong();
				text = RandomTestUtil.randomString();
			}
		};
	}

	protected Comment randomIrrelevantComment() throws Exception {
		Comment randomIrrelevantComment = randomComment();

		return randomIrrelevantComment;
	}

	protected Comment randomPatchComment() throws Exception {
		return randomComment();
	}

	protected CommentResource commentResource;
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
		BaseCommentResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.CommentResource
		_commentResource;

}