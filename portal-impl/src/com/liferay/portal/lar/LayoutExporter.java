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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.util.ContentUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

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

	public static List<Portlet> getPortletDataHandlerPortlets(
			List<Layout> layouts)
		throws SystemException {

		List<Portlet> portlets = new ArrayList<Portlet>();
		Set<String> rootPortletIds = new HashSet<String>();

		for (Layout curLayout : layouts) {
			if (!curLayout.isTypePortlet()) {
				continue;
			}

			LayoutTypePortlet curLayoutTypePortlet =
				(LayoutTypePortlet)curLayout.getLayoutType();

			for (String portletId : curLayoutTypePortlet.getPortletIds()) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					curLayout.getCompanyId(), portletId);

				if (portlet == null) {
					continue;
				}

				PortletDataHandler portletDataHandler =
					portlet.getPortletDataHandlerInstance();

				if ((portletDataHandler == null) ||
					rootPortletIds.contains(portlet.getRootPortletId())) {

					continue;
				}

				rootPortletIds.add(portlet.getRootPortletId());

				portlets.add(portlet);
			}
		}

		return portlets;
	}

	public static List<Portlet> getPortletDataHandlerPortlets(
			long groupId, boolean privateLayout)
		throws SystemException {

		return getPortletDataHandlerPortlets(
			LayoutLocalServiceUtil.getLayouts(groupId, privateLayout));
	}

	public static void updateLastPublishDate(
			LayoutSet layoutSet, long lastPublishDate)
		throws Exception {

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		if (lastPublishDate <= 0) {
			settingsProperties.remove("last-publish-date");
		}
		else {
			settingsProperties.setProperty(
				"last-publish-date", String.valueOf(lastPublishDate));
		}

		LayoutSetLocalServiceUtil.updateSettings(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			settingsProperties.toString());
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
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean exportTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);
		boolean exportThemeSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME_REFERENCE);
		boolean exportLogo = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LOGO);
		boolean exportLayoutSetSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);
		boolean updateLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
			_log.debug(
				"Export portlet archived setups " +
					exportPortletArchivedSetups);
			_log.debug(
				"Export portlet user preferences " +
					exportPortletUserPreferences);
			_log.debug("Export theme " + exportTheme);
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

		long lastPublishDate = System.currentTimeMillis();

		if (endDate != null) {
			lastPublishDate = endDate.getTime();
		}

		if (exportIgnoreLastPublishDate) {
			endDate = null;
			startDate = null;
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				companyId, groupId, parameterMap, startDate, endDate,
				zipWriter);

		portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(portletDataContext));

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		Element headerElement = rootElement.addElement("header");

		headerElement.addAttribute(
			"available-locales",
			StringUtil.merge(LanguageUtil.getAvailableLocales()));
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

		if (exportTheme || exportThemeSettings) {
			headerElement.addAttribute("theme-id", layoutSet.getThemeId());
			headerElement.addAttribute(
				"color-scheme-id", layoutSet.getColorSchemeId());
		}

		if (exportLogo) {
			Image image = ImageLocalServiceUtil.getImage(layoutSet.getLogoId());

			if ((image != null) && (image.getTextObj() != null)) {
				String logoPath = ExportImportPathUtil.getRootPath(
					portletDataContext);

				logoPath += "/logo";

				headerElement.addAttribute("logo-path", logoPath);

				portletDataContext.addZipEntry(logoPath, image.getTextObj());
			}
		}

		if (exportLayoutSetSettings) {
			Element settingsElement = headerElement.addElement("settings");

			settingsElement.addCDATA(layoutSet.getSettings());
		}

		Element cssElement = headerElement.addElement("css");

		cssElement.addCDATA(layoutSet.getCss());

		Map<String, Object[]> portletIds =
			new LinkedHashMap<String, Object[]>();

		List<Layout> layouts = null;

		if ((layoutIds == null) || (layoutIds.length == 0)) {
			layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);
		}
		else {
			layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, layoutIds);
		}

		List<Portlet> portlets = getDataSiteLevelPortlets(companyId);

		long plid = LayoutConstants.DEFAULT_PLID;

		if (!layouts.isEmpty()) {
			Layout firstLayout = layouts.get(0);

			plid = firstLayout.getPlid();
		}

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		for (Portlet portlet : portlets) {
			String portletId = portlet.getRootPortletId();

			if (!group.isStagedPortlet(portletId)) {
				continue;
			}

			String key = PortletPermissionUtil.getPrimaryKey(0, portletId);

			if (portletIds.get(key) == null) {
				portletIds.put(
					key,
					new Object[] {
						portletId, plid, groupId, StringPool.BLANK,
						StringPool.BLANK
					});
			}
		}

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

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
			exportLayout(
				portletDataContext, portlets, portletIds, layout,
				layoutsElement);
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		Element portletsElement = rootElement.addElement("portlets");

		for (Map.Entry<String, Object[]> portletIdsEntry :
				portletIds.entrySet()) {

			Object[] portletObjects = portletIdsEntry.getValue();

			String portletId = null;
			plid = LayoutConstants.DEFAULT_PLID;
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
				if (!group.isCompany() &&
					(plid <= LayoutConstants.DEFAULT_PLID)) {

					continue;
				}

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Assuming global scope because no layout was found");
				}

				layout = new LayoutImpl();

				layout.setGroupId(groupId);
				layout.setCompanyId(companyId);
			}

			portletDataContext.setPlid(plid);
			portletDataContext.setOldPlid(plid);
			portletDataContext.setScopeGroupId(scopeGroupId);
			portletDataContext.setScopeType(scopeType);
			portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

			boolean[] exportPortletControls = getExportPortletControls(
				companyId, portletId, parameterMap, type);

			_portletExporter.exportPortlet(
				portletDataContext, layoutCache, portletId, layout,
				portletsElement, defaultUserId, exportPermissions,
				exportPortletArchivedSetups, exportPortletControls[0],
				exportPortletControls[1], exportPortletUserPreferences);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		exportAssetCategories(
			portletDataContext, exportPortletDataAll, exportCategories,
			group.isCompany());

		_portletExporter.exportAssetLinks(portletDataContext);
		_portletExporter.exportAssetTags(portletDataContext);
		_portletExporter.exportComments(portletDataContext);
		_portletExporter.exportDeletions(portletDataContext);
		_portletExporter.exportExpandoTables(portletDataContext);
		_portletExporter.exportLocks(portletDataContext);

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(
				portletDataContext);
		}

		_portletExporter.exportRatingsEntries(portletDataContext, rootElement);

		if (exportTheme && !portletDataContext.isPerformDirectBinaryImport()) {
			exportTheme(layoutSet, zipWriter);
		}

		ExportImportUtil.writeManifestSummary(
			document, portletDataContext.getManifestSummary());

		if (_log.isInfoEnabled()) {
			if (stopWatch != null) {
				_log.info(
					"Exporting layouts takes " + stopWatch.getTime() + " ms");
			}
			else {
				_log.info("Exporting layouts is finished");
			}
		}

		portletDataContext.addZipEntry(
			"/manifest.xml", document.formattedString());

		try {
			return zipWriter.getFile();
		}
		finally {
			if (updateLastPublishDate) {
				updateLastPublishDate(layoutSet, lastPublishDate);
			}
		}
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
			PortletDataContext portletDataContext, List<Portlet> portlets,
			Map<String, Object[]> portletIds, Layout layout,
			Element layoutsElement)
		throws Exception {

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, layout);

		if (!layout.isSupportsEmbeddedPortlets()) {

			// Only portlet type layouts support page scoping

			return;
		}

		if (layout.isTypePortlet()) {
			for (Portlet portlet : portlets) {
				if (portlet.isScopeable() && layout.hasScopeGroup()) {
					String key = PortletPermissionUtil.getPrimaryKey(
						layout.getPlid(), portlet.getPortletId());

					Group scopeGroup = layout.getScopeGroup();

					portletIds.put(
						key,
						new Object[] {
							portlet.getPortletId(), layout.getPlid(),
							scopeGroup.getGroupId(), StringPool.BLANK,
							layout.getUuid()
						});
				}
			}
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		// The getAllPortlets method returns all effective portlets for any
		// layout type, including embedded portlets, or in the case of panel
		// type layout, selected portlets

		for (Portlet portlet : layoutTypePortlet.getAllPortlets()) {
			String portletId = portlet.getPortletId();

			javax.portlet.PortletPreferences jxPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);

			String scopeType = GetterUtil.getString(
				jxPreferences.getValue("lfrScopeType", null));
			String scopeLayoutUuid = GetterUtil.getString(
				jxPreferences.getValue("lfrScopeLayoutUuid", null));

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

	protected void exportTheme(LayoutSet layoutSet, ZipWriter zipWriter)
		throws Exception {

		Theme theme = layoutSet.getTheme();

		String lookAndFeelXML = ContentUtil.get(
			"com/liferay/portal/dependencies/liferay-look-and-feel.xml.tmpl");

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$TEMPLATE_EXTENSION$]", "[$VIRTUAL_PATH$]"
			},
			new String[] {
				theme.getTemplateExtension(), theme.getVirtualPath()
			}
		);

		String servletContextName = theme.getServletContextName();

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Servlet context not found for theme " +
						theme.getThemeId());
			}

			return;
		}

		File themeZip = new File(zipWriter.getPath() + "/theme.zip");

		ZipWriter themeZipWriter = ZipWriterFactoryUtil.getZipWriter(themeZip);

		themeZipWriter.addEntry("liferay-look-and-feel.xml", lookAndFeelXML);

		File cssPath = null;
		File imagesPath = null;
		File javaScriptPath = null;
		File templatesPath = null;

		if (!theme.isLoadFromServletContext()) {
			ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
				servletContextName);

			if (themeLoader == null) {
				_log.error(
					servletContextName + " does not map to a theme loader");
			}
			else {
				File file = themeLoader.getFileStorage();

				String realPath =
					file.getPath() + StringPool.SLASH + theme.getName();

				cssPath = new File(realPath + "/css");
				imagesPath = new File(realPath + "/images");
				javaScriptPath = new File(realPath + "/javascript");
				templatesPath = new File(realPath + "/templates");
			}
		}
		else {
			cssPath = new File(servletContext.getRealPath(theme.getCssPath()));
			imagesPath = new File(
				servletContext.getRealPath(theme.getImagesPath()));
			javaScriptPath = new File(
				servletContext.getRealPath(theme.getJavaScriptPath()));
			templatesPath = new File(
				servletContext.getRealPath(theme.getTemplatesPath()));
		}

		exportThemeFiles("css", cssPath, themeZipWriter);
		exportThemeFiles("images", imagesPath, themeZipWriter);
		exportThemeFiles("javascript", javaScriptPath, themeZipWriter);
		exportThemeFiles("templates", templatesPath, themeZipWriter);
	}

	protected void exportThemeFiles(String path, File dir, ZipWriter zipWriter)
		throws Exception {

		if ((dir == null) || !dir.exists()) {
			return;
		}

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				exportThemeFiles(
					path + StringPool.SLASH + file.getName(), file, zipWriter);
			}
			else {
				zipWriter.addEntry(
					path + StringPool.SLASH + file.getName(),
					FileUtil.getBytes(file));
			}
		}
	}

	protected boolean[] getExportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		boolean exportPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean exportPortletSetupAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP_ALL);

		if (_log.isDebugEnabled()) {
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
			_log.debug("Export portlet setup " + exportPortletSetup);
		}

		boolean exportCurPortletData = exportPortletData;
		boolean exportCurPortletSetup = exportPortletSetup;

		// If PORTLET_DATA_ALL is true, this means that staging has just been
		// activated and all data and setup must be exported. There is no
		// portlet export control to check in this case.

		if (exportPortletDataAll) {
			exportCurPortletData = true;
			exportCurPortletSetup = true;
		}
		else {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			if (portlet != null) {
				String portletDataHandlerClass =
					portlet.getPortletDataHandlerClass();

				// Checking if the portlet has a data handler, if it doesn't,
				// the default values are the ones set in PORTLET_DATA and
				// PORTLET_SETUP. If it has a data handler, iterate over each
				// portlet export control.

				if (portletDataHandlerClass != null) {
					String rootPortletId = PortletConstants.getRootPortletId(
						portletId);

					// PORTLET_DATA and the PORTLET_DATA for this specific data
					// handler must be true

					exportCurPortletData =
						exportPortletData &&
						MapUtil.getBoolean(
							parameterMap,
							PortletDataHandlerKeys.PORTLET_DATA +
								StringPool.UNDERLINE + rootPortletId);

					// PORTLET_SETUP and the PORTLET_SETUP for this specific
					// data handler must be true

					exportCurPortletSetup =
						exportPortletSetup &&
						MapUtil.getBoolean(
							parameterMap,
							PortletDataHandlerKeys.PORTLET_SETUP +
								StringPool.UNDERLINE + rootPortletId);
				}
			}
		}

		if (exportPortletSetupAll ||
			(exportPortletSetup && type.equals("layout-prototype"))) {

			exportCurPortletSetup = true;
		}

		return new boolean[] {exportCurPortletData, exportCurPortletSetup};
	}

	protected String getLayoutIconPath(
		PortletDataContext portletDataContext, Layout layout, Image image) {

		StringBundler sb = new StringBundler(5);

		sb.append(
			ExportImportPathUtil.getLayoutPath(
				portletDataContext, layout.getPlid()));
		sb.append("/icons/");
		sb.append(image.getImageId());
		sb.append(StringPool.PERIOD);
		sb.append(image.getType());

		return sb.toString();
	}

	protected String getLayoutSetPrototype(
		PortletDataContext portletDataContext, String layoutSetPrototypeUuid) {

		StringBundler sb = new StringBundler(3);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/layout-set-prototype/");
		sb.append(layoutSetPrototypeUuid);

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutExporter.class);

	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();

}