/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class AssetDisplayPageStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception, PortalException {
		super.setUp();

		_classNameId = PortalUtil.getClassNameId(JournalArticle.class);

		Group group = GroupLocalServiceUtil.getCompanyGroup(
			TestPropsValues.getCompanyId());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_classPK = journalArticle.getResourcePrimKey();

		_classTypeKey = ddmStructure.getStructureKey();
	}

	@Test
	public void testExportImport() throws Exception {
		initExport();

		String externalReferenceCode = RandomTestUtil.randomString();

		StagedModel stagedModel = _addAssetDisplayPageEntry(
			externalReferenceCode, stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		try (SafeCloseable safeCloseable = initImportWithSafeCloseable()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(stagedModel);

			Assert.assertNotNull(
				element.attributeValue("layoutPageTemplateEntryERC"));

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNotNull(exportedStagedModel);

			ExportImportThreadLocal.setPortletImportInProcess(true);

			try {
				AssertUtils.assertFailure(
					PortalException.class,
					StringBundler.concat(
						"No LayoutPageTemplateEntry exists with the key ",
						"{externalReferenceCode=", externalReferenceCode,
						", groupId=", liveGroup.getGroupId(), "}"),
					() -> StagedModelDataHandlerUtil.importStagedModel(
						portletDataContext, exportedStagedModel));
			}
			finally {
				ExportImportThreadLocal.setPortletImportInProcess(false);
			}
		}

		_layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				externalReferenceCode, TestPropsValues.getUserId(),
				liveGroup.getGroupId(), 0, null, _classNameId, _classTypeKey,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0,
				0, 0, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					liveGroup.getGroupId(), TestPropsValues.getUserId()));

		try (SafeCloseable safeCloseable = initImportWithSafeCloseable()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(stagedModel);

			Assert.assertNotNull(
				element.attributeValue("layoutPageTemplateEntryERC"));

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNotNull(exportedStagedModel);

			ExportImportThreadLocal.setPortletImportInProcess(true);

			try {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, exportedStagedModel);
			}
			finally {
				ExportImportThreadLocal.setPortletImportInProcess(false);
			}

			AssetDisplayPageEntry importedAssetDisplayPageEntry =
				_assetDisplayPageEntryLocalService.
					fetchAssetDisplayPageEntryByUuidAndGroupId(
						exportedStagedModel.getUuid(), liveGroup.getGroupId());

			Assert.assertNotNull(importedAssetDisplayPageEntry);

			validateImportedStagedModel(
				stagedModel, importedAssetDisplayPageEntry);
		}
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				externalReferenceCode, TestPropsValues.getUserId(),
				liveGroup.getGroupId(), 0, null, _classNameId, _classTypeKey,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0,
				0, 0, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					liveGroup.getGroupId(), TestPropsValues.getUserId()));

		return _addAssetDisplayPageEntry(externalReferenceCode, group);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return _assetDisplayPageEntryLocalService.
			fetchAssetDisplayPageEntryByUuidAndGroupId(
				uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return AssetDisplayPageEntry.class;
	}

	@Override
	protected void initExport(Group exportGroup) throws Exception {
		super.initExport(exportGroup);

		portletDataContext.setPortletId(
			"com_liferay_journal_web_portlet_JournalPortlet");
	}

	@Override
	protected SafeCloseable initImportWithSafeCloseable(
			Group exportGroup, Group importGroup)
		throws Exception {

		SafeCloseable safeCloseable = super.initImportWithSafeCloseable(
			exportGroup, importGroup);

		portletDataContext.setPortletId(
			"com_liferay_journal_web_portlet_JournalPortlet");

		return safeCloseable;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		DateTestUtil.assertEquals(
			stagedModel.getCreateDate(), importedStagedModel.getCreateDate());

		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());

		AssetDisplayPageEntry assetDisplayPageEntry =
			(AssetDisplayPageEntry)stagedModel;
		AssetDisplayPageEntry importedAssetDisplayPageEntry =
			(AssetDisplayPageEntry)importedStagedModel;

		Assert.assertEquals(
			assetDisplayPageEntry.getClassNameId(),
			importedAssetDisplayPageEntry.getClassNameId());
		Assert.assertEquals(
			_layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			importedAssetDisplayPageEntry.getLayoutPageTemplateEntryId());
	}

	private AssetDisplayPageEntry _addAssetDisplayPageEntry(
			String externalReferenceCode, Group group)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				externalReferenceCode, TestPropsValues.getUserId(),
				group.getGroupId(), 0, null, _classNameId, _classTypeKey,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0,
				0, 0, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					liveGroup.getGroupId(), TestPropsValues.getUserId()));

		return _assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), group.getGroupId(), _classNameId,
			_classPK, layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			new ServiceContext());
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	private long _classNameId;
	private long _classPK;
	private String _classTypeKey;
	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}