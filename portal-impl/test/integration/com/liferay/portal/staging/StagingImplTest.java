/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	SynchronousDestinationExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class StagingImplTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testLocalStagingCategories() throws Exception {
		enableLocalStaging(false, true);
	}

	@Test
	public void testLocalStagingJournal() throws Exception {
		enableLocalStaging(true, false);
	}

	protected AssetCategory addAssetCategory(
			long groupId, String title, String description)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), "TestVocabulary", titleMap,
				descriptionMap, null, serviceContext);

		return AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), 0, titleMap, descriptionMap,
			assetVocabulary.getVocabularyId(), new String[0], serviceContext);
	}

	protected void enableLocalStaging(
			boolean stageJournal, boolean stageCategories)
		throws Exception {

		LayoutTestUtil.addLayout(_group.getGroupId(), "Page1");
		LayoutTestUtil.addLayout(_group.getGroupId(), "Page2");

		int initialPagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			_group, false);

		// Create content

		AssetCategory assetCategory = addAssetCategory(
			_group.getGroupId(), "Title", "content");
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "Title", "content");

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		Map<String, String[]> parameters = StagingUtil.getStagingParameters();

		parameters.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {String.valueOf(stageCategories)});
		parameters.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION + "_" +
				PortletKeys.JOURNAL,
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
			StagingUtil.getStagedPortletId(PortletDataHandlerKeys.CATEGORIES),
			stageCategories);
		serviceContext.setAttribute(
			StagingUtil.getStagedPortletId(PortletKeys.JOURNAL), stageJournal);

		for (String parameterName : parameters.keySet()) {
			serviceContext.setAttribute(
				parameterName, parameters.get(parameterName)[0]);
		}

		// Enable staging

		StagingUtil.enableLocalStaging(
			TestPropsValues.getUserId(), _group, _group, false, false,
			serviceContext);

		Group stagingGroup = _group.getStagingGroup();

		Assert.assertNotNull(stagingGroup);

		Assert.assertEquals(
			initialPagesCount,
			LayoutLocalServiceUtil.getLayoutsCount(stagingGroup, false));

		// Update content in staging

		AssetCategory stagingAssetCategory =
			AssetCategoryLocalServiceUtil.getCategory(
				assetCategory.getUuid(), stagingGroup.getGroupId());

		stagingAssetCategory = updateAssetCategory(
			stagingAssetCategory, "new name");

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

		assetCategory = AssetCategoryLocalServiceUtil.getCategory(
			assetCategory.getUuid(), _group.getGroupId());
		journalArticle = JournalArticleLocalServiceUtil.getArticle(
			_group.getGroupId(), journalArticle.getArticleId());

		if (stageCategories) {
			for (Locale locale : _locales) {
				Assert.assertEquals(
					assetCategory.getTitle(locale),
					stagingAssetCategory.getTitle(locale));
			}
		}
		else {
			for (Locale locale : _locales) {
				Assert.assertNotEquals(
					assetCategory.getTitle(locale),
					stagingAssetCategory.getTitle(locale));
			}
		}

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

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		return AssetCategoryLocalServiceUtil.updateCategory(
			TestPropsValues.getUserId(), category.getCategoryId(),
			category.getParentCategoryId(), titleMap,
			category.getDescriptionMap(), category.getVocabularyId(), null,
			ServiceTestUtil.getServiceContext());
	}

	private static Locale[] _locales = {
		LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US
	};

	private Group _group;

}