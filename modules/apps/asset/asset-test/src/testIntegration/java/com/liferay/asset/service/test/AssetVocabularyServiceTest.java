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
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class AssetVocabularyServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_locale = LocaleThreadLocal.getSiteDefaultLocale();
	}

	@After
	public void tearDown() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(_locale);
	}

	@Test
	public void testDeleteVocabulary() throws Exception {
		int initialAssetCategoriesCount = searchCount();
		int initialResourcesActionsCount =
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				AssetVocabulary.class.getName());

		AssetVocabulary vocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory category = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId());

		AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary.getVocabularyId(),
			category.getCategoryId());

		Assert.assertEquals(initialAssetCategoriesCount + 2, searchCount());

		AssetVocabularyLocalServiceUtil.deleteVocabulary(
			vocabulary.getVocabularyId());

		Assert.assertEquals(initialAssetCategoriesCount, searchCount());
		Assert.assertEquals(
			initialResourcesActionsCount,
			ResourceActionLocalServiceUtil.getResourceActionsCount(
				AssetVocabulary.class.getName()));
		Assert.assertNull(
			AssetCategoryLocalServiceUtil.fetchAssetCategory(
				category.getCategoryId()));
		Assert.assertNull(
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
				vocabulary.getVocabularyId()));
	}

	@Test
	public void testGetGroupVocabulariesPaginatedWithNoViewableVocabularies()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			List<AssetVocabulary> vocabularies =
				AssetVocabularyServiceUtil.getGroupVocabularies(
					_group.getGroupId(), false, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			Assert.assertTrue(ListUtil.isEmpty(vocabularies));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetGroupVocabulariesPaginatedWithNoViewableVocabulariesDoesNotCreateDefaultVocabulary()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			List<AssetVocabulary> vocabularies =
				AssetVocabularyServiceUtil.getGroupVocabularies(
					_group.getGroupId(), true, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			Assert.assertTrue(ListUtil.isEmpty(vocabularies));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetGroupVocabulariesPaginatedWithNoVocabularies()
		throws Exception {

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				_group.getGroupId(), false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertTrue(ListUtil.isEmpty(vocabularies));
	}

	@Test
	public void testGetGroupVocabulariesPaginatedWithNoVocabulariesCreatesDefaultVocabulary()
		throws Exception {

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				_group.getGroupId(), true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		Assert.assertEquals(vocabularies.toString(), 1, vocabularies.size());
	}

	@Test
	public void testGetGroupVocabulariesWithNoViewableVocabularies()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			List<AssetVocabulary> vocabularies =
				AssetVocabularyServiceUtil.getGroupVocabularies(
					_group.getGroupId(), false);

			Assert.assertTrue(ListUtil.isEmpty(vocabularies));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetGroupVocabulariesWithNoViewableVocabulariesDoesNotCreateDefaultVocabulary()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		AssetVocabularyLocalServiceUtil.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			List<AssetVocabulary> vocabularies =
				AssetVocabularyServiceUtil.getGroupVocabularies(
					_group.getGroupId(), true);

			Assert.assertTrue(ListUtil.isEmpty(vocabularies));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetGroupVocabulariesWithNoVocabularies() throws Exception {
		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				_group.getGroupId(), false);

		Assert.assertTrue(ListUtil.isEmpty(vocabularies));
	}

	@Test
	public void testGetGroupVocabulariesWithNoVocabulariesCreatesDefaultVocabulary()
		throws Exception {

		List<AssetVocabulary> vocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				_group.getGroupId(), true);

		Assert.assertEquals(vocabularies.toString(), 1, vocabularies.size());
	}

	@Test
	public void testLocalizedSiteAddDefaultVocabulary() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				_group.getGroupId());

		Assert.assertEquals(
			PropsValues.ASSET_VOCABULARY_DEFAULT,
			vocabulary.getTitle(LocaleUtil.US, true));
	}

	@Test
	public void testLocalizedSiteAddLocalizedVocabulary() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		String title = RandomTestUtil.randomString();

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.SPAIN, title + "_ES");
		titleMap.put(LocaleUtil.US, title + "_US");

		String description = RandomTestUtil.randomString();

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.SPAIN, description + "_ES");
		descriptionMap.put(LocaleUtil.US, description + "_US");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				StringPool.BLANK, titleMap, descriptionMap, StringPool.BLANK,
				serviceContext);

		Assert.assertEquals(
			titleMap.get(LocaleUtil.SPAIN), vocabulary.getName());
		Assert.assertEquals(
			titleMap.get(LocaleUtil.SPAIN),
			vocabulary.getTitle(LocaleUtil.GERMANY, true));
		Assert.assertEquals(
			titleMap.get(LocaleUtil.SPAIN),
			vocabulary.getTitle(LocaleUtil.SPAIN, true));
		Assert.assertEquals(
			titleMap.get(LocaleUtil.US),
			vocabulary.getTitle(LocaleUtil.US, true));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.SPAIN),
			vocabulary.getDescription(LocaleUtil.GERMANY, true));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.SPAIN),
			vocabulary.getDescription(LocaleUtil.SPAIN, true));
		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.US),
			vocabulary.getDescription(LocaleUtil.US, true));
	}

	@Test
	public void testLocalizedSiteAddVocabulary() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		String title = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				title, serviceContext);

		Assert.assertEquals(title, vocabulary.getTitle(LocaleUtil.US, true));
		Assert.assertEquals(title, vocabulary.getName());
	}

	protected int searchCount() throws Exception {
		Indexer<AssetCategory> indexer = IndexerRegistryUtil.getIndexer(
			AssetCategory.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	@DeleteAfterTestRun
	private Group _group;

	private Locale _locale;

}