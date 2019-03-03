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

import com.liferay.bulk.rest.dto.v1_0.BulkActionResponseModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonCategoriesModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryCommonTagsModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateCategoriesActionModel;
import com.liferay.bulk.rest.dto.v1_0.BulkAssetEntryUpdateTagsActionModel;
import com.liferay.bulk.rest.resource.v1_0.BulkActionResponseModelResource;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseBulkActionResponseModelResourceTestCase {

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

		_resourceURL = new URL("http://localhost:8080/o/bulk-rest/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testPostCategoryClassName() throws Exception {
		BulkActionResponseModel randomBulkActionResponseModel =
			randomBulkActionResponseModel();

		BulkActionResponseModel postBulkActionResponseModel =
			testPostCategoryClassName_addBulkActionResponseModel(
				randomBulkActionResponseModel);

		assertEquals(
			randomBulkActionResponseModel, postBulkActionResponseModel);
		assertValid(postBulkActionResponseModel);
	}

	@Test
	public void testPostCategoryGroupCategoryClassName() throws Exception {
		BulkActionResponseModel randomBulkActionResponseModel =
			randomBulkActionResponseModel();

		BulkActionResponseModel postBulkActionResponseModel =
			testPostCategoryGroupCategoryClassName_addBulkActionResponseModel(
				randomBulkActionResponseModel);

		assertEquals(
			randomBulkActionResponseModel, postBulkActionResponseModel);
		assertValid(postBulkActionResponseModel);
	}

	@Test
	public void testPostTagClassName() throws Exception {
		BulkActionResponseModel randomBulkActionResponseModel =
			randomBulkActionResponseModel();

		BulkActionResponseModel postBulkActionResponseModel =
			testPostTagClassName_addBulkActionResponseModel(
				randomBulkActionResponseModel);

		assertEquals(
			randomBulkActionResponseModel, postBulkActionResponseModel);
		assertValid(postBulkActionResponseModel);
	}

	@Test
	public void testPostTagGroupTagClassName() throws Exception {
		BulkActionResponseModel randomBulkActionResponseModel =
			randomBulkActionResponseModel();

		BulkActionResponseModel postBulkActionResponseModel =
			testPostTagGroupTagClassName_addBulkActionResponseModel(
				randomBulkActionResponseModel);

		assertEquals(
			randomBulkActionResponseModel, postBulkActionResponseModel);
		assertValid(postBulkActionResponseModel);
	}

	protected void assertEquals(
		BulkActionResponseModel bulkActionResponseModel1,
		BulkActionResponseModel bulkActionResponseModel2) {

		Assert.assertTrue(
			bulkActionResponseModel1 + " does not equal " +
				bulkActionResponseModel2,
			equals(bulkActionResponseModel1, bulkActionResponseModel2));
	}

	protected void assertEquals(
		List<BulkActionResponseModel> bulkActionResponseModels1,
		List<BulkActionResponseModel> bulkActionResponseModels2) {

		Assert.assertEquals(
			bulkActionResponseModels1.size(), bulkActionResponseModels2.size());

		for (int i = 0; i < bulkActionResponseModels1.size(); i++) {
			BulkActionResponseModel bulkActionResponseModel1 =
				bulkActionResponseModels1.get(i);
			BulkActionResponseModel bulkActionResponseModel2 =
				bulkActionResponseModels2.get(i);

			assertEquals(bulkActionResponseModel1, bulkActionResponseModel2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BulkActionResponseModel> bulkActionResponseModels1,
		List<BulkActionResponseModel> bulkActionResponseModels2) {

		Assert.assertEquals(
			bulkActionResponseModels1.size(), bulkActionResponseModels2.size());

		for (BulkActionResponseModel bulkActionResponseModel1 :
				bulkActionResponseModels1) {

			boolean contains = false;

			for (BulkActionResponseModel bulkActionResponseModel2 :
					bulkActionResponseModels2) {

				if (equals(
						bulkActionResponseModel1, bulkActionResponseModel2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				bulkActionResponseModels2 + " does not contain " +
					bulkActionResponseModel1,
				contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(
		BulkActionResponseModel bulkActionResponseModel) {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<BulkActionResponseModel> page) {
		boolean valid = false;

		Collection<BulkActionResponseModel> bulkActionResponseModels =
			page.getItems();

		int size = bulkActionResponseModels.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		BulkActionResponseModel bulkActionResponseModel1,
		BulkActionResponseModel bulkActionResponseModel2) {

		if (bulkActionResponseModel1 == bulkActionResponseModel2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_bulkActionResponseModelResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_bulkActionResponseModelResource;

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
		BulkActionResponseModel bulkActionResponseModel) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(bulkActionResponseModel.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			sb.append("'");
			sb.append(String.valueOf(bulkActionResponseModel.getStatus()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected BulkActionResponseModel invokePostCategoryClassName(
			Long categoryClassNameId,
			BulkAssetEntryUpdateCategoriesActionModel
				bulkAssetEntryUpdateCategoriesActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/categories/{category-class-name-id}",
					categoryClassNameId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BulkActionResponseModel.class);
	}

	protected Http.Response invokePostCategoryClassNameResponse(
			Long categoryClassNameId,
			BulkAssetEntryUpdateCategoriesActionModel
				bulkAssetEntryUpdateCategoriesActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/categories/{category-class-name-id}",
					categoryClassNameId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BulkAssetEntryCommonCategoriesModel
			invokePostCategoryGroupCategoryClassName(
				Long categoryGroupId, Long categoryClassNameId,
				BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/categories/{category-group-id}/{category-class-name-id}/common",
					categoryGroupId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			BulkAssetEntryCommonCategoriesModel.class);
	}

	protected Http.Response invokePostCategoryGroupCategoryClassNameResponse(
			Long categoryGroupId, Long categoryClassNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/categories/{category-group-id}/{category-class-name-id}/common",
					categoryGroupId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BulkActionResponseModel invokePostTagClassName(
			Long tagClassNameId,
			BulkAssetEntryUpdateTagsActionModel
				bulkAssetEntryUpdateTagsActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/tags/{tag-class-name-id}", tagClassNameId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BulkActionResponseModel.class);
	}

	protected Http.Response invokePostTagClassNameResponse(
			Long tagClassNameId,
			BulkAssetEntryUpdateTagsActionModel
				bulkAssetEntryUpdateTagsActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath("/tags/{tag-class-name-id}", tagClassNameId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BulkAssetEntryCommonTagsModel invokePostTagGroupTagClassName(
			Long tagGroupId, Long tagClassNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/tags/{tag-group-id}/{tag-class-name-id}/common",
					tagGroupId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BulkAssetEntryCommonTagsModel.class);
	}

	protected Http.Response invokePostTagGroupTagClassNameResponse(
			Long tagGroupId, Long tagClassNameId,
			BulkAssetEntryActionModel bulkAssetEntryActionModel)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/tags/{tag-group-id}/{tag-class-name-id}/common",
					tagGroupId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BulkActionResponseModel randomBulkActionResponseModel() {
		return new BulkActionResponseModel() {
			{
				description = RandomTestUtil.randomString();
				status = RandomTestUtil.randomString();
			}
		};
	}

	protected BulkActionResponseModel
			testPostCategoryClassName_addBulkActionResponseModel(
				BulkActionResponseModel bulkActionResponseModel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkActionResponseModel
			testPostCategoryGroupCategoryClassName_addBulkActionResponseModel(
				BulkActionResponseModel bulkActionResponseModel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkActionResponseModel
			testPostTagClassName_addBulkActionResponseModel(
				BulkActionResponseModel bulkActionResponseModel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkActionResponseModel
			testPostTagGroupTagClassName_addBulkActionResponseModel(
				BulkActionResponseModel bulkActionResponseModel)
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

	@Inject
	private BulkActionResponseModelResource _bulkActionResponseModelResource;

	private URL _resourceURL;

}