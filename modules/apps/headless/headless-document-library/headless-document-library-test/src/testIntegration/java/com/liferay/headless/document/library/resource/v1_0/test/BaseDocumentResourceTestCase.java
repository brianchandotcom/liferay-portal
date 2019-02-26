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

package com.liferay.headless.document.library.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
public abstract class BaseDocumentResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetContentSpaceDocumentsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFolderDocumentsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPatchDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFolderDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertEquals(Document document1, Document document2) {
		Assert.assertTrue(
			document1 + " does not equal " + document2,
			equals(document1, document2));
	}

	protected void assertEquals(
		List<Document> documents1, List<Document> documents2) {

		Assert.assertEquals(documents1.size(), documents2.size());

		for (int i = 0; i < documents1.size(); i++) {
			Document document1 = documents1.get(i);
			Document document2 = documents2.get(i);

			assertEquals(document1, document2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Document> documents1, List<Document> documents2) {

		Assert.assertEquals(documents1.size(), documents2.size());

		for (Document document1 : documents1) {
			boolean contains = false;

			for (Document document2 : documents2) {
				if (equals(document1, document2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				documents2 + " does not contain " + document1, contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected boolean equals(Document document1, Document document2) {
		if (document1 == document2) {
			return true;
		}

		return false;
	}

	protected boolean invokeDeleteDocument(Long documentId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteDocumentResponse(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Document> invokeGetContentSpaceDocumentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/documents",
					contentSpaceId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Document>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceDocumentsPageResponse(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/documents",
					contentSpaceId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokeGetDocument(Long documentId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokeGetDocumentResponse(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Document> invokeGetFolderDocumentsPage(
			Long folderId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/documents", folderId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Document>>() {
			});
	}

	protected Http.Response invokeGetFolderDocumentsPageResponse(
			Long folderId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/documents", folderId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePatchDocument(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePatchDocumentResponse(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePostContentSpaceDocument(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/documents",
					contentSpaceId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePostContentSpaceDocumentResponse(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/documents",
					contentSpaceId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePostFolderDocument(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/documents", folderId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePostFolderDocumentResponse(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/documents", folderId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePutDocument(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePutDocumentResponse(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document randomDocument() {
		return new Document() {
			{
				contentUrl = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				folderId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
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