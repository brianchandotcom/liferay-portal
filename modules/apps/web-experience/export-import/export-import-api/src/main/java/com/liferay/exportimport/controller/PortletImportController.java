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

package com.liferay.exportimport.controller;

import com.liferay.exportimport.kernel.controller.ImportController;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Matthew Tambara
 */
public interface PortletImportController extends ImportController {

	public void deletePortletData(PortletDataContext portletDataContext)
		throws Exception;

	public void importAssetLinks(PortletDataContext portletDataContext)
		throws Exception;

	public void importPortletData(
			PortletDataContext portletDataContext, Element portletDataElement)
		throws Exception;

	public void importPortletPreferences(
			PortletDataContext portletDataContext, long companyId, long groupId,
			Layout layout, Element parentElement, boolean preserveScopeLayoutId,
			boolean importPortletArchivedSetups, boolean importPortletData,
			boolean importPortletSetup, boolean importPortletUserPreferences)
		throws Exception;

	public void importServicePortletPreferences(
			PortletDataContext portletDataContext, Element serviceElement)
		throws PortalException;

	public void readExpandoTables(PortletDataContext portletDataContext)
		throws Exception;

	public void readLocks(PortletDataContext portletDataContext)
		throws Exception;

	public void resetPortletScope(
		PortletDataContext portletDataContext, long groupId);

}