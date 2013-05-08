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
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;

/**
 * @author Zsolt Berentey
 */
public class ExportImportUtil {

	public static String exportContentReferences(
			PortletDataContext portletDataContext, StagedModel entity,
			Element entityElement, String content)
		throws Exception {

		return getExportImport().exportContentReferences(
			portletDataContext, entity, entityElement, content);
	}

	public static String exportDLReferences(
			PortletDataContext portletDataContext, StagedModel entity,
			Element entityElement, String content)
		throws Exception {

		return getExportImport().exportDLReferences(
			portletDataContext, entity, entityElement, content);
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

	public void setExportImport(ExportImport exportImport) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_exportImport = exportImport;
	}

	private static ExportImport _exportImport;

}