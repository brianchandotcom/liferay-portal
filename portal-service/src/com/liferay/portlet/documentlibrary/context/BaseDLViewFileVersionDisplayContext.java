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
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.IOException;

import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class BaseDLViewFileVersionDisplayContext
	extends BaseDLDisplayContext<DLViewFileVersionDisplayContext>
	implements DLViewFileVersionDisplayContext {

	public BaseDLViewFileVersionDisplayContext(
		UUID uuid, DLViewFileVersionDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		super(uuid, parentDLDisplayContext, request, response);

		this.fileVersion = fileVersion;
	}

	@Override
	public List<DDMStructure> getDDMStructures() throws PortalException {
		return parentDLDisplayContext.getDDMStructures();
	}

	@Override
	public Fields getFields(DDMStructure ddmStructure) throws PortalException {
		return parentDLDisplayContext.getFields(ddmStructure);
	}

	@Override
	public List<MenuItem> getMenuItems() throws PortalException {
		return parentDLDisplayContext.getMenuItems();
	}

	@Override
	public List<ToolbarItem> getToolbarItems() throws PortalException {
		return parentDLDisplayContext.getToolbarItems();
	}

	@Override
	public boolean isAssetMetadataVisible() throws PortalException {
		return parentDLDisplayContext.isAssetMetadataVisible();
	}

	@Override
	public boolean isCancelCheckoutDocumentButtonVisible()
		throws PortalException {

		return parentDLDisplayContext.isCancelCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isCheckinButtonVisible() throws PortalException {
		return parentDLDisplayContext.isCheckinButtonVisible();
	}

	@Override
	public boolean isCheckoutDocumentButtonVisible() throws PortalException {
		return parentDLDisplayContext.isCheckoutDocumentButtonVisible();
	}

	@Override
	public boolean isDeleteButtonVisible() throws PortalException {
		return parentDLDisplayContext.isDeleteButtonVisible();
	}

	@Override
	public boolean isDownloadButtonVisible() throws PortalException {
		return parentDLDisplayContext.isDownloadButtonVisible();
	}

	@Override
	public boolean isEditButtonVisible() throws PortalException {
		return parentDLDisplayContext.isEditButtonVisible();
	}

	@Override
	public boolean isMoveButtonVisible() throws PortalException {
		return parentDLDisplayContext.isMoveButtonVisible();
	}

	@Override
	public boolean isMoveToTheRecycleBinButtonVisible() throws PortalException {
		return parentDLDisplayContext.isMoveToTheRecycleBinButtonVisible();
	}

	@Override
	public boolean isOpenInMsOfficeButtonVisible() throws PortalException {
		return parentDLDisplayContext.isOpenInMsOfficeButtonVisible();
	}

	@Override
	public boolean isPermissionsButtonVisible() throws PortalException {
		return parentDLDisplayContext.isPermissionsButtonVisible();
	}

	@Override
	public boolean isViewButtonVisible() throws PortalException {
		return parentDLDisplayContext.isViewButtonVisible();
	}

	@Override
	public boolean isViewOriginalFileButtonVisible() throws PortalException {
		return parentDLDisplayContext.isViewOriginalFileButtonVisible();
	}

	@Override
	public void renderPreview(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		parentDLDisplayContext.renderPreview(request, response);
	}

	protected FileVersion fileVersion;

}