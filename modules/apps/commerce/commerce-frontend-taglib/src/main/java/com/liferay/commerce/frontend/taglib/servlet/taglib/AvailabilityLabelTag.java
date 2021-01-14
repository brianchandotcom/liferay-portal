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
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 */
public class AvailabilityLabelTag extends IncludeTag {

	public int getStockQuantity() {
		return _stockQuantity;
	}

	public boolean isLowStock() {
		return _lowStock;
	}

	public boolean isWillUpdate() {
		return _willUpdate;
	}

	@Override
	public void setAttributes(HttpServletRequest httpServletRequest) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(httpServletRequest, "lowStock", _lowStock);
		setNamespacedAttribute(
			httpServletRequest, "stockQuantity", _stockQuantity);
		setNamespacedAttribute(httpServletRequest, "willUpdate", _willUpdate);
	}

	public void setLowStock(boolean lowStock) {
		_lowStock = lowStock;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setStockQuantity(int stockQuantity) {
		_stockQuantity = stockQuantity;
	}

	public void setWillUpdate(boolean willUpdate) {
		_willUpdate = willUpdate;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_lowStock = false;
		_stockQuantity = 0;
		_willUpdate = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-commerce:availability-label:";

	private static final String _PAGE = "/availability_label/page.jsp";

	private boolean _lowStock;
	private int _stockQuantity;
	private boolean _willUpdate;

}