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

import com.liferay.petra.string.StringPool;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Máté Thurzó
 */
public class PermissionsTag extends IncludeTag {

	public String getAction() {
		return _action;
	}

	public String getDescriptionCSSClass() {
		return _descriptionCSSClass;
	}

	public long getExportImportConfigurationId() {
		return _exportImportConfigurationId;
	}

	public String getLabelCSSClass() {
		return _labelCSSClass;
	}

	public boolean isDisableInputs() {
		return _disableInputs;
	}

	public boolean isGlobal() {
		return _global;
	}

	public void setAction(String action) {
		if (action == null) {
			_action = StringPool.BLANK;
		}
		else {
			_action = action;
		}
	}

	public void setDescriptionCSSClass(String descriptionCSSClass) {
		if (descriptionCSSClass == null) {
			_descriptionCSSClass = StringPool.BLANK;
		}
		else {
			_descriptionCSSClass = descriptionCSSClass;
		}
	}

	public void setDisableInputs(boolean disableInputs) {
		_disableInputs = disableInputs;
	}

	public void setExportImportConfigurationId(
		long exportImportConfigurationId) {

		_exportImportConfigurationId = exportImportConfigurationId;
	}

	public void setGlobal(boolean global) {
		_global = global;
	}

	public void setLabelCSSClass(String labelCSSClass) {
		if (labelCSSClass == null) {
			_labelCSSClass = StringPool.BLANK;
		}
		else {
			_labelCSSClass = labelCSSClass;
		}
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_action = StringPool.BLANK;
		_descriptionCSSClass = StringPool.BLANK;
		_disableInputs = false;
		_exportImportConfigurationId = 0;
		_global = false;
		_labelCSSClass = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:deletions:global", _global);
		httpServletRequest.setAttribute(
			"liferay-staging:permissions:action", _action);
		httpServletRequest.setAttribute(
			"liferay-staging:permissions:descriptionCSSClass",
			_descriptionCSSClass);
		httpServletRequest.setAttribute(
			"liferay-staging:permissions:disableInputs", _disableInputs);
		httpServletRequest.setAttribute(
			"liferay-staging:permissions:exportImportConfigurationId",
			_exportImportConfigurationId);
		httpServletRequest.setAttribute(
			"liferay-staging:permissions:labelCSSClass", _labelCSSClass);
	}

	private static final String _PAGE = "/permissions/page.jsp";

	private String _action = StringPool.BLANK;
	private String _descriptionCSSClass = StringPool.BLANK;
	private boolean _disableInputs;
	private long _exportImportConfigurationId;
	private boolean _global;
	private String _labelCSSClass = StringPool.BLANK;

}