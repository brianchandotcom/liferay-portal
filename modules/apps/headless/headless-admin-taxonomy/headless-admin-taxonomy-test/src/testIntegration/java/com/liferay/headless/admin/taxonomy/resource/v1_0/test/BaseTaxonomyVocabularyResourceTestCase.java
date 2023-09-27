/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.http.HttpInvoker;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.permission.Permission;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.TaxonomyVocabularySerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
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

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
public abstract class BaseTaxonomyVocabularyResourceTestCase {

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

		_taxonomyVocabularyResource.setContextCompany(testCompany);

		TaxonomyVocabularyResource.Builder builder =
			TaxonomyVocabularyResource.builder();

		taxonomyVocabularyResource = builder.authentication(
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

		TaxonomyVocabulary taxonomyVocabulary1 = randomTaxonomyVocabulary();

		String json = objectMapper.writeValueAsString(taxonomyVocabulary1);

		TaxonomyVocabulary taxonomyVocabulary2 = TaxonomyVocabularySerDes.toDTO(
			json);

		Assert.assertTrue(equals(taxonomyVocabulary1, taxonomyVocabulary2));
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

		TaxonomyVocabulary taxonomyVocabulary = randomTaxonomyVocabulary();

		String json1 = objectMapper.writeValueAsString(taxonomyVocabulary);
		String json2 = TaxonomyVocabularySerDes.toJSON(taxonomyVocabulary);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		TaxonomyVocabulary taxonomyVocabulary = randomTaxonomyVocabulary();

		taxonomyVocabulary.setAssetLibraryKey(regex);
		taxonomyVocabulary.setDescription(regex);
		taxonomyVocabulary.setExternalReferenceCode(regex);
		taxonomyVocabulary.setName(regex);

		String json = TaxonomyVocabularySerDes.toJSON(taxonomyVocabulary);

		Assert.assertFalse(json.contains(regex));

		taxonomyVocabulary = TaxonomyVocabularySerDes.toDTO(json);

		Assert.assertEquals(regex, taxonomyVocabulary.getAssetLibraryKey());
		Assert.assertEquals(regex, taxonomyVocabulary.getDescription());
		Assert.assertEquals(
			regex, taxonomyVocabulary.getExternalReferenceCode());
		Assert.assertEquals(regex, taxonomyVocabulary.getName());
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPage() throws Exception {
		Long assetLibraryId =
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryTaxonomyVocabulariesPage_getIrrelevantAssetLibraryId();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantAssetLibraryId != null) {
			TaxonomyVocabulary irrelevantTaxonomyVocabulary =
				testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
					irrelevantAssetLibraryId,
					randomIrrelevantTaxonomyVocabulary());

			page =
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabulariesPage(
						irrelevantAssetLibraryId, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTaxonomyVocabulary),
				(List<TaxonomyVocabulary>)page.getItems());
			assertValid(
				page,
				testGetAssetLibraryTaxonomyVocabulariesPage_getExpectedActions(
					irrelevantAssetLibraryId));
		}

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		TaxonomyVocabulary taxonomyVocabulary2 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
			(List<TaxonomyVocabulary>)page.getItems());
		assertValid(
			page,
			testGetAssetLibraryTaxonomyVocabulariesPage_getExpectedActions(
				assetLibraryId));

		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary1.getId());

		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAssetLibraryTaxonomyVocabulariesPage_getExpectedActions(
				Long assetLibraryId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/batch".
				replace("{assetLibraryId}", String.valueOf(assetLibraryId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId();

		TaxonomyVocabulary taxonomyVocabulary1 = randomTaxonomyVocabulary();

		taxonomyVocabulary1 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, taxonomyVocabulary1);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyVocabulary> page =
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabulariesPage(
						assetLibraryId, null, null,
						getFilterString(
							entityField, "between", taxonomyVocabulary1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithFilterDoubleEquals()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithFilterStringContains()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithFilterStringEquals()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithFilterStringStartsWith()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetAssetLibraryTaxonomyVocabulariesPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId();

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary2 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		for (EntityField entityField : entityFields) {
			Page<TaxonomyVocabulary> page =
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabulariesPage(
						assetLibraryId, null, null,
						getFilterString(
							entityField, operator, taxonomyVocabulary1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithPagination()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId();

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		TaxonomyVocabulary taxonomyVocabulary2 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		TaxonomyVocabulary taxonomyVocabulary3 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		Page<TaxonomyVocabulary> page1 =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 2), null);

		List<TaxonomyVocabulary> taxonomyVocabularies1 =
			(List<TaxonomyVocabulary>)page1.getItems();

		Assert.assertEquals(
			taxonomyVocabularies1.toString(), 2, taxonomyVocabularies1.size());

		Page<TaxonomyVocabulary> page2 =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<TaxonomyVocabulary> taxonomyVocabularies2 =
			(List<TaxonomyVocabulary>)page2.getItems();

		Assert.assertEquals(
			taxonomyVocabularies2.toString(), 1, taxonomyVocabularies2.size());

		Page<TaxonomyVocabulary> page3 =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				taxonomyVocabulary1, taxonomyVocabulary2, taxonomyVocabulary3),
			(List<TaxonomyVocabulary>)page3.getItems());
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				BeanTestUtil.setProperty(
					taxonomyVocabulary1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithSortDouble()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				BeanTestUtil.setProperty(
					taxonomyVocabulary1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					taxonomyVocabulary2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				BeanTestUtil.setProperty(
					taxonomyVocabulary1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					taxonomyVocabulary2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithSortString()
		throws Exception {

		testGetAssetLibraryTaxonomyVocabulariesPageWithSort(
			EntityField.Type.STRING,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				Class<?> clazz = taxonomyVocabulary1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						taxonomyVocabulary1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						taxonomyVocabulary2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						taxonomyVocabulary1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						taxonomyVocabulary2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						taxonomyVocabulary1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						taxonomyVocabulary2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryTaxonomyVocabulariesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, TaxonomyVocabulary, TaxonomyVocabulary, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId();

		TaxonomyVocabulary taxonomyVocabulary1 = randomTaxonomyVocabulary();
		TaxonomyVocabulary taxonomyVocabulary2 = randomTaxonomyVocabulary();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, taxonomyVocabulary1, taxonomyVocabulary2);
		}

		taxonomyVocabulary1 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, taxonomyVocabulary1);

		taxonomyVocabulary2 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, taxonomyVocabulary2);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyVocabulary> ascPage =
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabulariesPage(
						assetLibraryId, null, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
				(List<TaxonomyVocabulary>)ascPage.getItems());

			Page<TaxonomyVocabulary> descPage =
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabulariesPage(
						assetLibraryId, null, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary2, taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)descPage.getItems());
		}
	}

	protected TaxonomyVocabulary
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				Long assetLibraryId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabularyResource.postAssetLibraryTaxonomyVocabulary(
			assetLibraryId, taxonomyVocabulary);
	}

	protected Long
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long
			testGetAssetLibraryTaxonomyVocabulariesPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAssetLibraryTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary randomTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		TaxonomyVocabulary postTaxonomyVocabulary =
			testPostAssetLibraryTaxonomyVocabulary_addTaxonomyVocabulary(
				randomTaxonomyVocabulary);

		assertEquals(randomTaxonomyVocabulary, postTaxonomyVocabulary);
		assertValid(postTaxonomyVocabulary);
	}

	protected TaxonomyVocabulary
			testPostAssetLibraryTaxonomyVocabulary_addTaxonomyVocabulary(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabularyResource.postAssetLibraryTaxonomyVocabulary(
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
			taxonomyVocabulary);
	}

	@Test
	public void testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary =
			testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		assertHttpResponseStatusCode(
			204,
			taxonomyVocabularyResource.
				deleteAssetLibraryTaxonomyVocabularyByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					taxonomyVocabulary.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.
				getAssetLibraryTaxonomyVocabularyByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					taxonomyVocabulary.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.
				getAssetLibraryTaxonomyVocabularyByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					taxonomyVocabulary.getExternalReferenceCode()));
	}

	protected Long
			testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected TaxonomyVocabulary
			testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		TaxonomyVocabulary postTaxonomyVocabulary =
			testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.
				getAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					postTaxonomyVocabulary.getExternalReferenceCode());

		assertEquals(postTaxonomyVocabulary, getTaxonomyVocabulary);
		assertValid(getTaxonomyVocabulary);
	}

	protected Long
			testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected TaxonomyVocabulary
			testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		TaxonomyVocabulary taxonomyVocabulary =
			testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		Assert.assertTrue(
			equals(
				taxonomyVocabulary,
				TaxonomyVocabularySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"assetLibraryTaxonomyVocabularyByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"assetLibraryId",
											"\"" +
												testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId() +
													"\"");

										put(
											"externalReferenceCode",
											"\"" +
												taxonomyVocabulary.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/assetLibraryTaxonomyVocabularyByExternalReferenceCode"))));
	}

	protected Long
			testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"assetLibraryTaxonomyVocabularyByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"assetLibraryId",
									"\"" +
										testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId() +
											"\"");
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected TaxonomyVocabulary
			testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary();
	}

	@Test
	public void testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		TaxonomyVocabulary postTaxonomyVocabulary =
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		TaxonomyVocabulary randomTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		TaxonomyVocabulary putTaxonomyVocabulary =
			taxonomyVocabularyResource.
				putAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					postTaxonomyVocabulary.getExternalReferenceCode(),
					randomTaxonomyVocabulary);

		assertEquals(randomTaxonomyVocabulary, putTaxonomyVocabulary);
		assertValid(putTaxonomyVocabulary);

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.
				getAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					putTaxonomyVocabulary.getExternalReferenceCode());

		assertEquals(randomTaxonomyVocabulary, getTaxonomyVocabulary);
		assertValid(getTaxonomyVocabulary);

		TaxonomyVocabulary newTaxonomyVocabulary =
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_createTaxonomyVocabulary();

		putTaxonomyVocabulary =
			taxonomyVocabularyResource.
				putAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					newTaxonomyVocabulary.getExternalReferenceCode(),
					newTaxonomyVocabulary);

		assertEquals(newTaxonomyVocabulary, putTaxonomyVocabulary);
		assertValid(putTaxonomyVocabulary);

		getTaxonomyVocabulary =
			taxonomyVocabularyResource.
				getAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					putTaxonomyVocabulary.getExternalReferenceCode());

		assertEquals(newTaxonomyVocabulary, getTaxonomyVocabulary);

		Assert.assertEquals(
			newTaxonomyVocabulary.getExternalReferenceCode(),
			putTaxonomyVocabulary.getExternalReferenceCode());
	}

	protected Long
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected TaxonomyVocabulary
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_createTaxonomyVocabulary()
		throws Exception {

		return randomTaxonomyVocabulary();
	}

	protected TaxonomyVocabulary
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGetAssetLibraryTaxonomyVocabularyPermissionsPage()
		throws Exception {

		Page<Permission> page =
			taxonomyVocabularyResource.
				getAssetLibraryTaxonomyVocabularyPermissionsPage(
					testDepotEntry.getDepotEntryId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected TaxonomyVocabulary
			testGetAssetLibraryTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary()
		throws Exception {

		return testPostAssetLibraryTaxonomyVocabulary_addTaxonomyVocabulary(
			randomTaxonomyVocabulary());
	}

	@Test
	public void testPutAssetLibraryTaxonomyVocabularyPermissionsPage()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary =
			testPutAssetLibraryTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			taxonomyVocabularyResource.
				putAssetLibraryTaxonomyVocabularyPermissionsPageHttpResponse(
					testDepotEntry.getDepotEntryId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"PERMISSIONS"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.
				putAssetLibraryTaxonomyVocabularyPermissionsPageHttpResponse(
					testDepotEntry.getDepotEntryId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected TaxonomyVocabulary
			testPutAssetLibraryTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postAssetLibraryTaxonomyVocabulary(
			testDepotEntry.getDepotEntryId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPage() throws Exception {
		Long siteId = testGetSiteTaxonomyVocabulariesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteTaxonomyVocabulariesPage_getIrrelevantSiteId();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSiteId != null) {
			TaxonomyVocabulary irrelevantTaxonomyVocabulary =
				testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
					irrelevantSiteId, randomIrrelevantTaxonomyVocabulary());

			page = taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTaxonomyVocabulary),
				(List<TaxonomyVocabulary>)page.getItems());
			assertValid(
				page,
				testGetSiteTaxonomyVocabulariesPage_getExpectedActions(
					irrelevantSiteId));
		}

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, randomTaxonomyVocabulary());

		TaxonomyVocabulary taxonomyVocabulary2 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, randomTaxonomyVocabulary());

		page = taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
			siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
			(List<TaxonomyVocabulary>)page.getItems());
		assertValid(
			page,
			testGetSiteTaxonomyVocabulariesPage_getExpectedActions(siteId));

		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary1.getId());

		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSiteTaxonomyVocabulariesPage_getExpectedActions(Long siteId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/batch".
				replace("{siteId}", String.valueOf(siteId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteTaxonomyVocabulariesPage_getSiteId();

		TaxonomyVocabulary taxonomyVocabulary1 = randomTaxonomyVocabulary();

		taxonomyVocabulary1 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, taxonomyVocabulary1);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyVocabulary> page =
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					siteId, null, null,
					getFilterString(
						entityField, "between", taxonomyVocabulary1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithFilterDoubleEquals()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithFilterStringContains()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithFilterStringEquals()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithFilterStringStartsWith()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetSiteTaxonomyVocabulariesPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteTaxonomyVocabulariesPage_getSiteId();

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, randomTaxonomyVocabulary());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary2 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, randomTaxonomyVocabulary());

		for (EntityField entityField : entityFields) {
			Page<TaxonomyVocabulary> page =
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					siteId, null, null,
					getFilterString(entityField, operator, taxonomyVocabulary1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteTaxonomyVocabulariesPage_getSiteId();

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, randomTaxonomyVocabulary());

		TaxonomyVocabulary taxonomyVocabulary2 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, randomTaxonomyVocabulary());

		TaxonomyVocabulary taxonomyVocabulary3 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, randomTaxonomyVocabulary());

		Page<TaxonomyVocabulary> page1 =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<TaxonomyVocabulary> taxonomyVocabularies1 =
			(List<TaxonomyVocabulary>)page1.getItems();

		Assert.assertEquals(
			taxonomyVocabularies1.toString(), 2, taxonomyVocabularies1.size());

		Page<TaxonomyVocabulary> page2 =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<TaxonomyVocabulary> taxonomyVocabularies2 =
			(List<TaxonomyVocabulary>)page2.getItems();

		Assert.assertEquals(
			taxonomyVocabularies2.toString(), 1, taxonomyVocabularies2.size());

		Page<TaxonomyVocabulary> page3 =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				taxonomyVocabulary1, taxonomyVocabulary2, taxonomyVocabulary3),
			(List<TaxonomyVocabulary>)page3.getItems());
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithSortDateTime()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				BeanTestUtil.setProperty(
					taxonomyVocabulary1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithSortDouble()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				BeanTestUtil.setProperty(
					taxonomyVocabulary1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					taxonomyVocabulary2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithSortInteger()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				BeanTestUtil.setProperty(
					taxonomyVocabulary1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					taxonomyVocabulary2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteTaxonomyVocabulariesPageWithSortString()
		throws Exception {

		testGetSiteTaxonomyVocabulariesPageWithSort(
			EntityField.Type.STRING,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				Class<?> clazz = taxonomyVocabulary1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						taxonomyVocabulary1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						taxonomyVocabulary2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						taxonomyVocabulary1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						taxonomyVocabulary2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						taxonomyVocabulary1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						taxonomyVocabulary2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteTaxonomyVocabulariesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, TaxonomyVocabulary, TaxonomyVocabulary, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteTaxonomyVocabulariesPage_getSiteId();

		TaxonomyVocabulary taxonomyVocabulary1 = randomTaxonomyVocabulary();
		TaxonomyVocabulary taxonomyVocabulary2 = randomTaxonomyVocabulary();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, taxonomyVocabulary1, taxonomyVocabulary2);
		}

		taxonomyVocabulary1 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, taxonomyVocabulary1);

		taxonomyVocabulary2 =
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				siteId, taxonomyVocabulary2);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyVocabulary> ascPage =
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
				(List<TaxonomyVocabulary>)ascPage.getItems());

			Page<TaxonomyVocabulary> descPage =
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary2, taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)descPage.getItems());
		}
	}

	protected TaxonomyVocabulary
			testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				Long siteId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			siteId, taxonomyVocabulary);
	}

	protected Long testGetSiteTaxonomyVocabulariesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteTaxonomyVocabulariesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteTaxonomyVocabulariesPage() throws Exception {
		Long siteId = testGetSiteTaxonomyVocabulariesPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"taxonomyVocabularies",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject taxonomyVocabulariesJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/taxonomyVocabularies");

		Assert.assertEquals(
			0, taxonomyVocabulariesJSONObject.get("totalCount"));

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();
		TaxonomyVocabulary taxonomyVocabulary2 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();

		taxonomyVocabulariesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/taxonomyVocabularies");

		Assert.assertEquals(
			2, taxonomyVocabulariesJSONObject.getLong("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
			Arrays.asList(
				TaxonomyVocabularySerDes.toDTOs(
					taxonomyVocabulariesJSONObject.getString("items"))));
	}

	protected TaxonomyVocabulary
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary()
		throws Exception {

		return testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary();
	}

	@Test
	public void testPostSiteTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary randomTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		TaxonomyVocabulary postTaxonomyVocabulary =
			testPostSiteTaxonomyVocabulary_addTaxonomyVocabulary(
				randomTaxonomyVocabulary);

		assertEquals(randomTaxonomyVocabulary, postTaxonomyVocabulary);
		assertValid(postTaxonomyVocabulary);
	}

	protected TaxonomyVocabulary
			testPostSiteTaxonomyVocabulary_addTaxonomyVocabulary(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGetSiteTaxonomyVocabulariesPage_getSiteId(),
			taxonomyVocabulary);
	}

	@Test
	public void testGraphQLPostSiteTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary randomTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		TaxonomyVocabulary taxonomyVocabulary =
			testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary(
				randomTaxonomyVocabulary);

		Assert.assertTrue(equals(randomTaxonomyVocabulary, taxonomyVocabulary));
	}

	@Test
	public void testDeleteSiteTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary =
			testDeleteSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		assertHttpResponseStatusCode(
			204,
			taxonomyVocabularyResource.
				deleteSiteTaxonomyVocabularyByExternalReferenceCodeHttpResponse(
					testDeleteSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						taxonomyVocabulary),
					taxonomyVocabulary.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.
				getSiteTaxonomyVocabularyByExternalReferenceCodeHttpResponse(
					testDeleteSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						taxonomyVocabulary),
					taxonomyVocabulary.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.
				getSiteTaxonomyVocabularyByExternalReferenceCodeHttpResponse(
					testDeleteSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						taxonomyVocabulary),
					taxonomyVocabulary.getExternalReferenceCode()));
	}

	protected Long
			testDeleteSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabulary.getSiteId();
	}

	protected TaxonomyVocabulary
			testDeleteSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGetSiteTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		TaxonomyVocabulary postTaxonomyVocabulary =
			testGetSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.
				getSiteTaxonomyVocabularyByExternalReferenceCode(
					testGetSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						postTaxonomyVocabulary),
					postTaxonomyVocabulary.getExternalReferenceCode());

		assertEquals(postTaxonomyVocabulary, getTaxonomyVocabulary);
		assertValid(getTaxonomyVocabulary);
	}

	protected Long
			testGetSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabulary.getSiteId();
	}

	protected TaxonomyVocabulary
			testGetSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGraphQLGetSiteTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		TaxonomyVocabulary taxonomyVocabulary =
			testGraphQLGetSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		Assert.assertTrue(
			equals(
				taxonomyVocabulary,
				TaxonomyVocabularySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"taxonomyVocabularyByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" +
												testGraphQLGetSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
													taxonomyVocabulary) + "\"");

										put(
											"externalReferenceCode",
											"\"" +
												taxonomyVocabulary.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/taxonomyVocabularyByExternalReferenceCode"))));
	}

	protected Long
			testGraphQLGetSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabulary.getSiteId();
	}

	@Test
	public void testGraphQLGetSiteTaxonomyVocabularyByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"taxonomyVocabularyByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected TaxonomyVocabulary
			testGraphQLGetSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary();
	}

	@Test
	public void testPutSiteTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		TaxonomyVocabulary postTaxonomyVocabulary =
			testPutSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		TaxonomyVocabulary randomTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		TaxonomyVocabulary putTaxonomyVocabulary =
			taxonomyVocabularyResource.
				putSiteTaxonomyVocabularyByExternalReferenceCode(
					testPutSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						postTaxonomyVocabulary),
					postTaxonomyVocabulary.getExternalReferenceCode(),
					randomTaxonomyVocabulary);

		assertEquals(randomTaxonomyVocabulary, putTaxonomyVocabulary);
		assertValid(putTaxonomyVocabulary);

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.
				getSiteTaxonomyVocabularyByExternalReferenceCode(
					testPutSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						putTaxonomyVocabulary),
					putTaxonomyVocabulary.getExternalReferenceCode());

		assertEquals(randomTaxonomyVocabulary, getTaxonomyVocabulary);
		assertValid(getTaxonomyVocabulary);

		TaxonomyVocabulary newTaxonomyVocabulary =
			testPutSiteTaxonomyVocabularyByExternalReferenceCode_createTaxonomyVocabulary();

		putTaxonomyVocabulary =
			taxonomyVocabularyResource.
				putSiteTaxonomyVocabularyByExternalReferenceCode(
					testPutSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						newTaxonomyVocabulary),
					newTaxonomyVocabulary.getExternalReferenceCode(),
					newTaxonomyVocabulary);

		assertEquals(newTaxonomyVocabulary, putTaxonomyVocabulary);
		assertValid(putTaxonomyVocabulary);

		getTaxonomyVocabulary =
			taxonomyVocabularyResource.
				getSiteTaxonomyVocabularyByExternalReferenceCode(
					testPutSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
						putTaxonomyVocabulary),
					putTaxonomyVocabulary.getExternalReferenceCode());

		assertEquals(newTaxonomyVocabulary, getTaxonomyVocabulary);

		Assert.assertEquals(
			newTaxonomyVocabulary.getExternalReferenceCode(),
			putTaxonomyVocabulary.getExternalReferenceCode());
	}

	protected Long
			testPutSiteTaxonomyVocabularyByExternalReferenceCode_getSiteId(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		return taxonomyVocabulary.getSiteId();
	}

	protected TaxonomyVocabulary
			testPutSiteTaxonomyVocabularyByExternalReferenceCode_createTaxonomyVocabulary()
		throws Exception {

		return randomTaxonomyVocabulary();
	}

	protected TaxonomyVocabulary
			testPutSiteTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGetSiteTaxonomyVocabularyPermissionsPage()
		throws Exception {

		Page<Permission> page =
			taxonomyVocabularyResource.getSiteTaxonomyVocabularyPermissionsPage(
				testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected TaxonomyVocabulary
			testGetSiteTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary()
		throws Exception {

		return testPostSiteTaxonomyVocabulary_addTaxonomyVocabulary(
			randomTaxonomyVocabulary());
	}

	@Test
	public void testPutSiteTaxonomyVocabularyPermissionsPage()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary =
			testPutSiteTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			taxonomyVocabularyResource.
				putSiteTaxonomyVocabularyPermissionsPageHttpResponse(
					taxonomyVocabulary.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"PERMISSIONS"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.
				putSiteTaxonomyVocabularyPermissionsPageHttpResponse(
					taxonomyVocabulary.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected TaxonomyVocabulary
			testPutSiteTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testDeleteTaxonomyVocabulary() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary =
			testDeleteTaxonomyVocabulary_addTaxonomyVocabulary();

		assertHttpResponseStatusCode(
			204,
			taxonomyVocabularyResource.deleteTaxonomyVocabularyHttpResponse(
				taxonomyVocabulary.getId()));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.getTaxonomyVocabularyHttpResponse(
				taxonomyVocabulary.getId()));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.getTaxonomyVocabularyHttpResponse(0L));
	}

	protected TaxonomyVocabulary
			testDeleteTaxonomyVocabulary_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGraphQLDeleteTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary taxonomyVocabulary =
			testGraphQLDeleteTaxonomyVocabulary_addTaxonomyVocabulary();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteTaxonomyVocabulary",
						new HashMap<String, Object>() {
							{
								put(
									"taxonomyVocabularyId",
									taxonomyVocabulary.getId());
							}
						})),
				"JSONObject/data", "Object/deleteTaxonomyVocabulary"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"taxonomyVocabulary",
					new HashMap<String, Object>() {
						{
							put(
								"taxonomyVocabularyId",
								taxonomyVocabulary.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected TaxonomyVocabulary
			testGraphQLDeleteTaxonomyVocabulary_addTaxonomyVocabulary()
		throws Exception {

		return testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary();
	}

	@Test
	public void testGetTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary postTaxonomyVocabulary =
			testGetTaxonomyVocabulary_addTaxonomyVocabulary();

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.getTaxonomyVocabulary(
				postTaxonomyVocabulary.getId());

		assertEquals(postTaxonomyVocabulary, getTaxonomyVocabulary);
		assertValid(getTaxonomyVocabulary);
	}

	protected TaxonomyVocabulary
			testGetTaxonomyVocabulary_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGraphQLGetTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary taxonomyVocabulary =
			testGraphQLGetTaxonomyVocabulary_addTaxonomyVocabulary();

		Assert.assertTrue(
			equals(
				taxonomyVocabulary,
				TaxonomyVocabularySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"taxonomyVocabulary",
								new HashMap<String, Object>() {
									{
										put(
											"taxonomyVocabularyId",
											taxonomyVocabulary.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/taxonomyVocabulary"))));
	}

	@Test
	public void testGraphQLGetTaxonomyVocabularyNotFound() throws Exception {
		Long irrelevantTaxonomyVocabularyId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"taxonomyVocabulary",
						new HashMap<String, Object>() {
							{
								put(
									"taxonomyVocabularyId",
									irrelevantTaxonomyVocabularyId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected TaxonomyVocabulary
			testGraphQLGetTaxonomyVocabulary_addTaxonomyVocabulary()
		throws Exception {

		return testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary();
	}

	@Test
	public void testPatchTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary postTaxonomyVocabulary =
			testPatchTaxonomyVocabulary_addTaxonomyVocabulary();

		TaxonomyVocabulary randomPatchTaxonomyVocabulary =
			randomPatchTaxonomyVocabulary();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary patchTaxonomyVocabulary =
			taxonomyVocabularyResource.patchTaxonomyVocabulary(
				postTaxonomyVocabulary.getId(), randomPatchTaxonomyVocabulary);

		TaxonomyVocabulary expectedPatchTaxonomyVocabulary =
			postTaxonomyVocabulary.clone();

		BeanTestUtil.copyProperties(
			randomPatchTaxonomyVocabulary, expectedPatchTaxonomyVocabulary);

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.getTaxonomyVocabulary(
				patchTaxonomyVocabulary.getId());

		assertEquals(expectedPatchTaxonomyVocabulary, getTaxonomyVocabulary);
		assertValid(getTaxonomyVocabulary);
	}

	protected TaxonomyVocabulary
			testPatchTaxonomyVocabulary_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testPutTaxonomyVocabulary() throws Exception {
		TaxonomyVocabulary postTaxonomyVocabulary =
			testPutTaxonomyVocabulary_addTaxonomyVocabulary();

		TaxonomyVocabulary randomTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		TaxonomyVocabulary putTaxonomyVocabulary =
			taxonomyVocabularyResource.putTaxonomyVocabulary(
				postTaxonomyVocabulary.getId(), randomTaxonomyVocabulary);

		assertEquals(randomTaxonomyVocabulary, putTaxonomyVocabulary);
		assertValid(putTaxonomyVocabulary);

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.getTaxonomyVocabulary(
				putTaxonomyVocabulary.getId());

		assertEquals(randomTaxonomyVocabulary, getTaxonomyVocabulary);
		assertValid(getTaxonomyVocabulary);
	}

	protected TaxonomyVocabulary
			testPutTaxonomyVocabulary_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Test
	public void testGetTaxonomyVocabularyPermissionsPage() throws Exception {
		TaxonomyVocabulary postTaxonomyVocabulary =
			testGetTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary();

		Page<Permission> page =
			taxonomyVocabularyResource.getTaxonomyVocabularyPermissionsPage(
				postTaxonomyVocabulary.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected TaxonomyVocabulary
			testGetTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary()
		throws Exception {

		return testPostSiteTaxonomyVocabulary_addTaxonomyVocabulary(
			randomTaxonomyVocabulary());
	}

	@Test
	public void testPutTaxonomyVocabularyPermissionsPage() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyVocabulary taxonomyVocabulary =
			testPutTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			taxonomyVocabularyResource.
				putTaxonomyVocabularyPermissionsPageHttpResponse(
					taxonomyVocabulary.getId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			taxonomyVocabularyResource.
				putTaxonomyVocabularyPermissionsPageHttpResponse(
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

	protected TaxonomyVocabulary
			testPutTaxonomyVocabularyPermissionsPage_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			testGroup.getGroupId(), randomTaxonomyVocabulary());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Object[]) {
			StringBuilder arraySB = new StringBuilder("[");

			for (Object object : (Object[])value) {
				if (arraySB.length() > 1) {
					arraySB.append(", ");
				}

				arraySB.append("{");

				Class<?> clazz = object.getClass();

				for (java.lang.reflect.Field field :
						getDeclaredFields(clazz.getSuperclass())) {

					arraySB.append(field.getName());
					arraySB.append(": ");

					appendGraphQLFieldValue(arraySB, field.get(object));

					arraySB.append(", ");
				}

				arraySB.setLength(arraySB.length() - 2);

				arraySB.append("}");
			}

			arraySB.append("]");

			sb.append(arraySB.toString());
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	protected TaxonomyVocabulary
			testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary()
		throws Exception {

		return testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary(
			randomTaxonomyVocabulary());
	}

	protected TaxonomyVocabulary
			testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		JSONDeserializer<TaxonomyVocabulary> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (java.lang.reflect.Field field :
				getDeclaredFields(TaxonomyVocabulary.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(taxonomyVocabulary));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("externalReferenceCode"));

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteTaxonomyVocabulary",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("taxonomyVocabulary", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteTaxonomyVocabulary"),
			TaxonomyVocabulary.class);
	}

	protected void assertContains(
		TaxonomyVocabulary taxonomyVocabulary,
		List<TaxonomyVocabulary> taxonomyVocabularies) {

		boolean contains = false;

		for (TaxonomyVocabulary item : taxonomyVocabularies) {
			if (equals(taxonomyVocabulary, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			taxonomyVocabularies + " does not contain " + taxonomyVocabulary,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		TaxonomyVocabulary taxonomyVocabulary1,
		TaxonomyVocabulary taxonomyVocabulary2) {

		Assert.assertTrue(
			taxonomyVocabulary1 + " does not equal " + taxonomyVocabulary2,
			equals(taxonomyVocabulary1, taxonomyVocabulary2));
	}

	protected void assertEquals(
		List<TaxonomyVocabulary> taxonomyVocabularies1,
		List<TaxonomyVocabulary> taxonomyVocabularies2) {

		Assert.assertEquals(
			taxonomyVocabularies1.size(), taxonomyVocabularies2.size());

		for (int i = 0; i < taxonomyVocabularies1.size(); i++) {
			TaxonomyVocabulary taxonomyVocabulary1 = taxonomyVocabularies1.get(
				i);
			TaxonomyVocabulary taxonomyVocabulary2 = taxonomyVocabularies2.get(
				i);

			assertEquals(taxonomyVocabulary1, taxonomyVocabulary2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<TaxonomyVocabulary> taxonomyVocabularies1,
		List<TaxonomyVocabulary> taxonomyVocabularies2) {

		Assert.assertEquals(
			taxonomyVocabularies1.size(), taxonomyVocabularies2.size());

		for (TaxonomyVocabulary taxonomyVocabulary1 : taxonomyVocabularies1) {
			boolean contains = false;

			for (TaxonomyVocabulary taxonomyVocabulary2 :
					taxonomyVocabularies2) {

				if (equals(taxonomyVocabulary1, taxonomyVocabulary2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				taxonomyVocabularies2 + " does not contain " +
					taxonomyVocabulary1,
				contains);
		}
	}

	protected void assertValid(TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		boolean valid = true;

		if (taxonomyVocabulary.getDateCreated() == null) {
			valid = false;
		}

		if (taxonomyVocabulary.getDateModified() == null) {
			valid = false;
		}

		if (taxonomyVocabulary.getId() == null) {
			valid = false;
		}

		Group group = testDepotEntry.getGroup();

		if (!Objects.equals(
				taxonomyVocabulary.getAssetLibraryKey(), group.getGroupKey()) &&
			!Objects.equals(
				taxonomyVocabulary.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getAssetLibraryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetTypes", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getAssetTypes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (taxonomyVocabulary.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (taxonomyVocabulary.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getName_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfTaxonomyCategories", additionalAssertFieldName)) {

				if (taxonomyVocabulary.getNumberOfTaxonomyCategories() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (taxonomyVocabulary.getViewableBy() == null) {
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

	protected void assertValid(Page<TaxonomyVocabulary> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<TaxonomyVocabulary> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<TaxonomyVocabulary> taxonomyVocabularies =
			page.getItems();

		int size = taxonomyVocabularies.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.admin.taxonomy.dto.v1_0.
						TaxonomyVocabulary.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
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
					getDeclaredFields(clazz));

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
		TaxonomyVocabulary taxonomyVocabulary1,
		TaxonomyVocabulary taxonomyVocabulary2) {

		if (taxonomyVocabulary1 == taxonomyVocabulary2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)taxonomyVocabulary1.getActions(),
						(Map)taxonomyVocabulary2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assetTypes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getAssetTypes(),
						taxonomyVocabulary2.getAssetTypes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyVocabulary1.getAvailableLanguages(),
						taxonomyVocabulary2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getCreator(),
						taxonomyVocabulary2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getDateCreated(),
						taxonomyVocabulary2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getDateModified(),
						taxonomyVocabulary2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getDescription(),
						taxonomyVocabulary2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)taxonomyVocabulary1.getDescription_i18n(),
						(Map)taxonomyVocabulary2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyVocabulary1.getExternalReferenceCode(),
						taxonomyVocabulary2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getId(),
						taxonomyVocabulary2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getName(),
						taxonomyVocabulary2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)taxonomyVocabulary1.getName_i18n(),
						(Map)taxonomyVocabulary2.getName_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfTaxonomyCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyVocabulary1.getNumberOfTaxonomyCategories(),
						taxonomyVocabulary2.getNumberOfTaxonomyCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyVocabulary1.getViewableBy(),
						taxonomyVocabulary2.getViewableBy())) {

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

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_taxonomyVocabularyResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_taxonomyVocabularyResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		TaxonomyVocabulary taxonomyVocabulary) {

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
			Object object = taxonomyVocabulary.getAssetLibraryKey();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("assetTypes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("availableLanguages")) {
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
							taxonomyVocabulary.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							taxonomyVocabulary.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(taxonomyVocabulary.getDateCreated()));
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
							taxonomyVocabulary.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							taxonomyVocabulary.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(taxonomyVocabulary.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			Object object = taxonomyVocabulary.getDescription();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = taxonomyVocabulary.getExternalReferenceCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			Object object = taxonomyVocabulary.getName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("name_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfTaxonomyCategories")) {
			sb.append(
				String.valueOf(
					taxonomyVocabulary.getNumberOfTaxonomyCategories()));

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected TaxonomyVocabulary randomTaxonomyVocabulary() throws Exception {
		return new TaxonomyVocabulary() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfTaxonomyCategories = RandomTestUtil.randomInt();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected TaxonomyVocabulary randomIrrelevantTaxonomyVocabulary()
		throws Exception {

		TaxonomyVocabulary randomIrrelevantTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		randomIrrelevantTaxonomyVocabulary.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantTaxonomyVocabulary;
	}

	protected TaxonomyVocabulary randomPatchTaxonomyVocabulary()
		throws Exception {

		return randomTaxonomyVocabulary();
	}

	protected TaxonomyVocabularyResource taxonomyVocabularyResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected DepotEntry testDepotEntry;
	protected Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseTaxonomyVocabularyResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.admin.taxonomy.resource.v1_0.
			TaxonomyVocabularyResource _taxonomyVocabularyResource;

}