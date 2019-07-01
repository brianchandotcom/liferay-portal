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

import com.liferay.change.tracking.rest.client.dto.v1_0.AffectedEntry;
import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.AffectedEntryResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.AffectedEntrySerDes;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

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
public abstract class BaseAffectedEntryResourceTestCase {

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

		_affectedEntryResource.setContextCompany(testCompany);

		AffectedEntryResource.Builder builder = AffectedEntryResource.builder();

		affectedEntryResource = builder.locale(
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

		AffectedEntry affectedEntry1 = randomAffectedEntry();

		String json = objectMapper.writeValueAsString(affectedEntry1);

		AffectedEntry affectedEntry2 = AffectedEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(affectedEntry1, affectedEntry2));
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

		AffectedEntry affectedEntry = randomAffectedEntry();

		String json1 = objectMapper.writeValueAsString(affectedEntry);
		String json2 = AffectedEntrySerDes.toJSON(affectedEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AffectedEntry affectedEntry = randomAffectedEntry();

		affectedEntry.setContentType(regex);
		affectedEntry.setTitle(regex);

		String json = AffectedEntrySerDes.toJSON(affectedEntry);

		Assert.assertFalse(json.contains(regex));

		affectedEntry = AffectedEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, affectedEntry.getContentType());
		Assert.assertEquals(regex, affectedEntry.getTitle());
	}

	@Test
	public void testGetCollectionEntryAffectedEntriesPage() throws Exception {
		Page<AffectedEntry> page =
			affectedEntryResource.getCollectionEntryAffectedEntriesPage(
				testGetCollectionEntryAffectedEntriesPage_getCollectionId(),
				testGetCollectionEntryAffectedEntriesPage_getEntryId(),
				RandomTestUtil.randomString(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long collectionId =
			testGetCollectionEntryAffectedEntriesPage_getCollectionId();
		Long irrelevantCollectionId =
			testGetCollectionEntryAffectedEntriesPage_getIrrelevantCollectionId();
		Long entryId = testGetCollectionEntryAffectedEntriesPage_getEntryId();
		Long irrelevantEntryId =
			testGetCollectionEntryAffectedEntriesPage_getIrrelevantEntryId();

		if ((irrelevantCollectionId != null) && (irrelevantEntryId != null)) {
			AffectedEntry irrelevantAffectedEntry =
				testGetCollectionEntryAffectedEntriesPage_addAffectedEntry(
					irrelevantCollectionId, irrelevantEntryId,
					randomIrrelevantAffectedEntry());

			page = affectedEntryResource.getCollectionEntryAffectedEntriesPage(
				irrelevantCollectionId, irrelevantEntryId, null,
				Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAffectedEntry),
				(List<AffectedEntry>)page.getItems());
			assertValid(page);
		}

		AffectedEntry affectedEntry1 =
			testGetCollectionEntryAffectedEntriesPage_addAffectedEntry(
				collectionId, entryId, randomAffectedEntry());

		AffectedEntry affectedEntry2 =
			testGetCollectionEntryAffectedEntriesPage_addAffectedEntry(
				collectionId, entryId, randomAffectedEntry());

		page = affectedEntryResource.getCollectionEntryAffectedEntriesPage(
			collectionId, entryId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(affectedEntry1, affectedEntry2),
			(List<AffectedEntry>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetCollectionEntryAffectedEntriesPageWithPagination()
		throws Exception {

		Long collectionId =
			testGetCollectionEntryAffectedEntriesPage_getCollectionId();
		Long entryId = testGetCollectionEntryAffectedEntriesPage_getEntryId();

		AffectedEntry affectedEntry1 =
			testGetCollectionEntryAffectedEntriesPage_addAffectedEntry(
				collectionId, entryId, randomAffectedEntry());

		AffectedEntry affectedEntry2 =
			testGetCollectionEntryAffectedEntriesPage_addAffectedEntry(
				collectionId, entryId, randomAffectedEntry());

		AffectedEntry affectedEntry3 =
			testGetCollectionEntryAffectedEntriesPage_addAffectedEntry(
				collectionId, entryId, randomAffectedEntry());

		Page<AffectedEntry> page1 =
			affectedEntryResource.getCollectionEntryAffectedEntriesPage(
				collectionId, entryId, null, Pagination.of(1, 2));

		List<AffectedEntry> affectedEntries1 =
			(List<AffectedEntry>)page1.getItems();

		Assert.assertEquals(
			affectedEntries1.toString(), 2, affectedEntries1.size());

		Page<AffectedEntry> page2 =
			affectedEntryResource.getCollectionEntryAffectedEntriesPage(
				collectionId, entryId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AffectedEntry> affectedEntries2 =
			(List<AffectedEntry>)page2.getItems();

		Assert.assertEquals(
			affectedEntries2.toString(), 1, affectedEntries2.size());

		Page<AffectedEntry> page3 =
			affectedEntryResource.getCollectionEntryAffectedEntriesPage(
				collectionId, entryId, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(affectedEntry1, affectedEntry2, affectedEntry3),
			(List<AffectedEntry>)page3.getItems());
	}

	protected AffectedEntry
			testGetCollectionEntryAffectedEntriesPage_addAffectedEntry(
				Long collectionId, Long entryId, AffectedEntry affectedEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCollectionEntryAffectedEntriesPage_getCollectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetCollectionEntryAffectedEntriesPage_getIrrelevantCollectionId()
		throws Exception {

		return null;
	}

	protected Long testGetCollectionEntryAffectedEntriesPage_getEntryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetCollectionEntryAffectedEntriesPage_getIrrelevantEntryId()
		throws Exception {

		return null;
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AffectedEntry affectedEntry1, AffectedEntry affectedEntry2) {

		Assert.assertTrue(
			affectedEntry1 + " does not equal " + affectedEntry2,
			equals(affectedEntry1, affectedEntry2));
	}

	protected void assertEquals(
		List<AffectedEntry> affectedEntries1,
		List<AffectedEntry> affectedEntries2) {

		Assert.assertEquals(affectedEntries1.size(), affectedEntries2.size());

		for (int i = 0; i < affectedEntries1.size(); i++) {
			AffectedEntry affectedEntry1 = affectedEntries1.get(i);
			AffectedEntry affectedEntry2 = affectedEntries2.get(i);

			assertEquals(affectedEntry1, affectedEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AffectedEntry> affectedEntries1,
		List<AffectedEntry> affectedEntries2) {

		Assert.assertEquals(affectedEntries1.size(), affectedEntries2.size());

		for (AffectedEntry affectedEntry1 : affectedEntries1) {
			boolean contains = false;

			for (AffectedEntry affectedEntry2 : affectedEntries2) {
				if (equals(affectedEntry1, affectedEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				affectedEntries2 + " does not contain " + affectedEntry1,
				contains);
		}
	}

	protected void assertValid(AffectedEntry affectedEntry) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (affectedEntry.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (affectedEntry.getTitle() == null) {
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

	protected void assertValid(Page<AffectedEntry> page) {
		boolean valid = false;

		java.util.Collection<AffectedEntry> affectedEntries = page.getItems();

		int size = affectedEntries.size();

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

	protected boolean equals(
		AffectedEntry affectedEntry1, AffectedEntry affectedEntry2) {

		if (affectedEntry1 == affectedEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						affectedEntry1.getContentType(),
						affectedEntry2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						affectedEntry1.getTitle(), affectedEntry2.getTitle())) {

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

		if (!(_affectedEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_affectedEntryResource;

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
		EntityField entityField, String operator, AffectedEntry affectedEntry) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(affectedEntry.getContentType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(affectedEntry.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected AffectedEntry randomAffectedEntry() throws Exception {
		return new AffectedEntry() {
			{
				contentType = RandomTestUtil.randomString();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected AffectedEntry randomIrrelevantAffectedEntry() throws Exception {
		AffectedEntry randomIrrelevantAffectedEntry = randomAffectedEntry();

		return randomIrrelevantAffectedEntry;
	}

	protected AffectedEntry randomPatchAffectedEntry() throws Exception {
		return randomAffectedEntry();
	}

	protected AffectedEntryResource affectedEntryResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAffectedEntryResourceTestCase.class);

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
	private com.liferay.change.tracking.rest.resource.v1_0.AffectedEntryResource
		_affectedEntryResource;

}