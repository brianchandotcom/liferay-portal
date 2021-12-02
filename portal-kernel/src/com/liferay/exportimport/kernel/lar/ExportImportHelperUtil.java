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

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.File;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Zsolt Berentey
 */
public class ExportImportHelperUtil {

	public static long[] getAllLayoutIds(long groupId, boolean privateLayout) {
		return _exportImportHelper.getAllLayoutIds(groupId, privateLayout);
	}

	public static Map<Long, Boolean> getAllLayoutIdsMap(
		long groupId, boolean privateLayout) {

		return _exportImportHelper.getAllLayoutIdsMap(groupId, privateLayout);
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

	public static String getExportableRootPortletId(
			long companyId, String portletId)
		throws Exception {

		return _exportImportHelper.getExportableRootPortletId(
			companyId, portletId);
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

	public static long getLayoutModelDeletionCount(
			PortletDataContext portletDataContext, boolean privateLayout)
		throws PortalException {

		return _exportImportHelper.getLayoutModelDeletionCount(
			portletDataContext, privateLayout);
	}

	public static Layout getLayoutOrCreateDummyRootLayout(long plid)
		throws PortalException {

		return _exportImportHelper.getLayoutOrCreateDummyRootLayout(plid);
	}

	public static ZipWriter getLayoutSetZipWriter(long groupId) {
		return _exportImportHelper.getLayoutSetZipWriter(groupId);
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
			PortletDataContext portletDataContext,
			StagedModelType stagedModelType)
		throws PortalException {

		return _exportImportHelper.getModelDeletionCount(
			portletDataContext, stagedModelType);
	}

	public static String getPortletExportFileName(Portlet portlet) {
		return _exportImportHelper.getPortletExportFileName(portlet);
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

	public static boolean isAlwaysIncludeReference(
		PortletDataContext portletDataContext,
		StagedModel referenceStagedModel) {

		return _exportImportHelper.isAlwaysIncludeReference(
			portletDataContext, referenceStagedModel);
	}

	public static boolean isAlwaysIncludeReference(
		PortletDataContext portletDataContext, StagedModel referenceStagedModel,
		String rootPortletId) {

		return _exportImportHelper.isAlwaysIncludeReference(
			portletDataContext, referenceStagedModel, rootPortletId);
	}

	public static boolean isExportPortletData(
		PortletDataContext portletDataContext) {

		return _exportImportHelper.isExportPortletData(portletDataContext);
	}

	public static boolean isLayoutRevisionInReview(Layout layout) {
		return _exportImportHelper.isLayoutRevisionInReview(layout);
	}

	public static boolean isPublishDisplayedContent(
		PortletDataContext portletDataContext, Portlet portlet) {

		return _exportImportHelper.isPublishDisplayedContent(
			portletDataContext, portlet);
	}

	public static boolean isReferenceWithinExportScope(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		return _exportImportHelper.isReferenceWithinExportScope(
			portletDataContext, stagedModel);
	}

	public static void processBackgroundTaskManifestSummary(
			long userId, long sourceGroupId, BackgroundTask backgroundTask,
			File file)
		throws PortalException {

		_exportImportHelper.processBackgroundTaskManifestSummary(
			userId, sourceGroupId, backgroundTask, file);
	}

	public static void setPortletScope(
		PortletDataContext portletDataContext, Element portletElement) {

		_exportImportHelper.setPortletScope(portletDataContext, portletElement);
	}

	public static MissingReferences validateMissingReferences(
			PortletDataContext portletDataContext)
		throws Exception {

		return _exportImportHelper.validateMissingReferences(
			portletDataContext);
	}

	public static void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {

		_exportImportHelper.writeManifestSummary(document, manifestSummary);
	}

	private static volatile ExportImportHelper _exportImportHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			ExportImportHelper.class, ExportImportHelperUtil.class,
			"_exportImportHelper", false);

}