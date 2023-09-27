/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.controller;

import com.liferay.exportimport.kernel.controller.ExportController;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Matthew Tambara
 */
public interface PortletExportController extends ExportController {

	public void exportAssetLinks(PortletDataContext portletDataContext)
		throws Exception;

	public void exportLocks(PortletDataContext portletDataContext)
		throws Exception;

	public void exportPortlet(
			PortletDataContext portletDataContext, long plid,
			Element parentElement, boolean exportPermissions,
			boolean exportPortletArchivedSetups, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletUserPreferences)
		throws Exception;

	public void exportService(
			PortletDataContext portletDataContext, Element rootElement,
			boolean exportServiceSetup)
		throws Exception;

}