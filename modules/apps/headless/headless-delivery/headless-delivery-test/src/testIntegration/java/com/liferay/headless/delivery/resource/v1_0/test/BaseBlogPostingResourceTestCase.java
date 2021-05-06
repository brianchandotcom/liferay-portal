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

import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.BlogPostingResource;
import com.liferay.headless.delivery.client.serdes.v1_0.BlogPostingSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
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
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
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

import java.lang.reflect.Field;
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
public abstract class BaseBlogPostingResourceTestCase {

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

		_blogPostingResource.setContextCompany(testCompany);

		BlogPostingResource.Builder builder = BlogPostingResource.builder();

		blogPostingResource = builder.authentication(
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

		BlogPosting blogPosting1 = randomBlogPosting();

		String json = objectMapper.writeValueAsString(blogPosting1);

		BlogPosting blogPosting2 = BlogPostingSerDes.toDTO(json);

		Assert.assertTrue(equals(blogPosting1, blogPosting2));
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

		BlogPosting blogPosting = randomBlogPosting();

		String json1 = objectMapper.writeValueAsString(blogPosting);
		String json2 = BlogPostingSerDes.toJSON(blogPosting);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		BlogPosting blogPosting = randomBlogPosting();

		blogPosting.setAlternativeHeadline(regex);
		blogPosting.setArticleBody(regex);
		blogPosting.setDescription(regex);
		blogPosting.setEncodingFormat(regex);
		blogPosting.setFriendlyUrlPath(regex);
		blogPosting.setHeadline(regex);

		String json = BlogPostingSerDes.toJSON(blogPosting);

		Assert.assertFalse(json.contains(regex));

		blogPosting = BlogPostingSerDes.toDTO(json);

		Assert.assertEquals(regex, blogPosting.getAlternativeHeadline());
		Assert.assertEquals(regex, blogPosting.getArticleBody());
		Assert.assertEquals(regex, blogPosting.getDescription());
		Assert.assertEquals(regex, blogPosting.getEncodingFormat());
		Assert.assertEquals(regex, blogPosting.getFriendlyUrlPath());
		Assert.assertEquals(regex, blogPosting.getHeadline());
	}

