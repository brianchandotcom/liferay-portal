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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManagerUtil;
import com.liferay.exportimport.kernel.lifecycle.constants.ExportImportLifecycleConstants;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletConfigurationListener;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class LayoutPortletExportImportTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_companyId = TestPropsValues.getCompanyId();

		_globalGroup = GroupLocalServiceUtil.getCompanyGroup(_companyId);

		_ownerRole = RoleLocalServiceUtil.getRole(
			_companyId, RoleConstants.OWNER);
	}

	@Test
	public void testGloballyScopedPortletExportImportDoesNotOverrideGlobalSitePermissions()
		throws Exception {

		_exportGroup = GroupTestUtil.addGroup();

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(_exportGroup);

		_addPortletToLayoutWithGlobalScope(exportLayout);

		ResourcePermissionLocalServiceUtil.addModelResourcePermissions(
			_exportGroup.getCompanyId(), _exportGroup.getGroupId(),
			TestPropsValues.getUserId(), DLConstants.RESOURCE_NAME,
			String.valueOf(_exportGroup.getGroupId()), null, null);

		_initExport(_exportGroup);

		Map<String, List<KeyValuePair>> permissionsMap =
			_portletDataContext.getPermissions();

		_removeOwnerPermissionsFromDLHomeFolderPermissionsInGlobalSite();

		StagedModelDataHandlerUtil.exportStagedModel(
			_portletDataContext, exportLayout);

		Group importGroup = GroupTestUtil.addGroup();

		_initImport(_exportGroup, importGroup);

		_portletDataContext.addPermissions(
			DLConstants.RESOURCE_NAME, _exportGroup.getGroupId(),
			permissionsMap.get(
				DLConstants.RESOURCE_NAME + "#" +
					String.valueOf(_exportGroup.getGroupId())));

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			_portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				_portletDataContext));

		Layout exportedLayout = (Layout)_readExportedStagedModel(exportLayout);

		StagedModelDataHandlerUtil.importStagedModel(
			_portletDataContext, exportedLayout);

		ExportImportLifecycleManagerUtil.fireExportImportLifecycleEvent(
			ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			_portletDataContext.getExportImportProcessId(),
			PortletDataContextFactoryUtil.clonePortletDataContext(
				_portletDataContext));

		_resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				exportLayout.getCompanyId(), DLConstants.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_globalGroup.getGroupId()),
				_ownerRole.getRoleId());

		Assert.assertEquals(0, _resourcePermission.getActionIds());
		Assert.assertFalse(_resourcePermission.isViewActionId());
	}

	private void _addPortletToLayoutWithGlobalScope(Layout exportLayout)
		throws Exception {

		String portletId = LayoutTestUtil.addPortletToLayout(
			exportLayout, DLPortletKeys.DOCUMENT_LIBRARY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				exportLayout, portletId);

		portletPreferences.setValue("lfrScopeType", "company");

		portletPreferences.setValue("lfrScopeLayoutUuid", "");

		String languageId = LanguageUtil.getLanguageId(LocaleUtil.getDefault());

		String portletTitle = portletPreferences.getValue(
			"portletSetupTitle_" + languageId, StringPool.BLANK);

		portletPreferences.setValue(
			"portletSetupTitle_" + languageId,
			PortalUtil.getNewPortletTitle(portletTitle, null, "global"));

		portletPreferences.setValue(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString());

		portletPreferences.store();

		PortletConfigurationListener portletConfigurationListener =
			portlet.getPortletConfigurationListenerInstance();

		if (portletConfigurationListener != null) {
			portletConfigurationListener.onUpdateScope(
				portletId, portletPreferences);
		}
	}

	private Map<String, String[]> _getParameterMap() {
		return ExportImportConfigurationParameterMapFactoryUtil.
			buildParameterMap();
	}

	private void _initExport(Group exportGroup) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute("exportLAR", Boolean.TRUE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_zipWriter = ZipWriterFactoryUtil.getZipWriter();

		_portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				exportGroup.getCompanyId(), exportGroup.getGroupId(),
				_getParameterMap(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				_zipWriter);

		_portletDataContext.setExportImportProcessId(
			BaseStagedModelDataHandlerTestCase.class.getName());

		_rootElement = SAXReaderUtil.createElement("root");

		_portletDataContext.setExportDataRootElement(_rootElement);

		_missingReferencesElement = _rootElement.addElement(
			"missing-references");

		_portletDataContext.setMissingReferencesElement(
			_missingReferencesElement);

		_portletDataContext.addPortletPermissions(DLConstants.RESOURCE_NAME);
	}

	private void _initImport(Group exportGroup, Group importGroup)
		throws Exception {

		_userIdStrategy = new TestUserIdStrategy();

		_zipReader = ZipReaderFactoryUtil.getZipReader(_zipWriter.getFile());

		String xml = _zipReader.getEntryAsString("/manifest.xml");

		if (xml == null) {
			Document document = SAXReaderUtil.createDocument();

			Element rootElement = document.addElement("root");

			rootElement.addElement("header");

			_zipWriter.addEntry("/manifest.xml", document.asXML());

			_zipReader = ZipReaderFactoryUtil.getZipReader(
				_zipWriter.getFile());
		}

		_portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				importGroup.getCompanyId(), importGroup.getGroupId(),
				_getParameterMap(), _userIdStrategy, _zipReader);

		_portletDataContext.setExportImportProcessId(
			BaseStagedModelDataHandlerTestCase.class.getName());
		_portletDataContext.setImportDataRootElement(_rootElement);

		Element missingReferencesElement = _rootElement.element(
			"missing-references");

		if (missingReferencesElement == null) {
			missingReferencesElement = _rootElement.addElement(
				"missing-references");
		}

		_portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		Group sourceCompanyGroup = GroupLocalServiceUtil.getCompanyGroup(
			exportGroup.getCompanyId());

		_portletDataContext.setSourceCompanyGroupId(
			sourceCompanyGroup.getGroupId());

		_portletDataContext.setSourceCompanyId(exportGroup.getCompanyId());
		_portletDataContext.setSourceGroupId(exportGroup.getGroupId());
	}

	private StagedModel _readExportedStagedModel(StagedModel stagedModel) {
		String stagedModelPath = ExportImportPathUtil.getModelPath(stagedModel);

		return (StagedModel)_portletDataContext.getZipEntryAsObject(
			stagedModelPath);
	}

	private void _removeOwnerPermissionsFromDLHomeFolderPermissionsInGlobalSite()
		throws Exception {

		long globalGroupId = _globalGroup.getGroupId();

		ResourcePermissionLocalServiceUtil.addModelResourcePermissions(
			_globalGroup.getCompanyId(), globalGroupId,
			TestPropsValues.getUserId(), DLConstants.RESOURCE_NAME,
			String.valueOf(globalGroupId), null, null);

		_resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				_globalGroup.getCompanyId(), DLConstants.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(globalGroupId), _ownerRole.getRoleId());

		_resourcePermission.setActionIds(0);
		_resourcePermission.setViewActionId(false);

		ResourcePermissionLocalServiceUtil.updateResourcePermission(
			_resourcePermission);
	}

	private long _companyId;

	@DeleteAfterTestRun
	private Group _exportGroup;

	private Group _globalGroup;
	private Element _missingReferencesElement;
	private Role _ownerRole;
	private PortletDataContext _portletDataContext;

	@DeleteAfterTestRun
	private ResourcePermission _resourcePermission;

	private Element _rootElement;
	private UserIdStrategy _userIdStrategy;
	private ZipReader _zipReader;
	private ZipWriter _zipWriter;

	private class TestUserIdStrategy implements UserIdStrategy {

		public TestUserIdStrategy() {
			_userId = _initializeUserId();
		}

		public TestUserIdStrategy(User user) {
			_userId = user.getUserId();
		}

		@Override
		public long getUserId(String userUuid) {
			return _userId;
		}

		private long _initializeUserId() {
			try {
				return TestPropsValues.getUserId();
			}
			catch (Exception exception) {
				return 0;
			}
		}

		private final long _userId;

	}

}