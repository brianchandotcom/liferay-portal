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

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionServiceUtil;
import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class QuantityInputTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			_allowedCartQuantity = StringPool.BLANK;
			_maxCartQuantity =
				CommerceConstants.
					CP_DEFINITION_INVENTORY_DEFAULT_MAX_CART_QUANTITY;
			_minCartQuantity =
				CommerceConstants.
					CP_DEFINITION_INVENTORY_DEFAULT_MIN_CART_QUANTITY;
			_multipleCartQuantity =
				CommerceConstants.
					CP_DEFINITION_INVENTORY_DEFAULT_MULTIPLE_CART_QUANTITY;

			CPDefinitionInventory cpDefinitionInventory =
				CPDefinitionInventoryServiceUtil.
					fetchCPDefinitionInventoryByCPDefinitionId(_cpDefinitionId);

			if (cpDefinitionInventory != null) {
				_allowedCartQuantity =
					cpDefinitionInventory.getAllowedCartQuantities();
				_maxCartQuantity = cpDefinitionInventory.getMaxCartQuantity();
				_minCartQuantity = cpDefinitionInventory.getMinCartQuantity();
				_multipleCartQuantity =
					cpDefinitionInventory.getMultipleCartQuantity();
			}

			_cpDefinition = CPDefinitionServiceUtil.getCPDefinition(
				_cpDefinitionId);

			if (_value == 0) {
				_value = _minCartQuantity;
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

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setUseSelect(boolean useSelect) {
		_useSelect = useSelect;
	}

	public void setValue(int value) {
		_value = value;
	}

	@Override
	protected void cleanUp() {
		_allowedCartQuantity = null;
		_cpDefinition = null;
		_cpDefinitionId = 0;
		_maxCartQuantity = 0;
		_minCartQuantity = 0;
		_multipleCartQuantity = 0;
		_useSelect = true;
		_value = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce:quantity-input:allowedCartQuantity",
			_allowedCartQuantity);
		request.setAttribute(
			"liferay-commerce:quantity-input:cpDefinition", _cpDefinition);
		request.setAttribute(
			"liferay-commerce:quantity-input:maxCartQuantity",
			_maxCartQuantity);
		request.setAttribute(
			"liferay-commerce:quantity-input:minCartQuantity",
			_minCartQuantity);
		request.setAttribute(
			"liferay-commerce:quantity-input:multipleCartQuantity",
			_multipleCartQuantity);
		request.setAttribute(
			"liferay-commerce:quantity-input:useSelect", _useSelect);
		request.setAttribute("liferay-commerce:quantity-input:value", _value);
	}

	private static final String _PAGE = "/quantity_input/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		QuantityInputTag.class);

	private String _allowedCartQuantity;
	private CPDefinition _cpDefinition;
	private long _cpDefinitionId;
	private int _maxCartQuantity;
	private int _minCartQuantity;
	private int _multipleCartQuantity;
	private boolean _useSelect = true;
	private int _value;

}