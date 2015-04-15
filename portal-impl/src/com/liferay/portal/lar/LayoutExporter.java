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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleManager;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
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
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
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
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;

import java.io.File;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.time.StopWatch;

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
public class LayoutExporter {

	public static List<Portlet> getDataSiteLevelPortlets(long companyId)
		throws Exception {

		return getDataSiteLevelPortlets(companyId, false);
	}

	public static List<Portlet> getDataSiteLevelPortlets(
			long companyId, boolean excludeDataAlwaysStaged)
		throws Exception {

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(companyId);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			if (!portlet.isActive()) {
				itr.remove();

				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler == null) ||
				!portletDataHandler.isDataSiteLevel() ||
				(excludeDataAlwaysStaged &&
				 portletDataHandler.isDataAlwaysStaged())) {

				itr.remove();
			}
		}

		return portlets;
	}

	public static LayoutExporter getInstance() {
		return _instance;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static List<Portlet> getPortletDataHandlerPortlets(
			long groupId, List<Layout> layouts)
		throws Exception {

		return Collections.emptyList();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		File file = exportLayoutsAsFile(
			groupId, layoutIds, parameterMap, startDate, endDate);

		try {
			return FileUtil.getBytes(file);
		}
		finally {
			file.delete();
		}
	}

	public File exportLayoutsAsFile(
			long groupId, long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		PortletDataContext portletDataContext = null;

		try {
			ExportImportThreadLocal.setLayoutExportInProcess(true);

			portletDataContext = getPortletDataContext(
				groupId, parameterMap, startDate, endDate);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_STARTED,
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			File file = doExportLayoutsAsFile(portletDataContext, layoutIds);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_SUCCEEDED,
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			return file;
		}
		catch (Throwable t) {
			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED,
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
		finally {
			ExportImportThreadLocal.setLayoutExportInProcess(false);
		}
	}

	protected File doExportLayoutsAsFile(
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

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			portletDataContext.getGroupId());

		long companyId = layoutSet.getCompanyId();
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

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

		Group group = layoutSet.getGroup();

		String type = "layout-set";

		if (group.isLayoutPrototype()) {
			type = "layout-prototype";

			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.getLayoutPrototype(
					group.getClassPK());

			headerElement.addAttribute("type-uuid", layoutPrototype.getUuid());
		}
		else if (group.isLayoutSetPrototype()) {
			type ="layout-set-prototype";

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					group.getClassPK());

			headerElement.addAttribute(
				"type-uuid", layoutSetPrototype.getUuid());
		}

		headerElement.addAttribute("type", type);

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
				layoutSetBranchId);

		if (exportLogo) {
			Image image = null;

			if (layoutSetBranch != null) {
				image = ImageLocalServiceUtil.getImage(
					layoutSetBranch.getLogoId());
			}
			else {
				image = ImageLocalServiceUtil.getImage(layoutSet.getLogoId());
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

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		// Collect data portlets

		for (Portlet portlet : getDataSiteLevelPortlets(companyId)) {
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

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			new StagedModelType(Layout.class));

		Element layoutsElement = portletDataContext.getExportDataGroupElement(
			Layout.class);

		String layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);

			layoutsElement.addAttribute(
				"layout-set-prototype-uuid", layoutSetPrototypeUuid);

			layoutsElement.addAttribute(
				"layout-set-prototype-name",
				layoutSetPrototype.getName(LocaleUtil.getDefault()));
		}

		for (Layout layout : layouts) {
			exportLayout(portletDataContext, layoutIds, portletIds, layout);
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"layout", ArrayUtil.toStringArray(portletIds.keySet()),
				manifestSummary);

			manifestSummary.resetCounters();
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

			Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

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
				ExportImportLifecycleManager.fireExportImportLifecycleEvent(
					ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED,
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));

				_portletExporter.exportPortlet(
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
				_portletExporter.exportService(
					portletDataContext, servicesElement,
					exportPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_SETUP));

				ExportImportLifecycleManager.fireExportImportLifecycleEvent(
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_SUCCEEDED,
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext));
			}
			catch (Throwable t) {
				ExportImportLifecycleManager.fireExportImportLifecycleEvent(
					ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED,
					PortletDataContextFactoryUtil.clonePortletDataContext(
						portletDataContext),
					t);

				throw t;
			}
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		_portletExporter.exportAssetLinks(portletDataContext);
		_portletExporter.exportExpandoTables(portletDataContext);
		_portletExporter.exportLocks(portletDataContext);

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

		if (updateLastPublishDate) {
			TransactionCommitCallbackRegistryUtil.registerCallback(
				new UpdateLayoutSetLastPublishDateCallable(
					portletDataContext.getDateRange(),
					portletDataContext.getGroupId()));
		}

		portletDataContext.addZipEntry(
			"/manifest.xml", document.formattedString());

		ZipWriter zipWriter = portletDataContext.getZipWriter();

		return zipWriter.getFile();
	}

	protected void exportLayout(
			PortletDataContext portletDataContext, long[] layoutIds,
			Map<String, Object[]> portletIds, Layout layout)
		throws Exception {

		if (!ArrayUtil.contains(layoutIds, layout.getLayoutId())) {
			Element layoutElement = portletDataContext.getExportDataElement(
				layout);

			layoutElement.addAttribute(Constants.ACTION, Constants.SKIP);

			return;
		}

		boolean exportLAR = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), "exportLAR");

		if (!exportLAR && LayoutStagingUtil.isBranchingLayout(layout)) {
			long layoutSetBranchId = MapUtil.getLong(
				portletDataContext.getParameterMap(), "layoutSetBranchId");

			if (layoutSetBranchId <= 0) {
				return;
			}

			LayoutRevision layoutRevision =
				LayoutRevisionLocalServiceUtil.fetchLayoutRevision(
					layoutSetBranchId, true, layout.getPlid());

			if (layoutRevision == null) {
				return;
			}

			LayoutStagingHandler layoutStagingHandler =
				LayoutStagingUtil.getLayoutStagingHandler(layout);

			layoutStagingHandler.setLayoutRevision(layoutRevision);
		}

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

		if (!layout.isSupportsEmbeddedPortlets()) {

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
					scopeGroup = GroupLocalServiceUtil.getCompanyGroup(
						layout.getCompanyId());
				}
				else if (scopeType.equals("layout")) {
					Layout scopeLayout = null;

					scopeLayout =
						LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
							scopeLayoutUuid, portletDataContext.getGroupId());

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
			long groupId, Map<String, String[]> parameterMap, Date startDate,
			Date endDate)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return PortletDataContextFactoryUtil.createExportPortletDataContext(
			group.getCompanyId(), groupId, parameterMap, startDate, endDate,
			ZipWriterFactoryUtil.getZipWriter());
	}

	private LayoutExporter() {
		XStreamAliasRegistryUtil.register(LayoutImpl.class, "Layout");
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutExporter.class);

	private static final LayoutExporter _instance = new LayoutExporter();

	private final DeletionSystemEventExporter _deletionSystemEventExporter =
		DeletionSystemEventExporter.getInstance();
	private final PermissionExporter _permissionExporter =
		PermissionExporter.getInstance();
	private final PortletExporter _portletExporter =
		PortletExporter.getInstance();
	private final ThemeExporter _themeExporter = ThemeExporter.getInstance();

	private class UpdateLayoutSetLastPublishDateCallable
		implements Callable<Void> {

		public UpdateLayoutSetLastPublishDateCallable(
			DateRange dateRange, long groupId) {

			_dateRange = dateRange;
			_groupId = groupId;
		}

		@Override
		public Void call() throws PortalException {
			Group group = GroupLocalServiceUtil.getGroup(_groupId);

			Date endDate = null;

			if (_dateRange != null) {
				endDate = _dateRange.getEndDate();
			}

			if (ExportImportThreadLocal.isStagingInProcess() &&
				group.hasStagingGroup()) {

				Group stagingGroup = group.getStagingGroup();

				ExportImportDateUtil.updateLastPublishDate(
					stagingGroup.getGroupId(), _dateRange, endDate);
			}
			else {
				ExportImportDateUtil.updateLastPublishDate(
					_groupId, _dateRange, endDate);
			}

			return null;
		}

		private final DateRange _dateRange;
		private final long _groupId;

	}

}