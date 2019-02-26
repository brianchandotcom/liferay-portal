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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseVocabularyResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteVocabulary() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetContentSpaceVocabulariesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetVocabulary() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceVocabulary() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutVocabulary() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertEquals(
		List<Vocabulary> vocabularies1, List<Vocabulary> vocabularies2) {

		Assert.assertEquals(vocabularies1.size(), vocabularies2.size());

		for (int i = 0; i < vocabularies1.size(); i++) {
			Vocabulary vocabulary1 = vocabularies1.get(i);
			Vocabulary vocabulary2 = vocabularies2.get(i);

			assertEquals(vocabulary1, vocabulary2);
		}
	}

	protected void assertEquals(
		Vocabulary vocabulary1, Vocabulary vocabulary2) {

		Assert.assertTrue(
			vocabulary1 + " does not equal " + vocabulary2,
			equals(vocabulary1, vocabulary2));
	}

	protected void assertEqualsIgnoringOrder(
		List<Vocabulary> vocabularies1, List<Vocabulary> vocabularies2) {

		Assert.assertEquals(vocabularies1.size(), vocabularies2.size());

		for (Vocabulary vocabulary1 : vocabularies1) {
			boolean contains = false;

			for (Vocabulary vocabulary2 : vocabularies2) {
				if (equals(vocabulary1, vocabulary2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				vocabularies2 + " does not contain " + vocabulary1, contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected boolean equals(Vocabulary vocabulary1, Vocabulary vocabulary2) {
		if (vocabulary1 == vocabulary2) {
			return true;
		}

		return false;
	}

	protected boolean invokeDeleteVocabulary(Long vocabularyId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath("/vocabularies/{vocabulary-id}", vocabularyId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteVocabularyResponse(Long vocabularyId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath("/vocabularies/{vocabulary-id}", vocabularyId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Vocabulary> invokeGetContentSpaceVocabulariesPage(
		Long contentSpaceId, String filter, Pagination pagination, String sort)
			throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceVocabulariesLocation(
				contentSpaceId, filter, pagination, sort));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Vocabulary>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceVocabulariesPageResponse(
		Long contentSpaceId, String filter, Pagination pagination, String sort)
			throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceVocabulariesLocation(
				contentSpaceId, filter, pagination, sort));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Vocabulary invokeGetVocabulary(Long vocabularyId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/vocabularies/{vocabulary-id}", vocabularyId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Vocabulary.class);
	}

	protected Http.Response invokeGetVocabularyResponse(Long vocabularyId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/vocabularies/{vocabulary-id}", vocabularyId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Vocabulary invokePostContentSpaceVocabulary(
			Long contentSpaceId, Vocabulary vocabulary)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(vocabulary),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/vocabularies",
					contentSpaceId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Vocabulary.class);
	}

	protected Http.Response invokePostContentSpaceVocabularyResponse(
			Long contentSpaceId, Vocabulary vocabulary)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(vocabulary),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/vocabularies",
					contentSpaceId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Vocabulary invokePutVocabulary(
			Long vocabularyId, Vocabulary vocabulary)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(vocabulary),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/vocabularies/{vocabulary-id}", vocabularyId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Vocabulary.class);
	}

	protected Http.Response invokePutVocabularyResponse(
			Long vocabularyId, Vocabulary vocabulary)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(vocabulary),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/vocabularies/{vocabulary-id}", vocabularyId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Vocabulary randomVocabulary() {
		return new Vocabulary() {
			{
				contentSpace = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				hasCategories = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public int getItemsPerPage() {
			return itemsPerPage;
		}

		public int getLastPageNumber() {
			return lastPageNumber;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public int getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected int itemsPerPage;

		@JsonProperty
		protected int lastPageNumber;

		@JsonProperty("page")
		protected int pageNumber;

		@JsonProperty
		protected int totalCount;

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

	private String _getContentSpaceVocabulariesLocation(
		Long contentSpaceId, String filter, Pagination pagination,
		String sort) {

		Http http = new HttpImpl();

		String url = _resourceURL + _toPath(
			"/content-spaces/{content-space-id}/vocabularies", contentSpaceId);

		url = http.addParameter(url, "filter", filter);
		url = http.addParameter(url, "page", pagination.getPageNumber());
		url = http.addParameter(url, "pageSize", pagination.getItemsPerPage());
		url = http.addParameter(url, "sort", sort);

		return url;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}