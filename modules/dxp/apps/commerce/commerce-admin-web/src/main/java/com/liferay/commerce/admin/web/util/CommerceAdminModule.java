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

package com.liferay.commerce.admin.web.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommerceAdminModule {

	public void deleteData(PortletDataContext portletDataContext)
		throws Exception;

	public void exportData(
			String namespace, PortletDataContext portletDataContext)
		throws Exception;

	public List<StagedModelType> getDeletionSystemEventStagedModelTypes();

	public List<PortletDataHandlerControl> getExportControls(String namespace);

	public String getLabel(Locale locale);

	public PortletURL getSearchURL(
		RenderRequest renderRequest, RenderResponse renderResponse);

	public void importData(
			String namespace, PortletDataContext portletDataContext)
		throws Exception;

	public boolean isVisible(HttpServletRequest httpServletRequest)
		throws PortalException;

	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws Exception;

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException;

}