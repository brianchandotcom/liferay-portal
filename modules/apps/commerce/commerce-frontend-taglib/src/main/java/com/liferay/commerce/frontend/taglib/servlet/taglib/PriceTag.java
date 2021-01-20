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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.configuration.CommercePriceConfiguration;
import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.configuration.RoundingTypeConfiguration;
import com.liferay.commerce.currency.constants.RoundingTypeConstants;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public class PriceTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			_commerceContext = (CommerceContext)request.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);
			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			setProductInfo();

			setPriceInfo(_commerceContext, _themeDisplay.getLocale());

			CommerceChannel commerceChannel =
				commerceChannelLocalService.fetchCommerceChannel(
					_commerceContext.getCommerceChannelId());

			if ((commerceChannel != null) &&
				Objects.equals(
					commerceChannel.getPriceDisplayType(),
					CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

				_netPrice = false;
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_commerceContext = null;
			_commerceDiscountValue = null;
			_discountLabel = null;
			_displayDiscountLevels = false;
			_displayOneLine = false;
			_formattedFinalPrice = null;
			_formattedPrice = null;
			_formattedPromoPrice = null;
			_promoPriceLabel = null;
			_netPrice = true;
			_showDiscount = false;
			_showDiscountAmount = false;
			_showPercentage = false;
			_showPriceRange = false;
			_showPromo = false;
			_themeDisplay = null;

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	public String getDiscountLabel() {
		return _discountLabel;
	}

	public String getFormattedFinalPrice() {
		return _formattedFinalPrice;
	}

	public String getPromoPriceLabel() {
		return _promoPriceLabel;
	}

	public int getQuantity() {
		return _quantity;
	}

	public boolean isDisplayOneLine() {
		return _displayOneLine;
	}

	public boolean isNetPrice() {
		return _netPrice;
	}

	public boolean isShowDiscount() {
		return _showDiscount;
	}

	public boolean isShowDiscountAmount() {
		return _showDiscountAmount;
	}

	public boolean isShowPercentage() {
		return _showPercentage;
	}

	public boolean isShowPriceRange() {
		return _showPriceRange;
	}

	public boolean isShowPromo() {
		return _showPromo;
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	public void setDiscountLabel(String discountLabel) {
		_discountLabel = discountLabel;
	}

	public void setDisplayOneLine(boolean displayOneLine) {
		_displayOneLine = displayOneLine;
	}

	public void setFormattedFinalPrice(String formattedFinalPrice) {
		_formattedFinalPrice = formattedFinalPrice;
	}

	public void setNetPrice(boolean netPrice) {
		_netPrice = netPrice;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		commerceChannelLocalService =
			ServletContextUtil.getCommerceChannelLocalService();
		commerceProductPriceCalculation =
			ServletContextUtil.getCommerceProductPriceCalculation();
		configurationProvider = ServletContextUtil.getConfigurationProvider();
		cpDefinitionHelper = ServletContextUtil.getCPDefinitionHelper();
		cpInstanceHelper = ServletContextUtil.getCPInstanceHelper();
		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPromoPriceLabel(String promoPriceLabel) {
		_promoPriceLabel = promoPriceLabel;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setShowDiscount(boolean showDiscount) {
		_showDiscount = showDiscount;
	}

	public void setShowDiscountAmount(boolean showDiscountAmount) {
		_showDiscountAmount = showDiscountAmount;
	}

	public void setShowPercentage(boolean showPercentage) {
		_showPercentage = showPercentage;
	}

	public void setShowPriceRange(boolean showPriceRange) {
		_showPriceRange = showPriceRange;
	}

	public void setShowPromo(boolean showPromo) {
		_showPromo = showPromo;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_commerceContext = null;
		_commerceDiscountValue = null;
		_cpDefinitionId = 0;
		_cpInstanceId = 0;
		_decimalFormat = null;
		_discountLabel = null;
		_displayDiscountLevels = false;
		_displayOneLine = false;
		_formattedFinalPrice = null;
		_formattedPrice = null;
		_formattedPromoPrice = null;
		_netPrice = true;
		_promoPriceLabel = null;
		_quantity = 0;
		_showDiscount = true;
		_showDiscountAmount = false;
		_showPercentage = true;
		_showPriceRange = true;
		_showPromo = true;
		_themeDisplay = null;
	}

	protected String getFormattedPrice(
			int quantity, CommerceContext commerceContext, Locale locale)
		throws PortalException {

		CommerceMoney minPriceCommerceMoney =
			commerceProductPriceCalculation.getUnitMinPrice(
				_cpDefinitionId, quantity, commerceContext);

		if (minPriceCommerceMoney == null) {
			return StringPool.BLANK;
		}

		if (!_showPriceRange) {
			return minPriceCommerceMoney.format(locale);
		}

		CommerceMoney maxPriceCommerceMoney =
			commerceProductPriceCalculation.getUnitMaxPrice(
				_cpDefinitionId, quantity, commerceContext);

		return minPriceCommerceMoney.format(locale) + " - " +
			maxPriceCommerceMoney.format(locale);
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"commerce-ui:price:commerceDiscountValue", _commerceDiscountValue);
		request.setAttribute("commerce-ui:price:cpInstanceId", _cpInstanceId);
		request.setAttribute("commerce-ui:price:decimalFormat", _decimalFormat);
		request.setAttribute("commerce-ui:price:discountLabel", _discountLabel);
		request.setAttribute(
			"commerce-ui:price:displayDiscountLevels", _displayDiscountLevels);
		request.setAttribute(
			"commerce-ui:price:displayOneLine", _displayOneLine);
		request.setAttribute(
			"commerce-ui:price:formattedFinalPrice", _formattedFinalPrice);
		request.setAttribute(
			"commerce-ui:price:formattedPrice", _formattedPrice);
		request.setAttribute(
			"commerce-ui:price:formattedPromoPrice", _formattedPromoPrice);
		request.setAttribute("commerce-ui:price:netPrice", _netPrice);
		request.setAttribute(
			"commerce-ui:price:promoPriceLabel", _promoPriceLabel);
		request.setAttribute("commerce-ui:price:showDiscount", _showDiscount);
		request.setAttribute(
			"commerce-ui:price:showDiscountAmount", _showDiscountAmount);
		request.setAttribute(
			"commerce-ui:price:showPercentage", _showPercentage);
		request.setAttribute(
			"commerce-ui:price:showPriceRange", _showPriceRange);
	}

	protected void setDecimalFormat() throws PortalException {
		RoundingTypeConfiguration roundingTypeConfiguration =
			configurationProvider.getConfiguration(
				RoundingTypeConfiguration.class,
				new SystemSettingsLocator(RoundingTypeConstants.SERVICE_NAME));

		_decimalFormat = new DecimalFormat();

		_decimalFormat.setMaximumFractionDigits(
			roundingTypeConfiguration.maximumFractionDigits());
		_decimalFormat.setMinimumFractionDigits(
			roundingTypeConfiguration.minimumFractionDigits());
		_decimalFormat.setRoundingMode(
			roundingTypeConfiguration.roundingMode());
	}

	protected void setDiscountInfo(
			CommerceProductPrice commerceProductPrice, Locale locale)
		throws PortalException {

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		_formattedFinalPrice = finalPriceCommerceMoney.format(locale);

		_commerceDiscountValue = commerceProductPrice.getDiscountValue();

		CommercePriceConfiguration commercePriceConfiguration =
			configurationProvider.getConfiguration(
				CommercePriceConfiguration.class,
				new SystemSettingsLocator(
					CommerceConstants.PRICE_SERVICE_NAME));

		_displayDiscountLevels =
			commercePriceConfiguration.displayDiscountLevels();

		setDecimalFormat();
	}

	protected void setPriceInfo(CommerceContext commerceContext, Locale locale)
		throws PortalException {

		_formattedPromoPrice = StringPool.BLANK;

		if (_cpInstanceId <= 0) {
			_formattedPrice = getFormattedPrice(
				_quantity, commerceContext, locale);
		}
		else {
			CommerceProductPrice commerceProductPrice =
				commerceProductPriceCalculation.getCommerceProductPrice(
					_cpInstanceId, _quantity, commerceContext);

			if (commerceProductPrice == null) {
				return;
			}

			CommerceMoney unitPriceCommerceMoney =
				commerceProductPrice.getUnitPrice();

			_formattedPrice = unitPriceCommerceMoney.format(locale);

			_formattedPromoPrice = StringPool.BLANK;

			if (_showPromo) {
				CommerceMoney unitPromoPriceCommerceMoney =
					commerceProductPrice.getUnitPromoPrice();

				BigDecimal promoPrice = unitPromoPriceCommerceMoney.getPrice();

				if ((promoPrice != null) &&
					(promoPrice.compareTo(unitPriceCommerceMoney.getPrice()) <
						0)) {

					_formattedPromoPrice = unitPromoPriceCommerceMoney.format(
						locale);
				}
			}

			if (_showDiscount) {
				setDiscountInfo(commerceProductPrice, locale);
			}
		}
	}

	protected void setProductInfo() throws Exception {
		CommerceAccount commerceAccount = _commerceContext.getCommerceAccount();

		CPCatalogEntry cpCatalogEntry = cpDefinitionHelper.getCPCatalogEntry(
			commerceAccount.getCommerceAccountId(),
			_themeDisplay.getScopeGroupId(), _cpDefinitionId,
			_themeDisplay.getLocale());

		if (_quantity <= 0) {
			CPDefinitionInventory cpDefinitionInventory =
				CPDefinitionInventoryLocalServiceUtil.
					fetchCPDefinitionInventoryByCPDefinitionId(_cpDefinitionId);

			if (cpDefinitionInventory != null) {
				_quantity = cpDefinitionInventory.getMinOrderQuantity();
			}
			else {
				_quantity =
					CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;
			}
		}

		if ((_cpInstanceId <= 0) && cpCatalogEntry.isIgnoreSKUCombinations()) {
			CPInstance cpInstance = cpInstanceHelper.getDefaultCPInstance(
				_cpDefinitionId);

			_cpInstanceId = cpInstance.getCPInstanceId();
		}
	}

	protected CommerceChannelLocalService commerceChannelLocalService;
	protected CommerceProductPriceCalculation commerceProductPriceCalculation;
	protected ConfigurationProvider configurationProvider;
	protected CPDefinitionHelper cpDefinitionHelper;
	protected CPInstanceHelper cpInstanceHelper;

	private static final String _PAGE = "/price/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(PriceTag.class);

	private CommerceContext _commerceContext;
	private CommerceDiscountValue _commerceDiscountValue;
	private long _cpDefinitionId;
	private long _cpInstanceId;
	private DecimalFormat _decimalFormat;
	private String _discountLabel;
	private boolean _displayDiscountLevels;
	private boolean _displayOneLine;
	private String _formattedFinalPrice;
	private String _formattedPrice;
	private String _formattedPromoPrice;
	private boolean _netPrice = true;
	private String _promoPriceLabel;
	private int _quantity;
	private boolean _showDiscount = true;
	private boolean _showDiscountAmount;
	private boolean _showPercentage = true;
	private boolean _showPriceRange = true;
	private boolean _showPromo = true;
	private ThemeDisplay _themeDisplay;

}