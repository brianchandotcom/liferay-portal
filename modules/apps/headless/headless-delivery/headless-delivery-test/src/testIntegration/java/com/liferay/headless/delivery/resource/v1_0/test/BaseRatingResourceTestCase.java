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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.RatingResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
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
public abstract class BaseRatingResourceTestCase {

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
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetBlogPostingRatingsPage() throws Exception {
		Long blogPostingId = testGetBlogPostingRatingsPage_getBlogPostingId();
		Long irrelevantBlogPostingId =
			testGetBlogPostingRatingsPage_getIrrelevantBlogPostingId();

		if ((irrelevantBlogPostingId != null)) {
			Rating irrelevantRating = testGetBlogPostingRatingsPage_addRating(
				irrelevantBlogPostingId, randomIrrelevantRating());

			Page<Rating> page = invokeGetBlogPostingRatingsPage(
				irrelevantBlogPostingId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantRating), (List<Rating>)page.getItems());
			assertValid(page);
		}

		Rating rating1 = testGetBlogPostingRatingsPage_addRating(
			blogPostingId, randomRating());

		Rating rating2 = testGetBlogPostingRatingsPage_addRating(
			blogPostingId, randomRating());

		Page<Rating> page = invokeGetBlogPostingRatingsPage(blogPostingId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(rating1, rating2), (List<Rating>)page.getItems());
		assertValid(page);
	}

	protected Rating testGetBlogPostingRatingsPage_addRating(
			Long blogPostingId, Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetBlogPostingRatingsPage_getBlogPostingId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetBlogPostingRatingsPage_getIrrelevantBlogPostingId()
		throws Exception {

		return null;
	}

	protected Page<Rating> invokeGetBlogPostingRatingsPage(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blog-posting-id}/ratings", blogPostingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Rating>>() {
			});
	}

	protected Http.Response invokeGetBlogPostingRatingsPageResponse(
			Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blog-posting-id}/ratings", blogPostingId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostBlogPostingRating() throws Exception {
		Rating randomRating = randomRating();

		Rating postRating = testPostBlogPostingRating_addRating(randomRating);

		assertEquals(randomRating, postRating);
		assertValid(postRating);
	}

	protected Rating testPostBlogPostingRating_addRating(Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokePostBlogPostingRating(
			Long blogPostingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blog-posting-id}/ratings", blogPostingId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostBlogPostingRatingResponse(
			Long blogPostingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blog-posting-id}/ratings", blogPostingId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDocumentRatingsPage() throws Exception {
		Long documentId = testGetDocumentRatingsPage_getDocumentId();
		Long irrelevantDocumentId =
			testGetDocumentRatingsPage_getIrrelevantDocumentId();

		if ((irrelevantDocumentId != null)) {
			Rating irrelevantRating = testGetDocumentRatingsPage_addRating(
				irrelevantDocumentId, randomIrrelevantRating());

			Page<Rating> page = invokeGetDocumentRatingsPage(
				irrelevantDocumentId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantRating), (List<Rating>)page.getItems());
			assertValid(page);
		}

		Rating rating1 = testGetDocumentRatingsPage_addRating(
			documentId, randomRating());

		Rating rating2 = testGetDocumentRatingsPage_addRating(
			documentId, randomRating());

		Page<Rating> page = invokeGetDocumentRatingsPage(documentId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(rating1, rating2), (List<Rating>)page.getItems());
		assertValid(page);
	}

	protected Rating testGetDocumentRatingsPage_addRating(
			Long documentId, Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDocumentRatingsPage_getDocumentId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDocumentRatingsPage_getIrrelevantDocumentId()
		throws Exception {

		return null;
	}

	protected Page<Rating> invokeGetDocumentRatingsPage(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{document-id}/ratings", documentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Rating>>() {
			});
	}

	protected Http.Response invokeGetDocumentRatingsPageResponse(
			Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{document-id}/ratings", documentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostDocumentRating() throws Exception {
		Rating randomRating = randomRating();

		Rating postRating = testPostDocumentRating_addRating(randomRating);

		assertEquals(randomRating, postRating);
		assertValid(postRating);
	}

	protected Rating testPostDocumentRating_addRating(Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokePostDocumentRating(Long documentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/documents/{document-id}/ratings", documentId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostDocumentRatingResponse(
			Long documentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/documents/{document-id}/ratings", documentId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKnowledgeBaseArticleRatingsPage() throws Exception {
		Long knowledgeBaseArticleId =
			testGetKnowledgeBaseArticleRatingsPage_getKnowledgeBaseArticleId();
		Long irrelevantKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleRatingsPage_getIrrelevantKnowledgeBaseArticleId();

		if ((irrelevantKnowledgeBaseArticleId != null)) {
			Rating irrelevantRating =
				testGetKnowledgeBaseArticleRatingsPage_addRating(
					irrelevantKnowledgeBaseArticleId, randomIrrelevantRating());

			Page<Rating> page = invokeGetKnowledgeBaseArticleRatingsPage(
				irrelevantKnowledgeBaseArticleId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantRating), (List<Rating>)page.getItems());
			assertValid(page);
		}

		Rating rating1 = testGetKnowledgeBaseArticleRatingsPage_addRating(
			knowledgeBaseArticleId, randomRating());

		Rating rating2 = testGetKnowledgeBaseArticleRatingsPage_addRating(
			knowledgeBaseArticleId, randomRating());

		Page<Rating> page = invokeGetKnowledgeBaseArticleRatingsPage(
			knowledgeBaseArticleId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(rating1, rating2), (List<Rating>)page.getItems());
		assertValid(page);
	}

	protected Rating testGetKnowledgeBaseArticleRatingsPage_addRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseArticleRatingsPage_getKnowledgeBaseArticleId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseArticleRatingsPage_getIrrelevantKnowledgeBaseArticleId()
		throws Exception {

		return null;
	}

	protected Page<Rating> invokeGetKnowledgeBaseArticleRatingsPage(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledge-base-article-id}/ratings",
					knowledgeBaseArticleId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Rating>>() {
			});
	}

	protected Http.Response invokeGetKnowledgeBaseArticleRatingsPageResponse(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledge-base-article-id}/ratings",
					knowledgeBaseArticleId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostKnowledgeBaseArticleRating() throws Exception {
		Rating randomRating = randomRating();

		Rating postRating = testPostKnowledgeBaseArticleRating_addRating(
			randomRating);

		assertEquals(randomRating, postRating);
		assertValid(postRating);
	}

	protected Rating testPostKnowledgeBaseArticleRating_addRating(Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokePostKnowledgeBaseArticleRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledge-base-article-id}/ratings",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostKnowledgeBaseArticleRatingResponse(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledge-base-article-id}/ratings",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetMessageBoardMessageRatingsPage() throws Exception {
		Long messageBoardMessageId =
			testGetMessageBoardMessageRatingsPage_getMessageBoardMessageId();
		Long irrelevantMessageBoardMessageId =
			testGetMessageBoardMessageRatingsPage_getIrrelevantMessageBoardMessageId();

		if ((irrelevantMessageBoardMessageId != null)) {
			Rating irrelevantRating =
				testGetMessageBoardMessageRatingsPage_addRating(
					irrelevantMessageBoardMessageId, randomIrrelevantRating());

			Page<Rating> page = invokeGetMessageBoardMessageRatingsPage(
				irrelevantMessageBoardMessageId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantRating), (List<Rating>)page.getItems());
			assertValid(page);
		}

		Rating rating1 = testGetMessageBoardMessageRatingsPage_addRating(
			messageBoardMessageId, randomRating());

		Rating rating2 = testGetMessageBoardMessageRatingsPage_addRating(
			messageBoardMessageId, randomRating());

		Page<Rating> page = invokeGetMessageBoardMessageRatingsPage(
			messageBoardMessageId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(rating1, rating2), (List<Rating>)page.getItems());
		assertValid(page);
	}

	protected Rating testGetMessageBoardMessageRatingsPage_addRating(
			Long messageBoardMessageId, Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardMessageRatingsPage_getMessageBoardMessageId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardMessageRatingsPage_getIrrelevantMessageBoardMessageId()
		throws Exception {

		return null;
	}

	protected Page<Rating> invokeGetMessageBoardMessageRatingsPage(
			Long messageBoardMessageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-messages/{message-board-message-id}/ratings",
					messageBoardMessageId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Rating>>() {
			});
	}

	protected Http.Response invokeGetMessageBoardMessageRatingsPageResponse(
			Long messageBoardMessageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-messages/{message-board-message-id}/ratings",
					messageBoardMessageId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostMessageBoardMessageRating() throws Exception {
		Rating randomRating = randomRating();

		Rating postRating = testPostMessageBoardMessageRating_addRating(
			randomRating);

		assertEquals(randomRating, postRating);
		assertValid(postRating);
	}

	protected Rating testPostMessageBoardMessageRating_addRating(Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokePostMessageBoardMessageRating(
			Long messageBoardMessageId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-messages/{message-board-message-id}/ratings",
					messageBoardMessageId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostMessageBoardMessageRatingResponse(
			Long messageBoardMessageId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-messages/{message-board-message-id}/ratings",
					messageBoardMessageId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetMessageBoardThreadRatingsPage() throws Exception {
		Long messageBoardThreadId =
			testGetMessageBoardThreadRatingsPage_getMessageBoardThreadId();
		Long irrelevantMessageBoardThreadId =
			testGetMessageBoardThreadRatingsPage_getIrrelevantMessageBoardThreadId();

		if ((irrelevantMessageBoardThreadId != null)) {
			Rating irrelevantRating =
				testGetMessageBoardThreadRatingsPage_addRating(
					irrelevantMessageBoardThreadId, randomIrrelevantRating());

			Page<Rating> page = invokeGetMessageBoardThreadRatingsPage(
				irrelevantMessageBoardThreadId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantRating), (List<Rating>)page.getItems());
			assertValid(page);
		}

		Rating rating1 = testGetMessageBoardThreadRatingsPage_addRating(
			messageBoardThreadId, randomRating());

		Rating rating2 = testGetMessageBoardThreadRatingsPage_addRating(
			messageBoardThreadId, randomRating());

		Page<Rating> page = invokeGetMessageBoardThreadRatingsPage(
			messageBoardThreadId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(rating1, rating2), (List<Rating>)page.getItems());
		assertValid(page);
	}

	protected Rating testGetMessageBoardThreadRatingsPage_addRating(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardThreadRatingsPage_getMessageBoardThreadId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetMessageBoardThreadRatingsPage_getIrrelevantMessageBoardThreadId()
		throws Exception {

		return null;
	}

	protected Page<Rating> invokeGetMessageBoardThreadRatingsPage(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{message-board-thread-id}/ratings",
					messageBoardThreadId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Rating>>() {
			});
	}

	protected Http.Response invokeGetMessageBoardThreadRatingsPageResponse(
			Long messageBoardThreadId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{message-board-thread-id}/ratings",
					messageBoardThreadId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostMessageBoardThreadRating() throws Exception {
		Rating randomRating = randomRating();

		Rating postRating = testPostMessageBoardThreadRating_addRating(
			randomRating);

		assertEquals(randomRating, postRating);
		assertValid(postRating);
	}

	protected Rating testPostMessageBoardThreadRating_addRating(Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokePostMessageBoardThreadRating(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{message-board-thread-id}/ratings",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostMessageBoardThreadRatingResponse(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/message-board-threads/{message-board-thread-id}/ratings",
					messageBoardThreadId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteRating() throws Exception {
		Rating rating = testDeleteRating_addRating();

		assertResponseCode(204, invokeDeleteRatingResponse(rating.getId()));

		assertResponseCode(404, invokeGetRatingResponse(rating.getId()));
	}

	protected Rating testDeleteRating_addRating() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteRating(Long ratingId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/ratings/{rating-id}", ratingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteRatingResponse(Long ratingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/ratings/{rating-id}", ratingId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetRating() throws Exception {
		Rating postRating = testGetRating_addRating();

		Rating getRating = invokeGetRating(postRating.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetRating_addRating() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokeGetRating(Long ratingId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/ratings/{rating-id}", ratingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetRatingResponse(Long ratingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/ratings/{rating-id}", ratingId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutRating() throws Exception {
		Rating postRating = testPutRating_addRating();

		Rating randomRating = randomRating();

		Rating putRating = invokePutRating(postRating.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);

		Rating getRating = invokeGetRating(putRating.getId());

		assertEquals(randomRating, getRating);
		assertValid(getRating);
	}

	protected Rating testPutRating_addRating() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokePutRating(Long ratingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/ratings/{rating-id}", ratingId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutRatingResponse(
			Long ratingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/ratings/{rating-id}", ratingId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetStructuredContentRatingsPage() throws Exception {
		Long structuredContentId =
			testGetStructuredContentRatingsPage_getStructuredContentId();
		Long irrelevantStructuredContentId =
			testGetStructuredContentRatingsPage_getIrrelevantStructuredContentId();

		if ((irrelevantStructuredContentId != null)) {
			Rating irrelevantRating =
				testGetStructuredContentRatingsPage_addRating(
					irrelevantStructuredContentId, randomIrrelevantRating());

			Page<Rating> page = invokeGetStructuredContentRatingsPage(
				irrelevantStructuredContentId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantRating), (List<Rating>)page.getItems());
			assertValid(page);
		}

		Rating rating1 = testGetStructuredContentRatingsPage_addRating(
			structuredContentId, randomRating());

		Rating rating2 = testGetStructuredContentRatingsPage_addRating(
			structuredContentId, randomRating());

		Page<Rating> page = invokeGetStructuredContentRatingsPage(
			structuredContentId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(rating1, rating2), (List<Rating>)page.getItems());
		assertValid(page);
	}

	protected Rating testGetStructuredContentRatingsPage_addRating(
			Long structuredContentId, Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetStructuredContentRatingsPage_getStructuredContentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentRatingsPage_getIrrelevantStructuredContentId()
		throws Exception {

		return null;
	}

	protected Page<Rating> invokeGetStructuredContentRatingsPage(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/ratings",
					structuredContentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Rating>>() {
			});
	}

	protected Http.Response invokeGetStructuredContentRatingsPageResponse(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/ratings",
					structuredContentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostStructuredContentRating() throws Exception {
		Rating randomRating = randomRating();

		Rating postRating = testPostStructuredContentRating_addRating(
			randomRating);

		assertEquals(randomRating, postRating);
		assertValid(postRating);
	}

	protected Rating testPostStructuredContentRating_addRating(Rating rating)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Rating invokePostStructuredContentRating(
			Long structuredContentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/ratings",
					structuredContentId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostStructuredContentRatingResponse(
			Long structuredContentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(rating),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/ratings",
					structuredContentId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
	}

	protected void assertEquals(List<Rating> ratings1, List<Rating> ratings2) {
		Assert.assertEquals(ratings1.size(), ratings2.size());

		for (int i = 0; i < ratings1.size(); i++) {
			Rating rating1 = ratings1.get(i);
			Rating rating2 = ratings2.get(i);

			assertEquals(rating1, rating2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Rating> ratings1, List<Rating> ratings2) {

		Assert.assertEquals(ratings1.size(), ratings2.size());

		for (Rating rating1 : ratings1) {
			boolean contains = false;

			for (Rating rating2 : ratings2) {
				if (equals(rating1, rating2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				ratings2 + " does not contain " + rating1, contains);
		}
	}

	protected void assertValid(Rating rating) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Rating> page) {
		boolean valid = false;

		Collection<Rating> ratings = page.getItems();

		int size = ratings.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(Rating rating1, Rating rating2) {
		if (rating1 == rating2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_ratingResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_ratingResource;

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
		EntityField entityField, String operator, Rating rating) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("bestRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(rating.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(rating.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("ratingValue")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("worstRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Rating randomRating() {
		return new Rating() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected Rating randomIrrelevantRating() {
		return randomRating();
	}

	protected Rating randomPatchRating() {
		return randomRating();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;

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

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

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
		BaseRatingResourceTestCase.class);

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
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
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
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
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

	@Inject
	private RatingResource _ratingResource;

	private URL _resourceURL;

}