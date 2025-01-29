/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration;
import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.configuration.CommercePriceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.util.CommerceOrderInfoItemUtil;
import com.liferay.friendly.url.provider.FriendlyURLSeparatorProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Gianmarco Brunialti Masera
 */
public class MiniCartTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest httpServletRequest = getRequest();

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (commerceContext == null) {
				_commerceChannelId = 0;
				_commerceChannelGroupId = 0;
				_checkoutURL = StringPool.BLANK;
				_itemsQuantity = 0;
				_orderDetailURL = StringPool.BLANK;
				_orderId = 0;

				return super.doStartTag();
			}

			AccountEntry accountEntry = commerceContext.getAccountEntry();

			if (accountEntry != null) {
				_accountEntryId = accountEntry.getAccountEntryId();
			}

			PortletURL commerceCheckoutPortletURL =
				PortletProviderUtil.getPortletURL(
					httpServletRequest, CommercePortletKeys.COMMERCE_CHECKOUT,
					PortletProvider.Action.VIEW);

			if (commerceCheckoutPortletURL != null) {
				_checkoutURL = PortletURLBuilder.create(
					commerceCheckoutPortletURL
				).setMVCRenderCommandName(
					"/commerce_checkout/checkout_redirect"
				).buildString();
			}

			_commerceChannelId = commerceContext.getCommerceChannelId();
			_commerceChannelGroupId =
				commerceContext.getCommerceChannelGroupId();

			CommerceCurrency commerceCurrency =
				commerceContext.getCommerceCurrency();

			_commerceCurrencyCode = commerceCurrency.getCode();

			if (accountEntry != null) {
				_guestOrderEnabled = _isGuestOrderEnabled(
					accountEntry, _commerceChannelGroupId);
			}

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				_itemsQuantity = _getItemsQuantity(
					commerceOrder, httpServletRequest);
			}

			_orderDetailURL =
				_commerceOrderHttpHelper.getCommerceCartPortletURL(
					httpServletRequest, commerceOrder);

			if (commerceOrder != null) {
				_orderId = commerceOrder.getCommerceOrderId();
			}

			_requestQuoteEnabled = _isRequestQuoteEnabled();

			_siteDefaultURL = _getSiteDefaultURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			_commerceChannelId = 0;
			_commerceChannelGroupId = 0;
			_checkoutURL = StringPool.BLANK;
			_guestOrderEnabled = false;
			_itemsQuantity = 0;
			_orderDetailURL = StringPool.BLANK;
			_orderId = 0;
		}

		CPFriendlyURL cpFriendlyURL = ServletContextUtil.getCPFriendlyURL();

		_productURLSeparator = cpFriendlyURL.getProductURLSeparator(
			themeDisplay.getCompanyId());

		if (_guestOrderEnabled && !themeDisplay.isSignedIn()) {
			_signInURL = themeDisplay.getURLSignIn();
		}

		return super.doStartTag();
	}

	public String getCssClasses() {
		return _cssClasses;
	}

	public Map<String, String> getLabels() {
		return _labels;
	}

	public Map<String, String> getViews() {
		return _views;
	}

	public boolean isDisplayTotalItemsQuantity() {
		return _displayTotalItemsQuantity;
	}

	public boolean isToggleable() {
		return _toggleable;
	}

	public void setCssClasses(String cssClasses) {
		_cssClasses = cssClasses;
	}

	public void setDisplayTotalItemsQuantity(
		boolean displayTotalItemsQuantity) {

		_displayTotalItemsQuantity = displayTotalItemsQuantity;
	}

	public void setLabels(Map<String, String> labels) {
		_labels = labels;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());

		_configurationProvider = ServletContextUtil.getConfigurationProvider();
		_commerceOrderHttpHelper =
			ServletContextUtil.getCommerceOrderHttpHelper();
	}

	public void setToggleable(boolean toggleable) {
		_toggleable = toggleable;
	}

	public void setViews(Map<String, String> views) {
		_views = views;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_accountEntryId = AccountConstants.ACCOUNT_ENTRY_ID_ANY;
		_checkoutURL = StringPool.BLANK;
		_commerceChannelGroupId = 0;
		_commerceChannelId = 0;
		_commerceCurrencyCode = StringPool.BLANK;
		_commerceOrderHttpHelper = null;
		_configurationProvider = null;
		_cssClasses = StringPool.BLANK;
		_displayTotalItemsQuantity = false;
		_guestOrderEnabled = false;
		_itemsQuantity = 0;
		_labels = new HashMap<>();
		_orderDetailURL = StringPool.BLANK;
		_orderId = 0;
		_productURLSeparator = StringPool.BLANK;
		_requestQuoteEnabled = false;
		_signInURL = StringPool.BLANK;
		_siteDefaultURL = StringPool.BLANK;
		_toggleable = true;
		_views = new HashMap<>();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:accountEntryId", _accountEntryId);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:cartViews", _views);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:checkoutURL", _checkoutURL);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:commerceChannelGroupId",
			_commerceChannelGroupId);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:commerceChannelId", _commerceChannelId);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:commerceCurrencyCode",
			_commerceCurrencyCode);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:cssClasses", _cssClasses);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:displayDiscountLevels",
			_isDisplayDiscountLevels());
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:displayTotalItemsQuantity",
			_displayTotalItemsQuantity);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:guestOrderEnabled", _guestOrderEnabled);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:hasCommerceOpenOrderContentPortlet",
			_hasCommerceOpenOrderContentPortlet(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:itemsQuantity", _itemsQuantity);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:labels", _labels);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:orderDetailURL", _orderDetailURL);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:orderId", _orderId);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:productURLSeparator", _productURLSeparator);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:requestQuoteEnabled", _requestQuoteEnabled);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:signInURL", _signInURL);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:siteDefaultURL", _siteDefaultURL);
		httpServletRequest.setAttribute(
			"liferay-commerce:cart:toggleable", _toggleable);
	}

	private int _getItemsQuantity(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest)
		throws PortalException {

		if (_displayTotalItemsQuantity) {
			BigDecimal quantity =
				_commerceOrderHttpHelper.getCommerceOrderItemsQuantity(
					httpServletRequest);

			return quantity.intValue();
		}

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		return commerceOrderItems.size();
	}

	private String _getSiteDefaultURL(ThemeDisplay themeDisplay)
		throws PortalException {

		if (_hasCommerceOpenOrderContentPortlet(themeDisplay.getRequest())) {
			PortletURL portletURL = null;

			long plid = PortalUtil.getPlidFromPortletId(
				themeDisplay.getScopeGroupId(),
				CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

			if (plid > 0) {
				portletURL = PortletURLFactoryUtil.create(
					themeDisplay.getRequest(),
					CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT, plid,
					PortletRequest.RENDER_PHASE);
			}
			else {
				portletURL = PortletURLFactoryUtil.create(
					themeDisplay.getRequest(),
					CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
					PortletRequest.RENDER_PHASE);
			}

			portletURL.setParameter(
				"mvcRenderCommandName",
				"/commerce_open_order_content/edit_commerce_order");
			portletURL.setParameter(
				"backURL",
				ParamUtil.getString(themeDisplay.getRequest(), "backURL"));

			return HtmlUtil.escape(portletURL.toString());
		}

		return HtmlUtil.escape(
			CommerceOrderInfoItemUtil.getCommerceOrderFriendlyURL(
				_friendlyURLSeparatorProviderSnapshot.get(),
				themeDisplay.getRequest()));
	}

	private boolean _hasCommerceOpenOrderContentPortlet(
		HttpServletRequest httpServletRequest) {

		try {
			long plid = PortalUtil.getPlidFromPortletId(
				PortalUtil.getScopeGroupId(httpServletRequest),
				CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

			if (plid > 0) {
				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	private boolean _isDisplayDiscountLevels() {
		try {
			CommercePriceConfiguration commercePriceConfiguration =
				_configurationProvider.getConfiguration(
					CommercePriceConfiguration.class,
					new SystemSettingsLocator(
						CommerceConstants.SERVICE_NAME_COMMERCE_PRICE));

			return commercePriceConfiguration.displayDiscountLevels();
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			return false;
		}
	}

	private boolean _isGuestOrderEnabled(
			AccountEntry accountEntry, long commerceChannelGroupId)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35678") ||
			!accountEntry.isGuestAccount()) {

			return false;
		}

		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannelGroupId,
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		return commerceOrderCheckoutConfiguration.guestCheckoutEnabled();
	}

	private boolean _isRequestQuoteEnabled() throws PortalException {
		CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderFieldsConfiguration.class,
				new GroupServiceSettingsLocator(
					_commerceChannelGroupId,
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER_FIELDS));

		return commerceOrderFieldsConfiguration.requestQuoteEnabled();
	}

	private static final String _PAGE = "/mini_cart/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(MiniCartTag.class);

	private static final Snapshot<FriendlyURLSeparatorProvider>
		_friendlyURLSeparatorProviderSnapshot = new Snapshot<>(
			MiniCartTag.class, FriendlyURLSeparatorProvider.class);

	private long _accountEntryId = AccountConstants.ACCOUNT_ENTRY_ID_ANY;
	private String _checkoutURL = StringPool.BLANK;
	private long _commerceChannelGroupId;
	private long _commerceChannelId;
	private String _commerceCurrencyCode = StringPool.BLANK;
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private ConfigurationProvider _configurationProvider;
	private String _cssClasses = StringPool.BLANK;
	private boolean _displayTotalItemsQuantity;
	private boolean _guestOrderEnabled;
	private int _itemsQuantity;
	private Map<String, String> _labels = new HashMap<>();
	private String _orderDetailURL = StringPool.BLANK;
	private long _orderId;
	private String _productURLSeparator = StringPool.BLANK;
	private boolean _requestQuoteEnabled;
	private String _signInURL = StringPool.BLANK;
	private String _siteDefaultURL = StringPool.BLANK;
	private boolean _toggleable = true;
	private Map<String, String> _views = new HashMap<>();

}