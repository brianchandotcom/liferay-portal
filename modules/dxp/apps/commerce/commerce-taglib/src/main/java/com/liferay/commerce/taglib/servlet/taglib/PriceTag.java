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
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceServiceUtil;
import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;
import com.liferay.commerce.taglib.servlet.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class PriceTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		CommerceContext commerceContext = (CommerceContext)request.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);

		try {
			if (_quantity == 0) {
				CPInstance cpInstance = CPInstanceServiceUtil.getCPInstance(
					_cpInstanceId);

				CPDefinitionInventory cpDefinitionInventory =
					CPDefinitionInventoryServiceUtil.
						fetchCPDefinitionInventoryByCPDefinitionId(
							cpInstance.getCPDefinitionId());

				if (cpDefinitionInventory != null) {
					_quantity = cpDefinitionInventory.getMinOrderQuantity();
				}
				else {
					_quantity =
						CPDefinitionInventoryConstants.
							DEFAULT_MULTIPLE_ORDER_QUANTITY;
				}
			}

			long groupId = PortalUtil.getScopeGroupId(request);

			if (_commerceCurrencyId == 0) {
				CommerceCurrency commerceCurrency =
					CommerceCurrencyLocalServiceUtil.
						fetchPrimaryCommerceCurrency(groupId);

				_commerceCurrencyId = commerceCurrency.getCommerceCurrencyId();
			}

			CommerceMoney commerceMoney =
				commercePriceCalculation.getFinalPrice(
					_cpInstanceId, _quantity, true, true, commerceContext);

			_formattedPrice = commerceMoney.toString();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setCommerceCurrencyId(long commerceCurrencyId) {
		_commerceCurrencyId = commerceCurrencyId;
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

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceCurrencyId = 0;
		_cpInstanceId = 0;
		_quantity = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce:price:commerceCurrencyId", _commerceCurrencyId);
		request.setAttribute(
			"liferay-commerce:price:cpInstanceId", _cpInstanceId);
		request.setAttribute(
			"liferay-commerce:price:formattedPrice", _formattedPrice);
		request.setAttribute("liferay-commerce:price:quantity", _quantity);
	}

	protected CommercePriceCalculation commercePriceCalculation;

	private static final String _PAGE = "/price/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(PriceTag.class);

	private long _commerceCurrencyId;
	private long _cpInstanceId;
	private String _formattedPrice;
	private int _quantity;

}