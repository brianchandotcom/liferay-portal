/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.cms.client.dto.v1_0.SimilarityCluster;
import com.liferay.headless.cms.client.dto.v1_0.SimilarityClusterAsset;
import com.liferay.headless.cms.client.pagination.Page;
import com.liferay.headless.cms.client.pagination.Pagination;
import com.liferay.headless.cms.client.resource.v1_0.SimilarityClusterResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@FeatureFlag("LPD-82226")
@RunWith(Arquillian.class)
public class SimilarityClusterResourceTest
	extends BaseSimilarityClusterResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testGetSimilarityClustersPage() throws Exception {
		DepotEntry depotEntry = _addSpaceDepotEntry(
			ServiceContextTestUtil.getServiceContext());

		long groupId = depotEntry.getGroupId();

		ObjectDefinition objectDefinition =
			_getBasicWebContentObjectDefinition();

		Page<SimilarityCluster> similarityClustersPage =
			_getSimilarityClustersPage(groupId, null);

		Assert.assertEquals(0, similarityClustersPage.getTotalCount());

		List<SimilarityCluster> similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 0, similarityClusters.size());

		ObjectEntry nearDuplicateObjectEntry1 = _addObjectEntry(
			depotEntry, objectDefinition, _NEAR_DUPLICATE_TITLE,
			_NEAR_DUPLICATE_CONTENT);
		ObjectEntry nearDuplicateObjectEntry2 = _addObjectEntry(
			depotEntry, objectDefinition, _NEAR_DUPLICATE_TITLE,
			_NEAR_DUPLICATE_CONTENT +
				" You can also contact support for help.");

		_addObjectEntry(
			depotEntry, objectDefinition, RandomTestUtil.randomString(),
			_DISTINCT_CONTENT);

		similarityClustersPage = _getSimilarityClustersPage(groupId, null);

		Assert.assertEquals(2, similarityClustersPage.getTotalCount());

		similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 1, similarityClusters.size());

		SimilarityCluster similarityCluster = similarityClusters.get(0);

		Assert.assertEquals(
			2, GetterUtil.getInteger(similarityCluster.getSize()));

		SimilarityClusterAsset[] similarityClusterAssets =
			similarityCluster.getSimilarityClusterAssets();

		Assert.assertEquals(
			Arrays.toString(similarityClusterAssets), 2,
			similarityClusterAssets.length);

		List<Long> objectEntryIds = new ArrayList<>();

		for (SimilarityClusterAsset similarityClusterAsset :
				similarityClusterAssets) {

			objectEntryIds.add(similarityClusterAsset.getId());

			Assert.assertEquals(
				_NEAR_DUPLICATE_TITLE, similarityClusterAsset.getTitle());
			Assert.assertNotNull(similarityClusterAsset.getContentType());
			Assert.assertNotNull(similarityClusterAsset.getDateModified());
		}

		Assert.assertTrue(
			objectEntryIds.contains(
				nearDuplicateObjectEntry1.getObjectEntryId()));
		Assert.assertTrue(
			objectEntryIds.contains(
				nearDuplicateObjectEntry2.getObjectEntryId()));

		_depotEntryLocalService.deleteDepotEntry(depotEntry.getDepotEntryId());

		_testGetSimilarityClusterPermissions();
		_testGetSimilarityClusterTranslation();
	}

	@Override
	@Test
	public void testGetSimilarityClustersPageWithPagination() throws Exception {
		DepotEntry depotEntry = _addSpaceDepotEntry(
			ServiceContextTestUtil.getServiceContext());

		long groupId = depotEntry.getGroupId();

		_addObjectEntries(depotEntry);

		Page<SimilarityCluster> similarityClustersPage =
			_getSimilarityClustersPage(groupId, Pagination.of(1, 2));

		Assert.assertEquals(5, similarityClustersPage.getTotalCount());

		List<SimilarityCluster> similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 1, similarityClusters.size());

		_assertSimilarityCluster(
			similarityClusters.get(0), 3,
			new String[] {"Big Summer Sale", "Summer Sale 2026"});

		similarityClustersPage = _getSimilarityClustersPage(
			groupId, Pagination.of(2, 2));

		Assert.assertEquals(5, similarityClustersPage.getTotalCount());

		similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 2, similarityClusters.size());

		_assertSimilarityCluster(
			similarityClusters.get(0), 3,
			new String[] {"Summer Sale Highlights"});
		_assertSimilarityCluster(
			similarityClusters.get(1), 2, new String[] {_PRODUCT_LAUNCH_TITLE});

		similarityClustersPage = _getSimilarityClustersPage(
			groupId, Pagination.of(3, 2));

		Assert.assertEquals(5, similarityClustersPage.getTotalCount());

		similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 1, similarityClusters.size());

		_assertSimilarityCluster(
			similarityClusters.get(0), 2, new String[] {_PRODUCT_LAUNCH_TITLE});

		_depotEntryLocalService.deleteDepotEntry(depotEntry.getDepotEntryId());
	}

	@Test
	public void testGraphQLGetSimilarityClustersPage() throws Exception {
		DepotEntry depotEntry = _addSpaceDepotEntry(
			ServiceContextTestUtil.getServiceContext());

		ObjectDefinition objectDefinition =
			_getBasicWebContentObjectDefinition();

		_addObjectEntry(
			depotEntry, objectDefinition, _NEAR_DUPLICATE_TITLE,
			_NEAR_DUPLICATE_CONTENT);
		_addObjectEntry(
			depotEntry, objectDefinition, _NEAR_DUPLICATE_TITLE,
			_NEAR_DUPLICATE_CONTENT +
				" You can also contact support for help.");

		GraphQLField graphQLField = new GraphQLField(
			"similarityClusters",
			HashMapBuilder.<String, Object>put(
				"assetLibraryId", "\"" + depotEntry.getGroupId() + "\""
			).build(),
			new GraphQLField("items", new GraphQLField("size")),
			new GraphQLField("totalCount"));

		JSONObject similarityClustersPageJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/similarityClusters");

		Assert.assertEquals(
			2, similarityClustersPageJSONObject.getLong("totalCount"));

		JSONArray similarityClustersJSONArray =
			similarityClustersPageJSONObject.getJSONArray("items");

		Assert.assertEquals(
			similarityClustersJSONArray.toString(), 1,
			similarityClustersJSONArray.length());

		JSONObject similarityClusterJSONObject =
			similarityClustersJSONArray.getJSONObject(0);

		Assert.assertEquals(2, similarityClusterJSONObject.getInt("size"));

		_depotEntryLocalService.deleteDepotEntry(depotEntry.getDepotEntryId());
	}

	private void _addObjectEntries(DepotEntry depotEntry) throws Exception {
		ObjectDefinition objectDefinition =
			_getBasicWebContentObjectDefinition();

		_addObjectEntry(
			depotEntry, objectDefinition, "Big Summer Sale",
			_SUMMER_SALE_CONTENT);
		_addObjectEntry(
			depotEntry, objectDefinition, "Summer Sale 2026",
			_SUMMER_SALE_CONTENT + " The offer ends this Sunday.");
		_addObjectEntry(
			depotEntry, objectDefinition, "Summer Sale Highlights",
			_SUMMER_SALE_CONTENT +
				" The offer ends this Sunday and stock is limited.");

		_addObjectEntry(
			depotEntry, objectDefinition, _PRODUCT_LAUNCH_TITLE,
			_PRODUCT_LAUNCH_CONTENT);
		_addObjectEntry(
			depotEntry, objectDefinition, _PRODUCT_LAUNCH_TITLE,
			_PRODUCT_LAUNCH_CONTENT +
				" Contact the press office for further details.");

		_addObjectEntry(
			depotEntry, objectDefinition, RandomTestUtil.randomString(),
			_DISTINCT_CONTENT);
	}

	private ObjectEntry _addObjectEntry(
			DepotEntry depotEntry, ObjectDefinition objectDefinition,
			String title, String content)
		throws Exception {

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					"L_CONTENTS", depotEntry.getGroupId(),
					depotEntry.getCompanyId());

		return _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder.getObjectEntryFolderId(), "en_US",
			HashMapBuilder.<String, Serializable>put(
				"content_i18n",
				HashMapBuilder.put(
					"en_US", (Serializable)content
				).build()
			).put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", (Serializable)title
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private DepotEntry _addSpaceDepotEntry(ServiceContext serviceContext)
		throws Exception {

		return _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE, serviceContext);
	}

	private ObjectEntry _addTranslatedObjectEntry(
			DepotEntry depotEntry, ObjectDefinition objectDefinition,
			String title, String content, String spanishTitle,
			String spanishContent)
		throws Exception {

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					"L_CONTENTS", depotEntry.getGroupId(),
					depotEntry.getCompanyId());

		return _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder.getObjectEntryFolderId(), "en_US",
			HashMapBuilder.<String, Serializable>put(
				"content_i18n",
				HashMapBuilder.put(
					"en_US", content
				).put(
					"es_ES", spanishContent
				).build()
			).put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", title
				).put(
					"es_ES", spanishTitle
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertSimilarityCluster(
		SimilarityCluster similarityCluster, int size, String[] titles) {

		Assert.assertEquals(
			size, GetterUtil.getInteger(similarityCluster.getSize()));

		SimilarityClusterAsset[] similarityClusterAssets =
			similarityCluster.getSimilarityClusterAssets();

		Assert.assertEquals(
			Arrays.toString(similarityClusterAssets), titles.length,
			similarityClusterAssets.length);

		for (int i = 0; i < titles.length; i++) {
			SimilarityClusterAsset similarityClusterAsset =
				similarityClusterAssets[i];

			Assert.assertEquals(titles[i], similarityClusterAsset.getTitle());
		}
	}

	private String _getAdminUserEmailAddress() throws Exception {
		User user = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		return user.getEmailAddress();
	}

	private ObjectDefinition _getBasicWebContentObjectDefinition()
		throws Exception {

		Group cmsGroup = _groupLocalService.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.CMS);

		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_CMS_BASIC_WEB_CONTENT", cmsGroup.getCompanyId());
	}

	private Page<SimilarityCluster> _getSimilarityClustersPage(
			long groupId, Pagination pagination)
		throws Exception {

		return similarityClusterResource.getSimilarityClustersPage(
			groupId, pagination);
	}

	private void _testGetSimilarityClusterPermissions() throws Exception {
		DepotEntry depotEntry = _addSpaceDepotEntry(
			ServiceContextTestUtil.getServiceContext());

		long groupId = depotEntry.getGroupId();

		ObjectDefinition objectDefinition =
			_getBasicWebContentObjectDefinition();

		_addObjectEntry(
			depotEntry, objectDefinition, _NEAR_DUPLICATE_TITLE,
			_NEAR_DUPLICATE_CONTENT);
		_addObjectEntry(
			depotEntry, objectDefinition, _NEAR_DUPLICATE_TITLE,
			_NEAR_DUPLICATE_CONTENT +
				" You can also contact support for help.");

		String password = RandomTestUtil.randomString();

		User user = UserTestUtil.addUser(testCompany, password);

		_userLocalService.addGroupUser(groupId, user.getUserId());

		SimilarityClusterResource userSimilarityClusterResource =
			SimilarityClusterResource.builder(
			).authentication(
				user.getEmailAddress(), password
			).endpoint(
				testCompany.getVirtualHostname(),
				PortalUtil.getPortalServerPort(false), "http"
			).locale(
				LocaleUtil.getDefault()
			).build();

		Page<SimilarityCluster> similarityClustersPage =
			userSimilarityClusterResource.getSimilarityClustersPage(
				groupId, null);

		Assert.assertEquals(2, similarityClustersPage.getTotalCount());

		List<SimilarityCluster> similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 1, similarityClusters.size());

		_assertSimilarityCluster(
			similarityClusters.get(0), 2,
			new String[] {_NEAR_DUPLICATE_TITLE, _NEAR_DUPLICATE_TITLE});

		_userLocalService.deleteUser(user);

		_depotEntryLocalService.deleteDepotEntry(depotEntry.getDepotEntryId());
	}

	private void _testGetSimilarityClusterTranslation() throws Exception {
		DepotEntry depotEntry = _addSpaceDepotEntry(
			ServiceContextTestUtil.getServiceContext());

		long groupId = depotEntry.getGroupId();

		ObjectDefinition objectDefinition =
			_getBasicWebContentObjectDefinition();

		_addTranslatedObjectEntry(
			depotEntry, objectDefinition, _NEAR_DUPLICATE_TITLE,
			_NEAR_DUPLICATE_CONTENT, "Oferta de Verano Grande",
			_SPANISH_SUMMER_SALE_CONTENT);
		_addTranslatedObjectEntry(
			depotEntry, objectDefinition, RandomTestUtil.randomString(),
			_DISTINCT_CONTENT, "Oferta de Verano 2026",
			_SPANISH_SUMMER_SALE_CONTENT + " La oferta acaba el domingo.");

		SimilarityClusterResource spanishSimilarityClusterResource =
			SimilarityClusterResource.builder(
			).authentication(
				_getAdminUserEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
			).endpoint(
				testCompany.getVirtualHostname(),
				PortalUtil.getPortalServerPort(false), "http"
			).locale(
				LocaleUtil.SPAIN
			).build();

		Page<SimilarityCluster> similarityClustersPage =
			spanishSimilarityClusterResource.getSimilarityClustersPage(
				groupId, null);

		Assert.assertEquals(2, similarityClustersPage.getTotalCount());

		List<SimilarityCluster> similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 1, similarityClusters.size());

		_assertSimilarityCluster(
			similarityClusters.get(0), 2,
			new String[] {"Oferta de Verano Grande", "Oferta de Verano 2026"});

		similarityClustersPage = _getSimilarityClustersPage(groupId, null);

		Assert.assertEquals(0, similarityClustersPage.getTotalCount());

		similarityClusters =
			(List<SimilarityCluster>)similarityClustersPage.getItems();

		Assert.assertEquals(
			similarityClusters.toString(), 0, similarityClusters.size());

		_depotEntryLocalService.deleteDepotEntry(depotEntry.getDepotEntryId());
	}

	private static final String _DISTINCT_CONTENT =
		"The quarterly sales report shows strong revenue growth across the " +
			"European market with product categories in retail and wholesale " +
				"increasing during the last fiscal period.";

	private static final String _NEAR_DUPLICATE_CONTENT =
		"If you forgot your password go to the login page and click the " +
			"forgot password link enter your email address and you will " +
				"receive an email with instructions to create a new password.";

	private static final String _NEAR_DUPLICATE_TITLE = "Reset Your Password";

	private static final String _PRODUCT_LAUNCH_CONTENT =
		"The new generation of our platform is available today with a " +
			"redesigned workspace faster search and a set of integrations " +
				"that our customers have been asking for during this year.";

	private static final String _PRODUCT_LAUNCH_TITLE =
		"Product Launch Press Release";

	private static final String _SPANISH_SUMMER_SALE_CONTENT =
		"Nuestras rebajas de verano traen descuentos de hasta el cincuenta " +
			"por ciento en toda la coleccion de exterior incluidas tiendas " +
				"mochilas y botas de montana mientras queden existencias.";

	private static final String _SUMMER_SALE_CONTENT =
		"Our summer sale brings discounts of up to fifty percent on every " +
			"outdoor collection including tents backpacks and hiking boots " +
				"while stock lasts in all of our stores.";

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}