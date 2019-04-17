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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseArticleSerDes;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();

		String json = objectMapper.writeValueAsString(knowledgeBaseArticle1);

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			KnowledgeBaseArticleSerDes.toDTO(json);

		Assert.assertTrue(equals(knowledgeBaseArticle1, knowledgeBaseArticle2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		KnowledgeBaseArticle knowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		String json1 = objectMapper.writeValueAsString(knowledgeBaseArticle);
		String json2 = KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
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

		return invokePostSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
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

		return invokePostSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
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
			return KnowledgeBaseArticleSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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

		return invokePostSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	protected KnowledgeBaseArticle invokePatchKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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
			return KnowledgeBaseArticleSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePatchKnowledgeBaseArticleResponse(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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

		return invokePostSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	protected KnowledgeBaseArticle invokePutKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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
			return KnowledgeBaseArticleSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutKnowledgeBaseArticleResponse(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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

		return invokePostSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
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
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle1);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null,
					getFilterString(
						entityField, "between", knowledgeBaseArticle1),
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

		return invokePostKnowledgeBaseArticleKnowledgeBaseArticle(
			parentKnowledgeBaseArticleId, knowledgeBaseArticle);
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

		return Page.of(string, KnowledgeBaseArticleSerDes::toDTO);
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

		return invokePostKnowledgeBaseArticleKnowledgeBaseArticle(
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId(),
			knowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			invokePostKnowledgeBaseArticleKnowledgeBaseArticle(
				Long parentKnowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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
			return KnowledgeBaseArticleSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle1);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null,
					getFilterString(
						entityField, "between", knowledgeBaseArticle1),
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

		return invokePostKnowledgeBaseFolderKnowledgeBaseArticle(
			knowledgeBaseFolderId, knowledgeBaseArticle);
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

		return Page.of(string, KnowledgeBaseArticleSerDes::toDTO);
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

		return invokePostKnowledgeBaseFolderKnowledgeBaseArticle(
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId(),
			knowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			invokePostKnowledgeBaseFolderKnowledgeBaseArticle(
				Long knowledgeBaseFolderId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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
			return KnowledgeBaseArticleSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
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

	@Test
	public void testGetSiteKnowledgeBaseArticlesPage() throws Exception {
		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteKnowledgeBaseArticlesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			KnowledgeBaseArticle irrelevantKnowledgeBaseArticle =
				testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
					irrelevantSiteId, randomIrrelevantKnowledgeBaseArticle());

			Page<KnowledgeBaseArticle> page =
				invokeGetSiteKnowledgeBaseArticlesPage(
					irrelevantSiteId, null, null, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseArticle),
				(List<KnowledgeBaseArticle>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page =
			invokeGetSiteKnowledgeBaseArticlesPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			(List<KnowledgeBaseArticle>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();

		knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle1);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetSiteKnowledgeBaseArticlesPage(
					siteId, null, null,
					getFilterString(
						entityField, "between", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				invokeGetSiteKnowledgeBaseArticlesPage(
					siteId, null, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle3 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page1 =
			invokeGetSiteKnowledgeBaseArticlesPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<KnowledgeBaseArticle> knowledgeBaseArticles1 =
			(List<KnowledgeBaseArticle>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles1.toString(), 2,
			knowledgeBaseArticles1.size());

		Page<KnowledgeBaseArticle> page2 =
			invokeGetSiteKnowledgeBaseArticlesPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

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
	public void testGetSiteKnowledgeBaseArticlesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

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
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetSiteKnowledgeBaseArticlesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetSiteKnowledgeBaseArticlesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

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
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				invokeGetSiteKnowledgeBaseArticlesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				invokeGetSiteKnowledgeBaseArticlesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	protected KnowledgeBaseArticle
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return invokePostSiteKnowledgeBaseArticle(siteId, knowledgeBaseArticle);
	}

	protected Long testGetSiteKnowledgeBaseArticlesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteKnowledgeBaseArticlesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<KnowledgeBaseArticle> invokeGetSiteKnowledgeBaseArticlesPage(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-articles", siteId);

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

		return Page.of(string, KnowledgeBaseArticleSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteKnowledgeBaseArticlesPageResponse(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-articles", siteId);

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
	public void testPostSiteKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPostSiteKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, postKnowledgeBaseArticle);
		assertValid(postKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPostSiteKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return invokePostSiteKnowledgeBaseArticle(
			testGetSiteKnowledgeBaseArticlesPage_getSiteId(),
			knowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle invokePostSiteKnowledgeBaseArticle(
			Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-articles", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return KnowledgeBaseArticleSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostSiteKnowledgeBaseArticleResponse(
			Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-articles", siteId);

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
		boolean valid = true;

		if (knowledgeBaseArticle.getDateCreated() == null) {
			valid = false;
		}

		if (knowledgeBaseArticle.getDateModified() == null) {
			valid = false;
		}

		if (knowledgeBaseArticle.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				knowledgeBaseArticle.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getArticleBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfAttachments", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getNumberOfAttachments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseArticles",
					additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolder", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getParentKnowledgeBaseFolder() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolderId", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getParentKnowledgeBaseFolderId() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getTaxonomyCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getViewableBy() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		KnowledgeBaseArticle knowledgeBaseArticle1,
		KnowledgeBaseArticle knowledgeBaseArticle2) {

		if (knowledgeBaseArticle1 == knowledgeBaseArticle2) {
			return true;
		}

		if (!Objects.equals(
				knowledgeBaseArticle1.getSiteId(),
				knowledgeBaseArticle2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getAggregateRating(),
						knowledgeBaseArticle2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getArticleBody(),
						knowledgeBaseArticle2.getArticleBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getCreator(),
						knowledgeBaseArticle2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getDateCreated(),
						knowledgeBaseArticle2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getDateModified(),
						knowledgeBaseArticle2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getDescription(),
						knowledgeBaseArticle2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getEncodingFormat(),
						knowledgeBaseArticle2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getFriendlyUrlPath(),
						knowledgeBaseArticle2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getId(),
						knowledgeBaseArticle2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getKeywords(),
						knowledgeBaseArticle2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfAttachments", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getNumberOfAttachments(),
						knowledgeBaseArticle2.getNumberOfAttachments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseArticles",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.
							getNumberOfKnowledgeBaseArticles(),
						knowledgeBaseArticle2.
							getNumberOfKnowledgeBaseArticles())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolder", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getParentKnowledgeBaseFolder(),
						knowledgeBaseArticle2.getParentKnowledgeBaseFolder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolderId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getParentKnowledgeBaseFolderId(),
						knowledgeBaseArticle2.
							getParentKnowledgeBaseFolderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getTaxonomyCategories(),
						knowledgeBaseArticle2.getTaxonomyCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getTaxonomyCategoryIds(),
						knowledgeBaseArticle2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getTitle(),
						knowledgeBaseArticle2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getViewableBy(),
						knowledgeBaseArticle2.getViewableBy())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
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

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseArticle.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseArticle.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(knowledgeBaseArticle.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseArticle.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseArticle.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(knowledgeBaseArticle.getDateModified()));
			}

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

		if (entityFieldName.equals("siteId")) {
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
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				parentKnowledgeBaseFolderId = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected KnowledgeBaseArticle randomIrrelevantKnowledgeBaseArticle() {
		KnowledgeBaseArticle randomIrrelevantKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		randomIrrelevantKnowledgeBaseArticle.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantKnowledgeBaseArticle;
	}

	protected KnowledgeBaseArticle randomPatchKnowledgeBaseArticle() {
		return randomKnowledgeBaseArticle();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
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