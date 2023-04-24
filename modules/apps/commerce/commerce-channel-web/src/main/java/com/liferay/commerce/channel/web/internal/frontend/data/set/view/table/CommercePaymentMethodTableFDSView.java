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

package com.liferay.commerce.channel.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.channel.web.internal.constants.CommerceChannelFDSNames;
import com.liferay.commerce.channel.web.internal.frontend.util.CommerceChannelClayTableUtil;
import com.liferay.commerce.channel.web.internal.model.PaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"fds.data.provider.key=" + CommerceChannelFDSNames.PAYMENT_METHOD,
		"frontend.data.set.name=" + CommerceChannelFDSNames.PAYMENT_METHOD
	},
	service = {FDSDataProvider.class, FDSView.class}
)
public class CommercePaymentMethodTableFDSView
	extends BaseTableFDSView implements FDSDataProvider<PaymentMethod> {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"name", "name",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"description", "description"
		).add(
			"paymentEngine", "payment-engine"
		).add(
			"status", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"label")
		).build();
	}

	@Override
	public List<PaymentMethod> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		Map<String, CommercePaymentMethod> commercePaymentMethodMap =
			_commercePaymentMethodRegistry.getCommercePaymentMethods();

		List<PaymentMethod> paymentMethods = new ArrayList<>();

		for (Map.Entry<String, CommercePaymentMethod> entry :
				commercePaymentMethodMap.entrySet()) {

			CommercePaymentMethod commercePaymentMethod = entry.getValue();

			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelService.
					fetchCommercePaymentMethodGroupRel(
						commerceChannel.getGroupId(),
						commercePaymentMethod.getKey());

			String commercePaymentDescription =
				commercePaymentMethod.getDescription(themeDisplay.getLocale());
			String commercePaymentName = commercePaymentMethod.getName(
				themeDisplay.getLocale());

			if (commercePaymentMethodGroupRel != null) {
				commercePaymentDescription =
					commercePaymentMethodGroupRel.getDescription(
						themeDisplay.getLocale());
				commercePaymentName = commercePaymentMethodGroupRel.getName(
					themeDisplay.getLocale());
			}

			paymentMethods.add(
				new PaymentMethod(
					commercePaymentDescription, commercePaymentMethod.getKey(),
					commercePaymentName,
					commercePaymentMethod.getName(themeDisplay.getLocale()),
					CommerceChannelClayTableUtil.getLabelField(
						_isActive(commercePaymentMethodGroupRel),
						themeDisplay.getLocale())));
		}

		return paymentMethods;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		Map<String, CommercePaymentMethod> commercePaymentMethodMap =
			_commercePaymentMethodRegistry.getCommercePaymentMethods();

		return commercePaymentMethodMap.size();
	}

	private boolean _isActive(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		if (commercePaymentMethodGroupRel == null) {
			return false;
		}

		return commercePaymentMethodGroupRel.isActive();
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private CommercePaymentMethodRegistry _commercePaymentMethodRegistry;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}