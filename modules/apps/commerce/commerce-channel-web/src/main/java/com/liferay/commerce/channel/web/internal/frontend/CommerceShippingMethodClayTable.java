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
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableSchema;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilder;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilderFactory;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaField;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceShippingMethodClayTable.NAME,
		"commerce.data.set.display.name=" + CommerceShippingMethodClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceShippingMethodClayTable
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider,
			   CommerceDataSetDataProvider<ShippingMethod> {

	public static final String NAME = "shipping-methods";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		try {
			ShippingMethod shippingMethod = (ShippingMethod)model;

			long commerceChannelId = ParamUtil.getLong(
				httpServletRequest, "commerceChannelId");

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceShippingMethod.class.getName(),
				PortletProvider.Action.EDIT);

			portletURL.setParameter(
				"commerceChannelId", String.valueOf(commerceChannelId));
			portletURL.setParameter(
				"commerceShippingMethodEngineKey",
				String.valueOf(shippingMethod.getKey()));

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			ClayDataSetAction clayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, portletURL.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "edit"), null, false,
				false);

			clayDataSetAction.setTarget("sidePanel");

			clayTableActions.add(clayDataSetAction);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return clayTableActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		Map<String, CommerceShippingEngine> commerceShippingEngines =
			_commerceShippingEngineRegistry.getCommerceShippingEngines();

		return commerceShippingEngines.size();
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		ClayTableSchemaField nameField = clayTableSchemaBuilder.addField(
			"name", "name");

		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addField("description", "description");

		clayTableSchemaBuilder.addField("shippingEngine", "shipping-engine");

		ClayTableSchemaField statusField = clayTableSchemaBuilder.addField(
			"status", "status");

		statusField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
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
					HtmlUtil.escape(commerceShippingDescription),
					entry.getKey(), HtmlUtil.escape(commerceShippingName),
					HtmlUtil.escape(
						commerceShippingEngine.getName(
							themeDisplay.getLocale())),
					CommerceChannelClayTableUtil.getLabelField(
						_isActive(commerceShippingMethod),
						themeDisplay.getLocale())));
		}

		return shippingMethods;
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