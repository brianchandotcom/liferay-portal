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

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.adapter.StagedAssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.exportimport.kernel.controller.ExportController;
import com.liferay.exportimport.kernel.controller.ExportImportController;
import com.liferay.exportimport.kernel.exception.LayoutImportException;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.lar.DeletionSystemEventExporter;
import com.liferay.exportimport.lar.PermissionExporter;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessorRegistryUtil;
import com.liferay.petra.xml.DocUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletItemLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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
 * @author Zsigmond Rab
 * @author Douglas Wong
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.kernel.model.Portlet"},
	service = {ExportImportController.class, PortletExportController.class}
)
@ProviderType
public class PortletExportController implements ExportController {

	@Override
	public File export(ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		PortletDataContext portletDataContext = null;

		try {
			ExportImportThreadLocal.setPortletExportInProcess(true);

			portletDataContext = getPortletDataContext(
				exportImportConfiguration);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_EXPORT_STARTED, getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			File file = doExport(portletDataContext);

			ExportImportThreadLocal.setPortletExportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_EXPORT_SUCCEEDED, getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			return file;
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setPortletExportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_EXPORT_FAILED, getProcessFlag(),
				String.valueOf(
					exportImportConfiguration.getExportImportConfigurationId()),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
	}

	public void exportPortlet(
			PortletDataContext portletDataContext, long plid,
			Element parentElement, boolean exportPermissions,
			boolean exportPortletArchivedSetups, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletUserPreferences)
		throws Exception {

		long layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout != null) {
			plid = layout.getPlid();
			layoutId = layout.getLayoutId();
		}

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(),
			portletDataContext.getPortletId());

