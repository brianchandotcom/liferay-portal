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
import com.liferay.commerce.channel.web.internal.model.TaxMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
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
		"clay.data.provider.key=" + CommerceTaxMethodClayTable.NAME,
		"clay.data.set.display.name=" + CommerceTaxMethodClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceTaxMethodClayTable
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider, ClayDataSetDataProvider<TaxMethod> {

	public static final String NAME = "tax-methods";

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
			"taxEngine", "tax-engine");

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
							CommerceTaxMethod.class.getName(),
							PortletProvider.Action.EDIT)
					).setParameter(
						"commerceChannelId",
						ParamUtil.getLong(
							httpServletRequest, "commerceChannelId")
					).setParameter(
						"commerceTaxMethodEngineKey",
						() -> {
							TaxMethod taxMethod = (TaxMethod)model;

							return taxMethod.getKey();
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
	public List<TaxMethod> getItems(
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

		Map<String, CommerceTaxEngine> commerceTaxEngines =
			_commerceTaxEngineRegistry.getCommerceTaxEngines();

		List<TaxMethod> taxMethods = new ArrayList<>();

		for (Map.Entry<String, CommerceTaxEngine> entry :
				commerceTaxEngines.entrySet()) {

			CommerceTaxEngine commerceTaxEngine = entry.getValue();

			CommerceTaxMethod commerceTaxMethod =
				_commerceTaxMethodService.fetchCommerceTaxMethod(
					commerceChannel.getGroupId(), entry.getKey());

			String commerceTaxDescription = commerceTaxEngine.getDescription(
				themeDisplay.getLocale());
			String commerceTaxName = commerceTaxEngine.getName(
				themeDisplay.getLocale());

			if (commerceTaxMethod != null) {
				commerceTaxDescription = commerceTaxMethod.getDescription(
					themeDisplay.getLocale());
				commerceTaxName = commerceTaxMethod.getName(
					themeDisplay.getLocale());
			}

			taxMethods.add(
				new TaxMethod(
					commerceTaxDescription, entry.getKey(), commerceTaxName,
					CommerceChannelClayTableUtil.getLabelField(
						_isActive(commerceTaxMethod), themeDisplay.getLocale()),
					commerceTaxEngine.getName(themeDisplay.getLocale())));
		}

		return taxMethods;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		Map<String, CommerceTaxEngine> commerceTaxEngines =
			_commerceTaxEngineRegistry.getCommerceTaxEngines();

		return commerceTaxEngines.size();
	}

	private boolean _isActive(CommerceTaxMethod commerceTaxMethod) {
		if (commerceTaxMethod == null) {
			return false;
		}

		return commerceTaxMethod.isActive();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceTaxEngineRegistry _commerceTaxEngineRegistry;

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

}