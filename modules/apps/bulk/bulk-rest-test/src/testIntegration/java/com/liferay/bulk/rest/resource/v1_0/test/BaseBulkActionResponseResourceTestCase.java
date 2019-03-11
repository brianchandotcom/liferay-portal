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

package com.liferay.bulk.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.bulk.rest.dto.v1_0.BulkActionResponse;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryAction;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonCategories;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonTags;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateCategoriesAction;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateTagsAction;
import com.liferay.bulk.rest.resource.v1_0.BulkActionResponseResource;
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
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public abstract class BaseBulkActionResponseResourceTestCase {

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

		_resourceURL = new URL("http://localhost:8080/o/bulk/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testPostCategoryCategoryClassName() throws Exception {
		BulkActionResponse randomBulkActionResponse =
			randomBulkActionResponse();

		BulkActionResponse postBulkActionResponse =
			testPostCategoryCategoryClassName_addBulkActionResponse(
				randomBulkActionResponse);

		assertEquals(randomBulkActionResponse, postBulkActionResponse);
		assertValid(postBulkActionResponse);
	}

	protected BulkActionResponse
			testPostCategoryCategoryClassName_addBulkActionResponse(
				BulkActionResponse bulkActionResponse)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkActionResponse invokePostCategoryCategoryClassName(
			Long categoryClassNameId,
			BulkAssetEntryUpdateCategoriesAction
				bulkAssetEntryUpdateCategoriesAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/categories/{category-class-name-id}",
					categoryClassNameId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BulkActionResponse.class);
	}

	protected Http.Response invokePostCategoryCategoryClassNameResponse(
			Long categoryClassNameId,
			BulkAssetEntryUpdateCategoriesAction
				bulkAssetEntryUpdateCategoriesAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/categories/{category-class-name-id}",
					categoryClassNameId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostCategoryCategoryGroupCategoryClassNameCommon()
		throws Exception {

		BulkActionResponse randomBulkActionResponse =
			randomBulkActionResponse();

		BulkActionResponse postBulkActionResponse =
			testPostCategoryCategoryGroupCategoryClassNameCommon_addBulkActionResponse(
				randomBulkActionResponse);

		assertEquals(randomBulkActionResponse, postBulkActionResponse);
		assertValid(postBulkActionResponse);
	}

	protected BulkActionResponse
			testPostCategoryCategoryGroupCategoryClassNameCommon_addBulkActionResponse(
				BulkActionResponse bulkActionResponse)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkAssetEntryCommonCategories
			invokePostCategoryCategoryGroupCategoryClassNameCommon(
				Long categoryGroupId, Long categoryClassNameId,
				BulkAssetEntryAction bulkAssetEntryAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/categories/{category-group-id}/{category-class-name-id}/common",
					categoryGroupId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			BulkAssetEntryCommonCategories.class);
	}

	protected Http.Response
			invokePostCategoryCategoryGroupCategoryClassNameCommonResponse(
				Long categoryGroupId, Long categoryClassNameId,
				BulkAssetEntryAction bulkAssetEntryAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/categories/{category-group-id}/{category-class-name-id}/common",
					categoryGroupId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostTagTagClassName() throws Exception {
		BulkActionResponse randomBulkActionResponse =
			randomBulkActionResponse();

		BulkActionResponse postBulkActionResponse =
			testPostTagTagClassName_addBulkActionResponse(
				randomBulkActionResponse);

		assertEquals(randomBulkActionResponse, postBulkActionResponse);
		assertValid(postBulkActionResponse);
	}

	protected BulkActionResponse testPostTagTagClassName_addBulkActionResponse(
			BulkActionResponse bulkActionResponse)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkActionResponse invokePostTagTagClassName(
			Long tagClassNameId,
			BulkAssetEntryUpdateTagsAction bulkAssetEntryUpdateTagsAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/tags/{tag-class-name-id}", tagClassNameId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BulkActionResponse.class);
	}

	protected Http.Response invokePostTagTagClassNameResponse(
			Long tagClassNameId,
			BulkAssetEntryUpdateTagsAction bulkAssetEntryUpdateTagsAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/tags/{tag-class-name-id}", tagClassNameId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPostTagTagGroupTagClassNameCommon() throws Exception {
		BulkActionResponse randomBulkActionResponse =
			randomBulkActionResponse();

		BulkActionResponse postBulkActionResponse =
			testPostTagTagGroupTagClassNameCommon_addBulkActionResponse(
				randomBulkActionResponse);

		assertEquals(randomBulkActionResponse, postBulkActionResponse);
		assertValid(postBulkActionResponse);
	}

	protected BulkActionResponse
			testPostTagTagGroupTagClassNameCommon_addBulkActionResponse(
				BulkActionResponse bulkActionResponse)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkAssetEntryCommonTags invokePostTagTagGroupTagClassNameCommon(
			Long tagGroupId, Long tagClassNameId,
			BulkAssetEntryAction bulkAssetEntryAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/tags/{tag-group-id}/{tag-class-name-id}/common",
					tagGroupId);

		options.setLocation(location);

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BulkAssetEntryCommonTags.class);
	}

	protected Http.Response invokePostTagTagGroupTagClassNameCommonResponse(
			Long tagGroupId, Long tagClassNameId,
			BulkAssetEntryAction bulkAssetEntryAction)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/tags/{tag-group-id}/{tag-class-name-id}/common",
					tagGroupId);

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

	protected void assertEquals(
		BulkActionResponse bulkActionResponse1,
		BulkActionResponse bulkActionResponse2) {

		Assert.assertTrue(
			bulkActionResponse1 + " does not equal " + bulkActionResponse2,
			equals(bulkActionResponse1, bulkActionResponse2));
	}

	protected void assertEquals(
		List<BulkActionResponse> bulkActionResponses1,
		List<BulkActionResponse> bulkActionResponses2) {

		Assert.assertEquals(
			bulkActionResponses1.size(), bulkActionResponses2.size());

		for (int i = 0; i < bulkActionResponses1.size(); i++) {
			BulkActionResponse bulkActionResponse1 = bulkActionResponses1.get(
				i);
			BulkActionResponse bulkActionResponse2 = bulkActionResponses2.get(
				i);

			assertEquals(bulkActionResponse1, bulkActionResponse2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BulkActionResponse> bulkActionResponses1,
		List<BulkActionResponse> bulkActionResponses2) {

		Assert.assertEquals(
			bulkActionResponses1.size(), bulkActionResponses2.size());

		for (BulkActionResponse bulkActionResponse1 : bulkActionResponses1) {
			boolean contains = false;

			for (BulkActionResponse bulkActionResponse2 :
					bulkActionResponses2) {

				if (equals(bulkActionResponse1, bulkActionResponse2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				bulkActionResponses2 + " does not contain " +
					bulkActionResponse1,
				contains);
		}
	}

	protected void assertValid(BulkActionResponse bulkActionResponse) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<BulkActionResponse> page) {
		boolean valid = false;

		Collection<BulkActionResponse> bulkActionResponses = page.getItems();

		int size = bulkActionResponses.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		BulkActionResponse bulkActionResponse1,
		BulkActionResponse bulkActionResponse2) {

		if (bulkActionResponse1 == bulkActionResponse2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_bulkActionResponseResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_bulkActionResponseResource;

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
		BulkActionResponse bulkActionResponse) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(bulkActionResponse.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			sb.append("'");
			sb.append(String.valueOf(bulkActionResponse.getStatus()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected BulkActionResponse randomBulkActionResponse() {
		return new BulkActionResponse() {
			{
				description = RandomTestUtil.randomString();
				status = RandomTestUtil.randomString();
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
	private BulkActionResponseResource _bulkActionResponseResource;

	private URL _resourceURL;

}