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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.Creator;
import com.liferay.headless.foundation.dto.v1_0.ParentCategory;
import com.liferay.headless.foundation.dto.v1_0.ParentVocabulary;
import com.liferay.headless.foundation.internal.dto.v1_0.CategoryImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.ParentCategoryImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.ParentVocabularyImpl;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.Date;

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
public abstract class BaseCategoryResourceTestCase {

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
	public void testDeleteCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetCategoryCategoriesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetVocabularyCategoriesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostCategoryCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostVocabularyCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutCategory() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected boolean invokeDeleteCategory(Long categoryId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/categories/{category-id}", categoryId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteCategoryResponse(Long categoryId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/categories/{category-id}", categoryId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Category invokeGetCategory(Long categoryId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/categories/{category-id}", categoryId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), CategoryImpl.class);
	}

	protected Page<Category> invokeGetCategoryCategoriesPage(
			Long categoryId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetCategoryCategoriesPageResponse(
			Long categoryId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Http.Response invokeGetCategoryResponse(Long categoryId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/categories/{category-id}", categoryId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Category> invokeGetVocabularyCategoriesPage(
			Long vocabularyId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetVocabularyCategoriesPageResponse(
			Long vocabularyId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Category invokePostCategoryCategory(
			Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), CategoryImpl.class);
	}

	protected Http.Response invokePostCategoryCategoryResponse(
			Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Category invokePostVocabularyCategory(
			Long vocabularyId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), CategoryImpl.class);
	}

	protected Http.Response invokePostVocabularyCategoryResponse(
			Long vocabularyId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Category invokePutCategory(Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/categories/{category-id}", categoryId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), CategoryImpl.class);
	}

	protected Http.Response invokePutCategoryResponse(
			Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/categories/{category-id}", categoryId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Category randomCategory() {
		return new CategoryImpl() {
			{

				creatorId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				hasCategories = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				parentVocabularyId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Group testGroup;

	protected static class CategoryImpl implements Category {

		public String[] getAvailableLanguages() {
			return availableLanguages;
		}

		public Creator getCreator() {
			return creator;
		}

		public Long getCreatorId() {
			return creatorId;
		}

		public Date getDateCreated() {
			return dateCreated;
		}

		public Date getDateModified() {
			return dateModified;
		}

		public String getDescription() {
			return description;
		}

		public Boolean getHasCategories() {
			return hasCategories;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public ParentCategory getParentCategory() {
			return parentCategory;
		}

		public ParentVocabulary getParentVocabulary() {
			return parentVocabulary;
		}

		public Long getParentVocabularyId() {
			return parentVocabularyId;
		}

		public void setAvailableLanguages(String[] availableLanguages) {
			this.availableLanguages = (String[])availableLanguages;
		}

		@JsonIgnore
		public void setAvailableLanguages(
			UnsafeSupplier<String[], Throwable>
				availableLanguagesUnsafeSupplier) {

			try {
				availableLanguages =
					(String[])availableLanguagesUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setCreator(Creator creator) {
			this.creator = (CreatorImpl)creator;
		}

		@JsonIgnore
		public void setCreator(
			UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

			try {
				creator = (CreatorImpl)creatorUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setCreatorId(Long creatorId) {
			this.creatorId = (Long)creatorId;
		}

		@JsonIgnore
		public void setCreatorId(
			UnsafeSupplier<Long, Throwable> creatorIdUnsafeSupplier) {

			try {
				creatorId = (Long)creatorIdUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDateCreated(Date dateCreated) {
			this.dateCreated = (Date)dateCreated;
		}

		@JsonIgnore
		public void setDateCreated(
			UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

			try {
				dateCreated = (Date)dateCreatedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDateModified(Date dateModified) {
			this.dateModified = (Date)dateModified;
		}

		@JsonIgnore
		public void setDateModified(
			UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

			try {
				dateModified = (Date)dateModifiedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDescription(String description) {
			this.description = (String)description;
		}

		@JsonIgnore
		public void setDescription(
			UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

			try {
				description = (String)descriptionUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setHasCategories(Boolean hasCategories) {
			this.hasCategories = (Boolean)hasCategories;
		}

		@JsonIgnore
		public void setHasCategories(
			UnsafeSupplier<Boolean, Throwable> hasCategoriesUnsafeSupplier) {

			try {
				hasCategories = (Boolean)hasCategoriesUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setId(Long id) {
			this.id = (Long)id;
		}

		@JsonIgnore
		public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
			try {
				id = (Long)idUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setName(String name) {
			this.name = (String)name;
		}

		@JsonIgnore
		public void setName(
			UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {

			try {
				name = (String)nameUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setParentCategory(ParentCategory parentCategory) {
			this.parentCategory = (ParentCategoryImpl)parentCategory;
		}

		@JsonIgnore
		public void setParentCategory(
			UnsafeSupplier<ParentCategory, Throwable>
				parentCategoryUnsafeSupplier) {

			try {
				parentCategory =
					(ParentCategoryImpl)parentCategoryUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setParentVocabulary(ParentVocabulary parentVocabulary) {
			this.parentVocabulary = (ParentVocabularyImpl)parentVocabulary;
		}

		@JsonIgnore
		public void setParentVocabulary(
			UnsafeSupplier<ParentVocabulary, Throwable>
				parentVocabularyUnsafeSupplier) {

			try {
				parentVocabulary =
					(ParentVocabularyImpl)parentVocabularyUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setParentVocabularyId(Long parentVocabularyId) {
			this.parentVocabularyId = (Long)parentVocabularyId;
		}

		@JsonIgnore
		public void setParentVocabularyId(
			UnsafeSupplier<Long, Throwable> parentVocabularyIdUnsafeSupplier) {

			try {
				parentVocabularyId =
					(Long)parentVocabularyIdUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@JsonProperty
		protected String[] availableLanguages;

		@JsonProperty
		protected CreatorImpl creator;

		@JsonProperty
		protected Long creatorId;

		@JsonProperty
		protected Date dateCreated;

		@JsonProperty
		protected Date dateModified;

		@JsonProperty
		protected String description;

		@JsonProperty
		protected Boolean hasCategories;

		@JsonProperty
		protected Long id;

		@JsonProperty
		protected String name;

		@JsonProperty
		protected ParentCategoryImpl parentCategory;

		@JsonProperty
		protected ParentVocabularyImpl parentVocabulary;

		@JsonProperty
		protected Long parentVocabularyId;

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