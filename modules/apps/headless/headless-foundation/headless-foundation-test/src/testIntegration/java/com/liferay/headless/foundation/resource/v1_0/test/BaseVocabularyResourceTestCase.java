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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.liferay.headless.foundation.dto.v1_0.Creator;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/vocabularies",
					contentSpaceId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<VocabularyImpl>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceVocabulariesPageResponse(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/vocabularies",
					contentSpaceId));

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
			HttpUtil.URLtoString(options), VocabularyImpl.class);
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
			HttpUtil.URLtoString(options), VocabularyImpl.class);
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
			HttpUtil.URLtoString(options), VocabularyImpl.class);
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
		return new VocabularyImpl() {
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

	protected static class CreatorImpl implements Creator {

		public String getAdditionalName() {
				return additionalName;
		}

		public void setAdditionalName(
				String additionalName) {

				this.additionalName = additionalName;
		}

		@JsonIgnore
		public void setAdditionalName(
				UnsafeSupplier<String, Throwable>
					additionalNameUnsafeSupplier) {

				try {
					additionalName =
						additionalNameUnsafeSupplier.get();
		}
				catch (Throwable t) {
					throw new RuntimeException(t);
		}
		}

		@JsonProperty
		protected String additionalName;

		public String getFamilyName() {
				return familyName;
		}

		public void setFamilyName(
				String familyName) {

				this.familyName = familyName;
		}

		@JsonIgnore
		public void setFamilyName(
				UnsafeSupplier<String, Throwable>
					familyNameUnsafeSupplier) {

				try {
					familyName =
						familyNameUnsafeSupplier.get();
		}
				catch (Throwable t) {
					throw new RuntimeException(t);
		}
		}

		@JsonProperty
		protected String familyName;

		public String getGivenName() {
				return givenName;
		}

		public void setGivenName(
				String givenName) {

				this.givenName = givenName;
		}

		@JsonIgnore
		public void setGivenName(
				UnsafeSupplier<String, Throwable>
					givenNameUnsafeSupplier) {

				try {
					givenName =
						givenNameUnsafeSupplier.get();
		}
				catch (Throwable t) {
					throw new RuntimeException(t);
		}
		}

		@JsonProperty
		protected String givenName;

		public Long getId() {
				return id;
		}

		public void setId(
				Long id) {

				this.id = id;
		}

		@JsonIgnore
		public void setId(
				UnsafeSupplier<Long, Throwable>
					idUnsafeSupplier) {

				try {
					id =
						idUnsafeSupplier.get();
		}
				catch (Throwable t) {
					throw new RuntimeException(t);
		}
		}

		@JsonProperty
		protected Long id;

		public String getImage() {
				return image;
		}

		public void setImage(
				String image) {

				this.image = image;
		}

		@JsonIgnore
		public void setImage(
				UnsafeSupplier<String, Throwable>
					imageUnsafeSupplier) {

				try {
					image =
						imageUnsafeSupplier.get();
		}
				catch (Throwable t) {
					throw new RuntimeException(t);
		}
		}

		@JsonProperty
		protected String image;

		public String getName() {
				return name;
		}

		public void setName(
				String name) {

				this.name = name;
		}

		@JsonIgnore
		public void setName(
				UnsafeSupplier<String, Throwable>
					nameUnsafeSupplier) {

				try {
					name =
						nameUnsafeSupplier.get();
		}
				catch (Throwable t) {
					throw new RuntimeException(t);
		}
		}

		@JsonProperty
		protected String name;

		public String getProfileURL() {
				return profileURL;
		}

		public void setProfileURL(
				String profileURL) {

				this.profileURL = profileURL;
		}

		@JsonIgnore
		public void setProfileURL(
				UnsafeSupplier<String, Throwable>
					profileURLUnsafeSupplier) {

				try {
					profileURL =
						profileURLUnsafeSupplier.get();
		}
				catch (Throwable t) {
					throw new RuntimeException(t);
		}
		}

		@JsonProperty
		protected String profileURL;
	}

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

	protected static class VocabularyImpl implements Vocabulary {

		public String[] getAvailableLanguages() {
			return availableLanguages;
		}

		public Long getContentSpace() {
			return contentSpace;
		}

		public Creator getCreator() {
			return creator;
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

		public void setAvailableLanguages(String[] availableLanguages) {
			this.availableLanguages = availableLanguages;
		}

		@JsonIgnore
		public void setAvailableLanguages(
			UnsafeSupplier<String[], Throwable>
				availableLanguagesUnsafeSupplier) {

			try {
				availableLanguages = availableLanguagesUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setContentSpace(Long contentSpace) {
			this.contentSpace = contentSpace;
		}

		@JsonIgnore
		public void setContentSpace(
			UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier) {

			try {
				contentSpace = contentSpaceUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setCreator(Creator creator) {
			this.creator = creator;
		}

		@JsonIgnore
		public void setCreator(
			UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

			try {
				creator = creatorUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
		}

		@JsonIgnore
		public void setDateCreated(
			UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

			try {
				dateCreated = dateCreatedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDateModified(Date dateModified) {
			this.dateModified = dateModified;
		}

		@JsonIgnore
		public void setDateModified(
			UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

			try {
				dateModified = dateModifiedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@JsonIgnore
		public void setDescription(
			UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

			try {
				description = descriptionUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setHasCategories(Boolean hasCategories) {
			this.hasCategories = hasCategories;
		}

		@JsonIgnore
		public void setHasCategories(
			UnsafeSupplier<Boolean, Throwable> hasCategoriesUnsafeSupplier) {

			try {
				hasCategories = hasCategoriesUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setId(Long id) {
			this.id = id;
		}

		@JsonIgnore
		public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
			try {
				id = idUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setName(String name) {
			this.name = name;
		}

		@JsonIgnore
		public void setName(
			UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {

			try {
				name = nameUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public String toString() {
			StringBundler sb = new StringBundler(20);

			sb.append("{");

			sb.append("availableLanguages=");

			sb.append(availableLanguages);
			sb.append(", contentSpace=");

			sb.append(contentSpace);
			sb.append(", creator=");

			sb.append(creator);
			sb.append(", dateCreated=");

			sb.append(dateCreated);
			sb.append(", dateModified=");

			sb.append(dateModified);
			sb.append(", description=");

			sb.append(description);
			sb.append(", hasCategories=");

			sb.append(hasCategories);
			sb.append(", id=");

			sb.append(id);
			sb.append(", name=");

			sb.append(name);

			sb.append("}");

			return sb.toString();
		}

		@JsonProperty
		protected String[] availableLanguages;

		@JsonProperty
		protected Long contentSpace;

		@JsonDeserialize(as = CreatorImpl.class)
		protected Creator creator;

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