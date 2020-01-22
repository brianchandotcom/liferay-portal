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

package com.liferay.site.internal.exportimport.data.handler;

import com.liferay.exportimport.controller.PortletExportController;
import com.liferay.exportimport.controller.PortletImportController;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactory;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerStatusMessageSender;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.site.internal.exportimport.staged.model.repository.StagedGroupStagedModelRepository;
import com.liferay.site.model.adapter.StagedGroup;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedGroup> {

	public static final String[] CLASS_NAMES = {StagedGroup.class.getName()};

	@Override
	public void deleteStagedModel(StagedGroup stagedGroup) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<StagedGroup> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return Collections.emptyList();
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedGroup stagedGroup) {
		Group group = stagedGroup.getGroup();

		return group.getName();
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		if ((groupId == 0) || groupIds.containsKey(groupId)) {
			return true;
		}

		Group existingGroup =
			_stagedGroupStagedModelRepository.fetchExistingGroup(
				portletDataContext, referenceElement);

		if (existingGroup == null) {
			return false;
		}

		groupIds.put(groupId, existingGroup.getGroupId());

		return true;
	}

	protected Set<String> checkDataSiteLevelPortlets(
			PortletDataContext portletDataContext, Group group)
		throws Exception {

		List<Portlet> dataSiteLevelPortlets =
			_exportImportHelper.getDataSiteLevelPortlets(
				portletDataContext.getCompanyId());

		Group liveGroup = group;

		if (liveGroup.isStagingGroup()) {
			liveGroup = liveGroup.getLiveGroup();
		}

		Set<String> portletIds = new LinkedHashSet<>();

		for (Portlet portlet : dataSiteLevelPortlets) {
			String portletId = portlet.getRootPortletId();

			if (ExportImportThreadLocal.isStagingInProcess() &&
				!liveGroup.isStagedPortlet(portletId)) {

				continue;
			}

			// Calculate the amount of exported data

			if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
				Map<String, Boolean> exportPortletControlsMap =
					_exportImportHelper.getExportPortletControlsMap(
						portletDataContext.getCompanyId(), portletId,
						portletDataContext.getParameterMap(),
						portletDataContext.getType());

				if (exportPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA)) {

					PortletDataHandler portletDataHandler =
						portlet.getPortletDataHandlerInstance();

					portletDataHandler.prepareManifestSummary(
						portletDataContext);
				}
			}

			// Add portlet ID to exportable portlets list

			portletIds.add(portletId);
		}

		return portletIds;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, StagedGroup stagedGroup)
		throws Exception {

		// Collect site portlets and initialize the progress bar

		Group group = stagedGroup.getGroup();

		Set<String> dataSiteLevelPortletIds = checkDataSiteLevelPortlets(
			portletDataContext, group);

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			_portletDataHandlerStatusMessageSender.sendStatusMessage(
				"layout", ArrayUtil.toStringArray(dataSiteLevelPortletIds),
				manifestSummary);

			manifestSummary.resetCounters();
		}

		long[] layoutIds = portletDataContext.getLayoutIds();

		if (group.isLayoutPrototype()) {
			layoutIds = _exportImportHelper.getAllLayoutIds(
				group.getGroupId(), portletDataContext.isPrivateLayout());
		}

		// Export site data portlets

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		try {
			exportSitePortlets(
				portletDataContext, stagedGroup, dataSiteLevelPortletIds,
				layoutIds);
		}
		finally {
			portletDataContext.setScopeGroupId(previousScopeGroupId);
		}

		// Layout set with layouts

		List<? extends StagedModel> childStagedModels =
			_stagedGroupStagedModelRepository.fetchChildrenStagedModels(
				portletDataContext, stagedGroup);

		for (StagedModel stagedModel : childStagedModels) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedGroup, stagedModel,
				PortletDataContext.REFERENCE_TYPE_CHILD);
		}

		// Serialize the group

		Element groupElement = portletDataContext.getExportDataElement(
			stagedGroup);

		portletDataContext.addClassedModel(
			groupElement, ExportImportPathUtil.getModelPath(stagedGroup),
			stagedGroup);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		if ((groupId == 0) || groupIds.containsKey(groupId)) {
			return;
		}

		Group existingGroup =
			_stagedGroupStagedModelRepository.fetchExistingGroup(
				portletDataContext, referenceElement);

		if (existingGroup == null) {
			return;
		}

		groupIds.put(groupId, existingGroup.getGroupId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, StagedGroup stagedGroup)
		throws Exception {

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element sitePortletsElement = rootElement.element("site-portlets");

		List<Element> sitePortletElements = sitePortletsElement.elements();

		// Initialize progress bar

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			List<String> portletIds = new ArrayList<>();

			for (Element portletElement : sitePortletElements) {
				String portletId = portletElement.attributeValue("portlet-id");

				Portlet portlet = _portletLocalService.getPortletById(
					portletDataContext.getCompanyId(), portletId);

				if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
					continue;
				}

				portletIds.add(portletId);
			}

			_portletDataHandlerStatusMessageSender.sendStatusMessage(
				"layout", ArrayUtil.toStringArray(portletIds),
				portletDataContext.getManifestSummary());
		}

		// Import site data portlets

		if (_log.isDebugEnabled() && !sitePortletElements.isEmpty()) {
			_log.debug("Importing portlets");
		}

		importSitePortlets(portletDataContext, sitePortletElements);

		// Import services

		Element siteServicesElement = rootElement.element("site-services");

		List<Element> siteServiceElements = siteServicesElement.elements(
			"service");

		if (_log.isDebugEnabled() && !siteServiceElements.isEmpty()) {
			_log.debug("Importing services");
		}

		importSiteServices(portletDataContext, siteServiceElements);

		// Import layout set

		Element layoutSetElement = portletDataContext.getImportDataGroupElement(
			StagedLayoutSet.class);

		for (Element groupElement : layoutSetElement.elements()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, groupElement);
		}
	}

	protected void exportPortlet(
			PortletDataContext portletDataContext, String portletId, long plid,
			long scopeGroupId, String scopeType, String scopeLayoutUuid,
			String type, Element portletsElement, Element servicesElement,
			boolean permissions)
		throws Exception {

		portletDataContext.setPlid(plid);
		portletDataContext.setPortletId(portletId);
		portletDataContext.setScopeGroupId(scopeGroupId);
		portletDataContext.setScopeType(scopeType);
		portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

		Map<String, Boolean> exportPortletControlsMap =
			_exportImportHelper.getExportPortletControlsMap(
				portletDataContext.getCompanyId(), portletId,
				portletDataContext.getParameterMap(), type);

		try {
			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED,
				getProcessFlag(), portletDataContext.getExportImportProcessId(),
				_portletDataContextFactory.clonePortletDataContext(
					portletDataContext));

			_portletExportController.exportPortlet(
				portletDataContext, plid, portletsElement, permissions,
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
			_portletExportController.exportService(
				portletDataContext, servicesElement,
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP));

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED,
				getProcessFlag(), portletDataContext.getExportImportProcessId(),
				_portletDataContextFactory.clonePortletDataContext(
					portletDataContext));
		}
		catch (Throwable t) {
			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED,
				getProcessFlag(), portletDataContext.getExportImportProcessId(),
				_portletDataContextFactory.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
	}

	protected void exportSitePortlets(
			PortletDataContext portletDataContext, StagedGroup stagedGroup,
			Set<String> portletIds, long[] layoutIds)
		throws Exception {

		// Prepare XML

		Element rootElement = portletDataContext.getExportDataRootElement();

		Element portletsElement = rootElement.element("site-portlets");
		Element servicesElement = rootElement.element("site-services");

		String type = portletDataContext.getType();

		// Export portlets

		boolean permissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		Group group = stagedGroup.getGroup();

		List<Layout> layouts = _layoutLocalService.getLayouts(
			group.getGroupId(), portletDataContext.isPrivateLayout());

		for (String portletId : portletIds) {

			// Default scope

			exportPortlet(
				portletDataContext, portletId, LayoutConstants.DEFAULT_PLID,
				portletDataContext.getGroupId(), StringPool.BLANK,
				StringPool.BLANK, type, portletsElement, servicesElement,
				permissions);

			Portlet portlet = _portletLocalService.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (!portlet.isScopeable()) {
				continue;
			}

			// Scoped data

			for (Layout layout : layouts) {
				if (!ArrayUtil.contains(layoutIds, layout.getLayoutId()) ||
					!layout.isTypePortlet() || !layout.hasScopeGroup()) {

					continue;
				}

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (!layoutTypePortlet.hasPortletId(portletId)) {
					continue;
				}

				Group scopeGroup = layout.getScopeGroup();

				exportPortlet(
					portletDataContext, portletId, layout.getPlid(),
					scopeGroup.getGroupId(), StringPool.BLANK, layout.getUuid(),
					type, portletsElement, servicesElement, permissions);
			}
		}
	}

	@Override
	protected StagedModelRepository<StagedGroup> getStagedModelRepository() {
		return _stagedGroupStagedModelRepository;
	}

	@Override
	protected void importReferenceStagedModels(
			PortletDataContext portletDataContext, StagedGroup stagedModel)
		throws PortletDataException {
	}

	protected void importSitePortlets(
			PortletDataContext portletDataContext,
			List<Element> sitePortletElements)
		throws Exception {

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		boolean permissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		_permissionImporter.clearCache();

		for (Element portletElement : sitePortletElements) {
			String portletId = portletElement.attributeValue("portlet-id");

			Portlet portlet = _portletLocalService.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
				continue;
			}

			long layoutId = GetterUtil.getLong(
				portletElement.attributeValue("layout-id"));

			Layout layout = layouts.get(layoutId);

			long plid = LayoutConstants.DEFAULT_PLID;

			if (layout != null) {
				if (SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {
					continue;
				}

				plid = layout.getPlid();
			}

			portletDataContext.setPlid(plid);

			portletDataContext.setPortletId(portletId);

			if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
				_portletDataHandlerStatusMessageSender.sendStatusMessage(
					"portlet", portletId,
					portletDataContext.getManifestSummary());
			}

			String portletPath = portletElement.attributeValue("path");

			Document portletDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(portletPath));

			portletElement = portletDocument.getRootElement();

			// The order of the import is important. You must always import the
			// portlet preferences first, then the portlet data, then the
			// portlet permissions. The import of the portlet data assumes that
			// portlet preferences already exist.

			_exportImportHelper.setPortletScope(
				portletDataContext, portletElement);

			long portletPreferencesGroupId = portletDataContext.getGroupId();

			Element portletDataElement = portletElement.element("portlet-data");

			Map<String, Boolean> importPortletControlsMap =
				_exportImportHelper.getImportPortletControlsMap(
					portletDataContext.getCompanyId(), portletId,
					portletDataContext.getParameterMap(), portletDataElement,
					portletDataContext.getManifestSummary());

			if (layout != null) {
				portletPreferencesGroupId = layout.getGroupId();
			}

			try {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_STARTED,
					getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					_portletDataContextFactory.clonePortletDataContext(
						portletDataContext));

				// Portlet preferences

				_portletImportController.importPortletPreferences(
					portletDataContext, portletDataContext.getCompanyId(),
					portletPreferencesGroupId, layout, portletElement, false,
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_SETUP),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

				// Portlet data

				if (importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA)) {

					_portletImportController.importPortletData(
						portletDataContext, portletDataElement);
				}

				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_SUCCEEDED,
					getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					_portletDataContextFactory.clonePortletDataContext(
						portletDataContext));
			}
			catch (Throwable t) {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED,
					getProcessFlag(),
					portletDataContext.getExportImportProcessId(),
					_portletDataContextFactory.clonePortletDataContext(
						portletDataContext),
					t);

				throw t;
			}
			finally {
				_portletImportController.resetPortletScope(
					portletDataContext, portletPreferencesGroupId);
			}

			// Portlet permissions

			if (permissions) {
				_permissionImporter.importPortletPermissions(
					portletDataContext.getCompanyId(),
					portletDataContext.getGroupId(), serviceContext.getUserId(),
					layout, portletElement, portletId);
			}

			// Archived setups

			_portletImportController.importPortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				portletDataContext.getGroupId(), null, portletElement, false,
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
		}
	}

	protected void importSiteServices(
			PortletDataContext portletDataContext,
			List<Element> siteServiceElements)
		throws Exception {

		for (Element serviceElement : siteServiceElements) {
			String path = serviceElement.attributeValue("path");

			Document siteServiceDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(path));

			serviceElement = siteServiceDocument.getRootElement();

			_portletImportController.importServicePortletPreferences(
				portletDataContext, serviceElement);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedGroupStagedModelDataHandler.class);

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLifecycleManager _exportImportLifecycleManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private PermissionImporter _permissionImporter;

	@Reference
	private PortletDataContextFactory _portletDataContextFactory;

	@Reference
	private PortletDataHandlerStatusMessageSender
		_portletDataHandlerStatusMessageSender;

	@Reference
	private PortletExportController _portletExportController;

	@Reference
	private PortletImportController _portletImportController;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private StagedGroupStagedModelRepository _stagedGroupStagedModelRepository;

}