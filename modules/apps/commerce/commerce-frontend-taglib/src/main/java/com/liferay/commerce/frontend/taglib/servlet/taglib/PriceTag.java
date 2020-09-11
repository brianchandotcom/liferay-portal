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
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.Objects;

import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class PriceTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		CommerceContext commerceContext = (CommerceContext)request.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Map<String, Object> context = getContext();

			long cpDefinitionId = (Long)context.getOrDefault(
				"CPDefinitionId", 0);

			long cpInstanceId = (Long)context.getOrDefault("CPInstanceId", 0);

			int quantity = (Integer)context.getOrDefault("quantity", 1);

			if (quantity <= 0) {
				ProductSettingsModel productSettingsModel =
					_productHelper.getProductSettingsModel(cpInstanceId);

				quantity = productSettingsModel.getMinQuantity();
			}

			PriceModel priceModel = null;

			if (cpInstanceId > 0) {
				priceModel = _productHelper.getPrice(
					cpInstanceId, quantity, commerceContext,
					themeDisplay.getLocale());
			}
			else {
				priceModel = _productHelper.getMinPrice(
					cpDefinitionId, commerceContext, themeDisplay.getLocale());
			}

			CommercePriceConfiguration commercePriceConfiguration =
				_configurationProvider.getConfiguration(
					CommercePriceConfiguration.class,
					new SystemSettingsLocator(
						CommerceConstants.PRICE_SERVICE_NAME));

			putValue(
				"displayDiscountLevels",
				commercePriceConfiguration.displayDiscountLevels());

			putValue("prices", priceModel);

			boolean netPrice = true;

			CommerceChannel commerceChannel =
				_commerceChannelService.fetchCommerceChannel(
					commerceContext.getCommerceChannelId());

			if ((commerceChannel != null) &&
				Objects.equals(
					commerceChannel.getPriceDisplayType(),
					CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

				netPrice = false;
			}

			putValue("netPrice", netPrice);

			setTemplateNamespace("Price.render");
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = ServletContextUtil.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"commerce-frontend-taglib/price/Price.es");
	}

	public void setAdditionalDiscountClasses(String additionalDiscountClasses) {
		putValue("additionalDiscountClasses", additionalDiscountClasses);
	}

	public void setAdditionalPriceClasses(String additionalPriceClasses) {
		putValue("additionalPriceClasses", additionalPriceClasses);
	}

	public void setAdditionalPromoPriceClasses(
		String additionalPromoPriceClasses) {

		putValue("additionalPromoPriceClasses", additionalPromoPriceClasses);
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		putValue("CPDefinitionId", cpDefinitionId);
	}

	public void setCPInstanceId(long cpInstanceId) {
		putValue("CPInstanceId", cpInstanceId);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		_commerceChannelService =
			ServletContextUtil.getCommerceChannelService();
		_configurationProvider = ServletContextUtil.getConfigurationProvider();
		_productHelper = ServletContextUtil.getProductHelper();
	}

	public void setQuantity(String quantity) {
		putValue("quantity", quantity);
	}

	private static final Log _log = LogFactoryUtil.getLog(PriceTag.class);

	private CommerceChannelService _commerceChannelService;
	private ConfigurationProvider _configurationProvider;
	private ProductHelper _productHelper;

}