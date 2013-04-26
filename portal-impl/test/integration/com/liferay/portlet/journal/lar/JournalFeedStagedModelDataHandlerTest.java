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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JournalFeedStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addLayout(
			stagingGroup.getGroupId(), ServiceTestUtil.randomString());

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
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate);

		ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		DDMStructure ddmStructure = (DDMStructure)dependentStagedModels.get(0);

		dependentStagedModels = dependentStagedModelsMap.get(
			DDMTemplate.class.getSimpleName());

		DDMTemplate ddmTemplate = (DDMTemplate)dependentStagedModels.get(0);

		DDMTemplate rendererTemplate = (DDMTemplate)dependentStagedModels.get(
			1);

		return JournalTestUtil.addFeed(
			group.getGroupId(), _layout.getPlid(),
			ServiceTestUtil.randomString(), ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), rendererTemplate.getTemplateKey());
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

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMTemplate.class.getSimpleName());

		Assert.assertEquals(2, dependentStagedModels.size());

		for (StagedModel stagedModel : dependentStagedModels) {
			DDMTemplate ddmTemplate = (DDMTemplate)stagedModel;

			DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
				ddmTemplate.getUuid(), group.getGroupId());
		}

		dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		DDMStructure ddmStructure = (DDMStructure)dependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());
	}

	private Layout _layout;

}