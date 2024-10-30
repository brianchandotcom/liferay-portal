/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addTypeContentLayout(stagingGroup);
	}

	@Test
	@TestInfo("LPD-40868")
	public void testFragmentEntryLinkWithFragmentEntryInCompanyGroup()
		throws Exception {

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(companyGroup.getGroupId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), companyGroup.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, null, null, 0, false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					companyGroup.getGroupId()));

		StagedModel stagedModel =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), stagingGroup.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid()),
				stagingGroup.getDefaultPublicPlid(), StringPool.BLANK, "html",
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, StringPool.BLANK,
				FragmentConstants.TYPE_COMPONENT,
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		try {
			exportImportStagedModel(stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), liveGroup);

		FragmentEntryLink importedFragmentEntryLink =
			(FragmentEntryLink)importedStagedModel;

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryId(),
			importedFragmentEntryLink.getFragmentEntryId());
	}

	@Test
	public void testStageFragmentEntryLink() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.
				fetchFragmentEntryLinkByUuidAndGroupId(
					stagedModel.getUuid(), stagingGroup.getGroupId());

		stagedModel = _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			TestPropsValues.getUserId(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			fragmentEntryLink.getFragmentEntryId(), fragmentEntryLink.getPlid(),
			"css", "html", "js", fragmentEntryLink.getConfiguration(),
			fragmentEntryLink.getEditableValues(),
			fragmentEntryLink.getNamespace(),
			fragmentEntryLink.getPosition() + 1, fragmentEntryLink.getType(),
			serviceContext);

		try {
			exportImportStagedModel(stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);

		validateImportedStagedModel(stagedModel, importedStagedModel);
	}

	@Test
	public void testStageFragmentEntryLinkWithCollectionEditableValues()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.addTemplate(
			null, TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			_portal.getClassNameId(TemplateEntry.class), 0,
			_portal.getClassNameId(TemplateEntry.class),
			Collections.singletonMap(LocaleUtil.US, "name"),
			Collections.emptyMap(), DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
			StringPool.BLANK, TemplateConstants.LANG_TYPE_FTL,
			"<#-- Empty script -->", new ServiceContext());

		TemplateEntry templateEntry =
			_templateEntryLocalService.addTemplateEntry(
				externalReferenceCode, TestPropsValues.getUserId(),
				stagingGroup.getGroupId(), ddmTemplate.getTemplateId(),
				StringPool.BLANK, StringPool.BLANK, serviceContext);

		String configuration = _read("configuration-valid-all-types.json");

		String editableValues = StringUtil.replace(
			_read("collection-item-template-editable-values.json"),
			"${TEMPLATE_ENTRY_ID}",
			String.valueOf(templateEntry.getTemplateEntryId()));

		StagedModel stagedModel =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), stagingGroup.getGroupId(), 0,
				0,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid()),
				stagingGroup.getDefaultPublicPlid(), StringPool.BLANK, "html",
				StringPool.BLANK, configuration, editableValues,
				StringPool.BLANK, 0, StringPool.BLANK,
				FragmentConstants.TYPE_COMPONENT, serviceContext);

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

		TemplateEntry importedTemplateEntry =
			_templateEntryLocalService.
				fetchTemplateEntryByExternalReferenceCode(
					externalReferenceCode, liveGroup.getGroupId());

		Assert.assertNotNull(importedTemplateEntry);

		FragmentEntryLink fragmentEntryLink =
			(FragmentEntryLink)importedStagedModel;

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject editableValuesJSONObject = jsonObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		JSONObject collectionJSONObject =
			editableValuesJSONObject.getJSONObject("element-text");

		Assert.assertEquals(
			StringBundler.concat(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX,
				StringPool.UNDERLINE,
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX,
				importedTemplateEntry.getTemplateEntryId()),
			collectionJSONObject.getString("collectionFieldId"));
	}

	@Test
	public void testStageFragmentEntryLinkWithCollectionSelector()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				externalReferenceCode, TestPropsValues.getUserId(),
				stagingGroup.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC, serviceContext);

		String configuration = _read("configuration-collection-selector.json");

		String editableValues = StringUtil.replace(
			_read("collection-selector-editable-values.json"),
			new String[] {"${CLASS_NAME}", "${CLASS_NAME_ID}", "${CLASS_PK}"},
			new String[] {
				assetListEntry.getModelClassName(),
				String.valueOf(
					_portal.getClassNameId(assetListEntry.getModelClassName())),
				String.valueOf(assetListEntry.getAssetListEntryId())
			});

		StagedModel stagedModel =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), stagingGroup.getGroupId(), 0,
				0,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid()),
				stagingGroup.getDefaultPublicPlid(), StringPool.BLANK, "html",
				StringPool.BLANK, configuration, editableValues,
				StringPool.BLANK, 0, StringPool.BLANK,
				FragmentConstants.TYPE_COMPONENT, serviceContext);

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

		AssetListEntry importedAssetListEntry =
			_assetListEntryLocalService.
				fetchAssetListEntryByExternalReferenceCode(
					externalReferenceCode, liveGroup.getGroupId());

		Assert.assertNotNull(importedAssetListEntry);

		FragmentEntryLink fragmentEntryLink =
			(FragmentEntryLink)importedStagedModel;

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject freeMarkerJSONObject = jsonObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		JSONObject collectionJSONObject = freeMarkerJSONObject.getJSONObject(
			"collection");

		Assert.assertEquals(
			importedAssetListEntry.getAssetListEntryId(),
			collectionJSONObject.getLong("classPK"));
	}

	@Test
	public void testStageFragmentEntryLinkWithNoFragmentEntry()
		throws Exception {

		StagedModel stagedModel =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), stagingGroup.getGroupId(), 0,
				0,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid()),
				stagingGroup.getDefaultPublicPlid(), StringPool.BLANK, "html",
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, 0, StringPool.BLANK,
				FragmentConstants.TYPE_COMPONENT,
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		try {
			exportImportStagedModel(stagedModel);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), liveGroup);

		Assert.assertNotNull(importedStagedModel);

		validateImportedStagedModel(stagedModel, importedStagedModel);
	}

	@Test
	public void testValidateFragmentEntry() throws Exception {
		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		FragmentEntryLink fragmentEntryLink = (FragmentEntryLink)stagedModel;

		Assert.assertNotNull(
			_fragmentEntryLocalService.getFragmentEntry(
				fragmentEntryLink.getFragmentEntryId()));

		try {
			_exportImportStagedModel(stagedModel, true);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), liveGroup);

		FragmentEntryLink importedFragmentEntryLink =
			(FragmentEntryLink)importedStagedModel;

		Assert.assertNotNull(
			_fragmentEntryLocalService.getFragmentEntry(
				importedFragmentEntryLink.getFragmentEntryId()));
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(group.getGroupId());

		String configuration = _read("configuration-valid-all-types.json");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, configuration, null, 0,
				false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			0, fragmentEntry.getFragmentEntryId(),
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			group.getDefaultPublicPlid(), fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs(),
			fragmentEntry.getConfiguration(), StringPool.BLANK,
			StringPool.BLANK, 1, StringPool.BLANK, fragmentEntry.getType(),
			serviceContext);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _fragmentEntryLinkLocalService.
			getFragmentEntryLinkByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return FragmentEntryLink.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = (FragmentEntryLink)stagedModel;
		FragmentEntryLink importedFragmentEntryLink =
			(FragmentEntryLink)importedStagedModel;

		Assert.assertEquals(
			importedFragmentEntryLink.getCss(), fragmentEntryLink.getCss());
		Assert.assertEquals(
			importedFragmentEntryLink.getHtml(), fragmentEntryLink.getHtml());
		Assert.assertEquals(
			importedFragmentEntryLink.getJs(), fragmentEntryLink.getJs());
		Assert.assertEquals(
			importedFragmentEntryLink.getConfiguration(),
			fragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			importedFragmentEntryLink.getPosition(),
			fragmentEntryLink.getPosition());
	}

	private void _exportImportStagedModel(
			StagedModel stagedModel,
			boolean deleteFragmentEntryAndFragmentEntryLinkBeforeImport)
		throws Exception {

		if (!deleteFragmentEntryAndFragmentEntryLinkBeforeImport) {
			exportImportStagedModel(stagedModel);

			return;
		}

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		FragmentEntryLink fragmentEntryLink = (FragmentEntryLink)stagedModel;

		_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLink);

		_fragmentEntryLocalService.deleteFragmentEntry(
			fragmentEntryLink.getFragmentEntryId());

		initImport();

		StagedModel exportedStagedModel = readExportedStagedModel(stagedModel);

		Assert.assertNotNull(exportedStagedModel);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedModel);
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	private Layout _layout;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private TemplateEntryLocalService _templateEntryLocalService;

}