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
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelService;
import com.liferay.commerce.shipping.engine.fixed.web.internal.model.ShippingFixedOptionSetting;
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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceShippingFixedOptionSettingClayTableDataSetDisplayView.NAME,
		"clay.data.set.display.name=" + CommerceShippingFixedOptionSettingClayTableDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceShippingFixedOptionSettingClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider,
			   ClayDataSetDataProvider<ShippingFixedOptionSetting> {

	public static final String NAME = "shipping-fixed-option-settings";

	@Override
	public ClayTableSchema getClayTableSchema(Locale locale) {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField shippingOptionClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"shippingOption", "shipping-option");

		shippingOptionClayTableSchemaField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"shippingMethod", "shipping-method");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"warehouse", "warehouse");

		clayTableSchemaBuilder.addClayTableSchemaField("country", "country");

		clayTableSchemaBuilder.addClayTableSchemaField("region", "region");

		clayTableSchemaBuilder.addClayTableSchemaField("zip", "zip");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		ShippingFixedOptionSetting shippingFixedOptionSetting =
			(ShippingFixedOptionSetting)model;

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					_getShippingFixedOptionSettingEditURL(
						httpServletRequest,
						shippingFixedOptionSetting.
							getShippingFixedOptionSettingId()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					_getShippingFixedOptionSettingDeleteURL(
						httpServletRequest,
						shippingFixedOptionSetting.
							getShippingFixedOptionSettingId()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	@Override
	public List<ShippingFixedOptionSetting> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceShippingMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceShippingMethodId");

		List<CommerceShippingFixedOptionRel>
			commerceShippingMethodFixedOptionRels =
				_commerceShippingFixedOptionRelService.
					getCommerceShippingMethodFixedOptionRels(
						commerceShippingMethodId, pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		List<ShippingFixedOptionSetting> shippingFixedOptionSettings =
			new ArrayList<>();

		for (CommerceShippingFixedOptionRel commerceShippingFixedOptionRel :
				commerceShippingMethodFixedOptionRels) {

			CommerceShippingFixedOption commerceShippingFixedOption =
				commerceShippingFixedOptionRel.getCommerceShippingFixedOption();
			CommerceShippingMethod commerceShippingMethod =
				commerceShippingFixedOptionRel.getCommerceShippingMethod();

			shippingFixedOptionSettings.add(
				new ShippingFixedOptionSetting(
					_getCountry(commerceShippingFixedOptionRel, themeDisplay),
					_getRegion(commerceShippingFixedOptionRel),
					commerceShippingFixedOptionRel.
						getCommerceShippingFixedOptionRelId(),
					commerceShippingMethod.getName(
						themeDisplay.getLanguageId()),
					commerceShippingFixedOption.getName(
						themeDisplay.getLanguageId()),
					_getWarehouse(commerceShippingFixedOptionRel),
					_getZip(commerceShippingFixedOptionRel)));
		}

		return shippingFixedOptionSettings;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceShippingMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceShippingMethodId");

		return _commerceShippingFixedOptionRelService.
			getCommerceShippingMethodFixedOptionRelsCount(
				commerceShippingMethodId);
	}

	private String _getCountry(
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Country country = commerceShippingFixedOptionRel.getCountry();

		if (country == null) {
			return StringPool.STAR;
		}

		return country.getTitle(themeDisplay.getLanguageId());
	}

	private String _getRegion(
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel)
		throws PortalException {

		Region region = commerceShippingFixedOptionRel.getRegion();

		if (region == null) {
			return StringPool.STAR;
		}

		return region.getName();
	}

	private String _getShippingFixedOptionSettingDeleteURL(
		HttpServletRequest httpServletRequest,
		long shippingFixedOptionSettingId) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest,
				CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_shipping_methods/edit_commerce_shipping_fixed_option_rel"
		).setCMD(
			Constants.DELETE
		).setRedirect(
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest))
		).setParameter(
			"commerceShippingFixedOptionRelId", shippingFixedOptionSettingId
		).buildString();
	}

	private String _getShippingFixedOptionSettingEditURL(
			HttpServletRequest httpServletRequest,
			long shippingFixedOptionSettingId)
		throws Exception {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceShippingMethod.class.getName(),
				PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_shipping_methods/edit_commerce_shipping_fixed_option_rel"
		).buildPortletURL();

		long commerceShippingMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceShippingMethodId");

		portletURL.setParameter(
			"commerceShippingMethodId",
			String.valueOf(commerceShippingMethodId));

		portletURL.setParameter(
			"commerceShippingFixedOptionRelId",
			String.valueOf(shippingFixedOptionSettingId));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private String _getWarehouse(
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel)
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceShippingFixedOptionRel.getCommerceInventoryWarehouse();

		if (commerceInventoryWarehouse == null) {
			return StringPool.STAR;
		}

		return commerceInventoryWarehouse.getName();
	}

	private String _getZip(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		if (Validator.isNull(commerceShippingFixedOptionRel.getZip())) {
			return StringPool.STAR;
		}

		return commerceShippingFixedOptionRel.getZip();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingFixedOptionRelService
		_commerceShippingFixedOptionRelService;

	@Reference
	private Portal _portal;

}