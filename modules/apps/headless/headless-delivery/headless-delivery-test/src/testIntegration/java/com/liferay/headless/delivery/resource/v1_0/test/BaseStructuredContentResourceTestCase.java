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

import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.log4j.Level;

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_structuredContentResource.setContextCompany(testCompany);

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		structuredContentResource = builder.locale(
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
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setDescription(regex);
		structuredContent.setFriendlyUrlPath(regex);
		structuredContent.setKey(regex);
		structuredContent.setTitle(regex);
		structuredContent.setUuid(regex);

		String json = StructuredContentSerDes.toJSON(structuredContent);

		Assert.assertFalse(json.contains(regex));

		structuredContent = StructuredContentSerDes.toDTO(json);

		Assert.assertEquals(regex, structuredContent.getDescription());
		Assert.assertEquals(regex, structuredContent.getFriendlyUrlPath());
		Assert.assertEquals(regex, structuredContent.getKey());
		Assert.assertEquals(regex, structuredContent.getTitle());
		Assert.assertEquals(regex, structuredContent.getUuid());
	}

	@Test
	public void testGetContentStructureStructuredContentsPage()
		throws Exception {

		Page<StructuredContent> page =
			structuredContentResource.getContentStructureStructuredContentsPage(
				testGetContentStructureStructuredContentsPage_getContentStructureId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();
		Long irrelevantContentStructureId =
			testGetContentStructureStructuredContentsPage_getIrrelevantContentStructureId();

		if ((irrelevantContentStructureId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetContentStructureStructuredContentsPage_addStructuredContent(
					irrelevantContentStructureId,
					randomIrrelevantStructuredContent());

			page =
				structuredContentResource.
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

		page =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			structuredContent1.getId());

		structuredContentResource.deleteStructuredContent(
			structuredContent2.getId());
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
				structuredContentResource.
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
				structuredContentResource.
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
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortString()
		throws Exception {

		testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						structuredContent2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.
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
		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGetSiteStructuredContentsPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteStructuredContentsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetSiteStructuredContentsPage_addStructuredContent(
					irrelevantSiteId, randomIrrelevantStructuredContent());

			page = structuredContentResource.getSiteStructuredContentsPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

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

		page = structuredContentResource.getSiteStructuredContentsPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			structuredContent1.getId());

		structuredContentResource.deleteStructuredContent(
			structuredContent2.getId());
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
				structuredContentResource.getSiteStructuredContentsPage(
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
				structuredContentResource.getSiteStructuredContentsPage(
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
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortString()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						structuredContent2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetSiteStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.getSiteStructuredContentsPage(
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

		return structuredContentResource.postSiteStructuredContent(
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
	public void testGraphQLGetSiteStructuredContentsPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"structuredContents",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject structuredContentsJSONObject = dataJSONObject.getJSONObject(
			"structuredContents");

		Assert.assertEquals(0, structuredContentsJSONObject.get("totalCount"));

		StructuredContent structuredContent1 =
			testGraphQLStructuredContent_addStructuredContent();
		StructuredContent structuredContent2 =
			testGraphQLStructuredContent_addStructuredContent();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		structuredContentsJSONObject = dataJSONObject.getJSONObject(
			"structuredContents");

		Assert.assertEquals(2, structuredContentsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(structuredContent1, structuredContent2),
			structuredContentsJSONObject.getJSONArray("items"));
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

		return structuredContentResource.postSiteStructuredContent(
			testGetSiteStructuredContentsPage_getSiteId(), structuredContent);
	}

	@Test
	public void testGraphQLPostSiteStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent(
				randomStructuredContent);

		Assert.assertTrue(
			equalsJSONObject(
				randomStructuredContent,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(structuredContent))));
	}

	@Test
	public void testGetSiteStructuredContentByKey() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByKey_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getSiteStructuredContentByKey(
				postStructuredContent.getSiteId(),
				postStructuredContent.getKey());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetSiteStructuredContentByKey_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLGetSiteStructuredContentByKey() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"structuredContentByKey",
				new HashMap<String, Object>() {
					{
						put("siteId", structuredContent.getSiteId());
						put("key", structuredContent.getKey());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				structuredContent,
				dataJSONObject.getJSONObject("structuredContentByKey")));
	}

	@Test
	public void testGetSiteStructuredContentByUuid() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByUuid_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getSiteStructuredContentByUuid(
				postStructuredContent.getSiteId(),
				postStructuredContent.getUuid());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetSiteStructuredContentByUuid_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLGetSiteStructuredContentByUuid() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"structuredContentByUuid",
				new HashMap<String, Object>() {
					{
						put("siteId", structuredContent.getSiteId());
						put("uuid", structuredContent.getUuid());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				structuredContent,
				dataJSONObject.getJSONObject("structuredContentByUuid")));
	}

	@Test
	public void testPutSiteStructuredContentPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutSiteStructuredContentPermission_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				putSiteStructuredContentPermissionHttpResponse(
					null,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName("Guest");
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.
				putSiteStructuredContentPermissionHttpResponse(
					null,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected StructuredContent
			testPutSiteStructuredContentPermission_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPage()
		throws Exception {

		Page<StructuredContent> page =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId(),
					null, RandomTestUtil.randomString(), null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();
		Long irrelevantStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId();

		if ((irrelevantStructuredContentFolderId != null)) {
			StructuredContent irrelevantStructuredContent =
				testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
					irrelevantStructuredContentFolderId,
					randomIrrelevantStructuredContent());

			page =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						irrelevantStructuredContentFolderId, null, null, null,
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

		page =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			structuredContent1.getId());

		structuredContentResource.deleteStructuredContent(
			structuredContent2.getId());
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
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null,
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
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null,
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
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null,
					Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortString()
		throws Exception {

		testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						structuredContent2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null, null,
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

		return structuredContentResource.
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

		return structuredContentResource.
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
			structuredContentResource.deleteStructuredContentHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentHttpResponse(0L));
	}

	protected StructuredContent
			testDeleteStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLDeleteStructuredContent() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteStructuredContent",
				new HashMap<String, Object>() {
					{
						put("structuredContentId", structuredContent.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteStructuredContent"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"structuredContent",
					new HashMap<String, Object>() {
						{
							put(
								"structuredContentId",
								structuredContent.getId());
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testGetStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLGetStructuredContent() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"structuredContent",
				new HashMap<String, Object>() {
					{
						put("structuredContentId", structuredContent.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				structuredContent,
				dataJSONObject.getJSONObject("structuredContent")));
	}

	@Test
	public void testPatchStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPatchStructuredContent_addStructuredContent();

		StructuredContent randomPatchStructuredContent =
			randomPatchStructuredContent();

		StructuredContent patchStructuredContent =
			structuredContentResource.patchStructuredContent(
				postStructuredContent.getId(), randomPatchStructuredContent);

		StructuredContent expectedPatchStructuredContent =
			(StructuredContent)BeanUtils.cloneBean(postStructuredContent);

		_beanUtilsBean.copyProperties(
			expectedPatchStructuredContent, randomPatchStructuredContent);

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				patchStructuredContent.getId());

		assertEquals(expectedPatchStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testPatchStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testPutStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPutStructuredContent_addStructuredContent();

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent putStructuredContent =
			structuredContentResource.putStructuredContent(
				postStructuredContent.getId(), randomStructuredContent);

		assertEquals(randomStructuredContent, putStructuredContent);
		assertValid(putStructuredContent);

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				putStructuredContent.getId());

		assertEquals(randomStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testPutStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testDeleteStructuredContentMyRating() throws Exception {
		StructuredContent structuredContent =
			testDeleteStructuredContentMyRating_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				deleteStructuredContentMyRatingHttpResponse(
					structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentMyRatingHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentMyRatingHttpResponse(
				0L));
	}

	protected StructuredContent
			testDeleteStructuredContentMyRating_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testPutStructuredContentPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutStructuredContentPermission_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				putStructuredContentPermissionHttpResponse(
					structuredContent.getId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName("Guest");
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.
				putStructuredContentPermissionHttpResponse(
					0L,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected StructuredContent
			testPutStructuredContentPermission_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentRenderedContentTemplate()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testPutStructuredContentSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutStructuredContentSubscribe_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.putStructuredContentSubscribeHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.putStructuredContentSubscribeHttpResponse(
				0L));
	}

	protected StructuredContent
			testPutStructuredContentSubscribe_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testPutStructuredContentUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutStructuredContentUnsubscribe_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				putStructuredContentUnsubscribeHttpResponse(
					structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.
				putStructuredContentUnsubscribeHttpResponse(0L));
	}

	protected StructuredContent
			testPutStructuredContentUnsubscribe_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentMyRating() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		Rating postRating = testGetStructuredContentMyRating_addRating(
			postStructuredContent.getId(), randomRating());

		Rating getRating =
			structuredContentResource.getStructuredContentMyRating(
				postStructuredContent.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetStructuredContentMyRating_addRating(
			long structuredContentId, Rating rating)
		throws Exception {

		return structuredContentResource.postStructuredContentMyRating(
			structuredContentId, rating);
	}

	@Test
	public void testPostStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutStructuredContentMyRating() throws Exception {
		StructuredContent postStructuredContent =
			testPutStructuredContent_addStructuredContent();

		testPutStructuredContentMyRating_addRating(
			postStructuredContent.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating =
			structuredContentResource.putStructuredContentMyRating(
				postStructuredContent.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);
	}

	protected Rating testPutStructuredContentMyRating_addRating(
			long structuredContentId, Rating rating)
		throws Exception {

		return structuredContentResource.postStructuredContentMyRating(
			structuredContentId, rating);
	}

	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent()
		throws Exception {

		return testGraphQLStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"contentStructureId", additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getContentStructureId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getDescription();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getFriendlyUrlPath();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getKey();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getNumberOfComments();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("siteId", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getSiteId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getSubscribed();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getTitle();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = structuredContent.getUuid();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"createSiteStructuredContent",
				new HashMap<String, Object>() {
					{
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
						put("structuredContent", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<StructuredContent> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		return jsonDeserializer.deserialize(
			String.valueOf(
				dataJSONObject.getJSONObject("createSiteStructuredContent")),
			StructuredContent.class);
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

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
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

	protected void assertEqualsJSONArray(
		List<StructuredContent> structuredContents, JSONArray jsonArray) {

		for (StructuredContent structuredContent : structuredContents) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(structuredContent, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + structuredContent, contains);
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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (structuredContent.getActions() == null) {
					valid = false;
				}

				continue;
			}

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

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (structuredContent.getDescription_i18n() == null) {
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

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (structuredContent.getFriendlyUrlPath_i18n() == null) {
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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (structuredContent.getSubscribed() == null) {
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

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (structuredContent.getTitle_i18n() == null) {
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

		java.util.Collection<StructuredContent> structuredContents =
			page.getItems();

		int size = structuredContents.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Rating rating) {
		boolean valid = true;

		if (rating.getDateCreated() == null) {
			valid = false;
		}

		if (rating.getDateModified() == null) {
			valid = false;
		}

		if (rating.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (rating.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (rating.getBestRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (rating.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (rating.getRatingValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (rating.getWorstRating() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalRatingAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getActions(),
						structuredContent2.getActions())) {

					return false;
				}

				continue;
			}

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

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDescription_i18n(),
						structuredContent2.getDescription_i18n())) {

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

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getFriendlyUrlPath_i18n(),
						structuredContent2.getFriendlyUrlPath_i18n())) {

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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getSubscribed(),
						structuredContent2.getSubscribed())) {

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

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getTitle_i18n(),
						structuredContent2.getTitle_i18n())) {

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

	protected boolean equals(Rating rating1, Rating rating2) {
		if (rating1 == rating2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getActions(), rating2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getBestRating(), rating2.getBestRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getCreator(), rating2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateCreated(), rating2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateModified(), rating2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(rating1.getId(), rating2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getRatingValue(), rating2.getRatingValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getWorstRating(), rating2.getWorstRating())) {

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

	protected boolean equalsJSONObject(
		StructuredContent structuredContent, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("contentStructureId", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getContentStructureId(),
						jsonObject.getLong("contentStructureId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getFriendlyUrlPath(),
						jsonObject.getString("friendlyUrlPath"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getKey(),
						jsonObject.getString("key"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getNumberOfComments(),
						jsonObject.getInt("numberOfComments"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getSubscribed(),
						jsonObject.getBoolean("subscribed"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getTitle(),
						jsonObject.getString("title"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("uuid", fieldName)) {
				if (!Objects.deepEquals(
						structuredContent.getUuid(),
						jsonObject.getString("uuid"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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
		EntityField entityField, String operator,
		StructuredContent structuredContent) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

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

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

		if (entityFieldName.equals("subscribed")) {
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

		if (entityFieldName.equals("title_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
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
				numberOfComments = RandomTestUtil.randomInt();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
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

	protected Rating randomRating() throws Exception {
		return new Rating() {
			{
				bestRating = RandomTestUtil.randomDouble();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				ratingValue = RandomTestUtil.randomDouble();
				worstRating = RandomTestUtil.randomDouble();
			}
		};
	}

	protected StructuredContentResource structuredContentResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

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