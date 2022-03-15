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

package com.liferay.commerce.channel.web.internal.frontend;

import com.liferay.commerce.channel.web.internal.frontend.util.CommerceChannelClayTableUtil;
import com.liferay.commerce.channel.web.internal.model.PaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommercePaymentMethodClayTable.NAME,
		"clay.data.set.display.name=" + CommercePaymentMethodClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommercePaymentMethodClayTable
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider,
			   ClayDataSetDataProvider<PaymentMethod> {

	public static final String NAME = "payment-methods";

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField nameClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		nameClayTableSchemaField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"description", "description");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"paymentEngine", "payment-engine");

		ClayTableSchemaField statusClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("status", "status");

		statusClayTableSchemaField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.create(
						PortletProviderUtil.getPortletURL(
							httpServletRequest,
							CommercePaymentMethodGroupRel.class.getName(),
							PortletProvider.Action.EDIT)
					).setParameter(
						"commerceChannelId",
						ParamUtil.getLong(
							httpServletRequest, "commerceChannelId")
					).setParameter(
						"commercePaymentMethodEngineKey",
						() -> {
							PaymentMethod paymentMethod = (PaymentMethod)model;

							return paymentMethod.getKey();
						}
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).build();
	}

	@Override
	public List<PaymentMethod> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
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
			HttpServletRequest httpServletRequest, Filter filter)
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
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;

	@Reference
	private CommercePaymentMethodRegistry _commercePaymentMethodRegistry;

}