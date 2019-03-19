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

import com.liferay.bulk.rest.dto.v1_0.TaxonomyCategory;
import com.liferay.bulk.rest.dto.v1_0.TaxonomyCategoryBulkSelection;
import com.liferay.bulk.rest.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
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

import java.lang.reflect.InvocationTargetException;

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
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

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
public abstract class BaseTaxonomyCategoryResourceTestCase {

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

		_resourceURL = new URL("http://localhost:8080/o/bulk/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testPatchTaxonomyCategoryBatch() throws Exception {
		TaxonomyCategory postTaxonomyCategory =
			testPatchTaxonomyCategoryBatch_addTaxonomyCategory(
				randomTaxonomyCategory());

		TaxonomyCategory randomPatchTaxonomyCategory = randomTaxonomyCategory();

		TaxonomyCategory patchTaxonomyCategory =
			testPatchTaxonomyCategoryBatch_addTaxonomyCategory(
				randomPatchTaxonomyCategory);

		TaxonomyCategory expectedPatchTaxonomyCategory =
			(TaxonomyCategory)BeanUtils.cloneBean(postTaxonomyCategory);

		_beanUtilsBean.copyProperties(
			expectedPatchTaxonomyCategory, randomPatchTaxonomyCategory);

		TaxonomyCategory getTaxonomyCategory = invokeGetTaxonomyCategory(
			patchTaxonomyCategory.getId());

		assertEquals(expectedPatchTaxonomyCategory, getTaxonomyCategory);
		assertValid(getTaxonomyCategory);
	}

	protected TaxonomyCategory
			testPatchTaxonomyCategoryBatch_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean invokePatchTaxonomyCategoryBatch(
			TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/taxonomy-categories/batch");

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		try {
			return _outputObjectMapper.readValue(string, Boolean.class);
		}
		catch (Exception e) {
			Assert.fail("HTTP response: " + string);

			throw e;
		}
	}

	protected Http.Response invokePatchTaxonomyCategoryBatchResponse(
			TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/taxonomy-categories/batch");

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	@Test
	public void testPutTaxonomyCategoryBatch() throws Exception {
		TaxonomyCategory postTaxonomyCategory =
			testPutTaxonomyCategoryBatch_addTaxonomyCategory();

		TaxonomyCategory randomTaxonomyCategory = randomTaxonomyCategory();

		TaxonomyCategory putTaxonomyCategory = invokePutTaxonomyCategory(
			postTaxonomyCategory.getId(), randomTaxonomyCategory);

		assertEquals(randomTaxonomyCategory, putTaxonomyCategory);
		assertValid(putTaxonomyCategory);

		TaxonomyCategory getTaxonomyCategory = invokeGetTaxonomyCategory(
			putTaxonomyCategory.getId());

		assertEquals(randomTaxonomyCategory, getTaxonomyCategory);
		assertValid(getTaxonomyCategory);
	}

	protected TaxonomyCategory
			testPutTaxonomyCategoryBatch_addTaxonomyCategory()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean invokePutTaxonomyCategoryBatch(
			TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/taxonomy-categories/batch");

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		try {
			return _outputObjectMapper.readValue(string, Boolean.class);
		}
		catch (Exception e) {
			Assert.fail("HTTP response: " + string);

			throw e;
		}
	}

	protected Http.Response invokePutTaxonomyCategoryBatchResponse(
			TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/taxonomy-categories/batch");

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		TaxonomyCategory taxonomyCategory1,
		TaxonomyCategory taxonomyCategory2) {

		Assert.assertTrue(
			taxonomyCategory1 + " does not equal " + taxonomyCategory2,
			equals(taxonomyCategory1, taxonomyCategory2));
	}

	protected void assertEquals(
		List<TaxonomyCategory> taxonomyCategories1,
		List<TaxonomyCategory> taxonomyCategories2) {

		Assert.assertEquals(
			taxonomyCategories1.size(), taxonomyCategories2.size());

		for (int i = 0; i < taxonomyCategories1.size(); i++) {
			TaxonomyCategory taxonomyCategory1 = taxonomyCategories1.get(i);
			TaxonomyCategory taxonomyCategory2 = taxonomyCategories2.get(i);

			assertEquals(taxonomyCategory1, taxonomyCategory2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<TaxonomyCategory> taxonomyCategories1,
		List<TaxonomyCategory> taxonomyCategories2) {

		Assert.assertEquals(
			taxonomyCategories1.size(), taxonomyCategories2.size());

		for (TaxonomyCategory taxonomyCategory1 : taxonomyCategories1) {
			boolean contains = false;

			for (TaxonomyCategory taxonomyCategory2 : taxonomyCategories2) {
				if (equals(taxonomyCategory1, taxonomyCategory2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				taxonomyCategories2 + " does not contain " + taxonomyCategory1,
				contains);
		}
	}

	protected void assertValid(TaxonomyCategory taxonomyCategory) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<TaxonomyCategory> page) {
		boolean valid = false;

		Collection<TaxonomyCategory> taxonomyCategories = page.getItems();

		int size = taxonomyCategories.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		TaxonomyCategory taxonomyCategory1,
		TaxonomyCategory taxonomyCategory2) {

		if (taxonomyCategory1 == taxonomyCategory2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_taxonomyCategoryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_taxonomyCategoryResource;

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
		TaxonomyCategory taxonomyCategory) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("taxonomyCategoryId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryName")) {
			sb.append("'");
			sb.append(
				String.valueOf(taxonomyCategory.getTaxonomyCategoryName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected TaxonomyCategory randomTaxonomyCategory() {
		return new TaxonomyCategory() {
			{
				taxonomyCategoryId = RandomTestUtil.randomLong();
				taxonomyCategoryName = RandomTestUtil.randomString();
			}
		};
	}

	protected TaxonomyCategory randomIrrelevantTaxonomyCategory() {
		return randomTaxonomyCategory();
	}

	protected TaxonomyCategory randomPatchTaxonomyCategory() {
		return randomTaxonomyCategory();
	}

	protected Group irrelevantGroup;
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

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*\\}", String.valueOf(values[i]));
		}

		return template;
	}

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
	private TaxonomyCategoryResource _taxonomyCategoryResource;

	private URL _resourceURL;

}