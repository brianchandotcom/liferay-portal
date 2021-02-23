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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 */
public class ProductCardTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_productDetailURL = _cpContentHelper.getFriendlyURL(
				_cpCatalogEntry, themeDisplay);

			CPSku cpSku = _cpContentHelper.getDefaultCPSku(_cpCatalogEntry);

			if (cpSku != null) {
				_sku = cpSku.getSku();
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return super.doStartTag();
	}

	public CPCatalogEntry getCpCatalogEntry() {
		return _cpCatalogEntry;
	}

	public String getElementClasses() {
		return _elementClasses;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(
			httpServletRequest, "cpCatalogEntry", _cpCatalogEntry);
		setNamespacedAttribute(
			httpServletRequest, "elementClasses", _elementClasses);
		setNamespacedAttribute(
			httpServletRequest, "productDetailURL", _productDetailURL);
		setNamespacedAttribute(httpServletRequest, "sku", _sku);
	}

	public void setCpCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setElementClasses(String elementClasses) {
		_elementClasses = elementClasses;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		_cpContentHelper = ServletContextUtil.getCPContentHelper();
		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpCatalogEntry = null;
		_cpContentHelper = null;
		_elementClasses = null;
		_productDetailURL = null;
		_sku = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:product-card:";

	private static final String _PAGE = "/product_card/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(ProductCardTag.class);

	private CPCatalogEntry _cpCatalogEntry;
	private CPContentHelper _cpContentHelper;
	private String _elementClasses;
	private String _productDetailURL;
	private String _sku;

}