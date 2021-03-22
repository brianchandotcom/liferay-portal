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
import com.liferay.commerce.channel.web.internal.model.ShippingMethod;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
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
		"clay.data.provider.key=" + CommerceShippingMethodClayTable.NAME,
		"clay.data.set.display.name=" + CommerceShippingMethodClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceShippingMethodClayTable
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider,
			   ClayDataSetDataProvider<ShippingMethod> {

	public static final String NAME = "shipping-methods";

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField nameField =
			clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"description", "description");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"shippingEngine", "shipping-engine");

		ClayTableSchemaField statusField =
			clayTableSchemaBuilder.addClayTableSchemaField("status", "status");

		statusField.setContentRenderer("label");

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
							CommerceShippingMethod.class.getName(),
							PortletProvider.Action.EDIT)
					).setParameter(
						"commerceChannelId",
						ParamUtil.getLong(
							httpServletRequest, "commerceChannelId")
					).setParameter(
						"commerceShippingMethodEngineKey",
						() -> {
							ShippingMethod shippingMethod =
								(ShippingMethod)model;

							return shippingMethod.getKey();
						}
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).build();
	}

	@Override
	public List<ShippingMethod> getItems(
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

		Map<String, CommerceShippingEngine> commerceShippingEngines =
			_commerceShippingEngineRegistry.getCommerceShippingEngines();

		List<ShippingMethod> shippingMethods = new ArrayList<>();

		for (Map.Entry<String, CommerceShippingEngine> entry :
				commerceShippingEngines.entrySet()) {

			CommerceShippingEngine commerceShippingEngine = entry.getValue();

			CommerceShippingMethod commerceShippingMethod =
				_commerceShippingMethodService.fetchCommerceShippingMethod(
					commerceChannel.getGroupId(), entry.getKey());

			String commerceShippingDescription =
				commerceShippingEngine.getDescription(themeDisplay.getLocale());
			String commerceShippingName = commerceShippingEngine.getName(
				themeDisplay.getLocale());

			if (commerceShippingMethod != null) {
				commerceShippingDescription =
					commerceShippingMethod.getDescription(
						themeDisplay.getLocale());
				commerceShippingName = commerceShippingMethod.getName(
					themeDisplay.getLocale());
			}

			shippingMethods.add(
				new ShippingMethod(
					commerceShippingDescription, entry.getKey(),
					commerceShippingName,
					commerceShippingEngine.getName(themeDisplay.getLocale()),
					CommerceChannelClayTableUtil.getLabelField(
						_isActive(commerceShippingMethod),
						themeDisplay.getLocale())));
		}

		return shippingMethods;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		Map<String, CommerceShippingEngine> commerceShippingEngines =
			_commerceShippingEngineRegistry.getCommerceShippingEngines();

		return commerceShippingEngines.size();
	}

	private boolean _isActive(CommerceShippingMethod commerceShippingMethod) {
		if (commerceShippingMethod == null) {
			return false;
		}

		return commerceShippingMethod.isActive();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

}