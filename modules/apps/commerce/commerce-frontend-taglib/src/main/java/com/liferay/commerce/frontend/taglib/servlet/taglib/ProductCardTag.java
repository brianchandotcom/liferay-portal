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

import com.liferay.commerce.frontend.model.CPContentListEntryModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 */
public class ProductCardTag extends IncludeTag {

	public CPContentListEntryModel getCpContentListEntryModel() {
		return _cpContentListEntryModel;
	}

	public String getElementClasses() {
		return _elementClasses;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(
			httpServletRequest, "cpContentListEntryModel",
			_cpContentListEntryModel);
		setNamespacedAttribute(
			httpServletRequest, "elementClasses", _elementClasses);
	}

	public void setCpContentListEntryModel(CPContentListEntryModel model) {
		_cpContentListEntryModel = model;
	}

	public void setElementClasses(String elementClasses) {
		_elementClasses = elementClasses;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpContentListEntryModel = null;
		_elementClasses = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:product-card:";

	private static final String _PAGE = "/product_card/page.jsp";

	private CPContentListEntryModel _cpContentListEntryModel;
	private String _elementClasses;

}