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

package com.liferay.journal.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.exportimport.data.handler.JournalFeedStagedModelDataHandler;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync
public class JournalFeedStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addLayout(stagingGroup);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(_layout.getUuid());

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), liveGroup.getGroupId(),
			_layout.getPrivateLayout(), _layout.getParentLayoutId(),
			_layout.getName(), _layout.getTitle(), _layout.getDescription(),
			_layout.getType(), _layout.getHidden(), _layout.getFriendlyURL(),
			serviceContext);
	}

	@Override
	@Test
	public void testCleanStagedModelDataHandler() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					JournalFeedStagedModelDataHandler.class.getName(),
					Level.WARN)) {

			super.testCleanStagedModelDataHandler();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

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
	@Test
	public void testStagedModelDataHandler() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					JournalFeedStagedModelDataHandler.class.getName(),
					Level.WARN)) {

			super.testStagedModelDataHandler();

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(1, loggingEvents.size());

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
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return JournalFeedLocalServiceUtil.getJournalFeedByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
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

		Assert.assertEquals(1, ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(2, ddmTemplateDependentStagedModels.size());

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

}