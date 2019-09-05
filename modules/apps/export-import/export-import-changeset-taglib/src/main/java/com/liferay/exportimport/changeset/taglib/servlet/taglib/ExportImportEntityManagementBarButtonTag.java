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

package com.liferay.exportimport.changeset.taglib.servlet.taglib;

import com.liferay.exportimport.changeset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Akos Thurzo
 */
public class ExportImportEntityManagementBarButtonTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		_searchContainerId = GetterUtil.getString(
			request.getAttribute(
				"liferay-frontend:management-bar:searchContainerId"));

		return super.doStartTag();
	}

	public String getCmd() {
		return _cmd;
	}

	public String getSearchContainerMappingId() {
		return _searchContainerMappingId;
	}

	public void setCmd(String cmd) {
		_cmd = cmd;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSearchContainerMappingId(String searchContainerMappingId) {
		_searchContainerMappingId = searchContainerMappingId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cmd = StringPool.BLANK;
		_searchContainerId = StringPool.BLANK;
		_searchContainerMappingId =
			ExportImportEntitySearchContainerMappingTag.
				DEFAULT_SEARCH_CONTAINER_MAPPING_ID;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:" +
				"export-import-entity-management-bar-button:cmd",
			_cmd);
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:" +
				"export-import-entity-management-bar-button:searchContainerId",
			_searchContainerId);
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:" +
				"export-import-entity-management-bar-button:" +
					"searchContainerMappingId",
			_searchContainerMappingId);
	}

	private static final String _PAGE =
		"/export_import_entity_management_bar_button/page.jsp";

	private String _cmd = StringPool.BLANK;
	private String _searchContainerId = StringPool.BLANK;
	private String _searchContainerMappingId =
		ExportImportEntitySearchContainerMappingTag.
			DEFAULT_SEARCH_CONTAINER_MAPPING_ID;

}