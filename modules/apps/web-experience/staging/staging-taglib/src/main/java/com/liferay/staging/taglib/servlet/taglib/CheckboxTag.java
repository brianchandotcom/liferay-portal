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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringPool;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Peter Borkuti
 */
@ProviderType
public class CheckboxTag extends IncludeTag {

	public void setChecked(boolean checked) {
		_checked = checked;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSuggestion(String suggestion) {
		_suggestion = suggestion;
	}

	public void setWarning(String warning) {
		_warning = warning;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checked = false;
		_description = StringPool.BLANK;
		_disabled = false;
		_id = StringPool.BLANK;
		_label = StringPool.BLANK;
		_name = StringPool.BLANK;
		_suggestion = StringPool.BLANK;
		_warning = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-staging:checkbox:checked", _checked);
		request.setAttribute(
			"liferay-staging:checkbox:description", _description);
		request.setAttribute("liferay-staging:checkbox:disabled", _disabled);
		request.setAttribute("liferay-staging:checkbox:id", _id);
		request.setAttribute("liferay-staging:checkbox:label", _label);
		request.setAttribute("liferay-staging:checkbox:name", _name);
		request.setAttribute(
			"liferay-staging:checkbox:suggestion", _suggestion);
		request.setAttribute("liferay-staging:checkbox:warning", _warning);
	}

	private static final String _PAGE = "/checkbox/page.jsp";

	private boolean _checked;
	private String _description = StringPool.BLANK;
	private boolean _disabled;
	private String _id = StringPool.BLANK;
	private String _label = StringPool.BLANK;
	private String _name = StringPool.BLANK;
	private String _suggestion = StringPool.BLANK;
	private String _warning = StringPool.BLANK;

}