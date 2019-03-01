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

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.web.experience.dto.v1_0.Options;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentImage;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentImageResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
public abstract class BaseStructuredContentImageResourceTestCase {

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
			"http://localhost:8080/o/headless-web-experience/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteStructuredContentImage() throws Exception {
		StructuredContentImage structuredContentImage =
			testDeleteStructuredContentImage_addStructuredContentImage();

		assertResponseCode(
			200,
			invokeDeleteStructuredContentImageResponse(
				structuredContentImage.getId()));

		assertResponseCode(
			404,
			invokeGetStructuredContentImageResponse(
				structuredContentImage.getId()));
	}

	@Test
	public void testGetStructuredContentImage() throws Exception {
		StructuredContentImage postStructuredContentImage =
			testGetStructuredContentImage_addStructuredContentImage();

		StructuredContentImage getStructuredContentImage =
			invokeGetStructuredContentImage(postStructuredContentImage.getId());

		assertEquals(postStructuredContentImage, getStructuredContentImage);
		assertValid(getStructuredContentImage);
	}

	@Test
	public void testGetStructuredContentStructuredContentImagesPage()
		throws Exception {

		Long structuredContentId =
			testGetStructuredContentStructuredContentImagesPage_getStructuredContentId();

		StructuredContentImage structuredContentImage1 =
			testGetStructuredContentStructuredContentImagesPage_addStructuredContentImage(
				structuredContentId, randomStructuredContentImage());
		StructuredContentImage structuredContentImage2 =
			testGetStructuredContentStructuredContentImagesPage_addStructuredContentImage(
				structuredContentId, randomStructuredContentImage());

		Page<StructuredContentImage> page =
			invokeGetStructuredContentStructuredContentImagesPage(
				structuredContentId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContentImage1, structuredContentImage2),
			(List<StructuredContentImage>)page.getItems());
		assertValid(page);
	}

	protected void assertEquals(
		List<StructuredContentImage> structuredContentImages1,
		List<StructuredContentImage> structuredContentImages2) {

		Assert.assertEquals(
			structuredContentImages1.size(), structuredContentImages2.size());

		for (int i = 0; i < structuredContentImages1.size(); i++) {
			StructuredContentImage structuredContentImage1 =
				structuredContentImages1.get(i);
			StructuredContentImage structuredContentImage2 =
				structuredContentImages2.get(i);

			assertEquals(structuredContentImage1, structuredContentImage2);
		}
	}

	protected void assertEquals(
		StructuredContentImage structuredContentImage1,
		StructuredContentImage structuredContentImage2) {

		Assert.assertTrue(
			structuredContentImage1 + " does not equal " +
				structuredContentImage2,
			equals(structuredContentImage1, structuredContentImage2));
	}

	protected void assertEqualsIgnoringOrder(
		List<StructuredContentImage> structuredContentImages1,
		List<StructuredContentImage> structuredContentImages2) {

		Assert.assertEquals(
			structuredContentImages1.size(), structuredContentImages2.size());

		for (StructuredContentImage structuredContentImage1 :
				structuredContentImages1) {

			boolean contains = false;

			for (StructuredContentImage structuredContentImage2 :
					structuredContentImages2) {

				if (equals(structuredContentImage1, structuredContentImage2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				structuredContentImages2 + " does not contain " +
					structuredContentImage1,
				contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Page<StructuredContentImage> page) {
		boolean valid = false;

		Collection<StructuredContentImage> structuredContentImages =
			page.getItems();

		int size = structuredContentImages.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(StructuredContentImage structuredContentImage) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(
		StructuredContentImage structuredContentImage1,
		StructuredContentImage structuredContentImage2) {

		if (structuredContentImage1 == structuredContentImage2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_structuredContentImageResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_structuredContentImageResource;

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
		StructuredContentImage structuredContentImage) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContentImage.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(
				_dateFormat.format(structuredContentImage.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(
				_dateFormat.format(structuredContentImage.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(
				String.valueOf(structuredContentImage.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			sb.append("'");
			sb.append(
				String.valueOf(structuredContentImage.getFileExtension()));
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

		if (entityFieldName.equals("structuredContentId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContentImage.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected boolean invokeDeleteStructuredContentImage(
			Long structuredContentImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-content-images/{structured-content-image-id}",
					structuredContentImageId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteStructuredContentImageResponse(
			Long structuredContentImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-content-images/{structured-content-image-id}",
					structuredContentImageId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected StructuredContentImage invokeGetStructuredContentImage(
			Long structuredContentImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-content-images/{structured-content-image-id}",
					structuredContentImageId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), StructuredContentImage.class);
	}

	protected Http.Response invokeGetStructuredContentImageResponse(
			Long structuredContentImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-content-images/{structured-content-image-id}",
					structuredContentImageId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<StructuredContentImage>
			invokeGetStructuredContentStructuredContentImagesPage(
				Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/structured-content-images",
					structuredContentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<StructuredContentImage>>() {
			});
	}

	protected Http.Response
			invokeGetStructuredContentStructuredContentImagesPageResponse(
				Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/structured-content-images",
					structuredContentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected StructuredContentImage randomStructuredContentImage() {
		return new StructuredContentImage() {
			{
				contentUrl = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				structuredContentId = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected StructuredContentImage
			testDeleteStructuredContentImage_addStructuredContentImage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContentImage
			testGetStructuredContentImage_addStructuredContentImage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContentImage
			testGetStructuredContentStructuredContentImagesPage_addStructuredContentImage(
				Long structuredContentId,
				StructuredContentImage structuredContentImage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentStructuredContentImagesPage_getStructuredContentId()
		throws Exception {

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
	private StructuredContentImageResource _structuredContentImageResource;

}