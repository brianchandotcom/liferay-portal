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

package com.liferay.layout.admin.web.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Máté Thurzó
 */
@RunWith(Arquillian.class)
public class LayoutStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		Layout exportedLayout = (Layout)readExportedStagedModel(layout);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayout);

		Layout exportedLinkedLayout = (Layout)readExportedStagedModel(
			linkedLayout);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLinkedLayout);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

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
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			uuid, group.getGroupId(), false);
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

		Assert.assertEquals(
			dependentStagedModels.toString(), 1, dependentStagedModels.size());

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

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		Assert.assertTrue(
			stagedModel.getCreateDate() + " " +
				importedStagedModel.getCreateDate(),
			DateUtil.equals(
				stagedModel.getCreateDate(),
				importedStagedModel.getCreateDate()));
		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		Layout layout = (Layout)stagedModel;
		Layout importedLayout = (Layout)importedStagedModel;

		Assert.assertEquals(layout.getName(), importedLayout.getName());
		Assert.assertEquals(layout.getTitle(), importedLayout.getTitle());
		Assert.assertEquals(
			layout.getDescription(), importedLayout.getDescription());
		Assert.assertEquals(layout.getKeywords(), importedLayout.getKeywords());
		Assert.assertEquals(layout.getRobots(), importedLayout.getRobots());
		Assert.assertEquals(layout.getType(), importedLayout.getType());
		Assert.assertEquals(
			layout.getFriendlyURL(), importedLayout.getFriendlyURL());
		Assert.assertEquals(layout.getCss(), importedLayout.getCss());
	}

}