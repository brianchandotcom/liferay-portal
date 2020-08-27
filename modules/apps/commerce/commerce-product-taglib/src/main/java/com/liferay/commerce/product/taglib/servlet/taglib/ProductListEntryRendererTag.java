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

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductListEntryRendererTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		if (_cpCatalogEntry == null) {
			return SKIP_BODY;
		}

		if (Validator.isNull(_key)) {
			Map<String, String> entryKeys =
				(Map<String, String>)request.getAttribute(
					CPContentWebKeys.CP_CONTENT_LIST_ENTRY_RENDERER_KEYS);

			_key = entryKeys.get(_cpCatalogEntry.getProductTypeName());
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpContentListEntryRenderer =
			cpContentListEntryRendererRegistry.getCPContentListEntryRenderer(
				_key, portletDisplay.getPortletName(),
				_cpCatalogEntry.getProductTypeName());

		return super.doStartTag();
	}

	public CPCatalogEntry getCPCatalogEntry() {
		return _cpCatalogEntry;
	}

	public String getKey() {
		return _key;
	}

	public void setCPCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setKey(String key) {
		_key = key;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		cpContentListEntryRendererRegistry =
			ServletContextUtil.getCPContentListEntryRendererRegistry();
		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpCatalogEntry = null;
		_cpContentListEntryRenderer = null;
		_key = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce-product:product-list-entry-renderer:" +
				"cpCatalogEntry",
			_cpCatalogEntry);
		request.setAttribute(
			"liferay-commerce-product:product-list-entry-renderer:" +
				"cpContentListEntryRenderer",
			_cpContentListEntryRenderer);
	}

	protected CPContentListEntryRendererRegistry
		cpContentListEntryRendererRegistry;

	private static final String _PAGE = "/product_list_entry_renderer/page.jsp";

	private CPCatalogEntry _cpCatalogEntry;
	private CPContentListEntryRenderer _cpContentListEntryRenderer;
	private String _key;

}