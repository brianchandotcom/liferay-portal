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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
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

		super.testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode();

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
		super.testGetAssetLibraryTaxonomyVocabulariesPage();

		TaxonomyVocabulary taxonomyVocabulary =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
				randomTaxonomyVocabulary());

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
				null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

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

		page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				testDepotEntry.getDepotEntryId(), null, null, null,
				Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			new TaxonomyVocabulary() {
				{
					name = taxonomyVocabulary.getName();
				}
			},
			page.fetchFirstItem());

		assertValid(page);
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
		super.testGraphQLGetSiteTaxonomyVocabulariesPage();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				testGroup.getGroupId(), null, null, null, Pagination.of(1, 10),
				null);

		for (TaxonomyVocabulary taxonomyVocabulary : page.getItems()) {
			taxonomyVocabularyResource.deleteTaxonomyVocabulary(
				taxonomyVocabulary.getId());
		}

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();
		TaxonomyVocabulary taxonomyVocabulary2 =
			testGraphQLGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary();

		GraphQLField graphQLField = new GraphQLField(
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

		JSONObject taxonomyVocabulariesJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/taxonomyVocabularies");

		Assert.assertEquals(
			2, taxonomyVocabulariesJSONObject.getLong("totalCount"));
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
					0
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
					1
				).getString(
					"term"
				)));

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
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

}