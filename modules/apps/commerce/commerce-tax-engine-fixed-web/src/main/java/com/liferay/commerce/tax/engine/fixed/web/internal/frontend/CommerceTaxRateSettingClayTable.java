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

package com.liferay.commerce.tax.engine.fixed.web.internal.frontend;

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
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelService;
import com.liferay.commerce.tax.engine.fixed.web.internal.model.TaxRateSetting;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
		"commerce.data.provider.key=" + CommerceTaxRateSettingClayTable.NAME,
		"commerce.data.set.display.name=" + CommerceTaxRateSettingClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceTaxRateSettingClayTable
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider,
			   CommerceDataSetDataProvider<TaxRateSetting> {

	public static final String NAME = "tax-rate-settings";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			TaxRateSetting taxRateSetting = (TaxRateSetting)model;

			long commerceChannelId = ParamUtil.getLong(
				httpServletRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

			if (!_commerceChannelModelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), commerceChannel,
					ActionKeys.UPDATE)) {

				return clayTableActions;
			}

			ClayDataSetAction editClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getTaxRateSettingEditURL(
					httpServletRequest, taxRateSetting.getTaxRateSettingId()),
				StringPool.BLANK, LanguageUtil.get(httpServletRequest, "edit"),
				null, false, false);

			editClayDataSetAction.setTarget("sidePanel");

			clayTableActions.add(editClayDataSetAction);

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK,
				_getTaxRateSettingDeleteURL(
					httpServletRequest, taxRateSetting.getTaxRateSettingId()),
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

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");
		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		return _commerceTaxFixedRateAddressRelService.
			getCommerceTaxMethodFixedRateAddressRelsCount(
				commerceChannel.getGroupId(), commerceTaxMethodId);
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		ClayTableSchemaField taxRateField = clayTableSchemaBuilder.addField(
			"taxRate", "tax-rate");

		taxRateField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addField("country", "country");

		clayTableSchemaBuilder.addField("region", "region");

		clayTableSchemaBuilder.addField("zip", "zip");

		clayTableSchemaBuilder.addField("rate", "rate");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<TaxRateSetting> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");
		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels =
			_commerceTaxFixedRateAddressRelService.
				getCommerceTaxMethodFixedRateAddressRels(
					commerceChannel.getGroupId(), commerceTaxMethodId,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

		List<TaxRateSetting> taxRateSettings = new ArrayList<>();

		for (CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel :
				commerceTaxFixedRateAddressRels) {

			CPTaxCategory cpTaxCategory =
				commerceTaxFixedRateAddressRel.getCPTaxCategory();
			CommerceCountry commerceCountry =
				commerceTaxFixedRateAddressRel.getCommerceCountry();
			CommerceRegion commerceRegion =
				commerceTaxFixedRateAddressRel.getCommerceRegion();

			taxRateSettings.add(
				new TaxRateSetting(
					_getCountry(commerceCountry, themeDisplay.getLanguageId()),
					commerceTaxFixedRateAddressRel.getRate(),
					_getRegion(commerceRegion),
					cpTaxCategory.getName(themeDisplay.getLanguageId()),
					commerceTaxFixedRateAddressRel.
						getCommerceTaxFixedRateAddressRelId(),
					_getZip(commerceTaxFixedRateAddressRel.getZip())));
		}

		return taxRateSettings;
	}

	private String _getCountry(
		CommerceCountry commerceCountry, String languageId) {

		if (commerceCountry == null) {
			return StringPool.STAR;
		}

		return commerceCountry.getName(languageId);
	}

	private String _getRegion(CommerceRegion commerceRegion) {
		if (commerceRegion == null) {
			return StringPool.STAR;
		}

		return commerceRegion.getName();
	}

	private String _getTaxRateSettingDeleteURL(
		HttpServletRequest httpServletRequest, long taxRateSettingId) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_TAX_METHODS,
			PortletRequest.ACTION_PHASE);

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceTaxFixedRateAddressRel");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"commerceTaxFixedRateAddressRelId",
			String.valueOf(taxRateSettingId));

		return portletURL.toString();
	}

	private String _getTaxRateSettingEditURL(
			HttpServletRequest httpServletRequest, long taxRateSettingId)
		throws Exception {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceTaxMethod.class.getName(),
			PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceTaxFixedRateAddressRel");

		long commerceTaxMethodId = ParamUtil.getLong(
			httpServletRequest, "commerceTaxMethodId");

		portletURL.setParameter(
			"commerceTaxMethodId", String.valueOf(commerceTaxMethodId));

		portletURL.setParameter(
			"commerceTaxFixedRateAddressRelId",
			String.valueOf(taxRateSettingId));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private String _getZip(String zip) {
		if (Validator.isNull(zip)) {
			return StringPool.STAR;
		}

		return zip;
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceTaxFixedRateAddressRelService
		_commerceTaxFixedRateAddressRelService;

	@Reference
	private Portal _portal;

}