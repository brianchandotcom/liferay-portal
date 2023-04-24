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
import com.liferay.commerce.channel.web.internal.model.TaxMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
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
		"fds.data.provider.key=" + CommerceChannelFDSNames.TAX_METHOD,
		"frontend.data.set.name=" + CommerceChannelFDSNames.TAX_METHOD
	},
	service = {FDSDataProvider.class, FDSView.class}
)
public class CommerceTaxMethodTableFDSView
	extends BaseTableFDSView implements FDSDataProvider<TaxMethod> {

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
			"taxEngine", "tax-engine"
		).add(
			"status", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"label")
		).build();
	}

	@Override
	public List<TaxMethod> getItems(
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
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
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
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceTaxEngineRegistry _commerceTaxEngineRegistry;

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}