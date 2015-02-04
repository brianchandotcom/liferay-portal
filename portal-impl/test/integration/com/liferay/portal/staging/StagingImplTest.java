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

package com.liferay.portal.staging;

import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetBranchConstants;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.StagingLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Julio Camarero
 * @author Daniel Kocsis
 */
@Sync(cleanTransaction = true)
public class StagingImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testLocalStaging() throws Exception {
		enableLocalStaging(false);
	}

	@Test
	public void testLocalStagingAssetCategories() throws Exception {
		enableLocalStagingWithContent(false, true, false);
	}

	@Test
	public void testLocalStagingJournal() throws Exception {
		enableLocalStagingWithContent(true, false, false);
	}

	@Test
	public void testLocalStagingUpdateLastPublishDate() throws Exception {
		enableLocalStagingWithContent(true, false, false);

		Group stagingGroup = _group.getStagingGroup();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_group.getGroupId(), false);

		Assert.assertNull(ExportImportDateUtil.getLastPublishDate(layoutSet));

		layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			stagingGroup.getGroupId(), false);

		Assert.assertNotNull(
			ExportImportDateUtil.getLastPublishDate(layoutSet));

		Layout layout = new LayoutImpl();

		layout.setCompanyId(_group.getCompanyId());
		layout.setGroupId(_group.getGroupId());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, PortletKeys.JOURNAL);

		Assert.assertNull(
			ExportImportDateUtil.getLastPublishDate(portletPreferences));

		layout.setGroupId(stagingGroup.getGroupId());

		portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, PortletKeys.JOURNAL);

		Assert.assertNotNull(
			ExportImportDateUtil.getLastPublishDate(portletPreferences));
	}

	@Test
	public void testLocalStagingWithLayoutVersioning() throws Exception {
		enableLocalStaging(true);
	}

	@Test
	public void testLocalStagingWithLayoutVersioningAssetCategories()
		throws Exception {

		enableLocalStagingWithContent(false, true, true);
	}

	@Test
	public void testLocalStagingWithLayoutVersioningJournal() throws Exception {
		enableLocalStagingWithContent(true, false, true);
	}

	protected AssetCategory addAssetCategory(
			long groupId, String title, String description)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), "TestVocabulary", titleMap,
				descriptionMap, null, serviceContext);

		return AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), 0, titleMap, descriptionMap,
			assetVocabulary.getVocabularyId(), new String[0], serviceContext);
	}

	protected void enableLocalStaging(boolean branching) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Map<String, String[]> stagingParameters =
			StagingUtil.getStagingParameters();

		for (String stagingParameterName : stagingParameters.keySet()) {
			serviceContext.setAttribute(
				stagingParameterName,
				stagingParameters.get(stagingParameterName)[0]);
		}

		if (branching) {
			serviceContext.setSignedIn(true);
		}

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		enableLocalStaging(branching, serviceContext);

		ServiceContextThreadLocal.popServiceContext();

		if (!branching) {
			return;
		}

		UnicodeProperties typeSettingsProperties =
			_group.getTypeSettingsProperties();

		Assert.assertTrue(
			GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("branchingPrivate")));
		Assert.assertTrue(
			GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("branchingPublic")));

		Group stagingGroup = _group.getStagingGroup();

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
				stagingGroup.getGroupId(), false,
				LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		Assert.assertNotNull(layoutSetBranch);

		layoutSetBranch = LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
			stagingGroup.getGroupId(), true,
			LayoutSetBranchConstants.MASTER_BRANCH_NAME);

		Assert.assertNotNull(layoutSetBranch);
	}

	protected void enableLocalStaging(
			boolean branching, ServiceContext serviceContext)
		throws Exception {

		int initialLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			_group, false);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), _group, branching, branching,
			serviceContext);

		Group stagingGroup = _group.getStagingGroup();

		Assert.assertNotNull(stagingGroup);
		Assert.assertEquals(
			initialLayoutsCount,
			LayoutLocalServiceUtil.getLayoutsCount(stagingGroup, false));
	}

	protected void enableLocalStagingWithContent(
			boolean stageJournal, boolean stageAssetCategories,
			boolean branching)
		throws Exception {

		// Layouts

		LayoutTestUtil.addLayout(_group);
		LayoutTestUtil.addLayout(_group);

		// Create content

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "Title", "content");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Map<String, String[]> parameters = StagingUtil.getStagingParameters();

		parameters.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION +
				StringPool.UNDERLINE + PortletKeys.JOURNAL,
			new String[] {String.valueOf(stageJournal)});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.FALSE.toString()});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				PortletKeys.JOURNAL,
			new String[] {String.valueOf(stageJournal)});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_SETUP + StringPool.UNDERLINE +
				PortletKeys.JOURNAL,
			new String[] {String.valueOf(stageJournal)});

		serviceContext.setAttribute(
			StagingUtil.getStagedPortletId(PortletKeys.JOURNAL), stageJournal);

		for (String parameterName : parameters.keySet()) {
			serviceContext.setAttribute(
				parameterName, parameters.get(parameterName)[0]);
		}

		enableLocalStaging(branching, serviceContext);

		Group stagingGroup = _group.getStagingGroup();

		// Update content in staging

		JournalArticle stagingJournalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				stagingGroup.getGroupId(), journalArticle.getUrlTitle());

		stagingJournalArticle = JournalTestUtil.updateArticle(
			stagingJournalArticle, "Title2",
			stagingJournalArticle.getContent());

		// Publish to live

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			_group.getGroupId(), false, parameters, null, null);

		// Retrieve content from live after publishing

		journalArticle = JournalArticleLocalServiceUtil.getArticle(
			_group.getGroupId(), journalArticle.getArticleId());

		if (stageJournal) {
			for (Locale locale : _locales) {
				Assert.assertEquals(
					journalArticle.getTitle(locale),
					stagingJournalArticle.getTitle(locale));
			}
		}
		else {
			for (Locale locale : _locales) {
				Assert.assertNotEquals(
					journalArticle.getTitle(locale),
					stagingJournalArticle.getTitle(locale));
			}
		}
	}

	protected AssetCategory updateAssetCategory(
			AssetCategory category, String name)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		return AssetCategoryLocalServiceUtil.updateCategory(
			TestPropsValues.getUserId(), category.getCategoryId(),
			category.getParentCategoryId(), titleMap,
			category.getDescriptionMap(), category.getVocabularyId(), null,
			ServiceContextTestUtil.getServiceContext());
	}

	private static final Locale[] _locales = {
		LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US
	};

	@DeleteAfterTestRun
	private Group _group;

}