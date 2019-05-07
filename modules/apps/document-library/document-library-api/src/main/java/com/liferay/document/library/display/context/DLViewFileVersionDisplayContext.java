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

package com.liferay.document.library.display.context;

import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public interface DLViewFileVersionDisplayContext extends DLDisplayContext {

	public String getCssClassFileMimeType();

	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException;

	public DDMFormValues getDDMFormValues(long ddmStorageId)
		throws PortalException;

	public List<DDMStructure> getDDMStructures() throws PortalException;

	public int getDDMStructuresCount() throws PortalException;

	public String getDiscussionClassName();

	public long getDiscussionClassPK();

	public String getDiscussionLabel(Locale locale);

	public default String getIconFileMimeType() {
		return "document-default";
	}

	public Menu getMenu() throws PortalException;

	public List<ToolbarItem> getToolbarItems() throws PortalException;

	public default boolean hasCustomThumbnail() {
		return false;
	}

	public boolean hasPreview();

	public default boolean isActionsVisible() {
		return false;
	}

	public boolean isDownloadLinkVisible() throws PortalException;

	public default boolean isSharingLinkVisible() throws PortalException {
		return false;
	}

	public boolean isVersionInfoVisible() throws PortalException;

	public default void renderCustomThumbnail(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {
	}

	public void renderPreview(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException;

}