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

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.ContentTemplate;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.ContentTemplateResource;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentTemplateSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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
public abstract class BaseContentTemplateResourceTestCase {

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

		testDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		_contentTemplateResource.setContextCompany(testCompany);

		ContentTemplateResource.Builder builder =
			ContentTemplateResource.builder();

		contentTemplateResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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

		ContentTemplate contentTemplate1 = randomContentTemplate();

		String json = objectMapper.writeValueAsString(contentTemplate1);

		ContentTemplate contentTemplate2 = ContentTemplateSerDes.toDTO(json);

		Assert.assertTrue(equals(contentTemplate1, contentTemplate2));
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

		ContentTemplate contentTemplate = randomContentTemplate();

		String json1 = objectMapper.writeValueAsString(contentTemplate);
		String json2 = ContentTemplateSerDes.toJSON(contentTemplate);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ContentTemplate contentTemplate = randomContentTemplate();

		contentTemplate.setAssetLibraryKey(regex);
		contentTemplate.setDescription(regex);
		contentTemplate.setId(regex);
		contentTemplate.setName(regex);
		contentTemplate.setProgrammingLanguage(regex);
		contentTemplate.setTemplateScript(regex);

		String json = ContentTemplateSerDes.toJSON(contentTemplate);

		Assert.assertFalse(json.contains(regex));

		contentTemplate = ContentTemplateSerDes.toDTO(json);

