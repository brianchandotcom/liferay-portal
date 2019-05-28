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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();
		testLocale = LocaleUtil.getDefault();
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

		StructuredContent structuredContent1 = randomStructuredContent();

		String json = objectMapper.writeValueAsString(structuredContent1);

		StructuredContent structuredContent2 = StructuredContentSerDes.toDTO(
			json);

		Assert.assertTrue(equals(structuredContent1, structuredContent2));
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

		StructuredContent structuredContent = randomStructuredContent();

		String json1 = objectMapper.writeValueAsString(structuredContent);
		String json2 = StructuredContentSerDes.toJSON(structuredContent);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetContentStructureStructuredContentsPage()
		throws Exception {

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();
		Long irrelevantContentStructureId =
			testGetContentStructureStructuredContentsPage_getIrrelevantContentStructureId();

		if ((irrelevantContentStructureId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetContentStructureStructuredContentsPage_addStructuredContent(
					irrelevantContentStructureId,
					randomIrrelevantStructuredContent());

			Page<StructuredContent> page =
				StructuredContentResource.
					getContentStructureStructuredContentsPage(
						irrelevantContentStructureId, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		Page<StructuredContent> page =
			StructuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

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

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				StructuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null,
						getFilterString(
							entityField, "between", structuredContent1),
						Pagination.of(1, 2), null);

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

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				StructuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null,
						getFilterString(entityField, "eq", structuredContent1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithPagination()
		throws Exception {

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		Page<StructuredContent> page1 =
			StructuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			StructuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

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

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				StructuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				StructuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null, Pagination.of(1, 2),
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

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

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
				contentStructureId, structuredContent1);

		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				StructuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				StructuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
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

	protected Long
			testGetContentStructureStructuredContentsPage_getIrrelevantContentStructureId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetSiteStructuredContentsPage() throws Exception {
		Long siteId = testGetSiteStructuredContentsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteStructuredContentsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetSiteStructuredContentsPage_addStructuredContent(
					irrelevantSiteId, randomIrrelevantStructuredContent());

			Page<StructuredContent> page =
				StructuredContentResource.getSiteStructuredContentsPage(
					irrelevantSiteId, null, null, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		Page<StructuredContent> page =
			StructuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				StructuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null,
					getFilterString(entityField, "between", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				StructuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		Page<StructuredContent> page1 =
			StructuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			StructuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

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
	public void testGetSiteStructuredContentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				StructuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				StructuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				structuredContent2, entityField.getName(), "Bbb");
		}

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				StructuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				StructuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetSiteStructuredContentsPage_addStructuredContent(
				Long siteId, StructuredContent structuredContent)
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			siteId, structuredContent);
	}

	protected Long testGetSiteStructuredContentsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteStructuredContentsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testPostSiteStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostSiteStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	protected StructuredContent
			testPostSiteStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGetSiteStructuredContentsPage_getSiteId(), structuredContent);
	}

	@Test
	public void testGetSiteStructuredContentByKey() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByKey_addStructuredContent();

		StructuredContent getStructuredContent =
			StructuredContentResource.getSiteStructuredContentByKey(
				postStructuredContent.getSiteId(),
				postStructuredContent.getKey());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetSiteStructuredContentByKey_addStructuredContent()
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetSiteStructuredContentByUuid() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByUuid_addStructuredContent();

		StructuredContent getStructuredContent =
			StructuredContentResource.getSiteStructuredContentByUuid(
				postStructuredContent.getSiteId(),
				postStructuredContent.getUuid());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetSiteStructuredContentByUuid_addStructuredContent()
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPage()
		throws Exception {

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();
		Long irrelevantStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId();

		if ((irrelevantStructuredContentFolderId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
					irrelevantStructuredContentFolderId,
					randomIrrelevantStructuredContent());

			Page<StructuredContent> page =
				StructuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						irrelevantStructuredContentFolderId, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		Page<StructuredContent> page =
			StructuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				StructuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null,
						getFilterString(
							entityField, "between", structuredContent1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				StructuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null,
						getFilterString(entityField, "eq", structuredContent1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithPagination()
		throws Exception {

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		Page<StructuredContent> page1 =
			StructuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, Pagination.of(1, 2),
					null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			StructuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

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
	public void testGetStructuredContentFolderStructuredContentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				StructuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				StructuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				structuredContent1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				structuredContent2, entityField.getName(), "Bbb");
		}

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				StructuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				StructuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				Long structuredContentFolderId,
				StructuredContent structuredContent)
		throws Exception {

		return StructuredContentResource.
			postStructuredContentFolderStructuredContent(
				structuredContentFolderId, structuredContent);
	}

	protected Long
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostStructuredContentFolderStructuredContent()
		throws Exception {

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostStructuredContentFolderStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	protected StructuredContent
			testPostStructuredContentFolderStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		return StructuredContentResource.
			postStructuredContentFolderStructuredContent(
				testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId(),
				structuredContent);
	}

	@Test
	public void testDeleteStructuredContent() throws Exception {
		StructuredContent structuredContent =
			testDeleteStructuredContent_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			StructuredContentResource.deleteStructuredContentHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			StructuredContentResource.getStructuredContentHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			StructuredContentResource.getStructuredContentHttpResponse(0L));
	}

	protected StructuredContent
			testDeleteStructuredContent_addStructuredContent()
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		StructuredContent getStructuredContent =
			StructuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testGetStructuredContent_addStructuredContent()
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testPatchStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPatchStructuredContent_addStructuredContent();

		StructuredContent randomPatchStructuredContent =
			randomPatchStructuredContent();

		StructuredContent patchStructuredContent =
			StructuredContentResource.patchStructuredContent(
				postStructuredContent.getId(), randomPatchStructuredContent);

		StructuredContent expectedPatchStructuredContent =
			(StructuredContent)BeanUtils.cloneBean(postStructuredContent);

		_beanUtilsBean.copyProperties(
			expectedPatchStructuredContent, randomPatchStructuredContent);

		StructuredContent getStructuredContent =
			StructuredContentResource.getStructuredContent(
				patchStructuredContent.getId());

		assertEquals(expectedPatchStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testPatchStructuredContent_addStructuredContent()
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testPutStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPutStructuredContent_addStructuredContent();

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent putStructuredContent =
			StructuredContentResource.putStructuredContent(
				postStructuredContent.getId(), randomStructuredContent);

		assertEquals(randomStructuredContent, putStructuredContent);
		assertValid(putStructuredContent);

		StructuredContent getStructuredContent =
			StructuredContentResource.getStructuredContent(
				putStructuredContent.getId());

		assertEquals(randomStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testPutStructuredContent_addStructuredContent()
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testDeleteStructuredContentMyRating() throws Exception {
		StructuredContent structuredContent =
			testDeleteStructuredContentMyRating_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			StructuredContentResource.
				deleteStructuredContentMyRatingHttpResponse(
					structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			StructuredContentResource.getStructuredContentMyRatingHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			StructuredContentResource.getStructuredContentMyRatingHttpResponse(
				0L));
	}

	protected StructuredContent
			testDeleteStructuredContentMyRating_addStructuredContent()
		throws Exception {

		return StructuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetStructuredContentRenderedContentTemplate()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		Assert.assertTrue(
			structuredContent1 + " does not equal " + structuredContent2,
			equals(structuredContent1, structuredContent2));
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

	protected void assertValid(StructuredContent structuredContent) {
		boolean valid = true;

		if (structuredContent.getDateCreated() == null) {
			valid = false;
		}

		if (structuredContent.getDateModified() == null) {
			valid = false;
		}

		if (structuredContent.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				structuredContent.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (structuredContent.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (structuredContent.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentFields", additionalAssertFieldName)) {
				if (structuredContent.getContentFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureId", additionalAssertFieldName)) {

				if (structuredContent.getContentStructureId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (structuredContent.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (structuredContent.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (structuredContent.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (structuredContent.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (structuredContent.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (structuredContent.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (structuredContent.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (structuredContent.getNumberOfComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (structuredContent.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("renderedContents", additionalAssertFieldName)) {
				if (structuredContent.getRenderedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (structuredContent.getTaxonomyCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (structuredContent.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (structuredContent.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (structuredContent.getUuid() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (structuredContent.getViewableBy() == null) {
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

	protected void assertValid(Page<StructuredContent> page) {
		boolean valid = false;

		Collection<StructuredContent> structuredContents = page.getItems();

		int size = structuredContents.size();

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

	protected boolean equals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		if (structuredContent1 == structuredContent2) {
			return true;
		}

		if (!Objects.equals(
				structuredContent1.getSiteId(),
				structuredContent2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getAggregateRating(),
						structuredContent2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getAvailableLanguages(),
						structuredContent2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getContentFields(),
						structuredContent2.getContentFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getContentStructureId(),
						structuredContent2.getContentStructureId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getCreator(),
						structuredContent2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getCustomFields(),
						structuredContent2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDateCreated(),
						structuredContent2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDateModified(),
						structuredContent2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDatePublished(),
						structuredContent2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDescription(),
						structuredContent2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getFriendlyUrlPath(),
						structuredContent2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getId(),
						structuredContent2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getKey(),
						structuredContent2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getKeywords(),
						structuredContent2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getNumberOfComments(),
						structuredContent2.getNumberOfComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getRelatedContents(),
						structuredContent2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("renderedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getRenderedContents(),
						structuredContent2.getRenderedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getTaxonomyCategories(),
						structuredContent2.getTaxonomyCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getTaxonomyCategoryIds(),
						structuredContent2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getTitle(),
						structuredContent2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getUuid(),
						structuredContent2.getUuid())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getViewableBy(),
						structuredContent2.getViewableBy())) {

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

		if (entityFieldName.equals("contentFields")) {
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

		if (entityFieldName.equals("customFields")) {
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
						DateUtils.addSeconds(
							structuredContent.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(structuredContent.getDateCreated()));
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
							structuredContent.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(structuredContent.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("datePublished")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(structuredContent.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("relatedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("renderedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("uuid")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getUuid()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected StructuredContent randomStructuredContent() throws Exception {
		return new StructuredContent() {
			{
				contentStructureId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				key = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				title = RandomTestUtil.randomString();
				uuid = RandomTestUtil.randomString();
			}
		};
	}

	protected StructuredContent randomIrrelevantStructuredContent()
		throws Exception {

		StructuredContent randomIrrelevantStructuredContent =
			randomStructuredContent();

		randomIrrelevantStructuredContent.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantStructuredContent;
	}

	protected StructuredContent randomPatchStructuredContent()
		throws Exception {

		return randomStructuredContent();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStructuredContentResourceTestCase.class);

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
	private
		com.liferay.headless.delivery.resource.v1_0.StructuredContentResource
			_structuredContentResource;

}