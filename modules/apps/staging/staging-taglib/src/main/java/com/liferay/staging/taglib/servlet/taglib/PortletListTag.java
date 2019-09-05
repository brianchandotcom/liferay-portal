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

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Levente Hudák
 */
public class PortletListTag extends IncludeTag {

	public long getExportImportConfigurationId() {
		return _exportImportConfigurationId;
	}

	public List<Portlet> getPortlets() {
		return _portlets;
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

	public void setPortlets(List<Portlet> portlets) {
		_portlets = portlets;
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

		_disableInputs = false;
		_exportImportConfigurationId = 0;
		_portlets = null;
		_showAllPortlets = false;
		_type = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:portlet-list:disableInputs", _disableInputs);
		httpServletRequest.setAttribute(
			"liferay-staging:portlet-list:exportImportConfigurationId",
			_exportImportConfigurationId);
		httpServletRequest.setAttribute(
			"liferay-staging:portlet-list:portlets", _portlets);
		httpServletRequest.setAttribute(
			"liferay-staging:portlet-list:showAllPortlets", _showAllPortlets);
		httpServletRequest.setAttribute(
			"liferay-staging:portlet-list:type", _type);
	}

	private static final String _PAGE = "/portlet_list/page.jsp";

	private boolean _disableInputs;
	private long _exportImportConfigurationId;
	private List<Portlet> _portlets;
	private boolean _showAllPortlets;
	private String _type;

}