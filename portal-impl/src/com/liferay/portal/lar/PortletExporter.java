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

import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
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
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.util.xml.DocUtil;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;

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

		if (portletDataHandler == null) {
			return;
		}

		String portletId = portlet.getPortletId();

		Group liveGroup = layout.getGroup();

		if (liveGroup.isStagingGroup()) {
			liveGroup = liveGroup.getLiveGroup();
		}

		boolean staged = liveGroup.isStagedPortlet(portlet.getRootPortletId());

		if (!staged && ExportImportThreadLocal.isLayoutExportInProcess()) {
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

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		long lastPublishDate = GetterUtil.getLong(
			jxPortletPreferences.getValue(
				"last-publish-date", StringPool.BLANK));

		Date startDate = portletDataContext.getStartDate();

		if ((lastPublishDate > 0) && (startDate != null) &&
			(lastPublishDate < startDate.getTime())) {

			portletDataContext.setStartDate(new Date(lastPublishDate));
		}

		String data = null;

		long groupId = portletDataContext.getGroupId();

		portletDataContext.setGroupId(portletDataContext.getScopeGroupId());

		try {
			data = portletDataHandler.exportData(
				portletDataContext, portletId, jxPortletPreferences);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			portletDataContext.setGroupId(groupId);
			portletDataContext.setStartDate(startDate);
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

		Date endDate = portletDataContext.getEndDate();

		if (endDate != null) {
			try {
				jxPortletPreferences.setValue(
					"last-publish-date", String.valueOf(endDate.getTime()));

				jxPortletPreferences.store();
			}
			catch (UnsupportedOperationException uoe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Not updating the portlet setup for " + portletId +
							" because no setup was returned for the current " +
								"page");
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Deprecated
	public byte[] exportPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		File file = exportPortletInfoAsFile(
			plid, groupId, portletId, parameterMap, startDate, endDate);

		try {
			return FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			file.delete();
		}
	}

	public File exportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		try {
			ExportImportThreadLocal.setPortletExportInProcess(true);

			return doExportPortletInfoAsFile(
				plid, groupId, portletId, parameterMap, startDate, endDate);
		}
		finally {
			ExportImportThreadLocal.setPortletExportInProcess(false);
		}
	}

	protected File doExportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportPortletConfiguration = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION);
		boolean exportPortletConfigurationAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL);
		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (exportPortletDataAll) {
			exportPortletData = true;
		}
		else if (parameterMap.containsKey(
					PortletDataHandlerKeys.PORTLET_DATA + "_" +
						rootPortletId)) {

			exportPortletData = MapUtil.getBoolean(
				parameterMap,
				PortletDataHandlerKeys.PORTLET_DATA + "_" + rootPortletId);
		}

		boolean exportPortletArchivedSetups = exportPortletConfiguration;
		boolean exportPortletSetup = exportPortletConfiguration;
		boolean exportPortletUserPreferences = exportPortletConfiguration;

		if (exportPortletConfigurationAll) {
			exportPortletArchivedSetups =
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL);
			exportPortletSetup =
				MapUtil.getBoolean(
					parameterMap, PortletDataHandlerKeys.PORTLET_SETUP_ALL);
			exportPortletUserPreferences =
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL);
		}
		else if (parameterMap.containsKey(
					PortletDataHandlerKeys.PORTLET_CONFIGURATION + "_" +
						rootPortletId)) {

			exportPortletConfiguration =
				exportPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_CONFIGURATION +
						StringPool.UNDERLINE + rootPortletId);

			exportPortletArchivedSetups =
				exportPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
						StringPool.UNDERLINE + rootPortletId);
			exportPortletSetup =
				exportPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_SETUP +
						StringPool.UNDERLINE + rootPortletId);
			exportPortletUserPreferences =
				exportPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES +
						StringPool.UNDERLINE + rootPortletId);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
			_log.debug(
				"Export portlet archived setups " +
					exportPortletArchivedSetups);
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
			_log.debug(
				"Export portlet configuration " + exportPortletConfiguration);
			_log.debug(
				"Export portlet user preferences " +
					exportPortletUserPreferences);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (!layout.isTypeControlPanel() && !layout.isTypePanel() &&
			!layout.isTypePortlet()) {

			throw new LayoutImportException(
				"Layout type " + layout.getType() + " is not valid");
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			layout.getCompanyId());

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		long scopeGroupId = groupId;

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

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

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				layout.getCompanyId(), scopeGroupId, parameterMap, startDate,
				endDate, zipWriter);

		portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(portletDataContext));

		portletDataContext.setPlid(plid);
		portletDataContext.setOldPlid(plid);
		portletDataContext.setScopeType(scopeType);
		portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

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
			"private-layout", String.valueOf(layout.isPrivateLayout()));
		headerElement.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		exportPortlet(
			portletDataContext, layoutCache, portletId, layout, rootElement,
			defaultUserId, exportPermissions, exportPortletArchivedSetups,
			exportPortletData, exportPortletSetup,
			exportPortletUserPreferences);

		exportAssetCategories(portletDataContext);
		exportAssetLinks(portletDataContext);
		exportAssetTags(portletDataContext);
		exportComments(portletDataContext);
		exportExpandoTables(portletDataContext);
		exportLocks(portletDataContext);

		_deletionSystemEventExporter.export(portletDataContext);

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(
				portletDataContext);
		}

		exportRatingsEntries(portletDataContext, rootElement);

		ExportImportHelperUtil.writeManifestSummary(
			document, portletDataContext.getManifestSummary());

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet took " + stopWatch.getTime() + " ms");
		}

		portletDataContext.clearScopedPrimaryKeys();

		try {
			portletDataContext.addZipEntry(
				"/manifest.xml", document.formattedString());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return zipWriter.getFile();
	}

	protected void exportAssetCategories(PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("categories-hierarchy");

		exportAssetCategories(portletDataContext, rootElement);

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/categories-hierarchy.xml",
			document.formattedString());
	}

	protected void exportAssetCategories(
			PortletDataContext portletDataContext, Element rootElement)
		throws Exception {

		Element assetsElement = rootElement.element("assets");

		if (assetsElement == null) {
			assetsElement = rootElement.addElement("assets");
		}

		Element assetCategoriesElement = rootElement.element("categories");

		if (assetCategoriesElement == null) {
			assetCategoriesElement = rootElement.addElement("categories");
		}

		Element assetVocabulariesElement = rootElement.element("vocabularies");

		if (assetVocabulariesElement == null) {
			assetVocabulariesElement = rootElement.addElement("vocabularies");
		}

		Map<String, String[]> assetCategoryUuidsMap =
			portletDataContext.getAssetCategoryUuidsMap();

		for (Map.Entry<String, String[]> entry :
				assetCategoryUuidsMap.entrySet()) {

			String[] assetCategoryEntryParts = StringUtil.split(
				entry.getKey(), CharPool.POUND);

			String className = assetCategoryEntryParts[0];
			String classPK = assetCategoryEntryParts[1];

			Element assetElement = assetsElement.addElement("asset");

			assetElement.addAttribute("class-name", className);
			assetElement.addAttribute("class-pk", classPK);
			assetElement.addAttribute(
				"category-uuids", StringUtil.merge(entry.getValue()));

			List<AssetCategory> assetCategories =
				AssetCategoryLocalServiceUtil.getCategories(
					className, GetterUtil.getLong(classPK));

			for (AssetCategory assestCategory : assetCategories) {
				exportAssetCategory(
					portletDataContext, assetVocabulariesElement,
					assetCategoriesElement, assestCategory);
			}
		}
	}

	protected void exportAssetCategory(
			PortletDataContext portletDataContext,
			Element assetVocabulariesElement, Element assetCategoriesElement,
			AssetCategory assetCategory)
		throws Exception {

		exportAssetVocabulary(
			portletDataContext, assetVocabulariesElement,
			assetCategory.getVocabularyId());

		if (assetCategory.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			exportAssetCategory(
				portletDataContext, assetVocabulariesElement,
				assetCategoriesElement, assetCategory.getParentCategoryId());
		}

		String path = getAssetCategoryPath(
			portletDataContext, assetCategory.getCategoryId());

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element assetCategoryElement = assetCategoriesElement.addElement(
			"category");

		if (assetCategory.getGroupId() ==
				portletDataContext.getCompanyGroupId()) {

			assetCategoryElement.addAttribute("global", "true");
		}

		assetCategoryElement.addAttribute("path", path);

		assetCategory.setUserUuid(assetCategory.getUserUuid());

		portletDataContext.addZipEntry(path, assetCategory);

		List<AssetCategoryProperty> assetCategoryProperties =
			AssetCategoryPropertyLocalServiceUtil.getCategoryProperties(
				assetCategory.getCategoryId());

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			Element propertyElement = assetCategoryElement.addElement(
				"property");

			propertyElement.addAttribute(
				"userUuid", assetCategoryProperty.getUserUuid());
			propertyElement.addAttribute("key", assetCategoryProperty.getKey());
			propertyElement.addAttribute(
				"value", assetCategoryProperty.getValue());
		}

		portletDataContext.addPermissions(
			AssetCategory.class, assetCategory.getCategoryId());
	}

	protected void exportAssetCategory(
			PortletDataContext portletDataContext,
			Element assetVocabulariesElement, Element assetCategoriesElement,
			long assetCategoryId)
		throws Exception {

		AssetCategory assetCategory = AssetCategoryUtil.fetchByPrimaryKey(
			assetCategoryId);

		if (assetCategory != null) {
			exportAssetCategory(
				portletDataContext, assetVocabulariesElement,
				assetCategoriesElement, assetCategory);
		}
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

				if (!portletDataContext.isPathNotProcessed(path)) {
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

	protected void exportAssetTag(
			PortletDataContext portletDataContext, AssetTag assetTag,
			Element assetTagsElement)
		throws PortalException, SystemException {

		String path = getAssetTagPath(portletDataContext, assetTag.getTagId());

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element assetTagElement = assetTagsElement.addElement("tag");

		assetTagElement.addAttribute("path", path);

		assetTag.setUserUuid(assetTag.getUserUuid());

		portletDataContext.addZipEntry(path, assetTag);

		List<AssetTagProperty> assetTagProperties =
			AssetTagPropertyLocalServiceUtil.getTagProperties(
				assetTag.getTagId());

		for (AssetTagProperty assetTagProperty : assetTagProperties) {
			Element propertyElement = assetTagElement.addElement("property");

			propertyElement.addAttribute("key", assetTagProperty.getKey());
			propertyElement.addAttribute("value", assetTagProperty.getValue());
		}

		portletDataContext.addPermissions(AssetTag.class, assetTag.getTagId());
	}

	protected void exportAssetTags(PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("tags");

		Map<String, String[]> assetTagNamesMap =
			portletDataContext.getAssetTagNamesMap();

		if (assetTagNamesMap.isEmpty()) {
			return;
		}

		for (Map.Entry<String, String[]> entry : assetTagNamesMap.entrySet()) {
			String[] assetTagNameParts = StringUtil.split(
				entry.getKey(), CharPool.POUND);

			String className = assetTagNameParts[0];
			String classPK = assetTagNameParts[1];

			Element assetElement = rootElement.addElement("asset");

			assetElement.addAttribute("class-name", className);
			assetElement.addAttribute("class-pk", classPK);
			assetElement.addAttribute(
				"tags", StringUtil.merge(entry.getValue()));

			List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(
				className, GetterUtil.getLong(classPK));

			for (AssetTag assetTag : assetTags) {
				exportAssetTag(portletDataContext, assetTag, rootElement);
			}
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) + "/tags.xml",
			document.formattedString());
	}

	protected void exportAssetVocabulary(
			PortletDataContext portletDataContext,
			Element assetVocabulariesElement, AssetVocabulary assetVocabulary)
		throws Exception {

		String path = getAssetVocabulariesPath(
			portletDataContext, assetVocabulary.getVocabularyId());

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element assetVocabularyElement = assetVocabulariesElement.addElement(
			"vocabulary");

		if (assetVocabulary.getGroupId() ==
				portletDataContext.getCompanyGroupId()) {

			assetVocabularyElement.addAttribute("global", "true");
		}

		assetVocabularyElement.addAttribute("path", path);

		assetVocabulary.setUserUuid(assetVocabulary.getUserUuid());

		portletDataContext.addZipEntry(path, assetVocabulary);

		portletDataContext.addPermissions(
			AssetVocabulary.class, assetVocabulary.getVocabularyId());
	}

	protected void exportAssetVocabulary(
			PortletDataContext portletDataContext,
			Element assetVocabulariesElement, long assetVocabularyId)
		throws Exception {

		AssetVocabulary assetVocabulary = AssetVocabularyUtil.findByPrimaryKey(
			assetVocabularyId);

		exportAssetVocabulary(
			portletDataContext, assetVocabulariesElement, assetVocabulary);
	}

	protected void exportComments(PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("comments");

		Map<String, List<MBMessage>> commentsMap =
			portletDataContext.getComments();

		for (Map.Entry<String, List<MBMessage>> entry :
				commentsMap.entrySet()) {

			String[] commentParts = StringUtil.split(
				entry.getKey(), CharPool.POUND);

			String className = commentParts[0];
			String classPK = commentParts[1];

			String commentsPath = getCommentsPath(
				portletDataContext, className, classPK);

			Element assetElement = rootElement.addElement("asset");

			assetElement.addAttribute("path", commentsPath);
			assetElement.addAttribute("class-name", className);
			assetElement.addAttribute("class-pk", classPK);

			List<MBMessage> mbMessages = entry.getValue();

			for (MBMessage mbMessage : mbMessages) {
				String commentPath = getCommentPath(
					portletDataContext, className, classPK, mbMessage);

				if (portletDataContext.isPathNotProcessed(commentPath)) {
					User user = UserLocalServiceUtil.fetchUser(
						mbMessage.getUserId());

					if (user == null) {
						continue;
					}

					portletDataContext.addZipEntry(commentPath, mbMessage);

					MBDiscussion mbDiscussion =
						MBDiscussionLocalServiceUtil.getDiscussion(
							className, GetterUtil.getLong(classPK));

					portletDataContext.addReferenceElement(
						mbDiscussion, assetElement, user,
						PortletDataContext.REFERENCE_TYPE_WEAK, true);
				}
			}
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/comments.xml",
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

			if (portletDataContext.isPathNotProcessed(path)) {
				portletDataContext.addZipEntry(path, lock);
			}
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) + "/locks.xml",
			document.formattedString());
	}

	protected void exportPortlet(
			PortletDataContext portletDataContext, LayoutCache layoutCache,
			String portletId, Layout layout, Element parentElement,
			long defaultUserId, boolean exportPermissions,
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
			portletDataContext.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not export portlet " + portletId +
						" because the portlet does not exist");
			}

			return;
		}

		if (!portlet.isInstanceable() &&
			!portlet.isPreferencesUniquePerLayout() &&
			portletDataContext.hasNotUniquePerLayout(portletId)) {

			return;
		}

		Document document = SAXReaderUtil.createDocument();

		Element portletElement = document.addElement("portlet");

		portletElement.addAttribute("portlet-id", portletId);
		portletElement.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));
		portletElement.addAttribute("old-plid", String.valueOf(plid));
		portletElement.addAttribute(
			"scope-layout-type", portletDataContext.getScopeType());
		portletElement.addAttribute(
			"scope-layout-uuid", portletDataContext.getScopeLayoutUuid());
		portletElement.addAttribute(
			"private-layout", String.valueOf(layout.isPrivateLayout()));

		// Data

		if (exportPortletData) {
			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.getStrictPortletSetup(
					layout, portletId);

			if (!portlet.isPreferencesUniquePerLayout()) {
				StringBundler sb = new StringBundler(5);

				sb.append(portletId);
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
			exportPortletPreferences(
				portletDataContext, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false, layout, plid,
				portletId, portletElement);

			exportPortletPreferences(
				portletDataContext, portletDataContext.getScopeGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, false, layout, plid,
				portletId, portletElement);

			exportPortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, false, layout, plid,
				portletId, portletElement);
		}

		// Portlet preferences

		if (exportPortletUserPreferences) {
			List<PortletPreferences> portletPreferencesList =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					PortletKeys.PREFS_OWNER_TYPE_USER, plid, portletId);

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
					plid, portletId, portletElement);
			}

			try {
				PortletPreferences groupPortletPreferences =
					PortletPreferencesLocalServiceUtil.getPortletPreferences(
						portletDataContext.getScopeGroupId(),
						PortletKeys.PREFS_OWNER_TYPE_GROUP,
						PortletKeys.PREFS_PLID_SHARED, portletId);

				exportPortletPreference(
					portletDataContext, portletDataContext.getScopeGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
					groupPortletPreferences, portletId,
					PortletKeys.PREFS_PLID_SHARED, portletElement);
			}
			catch (NoSuchPortletPreferencesException nsppe) {
			}
		}

		// Archived setups

		if (exportPortletArchivedSetups) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			List<PortletItem> portletItems =
				PortletItemLocalServiceUtil.getPortletItems(
					portletDataContext.getGroupId(), rootPortletId,
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
				portletDataContext, layoutCache, portletId, layout,
				portletElement);
		}

		// Zip

		StringBundler pathSB = new StringBundler(4);

		pathSB.append(
			ExportImportPathUtil.getPortletPath(portletDataContext, portletId));
		pathSB.append(StringPool.SLASH);
		pathSB.append(plid);
		pathSB.append("/portlet.xml");

		String path = pathSB.toString();

		Element element = parentElement.addElement("portlet");

		element.addAttribute("portlet-id", portletId);
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

		if (portletDataContext.isPathNotProcessed(path)) {
			try {
				portletDataContext.addZipEntry(
					path, document.formattedString());
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe.getMessage());
				}
			}

			portletDataContext.addPrimaryKey(String.class, path);
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

		if (portlet != null) {
			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if (portletDataHandler != null) {
				jxPortletPreferences =
					portletDataHandler.processExportPortletPreferences(
						portletDataContext, portletId, jxPortletPreferences,
						parentElement);
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

		String path = getPortletPreferencesPath(
			portletDataContext, portletId, ownerId, ownerType, plid);

		Element portletPreferencesElement = parentElement.addElement(
			"portlet-preferences");

		portletPreferencesElement.addAttribute("path", path);

		if (portletDataContext.isPathNotProcessed(path)) {
			portletDataContext.addZipEntry(
				path, document.formattedString(StringPool.TAB, false, false));
		}
	}

	protected void exportPortletPreferences(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			boolean defaultUser, Layout layout, long plid, String portletId,
			Element parentElement)
		throws Exception {

		PortletPreferences portletPreferences = null;

		try {
			if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
				(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) ||
				(ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED)) {

				portletPreferences =
					PortletPreferencesLocalServiceUtil.getPortletPreferences(
						ownerId, ownerType, PortletKeys.PREFS_OWNER_ID_DEFAULT,
						portletId);
			}
			else {
				portletPreferences =
					PortletPreferencesLocalServiceUtil.getPortletPreferences(
						ownerId, ownerType, plid, portletId);
			}
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

	protected void exportRatingsEntries(
			PortletDataContext portletDataContext, Element parentElement)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("ratings");

		Map<String, List<RatingsEntry>> ratingsEntriesMap =
			portletDataContext.getRatingsEntries();

		for (Map.Entry<String, List<RatingsEntry>> entry :
				ratingsEntriesMap.entrySet()) {

			String[] ratingsEntryParts = StringUtil.split(
				entry.getKey(), CharPool.POUND);

			String className = ratingsEntryParts[0];
			String classPK = ratingsEntryParts[1];

			String ratingsEntriesPath = getRatingsEntriesPath(
				portletDataContext, className, classPK);

			Element assetElement = rootElement.addElement("asset");

			assetElement.addAttribute("path", ratingsEntriesPath);
			assetElement.addAttribute("class-name", className);
			assetElement.addAttribute("class-pk", classPK);

			List<RatingsEntry> ratingsEntries = entry.getValue();

			for (RatingsEntry ratingsEntry : ratingsEntries) {
				String ratingsEntryPath = getRatingsEntryPath(
					portletDataContext, className, classPK, ratingsEntry);

				User user = UserLocalServiceUtil.fetchUser(
					ratingsEntry.getUserId());

				if (user == null) {
					continue;
				}

				portletDataContext.addZipEntry(ratingsEntryPath, ratingsEntry);

				portletDataContext.addReferenceElement(
					ratingsEntry, assetElement, user, User.class,
					PortletDataContext.REFERENCE_TYPE_WEAK, true);
			}
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/ratings.xml",
			document.formattedString());
	}

	protected String getAssetCategoryPath(
		PortletDataContext portletDataContext, long assetCategoryId) {

		StringBundler sb = new StringBundler(4);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/categories/");
		sb.append(assetCategoryId);
		sb.append(".xml");

		return sb.toString();
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

	protected String getAssetTagPath(
		PortletDataContext portletDataContext, long assetCategoryId) {

		StringBundler sb = new StringBundler(4);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/tags/");
		sb.append(assetCategoryId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getAssetVocabulariesPath(
		PortletDataContext portletDataContext, long assetVocabularyId) {

		StringBundler sb = new StringBundler(4);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/vocabularies/");
		sb.append(assetVocabularyId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getCommentPath(
		PortletDataContext portletDataContext, String className, String classPK,
		MBMessage mbMessage) {

		StringBundler sb = new StringBundler(8);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/comments/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(mbMessage.getMessageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getCommentsPath(
		PortletDataContext portletDataContext, String className,
		String classPK) {

		StringBundler sb = new StringBundler(6);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/comments/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);

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

	protected String getPortletDataPath(
		PortletDataContext portletDataContext, String portletId) {

		String portletPath = ExportImportPathUtil.getPortletPath(
			portletDataContext, portletId);

		return portletPath + "/portlet-data.xml";
	}

	protected String getPortletPreferencesPath(
		PortletDataContext portletDataContext, String portletId, long ownerId,
		int ownerType, long plid) {

		StringBundler sb = new StringBundler(8);

		sb.append(
			ExportImportPathUtil.getPortletPath(portletDataContext, portletId));
		sb.append("/preferences/");

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) {
			sb.append("company/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
			sb.append("group/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
			sb.append("layout/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			sb.append("user/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			sb.append("archived/");
		}

		sb.append(ownerId);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(plid);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append("portlet-preferences.xml");

		return sb.toString();
	}

	protected String getRatingsEntriesPath(
		PortletDataContext portletDataContext, String className,
		String classPK) {

		StringBundler sb = new StringBundler(6);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/ratings/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);

		return sb.toString();
	}

	protected String getRatingsEntryPath(
		PortletDataContext portletDataContext, String className, String classPK,
		RatingsEntry ratingsEntry) {

		StringBundler sb = new StringBundler(8);

		sb.append(ExportImportPathUtil.getRootPath(portletDataContext));
		sb.append("/ratings/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(ratingsEntry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(PortletExporter.class);

	private DeletionSystemEventExporter _deletionSystemEventExporter =
		new DeletionSystemEventExporter();
	private PermissionExporter _permissionExporter = new PermissionExporter();

}