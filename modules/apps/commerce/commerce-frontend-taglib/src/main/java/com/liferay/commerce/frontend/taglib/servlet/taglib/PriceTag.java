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

import com.liferay.commerce.configuration.CommercePriceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 * @author Alec Sloan
 */
public class PriceTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			_commerceContext = (CommerceContext)request.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);
			_themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (_quantity <= 0) {
				ProductSettingsModel productSettingsModel =
					productHelper.getProductSettingsModel(_cpInstanceId);

				_quantity = productSettingsModel.getMinQuantity();
			}

			PriceModel priceModel = null;

			if (_cpInstanceId > 0) {
				priceModel = productHelper.getPriceModel(
					_cpInstanceId, _quantity, _commerceContext,
					StringPool.BLANK, _themeDisplay.getLocale());
			}
			else {
				priceModel = productHelper.getMinPrice(
					_cpDefinitionId, _commerceContext,
					_themeDisplay.getLocale());
			}

			CommercePriceConfiguration commercePriceConfiguration =
				configurationProvider.getConfiguration(
					CommercePriceConfiguration.class,
					new SystemSettingsLocator(
						CommerceConstants.PRICE_SERVICE_NAME));

			_displayDiscountLevels =
				commercePriceConfiguration.displayDiscountLevels();

			_prices = priceModel;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_additionalDiscountClasses = null;
			_additionalPriceClasses = null;
			_additionalPromoPriceClasses = null;
			_commerceContext = null;
			_commerceDiscountValue = null;
			_displayDiscountLevels = false;
			_themeDisplay = null;

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public String getAdditionalDiscountClasses() {
		return _additionalDiscountClasses;
	}

	public String getAdditionalPriceClasses() {
		return _additionalPriceClasses;
	}

	public String getAdditionalPromoPriceClasses() {
		return _additionalPromoPriceClasses;
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String setAdditionalDiscountClasses(
		String additionalDiscountClasses) {

		return _additionalDiscountClasses = additionalDiscountClasses;
	}

	public String setAdditionalPriceClasses(String additionalPriceClasses) {
		return _additionalPriceClasses = additionalPriceClasses;
	}

	public String setAdditionalPromoPriceClasses(
		String additionalPromoPriceClasses) {

		return _additionalPromoPriceClasses = additionalPromoPriceClasses;
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionId = cpDefinitionId;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		commerceChannelLocalService =
			ServletContextUtil.getCommerceChannelLocalService();
		configurationProvider = ServletContextUtil.getConfigurationProvider();
		productHelper = ServletContextUtil.getProductHelper();
		servletContext = ServletContextUtil.getServletContext();
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_additionalDiscountClasses = null;
		_additionalPriceClasses = null;
		_additionalPromoPriceClasses = null;
		_commerceContext = null;
		_commerceDiscountValue = null;
		_cpDefinitionId = 0;
		_cpInstanceId = 0;
		_displayDiscountLevels = true;
		_prices = null;
		_quantity = 0;
		_themeDisplay = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"commerce-ui:price:additionalDiscountClasses",
			_additionalDiscountClasses);
		request.setAttribute(
			"commerce-ui:price:additionalPriceClasses",
			_additionalPriceClasses);
		request.setAttribute(
			"commerce-ui:price:additionalPromoPriceClasses",
			_additionalPromoPriceClasses);
		request.setAttribute(
			"commerce-ui:price:commerceDiscountValue", _commerceDiscountValue);
		request.setAttribute(
			"commerce-ui:price:cpDefinitionId", _cpDefinitionId);
		request.setAttribute(
			"commerce-ui:price:displayDiscountLevels", _displayDiscountLevels);
		request.setAttribute("commerce-ui:price:prices", _prices);
	}

	protected CommerceChannelLocalService commerceChannelLocalService;
	protected ConfigurationProvider configurationProvider;
	protected ProductHelper productHelper;

	private static final String _PAGE = "/price/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(PriceTag.class);

	private String _additionalDiscountClasses;
	private String _additionalPriceClasses;
	private String _additionalPromoPriceClasses;
	private CommerceContext _commerceContext;
	private CommerceDiscountValue _commerceDiscountValue;
	private long _cpDefinitionId;
	private long _cpInstanceId;
	private boolean _displayDiscountLevels = true;
	private PriceModel _prices;
	private int _quantity;
	private ThemeDisplay _themeDisplay;

}