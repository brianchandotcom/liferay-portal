/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.lifecycle.constants.ExportImportLifecycleConstants;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Miklos Zakanyi
 */
@RunWith(Arquillian.class)
public class LayoutUtilityPageEntryStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testImportCopyAsNewIfUtilityPageAlreadyExists()
		throws Exception {

		initExport();

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				null, stagingGroup.getGroupId(), 0, 0, true, _testName,
				_testType, 0,
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layoutUtilityPageEntry);

		initImport();

		_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
			null, liveGroup.getGroupId(), 0, 0, true, _testName, _testType, 0,
			ServiceContextTestUtil.getServiceContext(
				liveGroup.getGroupId(), TestPropsValues.getUserId()));

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		LayoutUtilityPageEntry exportedLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)readExportedStagedModel(
				layoutUtilityPageEntry);

		portletDataContext.setDataStrategy(
			PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayoutUtilityPageEntry);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		LayoutUtilityPageEntry importedLayoutUtilityPageEntry =
			_layoutUtilityPageEntryService.getDefaultLayoutUtilityPageEntry(
				liveGroup.getGroupId(), _testType);

		Assert.assertNotEquals(
			layoutUtilityPageEntry.getUuid(),
			importedLayoutUtilityPageEntry.getUuid());
	}

	@Override
	protected StagedModel addStagedModel(
		Group group,
		Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			Layout.class.getSimpleName());

		LayoutUtilityPageEntry parentLayoutUtilityPageEntry = 
			(LayoutUtilityPageEntry)dependentStagedModels.get(0);

		return _addLayoutUtilityPageEntry(
			true, parentLayoutUtilityPageEntry.getGroupId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return _layoutUtilityPageEntryLocalService.
			fetchLayoutUtilityPageEntryByUuidAndGroupId(
				uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return LayoutUtilityPageEntry.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		DateTestUtil.assertEquals(
			stagedModel.getCreateDate(), importedStagedModel.getCreateDate());

		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			(LayoutUtilityPageEntry)stagedModel;
		LayoutUtilityPageEntry importLayoutUtilityPageEntry =
			(LayoutUtilityPageEntry)importedStagedModel;

		Assert.assertEquals(
			layoutUtilityPageEntry.getName(),
			importLayoutUtilityPageEntry.getName());
		Assert.assertEquals(
			layoutUtilityPageEntry.getType(),
			importLayoutUtilityPageEntry.getType());
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
			LayoutUtilityPageEntry.class.getSimpleName());

		Assert.assertEquals(
			dependentStagedModels.toString(), 1, dependentStagedModels.size());

		LayoutUtilityPageEntry parentLayout =
						(LayoutUtilityPageEntry)dependentStagedModels.get(0);

		_layoutUtilityPageEntryLocalService.getLayoutUtilityPageEntry(
			parentLayout.getLayoutUtilityPageEntryId());
	}

	private LayoutUtilityPageEntry _addLayoutUtilityPageEntry(
		boolean defaultLayoutUtilityPageEntry, long groupId)
	throws Exception {

	return _layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
		null, TestPropsValues.getUserId(), groupId, 0, 0,
		defaultLayoutUtilityPageEntry, RandomTestUtil.randomString(),
		LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND, 0,
		ServiceContextTestUtil.getServiceContext(
			groupId, TestPropsValues.getUserId()));
}
	
	private final String _testType = "test";
	private final String _testName = "Test Layout Utility Page";

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

	@Inject
	private Portal _portal;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}