	@Test
	public void testDeleteBlogPosting() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting = testDeleteBlogPosting_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.deleteBlogPostingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404,
			blogPostingResource.getBlogPostingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404, blogPostingResource.getBlogPostingHttpResponse(0L));
	}

	protected BlogPosting testDeleteBlogPosting_addBlogPosting()
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testGraphQLDeleteBlogPosting() throws Exception {
		BlogPosting blogPosting = testGraphQLBlogPosting_addBlogPosting();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteBlogPosting",
						new HashMap<String, Object>() {
							{
								put("blogPostingId", blogPosting.getId());
							}
						})),
				"JSONObject/data", "Object/deleteBlogPosting"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"blogPosting",
					new HashMap<String, Object>() {
						{
							put("blogPostingId", blogPosting.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testGetBlogPosting_addBlogPosting();

		BlogPosting getBlogPosting = blogPostingResource.getBlogPosting(
			postBlogPosting.getId());

		assertEquals(postBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testGetBlogPosting_addBlogPosting() throws Exception {
		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testGraphQLGetBlogPosting() throws Exception {
		BlogPosting blogPosting = testGraphQLBlogPosting_addBlogPosting();

		Assert.assertTrue(
			equals(
				blogPosting,
				BlogPostingSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"blogPosting",
								new HashMap<String, Object>() {
									{
										put(
											"blogPostingId",
											blogPosting.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/blogPosting"))));
	}

	@Test
	public void testGraphQLGetBlogPostingNotFound() throws Exception {
		Long irrelevantBlogPostingId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"blogPosting",
						new HashMap<String, Object>() {
							{
								put("blogPostingId", irrelevantBlogPostingId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testPatchBlogPosting_addBlogPosting();

		BlogPosting randomPatchBlogPosting = randomPatchBlogPosting();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting patchBlogPosting = blogPostingResource.patchBlogPosting(
			postBlogPosting.getId(), randomPatchBlogPosting);

		BlogPosting expectedPatchBlogPosting = postBlogPosting.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchBlogPosting, randomPatchBlogPosting);

		BlogPosting getBlogPosting = blogPostingResource.getBlogPosting(
			patchBlogPosting.getId());

		assertEquals(expectedPatchBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testPatchBlogPosting_addBlogPosting()
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testPutBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testPutBlogPosting_addBlogPosting();

		BlogPosting randomBlogPosting = randomBlogPosting();

		BlogPosting putBlogPosting = blogPostingResource.putBlogPosting(
			postBlogPosting.getId(), randomBlogPosting);

		assertEquals(randomBlogPosting, putBlogPosting);
		assertValid(putBlogPosting);

		BlogPosting getBlogPosting = blogPostingResource.getBlogPosting(
			putBlogPosting.getId());

		assertEquals(randomBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testPutBlogPosting_addBlogPosting() throws Exception {
		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testDeleteBlogPostingMyRating() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting =
			testDeleteBlogPostingMyRating_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.deleteBlogPostingMyRatingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404,
			blogPostingResource.getBlogPostingMyRatingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404, blogPostingResource.getBlogPostingMyRatingHttpResponse(0L));
	}

	protected BlogPosting testDeleteBlogPostingMyRating_addBlogPosting()
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testGetBlogPostingPermissionsPage() throws Exception {
		BlogPosting postBlogPosting =
			testGetBlogPostingPermissionsPage_addBlogPosting();

		Page<Permission> page =
			blogPostingResource.getBlogPostingPermissionsPage(
				postBlogPosting.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected BlogPosting testGetBlogPostingPermissionsPage_addBlogPosting()
		throws Exception {

		return testPostSiteBlogPosting_addBlogPosting(randomBlogPosting());
	}

	@Test
	public void testPutBlogPostingPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting = testPutBlogPostingPermission_addBlogPosting();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			blogPostingResource.putBlogPostingPermissionHttpResponse(
				blogPosting.getId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"VIEW"});
							setRoleName(role.getName());
						}
					}
				}));

		assertHttpResponseStatusCode(
			404,
			blogPostingResource.putBlogPostingPermissionHttpResponse(
				0L,
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"-"});
							setRoleName("-");
						}
					}
				}));
	}

	protected BlogPosting testPutBlogPostingPermission_addBlogPosting()
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testGetBlogPostingRenderedContentByDisplayPageDisplayPageKey()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetSiteBlogPostingsPage() throws Exception {
		Page<BlogPosting> page = blogPostingResource.getSiteBlogPostingsPage(
			testGetSiteBlogPostingsPage_getSiteId(),
			RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteBlogPostingsPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			BlogPosting irrelevantBlogPosting =
				testGetSiteBlogPostingsPage_addBlogPosting(
					irrelevantSiteId, randomIrrelevantBlogPosting());

			page = blogPostingResource.getSiteBlogPostingsPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantBlogPosting),
				(List<BlogPosting>)page.getItems());
			assertValid(page);
		}

		BlogPosting blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		BlogPosting blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		page = blogPostingResource.getSiteBlogPostingsPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPosting1, blogPosting2),
			(List<BlogPosting>)page.getItems());
		assertValid(page);

		blogPostingResource.deleteBlogPosting(blogPosting1.getId());

		blogPostingResource.deleteBlogPosting(blogPosting2.getId());
	}

	@Test
	public void testGetSiteBlogPostingsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = randomBlogPosting();

		blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting1);

		for (EntityField entityField : entityFields) {
			Page<BlogPosting> page =
				blogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null,
					getFilterString(entityField, "between", blogPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPosting1),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		for (EntityField entityField : entityFields) {
			Page<BlogPosting> page =
				blogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null,
					getFilterString(entityField, "eq", blogPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPosting1),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingsPageWithPagination() throws Exception {
		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		BlogPosting blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		BlogPosting blogPosting3 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		Page<BlogPosting> page1 = blogPostingResource.getSiteBlogPostingsPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		List<BlogPosting> blogPostings1 = (List<BlogPosting>)page1.getItems();

		Assert.assertEquals(blogPostings1.toString(), 2, blogPostings1.size());

		Page<BlogPosting> page2 = blogPostingResource.getSiteBlogPostingsPage(
			siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<BlogPosting> blogPostings2 = (List<BlogPosting>)page2.getItems();

		Assert.assertEquals(blogPostings2.toString(), 1, blogPostings2.size());

		Page<BlogPosting> page3 = blogPostingResource.getSiteBlogPostingsPage(
			siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPosting1, blogPosting2, blogPosting3),
			(List<BlogPosting>)page3.getItems());
	}

	@Test
	public void testGetSiteBlogPostingsPageWithSortDateTime() throws Exception {
		testGetSiteBlogPostingsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, blogPosting1, blogPosting2) -> {
				BeanUtils.setProperty(
					blogPosting1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteBlogPostingsPageWithSortInteger() throws Exception {
		testGetSiteBlogPostingsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, blogPosting1, blogPosting2) -> {
				BeanUtils.setProperty(blogPosting1, entityField.getName(), 0);
				BeanUtils.setProperty(blogPosting2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteBlogPostingsPageWithSortString() throws Exception {
		testGetSiteBlogPostingsPageWithSort(
			EntityField.Type.STRING,
			(entityField, blogPosting1, blogPosting2) -> {
				Class<?> clazz = blogPosting1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						blogPosting1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						blogPosting2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						blogPosting1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						blogPosting2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						blogPosting1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						blogPosting2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteBlogPostingsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, BlogPosting, BlogPosting, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = randomBlogPosting();
		BlogPosting blogPosting2 = randomBlogPosting();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, blogPosting1, blogPosting2);
		}

		blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting1);

		blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting2);

		for (EntityField entityField : entityFields) {
			Page<BlogPosting> ascPage =
				blogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPosting1, blogPosting2),
				(List<BlogPosting>)ascPage.getItems());

			Page<BlogPosting> descPage =
				blogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(blogPosting2, blogPosting1),
				(List<BlogPosting>)descPage.getItems());
		}
	}

	protected BlogPosting testGetSiteBlogPostingsPage_addBlogPosting(
			Long siteId, BlogPosting blogPosting)
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(siteId, blogPosting);
	}

	protected Long testGetSiteBlogPostingsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteBlogPostingsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteBlogPostingsPage() throws Exception {
		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"blogPostings",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject blogPostingsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/blogPostings");

		Assert.assertEquals(0, blogPostingsJSONObject.get("totalCount"));

		BlogPosting blogPosting1 = testGraphQLBlogPosting_addBlogPosting();
		BlogPosting blogPosting2 = testGraphQLBlogPosting_addBlogPosting();

		blogPostingsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/blogPostings");

		Assert.assertEquals(2, blogPostingsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPosting1, blogPosting2),
			Arrays.asList(
				BlogPostingSerDes.toDTOs(
					blogPostingsJSONObject.getString("items"))));
	}

	@Test
	public void testPostSiteBlogPosting() throws Exception {
		BlogPosting randomBlogPosting = randomBlogPosting();

		BlogPosting postBlogPosting = testPostSiteBlogPosting_addBlogPosting(
			randomBlogPosting);

		assertEquals(randomBlogPosting, postBlogPosting);
		assertValid(postBlogPosting);
	}

	protected BlogPosting testPostSiteBlogPosting_addBlogPosting(
			BlogPosting blogPosting)
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGetSiteBlogPostingsPage_getSiteId(), blogPosting);
	}

	@Test
	public void testGraphQLPostSiteBlogPosting() throws Exception {
		BlogPosting randomBlogPosting = randomBlogPosting();

		BlogPosting blogPosting = testGraphQLBlogPosting_addBlogPosting(
			randomBlogPosting);

		Assert.assertTrue(equals(randomBlogPosting, blogPosting));
	}

	@Test
	public void testGetSiteBlogPostingPermissionsPage() throws Exception {
		Page<Permission> page =
			blogPostingResource.getSiteBlogPostingPermissionsPage(
				testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected BlogPosting testGetSiteBlogPostingPermissionsPage_addBlogPosting()
		throws Exception {

		return testPostSiteBlogPosting_addBlogPosting(randomBlogPosting());
	}

	@Test
	public void testPutSiteBlogPostingPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting =
			testPutSiteBlogPostingPermission_addBlogPosting();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			blogPostingResource.putSiteBlogPostingPermissionHttpResponse(
				blogPosting.getSiteId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"PERMISSIONS"});
							setRoleName(role.getName());
						}
					}
				}));

		assertHttpResponseStatusCode(
			404,
			blogPostingResource.putSiteBlogPostingPermissionHttpResponse(
				blogPosting.getSiteId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"-"});
							setRoleName("-");
						}
					}
				}));
	}

	protected BlogPosting testPutSiteBlogPostingPermission_addBlogPosting()
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testPutSiteBlogPostingSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting =
			testPutSiteBlogPostingSubscribe_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.putSiteBlogPostingSubscribeHttpResponse(
				blogPosting.getSiteId()));

		assertHttpResponseStatusCode(
			404,
			blogPostingResource.putSiteBlogPostingSubscribeHttpResponse(
				blogPosting.getSiteId()));
	}

	protected BlogPosting testPutSiteBlogPostingSubscribe_addBlogPosting()
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testPutSiteBlogPostingUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting =
			testPutSiteBlogPostingUnsubscribe_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			blogPostingResource.putSiteBlogPostingUnsubscribeHttpResponse(
				blogPosting.getSiteId()));

		assertHttpResponseStatusCode(
			404,
			blogPostingResource.putSiteBlogPostingUnsubscribeHttpResponse(
				blogPosting.getSiteId()));
	}

	protected BlogPosting testPutSiteBlogPostingUnsubscribe_addBlogPosting()
		throws Exception {

		return blogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Test
	public void testGetBlogPostingMyRating() throws Exception {
		BlogPosting postBlogPosting = testGetBlogPosting_addBlogPosting();

		Rating postRating = testGetBlogPostingMyRating_addRating(
			postBlogPosting.getId(), randomRating());

		Rating getRating = blogPostingResource.getBlogPostingMyRating(
			postBlogPosting.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetBlogPostingMyRating_addRating(
			long blogPostingId, Rating rating)
		throws Exception {

		return blogPostingResource.postBlogPostingMyRating(
			blogPostingId, rating);
	}

	@Test
	public void testPostBlogPostingMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutBlogPostingMyRating() throws Exception {
		BlogPosting postBlogPosting = testPutBlogPosting_addBlogPosting();

		testPutBlogPostingMyRating_addRating(
			postBlogPosting.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating = blogPostingResource.putBlogPostingMyRating(
			postBlogPosting.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);
	}

	protected Rating testPutBlogPostingMyRating_addRating(
			long blogPostingId, Rating rating)
		throws Exception {

		return blogPostingResource.postBlogPostingMyRating(
			blogPostingId, rating);
	}

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

				for (Field field : getDeclaredFields(clazz.getSuperclass())) {
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

	protected BlogPosting testGraphQLBlogPosting_addBlogPosting()
		throws Exception {

		return testGraphQLBlogPosting_addBlogPosting(randomBlogPosting());
	}

	protected BlogPosting testGraphQLBlogPosting_addBlogPosting(
			BlogPosting blogPosting)
		throws Exception {

		JSONDeserializer<BlogPosting> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : getDeclaredFields(BlogPosting.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(blogPosting));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteBlogPosting",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("blogPosting", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteBlogPosting"),
			BlogPosting.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		Assert.assertTrue(
			blogPosting1 + " does not equal " + blogPosting2,
			equals(blogPosting1, blogPosting2));
	}

	protected void assertEquals(
		List<BlogPosting> blogPostings1, List<BlogPosting> blogPostings2) {

		Assert.assertEquals(blogPostings1.size(), blogPostings2.size());

		for (int i = 0; i < blogPostings1.size(); i++) {
			BlogPosting blogPosting1 = blogPostings1.get(i);
			BlogPosting blogPosting2 = blogPostings2.get(i);

			assertEquals(blogPosting1, blogPosting2);
		}
	}

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
	}

	protected void assertEqualsIgnoringOrder(
		List<BlogPosting> blogPostings1, List<BlogPosting> blogPostings2) {

		Assert.assertEquals(blogPostings1.size(), blogPostings2.size());

		for (BlogPosting blogPosting1 : blogPostings1) {
			boolean contains = false;

			for (BlogPosting blogPosting2 : blogPostings2) {
				if (equals(blogPosting1, blogPosting2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				blogPostings2 + " does not contain " + blogPosting1, contains);
		}
	}

	protected void assertValid(BlogPosting blogPosting) throws Exception {
		boolean valid = true;

		if (blogPosting.getDateCreated() == null) {
			valid = false;
		}

		if (blogPosting.getDateModified() == null) {
			valid = false;
		}

		if (blogPosting.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(blogPosting.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (blogPosting.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (blogPosting.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"alternativeHeadline", additionalAssertFieldName)) {

				if (blogPosting.getAlternativeHeadline() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (blogPosting.getArticleBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (blogPosting.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (blogPosting.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (blogPosting.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (blogPosting.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (blogPosting.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (blogPosting.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (blogPosting.getHeadline() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (blogPosting.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (blogPosting.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (blogPosting.getNumberOfComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (blogPosting.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("renderedContents", additionalAssertFieldName)) {
				if (blogPosting.getRenderedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (blogPosting.getTaxonomyCategoryBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (blogPosting.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (blogPosting.getViewableBy() == null) {
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

	protected void assertValid(Page<BlogPosting> page) {
		boolean valid = false;

		java.util.Collection<BlogPosting> blogPostings = page.getItems();

		int size = blogPostings.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Rating rating) {
		boolean valid = true;

		if (rating.getDateCreated() == null) {
			valid = false;
		}

		if (rating.getDateModified() == null) {
			valid = false;
		}

		if (rating.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (rating.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (rating.getBestRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (rating.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (rating.getRatingValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (rating.getWorstRating() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalRatingAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.BlogPosting.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
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
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		if (blogPosting1 == blogPosting2) {
			return true;
		}

		if (!Objects.equals(
				blogPosting1.getSiteId(), blogPosting2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)blogPosting1.getActions(),
						(Map)blogPosting2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getAggregateRating(),
						blogPosting2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"alternativeHeadline", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						blogPosting1.getAlternativeHeadline(),
						blogPosting2.getAlternativeHeadline())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getArticleBody(),
						blogPosting2.getArticleBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getCreator(), blogPosting2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getCustomFields(),
						blogPosting2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDateCreated(),
						blogPosting2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDateModified(),
						blogPosting2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDatePublished(),
						blogPosting2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDescription(),
						blogPosting2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getEncodingFormat(),
						blogPosting2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getFriendlyUrlPath(),
						blogPosting2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getHeadline(),
						blogPosting2.getHeadline())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getId(), blogPosting2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getImage(), blogPosting2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getKeywords(),
						blogPosting2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getNumberOfComments(),
						blogPosting2.getNumberOfComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getRelatedContents(),
						blogPosting2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("renderedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getRenderedContents(),
						blogPosting2.getRenderedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						blogPosting1.getTaxonomyCategoryBriefs(),
						blogPosting2.getTaxonomyCategoryBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						blogPosting1.getTaxonomyCategoryIds(),
						blogPosting2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getViewableBy(),
						blogPosting2.getViewableBy())) {

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

	protected boolean equals(Rating rating1, Rating rating2) {
		if (rating1 == rating2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getActions(), rating2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getBestRating(), rating2.getBestRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getCreator(), rating2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateCreated(), rating2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateModified(), rating2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(rating1.getId(), rating2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getRatingValue(), rating2.getRatingValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getWorstRating(), rating2.getWorstRating())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_blogPostingResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_blogPostingResource;

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
		EntityField entityField, String operator, BlogPosting blogPosting) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("alternativeHeadline")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getAlternativeHeadline()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getArticleBody()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
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
							blogPosting.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(blogPosting.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(blogPosting.getDateCreated()));
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
							blogPosting.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							blogPosting.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(blogPosting.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("datePublished")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							blogPosting.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							blogPosting.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(blogPosting.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("headline")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getHeadline()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("image")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("relatedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("renderedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryBriefs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
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

	protected BlogPosting randomBlogPosting() throws Exception {
		return new BlogPosting() {
			{
				alternativeHeadline = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				articleBody = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				encodingFormat = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				friendlyUrlPath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				headline = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				numberOfComments = RandomTestUtil.randomInt();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected BlogPosting randomIrrelevantBlogPosting() throws Exception {
		BlogPosting randomIrrelevantBlogPosting = randomBlogPosting();

		randomIrrelevantBlogPosting.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantBlogPosting;
	}

	protected BlogPosting randomPatchBlogPosting() throws Exception {
		return randomBlogPosting();
	}

	protected Rating randomRating() throws Exception {
		return new Rating() {
			{
				bestRating = RandomTestUtil.randomDouble();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				ratingValue = RandomTestUtil.randomDouble();
				worstRating = RandomTestUtil.randomDouble();
			}
		};
	}

	protected BlogPostingResource blogPostingResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBlogPostingResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.BlogPostingResource
		_blogPostingResource;

}