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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Borkuti
 */
public class ProcessDateTag extends IncludeTag {

	public Date getDate() {
		return _date;
	}

	public String getLabelKey() {
		return _labelKey;
	}

	public boolean isListView() {
		return _listView;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public void setLabelKey(String labelKey) {
		_labelKey = labelKey;
	}

	public void setListView(boolean listView) {
		_listView = listView;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_date = null;
		_labelKey = StringPool.BLANK;
		_listView = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:process-date:date", _date);
		httpServletRequest.setAttribute(
			"liferay-staging:process-date:labelKey", _labelKey);
		httpServletRequest.setAttribute(
			"liferay-staging:process-date:listView", _listView);
	}

	private static final String _PAGE = "/process_date/page.jsp";

	private Date _date;
	private String _labelKey = StringPool.BLANK;
	private boolean _listView;

}