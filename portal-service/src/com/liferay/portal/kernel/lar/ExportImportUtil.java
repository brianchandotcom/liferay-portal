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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.cal.DateRange;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

import java.io.File;

import java.util.Calendar;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Zsolt Berentey
 */
public class ExportImportUtil {

	public static Calendar getDate(
		PortletRequest portletRequest, String paramPrefix,
		boolean timeZoneSensitive) {

		return getExportImport().getDate(
			portletRequest, paramPrefix, timeZoneSensitive);
	}

	public static DateRange getDateRange(
			PortletRequest portletRequest, long groupId, long plid,
			String portletId, boolean privateLayout, boolean timeZoneSensitive)
		throws Exception {

		return getExportImport().getDateRange(
			portletRequest, groupId, plid, portletId, privateLayout,
			timeZoneSensitive);
	}

	public static ExportImport getExportImport() {
		PortalRuntimePermission.checkGetBeanProperty(ExportImportUtil.class);

		return _exportImport;
	}

	public static ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		return getExportImport().getManifestSummary(
			userId, groupId, parameterMap, file);
	}

	public static String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return getExportImport().replaceExportContentReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	public static String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return getExportImport().replaceExportDLReferences(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	public static String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean exportReferencedContent)
		throws Exception {

		return getExportImport().replaceExportLayoutReferences(
			portletDataContext, content, exportReferencedContent);
	}

	public static String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return getExportImport().replaceExportLinksToLayouts(
			portletDataContext, entityStagedModel, entityElement, content,
			exportReferencedContent);
	}

	public static String replaceImportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return getExportImport().replaceImportContentReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);
	}

	public static String replaceImportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return getExportImport().replaceImportDLReferences(
			portletDataContext, entityElement, content,
			importReferencedContent);
	}

	public static String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return getExportImport().replaceImportLayoutReferences(
			portletDataContext, content, importReferencedContent);
	}

	public static String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return getExportImport().replaceImportLinksToLayouts(
			portletDataContext, content, importReferencedContent);
	}

	public static Map<String, MissingReference> validateMissingReferences(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		return getExportImport().validateMissingReferences(
			userId, groupId, parameterMap, file);
	}

	public static void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {

		getExportImport().writeManifestSummary(document, manifestSummary);
	}

	public void setExportImport(ExportImport exportImport) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_exportImport = exportImport;
	}

	private static ExportImport _exportImport;

}