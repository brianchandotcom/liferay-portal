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

package com.liferay.exportimport.controller;

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.constants.ExportImportConstants;
import com.liferay.exportimport.kernel.controller.ExportImportController;
import com.liferay.exportimport.kernel.controller.ImportController;
import com.liferay.exportimport.kernel.exception.LARFileException;
import com.liferay.exportimport.kernel.exception.LARTypeException;
import com.liferay.exportimport.kernel.exception.LayoutImportException;
import com.liferay.exportimport.kernel.exception.MissingReferenceException;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.lar.DeletionSystemEventImporter;
import com.liferay.exportimport.lar.LayoutCache;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
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
import com.liferay.site.model.adapter.StagedGroup;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
	property = {"model.class.name=com.liferay.portal.kernel.model.Layout"},
	service = {ExportImportController.class, LayoutImportController.class}
)
@ProviderType
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
				EVENT_LAYOUT_IMPORT_STARTED, getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long userId = MapUtil.getLong(settingsMap, "userId");

			doImportFile(portletDataContext, userId);

			ExportImportThreadLocal.setLayoutImportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_LAYOUT_IMPORT_SUCCEEDED, getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				userId);
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setLayoutImportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_LAYOUT_IMPORT_FAILED, getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				PortletDataContextFactoryUtil.clonePortletDataContext(
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
				ExportImportHelperUtil.validateMissingReferences(
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

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void deleteMissingLayouts(
			PortletDataContext portletDataContext,
			List<String> sourceLayoutUuids, List<Layout> previousLayouts,
			ServiceContext serviceContext)
		throws Exception {
	}

	protected void deletePortletData(PortletDataContext portletDataContext)
		throws Exception {

		List<Element> portletElements = fetchPortletElements(
			portletDataContext.getImportDataRootElement());

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		if (_log.isDebugEnabled()) {
			if (!portletElements.isEmpty()) {
				_log.debug("Deleting portlet data");
			}
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

		LayoutCache layoutCache = new LayoutCache();

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
			ExportImportHelperUtil.getManifestSummary(portletDataContext);

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

			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.getLayoutPrototype(
					group.getClassPK());

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
				catch (NoSuchLayoutPrototypeException nslpe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(nslpe, nslpe);
					}
				}
			}

			if (existingLayoutPrototype == null) {
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

			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.getLayoutSetPrototype(
					group.getClassPK());

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
				catch (NoSuchLayoutSetPrototypeException nslspe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(nslspe, nslspe);
					}
				}
			}

			if (existingLayoutSetPrototype == null) {
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
					layoutCache, companyId, portletDataContext.getGroupId(),
					userId, portletDocument.getRootElement());
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
			(node) -> (Element)node
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

		UserIdStrategy userIdStrategy =
			ExportImportHelperUtil.getUserIdStrategy(
				userId, userIdStrategyString);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
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
			return PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;
		}

		return PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS;
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void importLayout(
			PortletDataContext portletDataContext,
			List<String> sourceLayoutsUuids, Element layoutElement)
		throws Exception {
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
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setExportImportLifecycleManager(
		ExportImportLifecycleManager exportImportLifecycleManager) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setPortletImportController(
		PortletImportController portletImportController) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void setPortletScope(
		PortletDataContext portletDataContext, Element portletElement) {
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void updateLayoutPriorities(
		PortletDataContext portletDataContext, List<Element> layoutElements,
		boolean privateLayout) {
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
		catch (Exception e) {
			throw new LARFileException(
				LARFileException.TYPE_INVALID_MANIFEST, e);
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

		String[] expectedLARTypes = new String[] {
			"layout-prototype", "layout-set", "layout-set-prototype"
		};

		if (Stream.of(expectedLARTypes).noneMatch(lt -> lt.equals(larType))) {
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
			else if ((group.isStaged() || group.hasStagingGroup()) &&
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

			String schemaVersion = GetterUtil.getString(
				portletElement.attributeValue("schema-version"));

			PortletDataHandler portletDataHandler =
				_portletDataHandlerProvider.provide(companyId, portletId);

			if (portletDataHandler == null) {
				continue;
			}

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

				LocaleException le = new LocaleException(
					LocaleException.TYPE_EXPORT_IMPORT);

				le.setSourceAvailableLocales(sourceAvailableLocales);
				le.setTargetAvailableLocales(
					LanguageUtil.getAvailableLocales(groupId));

				throw le;
			}
		}

		// Layout prototypes validity

		Element layoutsElement = rootElement.element(
			Layout.class.getSimpleName());

		validateLayoutPrototypes(companyId, headerElement, layoutsElement);
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #validateLayoutPrototypes(long, Element, Element)}
	 */
	@Deprecated
	protected void validateLayoutPrototypes(
			long companyId, Element layoutsElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method is deprecated and replaced by " +
				"#validateLayoutPrototypes(long, Element, Element)");
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
			catch (NoSuchLayoutSetPrototypeException nslspe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nslspe, nslspe);
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

			String layoutPrototypeUuid = GetterUtil.getString(
				layoutElement.attributeValue("layout-prototype-uuid"));

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				try {
					_layoutPrototypeLocalService.
						getLayoutPrototypeByUuidAndCompanyId(
							layoutPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutPrototypeException nslpe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(nslpe, nslpe);
					}

					String layoutPrototypeName = GetterUtil.getString(
						layoutElement.attributeValue("layout-prototype-name"));

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

	private final PermissionImporter _permissionImporter =
		PermissionImporter.getInstance();

	@Reference
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	@Reference
	private PortletImportController _portletImportController;

	@Reference
	private PortletLocalService _portletLocalService;

}