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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.delivery.client.serdes.v1_0.BlogPostingImageSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Level;

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
public abstract class BaseBlogPostingImageResourceTestCase {

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_blogPostingImageResource.setContextCompany(testCompany);

		BlogPostingImageResource.Builder builder =
			BlogPostingImageResource.builder();

		blogPostingImageResource = builder.locale(
			LocaleUtil.getDefault()
		).build();
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
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		BlogPostingImage blogPostingImage1 = randomBlogPostingImage();

		String json = objectMapper.writeValueAsString(blogPostingImage1);

		BlogPostingImage blogPostingImage2 = BlogPostingImageSerDes.toDTO(json);

		Assert.assertTrue(equals(blogPostingImage1, blogPostingImage2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		BlogPostingImage blogPostingImage = randomBlogPostingImage();

		String json1 = objectMapper.writeValueAsString(blogPostingImage);
		String json2 = BlogPostingImageSerDes.toJSON(blogPostingImage);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		BlogPostingImage blogPostingImage = randomBlogPostingImage();

		blogPostingImage.setContentUrl(regex);
		blogPostingImage.setEncodingFormat(regex);
		blogPostingImage.setFileExtension(regex);
		blogPostingImage.setTitle(regex);

		String json = BlogPostingImageSerDes.toJSON(blogPostingImage);

		Assert.assertFalse(json.contains(regex));

		blogPostingImage = BlogPostingImageSerDes.toDTO(json);

		Assert.assertEquals(regex, blogPostingImage.getContentUrl());
		Assert.assertEquals(regex, blogPostingImage.getEncodingFormat());
		Assert.assertEquals(regex, blogPostingImage.getFileExtension());
		Assert.assertEquals(regex, blogPostingImage.getTitle());
	}

	@Test
	public void testDeleteBlogPostingImage() throws Exception {
		BlogPostingImage blogPostingImage =
			testDeleteBlogPostingImage_addBlogPostingImage();

		assertHttpResponseStatusCode(
			204,
			blogPostingImageResource.deleteBlogPostingImageHttpResponse(
				blogPostingImage.getId()));

		assertHttpResponseStatusCode(
			404,
			blogPostingImageResource.getBlogPostingImageHttpResponse(
				blogPostingImage.getId()));

		assertHttpResponseStatusCode(
			404, blogPostingImageResource.getBlogPostingImageHttpResponse(0L));
	}

	protected BlogPostingImage testDeleteBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return blogPostingImageResource.postSiteBlogPostingImage(
			testGroup.getGroupId(), randomBlogPostingImage(),
			getMultipartFiles());
	}

