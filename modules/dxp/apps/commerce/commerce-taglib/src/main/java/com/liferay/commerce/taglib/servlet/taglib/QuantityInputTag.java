/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionServiceUtil;
import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
			_allowedOrderQuantity = StringPool.BLANK;
			_maxOrderQuantity =
				CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY;
			_minOrderQuantity =
				CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;
			_multipleOrderQuantity =
				CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY;

			CPDefinitionInventory cpDefinitionInventory =
				CPDefinitionInventoryServiceUtil.
					fetchCPDefinitionInventoryByCPDefinitionId(_cpDefinitionId);

			if (cpDefinitionInventory != null) {
				_allowedOrderQuantity =
					cpDefinitionInventory.getAllowedOrderQuantities();
				_maxOrderQuantity = cpDefinitionInventory.getMaxOrderQuantity();
				_minOrderQuantity = cpDefinitionInventory.getMinOrderQuantity();
				_multipleOrderQuantity =
					cpDefinitionInventory.getMultipleOrderQuantity();
			}

			_cpDefinition = CPDefinitionServiceUtil.getCPDefinition(
				_cpDefinitionId);

			if (_value == 0) {
				_value = _minOrderQuantity;
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

	public void setName(String name) {
		_name = name;
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
		super.cleanUp();

		_allowedOrderQuantity = null;
		_cpDefinition = null;
		_cpDefinitionId = 0;
		_maxOrderQuantity = 0;
		_minOrderQuantity = 0;
		_multipleOrderQuantity = 0;
		_name = null;
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
			"liferay-commerce:quantity-input:allowedOrderQuantity",
			_allowedOrderQuantity);
		request.setAttribute(
			"liferay-commerce:quantity-input:cpDefinition", _cpDefinition);
		request.setAttribute(
			"liferay-commerce:quantity-input:maxOrderQuantity",
			_maxOrderQuantity);
		request.setAttribute(
			"liferay-commerce:quantity-input:minOrderQuantity",
			_minOrderQuantity);
		request.setAttribute(
			"liferay-commerce:quantity-input:multipleOrderQuantity",
			_multipleOrderQuantity);
		request.setAttribute("liferay-commerce:quantity-input:name", _name);
		request.setAttribute(
			"liferay-commerce:quantity-input:useSelect", _useSelect);
		request.setAttribute("liferay-commerce:quantity-input:value", _value);
	}

	private static final String _PAGE = "/quantity_input/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		QuantityInputTag.class);

	private String _allowedOrderQuantity;
	private CPDefinition _cpDefinition;
	private long _cpDefinitionId;
	private int _maxOrderQuantity;
	private int _minOrderQuantity;
	private int _multipleOrderQuantity;
	private String _name;
	private boolean _useSelect = true;
	private int _value;

}