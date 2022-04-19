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

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelService;
import com.liferay.commerce.shipping.engine.fixed.web.internal.constants.CommerceShippingFixedOptionFDSNames;
import com.liferay.commerce.shipping.engine.fixed.web.internal.model.ShippingFixedOptionSetting;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
		"fds.data.provider.key=" + CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTION_SETTINGS,
		"frontend.data.set.name=" + CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTION_SETTINGS
	},
	service = {FDSActionProvider.class, FDSDataProvider.class, FDSView.class}
)
public class CommerceShippingFixedOptionSettingTableFDSView
	extends BaseTableFDSView
	implements FDSActionProvider, FDSDataProvider<ShippingFixedOptionSetting> {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
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
					_language.get(httpServletRequest, "edit"));
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
					_language.get(httpServletRequest, "delete"));
			}
		).build();
	}

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"shippingOption", "shipping-option",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"shippingMethod", "shipping-method"
		).add(
			"warehouse", "warehouse"
		).add(
			"country", "country"
		).add(
			"region", "region"
		).add(
			"zip", "zip"
		).build();
	}

	@Override
	public List<ShippingFixedOptionSetting> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
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
						commerceShippingMethodId,
						fdsPagination.getStartPosition(),
						fdsPagination.getEndPosition(), null);

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
					_getWarehouse(
						commerceShippingFixedOptionRel,
						themeDisplay.getLocale()),
					_getZip(commerceShippingFixedOptionRel)));
		}

		return shippingFixedOptionSettings;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
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
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
			Locale locale)
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceShippingFixedOptionRel.getCommerceInventoryWarehouse();

		if (commerceInventoryWarehouse == null) {
			return StringPool.STAR;
		}

		return commerceInventoryWarehouse.getName(locale);
	}

	private String _getZip(
		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel) {

		if (Validator.isNull(commerceShippingFixedOptionRel.getZip())) {
			return StringPool.STAR;
		}

		return commerceShippingFixedOptionRel.getZip();
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingFixedOptionRelService
		_commerceShippingFixedOptionRelService;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}