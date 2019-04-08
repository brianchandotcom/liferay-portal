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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
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
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

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
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
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
public abstract class BaseKeywordResourceTestCase {

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
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceKeywordsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceKeywordsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceKeywordsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			Keyword irrelevantKeyword =
				testGetContentSpaceKeywordsPage_addKeyword(
					irrelevantContentSpaceId, randomIrrelevantKeyword());

			Page<Keyword> page = invokeGetContentSpaceKeywordsPage(
				irrelevantContentSpaceId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKeyword),
				(List<Keyword>)page.getItems());
			assertValid(page);
		}

		Keyword keyword1 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, randomKeyword());

		Keyword keyword2 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, randomKeyword());

		Page<Keyword> page = invokeGetContentSpaceKeywordsPage(
			contentSpaceId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceKeywordsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKeywordsPage_getContentSpaceId();

		Keyword keyword1 = randomKeyword();
		Keyword keyword2 = randomKeyword();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				keyword1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		keyword1 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, keyword1);

		Thread.sleep(1000);

		keyword2 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, keyword2);

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = invokeGetContentSpaceKeywordsPage(
				contentSpaceId, null,
				getFilterString(entityField, "eq", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceKeywordsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKeywordsPage_getContentSpaceId();

		Keyword keyword1 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, randomKeyword());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword2 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, randomKeyword());

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = invokeGetContentSpaceKeywordsPage(
				contentSpaceId, null,
				getFilterString(entityField, "eq", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceKeywordsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceKeywordsPage_getContentSpaceId();

		Keyword keyword1 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, randomKeyword());

		Keyword keyword2 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, randomKeyword());

		Keyword keyword3 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, randomKeyword());

		Page<Keyword> page1 = invokeGetContentSpaceKeywordsPage(
			contentSpaceId, null, null, Pagination.of(1, 2), null);

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = invokeGetContentSpaceKeywordsPage(
			contentSpaceId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2, keyword3),
			new ArrayList<Keyword>() {
				{
					addAll(keywords1);
					addAll(keywords2);
				}
			});
	}

	@Test
	public void testGetContentSpaceKeywordsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKeywordsPage_getContentSpaceId();

		Keyword keyword1 = randomKeyword();
		Keyword keyword2 = randomKeyword();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				keyword1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		keyword1 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, keyword1);

		Thread.sleep(1000);

		keyword2 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, keyword2);

		for (EntityField entityField : entityFields) {
			Page<Keyword> ascPage = invokeGetContentSpaceKeywordsPage(
				contentSpaceId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(keyword1, keyword2),
				(List<Keyword>)ascPage.getItems());

			Page<Keyword> descPage = invokeGetContentSpaceKeywordsPage(
				contentSpaceId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(keyword2, keyword1),
				(List<Keyword>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceKeywordsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKeywordsPage_getContentSpaceId();

		Keyword keyword1 = randomKeyword();
		Keyword keyword2 = randomKeyword();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(keyword1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(keyword2, entityField.getName(), "Bbb");
		}

		keyword1 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, keyword1);

		keyword2 = testGetContentSpaceKeywordsPage_addKeyword(
			contentSpaceId, keyword2);

		for (EntityField entityField : entityFields) {
			Page<Keyword> ascPage = invokeGetContentSpaceKeywordsPage(
				contentSpaceId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(keyword1, keyword2),
				(List<Keyword>)ascPage.getItems());

			Page<Keyword> descPage = invokeGetContentSpaceKeywordsPage(
				contentSpaceId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(keyword2, keyword1),
				(List<Keyword>)descPage.getItems());
		}
	}

	protected Keyword testGetContentSpaceKeywordsPage_addKeyword(
			Long contentSpaceId, Keyword keyword)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceKeywordsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetContentSpaceKeywordsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<Keyword> invokeGetContentSpaceKeywordsPage(
			Long contentSpaceId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/keywords",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Keyword>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceKeywordsPageResponse(
			Long contentSpaceId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/keywords",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostContentSpaceKeyword() throws Exception {
		Keyword randomKeyword = randomKeyword();

		Keyword postKeyword = testPostContentSpaceKeyword_addKeyword(
			randomKeyword);

		assertEquals(randomKeyword, postKeyword);
		assertValid(postKeyword);
	}

	protected Keyword testPostContentSpaceKeyword_addKeyword(Keyword keyword)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Keyword invokePostContentSpaceKeyword(
			Long contentSpaceId, Keyword keyword)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(keyword),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/keywords",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Keyword.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceKeywordResponse(
			Long contentSpaceId, Keyword keyword)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(keyword),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/keywords",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteKeyword() throws Exception {
		Keyword keyword = testDeleteKeyword_addKeyword();

		assertResponseCode(204, invokeDeleteKeywordResponse(keyword.getId()));

		assertResponseCode(404, invokeGetKeywordResponse(keyword.getId()));
	}

	protected Keyword testDeleteKeyword_addKeyword() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteKeyword(Long keywordId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/keywords/{keywordId}", keywordId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteKeywordResponse(Long keywordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/keywords/{keywordId}", keywordId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKeyword() throws Exception {
		Keyword postKeyword = testGetKeyword_addKeyword();

		Keyword getKeyword = invokeGetKeyword(postKeyword.getId());

		assertEquals(postKeyword, getKeyword);
		assertValid(getKeyword);
	}

	protected Keyword testGetKeyword_addKeyword() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Keyword invokeGetKeyword(Long keywordId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/keywords/{keywordId}", keywordId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Keyword.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetKeywordResponse(Long keywordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/keywords/{keywordId}", keywordId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutKeyword() throws Exception {
		Keyword postKeyword = testPutKeyword_addKeyword();

		Keyword randomKeyword = randomKeyword();

		Keyword putKeyword = invokePutKeyword(
			postKeyword.getId(), randomKeyword);

		assertEquals(randomKeyword, putKeyword);
		assertValid(putKeyword);

		Keyword getKeyword = invokeGetKeyword(putKeyword.getId());

		assertEquals(randomKeyword, getKeyword);
		assertValid(getKeyword);
	}

	protected Keyword testPutKeyword_addKeyword() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Keyword invokePutKeyword(Long keywordId, Keyword keyword)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(keyword),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/keywords/{keywordId}", keywordId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Keyword.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutKeywordResponse(
			Long keywordId, Keyword keyword)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(keyword),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/keywords/{keywordId}", keywordId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(Keyword keyword1, Keyword keyword2) {
		Assert.assertTrue(
			keyword1 + " does not equal " + keyword2,
			equals(keyword1, keyword2));
	}

	protected void assertEquals(
		List<Keyword> keywords1, List<Keyword> keywords2) {

		Assert.assertEquals(keywords1.size(), keywords2.size());

		for (int i = 0; i < keywords1.size(); i++) {
			Keyword keyword1 = keywords1.get(i);
			Keyword keyword2 = keywords2.get(i);

			assertEquals(keyword1, keyword2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Keyword> keywords1, List<Keyword> keywords2) {

		Assert.assertEquals(keywords1.size(), keywords2.size());

		for (Keyword keyword1 : keywords1) {
			boolean contains = false;

			for (Keyword keyword2 : keywords2) {
				if (equals(keyword1, keyword2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				keywords2 + " does not contain " + keyword1, contains);
		}
	}

	protected void assertValid(Keyword keyword) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Keyword> page) {
		boolean valid = false;

		Collection<Keyword> keywords = page.getItems();

		int size = keywords.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(Keyword keyword1, Keyword keyword2) {
		if (keyword1 == keyword2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_keywordResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_keywordResource;

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
		EntityField entityField, String operator, Keyword keyword) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(keyword.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(keyword.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywordUsageCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(keyword.getName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Keyword randomKeyword() {
		return new Keyword() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected Keyword randomIrrelevantKeyword() {
		return randomKeyword();
	}

	protected Keyword randomPatchKeyword() {
		return randomKeyword();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
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
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
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

	protected String contentType = "application/json";
	protected Group irrelevantGroup;
	protected Group testGroup;
	protected String userNameAndPassword = "test@liferay.com:test";

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

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", contentType);

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
		BaseKeywordResourceTestCase.class);

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
	private KeywordResource _keywordResource;

	private URL _resourceURL;

}