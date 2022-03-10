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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.web.internal.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.permission.ResourceActionsImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class BlogsEntryContentDashboardItemTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageResources languageResources = new LanguageResources();

		languageResources.setConfig(StringPool.BLANK);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		ResourceActionsUtil resourceActionsUtil = new ResourceActionsUtil();

		resourceActionsUtil.setResourceActions(new ResourceActionsImpl());
	}

	@Test
	public void testGetAssetCategories() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				Collections.singletonList(assetCategory), null, blogsEntry,
				null, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetCategory),
			blogsEntryContentDashboardItem.getAssetCategories());
	}

	@Test
	public void testGetAssetCategoriesByAssetVocabulary() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				Collections.singletonList(assetCategory), null, blogsEntry,
				null, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetCategory),
			blogsEntryContentDashboardItem.getAssetCategories(
				assetCategory.getVocabularyId()));
	}

	@Test
	public void testGetAssetCategoriesByAssetVocabularyWithEmptyAssetCategories() {
		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, null, null, null, null);

		Assert.assertEquals(
			Collections.emptyList(),
			blogsEntryContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetCategoriesWithNoAssetCategoriesInAssetVocabulary() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);
		BlogsEntry blogsEntry = _getBlogsEntry();

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				Collections.singletonList(assetCategory), null, blogsEntry,
				null, null, null, null, null);

		Assert.assertEquals(
			Collections.emptyList(),
			blogsEntryContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTags() {
		AssetTag assetTag = Mockito.mock(AssetTag.class);

		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, Collections.singletonList(assetTag), blogsEntry, null,
				null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetTag),
			blogsEntryContentDashboardItem.getAssetTags());
	}

	@Test
	public void testGetDescription() {
		BlogsEntry blogsEntry = _getBlogsEntry();

		Mockito.when(
			blogsEntry.getSubtitle()
		).thenReturn(
			"description"
		);

		InfoItemFieldValuesProvider<BlogsEntry> infoItemFieldValuesProvider =
			Mockito.mock(InfoItemFieldValuesProvider.class);

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, null, infoItemFieldValuesProvider,
				null, null);

		Assert.assertEquals(
			"description",
			blogsEntryContentDashboardItem.getDescription(LocaleUtil.US));
	}

	@Test
	public void testGetModifiedDate() {
		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, null, null, null, null);

		Assert.assertEquals(
			blogsEntry.getModifiedDate(),
			blogsEntryContentDashboardItem.getModifiedDate());
	}

	@Test
	public void testGetScopeName() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDescriptiveName(Matchers.any(Locale.class))
		).thenReturn(
			"scopeName"
		);

		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, group, null, null, null);

		Assert.assertEquals(
			"scopeName",
			blogsEntryContentDashboardItem.getScopeName(LocaleUtil.US));
	}

	@Test
	public void testGetSubtype() {
		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, null, null, null, null);

		ContentDashboardItemSubtype contentDashboardItemType =
			blogsEntryContentDashboardItem.getContentDashboardItemSubtype();

		Assert.assertNull(contentDashboardItemType);
	}

	@Test
	public void testGetTitle() {
		BlogsEntry blogsEntry = _getBlogsEntry();

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, null, null, null, null);

		Assert.assertEquals(
			blogsEntry.getTitle(),
			blogsEntryContentDashboardItem.getTitle(LocaleUtil.US));
	}

	@Test
	public void testGetUserId() {
		BlogsEntry blogsEntry = _getBlogsEntry();

		Mockito.when(
			blogsEntry.getUserId()
		).thenReturn(
			12345L
		);

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, null, null, null, null);

		Assert.assertEquals(
			blogsEntry.getUserId(), blogsEntryContentDashboardItem.getUserId());
	}

	@Test
	public void testGetUserName() {
		BlogsEntry blogsEntry = _getBlogsEntry();

		Mockito.when(
			blogsEntry.getUserName()
		).thenReturn(
			"name"
		);

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry, null, null, null, null, null);

		Assert.assertEquals(
			blogsEntry.getUserId(), blogsEntryContentDashboardItem.getUserId());
	}

	@Test
	public void testIsViewable() throws Exception {
		BlogsEntry blogsEntry = _getBlogsEntry();

		Mockito.when(
			blogsEntry.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		BlogsEntryContentDashboardItem blogsEntryContentDashboardItem =
			new BlogsEntryContentDashboardItem(
				null, null, blogsEntry,
				_getContentDashboardItemActionProviderTracker(
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.VIEW,
						"http://localhost:8080/view")),
				null, null, _getLanguage(), null);

		Assert.assertTrue(
			blogsEntryContentDashboardItem.isViewable(
				_getHttpServletRequest(RandomTestUtil.randomLong())));
	}

	private BlogsEntry _getBlogsEntry() {
		BlogsEntry blogsEntry = Mockito.mock(BlogsEntry.class);

		Mockito.when(
			blogsEntry.getCreateDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			blogsEntry.getModifiedDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			blogsEntry.getSubtitle()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			blogsEntry.getTitle()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return blogsEntry;
	}

	private ContentDashboardItemAction _getContentDashboardItemAction(
		String url) {

		ContentDashboardItemAction contentDashboardItemAction = Mockito.mock(
			ContentDashboardItemAction.class);

		Mockito.when(
			contentDashboardItemAction.getURL()
		).thenReturn(
			url
		);
		Mockito.when(
			contentDashboardItemAction.getURL(Matchers.any(Locale.class))
		).thenReturn(
			url
		);

		return contentDashboardItemAction;
	}

	private ContentDashboardItemActionProvider
			_getContentDashboardItemActionProvider(
				ContentDashboardItemAction.Type type, String url)
		throws Exception {

		ContentDashboardItemActionProvider contentDashboardItemActionProvider =
			Mockito.mock(ContentDashboardItemActionProvider.class);

		ContentDashboardItemAction contentDashboardItemAction =
			_getContentDashboardItemAction(url);

		Mockito.when(
			contentDashboardItemActionProvider.getContentDashboardItemAction(
				Matchers.any(BlogsEntry.class),
				Matchers.any(HttpServletRequest.class))
		).thenReturn(
			contentDashboardItemAction
		);

		Mockito.when(
			contentDashboardItemActionProvider.getType()
		).thenReturn(
			type
		);

		Mockito.when(
			contentDashboardItemActionProvider.isShow(
				Matchers.any(JournalArticle.class),
				Matchers.any(HttpServletRequest.class))
		).thenReturn(
			true
		);

		return contentDashboardItemActionProvider;
	}

	private ContentDashboardItemActionProviderTracker
		_getContentDashboardItemActionProviderTracker(
			ContentDashboardItemActionProvider...
				contentDashboardItemActionProviders) {

		ContentDashboardItemActionProviderTracker
			contentDashboardItemActionProviderTracker = Mockito.mock(
				ContentDashboardItemActionProviderTracker.class);

		if (contentDashboardItemActionProviders == null) {
			Mockito.when(
				contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviderOptional(
						Mockito.anyString(), Mockito.anyObject())
			).thenReturn(
				Optional.empty()
			);

			return contentDashboardItemActionProviderTracker;
		}

		for (ContentDashboardItemActionProvider
				contentDashboardItemActionProvider :
					contentDashboardItemActionProviders) {

			Mockito.when(
				contentDashboardItemActionProviderTracker.
					getContentDashboardItemActionProviderOptional(
						BlogsEntry.class.getName(),
						contentDashboardItemActionProvider.getType())
			).thenReturn(
				Optional.of(contentDashboardItemActionProvider)
			);
		}

		return contentDashboardItemActionProviderTracker;
	}

	private HttpServletRequest _getHttpServletRequest(long userId)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.USER_ID, userId);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.clone()
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private Language _getLanguage() {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(Matchers.any(Locale.class), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArguments()[1]
		);

		Mockito.when(
			language.getLocale(Mockito.anyString())
		).thenAnswer(
			invocation -> LocaleUtil.US
		);

		return language;
	}

}