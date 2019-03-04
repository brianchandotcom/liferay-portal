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
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;

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
public abstract class BaseVocabularyResourceTestCase {

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
		Vocabulary vocabulary = testDeleteVocabulary_addVocabulary();

		assertResponseCode(
			200, invokeDeleteVocabularyResponse(vocabulary.getId()));

		assertResponseCode(
			404, invokeGetVocabularyResponse(vocabulary.getId()));
	}

	@Test
	public void testGetContentSpaceVocabulariesPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceVocabulariesPage_getContentSpaceId();

		Vocabulary vocabulary1 =
			testGetContentSpaceVocabulariesPage_addVocabulary(
				contentSpaceId, randomVocabulary());
		Vocabulary vocabulary2 =
			testGetContentSpaceVocabulariesPage_addVocabulary(
				contentSpaceId, randomVocabulary());

		Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
			contentSpaceId, (String)null, Pagination.of(2, 1), (String)null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(vocabulary1, vocabulary2),
			(List<Vocabulary>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceVocabulariesPage_getContentSpaceId();

		Vocabulary vocabulary1 = randomVocabulary();
		Vocabulary vocabulary2 = randomVocabulary();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				vocabulary1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		vocabulary1 = testGetContentSpaceVocabulariesPage_addVocabulary(
			contentSpaceId, vocabulary1);

		Thread.sleep(1000);

		vocabulary2 = testGetContentSpaceVocabulariesPage_addVocabulary(
			contentSpaceId, vocabulary2);

		for (EntityField entityField : entityFields) {
			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				contentSpaceId, getFilterString(entityField, "eq", vocabulary1),
				Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(vocabulary1),
				(List<Vocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceVocabulariesPage_getContentSpaceId();

		Vocabulary vocabulary1 =
			testGetContentSpaceVocabulariesPage_addVocabulary(
				contentSpaceId, randomVocabulary());
		Vocabulary vocabulary2 =
			testGetContentSpaceVocabulariesPage_addVocabulary(
				contentSpaceId, randomVocabulary());

		for (EntityField entityField : entityFields) {
			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				contentSpaceId, getFilterString(entityField, "eq", vocabulary1),
				Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(vocabulary1),
				(List<Vocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceVocabulariesPage_getContentSpaceId();

		Vocabulary vocabulary1 =
			testGetContentSpaceVocabulariesPage_addVocabulary(
				contentSpaceId, randomVocabulary());
		Vocabulary vocabulary2 =
			testGetContentSpaceVocabulariesPage_addVocabulary(
				contentSpaceId, randomVocabulary());
		Vocabulary vocabulary3 =
			testGetContentSpaceVocabulariesPage_addVocabulary(
				contentSpaceId, randomVocabulary());

		Page<Vocabulary> page1 = invokeGetContentSpaceVocabulariesPage(
			contentSpaceId, (String)null, Pagination.of(2, 1), (String)null);

		List<Vocabulary> vocabularies1 = (List<Vocabulary>)page1.getItems();

		Assert.assertEquals(vocabularies1.toString(), 2, vocabularies1.size());

		Page<Vocabulary> page2 = invokeGetContentSpaceVocabulariesPage(
			contentSpaceId, (String)null, Pagination.of(2, 2), (String)null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Vocabulary> vocabularies2 = (List<Vocabulary>)page2.getItems();

		Assert.assertEquals(vocabularies2.toString(), 1, vocabularies2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(vocabulary1, vocabulary2, vocabulary3),
			new ArrayList<Vocabulary>() {
				{
					addAll(vocabularies1);
					addAll(vocabularies2);
				}
			});
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceVocabulariesPage_getContentSpaceId();

		Vocabulary vocabulary1 = randomVocabulary();
		Vocabulary vocabulary2 = randomVocabulary();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				vocabulary1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		vocabulary1 = testGetContentSpaceVocabulariesPage_addVocabulary(
			contentSpaceId, vocabulary1);

		Thread.sleep(1000);

		vocabulary2 = testGetContentSpaceVocabulariesPage_addVocabulary(
			contentSpaceId, vocabulary2);

		for (EntityField entityField : entityFields) {
			Page<Vocabulary> ascPage = invokeGetContentSpaceVocabulariesPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(vocabulary1, vocabulary2),
				(List<Vocabulary>)ascPage.getItems());

			Page<Vocabulary> descPage = invokeGetContentSpaceVocabulariesPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(vocabulary2, vocabulary1),
				(List<Vocabulary>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceVocabulariesPage_getContentSpaceId();

		Vocabulary vocabulary1 = randomVocabulary();
		Vocabulary vocabulary2 = randomVocabulary();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(vocabulary1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(vocabulary2, entityField.getName(), "Bbb");
		}

		vocabulary1 = testGetContentSpaceVocabulariesPage_addVocabulary(
			contentSpaceId, vocabulary1);
		vocabulary2 = testGetContentSpaceVocabulariesPage_addVocabulary(
			contentSpaceId, vocabulary2);

		for (EntityField entityField : entityFields) {
			Page<Vocabulary> ascPage = invokeGetContentSpaceVocabulariesPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(vocabulary1, vocabulary2),
				(List<Vocabulary>)ascPage.getItems());

			Page<Vocabulary> descPage = invokeGetContentSpaceVocabulariesPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(vocabulary2, vocabulary1),
				(List<Vocabulary>)descPage.getItems());
		}
	}

	@Test
	public void testGetVocabulary() throws Exception {
		Vocabulary postVocabulary = testGetVocabulary_addVocabulary();

		Vocabulary getVocabulary = invokeGetVocabulary(postVocabulary.getId());

		assertEquals(postVocabulary, getVocabulary);
		assertValid(getVocabulary);
	}

	@Test
	public void testPostContentSpaceVocabulary() throws Exception {
		Vocabulary randomVocabulary = randomVocabulary();

		Vocabulary postVocabulary =
			testPostContentSpaceVocabulary_addVocabulary(randomVocabulary);

		assertEquals(randomVocabulary, postVocabulary);
		assertValid(postVocabulary);
	}

	@Test
	public void testPutVocabulary() throws Exception {
		Vocabulary postVocabulary = testPutVocabulary_addVocabulary();

		Vocabulary randomVocabulary = randomVocabulary();

		Vocabulary putVocabulary = invokePutVocabulary(
			postVocabulary.getId(), randomVocabulary);

		assertEquals(randomVocabulary, putVocabulary);
		assertValid(putVocabulary);

		Vocabulary getVocabulary = invokeGetVocabulary(putVocabulary.getId());

		assertEquals(randomVocabulary, getVocabulary);
		assertValid(getVocabulary);
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

	protected void assertValid(Page<Vocabulary> page) {
		boolean valid = false;

		Collection<Vocabulary> vocabularies = page.getItems();

		int size = vocabularies.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Vocabulary vocabulary) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(Vocabulary vocabulary1, Vocabulary vocabulary2) {
		if (vocabulary1 == vocabulary2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_vocabularyResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_vocabularyResource;

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
		EntityField entityField, String operator, Vocabulary vocabulary) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentSpace")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(vocabulary.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(vocabulary.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(vocabulary.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("hasCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(vocabulary.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
			sb.append("'");
			sb.append(String.valueOf(vocabulary.getViewableBy()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
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
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceVocabulariesLocation(
				contentSpaceId, filterString, pagination, sortString));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Vocabulary>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceVocabulariesPageResponse(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceVocabulariesLocation(
				contentSpaceId, filterString, pagination, sortString));

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
				viewableBy = RandomTestUtil.randomString();
			}
		};
	}

	protected Vocabulary testDeleteVocabulary_addVocabulary() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Vocabulary testGetContentSpaceVocabulariesPage_addVocabulary(
			Long contentSpaceId, Vocabulary vocabulary)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceVocabulariesPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Vocabulary testGetVocabulary_addVocabulary() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Vocabulary testPostContentSpaceVocabulary_addVocabulary(
			Vocabulary vocabulary)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Vocabulary testPutVocabulary_addVocabulary() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getItemsPerPage() {
			return itemsPerPage;
		}

		public long getLastPageNumber() {
			return lastPageNumber;
		}

		public long getPageNumber() {
			return pageNumber;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected long itemsPerPage;

		@JsonProperty
		protected long lastPageNumber;

		@JsonProperty("page")
		protected long pageNumber;

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

	private String _getContentSpaceVocabulariesLocation(
		Long contentSpaceId, String filterString, Pagination pagination,
		String sortString) {

		String url =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/vocabularies",
					contentSpaceId);

		url += "?filter=" + URLCodec.encodeURL(filterString);
		url += "&page=" + pagination.getPageNumber();
		url += "&pageSize=" + pagination.getItemsPerPage();
		url += "&sort=" + URLCodec.encodeURL(sortString);

		return url;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

	@Inject
	private VocabularyResource _vocabularyResource;

}