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

package com.liferay.content.dashboard.journal.internal.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.ContentDashboardItemVersion;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionActionProviderTracker;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class JournalArticleContentDashboardItemTest {

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

		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				Collections.singletonList(assetCategory), null, null, null,
				null, null, null, journalArticle, null, null, null, null);

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
				null, null, null, journalArticle, null, null, null, null);

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
				null, null, null, null, null, null, null, journalArticle, null,
				null, null, null);

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
				null, null, null, journalArticle, null, null, null, null);

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
				null, null, journalArticle, null, null, null, null);

		Assert.assertEquals(
			Collections.singletonList(assetTag),
			journalArticleContentDashboardItem.getAssetTags());
	}

	@Test
	public void testGetContentDashboardItemVersionActions() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		ContentDashboardItemVersionAction contentDashboardItemVersionAction1 =
			_getContentDashboardItemVersionAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ContentDashboardItemVersionAction contentDashboardItemVersionAction2 =
			_getContentDashboardItemVersionAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null,
				_getContentDashboardItemVersionActionProviderTracker(
					_getContentDashboardItemVersionActionProvider(
						contentDashboardItemVersionAction1, true),
					_getContentDashboardItemVersionActionProvider(
						contentDashboardItemVersionAction2, true)),
				null, null, null, journalArticle,
				_getJournalArticleService(_getJournalArticle()), _getLanguage(),
				null, null);

		List<ContentDashboardItemVersion> contentDashboardItemVersions =
			journalArticleContentDashboardItem.
				getAllContentDashboardItemVersions(
					_getHttpServletRequest(RandomTestUtil.randomLong()));

		Assert.assertEquals(
			contentDashboardItemVersions.toString(), 1,
			contentDashboardItemVersions.size());

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersions.get(0);

		List<ContentDashboardItemVersionAction>
			contentDashboardItemVersionActions =
				contentDashboardItemVersion.
					getContentDashboardItemVersionActions();

		Assert.assertNotNull(contentDashboardItemVersionActions);

		Assert.assertEquals(
			contentDashboardItemVersionActions.toString(), 2,
			contentDashboardItemVersionActions.size());

		Assert.assertEquals(
			contentDashboardItemVersionAction1,
			contentDashboardItemVersionActions.get(0));
		Assert.assertEquals(
			contentDashboardItemVersionAction2,
			contentDashboardItemVersionActions.get(1));
	}

	@Test
	public void testGetContentDashboardItemVersionsWithNoActions()
		throws Exception {

		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null,
				_getContentDashboardItemVersionActionProviderTracker(), null,
				null, null, journalArticle,
				_getJournalArticleService(_getJournalArticle()), _getLanguage(),
				null, null);

		List<ContentDashboardItemVersion> contentDashboardItemVersions =
			journalArticleContentDashboardItem.
				getAllContentDashboardItemVersions(
					_getHttpServletRequest(RandomTestUtil.randomLong()));

		Assert.assertEquals(
			contentDashboardItemVersions.toString(), 1,
			contentDashboardItemVersions.size());

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersions.get(0);

		List<ContentDashboardItemVersionAction>
			contentDashboardItemVersionActions =
				contentDashboardItemVersion.
					getContentDashboardItemVersionActions();

		Assert.assertEquals(
			contentDashboardItemVersionActions.toString(), 0,
			contentDashboardItemVersionActions.size());
	}

	@Test
	public void testGetDefaultContentDashboardItemAction() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null,
				_getContentDashboardItemActionProviderTracker(
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.VIEW,
						"http://localhost:8080/view")),
				null, null, null, null, journalArticle, null,
				new LanguageImpl(), null, new PortalImpl());

		ContentDashboardItemAction contentDashboardItemAction =
			journalArticleContentDashboardItem.
				getDefaultContentDashboardItemAction(
					_getHttpServletRequest(RandomTestUtil.randomLong()));

		Assert.assertEquals(
			"http://localhost:8080/view", contentDashboardItemAction.getURL());
	}

	@Test
	public void testGetDefaultContentDashboardItemActionWithApprovedAndDraftStatusAndNotOwnerUser()
		throws Exception {

		JournalArticle journalArticle1 = _getJournalArticle();

		Mockito.when(
			journalArticle1.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		JournalArticle journalArticle2 = _getJournalArticle();

		Mockito.when(
			journalArticle2.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Mockito.when(
			journalArticle2.getVersion()
		).thenReturn(
			1.1
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null,
				_getContentDashboardItemActionProviderTracker(
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.VIEW,
						"http://localhost:8080/view"),
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.EDIT,
						"http://localhost:8080/edit")),
				null, null, null, null, journalArticle1, null,
				new LanguageImpl(), journalArticle2, new PortalImpl());

		ContentDashboardItemAction contentDashboardItemAction =
			journalArticleContentDashboardItem.
				getDefaultContentDashboardItemAction(
					_getHttpServletRequest(RandomTestUtil.randomLong()));

		Assert.assertEquals(
			"http://localhost:8080/view", contentDashboardItemAction.getURL());
	}

	@Test
	public void testGetDefaultContentDashboardItemActionWithApprovedAndDraftStatusAndOwnerUser()
		throws Exception {

		JournalArticle journalArticle1 = _getJournalArticle();

		Mockito.when(
			journalArticle1.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		Mockito.when(
			journalArticle1.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		JournalArticle journalArticle2 = _getJournalArticle();

		Mockito.when(
			journalArticle2.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Mockito.when(
			journalArticle2.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			journalArticle2.getVersion()
		).thenReturn(
			1.1
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null,
				_getContentDashboardItemActionProviderTracker(
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.VIEW,
						"http://localhost:8080/view"),
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.EDIT,
						"http://localhost:8080/edit")),
				null, null, null, null, journalArticle1, null,
				new LanguageImpl(), journalArticle2, new PortalImpl());

		ContentDashboardItemAction contentDashboardItemAction =
			journalArticleContentDashboardItem.
				getDefaultContentDashboardItemAction(
					_getHttpServletRequest(journalArticle2.getUserId()));

		Assert.assertEquals(
			"http://localhost:8080/edit", contentDashboardItemAction.getURL());
	}

	@Test
	public void testGetDefaultContentDashboardItemActionWithApprovedAndDraftStatusAnWithoutContentDashboardItemActionProviders()
		throws Exception {

		JournalArticle journalArticle1 = _getJournalArticle();

		Mockito.when(
			journalArticle1.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		JournalArticle journalArticle2 = _getJournalArticle();

		Mockito.when(
			journalArticle2.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Mockito.when(
			journalArticle2.getVersion()
		).thenReturn(
			1.1
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, _getContentDashboardItemActionProviderTracker(null),
				null, null, null, null, journalArticle1, null,
				new LanguageImpl(), journalArticle2, new PortalImpl());

		ContentDashboardItemAction contentDashboardItemAction =
			journalArticleContentDashboardItem.
				getDefaultContentDashboardItemAction(
					_getHttpServletRequest(RandomTestUtil.randomLong()));

		Assert.assertNull(contentDashboardItemAction);
	}

	@Test
	public void testGetModifiedDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, null, journalArticle, null,
				null, null, null);

		Assert.assertEquals(
			journalArticle.getModifiedDate(),
			journalArticleContentDashboardItem.getModifiedDate());
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
				null, null, null, null, null, group, null, journalArticle, null,
				null, null, null);

		Assert.assertEquals(
			"scopeName",
			journalArticleContentDashboardItem.getScopeName(LocaleUtil.US));
	}

	@Test
	public void testGetSubtype() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null,
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
				null, null, journalArticle, null, null, null, null);

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			journalArticleContentDashboardItem.getContentDashboardItemSubtype();

		Assert.assertEquals(
			"subtype", contentDashboardItemSubtype.getLabel(LocaleUtil.US));
	}

	@Test
	public void testGetTitle() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, null, journalArticle, null,
				null, null, null);

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			journalArticleContentDashboardItem.getTitle(LocaleUtil.US));
	}

	@Test
	public void testGetUserId() {
		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.getUserId()
		).thenReturn(
			12345L
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, null, journalArticle, null,
				null, null, null);

		Assert.assertEquals(
			journalArticle.getUserId(),
			journalArticleContentDashboardItem.getUserId());
	}

	@Test
	public void testGetUserIdWithLatestApprovedJournalArticle() {
		JournalArticle journalArticle1 = _getJournalArticle();

		Mockito.when(
			journalArticle1.getUserId()
		).thenReturn(
			12345L
		);

		JournalArticle journalArticle2 = _getJournalArticle();

		Mockito.when(
			journalArticle2.getUserId()
		).thenReturn(
			52345L
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, null, journalArticle1, null,
				null, journalArticle2, null);

		Assert.assertEquals(
			journalArticle2.getUserId(),
			journalArticleContentDashboardItem.getUserId());
	}

	@Test
	public void testGetUserName() {
		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.getUserName()
		).thenReturn(
			"name"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, null, journalArticle, null,
				null, null, null);

		Assert.assertEquals(
			journalArticle.getUserId(),
			journalArticleContentDashboardItem.getUserId());
	}

	@Test
	public void testGetUserNameWithLatestApprovedJournalArticle() {
		JournalArticle journalArticle1 = _getJournalArticle();

		Mockito.when(
			journalArticle1.getUserName()
		).thenReturn(
			"name1"
		);

		JournalArticle journalArticle2 = _getJournalArticle();

		Mockito.when(
			journalArticle2.getUserName()
		).thenReturn(
			"name2"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null, null, null, null, null, journalArticle1, null,
				null, journalArticle2, null);

		Assert.assertEquals(
			journalArticle2.getUserId(),
			journalArticleContentDashboardItem.getUserId());
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
				null, null, null, null, null, null, null, journalArticle, null,
				_getLanguage(), null, null);

		List<ContentDashboardItemVersion> contentDashboardItemVersions =
			journalArticleContentDashboardItem.
				getLatestContentDashboardItemVersions(LocaleUtil.US);

		Assert.assertEquals(
			contentDashboardItemVersions.toString(), 1,
			contentDashboardItemVersions.size());

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersions.get(0);

		Assert.assertEquals(
			WorkflowConstants.LABEL_APPROVED,
			contentDashboardItemVersion.getLabel());
		Assert.assertEquals("success", contentDashboardItemVersion.getStyle());
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
				null, null, null, null, null, null, null, journalArticle1, null,
				_getLanguage(), journalArticle2, null);

		List<ContentDashboardItemVersion> contentDashboardItemVersions =
			journalArticleContentDashboardItem.
				getLatestContentDashboardItemVersions(LocaleUtil.US);

		Assert.assertEquals(
			contentDashboardItemVersions.toString(), 2,
			contentDashboardItemVersions.size());

		ContentDashboardItemVersion contentDashboardItemVersion1 =
			contentDashboardItemVersions.get(0);

		Assert.assertEquals(
			WorkflowConstants.LABEL_APPROVED,
			contentDashboardItemVersion1.getLabel());
		Assert.assertEquals("success", contentDashboardItemVersion1.getStyle());

		ContentDashboardItemVersion contentDashboardItemVersion2 =
			contentDashboardItemVersions.get(1);

		Assert.assertEquals(
			WorkflowConstants.LABEL_DRAFT,
			contentDashboardItemVersion2.getLabel());
		Assert.assertEquals(
			"secondary", contentDashboardItemVersion2.getStyle());
	}

	@Test
	public void testGetVisibleContentDashboardItemVersionActions()
		throws Exception {

		JournalArticle journalArticle = _getJournalArticle();

		Mockito.when(
			journalArticle.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		ContentDashboardItemVersionAction contentDashboardItemVersionAction =
			_getContentDashboardItemVersionAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null, null,
				_getContentDashboardItemVersionActionProviderTracker(
					_getContentDashboardItemVersionActionProvider(null, false),
					_getContentDashboardItemVersionActionProvider(
						contentDashboardItemVersionAction, true),
					_getContentDashboardItemVersionActionProvider(null, false)),
				null, null, null, journalArticle,
				_getJournalArticleService(_getJournalArticle()), _getLanguage(),
				null, null);

		List<ContentDashboardItemVersion> contentDashboardItemVersions =
			journalArticleContentDashboardItem.
				getAllContentDashboardItemVersions(
					_getHttpServletRequest(RandomTestUtil.randomLong()));

		Assert.assertEquals(
			contentDashboardItemVersions.toString(), 1,
			contentDashboardItemVersions.size());

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersions.get(0);

		List<ContentDashboardItemVersionAction>
			contentDashboardItemVersionActions =
				contentDashboardItemVersion.
					getContentDashboardItemVersionActions();

		Assert.assertNotNull(contentDashboardItemVersionActions);

		Assert.assertEquals(
			contentDashboardItemVersionActions.toString(), 1,
			contentDashboardItemVersionActions.size());

		Assert.assertEquals(
			contentDashboardItemVersionAction,
			contentDashboardItemVersionActions.get(0));
	}

	@Test
	public void testIsViewable() throws Exception {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				null, null,
				_getContentDashboardItemActionProviderTracker(
					_getContentDashboardItemActionProvider(
						ContentDashboardItemAction.Type.VIEW,
						"http://localhost:8080/view")),
				null, null, null, null, journalArticle, null, _getLanguage(),
				null, null);

		Assert.assertTrue(
			journalArticleContentDashboardItem.isViewable(
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
			contentDashboardItemAction.getURL(Mockito.any(Locale.class))
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
				Mockito.any(JournalArticle.class),
				Mockito.any(HttpServletRequest.class))
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
				Mockito.any(JournalArticle.class),
				Mockito.any(HttpServletRequest.class))
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
						Mockito.anyString(), Mockito.any())
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
						JournalArticle.class.getName(),
						contentDashboardItemActionProvider.getType())
			).thenReturn(
				Optional.of(contentDashboardItemActionProvider)
			);
		}

		return contentDashboardItemActionProviderTracker;
	}

	private ContentDashboardItemVersionAction
		_getContentDashboardItemVersionAction(
			String icon, String label, String name, String url) {

		ContentDashboardItemVersionAction contentDashboardItemVersionAction =
			Mockito.mock(ContentDashboardItemVersionAction.class);

		Mockito.when(
			contentDashboardItemVersionAction.getIcon()
		).thenReturn(
			icon
		);

		Mockito.when(
			contentDashboardItemVersionAction.getLabel(
				Mockito.any(Locale.class))
		).thenReturn(
			label
		);

		Mockito.when(
			contentDashboardItemVersionAction.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			contentDashboardItemVersionAction.getURL()
		).thenReturn(
			url
		);

		return contentDashboardItemVersionAction;
	}

	private ContentDashboardItemVersionActionProvider
			_getContentDashboardItemVersionActionProvider(
				ContentDashboardItemVersionAction
					contentDashboardItemVersionAction,
				boolean showContentDashboardItemVersionActionProvider)
		throws Exception {

		ContentDashboardItemVersionActionProvider
			contentDashboardItemVersionActionProvider = Mockito.mock(
				ContentDashboardItemVersionActionProvider.class);

		Mockito.when(
			contentDashboardItemVersionActionProvider.
				getContentDashboardItemVersionAction(
					Mockito.any(JournalArticle.class),
					Mockito.any(HttpServletRequest.class))
		).thenReturn(
			contentDashboardItemVersionAction
		);

		Mockito.when(
			contentDashboardItemVersionActionProvider.isShow(
				Mockito.any(JournalArticle.class),
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			showContentDashboardItemVersionActionProvider
		);

		return contentDashboardItemVersionActionProvider;
	}

	private ContentDashboardItemVersionActionProviderTracker
		_getContentDashboardItemVersionActionProviderTracker(
			ContentDashboardItemVersionActionProvider...
				contentDashboardItemVersionActionProviders) {

		ContentDashboardItemVersionActionProviderTracker
			contentDashboardItemVersionActionProviderTracker = Mockito.mock(
				ContentDashboardItemVersionActionProviderTracker.class);

		if (contentDashboardItemVersionActionProviders == null) {
			Mockito.when(
				contentDashboardItemVersionActionProviderTracker.
					getContentDashboardItemVersionActionProviders(
						Mockito.anyString())
			).thenReturn(
				null
			);

			return contentDashboardItemVersionActionProviderTracker;
		}

		Mockito.when(
			contentDashboardItemVersionActionProviderTracker.
				getContentDashboardItemVersionActionProviders(
					JournalArticle.class.getName())
		).thenReturn(
			ListUtil.fromArray(contentDashboardItemVersionActionProviders)
		);

		return contentDashboardItemVersionActionProviderTracker;
	}

	private HttpServletRequest _getHttpServletRequest(long userId)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.USER_ID, userId);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.isContentReviewer(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			true
		);

		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getUserId()
		).thenReturn(
			userId
		);

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

		Mockito.when(
			themeDisplay.getPermissionChecker()
		).thenReturn(
			permissionChecker
		);

		Mockito.when(
			themeDisplay.getUser()
		).thenReturn(
			user
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

	private JournalArticleService _getJournalArticleService(
		JournalArticle... journalArticles) {

		JournalArticleService journalArticleService = Mockito.mock(
			JournalArticleService.class);

		Mockito.when(
			journalArticleService.getArticlesByArticleId(
				Mockito.anyLong(), Mockito.any(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(OrderByComparator.class))
		).thenReturn(
			ListUtil.fromArray(journalArticles)
		);

		return journalArticleService;
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