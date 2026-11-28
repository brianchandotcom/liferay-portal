/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.friendly.url.constants.FriendlyURLEntryConstants;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.exception.FriendlyURLCategoryException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.exception.FriendlyURLLocalizationUrlTitleException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryTitles;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryPersistence;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LanguageIds;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
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
 * @author Adolfo Pérez
 */
@LanguageIds(
	availableLanguageIds = {"en_US", "es_ES", "pt_BR", "zh_CN"},
	defaultLanguageId = "en_US"
)
@RunWith(Arquillian.class)
public class FriendlyURLEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		_assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), _assetVocabulary.getVocabularyId());

		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() {
		_friendlyURLEntryLocalService.deleteGroupFriendlyURLEntries(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class));
		_friendlyURLEntryLocalService.deleteGroupFriendlyURLEntries(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testAddFriendlyURLEntryAllowsSameUrlTitleForDifferentParentClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());
		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, 1L, TestPropsValues.getUserId(),
			defaultLanguageId,
			Collections.singletonMap(defaultLanguageId, urlTitle),
			_getServiceContext());

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId, 2L, _user.getUserId(),
				defaultLanguageId,
				Collections.singletonMap(defaultLanguageId, urlTitle),
				_getServiceContext());

		Assert.assertEquals(urlTitle, friendlyURLEntry.getUrlTitle());
		Assert.assertEquals(2L, friendlyURLEntry.getParentClassPK());
	}

	@Test
	public void testAddFriendlyURLEntryKeepsOldLocalizedValues()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			Collections.singletonMap(
				_language.getLanguageId(LocaleUtil.US), "url-title-en"),
			_getServiceContext());

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			Collections.singletonMap(
				_language.getLanguageId(LocaleUtil.CHINA), "url-title-zh"),
			_getServiceContext());

		FriendlyURLEntry finalFriendlyURL =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
				"url-title-en", _getServiceContext());

		Assert.assertEquals(
			"url-title-en",
			finalFriendlyURL.getUrlTitle(
				_language.getLanguageId(LocaleUtil.US)));
		Assert.assertEquals(
			"url-title-zh",
			finalFriendlyURL.getUrlTitle(
				_language.getLanguageId(LocaleUtil.CHINA)));
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	@TestInfo("LPD-90910")
	public void testAddFriendlyURLEntryRejectsDuplicateUrlTitleForSameParentClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());
		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, 1L, TestPropsValues.getUserId(),
			defaultLanguageId,
			Collections.singletonMap(defaultLanguageId, urlTitle),
			_getServiceContext());

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, 1L, _user.getUserId(),
			defaultLanguageId,
			Collections.singletonMap(defaultLanguageId, urlTitle),
			_getServiceContext());
	}

	@Test
	public void testAddFriendlyURLEntryReusesOwnedUrlTitles() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			"existing-url-title-2", _getServiceContext());

		FriendlyURLEntry finalFriendlyURL =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
				urlTitle, _getServiceContext());

		Assert.assertEquals(urlTitle, finalFriendlyURL.getUrlTitle());
	}

	@Test
	public void testAddFriendlyURLEntryWithAssetCategories() throws Exception {
		ServiceContext serviceContext = _getServiceContext();

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "cat1",
			assetVocabulary.getVocabularyId(), serviceContext);
		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "cat2",
			assetVocabulary.getVocabularyId(), serviceContext);

		serviceContext.setAttribute(
			"friendlyURLAssetCategoryIds",
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				TestPropsValues.getUserId(),
				Collections.singletonMap(
					_language.getLanguageId(LocaleUtil.US), "url-title-en"),
				serviceContext);

		Assert.assertEquals(
			"cat1/cat2/url-title-en",
			friendlyURLEntry.getCategorizedUrlTitle(
				_language.getLanguageId(LocaleUtil.US)));
	}

	@Test
	public void testAddFriendlyURLEntryWithLocalizedAssetCategories()
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		_addLocalizedAssetCategories(serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				TestPropsValues.getUserId(),
				HashMapBuilder.put(
					_language.getLanguageId(LocaleUtil.US), "url-title-en"
				).put(
					_language.getLanguageId(new Locale("es", "ES")),
					"url-title-es"
				).build(),
				serviceContext);

		Assert.assertEquals(
			"cat1-en/cat2-en/url-title-en",
			friendlyURLEntry.getCategorizedUrlTitle(
				_language.getLanguageId(LocaleUtil.US)));
		Assert.assertEquals(
			"cat1-es/cat2-es/url-title-es",
			friendlyURLEntry.getCategorizedUrlTitle(
				_language.getLanguageId(new Locale("es", "ES"))));
	}

	@Test(expected = FriendlyURLCategoryException.class)
	public void testAddFriendlyURLEntryWithSlashAndAssetCategories()
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "cat1",
			assetVocabulary.getVocabularyId(), serviceContext);
		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "cat2",
			assetVocabulary.getVocabularyId(), serviceContext);

		serviceContext.setAttribute(
			"friendlyURLAssetCategoryIds",
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class),
			TestPropsValues.getUserId(),
			Collections.singletonMap(
				_language.getLanguageId(LocaleUtil.US), "url/title/en"),
			serviceContext);
	}

	@Test
	public void testAddFriendlyURLEntryWithStrictAdd() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);
		long classPK = RandomTestUtil.nextLong();
		String languageId = _language.getLanguageId(LocaleUtil.US);
		String urlTitle = _getRandomURLTitle();

		ServiceContext serviceContext = _getServiceContext();

		serviceContext.setStrictAdd(true);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId,
				FriendlyURLEntryConstants.
					FRIENDLY_URL_ENTRY_PARENT_CLASS_PK_DEFAULT,
				classPK, languageId,
				Collections.singletonMap(languageId, urlTitle), serviceContext);

		Assert.assertEquals(
			friendlyURLEntry,
			_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
				classNameId, classPK));

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.getFriendlyURLEntryLocalization(
				friendlyURLEntry.getFriendlyURLEntryId(), languageId);

		Assert.assertEquals(
			urlTitle, friendlyURLEntryLocalization.getUrlTitle());
	}

	@Test
	public void testAddUnlocalizedFriendlyURLEntryWithLocalizedAssetCategories()
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		_addLocalizedAssetCategories(serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				TestPropsValues.getUserId(),
				HashMapBuilder.put(
					_language.getLanguageId(LocaleUtil.US), "url-title-en"
				).build(),
				serviceContext);

		Assert.assertEquals(
			"cat1-en/cat2-en/url-title-en",
			friendlyURLEntry.getCategorizedUrlTitle(
				_language.getLanguageId(LocaleUtil.US)));
		Assert.assertEquals(
			"cat1-es/cat2-es/url-title-en",
			friendlyURLEntry.getCategorizedUrlTitle(
				_language.getLanguageId(new Locale("es", "ES"))));
	}

	@Test
	public void testDeleteFriendlyURLLocalizationEntry() throws Exception {
		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				TestPropsValues.getUserId(),
				HashMapBuilder.put(
					_language.getLanguageId(LocaleUtil.CHINA), "url-title-zh"
				).put(
					_language.getLanguageId(LocaleUtil.US), "url-title-en"
				).build(),
				_getServiceContext());

		_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
			friendlyURLEntry.getFriendlyURLEntryId(),
			_language.getLanguageId(LocaleUtil.CHINA));

		Assert.assertNotNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId()));

		_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
			friendlyURLEntry.getFriendlyURLEntryId(),
			_language.getLanguageId(LocaleUtil.US));

		Assert.assertNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId()));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testFetchFriendlyURLEntryByParentClassPKDoesNotMatchAcrossParents()
		throws Exception {

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(AssetCategory.class),
			_assetVocabulary.getVocabularyId(), _assetCategory.getCategoryId(),
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
			Collections.singletonMap(
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()), urlTitle),
			_getServiceContext());

		Assert.assertNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class),
				FriendlyURLEntryConstants.
					FRIENDLY_URL_ENTRY_PARENT_CLASS_PK_DEFAULT,
				urlTitle));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testFetchFriendlyURLEntryByParentClassPKReturnsEntry()
		throws Exception {

		String urlTitle = _getRandomURLTitle();

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class),
				_assetVocabulary.getVocabularyId(),
				_assetCategory.getCategoryId(),
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
				Collections.singletonMap(
					LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
					urlTitle),
				_getServiceContext());

		Assert.assertEquals(
			friendlyURLEntry,
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class),
				_assetVocabulary.getVocabularyId(), urlTitle));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testFetchFriendlyURLEntryLocalizationByLanguageIdAndParentClassPKDoesNotMatchAcrossLanguages()
		throws Exception {

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(AssetCategory.class),
			_assetVocabulary.getVocabularyId(), _assetCategory.getCategoryId(),
			_language.getLanguageId(LocaleUtil.CHINA),
			Collections.singletonMap(
				_language.getLanguageId(LocaleUtil.CHINA), urlTitle),
			_getServiceContext());

		Assert.assertNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class),
				FriendlyURLEntryConstants.
					FRIENDLY_URL_ENTRY_PARENT_CLASS_PK_DEFAULT,
				_language.getLanguageId(LocaleUtil.US), urlTitle));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testFetchFriendlyURLEntryLocalizationByLanguageIdAndParentClassPKReturnsLocalization()
		throws Exception {

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(AssetCategory.class),
			_assetVocabulary.getVocabularyId(), _assetCategory.getCategoryId(),
			_language.getLanguageId(LocaleUtil.CHINA),
			Collections.singletonMap(
				_language.getLanguageId(LocaleUtil.CHINA), urlTitle),
			_getServiceContext());

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class),
				_assetVocabulary.getVocabularyId(),
				_language.getLanguageId(LocaleUtil.CHINA), urlTitle);

		Assert.assertEquals(
			_assetCategory.getCategoryId(),
			friendlyURLEntryLocalization.getClassPK());
	}

	@Test
	@TestInfo("LPD-90910")
	public void testFetchFriendlyURLEntryLocalizationByParentClassPKDoesNotMatchAcrossParents()
		throws Exception {

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(AssetCategory.class),
			_assetVocabulary.getVocabularyId(), _assetCategory.getCategoryId(),
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
			Collections.singletonMap(
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()), urlTitle),
			_getServiceContext());

		Assert.assertNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class),
				FriendlyURLEntryConstants.
					FRIENDLY_URL_ENTRY_PARENT_CLASS_PK_DEFAULT,
				urlTitle));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testFetchFriendlyURLEntryLocalizationByParentClassPKReturnsLocalization()
		throws Exception {

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(AssetCategory.class),
			_assetVocabulary.getVocabularyId(), _assetCategory.getCategoryId(),
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
			Collections.singletonMap(
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()), urlTitle),
			_getServiceContext());

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(AssetCategory.class),
				_assetVocabulary.getVocabularyId(), urlTitle);

		Assert.assertEquals(
			_assetCategory.getCategoryId(),
			friendlyURLEntryLocalization.getClassPK());
	}

	@Test
	public void testGetMainFriendlyURLEntryTitles() throws Exception {
		_testGetMainFriendlyURLEntryTitles();
	}

	@Test
	public void testGetMainFriendlyURLEntryTitlesWithCTCollection()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);
		long classPK = RandomTestUtil.randomLong();

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId, classPK, "en_US",
				HashMapBuilder.put(
					"en_US", "url-title-en"
				).put(
					"es_ES", "url-title-es"
				).build(),
				_getServiceContext());

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			friendlyURLEntry =
				_friendlyURLEntryLocalService.getFriendlyURLEntry(
					friendlyURLEntry.getFriendlyURLEntryId());

			friendlyURLEntry.setDefaultLanguageId("es_ES");

			_friendlyURLEntryLocalService.updateFriendlyURLEntry(
				friendlyURLEntry);

			Map<Long, FriendlyURLEntryTitles> friendlyURLEntryTitlesMap =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntryTitles(
					classNameId, new long[] {classPK});

			FriendlyURLEntryTitles friendlyURLEntryTitles =
				friendlyURLEntryTitlesMap.get(classPK);

			Assert.assertEquals(
				"url-title-es", friendlyURLEntryTitles.getUrlTitle("pt_BR"));
		}

		Map<Long, FriendlyURLEntryTitles> friendlyURLEntryTitlesMap =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntryTitles(
				classNameId, new long[] {classPK});

		Assert.assertEquals(
			Collections.singleton(classPK), friendlyURLEntryTitlesMap.keySet());

		FriendlyURLEntryTitles friendlyURLEntryTitles =
			friendlyURLEntryTitlesMap.get(classPK);

		Assert.assertEquals(
			HashMapBuilder.put(
				"en_US", "url-title-en"
			).put(
				"es_ES", "url-title-es"
			).build(),
			friendlyURLEntryTitles.getLanguageIdToUrlTitleMap());
		Assert.assertEquals(
			"url-title-en", friendlyURLEntryTitles.getUrlTitle("pt_BR"));

		_assertFriendlyURLEntryTitles(
			classNameId, classPK, friendlyURLEntryTitles);
	}

	@Test
	public void testGetMainFriendlyURLEntryTitlesWithDatabaseInMaxParameters()
		throws Exception {

		int friendlyURLEntryDatabaseInMaxParameters =
			ReflectionTestUtil.getAndSetFieldValue(
				_friendlyURLEntryPersistence, "databaseInMaxParameters", 2);
		int friendlyURLEntryLocalizationDatabaseInMaxParameters =
			ReflectionTestUtil.getAndSetFieldValue(
				_friendlyURLEntryLocalizationPersistence,
				"databaseInMaxParameters", 2);
		int friendlyURLEntryMappingDatabaseInMaxParameters =
			ReflectionTestUtil.getAndSetFieldValue(
				_friendlyURLEntryMappingPersistence, "databaseInMaxParameters",
				2);

		try {
			_testGetMainFriendlyURLEntryTitles();
		}
		finally {
			ReflectionTestUtil.getAndSetFieldValue(
				_friendlyURLEntryLocalizationPersistence,
				"databaseInMaxParameters",
				friendlyURLEntryLocalizationDatabaseInMaxParameters);
			ReflectionTestUtil.getAndSetFieldValue(
				_friendlyURLEntryMappingPersistence, "databaseInMaxParameters",
				friendlyURLEntryMappingDatabaseInMaxParameters);
			ReflectionTestUtil.getAndSetFieldValue(
				_friendlyURLEntryPersistence, "databaseInMaxParameters",
				friendlyURLEntryDatabaseInMaxParameters);
		}
	}

	@Test
	public void testGetMainFriendlyURLEntryTitlesWithFinderCache()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);
		long classPK1 = RandomTestUtil.randomLong();
		ServiceContext serviceContext = _getServiceContext();

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				_group.getGroupId(), classNameId, classPK1, "en_US",
				Collections.singletonMap("en_US", "url-title-1-en"),
				serviceContext);

		long classPK2 = RandomTestUtil.randomLong();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, classPK2, "en_US",
			Collections.singletonMap("en_US", RandomTestUtil.randomString()),
			serviceContext);

		long classPK3 = RandomTestUtil.randomLong();

		long[] classPKs = {classPK1, classPK2, classPK3};

		Map<Long, FriendlyURLEntryTitles> friendlyURLEntryTitlesMap =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntryTitles(
				classNameId, classPKs);

		Assert.assertEquals(
			SetUtil.fromArray(new long[] {classPK1, classPK2}),
			friendlyURLEntryTitlesMap.keySet());

		FriendlyURLEntryTitles friendlyURLEntryTitles =
			friendlyURLEntryTitlesMap.get(classPK1);

		Assert.assertEquals(
			"url-title-1-en", friendlyURLEntryTitles.getUrlTitle("en_US"));

		_friendlyURLEntryLocalService.updateFriendlyURLEntryLocalization(
			friendlyURLEntry, "en_US", "url-title-3-en");

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, classPK2, "en_US",
			Collections.singletonMap("en_US", "url-title-4-en"),
			serviceContext);
		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, classPK3, "en_US",
			Collections.singletonMap("en_US", "url-title-5-en"),
			serviceContext);

		friendlyURLEntryTitlesMap =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntryTitles(
				classNameId, classPKs);

		Assert.assertEquals(
			SetUtil.fromArray(classPKs), friendlyURLEntryTitlesMap.keySet());

		friendlyURLEntryTitles = friendlyURLEntryTitlesMap.get(classPK1);

		Assert.assertEquals(
			"url-title-3-en", friendlyURLEntryTitles.getUrlTitle("en_US"));

		_assertFriendlyURLEntryTitles(
			classNameId, classPK1, friendlyURLEntryTitles);

		friendlyURLEntryTitles = friendlyURLEntryTitlesMap.get(classPK2);

		Assert.assertEquals(
			"url-title-4-en", friendlyURLEntryTitles.getUrlTitle("en_US"));

		_assertFriendlyURLEntryTitles(
			classNameId, classPK2, friendlyURLEntryTitles);

		friendlyURLEntryTitles = friendlyURLEntryTitlesMap.get(classPK3);

		Assert.assertEquals(
			"url-title-5-en", friendlyURLEntryTitles.getUrlTitle("en_US"));

		_assertFriendlyURLEntryTitles(
			classNameId, classPK3, friendlyURLEntryTitles);
	}

	@Test
	@TestInfo("LPD-90910")
	public void testGetUniqueUrlTitleMapGeneratesUniqueTitlesPerLocale()
		throws Exception {

		String urlTitle1 = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class),
			TestPropsValues.getUserId(), urlTitle1, _getServiceContext());

		String urlTitle2 = _getRandomURLTitle();

		Map<String, String> uniqueUrlTitleMap =
			_friendlyURLEntryLocalService.getUniqueUrlTitleMap(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				FriendlyURLEntryConstants.
					FRIENDLY_URL_ENTRY_PARENT_CLASS_PK_DEFAULT,
				_user.getUserId(),
				HashMapBuilder.put(
					LocaleUtil.CHINA, urlTitle1
				).put(
					LocaleUtil.US, urlTitle2
				).build());

		String urlTitle3 = uniqueUrlTitleMap.get(
			_language.getLanguageId(LocaleUtil.CHINA));

		Assert.assertTrue(urlTitle3, urlTitle3.endsWith("-1"));

		Assert.assertEquals(
			urlTitle2,
			uniqueUrlTitleMap.get(_language.getLanguageId(LocaleUtil.US)));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testGetUniqueUrlTitleMapGeneratesUniqueTitlesWhenDuplicated()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			AssetCategory.class);
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());
		String urlTitle1 = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId,
			_assetVocabulary.getVocabularyId(), _assetCategory.getCategoryId(),
			defaultLanguageId,
			Collections.singletonMap(defaultLanguageId, urlTitle1),
			_getServiceContext());

		Map<String, String> uniqueUrlTitleMap =
			_friendlyURLEntryLocalService.getUniqueUrlTitleMap(
				_group.getGroupId(), classNameId,
				_assetVocabulary.getVocabularyId(),
				_assetCategory.getCategoryId() + 1,
				Collections.singletonMap(LocaleUtil.US, urlTitle1));

		String urlTitle2 = uniqueUrlTitleMap.get(
			LocaleUtil.toLanguageId(LocaleUtil.US));

		Assert.assertTrue(urlTitle2, urlTitle2.endsWith("-1"));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testGetUniqueUrlTitleMapSkipsBlankTitles() throws Exception {
		String urlTitle = _getRandomURLTitle();

		Map<String, String> urlTitleMap =
			_friendlyURLEntryLocalService.getUniqueUrlTitleMap(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				FriendlyURLEntryConstants.
					FRIENDLY_URL_ENTRY_PARENT_CLASS_PK_DEFAULT,
				TestPropsValues.getUserId(),
				HashMapBuilder.put(
					LocaleUtil.CHINA, StringPool.BLANK
				).put(
					LocaleUtil.US, urlTitle
				).build());

		Assert.assertEquals(urlTitleMap.toString(), 1, urlTitleMap.size());
		Assert.assertEquals(
			urlTitle, urlTitleMap.get(_language.getLanguageId(LocaleUtil.US)));
	}

	@Test
	public void testGetUniqueUrlTitleNormalizesUrlTitle() throws Exception {
		String urlTitle = "url title with spaces";

		Assert.assertEquals(
			"url-title-with-spaces",
			_friendlyURLEntryLocalService.getUniqueUrlTitle(
				_group.getGroupId(),
				_classNameLocalService.getClassNameId(User.class),
				TestPropsValues.getUserId(), urlTitle));
	}

	@Test
	public void testGetUniqueUrlTitleResolvesConflicts() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		Assert.assertEquals(
			"existing-url-title-1",
			_friendlyURLEntryLocalService.getUniqueUrlTitle(
				_group.getGroupId(), classNameId, _user.getUserId(), urlTitle));
	}

	@Test
	public void testGetUniqueUrlTitleResolvesConflictsAcrossLanguages()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			HashMapBuilder.put(
				_language.getLanguageId(LocaleUtil.BRAZIL), urlTitle
			).build(),
			_getServiceContext());

		Assert.assertEquals(
			"existing-url-title-1",
			_friendlyURLEntryLocalService.getUniqueUrlTitle(
				_group.getGroupId(), classNameId, _user.getUserId(), urlTitle));
	}

	@Test
	@TestInfo("LPD-90910")
	public void testGetUniqueUrlTitleReturnsSameTitleAcrossDifferentParentClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			AssetCategory.class);
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());
		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId,
			_assetVocabulary.getVocabularyId(), _assetCategory.getCategoryId(),
			defaultLanguageId,
			Collections.singletonMap(defaultLanguageId, urlTitle),
			_getServiceContext());

		String uniqueUrlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			_group.getGroupId(), classNameId,
			FriendlyURLEntryConstants.
				FRIENDLY_URL_ENTRY_PARENT_CLASS_PK_DEFAULT,
			_assetCategory.getCategoryId(), urlTitle);

		Assert.assertEquals(urlTitle, uniqueUrlTitle);
	}

	@Test
	public void testGetUniqueUrlTitleReusesOwnedUrlTitles() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);
		String urlTitle = "existing-url-title";

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		String uniqueUrlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle);

		Assert.assertEquals(urlTitle, uniqueUrlTitle);
	}

	@Test
	public void testGetUniqueUrlTitleShortensToMaxLength() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength + 1);

		String uniqueUrlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle);

		Assert.assertEquals(maxLength, uniqueUrlTitle.length());
	}

	@Test
	public void testGetUniqueUrlTitleWithNonasciiCharsShortensToMaxLength()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength - 1);

		urlTitle += "あ";

		String uniqueUrlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle);

		Assert.assertEquals(maxLength - 1, uniqueUrlTitle.length());
	}

	@Test
	public void testHasMainFriendlyURLEntry() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		Assert.assertFalse(
			_friendlyURLEntryLocalService.hasMainFriendlyURLEntry(
				classNameId, TestPropsValues.getUserId()));

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			_getRandomURLTitle(), _getServiceContext());

		Assert.assertTrue(
			_friendlyURLEntryLocalService.hasMainFriendlyURLEntry(
				classNameId, TestPropsValues.getUserId()));
	}

	@Test
	public void testValidateAllowsDuplicatesInDifferentLanguagesForDifferentClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			HashMapBuilder.put(
				_language.getLanguageId(LocaleUtil.getDefault()), urlTitle
			).build(),
			_getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, _user.getUserId(),
			_language.getLanguageId(LocaleUtil.BRAZIL), urlTitle);
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateDuplicateLocalizedUrlTitleForDifferentClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			HashMapBuilder.put(
				_language.getLanguageId(LocaleUtil.getDefault()), urlTitle
			).build(),
			_getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, _user.getUserId(),
			_language.getLanguageId(LocaleUtil.getDefault()), urlTitle);
	}

	@Test
	public void testValidateDuplicateLocalizedUrlTitleForSameClassPK()
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			HashMapBuilder.put(
				_language.getLanguageId(LocaleUtil.getDefault()), urlTitle
			).build(),
			_getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			_language.getLanguageId(LocaleUtil.BRAZIL), urlTitle);
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateDuplicateUrlTitle() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, urlTitle);
	}

	@Test(expected = DuplicateFriendlyURLEntryException.class)
	public void testValidateUrlTitleNotOwnedByModel() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, _user.getUserId(), urlTitle);
	}

	@Test
	public void testValidateUrlTitleOwnedByModel() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		String urlTitle = _getRandomURLTitle();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle, _getServiceContext());

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, TestPropsValues.getUserId(),
			urlTitle);
	}

	@Test(expected = FriendlyURLLengthException.class)
	public void testValidateUrlTitleWithInvalidLength() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(User.class);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String urlTitle = StringUtil.randomString(maxLength + 1);

		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(), classNameId, urlTitle);
	}

	@Test(
		expected = FriendlyURLLocalizationUrlTitleException.MustNotHaveTrailingSlash.class
	)
	public void testValidateUrlTitleWithInvalidSlash() throws Exception {
		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class),
			TestPropsValues.getUserId(), "test/");
	}

	@Test
	public void testValidateUrlTitleWithMaxLength() throws Exception {
		_friendlyURLEntryLocalService.validate(
			_group.getGroupId(),
			_classNameLocalService.getClassNameId(User.class),
			_getRandomURLTitle());
	}

	private void _addLocalizedAssetCategories(ServiceContext serviceContext)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
			HashMapBuilder.put(
				LocaleUtil.US, "cat1-en"
			).put(
				new Locale("es", "ES"), "cat1-es"
			).build(),
			new HashMap<>(), assetVocabulary.getVocabularyId(), false, null,
			serviceContext);
		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
			HashMapBuilder.put(
				LocaleUtil.US, "cat2-en"
			).put(
				new Locale("es", "ES"), "cat2-es"
			).build(),
			new HashMap<>(), assetVocabulary.getVocabularyId(), false, null,
			serviceContext);

		serviceContext.setAttribute(
			"friendlyURLAssetCategoryIds",
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});
	}

	private void _assertFriendlyURLEntryTitles(
		long classNameId, long classPK,
		FriendlyURLEntryTitles friendlyURLEntryTitles) {

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
				classNameId, classPK);

		Assert.assertEquals(
			friendlyURLEntry.getLanguageIdToUrlTitleMap(),
			friendlyURLEntryTitles.getLanguageIdToUrlTitleMap());

		for (String languageId :
				new String[] {"en_US", "es_ES", "pt_BR", "zh_CN"}) {

			Assert.assertEquals(
				friendlyURLEntry.getUrlTitle(languageId),
				friendlyURLEntryTitles.getUrlTitle(languageId));
		}
	}

	private String _getRandomURLTitle() {
		return StringUtil.toLowerCase(
			StringUtil.randomString(
				ModelHintsUtil.getMaxLength(
					FriendlyURLEntryLocalization.class.getName(), "urlTitle")));
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	private void _testGetMainFriendlyURLEntryTitles() throws Exception {
		long classNameId1 = _classNameLocalService.getClassNameId(User.class);
		long classPK1 = RandomTestUtil.randomLong();
		ServiceContext serviceContext = _getServiceContext();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId1, classPK1, "es_ES",
			HashMapBuilder.put(
				"en_US", "url-title-1-en"
			).put(
				"es_ES", "url-title-1-es"
			).build(),
			serviceContext);
		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId1, classPK1, "es_ES",
			Collections.singletonMap("en_US", "url-title-2-en"),
			serviceContext);

		long classPK2 = RandomTestUtil.randomLong();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId1, classPK2, "zh_CN",
			HashMapBuilder.put(
				"pt_BR", RandomTestUtil.randomString()
			).put(
				"zh_CN", RandomTestUtil.randomString()
			).build(),
			serviceContext);

		long classPK3 = RandomTestUtil.randomLong();

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId1, classPK3, "en_US",
			Collections.singletonMap("en_US", StringPool.BLANK),
			serviceContext);

		long classNameId2 = _classNameLocalService.getClassNameId(Group.class);

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			_group.getGroupId(), classNameId2, classPK1, "en_US",
			Collections.singletonMap("en_US", "url-title-4-en"),
			serviceContext);

		_friendlyURLEntryPersistence.clearCache();

		try {
			Map<Long, FriendlyURLEntryTitles> friendlyURLEntryTitlesMap =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntryTitles(
					classNameId1,
					new long[] {
						classPK1, classPK2, classPK3,
						RandomTestUtil.randomLong()
					});

			Assert.assertEquals(
				SetUtil.fromArray(new long[] {classPK1, classPK2, classPK3}),
				friendlyURLEntryTitlesMap.keySet());

			FriendlyURLEntryTitles friendlyURLEntryTitles =
				friendlyURLEntryTitlesMap.get(classPK1);

			Assert.assertEquals(
				HashMapBuilder.put(
					"en_US", "url-title-2-en"
				).put(
					"es_ES", "url-title-1-es"
				).build(),
				friendlyURLEntryTitles.getLanguageIdToUrlTitleMap());
			Assert.assertEquals(
				"url-title-1-es", friendlyURLEntryTitles.getUrlTitle("pt_BR"));

			_assertFriendlyURLEntryTitles(
				classNameId1, classPK1, friendlyURLEntryTitles);

			_assertFriendlyURLEntryTitles(
				classNameId1, classPK2,
				friendlyURLEntryTitlesMap.get(classPK2));

			friendlyURLEntryTitles = friendlyURLEntryTitlesMap.get(classPK3);

			Assert.assertEquals(
				Collections.emptyMap(),
				friendlyURLEntryTitles.getLanguageIdToUrlTitleMap());
			Assert.assertEquals(
				StringPool.BLANK, friendlyURLEntryTitles.getUrlTitle("en_US"));

			_assertFriendlyURLEntryTitles(
				classNameId1, classPK3, friendlyURLEntryTitles);

			friendlyURLEntryTitlesMap =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntryTitles(
					classNameId2, new long[] {classPK1, classPK2, classPK3});

			Assert.assertEquals(
				Collections.singleton(classPK1),
				friendlyURLEntryTitlesMap.keySet());

			friendlyURLEntryTitles = friendlyURLEntryTitlesMap.get(classPK1);

			Assert.assertEquals(
				Collections.singletonMap("en_US", "url-title-4-en"),
				friendlyURLEntryTitles.getLanguageIdToUrlTitleMap());

			_assertFriendlyURLEntryTitles(
				classNameId2, classPK1, friendlyURLEntryTitles);
		}
		finally {
			_friendlyURLEntryLocalService.deleteGroupFriendlyURLEntries(
				_group.getGroupId(), classNameId2);
		}
	}

	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

	@Inject
	private FriendlyURLEntryMappingPersistence
		_friendlyURLEntryMappingPersistence;

	@Inject
	private FriendlyURLEntryPersistence _friendlyURLEntryPersistence;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Language _language;

	@DeleteAfterTestRun
	private User _user;

}