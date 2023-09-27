/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.headless.delivery.client.dto.v1_0.Field;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.delivery.client.serdes.v1_0.BlogPostingImageSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.File;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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

		blogPostingImageResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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
		blogPostingImage.setContentValue(regex);
		blogPostingImage.setEncodingFormat(regex);
		blogPostingImage.setFileExtension(regex);
		blogPostingImage.setTitle(regex);

		String json = BlogPostingImageSerDes.toJSON(blogPostingImage);

		Assert.assertFalse(json.contains(regex));

		blogPostingImage = BlogPostingImageSerDes.toDTO(json);

		Assert.assertEquals(regex, blogPostingImage.getContentUrl());
		Assert.assertEquals(regex, blogPostingImage.getContentValue());
		Assert.assertEquals(regex, blogPostingImage.getEncodingFormat());
		Assert.assertEquals(regex, blogPostingImage.getFileExtension());
		Assert.assertEquals(regex, blogPostingImage.getTitle());
	}

	@Test
	public void testDeleteBlogPostingImage() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
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
			testGraphQLDeleteBlogPostingImage_addBlogPostingImage();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteBlogPostingImage",
						new HashMap<String, Object>() {
							{
								put(
									"blogPostingImageId",
									blogPostingImage.getId());
							}
						})),
				"JSONObject/data", "Object/deleteBlogPostingImage"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"blogPostingImage",
					new HashMap<String, Object>() {
						{
							put("blogPostingImageId", blogPostingImage.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected BlogPostingImage
			testGraphQLDeleteBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return testGraphQLBlogPostingImage_addBlogPostingImage();
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
			testGraphQLGetBlogPostingImage_addBlogPostingImage();

		Assert.assertTrue(
			equals(
				blogPostingImage,
				BlogPostingImageSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"blogPostingImage",
								new HashMap<String, Object>() {
									{
										put(
											"blogPostingImageId",
											blogPostingImage.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/blogPostingImage"))));
	}

	@Test
	public void testGraphQLGetBlogPostingImageNotFound() throws Exception {
		Long irrelevantBlogPostingImageId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"blogPostingImage",
						new HashMap<String, Object>() {
							{
								put(
									"blogPostingImageId",
									irrelevantBlogPostingImageId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected BlogPostingImage
			testGraphQLGetBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return testGraphQLBlogPostingImage_addBlogPostingImage();
	}

	@Test
	public void testGetSiteBlogPostingImagesPage() throws Exception {
		Long siteId = testGetSiteBlogPostingImagesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteBlogPostingImagesPage_getIrrelevantSiteId();

		Page<BlogPostingImage> page =
			blogPostingImageResource.getSiteBlogPostingImagesPage(
				siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSiteId != null) {
			BlogPostingImage irrelevantBlogPostingImage =
				testGetSiteBlogPostingImagesPage_addBlogPostingImage(
					irrelevantSiteId, randomIrrelevantBlogPostingImage());

			page = blogPostingImageResource.getSiteBlogPostingImagesPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantBlogPostingImage),
				(List<BlogPostingImage>)page.getItems());
			assertValid(
				page,
				testGetSiteBlogPostingImagesPage_getExpectedActions(
					irrelevantSiteId));
		}

		BlogPostingImage blogPostingImage1 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		BlogPostingImage blogPostingImage2 =
			testGetSiteBlogPostingImagesPage_addBlogPostingImage(
				siteId, randomBlogPostingImage());

		page = blogPostingImageResource.getSiteBlogPostingImagesPage(
			siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPostingImage1, blogPostingImage2),
			(List<BlogPostingImage>)page.getItems());
		assertValid(
			page, testGetSiteBlogPostingImagesPage_getExpectedActions(siteId));

		blogPostingImageResource.deleteBlogPostingImage(
			blogPostingImage1.getId());

		blogPostingImageResource.deleteBlogPostingImage(
			blogPostingImage2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSiteBlogPostingImagesPage_getExpectedActions(Long siteId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/blog-posting-images/batch".
				replace("{siteId}", String.valueOf(siteId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
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
					siteId, null, null,
					getFilterString(entityField, "between", blogPostingImage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPostingImage1),
				(List<BlogPostingImage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithFilterDoubleEquals()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithFilterStringContains()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithFilterStringEquals()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithFilterStringStartsWith()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetSiteBlogPostingImagesPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

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
					siteId, null, null,
					getFilterString(entityField, operator, blogPostingImage1),
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
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<BlogPostingImage> blogPostingImages1 =
			(List<BlogPostingImage>)page1.getItems();

		Assert.assertEquals(
			blogPostingImages1.toString(), 2, blogPostingImages1.size());

		Page<BlogPostingImage> page2 =
			blogPostingImageResource.getSiteBlogPostingImagesPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<BlogPostingImage> blogPostingImages2 =
			(List<BlogPostingImage>)page2.getItems();

		Assert.assertEquals(
			blogPostingImages2.toString(), 1, blogPostingImages2.size());

		Page<BlogPostingImage> page3 =
			blogPostingImageResource.getSiteBlogPostingImagesPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

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
				BeanTestUtil.setProperty(
					blogPostingImage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithSortDouble()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, blogPostingImage1, blogPostingImage2) -> {
				BeanTestUtil.setProperty(
					blogPostingImage1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					blogPostingImage2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetSiteBlogPostingImagesPageWithSortInteger()
		throws Exception {

		testGetSiteBlogPostingImagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, blogPostingImage1, blogPostingImage2) -> {
				BeanTestUtil.setProperty(
					blogPostingImage1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
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

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						blogPostingImage1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						blogPostingImage2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						blogPostingImage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						blogPostingImage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						blogPostingImage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						blogPostingImage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
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
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPostingImage1, blogPostingImage2),
				(List<BlogPostingImage>)ascPage.getItems());

			Page<BlogPostingImage> descPage =
				blogPostingImageResource.getSiteBlogPostingImagesPage(
					siteId, null, null, null, Pagination.of(1, 2),
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
		Long siteId = testGetSiteBlogPostingImagesPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"blogPostingImages",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject blogPostingImagesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/blogPostingImages");

		Assert.assertEquals(0, blogPostingImagesJSONObject.get("totalCount"));

		BlogPostingImage blogPostingImage1 =
			testGraphQLGetSiteBlogPostingImagesPage_addBlogPostingImage();
		BlogPostingImage blogPostingImage2 =
			testGraphQLGetSiteBlogPostingImagesPage_addBlogPostingImage();

		blogPostingImagesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/blogPostingImages");

		Assert.assertEquals(
			2, blogPostingImagesJSONObject.getLong("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPostingImage1, blogPostingImage2),
			Arrays.asList(
				BlogPostingImageSerDes.toDTOs(
					blogPostingImagesJSONObject.getString("items"))));
	}

	protected BlogPostingImage
			testGraphQLGetSiteBlogPostingImagesPage_addBlogPostingImage()
		throws Exception {

		return testGraphQLBlogPostingImage_addBlogPostingImage();
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

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Object[]) {
			StringBuilder arraySB = new StringBuilder("[");

			for (Object object : (Object[])value) {
				if (arraySB.length() > 1) {
					arraySB.append(", ");
				}

				arraySB.append("{");

				Class<?> clazz = object.getClass();

				for (java.lang.reflect.Field field :
						getDeclaredFields(clazz.getSuperclass())) {

					arraySB.append(field.getName());
					arraySB.append(": ");

					appendGraphQLFieldValue(arraySB, field.get(object));

					arraySB.append(", ");
				}

				arraySB.setLength(arraySB.length() - 2);

				arraySB.append("}");
			}

			arraySB.append("]");

			sb.append(arraySB.toString());
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	protected BlogPostingImage testGraphQLBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return testGraphQLBlogPostingImage_addBlogPostingImage(
			randomBlogPostingImage());
	}

	protected BlogPostingImage testGraphQLBlogPostingImage_addBlogPostingImage(
			BlogPostingImage blogPostingImage)
		throws Exception {

		JSONDeserializer<BlogPostingImage> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (java.lang.reflect.Field field :
				getDeclaredFields(BlogPostingImage.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(blogPostingImage));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteBlogPostingImage",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("blogPostingImage", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteBlogPostingImage"),
			BlogPostingImage.class);
	}

	protected void assertContains(
		BlogPostingImage blogPostingImage,
		List<BlogPostingImage> blogPostingImages) {

		boolean contains = false;

		for (BlogPostingImage item : blogPostingImages) {
			if (equals(blogPostingImage, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			blogPostingImages + " does not contain " + blogPostingImage,
			contains);
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

	protected void assertValid(BlogPostingImage blogPostingImage)
		throws Exception {

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

			if (Objects.equals("contentValue", additionalAssertFieldName)) {
				if (blogPostingImage.getContentValue() == null) {
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
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<BlogPostingImage> page,
		Map<String, Map<String, String>> expectedActions) {

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

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.BlogPostingImage.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
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

			if (Objects.equals("contentValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPostingImage1.getContentValue(),
						blogPostingImage2.getContentValue())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
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

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
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
			Object object = blogPostingImage.getContentUrl();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("contentValue")) {
			Object object = blogPostingImage.getContentValue();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			Object object = blogPostingImage.getEncodingFormat();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			Object object = blogPostingImage.getFileExtension();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

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
			Object object = blogPostingImage.getTitle();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected BlogPostingImage randomBlogPostingImage() throws Exception {
		return new BlogPostingImage() {
			{
				contentUrl = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				contentValue = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				encodingFormat = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				fileExtension = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				sizeInBytes = RandomTestUtil.randomLong();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
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

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseBlogPostingImageResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource
		_blogPostingImageResource;

}