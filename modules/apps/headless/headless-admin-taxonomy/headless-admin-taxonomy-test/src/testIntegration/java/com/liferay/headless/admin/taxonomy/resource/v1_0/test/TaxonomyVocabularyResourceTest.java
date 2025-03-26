/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.asset.kernel.service.AssetVocabularyGroupRelLocalService;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.AssetLibrary;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.problem.Problem;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.TaxonomyVocabularySerDes;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags("LPD-17564")
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
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetAssetLibraryTaxonomyVocabulariesPage() throws Exception {
		super.testGetAssetLibraryTaxonomyVocabulariesPage();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
				null, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		TaxonomyVocabulary taxonomyVocabulary =
			testGetAssetLibraryTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
				randomTaxonomyVocabulary());

		page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				testGetAssetLibraryTaxonomyVocabulariesPage_getAssetLibraryId(),
				null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

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
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"restrictFields",
			StringBundler.concat(
				"actions,assetLibraries,assetLibraryKey,",
				"assetTypes,availableLanguages,creator,dateCreated,",
				"dateModified,description,externalReferenceCode,id,",
				"multiValued,numberOfTaxonomyCategories,visibilityType")
		).build();

		page =
			taxonomyVocabularyResource.getAssetLibraryTaxonomyVocabulariesPage(
				testDepotEntry.getDepotEntryId(), null, null, null,
				Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

		assertContains(
			new TaxonomyVocabulary() {
				{
					name = taxonomyVocabulary.getName();
				}
			},
			(List<TaxonomyVocabulary>)page.getItems());

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
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetSiteTaxonomyVocabulariesPage() throws Exception {
		super.testGetSiteTaxonomyVocabulariesPage();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				testGetSiteTaxonomyVocabulariesPage_getSiteId(), null, null,
				null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		testGetSiteTaxonomyVocabulariesPage_addTaxonomyVocabulary(
			testGetSiteTaxonomyVocabulariesPage_getSiteId(),
			randomTaxonomyVocabulary());

		page = taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
			testGetSiteTaxonomyVocabulariesPage_getSiteId(), null, null, null,
			Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

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
	public void testGetTaxonomyVocabulariesPage() throws Exception {
		super.testGetTaxonomyVocabulariesPage();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getTaxonomyVocabulariesPage(
				null, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		TaxonomyVocabulary taxonomyVocabulary =
			testGetTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				randomTaxonomyVocabulary());

		testPostSiteTaxonomyVocabulary_addTaxonomyVocabulary(
			randomTaxonomyVocabulary());

		page = taxonomyVocabularyResource.getTaxonomyVocabulariesPage(
			null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

		assertContains(
			taxonomyVocabulary, (List<TaxonomyVocabulary>)page.getItems());

		assertValid(
			page,
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies"
				).put(
					"method", "POST"
				).build()
			).put(
				"createBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-vocabularies/batch"
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

		_testGetTaxonomyVocabularyActions();
		_testGetTaxonomyVocabularyWithoutPermissionsAction();
	}

	@Override
	@Test
	public void testGraphQLGetTaxonomyVocabulariesPage() throws Exception {
		super.testGraphQLGetTaxonomyVocabulariesPage();

		Page<TaxonomyVocabulary> page =
			taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
				testGroup.getGroupId(), null, null, null, Pagination.of(1, 10),
				null);

		for (TaxonomyVocabulary taxonomyVocabulary : page.getItems()) {
			taxonomyVocabularyResource.deleteTaxonomyVocabulary(
				taxonomyVocabulary.getId());
		}

		TaxonomyVocabulary taxonomyVocabulary1 =
			testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary();
		TaxonomyVocabulary taxonomyVocabulary2 =
			testGraphQLTaxonomyVocabulary_addTaxonomyVocabulary();

		GraphQLField graphQLField = new GraphQLField(
			"siteTaxonomyVocabularies",
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
				"JSONObject/siteTaxonomyVocabularies");

		Assert.assertEquals(
			2, taxonomyVocabulariesJSONObject.getLong("totalCount"));

		JSONAssert.assertEquals(
			JSONFactoryUtil.createJSONArray(
			).put(
				JSONUtil.put(
					"facetCriteria", "id"
				).put(
					"facetValues",
					JSONFactoryUtil.createJSONArray(
					).put(
						JSONUtil.put(
							"numberOfOccurrences", 1
						).put(
							"term", String.valueOf(taxonomyVocabulary1.getId())
						)
					).put(
						JSONUtil.put(
							"numberOfOccurrences", 1
						).put(
							"term", String.valueOf(taxonomyVocabulary2.getId())
						)
					)
				)
			).toString(),
			taxonomyVocabulariesJSONObject.getJSONArray(
				"facets"
			).toString(),
			JSONCompareMode.LENIENT);

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyVocabulary1, taxonomyVocabulary2),
			Arrays.asList(
				TaxonomyVocabularySerDes.toDTOs(
					taxonomyVocabulariesJSONObject.getString("items"))));
	}

	@Override
	@Test
	public void testPostTaxonomyVocabulary() throws Exception {
		AssetLibrary[] assetLibraries = {
			_randomAssetLibrary(), _randomAssetLibrary()
		};

		TaxonomyVocabulary randomTaxonomyVocabulary =
			_randomTaxonomyVocabularyWithAssetLibraries(assetLibraries);

		TaxonomyVocabulary postTaxonomyVocabulary =
			taxonomyVocabularyResource.postTaxonomyVocabulary(
				randomTaxonomyVocabulary);

		assertEquals(randomTaxonomyVocabulary, postTaxonomyVocabulary);
		Assert.assertTrue(
			Objects.deepEquals(
				assetLibraries, postTaxonomyVocabulary.getAssetLibraries()));

		List<Long> groupIds = ListUtil.toList(
			_assetVocabularyGroupRelLocalService.
				getAssetVocabularyGroupRelsByVocabularyId(
					postTaxonomyVocabulary.getId()),
			AssetVocabularyGroupRel::getGroupId);

		Assert.assertEquals(
			groupIds.toString(), assetLibraries.length, groupIds.size());
		Assert.assertTrue(
			groupIds.containsAll(
				TransformUtil.transformToList(
					assetLibraries, AssetLibrary::getId)));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"assetTypes", "description", "multiValued", "name", "visibilityType"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"dateCreated", "dateModified"};
	}

	@Override
	protected TaxonomyVocabulary randomTaxonomyVocabulary() throws Exception {
		return new TaxonomyVocabulary() {
			{
				assetTypes = new AssetType[] {
					new AssetType() {
						{
							required = RandomTestUtil.randomBoolean();
							subtype = "AllAssetSubtypes";
							type = "AllAssetTypes";
							typeId = 0L;
						}
					}
				};
				description = RandomTestUtil.randomString();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				multiValued = RandomTestUtil.randomBoolean();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				visibilityType = VisibilityType.PUBLIC;
			}
		};
	}

	@Override
	protected Long
			testDeleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected Long
			testGetAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected TaxonomyVocabulary
			testGetTaxonomyVocabulariesPage_addTaxonomyVocabulary(
				TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		taxonomyVocabulary.setAssetLibraries(
			new AssetLibrary[] {_randomAssetLibrary()});

		return taxonomyVocabularyResource.postTaxonomyVocabulary(
			taxonomyVocabulary);
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
	protected TaxonomyVocabulary
			testGraphQLGetTaxonomyVocabulariesPage_addTaxonomyVocabulary()
		throws Exception {

		return taxonomyVocabularyResource.postTaxonomyVocabulary(
			_randomTaxonomyVocabularyWithAssetLibraries(_randomAssetLibrary()));
	}

	@Override
	protected Long
			testPutAssetLibraryTaxonomyVocabularyByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	private AssetLibrary _randomAssetLibrary() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(), null,
			ServiceContextTestUtil.getServiceContext());

		Group depotEntryGroup = depotEntry.getGroup();

		return new AssetLibrary() {
			{
				id = depotEntryGroup.getGroupId();
				name = depotEntryGroup.getName(LocaleUtil.getDefault());
			}
		};
	}

	private TaxonomyVocabulary _randomTaxonomyVocabularyWithAssetLibraries(
			AssetLibrary... assetLibraries)
		throws Exception {

		TaxonomyVocabulary taxonomyVocabulary = randomTaxonomyVocabulary();

		taxonomyVocabulary.setAssetLibraries(assetLibraries);

		taxonomyVocabulary.setSiteId(GroupConstants.DEFAULT_LIVE_GROUP_ID);

		return taxonomyVocabulary;
	}

	private void _testGetTaxonomyVocabularyActions() throws Exception {
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

	private void _testGetTaxonomyVocabularyWithoutPermissionsAction()
		throws Exception {

		TaxonomyVocabulary randomTaxonomyVocabulary =
			randomTaxonomyVocabulary();

		randomTaxonomyVocabulary.setViewableBy(
			TaxonomyVocabulary.ViewableBy.MEMBERS);

		TaxonomyVocabulary postTaxonomyVocabulary =
			taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
				testGroup.getGroupId(), randomTaxonomyVocabulary);

		User siteMemberUser = UserTestUtil.addGroupUser(
			testGroup, RoleConstants.SITE_MEMBER);

		String password = RandomTestUtil.randomString();

		_userLocalService.updatePassword(
			siteMemberUser.getUserId(), password, password, false, true);

		TaxonomyVocabularyResource siteMemberTaxonomyVocabularyResource =
			TaxonomyVocabularyResource.builder(
			).authentication(
				siteMemberUser.getEmailAddress(), password
			).endpoint(
				testCompany.getVirtualHostname(), 8080, "http"
			).locale(
				LocaleUtil.getDefault()
			).build();

		TaxonomyVocabulary getTaxonomyVocabulary =
			siteMemberTaxonomyVocabularyResource.getTaxonomyVocabulary(
				postTaxonomyVocabulary.getId());

		Assert.assertNull(getTaxonomyVocabulary.getPermissions());
	}

	@Inject
	private AssetVocabularyGroupRelLocalService
		_assetVocabularyGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private UserLocalService _userLocalService;

}