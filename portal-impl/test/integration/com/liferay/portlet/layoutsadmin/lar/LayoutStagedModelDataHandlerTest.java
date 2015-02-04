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

package com.liferay.portlet.layoutsadmin.lar;

import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mate Thurzo
 */
public class LayoutStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Test
	public void testTypeArticle() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, JournalArticle.class, journalArticle);

		Layout layout = LayoutTestUtil.addTypeArticleLayout(
			stagingGroup.getGroupId(), journalArticle.getArticleId());

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
	public void testTypeLinkToLayout() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Layout linkedLayout = LayoutTestUtil.addLayout(stagingGroup);

		List<LayoutFriendlyURL> linkedLayoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				linkedLayout.getPlid());

		addDependentStagedModel(
			dependentStagedModelsMap, Layout.class, linkedLayout);

		addDependentLayoutFriendlyURLs(dependentStagedModelsMap, linkedLayout);

		Layout layout = LayoutTestUtil.addTypeLinkToLayoutLayout(
			stagingGroup.getGroupId(), linkedLayout.getLayoutId());

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

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

		LayoutFriendlyURL linkedLayoutFriendlyURL =
			linkedLayoutFriendlyURLs.get(0);

		LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLByUuidAndGroupId(
			linkedLayoutFriendlyURL.getUuid(), liveGroup.getGroupId());

		LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), false);

		LayoutFriendlyURL layoutFriendlyURL = layoutFriendlyURLs.get(0);

		LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLByUuidAndGroupId(
			layoutFriendlyURL.getUuid(), liveGroup.getGroupId());
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
			new HashMap<>();

		Layout parentLayout = LayoutTestUtil.addLayout(group);

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

		Layout layout = LayoutTestUtil.addLayout(group, parentLayout.getPlid());

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute("exportLAR", Boolean.TRUE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
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

		List<LayoutFriendlyURL> parentLayoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				parentLayout.getPlid());

		LayoutFriendlyURL parentLayoutFriendlyURL =
			parentLayoutFriendlyURLs.get(0);

		LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLByUuidAndGroupId(
			parentLayoutFriendlyURL.getUuid(), group.getGroupId());
	}

}