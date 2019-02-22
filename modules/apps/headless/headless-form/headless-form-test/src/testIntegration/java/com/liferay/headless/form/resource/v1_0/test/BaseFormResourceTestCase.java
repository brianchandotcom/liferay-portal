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

package com.liferay.headless.form.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.Options;
import com.liferay.headless.form.internal.dto.v1_0.CreatorImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormRecordImpl;
import com.liferay.headless.form.internal.dto.v1_0.FormStructureImpl;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
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
public abstract class BaseFormResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-form/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceFormsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetForm() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFormFetchLatestDraft() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFormEvaluateContext() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFormUploadFile() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected Page<Form> invokeGetContentSpaceFormsPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/form", contentSpaceId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Page.class);
	}

	protected Http.Response invokeGetContentSpaceFormsPageResponse(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/form", contentSpaceId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Form invokeGetForm(Long formId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(_resourceURL + _toPath("/forms/{form-id}", formId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), FormImpl.class);
	}

	protected Form invokeGetFormFetchLatestDraft(Long formId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/forms/{form-id}/fetch-latest-draft", formId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), FormImpl.class);
	}

	protected Http.Response invokeGetFormFetchLatestDraftResponse(Long formId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/forms/{form-id}/fetch-latest-draft", formId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Http.Response invokeGetFormResponse(Long formId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(_resourceURL + _toPath("/forms/{form-id}", formId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Form invokePostFormEvaluateContext(Long formId, Form form)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(form),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/forms/{form-id}/evaluate-context", formId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), FormImpl.class);
	}

	protected Http.Response invokePostFormEvaluateContextResponse(
			Long formId, Form form)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(form),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath("/forms/{form-id}/evaluate-context", formId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Form invokePostFormUploadFile(Long formId, Form form)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(form),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/forms/{form-id}/upload-file", formId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), FormImpl.class);
	}

	protected Http.Response invokePostFormUploadFileResponse(
			Long formId, Form form)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(form),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/forms/{form-id}/upload-file", formId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Form randomForm() {
		return new FormImpl() {
			{

				contentSpace = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				defaultLanguage = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				structureId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Group testGroup;

	protected static class FormImpl implements Form {

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

		public Date getDatePublished() {
			return datePublished;
		}

		public String getDefaultLanguage() {
			return defaultLanguage;
		}

		public String getDescription() {
			return description;
		}

		public FormRecord[] getFormRecords() {
			return formRecords;
		}

		public Long[] getFormRecordsIds() {
			return formRecordsIds;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public FormStructure getStructure() {
			return structure;
		}

		public Long getStructureId() {
			return structureId;
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

		public void setContentSpace(Long contentSpace) {
			this.contentSpace = (Long)contentSpace;
		}

		@JsonIgnore
		public void setContentSpace(
			UnsafeSupplier<Long, Throwable> contentSpaceUnsafeSupplier) {

			try {
				contentSpace = (Long)contentSpaceUnsafeSupplier.get();
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

		public void setDatePublished(Date datePublished) {
			this.datePublished = (Date)datePublished;
		}

		@JsonIgnore
		public void setDatePublished(
			UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier) {

			try {
				datePublished = (Date)datePublishedUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setDefaultLanguage(String defaultLanguage) {
			this.defaultLanguage = (String)defaultLanguage;
		}

		@JsonIgnore
		public void setDefaultLanguage(
			UnsafeSupplier<String, Throwable> defaultLanguageUnsafeSupplier) {

			try {
				defaultLanguage = (String)defaultLanguageUnsafeSupplier.get();
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

		public void setFormRecords(FormRecord[] formRecords) {
			this.formRecords = (FormRecordImpl[])formRecords;
		}

		@JsonIgnore
		public void setFormRecords(
			UnsafeSupplier<FormRecord[], Throwable> formRecordsUnsafeSupplier) {

			try {
				formRecords = (FormRecordImpl[])formRecordsUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setFormRecordsIds(Long[] formRecordsIds) {
			this.formRecordsIds = (Long[])formRecordsIds;
		}

		@JsonIgnore
		public void setFormRecordsIds(
			UnsafeSupplier<Long[], Throwable> formRecordsIdsUnsafeSupplier) {

			try {
				formRecordsIds = (Long[])formRecordsIdsUnsafeSupplier.get();
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

		public void setStructure(FormStructure structure) {
			this.structure = (FormStructureImpl)structure;
		}

		@JsonIgnore
		public void setStructure(
			UnsafeSupplier<FormStructure, Throwable> structureUnsafeSupplier) {

			try {
				structure = (FormStructureImpl)structureUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public void setStructureId(Long structureId) {
			this.structureId = (Long)structureId;
		}

		@JsonIgnore
		public void setStructureId(
			UnsafeSupplier<Long, Throwable> structureIdUnsafeSupplier) {

			try {
				structureId = (Long)structureIdUnsafeSupplier.get();
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@JsonProperty
		protected String[] availableLanguages;

		@JsonProperty
		protected Long contentSpace;

		@JsonProperty
		protected CreatorImpl creator;

		@JsonProperty
		protected Date dateCreated;

		@JsonProperty
		protected Date dateModified;

		@JsonProperty
		protected Date datePublished;

		@JsonProperty
		protected String defaultLanguage;

		@JsonProperty
		protected String description;

		@JsonProperty
		protected FormRecordImpl[] formRecords;

		@JsonProperty
		protected Long[] formRecordsIds;

		@JsonProperty
		protected Long id;

		@JsonProperty
		protected String name;

		@JsonProperty
		protected FormStructureImpl structure;

		@JsonProperty
		protected Long structureId;

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