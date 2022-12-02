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

package com.liferay.asset.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.AssetCategoryNameException;
import com.liferay.asset.kernel.exception.DuplicateCategoryException;
import com.liferay.asset.kernel.exception.DuplicateCategoryExternalReferenceCodeException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class AssetCategoryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddCategoryWithExternalReferenceCode() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		String externalReferenceCode = StringUtil.randomString();
		Locale locale = PortalUtil.getSiteDefaultLocale(_group.getGroupId());

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			HashMapBuilder.put(
				locale, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				locale, StringPool.BLANK
			).build(),
			assetVocabulary.getVocabularyId(), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			externalReferenceCode, assetCategory.getExternalReferenceCode());

		assetCategory =
			AssetCategoryLocalServiceUtil.
				getAssetCategoryByExternalReferenceCode(
					externalReferenceCode, _group.getGroupId());

		Assert.assertEquals(
			externalReferenceCode, assetCategory.getExternalReferenceCode());
	}

	@Test
	public void testAddCategoryWithoutExternalReferenceCode() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		String externalReferenceCode =
			assetCategory1.getExternalReferenceCode();

		Assert.assertEquals(assetCategory1.getUuid(), externalReferenceCode);

		AssetCategory assetCategory2 =
			AssetCategoryLocalServiceUtil.
				getAssetCategoryByExternalReferenceCode(
					externalReferenceCode, _group.getGroupId());

		Assert.assertEquals(assetCategory1, assetCategory2);
	}

	@Test(expected = DuplicateCategoryException.class)
	public void testCannotAddCategoryWithDuplicateName() throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), titleMap, null, null,
				serviceContext);

		String assetCategoryName = RandomTestUtil.randomString();

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), assetCategoryName,
			assetVocabulary.getVocabularyId(), serviceContext);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), assetCategoryName,
			assetVocabulary.getVocabularyId(), serviceContext);
	}

	@Test(expected = AssetCategoryNameException.class)
	public void testCannotAddCategoryWithEmptyName() throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), titleMap, null, null,
				serviceContext);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), StringPool.BLANK,
			assetVocabulary.getVocabularyId(), serviceContext);
	}

	@Test(expected = DuplicateCategoryException.class)
	public void testCannotAddDuplicatedCategoryWithLongName() throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), titleMap, null, null,
				serviceContext);

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			AssetCategory.class.getName(), "name");

		String assetCategoryName = RandomTestUtil.randomString(nameMaxLength);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			assetCategoryName + RandomTestUtil.randomString(10),
			assetVocabulary.getVocabularyId(), serviceContext);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			assetCategoryName + RandomTestUtil.randomString(10),
			assetVocabulary.getVocabularyId(), serviceContext);
	}

	@Test
	public void testCategoryWithLongNameIsTrimmed() throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), titleMap, null, null,
				serviceContext);

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			AssetCategory.class.getName(), "name");

		String assetCategoryName = RandomTestUtil.randomString(nameMaxLength);

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			assetCategoryName + RandomTestUtil.randomString(10),
			assetVocabulary.getVocabularyId(), serviceContext);

		Assert.assertEquals(assetCategoryName, assetCategory.getName());
	}

	@Test
	public void testDeleteCategory() throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), titleMap, null, null,
				serviceContext);

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			serviceContext);

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		_organization = _organizationLocalService.addOrganization(
			TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(),
			OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
			RandomTestUtil.randomString(), true, serviceContext);

		TestAssetIndexer testAssetIndexer = new TestAssetIndexer();

		testAssetIndexer.setExpectedValues(
			Organization.class.getName(), _organization.getOrganizationId());

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				Indexer.class, testAssetIndexer,
				MapUtil.singletonDictionary(
					"service.ranking", Integer.MAX_VALUE));

		try {
			_assetCategoryLocalService.deleteCategory(assetCategory, true);
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test(expected = DuplicateCategoryExternalReferenceCodeException.class)
	public void testDuplicateCategoryExternalReferenceCode() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		String externalReferenceCode = StringUtil.randomString();
		Locale locale = PortalUtil.getSiteDefaultLocale(_group.getGroupId());

		AssetCategoryLocalServiceUtil.addCategory(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			HashMapBuilder.put(
				locale, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				locale, StringPool.BLANK
			).build(),
			assetVocabulary.getVocabularyId(), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		AssetCategoryLocalServiceUtil.addCategory(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			HashMapBuilder.put(
				locale, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				locale, StringPool.BLANK
			).build(),
			assetVocabulary.getVocabularyId(), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private OrganizationLocalService _organizationLocalService;

}