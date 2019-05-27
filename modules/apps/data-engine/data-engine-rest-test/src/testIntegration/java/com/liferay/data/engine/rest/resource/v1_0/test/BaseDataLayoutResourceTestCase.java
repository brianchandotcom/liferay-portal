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

package com.liferay.data.engine.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.resource.v1_0.DataLayoutResource;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataLayoutSerDes;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataLayoutResourceTestCase {

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_dataLayoutResource.setContextCompany(testCompany);
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

		DataLayout dataLayout1 = randomDataLayout();

		String json = objectMapper.writeValueAsString(dataLayout1);

		DataLayout dataLayout2 = DataLayoutSerDes.toDTO(json);

		Assert.assertTrue(equals(dataLayout1, dataLayout2));
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

		DataLayout dataLayout = randomDataLayout();

		String json1 = objectMapper.writeValueAsString(dataLayout);
		String json2 = DataLayoutSerDes.toJSON(dataLayout);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataLayout dataLayout = randomDataLayout();

		dataLayout.setDataLayoutKey(regex);
		dataLayout.setDefaultLanguageId(regex);
		dataLayout.setPaginationMode(regex);

		String json = DataLayoutSerDes.toJSON(dataLayout);

		Assert.assertFalse(json.contains(regex));

		dataLayout = DataLayoutSerDes.toDTO(json);

		Assert.assertEquals(regex, dataLayout.getDataLayoutKey());
		Assert.assertEquals(regex, dataLayout.getDefaultLanguageId());
		Assert.assertEquals(regex, dataLayout.getPaginationMode());
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPage() throws Exception {
		Page<DataLayout> page =
			DataLayoutResource.getDataDefinitionDataLayoutsPage(
				testGetDataDefinitionDataLayoutsPage_getDataDefinitionId(),
				RandomTestUtil.randomString(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long dataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getIrrelevantDataDefinitionId();

		if ((irrelevantDataDefinitionId != null)) {
			DataLayout irrelevantDataLayout =
				testGetDataDefinitionDataLayoutsPage_addDataLayout(
					irrelevantDataDefinitionId, randomIrrelevantDataLayout());

			page = DataLayoutResource.getDataDefinitionDataLayoutsPage(
				irrelevantDataDefinitionId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataLayout),
				(List<DataLayout>)page.getItems());
			assertValid(page);
		}

		DataLayout dataLayout1 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		DataLayout dataLayout2 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		page = DataLayoutResource.getDataDefinitionDataLayoutsPage(
			dataDefinitionId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout1, dataLayout2),
			(List<DataLayout>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDataDefinitionDataLayoutsPageWithPagination()
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId();

		DataLayout dataLayout1 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		DataLayout dataLayout2 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		DataLayout dataLayout3 =
			testGetDataDefinitionDataLayoutsPage_addDataLayout(
				dataDefinitionId, randomDataLayout());

		Page<DataLayout> page1 =
			DataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, null, Pagination.of(1, 2));

		List<DataLayout> dataLayouts1 = (List<DataLayout>)page1.getItems();

		Assert.assertEquals(dataLayouts1.toString(), 2, dataLayouts1.size());

		Page<DataLayout> page2 =
			DataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataLayout> dataLayouts2 = (List<DataLayout>)page2.getItems();

		Assert.assertEquals(dataLayouts2.toString(), 1, dataLayouts2.size());

		Page<DataLayout> page3 =
			DataLayoutResource.getDataDefinitionDataLayoutsPage(
				dataDefinitionId, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout1, dataLayout2, dataLayout3),
			(List<DataLayout>)page3.getItems());
	}

	protected DataLayout testGetDataDefinitionDataLayoutsPage_addDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		return DataLayoutResource.postDataDefinitionDataLayout(
			dataDefinitionId, dataLayout);
	}

	protected Long testGetDataDefinitionDataLayoutsPage_getDataDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataDefinitionDataLayoutsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataDefinitionDataLayout() throws Exception {
		DataLayout randomDataLayout = randomDataLayout();

		DataLayout postDataLayout =
			testPostDataDefinitionDataLayout_addDataLayout(randomDataLayout);

		assertEquals(randomDataLayout, postDataLayout);
		assertValid(postDataLayout);
	}

	protected DataLayout testPostDataDefinitionDataLayout_addDataLayout(
			DataLayout dataLayout)
		throws Exception {

		return DataLayoutResource.postDataDefinitionDataLayout(
			testGetDataDefinitionDataLayoutsPage_getDataDefinitionId(),
			dataLayout);
	}

	@Test
	public void testPostDataLayoutDataLayoutPermission() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testDeleteDataLayout() throws Exception {
		DataLayout dataLayout = testDeleteDataLayout_addDataLayout();

		assertHttpResponseStatusCode(
			204,
			DataLayoutResource.deleteDataLayoutHttpResponse(
				dataLayout.getId()));

		assertHttpResponseStatusCode(
			404,
			DataLayoutResource.getDataLayoutHttpResponse(dataLayout.getId()));

		assertHttpResponseStatusCode(
			404, DataLayoutResource.getDataLayoutHttpResponse(0L));
	}

	protected DataLayout testDeleteDataLayout_addDataLayout() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDataLayout() throws Exception {
		DataLayout postDataLayout = testGetDataLayout_addDataLayout();

		DataLayout getDataLayout = DataLayoutResource.getDataLayout(
			postDataLayout.getId());

		assertEquals(postDataLayout, getDataLayout);
		assertValid(getDataLayout);
	}

	protected DataLayout testGetDataLayout_addDataLayout() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutDataLayout() throws Exception {
		DataLayout postDataLayout = testPutDataLayout_addDataLayout();

		DataLayout randomDataLayout = randomDataLayout();

		DataLayout putDataLayout = DataLayoutResource.putDataLayout(
			postDataLayout.getId(), randomDataLayout);

		assertEquals(randomDataLayout, putDataLayout);
		assertValid(putDataLayout);

		DataLayout getDataLayout = DataLayoutResource.getDataLayout(
			putDataLayout.getId());

		assertEquals(randomDataLayout, getDataLayout);
		assertValid(getDataLayout);
	}

	protected DataLayout testPutDataLayout_addDataLayout() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteDataLayoutPage() throws Exception {
		Page<DataLayout> page = DataLayoutResource.getSiteDataLayoutPage(
			testGetSiteDataLayoutPage_getSiteId(),
			RandomTestUtil.randomString(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteDataLayoutPage_getSiteId();
		Long irrelevantSiteId = testGetSiteDataLayoutPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			DataLayout irrelevantDataLayout =
				testGetSiteDataLayoutPage_addDataLayout(
					irrelevantSiteId, randomIrrelevantDataLayout());

			page = DataLayoutResource.getSiteDataLayoutPage(
				irrelevantSiteId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataLayout),
				(List<DataLayout>)page.getItems());
			assertValid(page);
		}

		DataLayout dataLayout1 = testGetSiteDataLayoutPage_addDataLayout(
			siteId, randomDataLayout());

		DataLayout dataLayout2 = testGetSiteDataLayoutPage_addDataLayout(
			siteId, randomDataLayout());

		page = DataLayoutResource.getSiteDataLayoutPage(
			siteId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout1, dataLayout2),
			(List<DataLayout>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteDataLayoutPageWithPagination() throws Exception {
		Long siteId = testGetSiteDataLayoutPage_getSiteId();

		DataLayout dataLayout1 = testGetSiteDataLayoutPage_addDataLayout(
			siteId, randomDataLayout());

		DataLayout dataLayout2 = testGetSiteDataLayoutPage_addDataLayout(
			siteId, randomDataLayout());

		DataLayout dataLayout3 = testGetSiteDataLayoutPage_addDataLayout(
			siteId, randomDataLayout());

		Page<DataLayout> page1 = DataLayoutResource.getSiteDataLayoutPage(
			siteId, null, Pagination.of(1, 2));

		List<DataLayout> dataLayouts1 = (List<DataLayout>)page1.getItems();

		Assert.assertEquals(dataLayouts1.toString(), 2, dataLayouts1.size());

		Page<DataLayout> page2 = DataLayoutResource.getSiteDataLayoutPage(
			siteId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataLayout> dataLayouts2 = (List<DataLayout>)page2.getItems();

		Assert.assertEquals(dataLayouts2.toString(), 1, dataLayouts2.size());

		Page<DataLayout> page3 = DataLayoutResource.getSiteDataLayoutPage(
			siteId, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(dataLayout1, dataLayout2, dataLayout3),
			(List<DataLayout>)page3.getItems());
	}

	protected DataLayout testGetSiteDataLayoutPage_addDataLayout(
			Long siteId, DataLayout dataLayout)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteDataLayoutPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteDataLayoutPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testPostSiteDataLayoutPermission() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetSiteDataLayout() throws Exception {
		DataLayout postDataLayout = testGetSiteDataLayout_addDataLayout();

		DataLayout getDataLayout = DataLayoutResource.getSiteDataLayout(
			postDataLayout.getSiteId(), postDataLayout.getDataLayoutKey());

		assertEquals(postDataLayout, getDataLayout);
		assertValid(getDataLayout);
	}

	protected DataLayout testGetSiteDataLayout_addDataLayout()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DataLayout dataLayout1, DataLayout dataLayout2) {

		Assert.assertTrue(
			dataLayout1 + " does not equal " + dataLayout2,
			equals(dataLayout1, dataLayout2));
	}

	protected void assertEquals(
		List<DataLayout> dataLayouts1, List<DataLayout> dataLayouts2) {

		Assert.assertEquals(dataLayouts1.size(), dataLayouts2.size());

		for (int i = 0; i < dataLayouts1.size(); i++) {
			DataLayout dataLayout1 = dataLayouts1.get(i);
			DataLayout dataLayout2 = dataLayouts2.get(i);

			assertEquals(dataLayout1, dataLayout2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataLayout> dataLayouts1, List<DataLayout> dataLayouts2) {

		Assert.assertEquals(dataLayouts1.size(), dataLayouts2.size());

		for (DataLayout dataLayout1 : dataLayouts1) {
			boolean contains = false;

			for (DataLayout dataLayout2 : dataLayouts2) {
				if (equals(dataLayout1, dataLayout2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataLayouts2 + " does not contain " + dataLayout1, contains);
		}
	}

	protected void assertValid(DataLayout dataLayout) {
		boolean valid = true;

		if (dataLayout.getDateCreated() == null) {
			valid = false;
		}

		if (dataLayout.getDateModified() == null) {
			valid = false;
		}

		if (dataLayout.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(dataLayout.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (dataLayout.getDataDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutKey", additionalAssertFieldName)) {
				if (dataLayout.getDataLayoutKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutPages", additionalAssertFieldName)) {
				if (dataLayout.getDataLayoutPages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (dataLayout.getDefaultLanguageId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (dataLayout.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataLayout.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paginationMode", additionalAssertFieldName)) {
				if (dataLayout.getPaginationMode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (dataLayout.getUserId() == null) {
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

	protected void assertValid(Page<DataLayout> page) {
		boolean valid = false;

		Collection<DataLayout> dataLayouts = page.getItems();

		int size = dataLayouts.size();

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

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(DataLayout dataLayout1, DataLayout dataLayout2) {
		if (dataLayout1 == dataLayout2) {
			return true;
		}

		if (!Objects.equals(dataLayout1.getSiteId(), dataLayout2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDataDefinitionId(),
						dataLayout2.getDataDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDataLayoutKey(),
						dataLayout2.getDataLayoutKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutPages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDataLayoutPages(),
						dataLayout2.getDataLayoutPages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDateCreated(),
						dataLayout2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDateModified(),
						dataLayout2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataLayout1.getDefaultLanguageId(),
						dataLayout2.getDefaultLanguageId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getDescription(),
						dataLayout2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getId(), dataLayout2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getName(), dataLayout2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paginationMode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getPaginationMode(),
						dataLayout2.getPaginationMode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataLayout1.getUserId(), dataLayout2.getUserId())) {

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
		if (!(_dataLayoutResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataLayoutResource;

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
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, DataLayout dataLayout) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataLayoutKey")) {
			sb.append("'");
			sb.append(String.valueOf(dataLayout.getDataLayoutKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dataLayoutPages")) {
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
						DateUtils.addSeconds(dataLayout.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(dataLayout.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataLayout.getDateCreated()));
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
							dataLayout.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(dataLayout.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataLayout.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultLanguageId")) {
			sb.append("'");
			sb.append(String.valueOf(dataLayout.getDefaultLanguageId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("paginationMode")) {
			sb.append("'");
			sb.append(String.valueOf(dataLayout.getPaginationMode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected DataLayout randomDataLayout() throws Exception {
		return new DataLayout() {
			{
				dataDefinitionId = RandomTestUtil.randomLong();
				dataLayoutKey = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultLanguageId = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				paginationMode = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataLayout randomIrrelevantDataLayout() throws Exception {
		DataLayout randomIrrelevantDataLayout = randomDataLayout();

		randomIrrelevantDataLayout.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDataLayout;
	}

	protected DataLayout randomPatchDataLayout() throws Exception {
		return randomDataLayout();
	}

	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDataLayoutResourceTestCase.class);

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
	private com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource
		_dataLayoutResource;

}