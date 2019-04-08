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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
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
public abstract class BaseKnowledgeBaseArticleResourceTestCase {

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
	public void testGetContentSpaceKnowledgeBaseArticlesPage()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseArticlesPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceKnowledgeBaseArticlesPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			KnowledgeBaseArticle irrelevantKnowledgeBaseArticle =
				testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
					irrelevantContentSpaceId,
					randomIrrelevantKnowledgeBaseArticle());

			Page<KnowledgeBaseArticle> page =
				invokeGetContentSpaceKnowledgeBaseArticlesPage(
					irrelevantContentSpaceId, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseArticle),
				(List<KnowledgeBaseArticle>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page =
			invokeGetContentSpaceKnowledgeBaseArticlesPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			(List<KnowledgeBaseArticle>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceKnowledgeBaseArticlesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseArticlesPage_getContentSpaceId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		knowledgeBaseArticle1 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, knowledgeBaseArticle1);

		Thread.sleep(1000);

		knowledgeBaseArticle2 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetContentSpaceKnowledgeBaseArticlesPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceKnowledgeBaseArticlesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseArticlesPage_getContentSpaceId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, randomKnowledgeBaseArticle());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, randomKnowledgeBaseArticle());

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetContentSpaceKnowledgeBaseArticlesPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceKnowledgeBaseArticlesPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseArticlesPage_getContentSpaceId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle3 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page1 =
			invokeGetContentSpaceKnowledgeBaseArticlesPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		List<KnowledgeBaseArticle> knowledgeBaseArticles1 =
			(List<KnowledgeBaseArticle>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles1.toString(), 2,
			knowledgeBaseArticles1.size());

		Page<KnowledgeBaseArticle> page2 =
			invokeGetContentSpaceKnowledgeBaseArticlesPage(
				contentSpaceId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseArticle> knowledgeBaseArticles2 =
			(List<KnowledgeBaseArticle>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles2.toString(), 1,
			knowledgeBaseArticles2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseArticle1, knowledgeBaseArticle2,
				knowledgeBaseArticle3),
			new ArrayList<KnowledgeBaseArticle>() {
				{
					addAll(knowledgeBaseArticles1);
					addAll(knowledgeBaseArticles2);
				}
			});
	}

	@Test
	public void testGetContentSpaceKnowledgeBaseArticlesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseArticlesPage_getContentSpaceId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		knowledgeBaseArticle1 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, knowledgeBaseArticle1);

		Thread.sleep(1000);

		knowledgeBaseArticle2 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetContentSpaceKnowledgeBaseArticlesPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetContentSpaceKnowledgeBaseArticlesPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceKnowledgeBaseArticlesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseArticlesPage_getContentSpaceId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				knowledgeBaseArticle2, entityField.getName(), "Bbb");
		}

		knowledgeBaseArticle1 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				contentSpaceId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetContentSpaceKnowledgeBaseArticlesPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetContentSpaceKnowledgeBaseArticlesPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	protected KnowledgeBaseArticle
			testGetContentSpaceKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				Long contentSpaceId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSpaceKnowledgeBaseArticlesPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceKnowledgeBaseArticlesPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<KnowledgeBaseArticle>
			invokeGetContentSpaceKnowledgeBaseArticlesPage(
				Long contentSpaceId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-articles",
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
			new TypeReference<Page<KnowledgeBaseArticle>>() {
			});
	}

	protected Http.Response
			invokeGetContentSpaceKnowledgeBaseArticlesPageResponse(
				Long contentSpaceId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-articles",
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
	public void testPostContentSpaceKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPostContentSpaceKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, postKnowledgeBaseArticle);
		assertValid(postKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPostContentSpaceKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseArticle invokePostContentSpaceKnowledgeBaseArticle(
			Long contentSpaceId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-articles",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseArticle.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceKnowledgeBaseArticleResponse(
			Long contentSpaceId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-articles",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testDeleteKnowledgeBaseArticle_addKnowledgeBaseArticle();

		assertResponseCode(
			204,
			invokeDeleteKnowledgeBaseArticleResponse(
				knowledgeBaseArticle.getId()));

		assertResponseCode(
			404,
			invokeGetKnowledgeBaseArticleResponse(
				knowledgeBaseArticle.getId()));
	}

	protected KnowledgeBaseArticle
			testDeleteKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteKnowledgeBaseArticle(Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteKnowledgeBaseArticleResponse(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testGetKnowledgeBaseArticle_addKnowledgeBaseArticle();

		KnowledgeBaseArticle getKnowledgeBaseArticle =
			invokeGetKnowledgeBaseArticle(postKnowledgeBaseArticle.getId());

		assertEquals(postKnowledgeBaseArticle, getKnowledgeBaseArticle);
		assertValid(getKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testGetKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseArticle invokeGetKnowledgeBaseArticle(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseArticle.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetKnowledgeBaseArticleResponse(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPatchKnowledgeBaseArticle_addKnowledgeBaseArticle();

		KnowledgeBaseArticle randomPatchKnowledgeBaseArticle =
			randomPatchKnowledgeBaseArticle();

		KnowledgeBaseArticle patchKnowledgeBaseArticle =
			invokePatchKnowledgeBaseArticle(
				postKnowledgeBaseArticle.getId(),
				randomPatchKnowledgeBaseArticle);

		KnowledgeBaseArticle expectedPatchKnowledgeBaseArticle =
			(KnowledgeBaseArticle)BeanUtils.cloneBean(postKnowledgeBaseArticle);

		_beanUtilsBean.copyProperties(
			expectedPatchKnowledgeBaseArticle, randomPatchKnowledgeBaseArticle);

		KnowledgeBaseArticle getKnowledgeBaseArticle =
			invokeGetKnowledgeBaseArticle(patchKnowledgeBaseArticle.getId());

		assertEquals(
			expectedPatchKnowledgeBaseArticle, getKnowledgeBaseArticle);
		assertValid(getKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPatchKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseArticle invokePatchKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseArticle.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchKnowledgeBaseArticleResponse(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPutKnowledgeBaseArticle_addKnowledgeBaseArticle();

		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle putKnowledgeBaseArticle =
			invokePutKnowledgeBaseArticle(
				postKnowledgeBaseArticle.getId(), randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, putKnowledgeBaseArticle);
		assertValid(putKnowledgeBaseArticle);

		KnowledgeBaseArticle getKnowledgeBaseArticle =
			invokeGetKnowledgeBaseArticle(putKnowledgeBaseArticle.getId());

		assertEquals(randomKnowledgeBaseArticle, getKnowledgeBaseArticle);
		assertValid(getKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPutKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseArticle invokePutKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseArticle.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutKnowledgeBaseArticleResponse(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteKnowledgeBaseArticleMyRating() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testDeleteKnowledgeBaseArticleMyRating_addKnowledgeBaseArticle();

		assertResponseCode(
			204,
			invokeDeleteKnowledgeBaseArticleMyRatingResponse(
				knowledgeBaseArticle.getId()));

		assertResponseCode(
			404,
			invokeGetKnowledgeBaseArticleMyRatingResponse(
				knowledgeBaseArticle.getId()));
	}

	protected KnowledgeBaseArticle
			testDeleteKnowledgeBaseArticleMyRating_addKnowledgeBaseArticle()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteKnowledgeBaseArticleMyRatingResponse(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKnowledgeBaseArticleMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokeGetKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetKnowledgeBaseArticleMyRatingResponse(
			Long knowledgeBaseArticleId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostKnowledgeBaseArticleMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePostKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostKnowledgeBaseArticleMyRatingResponse(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutKnowledgeBaseArticleMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePutKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutKnowledgeBaseArticleMyRatingResponse(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{knowledgeBaseArticleId}/my-rating",
					knowledgeBaseArticleId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage()
		throws Exception {

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();
		Long irrelevantParentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getIrrelevantParentKnowledgeBaseArticleId();

		if ((irrelevantParentKnowledgeBaseArticleId != null)) {
			KnowledgeBaseArticle irrelevantKnowledgeBaseArticle =
				testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
					irrelevantParentKnowledgeBaseArticleId,
					randomIrrelevantKnowledgeBaseArticle());

			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					irrelevantParentKnowledgeBaseArticleId, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseArticle),
				(List<KnowledgeBaseArticle>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page =
			invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				parentKnowledgeBaseArticleId, null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			(List<KnowledgeBaseArticle>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle1);

		Thread.sleep(1000);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithPagination()
		throws Exception {

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle3 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page1 =
			invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				parentKnowledgeBaseArticleId, null, null, Pagination.of(1, 2),
				null);

		List<KnowledgeBaseArticle> knowledgeBaseArticles1 =
			(List<KnowledgeBaseArticle>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles1.toString(), 2,
			knowledgeBaseArticles1.size());

		Page<KnowledgeBaseArticle> page2 =
			invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				parentKnowledgeBaseArticleId, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseArticle> knowledgeBaseArticles2 =
			(List<KnowledgeBaseArticle>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles2.toString(), 1,
			knowledgeBaseArticles2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseArticle1, knowledgeBaseArticle2,
				knowledgeBaseArticle3),
			new ArrayList<KnowledgeBaseArticle>() {
				{
					addAll(knowledgeBaseArticles1);
					addAll(knowledgeBaseArticles2);
				}
			});
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle1);

		Thread.sleep(1000);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				knowledgeBaseArticle2, entityField.getName(), "Bbb");
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	protected KnowledgeBaseArticle
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				Long parentKnowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getIrrelevantParentKnowledgeBaseArticleId()
		throws Exception {

		return null;
	}

	protected Page<KnowledgeBaseArticle>
			invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				Long parentKnowledgeBaseArticleId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles",
					parentKnowledgeBaseArticleId);

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
			new TypeReference<Page<KnowledgeBaseArticle>>() {
			});
	}

	protected Http.Response
			invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPageResponse(
				Long parentKnowledgeBaseArticleId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles",
					parentKnowledgeBaseArticleId);

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
	public void testPostKnowledgeBaseArticleKnowledgeBaseArticle()
		throws Exception {

		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPostKnowledgeBaseArticleKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, postKnowledgeBaseArticle);
		assertValid(postKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPostKnowledgeBaseArticleKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseArticle
			invokePostKnowledgeBaseArticleKnowledgeBaseArticle(
				Long parentKnowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles",
					parentKnowledgeBaseArticleId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseArticle.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostKnowledgeBaseArticleKnowledgeBaseArticleResponse(
				Long parentKnowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-articles/{parentKnowledgeBaseArticleId}/knowledge-base-articles",
					parentKnowledgeBaseArticleId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage()
		throws Exception {

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();
		Long irrelevantKnowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getIrrelevantKnowledgeBaseFolderId();

		if ((irrelevantKnowledgeBaseFolderId != null)) {
			KnowledgeBaseArticle irrelevantKnowledgeBaseArticle =
				testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
					irrelevantKnowledgeBaseFolderId,
					randomIrrelevantKnowledgeBaseArticle());

			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					irrelevantKnowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseArticle),
				(List<KnowledgeBaseArticle>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page =
			invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				knowledgeBaseFolderId, null, null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			(List<KnowledgeBaseArticle>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle1);

		Thread.sleep(1000);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithPagination()
		throws Exception {

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle3 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page1 =
			invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				knowledgeBaseFolderId, null, null, null, Pagination.of(1, 2),
				null);

		List<KnowledgeBaseArticle> knowledgeBaseArticles1 =
			(List<KnowledgeBaseArticle>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles1.toString(), 2,
			knowledgeBaseArticles1.size());

		Page<KnowledgeBaseArticle> page2 =
			invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				knowledgeBaseFolderId, null, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseArticle> knowledgeBaseArticles2 =
			(List<KnowledgeBaseArticle>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles2.toString(), 1,
			knowledgeBaseArticles2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseArticle1, knowledgeBaseArticle2,
				knowledgeBaseArticle3),
			new ArrayList<KnowledgeBaseArticle>() {
				{
					addAll(knowledgeBaseArticles1);
					addAll(knowledgeBaseArticles2);
				}
			});
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle1);

		Thread.sleep(1000);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				knowledgeBaseArticle1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				knowledgeBaseArticle2, entityField.getName(), "Bbb");
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	protected KnowledgeBaseArticle
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				Long knowledgeBaseFolderId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getIrrelevantKnowledgeBaseFolderId()
		throws Exception {

		return null;
	}

	protected Page<KnowledgeBaseArticle>
			invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				Long knowledgeBaseFolderId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles",
					knowledgeBaseFolderId);

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
			new TypeReference<Page<KnowledgeBaseArticle>>() {
			});
	}

	protected Http.Response
			invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPageResponse(
				Long knowledgeBaseFolderId, Boolean flatten, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles",
					knowledgeBaseFolderId);

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
	public void testPostKnowledgeBaseFolderKnowledgeBaseArticle()
		throws Exception {

		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPostKnowledgeBaseFolderKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, postKnowledgeBaseArticle);
		assertValid(postKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPostKnowledgeBaseFolderKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseArticle
			invokePostKnowledgeBaseFolderKnowledgeBaseArticle(
				Long knowledgeBaseFolderId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles",
					knowledgeBaseFolderId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseArticle.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostKnowledgeBaseFolderKnowledgeBaseArticleResponse(
				Long knowledgeBaseFolderId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}/knowledge-base-articles",
					knowledgeBaseFolderId);

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

	protected void assertEquals(
		KnowledgeBaseArticle knowledgeBaseArticle1,
		KnowledgeBaseArticle knowledgeBaseArticle2) {

		Assert.assertTrue(
			knowledgeBaseArticle1 + " does not equal " + knowledgeBaseArticle2,
			equals(knowledgeBaseArticle1, knowledgeBaseArticle2));
	}

	protected void assertEquals(
		List<KnowledgeBaseArticle> knowledgeBaseArticles1,
		List<KnowledgeBaseArticle> knowledgeBaseArticles2) {

		Assert.assertEquals(
			knowledgeBaseArticles1.size(), knowledgeBaseArticles2.size());

		for (int i = 0; i < knowledgeBaseArticles1.size(); i++) {
			KnowledgeBaseArticle knowledgeBaseArticle1 =
				knowledgeBaseArticles1.get(i);
			KnowledgeBaseArticle knowledgeBaseArticle2 =
				knowledgeBaseArticles2.get(i);

			assertEquals(knowledgeBaseArticle1, knowledgeBaseArticle2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<KnowledgeBaseArticle> knowledgeBaseArticles1,
		List<KnowledgeBaseArticle> knowledgeBaseArticles2) {

		Assert.assertEquals(
			knowledgeBaseArticles1.size(), knowledgeBaseArticles2.size());

		for (KnowledgeBaseArticle knowledgeBaseArticle1 :
				knowledgeBaseArticles1) {

			boolean contains = false;

			for (KnowledgeBaseArticle knowledgeBaseArticle2 :
					knowledgeBaseArticles2) {

				if (equals(knowledgeBaseArticle1, knowledgeBaseArticle2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				knowledgeBaseArticles2 + " does not contain " +
					knowledgeBaseArticle1,
				contains);
		}
	}

	protected void assertValid(KnowledgeBaseArticle knowledgeBaseArticle) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<KnowledgeBaseArticle> page) {
		boolean valid = false;

		Collection<KnowledgeBaseArticle> knowledgeBaseArticles =
			page.getItems();

		int size = knowledgeBaseArticles.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		KnowledgeBaseArticle knowledgeBaseArticle1,
		KnowledgeBaseArticle knowledgeBaseArticle2) {

		if (knowledgeBaseArticle1 == knowledgeBaseArticle2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_knowledgeBaseArticleResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_knowledgeBaseArticleResource;

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
		EntityField entityField, String operator,
		KnowledgeBaseArticle knowledgeBaseArticle) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseArticle.getArticleBody()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(
				_dateFormat.format(knowledgeBaseArticle.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(
				_dateFormat.format(knowledgeBaseArticle.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseArticle.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseArticle.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(
				String.valueOf(knowledgeBaseArticle.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfAttachments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfKnowledgeBaseArticles")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentKnowledgeBaseFolder")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentKnowledgeBaseFolderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseArticle.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected KnowledgeBaseArticle randomKnowledgeBaseArticle() {
		return new KnowledgeBaseArticle() {
			{
				articleBody = RandomTestUtil.randomString();
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				parentKnowledgeBaseFolderId = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected KnowledgeBaseArticle randomIrrelevantKnowledgeBaseArticle() {
		return randomKnowledgeBaseArticle();
	}

	protected KnowledgeBaseArticle randomPatchKnowledgeBaseArticle() {
		return randomKnowledgeBaseArticle();
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
		BaseKnowledgeBaseArticleResourceTestCase.class);

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
	private KnowledgeBaseArticleResource _knowledgeBaseArticleResource;

	private URL _resourceURL;

}