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
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
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
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class JournalArticleContentDashboardItemTest {

	@Test
	public void testGetAssetCategories() {
		JournalArticle journalArticle = _getJournalArticle();

		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, journalArticle, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetCategory),
			journalArticleContentDashboardItem.getAssetCategories(
				assetCategory.getVocabularyId()));
	}

	@Test
	public void testGetAssetCategoriesWithEmptyAssetCategories() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, journalArticle, null, null);

		Assert.assertEquals(
			Collections.emptyList(),
			journalArticleContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetCategoriesWithNoAssetCategoriesInAssetVocabulary() {
		JournalArticle journalArticle = _getJournalArticle();

		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, journalArticle, null, null);

		Assert.assertEquals(
			Collections.emptyList(),
			journalArticleContentDashboardItem.getAssetCategories(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetEditURL() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		InfoEditURLProvider<JournalArticle> infoEditURLProvider = Mockito.mock(
			InfoEditURLProvider.class);

		Mockito.when(
			infoEditURLProvider.getURL(
				Mockito.any(JournalArticle.class),
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			"validURL"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, infoEditURLProvider, journalArticle,
				null, null);

		Assert.assertEquals(
			"validURL",
			journalArticleContentDashboardItem.getEditURL(
				_getHttpServletRequest()));
	}

	@Test
	public void testGetEditURLWithNullURL() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		InfoEditURLProvider<JournalArticle> infoEditURLProvider = Mockito.mock(
			InfoEditURLProvider.class);

		Mockito.when(
			infoEditURLProvider.getURL(
				Mockito.any(JournalArticle.class),
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			null
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, infoEditURLProvider, journalArticle,
				null, null);

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
				null, null, null, null, null, journalArticle, null, null);

		Assert.assertEquals(
			journalArticle.getExpirationDate(),
			journalArticleContentDashboardItem.getExpirationDate());
	}

	@Test
	public void testGetModifiedDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, journalArticle, null, null);

		Assert.assertEquals(
			journalArticle.getModifiedDate(),
			journalArticleContentDashboardItem.getModifiedDate());
	}

	@Test
	public void testGetPublishDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, journalArticle, null, null);

		Assert.assertEquals(
			journalArticle.getDisplayDate(),
			journalArticleContentDashboardItem.getPublishDate());
	}

	@Test
	public void testGetScopeName() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDescriptiveName(Mockito.any(Locale.class))
		).thenReturn(
			"scopeName"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, group, null, journalArticle, null, null);

		Assert.assertEquals(
			"scopeName",
			journalArticleContentDashboardItem.getScopeName(LocaleUtil.US));
	}

	@Test
	public void testGetStatuses() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, journalArticle, _getLanguage(),
				null);

		List<ContentDashboardItem.Status> statuses =
			journalArticleContentDashboardItem.getStatuses(LocaleUtil.US);

		Assert.assertEquals(statuses.toString(), 1, statuses.size());

		ContentDashboardItem.Status status = statuses.get(0);

		Assert.assertEquals(
			WorkflowConstants.LABEL_APPROVED, status.getLabel());
		Assert.assertEquals("success", status.getStyle());
	}

	@Test
	public void testGetStatusesWithMultipleStatuses() {
		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.hasApprovedVersion()
		).thenReturn(
			true
		);

		Mockito.when(
			journalArticle.isApproved()
		).thenReturn(
			false
		);

		Mockito.when(
			journalArticle.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, journalArticle, _getLanguage(),
				null);

		List<ContentDashboardItem.Status> statuses =
			journalArticleContentDashboardItem.getStatuses(LocaleUtil.US);

		Assert.assertEquals(statuses.toString(), 2, statuses.size());

		ContentDashboardItem.Status status1 = statuses.get(0);

		Assert.assertEquals(
			WorkflowConstants.LABEL_APPROVED, status1.getLabel());
		Assert.assertEquals("success", status1.getStyle());

		ContentDashboardItem.Status status2 = statuses.get(1);

		Assert.assertEquals(WorkflowConstants.LABEL_DRAFT, status2.getLabel());
		Assert.assertEquals("secondary", status2.getStyle());
	}

	@Test
	public void testGetSubtype() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null,
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

				},
				null, null, journalArticle, null, null);

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
				null, null, null, null, null, journalArticle, null, null);

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			journalArticleContentDashboardItem.getTitle(LocaleUtil.US));
	}

	@Test
	public void testGetViewURL() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		Mockito.when(
			assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			"validURL"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, assetDisplayPageFriendlyURLProvider, null, null, null,
				journalArticle, null, null);

		Assert.assertEquals(
			"validURL",
			journalArticleContentDashboardItem.getViewURL(
				_getHttpServletRequest()));
	}

	@Test
	public void testGetViewURLWithNullFriendlyURL() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, assetDisplayPageFriendlyURLProvider, null, null, null,
				journalArticle, null, null);

		Assert.assertEquals(
			StringPool.BLANK,
			journalArticleContentDashboardItem.getViewURL(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsEditURLEnabled() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

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

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, infoEditURLProvider, journalArticle,
				null, modelResourcePermission);

		Assert.assertTrue(
			journalArticleContentDashboardItem.isEditURLEnabled(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsEditURLEnabledWithoutPermissions() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		InfoEditURLProvider<JournalArticle> infoEditURLProvider = Mockito.mock(
			InfoEditURLProvider.class);

		ModelResourcePermission<JournalArticle> modelResourcePermission =
			Mockito.mock(ModelResourcePermission.class);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, infoEditURLProvider, journalArticle,
				null, modelResourcePermission);

		Assert.assertFalse(
			journalArticleContentDashboardItem.isEditURLEnabled(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsViewURLEnabled() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		Mockito.when(
			assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			"validURL"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, assetDisplayPageFriendlyURLProvider, null, null, null,
				journalArticle, null, null);

		Assert.assertTrue(
			journalArticleContentDashboardItem.isViewURLEnabled(
				_getHttpServletRequest()));
	}

	@Test
	public void testIsViewURLEnabledWithNotApprovedVersion() {
		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.hasApprovedVersion()
		).thenReturn(
			false
		);

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, assetDisplayPageFriendlyURLProvider, null, null, null,
				journalArticle, null, null);

		Assert.assertFalse(
			journalArticleContentDashboardItem.isViewURLEnabled(null));
	}

	@Test
	public void testIsViewURLEnabledWithNullFriendlyURL() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, assetDisplayPageFriendlyURLProvider, null, null, null,
				journalArticle, null, null);

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
			journalArticle.hasApprovedVersion()
		).thenReturn(
			true
		);

		Mockito.when(
			journalArticle.isApproved()
		).thenReturn(
			true
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

		return journalArticle;
	}

	private Language _getLanguage() {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(Mockito.any(Locale.class), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArguments()[1]
		);

		return language;
	}

}