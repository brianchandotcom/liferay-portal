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

package com.liferay.portlet.exportimport.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.File;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Zsolt Berentey
 */
@ProviderType
public class ExportImportHelperUtil {

	public static long[] getAllLayoutIds(long groupId, boolean privateLayout) {
		return _exportImportHelper.getAllLayoutIds(groupId, privateLayout);
	}

	public static Map<Long, Boolean> getAllLayoutIdsMap(
		long groupId, boolean privateLayout) {

		return _exportImportHelper.getAllLayoutIdsMap(groupId, privateLayout);
	}

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             ExportImportDateUtil#getCalendar(PortletRequest, String,
	 *             boolean)}
	 */
	@Deprecated
	public static Calendar getCalendar(
		PortletRequest portletRequest, String paramPrefix,
		boolean timeZoneSensitive) {

		return _exportImportHelper.getCalendar(
			portletRequest, paramPrefix, timeZoneSensitive);
	}

	public static List<Portlet> getDataSiteLevelPortlets(long companyId)
		throws Exception {

		return _exportImportHelper.getDataSiteLevelPortlets(companyId);
	}

	public static List<Portlet> getDataSiteLevelPortlets(
			long companyId, boolean excludeDataAlwaysStaged)
		throws Exception {

		return _exportImportHelper.getDataSiteLevelPortlets(
			companyId, excludeDataAlwaysStaged);
	}

	/**
	 * @deprecated As of 7.0.0, moved to {@link
	 *             ExportImportDateUtil#getDateRange(PortletRequest, long,
	 *             boolean, long, String, String)}
	 */
	@Deprecated
	public static DateRange getDateRange(
			PortletRequest portletRequest, long groupId, boolean privateLayout,
			long plid, String portletId, String defaultRange)
		throws Exception {

		return _exportImportHelper.getDateRange(
			portletRequest, groupId, privateLayout, plid, portletId,
			defaultRange);
	}

	public static Layout getExportableLayout(ThemeDisplay themeDisplay)
		throws PortalException {

		return _exportImportHelper.getExportableLayout(themeDisplay);
	}

	public static String getExportableRootPortletId(
			long companyId, String portletId)
		throws Exception {

		return _exportImportHelper.getExportableRootPortletId(
			companyId, portletId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExportPortletControlsMap(long, String, Map)}
	 */
	@Deprecated
	public static boolean[] getExportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return _exportImportHelper.getExportPortletControls(
			companyId, portletId, parameterMap);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getExportPortletControlsMap(long, String, Map, String)}
	 */
	@Deprecated
	public static boolean[] getExportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		return _exportImportHelper.getExportPortletControls(
			companyId, portletId, parameterMap, type);
	}

	public static Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return _exportImportHelper.getExportPortletControlsMap(
			companyId, portletId, parameterMap);
	}

	public static Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		return _exportImportHelper.getExportPortletControlsMap(
			companyId, portletId, parameterMap, type);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getImportPortletControlsMap(long, String, Map, Element,
	 *             ManifestSummary)}
	 */
	@Deprecated
	public static boolean[] getImportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement)
		throws Exception {

		return _exportImportHelper.getImportPortletControls(
			companyId, portletId, parameterMap, portletDataElement);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getImportPortletControlsMap(long, String, Map, Element,
	 *             ManifestSummary)}
	 */
	@Deprecated
	public static boolean[] getImportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement,
			ManifestSummary manifestSummary)
		throws Exception {

		return _exportImportHelper.getImportPortletControls(
			companyId, portletId, parameterMap, portletDataElement,
			manifestSummary);
	}

	public static Map<String, Boolean> getImportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement,
			ManifestSummary manifestSummary)
		throws Exception {

		return _exportImportHelper.getImportPortletControlsMap(
			companyId, portletId, parameterMap, portletDataElement,
			manifestSummary);
	}

	public static Map<Long, Boolean> getLayoutIdMap(
			PortletRequest portletRequest)
		throws PortalException {

		return _exportImportHelper.getLayoutIdMap(portletRequest);
	}

	public static long[] getLayoutIds(List<Layout> layouts) {
		return _exportImportHelper.getLayoutIds(layouts);
	}

	public static long[] getLayoutIds(Map<Long, Boolean> layoutIdMap)
		throws PortalException {

		return _exportImportHelper.getLayoutIds(layoutIdMap);
	}

	public static long[] getLayoutIds(
			Map<Long, Boolean> layoutIdMap, long targetGroupId)
		throws PortalException {

		return _exportImportHelper.getLayoutIds(layoutIdMap, targetGroupId);
	}

	public static long[] getLayoutIds(PortletRequest portletRequest)
		throws PortalException {

		return _exportImportHelper.getLayoutIds(portletRequest);
	}

	public static long[] getLayoutIds(
			PortletRequest portletRequest, long targetGroupId)
		throws PortalException {

		return _exportImportHelper.getLayoutIds(portletRequest, targetGroupId);
	}

	public static ZipWriter getLayoutSetZipWriter(long groupId) {
		return _exportImportHelper.getLayoutSetZipWriter(groupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getManifestSummary(PortletDataContext)}
	 */
	@Deprecated
	public static ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		return _exportImportHelper.getManifestSummary(
			userId, groupId, parameterMap, file);
	}

	public static ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			FileEntry fileEntry)
		throws Exception {

		return _exportImportHelper.getManifestSummary(
			userId, groupId, parameterMap, fileEntry);
	}

	public static ManifestSummary getManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		return _exportImportHelper.getManifestSummary(portletDataContext);
	}

	public static List<Layout> getMissingParentLayouts(
			Layout layout, long liveGroupId)
		throws PortalException {

		return _exportImportHelper.getMissingParentLayouts(layout, liveGroupId);
	}

	public static long getModelDeletionCount(
			final PortletDataContext portletDataContext,
			final StagedModelType stagedModelType)
		throws PortalException {

		return _exportImportHelper.getModelDeletionCount(
			portletDataContext, stagedModelType);
	}

	public static ZipWriter getPortletZipWriter(String portletId) {
		return _exportImportHelper.getPortletZipWriter(portletId);
	}

	public static String getSelectedLayoutsJSON(
		long groupId, boolean privateLayout, String selectedNodes) {

		return _exportImportHelper.getSelectedLayoutsJSON(
			groupId, privateLayout, selectedNodes);
	}

	public static FileEntry getTempFileEntry(
			long groupId, long userId, String folderName)
		throws PortalException {

		return _exportImportHelper.getTempFileEntry(
			groupId, userId, folderName);
	}

	public static UserIdStrategy getUserIdStrategy(
			long userId, String userIdStrategy)
		throws PortalException {

		return _exportImportHelper.getUserIdStrategy(userId, userIdStrategy);
	}

	public static boolean isReferenceWithinExportScope(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		return _exportImportHelper.isReferenceWithinExportScope(
			portletDataContext, stagedModel);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessorr#replaceExportContentReferences(
	 *             PortletDataContext, StagedModel, String, boolean, boolean) in
	 *             the export-import-api module
	 */
	@Deprecated
	public static String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceExportContentReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceExportContentReferences(
	 *             PortletDataContext, StagedModel, String, boolean, boolean) in
	 *             the export-import-api module
	 */
	@Deprecated
	public static String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceExportContentReferences(
			portletDataContext, entityStagedModel, content,
			exportReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessorr#replaceExportContentReferences(
	 *             PortletDataContext, StagedModel, String, boolean, boolean) in
	 *             the export-import-api module
	 */
	@Deprecated
	public static String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		return _exportImportHelper.replaceExportContentReferences(
			portletDataContext, entityStagedModel, content,
			exportReferencedContent, escapeContent);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceExportDLReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceExportDLReferences(
			portletDataContext, entityStagedModel, content,
			exportReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return _exportImportHelper.replaceExportLayoutReferences(
			portletDataContext, content);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean exportReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceExportLayoutReferences(
			portletDataContext, content, exportReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceExportLinksToLayouts(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return _exportImportHelper.replaceExportLinksToLayouts(
			portletDataContext, entityStagedModel, content);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceImportContentReferences(
	 *             PortletDataContext, StagedModel, String) in the
	 *             export-import-api module
	 */
	@Deprecated
	public static String replaceImportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceImportContentReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceImportContentReferences(
	 *             PortletDataContext, StagedModel, String) in the
	 *             export-import-api module
	 */
	@Deprecated
	public static String replaceImportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return _exportImportHelper.replaceImportContentReferences(
			portletDataContext, entityStagedModel, content);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceImportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceImportDLReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceImportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return _exportImportHelper.replaceImportDLReferences(
			portletDataContext, entityStagedModel, content);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return _exportImportHelper.replaceImportLayoutReferences(
			portletDataContext, content);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceImportLayoutReferences(
			portletDataContext, content, importReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return _exportImportHelper.replaceImportLinksToLayouts(
			portletDataContext, content);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return _exportImportHelper.replaceImportLinksToLayouts(
			portletDataContext, content, importReferencedContent);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             DefaultConfigurationPortletDataHandler#updateExportPortletPreferencesClassPKs(
	 *             PortletDataContext, Portlet, PortletPreferences, String,
	 *             String)}
	 */
	@Deprecated
	public static void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className)
		throws Exception {

		_exportImportHelper.updateExportPortletPreferencesClassPKs(
			portletDataContext, portlet, portletPreferences, key, className);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #updateExportPortletPreferencesClassPKs(PortletDataContext,
	 *             Portlet, PortletPreferences, String, String)}
	 */
	@Deprecated
	public static void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className,
			Element rootElement)
		throws Exception {

		_exportImportHelper.updateExportPortletPreferencesClassPKs(
			portletDataContext, portlet, portletPreferences, key, className,
			rootElement);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link
	 *             DefaultConfigurationPortletDataHandler#updateImportPortletPreferencesClassPKs(
	 *             PortletDataContext, PortletPreferences, String, Class, long)}
	 */
	@Deprecated
	public static void updateImportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key, Class<?> clazz,
			long companyGroupId)
		throws Exception {

		_exportImportHelper.updateImportPortletPreferencesClassPKs(
			portletDataContext, portletPreferences, key, clazz, companyGroupId);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #validateMissingReferences(PortletDataContext)}
	 */
	@Deprecated
	public static MissingReferences validateMissingReferences(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		return _exportImportHelper.validateMissingReferences(
			userId, groupId, parameterMap, file);
	}

	public static MissingReferences validateMissingReferences(
			final PortletDataContext portletDataContext)
		throws Exception {

		return _exportImportHelper.validateMissingReferences(
			portletDataContext);
	}

	public static void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {

		_exportImportHelper.writeManifestSummary(document, manifestSummary);
	}

	private static final ExportImportHelper _exportImportHelper =
		ProxyFactory.newServiceTrackedInstance(ExportImportHelper.class);

}