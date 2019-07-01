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
import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.EntryResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.EntrySerDes;
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
public abstract class BaseEntryResourceTestCase {

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

		_entryResource.setContextCompany(testCompany);

		EntryResource.Builder builder = EntryResource.builder();

		entryResource = builder.locale(
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

		Entry entry1 = randomEntry();

		String json = objectMapper.writeValueAsString(entry1);

		Entry entry2 = EntrySerDes.toDTO(json);

		Assert.assertTrue(equals(entry1, entry2));
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

		Entry entry = randomEntry();

		String json1 = objectMapper.writeValueAsString(entry);
		String json2 = EntrySerDes.toJSON(entry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Entry entry = randomEntry();

		entry.setContentType(regex);
		entry.setSiteName(regex);
		entry.setTitle(regex);
		entry.setUserName(regex);
		entry.setVersion(regex);

		String json = EntrySerDes.toJSON(entry);

		Assert.assertFalse(json.contains(regex));

		entry = EntrySerDes.toDTO(json);

		Assert.assertEquals(regex, entry.getContentType());
		Assert.assertEquals(regex, entry.getSiteName());
		Assert.assertEquals(regex, entry.getTitle());
		Assert.assertEquals(regex, entry.getUserName());
		Assert.assertEquals(regex, entry.getVersion());
	}

	@Test
	public void testGetCollectionEntriesPage() throws Exception {
		Page<Entry> page = entryResource.getCollectionEntriesPage(
			null, null, null, testGetCollectionEntriesPage_getCollectionId(),
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long collectionId = testGetCollectionEntriesPage_getCollectionId();
		Long irrelevantCollectionId =
			testGetCollectionEntriesPage_getIrrelevantCollectionId();

		if ((irrelevantCollectionId != null)) {
			Entry irrelevantEntry = testGetCollectionEntriesPage_addEntry(
				irrelevantCollectionId, randomIrrelevantEntry());

			page = entryResource.getCollectionEntriesPage(
				null, null, null, irrelevantCollectionId, null, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantEntry), (List<Entry>)page.getItems());
			assertValid(page);
		}

		Entry entry1 = testGetCollectionEntriesPage_addEntry(
			collectionId, randomEntry());

		Entry entry2 = testGetCollectionEntriesPage_addEntry(
			collectionId, randomEntry());

		page = entryResource.getCollectionEntriesPage(
			null, null, null, collectionId, null, null, null,
			Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(entry1, entry2), (List<Entry>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetCollectionEntriesPageWithPagination() throws Exception {
		Long collectionId = testGetCollectionEntriesPage_getCollectionId();

		Entry entry1 = testGetCollectionEntriesPage_addEntry(
			collectionId, randomEntry());

		Entry entry2 = testGetCollectionEntriesPage_addEntry(
			collectionId, randomEntry());

		Entry entry3 = testGetCollectionEntriesPage_addEntry(
			collectionId, randomEntry());

		Page<Entry> page1 = entryResource.getCollectionEntriesPage(
			null, null, null, collectionId, null, null, null,
			Pagination.of(1, 2), null);

		List<Entry> entries1 = (List<Entry>)page1.getItems();

		Assert.assertEquals(entries1.toString(), 2, entries1.size());

		Page<Entry> page2 = entryResource.getCollectionEntriesPage(
			null, null, null, collectionId, null, null, null,
			Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Entry> entries2 = (List<Entry>)page2.getItems();

		Assert.assertEquals(entries2.toString(), 1, entries2.size());

		Page<Entry> page3 = entryResource.getCollectionEntriesPage(
			null, null, null, collectionId, null, null, null,
			Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(entry1, entry2, entry3),
			(List<Entry>)page3.getItems());
	}

	@Test
	public void testGetCollectionEntriesPageWithSortDateTime()
		throws Exception {

		testGetCollectionEntriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, entry1, entry2) -> {
				BeanUtils.setProperty(
					entry1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCollectionEntriesPageWithSortInteger() throws Exception {
		testGetCollectionEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, entry1, entry2) -> {
				BeanUtils.setProperty(entry1, entityField.getName(), 0);
				BeanUtils.setProperty(entry2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCollectionEntriesPageWithSortString() throws Exception {
		testGetCollectionEntriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, entry1, entry2) -> {
				BeanUtils.setProperty(entry1, entityField.getName(), "Aaa");
				BeanUtils.setProperty(entry2, entityField.getName(), "Bbb");
			});
	}

	protected void testGetCollectionEntriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Entry, Entry, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long collectionId = testGetCollectionEntriesPage_getCollectionId();

		Entry entry1 = randomEntry();
		Entry entry2 = randomEntry();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, entry1, entry2);
		}

		entry1 = testGetCollectionEntriesPage_addEntry(collectionId, entry1);

		entry2 = testGetCollectionEntriesPage_addEntry(collectionId, entry2);

		for (EntityField entityField : entityFields) {
			Page<Entry> ascPage = entryResource.getCollectionEntriesPage(
				null, null, null, collectionId, null, null, null,
				Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(entry1, entry2), (List<Entry>)ascPage.getItems());

			Page<Entry> descPage = entryResource.getCollectionEntriesPage(
				null, null, null, collectionId, null, null, null,
				Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(entry2, entry1),
				(List<Entry>)descPage.getItems());
		}
	}

	protected Entry testGetCollectionEntriesPage_addEntry(
			Long collectionId, Entry entry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCollectionEntriesPage_getCollectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCollectionEntriesPage_getIrrelevantCollectionId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetEntry() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Entry entry1, Entry entry2) {
		Assert.assertTrue(
			entry1 + " does not equal " + entry2, equals(entry1, entry2));
	}

	protected void assertEquals(List<Entry> entries1, List<Entry> entries2) {
		Assert.assertEquals(entries1.size(), entries2.size());

		for (int i = 0; i < entries1.size(); i++) {
			Entry entry1 = entries1.get(i);
			Entry entry2 = entries2.get(i);

			assertEquals(entry1, entry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Entry> entries1, List<Entry> entries2) {

		Assert.assertEquals(entries1.size(), entries2.size());

		for (Entry entry1 : entries1) {
			boolean contains = false;

			for (Entry entry2 : entries2) {
				if (equals(entry1, entry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				entries2 + " does not contain " + entry1, contains);
		}
	}

	protected void assertValid(Entry entry) {
		boolean valid = true;

		if (entry.getDateModified() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"affectedByEntriesCount", additionalAssertFieldName)) {

				if (entry.getAffectedByEntriesCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("changeType", additionalAssertFieldName)) {
				if (entry.getChangeType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("classNameId", additionalAssertFieldName)) {
				if (entry.getClassNameId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("classPK", additionalAssertFieldName)) {
				if (entry.getClassPK() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("collision", additionalAssertFieldName)) {
				if (entry.getCollision() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (entry.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("entryId", additionalAssertFieldName)) {
				if (entry.getEntryId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (entry.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("siteName", additionalAssertFieldName)) {
				if (entry.getSiteName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (entry.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (entry.getUserName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (entry.getVersion() == null) {
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

	protected void assertValid(Page<Entry> page) {
		boolean valid = false;

		java.util.Collection<Entry> entries = page.getItems();

		int size = entries.size();

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

	protected boolean equals(Entry entry1, Entry entry2) {
		if (entry1 == entry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"affectedByEntriesCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						entry1.getAffectedByEntriesCount(),
						entry2.getAffectedByEntriesCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("changeType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getChangeType(), entry2.getChangeType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("classNameId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getClassNameId(), entry2.getClassNameId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("classPK", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getClassPK(), entry2.getClassPK())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("collision", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getCollision(), entry2.getCollision())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getContentType(), entry2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getDateModified(), entry2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("entryId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getEntryId(), entry2.getEntryId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(entry1.getKey(), entry2.getKey())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("siteName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getSiteName(), entry2.getSiteName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(entry1.getTitle(), entry2.getTitle())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getUserName(), entry2.getUserName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						entry1.getVersion(), entry2.getVersion())) {

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

		if (!(_entryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_entryResource;

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
		EntityField entityField, String operator, Entry entry) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("affectedByEntriesCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("changeType")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("classNameId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("classPK")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("collision")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(entry.getContentType()));
			sb.append("'");

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
						DateUtils.addSeconds(entry.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(entry.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(entry.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("entryId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteName")) {
			sb.append("'");
			sb.append(String.valueOf(entry.getSiteName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(entry.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(entry.getUserName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("version")) {
			sb.append("'");
			sb.append(String.valueOf(entry.getVersion()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Entry randomEntry() throws Exception {
		return new Entry() {
			{
				classNameId = RandomTestUtil.randomLong();
				classPK = RandomTestUtil.randomLong();
				collision = RandomTestUtil.randomBoolean();
				contentType = RandomTestUtil.randomString();
				dateModified = RandomTestUtil.nextDate();
				entryId = RandomTestUtil.randomLong();
				key = RandomTestUtil.randomLong();
				siteName = RandomTestUtil.randomString();
				title = RandomTestUtil.randomString();
				userName = RandomTestUtil.randomString();
				version = RandomTestUtil.randomString();
			}
		};
	}

	protected Entry randomIrrelevantEntry() throws Exception {
		Entry randomIrrelevantEntry = randomEntry();

		return randomIrrelevantEntry;
	}

	protected Entry randomPatchEntry() throws Exception {
		return randomEntry();
	}

	protected EntryResource entryResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEntryResourceTestCase.class);

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
	private com.liferay.change.tracking.rest.resource.v1_0.EntryResource
		_entryResource;

}