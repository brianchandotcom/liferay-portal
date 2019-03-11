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

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
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
public abstract class BaseCategoryResourceTestCase {

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
	public void testDeleteCategory() throws Exception {
		Category category = testDeleteCategory_addCategory();

		assertResponseCode(200, invokeDeleteCategoryResponse(category.getId()));

		assertResponseCode(404, invokeGetCategoryResponse(category.getId()));
	}

	protected Category testDeleteCategory_addCategory() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean invokeDeleteCategory(Long categoryId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/categories/{category-id}", categoryId);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteCategoryResponse(Long categoryId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/categories/{category-id}", categoryId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetCategory() throws Exception {
		Category postCategory = testGetCategory_addCategory();

		Category getCategory = invokeGetCategory(postCategory.getId());

		assertEquals(postCategory, getCategory);
		assertValid(getCategory);
	}

	protected Category testGetCategory_addCategory() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Category invokeGetCategory(Long categoryId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/categories/{category-id}", categoryId);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Category.class);
	}

	protected Http.Response invokeGetCategoryResponse(Long categoryId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/categories/{category-id}", categoryId);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPutCategory() throws Exception {
		Category postCategory = testPutCategory_addCategory();

		Category randomCategory = randomCategory();

		Category putCategory = invokePutCategory(
			postCategory.getId(), randomCategory);

		assertEquals(randomCategory, putCategory);
		assertValid(putCategory);

		Category getCategory = invokeGetCategory(putCategory.getId());

		assertEquals(randomCategory, getCategory);
		assertValid(getCategory);
	}

	protected Category testPutCategory_addCategory() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Category invokePutCategory(Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/categories/{category-id}", categoryId);

		options.setLocation(location);

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Category.class);
	}

	protected Http.Response invokePutCategoryResponse(
			Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/categories/{category-id}", categoryId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetCategoryCategoriesPage() throws Exception {
		Long categoryId = testGetCategoryCategoriesPage_getCategoryId();

		Category category1 = testGetCategoryCategoriesPage_addCategory(
			categoryId, randomCategory());
		Category category2 = testGetCategoryCategoriesPage_addCategory(
			categoryId, randomCategory());

		Page<Category> page = invokeGetCategoryCategoriesPage(
			categoryId, (String)null, Pagination.of(1, 2), (String)null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(category1, category2),
			(List<Category>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetCategoryCategoriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long categoryId = testGetCategoryCategoriesPage_getCategoryId();

		Category category1 = randomCategory();
		Category category2 = randomCategory();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				category1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		category1 = testGetCategoryCategoriesPage_addCategory(
			categoryId, category1);

		Thread.sleep(1000);

		category2 = testGetCategoryCategoriesPage_addCategory(
			categoryId, category2);

		for (EntityField entityField : entityFields) {
			Page<Category> page = invokeGetCategoryCategoriesPage(
				categoryId, getFilterString(entityField, "eq", category1),
				Pagination.of(1, 2), (String)null);

			assertEquals(
				Collections.singletonList(category1),
				(List<Category>)page.getItems());
		}
	}

	@Test
	public void testGetCategoryCategoriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long categoryId = testGetCategoryCategoriesPage_getCategoryId();

		Category category1 = testGetCategoryCategoriesPage_addCategory(
			categoryId, randomCategory());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Category category2 = testGetCategoryCategoriesPage_addCategory(
			categoryId, randomCategory());

		for (EntityField entityField : entityFields) {
			Page<Category> page = invokeGetCategoryCategoriesPage(
				categoryId, getFilterString(entityField, "eq", category1),
				Pagination.of(1, 2), (String)null);

			assertEquals(
				Collections.singletonList(category1),
				(List<Category>)page.getItems());
		}
	}

	@Test
	public void testGetCategoryCategoriesPageWithPagination() throws Exception {
		Long categoryId = testGetCategoryCategoriesPage_getCategoryId();

		Category category1 = testGetCategoryCategoriesPage_addCategory(
			categoryId, randomCategory());
		Category category2 = testGetCategoryCategoriesPage_addCategory(
			categoryId, randomCategory());
		Category category3 = testGetCategoryCategoriesPage_addCategory(
			categoryId, randomCategory());

		Page<Category> page1 = invokeGetCategoryCategoriesPage(
			categoryId, (String)null, Pagination.of(1, 2), (String)null);

		List<Category> categories1 = (List<Category>)page1.getItems();

		Assert.assertEquals(categories1.toString(), 2, categories1.size());

		Page<Category> page2 = invokeGetCategoryCategoriesPage(
			categoryId, (String)null, Pagination.of(2, 2), (String)null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Category> categories2 = (List<Category>)page2.getItems();

		Assert.assertEquals(categories2.toString(), 1, categories2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(category1, category2, category3),
			new ArrayList<Category>() {
				{
					addAll(categories1);
					addAll(categories2);
				}
			});
	}

	@Test
	public void testGetCategoryCategoriesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long categoryId = testGetCategoryCategoriesPage_getCategoryId();

		Category category1 = randomCategory();
		Category category2 = randomCategory();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				category1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		category1 = testGetCategoryCategoriesPage_addCategory(
			categoryId, category1);

		Thread.sleep(1000);

		category2 = testGetCategoryCategoriesPage_addCategory(
			categoryId, category2);

		for (EntityField entityField : entityFields) {
			Page<Category> ascPage = invokeGetCategoryCategoriesPage(
				categoryId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(category1, category2),
				(List<Category>)ascPage.getItems());

			Page<Category> descPage = invokeGetCategoryCategoriesPage(
				categoryId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(category2, category1),
				(List<Category>)descPage.getItems());
		}
	}

	@Test
	public void testGetCategoryCategoriesPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long categoryId = testGetCategoryCategoriesPage_getCategoryId();

		Category category1 = randomCategory();
		Category category2 = randomCategory();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(category1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(category2, entityField.getName(), "Bbb");
		}

		category1 = testGetCategoryCategoriesPage_addCategory(
			categoryId, category1);
		category2 = testGetCategoryCategoriesPage_addCategory(
			categoryId, category2);

		for (EntityField entityField : entityFields) {
			Page<Category> ascPage = invokeGetCategoryCategoriesPage(
				categoryId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(category1, category2),
				(List<Category>)ascPage.getItems());

			Page<Category> descPage = invokeGetCategoryCategoriesPage(
				categoryId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(category2, category1),
				(List<Category>)descPage.getItems());
		}
	}

