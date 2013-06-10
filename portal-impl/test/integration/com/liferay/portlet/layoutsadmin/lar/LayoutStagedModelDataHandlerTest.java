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

package com.liferay.portlet.layoutsadmin.lar;

import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		PersistenceExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LayoutStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Test
	@Transactional
	public void testTypeArticle() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, JournalArticle.class, journalArticle);

		Layout layout = LayoutTestUtil.addLayoutTypeArticle(
			stagingGroup.getGroupId(), ServiceTestUtil.randomString(),
			journalArticle.getArticleId());

		addDependentLayoutFriendlyURLs(dependentStagedModelsMap, layout);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

		validateExport(portletDataContext, layout, dependentStagedModelsMap);

		initImport();

		Layout exportedLayout = (Layout)readExportedStagedModel(layout);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayout);
	}

	@Test
	@Transactional
	public void testTypeLinkToLayout() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		Layout linkedLayout = LayoutTestUtil.addLayout(
			stagingGroup.getGroupId(), ServiceTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, Layout.class, linkedLayout);

		addDependentLayoutFriendlyURLs(dependentStagedModelsMap, linkedLayout);

		Layout layout = LayoutTestUtil.addLayoutTypeLinkedToLayout(
			stagingGroup.getGroupId(), ServiceTestUtil.randomString(),
			linkedLayout.getLayoutId());

		addDependentLayoutFriendlyURLs(dependentStagedModelsMap, layout);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

		validateExport(portletDataContext, layout, dependentStagedModelsMap);

		initImport();

		Layout exportedLayout = (Layout)readExportedStagedModel(layout);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayout);

		LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			linkedLayout.getUuid(), liveGroup.getGroupId(), false);

		LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), false);
	}

	protected void addDependentLayoutFriendlyURLs(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Layout layout)
		throws Exception {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			addDependentStagedModel(
				dependentStagedModelsMap, LayoutFriendlyURL.class,
				layoutFriendlyURL);
		}
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		Layout parentLayout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, Layout.class, parentLayout);

		addDependentLayoutFriendlyURLs(dependentStagedModelsMap, parentLayout);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			Layout.class.getSimpleName());

		Layout parentLayout = (Layout)dependentStagedModels.get(0);

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString(),
			parentLayout.getPlid());

		addDependentLayoutFriendlyURLs(dependentStagedModelsMap, layout);

		return layout;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				uuid, group.getGroupId(), false);
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Layout.class;
	}

	@Override
	protected void initExport() throws Exception {
		super.initExport();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAttribute("exportLAR", true);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			Layout.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		Layout parentLayout = (Layout)dependentStagedModels.get(0);

		LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			parentLayout.getUuid(), group.getGroupId(), false);
	}

}