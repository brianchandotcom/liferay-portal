/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration;
import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.exception.AssetCategoryLimitException;
import com.liferay.asset.kernel.exception.AssetCategoryNameException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class AssetCategoryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(), "Vocabulary",
			new ServiceContext());
	}

	@Test
	public void testAddAssetCategory() throws Exception {
		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.FRANCE),
			LocaleUtil.SPAIN);

		String expectedAssetCategoryTitle = "Título";

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			expectedAssetCategoryTitle, _assetVocabulary.getVocabularyId(),
			new ServiceContext());

		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			assetCategory.getTitle(LocaleUtil.SPAIN));

		Map<Locale, String> titleMap = assetCategory.getTitleMap();

		Assert.assertTrue(
			"Title map does not contains site default locale",
			titleMap.containsKey(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			titleMap.get(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title map length does not match", 1, titleMap.size());
	}

	@Test
	public void testAddAssetCategoryWithMissingTranslationInSiteDefaultLocale()
		throws Exception {

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.FRANCE),
			LocaleUtil.SPAIN);

		expectedException.expect(AssetCategoryNameException.class);
		expectedException.expectMessage(
			"Category name cannot be null for category 0 and vocabulary " +
				_assetVocabulary.getVocabularyId());

		_assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			Collections.singletonMap(LocaleUtil.FRANCE, "Qualification"),
			Collections.singletonMap(LocaleUtil.FRANCE, "La description"),
			_assetVocabulary.getVocabularyId(), null, new ServiceContext());
	}

	@Test
	public void testAddAssetCategoryWithTranslationInSiteDefaultLocale()
		throws Exception {

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.FRANCE),
			LocaleUtil.SPAIN);

		String expectedAssetCategoryTitle = "Título";

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).put(
			LocaleUtil.SPAIN, expectedAssetCategoryTitle
		).build();

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap,
			HashMapBuilder.put(
				LocaleUtil.FRANCE, "La description"
			).put(
				LocaleUtil.SPAIN, "Descripción"
			).build(),
			_assetVocabulary.getVocabularyId(), null, new ServiceContext());

		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			assetCategory.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title map does not match", titleMap,
			assetCategory.getTitleMap());
	}

	@Test(expected = AssetCategoryLimitException.class)
	public void testAssetCategoryLimitExceeded() throws Exception {
		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.FRANCE),
			LocaleUtil.SPAIN);

		try {
			_configurationProvider.saveCompanyConfiguration(
				AssetCategoriesCompanyConfiguration.class,
				_group.getCompanyId(),
				HashMapDictionaryBuilder.<String, Object>put(
					"maximumNumberOfCategoriesPerVocabulary", 3
				).build());

			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
			_assetCategoryLocalService.addCategory(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				_assetVocabulary.getVocabularyId(), new ServiceContext());
		}
		finally {
			_configurationProvider.deleteCompanyConfiguration(
				AssetCategoriesCompanyConfiguration.class,
				_group.getCompanyId());
		}
	}

	@Test
	public void testGetCategories() throws Exception {
		JournalArticle journalArticle = _addJournalArticleWithAssetCategories(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				_portal.getClassNameId(JournalArticle.class.getName()),
				journalArticle.getResourcePrimKey(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertNotNull(assetCategories);
		Assert.assertEquals(
			assetCategories.toString(), 2, assetCategories.size());
	}

	@Test
	public void testGetCategoriesCount() throws Exception {
		JournalArticle journalArticle = _addJournalArticleWithAssetCategories(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			2,
			_assetCategoryLocalService.getCategoriesCount(
				_portal.getClassNameId(JournalArticle.class.getName()),
				journalArticle.getResourcePrimKey()));
	}

	@Test
	public void testGetCategoriesCountWithoutPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		JournalArticle journalArticle = _addJournalArticleWithAssetCategories(
			serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Assert.assertEquals(
				0,
				_assetCategoryLocalService.getCategoriesCount(
					_portal.getClassNameId(JournalArticle.class.getName()),
					journalArticle.getResourcePrimKey()));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetCategoriesWithoutPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		JournalArticle journalArticle = _addJournalArticleWithAssetCategories(
			serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			List<AssetCategory> assetCategories =
				_assetCategoryLocalService.getCategories(
					_portal.getClassNameId(JournalArticle.class.getName()),
					journalArticle.getResourcePrimKey(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			Assert.assertTrue(ListUtil.isEmpty(assetCategories));
		}
		finally {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testSearch() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			serviceContext);
		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			serviceContext);

		List<AssetCategory> assetCategories = _assetCategoryLocalService.search(
			_group.getGroupId(), null, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertNotNull(assetCategories);

		Assert.assertEquals(
			assetCategories.toString(), 2, assetCategories.size());

		Assert.assertTrue(assetCategories.contains(assetCategory1));
		Assert.assertTrue(assetCategories.contains(assetCategory2));
	}

	@Test
	public void testSearchWithAssetCategoryProperty() throws Exception {
		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		AssetCategoryProperty assetCategoryProperty1 =
			_assetCategoryPropertyLocalService.addCategoryProperty(
				TestPropsValues.getUserId(), assetCategory1.getCategoryId(),
				"key1", "value1");

		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), assetCategory1.getCategoryId(), "key2",
			"value2");

		String[] assetCategoryProperties = {
			StringBundler.concat(
				assetCategoryProperty1.getKey(),
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR,
				assetCategoryProperty1.getValue())
		};

		List<AssetCategory> assetCategories = _assetCategoryLocalService.search(
			_group.getGroupId(), null, assetCategoryProperties,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertNotNull(assetCategories);

		Assert.assertEquals(
			assetCategories.toString(), 1, assetCategories.size());

		Assert.assertTrue(assetCategories.contains(assetCategory1));
	}

	@Test
	public void testSearchWithName() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			serviceContext);

		_assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			serviceContext);

		List<AssetCategory> assetCategories = _assetCategoryLocalService.search(
			_group.getGroupId(), assetCategory1.getName(), null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertNotNull(assetCategories);

		Assert.assertEquals(
			assetCategories.toString(), 1, assetCategories.size());

		Assert.assertTrue(assetCategories.contains(assetCategory1));
	}

	@Test
	public void testSearchWithNameAndMultipleAssetCategoryProperties()
		throws Exception {

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		AssetCategoryProperty assetCategoryProperty1 =
			_assetCategoryPropertyLocalService.addCategoryProperty(
				TestPropsValues.getUserId(), assetCategory.getCategoryId(),
				"key1", "value1");
		AssetCategoryProperty assetCategoryProperty2 =
			_assetCategoryPropertyLocalService.addCategoryProperty(
				TestPropsValues.getUserId(), assetCategory.getCategoryId(),
				"key2", "value2");

		String[] assetCategoryProperties = {
			StringBundler.concat(
				assetCategoryProperty1.getKey(),
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR,
				assetCategoryProperty1.getValue()),
			StringBundler.concat(
				assetCategoryProperty2.getKey(),
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR,
				assetCategoryProperty2.getValue())
		};

		List<AssetCategory> assetCategories = _assetCategoryLocalService.search(
			_group.getGroupId(), assetCategory.getName(),
			assetCategoryProperties, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertNotNull(assetCategories);

		Assert.assertEquals(
			assetCategories.toString(), 1, assetCategories.size());

		Assert.assertTrue(assetCategories.contains(assetCategory));
	}

	@Test
	public void testSearchWithSameAssetCategoryPropertiesInDifferentAssetVocabularies()
		throws Exception {

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), assetCategory1.getCategoryId(), "key1",
			"value1");

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), new ServiceContext());

		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), assetCategory2.getCategoryId(), "key1",
			"value1");

		String[] assetCategoryProperties = {
			StringBundler.concat(
				"key1", AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR,
				"value1")
		};

		List<AssetCategory> assetCategories = _assetCategoryLocalService.search(
			_group.getGroupId(), null, assetCategoryProperties,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertNotNull(assetCategories);

		Assert.assertEquals(
			assetCategories.toString(), 2, assetCategories.size());

		Assert.assertTrue(assetCategories.contains(assetCategory1));
		Assert.assertTrue(assetCategories.contains(assetCategory2));
	}

	@Test
	public void testSearchWithSameNameInDifferentAssetVocabulary0()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String assetCategoryName = RandomTestUtil.randomString();

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), assetCategoryName,
			_assetVocabulary.getVocabularyId(), serviceContext);

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), assetCategoryName,
			assetVocabulary.getVocabularyId(), serviceContext);

		List<AssetCategory> assetCategories = _assetCategoryLocalService.search(
			_group.getGroupId(), assetCategoryName, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertNotNull(assetCategories);

		Assert.assertEquals(
			assetCategories.toString(), 2, assetCategories.size());

		Assert.assertTrue(assetCategories.contains(assetCategory1));
		Assert.assertTrue(assetCategories.contains(assetCategory2));
	}

	@Test
	public void testUpdateAssetCategoryWithMissingTranslationInSiteDefaultLocale()
		throws Exception {

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.FRANCE),
			LocaleUtil.SPAIN);

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "Título",
			_assetVocabulary.getVocabularyId(), new ServiceContext());

		expectedException.expect(AssetCategoryNameException.class);
		expectedException.expectMessage(
			StringBundler.concat(
				"Category name cannot be null for category ",
				assetCategory.getCategoryId(), " and vocabulary ",
				assetCategory.getVocabularyId()));

		_assetCategoryLocalService.updateCategory(
			TestPropsValues.getUserId(), assetCategory.getCategoryId(),
			assetCategory.getParentCategoryId(),
			Collections.singletonMap(LocaleUtil.FRANCE, "Qualification"),
			Collections.singletonMap(LocaleUtil.FRANCE, "La description"),
			assetCategory.getVocabularyId(), null, new ServiceContext());
	}

	@Test
	public void testUpdateAssetCategoryWithTranslationInSiteDefaultLocale()
		throws Exception {

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.FRANCE),
			LocaleUtil.SPAIN);

		String expectedAssetCategoryTitle = "Título";

		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			expectedAssetCategoryTitle, _assetVocabulary.getVocabularyId(),
			new ServiceContext());

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.FRANCE, "Qualification"
		).put(
			LocaleUtil.SPAIN, expectedAssetCategoryTitle
		).build();

		assetCategory = _assetCategoryLocalService.updateCategory(
			TestPropsValues.getUserId(), assetCategory.getCategoryId(),
			assetCategory.getParentCategoryId(), titleMap,
			HashMapBuilder.put(
				LocaleUtil.FRANCE, "La description"
			).put(
				LocaleUtil.SPAIN, "Descripción"
			).build(),
			assetCategory.getVocabularyId(), null, new ServiceContext());

		Assert.assertEquals(
			"Expected title does not match", expectedAssetCategoryTitle,
			assetCategory.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Expected title map does not match", titleMap,
			assetCategory.getTitleMap());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private JournalArticle _addJournalArticleWithAssetCategories(
			ServiceContext serviceContext)
		throws Exception {

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			serviceContext);
		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			serviceContext);

		serviceContext.setAssetCategoryIds(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		return JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

}