	@Test
	public void testGraphQLDeleteBlogPostingImage() throws Exception {
		BlogPostingImage blogPostingImage =
			testGraphQLBlogPostingImage_addBlogPostingImage();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteBlogPostingImage",
				new HashMap<String, Object>() {
					{
						put("blogPostingImageId", blogPostingImage.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteBlogPostingImage"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"blogPostingImage",
					new HashMap<String, Object>() {
						{
							put("blogPostingImageId", blogPostingImage.getId());
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetBlogPostingImage() throws Exception {
		BlogPostingImage postBlogPostingImage =
			testGetBlogPostingImage_addBlogPostingImage();

		BlogPostingImage getBlogPostingImage =
			blogPostingImageResource.getBlogPostingImage(
				postBlogPostingImage.getId());

		assertEquals(postBlogPostingImage, getBlogPostingImage);
		assertValid(getBlogPostingImage);
	}

	protected BlogPostingImage testGetBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return blogPostingImageResource.postSiteBlogPostingImage(
			testGroup.getGroupId(), randomBlogPostingImage(),
			getMultipartFiles());
	}

	@Test
	public void testGraphQLGetBlogPostingImage() throws Exception {
		BlogPostingImage blogPostingImage =
			testGraphQLBlogPostingImage_addBlogPostingImage();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"blogPostingImage",
				new HashMap<String, Object>() {
					{
						put("blogPostingImageId", blogPostingImage.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				blogPostingImage,
				dataJSONObject.getJSONObject("blogPostingImage")));
	}

	@Test
	public void testGetSiteBlogPostingImagesPage() throws Exception {
		Page<BlogPostingImage> page =
			blogPostingImageResource.getSiteBlogPostingImagesPage(
				testGetSiteBlogPostingImagesPage_getSiteId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteBlogPostingImagesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteBlogPostingImagesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			BlogPostingImage irrelevantBlogPostingImage =
				testGetSiteBlogPostingImagesPage_addBlogPostingImage(
					irrelevantSiteId, randomIrrelevantBlogPostingImage());

			page = blogPostingImageResource.getSiteBlogPostingImagesPage(
				irrelevantSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantBlogPostingImage),
				(List<BlogPostingImage>)page.getItems());
			assertValid(page);
		}

		BlogPostingImage blogPostingImage1 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		BlogPostingImage blogPostingImage2 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		page = blogPostingImageResource.getSiteBlogPostingImagesPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPostingImage1, blogPostingImage2),
			(List<BlogPostingImage>)page.getItems());
		assertValid(page);

		blogPostingImageResource.deleteBlogPostingImage(
			blogPostingImage1.getId());

		blogPostingImageResource.deleteBlogPostingImage(
			blogPostingImage2.getId());
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingImagesPage_getSiteId();

		BlogPostingImage blogPostingImage1 = randomBlogPostingImage();

		blogPostingImage1 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, blogPostingImage1);

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> page =
				blogPostingImageResource.getSiteBlogPostingImagesPage(
					siteId, null,
					getFilterString(entityField, "between", blogPostingImage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPostingImage1),
				(List<BlogPostingImage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingImagesPage_getSiteId();

		BlogPostingImage blogPostingImage1 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPostingImage blogPostingImage2 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> page =
				blogPostingImageResource.getSiteBlogPostingImagesPage(
					siteId, null,
					getFilterString(entityField, "eq", blogPostingImage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPostingImage1),
				(List<BlogPostingImage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteBlogPostingImagesPage_getSiteId();

		BlogPostingImage blogPostingImage1 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		BlogPostingImage blogPostingImage2 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		BlogPostingImage blogPostingImage3 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		Page<BlogPostingImage> page1 =
			blogPostingImageResource.getSiteBlogPostingImagesPage(
				siteId, null, null, Pagination.of(1, 2), null);

		List<BlogPostingImage> blogPostingImages1 =
			(List<BlogPostingImage>)page1.getItems();

		Assert.assertEquals(
			blogPostingImages1.toString(), 2, blogPostingImages1.size());

		Page<BlogPostingImage> page2 =
			blogPostingImageResource.getSiteBlogPostingImagesPage(
				siteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<BlogPostingImage> blogPostingImages2 =
			(List<BlogPostingImage>)page2.getItems();

		Assert.assertEquals(
			blogPostingImages2.toString(), 1, blogPostingImages2.size());

		Page<BlogPostingImage> page3 =
			blogPostingImageResource.getSiteBlogPostingImagesPage(
				siteId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				blogPostingImage1, blogPostingImage2, blogPostingImage3),
			(List<BlogPostingImage>)page3.getItems());
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithSortDateTime()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, blogPostingImage1, blogPostingImage2) -> {
				BeanUtils.setProperty(
					blogPostingImage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithSortInteger()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, blogPostingImage1, blogPostingImage2) -> {
				BeanUtils.setProperty(
					blogPostingImage1, entityField.getName(), 0);
				BeanUtils.setProperty(
					blogPostingImage2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithSortString()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithSort(
			EntityField.Type.STRING,
			(entityField, blogPostingImage1, blogPostingImage2) -> {
				Class<?> clazz = blogPostingImage1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						blogPostingImage1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						blogPostingImage2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						blogPostingImage1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						blogPostingImage2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetSiteBlogPostingImagesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, BlogPostingImage, BlogPostingImage, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingImagesPage_getSiteId();

		BlogPostingImage blogPostingImage1 = randomBlogPostingImage();
		BlogPostingImage blogPostingImage2 = randomBlogPostingImage();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, blogPostingImage1, blogPostingImage2);
		}

		blogPostingImage1 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, blogPostingImage1);

		blogPostingImage2 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, blogPostingImage2);

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> ascPage =
				blogPostingImageResource.getSiteBlogPostingImagesPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPostingImage1, blogPostingImage2),
				(List<BlogPostingImage>)ascPage.getItems());

			Page<BlogPostingImage> descPage =
				blogPostingImageResource.getSiteBlogPostingImagesPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(blogPostingImage2, blogPostingImage1),
				(List<BlogPostingImage>)descPage.getItems());
		}
	}

	protected BlogPostingImage
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				Long siteId, BlogPostingImage blogPostingImage)
		throws Exception {

		return blogPostingImageResource.postSiteBlogPostingImage(
			siteId, blogPostingImage, getMultipartFiles());
	}

	protected Long testGetSiteBlogPostingImagesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteBlogPostingImagesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteBlogPostingImagesPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"blogPostingImages",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject blogPostingImagesJSONObject = dataJSONObject.getJSONObject(
			"blogPostingImages");

		Assert.assertEquals(0, blogPostingImagesJSONObject.get("totalCount"));

		BlogPostingImage blogPostingImage1 =
			testGraphQLBlogPostingImage_addBlogPostingImage();
		BlogPostingImage blogPostingImage2 =
			testGraphQLBlogPostingImage_addBlogPostingImage();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		blogPostingImagesJSONObject = dataJSONObject.getJSONObject(
			"blogPostingImages");

		Assert.assertEquals(2, blogPostingImagesJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(blogPostingImage1, blogPostingImage2),
			blogPostingImagesJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostSiteBlogPostingImage() throws Exception {
		BlogPostingImage randomBlogPostingImage = randomBlogPostingImage();

		Map<String, File> multipartFiles = getMultipartFiles();

		BlogPostingImage postBlogPostingImage =
			testPostSiteBlogPostingImage_addBlogPostingImage(
				randomBlogPostingImage, multipartFiles);

		assertEquals(randomBlogPostingImage, postBlogPostingImage);
		assertValid(postBlogPostingImage);

		assertValid(postBlogPostingImage, multipartFiles);
	}

	protected BlogPostingImage testPostSiteBlogPostingImage_addBlogPostingImage(
			BlogPostingImage blogPostingImage, Map<String, File> multipartFiles)
		throws Exception {

		return blogPostingImageResource.postSiteBlogPostingImage(
			testGetSiteBlogPostingImagesPage_getSiteId(), blogPostingImage,
			multipartFiles);
	}

	@Test
	public void testGraphQLPostSiteBlogPostingImage() throws Exception {
		BlogPostingImage randomBlogPostingImage = randomBlogPostingImage();

		BlogPostingImage blogPostingImage =
			testGraphQLBlogPostingImage_addBlogPostingImage(
				randomBlogPostingImage);

		Assert.assertTrue(
			equalsJSONObject(
				randomBlogPostingImage,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(blogPostingImage))));
	}

	protected BlogPostingImage testGraphQLBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return testGraphQLBlogPostingImage_addBlogPostingImage(
			randomBlogPostingImage());
	}

