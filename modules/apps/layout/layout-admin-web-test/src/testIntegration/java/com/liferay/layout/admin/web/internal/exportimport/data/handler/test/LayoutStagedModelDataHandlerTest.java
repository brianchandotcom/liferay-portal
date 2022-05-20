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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.lifecycle.constants.ExportImportLifecycleConstants;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DateTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.Portlet;

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
	public void testCompanyScopedPortletOnContentLayoutHasCorrectAttributes()
		throws Exception {

		ServiceRegistration<Portlet> serviceRegistration =
			_registerTestPortlet();

		try {
			initExport();

			Layout layout = LayoutTestUtil.addTypeContentLayout(stagingGroup);

			PortletPreferences portletPreferences =
				PortletPreferencesLocalServiceUtil.addPortletPreferences(
					stagingGroup.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					_TEST_PORTLET_NAME, null, null);

			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferenceValueLocalServiceUtil.getPreferences(
					portletPreferences);

			jxPortletPreferences.setValue("lfrScopeType", "company");

			PortletPreferencesLocalServiceUtil.updatePreferences(
				portletPreferences.getOwnerId(),
				portletPreferences.getOwnerType(), portletPreferences.getPlid(),
				portletPreferences.getPortletId(), jxPortletPreferences);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, layout);

			initImport();

			Company company = CompanyLocalServiceUtil.getCompany(
				liveGroup.getCompanyId());

			validatePortletAttributes(
				layout.getUuid(), _TEST_PORTLET_NAME, company.getGroupId(),
				"company");
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

			LayoutTestUtil.addPortletToLayout(
				layout, _TEST_PORTLET_NAME,
				HashMapBuilder.put(
					"lfrScopeType", new String[] {"company"}
				).build());

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, layout);

			initImport();

			Company company = CompanyLocalServiceUtil.getCompany(
				liveGroup.getCompanyId());

			validatePortletAttributes(
				layout.getUuid(), _TEST_PORTLET_NAME, company.getGroupId(),
				"company");
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testTypeLinkToLayout() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Layout linkedLayout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		List<LayoutFriendlyURL> linkedLayoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				linkedLayout.getPlid());

		addDependentStagedModel(
			dependentStagedModelsMap, Layout.class, linkedLayout);

		_addDependentFriendlyURLEntries(dependentStagedModelsMap, linkedLayout);
		_addDependentLayoutFriendlyURLs(dependentStagedModelsMap, linkedLayout);

		Layout layout = LayoutTestUtil.addTypeLinkToLayoutLayout(
			stagingGroup.getGroupId(), linkedLayout.getLayoutId());

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
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

	@Test
	public void testTypeLinkToURL() throws Exception {
		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		String fileName = "PDF_Test.pdf";

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.APPLICATION_PDF,
			FileUtil.getBytes(getClass(), "dependencies/" + fileName), null,
			null,
			ServiceContextTestUtil.getServiceContext(
				liveGroup.getGroupId(), TestPropsValues.getUserId()));

		String stagingPreviewURL = DLURLHelperUtil.getPreviewURL(
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
			DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
				fileEntry.getUuid(), liveGroup.getGroupId());

		String livePreviewURL = DLURLHelperUtil.getPreviewURL(
			importedFileEntry, importedFileEntry.getFileVersion(), null,
			StringPool.BLANK);

		Layout importedLayout =
			LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
				layout.getUuid(), liveGroup.getGroupId(),
				layout.isPrivateLayout());

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
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		super.validateImport(
			stagedModel, stagedModelAssets, dependentStagedModelsMap, group);

		Layout layout = (Layout)stagedModel;

		Layout importedLayout =
			LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
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
				"Unable to find layout element with uuid " + layoutUuid);
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
				"Unable to find portlet element with portlet id " + portletId);
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
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			addDependentStagedModel(
				dependentStagedModelsMap, LayoutFriendlyURL.class,
				layoutFriendlyURL);
		}
	}

	private List<FriendlyURLEntry> _getFriendlyURLEntries(Layout layout) {
		return FriendlyURLEntryLocalServiceUtil.getFriendlyURLEntries(
			layout.getGroupId(),
			PortalUtil.getClassNameId(
				ResourceActionsUtil.getCompositeModelName(
					Layout.class.getName(),
					String.valueOf(layout.isPrivateLayout()))),
			layout.getPlid());
	}

	private ServiceRegistration<Portlet> _registerTestPortlet() {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutStagedModelDataHandlerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			Portlet.class, new MVCPortlet(),
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.preferences-company-wide", "true"
			).put(
				"javax.portlet.name", _TEST_PORTLET_NAME
			).build());
	}

	private static final String _TEST_PORTLET_NAME =
		"com_liferay_test_portlet_TestPortlet";

}