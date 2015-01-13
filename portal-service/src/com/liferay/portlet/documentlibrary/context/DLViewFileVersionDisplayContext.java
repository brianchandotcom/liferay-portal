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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public interface DLViewFileVersionDisplayContext extends DLDisplayContext {

	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException;

	public List<DDMStructure> getDDMStructures() throws PortalException;

	public List<MenuItem> getMenuItems() throws PortalException;

	public List<ToolbarItem> getToolbarItems() throws PortalException;

	public boolean isEditButtonVisible() throws PortalException;

	public boolean isMoveButtonVisible() throws PortalException;

	public boolean isMoveToTheRecycleBinButtonVisible() throws PortalException;

	public boolean isOpenInMsOfficeButtonVisible() throws PortalException;

	public boolean isPermissionsButtonVisible() throws PortalException;

	public boolean isVersionInfoVisible() throws PortalException;

	public boolean isViewButtonVisible() throws PortalException;

	public boolean isViewOriginalFileButtonVisible() throws PortalException;

	public void renderPreview(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException;

	boolean isDownloadLinkVisible() throws PortalException;
}