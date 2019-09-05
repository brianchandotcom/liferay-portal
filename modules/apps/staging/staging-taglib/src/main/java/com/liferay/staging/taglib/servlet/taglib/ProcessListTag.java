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
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Borkuti
 */
public class ProcessListTag extends IncludeTag {

	public String getEmptyResultsMessage() {
		return _emptyResultsMessage;
	}

	public String getLocalTaskExecutorClassName() {
		return _localTaskExecutorClassName;
	}

	public String getMvcRenderCommandName() {
		return _mvcRenderCommandName;
	}

	public String getRemoteTaskExecutorClassName() {
		return _remoteTaskExecutorClassName;
	}

	public ResultRowSplitter getResultRowSplitter() {
		return _resultRowSplitter;
	}

	public boolean isDeleteMenu() {
		return _deleteMenu;
	}

	public boolean isRelaunchMenu() {
		return _relaunchMenu;
	}

	public boolean isSummaryMenu() {
		return _summaryMenu;
	}

	public void setDeleteMenu(boolean deleteMenu) {
		_deleteMenu = deleteMenu;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	public void setLocalTaskExecutorClassName(
		String localTaskExecutorClassName) {

		_localTaskExecutorClassName = localTaskExecutorClassName;
	}

	public void setMvcRenderCommandName(String mvcRenderCommandName) {
		_mvcRenderCommandName = mvcRenderCommandName;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setRelaunchMenu(boolean relaunchMenu) {
		_relaunchMenu = relaunchMenu;
	}

	public void setRemoteTaskExecutorClassName(
		String remoteTaskExecutorClassName) {

		_remoteTaskExecutorClassName = remoteTaskExecutorClassName;
	}

	public void setResultRowSplitter(ResultRowSplitter resultRowSplitter) {
		_resultRowSplitter = resultRowSplitter;
	}

	public void setSummaryMenu(boolean summaryMenu) {
		_summaryMenu = summaryMenu;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_deleteMenu = true;
		_emptyResultsMessage = StringPool.BLANK;
		_localTaskExecutorClassName = StringPool.BLANK;
		_mvcRenderCommandName = StringPool.BLANK;
		_relaunchMenu = true;
		_remoteTaskExecutorClassName = StringPool.BLANK;
		_resultRowSplitter = null;
		_summaryMenu = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:deleteMenu", _deleteMenu);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:emptyResultsMessage",
			_emptyResultsMessage);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:localTaskExecutorClassName",
			_localTaskExecutorClassName);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:mvcRenderCommandName",
			_mvcRenderCommandName);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:relaunchMenu", _relaunchMenu);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:remoteTaskExecutorClassName",
			_remoteTaskExecutorClassName);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:resultRowSplitter",
			_resultRowSplitter);
		httpServletRequest.setAttribute(
			"liferay-staging:process-list:summaryMenu", _summaryMenu);
	}

	private static final String _PAGE = "/process_list/page.jsp";

	private boolean _deleteMenu = true;
	private String _emptyResultsMessage = StringPool.BLANK;
	private String _localTaskExecutorClassName = StringPool.BLANK;
	private String _mvcRenderCommandName = StringPool.BLANK;
	private boolean _relaunchMenu = true;
	private String _remoteTaskExecutorClassName = StringPool.BLANK;
	private ResultRowSplitter _resultRowSplitter;
	private boolean _summaryMenu = true;

}