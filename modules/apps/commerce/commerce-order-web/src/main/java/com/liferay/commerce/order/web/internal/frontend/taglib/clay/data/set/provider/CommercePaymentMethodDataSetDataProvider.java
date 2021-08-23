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

package com.liferay.commerce.order.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.web.internal.model.PaymentMethod;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
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
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PAYMENT_METHODS,
	service = ClayDataSetDataProvider.class
)
public class CommercePaymentMethodDataSetDataProvider
	implements ClayDataSetDataProvider<PaymentMethod> {

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

		List<CommercePaymentMethod> commercePaymentMethods =
			_commercePaymentEngine.getEnabledCommercePaymentMethodsForOrder(
				commerceOrder.getGroupId(), commerceOrderId);

		for (CommercePaymentMethod commercePaymentMethod :
				commercePaymentMethods) {

			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelLocalService.
					getCommercePaymentMethodGroupRel(
						commerceOrder.getGroupId(),
						commercePaymentMethod.getKey());

			paymentMethods.add(
				new PaymentMethod(
					commercePaymentMethod.getDescription(
						themeDisplay.getLocale()),
					commercePaymentMethod.getKey(),
					_getThumbnail(commercePaymentMethodGroupRel, themeDisplay),
					commercePaymentMethod.getName(themeDisplay.getLocale())));
		}

		return paymentMethods;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		return _commercePaymentEngine.getCommercePaymentMethodGroupRelsCount(
			commerceOrder.getGroupId());
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
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

}