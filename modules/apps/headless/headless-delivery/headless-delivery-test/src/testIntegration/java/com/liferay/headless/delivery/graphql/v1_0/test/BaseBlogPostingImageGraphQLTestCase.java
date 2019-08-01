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

package com.liferay.headless.delivery.graphql.v1_0.test;

import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseBlogPostingImageGraphQLTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteBlogPostingImage() throws Exception {
		BlogPostingImage blogPostingImage =
			testBlogPostingImage_addBlogPostingImage();

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

		JSONArray errors = jsonObject.getJSONArray("errors");

		Assert.assertTrue(errors.length() > 0);
	}

	@Test
	public void testGetBlogPostingImage() throws Exception {
		BlogPostingImage blogPostingImage =
			testBlogPostingImage_addBlogPostingImage();

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
			equals(
				blogPostingImage,
				dataJSONObject.getJSONObject("blogPostingImage")));
	}

	@Test
	public void testGetSiteBlogPostingImagesPage() throws Exception {
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
						put("siteId", testGroup.getGroupId());
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
			testBlogPostingImage_addBlogPostingImage();
		BlogPostingImage blogPostingImage2 =
			testBlogPostingImage_addBlogPostingImage();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		blogPostingImagesJSONObject = dataJSONObject.getJSONObject(
			"blogPostingImages");

		Assert.assertEquals(2, blogPostingImagesJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPostingImage1, blogPostingImage2),
			blogPostingImagesJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostSiteBlogPostingImage() throws Exception {
		BlogPostingImage randomBlogPostingImage = randomBlogPostingImage();

		BlogPostingImage blogPostingImage =
			testBlogPostingImage_addBlogPostingImage(randomBlogPostingImage);

		Assert.assertTrue(
			equals(
				randomBlogPostingImage,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(blogPostingImage))));
	}

	protected void assertEqualsIgnoringOrder(
		List<BlogPostingImage> blogPostingImages, JSONArray jsonArray) {

		for (BlogPostingImage blogPostingImage : blogPostingImages) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equals(blogPostingImage, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + blogPostingImage, contains);
		}
	}

	protected boolean equals(
		BlogPostingImage blogPostingImage, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("contentUrl", fieldName)) {
				if (!Objects.equals(
						blogPostingImage.getContentUrl(),
						(String)jsonObject.getString("contentUrl"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", fieldName)) {
				if (!Objects.equals(
						blogPostingImage.getEncodingFormat(),
						(String)jsonObject.getString("encodingFormat"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", fieldName)) {
				if (!Objects.equals(
						blogPostingImage.getFileExtension(),
						(String)jsonObject.getString("fileExtension"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.equals(
						blogPostingImage.getId(),
						(Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", fieldName)) {
				if (!Objects.equals(
						blogPostingImage.getSizeInBytes(),
						(Long)jsonObject.getLong("sizeInBytes"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", fieldName)) {
				if (!Objects.equals(
						blogPostingImage.getTitle(),
						(String)jsonObject.getString("title"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
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

	protected BlogPostingImage testBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return testBlogPostingImage_addBlogPostingImage(
			randomBlogPostingImage());
	}

	protected BlogPostingImage testBlogPostingImage_addBlogPostingImage(
			BlogPostingImage blogPostingImage)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String field : getAdditionalAssertFieldNames()) {
			if (Objects.equals("contentUrl", field)) {
				sb.append(field);
				sb.append(":");

				Object value = blogPostingImage.getContentUrl();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("encodingFormat", field)) {
				sb.append(field);
				sb.append(":");

				Object value = blogPostingImage.getEncodingFormat();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("fileExtension", field)) {
				sb.append(field);
				sb.append(":");

				Object value = blogPostingImage.getFileExtension();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("id", field)) {
				sb.append(field);
				sb.append(":");

				Object value = blogPostingImage.getId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("sizeInBytes", field)) {
				sb.append(field);
				sb.append(":");

				Object value = blogPostingImage.getSizeInBytes();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}

			if (Objects.equals("title", field)) {
				sb.append(field);
				sb.append(":");

				Object value = blogPostingImage.getTitle();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(",");
			}
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"createSiteBlogPostingImage",
				new HashMap<String, Object>() {
					{
						put("siteId", testGroup.getGroupId());
						put("blogPostingImage", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<BlogPostingImage> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		String data = jsonObject.getJSONObject(
			"data"
		).getJSONObject(
			"createSiteBlogPostingImage"
		).toString();

		return jsonDeserializer.deserialize(data, BlogPostingImage.class);
	}

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

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

}