		Assert.assertEquals(regex, contentTemplate.getAssetLibraryKey());
		Assert.assertEquals(regex, contentTemplate.getDescription());
		Assert.assertEquals(regex, contentTemplate.getId());
		Assert.assertEquals(regex, contentTemplate.getName());
		Assert.assertEquals(regex, contentTemplate.getProgrammingLanguage());
		Assert.assertEquals(regex, contentTemplate.getTemplateScript());
	}

	@Test
	public void testGetAssetLibraryContentTemplatesPage() throws Exception {
		Page<ContentTemplate> page =
			contentTemplateResource.getAssetLibraryContentTemplatesPage(
				testGetAssetLibraryContentTemplatesPage_getAssetLibraryId(),
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long assetLibraryId =
			testGetAssetLibraryContentTemplatesPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryContentTemplatesPage_getIrrelevantAssetLibraryId();

		if ((irrelevantAssetLibraryId != null)) {
			ContentTemplate irrelevantContentTemplate =
				testGetAssetLibraryContentTemplatesPage_addContentTemplate(
					irrelevantAssetLibraryId,
					randomIrrelevantContentTemplate());

			page = contentTemplateResource.getAssetLibraryContentTemplatesPage(
				irrelevantAssetLibraryId, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentTemplate),
				(List<ContentTemplate>)page.getItems());
			assertValid(page);
		}

		ContentTemplate contentTemplate1 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, randomContentTemplate());

		ContentTemplate contentTemplate2 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, randomContentTemplate());

		page = contentTemplateResource.getAssetLibraryContentTemplatesPage(
			assetLibraryId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentTemplate1, contentTemplate2),
			(List<ContentTemplate>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAssetLibraryContentTemplatesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryContentTemplatesPage_getAssetLibraryId();

		ContentTemplate contentTemplate1 = randomContentTemplate();

		contentTemplate1 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, contentTemplate1);

		for (EntityField entityField : entityFields) {
			Page<ContentTemplate> page =
				contentTemplateResource.getAssetLibraryContentTemplatesPage(
					assetLibraryId, null, null,
					getFilterString(entityField, "between", contentTemplate1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentTemplate1),
				(List<ContentTemplate>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryContentTemplatesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryContentTemplatesPage_getAssetLibraryId();

		ContentTemplate contentTemplate1 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, randomContentTemplate());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentTemplate contentTemplate2 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, randomContentTemplate());

		for (EntityField entityField : entityFields) {
			Page<ContentTemplate> page =
				contentTemplateResource.getAssetLibraryContentTemplatesPage(
					assetLibraryId, null, null,
					getFilterString(entityField, "eq", contentTemplate1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentTemplate1),
				(List<ContentTemplate>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryContentTemplatesPageWithPagination()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryContentTemplatesPage_getAssetLibraryId();

		ContentTemplate contentTemplate1 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, randomContentTemplate());

		ContentTemplate contentTemplate2 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, randomContentTemplate());

		ContentTemplate contentTemplate3 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, randomContentTemplate());

		Page<ContentTemplate> page1 =
			contentTemplateResource.getAssetLibraryContentTemplatesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 2), null);

		List<ContentTemplate> contentTemplates1 =
			(List<ContentTemplate>)page1.getItems();

		Assert.assertEquals(
			contentTemplates1.toString(), 2, contentTemplates1.size());

		Page<ContentTemplate> page2 =
			contentTemplateResource.getAssetLibraryContentTemplatesPage(
				assetLibraryId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentTemplate> contentTemplates2 =
			(List<ContentTemplate>)page2.getItems();

		Assert.assertEquals(
			contentTemplates2.toString(), 1, contentTemplates2.size());

		Page<ContentTemplate> page3 =
			contentTemplateResource.getAssetLibraryContentTemplatesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(contentTemplate1, contentTemplate2, contentTemplate3),
			(List<ContentTemplate>)page3.getItems());
	}

	@Test
	public void testGetAssetLibraryContentTemplatesPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryContentTemplatesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, contentTemplate1, contentTemplate2) -> {
				BeanUtils.setProperty(
					contentTemplate1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryContentTemplatesPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryContentTemplatesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, contentTemplate1, contentTemplate2) -> {
				BeanUtils.setProperty(
					contentTemplate1, entityField.getName(), 0);
				BeanUtils.setProperty(
					contentTemplate2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryContentTemplatesPageWithSortString()
		throws Exception {

		testGetAssetLibraryContentTemplatesPageWithSort(
			EntityField.Type.STRING,
			(entityField, contentTemplate1, contentTemplate2) -> {
				Class<?> clazz = contentTemplate1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						contentTemplate1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						contentTemplate2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						contentTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						contentTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						contentTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						contentTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryContentTemplatesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, ContentTemplate, ContentTemplate, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryContentTemplatesPage_getAssetLibraryId();

		ContentTemplate contentTemplate1 = randomContentTemplate();
		ContentTemplate contentTemplate2 = randomContentTemplate();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, contentTemplate1, contentTemplate2);
		}

		contentTemplate1 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, contentTemplate1);

		contentTemplate2 =
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				assetLibraryId, contentTemplate2);

		for (EntityField entityField : entityFields) {
			Page<ContentTemplate> ascPage =
				contentTemplateResource.getAssetLibraryContentTemplatesPage(
					assetLibraryId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentTemplate1, contentTemplate2),
				(List<ContentTemplate>)ascPage.getItems());

			Page<ContentTemplate> descPage =
				contentTemplateResource.getAssetLibraryContentTemplatesPage(
					assetLibraryId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentTemplate2, contentTemplate1),
				(List<ContentTemplate>)descPage.getItems());
		}
	}

	protected ContentTemplate
			testGetAssetLibraryContentTemplatesPage_addContentTemplate(
				Long assetLibraryId, ContentTemplate contentTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAssetLibraryContentTemplatesPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long
			testGetAssetLibraryContentTemplatesPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetSiteContentTemplatesPage() throws Exception {
		Page<ContentTemplate> page =
			contentTemplateResource.getSiteContentTemplatesPage(
				testGetSiteContentTemplatesPage_getSiteId(),
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteContentTemplatesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentTemplatesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			ContentTemplate irrelevantContentTemplate =
				testGetSiteContentTemplatesPage_addContentTemplate(
					irrelevantSiteId, randomIrrelevantContentTemplate());

			page = contentTemplateResource.getSiteContentTemplatesPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentTemplate),
				(List<ContentTemplate>)page.getItems());
			assertValid(page);
		}

		ContentTemplate contentTemplate1 =
			testGetSiteContentTemplatesPage_addContentTemplate(
				siteId, randomContentTemplate());

		ContentTemplate contentTemplate2 =
			testGetSiteContentTemplatesPage_addContentTemplate(
				siteId, randomContentTemplate());

		page = contentTemplateResource.getSiteContentTemplatesPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentTemplate1, contentTemplate2),
			(List<ContentTemplate>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentTemplatesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentTemplatesPage_getSiteId();

		ContentTemplate contentTemplate1 = randomContentTemplate();

		contentTemplate1 = testGetSiteContentTemplatesPage_addContentTemplate(
			siteId, contentTemplate1);

		for (EntityField entityField : entityFields) {
			Page<ContentTemplate> page =
				contentTemplateResource.getSiteContentTemplatesPage(
					siteId, null, null,
					getFilterString(entityField, "between", contentTemplate1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentTemplate1),
				(List<ContentTemplate>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentTemplatesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentTemplatesPage_getSiteId();

		ContentTemplate contentTemplate1 =
			testGetSiteContentTemplatesPage_addContentTemplate(
				siteId, randomContentTemplate());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentTemplate contentTemplate2 =
			testGetSiteContentTemplatesPage_addContentTemplate(
				siteId, randomContentTemplate());

		for (EntityField entityField : entityFields) {
			Page<ContentTemplate> page =
				contentTemplateResource.getSiteContentTemplatesPage(
					siteId, null, null,
					getFilterString(entityField, "eq", contentTemplate1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentTemplate1),
				(List<ContentTemplate>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentTemplatesPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteContentTemplatesPage_getSiteId();

		ContentTemplate contentTemplate1 =
			testGetSiteContentTemplatesPage_addContentTemplate(
				siteId, randomContentTemplate());

		ContentTemplate contentTemplate2 =
			testGetSiteContentTemplatesPage_addContentTemplate(
				siteId, randomContentTemplate());

		ContentTemplate contentTemplate3 =
			testGetSiteContentTemplatesPage_addContentTemplate(
				siteId, randomContentTemplate());

		Page<ContentTemplate> page1 =
			contentTemplateResource.getSiteContentTemplatesPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<ContentTemplate> contentTemplates1 =
			(List<ContentTemplate>)page1.getItems();

		Assert.assertEquals(
			contentTemplates1.toString(), 2, contentTemplates1.size());

		Page<ContentTemplate> page2 =
			contentTemplateResource.getSiteContentTemplatesPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentTemplate> contentTemplates2 =
			(List<ContentTemplate>)page2.getItems();

		Assert.assertEquals(
			contentTemplates2.toString(), 1, contentTemplates2.size());

		Page<ContentTemplate> page3 =
			contentTemplateResource.getSiteContentTemplatesPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(contentTemplate1, contentTemplate2, contentTemplate3),
			(List<ContentTemplate>)page3.getItems());
	}

	@Test
	public void testGetSiteContentTemplatesPageWithSortDateTime()
		throws Exception {

		testGetSiteContentTemplatesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, contentTemplate1, contentTemplate2) -> {
				BeanUtils.setProperty(
					contentTemplate1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteContentTemplatesPageWithSortInteger()
		throws Exception {

		testGetSiteContentTemplatesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, contentTemplate1, contentTemplate2) -> {
				BeanUtils.setProperty(
					contentTemplate1, entityField.getName(), 0);
				BeanUtils.setProperty(
					contentTemplate2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteContentTemplatesPageWithSortString()
		throws Exception {

		testGetSiteContentTemplatesPageWithSort(
			EntityField.Type.STRING,
			(entityField, contentTemplate1, contentTemplate2) -> {
				Class<?> clazz = contentTemplate1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						contentTemplate1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						contentTemplate2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						contentTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						contentTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						contentTemplate1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						contentTemplate2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteContentTemplatesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, ContentTemplate, ContentTemplate, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentTemplatesPage_getSiteId();

		ContentTemplate contentTemplate1 = randomContentTemplate();
		ContentTemplate contentTemplate2 = randomContentTemplate();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, contentTemplate1, contentTemplate2);
		}

		contentTemplate1 = testGetSiteContentTemplatesPage_addContentTemplate(
			siteId, contentTemplate1);

		contentTemplate2 = testGetSiteContentTemplatesPage_addContentTemplate(
			siteId, contentTemplate2);

		for (EntityField entityField : entityFields) {
			Page<ContentTemplate> ascPage =
				contentTemplateResource.getSiteContentTemplatesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentTemplate1, contentTemplate2),
				(List<ContentTemplate>)ascPage.getItems());

			Page<ContentTemplate> descPage =
				contentTemplateResource.getSiteContentTemplatesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentTemplate2, contentTemplate1),
				(List<ContentTemplate>)descPage.getItems());
		}
	}

	protected ContentTemplate
			testGetSiteContentTemplatesPage_addContentTemplate(
				Long siteId, ContentTemplate contentTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentTemplatesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteContentTemplatesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteContentTemplatesPage() throws Exception {
		Long siteId = testGetSiteContentTemplatesPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"contentTemplates",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject contentTemplatesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contentTemplates");

		Assert.assertEquals(0, contentTemplatesJSONObject.get("totalCount"));

		ContentTemplate contentTemplate1 =
			testGraphQLContentTemplate_addContentTemplate();
		ContentTemplate contentTemplate2 =
			testGraphQLContentTemplate_addContentTemplate();

		contentTemplatesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contentTemplates");

		Assert.assertEquals(2, contentTemplatesJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(contentTemplate1, contentTemplate2),
			Arrays.asList(
				ContentTemplateSerDes.toDTOs(
					contentTemplatesJSONObject.getString("items"))));
	}

	@Test
	public void testGetContentTemplate() throws Exception {
		ContentTemplate postContentTemplate =
			testGetContentTemplate_addContentTemplate();

		ContentTemplate getContentTemplate =
			contentTemplateResource.getContentTemplate(
				postContentTemplate.getSiteId(), postContentTemplate.getId());

		assertEquals(postContentTemplate, getContentTemplate);
		assertValid(getContentTemplate);
	}

	protected ContentTemplate testGetContentTemplate_addContentTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetContentTemplate() throws Exception {
		ContentTemplate contentTemplate =
			testGraphQLContentTemplate_addContentTemplate();

		Assert.assertTrue(
			equals(
				contentTemplate,
				ContentTemplateSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"contentTemplate",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" + contentTemplate.getSiteId() +
												"\"");
										put(
											"contentTemplateId",
											"\"" + contentTemplate.getId() +
												"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/contentTemplate"))));
	}

	@Test
	public void testGraphQLGetContentTemplateNotFound() throws Exception {
		String irrelevantContentTemplateId =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"contentTemplate",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put(
									"contentTemplateId",
									irrelevantContentTemplateId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected ContentTemplate testGraphQLContentTemplate_addContentTemplate()
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
		ContentTemplate contentTemplate1, ContentTemplate contentTemplate2) {

		Assert.assertTrue(
			contentTemplate1 + " does not equal " + contentTemplate2,
			equals(contentTemplate1, contentTemplate2));
	}

	protected void assertEquals(
		List<ContentTemplate> contentTemplates1,
		List<ContentTemplate> contentTemplates2) {

		Assert.assertEquals(contentTemplates1.size(), contentTemplates2.size());

		for (int i = 0; i < contentTemplates1.size(); i++) {
			ContentTemplate contentTemplate1 = contentTemplates1.get(i);
			ContentTemplate contentTemplate2 = contentTemplates2.get(i);

			assertEquals(contentTemplate1, contentTemplate2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentTemplate> contentTemplates1,
		List<ContentTemplate> contentTemplates2) {

		Assert.assertEquals(contentTemplates1.size(), contentTemplates2.size());

		for (ContentTemplate contentTemplate1 : contentTemplates1) {
			boolean contains = false;

			for (ContentTemplate contentTemplate2 : contentTemplates2) {
				if (equals(contentTemplate1, contentTemplate2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentTemplates2 + " does not contain " + contentTemplate1,
				contains);
		}
	}

	protected void assertValid(ContentTemplate contentTemplate)
		throws Exception {

		boolean valid = true;

		if (contentTemplate.getDateCreated() == null) {
			valid = false;
		}

		if (contentTemplate.getDateModified() == null) {
			valid = false;
		}

		if (contentTemplate.getId() == null) {
			valid = false;
		}

		Group group = testDepotEntry.getGroup();

		if (!Objects.equals(
				contentTemplate.getAssetLibraryKey(), group.getGroupKey()) &&
			!Objects.equals(
				contentTemplate.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (contentTemplate.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (contentTemplate.getAssetLibraryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (contentTemplate.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureId", additionalAssertFieldName)) {

				if (contentTemplate.getContentStructureId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (contentTemplate.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (contentTemplate.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (contentTemplate.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (contentTemplate.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (contentTemplate.getName_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"programmingLanguage", additionalAssertFieldName)) {

				if (contentTemplate.getProgrammingLanguage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("templateScript", additionalAssertFieldName)) {
				if (contentTemplate.getTemplateScript() == null) {
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

	protected void assertValid(Page<ContentTemplate> page) {
		boolean valid = false;

		java.util.Collection<ContentTemplate> contentTemplates =
			page.getItems();

		int size = contentTemplates.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.ContentTemplate.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		ContentTemplate contentTemplate1, ContentTemplate contentTemplate2) {

		if (contentTemplate1 == contentTemplate2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)contentTemplate1.getActions(),
						(Map)contentTemplate2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentTemplate1.getAvailableLanguages(),
						contentTemplate2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentTemplate1.getContentStructureId(),
						contentTemplate2.getContentStructureId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentTemplate1.getCreator(),
						contentTemplate2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentTemplate1.getDateCreated(),
						contentTemplate2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentTemplate1.getDateModified(),
						contentTemplate2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentTemplate1.getDescription(),
						contentTemplate2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)contentTemplate1.getDescription_i18n(),
						(Map)contentTemplate2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentTemplate1.getId(), contentTemplate2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentTemplate1.getName(),
						contentTemplate2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)contentTemplate1.getName_i18n(),
						(Map)contentTemplate2.getName_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"programmingLanguage", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentTemplate1.getProgrammingLanguage(),
						contentTemplate2.getProgrammingLanguage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("templateScript", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentTemplate1.getTemplateScript(),
						contentTemplate2.getTemplateScript())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_contentTemplateResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentTemplateResource;

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
		ContentTemplate contentTemplate) {

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

		if (entityFieldName.equals("assetLibraryKey")) {
			sb.append("'");
			sb.append(String.valueOf(contentTemplate.getAssetLibraryKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("availableLanguages")) {
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
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentTemplate.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentTemplate.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(contentTemplate.getDateCreated()));
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
							contentTemplate.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentTemplate.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(contentTemplate.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(contentTemplate.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			sb.append("'");
			sb.append(String.valueOf(contentTemplate.getId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(contentTemplate.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("programmingLanguage")) {
			sb.append("'");
			sb.append(String.valueOf(contentTemplate.getProgrammingLanguage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("templateScript")) {
			sb.append("'");
			sb.append(String.valueOf(contentTemplate.getTemplateScript()));
			sb.append("'");

			return sb.toString();
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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected ContentTemplate randomContentTemplate() throws Exception {
		return new ContentTemplate() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				contentStructureId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				programmingLanguage = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
				templateScript = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected ContentTemplate randomIrrelevantContentTemplate()
		throws Exception {

		ContentTemplate randomIrrelevantContentTemplate =
			randomContentTemplate();

		randomIrrelevantContentTemplate.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantContentTemplate;
	}

	protected ContentTemplate randomPatchContentTemplate() throws Exception {
		return randomContentTemplate();
	}

	protected ContentTemplateResource contentTemplateResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected DepotEntry testDepotEntry;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

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

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseContentTemplateResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.ContentTemplateResource
		_contentTemplateResource;

}