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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

import java.io.File;

import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class ExportImportUtil {

	public static String exportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content)
		throws Exception {

		return getExportImport().exportContentReferences(
			portletDataContext, entityStagedModel, entityElement, content);
	}

	public static String exportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content)
		throws Exception {

		return getExportImport().exportDLReferences(
			portletDataContext, entityStagedModel, entityElement, content);
	}

	public static String exportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return getExportImport().exportLayoutReferences(
			portletDataContext, content);
	}

	public static String exportLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return getExportImport().exportLinksToLayouts(
			portletDataContext, content);
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

	public static String importContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception {

		return getExportImport().importContentReferences(
			portletDataContext, entityElement, content);
	}

	public static String importDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception {

		return getExportImport().importDLReferences(
			portletDataContext, entityElement, content);
	}

	public static String importLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return getExportImport().importLayoutReferences(
			portletDataContext, content);
	}

	public static String importLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return getExportImport().importLinksToLayouts(
			portletDataContext, content);
	}

	public static List<MissingReference> validateMissingReferences(
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