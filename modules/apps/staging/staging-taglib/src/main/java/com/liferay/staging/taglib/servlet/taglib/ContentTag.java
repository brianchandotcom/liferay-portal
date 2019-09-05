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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Levente Hudák
 */
public class ContentTag extends IncludeTag {

	public String getCmd() {
		return _cmd;
	}

	public long getExportImportConfigurationId() {
		return _exportImportConfigurationId;
	}

	public String getType() {
		return _type;
	}

	public boolean isDisableInputs() {
		return _disableInputs;
	}

	public boolean isShowAllPortlets() {
		return _showAllPortlets;
	}

	public void setCmd(String cmd) {
		_cmd = cmd;
	}

	public void setDisableInputs(boolean disableInputs) {
		_disableInputs = disableInputs;
	}

	public void setExportImportConfigurationId(
		long exportImportConfigurationId) {

		_exportImportConfigurationId = exportImportConfigurationId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowAllPortlets(boolean showAllPortlets) {
		_showAllPortlets = showAllPortlets;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cmd = null;
		_disableInputs = false;
		_exportImportConfigurationId = 0;
		_showAllPortlets = false;
		_type = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("liferay-staging:content:cmd", _cmd);
		httpServletRequest.setAttribute(
			"liferay-staging:content:disableInputs", _disableInputs);
		httpServletRequest.setAttribute(
			"liferay-staging:content:exportImportConfigurationId",
			_exportImportConfigurationId);
		httpServletRequest.setAttribute(
			"liferay-staging:content:showAllPortlets", _showAllPortlets);
		httpServletRequest.setAttribute("liferay-staging:content:type", _type);
	}

	private static final String _PAGE = "/content/page.jsp";

	private String _cmd;
	private boolean _disableInputs;
	private long _exportImportConfigurationId;
	private boolean _showAllPortlets;
	private String _type;

}