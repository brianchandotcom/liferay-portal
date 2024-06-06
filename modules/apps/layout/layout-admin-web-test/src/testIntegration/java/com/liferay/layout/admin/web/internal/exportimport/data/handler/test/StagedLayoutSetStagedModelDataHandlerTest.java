/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.lifecycle.constants.ExportImportLifecycleConstants;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class StagedLayoutSetStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testClientExtensionEntries() throws Exception {
		_testClientExtensionEntriesByType(
			ClientExtensionEntryConstants.TYPE_GLOBAL_CSS, "http://css.css");
		_testClientExtensionEntriesByType(
			ClientExtensionEntryConstants.TYPE_THEME_CSS, "http://css.css");
		_testClientExtensionEntriesByType(
			ClientExtensionEntryConstants.TYPE_GLOBAL_JS, "http://js.js");
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			LayoutSet.class.getSimpleName());

		LayoutSet parentLayoutSet = (LayoutSet)dependentStagedModels.get(0); //check this

		return ModelAdapterUtil.adapt(
			_layoutSetLocalService.getLayoutSet(
				group.getGroupId(), parentLayoutSet.isPrivateLayout()),
			LayoutSet.class, StagedLayoutSet.class);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			_layoutSetLocalService.getLayoutSet(group.getGroupId(), false),
			LayoutSet.class, StagedLayoutSet.class);
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return StagedLayoutSet.class;
	}

	private void _testClientExtensionEntriesByType(String type, String url)
		throws Exception {

		initExport();

		ClientExtensionEntry clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				StringPool.BLANK, StringPool.BLANK, type,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"url", url
				).buildString());

		StagedLayoutSet stagedLayoutSet = ModelAdapterUtil.adapt(
			_layoutSetLocalService.getLayoutSet(
				stagingGroup.getGroupId(), false),
			LayoutSet.class, StagedLayoutSet.class);

		LayoutSet stagingLayoutSet = stagedLayoutSet.getLayoutSet();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			_portal.getClassNameId(LayoutSet.class),
			stagingLayoutSet.getLayoutSetId(),
			clientExtensionEntry.getExternalReferenceCode(), type,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId()));

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedLayoutSet);

		initImport();

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		StagedLayoutSet exportedStagedLayoutSet =
			(StagedLayoutSet)readExportedStagedModel(stagedLayoutSet);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedLayoutSet);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		StagedLayoutSet importedStagedLayoutSet = ModelAdapterUtil.adapt(
			_layoutSetLocalService.getLayoutSet(liveGroup.getGroupId(), false),
			LayoutSet.class, StagedLayoutSet.class);

		LayoutSet importedLayoutSet = importedStagedLayoutSet.getLayoutSet();

		Assert.assertEquals(
			1,
			_clientExtensionEntryRelLocalService.
				getClientExtensionEntryRelsCount(
					_portal.getClassNameId(LayoutSet.class),
					importedLayoutSet.getLayoutSetId(), type));

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
			_portal.getClassNameId(LayoutSet.class),
			stagingLayoutSet.getLayoutSetId(), type);

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			_portal.getClassNameId(LayoutSet.class),
			stagingLayoutSet.getLayoutSetId(),
			clientExtensionEntry.getExternalReferenceCode(), type,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId()));

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedLayoutSet);

		initImport();

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		exportedStagedLayoutSet = (StagedLayoutSet)readExportedStagedModel(
			stagedLayoutSet);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedLayoutSet);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		importedStagedLayoutSet = ModelAdapterUtil.adapt(
			_layoutSetLocalService.getLayoutSet(liveGroup.getGroupId(), false),
			LayoutSet.class, StagedLayoutSet.class);

		importedLayoutSet = importedStagedLayoutSet.getLayoutSet();

		Assert.assertEquals(
			1,
			_clientExtensionEntryRelLocalService.
				getClientExtensionEntryRelsCount(
					_portal.getClassNameId(LayoutSet.class),
					importedLayoutSet.getLayoutSetId(), type));
	}

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

}