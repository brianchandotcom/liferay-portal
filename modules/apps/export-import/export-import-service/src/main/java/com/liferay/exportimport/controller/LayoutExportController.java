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

import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_STARTED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_SUCCEEDED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_EXPORT_IN_PROCESS;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;

import com.liferay.exportimport.lar.DeletionSystemEventExporter;
import com.liferay.exportimport.lar.PermissionExporter;
import com.liferay.exportimport.lar.ThemeExporter;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutStagingHandler;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutPrototypeLocalService;
import com.liferay.portal.service.LayoutRevisionLocalService;
import com.liferay.portal.service.LayoutSetBranchLocalService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portlet.exportimport.controller.ExportController;
import com.liferay.portlet.exportimport.controller.ExportImportController;
import com.liferay.portlet.exportimport.lar.ExportImportDateUtil;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.portlet.exportimport.lar.ExportImportThreadLocal;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataContextFactoryUtil;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleManager;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.staging.LayoutStagingUtil;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Karthik Sudarshan
 * @author Zsigmond Rab
 * @author Douglas Wong
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.model.Layout"},
	service = {ExportImportController.class, LayoutExportController.class}
)
public class LayoutExportController implements ExportController {

	@Override
	public File export(ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		PortletDataContext portletDataContext = null;

		try {
			ExportImportThreadLocal.setLayoutExportInProcess(true);

			Map<String, Serializable> settingsMap =
				exportImportConfiguration.getSettingsMap();

			long[] layoutIds = GetterUtil.getLongValues(
				settingsMap.get("layoutIds"));

			portletDataContext = getPortletDataContext(
				exportImportConfiguration);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_LAYOUT_EXPORT_STARTED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			File file = doExport(portletDataContext, layoutIds);

			ExportImportThreadLocal.setLayoutExportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_LAYOUT_EXPORT_SUCCEEDED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			return file;
		}
		catch (Throwable t) {
			ExportImportThreadLocal.setLayoutExportInProcess(false);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_LAYOUT_EXPORT_FAILED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
	}

	@Activate
	protected void activate() {
		XStreamAliasRegistryUtil.register(LayoutImpl.class, "Layout");
	}

	protected File doExport(
			PortletDataContext portletDataContext, long[] layoutIds)
		throws Exception {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		boolean exportIgnoreLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE);
		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportLogo = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LOGO);
		boolean exportLayoutSetSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
		}

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());

		long companyId = layoutSet.getCompanyId();
		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setCompanyId(companyId);
		serviceContext.setSignedIn(false);
		serviceContext.setUserId(defaultUserId);

		serviceContext.setAttribute("exporting", Boolean.TRUE);

		long layoutSetBranchId = MapUtil.getLong(
			parameterMap, "layoutSetBranchId");

		serviceContext.setAttribute("layoutSetBranchId", layoutSetBranchId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		if (exportIgnoreLastPublishDate) {
			portletDataContext.setEndDate(null);
			portletDataContext.setStartDate(null);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		Element headerElement = rootElement.addElement("header");

		headerElement.addAttribute(
			"available-locales",
			StringUtil.merge(
				LanguageUtil.getAvailableLocales(
					portletDataContext.getScopeGroupId())));
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

		headerElement.addAttribute(
			"company-id", String.valueOf(portletDataContext.getCompanyId()));
		headerElement.addAttribute(
			"company-group-id",
			String.valueOf(portletDataContext.getCompanyGroupId()));
		headerElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getGroupId()));
		headerElement.addAttribute(
			"user-personal-site-group-id",
			String.valueOf(portletDataContext.getUserPersonalSiteGroupId()));
		headerElement.addAttribute(
			"private-layout",
			String.valueOf(portletDataContext.isPrivateLayout()));

		Group group = layoutSet.getGroup();

		String type = "layout-set";

		if (group.isLayoutPrototype()) {
			type = "layout-prototype";

			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.getLayoutPrototype(
					group.getClassPK());

			headerElement.addAttribute("type-uuid", layoutPrototype.getUuid());
		}
		else if (group.isLayoutSetPrototype()) {
			type ="layout-set-prototype";

			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.getLayoutSetPrototype(
					group.getClassPK());

			headerElement.addAttribute(
				"type-uuid", layoutSetPrototype.getUuid());
		}

		headerElement.addAttribute("type", type);

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				layoutSetBranchId);

		if (exportLogo) {
			Image image = null;

			if (layoutSetBranch != null) {
				image = _imageLocalService.getImage(
					layoutSetBranch.getLogoId());
			}
			else {
				image = _imageLocalService.getImage(layoutSet.getLogoId());
			}

			if ((image != null) && (image.getTextObj() != null)) {
				String logoPath = ExportImportPathUtil.getRootPath(
					portletDataContext);

				logoPath += "/logo";

				headerElement.addAttribute("logo-path", logoPath);

				portletDataContext.addZipEntry(logoPath, image.getTextObj());
			}
		}

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		if (layoutSetBranch != null) {
			_themeExporter.exportTheme(portletDataContext, layoutSetBranch);
		}
		else {
			_themeExporter.exportTheme(portletDataContext, layoutSet);
		}

		if (exportLayoutSetSettings) {
			Element settingsElement = headerElement.addElement("settings");

			if (layoutSetBranch != null) {
				settingsElement.addCDATA(layoutSetBranch.getSettings());
			}
			else {
				settingsElement.addCDATA(layoutSet.getSettings());
			}
		}

		Map<String, Object[]> portletIds = new LinkedHashMap<>();

		List<Layout> layouts = _layoutLocalService.getLayouts(
			portletDataContext.getGroupId(),
			portletDataContext.isPrivateLayout());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		// Collect data portlets

		for (Portlet portlet :
				ExportImportHelperUtil.getDataSiteLevelPortlets(companyId)) {

			String portletId = portlet.getRootPortletId();

			if (ExportImportThreadLocal.isStagingInProcess() &&
				!group.isStagedPortlet(portletId)) {

				continue;
			}

			// Calculate the amount of exported data

			if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
				PortletDataHandler portletDataHandler =
					portlet.getPortletDataHandlerInstance();

				portletDataHandler.prepareManifestSummary(portletDataContext);
			}

			// Add portlet ID to exportable portlets list

			portletIds.put(
				PortletPermissionUtil.getPrimaryKey(0, portletId),
				new Object[] {
					portletId, LayoutConstants.DEFAULT_PLID,
					portletDataContext.getGroupId(), StringPool.BLANK,
					StringPool.BLANK
				});

			if (!portlet.isScopeable()) {
				continue;
			}

			// Scoped data

			for (Layout layout : layouts) {
				if (!ArrayUtil.contains(layoutIds, layout.getLayoutId()) ||
					!layout.isTypePortlet() || !layout.hasScopeGroup()) {

					continue;
				}

				Group scopeGroup = layout.getScopeGroup();

				portletIds.put(
					PortletPermissionUtil.getPrimaryKey(
						layout.getPlid(), portlet.getPortletId()),
					new Object[] {
						portlet.getPortletId(), layout.getPlid(),
						scopeGroup.getGroupId(), StringPool.BLANK,
						layout.getUuid()
					});
			}
		}

		// Collect layout portlets

		for (Layout layout : layouts) {
			getLayoutPortlets(
				portletDataContext, layoutIds, portletIds, layout);
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"layout", ArrayUtil.toStringArray(portletIds.keySet()),
				manifestSummary);

			manifestSummary.resetCounters();
		}

		// Export actual data

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(Layout.class));

		Element layoutsElement = portletDataContext.getExportDataGroupElement(
			Layout.class);

		String layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);

			layoutsElement.addAttribute(
				"layout-set-prototype-uuid", layoutSetPrototypeUuid);

			layoutsElement.addAttribute(
				"layout-set-prototype-name",
				layoutSetPrototype.getName(LocaleUtil.getDefault()));
		}

		for (Layout layout : layouts) {
			exportLayout(portletDataContext, layoutIds, layout);
		}

		Element portletsElement = rootElement.addElement("portlets");

		Element servicesElement = rootElement.addElement("services");

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		for (Map.Entry<String, Object[]> portletIdsEntry :
				portletIds.entrySet()) {

			Object[] portletObjects = portletIdsEntry.getValue();

			String portletId = null;
			long plid = LayoutConstants.DEFAULT_PLID;
			long scopeGroupId = 0;
			String scopeType = StringPool.BLANK;
			String scopeLayoutUuid = null;

			if (portletObjects.length == 4) {
				portletId = (String)portletIdsEntry.getValue()[0];
				plid = (Long)portletIdsEntry.getValue()[1];
				scopeGroupId = (Long)portletIdsEntry.getValue()[2];
				scopeLayoutUuid = (String)portletIdsEntry.getValue()[3];
			}
			else {
				portletId = (String)portletIdsEntry.getValue()[0];
				plid = (Long)portletIdsEntry.getValue()[1];
				scopeGroupId = (Long)portletIdsEntry.getValue()[2];
				scopeType = (String)portletIdsEntry.getValue()[3];
				scopeLayoutUuid = (String)portletIdsEntry.getValue()[4];
			}

			Layout layout = _layoutLocalService.fetchLayout(plid);

			if (layout == null) {
				layout = new LayoutImpl();

				layout.setCompanyId(companyId);
				layout.setGroupId(portletDataContext.getGroupId());
			}

			portletDataContext.setPlid(plid);
			portletDataContext.setOldPlid(plid);
			portletDataContext.setPortletId(portletId);
			portletDataContext.setScopeGroupId(scopeGroupId);
			portletDataContext.setScopeType(scopeType);
			portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

			Map<String, Boolean> exportPortletControlsMap =
				ExportImportHelperUtil.getExportPortletControlsMap(
					companyId, portletId, parameterMap, type);

			try {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_EXPORT_STARTED, getProcessFlag(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));

				_portletExportController.exportPortlet(
					portletDataContext, layout, portletsElement,
					exportPermissions,
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
					EVENT_PORTLET_EXPORT_SUCCEEDED, getProcessFlag(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));
			}
			catch (Throwable t) {
				_exportImportLifecycleManager.fireExportImportLifecycleEvent(
					EVENT_PORTLET_EXPORT_FAILED, getProcessFlag(),
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext),
					t);

				throw t;
			}
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		_portletExportController.exportAssetLinks(portletDataContext);
		_portletExportController.exportExpandoTables(portletDataContext);
		_portletExportController.exportLocks(portletDataContext);

		_deletionSystemEventExporter.exportDeletionSystemEvents(
			portletDataContext);

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(
				portletDataContext);
		}

		ExportImportHelperUtil.writeManifestSummary(
			document, portletDataContext.getManifestSummary());

		if (_log.isInfoEnabled()) {
			_log.info("Exporting layouts takes " + stopWatch.getTime() + " ms");
		}

		boolean updateLastPublishDate = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (ExportImportThreadLocal.isStagingInProcess() &&
			updateLastPublishDate) {

			ExportImportProcessCallbackRegistryUtil.registerCallback(
				new UpdateLayoutSetLastPublishDateCallable(
					portletDataContext.getDateRange(),
					portletDataContext.getGroupId(),
					portletDataContext.isPrivateLayout()));
		}

		portletDataContext.addZipEntry(
			"/manifest.xml", document.formattedString());

		ZipWriter zipWriter = portletDataContext.getZipWriter();

		return zipWriter.getFile();
	}

	protected void exportLayout(
			PortletDataContext portletDataContext, long[] layoutIds,
			Layout layout)
		throws Exception {

		if (!ArrayUtil.contains(layoutIds, layout.getLayoutId())) {
			Element layoutElement = portletDataContext.getExportDataElement(
				layout);

			layoutElement.addAttribute(Constants.ACTION, Constants.SKIP);

			return;
		}

		if (!prepareLayoutStagingHandler(portletDataContext, layout)) {
			return;
		}

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);
	}

	protected void getLayoutPortlets(
			PortletDataContext portletDataContext, long[] layoutIds,
			Map<String, Object[]> portletIds, Layout layout)
		throws Exception {

		if (!ArrayUtil.contains(layoutIds, layout.getLayoutId())) {
			return;
		}

		if (!prepareLayoutStagingHandler(portletDataContext, layout) ||
			!layout.isSupportsEmbeddedPortlets()) {

			// Only portlet type layouts support page scoping

			return;
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		// The getAllPortlets method returns all effective nonsystem portlets
		// for any layout type, including embedded portlets, or in the case of
		// panel type layout, selected portlets

		for (Portlet portlet : layoutTypePortlet.getAllPortlets(false)) {
			String portletId = portlet.getPortletId();

			Settings portletInstanceSettings = SettingsFactoryUtil.getSettings(
				new PortletInstanceSettingsLocator(layout, portletId));

			String scopeType = portletInstanceSettings.getValue(
				"lfrScopeType", null);
			String scopeLayoutUuid = portletInstanceSettings.getValue(
				"lfrScopeLayoutUuid", null);

			long scopeGroupId = portletDataContext.getScopeGroupId();

			if (Validator.isNotNull(scopeType)) {
				Group scopeGroup = null;

				if (scopeType.equals("company")) {
					scopeGroup = _groupLocalService.getCompanyGroup(
						layout.getCompanyId());
				}
				else if (scopeType.equals("layout")) {
					Layout scopeLayout = null;

					scopeLayout =
						_layoutLocalService.fetchLayoutByUuidAndGroupId(
							scopeLayoutUuid, portletDataContext.getGroupId(),
							portletDataContext.isPrivateLayout());

					if (scopeLayout == null) {
						continue;
					}

					scopeGroup = scopeLayout.getScopeGroup();
				}
				else {
					throw new IllegalArgumentException(
						"Scope type " + scopeType + " is invalid");
				}

				if (scopeGroup != null) {
					scopeGroupId = scopeGroup.getGroupId();
				}
			}

			String key = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			portletIds.put(
				key,
				new Object[] {
					portletId, layout.getPlid(), scopeGroupId, scopeType,
					scopeLayoutUuid
				}
			);
		}
	}

	protected PortletDataContext getPortletDataContext(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");
		boolean privateLayout = MapUtil.getBoolean(
			settingsMap, "privateLayout");
		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");
		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration);

		Group group = _groupLocalService.getGroup(sourceGroupId);
		ZipWriter zipWriter = ExportImportHelperUtil.getLayoutSetZipWriter(
			sourceGroupId);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				group.getCompanyId(), sourceGroupId, parameterMap,
				dateRange.getStartDate(), dateRange.getEndDate(), zipWriter);

		portletDataContext.setPrivateLayout(privateLayout);

		return portletDataContext;
	}

	protected int getProcessFlag() {
		if (ExportImportThreadLocal.isLayoutStagingInProcess()) {
			return PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;
		}

		return PROCESS_FLAG_LAYOUT_EXPORT_IN_PROCESS;
	}

	protected boolean prepareLayoutStagingHandler(
		PortletDataContext portletDataContext, Layout layout) {

		boolean exportLAR = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), "exportLAR");

		if (exportLAR || !LayoutStagingUtil.isBranchingLayout(layout)) {
			return true;
		}

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		if (layoutSetBranchId <= 0) {
			return false;
		}

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.fetchLayoutRevision(
				layoutSetBranchId, true, layout.getPlid());

		if (layoutRevision == null) {
			return false;
		}

		LayoutStagingHandler layoutStagingHandler =
			LayoutStagingUtil.getLayoutStagingHandler(layout);

		layoutStagingHandler.setLayoutRevision(layoutRevision);

		return true;
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
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetBranchLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletExportController(
		PortletExportController portletExportController) {

		_portletExportController = portletExportController;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutExportController.class);

	private final DeletionSystemEventExporter _deletionSystemEventExporter =
		DeletionSystemEventExporter.getInstance();
	private volatile ExportImportLifecycleManager _exportImportLifecycleManager;
	private volatile GroupLocalService _groupLocalService;
	private volatile ImageLocalService _imageLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile LayoutPrototypeLocalService _layoutPrototypeLocalService;
	private volatile LayoutRevisionLocalService _layoutRevisionLocalService;
	private volatile LayoutSetBranchLocalService _layoutSetBranchLocalService;
	private volatile LayoutSetLocalService _layoutSetLocalService;
	private volatile LayoutSetPrototypeLocalService
		_layoutSetPrototypeLocalService;
	private final PermissionExporter _permissionExporter =
		PermissionExporter.getInstance();
	private volatile PortletExportController _portletExportController;
	private final ThemeExporter _themeExporter = ThemeExporter.getInstance();
	private volatile UserLocalService _userLocalService;

	private class UpdateLayoutSetLastPublishDateCallable
		implements Callable<Void> {

		public UpdateLayoutSetLastPublishDateCallable(
			DateRange dateRange, long groupId, boolean privateLayout) {

			_dateRange = dateRange;
			_groupId = groupId;
			_privateLayout = privateLayout;
		}

		@Override
		public Void call() throws PortalException {
			Group group = _groupLocalService.getGroup(_groupId);

			Date endDate = null;

			if (_dateRange != null) {
				endDate = _dateRange.getEndDate();
			}

			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				ExportImportDateUtil.updateLastPublishDate(
					stagingGroup.getGroupId(), _privateLayout, _dateRange,
					endDate);
			}
			else {
				ExportImportDateUtil.updateLastPublishDate(
					_groupId, _privateLayout, _dateRange, endDate);
			}

			return null;
		}

		private final DateRange _dateRange;
		private final long _groupId;
		private final boolean _privateLayout;

	}

}