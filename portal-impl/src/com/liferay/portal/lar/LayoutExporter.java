/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
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
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

	public static final String SAME_GROUP_FRIENDLY_URL =
		"/[$SAME_GROUP_FRIENDLY_URL$]";

	public static List<Portlet> getDataSiteLevelPortlets(long companyId)
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
				!portletDataHandler.isDataSiteLevel()) {

				itr.remove();
			}
		}

		return portlets;
	}

	public static Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return getExportPortletControlsMap(
			companyId, portletId, parameterMap, "layout-set");
	}

	public static Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		boolean exportPortletConfiguration = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION);
		boolean exportPortletConfigurationAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL);
		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);

		if (_log.isDebugEnabled()) {
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
			_log.debug(
				"Export portlet configuration " + exportPortletConfiguration);
		}

		boolean exportCurPortletData = exportPortletData;

		String rootPortletId =
			ExportImportHelperUtil.getExportableRootPortletId(
				companyId, portletId);

		if (exportPortletDataAll) {
			exportCurPortletData = true;
		}
		else if (rootPortletId != null) {

			// PORTLET_DATA and the PORTLET_DATA for this specific data handler
			// must be true

			exportCurPortletData =
				exportPortletData &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_DATA +
						StringPool.UNDERLINE + rootPortletId);
		}

		boolean exportCurPortletArchivedSetups = exportPortletConfiguration;
		boolean exportCurPortletConfiguration = exportPortletConfiguration;
		boolean exportCurPortletSetup = exportPortletConfiguration;
		boolean exportCurPortletUserPreferences = exportPortletConfiguration;

		if (exportPortletConfigurationAll ||
			(exportPortletConfiguration && type.equals("layout-prototype"))) {

			exportCurPortletConfiguration = true;

			exportCurPortletArchivedSetups =
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL);
			exportCurPortletSetup =
				MapUtil.getBoolean(
					parameterMap, PortletDataHandlerKeys.PORTLET_SETUP_ALL);
			exportCurPortletUserPreferences =
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL);
		}
		else if (rootPortletId != null) {
			exportCurPortletConfiguration =
				exportPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_CONFIGURATION +
						StringPool.UNDERLINE + rootPortletId);

			exportCurPortletArchivedSetups =
				exportCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
						StringPool.UNDERLINE + rootPortletId);
			exportCurPortletSetup =
				exportCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_SETUP +
						StringPool.UNDERLINE + rootPortletId);
			exportCurPortletUserPreferences =
				exportCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES +
						StringPool.UNDERLINE + rootPortletId);
		}

		Map<String, Boolean> exportPortletControlsMap =
			new HashMap<String, Boolean>();

		exportPortletControlsMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
			exportCurPortletArchivedSetups);
		exportPortletControlsMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			exportCurPortletConfiguration);
		exportPortletControlsMap.put(
			PortletDataHandlerKeys.PORTLET_DATA, exportCurPortletData);
		exportPortletControlsMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP, exportCurPortletSetup);
		exportPortletControlsMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			exportCurPortletUserPreferences);

		return exportPortletControlsMap;
	}

	public static List<Portlet> getPortletDataHandlerPortlets(
			List<Layout> layouts)
		throws Exception {

		List<Portlet> portlets = new ArrayList<Portlet>();
		Set<String> rootPortletIds = new HashSet<String>();

		for (Layout layout : layouts) {
			if (!layout.isTypePortlet()) {
				continue;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			for (String portletId : layoutTypePortlet.getPortletIds()) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					layout.getCompanyId(), portletId);

				if ((portlet == null) ||
					rootPortletIds.contains(portlet.getRootPortletId())) {

					continue;
				}

				PortletDataHandler portletDataHandler =
					portlet.getPortletDataHandlerInstance();

				if (portletDataHandler == null) {
					continue;
				}

				PortletDataHandlerControl[] portletDataHandlerControls =
					portletDataHandler.getExportConfigurationControls(
						layout.getCompanyId(), layout.getGroupId(), portlet,
						layout.getPlid(), layout.getPrivateLayout());

				if (ArrayUtil.isNotEmpty(portletDataHandlerControls)) {
					rootPortletIds.add(portlet.getRootPortletId());

					portlets.add(portlet);
				}
			}
		}

		return portlets;
	}

	public static List<Portlet> getPortletDataHandlerPortlets(
			long groupId, boolean privateLayout)
		throws Exception {

		return getPortletDataHandlerPortlets(
			LayoutLocalServiceUtil.getLayouts(groupId, privateLayout));
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		File file = exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		try {
			return FileUtil.getBytes(file);
		}
		finally {
			file.delete();
		}
	}

	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		try {
			ExportImportThreadLocal.setLayoutExportInProcess(true);

			return doExportLayoutsAsFile(
				groupId, privateLayout, layoutIds, parameterMap, startDate,
				endDate);
		}
		finally {
			ExportImportThreadLocal.setLayoutExportInProcess(false);
		}
	}

	protected File doExportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		boolean exportCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
		boolean exportIgnoreLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE);
		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		boolean exportLogo = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LOGO);
		boolean exportLayoutSetSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
		}

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setSignedIn(false);
			serviceContext.setUserId(defaultUserId);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		serviceContext.setAttribute("exporting", Boolean.TRUE);

		long layoutSetBranchId = MapUtil.getLong(
			parameterMap, "layoutSetBranchId");

		serviceContext.setAttribute("layoutSetBranchId", layoutSetBranchId);

		if (exportIgnoreLastPublishDate) {
			endDate = null;
			startDate = null;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		LayoutCache layoutCache = new LayoutCache();

		ZipWriter zipWriter = ExportImportHelperUtil.getLayoutSetZipWriter(
			groupId);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				companyId, groupId, parameterMap, startDate, endDate,
				zipWriter);

		portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(portletDataContext));
		portletDataContext.setPrivateLayout(privateLayout);

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
		headerElement.addAttribute("group-id", String.valueOf(groupId));
		headerElement.addAttribute(
			"user-personal-site-group-id",
			String.valueOf(portletDataContext.getUserPersonalSiteGroupId()));
		headerElement.addAttribute(
			"private-layout", String.valueOf(privateLayout));

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

		Map<String, Object[]> portletIds =
			new LinkedHashMap<String, Object[]>();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout);

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
					portletId, LayoutConstants.DEFAULT_PLID, groupId,
					StringPool.BLANK, StringPool.BLANK
				});

			if (!portlet.isScopeable()) {
				continue;
			}

			// Scoped data

			for (Layout layout : layouts) {
				if ((!ArrayUtil.contains(layoutIds, layout.getLayoutId()) &&
					 ArrayUtil.isNotEmpty(layoutIds)) ||
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

		if (!group.isStaged() && Validator.isNotNull(layoutSetPrototypeUuid)) {
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

			Set<String> portletIdsSet = portletIds.keySet();

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"layout", portletIdsSet.toArray(
					new String[portletIdsSet.size()]),
				manifestSummary);

			manifestSummary.resetCounters();
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		Element portletsElement = rootElement.addElement("portlets");

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

				layout.setGroupId(groupId);
				layout.setCompanyId(companyId);
			}

			portletDataContext.setPlid(plid);
			portletDataContext.setOldPlid(plid);
			portletDataContext.setScopeGroupId(scopeGroupId);
			portletDataContext.setScopeType(scopeType);
			portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

			Map<String, Boolean> exportPortletControlsMap =
				getExportPortletControlsMap(
					companyId, portletId, parameterMap, type);

			_portletExporter.exportPortlet(
				portletDataContext, layoutCache, portletId, layout,
				portletsElement, exportPermissions,
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		exportAssetCategories(
			portletDataContext, exportPortletDataAll, exportCategories,
			group.isCompany());

		_portletExporter.exportAssetLinks(portletDataContext);
		_portletExporter.exportAssetTags(portletDataContext);
		_portletExporter.exportComments(portletDataContext);
		_portletExporter.exportExpandoTables(portletDataContext);
		_portletExporter.exportLocks(portletDataContext);

		_deletionSystemEventExporter.exportDeletionSystemEvents(
			portletDataContext);

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(
				portletDataContext);
		}

		_portletExporter.exportRatingsEntries(portletDataContext, rootElement);

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
					portletDataContext.getDateRange(), groupId, privateLayout));
		}

		portletDataContext.addZipEntry(
			"/manifest.xml", document.formattedString());

		return zipWriter.getFile();
	}

	protected void exportAssetCategories(
			PortletDataContext portletDataContext, boolean exportPortletDataAll,
			boolean exportCategories, boolean companyGroup)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("categories-hierarchy");

		if (exportPortletDataAll || exportCategories || companyGroup) {
			if (_log.isDebugEnabled()) {
				_log.debug("Export categories");
			}

			Element assetVocabulariesElement = rootElement.addElement(
				"vocabularies");

			List<AssetVocabulary> assetVocabularies =
				AssetVocabularyLocalServiceUtil.getGroupVocabularies(
					portletDataContext.getGroupId());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				_portletExporter.exportAssetVocabulary(
					portletDataContext, assetVocabulariesElement,
					assetVocabulary);
			}

			Element categoriesElement = rootElement.addElement("categories");

			List<AssetCategory> assetCategories =
				AssetCategoryUtil.findByGroupId(
					portletDataContext.getGroupId());

			for (AssetCategory assetCategory : assetCategories) {
				_portletExporter.exportAssetCategory(
					portletDataContext, assetVocabulariesElement,
					categoriesElement, assetCategory);
			}
		}

		_portletExporter.exportAssetCategories(portletDataContext, rootElement);

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/categories-hierarchy.xml",
			document.formattedString());
	}

	protected void exportLayout(
			PortletDataContext portletDataContext, long[] layoutIds,
			Map<String, Object[]> portletIds, Layout layout)
		throws Exception {

		if (!ArrayUtil.contains(layoutIds, layout.getLayoutId()) &&
			ArrayUtil.isNotEmpty(layoutIds)) {

			Element layoutElement = portletDataContext.getExportDataElement(
				layout);

			layoutElement.addAttribute("action", Constants.SKIP);

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

			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);

			String scopeType = GetterUtil.getString(
				jxPortletPreferences.getValue("lfrScopeType", null));
			String scopeLayoutUuid = GetterUtil.getString(
				jxPortletPreferences.getValue("lfrScopeLayoutUuid", null));

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

	private static Log _log = LogFactoryUtil.getLog(LayoutExporter.class);

	private DeletionSystemEventExporter _deletionSystemEventExporter =
		new DeletionSystemEventExporter();
	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();
	private ThemeExporter _themeExporter = new ThemeExporter();

	private class UpdateLayoutSetLastPublishDateCallable
		implements Callable<Void> {

		public UpdateLayoutSetLastPublishDateCallable(
			DateRange dateRange, long groupId, boolean privateLayout) {

			_dateRange = dateRange;
			_groupId = groupId;
			_privateLayout = privateLayout;
		}

		@Override
		public Void call() throws Exception {
			Group group = GroupLocalServiceUtil.getGroup(_groupId);

			if (group.isStagedRemotely()) {
				return null;
			}

			Date endDate = null;

			if (_dateRange != null) {
				endDate = _dateRange.getEndDate();
			}

			if (ExportImportThreadLocal.isStagingInProcess() &&
				group.hasStagingGroup()) {

				Group stagingGroup = group.getStagingGroup();

				StagingUtil.updateLastPublishDate(
					stagingGroup.getGroupId(), _privateLayout, _dateRange,
					endDate);
			}
			else {
				StagingUtil.updateLastPublishDate(
					_groupId, _privateLayout, _dateRange, endDate);
			}

			return null;
		}

		private final DateRange _dateRange;
		private final long _groupId;
		private final boolean _privateLayout;

	}

}