	protected BlogPostingImage testGraphQLBlogPostingImage_addBlogPostingImage(
			BlogPostingImage blogPostingImage)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = blogPostingImage.getContentUrl();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = blogPostingImage.getEncodingFormat();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = blogPostingImage.getFileExtension();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = blogPostingImage.getId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = blogPostingImage.getSizeInBytes();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = blogPostingImage.getTitle();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"createSiteBlogPostingImage",
				new HashMap<String, Object>() {
					{
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
						put("blogPostingImage", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<BlogPostingImage> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		return jsonDeserializer.deserialize(
			String.valueOf(
				dataJSONObject.getJSONObject("createSiteBlogPostingImage")),
			BlogPostingImage.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		BlogPostingImage blogPostingImage1,
		BlogPostingImage blogPostingImage2) {

		Assert.assertTrue(
			blogPostingImage1 + " does not equal " + blogPostingImage2,
			equals(blogPostingImage1, blogPostingImage2));
	}

	protected void assertEquals(
		List<BlogPostingImage> blogPostingImages1,
		List<BlogPostingImage> blogPostingImages2) {

		Assert.assertEquals(
			blogPostingImages1.size(), blogPostingImages2.size());

		for (int i = 0; i < blogPostingImages1.size(); i++) {
			BlogPostingImage blogPostingImage1 = blogPostingImages1.get(i);
			BlogPostingImage blogPostingImage2 = blogPostingImages2.get(i);

			assertEquals(blogPostingImage1, blogPostingImage2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BlogPostingImage> blogPostingImages1,
		List<BlogPostingImage> blogPostingImages2) {

		Assert.assertEquals(
			blogPostingImages1.size(), blogPostingImages2.size());

		for (BlogPostingImage blogPostingImage1 : blogPostingImages1) {
			boolean contains = false;

			for (BlogPostingImage blogPostingImage2 : blogPostingImages2) {
				if (equals(blogPostingImage1, blogPostingImage2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				blogPostingImages2 + " does not contain " + blogPostingImage1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<BlogPostingImage> blogPostingImages, JSONArray jsonArray) {

		for (BlogPostingImage blogPostingImage : blogPostingImages) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(blogPostingImage, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + blogPostingImage, contains);
		}
	}

	protected void assertValid(BlogPostingImage blogPostingImage) {
		boolean valid = true;

		if (blogPostingImage.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (blogPostingImage.getContentUrl() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (blogPostingImage.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (blogPostingImage.getFileExtension() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (blogPostingImage.getSizeInBytes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (blogPostingImage.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (blogPostingImage.getViewableBy() == null) {
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

	protected void assertValid(
			BlogPostingImage blogPostingImage, Map<String, File> multipartFiles)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<BlogPostingImage> page) {
		boolean valid = false;

		java.util.Collection<BlogPostingImage> blogPostingImages =
			page.getItems();

		int size = blogPostingImages.size();

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

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		BlogPostingImage blogPostingImage1,
		BlogPostingImage blogPostingImage2) {

		if (blogPostingImage1 == blogPostingImage2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getContentUrl(),
						blogPostingImage2.getContentUrl())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getEncodingFormat(),
						blogPostingImage2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getFileExtension(),
						blogPostingImage2.getFileExtension())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getId(), blogPostingImage2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getSizeInBytes(),
						blogPostingImage2.getSizeInBytes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getTitle(),
						blogPostingImage2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getViewableBy(),
						blogPostingImage2.getViewableBy())) {

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

	protected boolean equalsJSONObject(
		BlogPostingImage blogPostingImage, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("contentUrl", fieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage.getContentUrl(),
						jsonObject.getString("contentUrl"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", fieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage.getEncodingFormat(),
						jsonObject.getString("encodingFormat"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", fieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage.getFileExtension(),
						jsonObject.getString("fileExtension"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", fieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage.getSizeInBytes(),
						jsonObject.getLong("sizeInBytes"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", fieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage.getTitle(),
						jsonObject.getString("title"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_blogPostingImageResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_blogPostingImageResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		BlogPostingImage blogPostingImage) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getFileExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getTitle()));
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

	protected Map<String, File> getMultipartFiles() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected BlogPostingImage randomBlogPostingImage() throws Exception {
		return new BlogPostingImage() {
			{
				contentUrl = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				sizeInBytes = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected BlogPostingImage randomIrrelevantBlogPostingImage()
		throws Exception {

		BlogPostingImage randomIrrelevantBlogPostingImage =
			randomBlogPostingImage();

		return randomIrrelevantBlogPostingImage;
	}

	protected BlogPostingImage randomPatchBlogPostingImage() throws Exception {
		return randomBlogPostingImage();
	}

	protected BlogPostingImageResource blogPostingImageResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBlogPostingImageResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource
		_blogPostingImageResource;

}