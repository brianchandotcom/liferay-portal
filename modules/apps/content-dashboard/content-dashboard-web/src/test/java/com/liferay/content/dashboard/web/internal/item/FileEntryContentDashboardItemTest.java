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
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.web.internal.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
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
 * @author Yurena Cabrera
 */
public class FileEntryContentDashboardItemTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageResources languageResources = new LanguageResources();

		languageResources.setConfig(StringPool.BLANK);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	@Test
	public void testGetAssetCategories() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, fileEntry, null, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetCategory),
			fileEntryContentDashboardItem.getAssetCategories());
	}

	@Test
	public void testGetAssetCategoriesByAssetVocabulary() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, fileEntry, null, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetCategory),
			fileEntryContentDashboardItem.getAssetCategories(
				assetCategory.getVocabularyId()));
	}

	@Test
	public void testGetAssetCategoriesByAssetVocabularyWithEmptyAssetCategories() {
		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null, null, null, fileEntry, null, null, null, null,
				null);

		Assert.assertEquals(
			Collections.emptyList(),
			fileEntryContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetCategoriesWithNoAssetCategoriesInAssetVocabulary() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);
		FileEntry fileEntry = _getFileEntry();

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, fileEntry, null, null, null, null, null);

		Assert.assertEquals(
			Collections.emptyList(),
			fileEntryContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTags() {
		AssetTag assetTag = Mockito.mock(AssetTag.class);

		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, Collections.singletonList(assetTag), null, null, null,
				fileEntry, null, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetTag),
			fileEntryContentDashboardItem.getAssetTags());
	}

	@Test
	public void testGetDescription() {
		FileEntry fileEntry = _getFileEntry();

		Mockito.when(
			fileEntry.getDescription()
		).thenReturn(
			"description"
		);

		InfoItemFieldValuesProvider<FileEntry> infoItemFieldValuesProvider =
			Mockito.mock(InfoItemFieldValuesProvider.class);

		Mockito.when(
			infoItemFieldValuesProvider.getInfoItemFieldValues(fileEntry)
		).thenReturn(
			InfoItemFieldValues.builder(
			).infoFieldValue(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						DateInfoFieldType.INSTANCE
					).name(
						"description"
					).labelInfoLocalizedValue(
						null
					).build(),
					"description")
			).build()
		);

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null, null, null, fileEntry, null, null,
				infoItemFieldValuesProvider, null, null);

		Assert.assertEquals(
			"description",
			fileEntryContentDashboardItem.getDescription(LocaleUtil.US));
	}

	@Test
	public void testGetModifiedDate() {
		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null, null, null, fileEntry, null, null, null, null,
				null);

		Assert.assertEquals(
			fileEntry.getModifiedDate(),
			fileEntryContentDashboardItem.getModifiedDate());
	}

	@Test
	public void testGetScopeName() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDescriptiveName(Matchers.any(Locale.class))
		).thenReturn(
			"scopeName"
		);

		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null, null, null, fileEntry, group, null, null,
				null, null);

		Assert.assertEquals(
			"scopeName",
			fileEntryContentDashboardItem.getScopeName(LocaleUtil.US));
	}

	@Test
	public void testGetSubtype() {
		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null,
				new ContentDashboardItemSubtype() {

					@Override
					public String getFullLabel(Locale locale) {
						return null;
					}

					@Override
					public InfoItemReference getInfoItemReference() {
						return null;
					}

					@Override
					public String getLabel(Locale locale) {
						return "subtype";
					}

					@Override
					public String toJSONString(Locale locale) {
						return StringPool.BLANK;
					}

				},
				null, fileEntry, null, null, null, null, null);

		ContentDashboardItemSubtype contentDashboardItemType =
			fileEntryContentDashboardItem.getContentDashboardItemSubtype();

		Assert.assertEquals(
			"subtype", contentDashboardItemType.getLabel(LocaleUtil.US));
	}

	@Test
	public void testGetTitle() {
		FileEntry fileEntry = _getFileEntry();

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null, null, null, fileEntry, null, null, null, null,
				null);

		Assert.assertEquals(
			fileEntry.getTitle(),
			fileEntryContentDashboardItem.getTitle(LocaleUtil.US));
	}

	@Test
	public void testGetUserId() {
		FileEntry fileEntry = _getFileEntry();

		Mockito.when(
			fileEntry.getUserId()
		).thenReturn(
			12345L
		);

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null, null, null, fileEntry, null, null, null, null,
				null);

		Assert.assertEquals(
			fileEntry.getUserId(), fileEntryContentDashboardItem.getUserId());
	}

	@Test
	public void testGetUserName() {
		FileEntry fileEntry = _getFileEntry();

		Mockito.when(
			fileEntry.getUserName()
		).thenReturn(
			"name"
		);

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null, null, null, null, fileEntry, null, null, null, null,
				null);

		Assert.assertEquals(
			fileEntry.getUserId(), fileEntryContentDashboardItem.getUserId());
	}

	@Test
	public void testIsViewable() throws Exception {
		FileEntry fileEntry = _getFileEntry();

		Mockito.when(
			fileEntry.getFileVersions(WorkflowConstants.STATUS_APPROVED)
		).thenReturn(
			new ArrayList<FileVersion>() {
				{
					add(Mockito.mock(FileVersion.class));
					add(Mockito.mock(FileVersion.class));
				}
			}
		);

		FileEntryContentDashboardItem fileEntryContentDashboardItem =
			new FileEntryContentDashboardItem(
				null, null,
				_getContentDashboardItemActionProviderTracker(
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.VIEW,
						"http://localhost:8080/view")),
				null, null, fileEntry, null, null, null, _getLanguage(), null);

		Assert.assertTrue(
			fileEntryContentDashboardItem.isViewable(
				_getHttpServletRequest(RandomTestUtil.randomLong())));
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
				Matchers.any(FileEntry.class),
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
						FileEntry.class.getName(),
						contentDashboardItemActionProvider.getType())
			).thenReturn(
				Optional.of(contentDashboardItemActionProvider)
			);
		}

		return contentDashboardItemActionProviderTracker;
	}

	private FileEntry _getFileEntry() {
		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		Mockito.when(
			fileEntry.getCreateDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			fileEntry.getDescription()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			fileEntry.getExtension()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			fileEntry.getSize()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			fileEntry.getExpirationDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			fileEntry.getModifiedDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			fileEntry.getTitle()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return fileEntry;
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