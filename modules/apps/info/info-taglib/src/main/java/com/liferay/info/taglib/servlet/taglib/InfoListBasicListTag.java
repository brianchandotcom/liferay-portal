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
import com.liferay.info.item.renderer.InfoItemRendererRegistry;
import com.liferay.info.taglib.internal.list.renderer.BasicListInfoListStyle;
import com.liferay.info.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Pavel Savinov
 */
public class InfoListBasicListTag extends IncludeTag {

	public List<? extends Object> getInfoListObjects() {
		return _infoListObjects;
	}

	public String getItemRendererKey() {
		return _itemRendererKey;
	}

	public String getListStyleKey() {
		return _listStyleKey;
	}

	public String getTemplateKey() {
		return _templateKey;
	}

	public void setInfoListObjects(List<? extends Object> infoListObjects) {
		_infoListObjects = infoListObjects;
	}

	public void setItemRendererKey(String itemRendererKey) {
		_itemRendererKey = itemRendererKey;
	}

	public void setListStyleKey(String listStyleKey) {
		if (Validator.isNull(listStyleKey)) {
			_listStyleKey = BasicListInfoListStyle.BORDERED.getKey();
		}
		else {
			_listStyleKey = listStyleKey;
		}
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
	}

	public void setTemplateKey(String templateKey) {
		_templateKey = templateKey;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_infoListObjects = null;
		_itemRendererKey = null;
		_listStyleKey = null;
		_templateKey = null;
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
			"liferay-info:info-list-grid:infoItemRenderer",
			_getInfoItemRenderer());
		httpServletRequest.setAttribute(
			"liferay-info:info-list-grid:infoListObjects",
			getInfoListObjects());
		httpServletRequest.setAttribute(
			"liferay-info:info-list-grid:listStyleKey", _listStyleKey);
		httpServletRequest.setAttribute(
			"liferay-info:info-list-grid:templateKey", _templateKey);
	}

	private InfoItemRenderer<?> _getInfoItemRenderer() {
		InfoItemRendererRegistry infoItemRendererRegistry =
			ServletContextUtil.getInfoItemRendererRegistry();

		return infoItemRendererRegistry.getInfoItemRenderer(
			getItemRendererKey());
	}

	private static final String _PAGE = "/info_list_basic_list/page.jsp";

	private List<? extends Object> _infoListObjects;
	private String _itemRendererKey;
	private String _listStyleKey;
	private String _templateKey;

}