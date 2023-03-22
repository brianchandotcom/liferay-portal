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

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.ParentTaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class TaxonomyCategoryResourceTest
	extends BaseTaxonomyCategoryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_assetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());
	}

	@Override
	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPage()
		throws Exception {

		super.testGetTaxonomyVocabularyTaxonomyCategoriesPage();

		_testGetTaxonomyVocabularyTaxonomyCategoriesPageFlatten();
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

	private AssetVocabulary _addAssetVocabulary() throws Exception {
		return AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
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

	private void _testGetTaxonomyVocabularyTaxonomyCategoriesPageFlatten()
		throws Exception {

		AssetVocabulary irrelevantAssetVocabulary = _addAssetVocabulary();

		TaxonomyCategory taxonomyCategory1 =
			_addTaxonomyCategoryWithParentAssetVocabulary(_assetVocabulary);

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
					_assetVocabulary.getVocabularyId(), flatten, null, null,
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
					_assetVocabulary.getVocabularyId(), flatten, null, null,
					null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyCategory1, taxonomyCategory2),
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
								setId(
									Long.valueOf(
										patchParentTaxonomyCategory.getId()));
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
								setId(
									Long.valueOf(
										randomTaxonomyCategory.getId()));
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

	private AssetVocabulary _assetVocabulary;

}