	protected Category testGetCategoryCategoriesPage_addCategory(
			Long categoryId, Category category)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCategoryCategoriesPage_getCategoryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<Category> invokeGetCategoryCategoriesPage(
			Long categoryId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Category>>() {
			});
	}

	protected Http.Response invokeGetCategoryCategoriesPageResponse(
			Long categoryId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostCategoryCategory() throws Exception {
		Category randomCategory = randomCategory();

		Category postCategory = testPostCategoryCategory_addCategory(
			randomCategory);

		assertEquals(randomCategory, postCategory);
		assertValid(postCategory);
	}

	protected Category testPostCategoryCategory_addCategory(Category category)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Category invokePostCategoryCategory(
			Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Category.class);
	}

	protected Http.Response invokePostCategoryCategoryResponse(
			Long categoryId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/categories/{category-id}/categories", categoryId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testGetVocabularyCategoriesPage() throws Exception {
		Long vocabularyId = testGetVocabularyCategoriesPage_getVocabularyId();

		Category category1 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, randomCategory());
		Category category2 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, randomCategory());

		Page<Category> page = invokeGetVocabularyCategoriesPage(
			vocabularyId, (String)null, Pagination.of(1, 2), (String)null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(category1, category2),
			(List<Category>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetVocabularyCategoriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long vocabularyId = testGetVocabularyCategoriesPage_getVocabularyId();

		Category category1 = randomCategory();
		Category category2 = randomCategory();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				category1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		category1 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, category1);

		Thread.sleep(1000);

		category2 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, category2);

		for (EntityField entityField : entityFields) {
			Page<Category> page = invokeGetVocabularyCategoriesPage(
				vocabularyId, getFilterString(entityField, "eq", category1),
				Pagination.of(1, 2), (String)null);

			assertEquals(
				Collections.singletonList(category1),
				(List<Category>)page.getItems());
		}
	}

	@Test
	public void testGetVocabularyCategoriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long vocabularyId = testGetVocabularyCategoriesPage_getVocabularyId();

		Category category1 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, randomCategory());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Category category2 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, randomCategory());

		for (EntityField entityField : entityFields) {
			Page<Category> page = invokeGetVocabularyCategoriesPage(
				vocabularyId, getFilterString(entityField, "eq", category1),
				Pagination.of(1, 2), (String)null);

			assertEquals(
				Collections.singletonList(category1),
				(List<Category>)page.getItems());
		}
	}

	@Test
	public void testGetVocabularyCategoriesPageWithPagination()
		throws Exception {

		Long vocabularyId = testGetVocabularyCategoriesPage_getVocabularyId();

		Category category1 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, randomCategory());
		Category category2 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, randomCategory());
		Category category3 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, randomCategory());

		Page<Category> page1 = invokeGetVocabularyCategoriesPage(
			vocabularyId, (String)null, Pagination.of(1, 2), (String)null);

		List<Category> categories1 = (List<Category>)page1.getItems();

		Assert.assertEquals(categories1.toString(), 2, categories1.size());

		Page<Category> page2 = invokeGetVocabularyCategoriesPage(
			vocabularyId, (String)null, Pagination.of(2, 2), (String)null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Category> categories2 = (List<Category>)page2.getItems();

		Assert.assertEquals(categories2.toString(), 1, categories2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(category1, category2, category3),
			new ArrayList<Category>() {
				{
					addAll(categories1);
					addAll(categories2);
				}
			});
	}

	@Test
	public void testGetVocabularyCategoriesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long vocabularyId = testGetVocabularyCategoriesPage_getVocabularyId();

		Category category1 = randomCategory();
		Category category2 = randomCategory();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				category1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		category1 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, category1);

		Thread.sleep(1000);

		category2 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, category2);

		for (EntityField entityField : entityFields) {
			Page<Category> ascPage = invokeGetVocabularyCategoriesPage(
				vocabularyId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(category1, category2),
				(List<Category>)ascPage.getItems());

			Page<Category> descPage = invokeGetVocabularyCategoriesPage(
				vocabularyId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(category2, category1),
				(List<Category>)descPage.getItems());
		}
	}

	@Test
	public void testGetVocabularyCategoriesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long vocabularyId = testGetVocabularyCategoriesPage_getVocabularyId();

		Category category1 = randomCategory();
		Category category2 = randomCategory();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(category1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(category2, entityField.getName(), "Bbb");
		}

		category1 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, category1);
		category2 = testGetVocabularyCategoriesPage_addCategory(
			vocabularyId, category2);

		for (EntityField entityField : entityFields) {
			Page<Category> ascPage = invokeGetVocabularyCategoriesPage(
				vocabularyId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(category1, category2),
				(List<Category>)ascPage.getItems());

			Page<Category> descPage = invokeGetVocabularyCategoriesPage(
				vocabularyId, (String)null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(category2, category1),
				(List<Category>)descPage.getItems());
		}
	}

	protected Category testGetVocabularyCategoriesPage_addCategory(
			Long vocabularyId, Category category)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetVocabularyCategoriesPage_getVocabularyId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<Category> invokeGetVocabularyCategoriesPage(
			Long vocabularyId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Category>>() {
			});
	}

	protected Http.Response invokeGetVocabularyCategoriesPageResponse(
			Long vocabularyId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostVocabularyCategory() throws Exception {
		Category randomCategory = randomCategory();

		Category postCategory = testPostVocabularyCategory_addCategory(
			randomCategory);

		assertEquals(randomCategory, postCategory);
		assertValid(postCategory);
	}

	protected Category testPostVocabularyCategory_addCategory(Category category)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Category invokePostVocabularyCategory(
			Long vocabularyId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Category.class);
	}

	protected Http.Response invokePostVocabularyCategoryResponse(
			Long vocabularyId, Category category)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(category),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/vocabularies/{vocabulary-id}/categories", vocabularyId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(Category category1, Category category2) {
		Assert.assertTrue(
			category1 + " does not equal " + category2,
			equals(category1, category2));
	}

	protected void assertEquals(
		List<Category> categories1, List<Category> categories2) {

		Assert.assertEquals(categories1.size(), categories2.size());

		for (int i = 0; i < categories1.size(); i++) {
			Category category1 = categories1.get(i);
			Category category2 = categories2.get(i);

			assertEquals(category1, category2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Category> categories1, List<Category> categories2) {

		Assert.assertEquals(categories1.size(), categories2.size());

		for (Category category1 : categories1) {
			boolean contains = false;

			for (Category category2 : categories2) {
				if (equals(category1, category2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				categories2 + " does not contain " + category1, contains);
		}
	}

	protected void assertValid(Category category) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Category> page) {
		boolean valid = false;

		Collection<Category> categories = page.getItems();

		int size = categories.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(Category category1, Category category2) {
		if (category1 == category2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_categoryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_categoryResource;

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
		EntityField entityField, String operator, Category category) {

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

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(category.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(category.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(category.getDescription()));
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
			sb.append(String.valueOf(category.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("parentCategory")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentVocabulary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentVocabularyId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Category randomCategory() {
		return new Category() {
			{
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

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

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
	private CategoryResource _categoryResource;

	private URL _resourceURL;

}