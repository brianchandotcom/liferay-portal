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

package com.liferay.commerce.product.taglib.servlet.taglib;

import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductListRendererTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		if (Validator.isNull(_key)) {
			return SKIP_BODY;
		}

		_cpContentListRenderer =
			cpContentListRendererRegistry.getCPContentListRenderer(_key);

		return super.doStartTag();
	}

	public CPDataSourceResult getCPDataSourceResult() {
		return _cpDataSourceResult;
	}

	public Map<String, String> getEntryKeys() {
		return _entryKeys;
	}

	public String getKey() {
		return _key;
	}

	public void setCPDataSourceResult(CPDataSourceResult cpDataSourceResult) {
		_cpDataSourceResult = cpDataSourceResult;
	}

	public void setEntryKeys(Map<String, String> entryKeys) {
		_entryKeys = entryKeys;
	}

	public void setKey(String key) {
		_key = key;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		cpContentHelper = ServletContextUtil.getCPContentHelper();
		cpContentListRendererRegistry =
			ServletContextUtil.getCPContentListRendererRegistry();
		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpContentListRenderer = null;
		_cpDataSourceResult = null;
		_entryKeys = null;
		_key = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce-product:product-list-renderer:cpContentHelper",
			cpContentHelper);
		request.setAttribute(
			"liferay-commerce-product:product-list-renderer:" +
				"cpContentListRenderer",
			_cpContentListRenderer);
		request.setAttribute(
			"liferay-commerce-product:product-list-renderer:cpDataSourceResult",
			_cpDataSourceResult);
		request.setAttribute(
			"liferay-commerce-product:product-list-renderer:entryKeys",
			_entryKeys);
	}

	protected CPContentHelper cpContentHelper;
	protected CPContentListRendererRegistry cpContentListRendererRegistry;

	private static final String _PAGE = "/product_list_renderer/page.jsp";

	private CPContentListRenderer _cpContentListRenderer;
	private CPDataSourceResult _cpDataSourceResult;
	private Map<String, String> _entryKeys;
	private String _key;

}