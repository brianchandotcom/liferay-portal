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

package com.liferay.commerce.shipping.engine.fixed.web.internal.frontend;

import com.liferay.commerce.constants.CommercePortletKeys;
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
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelService;
import com.liferay.commerce.shipping.engine.fixed.web.internal.model.ShippingFixedOptionSetting;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
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

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceShippingFixedOptionSettingClayTable.NAME,
		"commerce.data.set.display.name=" + CommerceShippingFixedOptionSettingClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceShippingFixedOptionSettingClayTable
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider,
			   CommerceDataSetDataProvider<ShippingFixedOptionSetting> {

	public static final String NAME = "shipping-fixed-option-settings";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		try {
			ShippingFixedOptionSetting shippingFixedOptionSetting =
				(ShippingFixedOptionSetting)model;

			ClayDataSetAction editClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getShippingFixedOptionSettingEditURL(
					httpServletRequest,
					shippingFixedOptionSetting.
						getShippingFixedOptionSettingId()),
				StringPool.BLANK, LanguageUtil.get(httpServletRequest, "edit"),
				null, false, false);

			editClayDataSetAction.setTarget("sidePanel");

			clayTableActions.add(editClayDataSetAction);

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getShippingFixedOptionSettingDeleteURL(
					httpServletRequest,
					shippingFixedOptionSetting.
						getShippingFixedOptionSettingId()),
				StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "delete"), null, false,
				false);

			clayTableActions.add(deleteClayDataSetAction);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return clayTableActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceShippingMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceShippingMethodId");

		return _commerceShippingFixedOptionRelService.
			getCommerceShippingMethodFixedOptionRelsCount(
				commerceShippingMethodId);
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		ClayTableSchemaField shippingOptionField =
			clayTableSchemaBuilder.addField(
				"shippingOption", "shipping-option");

		shippingOptionField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addField("shippingMethod", "shipping-method");

		clayTableSchemaBuilder.addField("warehouse", "warehouse");

		clayTableSchemaBuilder.addField("country", "country");

		clayTableSchemaBuilder.addField("region", "region");

		clayTableSchemaBuilder.addField("zip", "zip");

		return clayTableSchemaBuilder.build();
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

	private String _getCountry(
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel,
			ThemeDisplay themeDisplay)
		throws PortalException {

		CommerceCountry commerceCountry =
			commerceShippingFixedOptionRel.getCommerceCountry();

		if (commerceCountry == null) {
			return StringPool.STAR;
		}

		return commerceCountry.getName(themeDisplay.getLanguageId());
	}

	private String _getRegion(
			CommerceShippingFixedOptionRel commerceShippingFixedOptionRel)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceShippingFixedOptionRel.getCommerceRegion();

		if (commerceRegion == null) {
			return StringPool.STAR;
		}

		return commerceRegion.getName();
	}

	private String _getShippingFixedOptionSettingDeleteURL(
		HttpServletRequest httpServletRequest,
		long shippingFixedOptionSettingId) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
			PortletRequest.ACTION_PHASE);

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceShippingFixedOptionRel");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"commerceShippingFixedOptionRelId",
			String.valueOf(shippingFixedOptionSettingId));

		return portletURL.toString();
	}

	private String _getShippingFixedOptionSettingEditURL(
			HttpServletRequest httpServletRequest,
			long shippingFixedOptionSettingId)
		throws Exception {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceShippingMethod.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceShippingFixedOptionRel");

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