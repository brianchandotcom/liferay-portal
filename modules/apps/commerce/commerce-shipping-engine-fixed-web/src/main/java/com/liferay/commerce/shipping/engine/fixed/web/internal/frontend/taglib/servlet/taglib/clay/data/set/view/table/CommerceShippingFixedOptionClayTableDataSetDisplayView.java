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

package com.liferay.commerce.shipping.engine.fixed.web.internal.frontend.taglib.servlet.taglib.clay.data.set.view.table;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionPriorityComparator;
import com.liferay.commerce.shipping.engine.fixed.web.internal.model.ShippingFixedOption;
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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceShippingFixedOptionClayTableDataSetDisplayView.NAME,
		"clay.data.set.display.name=" + CommerceShippingFixedOptionClayTableDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceShippingFixedOptionClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider,
			   ClayDataSetDataProvider<ShippingFixedOption> {

	public static final String NAME = "shipping-fixed-options";

	@Override
	public ClayTableSchema getClayTableSchema(Locale locale) {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField nameField =
			clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"description", "description");

		ClayTableSchemaField priorityField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"priority", "priority");

		priorityField.setSortable(true);

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		ShippingFixedOption shippingFixedOption = (ShippingFixedOption)model;

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					_getShippingFixedOptionEditURL(
						httpServletRequest,
						shippingFixedOption.getShippingFixedOptionId()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					_getShippingFixedOptionDeleteURL(
						httpServletRequest,
						shippingFixedOption.getShippingFixedOptionId()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	@Override
	public List<ShippingFixedOption> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
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
				filter.getKeywords(), pagination.getStartPosition(),
				pagination.getEndPosition());

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
			HttpServletRequest httpServletRequest, Filter filter)
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

	private String _getShippingFixedOptionDeleteURL(
		HttpServletRequest httpServletRequest, long shippingFixedOptionId) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_shipping_methods/edit_commerce_shipping_fixed_option"
		).setCMD(
			Constants.DELETE
		).setRedirect(
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest))
		).setParameter(
			"commerceShippingFixedOptionId", shippingFixedOptionId
		).buildString();
	}

	private String _getShippingFixedOptionEditURL(
			HttpServletRequest httpServletRequest, long shippingFixedOptionId)
		throws Exception {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceShippingMethod.class.getName(),
				PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_shipping_methods/edit_commerce_shipping_fixed_option"
		).setParameter(
			"commerceShippingFixedOptionId", shippingFixedOptionId
		).buildPortletURL();

		long commerceShippingMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceShippingMethodId");

		portletURL.setParameter(
			"commerceShippingMethodId",
			String.valueOf(commerceShippingMethodId));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private Portal _portal;

}