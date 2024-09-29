/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.lifecycle.constants.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;
import com.liferay.template.test.util.TemplateTestUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

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
	public void testClientExtensionEntries() throws Exception {
		_testClientExtensionEntries(
			ClientExtensionEntryConstants.TYPE_GLOBAL_CSS, "http://css.css");
		_testClientExtensionEntries(
			ClientExtensionEntryConstants.TYPE_GLOBAL_JS, "http://js.js");
		_testClientExtensionEntries(
			ClientExtensionEntryConstants.TYPE_THEME_CSS, "http://css.css");
	}

	@Test
	public void testCompanyScopedPortletOnContentLayoutHasCorrectAttributes()
		throws Exception {

		ServiceRegistration<Portlet> serviceRegistration =
			_registerTestPortlet();

		try {
			initExport();

			Layout layout = LayoutTestUtil.addTypeContentLayout(stagingGroup);

			Layout draftLayout = layout.fetchDraftLayout();

			String portletId = _addPortletToLayout(draftLayout);

			PortletPreferencesIds portletPreferencesIds =
				_portletPreferencesFactory.getPortletPreferencesIds(
					draftLayout.getCompanyId(), draftLayout.getGroupId(), 0,
					draftLayout.getPlid(), portletId);

			PortletPreferences jxPortletPreferences =
				_portletPreferencesLocalService.fetchPreferences(
					portletPreferencesIds);

			jxPortletPreferences.setValue("lfrScopeType", "company");

			_portletPreferencesLocalService.updatePreferences(
				portletPreferencesIds.getOwnerId(),
				portletPreferencesIds.getOwnerType(),
				portletPreferencesIds.getPlid(),
				portletPreferencesIds.getPortletId(), jxPortletPreferences);

			ContentLayoutTestUtil.publishLayout(draftLayout, layout);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, layout);

			initImport();

			Company company = _companyLocalService.getCompany(
				liveGroup.getCompanyId());

			validatePortletAttributes(
				layout.getUuid(), portletId, company.getGroupId(), "company");
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testCompanyScopedPortletOnPortletLayoutHasCorrectAttributes()
		throws Exception {

		ServiceRegistration<Portlet> serviceRegistration =
			_registerTestPortlet();

		try {
			initExport();

			Layout layout = LayoutTestUtil.addTypePortletLayout(
				stagingGroup.getGroupId());

			String portletId = LayoutTestUtil.addPortletToLayout(
				layout, _TEST_PORTLET_NAME,
				HashMapBuilder.put(
					"lfrScopeType", new String[] {"company"}
				).build());

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, layout);

			initImport();

			Company company = _companyLocalService.getCompany(
				liveGroup.getCompanyId());

			validatePortletAttributes(
				layout.getUuid(), portletId, company.getGroupId(), "company");
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	@TestInfo("LPD-32929")
	public void testExportImportContentReference() throws Exception {
		Locale locale = _portal.getSiteDefaultLocale(stagingGroup);

		JournalArticle journalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				stagingGroup.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				DDMStructureTestUtil.getSampleStructuredContent(
					"content",
					Collections.singletonList(
						HashMapBuilder.put(
							locale, RandomTestUtil.randomString()
						).build()),
					LocaleUtil.toLanguageId(locale)),
				"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT");

		_assertExportImportContentReference(
			journalArticle.getResourcePrimKey(), "title",
			JournalArticle.class.getName(),
			String.valueOf(journalArticle.getDDMStructureId()),
			() -> {
				JournalArticle importedJournalArticle =
					_journalArticleLocalService.
						getJournalArticleByUuidAndGroupId(
							journalArticle.getUuid(), liveGroup.getGroupId());

				return importedJournalArticle.getResourcePrimKey();
			});
	}

	@Test
	@TestInfo("LPD-32929")
	public void testExportImportContentReferenceWithoutAssetEntry()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			stagingGroup.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			stagingGroup.getGroupId(), assetVocabulary.getVocabularyId());

		_assertExportImportContentReference(
			assetCategory.getCategoryId(), "name",
			AssetCategory.class.getName(), StringPool.BLANK,
			() -> {
				AssetCategory importedAssetCategory =
					_assetCategoryLocalService.getAssetCategoryByUuidAndGroupId(
						assetCategory.getUuid(), liveGroup.getGroupId());

				return importedAssetCategory.getCategoryId();
			});
	}

	@Test
	@TestInfo("LPS-121201")
	public void testExportLayoutWithCustomCanonicalURL() throws Exception {
		initExport();

		Layout layout = LayoutTestUtil.addTypeContentLayout(stagingGroup);

		_updateLayoutSEOEntry(layout);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);
	}

	@Test
	public void testLayoutPageTemplateEntry() throws Exception {
		initExport();

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), stagingGroup.getGroupId(), 0,
				"Test Master Page",
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId()));

		Layout layout = LayoutTestUtil.addTypeContentLayout(stagingGroup);

		layout = _layoutLocalService.updateMasterLayoutPlid(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			masterLayoutPageTemplateEntry.getPlid());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

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

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		LayoutPageTemplateEntry importedMasterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByUuidAndGroupId(
					masterLayoutPageTemplateEntry.getUuid(),
					liveGroup.getGroupId());

		Assert.assertNotEquals(
			masterLayoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			importedMasterLayoutPageTemplateEntry.
				getLayoutPageTemplateEntryId());
		Assert.assertNotEquals(
			masterLayoutPageTemplateEntry.getPlid(),
			importedMasterLayoutPageTemplateEntry.getPlid());

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

		Assert.assertNotEquals(
			layout.getMasterLayoutPlid(), importedLayout.getMasterLayoutPlid());
	}

	@Test
	@TestInfo("LPD-37740")
	public void testLayoutWithLayoutSeoEntryPublicationShouldNotFailWhenHasFragmentMappedToLayout()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout1 = LayoutTestUtil.addTypeContentLayout(group);
		Layout layout2 = LayoutTestUtil.addTypeContentLayout(group);

		LayoutSEOEntry layoutSEOEntry = _updateLayoutSEOEntry(layout1);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);

		Group stagingGroup = group.getStagingGroup();

		_assertLayoutSEOEntry(
			layoutSEOEntry.getCanonicalURLMap(), stagingGroup.getGroupId(),
			layoutSEOEntry.getUuid());

		Layout stagingLayout1 = _layoutLocalService.fetchLayout(
			layout1.getUuid(), stagingGroup.getGroupId(), false);
		Layout stagingLayout2 = _layoutLocalService.fetchLayout(
			layout2.getUuid(), stagingGroup.getGroupId(), false);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				"<div class=\"fragment_24\"><a href=\"#link\" " +
					"data-lfr-editable-id=\"link-1\" data-lfr-editable-type=" +
						"\"link\">textLink1</a></div>",
				StringPool.BLANK, false, StringPool.BLANK, null, 0, false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"link-1",
						JSONUtil.put(
							"config",
							JSONUtil.put(
								"layout",
								JSONUtil.put(
									"groupId", stagingLayout2.getGroupId()
								).put(
									"layoutId", stagingLayout2.getLayoutId()
								).put(
									"layoutUuid", stagingLayout2.getUuid()
								).put(
									"privateLayout",
									stagingLayout2.isPrivateLayout()
								).put(
									"title", stagingLayout2.getTitle()
								))
						).put(
							"defaultValue", "textLink1"
						))
				).toString(),
				fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
				fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), stagingLayout1,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				null, 0,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(stagingLayout1.getPlid()));

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			fragmentEntryLink, layout1.fetchDraftLayout(), null, 0,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				stagingLayout1.getPlid()));

		_publishLayouts(group, stagingGroup);
	}

	@Test
	@TestInfo("LPD-35629")
	public void testLocalStagingWithMasterLayoutWithSameLayoutId()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		Layout masterLayout = _addMasterLayout(serviceContext);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);

		Group stagingGroup = group.getStagingGroup();

		Layout stagingLayout = _layoutLocalService.fetchLayout(
			layout.getUuid(), stagingGroup.getGroupId(), false);

		LayoutSEOEntry stagingLayoutSEOEntry = _updateLayoutSEOEntry(
			stagingLayout);

		_publishLayouts(group, stagingGroup);

		_assertLayoutSEOEntry(
			stagingLayoutSEOEntry.getCanonicalURLMap(), group.getGroupId(),
			stagingLayoutSEOEntry.getUuid());

		Layout stagingMasterLayout = _layoutLocalService.fetchLayout(
			masterLayout.getUuid(), stagingGroup.getGroupId(), true);

		stagingLayout = _layoutLocalService.updateMasterLayoutPlid(
			stagingGroup.getGroupId(), false, stagingLayout.getLayoutId(),
			stagingMasterLayout.getPlid());

		stagingMasterLayout.setLayoutId(layout.getLayoutId());

		stagingMasterLayout = _layoutLocalService.updateLayout(
			stagingMasterLayout);

		Assert.assertEquals(
			layout.getLayoutId(), stagingMasterLayout.getLayoutId());

		_publishLayouts(group, stagingGroup);

		layout = _layoutLocalService.fetchLayout(
			stagingLayout.getUuid(), group.getGroupId(), false);
		masterLayout = _layoutLocalService.fetchLayout(
			stagingMasterLayout.getUuid(), group.getGroupId(), true);

		Assert.assertEquals(layout.getLayoutId(), masterLayout.getLayoutId());

		stagingLayoutSEOEntry = _updateLayoutSEOEntry(stagingLayout);

		_publishLayouts(group, stagingGroup);

		_assertLayoutSEOEntry(
			stagingLayoutSEOEntry.getCanonicalURLMap(), group.getGroupId(),
			stagingLayoutSEOEntry.getUuid());
	}

	@Test
	public void testStyleBookEntry() throws Exception {
		initExport();

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				null, TestPropsValues.getUserId(), stagingGroup.getGroupId(),
				false, StringPool.BLANK, RandomTestUtil.randomString(),
				StringPool.BLANK, RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(
					stagingGroup.getGroupId()));

		Layout layout = LayoutTestUtil.addTypeContentLayout(stagingGroup);

		layout = _layoutLocalService.updateStyleBookEntryId(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			styleBookEntry.getStyleBookEntryId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

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

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

		Assert.assertNotEquals(
			layout.getStyleBookEntryId(), importedLayout.getStyleBookEntryId());
		Assert.assertNotNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				importedLayout.getStyleBookEntryId()));
	}

	@Test
	public void testTypeLinkToLayout() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Layout linkedLayout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		List<LayoutFriendlyURL> linkedLayoutFriendlyURLs =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURLs(
				linkedLayout.getPlid());

		addDependentStagedModel(
			dependentStagedModelsMap, Layout.class, linkedLayout);

		_addDependentFriendlyURLEntries(dependentStagedModelsMap, linkedLayout);
		_addDependentLayoutFriendlyURLs(dependentStagedModelsMap, linkedLayout);

		Layout layout = LayoutTestUtil.addTypeLinkToLayoutLayout(
			stagingGroup.getGroupId(), linkedLayout.getLayoutId());

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURLs(
				layout.getPlid());

		_addDependentFriendlyURLEntries(dependentStagedModelsMap, layout);
		_addDependentLayoutFriendlyURLs(dependentStagedModelsMap, layout);

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

		_layoutLocalService.getLayoutByUuidAndGroupId(
			linkedLayout.getUuid(), liveGroup.getGroupId(), false);

		LayoutFriendlyURL linkedLayoutFriendlyURL =
			linkedLayoutFriendlyURLs.get(0);

		_layoutFriendlyURLLocalService.getLayoutFriendlyURLByUuidAndGroupId(
			linkedLayoutFriendlyURL.getUuid(), liveGroup.getGroupId());

		_layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), false);

		LayoutFriendlyURL layoutFriendlyURL = layoutFriendlyURLs.get(0);

		_layoutFriendlyURLLocalService.getLayoutFriendlyURLByUuidAndGroupId(
			layoutFriendlyURL.getUuid(), liveGroup.getGroupId());
	}

	@Test
	public void testTypeLinkToURL() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		String fileName = "PDF_Test.pdf";

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.APPLICATION_PDF,
			FileUtil.getBytes(getClass(), "dependencies/" + fileName), null,
			null, null,
			ServiceContextTestUtil.getServiceContext(
				liveGroup.getGroupId(), TestPropsValues.getUserId()));

		String stagingPreviewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);

		addDependentStagedModel(
			dependentStagedModelsMap, DLFileEntry.class, fileEntry);

		Layout layout = LayoutTestUtil.addTypeLinkToURLLayout(
			stagingGroup.getGroupId(), stagingPreviewURL);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, fileEntry);
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

		FileEntry exportedFileEntry = (FileEntry)readExportedStagedModel(
			fileEntry);
		Layout exportedLayout = (Layout)readExportedStagedModel(layout);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedFileEntry);
		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayout);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		FileEntry importedFileEntry =
			_dlAppLocalService.getFileEntryByUuidAndGroupId(
				fileEntry.getUuid(), liveGroup.getGroupId());

		String livePreviewURL = _dlURLHelper.getPreviewURL(
			importedFileEntry, importedFileEntry.getFileVersion(), null,
			StringPool.BLANK);

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

		UnicodeProperties typeSettingsUnicodeProperties =
			importedLayout.getTypeSettingsProperties();

		String liveLinkedURL = GetterUtil.getString(
			typeSettingsUnicodeProperties.getProperty("url"));

		Assert.assertEquals(
			HttpComponentsUtil.removeParameter(livePreviewURL, "t"),
			HttpComponentsUtil.removeParameter(liveLinkedURL, "t"));
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(group);

		addDependentStagedModel(
			dependentStagedModelsMap, Layout.class, parentLayout);

		_addDependentFriendlyURLEntries(dependentStagedModelsMap, parentLayout);
		_addDependentLayoutFriendlyURLs(dependentStagedModelsMap, parentLayout);

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

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			group, parentLayout.getPlid());

		_addDependentFriendlyURLEntries(dependentStagedModelsMap, layout);
		_addDependentLayoutFriendlyURLs(dependentStagedModelsMap, layout);

		return layout;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _layoutLocalService.getLayoutByUuidAndGroupId(
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

		_layoutLocalService.getLayoutByUuidAndGroupId(
			parentLayout.getUuid(), group.getGroupId(), false);

		List<LayoutFriendlyURL> parentLayoutFriendlyURLs =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURLs(
				parentLayout.getPlid());

		LayoutFriendlyURL parentLayoutFriendlyURL =
			parentLayoutFriendlyURLs.get(0);

		_layoutFriendlyURLLocalService.getLayoutFriendlyURLByUuidAndGroupId(
			parentLayoutFriendlyURL.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		super.validateImport(
			stagedModel, stagedModelAssets, dependentStagedModelsMap, group);

		Layout layout = (Layout)stagedModel;

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), group.getGroupId(), layout.isPrivateLayout());

		List<FriendlyURLEntry> layoutFriendlyURLEntries =
			_getFriendlyURLEntries(layout);

		List<FriendlyURLEntry> importedLayoutFriendlyURLEntries =
			_getFriendlyURLEntries(importedLayout);

		Assert.assertEquals(
			importedLayoutFriendlyURLEntries.toString(),
			layoutFriendlyURLEntries.size(),
			importedLayoutFriendlyURLEntries.size());

		for (int i = 0; i < layoutFriendlyURLEntries.size(); i++) {
			FriendlyURLEntry friendlyURLEntry = layoutFriendlyURLEntries.get(i);
			FriendlyURLEntry importedFriendlyURLEntry =
				importedLayoutFriendlyURLEntries.get(i);

			Assert.assertEquals(
				friendlyURLEntry.getUuid(), importedFriendlyURLEntry.getUuid());
		}
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		DateTestUtil.assertEquals(
			stagedModel.getCreateDate(), importedStagedModel.getCreateDate());

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

	protected void validatePortletAttributes(
			String layoutUuid, String portletId, long expectedScopeGroupId,
			String expectedScopeLayoutType)
		throws Exception {

		Element layoutRootElement = rootElement.element("Layout");

		List<Element> layoutElements = layoutRootElement.elements();

		Element layoutElement = null;

		for (Element curLayoutElement : layoutElements) {
			if (Objects.equals(
					curLayoutElement.attributeValue("uuid"), layoutUuid)) {

				layoutElement = curLayoutElement;

				break;
			}
		}

		if (layoutElement == null) {
			throw new IllegalStateException(
				"Unable to find layout element with UUID " + layoutUuid);
		}

		Element portletRootElement = layoutElement.element("portlets");

		List<Element> portletElements = portletRootElement.elements();

		Element portletElement = null;

		for (Element curPortletElement : portletElements) {
			if (Objects.equals(
					curPortletElement.attributeValue("portlet-id"),
					portletId)) {

				portletElement = curPortletElement;

				break;
			}
		}

		if (portletElement == null) {
			throw new IllegalStateException(
				"Unable to find portlet element with portlet ID " + portletId);
		}

		Document portletDocument = SAXReaderUtil.read(
			portletDataContext.getZipEntryAsString(
				portletElement.attributeValue("path")));

		Element portletDocumentRootElement = portletDocument.getRootElement();

		Assert.assertEquals(
			String.valueOf(expectedScopeGroupId),
			portletDocumentRootElement.attributeValue("scope-group-id"));

		Assert.assertEquals(
			expectedScopeLayoutType,
			portletDocumentRootElement.attributeValue("scope-layout-type"));
	}

	private void _addDependentFriendlyURLEntries(
		Map<String, List<StagedModel>> dependentStagedModelsMap,
		Layout layout) {

		for (FriendlyURLEntry friendlyURLEntry :
				_getFriendlyURLEntries(layout)) {

			addDependentStagedModel(
				dependentStagedModelsMap, FriendlyURLEntry.class,
				friendlyURLEntry);
		}
	}

	private void _addDependentLayoutFriendlyURLs(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Layout layout)
		throws Exception {

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURLs(
				layout.getPlid());

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			addDependentStagedModel(
				dependentStagedModelsMap, LayoutFriendlyURL.class,
				layoutFriendlyURL);
		}
	}

	private FragmentEntryLink _addFragmentEntryLinkToLayout(
			JSONObject editableJSONObject, Layout layout,
			ServiceContext serviceContext)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), StringUtil.randomString(),
				StringPool.BLANK, serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				"<h1 data-lfr-editable-id=\"element-text\" " +
					"data-lfr-editable-type=\"text\">Heading Example</h1>",
				StringPool.BLANK, false, StringPool.BLANK, null, 0, false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					editableJSONObject
				).toString(),
				fragmentEntry.getCss(), fragmentEntry.getConfiguration(),
				fragmentEntry.getFragmentEntryId(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), layout.fetchDraftLayout(),
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				null, 0,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid()));

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);

		return fragmentEntryLink;
	}

	private Layout _addMasterLayout(ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		return _layoutLocalService.fetchLayout(
			masterLayoutPageTemplateEntry.getPlid());
	}

	private String _addPortletToLayout(Layout layout) throws Exception {
		JSONObject processAddPortletJSONObject =
			ContentLayoutTestUtil.addPortletToLayout(
				layout, _TEST_PORTLET_NAME);

		JSONObject fragmentEntryLinkJSONObject =
			processAddPortletJSONObject.getJSONObject("fragmentEntryLink");

		JSONObject editableValuesJSONObject =
			fragmentEntryLinkJSONObject.getJSONObject("editableValues");

		return PortletIdCodec.encode(
			editableValuesJSONObject.getString("portletId"),
			editableValuesJSONObject.getString("instanceId"));
	}

	private void _assertExportImportContentReference(
			long classPK, String fieldName, String infoItemClassName,
			String infoItemFormVariationKey,
			UnsafeSupplier<Long, Exception> unsafeSupplier)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(stagingGroup);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(stagingGroup.getGroupId());

		TemplateEntry templateEntry = TemplateTestUtil.addTemplateEntry(
			infoItemClassName, infoItemFormVariationKey,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			TemplateTestUtil.getSampleScriptFTL(fieldName), serviceContext);

		InfoField infoField = _getTemplateEntryInfoField(
			infoItemClassName, infoItemFormVariationKey,
			templateEntry.getTemplateEntryId(), serviceContext);

		long classNameId = _portal.getClassNameId(infoItemClassName);

		FragmentEntryLink draftLayoutFragmentEntryLink =
			_addFragmentEntryLinkToLayout(
				JSONUtil.put(
					"element-text",
					JSONUtil.put(
						"className", infoItemClassName
					).put(
						"classNameId", String.valueOf(classNameId)
					).put(
						"classPK", String.valueOf(classPK)
					).put(
						"fieldId", infoField.getUniqueId()
					)),
				layout, serviceContext);

		FragmentEntryLink publishedLayoutFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				stagingGroup.getGroupId(),
				draftLayoutFragmentEntryLink.getFragmentEntryLinkId(),
				layout.getPlid());

		Assert.assertNotNull(publishedLayoutFragmentEntryLink);

		ExportImportThreadLocal.setPortletImportInProcess(true);

		try {
			exportImportStagedModel(layout);
		}
		finally {
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}

		TemplateEntry importedTemplateEntry =
			_templateEntryLocalService.getTemplateEntryByUuidAndGroupId(
				templateEntry.getUuid(), liveGroup.getGroupId());

		infoField = _getTemplateEntryInfoField(
			infoItemClassName, infoItemFormVariationKey,
			importedTemplateEntry.getTemplateEntryId(),
			ServiceContextTestUtil.getServiceContext(liveGroup.getGroupId()));

		JSONObject expectedEditableJSONObject = JSONUtil.put(
			"element-text",
			JSONUtil.put(
				"className", infoItemClassName
			).put(
				"classNameId", String.valueOf(classNameId)
			).put(
				"classPK", String.valueOf(unsafeSupplier.get())
			).put(
				"fieldId", infoField.getUniqueId()
			));

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

		_assertLayoutContentReferences(
			expectedEditableJSONObject, importedLayout);
		_assertLayoutContentReferences(
			expectedEditableJSONObject, importedLayout.fetchDraftLayout());
	}

	private void _assertLayoutContentReferences(
			JSONObject expectedEditableJSONObject, Layout layout)
		throws Exception {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				liveGroup.getGroupId(), layout.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 1, fragmentEntryLinks.size());

		FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(0);

		JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
			fragmentEntryLink.getEditableValues());

		JSONObject editableJSONObject = editableValuesJSONObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		Assert.assertTrue(
			editableJSONObject.toString(),
			JSONUtil.equals(expectedEditableJSONObject, editableJSONObject));
	}

	private void _assertLayoutSEOEntry(
		Map<Locale, String> canonicalURLMap, long groupId, String uuid) {

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntryByUuidAndGroupId(
				uuid, groupId);

		_assertMapEquals(canonicalURLMap, layoutSEOEntry.getCanonicalURLMap());
	}

	private void _assertMapEquals(
		Map<Locale, String> expectedMap, Map<Locale, String> map) {

		Assert.assertEquals(
			MapUtil.toString(map), expectedMap.size(), map.size());

		for (Map.Entry<Locale, String> entry : expectedMap.entrySet()) {
			Assert.assertEquals(entry.getValue(), map.get(entry.getKey()));
		}
	}

	private List<FriendlyURLEntry> _getFriendlyURLEntries(Layout layout) {
		return _friendlyURLEntryLocalService.getFriendlyURLEntries(
			layout.getGroupId(),
			_portal.getClassNameId(
				_resourceActions.getCompositeModelName(
					Layout.class.getName(),
					String.valueOf(layout.isPrivateLayout()))),
			layout.getPlid());
	}

	private InfoField _getTemplateEntryInfoField(
			String infoItemClassName, String infoItemFormVariationKey,
			long templateEntryId, ServiceContext serviceContext)
		throws Exception {

		InfoItemFormProvider<?> infoItemFormProvider =
			(InfoItemFormProvider<?>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFormProvider.class, infoItemClassName);

		try {
			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			InfoForm infoForm = infoItemFormProvider.getInfoForm(
				infoItemFormVariationKey, serviceContext.getScopeGroupId());

			return infoForm.getInfoField(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX + templateEntryId);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private void _publishLayouts(Group group, Group stagingGroup)
		throws Exception {

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.exportimport.internal.lifecycle." +
					"LoggerExportImportLifecycleListener",
				LoggerTestUtil.ERROR)) {

			StagingUtil.publishLayouts(
				TestPropsValues.getUserId(), stagingGroup.getGroupId(),
				group.getGroupId(), false, parameterMap);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}

		List<BackgroundTask> failedBackgroundTasks =
			_backgroundTaskLocalService.getBackgroundTasks(
				stagingGroup.getGroupId(),
				"com.liferay.exportimport.internal.background.task." +
					"LayoutStagingBackgroundTaskExecutor",
				BackgroundTaskConstants.STATUS_FAILED);

		Assert.assertTrue(
			failedBackgroundTasks.toString(), failedBackgroundTasks.isEmpty());
	}

	private ServiceRegistration<Portlet> _registerTestPortlet() {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutStagedModelDataHandlerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			Portlet.class, new MVCPortlet(),
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.instanceable", "true"
			).put(
				"com.liferay.portlet.preferences-owned-by-group", "true"
			).put(
				"javax.portlet.init-param.view-template", "/view.jsp"
			).put(
				"javax.portlet.name", _TEST_PORTLET_NAME
			).build());
	}

	private void _testClientExtensionEntries(String type, String url)
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

		Layout layout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			clientExtensionEntry.getExternalReferenceCode(), type,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId()));

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

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

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		Layout importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

		Assert.assertEquals(
			1,
			_clientExtensionEntryRelLocalService.
				getClientExtensionEntryRelsCount(
					_portal.getClassNameId(Layout.class),
					importedLayout.getPlid(), type));

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
			_portal.getClassNameId(Layout.class), layout.getPlid(), type);

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			clientExtensionEntry.getExternalReferenceCode(), type,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId()));

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

		initImport();

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		exportedLayout = (Layout)readExportedStagedModel(layout);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedLayout);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				portletDataContext));

		importedLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

		Assert.assertEquals(
			1,
			_clientExtensionEntryRelLocalService.
				getClientExtensionEntryRelsCount(
					_portal.getClassNameId(Layout.class),
					importedLayout.getPlid(), type));
	}

	private LayoutSEOEntry _updateLayoutSEOEntry(Layout layout)
		throws Exception {

		Map<Locale, String> canonicalURLMap =
			RandomTestUtil.randomLocaleStringMap();

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.updateLayoutSEOEntry(
				TestPropsValues.getUserId(), layout.getGroupId(), false,
				layout.getLayoutId(), true, canonicalURLMap,
				ServiceContextTestUtil.getServiceContext(
					layout.getGroupId(), TestPropsValues.getUserId()));

		_assertMapEquals(canonicalURLMap, layoutSEOEntry.getCanonicalURLMap());

		return layoutSEOEntry;
	}

	private static final String _TEST_PORTLET_NAME =
		"com_liferay_test_portlet_TestPortlet";

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

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
	private DLURLHelper _dlURLHelper;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private Portal _portal;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private ResourceActions _resourceActions;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private TemplateEntryLocalService _templateEntryLocalService;

}