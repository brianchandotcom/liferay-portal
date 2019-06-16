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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.CollectionResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CollectionSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import java.util.Date;
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
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public abstract class BaseCollectionResourceTestCase {

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

		_collectionResource.setContextCompany(testCompany);

		CollectionResource.Builder builder = CollectionResource.builder();

		collectionResource = builder.locale(
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

		Collection collection1 = randomCollection();

		String json = objectMapper.writeValueAsString(collection1);

		Collection collection2 = CollectionSerDes.toDTO(json);

		Assert.assertTrue(equals(collection1, collection2));
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

		Collection collection = randomCollection();

		String json1 = objectMapper.writeValueAsString(collection);
		String json2 = CollectionSerDes.toJSON(collection);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Collection collection = randomCollection();

		collection.setDescription(regex);
		collection.setName(regex);
		collection.setStatusByUserName(regex);

		String json = CollectionSerDes.toJSON(collection);

		Assert.assertFalse(json.contains(regex));

		collection = CollectionSerDes.toDTO(json);

		Assert.assertEquals(regex, collection.getDescription());
		Assert.assertEquals(regex, collection.getName());
		Assert.assertEquals(regex, collection.getStatusByUserName());
	}

	@Test
	public void testGetCollectionsPage() throws Exception {
		Page<Collection> page = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Collection collection1 = testGetCollectionsPage_addCollection(
			randomCollection());

		Collection collection2 = testGetCollectionsPage_addCollection(
			randomCollection());

		page = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(collection1, collection2),
			(List<Collection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetCollectionsPageWithPagination() throws Exception {
		Collection collection1 = testGetCollectionsPage_addCollection(
			randomCollection());

		Collection collection2 = testGetCollectionsPage_addCollection(
			randomCollection());

		Collection collection3 = testGetCollectionsPage_addCollection(
			randomCollection());

		Page<Collection> page1 = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 2), null);

		List<Collection> collections1 = (List<Collection>)page1.getItems();

		Assert.assertEquals(collections1.toString(), 2, collections1.size());

		Page<Collection> page2 = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Collection> collections2 = (List<Collection>)page2.getItems();

		Assert.assertEquals(collections2.toString(), 1, collections2.size());

		Page<Collection> page3 = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(collection1, collection2, collection3),
			(List<Collection>)page3.getItems());
	}

	@Test
	public void testGetCollectionsPageWithSortDateTime() throws Exception {
		testGetCollectionsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, collection1, collection2) -> {
				BeanUtils.setProperty(
					collection1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCollectionsPageWithSortInteger() throws Exception {
		testGetCollectionsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, collection1, collection2) -> {
				BeanUtils.setProperty(collection1, entityField.getName(), 0);
				BeanUtils.setProperty(collection2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCollectionsPageWithSortString() throws Exception {
		testGetCollectionsPageWithSort(
			EntityField.Type.STRING,
			(entityField, collection1, collection2) -> {
				BeanUtils.setProperty(
					collection1, entityField.getName(), "Aaa");
				BeanUtils.setProperty(
					collection2, entityField.getName(), "Bbb");
			});
	}

	protected void testGetCollectionsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Collection, Collection, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Collection collection1 = randomCollection();
		Collection collection2 = randomCollection();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, collection1, collection2);
		}

		collection1 = testGetCollectionsPage_addCollection(collection1);

		collection2 = testGetCollectionsPage_addCollection(collection2);

		for (EntityField entityField : entityFields) {
			Page<Collection> ascPage = collectionResource.getCollectionsPage(
				null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(collection1, collection2),
				(List<Collection>)ascPage.getItems());

			Page<Collection> descPage = collectionResource.getCollectionsPage(
				null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(collection2, collection1),
				(List<Collection>)descPage.getItems());
		}
	}

	protected Collection testGetCollectionsPage_addCollection(
			Collection collection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostCollection() throws Exception {
		Collection randomCollection = randomCollection();

		Collection postCollection = testPostCollection_addCollection(
			randomCollection);

		assertEquals(randomCollection, postCollection);
		assertValid(postCollection);
	}

	protected Collection testPostCollection_addCollection(Collection collection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteCollection() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetCollection() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostCollectionCheckout() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostCollectionPublish() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		Collection collection1, Collection collection2) {

		Assert.assertTrue(
			collection1 + " does not equal " + collection2,
			equals(collection1, collection2));
	}

	protected void assertEquals(
		List<Collection> collections1, List<Collection> collections2) {

		Assert.assertEquals(collections1.size(), collections2.size());

		for (int i = 0; i < collections1.size(); i++) {
			Collection collection1 = collections1.get(i);
			Collection collection2 = collections2.get(i);

			assertEquals(collection1, collection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Collection> collections1, List<Collection> collections2) {

		Assert.assertEquals(collections1.size(), collections2.size());

		for (Collection collection1 : collections1) {
			boolean contains = false;

			for (Collection collection2 : collections2) {
				if (equals(collection1, collection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				collections2 + " does not contain " + collection1, contains);
		}
	}

	protected void assertValid(Collection collection) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionCount", additionalAssertFieldName)) {
				if (collection.getAdditionCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("collectionId", additionalAssertFieldName)) {
				if (collection.getCollectionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (collection.getCompanyId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateStatus", additionalAssertFieldName)) {
				if (collection.getDateStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("deletionCount", additionalAssertFieldName)) {
				if (collection.getDeletionCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (collection.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"modificationCount", additionalAssertFieldName)) {

				if (collection.getModificationCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (collection.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("statusByUserName", additionalAssertFieldName)) {
				if (collection.getStatusByUserName() == null) {
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

	protected void assertValid(Page<Collection> page) {
		boolean valid = false;

		java.util.Collection<Collection> collections = page.getItems();

		int size = collections.size();

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

	protected boolean equals(Collection collection1, Collection collection2) {
		if (collection1 == collection2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getAdditionCount(),
						collection2.getAdditionCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("collectionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getCollectionId(),
						collection2.getCollectionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getCompanyId(),
						collection2.getCompanyId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getDateStatus(),
						collection2.getDateStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("deletionCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getDeletionCount(),
						collection2.getDeletionCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getDescription(),
						collection2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"modificationCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						collection1.getModificationCount(),
						collection2.getModificationCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getName(), collection2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("statusByUserName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getStatusByUserName(),
						collection2.getStatusByUserName())) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_collectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_collectionResource;

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
		EntityField entityField, String operator, Collection collection) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("additionCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("collectionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("companyId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateStatus")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(collection.getDateStatus(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(collection.getDateStatus(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(collection.getDateStatus()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("deletionCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(collection.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("modificationCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(collection.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("statusByUserName")) {
			sb.append("'");
			sb.append(String.valueOf(collection.getStatusByUserName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Collection randomCollection() throws Exception {
		return new Collection() {
			{
				additionCount = RandomTestUtil.randomLong();
				collectionId = RandomTestUtil.randomLong();
				companyId = RandomTestUtil.randomLong();
				dateStatus = RandomTestUtil.nextDate();
				deletionCount = RandomTestUtil.randomLong();
				description = RandomTestUtil.randomString();
				modificationCount = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				statusByUserName = RandomTestUtil.randomString();
			}
		};
	}

	protected Collection randomIrrelevantCollection() throws Exception {
		Collection randomIrrelevantCollection = randomCollection();

		return randomIrrelevantCollection;
	}

	protected Collection randomPatchCollection() throws Exception {
		return randomCollection();
	}

	protected CollectionResource collectionResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCollectionResourceTestCase.class);

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
	private com.liferay.change.tracking.rest.resource.v1_0.CollectionResource
		_collectionResource;

}