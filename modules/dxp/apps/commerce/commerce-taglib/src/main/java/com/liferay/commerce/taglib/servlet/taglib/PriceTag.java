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

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionServiceUtil;
import com.liferay.commerce.product.service.CPInstanceServiceUtil;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class PriceTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!CPPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), CPActionKeys.VIEW_PRICE)) {

			return super.doStartTag();
		}

		try {
			CommerceContext commerceContext =
				(CommerceContext)request.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CPDefinition cpDefinition = CPDefinitionServiceUtil.getCPDefinition(
				_cpDefinitionId);

			if (_quantity == 0) {
				CPDefinitionInventory cpDefinitionInventory =
					CPDefinitionInventoryServiceUtil.
						fetchCPDefinitionInventoryByCPDefinitionId(
							_cpDefinitionId);

				if (cpDefinitionInventory != null) {
					_quantity = cpDefinitionInventory.getMinOrderQuantity();
				}
				else {
					_quantity =
						CPDefinitionInventoryConstants.
							DEFAULT_MULTIPLE_ORDER_QUANTITY;
				}
			}

			if (_cpInstanceId > 0) {
				_cpInstance = CPInstanceServiceUtil.getCPInstance(
					_cpInstanceId);
			}
			else {
				if (cpDefinition.isIgnoreSKUCombinations()) {
					CPInstanceHelper cpInstanceHelper =
						ServletContextUtil.getCPInstanceHelper();

					_cpInstance = cpInstanceHelper.getCPInstance(
						_cpDefinitionId, null);
				}
			}

			if (_cpInstance == null) {
				_formattedPrice = getFormattedPrice(_quantity, commerceContext);
			}
			else {
				CommerceMoney commerceMoney =
					commercePriceCalculation.getFinalPrice(
						_cpInstance.getCPInstanceId(), _quantity, true, true,
						commerceContext);

				_formattedPrice = commerceMoney.toString();
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		commercePriceCalculation =
			ServletContextUtil.getCommercePriceCalculation();

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setShowPriceRange(boolean showPriceRange) {
		_showPriceRange = showPriceRange;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cpDefinitionId = 0;
		_cpInstanceId = 0;
		_quantity = 0;
		_showPriceRange = false;
	}

	protected String getFormattedPrice(
			int quantity, CommerceContext commerceContext)
		throws PortalException {

		CommerceMoney minPriceCommerceMoney =
			commercePriceCalculation.getUnitMinPrice(
				_cpDefinitionId, quantity, commerceContext);

		if (!_showPriceRange) {
			return minPriceCommerceMoney.toString();
		}

		CommerceMoney maxPriceCommerceMoney =
			commercePriceCalculation.getUnitMaxPrice(
				_cpDefinitionId, quantity, commerceContext);

		return minPriceCommerceMoney.toString() + " - " +
			maxPriceCommerceMoney.toString();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute("liferay-commerce:price:cpInstance", _cpInstance);
		request.setAttribute(
			"liferay-commerce:price:formattedPrice", _formattedPrice);
		request.setAttribute(
			"liferay-commerce:price:showPriceRange", _showPriceRange);
	}

	protected CommercePriceCalculation commercePriceCalculation;

	private static final String _PAGE = "/price/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(PriceTag.class);

	private long _cpDefinitionId;
	private CPInstance _cpInstance;
	private long _cpInstanceId;
	private String _formattedPrice;
	private int _quantity;
	private boolean _showPriceRange;

}