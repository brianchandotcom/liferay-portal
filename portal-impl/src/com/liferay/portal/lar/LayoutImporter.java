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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.LayoutPrototypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchLayoutPrototypeException;
import com.liferay.portal.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.portlet.sites.util.Sites;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

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
public class LayoutImporter {

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws Exception {

		try {
			ExportImportThreadLocal.setLayoutImportInProcess(true);

			doImportLayouts(userId, groupId, privateLayout, parameterMap, file);
		}
		finally {
			ExportImportThreadLocal.setLayoutImportInProcess(false);

			CacheUtil.clearCache();
			JournalContentUtil.clearCache();
			PermissionCacheUtil.clearCache();
		}
	}

	public void validateFile(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws Exception {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), null,
			zipReader);

		validateFile(portletDataContext, null, null, null, null);
	}

	protected String[] appendPortletIds(
		String[] portletIds, String[] newPortletIds, String portletsMergeMode) {

		for (String portletId : newPortletIds) {
			if (ArrayUtil.contains(portletIds, portletId)) {
				continue;
			}

			if (portletsMergeMode.equals(
					PortletDataHandlerKeys.PORTLETS_MERGE_MODE_ADD_TO_BOTTOM)) {

				portletIds = ArrayUtil.append(portletIds, portletId);
			}
			else {
				portletIds = ArrayUtil.append(
					new String[] {portletId}, portletIds);
			}
		}

		return portletIds;
	}

	protected void deleteMissingLayouts(
			long groupId, boolean privateLayout, List<Layout> newLayouts,
			List<Layout> previousLayouts, ServiceContext serviceContext)
		throws Exception {

		// Layouts

		Set<String> existingLayoutUuids = new HashSet<String>();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.hasStagingGroup()) {
			Group stagingGroup = group.getStagingGroup();

			if (stagingGroup.hasPrivateLayouts() ||
				stagingGroup.hasPublicLayouts()) {

				List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
					stagingGroup.getGroupId(), privateLayout);

				for (Layout layout : layouts) {
					existingLayoutUuids.add(layout.getUuid());
				}
			}
		}
		else {
			for (Layout layout : newLayouts) {
				existingLayoutUuids.add(layout.getUuid());
			}
		}

		if (_log.isDebugEnabled() && !existingLayoutUuids.isEmpty()) {
			_log.debug("Delete missing layouts");
		}

		for (Layout layout : previousLayouts) {
			if (!existingLayoutUuids.contains(layout.getUuid())) {
				try {
					LayoutLocalServiceUtil.deleteLayout(
						layout, privateLayout, serviceContext);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}
	}

	protected void doImportLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws Exception {

		boolean deleteMissingLayouts = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.booleanValue());
		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importPublicLayoutPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PUBLIC_LAYOUT_PERMISSIONS);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean importPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean importPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean importTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);
		boolean importThemeSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME_REFERENCE);
		boolean importLogo = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LOGO);
		boolean importLayoutSetSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		boolean layoutSetPrototypeLinkEnabled = MapUtil.getBoolean(
			parameterMap,
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED, true);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayoutSetPrototype()) {
			layoutSetPrototypeLinkEnabled = false;
		}

		//boolean publishToRemote = MapUtil.getBoolean(
		//	parameterMap, PortletDataHandlerKeys.PUBLISH_TO_REMOTE);
		String layoutsImportMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);
		String portletsMergeMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete portlet data " + deletePortletData);
			_log.debug("Import categories " + importCategories);
			_log.debug("Import permissions " + importPermissions);
			_log.debug("Import portlet data " + importPortletData);
			_log.debug("Import portlet setup " + importPortletSetup);
			_log.debug(
				"Import portlet archived setups " +
					importPortletArchivedSetups);
			_log.debug(
				"Import portlet user preferences " +
					importPortletUserPreferences);
			_log.debug("Import theme " + importTheme);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();

		User user = UserUtil.findByPrimaryKey(userId);

		UserIdStrategy strategy = _portletImporter.getUserIdStrategy(
			user, userIdStrategy);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), strategy,
			zipReader);

		portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(portletDataContext));

		portletDataContext.setPrivateLayout(privateLayout);

		// Zip

		InputStream themeZip = null;

		Element rootElement = null;
		Element headerElement = null;
		Element layoutsElement = null;
		List<Element> layoutElements = null;

		validateFile(
			portletDataContext, rootElement, headerElement, layoutsElement,
			layoutElements);

		// Company group id

		long sourceCompanyGroupId = GetterUtil.getLong(
			headerElement.attributeValue("company-group-id"));

		portletDataContext.setSourceCompanyGroupId(sourceCompanyGroupId);

		// Group id

		long sourceGroupId = GetterUtil.getLong(
			headerElement.attributeValue("group-id"));

		portletDataContext.setSourceGroupId(sourceGroupId);

		// Layout and layout set prototype

		String layoutSetPrototypeUuid = layoutsElement.attributeValue(
			"layout-set-prototype-uuid");

		String larType = headerElement.attributeValue("type");

		if (group.isLayoutPrototype() && larType.equals("layout-prototype")) {
			deleteMissingLayouts = false;

			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.getLayoutPrototype(
					group.getClassPK());

			String layoutPrototypeUuid = GetterUtil.getString(
				headerElement.attributeValue("type-uuid"));

			LayoutPrototype existingLayoutPrototype = null;

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				try {
					existingLayoutPrototype =
						LayoutPrototypeLocalServiceUtil.
							getLayoutPrototypeByUuidAndCompanyId(
								layoutPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutPrototypeException nslpe) {
				}
			}

			if (existingLayoutPrototype == null) {
				layoutPrototype.setUuid(layoutPrototypeUuid);

				LayoutPrototypeLocalServiceUtil.updateLayoutPrototype(
					layoutPrototype);
			}
		}
		else if (group.isLayoutSetPrototype() &&
				 larType.equals("layout-set-prototype")) {

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					group.getClassPK());

			String importedLayoutSetPrototypeUuid = GetterUtil.getString(
				headerElement.attributeValue("type-uuid"));

			LayoutSetPrototype existingLayoutSetPrototype = null;

			if (Validator.isNotNull(importedLayoutSetPrototypeUuid)) {
				try {
					existingLayoutSetPrototype =
						LayoutSetPrototypeLocalServiceUtil.
							getLayoutSetPrototypeByUuidAndCompanyId(
								importedLayoutSetPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutSetPrototypeException nslspe) {
				}
			}

			if (existingLayoutSetPrototype == null) {
				layoutSetPrototype.setUuid(importedLayoutSetPrototypeUuid);

				LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
					layoutSetPrototype);
			}
		}
		else if (larType.equals("layout-set-prototype")) {
			layoutSetPrototypeUuid = GetterUtil.getString(
				headerElement.attributeValue("type-uuid"));
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
			layoutSet.setLayoutSetPrototypeLinkEnabled(
				layoutSetPrototypeLinkEnabled);

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}

		// Look and feel

		if (importTheme) {
			themeZip = portletDataContext.getZipEntryAsInputStream("theme.zip");
		}

		// Look and feel

		String themeId = layoutSet.getThemeId();
		String colorSchemeId = layoutSet.getColorSchemeId();

		if (importThemeSettings) {
			Attribute themeIdAttribute = headerElement.attribute("theme-id");

			if (themeIdAttribute != null) {
				themeId = themeIdAttribute.getValue();
			}

			Attribute colorSchemeIdAttribute = headerElement.attribute(
				"color-scheme-id");

			if (colorSchemeIdAttribute != null) {
				colorSchemeId = colorSchemeIdAttribute.getValue();
			}
		}

		if (importLogo) {
			String logoPath = headerElement.attributeValue("logo-path");

			byte[] iconBytes = portletDataContext.getZipEntryAsByteArray(
				logoPath);

			if ((iconBytes != null) && (iconBytes.length > 0)) {
				File logo = FileUtil.createTempFile(iconBytes);

				LayoutSetLocalServiceUtil.updateLogo(
					groupId, privateLayout, true, logo);
			}
			else {
				LayoutSetLocalServiceUtil.updateLogo(
					groupId, privateLayout, false, (File)null);
			}
		}

		if (importLayoutSetSettings) {
			String settings = GetterUtil.getString(
				headerElement.elementText("settings"));

			LayoutSetLocalServiceUtil.updateSettings(
				groupId, privateLayout, settings);
		}

		String css = GetterUtil.getString(headerElement.elementText("css"));

		if (themeZip != null) {
			String importThemeId = importTheme(layoutSet, themeZip);

			if (importThemeId != null) {
				themeId = importThemeId;
				colorSchemeId =
					ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Importing theme takes " + stopWatch.getTime() + " ms");
			}
		}

		boolean wapTheme = false;

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			groupId, privateLayout, themeId, colorSchemeId, css, wapTheme);

		// Read asset categories, asset tags, comments, locks, permissions, and
		// ratings entries to make them available to the data handlers through
		// the context

		if (importPermissions) {
			_permissionImporter.readPortletDataPermissions(portletDataContext);
		}

		if (importCategories || group.isCompany()) {
			_portletImporter.readAssetCategories(portletDataContext);
		}

		_portletImporter.readAssetTags(portletDataContext);
		_portletImporter.readComments(portletDataContext);
		_portletImporter.readExpandoTables(portletDataContext);
		_portletImporter.readLocks(portletDataContext);
		_portletImporter.readRatingsEntries(portletDataContext);

		// Layouts

		List<Layout> previousLayouts = LayoutUtil.findByG_P(
			groupId, privateLayout);

		// Remove layouts that were deleted from the layout set prototype

		if (Validator.isNotNull(layoutSetPrototypeUuid) &&
			layoutSetPrototypeLinkEnabled) {

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);

			Group layoutSetPrototypeGroup = layoutSetPrototype.getGroup();

			for (Layout layout : previousLayouts) {
				String sourcePrototypeLayoutUuid =
					layout.getSourcePrototypeLayoutUuid();

				if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
					continue;
				}

				Layout sourcePrototypeLayout = LayoutUtil.fetchByUUID_G_P(
					sourcePrototypeLayoutUuid,
					layoutSetPrototypeGroup.getGroupId(), true);

				if (sourcePrototypeLayout == null) {
					LayoutLocalServiceUtil.deleteLayout(
						layout, false, serviceContext);
				}
			}
		}

		List<Layout> newLayouts = new ArrayList<Layout>();

		Map<Long, Layout> newLayoutsMap =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		if (_log.isDebugEnabled()) {
			if (layoutElements.size() > 0) {
				_log.debug("Importing layouts");
			}
		}

		for (Element layoutElement : layoutElements) {
			importLayout(
				portletDataContext, user, layoutCache, previousLayouts,
				newLayouts, newLayoutsMap, portletsMergeMode, themeId,
				colorSchemeId, layoutsImportMode, privateLayout,
				importPermissions, importPublicLayoutPermissions,
				importThemeSettings, rootElement, layoutElement);
		}

		Element portletsElement = rootElement.element("portlets");

		List<Element> portletElements = portletsElement.elements("portlet");

		// Delete portlet data

		if (deletePortletData) {
			if (_log.isDebugEnabled()) {
				if (portletElements.size() > 0) {
					_log.debug("Deleting portlet data");
				}
			}

			for (Element portletElement : portletElements) {
				String portletId = portletElement.attributeValue("portlet-id");
				long layoutId = GetterUtil.getLong(
					portletElement.attributeValue("layout-id"));
				long plid = newLayoutsMap.get(layoutId).getPlid();

				portletDataContext.setPlid(plid);

				_portletImporter.deletePortletData(
					portletDataContext, portletId, plid);
			}
		}

		// Import portlets

		if (_log.isDebugEnabled()) {
			if (portletElements.size() > 0) {
				_log.debug("Importing portlets");
			}
		}

		for (Element portletElement : portletElements) {
			String portletPath = portletElement.attributeValue("path");
			String portletId = portletElement.attributeValue("portlet-id");
			long layoutId = GetterUtil.getLong(
				portletElement.attributeValue("layout-id"));
			long oldPlid = GetterUtil.getLong(
				portletElement.attributeValue("old-plid"));

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
				continue;
			}

			Layout layout = newLayoutsMap.get(layoutId);

			long plid = LayoutConstants.DEFAULT_PLID;

			if (layout != null) {
				plid = layout.getPlid();
			}

			layout = LayoutUtil.fetchByPrimaryKey(plid);

			if ((layout == null) && !group.isCompany()) {
				continue;
			}

			portletDataContext.setPlid(plid);
			portletDataContext.setOldPlid(oldPlid);

			Document portletDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(portletPath));

			portletElement = portletDocument.getRootElement();

			// The order of the import is important. You must always import the
			// portlet preferences first, then the portlet data, then the
			// portlet permissions. The import of the portlet data assumes that
			// portlet preferences already exist.

			_portletImporter.setPortletScope(
				portletDataContext, portletElement);

			long portletPreferencesGroupId = groupId;

			Element portletDataElement = portletElement.element("portlet-data");

			boolean importData =
				importPortletData && (portletDataElement != null);

			try {
				if ((layout != null) && !group.isCompany()) {
					portletPreferencesGroupId = layout.getGroupId();
				}

				// Portlet preferences

				_portletImporter.importPortletPreferences(
					portletDataContext, layoutSet.getCompanyId(),
					portletPreferencesGroupId, layout, null, portletElement,
					importPortletSetup, importPortletArchivedSetups,
					importPortletUserPreferences, false, importData);

				// Portlet data

				if (importData) {
					_portletImporter.importPortletData(
						portletDataContext, portletId, plid,
						portletDataElement);
				}
			}
			finally {
				_portletImporter.resetPortletScope(
					portletDataContext, portletPreferencesGroupId);
			}

			// Portlet permissions

			if (importPermissions) {
				_permissionImporter.importPortletPermissions(
					layoutCache, companyId, groupId, userId, layout,
					portletElement, portletId);
			}

			// Archived setups

			_portletImporter.importPortletPreferences(
				portletDataContext, layoutSet.getCompanyId(), groupId, null,
				null, portletElement, importPortletSetup,
				importPortletArchivedSetups, importPortletUserPreferences,
				false, importData);
		}

		if (importPermissions) {
			if (userId > 0) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					User.class);

				indexer.reindex(userId);
			}
		}

		// Asset links

		_portletImporter.readAssetLinks(portletDataContext);

		// Delete missing layouts

		if (deleteMissingLayouts) {
			deleteMissingLayouts(
				groupId, privateLayout, newLayouts, previousLayouts,
				serviceContext);
		}

		// Page count

		layoutSet = LayoutSetLocalServiceUtil.updatePageCount(
			groupId, privateLayout);

		if (_log.isInfoEnabled()) {
			_log.info("Importing layouts takes " + stopWatch.getTime() + " ms");
		}

		// Site

		GroupLocalServiceUtil.updateSite(groupId, true);

		// Last merge time must be the same for merged layouts and the layout
		// set

		long lastMergeTime = System.currentTimeMillis();

		for (Layout layout : newLayouts) {
			boolean modifiedTypeSettingsProperties = false;

			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			// Journal article layout type

			String articleId = typeSettingsProperties.getProperty("article-id");

			if (Validator.isNotNull(articleId)) {
				Map<String, String> articleIds =
					(Map<String, String>)portletDataContext.
						getNewPrimaryKeysMap(
							JournalArticle.class + ".articleId");

				typeSettingsProperties.setProperty(
					"article-id",
					MapUtil.getString(articleIds, articleId, articleId));

				modifiedTypeSettingsProperties = true;
			}

			// Last merge time for layout

			if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

				typeSettingsProperties.setProperty(
					Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

				modifiedTypeSettingsProperties = true;
			}

			if (modifiedTypeSettingsProperties) {
				LayoutUtil.update(layout);
			}
		}

		// Last merge time for layout set

		if (layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			UnicodeProperties settingsProperties =
				layoutSet.getSettingsProperties();

			settingsProperties.setProperty(
				Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}

		zipReader.close();
	}

	protected void fixTypeSettings(Layout layout) throws Exception {
		if (!layout.isTypeURL()) {
			return;
		}

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		String url = GetterUtil.getString(typeSettings.getProperty("url"));

		String friendlyURLPrivateGroupPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String friendlyURLPrivateUserPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String friendlyURLPublicPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		if (!url.startsWith(friendlyURLPrivateGroupPath) &&
			!url.startsWith(friendlyURLPrivateUserPath) &&
			!url.startsWith(friendlyURLPublicPath)) {

			return;
		}

		int x = url.indexOf(CharPool.SLASH, 1);

		if (x == -1) {
			return;
		}

		int y = url.indexOf(CharPool.SLASH, x + 1);

		if (y == -1) {
			return;
		}

		String friendlyURL = url.substring(x, y);

		if (!friendlyURL.equals(LayoutExporter.SAME_GROUP_FRIENDLY_URL)) {
			return;
		}

		Group group = layout.getGroup();

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + group.getFriendlyURL() + url.substring(y));
	}

	protected String getLayoutSetPrototypePath(
		PortletDataContext portletDataContext, String layoutSetPrototypeUuid) {

		return ExportImportPathUtil.getSourceRootPath(portletDataContext) +
			"/layout-set-prototype/" + layoutSetPrototypeUuid;
	}

	protected void importJournalArticle(
			PortletDataContext portletDataContext, Layout layout,
			Element layoutElement)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String articleId = typeSettingsProperties.getProperty(
			"article-id", StringPool.BLANK);

		if (Validator.isNull(articleId)) {
			return;
		}

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				layoutElement, JournalArticle.class);

		if (!referenceDataElements.isEmpty()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, referenceDataElements.get(0));
		}

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		articleId = MapUtil.getString(articleIds, articleId, articleId);

		typeSettingsProperties.setProperty("article-id", articleId);

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			portletDataContext.getScopeGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId(), StringPool.BLANK, articleId, true);
	}

	protected void importLayout(
			PortletDataContext portletDataContext, User user,
			LayoutCache layoutCache, List<Layout> previousLayouts,
			List<Layout> newLayouts, Map<Long, Layout> newLayoutsMap,
			String portletsMergeMode, String themeId, String colorSchemeId,
			String layoutsImportMode, boolean privateLayout,
			boolean importPermissions, boolean importPublicLayoutPermissions,
			boolean importThemeSettings, Element rootElement,
			Element layoutElement)
		throws Exception {

		long groupId = portletDataContext.getGroupId();

		String layoutUuid = GetterUtil.getString(
			layoutElement.attributeValue("layout-uuid"));

		long layoutId = GetterUtil.getInteger(
			layoutElement.attributeValue("layout-id"));

		long oldLayoutId = layoutId;

		boolean deleteLayout = GetterUtil.getBoolean(
			layoutElement.attributeValue("delete"));

		if (deleteLayout) {
			Layout layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutUuid, groupId, privateLayout);

			if (layout != null) {
				newLayoutsMap.put(oldLayoutId, layout);

				ServiceContext serviceContext =
					ServiceContextThreadLocal.getServiceContext();

				LayoutLocalServiceUtil.deleteLayout(
					layout, false, serviceContext);
			}

			return;
		}

		String path = layoutElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Layout layout = (Layout)portletDataContext.getZipEntryAsObject(path);

		Layout existingLayout = null;
		Layout importedLayout = null;

		String friendlyURL = layout.getFriendlyURL();

		if (layoutsImportMode.equals(
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_ADD_AS_NEW)) {

			layoutId = LayoutLocalServiceUtil.getNextLayoutId(
				groupId, privateLayout);
			friendlyURL = StringPool.SLASH + layoutId;
		}
		else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME)) {

			Locale locale = LocaleUtil.getDefault();

			String localizedName = layout.getName(locale);

			for (Layout curLayout : previousLayouts) {
				if (localizedName.equals(curLayout.getName(locale)) ||
					friendlyURL.equals(curLayout.getFriendlyURL())) {

					existingLayout = curLayout;

					break;
				}
			}

			if (existingLayout == null) {
				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
		}
		else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			existingLayout = LayoutUtil.fetchByG_P_SPLU(
				groupId, privateLayout, layout.getUuid());

			if (SitesUtil.isLayoutModifiedSinceLastMerge(existingLayout)) {
				newLayoutsMap.put(oldLayoutId, existingLayout);

				return;
			}
		}
		else {

			// The default behaviour of import mode is
			// PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID

			existingLayout = LayoutUtil.fetchByUUID_G_P(
				layout.getUuid(), groupId, privateLayout);

			if (existingLayout == null) {
				existingLayout = LayoutUtil.fetchByG_P_F(
					groupId, privateLayout, friendlyURL);
			}

			if (existingLayout == null) {
				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
		}

		if (_log.isDebugEnabled()) {
			if (existingLayout == null) {
				_log.debug(
					"Layout with {groupId=" + groupId + ",privateLayout=" +
						privateLayout + ",layoutId=" + layoutId +
							"} does not exist");
			}
			else {
				_log.debug(
					"Layout with {groupId=" + groupId + ",privateLayout=" +
						privateLayout + ",layoutId=" + layoutId + "} exists");
			}
		}

		if (existingLayout == null) {
			long plid = CounterLocalServiceUtil.increment();

			importedLayout = LayoutUtil.create(plid);

			if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

				importedLayout.setSourcePrototypeLayoutUuid(layout.getUuid());

				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
			else {
				importedLayout.setCreateDate(layout.getCreateDate());
				importedLayout.setModifiedDate(layout.getModifiedDate());
				importedLayout.setLayoutPrototypeUuid(
					layout.getLayoutPrototypeUuid());
				importedLayout.setLayoutPrototypeLinkEnabled(
					layout.isLayoutPrototypeLinkEnabled());
				importedLayout.setSourcePrototypeLayoutUuid(
					layout.getSourcePrototypeLayoutUuid());
			}

			importedLayout.setUuid(layout.getUuid());
			importedLayout.setGroupId(groupId);
			importedLayout.setPrivateLayout(privateLayout);
			importedLayout.setLayoutId(layoutId);

			// Resources

			boolean addGroupPermissions = true;

			Group group = importedLayout.getGroup();

			if (privateLayout && group.isUser()) {
				addGroupPermissions = false;
			}

			boolean addGuestPermissions = false;

			if (!privateLayout || layout.isTypeControlPanel()) {
				addGuestPermissions = true;
			}

			ResourceLocalServiceUtil.addResources(
				user.getCompanyId(), groupId, user.getUserId(),
				Layout.class.getName(), importedLayout.getPlid(), false,
				addGroupPermissions, addGuestPermissions);

			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId, privateLayout);

			importedLayout.setLayoutSet(layoutSet);
		}
		else {
			importedLayout = existingLayout;
		}

		newLayoutsMap.put(oldLayoutId, importedLayout);

		long parentLayoutId = layout.getParentLayoutId();

		Node parentLayoutNode = rootElement.selectSingleNode(
			"./layouts/layout[@layout-id='" + parentLayoutId + "']");

		String parentLayoutUuid = GetterUtil.getString(
			layoutElement.attributeValue("parent-layout-uuid"));

		if ((parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) &&
			(parentLayoutNode != null)) {

			importLayout(
				portletDataContext, user, layoutCache, previousLayouts,
				newLayouts, newLayoutsMap, portletsMergeMode, themeId,
				colorSchemeId, layoutsImportMode, privateLayout,
				importPermissions, importPublicLayoutPermissions,
				importThemeSettings, rootElement, (Element)parentLayoutNode);

			Layout parentLayout = newLayoutsMap.get(parentLayoutId);

			parentLayoutId = parentLayout.getLayoutId();
		}
		else if (Validator.isNotNull(parentLayoutUuid)) {
			Layout parentLayout =
				LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					parentLayoutUuid, groupId, privateLayout);

			parentLayoutId = parentLayout.getLayoutId();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Importing layout with layout id " + layoutId +
					" and parent layout id " + parentLayoutId);
		}

		importedLayout.setCompanyId(user.getCompanyId());
		importedLayout.setParentLayoutId(parentLayoutId);
		importedLayout.setName(layout.getName());
		importedLayout.setTitle(layout.getTitle());
		importedLayout.setDescription(layout.getDescription());
		importedLayout.setKeywords(layout.getKeywords());
		importedLayout.setRobots(layout.getRobots());
		importedLayout.setType(layout.getType());

		if (layout.isTypeArticle()) {
			importJournalArticle(portletDataContext, layout, layoutElement);

			updateTypeSettings(importedLayout, layout);
		}
		else if (layout.isTypePortlet() &&
				 Validator.isNotNull(layout.getTypeSettings()) &&
				 !portletsMergeMode.equals(
					 PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE)) {

			mergePortlets(
				importedLayout, layout.getTypeSettings(), portletsMergeMode);
		}
		else if (layout.isTypeLinkToLayout()) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			long linkToLayoutId = GetterUtil.getLong(
				typeSettingsProperties.getProperty(
					"linkToLayoutId", StringPool.BLANK));

			if (linkToLayoutId > 0) {
				Node linkedLayoutNode = rootElement.selectSingleNode(
					"./layouts/layout[@layout-id='" + linkToLayoutId + "']");

				if (linkedLayoutNode != null) {
					importLayout(
						portletDataContext, user, layoutCache, previousLayouts,
						newLayouts, newLayoutsMap, portletsMergeMode, themeId,
						colorSchemeId, layoutsImportMode, privateLayout,
						importPermissions, importPublicLayoutPermissions,
						importThemeSettings, rootElement,
						(Element)linkedLayoutNode);

					Layout linkedLayout = newLayoutsMap.get(linkToLayoutId);

					typeSettingsProperties.setProperty(
						"privateLayout",
						String.valueOf(linkedLayout.isPrivateLayout()));
					typeSettingsProperties.setProperty(
						"linkToLayoutId",
						String.valueOf(linkedLayout.getLayoutId()));
				}
				else {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler();

						sb.append("Unable to link layout with friendly URL ");
						sb.append(layout.getFriendlyURL());
						sb.append(" and layout id ");
						sb.append(layout.getLayoutId());
						sb.append(" to layout with layout id ");
						sb.append(linkToLayoutId);

						_log.warn(sb.toString());
					}
				}
			}

			updateTypeSettings(importedLayout, layout);
		}
		else {
			updateTypeSettings(importedLayout, layout);
		}

		importedLayout.setHidden(layout.isHidden());
		importedLayout.setFriendlyURL(friendlyURL);

		if (importThemeSettings) {
			importedLayout.setThemeId(layout.getThemeId());
			importedLayout.setColorSchemeId(layout.getColorSchemeId());
		}
		else {
			importedLayout.setThemeId(StringPool.BLANK);
			importedLayout.setColorSchemeId(StringPool.BLANK);
		}

		importedLayout.setWapThemeId(layout.getWapThemeId());
		importedLayout.setWapColorSchemeId(layout.getWapColorSchemeId());
		importedLayout.setCss(layout.getCss());
		importedLayout.setPriority(layout.getPriority());
		importedLayout.setLayoutPrototypeUuid(layout.getLayoutPrototypeUuid());
		importedLayout.setLayoutPrototypeLinkEnabled(
			layout.isLayoutPrototypeLinkEnabled());

		StagingUtil.updateLastImportSettings(
			layoutElement, importedLayout, portletDataContext);

		fixTypeSettings(importedLayout);

		importedLayout.setIconImage(false);

		if (layout.isIconImage()) {
			String iconImagePath = layoutElement.elementText("icon-image-path");

			byte[] iconBytes = portletDataContext.getZipEntryAsByteArray(
				iconImagePath);

			if ((iconBytes != null) && (iconBytes.length > 0)) {
				importedLayout.setIconImage(true);

				if (importedLayout.getIconImageId() == 0) {
					long iconImageId = CounterLocalServiceUtil.increment();

					importedLayout.setIconImageId(iconImageId);
				}

				ImageLocalServiceUtil.updateImage(
					importedLayout.getIconImageId(), iconBytes);
			}
		}
		else {
			ImageLocalServiceUtil.deleteImage(importedLayout.getIconImageId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutElement, importedLayout, null);

		importedLayout.setExpandoBridgeAttributes(serviceContext);

		LayoutUtil.update(importedLayout);

		portletDataContext.setPlid(importedLayout.getPlid());
		portletDataContext.setOldPlid(layout.getPlid());

		newLayouts.add(importedLayout);

		// Layout permissions

		if (importPermissions) {
			_permissionImporter.importLayoutPermissions(
				layoutCache, portletDataContext.getCompanyId(), groupId,
				user.getUserId(), importedLayout, layoutElement, rootElement);
		}

		if (importPublicLayoutPermissions) {
			String resourceName = Layout.class.getName();
			String resourcePrimKey = String.valueOf(importedLayout.getPlid());

			Role guestRole = RoleLocalServiceUtil.getRole(
				importedLayout.getCompanyId(), RoleConstants.GUEST);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				importedLayout.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
				guestRole.getRoleId(), new String[] {ActionKeys.VIEW});
		}

		_portletImporter.importPortletData(
			portletDataContext, PortletKeys.DOCKBAR, null, layoutElement);
	}

	protected void importLayoutSetPrototype(
			PortletDataContext portletDataContext, User user,
			String layoutSetPrototypeUuid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String path = getLayoutSetPrototypePath(
			portletDataContext, layoutSetPrototypeUuid);

		LayoutSetPrototype layoutSetPrototype = null;

		try {
			layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, serviceContext.getCompanyId());
		}
		catch (NoSuchLayoutSetPrototypeException nslspe) {
		}

		if (layoutSetPrototype == null) {
			layoutSetPrototype =
				(LayoutSetPrototype)portletDataContext.getZipEntryAsObject(
					path.concat(".xml"));

			serviceContext.setUuid(layoutSetPrototypeUuid);

			layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
					user.getUserId(), user.getCompanyId(),
					layoutSetPrototype.getNameMap(),
					layoutSetPrototype.getDescription(),
					layoutSetPrototype.getActive(), true, serviceContext);
		}

		InputStream inputStream = portletDataContext.getZipEntryAsInputStream(
			path.concat(".lar"));

		SitesUtil.importLayoutSetPrototype(
			layoutSetPrototype, inputStream, serviceContext);
	}

	protected String importTheme(LayoutSet layoutSet, InputStream themeZip)
		throws Exception {

		ThemeLoader themeLoader = ThemeLoaderFactory.getDefaultThemeLoader();

		if (themeLoader == null) {
			_log.error("No theme loaders are deployed");

			return null;
		}

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(themeZip);

		String lookAndFeelXML = zipReader.getEntryAsString(
			"liferay-look-and-feel.xml");

		String themeId = String.valueOf(layoutSet.getGroupId());

		if (layoutSet.isPrivateLayout()) {
			themeId += "-private";
		}
		else {
			themeId += "-public";
		}

		if (PropsValues.THEME_LOADER_NEW_THEME_ID_ON_IMPORT) {
			Date now = new Date();

			themeId += "-" + Time.getShortTimestamp(now);
		}

		String themeName = themeId;

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$GROUP_ID$]", "[$THEME_ID$]", "[$THEME_NAME$]"
			},
			new String[] {
				String.valueOf(layoutSet.getGroupId()), themeId, themeName
			}
		);

		FileUtil.deltree(
			themeLoader.getFileStorage() + StringPool.SLASH + themeId);

		List<String> zipEntries = zipReader.getEntries();

		for (String zipEntry : zipEntries) {
			String key = zipEntry;

			if (key.equals("liferay-look-and-feel.xml")) {
				FileUtil.write(
					themeLoader.getFileStorage() + StringPool.SLASH + themeId +
						StringPool.SLASH + key,
					lookAndFeelXML.getBytes());
			}
			else {
				InputStream is = zipReader.getEntryAsInputStream(zipEntry);

				FileUtil.write(
					themeLoader.getFileStorage() + StringPool.SLASH + themeId +
						StringPool.SLASH + key,
					is);
			}
		}

		themeLoader.loadThemes();

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			_loadThemesMethodHandler, true);

		clusterRequest.setFireAndForget(true);

		ClusterExecutorUtil.execute(clusterRequest);

		themeId +=
			PortletConstants.WAR_SEPARATOR +
				themeLoader.getServletContextName();

		return PortalUtil.getJsSafePortletId(themeId);
	}

	protected void mergePortlets(
		Layout layout, String newTypeSettings, String portletsMergeMode) {

		try {
			UnicodeProperties previousTypeSettingsProperties =
				layout.getTypeSettingsProperties();

			LayoutTypePortlet previousLayoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			LayoutTemplate previousLayoutTemplate =
				previousLayoutType.getLayoutTemplate();

			List<String> previousColumns = previousLayoutTemplate.getColumns();

			UnicodeProperties newTypeSettingsProperties = new UnicodeProperties(
				true);

			newTypeSettingsProperties.load(newTypeSettings);

			String layoutTemplateId = newTypeSettingsProperties.getProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

			previousTypeSettingsProperties.setProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
				layoutTemplateId);

			String nestedColumnIds = newTypeSettingsProperties.getProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS);

			if (Validator.isNotNull(nestedColumnIds)) {
				previousTypeSettingsProperties.setProperty(
					LayoutTypePortletConstants.NESTED_COLUMN_IDS,
					nestedColumnIds);

				String[] nestedColumnIdsArray = StringUtil.split(
					nestedColumnIds);

				for (String nestedColumnId : nestedColumnIdsArray) {
					String nestedColumnValue =
						newTypeSettingsProperties.getProperty(nestedColumnId);

					previousTypeSettingsProperties.setProperty(
						nestedColumnId, nestedColumnValue);
				}
			}

			LayoutTemplate newLayoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, null);

			String[] newPortletIds = new String[0];

			for (String columnId : newLayoutTemplate.getColumns()) {
				String columnValue = newTypeSettingsProperties.getProperty(
					columnId);

				String[] portletIds = StringUtil.split(columnValue);

				if (!previousColumns.contains(columnId)) {
					newPortletIds = ArrayUtil.append(newPortletIds, portletIds);
				}
				else {
					String[] previousPortletIds = StringUtil.split(
						previousTypeSettingsProperties.getProperty(columnId));

					portletIds = appendPortletIds(
						previousPortletIds, portletIds, portletsMergeMode);

					previousTypeSettingsProperties.setProperty(
						columnId, StringUtil.merge(portletIds));
				}
			}

			// Add portlets in non-existent column to the first column

			String columnId = previousColumns.get(0);

			String[] portletIds = StringUtil.split(
				previousTypeSettingsProperties.getProperty(columnId));

			appendPortletIds(portletIds, newPortletIds, portletsMergeMode);

			previousTypeSettingsProperties.setProperty(
				columnId, StringUtil.merge(portletIds));

			layout.setTypeSettings(previousTypeSettingsProperties.toString());
		}
		catch (IOException ioe) {
			layout.setTypeSettings(newTypeSettings);
		}
	}

	protected void updateTypeSettings(Layout importedLayout, Layout layout)
		throws PortalException, SystemException {

		LayoutTypePortlet importedLayoutType =
			(LayoutTypePortlet)importedLayout.getLayoutType();

		List<String> importedPortletIds = importedLayoutType.getPortletIds();

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		importedPortletIds.removeAll(layoutType.getPortletIds());

		if (!importedPortletIds.isEmpty()) {
			PortletLocalServiceUtil.deletePortlets(
				importedLayout.getCompanyId(),
				importedPortletIds.toArray(
					new String[importedPortletIds.size()]),
				importedLayout.getPlid());
		}

		importedLayout.setTypeSettings(layout.getTypeSettings());
	}

	protected void validateFile(
			PortletDataContext portletDataContext, Element rootElement,
			Element headerElement, Element layoutsElement,
			List<Element> layoutElements)
		throws Exception {

		// Manifest

		String xml = portletDataContext.getZipEntryAsString("/manifest.xml");

		if (xml == null) {
			throw new LARFileException("manifest.xml not found in the LAR");
		}

		try {
			Document document = SAXReaderUtil.read(xml);

			rootElement = document.getRootElement();
		}
		catch (Exception e) {
			throw new LARFileException(e);
		}

		// Build compatibility

		headerElement = rootElement.element("header");

		int buildNumber = ReleaseInfo.getBuildNumber();

		int importBuildNumber = GetterUtil.getInteger(
			headerElement.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Type

		String larType = headerElement.attributeValue("type");

		if (!larType.equals("layout-prototype") &&
			!larType.equals("layout-set") &&
			!larType.equals("layout-set-prototype")) {

			throw new LARTypeException(larType);
		}

		// Available locales

		Locale[] sourceAvailableLocales = LocaleUtil.fromLanguageIds(
			StringUtil.split(
				headerElement.attributeValue("available-locales")));

		Locale[] targetAvailableLocales = LanguageUtil.getAvailableLocales();

		for (Locale sourceAvailableLocale : sourceAvailableLocales) {
			if (!ArrayUtil.contains(
					targetAvailableLocales, sourceAvailableLocale)) {

				LocaleException le = new LocaleException();

				le.setSourceAvailableLocales(sourceAvailableLocales);
				le.setTargetAvailableLocales(targetAvailableLocales);

				throw le;
			}
		}

		// Layout prototypes validity

		layoutsElement = rootElement.element("layouts");

		layoutElements = layoutsElement.elements("layout");

		validateLayoutPrototypes(
			portletDataContext.getCompanyId(), layoutsElement, layoutElements);
	}

	protected void validateLayoutPrototypes(
			long companyId, Element layoutsElement,
			List<Element> layoutElements)
		throws Exception {

		List<Tuple> missingLayoutPrototypes = new ArrayList<Tuple>();

		String layoutSetPrototypeUuid = layoutsElement.attributeValue(
			"layout-set-prototype-uuid");

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			try {
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);
			}
			catch (NoSuchLayoutSetPrototypeException nlspe) {
				String layoutSetPrototypeName = layoutsElement.attributeValue(
					"layout-set-prototype-name");

				missingLayoutPrototypes.add(
					new Tuple(
						LayoutSetPrototype.class.getName(),
						layoutSetPrototypeUuid, layoutSetPrototypeName));
			}
		}

		for (Element layoutElement : layoutElements) {
			String layoutPrototypeUuid = GetterUtil.getString(
				layoutElement.attributeValue("layout-prototype-uuid"));

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				try {
					LayoutPrototypeLocalServiceUtil.
						getLayoutPrototypeByUuidAndCompanyId(
							layoutPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutPrototypeException nslpe) {
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

	private static Log _log = LogFactoryUtil.getLog(LayoutImporter.class);

	private static MethodHandler _loadThemesMethodHandler = new MethodHandler(
		new MethodKey(ThemeLoaderFactory.class, "loadThemes"));

	private PermissionImporter _permissionImporter = new PermissionImporter();
	private PortletImporter _portletImporter = new PortletImporter();

}