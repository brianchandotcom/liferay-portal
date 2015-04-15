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

import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportDateUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.util.xml.DocUtil;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.apache.commons.lang.time.StopWatch;

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
public class PortletExporter {

	public static PortletExporter getInstance() {
		return _instance;
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

		Group group = layout.getGroup();

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

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(portletDataContext, portletId));
		sb.append(StringPool.SLASH);

		if (portlet.isPreferencesUniquePerLayout()) {
			sb.append(layout.getPlid());
		}
		else {
			sb.append(portletDataContext.getScopeGroupId());
		}

		sb.append("/portlet-data.xml");

		String path = sb.toString();

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
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new SystemException(e);
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

		if (updateLastPublishDate) {
			DateRange adjustedDateRange = new DateRange(
				portletLastPublishDate, portletDataContext.getEndDate());

			TransactionCommitCallbackRegistryUtil.registerCallback(
				new UpdatePortletLastPublishDateCallable(
					adjustedDateRange, portletDataContext.getEndDate(),
					portletDataContext.getGroupId(), layout.getPlid(),
					portletId));
		}
	}

	public File exportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		PortletDataContext portletDataContext = null;

		try {
			ExportImportThreadLocal.setPortletExportInProcess(true);

			portletDataContext = getPortletDataContext(
				plid, groupId, portletId, parameterMap, startDate, endDate);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED,
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			File file = doExportPortletInfoAsFile(portletDataContext);

			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED,
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			return file;
		}
		catch (Throwable t) {
			ExportImportLifecycleManager.fireExportImportLifecycleEvent(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED,
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
		finally {
			ExportImportThreadLocal.setPortletExportInProcess(false);
		}
	}

	protected File doExportPortletInfoAsFile(
			PortletDataContext portletDataContext)
		throws Exception {

		boolean exportPermissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Layout layout = LayoutLocalServiceUtil.getLayout(
			portletDataContext.getPlid());

		if (!layout.isTypeControlPanel() && !layout.isTypePanel() &&
			!layout.isTypePortlet()) {

			throw new LayoutImportException(
				"Layout type " + layout.getType() + " is not valid");
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(layout.getCompanyId());
			serviceContext.setSignedIn(false);

			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
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
				scopeGroup = GroupLocalServiceUtil.getCompanyGroup(
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
					PortalUtil.getSiteGroupId(
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

		headerElement.addAttribute("type", "portlet");
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
			portletDataContext, layout, rootElement, exportPermissions,
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
			throw new SystemException(ioe);
		}

		ZipWriter zipWriter = portletDataContext.getZipWriter();

		return zipWriter.getFile();
	}

	protected void exportAssetLinks(PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("links");

		Map<String, List<AssetLink>> assetLinksMap =
			portletDataContext.getAssetLinksMap();

		for (Entry<String, List<AssetLink>> entry : assetLinksMap.entrySet()) {
			String[] assetLinkNameParts = StringUtil.split(
				entry.getKey(), CharPool.POUND);

			List<AssetLink> assetLinks = entry.getValue();

			String sourceAssetEntryUuid = assetLinkNameParts[0];

			Element assetElement = rootElement.addElement("asset-link-group");

			assetElement.addAttribute("source-uuid", sourceAssetEntryUuid);

			for (AssetLink assetLink : assetLinks) {
				String path = getAssetLinkPath(
					portletDataContext, assetLink.getLinkId());

				if (portletDataContext.hasPrimaryKey(String.class, path)) {
					return;
				}

				Element assetLinkElement = assetElement.addElement(
					"asset-link");

				assetLinkElement.addAttribute("path", path);

				AssetEntry targetAssetEntry =
					AssetEntryLocalServiceUtil.fetchAssetEntry(
						assetLink.getEntryId2());

				assetLinkElement.addAttribute(
					"target-uuid", targetAssetEntry.getClassUuid());

				portletDataContext.addZipEntry(path, assetLink);
			}
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

	protected void exportPortlet(
			PortletDataContext portletDataContext, Layout layout,
			Element parentElement, boolean exportPermissions,
			boolean exportPortletArchivedSetups, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletUserPreferences)
		throws Exception {

		long plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		long layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		if (layout != null) {
			plid = layout.getPlid();
			layoutId = layout.getLayoutId();
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
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

		// Data

		if (exportPortletData) {
			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.getStrictPortletSetup(
					layout, portletDataContext.getPortletId());

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

			// Layout

			exportPortletPreferences(
				portletDataContext, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false, layout, plid,
				portletDataContext.getPortletId(), portletElement);
		}

		// Portlet user preferences

		if (exportPortletUserPreferences) {
			List<PortletPreferences> portletPreferencesList =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
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
					PortletPreferencesLocalServiceUtil.getPortletPreferences(
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
			}
		}

		// Archived setups

		if (exportPortletArchivedSetups) {
			List<PortletItem> portletItems =
				PortletItemLocalServiceUtil.getPortletItems(
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
			configurationOptionsSB.setIndex(configurationOptionsSB.index() -1);
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

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
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

				PortletDataHandler portletDataHandler =
					portlet.getPortletDataHandlerInstance();

				jxPortletPreferences =
					portletDataHandler.processExportPortletPreferences(
						portletDataContext, portletId, jxPortletPreferences);
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
			PortletItem portletItem =
				PortletItemLocalServiceUtil.getPortletItem(ownerId);

			rootElement.addAttribute(
				"archive-user-uuid", portletItem.getUserUuid());
			rootElement.addAttribute("archive-name", portletItem.getName());
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			User user = UserLocalServiceUtil.fetchUserById(ownerId);

			if (user == null) {
				return;
			}

			rootElement.addAttribute("user-uuid", user.getUserUuid());
		}

		List<Node> nodes = document.selectNodes(
			"/portlet-preferences/preference[name/text() = " +
				"'last-publish-date']");

		for (Node node : nodes) {
			document.remove(node);
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

	protected void exportService(
			PortletDataContext portletDataContext, Element rootElement,
			boolean exportServiceSetup)
		throws Exception {

		if (!exportServiceSetup) {
			return;
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDataContext.getPortletId());

		if ((portlet == null) || portlet.isUndeployedPortlet()) {
			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

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

	protected void exportServicePortletPreference(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			PortletPreferences portletPreferences, String serviceName,
			Element parentElement)
		throws Exception {

		String path = ExportImportPathUtil.getServicePortletPreferencesPath(
			portletDataContext, serviceName, ownerId, ownerType);

		if (portletDataContext.isPathProcessed(path)) {
			return;
		}

		String preferencesXML = portletPreferences.getPreferences();

		if (Validator.isNull(preferencesXML)) {
			preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
		}

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

		Element serviceElement = parentElement.addElement("service");

		serviceElement.addAttribute("service-name", serviceName);

		Document document = SAXReaderUtil.read(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element rootElement = document.getRootElement();

		rootElement.addAttribute("owner-id", String.valueOf(ownerId));
		rootElement.addAttribute("owner-type", String.valueOf(ownerType));
		rootElement.addAttribute("default-user", String.valueOf(false));
		rootElement.addAttribute("service-name", serviceName);

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			PortletItem portletItem =
				PortletItemLocalServiceUtil.getPortletItem(ownerId);

			rootElement.addAttribute(
				"archive-user-uuid", portletItem.getUserUuid());
			rootElement.addAttribute("archive-name", portletItem.getName());
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			User user = UserLocalServiceUtil.fetchUserById(ownerId);

			if (user == null) {
				return;
			}

			rootElement.addAttribute("user-uuid", user.getUserUuid());
		}

		List<Node> nodes = document.selectNodes(
			"/portlet-preferences/preference[name/text() = " +
				"'last-publish-date']");

		for (Node node : nodes) {
			document.remove(node);
		}

		serviceElement.addAttribute("path", path);

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
			return;
		}

		exportServicePortletPreference(
			portletDataContext, ownerId, ownerType, portletPreferences,
			serviceName, parentElement);
	}

	protected String getAssetLinkPath(
		PortletDataContext portletDataContext, long assetLinkId) {

		StringBundler sb = new StringBundler(4);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/links/");
		sb.append(assetLinkId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getLockPath(
		PortletDataContext portletDataContext, String className, String key,
		Lock lock) {

		StringBundler sb = new StringBundler(8);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/locks/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(key);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(lock.getLockId());
		sb.append(".xml");

		return sb.toString();
	}

	protected PortletDataContext getPortletDataContext(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				layout.getCompanyId(), groupId, parameterMap, startDate,
				endDate, zipWriter);

		portletDataContext.setOldPlid(plid);
		portletDataContext.setPlid(plid);
		portletDataContext.setPortletId(portletId);

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
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					ownerId, ownerType, LayoutConstants.DEFAULT_PLID,
					portletId);
		}
		else {
			portletPreferences =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					ownerId, ownerType, plid, portletId);
		}

		return portletPreferences;
	}

	private PortletExporter() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletExporter.class);

	private static final PortletExporter _instance = new PortletExporter();

	private final DeletionSystemEventExporter _deletionSystemEventExporter =
		DeletionSystemEventExporter.getInstance();
	private final PermissionExporter _permissionExporter =
		PermissionExporter.getInstance();

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
			Group group = GroupLocalServiceUtil.getGroup(_groupId);

			Layout layout = LayoutLocalServiceUtil.fetchLayout(_plid);

			if (ExportImportThreadLocal.isStagingInProcess() &&
				group.hasStagingGroup()) {

				group = group.getStagingGroup();

				if (layout != null) {
					layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
						layout.getUuid(), group.getGroupId());
				}
			}

			if (layout == null) {
				layout = new LayoutImpl();

				layout.setCompanyId(group.getCompanyId());
				layout.setGroupId(group.getGroupId());
			}

			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.getStrictPortletSetup(
					layout, _portletId);

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