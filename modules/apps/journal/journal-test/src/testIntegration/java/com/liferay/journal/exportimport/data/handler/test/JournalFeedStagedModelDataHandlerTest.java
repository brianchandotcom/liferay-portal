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

package com.liferay.journal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class JournalFeedStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addLayout(stagingGroup);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(_layout.getUuid());

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), liveGroup.getGroupId(),
			_layout.isPrivateLayout(), _layout.getParentLayoutId(),
			_layout.getName(), _layout.getTitle(), _layout.getDescription(),
			_layout.getType(), _layout.isHidden(), _layout.getFriendlyURL(),
			serviceContext);

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		PortalPreferences portalPreferenceces =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				TestPropsValues.getUserId(), true);

		_originalPortalPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portalPreferenceces);

		portalPreferenceces.setValue(
			"", "publishToLiveByDefaultEnabled", "true");
		portalPreferenceces.setValue(
			"", "versionHistoryByDefaultEnabled", "true");
		portalPreferenceces.setValue("", "articleCommentsEnabled", "true");
		portalPreferenceces.setValue(
			"", "expireAllArticleVersionsEnabled", "true");
		portalPreferenceces.setValue("", "folderIconCheckCountEnabled", "true");
		portalPreferenceces.setValue(
			"", "indexAllArticleVersionsEnabled", "true");
		portalPreferenceces.setValue(
			"", "databaseContentKeywordSearchEnabled", "true");
		portalPreferenceces.setValue("", "journalArticleStorageType", "json");
		portalPreferenceces.setValue(
			"", "journalArticlePageBreakToken", "@page_break@");

		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferenceces));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			_originalPortalPreferencesXML);
	}

	@Override
	@Test
	public void testCleanStagedModelDataHandler() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.journal.internal.exportimport.data.handler." +
						"JournalFeedStagedModelDataHandler",
					Level.WARN)) {

			super.testCleanStagedModelDataHandler();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			String message = (String)loggingEvent.getMessage();

			Assert.assertTrue(
				message, message.startsWith("A feed with the ID "));
			Assert.assertTrue(
				message,
				message.contains(" already exists. The new generated ID is "));
		}
	}

	@Test
	public void testDoubleExportImport() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			exportImportStagedModel(stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			exportImportStagedModel(stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		importedStagedModel = getStagedModel(stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);
	}

	@Override
	@Test
	public void testStagedModelDataHandler() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.journal.internal.exportimport.data.handler." +
						"JournalFeedStagedModelDataHandler",
					Level.WARN)) {

			super.testStagedModelDataHandler();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			String message = (String)loggingEvent.getMessage();

			Assert.assertTrue(
				message, message.startsWith("A feed with the ID "));
			Assert.assertTrue(
				message,
				message.contains(" already exists. The new generated ID is "));
		}
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		for (int i = 0; i < 2; i++) {
			DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
				group.getGroupId(), ddmStructure.getStructureId(),
				PortalUtil.getClassNameId(JournalArticle.class));

			addDependentStagedModel(
				dependentStagedModelsMap, DDMTemplate.class, ddmTemplate);
		}

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		DDMTemplate ddmTemplate =
			(DDMTemplate)ddmTemplateDependentStagedModels.get(0);

		DDMTemplate rendererDDMTemplate =
			(DDMTemplate)ddmTemplateDependentStagedModels.get(1);

		return JournalTestUtil.addFeed(
			group.getGroupId(), _layout.getPlid(),
			RandomTestUtil.randomString(), ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), rendererDDMTemplate.getTemplateKey());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return JournalFeedLocalServiceUtil.getJournalFeedByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return JournalFeed.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(
			ddmStructureDependentStagedModels.toString(), 1,
			ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(
			ddmTemplateDependentStagedModels.toString(), 2,
			ddmTemplateDependentStagedModels.size());

		for (StagedModel ddmTemplateDependentStagedModel :
				ddmTemplateDependentStagedModels) {

			DDMTemplate ddmTemplate =
				(DDMTemplate)ddmTemplateDependentStagedModel;

			DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
				ddmTemplate.getUuid(), group.getGroupId());
		}
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		JournalFeed feed = (JournalFeed)stagedModel;
		JournalFeed importedFeed = (JournalFeed)importedStagedModel;

		Assert.assertEquals(feed.getName(), importedFeed.getName());
		Assert.assertEquals(feed.getDelta(), importedFeed.getDelta());
		Assert.assertEquals(feed.getOrderByCol(), importedFeed.getOrderByCol());
		Assert.assertEquals(
			feed.getOrderByType(), importedFeed.getOrderByType());
		Assert.assertEquals(
			feed.getContentField(), importedFeed.getContentField());
	}

	private Layout _layout;
	private String _originalPortalPreferencesXML;

}