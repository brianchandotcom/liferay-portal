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

package com.liferay.exportimport.internal.controller;

import com.liferay.asset.kernel.model.adapter.StagedAssetLink;
import com.liferay.exportimport.constants.ExportImportConstants;
import com.liferay.exportimport.controller.PortletImportController;
import com.liferay.exportimport.internal.lar.DeletionSystemEventImporter;
import com.liferay.exportimport.kernel.controller.ExportImportController;
import com.liferay.exportimport.kernel.controller.ImportController;
import com.liferay.exportimport.kernel.exception.LARFileException;
import com.liferay.exportimport.kernel.exception.LARTypeException;
import com.liferay.exportimport.kernel.exception.LayoutImportException;
import com.liferay.exportimport.kernel.exception.MissingReferenceException;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactory;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.LayoutPrototypeException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchLayoutPrototypeException;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.site.model.adapter.StagedGroup;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Wesley Gong
 * @author Zsigmond Rab
 * @author Douglas Wong
 * @author Julio Camarero
 * @author Zsolt Berentey
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.model.Layout",
	service = {ExportImportController.class, LayoutImportController.class}
)
public class LayoutImportController implements ImportController {

	@Override
	public void importDataDeletions(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws Exception {

		ZipReader zipReader = null;

		try {

			// LAR validation

			ExportImportThreadLocal.setLayoutDataDeletionImportInProcess(true);

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
			boolean privateLayout = MapUtil.getBoolean(
				settingsMap, "privateLayout");
			Map<String, String[]> parameterMap =
				(Map<String, String[]>)settingsMap.get("parameterMap");

			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				targetGroupId, privateLayout);

			zipReader = ZipReaderFactoryUtil.getZipReader(file);

			validateFile(
				layoutSet.getCompanyId(), targetGroupId, parameterMap,
				zipReader);

			PortletDataContext portletDataContext = getPortletDataContext(
				exportImportConfiguration, file);

			boolean deletePortletData = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);

			// Portlet data deletion

			if (deletePortletData) {
				if (_log.isDebugEnabled()) {
					_log.debug("Deleting portlet data");
				}

				deletePortletData(portletDataContext);
			}

			// Deletion system events

			populateDeletionStagedModelTypes(portletDataContext);

			_deletionSystemEventImporter.importDeletionSystemEvents(
				portletDataContext);
		}
		finally {
			ExportImportThreadLocal.setLayoutDataDeletionImportInProcess(false);

			if (zipReader != null) {
				zipReader.close();
			}
		}
	}

	@Override
	public void importFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws Exception {

		PortletDataContext portletDataContext = null;

		try {
			ExportImportThreadLocal.setLayoutImportInProcess(true);

			portletDataContext = getPortletDataContext(
				exportImportConfiguration, file);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED,
				getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				_portletDataContextFactory.clonePortletDataContext(
					portletDataContext));

			long userId = MapUtil.getLong(
				exportImportConfiguration.getSettingsMap(), "userId");

			doImportFile(portletDataContext, userId);

			ExportImportThreadLocal.setLayoutImportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED,
				getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				_portletDataContextFactory.clonePortletDataContext(
					portletDataContext),
				userId);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setLayoutImportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_FAILED,
				getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				_portletDataContextFactory.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
	}

	@Override
	public MissingReferences validateFile(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws Exception {

		ZipReader zipReader = null;

		try {
			ExportImportThreadLocal.setLayoutValidationInProcess(true);

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
			boolean privateLayout = MapUtil.getBoolean(
				settingsMap, "privateLayout");
			Map<String, String[]> parameterMap =
				(Map<String, String[]>)settingsMap.get("parameterMap");

			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				targetGroupId, privateLayout);

			zipReader = ZipReaderFactoryUtil.getZipReader(file);

			validateFile(
				layoutSet.getCompanyId(), targetGroupId, parameterMap,
				zipReader);

			PortletDataContext portletDataContext = getPortletDataContext(
				exportImportConfiguration, file);

			portletDataContext.setPrivateLayout(privateLayout);

			MissingReferences missingReferences =
				_exportImportHelper.validateMissingReferences(
					portletDataContext);

			Map<String, MissingReference> dependencyMissingReferences =
				missingReferences.getDependencyMissingReferences();

			if (!dependencyMissingReferences.isEmpty()) {
				throw new MissingReferenceException(missingReferences);
			}

			return missingReferences;
		}
		finally {
			ExportImportThreadLocal.setLayoutValidationInProcess(false);

			if (zipReader != null) {
				zipReader.close();
			}
		}
	}

	protected void deletePortletData(PortletDataContext portletDataContext)
		throws Exception {

		List<Element> portletElements = fetchPortletElements(
			portletDataContext.getImportDataRootElement());

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		if (_log.isDebugEnabled() && !portletElements.isEmpty()) {
			_log.debug("Deleting portlet data");
		}

		for (Element portletElement : portletElements) {
			long layoutId = GetterUtil.getLong(
				portletElement.attributeValue("layout-id"));

			long plid = LayoutConstants.DEFAULT_PLID;

			Layout layout = layouts.get(layoutId);

			if (layout != null) {
				plid = layout.getPlid();
			}

			portletDataContext.setPlid(plid);
			portletDataContext.setPortletId(
				portletElement.attributeValue("portlet-id"));

			_portletImportController.deletePortletData(portletDataContext);
		}
	}

	protected void doImportFile(
			PortletDataContext portletDataContext, long userId)
		throws Exception {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		String layoutsImportMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);
		boolean permissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);

		if (group.isLayoutSetPrototype()) {
			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
				new String[] {Boolean.FALSE.toString()});
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Import permissions " + permissions);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		_permissionImporter.clearCache();

		long companyId = portletDataContext.getCompanyId();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setCompanyId(companyId);
		serviceContext.setSignedIn(false);
		serviceContext.setUserId(userId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		// LAR validation

		validateFile(
			companyId, portletDataContext.getGroupId(), parameterMap,
			portletDataContext.getZipReader());

		// Source and target group id

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		groupIds.put(
			portletDataContext.getSourceGroupId(),
			portletDataContext.getGroupId());

		// Manifest

		ManifestSummary manifestSummary =
			_exportImportHelper.getManifestSummary(portletDataContext);

		portletDataContext.setManifestSummary(manifestSummary);

		// Layout and layout set prototype

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element headerElement = rootElement.element("header");

		String layoutSetPrototypeUuid = headerElement.attributeValue(
			"layout-set-prototype-uuid");

		String larType = headerElement.attributeValue("type");

		portletDataContext.setType(larType);

		if (group.isLayoutPrototype() && larType.equals("layout-prototype")) {
			parameterMap.put(
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
				new String[] {Boolean.FALSE.toString()});

			String layoutPrototypeUuid = GetterUtil.getString(
				headerElement.attributeValue("type-uuid"));

			LayoutPrototype existingLayoutPrototype = null;

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				try {
					existingLayoutPrototype =
						_layoutPrototypeLocalService.
							getLayoutPrototypeByUuidAndCompanyId(
								layoutPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutPrototypeException
							noSuchLayoutPrototypeException) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(
							noSuchLayoutPrototypeException,
							noSuchLayoutPrototypeException);
					}
				}
			}

			if (existingLayoutPrototype == null) {
				LayoutPrototype layoutPrototype =
					_layoutPrototypeLocalService.getLayoutPrototype(
						group.getClassPK());

				List<Layout> layouts =
					_layoutLocalService.getLayoutsByLayoutPrototypeUuid(
						layoutPrototype.getUuid());

				layoutPrototype.setUuid(layoutPrototypeUuid);

				_layoutPrototypeLocalService.updateLayoutPrototype(
					layoutPrototype);

				for (Layout layout : layouts) {
					layout.setLayoutPrototypeUuid(layoutPrototypeUuid);

					_layoutLocalService.updateLayout(layout);
				}
			}
		}
		else if (group.isLayoutSetPrototype() &&
				 larType.equals("layout-set-prototype")) {

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
				new String[] {Boolean.TRUE.toString()});

			String importedLayoutSetPrototypeUuid = GetterUtil.getString(
				headerElement.attributeValue("type-uuid"));

			LayoutSetPrototype existingLayoutSetPrototype = null;

			if (Validator.isNotNull(importedLayoutSetPrototypeUuid)) {
				try {
					existingLayoutSetPrototype =
						_layoutSetPrototypeLocalService.
							getLayoutSetPrototypeByUuidAndCompanyId(
								importedLayoutSetPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutSetPrototypeException
							noSuchLayoutSetPrototypeException) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(
							noSuchLayoutSetPrototypeException,
							noSuchLayoutSetPrototypeException);
					}
				}
			}

			if (existingLayoutSetPrototype == null) {
				LayoutSetPrototype layoutSetPrototype =
					_layoutSetPrototypeLocalService.getLayoutSetPrototype(
						group.getClassPK());

				List<LayoutSet> layoutSets =
					_layoutSetLocalService.
						getLayoutSetsByLayoutSetPrototypeUuid(
							layoutSetPrototype.getUuid());

				layoutSetPrototype.setUuid(importedLayoutSetPrototypeUuid);

				_layoutSetPrototypeLocalService.updateLayoutSetPrototype(
					layoutSetPrototype);

				for (LayoutSet curLayoutSet : layoutSets) {
					curLayoutSet.setLayoutSetPrototypeUuid(
						importedLayoutSetPrototypeUuid);

					_layoutSetLocalService.updateLayoutSet(curLayoutSet);
				}
			}
		}
		else if (larType.equals("layout-set-prototype")) {
			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
				new String[] {Boolean.TRUE.toString()});

			layoutSetPrototypeUuid = GetterUtil.getString(
				headerElement.attributeValue("type-uuid"));
		}

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			portletDataContext.setLayoutSetPrototypeUuid(
				layoutSetPrototypeUuid);
		}

		List<Element> portletElements = fetchPortletElements(rootElement);

		if (permissions) {
			for (Element portletElement : portletElements) {
				String portletPath = portletElement.attributeValue("path");

				Document portletDocument = SAXReaderUtil.read(
					portletDataContext.getZipEntryAsString(portletPath));

				_permissionImporter.checkRoles(
					companyId, portletDataContext.getGroupId(), userId,
					portletDocument.getRootElement());
			}

			_permissionImporter.readPortletDataPermissions(portletDataContext);
		}

		if (!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			_portletImportController.readExpandoTables(portletDataContext);
		}

		_portletImportController.readLocks(portletDataContext);

		// Import the group

		Element groupsElement = portletDataContext.getImportDataGroupElement(
			StagedGroup.class);

		for (Element groupElement : groupsElement.elements()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, groupElement);
		}

		int buildNumber = GetterUtil.getInteger(
			headerElement.attributeValue("build-number"));

		if (buildNumber < 7100) {
			importLayoutsFromLegacyLar(
				portletDataContext, layoutSetPrototypeUuid, serviceContext,
				portletElements, manifestSummary, userId);
		}

		// Asset links

		_portletImportController.importAssetLinks(portletDataContext);

		// Site

		_groupLocalService.updateSite(portletDataContext.getGroupId(), true);

		if (_log.isInfoEnabled()) {
			_log.info("Importing layouts takes " + stopWatch.getTime() + " ms");
		}

		ZipReader zipReader = portletDataContext.getZipReader();

		zipReader.close();
	}

	protected List<Element> fetchPortletElements(Element rootElement) {
		List<Element> portletElements = new ArrayList<>();

		// Site portlets

		Element sitePortletsElement = rootElement.element("site-portlets");

		// LAR compatibility

		if (sitePortletsElement == null) {
			sitePortletsElement = rootElement.element("portlets");
		}

		portletElements.addAll(sitePortletsElement.elements("portlet"));

		// Layout portlets

		XPath xPath = SAXReaderUtil.createXPath(
			"staged-model/portlets/portlet");

		Element layoutsElement = rootElement.element(
			Layout.class.getSimpleName());

		List<Node> nodes = xPath.selectNodes(layoutsElement);

		Stream<Node> nodesStream = nodes.stream();

		nodesStream.map(
			node -> (Element)node
		).forEach(
			portletElements::add
		);

		return portletElements;
	}

	protected PortletDataContext getPortletDataContext(
			ExportImportConfiguration exportImportConfiguration, File file)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long userId = MapUtil.getLong(settingsMap, "userId");
		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			settingsMap, "privateLayout");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		Group group = _groupLocalService.getGroup(targetGroupId);

		String userIdStrategyString = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		UserIdStrategy userIdStrategy = _exportImportHelper.getUserIdStrategy(
			userId, userIdStrategyString);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			_portletDataContextFactory.createImportPortletDataContext(
				group.getCompanyId(), targetGroupId, parameterMap,
				userIdStrategy, zipReader);

		portletDataContext.setExportImportProcessId(
			String.valueOf(
				exportImportConfiguration.getExportImportConfigurationId()));
		portletDataContext.setPrivateLayout(privateLayout);

		return portletDataContext;
	}

	protected int getProcessFlag() {
		if (ExportImportThreadLocal.isLayoutStagingInProcess()) {
			return ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;
		}

		return ExportImportLifecycleConstants.
			PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS;
	}

	protected void importLayoutsFromLegacyLar(
			PortletDataContext portletDataContext,
			String layoutSetPrototypeUuid, ServiceContext serviceContext,
			List<Element> portletElements, ManifestSummary manifestSummary,
			long userId)
		throws Exception {

		// Layouts

		Set<Layout> modifiedLayouts = new HashSet<>();

		// Remove layouts that were deleted from the layout set prototype

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean layoutSetPrototypeLinkEnabled = MapUtil.getBoolean(
			parameterMap,
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED);

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		if (group.isLayoutSetPrototype()) {
			layoutSetPrototypeLinkEnabled = false;

			parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
				new String[] {Boolean.FALSE.toString()});
		}

		if (Validator.isNotNull(layoutSetPrototypeUuid) &&
			layoutSetPrototypeLinkEnabled) {

			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid,
						portletDataContext.getCompanyId());

			List<Layout> previousLayouts = _layoutLocalService.getLayouts(
				portletDataContext.getGroupId(),
				portletDataContext.isPrivateLayout());

			for (Layout layout : previousLayouts) {
				if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
					continue;
				}

				if (SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {
					modifiedLayouts.add(layout);

					continue;
				}

				Layout sourcePrototypeLayout =
					_layoutLocalService.fetchLayoutByUuidAndGroupId(
						layout.getSourcePrototypeLayoutUuid(),
						layoutSetPrototype.getGroupId(), true);

				if (sourcePrototypeLayout == null) {
					_layoutLocalService.deleteLayout(layout, serviceContext);
				}
			}
		}

		Element stagedLayoutSetsElement =
			portletDataContext.getImportDataGroupElement(StagedLayoutSet.class);

		List<Element> stagedLayoutSetElements =
			stagedLayoutSetsElement.elements();

		for (Element stagedLayoutSetElement : stagedLayoutSetElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, stagedLayoutSetElement);
		}

		Element layoutsElement = portletDataContext.getImportDataGroupElement(
			Layout.class);

		List<Element> layoutElements = layoutsElement.elements();

		if (_log.isDebugEnabled() && !layoutElements.isEmpty()) {
			_log.debug("Importing layouts");
		}

		for (Element layoutElement : layoutElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutElement);
		}

		// Import portlets

		if (_log.isDebugEnabled() && !portletElements.isEmpty()) {
			_log.debug("Importing portlets");
		}

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		for (Element portletElement : portletElements) {
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
				plid = layout.getPlid();

				if (modifiedLayouts.contains(layout)) {
					continue;
				}
			}

			portletDataContext.setPlid(plid);

			portletDataContext.setPortletId(portletId);

			if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
				PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
					"portlet", portletId, manifestSummary);
			}

			String portletPath = portletElement.attributeValue("path");

			Document portletDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(portletPath));

			portletElement = portletDocument.getRootElement();

			// The order of the import is important. You must always import the
			// portlet preferences first, then the portlet data, then the
			// portlet permissions. The import of the portlet data assumes that
			// portlet preferences already exist.

			long portletPreferencesGroupId = portletDataContext.getGroupId();

			Element portletDataElement = portletElement.element("portlet-data");

			Map<String, Boolean> importPortletControlsMap =
				_exportImportHelper.getImportPortletControlsMap(
					portletDataContext.getCompanyId(), portletId, parameterMap,
					portletDataElement, manifestSummary);

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

			boolean permissions = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PERMISSIONS);

			if (permissions) {
				_permissionImporter.importPortletPermissions(
					portletDataContext.getCompanyId(),
					portletDataContext.getGroupId(), userId, layout,
					portletElement, portletId);
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

		// Import services

		if (_log.isDebugEnabled() && !portletElements.isEmpty()) {
			_log.debug("Importing services");
		}

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element servicesElement = rootElement.element("services");

		List<Element> serviceElements = servicesElement.elements("service");

		for (Element serviceElement : serviceElements) {
			String path = serviceElement.attributeValue("path");

			Document serviceDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(path));

			serviceElement = serviceDocument.getRootElement();

			_portletImportController.importServicePortletPreferences(
				portletDataContext, serviceElement);
		}
	}

	protected void populateDeletionStagedModelTypes(
			PortletDataContext portletDataContext)
		throws Exception {

		List<Element> portletElements = fetchPortletElements(
			portletDataContext.getImportDataRootElement());

		for (Element portletElement : portletElements) {
			String portletId = portletElement.attributeValue("portlet-id");

			Portlet portlet = _portletLocalService.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if ((portlet == null) || !portlet.isActive() ||
				portlet.isUndeployedPortlet()) {

				continue;
			}

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(
					portletDataContext.getCompanyId(), portletId);

			if (portletDataHandler == null) {
				continue;
			}

			portletDataContext.addDeletionSystemEventStagedModelTypes(
				portletDataHandler.getDeletionSystemEventStagedModelTypes());
		}

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(Layout.class));
		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(SegmentsExperience.class, Layout.class));
		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(StagedAssetLink.class));
	}

	protected void validateFile(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			ZipReader zipReader)
		throws Exception {

		// XML

		String xml = zipReader.getEntryAsString("/manifest.xml");

		if (xml == null) {
			throw new LARFileException(LARFileException.TYPE_MISSING_MANIFEST);
		}

		Element rootElement = null;

		try {
			Document document = SAXReaderUtil.read(xml);

			rootElement = document.getRootElement();
		}
		catch (Exception exception) {
			throw new LARFileException(
				LARFileException.TYPE_INVALID_MANIFEST, exception);
		}

		// Bundle compatibility

		Element headerElement = rootElement.element("header");

		int importBuildNumber = GetterUtil.getInteger(
			headerElement.attributeValue("build-number"));

		if (importBuildNumber < ReleaseInfo.RELEASE_7_0_0_BUILD_NUMBER) {
			int buildNumber = ReleaseInfo.getBuildNumber();

			if (buildNumber != importBuildNumber) {
				throw new LayoutImportException(
					LayoutImportException.TYPE_WRONG_BUILD_NUMBER,
					new Object[] {importBuildNumber, buildNumber});
			}
		}
		else {
			BiPredicate<Version, Version> majorVersionBiPredicate =
				(currentVersion, importVersion) -> Objects.equals(
					currentVersion.getMajor(), importVersion.getMajor());

			BiPredicate<Version, Version> minorVersionBiPredicate =
				(currentVersion, importVersion) -> {
					int currentMinorVersion = GetterUtil.getInteger(
						currentVersion.getMinor(), -1);
					int importedMinorVersion = GetterUtil.getInteger(
						importVersion.getMinor(), -1);

					if (((currentMinorVersion == -1) &&
						 (importedMinorVersion == -1)) ||
						(currentMinorVersion < importedMinorVersion)) {

						return false;
					}

					return true;
				};

			BiPredicate<Version, Version> manifestVersionBiPredicate =
				(currentVersion, importVersion) -> {
					BiPredicate<Version, Version> versionBiPredicate =
						majorVersionBiPredicate.and(minorVersionBiPredicate);

					return versionBiPredicate.test(
						currentVersion, importVersion);
				};

			String importSchemaVersion = GetterUtil.getString(
				headerElement.attributeValue("schema-version"), "1.0.0");

			if (!manifestVersionBiPredicate.test(
					Version.getInstance(
						ExportImportConstants.EXPORT_IMPORT_SCHEMA_VERSION),
					Version.getInstance(importSchemaVersion))) {

				throw new LayoutImportException(
					LayoutImportException.TYPE_WRONG_LAR_SCHEMA_VERSION,
					new Object[] {
						importSchemaVersion,
						ExportImportConstants.EXPORT_IMPORT_SCHEMA_VERSION
					});
			}
		}

		// Type

		String larType = headerElement.attributeValue("type");

		String[] expectedLARTypes = {
			"layout-prototype", "layout-set", "layout-set-prototype"
		};

		Stream<String> stream = Stream.of(expectedLARTypes);

		if (stream.noneMatch(lt -> lt.equals(larType))) {
			throw new LARTypeException(larType, expectedLARTypes);
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		String layoutsImportMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE);

		if (larType.equals("layout-prototype") && !group.isLayoutPrototype() &&
			!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			throw new LARTypeException(LARTypeException.TYPE_LAYOUT_PROTOTYPE);
		}

		if (larType.equals("layout-set")) {
			if (group.isLayoutPrototype() || group.isLayoutSetPrototype()) {
				throw new LARTypeException(LARTypeException.TYPE_LAYOUT_SET);
			}

			long sourceCompanyGroupId = GetterUtil.getLong(
				headerElement.attributeValue("company-group-id"));
			long sourceGroupId = GetterUtil.getLong(
				headerElement.attributeValue("group-id"));

			boolean companySourceGroup = false;

			if (sourceCompanyGroupId == sourceGroupId) {
				companySourceGroup = true;
			}
			else if (ExportImportThreadLocal.isStagingInProcess() &&
					 (group.isStaged() || group.hasStagingGroup()) &&
					 !(group.isStagedRemotely() &&
					   group.hasRemoteStagingGroup())) {

				Group sourceGroup = _groupLocalService.fetchGroup(
					sourceGroupId);

				companySourceGroup = sourceGroup.isCompany();
			}

			if (group.isCompany() ^ companySourceGroup) {
				throw new LARTypeException(LARTypeException.TYPE_COMPANY_GROUP);
			}
		}

		if (larType.equals("layout-set-prototype") &&
			!group.isLayoutSetPrototype() &&
			!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			throw new LARTypeException(
				LARTypeException.TYPE_LAYOUT_SET_PROTOTYPE);
		}

		// Portlets compatibility

		List<Element> portletElements = fetchPortletElements(rootElement);

		for (Element portletElement : portletElements) {
			String portletId = GetterUtil.getString(
				portletElement.attributeValue("portlet-id"));

			if (Validator.isNull(portletId)) {
				continue;
			}

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(companyId, portletId);

			if (portletDataHandler == null) {
				continue;
			}

			String schemaVersion = GetterUtil.getString(
				portletElement.attributeValue("schema-version"));

			if (!portletDataHandler.validateSchemaVersion(schemaVersion)) {
				throw new LayoutImportException(
					LayoutImportException.TYPE_WRONG_PORTLET_SCHEMA_VERSION,
					new Object[] {
						schemaVersion, portletId,
						portletDataHandler.getSchemaVersion()
					});
			}
		}

		// Available locales

		List<Locale> sourceAvailableLocales = Arrays.asList(
			LocaleUtil.fromLanguageIds(
				StringUtil.split(
					headerElement.attributeValue("available-locales"))));

		for (Locale sourceAvailableLocale : sourceAvailableLocales) {
			if (!LanguageUtil.isAvailableLocale(
					groupId, sourceAvailableLocale)) {

				LocaleException localeException = new LocaleException(
					LocaleException.TYPE_EXPORT_IMPORT);

				localeException.setSourceAvailableLocales(
					sourceAvailableLocales);
				localeException.setTargetAvailableLocales(
					LanguageUtil.getAvailableLocales(groupId));

				throw localeException;
			}
		}

		// Layout prototypes validity

		Element layoutsElement = rootElement.element(
			Layout.class.getSimpleName());

		validateLayoutPrototypes(companyId, headerElement, layoutsElement);
	}

	protected void validateLayoutPrototypes(
			long companyId, Element headerElement, Element layoutsElement)
		throws Exception {

		List<Tuple> missingLayoutPrototypes = new ArrayList<>();

		String layoutSetPrototypeUuid = headerElement.attributeValue(
			"layout-set-prototype-uuid");

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			try {
				_layoutSetPrototypeLocalService.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);
			}
			catch (NoSuchLayoutSetPrototypeException
						noSuchLayoutSetPrototypeException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(
						noSuchLayoutSetPrototypeException,
						noSuchLayoutSetPrototypeException);
				}

				String layoutSetPrototypeName = headerElement.attributeValue(
					"layout-set-prototype-name");

				missingLayoutPrototypes.add(
					new Tuple(
						LayoutSetPrototype.class.getName(),
						layoutSetPrototypeUuid, layoutSetPrototypeName));
			}
		}

		List<Element> layoutElements = layoutsElement.elements();

		for (Element layoutElement : layoutElements) {
			String action = layoutElement.attributeValue(Constants.ACTION);

			if (action.equals(Constants.SKIP)) {
				continue;
			}

			boolean layoutPrototypeGlobal = GetterUtil.getBoolean(
				layoutElement.attributeValue(
					"layout-prototype-global", "true"));

			if (!layoutPrototypeGlobal) {
				continue;
			}

			String layoutPrototypeUuid = GetterUtil.getString(
				layoutElement.attributeValue("layout-prototype-uuid"));

			if (Validator.isNull(layoutPrototypeUuid)) {
				continue;
			}

			String layoutPrototypeName = GetterUtil.getString(
				layoutElement.attributeValue("layout-prototype-name"));

			boolean preloaded = GetterUtil.getBoolean(
				layoutElement.attributeValue("preloaded"));

			if (!preloaded) {
				LayoutPrototype layoutPrototype =
					_layoutPrototypeLocalService.
						fetchLayoutPrototypeByUuidAndCompanyId(
							layoutPrototypeUuid, companyId);

				if (layoutPrototype == null) {
					missingLayoutPrototypes.add(
						new Tuple(
							LayoutPrototype.class.getName(),
							layoutPrototypeUuid, layoutPrototypeName));
				}
			}
			else {
				LayoutPrototype layoutPrototype =
					_layoutPrototypeLocalService.fetchLayoutProtoype(
						companyId, layoutPrototypeName);

				if (layoutPrototype == null) {
					missingLayoutPrototypes.add(
						new Tuple(
							LayoutPrototype.class.getName(),
							layoutPrototypeUuid, layoutPrototypeName));
				}
			}
		}

		if (!missingLayoutPrototypes.isEmpty()) {
			throw new LayoutPrototypeException(missingLayoutPrototypes);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutImportController.class);

	private final DeletionSystemEventImporter _deletionSystemEventImporter =
		DeletionSystemEventImporter.getInstance();

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLifecycleManager _exportImportLifecycleManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private PermissionImporter _permissionImporter;

	@Reference
	private PortletDataContextFactory _portletDataContextFactory;

	@Reference
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	@Reference
	private PortletImportController _portletImportController;

	@Reference
	private PortletLocalService _portletLocalService;

}