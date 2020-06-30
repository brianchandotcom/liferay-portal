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

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.internal.model.PaymentMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PAYMENT_METHODS,
	service = CommerceDataSetDataProvider.class
)
public class CommercePaymentMethodDataSetDataProvider
	implements CommerceDataSetDataProvider<PaymentMethod> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRelsCount(commerceOrder.getGroupId());
	}

	@Override
	public List<PaymentMethod> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<PaymentMethod> paymentMethods = new ArrayList<>();

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			_commercePaymentMethodGroupRelService.
				getCommercePaymentMethodGroupRels(
					commerceOrder.getGroupId(), pagination.getStartPosition(),
					pagination.getEndPosition(), null);

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			paymentMethods.add(
				new PaymentMethod(
					commercePaymentMethodGroupRel.getDescription(
						themeDisplay.getLanguageId()),
					commercePaymentMethodGroupRel.getEngineKey(),
					_getThumbnail(commercePaymentMethodGroupRel, themeDisplay),
					commercePaymentMethodGroupRel.getName(
						themeDisplay.getLanguageId())));
		}

		return paymentMethods;
	}

	private ImageField _getThumbnail(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel,
		ThemeDisplay themeDisplay) {

		String imageURL = commercePaymentMethodGroupRel.getImageURL(
			themeDisplay);

		if (Validator.isNull(imageURL)) {
			return null;
		}

		return new ImageField(
			commercePaymentMethodGroupRel.getName(themeDisplay.getLanguageId()),
			"rounded", "sm", imageURL);
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

}