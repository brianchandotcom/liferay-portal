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

import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;

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
public abstract class BaseStructuredContentResourceTestCase {

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
	public void testDeleteStructuredContent() throws Exception {
		StructuredContent structuredContent =
			testDeleteStructuredContent_addStructuredContent();

		assertResponseCode(
			200,
			invokeDeleteStructuredContentResponse(structuredContent.getId()));

		assertResponseCode(
			404, invokeGetStructuredContentResponse(structuredContent.getId()));
	}

	@Test
	public void testGetContentSpaceStructuredContentsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());
		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		Page<StructuredContent> page =
			invokeGetContentSpaceStructuredContentsPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				(String)null);

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent randomStructuredContent1 = randomStructuredContent();

		randomStructuredContent1.setDatePublished(
			DateUtils.addMinutes(new Date(), -2));

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent1);

		Thread.sleep(1000);

		StructuredContent randomStructuredContent2 = randomStructuredContent();

		randomStructuredContent2.setDatePublished(
			DateUtils.addMinutes(
				randomStructuredContent1.getDatePublished(), 1));

		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());
		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());
		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());
		StructuredContent structuredContent3 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent());

		Page<StructuredContent> page1 =
			invokeGetContentSpaceStructuredContentsPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				(String)null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			invokeGetContentSpaceStructuredContentsPage(
				contentSpaceId, (String)null, Pagination.of(2, 2),
				(String)null);

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			new ArrayList<StructuredContent>() {
				{
					addAll(structuredContents1);
					addAll(structuredContents2);
				}
			});
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent randomStructuredContent1 = randomStructuredContent();

		randomStructuredContent1.setDatePublished(
			DateUtils.addMinutes(new Date(), -2));

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent1);

		StructuredContent randomStructuredContent2 = randomStructuredContent();

		randomStructuredContent2.setDatePublished(
			DateUtils.addMinutes(
				randomStructuredContent1.getDatePublished(), 1));

		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, randomStructuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceStructuredContentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceStructuredContentsPage_getContentSpaceId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				structuredContent2, entityField.getName(), "Bbb");
		}

		structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent1);
		structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				contentSpaceId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentSpaceStructuredContentsPage(
					contentSpaceId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPage()
		throws Exception {

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		Page<StructuredContent> page =
			invokeGetContentStructureStructuredContentsPage(
				contentStructureId, (String)null, Pagination.of(2, 1),
				(String)null);

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		StructuredContent randomStructuredContent1 = randomStructuredContent();

		randomStructuredContent1.setDatePublished(
			DateUtils.addMinutes(new Date(), -2));

		StructuredContent structuredContent1 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent1);

		Thread.sleep(1000);

		StructuredContent randomStructuredContent2 = randomStructuredContent();

		randomStructuredContent2.setDatePublished(
			DateUtils.addMinutes(
				randomStructuredContent1.getDatePublished(), 1));

		StructuredContent structuredContent2 =
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentStructureStructuredContentsPage(
					testGetContentStructureStructuredContentsPage_getContentStructureId(),
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());
		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				invokeGetContentStructureStructuredContentsPage(
					testGetContentStructureStructuredContentsPage_getContentStructureId(),
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithPagination()
		throws Exception {

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());
		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());
		StructuredContent structuredContent3 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		Page<StructuredContent> page1 =
			invokeGetContentStructureStructuredContentsPage(
				contentStructureId, (String)null, Pagination.of(2, 1),
				(String)null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			invokeGetContentStructureStructuredContentsPage(
				contentStructureId, (String)null, Pagination.of(2, 2),
				(String)null);

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			new ArrayList<StructuredContent>() {
				{
					addAll(structuredContents1);
					addAll(structuredContents2);
				}
			});
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		StructuredContent randomStructuredContent1 = randomStructuredContent();

		randomStructuredContent1.setDatePublished(
			DateUtils.addMinutes(new Date(), -2));

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent1);
		
		StructuredContent randomStructuredContent2 = randomStructuredContent();

		randomStructuredContent2.setDatePublished(
			DateUtils.addMinutes(
				randomStructuredContent1.getDatePublished(), 1));

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), randomStructuredContent2);

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				structuredContent2, entityField.getName(), "Bbb");
		}

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), structuredContent1);
		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				testGroup.getGroupId(), structuredContent2);

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				invokeGetContentStructureStructuredContentsPage(
					contentStructureId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		StructuredContent getStructuredContent = invokeGetStructuredContent(
			postStructuredContent.getId());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	@Test
	public void testGetStructuredContentTemplate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPatchStructuredContent() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostContentSpaceStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	@Test
	public void testPutStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPutStructuredContent_addStructuredContent();

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent putStructuredContent = invokePutStructuredContent(
			postStructuredContent.getId(), randomStructuredContent);

		assertEquals(randomStructuredContent, putStructuredContent);
		assertValid(putStructuredContent);

		StructuredContent getStructuredContent = invokeGetStructuredContent(
			putStructuredContent.getId());

		assertEquals(randomStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected void assertEquals(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (int i = 0; i < structuredContents1.size(); i++) {
			StructuredContent structuredContent1 = structuredContents1.get(i);
			StructuredContent structuredContent2 = structuredContents2.get(i);

			assertEquals(structuredContent1, structuredContent2);
		}
	}

	protected void assertEquals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		Assert.assertTrue(
			structuredContent1 + " does not equal " + structuredContent2,
			equals(structuredContent1, structuredContent2));
	}

	protected void assertEqualsIgnoringOrder(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (StructuredContent structuredContent1 : structuredContents1) {
			boolean contains = false;

			for (StructuredContent structuredContent2 : structuredContents2) {
				if (equals(structuredContent1, structuredContent2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				structuredContents2 + " does not contain " + structuredContent1,
				contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Page<StructuredContent> page) {
		boolean valid = false;

		Collection<StructuredContent> structuredContents = page.getItems();

		int size = structuredContents.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(StructuredContent structuredContent) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		if (structuredContent1 == structuredContent2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_structuredContentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_structuredContentResource;

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
		StructuredContent structuredContent) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("categories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("categoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentSpace")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentStructureId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(structuredContent.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(structuredContent.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("datePublished")) {
			sb.append(_dateFormat.format(structuredContent.getDatePublished()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("lastReviewed")) {
			sb.append(_dateFormat.format(structuredContent.getLastReviewed()));

			return sb.toString();
		}

		if (entityFieldName.equals("renderedContentsURL")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected boolean invokeDeleteStructuredContent(Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteStructuredContentResponse(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<StructuredContent>
			invokeGetContentSpaceStructuredContentsPage(
				Long contentSpaceId, String filterString, Pagination pagination,
				String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceStructuredContentsLocation(
				contentSpaceId, filterString, pagination, sortString));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<StructuredContent>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceStructuredContentsPageResponse(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceStructuredContentsLocation(
				contentSpaceId, filterString, pagination, sortString));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<StructuredContent>
			invokeGetContentStructureStructuredContentsPage(
				Long contentStructureId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentStructureStructuredContentsLocation(
				contentStructureId, filterString, pagination, sortString));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<StructuredContent>>() {
			});
	}

	protected Http.Response
			invokeGetContentStructureStructuredContentsPageResponse(
				Long contentStructureId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentStructureStructuredContentsLocation(
				contentStructureId, filterString, pagination, sortString));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected StructuredContent invokeGetStructuredContent(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), StructuredContent.class);
	}

	protected Http.Response invokeGetStructuredContentResponse(
			Long structuredContentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected String invokeGetStructuredContentTemplate(
			Long structuredContentId, Long templateId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/rendered-content/{template-id}",
					structuredContentId));

		return HttpUtil.URLtoString(options);
	}

	protected Http.Response invokeGetStructuredContentTemplateResponse(
			Long structuredContentId, Long templateId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}/rendered-content/{template-id}",
					structuredContentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected StructuredContent invokePatchStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), StructuredContent.class);
	}

	protected Http.Response invokePatchStructuredContentResponse(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected StructuredContent invokePostContentSpaceStructuredContent(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/structured-contents",
					contentSpaceId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), StructuredContent.class);
	}

	protected Http.Response invokePostContentSpaceStructuredContentResponse(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/structured-contents",
					contentSpaceId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected StructuredContent invokePutStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), StructuredContent.class);
	}

	protected Http.Response invokePutStructuredContentResponse(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(structuredContent),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/structured-contents/{structured-content-id}",
					structuredContentId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected StructuredContent randomStructuredContent() {
		return new StructuredContent() {
			{
				contentSpace = RandomTestUtil.randomLong();
				contentStructureId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				lastReviewed = RandomTestUtil.nextDate();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected StructuredContent
			testDeleteStructuredContent_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent
			testGetContentSpaceStructuredContentsPage_addStructuredContent(
				Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceStructuredContentsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected StructuredContent
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				Long contentStructureId, StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentStructureStructuredContentsPage_getContentStructureId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent testGetStructuredContent_addStructuredContent()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent
			testPostContentSpaceStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected StructuredContent testPutStructuredContent_addStructuredContent()
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

	private String _getContentSpaceStructuredContentsLocation(
		Long contentSpaceId, String filterString, Pagination pagination,
		String sortString) {

		String url =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/structured-contents",
					contentSpaceId);

		url += "?filter=" + URLCodec.encodeURL(filterString);
		url += "&page=" + pagination.getPageNumber();
		url += "&pageSize=" + pagination.getItemsPerPage();
		url += "&sort=" + URLCodec.encodeURL(sortString);

		return url;
	}

	private String _getContentStructureStructuredContentsLocation(
		Long contentStructureId, String filterString, Pagination pagination,
		String sortString) {

		String url =
			_resourceURL +
				_toPath(
					"/content-structures/{content-structure-id}/structured-contents",
					contentStructureId);

		url += "?filter=" + URLCodec.encodeURL(filterString);
		url += "&page=" + pagination.getPageNumber();
		url += "&pageSize=" + pagination.getItemsPerPage();
		url += "&sort=" + URLCodec.encodeURL(sortString);

		return url;
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
	private StructuredContentResource _structuredContentResource;

}