/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.problem.Problem;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.TaxonomyVocabularySerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class TaxonomyVocabularyResourceTest
	extends BaseTaxonomyVocabularyResourceTestCase {

	@Override
	@Test
	public void testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		try {
			taxonomyVocabularyResource.
				deleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					externalReferenceCode);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				StringBundler.concat(
					"No AssetVocabulary exists with the key {",
					"externalReferenceCode=", externalReferenceCode,
					", groupId=", testDepotEntry.getGroupId(), "}"),
				problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPage() throws Exception {
		Long assetLibraryId =
			testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(3, page.getTotalCount());

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		TaxonomyVocabulary taxonomyVocabulary2 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				assetLibraryId, randomTaxonomyVocabulary());

		List<TaxonomyVocabulary> expectedTaxonomyVocabularies =
			new ArrayList<>();

		expectedTaxonomyVocabularies.add(taxonomyVocabulary1);
		expectedTaxonomyVocabularies.add(taxonomyVocabulary2);
		expectedTaxonomyVocabularies.addAll(page.getItems());

		page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(5, page.getTotalCount());

		assertEqualsIgnoringOrder(
			expectedTaxonomyVocabularies,
			(List<TaxonomyVocabulary>)page.getItems());
		assertValid(
			page,
			testGetAssetLibraryTaxonomyVocabulariesPage_getExpectedActions(
				assetLibraryId));

		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary1.getId());

		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary2.getId());

		TaxonomyVocabulary taxonomyVocabulary3 =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
				randomTaxonomyVocabulary());

		page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
				null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(4, page.getTotalCount());

		assertValid(
			page,
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/asset-libraries/",
						testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
						"/taxonomy-vocabularies")
				).put(
					"method", "POST"
				).build()
			).put(
				"createBatch",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/asset-libraries/",
						testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
						"/taxonomy-vocabularies/batch")
				).put(
					"method", "POST"
				).build()
			).put(
				"deleteBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/batch"
				).put(
					"method", "DELETE"
				).build()
			).put(
				"updateBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/batch"
				).put(
					"method", "PUT"
				).build()
			).build());

		taxonomyVocabularyResource = TaxonomyVocabularyResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"restrictFields",
			"actions,assetLibraryKey,assetTypes,availableLanguages,creator," +
				"dateCreated,dateModified,description,externalReferenceCode," +
					"id,numberOfTaxonomyCategories"
		).build();

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				testDepotEntry.getDepotEntryId(), null, null,
				getFilterString(entityFields.get(0), "eq", taxonomyVocabulary3),
				Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			new TaxonomyVocabulary() {
				{
					name = taxonomyVocabulary3.getName();
				}
			},
			page.fetchFirstItem());

		assertValid(page);
	}

	@Override
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
				assetLibraryId, null, null, null, Pagination.of(1, 3), null);

		List<TaxonomyVocabulary> taxonomyVocabularies1 =
			(List<TaxonomyVocabulary>)page1.getItems();

		Assert.assertEquals(
			taxonomyVocabularies1.toString(), 3, taxonomyVocabularies1.size());

		Assert.assertEquals(6, page1.getTotalCount());

		Page<TaxonomyVocabulary> page2 =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				assetLibraryId, null, null, null, Pagination.of(2, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				taxonomyVocabulary1, taxonomyVocabulary2, taxonomyVocabulary3),
			(List<TaxonomyVocabulary>)page2.getItems());
	}

	@Override
	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPageWithSortString()
		throws Exception {

		testAssetLibrarySiteTaxonomyVocabulariesPageWithSort(
			EntityField.Type.STRING,
			(entityField, taxonomyVocabulary1, taxonomyVocabulary2) -> {
				Class<?> clazz = taxonomyVocabulary1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				_setEntityFieldNames(
					taxonomyVocabulary1, taxonomyVocabulary2, entityFieldName,
					method.getReturnType());
			});
	}

	@Override
	@Test
	public void testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		super.testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode();

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		try {
			taxonomyVocabularyResource.
				getAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					externalReferenceCode);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				StringBundler.concat(
					"No AssetVocabulary exists with the key {",
					"externalReferenceCode=", externalReferenceCode,
					", groupId=", testDepotEntry.getGroupId(), "}"),
				problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetSiteTaxonomyVocabulariesPage() throws Exception {
		super.testGetSiteTaxonomyVocabulariesPage();

		testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
			testGetSiteTaxonomyVocabulariesPage_getSiteId(),
			randomTaxonomyVocabulary());

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				testGetSiteTaxonomyVocabulariesPage_getSiteId(), null, null,
				null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertValid(
			page,
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/sites/",
						testGetSiteTaxonomyVocabulariesPage_getSiteId(),
						"/taxonomy-vocabularies")
				).put(
					"method", "POST"
				).build()
			).put(
				"createBatch",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/sites/",
						testGetSiteTaxonomyVocabulariesPage_getSiteId(),
						"/taxonomy-vocabularies/batch")
				).put(
					"method", "POST"
				).build()
			).put(
				"deleteBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/batch"
				).put(
					"method", "DELETE"
				).build()
			).put(
				"updateBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/batch"
				).put(
					"method", "PUT"
				).build()
			).build());
	}

	@Override
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

				_setEntityFieldNames(
					taxonomyVocabulary1, taxonomyVocabulary2, entityFieldName,
					method.getReturnType());
			});
	}

	@Override
	@Test
	public void testGetTaxonomyVocabulary() throws Exception {
		super.testGetTaxonomyVocabulary();

		TaxonomyVocabulary postTaxonomyVocabulary =
			testGetTaxonomyVocabulary_addTaxonomyVocabulary();

		TaxonomyVocabulary getTaxonomyVocabulary =
			taxonomyVocabularyResource.getTaxonomyVocabulary(
				postTaxonomyVocabulary.getId());

		assertValid(
			getTaxonomyVocabulary.getActions(),
			HashMapBuilder.<String, Map<String, String>>put(
				"delete",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/" +
							getTaxonomyVocabulary.getId()
				).put(
					"method", "DELETE"
				).build()
			).put(
				"get",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/" +
							getTaxonomyVocabulary.getId()
				).put(
					"method", "GET"
				).build()
			).put(
				"replace",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/" +
							getTaxonomyVocabulary.getId()
				).put(
					"method", "PUT"
				).build()
			).put(
				"update",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/" +
							getTaxonomyVocabulary.getId()
				).put(
					"method", "PATCH"
				).build()
			).build());
	}

	@Override
	@Test
	public void testGraphQLGetSiteTaxonomyVocabulariesPage() throws Exception {
		Long siteId = testGetSiteTaxonomyVocabulariesPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"taxonomyVocabularies",
			HashMapBuilder.<String, Object>put(
				"page", 1
			).put(
				"pageSize", 10
			).put(
				"siteKey", "\"" + siteId + "\""
			).build(),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject taxonomyVocabulariesJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/taxonomyVocabularies");

		Assert.assertEquals(
			3, taxonomyVocabulariesJSONObject.get("totalCount"));

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				siteId, null, null, null, Pagination.of(1, 10), null);

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();
		TaxonomyVocabulary taxonomyVocabulary2 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();

		taxonomyVocabulariesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/taxonomyVocabularies");

		Assert.assertEquals(
			5, taxonomyVocabulariesJSONObject.getLong("totalCount"));

		List<TaxonomyVocabulary> expectedTaxonomyVocabularies =
			new ArrayList<>();

		expectedTaxonomyVocabularies.add(taxonomyVocabulary1);
		expectedTaxonomyVocabularies.add(taxonomyVocabulary2);
		expectedTaxonomyVocabularies.addAll(page.getItems());

		assertEqualsIgnoringOrder(
			expectedTaxonomyVocabularies,
			Arrays.asList(
				TaxonomyVocabularySerDes.toDTOs(
					taxonomyVocabulariesJSONObject.getString("items"))));

		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary1.getId());
		taxonomyVocabularyResource.deleteTaxonomyVocabulary(
			taxonomyVocabulary2.getId());

		taxonomyVocabulary1 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();
		taxonomyVocabulary2 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();

		graphQLField = new GraphQLField(
			"taxonomyVocabularies",
			HashMapBuilder.<String, Object>put(
				"aggregation", "[\"id\"]"
			).put(
				"siteKey",
				StringBundler.concat("\"", testGroup.getGroupId(), "\"")
			).build(),
			new GraphQLField(
				"facets", new GraphQLField("facetCriteria"),
				new GraphQLField(
					"facetValues", new GraphQLField("numberOfOccurrences"),
					new GraphQLField("term"))),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("totalCount"));

		taxonomyVocabulariesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/taxonomyVocabularies");

		Assert.assertEquals(
			5, taxonomyVocabulariesJSONObject.getLong("totalCount"));
		Assert.assertEquals(
			"id",
			taxonomyVocabulariesJSONObject.getJSONArray(
				"facets"
			).getJSONObject(
				0
			).getString(
				"facetCriteria"
			));
		Assert.assertEquals(
			1,
			taxonomyVocabulariesJSONObject.getJSONArray(
				"facets"
			).getJSONObject(
				0
			).getJSONArray(
				"facetValues"
			).getJSONObject(
				0
			).getInt(
				"numberOfOccurrences"
			));
		Assert.assertEquals(
			taxonomyVocabulary1.getId(),
			Long.valueOf(
				taxonomyVocabulariesJSONObject.getJSONArray(
					"facets"
				).getJSONObject(
					0
				).getJSONArray(
					"facetValues"
				).getJSONObject(
					3
				).getString(
					"term"
				)));
		Assert.assertEquals(
			1,
			taxonomyVocabulariesJSONObject.getJSONArray(
				"facets"
			).getJSONObject(
				0
			).getJSONArray(
				"facetValues"
			).getJSONObject(
				1
			).getInt(
				"numberOfOccurrences"
			));
		Assert.assertEquals(
			taxonomyVocabulary2.getId(),
			Long.valueOf(
				taxonomyVocabulariesJSONObject.getJSONArray(
					"facets"
				).getJSONObject(
					0
				).getJSONArray(
					"facetValues"
				).getJSONObject(
					4
				).getString(
					"term"
				)));

		expectedTaxonomyVocabularies = new ArrayList<>();

		expectedTaxonomyVocabularies.add(taxonomyVocabulary1);
		expectedTaxonomyVocabularies.add(taxonomyVocabulary2);
		expectedTaxonomyVocabularies.addAll(page.getItems());

		assertEqualsIgnoringOrder(
			expectedTaxonomyVocabularies,
			Arrays.asList(
				TaxonomyVocabularySerDes.toDTOs(
					taxonomyVocabulariesJSONObject.getString("items"))));
	}

	@Override
	@Test
	public void testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode()
		throws Exception {

		super.testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode();

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		TaxonomyVocabulary taxonomyVocabulary =
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_createTaxonomyVocabulary();

		TaxonomyVocabulary putTaxonomyVocabulary =
			taxonomyVocabularyResource.
				putAssetLibraryTaxonomyVocabularyByExternalReferenceCode(
					testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId(),
					externalReferenceCode, taxonomyVocabulary);

		Assert.assertEquals(
			externalReferenceCode,
			putTaxonomyVocabulary.getExternalReferenceCode());
		assertValid(putTaxonomyVocabulary);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"assetTypes", "description", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"dateCreated", "dateModified"};
	}

	@Override
	protected TaxonomyVocabulary randomTaxonomyVocabulary() {
		return new TaxonomyVocabulary() {
			{
				assetTypes = new AssetType[] {
					new AssetType() {
						{
							required = RandomTestUtil.randomBoolean();
							subtype = "AllAssetSubtypes";
							type = "AllAssetTypes";
						}
					}
				};
				description = RandomTestUtil.randomString();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected void testAssetLibrarySiteTaxonomyVocabulariesPageWithSort(
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
						assetLibraryId, null, null,
						StringBundler.concat(
							getFilterString(
								entityField, "eq", taxonomyVocabulary1),
							" or ",
							getFilterString(
								entityField, "eq", taxonomyVocabulary2)),
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
				(List<TaxonomyVocabulary>)ascPage.getItems());

			Page<TaxonomyVocabulary> descPage =
				taxonomyVocabularyResource.
					getAssetLibraryTaxonomyVocabulariesPage(
						assetLibraryId, null, null,
						StringBundler.concat(
							getFilterString(
								entityField, "eq", taxonomyVocabulary1),
							" or ",
							getFilterString(
								entityField, "eq", taxonomyVocabulary2)),
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary2, taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)descPage.getItems());
		}
	}

	@Override
	protected TaxonomyVocabulary
			testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postAssetLibraryTaxonomyVocabulary(
			testDepotEntry.getDepotEntryId(), randomTaxonomyVocabulary());
	}

	@Override
	protected Long
			testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected TaxonomyVocabulary
			testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return testPostAssetLibraryTaxonomyVocabulary_addTaxonomyVocabulary(
			randomTaxonomyVocabulary());
	}

	@Override
	protected Long
			testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
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
					siteId, null, null,
					StringBundler.concat(
						getFilterString(entityField, "eq", taxonomyVocabulary1),
						" or ",
						getFilterString(
							entityField, "eq", taxonomyVocabulary2)),
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
				(List<TaxonomyVocabulary>)ascPage.getItems());

			Page<TaxonomyVocabulary> descPage =
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					siteId, null, null,
					StringBundler.concat(
						getFilterString(entityField, "eq", taxonomyVocabulary1),
						" or ",
						getFilterString(
							entityField, "eq", taxonomyVocabulary2)),
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(taxonomyVocabulary2, taxonomyVocabulary1),
				(List<TaxonomyVocabulary>)descPage.getItems());
		}
	}

	@Override
	protected TaxonomyVocabulary
			testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary()
		throws Exception {

		return testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_addTaxonomyVocabulary();
	}

	@Override
	protected Long
			testGraphQLGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected Long
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	private void _setEntityFieldNames(
			TaxonomyVocabulary taxonomyVocabulary1,
			TaxonomyVocabulary taxonomyVocabulary2, String entityFieldName,
			Class<?> returnType)
		throws Exception {

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
				StringBundler.concat(
					"aaa",
					StringUtil.toLowerCase(RandomTestUtil.randomString()),
					"@liferay.com"));
			BeanTestUtil.setProperty(
				taxonomyVocabulary2, entityFieldName,
				StringBundler.concat(
					"bbb",
					StringUtil.toLowerCase(RandomTestUtil.randomString()),
					"@liferay.com"));
		}
		else {
			String randomString1 = StringUtil.toLowerCase(
				RandomTestUtil.randomString());

			BeanTestUtil.setProperty(
				taxonomyVocabulary1, entityFieldName, "aaa" + randomString1);

			String randomString2 = StringUtil.toLowerCase(
				RandomTestUtil.randomString());

			BeanTestUtil.setProperty(
				taxonomyVocabulary2, entityFieldName, "bbb" + randomString2);
		}
	}

}