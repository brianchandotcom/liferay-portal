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

package com.liferay.info.taglib.servlet.taglib;

import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Guilherme Camacho
 */
public class InfoListBasicTableTag extends IncludeTag {

	public List<String> getInfoListObjectColumnNames() {
		return _infoListObjectColumnNames;
	}

	public List<?> getInfoListObjects() {
		return _infoListObjects;
	}

	public String getItemRendererKey() {
		return _itemRendererKey;
	}

	public void setInfoListObjectColumnNames(
		List<String> infoListObjectColumnNames) {

		_infoListObjectColumnNames = infoListObjectColumnNames;
	}

	public void setInfoListObjects(List<?> infoListObjects) {
		_infoListObjects = infoListObjects;
	}

	public void setItemRendererKey(String itemRendererKey) {
		_itemRendererKey = itemRendererKey;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_infoListObjectColumnNames = null;
		_infoListObjects = null;
		_itemRendererKey = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected ServletContext getServletContext() {
		return ServletContextUtil.getServletContext();
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-info:info-list-basic-table:infoItemRenderer",
			_getInfoItemRenderer());
		httpServletRequest.setAttribute(
			"liferay-info:info-list-basic-table:infoListObjectColumnNames",
			getInfoListObjectColumnNames());
		httpServletRequest.setAttribute(
			"liferay-info:info-list-basic-table:infoListObjects",
			getInfoListObjects());
	}

	private InfoItemRenderer<?> _getInfoItemRenderer() {
		InfoItemRendererTracker infoItemRendererTracker =
			ServletContextUtil.getInfoItemRendererTracker();

		return infoItemRendererTracker.getInfoItemRenderer(
			getItemRendererKey());
	}

	private static final String _PAGE = "/info_list_basic_table/page.jsp";

	private List<String> _infoListObjectColumnNames;
	private List<?> _infoListObjects;
	private String _itemRendererKey;

}