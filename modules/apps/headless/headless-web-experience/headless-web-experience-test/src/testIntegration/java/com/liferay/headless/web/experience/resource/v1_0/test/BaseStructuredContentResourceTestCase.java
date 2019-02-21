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

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.web.experience.dto.v1_0.AggregateRating;
import com.liferay.headless.web.experience.dto.v1_0.Categories;
import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.dto.v1_0.Creator;
import com.liferay.headless.web.experience.dto.v1_0.Options;
import com.liferay.headless.web.experience.dto.v1_0.RenderedContentsURL;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.Values;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
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
public abstract class BaseStructuredContentResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-web-experience/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceContentStructureStructuredContentsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetContentSpaceStructuredContentsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPatchStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetStructuredContentTemplate() throws Exception {
			Assert.assertTrue(true);
	}

	protected void assertResponseCode(int expectedResponseCode, Http.Response actualResponse) {
		Assert.assertEquals(expectedResponseCode, actualResponse.getResponseCode());
	}

	protected Page<StructuredContent> invokeGetContentSpaceContentStructureStructuredContentsPage(
				Long contentSpaceId,Long contentStructureId,Filter filter,Pagination pagination,Sort[] sorts)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/content-structures/{content-structure-id}/structured-contents", contentSpaceId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetContentSpaceContentStructureStructuredContentsPageResponse(
				Long contentSpaceId,Long contentStructureId,Filter filter,Pagination pagination,Sort[] sorts)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/content-structures/{content-structure-id}/structured-contents", contentSpaceId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected Page<StructuredContent> invokeGetContentSpaceStructuredContentsPage(
				Long contentSpaceId,Filter filter,Pagination pagination,Sort[] sorts)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/structured-contents", contentSpaceId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetContentSpaceStructuredContentsPageResponse(
				Long contentSpaceId,Filter filter,Pagination pagination,Sort[] sorts)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/structured-contents", contentSpaceId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected StructuredContent invokePostContentSpaceStructuredContent(
				Long contentSpaceId,StructuredContent structuredContent)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(structuredContent), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/structured-contents", contentSpaceId));

				options.setPost(true);

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), StructuredContentImpl.class);
	}

	protected Http.Response invokePostContentSpaceStructuredContentResponse(
				Long contentSpaceId,StructuredContent structuredContent)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(structuredContent), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/structured-contents", contentSpaceId));

				options.setPost(true);

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected boolean invokeDeleteStructuredContent(
				Long structuredContentId)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setDelete(true);

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteStructuredContentResponse(
				Long structuredContentId)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setDelete(true);

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected StructuredContent invokeGetStructuredContent(
				Long structuredContentId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), StructuredContentImpl.class);
	}

	protected Http.Response invokeGetStructuredContentResponse(
				Long structuredContentId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected StructuredContent invokePatchStructuredContent(
				Long structuredContentId,StructuredContent structuredContent)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), StructuredContentImpl.class);
	}

	protected Http.Response invokePatchStructuredContentResponse(
				Long structuredContentId,StructuredContent structuredContent)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected StructuredContent invokePutStructuredContent(
				Long structuredContentId,StructuredContent structuredContent)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(structuredContent), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

				options.setPut(true);

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), StructuredContentImpl.class);
	}

	protected Http.Response invokePutStructuredContentResponse(
				Long structuredContentId,StructuredContent structuredContent)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(structuredContent), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}", structuredContentId));

				options.setPut(true);

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected String invokeGetStructuredContentTemplate(
				Long structuredContentId,Long templateId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}/rendered-content/{template-id}", structuredContentId));

				return HttpUtil.URLtoString(options);
	}

	protected Http.Response invokeGetStructuredContentTemplateResponse(
				Long structuredContentId,Long templateId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/structured-contents/{structured-content-id}/rendered-content/{template-id}", structuredContentId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}

	protected StructuredContent randomStructuredContent() {
		return new StructuredContentImpl() {
			{

						contentSpace = RandomTestUtil.randomLong();
						contentStructureId = RandomTestUtil.randomLong();
						dateCreated = RandomTestUtil.nextDate();
						dateModified = RandomTestUtil.nextDate();
						datePublished = RandomTestUtil.nextDate();
						description = RandomTestUtil.randomString();
						id = RandomTestUtil.randomLong();
						lastReviewed = RandomTestUtil.nextDate();
						title = RandomTestUtil.randomString();
	}
		};
	}

	protected Group testGroup;

	protected static class StructuredContentImpl implements StructuredContent {

	public AggregateRating getAggregateRating() {
				return aggregateRating;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
				this.aggregateRating = aggregateRating;
	}

	@JsonIgnore
	public void setAggregateRating(
				UnsafeSupplier<AggregateRating, Throwable> aggregateRatingUnsafeSupplier) {

				try {
					aggregateRating = aggregateRatingUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected AggregateRating aggregateRating;
	public String[] getAvailableLanguages() {
				return availableLanguages;
	}

	public void setAvailableLanguages(String[] availableLanguages) {
				this.availableLanguages = availableLanguages;
	}

	@JsonIgnore
	public void setAvailableLanguages(
				UnsafeSupplier<String[], Throwable> availableLanguagesUnsafeSupplier) {

				try {
					availableLanguages = availableLanguagesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String[] availableLanguages;
	public Categories[] getCategories() {
				return categories;
	}

	public void setCategories(Categories[] categories) {
				this.categories = categories;
	}

	@JsonIgnore
	public void setCategories(
				UnsafeSupplier<Categories[], Throwable> categoriesUnsafeSupplier) {

				try {
					categories = categoriesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Categories[] categories;
	public Comment[] getComment() {
				return comment;
	}

	public void setComment(Comment[] comment) {
				this.comment = comment;
	}

	@JsonIgnore
	public void setComment(
				UnsafeSupplier<Comment[], Throwable> commentUnsafeSupplier) {

				try {
					comment = commentUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Comment[] comment;
	public Long getContentSpace() {
				return contentSpace;
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

	@JsonProperty
	protected Long contentSpace;
	public Long getContentStructureId() {
				return contentStructureId;
	}

	public void setContentStructureId(Long contentStructureId) {
				this.contentStructureId = contentStructureId;
	}

	@JsonIgnore
	public void setContentStructureId(
				UnsafeSupplier<Long, Throwable> contentStructureIdUnsafeSupplier) {

				try {
					contentStructureId = contentStructureIdUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long contentStructureId;
	public Creator getCreator() {
				return creator;
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

	@JsonProperty
	protected Creator creator;
	public Date getDateCreated() {
				return dateCreated;
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

	@JsonProperty
	protected Date dateCreated;
	public Date getDateModified() {
				return dateModified;
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

	@JsonProperty
	protected Date dateModified;
	public Date getDatePublished() {
				return datePublished;
	}

	public void setDatePublished(Date datePublished) {
				this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
				UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier) {

				try {
					datePublished = datePublishedUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Date datePublished;
	public String getDescription() {
				return description;
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

	@JsonProperty
	protected String description;
	public Long getId() {
				return id;
	}

	public void setId(Long id) {
				this.id = id;
	}

	@JsonIgnore
	public void setId(
				UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {

				try {
					id = idUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long id;
	public String[] getKeywords() {
				return keywords;
	}

	public void setKeywords(String[] keywords) {
				this.keywords = keywords;
	}

	@JsonIgnore
	public void setKeywords(
				UnsafeSupplier<String[], Throwable> keywordsUnsafeSupplier) {

				try {
					keywords = keywordsUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String[] keywords;
	public Date getLastReviewed() {
				return lastReviewed;
	}

	public void setLastReviewed(Date lastReviewed) {
				this.lastReviewed = lastReviewed;
	}

	@JsonIgnore
	public void setLastReviewed(
				UnsafeSupplier<Date, Throwable> lastReviewedUnsafeSupplier) {

				try {
					lastReviewed = lastReviewedUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Date lastReviewed;
	public RenderedContentsURL[] getRenderedContentsURL() {
				return renderedContentsURL;
	}

	public void setRenderedContentsURL(RenderedContentsURL[] renderedContentsURL) {
				this.renderedContentsURL = renderedContentsURL;
	}

	@JsonIgnore
	public void setRenderedContentsURL(
				UnsafeSupplier<RenderedContentsURL[], Throwable> renderedContentsURLUnsafeSupplier) {

				try {
					renderedContentsURL = renderedContentsURLUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected RenderedContentsURL[] renderedContentsURL;
	public String getTitle() {
				return title;
	}

	public void setTitle(String title) {
				this.title = title;
	}

	@JsonIgnore
	public void setTitle(
				UnsafeSupplier<String, Throwable> titleUnsafeSupplier) {

				try {
					title = titleUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String title;
	public Values[] getValues() {
				return values;
	}

	public void setValues(Values[] values) {
				this.values = values;
	}

	@JsonIgnore
	public void setValues(
				UnsafeSupplier<Values[], Throwable> valuesUnsafeSupplier) {

				try {
					values = valuesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Values[] values;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(userNameAndPassword.getBytes());

		options.addHeader("Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}