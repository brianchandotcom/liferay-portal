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

package com.liferay.commerce.cart.taglib.servlet.taglib;

import com.liferay.commerce.cart.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Alessio Antonio Rendina
 */
public class QuantityControlTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			_commerceOrderItem = commerceOrderItemService.getCommerceOrderItem(
				_commerceOrderItemId);

			if (!_useSelect) {
				_updateOnChange = false;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	public boolean isShowInputLabel() {
		return _showInputLabel;
	}

	public boolean isUpdateOnChange() {
		return _updateOnChange;
	}

	public boolean isUseSelect() {
		return _useSelect;
	}

	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItemId = commerceOrderItemId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		commerceOrderItemService =
			ServletContextUtil.getCommerceOrderItemService();
		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowInputLabel(boolean showInputLabel) {
		_showInputLabel = showInputLabel;
	}

	public void setUpdateOnChange(boolean updateOnChange) {
		_updateOnChange = updateOnChange;
	}

	public void setUseSelect(boolean useSelect) {
		_useSelect = useSelect;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceOrderItem = null;
		_commerceOrderItemId = 0;
		_showInputLabel = false;
		_updateOnChange = true;
		_useSelect = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce-cart:quantity-control:commerceOrderItem",
			_commerceOrderItem);
		request.setAttribute(
			"liferay-commerce-cart:quantity-control:showInputLabel",
			_showInputLabel);
		request.setAttribute(
			"liferay-commerce-cart:quantity-control:updateOnChange",
			_updateOnChange);
		request.setAttribute(
			"liferay-commerce-cart:quantity-control:useSelect", _useSelect);
	}

	protected CommerceOrderItemService commerceOrderItemService;

	private static final String _PAGE = "/quantity_control/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		QuantityControlTag.class);

	private CommerceOrderItem _commerceOrderItem;
	private long _commerceOrderItemId;
	private boolean _showInputLabel;
	private boolean _updateOnChange = true;
	private boolean _useSelect = true;

}