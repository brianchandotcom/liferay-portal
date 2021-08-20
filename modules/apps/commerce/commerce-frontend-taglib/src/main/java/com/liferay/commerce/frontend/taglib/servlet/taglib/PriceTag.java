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
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Objects;

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
		CommerceContext commerceContext =
			(CommerceContext)getRequest().getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		try {
			long cpInstanceId = 0;

			List<CPSku> cpSkus = _cpCatalogEntry.getCPSkus();

			if (cpSkus.size() == 1) {
				CPSku cpSku = cpSkus.get(0);

				cpInstanceId = cpSku.getCPInstanceId();
			}

			if (_quantity <= 0) {
				ProductSettingsModel productSettingsModel =
					_productHelper.getProductSettingsModel(cpInstanceId);

				_quantity = productSettingsModel.getMinQuantity();
			}

			_displayDiscountLevels = _isDisplayDiscountLevels();
			_netPrice = _isNetPrice(commerceContext.getCommerceChannelId());
			_priceModel = _getPriceModel(commerceContext, cpInstanceId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public CPCatalogEntry getCPCatalogEntry() {
		return _cpCatalogEntry;
	}

	public String getNamespace() {
		return _namespace;
	}

	public int getQuantity() {
		return _quantity;
	}

	public boolean isCompact() {
		return _compact;
	}

	public void setCompact(boolean compact) {
		_compact = compact;
	}

	public void setCPCatalogEntry(CPCatalogEntry cpCatalogEntry) {
		_cpCatalogEntry = cpCatalogEntry;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());

		commerceChannelLocalService =
			ServletContextUtil.getCommerceChannelLocalService();
		configurationProvider = ServletContextUtil.getConfigurationProvider();
		_productHelper = ServletContextUtil.getProductHelper();
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_compact = false;
		_cpCatalogEntry = null;
		_displayDiscountLevels = false;
		_namespace = StringPool.BLANK;
		_netPrice = true;
		_priceModel = null;
		_productHelper = null;
		_quantity = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("commerce-ui:price:compact", _compact);
		httpServletRequest.setAttribute(
			"commerce-ui:price:displayDiscountLevels", _displayDiscountLevels);
		httpServletRequest.setAttribute(
			"commerce-ui:price:namespace", _namespace);
		httpServletRequest.setAttribute(
			"commerce-ui:price:netPrice", _netPrice);
		httpServletRequest.setAttribute(
			"commerce-ui:price:priceModel", _priceModel);
	}

	protected CommerceChannelLocalService commerceChannelLocalService;
	protected ConfigurationProvider configurationProvider;

	private PriceModel _getPriceModel(
			CommerceContext commerceContext, long cpInstanceId)
		throws PortalException {

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (cpInstanceId > 0) {
			return _productHelper.getPriceModel(
				cpInstanceId, _quantity, commerceContext, StringPool.BLANK,
				themeDisplay.getLocale());
		}

		return _productHelper.getMinPrice(
			_cpCatalogEntry.getCPDefinitionId(), commerceContext,
			themeDisplay.getLocale());
	}

	private boolean _isDisplayDiscountLevels() throws ConfigurationException {
		CommercePriceConfiguration commercePriceConfiguration =
			configurationProvider.getConfiguration(
				CommercePriceConfiguration.class,
				new SystemSettingsLocator(
					CommerceConstants.SERVICE_NAME_COMMERCE_PRICE));

		return commercePriceConfiguration.displayDiscountLevels();
	}

	private boolean _isNetPrice(long commerceChannelId) {
		CommerceChannel commerceChannel =
			commerceChannelLocalService.fetchCommerceChannel(commerceChannelId);

		if ((commerceChannel != null) &&
			Objects.equals(
				commerceChannel.getPriceDisplayType(),
				CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

			return false;
		}

		return true;
	}

	private static final String _PAGE = "/price/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(PriceTag.class);

	private boolean _compact;
	private CPCatalogEntry _cpCatalogEntry;
	private boolean _displayDiscountLevels;
	private String _namespace = StringPool.BLANK;
	private boolean _netPrice = true;
	private PriceModel _priceModel;
	private ProductHelper _productHelper;
	private int _quantity;

}