		if ((portlet == null) || portlet.isUndeployedPortlet()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not export portlet " +
						portletDataContext.getPortletId() +
							" because the portlet is not deployed");
			}

			return;
		}

		if (!portlet.isInstanceable() &&
			!portlet.isPreferencesUniquePerLayout() &&
			portletDataContext.hasNotUniquePerLayout(
				portletDataContext.getPortletId())) {

			return;
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			PortletDataContext clonedPortletDataContext =
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext);

			ManifestSummary manifestSummary =
				clonedPortletDataContext.getManifestSummary();

			manifestSummary.resetCounters();

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			portletDataHandler.prepareManifestSummary(clonedPortletDataContext);

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"portlet", portletDataContext.getPortletId(), manifestSummary);
		}

		Document document = SAXReaderUtil.createDocument();

		Element portletElement = document.addElement("portlet");

		portletElement.addAttribute(
			"portlet-id", portletDataContext.getPortletId());
		portletElement.addAttribute(
			"root-portlet-id", portletDataContext.getRootPortletId());
		portletElement.addAttribute("old-plid", String.valueOf(plid));
		portletElement.addAttribute(
			"scope-group-id",
			String.valueOf(portletDataContext.getScopeGroupId()));
		portletElement.addAttribute(
			"scope-layout-type", portletDataContext.getScopeType());
		portletElement.addAttribute(
			"scope-layout-uuid", portletDataContext.getScopeLayoutUuid());
		portletElement.addAttribute(
			"private-layout",
			String.valueOf(portletDataContext.isPrivateLayout()));

		// Data

		if (exportPortletData) {
			javax.portlet.PortletPreferences jxPortletPreferences = null;

			if (ExportImportThreadLocal.isInitialLayoutStagingInProcess()) {
				if (layout != null) {
					Group liveGroup = layout.getGroup();

					Group stagingGroup = liveGroup.getStagingGroup();

					layout.setGroupId(stagingGroup.getGroupId());

					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							layout, portletDataContext.getPortletId());

					layout.setGroupId(liveGroup.getGroupId());
				}
				else {
					Group liveGroup = _groupLocalService.getGroup(
						portletDataContext.getGroupId());

					Group stagingGroup = liveGroup.getStagingGroup();

					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							portletDataContext.getCompanyId(),
							stagingGroup.getGroupId(),
							portletDataContext.getPortletId());
				}
			}
			else {
				if (layout != null) {
					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							layout, portletDataContext.getPortletId());
				}
				else {
					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							portletDataContext.getCompanyId(),
							portletDataContext.getGroupId(),
							portletDataContext.getPortletId());
				}
			}

			if (!portlet.isPreferencesUniquePerLayout()) {
				StringBundler sb = new StringBundler(5);

				sb.append(portletDataContext.getPortletId());
				sb.append(StringPool.AT);
				sb.append(portletDataContext.getScopeType());
				sb.append(StringPool.AT);
				sb.append(portletDataContext.getScopeLayoutUuid());

				String dataKey = sb.toString();

				if (!portletDataContext.hasNotUniquePerLayout(dataKey)) {
					portletDataContext.putNotUniquePerLayout(dataKey);

					exportPortletData(
						portletDataContext, portlet, layout,
						jxPortletPreferences, portletElement);
				}
			}
			else {
				exportPortletData(
					portletDataContext, portlet, layout, jxPortletPreferences,
					portletElement);
			}
		}

		// Portlet preferences

		if (exportPortletSetup) {

			// Company

			exportPortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, false, layout, plid,
				portlet.getRootPortletId(), portletElement);

			// Group

			exportPortletPreferences(
				portletDataContext, portletDataContext.getScopeGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, false, layout,
				PortletKeys.PREFS_PLID_SHARED, portlet.getRootPortletId(),
				portletElement);

			// Group embedded portlets

			exportPortletPreferences(
				portletDataContext, portletDataContext.getScopeGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false, layout,
				PortletKeys.PREFS_PLID_SHARED, portlet.getPortletId(),
				portletElement);

			// Layout

			exportPortletPreferences(
				portletDataContext, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false, layout, plid,
				portletDataContext.getPortletId(), portletElement);
		}

		// Portlet user preferences

		if (exportPortletUserPreferences) {
			List<PortletPreferences> portletPreferencesList =
				_portletPreferencesLocalService.getPortletPreferences(
					PortletKeys.PREFS_OWNER_TYPE_USER, plid,
					portletDataContext.getPortletId());

			for (PortletPreferences portletPreferences :
					portletPreferencesList) {

				boolean defaultUser = false;

				if (portletPreferences.getOwnerId() ==
						PortletKeys.PREFS_OWNER_ID_DEFAULT) {

					defaultUser = true;
				}

				exportPortletPreferences(
					portletDataContext, portletPreferences.getOwnerId(),
					PortletKeys.PREFS_OWNER_TYPE_USER, defaultUser, layout,
					plid, portletDataContext.getPortletId(), portletElement);
			}

			try {
				PortletPreferences groupPortletPreferences =
					_portletPreferencesLocalService.getPortletPreferences(
						portletDataContext.getScopeGroupId(),
						PortletKeys.PREFS_OWNER_TYPE_GROUP,
						PortletKeys.PREFS_PLID_SHARED,
						portlet.getRootPortletId());

				exportPortletPreference(
					portletDataContext, portletDataContext.getScopeGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
					groupPortletPreferences, portlet.getRootPortletId(),
					PortletKeys.PREFS_PLID_SHARED, portletElement);
			}
			catch (NoSuchPortletPreferencesException nsppe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsppe, nsppe);
				}
			}
		}

		// Archived setups

		if (exportPortletArchivedSetups) {
			List<PortletItem> portletItems =
				_portletItemLocalService.getPortletItems(
					portletDataContext.getGroupId(),
					portletDataContext.getRootPortletId(),
					PortletPreferences.class.getName());

			for (PortletItem portletItem : portletItems) {
				exportPortletPreferences(
					portletDataContext, portletItem.getPortletItemId(),
					PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, false, null, plid,
					portletItem.getPortletId(), portletElement);
			}
		}

		// Permissions

		if (exportPermissions) {
			_permissionExporter.exportPortletPermissions(
				portletDataContext, portletDataContext.getPortletId(), layout,
				portletElement);
		}

		// Zip

		StringBundler pathSB = new StringBundler(4);

		pathSB.append(ExportImportPathUtil.getPortletPath(portletDataContext));
		pathSB.append(StringPool.SLASH);
		pathSB.append(plid);
		pathSB.append("/portlet.xml");

		String path = pathSB.toString();

		Element element = parentElement.addElement("portlet");

		element.addAttribute("portlet-id", portletDataContext.getPortletId());
		element.addAttribute("layout-id", String.valueOf(layoutId));
		element.addAttribute("path", path);
		element.addAttribute("portlet-data", String.valueOf(exportPortletData));

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		element.addAttribute(
			"schema-version", portletDataHandler.getSchemaVersion());

		StringBundler configurationOptionsSB = new StringBundler(6);

		if (exportPortletSetup) {
			configurationOptionsSB.append("setup");
			configurationOptionsSB.append(StringPool.COMMA);
		}

		if (exportPortletArchivedSetups) {
			configurationOptionsSB.append("archived-setups");
			configurationOptionsSB.append(StringPool.COMMA);
		}

		if (exportPortletUserPreferences) {
			configurationOptionsSB.append("user-preferences");
			configurationOptionsSB.append(StringPool.COMMA);
		}

		if (configurationOptionsSB.index() > 0) {
			configurationOptionsSB.setIndex(configurationOptionsSB.index() - 1);
		}

		element.addAttribute(
			"portlet-configuration", configurationOptionsSB.toString());

		try {
			portletDataContext.addZipEntry(path, document.formattedString());
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe.getMessage());
			}
		}
	}

	public void exportPortletData(
			PortletDataContext portletDataContext, Portlet portlet,
			Layout layout,
			javax.portlet.PortletPreferences jxPortletPreferences,
			Element parentElement)
		throws Exception {

		if (portlet == null) {
			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if ((portletDataHandler == null) ||
			portletDataHandler.isDataPortletInstanceLevel()) {

			return;
		}

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		long plid = LayoutConstants.DEFAULT_PLID;

		if (layout != null) {
			group = layout.getGroup();
			plid = layout.getPlid();
		}

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		String portletId = portlet.getPortletId();

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!group.isStagedPortlet(portletId)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because it is configured not to be staged");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting data for " + portletId);
		}

		String path = ExportImportPathUtil.getPortletDataPath(
			portletDataContext);

		if (portletDataContext.hasPrimaryKey(String.class, path)) {
			return;
		}

		Date originalStartDate = portletDataContext.getStartDate();

		Date portletLastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletDataContext, jxPortletPreferences);

		portletDataContext.setStartDate(portletLastPublishDate);

		long groupId = portletDataContext.getGroupId();

		portletDataContext.setGroupId(portletDataContext.getScopeGroupId());

		portletDataContext.clearScopedPrimaryKeys();

		String data = null;

		try {
			data = portletDataHandler.exportData(
				portletDataContext, portletId, jxPortletPreferences);
		}
		finally {
			portletDataContext.setGroupId(groupId);
			portletDataContext.setStartDate(originalStartDate);
		}

		if (Validator.isNull(data)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		Element portletDataElement = parentElement.addElement("portlet-data");

		portletDataElement.addAttribute("path", path);

		portletDataContext.addZipEntry(path, data);

		boolean updateLastPublishDate = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (ExportImportThreadLocal.isStagingInProcess() &&
			updateLastPublishDate) {

			DateRange adjustedDateRange = new DateRange(
				portletLastPublishDate, portletDataContext.getEndDate());

			ExportImportProcessCallbackRegistryUtil.registerCallback(
				portletDataContext.getExportImportProcessId(),
				new UpdatePortletLastPublishDateCallable(
					adjustedDateRange, portletDataContext.getEndDate(),
					portletDataContext.getGroupId(), plid, portletId));
		}
	}

	public void exportService(
			PortletDataContext portletDataContext, Element rootElement,
			boolean exportServiceSetup)
		throws Exception {

		if (!exportServiceSetup) {
			return;
		}

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(
				portletDataContext.getPortletId());

		if (portletDataHandler == null) {
			return;
		}

		String serviceName = portletDataHandler.getServiceName();

		if (Validator.isNotNull(serviceName)) {

			// Company service

			exportServicePortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, serviceName, rootElement);

			// Group service

			exportServicePortletPreferences(
				portletDataContext, portletDataContext.getScopeGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, serviceName, rootElement);
		}
	}

	protected File doExport(PortletDataContext portletDataContext)
		throws Exception {

		boolean exportPermissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Layout layout = _layoutLocalService.getLayout(
			portletDataContext.getPlid());

		if (!layout.isTypeControlPanel() && !layout.isTypePanel() &&
			!layout.isTypePortlet()) {

			StringBundler sb = new StringBundler(4);

			sb.append("Unable to export layout ");
			sb.append(layout.getPlid());
			sb.append(" because it has an invalid type: ");
			sb.append(layout.getType());

			throw new LayoutImportException(sb.toString());
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(layout.getCompanyId());
			serviceContext.setSignedIn(false);

			long defaultUserId = _userLocalService.getDefaultUserId(
				layout.getCompanyId());

			serviceContext.setUserId(defaultUserId);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		serviceContext.setAttribute("layoutSetBranchId", layoutSetBranchId);

		long scopeGroupId = portletDataContext.getGroupId();

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletDataContext.getPortletId());

		String scopeType = GetterUtil.getString(
			jxPortletPreferences.getValue("lfrScopeType", null));
		String scopeLayoutUuid = GetterUtil.getString(
			jxPortletPreferences.getValue("lfrScopeLayoutUuid", null));

		if (Validator.isNotNull(scopeType)) {
			Group scopeGroup = null;

			if (scopeType.equals("company")) {
				scopeGroup = _groupLocalService.getCompanyGroup(
					layout.getCompanyId());
			}
			else if (Validator.isNotNull(scopeLayoutUuid)) {
				scopeGroup = layout.getScopeGroup();
			}

			if (scopeGroup != null) {
				scopeGroupId = scopeGroup.getGroupId();
			}
		}

		portletDataContext.setScopeType(scopeType);
		portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		Element headerElement = rootElement.addElement("header");

		headerElement.addAttribute(
			"available-locales",
			StringUtil.merge(
				LanguageUtil.getAvailableLocales(
					_portal.getSiteGroupId(
						portletDataContext.getScopeGroupId()))));
		headerElement.addAttribute(
			"build-number", String.valueOf(ReleaseInfo.getBuildNumber()));
		headerElement.addAttribute("export-date", Time.getRFC822());

		if (portletDataContext.hasDateRange()) {
			headerElement.addAttribute(
				"start-date",
				String.valueOf(portletDataContext.getStartDate()));
			headerElement.addAttribute(
				"end-date", String.valueOf(portletDataContext.getEndDate()));
		}

		headerElement.addAttribute("type", portletDataContext.getType());
		headerElement.addAttribute(
			"company-id", String.valueOf(portletDataContext.getCompanyId()));
		headerElement.addAttribute(
			"company-group-id",
			String.valueOf(portletDataContext.getCompanyGroupId()));
		headerElement.addAttribute("group-id", String.valueOf(scopeGroupId));
		headerElement.addAttribute(
			"user-personal-site-group-id",
			String.valueOf(portletDataContext.getUserPersonalSiteGroupId()));
		headerElement.addAttribute(
			"private-layout", String.valueOf(layout.isPrivateLayout()));
		headerElement.addAttribute(
			"root-portlet-id", portletDataContext.getRootPortletId());

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		Map<String, Boolean> exportPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				layout.getCompanyId(), portletDataContext.getPortletId(),
				portletDataContext.getParameterMap());

		exportPortlet(
			portletDataContext, layout.getPlid(), rootElement,
			exportPermissions,
			exportPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
			exportPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_DATA),
			exportPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_SETUP),
			exportPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
		exportService(
			portletDataContext, rootElement,
			exportPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_SETUP));

		exportAssetLinks(portletDataContext);
		exportExpandoTables(portletDataContext);
		exportLocks(portletDataContext);

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(StagedAssetLink.class));

		_deletionSystemEventExporter.exportDeletionSystemEvents(
			portletDataContext);

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(
				portletDataContext);
		}

		ExportImportHelperUtil.writeManifestSummary(
			document, portletDataContext.getManifestSummary());

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet took " + stopWatch.getTime() + " ms");
		}

		try {
			portletDataContext.addZipEntry(
				"/manifest.xml", document.formattedString());
		}
		catch (IOException ioe) {
			throw new SystemException(
				"Unable to create the export LAR manifest file for portlet " +
					portletDataContext.getPortletId(),
				ioe);
		}

		ZipWriter zipWriter = portletDataContext.getZipWriter();

		return zipWriter.getFile();
	}

	protected void exportAssetLinks(PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("links");

		Element exportDataRootElement =
			portletDataContext.getExportDataRootElement();

		try {
			portletDataContext.setExportDataRootElement(rootElement);

			ActionableDynamicQuery linkActionableDynamicQuery =
				_assetLinkLocalService.getExportActionbleDynamicQuery(
					portletDataContext);

			linkActionableDynamicQuery.performActions();

			for (long linkId : portletDataContext.getAssetLinkIds()) {
				AssetLink assetLink = _assetLinkLocalService.getAssetLink(
					linkId);

				StagedAssetLink stagedAssetLink = ModelAdapterUtil.adapt(
					assetLink, AssetLink.class, StagedAssetLink.class);

				portletDataContext.addClassedModel(
					portletDataContext.getExportDataElement(stagedAssetLink),
					ExportImportPathUtil.getModelPath(stagedAssetLink),
					stagedAssetLink);
			}
		}
		finally {
			portletDataContext.setExportDataRootElement(exportDataRootElement);
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) + "/links.xml",
			document.formattedString());
	}

	protected void exportExpandoTables(PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("expando-tables");

		Map<String, List<ExpandoColumn>> expandoColumnsMap =
			portletDataContext.getExpandoColumns();

		for (Map.Entry<String, List<ExpandoColumn>> entry :
				expandoColumnsMap.entrySet()) {

			String className = entry.getKey();

			Element expandoTableElement = rootElement.addElement(
				"expando-table");

			expandoTableElement.addAttribute("class-name", className);

			List<ExpandoColumn> expandoColumns = entry.getValue();

			for (ExpandoColumn expandoColumn : expandoColumns) {
				Element expandoColumnElement = expandoTableElement.addElement(
					"expando-column");

				expandoColumnElement.addAttribute(
					"column-id", String.valueOf(expandoColumn.getColumnId()));
				expandoColumnElement.addAttribute(
					"name", expandoColumn.getName());
				expandoColumnElement.addAttribute(
					"type", String.valueOf(expandoColumn.getType()));

				DocUtil.add(
					expandoColumnElement, "default-data",
					expandoColumn.getDefaultData());

				Element typeSettingsElement = expandoColumnElement.addElement(
					"type-settings");

				UnicodeProperties typeSettingsProperties =
					expandoColumn.getTypeSettingsProperties();

				typeSettingsElement.addCDATA(typeSettingsProperties.toString());
			}
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/expando-tables.xml",
			document.formattedString());
	}

	protected void exportLocks(PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("locks");

		Map<String, Lock> locksMap = portletDataContext.getLocks();

		for (Map.Entry<String, Lock> entry : locksMap.entrySet()) {
			Lock lock = entry.getValue();

			String entryKey = entry.getKey();

			int pos = entryKey.indexOf(CharPool.POUND);

			String className = entryKey.substring(0, pos);
			String key = entryKey.substring(pos + 1);

			String path = getLockPath(portletDataContext, className, key, lock);

			Element assetElement = rootElement.addElement("asset");

			assetElement.addAttribute("path", path);
			assetElement.addAttribute("class-name", className);
			assetElement.addAttribute("key", key);

			portletDataContext.addZipEntry(path, lock);
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) + "/locks.xml",
			document.formattedString());
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected void exportPortlet(
			PortletDataContext portletDataContext, Layout layout,
			Element parentElement, boolean exportPermissions,
			boolean exportPortletArchivedSetups, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletUserPreferences)
		throws Exception {

		exportPortlet(
			portletDataContext, layout.getPlid(), parentElement,
			exportPermissions, exportPortletArchivedSetups, exportPortletData,
			exportPortletSetup, exportPortletUserPreferences);
	}

	protected void exportPortletPreference(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			boolean defaultUser, PortletPreferences portletPreferences,
			String portletId, long plid, Element parentElement)
		throws Exception {

		String preferencesXML = portletPreferences.getPreferences();

		if (Validator.isNull(preferencesXML)) {
			preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
		}

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		Element portletPreferencesElement = parentElement.addElement(
			"portlet-preferences");

		if ((portlet != null) &&
			(portlet.getPortletDataHandlerInstance() != null)) {

			Element exportDataRootElement =
				portletDataContext.getExportDataRootElement();

			try {
				portletDataContext.clearScopedPrimaryKeys();

				Element preferenceDataElement =
					portletPreferencesElement.addElement("preference-data");

				portletDataContext.setExportDataRootElement(
					preferenceDataElement);

				ExportImportPortletPreferencesProcessor
					exportImportPortletPreferencesProcessor =
						ExportImportPortletPreferencesProcessorRegistryUtil.
							getExportImportPortletPreferencesProcessor(
								portlet.getRootPortletId());

				if (exportImportPortletPreferencesProcessor != null) {
					List<Capability> exportCapabilities =
						exportImportPortletPreferencesProcessor.
							getExportCapabilities();

					if (ListUtil.isNotEmpty(exportCapabilities)) {
						for (Capability exportCapability : exportCapabilities) {
							exportCapability.process(
								portletDataContext, jxPortletPreferences);
						}
					}

					exportImportPortletPreferencesProcessor.
						processExportPortletPreferences(
							portletDataContext, jxPortletPreferences);
				}
				else {
					PortletDataHandler portletDataHandler =
						portlet.getPortletDataHandlerInstance();

					jxPortletPreferences =
						portletDataHandler.processExportPortletPreferences(
							portletDataContext, portletId,
							jxPortletPreferences);
				}
			}
			finally {
				portletDataContext.setExportDataRootElement(
					exportDataRootElement);
			}
		}

		Document document = SAXReaderUtil.read(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element rootElement = document.getRootElement();

		rootElement.addAttribute("owner-id", String.valueOf(ownerId));
		rootElement.addAttribute("owner-type", String.valueOf(ownerType));
		rootElement.addAttribute("default-user", String.valueOf(defaultUser));
		rootElement.addAttribute("plid", String.valueOf(plid));
		rootElement.addAttribute("portlet-id", portletId);

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			PortletItem portletItem = _portletItemLocalService.getPortletItem(
				ownerId);

			rootElement.addAttribute(
				"archive-user-uuid", portletItem.getUserUuid());
			rootElement.addAttribute("archive-name", portletItem.getName());
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			User user = _userLocalService.fetchUserById(ownerId);

			if (user == null) {
				return;
			}

			rootElement.addAttribute("user-uuid", user.getUserUuid());
		}

		List<Node> nodes = document.selectNodes(
			"/portlet-preferences/preference[name/text() = " +
				"'last-publish-date']");

		for (Node node : nodes) {
			node.detach();
		}

		String path = ExportImportPathUtil.getPortletPreferencesPath(
			portletDataContext, portletId, ownerId, ownerType, plid);

		portletPreferencesElement.addAttribute("path", path);

		portletDataContext.addZipEntry(
			path, document.formattedString(StringPool.TAB, false, false));
	}

	protected void exportPortletPreferences(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			boolean defaultUser, Layout layout, long plid, String portletId,
			Element parentElement)
		throws Exception {

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences = getPortletPreferences(
				ownerId, ownerType, plid, portletId);
		}
		catch (NoSuchPortletPreferencesException nsppe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsppe, nsppe);
			}

			return;
		}

		LayoutTypePortlet layoutTypePortlet = null;

		if (layout != null) {
			layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();
		}

		if ((layoutTypePortlet == null) ||
			layoutTypePortlet.hasPortletId(portletId)) {

			exportPortletPreference(
				portletDataContext, ownerId, ownerType, defaultUser,
				portletPreferences, portletId, plid, parentElement);
		}
	}

	protected void exportServicePortletPreference(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			PortletPreferences portletPreferences, String serviceName,
			Element parentElement)
		throws Exception {

		String preferencesXML = portletPreferences.getPreferences();

		if (Validator.isNull(preferencesXML)) {
			preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
		}

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

		Document document = SAXReaderUtil.read(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element rootElement = document.getRootElement();

		rootElement.addAttribute("owner-id", String.valueOf(ownerId));
		rootElement.addAttribute("owner-type", String.valueOf(ownerType));
		rootElement.addAttribute("default-user", String.valueOf(false));
		rootElement.addAttribute("service-name", serviceName);

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			PortletItem portletItem = _portletItemLocalService.getPortletItem(
				ownerId);

			rootElement.addAttribute(
				"archive-user-uuid", portletItem.getUserUuid());
			rootElement.addAttribute("archive-name", portletItem.getName());
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			User user = _userLocalService.fetchUserById(ownerId);

			if (user == null) {
				return;
			}

			rootElement.addAttribute("user-uuid", user.getUserUuid());
		}

		List<Node> nodes = document.selectNodes(
			"/portlet-preferences/preference[name/text() = " +
				"'last-publish-date']");

		for (Node node : nodes) {
			node.detach();
		}

		Element serviceElement = parentElement.addElement("service");

		String path = ExportImportPathUtil.getServicePortletPreferencesPath(
			portletDataContext, serviceName, ownerId, ownerType);

		serviceElement.addAttribute("path", path);

		serviceElement.addAttribute("service-name", serviceName);

		portletDataContext.addZipEntry(path, document.formattedString());
	}

	protected void exportServicePortletPreferences(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			String serviceName, Element parentElement)
		throws Exception {

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences = getPortletPreferences(
				ownerId, ownerType, LayoutConstants.DEFAULT_PLID, serviceName);
		}
		catch (NoSuchPortletPreferencesException nsppe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsppe, nsppe);
			}

			return;
		}

		exportServicePortletPreference(
			portletDataContext, ownerId, ownerType, portletPreferences,
			serviceName, parentElement);
	}

	protected String getLockPath(
		PortletDataContext portletDataContext, String className, String key,
		Lock lock) {

		StringBundler sb = new StringBundler(8);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/locks/");
		sb.append(_portal.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(key);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(lock.getLockId());
		sb.append(".xml");

		return sb.toString();
	}

	protected PortletDataContext getPortletDataContext(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourcePlid = MapUtil.getLong(settingsMap, "sourcePlid");
		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		String portletId = MapUtil.getString(settingsMap, "portletId");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration);

		Layout layout = _layoutLocalService.getLayout(sourcePlid);
		ZipWriter zipWriter = ExportImportHelperUtil.getPortletZipWriter(
			portletId);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				layout.getCompanyId(), sourceGroupId, parameterMap,
				dateRange.getStartDate(), dateRange.getEndDate(), zipWriter);

		portletDataContext.setExportImportProcessId(
			String.valueOf(
				exportImportConfiguration.getExportImportConfigurationId()));
		portletDataContext.setOldPlid(sourcePlid);
		portletDataContext.setPlid(sourcePlid);
		portletDataContext.setPortletId(portletId);
		portletDataContext.setType("portlet");

		return portletDataContext;
	}

	protected PortletPreferences getPortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws PortalException {

		PortletPreferences portletPreferences = null;

		if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP)) {

			portletPreferences =
				_portletPreferencesLocalService.getPortletPreferences(
					ownerId, ownerType, LayoutConstants.DEFAULT_PLID,
					portletId);
		}
		else {
			portletPreferences =
				_portletPreferencesLocalService.getPortletPreferences(
					ownerId, ownerType, plid, portletId);
		}

		return portletPreferences;
	}

	protected int getProcessFlag() {
		if (ExportImportThreadLocal.isPortletStagingInProcess()) {
			return PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS;
		}

		return PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS;
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {

		_assetLinkLocalService = assetLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setExportImportLifecycleManager(
		ExportImportLifecycleManager exportImportLifecycleManager) {

		_exportImportLifecycleManager = exportImportLifecycleManager;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletItemLocalService(
		PortletItemLocalService portletItemLocalService) {

		_portletItemLocalService = portletItemLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletExportController.class);

	private AssetEntryLocalService _assetEntryLocalService;
	private AssetLinkLocalService _assetLinkLocalService;
	private final DeletionSystemEventExporter _deletionSystemEventExporter =
		DeletionSystemEventExporter.getInstance();
	private ExportImportLifecycleManager _exportImportLifecycleManager;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private final PermissionExporter _permissionExporter =
		PermissionExporter.getInstance();

	@Reference
	private Portal _portal;

	@Reference
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	private PortletItemLocalService _portletItemLocalService;
	private PortletLocalService _portletLocalService;
	private PortletPreferencesLocalService _portletPreferencesLocalService;
	private UserLocalService _userLocalService;

	private class UpdatePortletLastPublishDateCallable
		implements Callable<Void> {

		public UpdatePortletLastPublishDateCallable(
			DateRange dateRange, Date endDate, long groupId, long plid,
			String portletId) {

			_dateRange = dateRange;
			_endDate = endDate;
			_groupId = groupId;
			_plid = plid;
			_portletId = portletId;
		}

		@Override
		public Void call() throws PortalException {
			Group group = _groupLocalService.getGroup(_groupId);

			Layout layout = _layoutLocalService.fetchLayout(_plid);

			if (group.hasStagingGroup()) {
				group = group.getStagingGroup();

				if (layout != null) {
					layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
						layout.getUuid(), group.getGroupId(),
						layout.isPrivateLayout());
				}
			}

			javax.portlet.PortletPreferences jxPortletPreferences = null;

			if (layout == null) {
				jxPortletPreferences =
					PortletPreferencesFactoryUtil.getStrictPortletSetup(
						group.getCompanyId(), group.getGroupId(), _portletId);
			}
			else {
				jxPortletPreferences =
					PortletPreferencesFactoryUtil.getStrictPortletSetup(
						layout, _portletId);
			}

			ExportImportDateUtil.updateLastPublishDate(
				_portletId, jxPortletPreferences, _dateRange, _endDate);

			return null;
		}

		private final DateRange _dateRange;
		private final Date _endDate;
		private final long _groupId;
		private final long _plid;
		private final String _portletId;

	}

}