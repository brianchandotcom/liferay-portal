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

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class JournalArticleContentDashboardItemTest {

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetAssetCategories() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, null, journalArticle, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetCategory),
			journalArticleContentDashboardItem.getAssetCategories());
	}

	@Test
	public void testGetAssetCategoriesByAssetVocabulary() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, null, journalArticle, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetCategory),
			journalArticleContentDashboardItem.getAssetCategories(
				assetCategory.getVocabularyId()));
	}

	@Test
	public void testGetAssetCategoriesByAssetVocabularyWithEmptyAssetCategories() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, journalArticle, null, null,
				null, null);

		Assert.assertEquals(
			Collections.emptyList(),
			journalArticleContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetCategoriesWithNoAssetCategoriesInAssetVocabulary() {
		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, null, journalArticle, null, null, null, null);

		Assert.assertEquals(
			Collections.emptyList(),
			journalArticleContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTags() {
		AssetTag assetTag = Mockito.mock(AssetTag.class);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, Collections.singletonList(assetTag), null, null, null,
				null, journalArticle, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetTag),
			journalArticleContentDashboardItem.getAssetTags());
	}

	@Test
	public void testGetEditURL() throws Exception {
		InfoEditURLProvider<JournalArticle> infoEditURLProvider = Mockito.mock(
			InfoEditURLProvider.class);

		Mockito.when(
			infoEditURLProvider.getURL(
				Mockito.any(JournalArticle.class),
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			"validURL"
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, infoEditURLProvider,
				journalArticle, null, null, null, null);

		Assert.assertEquals(
			"validURL",
			journalArticleContentDashboardItem.getEditURL(
				_getHttpServletRequest()));
	}

	@Test
	public void testGetEditURLWithNullURL() throws Exception {
		InfoEditURLProvider<JournalArticle> infoEditURLProvider = Mockito.mock(
			InfoEditURLProvider.class);

		Mockito.when(
			infoEditURLProvider.getURL(
				Mockito.any(JournalArticle.class),
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			null
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, infoEditURLProvider,
				journalArticle, null, null, null, null);

		Assert.assertEquals(
			StringPool.BLANK,
			journalArticleContentDashboardItem.getEditURL(
				_getHttpServletRequest()));
	}

	@Test
	public void testGetExpirationDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, journalArticle, null, null,
				null, null);

		Assert.assertEquals(
			journalArticle.getExpirationDate(),
			journalArticleContentDashboardItem.getExpirationDate());
	}

	@Test
	public void testGetModifiedDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, journalArticle, null, null,
				null, null);

		Assert.assertEquals(
			journalArticle.getModifiedDate(),
			journalArticleContentDashboardItem.getModifiedDate());
	}

	@Test
	public void testGetPublishDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, journalArticle, null, null,
				null, null);

		Assert.assertEquals(
			journalArticle.getDisplayDate(),
			journalArticleContentDashboardItem.getPublishDate());
	}

	@Test
	public void testGetScopeName() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDescriptiveName(Mockito.any(Locale.class))
		).thenReturn(
			"scopeName"
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, group, null, journalArticle, null, null,
				null, null);

		Assert.assertEquals(
			"scopeName",
			journalArticleContentDashboardItem.getScopeName(LocaleUtil.US));
	}

	@Test
	public void testGetSubtype() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null,
				new ContentDashboardItemType() {

					@Override
					public String getClassName() {
						return null;
					}

					@Override
					public long getClassPK() {
						return 0;
					}

					@Override
					public String getFullLabel(Locale locale) {
						return null;
					}

					@Override
					public String getLabel(Locale locale) {
						return "subtype";
					}

					@Override
					public Date getModifiedDate() {
						return null;
					}

					@Override
					public long getUserId() {
						return 0;
					}

					@Override
					public String toJSONString(Locale locale) {
						return StringPool.BLANK;
					}

				},
				null, null, journalArticle, null, null, null, null);

		ContentDashboardItemType contentDashboardItemType =
			journalArticleContentDashboardItem.getContentDashboardItemType();

		Assert.assertEquals(
			"subtype", contentDashboardItemType.getLabel(LocaleUtil.US));
	}

	@Test
	public void testGetTitle() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, journalArticle, null, null,
				null, null);

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			journalArticleContentDashboardItem.getTitle(LocaleUtil.US));
	}

	@Test
	public void testGetVersions() {
		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, journalArticle,
				_getLanguage(), null, null, null);

		List<ContentDashboardItem.Version> versions =
			journalArticleContentDashboardItem.getVersions(LocaleUtil.US);

		Assert.assertEquals(versions.toString(), 1, versions.size());

		ContentDashboardItem.Version version = versions.get(0);

		Assert.assertEquals(
			WorkflowConstants.LABEL_APPROVED, version.getLabel());
		Assert.assertEquals("success", version.getStyle());
	}

	@Test
	public void testGetVersionsWithApprovedVersion() {
		JournalArticle journalArticle1 = _getJournalArticle();

		Mockito.when(
			journalArticle1.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Mockito.when(
			journalArticle1.getVersion()
		).thenReturn(
			1.1
		);

		JournalArticle journalArticle2 = _getJournalArticle();

		Mockito.when(
			journalArticle2.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		Mockito.when(
			journalArticle1.getVersion()
		).thenReturn(
			1.0
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, journalArticle1,
				_getLanguage(), journalArticle2, null, null);

		List<ContentDashboardItem.Version> versions =
			journalArticleContentDashboardItem.getVersions(LocaleUtil.US);

		Assert.assertEquals(versions.toString(), 2, versions.size());

		ContentDashboardItem.Version version1 = versions.get(0);

		Assert.assertEquals(
			WorkflowConstants.LABEL_APPROVED, version1.getLabel());
		Assert.assertEquals("success", version1.getStyle());

		ContentDashboardItem.Version version2 = versions.get(1);

		Assert.assertEquals(WorkflowConstants.LABEL_DRAFT, version2.getLabel());
		Assert.assertEquals("secondary", version2.getStyle());
	}

	@Test
	public void testGetViewURL() throws Exception {
		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		Mockito.when(
			assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			"validURL"
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, assetDisplayPageFriendlyURLProvider, null, null,
				null, journalArticle, _getLanguage(), null, null, null);

		Assert.assertEquals(
			"validURL",
			journalArticleContentDashboardItem.getViewURL(
				_getHttpServletRequest()));
	}

	@Test
	public void testGetViewURLWithNullFriendlyURL() throws Exception {
		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, assetDisplayPageFriendlyURLProvider, null, null,
				null, journalArticle, _getLanguage(), null, null, null);

		Assert.assertEquals(
			StringPool.BLANK,
			journalArticleContentDashboardItem.getViewURL(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsEditURLEnabled() throws Exception {
		InfoEditURLProvider<JournalArticle> infoEditURLProvider = Mockito.mock(
			InfoEditURLProvider.class);

		ModelResourcePermission<JournalArticle> modelResourcePermission =
			Mockito.mock(ModelResourcePermission.class);

		Mockito.when(
			modelResourcePermission.contains(
				Mockito.any(PermissionChecker.class),
				Mockito.any(JournalArticle.class), Mockito.anyString())
		).thenReturn(
			Boolean.TRUE
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, infoEditURLProvider,
				journalArticle, _getLanguage(), null, modelResourcePermission,
				null);

		Assert.assertTrue(
			journalArticleContentDashboardItem.isEditURLEnabled(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsEditURLEnabledWithoutPermissions() throws Exception {
		InfoEditURLProvider<JournalArticle> infoEditURLProvider = Mockito.mock(
			InfoEditURLProvider.class);

		ModelResourcePermission<JournalArticle> modelResourcePermission =
			Mockito.mock(ModelResourcePermission.class);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, infoEditURLProvider,
				journalArticle, _getLanguage(), null, modelResourcePermission,
				null);

		Assert.assertFalse(
			journalArticleContentDashboardItem.isEditURLEnabled(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsViewURLEnabled() throws Exception {
		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		Mockito.when(
			assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			"validURL"
		);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, assetDisplayPageFriendlyURLProvider, null, null,
				null, journalArticle, _getLanguage(), null, null, null);

		Assert.assertTrue(
			journalArticleContentDashboardItem.isViewURLEnabled(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsViewURLEnabledWithNotApprovedVersion() {
		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.hasApprovedVersion()
		).thenReturn(
			false
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, assetDisplayPageFriendlyURLProvider, null, null,
				null, journalArticle, null, null, null, null);

		Assert.assertFalse(
			journalArticleContentDashboardItem.isViewURLEnabled(null));
	}

	@Test
	public void testIsViewURLEnabledWithNullFriendlyURL() throws Exception {
		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, assetDisplayPageFriendlyURLProvider, null, null,
				null, journalArticle, _getLanguage(), null, null, null);

		Assert.assertFalse(
			journalArticleContentDashboardItem.isViewURLEnabled(
				_getHttpServletRequest()));
	}

	private HttpServletRequest _getHttpServletRequest() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

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

	private JournalArticle _getJournalArticle() {
		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		Mockito.when(
			journalArticle.getDisplayDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			journalArticle.getExpirationDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			journalArticle.getModifiedDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			journalArticle.getTitle(Mockito.any(Locale.class))
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			journalArticle.hasApprovedVersion()
		).thenReturn(
			true
		);

		Mockito.when(
			journalArticle.isApproved()
		).thenReturn(
			true
		);

		return journalArticle;
	}

	private Language _getLanguage() {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(Mockito.any(Locale.class), Mockito.anyString())
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