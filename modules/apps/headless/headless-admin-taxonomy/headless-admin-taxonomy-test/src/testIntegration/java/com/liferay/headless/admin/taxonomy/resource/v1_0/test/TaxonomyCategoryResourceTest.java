/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.ParentTaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategoryProperty;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.permission.Permission;
import com.liferay.headless.admin.taxonomy.client.problem.Problem;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@FeatureFlags("LPD-41304")
@RunWith(Arquillian.class)
public class TaxonomyCategoryResourceTest
	extends BaseTaxonomyCategoryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_assetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getGuestUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());

		DepotEntry depotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		_depotAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getGuestUserId(testGroup.getCompanyId()),
			depotEntry.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());

		_globalAssetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getGuestUserId(testGroup.getCompanyId()),
			testCompany.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());
		_internalAssetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				UserLocalServiceUtil.getGuestUserId(testGroup.getCompanyId()),
				testGroup.getGroupId(), null,
				HashMapBuilder.put(
					LocaleUtil.US, RandomTestUtil.randomString()
				).build(),
				null, null, AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL,
				new ServiceContext());
	}

	@Override
	@Test
	public void testGetTaxonomyCategory() throws Exception {
		super.testGetTaxonomyCategory();

		_testGetTaxonomyCategoryTaxonomyCategoryUsageCount();
		_testGetTaxonomyCategoryWithAssetCategoryProperty();
		_testGetTaxonomyCategoryWithPermissions();
	}

	@Override
	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (ListUtil.isEmpty(entityFields)) {
			return;
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		AssetCategory parentAssetCategory =
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), testGroup.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), serviceContext);

		AssetCategory assetCategory1 = _addAssetCategory(
			_assetVocabulary,
			new Date(System.currentTimeMillis() - (2 * Time.MINUTE)),
			parentAssetCategory, serviceContext);
		AssetCategory assetCategory2 = _addAssetCategory(
			_assetVocabulary, new Date(), parentAssetCategory, serviceContext);

		for (EntityField entityField : entityFields) {
			_assertTaxonomyCategoriesPageOrder(
				entityField, assetCategory1, assetCategory2, "asc",
				parentAssetCategory);
			_assertTaxonomyCategoriesPageOrder(
				entityField, assetCategory2, assetCategory1, "desc",
				parentAssetCategory);
		}
	}

	@Override
	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPage()
		throws Exception {

		super.testGetTaxonomyVocabularyTaxonomyCategoriesPage();

		_testGetTaxonomyVocabularyTaxonomyCategoriesPageFlatten(
			_assetVocabulary);
		_testGetTaxonomyVocabularyTaxonomyCategoriesPageFlatten(
			_depotAssetVocabulary);
		_testGetTaxonomyVocabularyTaxonomyCategoriesPageFlatten(
			_globalAssetVocabulary);
		_testGetTaxonomyVocabularyTaxonomyCategoriesPageFlatten(
			_internalAssetVocabulary);
		_testGetTaxonomyVocabularyTaxonomyCategoriesPageFlattenWithOnlyNameField(
			_assetVocabulary);
	}

	@Override
	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode()
		throws Exception {

		super.
			testGetTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode();

		TaxonomyCategory taxonomyCategory =
			testGetTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode_addTaxonomyCategory();

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		try {
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode(
					testGetTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode_getTaxonomyVocabularyId(
						taxonomyCategory),
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
	public void testPatchTaxonomyCategory() throws Exception {
		super.testPatchTaxonomyCategory();

		_testPatchTaxonomyCategoryWithExistingParentTaxonomyCategory(
			testPatchTaxonomyCategory_addTaxonomyCategory(),
			_addAssetVocabulary());
		_testPatchTaxonomyCategoryWithNonexistentParentTaxonomyCategory(
			randomTaxonomyCategory(),
			testPatchTaxonomyCategory_addTaxonomyCategory());
		_testPatchTaxonomyCategoryWithNonexistentParentTaxonomyVocabulary(
			testPatchTaxonomyCategory_addTaxonomyCategory(),
			_randomTaxonomyVocabulary());

		AssetVocabulary assetVocabulary1 = _addAssetVocabulary();
		AssetVocabulary assetVocabulary2 = _addAssetVocabulary();

		_testPatchTaxonomyCategoryWithParentTaxonomyCategoryInADifferentTaxonomyVocabulary(
			_addTaxonomyCategoryWithParentAssetVocabulary(assetVocabulary1),
			_addTaxonomyCategoryWithParentAssetVocabulary(assetVocabulary2));
	}

	@Override
	@Test
	public void testPostTaxonomyCategoryTaxonomyCategory() throws Exception {
		super.testPostTaxonomyCategoryTaxonomyCategory();

		_testPostTaxonomyCategoryTaxonomyCategoryWithPermissions();
	}

	@Override
	@Test
	public void testPutTaxonomyCategory() throws Exception {
		super.testPutTaxonomyCategory();

		_testPutTaxonomyCategoryWithPermissions();
	}

	@Override
	@Test
	public void testPutTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode()
		throws Exception {

		super.
			testPutTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected TaxonomyCategory randomTaxonomyCategory() throws Exception {
		TaxonomyCategory taxonomyCategory = super.randomTaxonomyCategory();

		taxonomyCategory.setId(String.valueOf(RandomTestUtil.randomLong()));
		taxonomyCategory.setTaxonomyVocabularyId(
			_assetVocabulary.getVocabularyId());

		return taxonomyCategory;
	}

	@Override
	protected TaxonomyCategory testDeleteTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return testGetTaxonomyCategory_addTaxonomyCategory();
	}

	@Override
	protected TaxonomyCategory
			testDeleteTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode_addTaxonomyCategory()
		throws Exception {

		return testGetTaxonomyCategory_addTaxonomyCategory();
	}

	@Override
	protected TaxonomyCategory
			testGetTaxonomyCategoriesRankedPage_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		taxonomyCategory =
			testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
				taxonomyCategory);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			testGroup.getGroupId());

		AssetEntryAssetCategoryRelLocalServiceUtil.
			addAssetEntryAssetCategoryRel(
				assetEntry.getEntryId(),
				GetterUtil.getLong(taxonomyCategory.getId()));

		return taxonomyCategory;
	}

	@Override
	protected TaxonomyCategory testGetTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), randomTaxonomyCategory());
	}

	@Override
	protected String
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId()
		throws Exception {

		TaxonomyCategory taxonomyCategory =
			taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
				_assetVocabulary.getVocabularyId(), randomTaxonomyCategory());

		return taxonomyCategory.getId();
	}

	@Override
	protected Long
		testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId() {

		return _assetVocabulary.getVocabularyId();
	}

	@Override
	protected TaxonomyCategory
			testGetTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode_addTaxonomyCategory()
		throws Exception {

		return testGetTaxonomyCategory_addTaxonomyCategory();
	}

	@Override
	protected TaxonomyCategory testGraphQLTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
			randomTaxonomyCategory());
	}

	@Override
	protected TaxonomyCategory testPatchTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return testGetTaxonomyCategory_addTaxonomyCategory();
	}

	@Override
	protected TaxonomyCategory
			testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), taxonomyCategory);
	}

	@Override
	protected TaxonomyCategory
			testPostTaxonomyVocabularyTaxonomyCategory_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		return testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
			taxonomyCategory);
	}

	@Override
	protected TaxonomyCategory testPutTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return testGetTaxonomyCategory_addTaxonomyCategory();
	}

	@Override
	protected TaxonomyCategory
			testPutTaxonomyCategoryPermissionsPage_addTaxonomyCategory()
		throws Exception {

		return testGetTaxonomyCategory_addTaxonomyCategory();
	}

	@Override
	protected TaxonomyCategory
			testPutTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode_addTaxonomyCategory()
		throws Exception {

		return testGetTaxonomyCategory_addTaxonomyCategory();
	}

	private AssetCategory _addAssetCategory(
			AssetVocabulary assetVocabulary, Date date,
			AssetCategory parentAssetCategory, ServiceContext serviceContext)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), testGroup.getGroupId(),
			parentAssetCategory.getCategoryId(),
			RandomTestUtil.randomLocaleStringMap(), null,
			assetVocabulary.getVocabularyId(), null, serviceContext);

		assetCategory.setCreateDate(date);
		assetCategory.setModifiedDate(date);

		return _assetCategoryLocalService.updateAssetCategory(assetCategory);
	}

	private AssetVocabulary _addAssetVocabulary() throws Exception {
		return AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getGuestUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());
	}

	private TaxonomyCategory _addTaxonomyCategoryWithParentAssetVocabulary(
			AssetVocabulary assetVocabulary)
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			assetVocabulary.getVocabularyId(), randomTaxonomyCategory());
	}

	private TaxonomyCategory _addTaxonomyCategoryWithParentTaxonomyCategory(
			String parentTaxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyCategoryTaxonomyCategory(
			parentTaxonomyCategoryId, taxonomyCategory);
	}

	private void _assertPermissions(
		String[] expectedActionIds, Permission permission, String role) {

		Assert.assertEquals(
			expectedActionIds.length, permission.getActionIds().length);

		Object[] actionIds = permission.getActionIds();

		for (String expectedActionId : expectedActionIds) {
			Assert.assertTrue(ArrayUtil.contains(actionIds, expectedActionId));
		}

		Assert.assertEquals(role, permission.getRoleName());
	}

	private void _assertTaxonomyCategoriesPageOrder(
			EntityField entityField, AssetCategory firstAssetCategory,
			AssetCategory secondAssetCategory, String orderBy,
			AssetCategory parentAssetCategory)
		throws Exception {

		Page<TaxonomyCategory> taxonomyCategoriesPage =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				String.valueOf(parentAssetCategory.getCategoryId()), null, null,
				null, Pagination.of(1, 2),
				entityField.getName() + ":" + orderBy);

		Assert.assertEquals(
			taxonomyCategoriesPage.toString(), 2,
			taxonomyCategoriesPage.getTotalCount());

		List<TaxonomyCategory> taxonomyCategories =
			(List<TaxonomyCategory>)taxonomyCategoriesPage.getItems();

		TaxonomyCategory taxonomyCategory = taxonomyCategories.get(0);

		Assert.assertEquals(
			String.valueOf(firstAssetCategory.getCategoryId()),
			taxonomyCategory.getId());

		taxonomyCategory = taxonomyCategories.get(1);

		Assert.assertEquals(
			String.valueOf(secondAssetCategory.getCategoryId()),
			taxonomyCategory.getId());
	}

	private TaxonomyVocabulary _randomTaxonomyVocabulary() {
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
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	private void _testGetTaxonomyCategoryTaxonomyCategoryUsageCount()
		throws Exception {

		TaxonomyCategory postTaxonomyCategory =
			testGetTaxonomyCategory_addTaxonomyCategory();

		TaxonomyCategory getTaxonomyCategory =
			taxonomyCategoryResource.getTaxonomyCategory(
				postTaxonomyCategory.getId());

		assertValid(
			getTaxonomyCategory.getActions(),
			HashMapBuilder.<String, Map<String, String>>put(
				"add-category",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/taxonomy-categories/", getTaxonomyCategory.getId(),
						"/taxonomy-categories")
				).put(
					"method", "POST"
				).build()
			).put(
				"delete",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-categories/" + getTaxonomyCategory.getId()
				).put(
					"method", "DELETE"
				).build()
			).put(
				"get",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-categories/" + getTaxonomyCategory.getId()
				).put(
					"method", "GET"
				).build()
			).put(
				"replace",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-categories/" + getTaxonomyCategory.getId()
				).put(
					"method", "PUT"
				).build()
			).put(
				"update",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/headless-admin-taxonomy/v1.0" +
						"/taxonomy-categories/" + getTaxonomyCategory.getId()
				).put(
					"method", "PATCH"
				).build()
			).build());

		Assert.assertNull(postTaxonomyCategory.getTaxonomyCategoryUsageCount());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			"headless-admin-taxonomy/v1.0/taxonomy-categories/" +
				getTaxonomyCategory.getId() +
					"?nestedFields=taxonomyCategoryUsageCount",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.get("taxonomyCategoryUsageCount"));

		_addTaxonomyCategoryWithParentTaxonomyCategory(
			postTaxonomyCategory.getId(), randomTaxonomyCategory());

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"headless-admin-taxonomy/v1.0/taxonomy-categories/",
				postTaxonomyCategory.getId(), "/taxonomy-categories",
				"?nestedFields=taxonomyCategoryUsageCount"),
			Http.Method.GET);

		JSONArray itemsJSONArray = (JSONArray)jsonObject.get("items");

		JSONObject itemJSONObject = (JSONObject)itemsJSONArray.get(0);

		Assert.assertNotNull(itemJSONObject.get("taxonomyCategoryUsageCount"));
	}

	private void _testGetTaxonomyCategoryWithAssetCategoryProperty()
		throws Exception {

		TaxonomyCategory taxonomyCategory =
			testGetTaxonomyCategoriesRankedPage_addTaxonomyCategory(
				randomTaxonomyCategory());

		String key = RandomTestUtil.randomString();
		String value = RandomTestUtil.randomString();

		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(),
			GetterUtil.getLong(taxonomyCategory.getId()), key, value);

		taxonomyCategory = taxonomyCategoryResource.getTaxonomyCategory(
			taxonomyCategory.getId());

		TaxonomyCategoryProperty[] taxonomyCategoryProperties =
			taxonomyCategory.getTaxonomyCategoryProperties();

		Assert.assertNotNull(taxonomyCategoryProperties[0]);

		TaxonomyCategoryProperty taxonomyCategoryProperty =
			taxonomyCategoryProperties[0];

		Assert.assertNotNull(
			taxonomyCategoryProperty.toString(),
			taxonomyCategoryProperty.getExternalReferenceCode());
		Assert.assertEquals(
			taxonomyCategoryProperty.toString(),
			taxonomyCategoryProperty.getKey(), key);
		Assert.assertEquals(
			taxonomyCategoryProperty.toString(),
			taxonomyCategoryProperty.getValue(), value);
	}

	private void _testGetTaxonomyCategoryWithPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		AssetCategory parentAssetCategory =
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), testGroup.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), serviceContext);

		AssetCategory assetCategory = _addAssetCategory(
			_assetVocabulary, new Date(), parentAssetCategory, serviceContext);

		_resourcePermissionLocalService.deleteResourcePermissions(
			assetCategory.getCompanyId(), AssetCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, assetCategory.getCategoryId());

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), AssetCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(assetCategory.getCategoryId()), role.getRoleId(),
			new String[] {ActionKeys.DELETE, ActionKeys.PERMISSIONS});

		Page<TaxonomyCategory> page =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				String.valueOf(parentAssetCategory.getCategoryId()), null, null,
				null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		List<TaxonomyCategory> taxonomyCategories =
			(List<TaxonomyCategory>)page.getItems();

		TaxonomyCategory taxonomyCategory = taxonomyCategories.get(0);

		Assert.assertNotNull(taxonomyCategory.getPermissions());

		Permission[] permissions = taxonomyCategory.getPermissions();

		Assert.assertEquals(
			Arrays.toString(permissions), 1, permissions.length);

		Permission permission = permissions[0];

		Assert.assertEquals(role.getName(), permission.getRoleName());
		Assert.assertEquals(
			new Object[] {ActionKeys.DELETE, ActionKeys.PERMISSIONS},
			permission.getActionIds());
	}

	private void _testGetTaxonomyVocabularyTaxonomyCategoriesPageFlatten(
			AssetVocabulary assetVocabulary)
		throws Exception {

		AssetVocabulary irrelevantAssetVocabulary = _addAssetVocabulary();

		TaxonomyCategory taxonomyCategory1 =
			_addTaxonomyCategoryWithParentAssetVocabulary(assetVocabulary);

		TaxonomyCategory taxonomyCategory2 =
			_addTaxonomyCategoryWithParentTaxonomyCategory(
				taxonomyCategory1.getId(), randomTaxonomyCategory());

		TaxonomyCategory irrelevantTaxonomyCategory =
			_addTaxonomyCategoryWithParentAssetVocabulary(
				irrelevantAssetVocabulary);

		Boolean flatten = false;

		Page<TaxonomyCategory> page =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					assetVocabulary.getVocabularyId(), flatten, null, null,
					null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyCategory1),
			(List<TaxonomyCategory>)page.getItems());
		assertValid(page);

		flatten = true;

		page =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					assetVocabulary.getVocabularyId(), flatten, null, null,
					null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyCategory1, taxonomyCategory2),
			(List<TaxonomyCategory>)page.getItems());
		assertValid(page);

		List<TaxonomyCategory> taxonomyCategories =
			(List<TaxonomyCategory>)page.getItems();

		TaxonomyCategory getTaxonomyCategory1 = taxonomyCategories.get(0);
		TaxonomyCategory getTaxonomyCategory2 = taxonomyCategories.get(1);

		ParentTaxonomyCategory parentTaxonomyCategory1 =
			getTaxonomyCategory1.getParentTaxonomyCategory();
		ParentTaxonomyCategory parentTaxonomyCategory2 =
			getTaxonomyCategory2.getParentTaxonomyCategory();

		Assert.assertTrue(
			((parentTaxonomyCategory1 == null) &&
			 (parentTaxonomyCategory2 != null)) ||
			((parentTaxonomyCategory1 != null) &&
			 (parentTaxonomyCategory2 == null)));

		if (parentTaxonomyCategory1 != null) {
			Assert.assertEquals(
				getTaxonomyCategory2.getId(),
				String.valueOf(parentTaxonomyCategory1.getId()));
			Assert.assertEquals(
				getTaxonomyCategory2.getName(),
				String.valueOf(parentTaxonomyCategory1.getName()));
		}

		if (parentTaxonomyCategory2 != null) {
			Assert.assertEquals(
				getTaxonomyCategory1.getId(),
				String.valueOf(parentTaxonomyCategory2.getId()));
			Assert.assertEquals(
				getTaxonomyCategory1.getName(),
				String.valueOf(parentTaxonomyCategory2.getName()));
		}

		taxonomyCategoryResource.deleteTaxonomyCategory(
			irrelevantTaxonomyCategory.getId());

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory2.getId());

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory1.getId());
	}

	private void
			_testGetTaxonomyVocabularyTaxonomyCategoriesPageFlattenWithOnlyNameField(
				AssetVocabulary assetVocabulary)
		throws Exception {

		AssetVocabulary irrelevantAssetVocabulary = _addAssetVocabulary();

		TaxonomyCategory taxonomyCategory1 =
			_addTaxonomyCategoryWithParentAssetVocabulary(assetVocabulary);

		TaxonomyCategory taxonomyCategory2 =
			_addTaxonomyCategoryWithParentTaxonomyCategory(
				taxonomyCategory1.getId(), randomTaxonomyCategory());

		TaxonomyCategory irrelevantTaxonomyCategory =
			_addTaxonomyCategoryWithParentAssetVocabulary(
				irrelevantAssetVocabulary);

		TaxonomyCategoryResource.Builder builder =
			TaxonomyCategoryResource.builder();

		taxonomyCategoryResource = builder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"fields", "name"
		).build();

		Page<TaxonomyCategory> page =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					assetVocabulary.getVocabularyId(), true, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		TaxonomyCategory getTaxonomyCategory1 = new TaxonomyCategory() {
			{
				name = taxonomyCategory1.getName();
			}
		};
		TaxonomyCategory getTaxonomyCategory2 = new TaxonomyCategory() {
			{
				name = taxonomyCategory2.getName();
			}
		};

		assertEqualsIgnoringOrder(
			Arrays.asList(getTaxonomyCategory1, getTaxonomyCategory2),
			(List<TaxonomyCategory>)page.getItems());

		assertValid(page);

		taxonomyCategoryResource.deleteTaxonomyCategory(
			irrelevantTaxonomyCategory.getId());

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory2.getId());

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory1.getId());
	}

	private void _testPatchTaxonomyCategoryWithExistingParentTaxonomyCategory(
			TaxonomyCategory taxonomyCategory, AssetVocabulary assetVocabulary)
		throws Exception {

		taxonomyCategoryResource.patchTaxonomyCategory(
			taxonomyCategory.getId(),
			new TaxonomyCategory() {
				{
					taxonomyVocabularyId = assetVocabulary.getVocabularyId();
				}
			});

		TaxonomyCategory patchParentTaxonomyCategory =
			taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
				assetVocabulary.getVocabularyId(), randomTaxonomyCategory());

		TaxonomyCategory patchTaxonomyCategory =
			taxonomyCategoryResource.patchTaxonomyCategory(
				taxonomyCategory.getId(),
				new TaxonomyCategory() {
					{
						parentTaxonomyCategory = new ParentTaxonomyCategory() {
							{
								externalReferenceCode =
									patchParentTaxonomyCategory.
										getExternalReferenceCode();
								id = Long.valueOf(
									patchParentTaxonomyCategory.getId());
							}
						};
					}
				});

		Assert.assertEquals(
			patchTaxonomyCategory.getTaxonomyVocabularyId(),
			Long.valueOf(assetVocabulary.getVocabularyId()));

		ParentTaxonomyCategory parentTaxonomyCategory =
			patchTaxonomyCategory.getParentTaxonomyCategory();

		Assert.assertEquals(
			parentTaxonomyCategory.getExternalReferenceCode(),
			patchParentTaxonomyCategory.getExternalReferenceCode());
		Assert.assertEquals(
			parentTaxonomyCategory.getId(),
			Long.valueOf(patchParentTaxonomyCategory.getId()));
	}

	private void
			_testPatchTaxonomyCategoryWithNonexistentParentTaxonomyCategory(
				TaxonomyCategory randomTaxonomyCategory,
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		assertHttpResponseStatusCode(
			404,
			taxonomyCategoryResource.patchTaxonomyCategoryHttpResponse(
				taxonomyCategory.getId(),
				new TaxonomyCategory() {
					{
						parentTaxonomyCategory = new ParentTaxonomyCategory() {
							{
								externalReferenceCode =
									randomTaxonomyCategory.
										getExternalReferenceCode();
								id = Long.valueOf(
									randomTaxonomyCategory.getId());
							}
						};
					}
				}));
	}

	private void
			_testPatchTaxonomyCategoryWithNonexistentParentTaxonomyVocabulary(
				TaxonomyCategory taxonomyCategory,
				TaxonomyVocabulary randomTaxonomyVocabulary)
		throws Exception {

		assertHttpResponseStatusCode(
			404,
			taxonomyCategoryResource.patchTaxonomyCategoryHttpResponse(
				taxonomyCategory.getId(),
				new TaxonomyCategory() {
					{
						taxonomyVocabularyId = randomTaxonomyVocabulary.getId();
					}
				}));
	}

	private void
			_testPatchTaxonomyCategoryWithParentTaxonomyCategoryInADifferentTaxonomyVocabulary(
				TaxonomyCategory taxonomyCategory1,
				TaxonomyCategory taxonomyCategory2)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			assertHttpResponseStatusCode(
				400,
				taxonomyCategoryResource.patchTaxonomyCategoryHttpResponse(
					taxonomyCategory1.getId(),
					new TaxonomyCategory() {
						{
							parentTaxonomyCategory =
								new ParentTaxonomyCategory() {
									{
										externalReferenceCode =
											taxonomyCategory2.
												getExternalReferenceCode();
										id = Long.valueOf(
											taxonomyCategory2.getId());
									}
								};
							taxonomyVocabularyId =
								taxonomyCategory1.getTaxonomyVocabularyId();
						}
					}));
		}
	}

	private void _testPostTaxonomyCategoryTaxonomyCategoryWithPermissions()
		throws Exception {

		TaxonomyCategory randomTaxonomyCategory = randomTaxonomyCategory();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		randomTaxonomyCategory.setPermissions(
			new Permission[] {
				new Permission() {
					{
						actionIds = new String[] {ActionKeys.DELETE};
						roleName = role.getName();
					}
				}
			});

		TaxonomyCategory postTaxonomyCategory =
			testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
				randomTaxonomyCategory);

		Permission[] permissions = postTaxonomyCategory.getPermissions();

		Assert.assertNotNull(permissions);
		Assert.assertEquals(
			Arrays.toString(permissions), 2, permissions.length);

		for (Permission permission : permissions) {
			if (Objects.equals(permission.getRoleName(), role.getName())) {
				Assert.assertArrayEquals(
					new String[] {ActionKeys.DELETE},
					permission.getActionIds());
			}

			if (Objects.equals(permission.getRoleName(), RoleConstants.OWNER)) {
				Assert.assertArrayEquals(
					new String[] {
						ActionKeys.DELETE, ActionKeys.PERMISSIONS,
						ActionKeys.ADD_CATEGORY, ActionKeys.UPDATE,
						ActionKeys.VIEW
					},
					permission.getActionIds());
			}
		}
	}

	private void _testPutTaxonomyCategoryWithPermissions() throws Exception {
		TaxonomyCategory postTaxonomyCategory =
			testPutTaxonomyCategory_addTaxonomyCategory();

		TaxonomyCategory randomTaxonomyCategory = randomTaxonomyCategory();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		Permission permission = new Permission() {
			{
				actionIds = new String[] {ActionKeys.VIEW};
				roleName = role.getName();
			}
		};

		randomTaxonomyCategory.setPermissions(new Permission[] {permission});

		TaxonomyCategory putTaxonomyCategory =
			taxonomyCategoryResource.putTaxonomyCategory(
				postTaxonomyCategory.getId(), randomTaxonomyCategory);

		Permission[] permissions = putTaxonomyCategory.getPermissions();

		Assert.assertNotNull(permissions);
		Assert.assertEquals(
			Arrays.toString(permissions), 1, permissions.length);

		_assertPermissions(
			new String[] {ActionKeys.VIEW}, permissions[0], role.getName());
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	private AssetVocabulary _assetVocabulary;
	private AssetVocabulary _depotAssetVocabulary;
	private AssetVocabulary _globalAssetVocabulary;
	private AssetVocabulary _internalAssetVocabulary;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}