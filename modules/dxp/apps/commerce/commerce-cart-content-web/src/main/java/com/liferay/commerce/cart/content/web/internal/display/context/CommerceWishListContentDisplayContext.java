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

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.cart.CommerceCartValidatorRegistry;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceWishListContentDisplayContext
	extends CommerceCartContentDisplayContext {

	public CommerceWishListContentDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		CommerceCartHelper commerceCartHelper,
		CommerceCartItemService commerceCartItemService,
		CommerceCartValidatorRegistry commerceCartValidatorRegistry,
		CommercePriceCalculator commercePriceCalculator,
		CommercePriceFormatter commercePriceFormatter,
		CPDefinitionHelper cpDefinitionHelper,
		CPInstanceHelper cpInstanceHelper) {

		super(
			httpServletRequest, httpServletResponse, commerceCartHelper,
			commerceCartItemService, commerceCartValidatorRegistry,
			commercePriceCalculator, commercePriceFormatter, cpDefinitionHelper,
			cpInstanceHelper);
	}

	@Override
	public int getCommerceCartType() {
		return ParamUtil.getInteger(
			commerceCartContentRequestHelper.getRequest(), "type",
			CommerceCartConstants.TYPE_WISH_LIST);
	}

	public boolean isIgnoreSKUCombinations(CommerceCartItem commerceCartItem)
		throws PortalException {

		CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

		if (cpDefinition.isIgnoreSKUCombinations() ||
			(commerceCartItem.getCPInstanceId() > 0)) {

			return true;
		}

		return false;
	}

}