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

import com.liferay.portal.kernel.repository.model.Folder;

import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public class BaseDLViewDisplayContext
	extends BaseDLDisplayContext<DLViewDisplayContext>
	implements DLViewDisplayContext {

	public BaseDLViewDisplayContext(
		UUID uuid, DLViewDisplayContext parentDLDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		Folder folder) {

		super(uuid, parentDLDisplayContext, request, response);

		this.folder = folder;
	}

	@Override
	public String getDisplayStyle() {
		return parentDisplayContext.getDisplayStyle();
	}

	@Override
	public PortletURL getDisplayStyleURL() {
		return parentDisplayContext.getDisplayStyleURL();
	}

	@Override
	public Map<String, String> getOrderColumns() {
		return parentDisplayContext.getOrderColumns();
	}

	@Override
	public PortletURL getSortURL() {
		return parentDisplayContext.getSortURL();
	}

	protected Folder folder;

}