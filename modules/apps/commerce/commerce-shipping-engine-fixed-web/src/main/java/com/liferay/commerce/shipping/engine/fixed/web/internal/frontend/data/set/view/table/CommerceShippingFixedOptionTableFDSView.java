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

package com.liferay.commerce.shipping.engine.fixed.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionPriorityComparator;
import com.liferay.commerce.shipping.engine.fixed.web.internal.constants.CommerceShippingFixedOptionFDSNames;
import com.liferay.commerce.shipping.engine.fixed.web.internal.model.ShippingFixedOption;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"fds.data.provider.key=" + CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTIONS,
		"frontend.data.set.name=" + CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTIONS
	},
	service = {FDSDataProvider.class, FDSView.class}
)
public class CommerceShippingFixedOptionTableFDSView
	extends BaseTableFDSView implements FDSDataProvider<ShippingFixedOption> {

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
			"priority", "priority",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).build();
	}

	@Override
	public List<ShippingFixedOption> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceShippingMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceShippingMethodId");

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.getCommerceShippingMethod(
				commerceShippingMethodId);

		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			_commerceShippingFixedOptionService.getCommerceShippingFixedOptions(
				themeDisplay.getCompanyId(),
				commerceShippingMethod.getGroupId(), commerceShippingMethodId,
				fdsKeywords.getKeywords(), fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition());

		List<ShippingFixedOption> shippingFixedOptions = new ArrayList<>();

		commerceShippingFixedOptions = ListUtil.sort(
			commerceShippingFixedOptions,
			new CommerceShippingFixedOptionPriorityComparator(
				sort.isReverse()));

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				commerceShippingFixedOptions) {

			shippingFixedOptions.add(
				new ShippingFixedOption(
					HtmlUtil.escape(
						commerceShippingFixedOption.getDescription(
							themeDisplay.getLocale())),
					HtmlUtil.escape(
						commerceShippingFixedOption.getName(
							themeDisplay.getLocale())),
					commerceShippingFixedOption.getPriority(),
					commerceShippingFixedOption.
						getCommerceShippingFixedOptionId()));
		}

		return shippingFixedOptions;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceShippingMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceShippingMethodId");

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.getCommerceShippingMethod(
				commerceShippingMethodId);

		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			_commerceShippingFixedOptionService.getCommerceShippingFixedOptions(
				commerceShippingMethod.getCompanyId(),
				commerceShippingMethod.getGroupId(), commerceShippingMethodId,
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return commerceShippingFixedOptions.size();
	}

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}