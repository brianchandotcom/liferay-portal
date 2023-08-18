/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.taglib.servlet.taglib;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryLocalServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.util.IncludeTag;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Luca Pellizzon
 */
public class QuantityInputTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		_allowedOrderQuantities = new int[0];
		_maxOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY.
				intValue();
		_minOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY.
				intValue();
		_multipleOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY.
				intValue();

		CPDefinitionInventory cpDefinitionInventory =
			CPDefinitionInventoryLocalServiceUtil.
				fetchCPDefinitionInventoryByCPDefinitionId(_cpDefinitionId);

		if (cpDefinitionInventory != null) {
			_allowedOrderQuantities = TransformUtil.transformToIntArray(
				ListUtil.fromArray(
					cpDefinitionInventory.getAllowedOrderQuantitiesArray()),
				BigDecimal::intValue);

			BigDecimal maxOrderQuantity =
				cpDefinitionInventory.getMaxOrderQuantity();

			_maxOrderQuantity = maxOrderQuantity.intValue();

			BigDecimal minOrderQuantity =
				cpDefinitionInventory.getMinOrderQuantity();

			_minOrderQuantity = minOrderQuantity.intValue();

			BigDecimal multipleOrderQuantity =
				cpDefinitionInventory.getMultipleOrderQuantity();

			_multipleOrderQuantity = multipleOrderQuantity.intValue();
		}

		if (_value == 0) {
			_value = _minOrderQuantity;
		}

		return super.doStartTag();
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public String getName() {
		return _name;
	}

	public int getValue() {
		return _value;
	}

	public boolean isShowLabel() {
		return _showLabel;
	}

	public boolean isUseSelect() {
		return _useSelect;
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

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setShowLabel(boolean showLabel) {
		_showLabel = showLabel;
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

		_allowedOrderQuantities = null;
		_cpDefinitionId = 0;
		_maxOrderQuantity = 0;
		_minOrderQuantity = 0;
		_multipleOrderQuantity = 0;
		_name = null;
		_showLabel = true;
		_useSelect = true;
		_value = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest = getRequest();

		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:allowedOrderQuantities",
			_allowedOrderQuantities);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:cpDefinitionId", _cpDefinitionId);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:maxOrderQuantity",
			_maxOrderQuantity);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:minOrderQuantity",
			_minOrderQuantity);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:multipleOrderQuantity",
			_multipleOrderQuantity);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:name", _name);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:showLabel", _showLabel);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:useSelect", _useSelect);
		httpServletRequest.setAttribute(
			"liferay-commerce:quantity-input:value", _value);
	}

	private static final String _PAGE = "/quantity_input/page.jsp";

	private int[] _allowedOrderQuantities;
	private long _cpDefinitionId;
	private int _maxOrderQuantity;
	private int _minOrderQuantity;
	private int _multipleOrderQuantity;
	private String _name;
	private boolean _showLabel = true;
	private boolean _useSelect = true;
	private int _value;

}