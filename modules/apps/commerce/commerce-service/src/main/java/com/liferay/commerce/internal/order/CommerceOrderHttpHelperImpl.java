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

package com.liferay.commerce.internal.order;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = CommerceOrderHttpHelper.class)
public class CommerceOrderHttpHelperImpl implements CommerceOrderHttpHelper {

	@Override
	public CommerceOrder addCommerceOrder(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceContext commerceContext = _getCommerceContext(
			httpServletRequest);

		CommerceOrder commerceOrder = null;

		long commerceCurrencyId = 0;

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		if (commerceCurrency != null) {
			commerceCurrencyId = commerceCurrency.getCommerceCurrencyId();
		}

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		long commerceChannelGroupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId());

		if (commerceAccount != null) {
			commerceOrder = _commerceOrderService.addCommerceOrder(
				themeDisplay.getUserId(), commerceChannelGroupId,
				commerceAccount.getCommerceAccountId(), commerceCurrencyId);
		}

		if (commerceAccount == null) {
			throw new CommerceOrderValidatorException(
				Collections.singletonList(
					new CommerceOrderValidatorResult(
						false,
						_getLocalizedMessage(
							_portal.getLocale(httpServletRequest),
							"please-select-a-valid-account"))));
		}

		setCurrentCommerceOrder(httpServletRequest, commerceOrder);

		return commerceOrder;
	}

	@Override
	public PortletURL getCommerceCartPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceOrder commerceOrder = getCurrentCommerceOrder(
			httpServletRequest);

		return getCommerceCartPortletURL(httpServletRequest, commerceOrder);
	}

	@Override
	public PortletURL getCommerceCartPortletURL(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		return getCommerceCartPortletURL(
			groupId, httpServletRequest, commerceOrder);
	}

	@Override
	public PortletURL getCommerceCartPortletURL(
			long groupId, HttpServletRequest httpServletRequest,
			CommerceOrder commerceOrder)
		throws PortalException {

		long plid = _portal.getPlidFromPortletId(
			groupId, CommercePortletKeys.COMMERCE_ORDER_CONTENT);

		if ((plid > 0) && (commerceOrder != null) && !commerceOrder.isOpen()) {
			PortletURL portletURL = _getPortletURL(
				groupId, httpServletRequest,
				CommercePortletKeys.COMMERCE_ORDER_CONTENT);

			if (commerceOrder != null) {
				portletURL.setParameter(
					"mvcRenderCommandName", "viewCommerceOrderDetails");
				portletURL.setParameter(
					"commerceOrderUuid",
					String.valueOf(commerceOrder.getUuid()));
			}

			return portletURL;
		}

		plid = _portal.getPlidFromPortletId(
			groupId, CommercePortletKeys.COMMERCE_CART_CONTENT);

		if (plid > 0) {
			return _getPortletURL(
				groupId, httpServletRequest,
				CommercePortletKeys.COMMERCE_CART_CONTENT);
		}

		plid = _portal.getPlidFromPortletId(
			groupId, CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

		if ((plid > 0) && (commerceOrder != null) && commerceOrder.isOpen()) {
			PortletURL portletURL = _getPortletURL(
				groupId, httpServletRequest,
				CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

			if (commerceOrder != null) {
				portletURL.setParameter(
					"mvcRenderCommandName", "editCommerceOrder");
				portletURL.setParameter(
					"commerceOrderUuid",
					String.valueOf(commerceOrder.getUuid()));
			}

			return portletURL;
		}

		return null;
	}

	@Override
	public PortletURL getCommerceCheckoutPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = _getPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_CHECKOUT);

		CommerceOrder commerceOrder = getCurrentCommerceOrder(
			httpServletRequest);

		if ((commerceOrder != null) && commerceOrder.isGuestOrder()) {
			Layout currentLayout = (Layout)httpServletRequest.getAttribute(
				WebKeys.LAYOUT);

			String friendlyURL = StringPool.FORWARD_SLASH + "authentication";

			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				_portal.getScopeGroupId(httpServletRequest), false,
				friendlyURL);

			if (!friendlyURL.equals(currentLayout.getFriendlyURL()) &&
				(layout != null)) {

				portletURL = _portletURLFactory.create(
					httpServletRequest,
					"com_liferay_login_web_portlet_LoginPortlet", layout,
					PortletRequest.RENDER_PHASE);
			}
			else {
				portletURL.setParameter(
					"continueAsGuest", Boolean.TRUE.toString());

				Cookie cookie = new Cookie(
					"continueAsGuest", Boolean.TRUE.toString());

				String domain = CookieKeys.getDomain(httpServletRequest);

				if (Validator.isNotNull(domain)) {
					cookie.setDomain(domain);
				}

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				cookie.setMaxAge(CookieKeys.MAX_AGE);
				cookie.setPath(StringPool.SLASH);

				CookieKeys.addCookie(
					themeDisplay.getRequest(), themeDisplay.getResponse(),
					cookie);
			}
		}

		return portletURL;
	}

	@Override
	public int getCommerceOrderItemsQuantity(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceOrder commerceOrder = getCurrentCommerceOrder(
			httpServletRequest);

		if (commerceOrder == null) {
			return 0;
		}

		return _commerceOrderItemService.getCommerceOrderItemsQuantity(
			commerceOrder.getCommerceOrderId());
	}

	@Override
	public String getCookieName(long groupId) {
		return CommerceOrder.class.getName() + StringPool.POUND + groupId;
	}

	@Override
	public CommerceOrder getCurrentCommerceOrder(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CommerceOrder commerceOrder = _getCurrentCommerceOrder(
			commerceAccount.getCommerceAccountId(), themeDisplay);

		if ((commerceOrder != null) && commerceOrder.isGuestOrder()) {
			commerceOrder = _checkGuestOrder(
				themeDisplay, commerceContext, commerceOrder);
		}

		if (((commerceOrder != null) && !commerceOrder.isOpen()) ||
			((commerceOrder != null) &&
			 !_commerceOrderModelResourcePermission.contains(
				 themeDisplay.getPermissionChecker(), commerceOrder,
				 ActionKeys.UPDATE))) {

			return null;
		}

		return commerceOrder;
	}

	@Override
	public boolean isGuestCheckoutEnabled(HttpServletRequest httpServletRequest)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		groupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				groupId);

		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					groupId, CommerceConstants.ORDER_SERVICE_NAME));

		return commerceOrderCheckoutConfiguration.guestCheckoutEnabled();
	}

	@Override
	public void setCurrentCommerceOrder(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		commerceOrder = _commerceOrderLocalService.recalculatePrice(
			commerceOrder.getCommerceOrderId(),
			_getCommerceContext(httpServletRequest));

		if (!themeDisplay.isSignedIn()) {
			_setGuestCommerceOrder(themeDisplay, commerceOrder);

			return;
		}

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(
			getCookieName(commerceOrder.getGroupId()), commerceOrder.getUuid());
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<CommerceOrder> modelResourcePermission) {

		_commerceOrderModelResourcePermission = modelResourcePermission;
	}

	private CommerceOrder _checkGuestOrder(
			ThemeDisplay themeDisplay, CommerceContext commerceContext,
			CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder == null) {
			return null;
		}

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount == null) {
			return null;
		}

		User user = themeDisplay.getUser();

		if ((user == null) || user.isDefaultUser()) {
			return commerceOrder;
		}

		String domain = CookieKeys.getDomain(themeDisplay.getRequest());

		String commerceOrderUuidWebKey = getCookieName(
			commerceOrder.getGroupId());

		// Remove thread local order when used

		CommerceOrder threadLocalCommerceOrder =
			_commerceOrderUuidThreadLocal.get();

		if ((threadLocalCommerceOrder != null) &&
			threadLocalCommerceOrder.isGuestOrder()) {

			CookieKeys.deleteCookies(
				themeDisplay.getRequest(), themeDisplay.getResponse(), domain,
				commerceOrderUuidWebKey);

			_commerceOrderUuidThreadLocal.remove();
		}

		long commerceChannelGroupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId());

		CommerceOrder userCommerceOrder =
			_commerceOrderService.fetchCommerceOrder(
				commerceAccount.getCommerceAccountId(), commerceChannelGroupId,
				themeDisplay.getUserId(),
				CommerceOrderConstants.ORDER_STATUS_OPEN);

		if (userCommerceOrder == null) {
			CookieKeys.deleteCookies(
				themeDisplay.getRequest(), themeDisplay.getResponse(), domain,
				commerceOrderUuidWebKey);

			return _commerceOrderLocalService.updateAccount(
				commerceOrder.getCommerceOrderId(), user.getUserId(),
				commerceAccount.getCommerceAccountId());
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			themeDisplay.getRequest());

		_commerceOrderService.mergeGuestCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			userCommerceOrder.getCommerceOrderId(),
			_getCommerceContext(themeDisplay.getRequest()), serviceContext);

		CookieKeys.deleteCookies(
			themeDisplay.getRequest(), themeDisplay.getResponse(), domain,
			commerceOrderUuidWebKey);

		return userCommerceOrder;
	}

	private CommerceContext _getCommerceContext(
		HttpServletRequest httpServletRequest) {

		return (CommerceContext)httpServletRequest.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);
	}

	private CommerceOrder _getCurrentCommerceOrder(
			long commerceAccountId, ThemeDisplay themeDisplay)
		throws PortalException {

		CommerceOrder commerceOrder = _commerceOrderUuidThreadLocal.get();

		if (commerceOrder != null) {
			return commerceOrder;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				themeDisplay.getScopeGroupId());

		if (commerceChannel == null) {
			return null;
		}

		if (commerceAccountId != CommerceAccountConstants.ACCOUNT_ID_GUEST) {
			String cookieName = getCookieName(commerceChannel.getGroupId());

			String commerceOrderUuid = CookieKeys.getCookie(
				themeDisplay.getRequest(), cookieName, true);

			commerceOrder =
				_commerceOrderLocalService.fetchCommerceOrderByUuidAndGroupId(
					commerceOrderUuid, commerceChannel.getGroupId());

			if (commerceOrder == null) {
				commerceOrder = _commerceOrderService.fetchCommerceOrder(
					commerceAccountId, commerceChannel.getGroupId(),
					themeDisplay.getUserId(),
					CommerceOrderConstants.ORDER_STATUS_OPEN);
			}

			if (commerceOrder != null) {
				_validateCommerceOrderItemVersions(commerceOrder, themeDisplay);

				_commerceOrderUuidThreadLocal.set(commerceOrder);

				return commerceOrder;
			}
		}

		String cookieName = getCookieName(commerceChannel.getGroupId());

		String commerceOrderUuid = CookieKeys.getCookie(
			themeDisplay.getRequest(), cookieName, true);

		if (Validator.isNotNull(commerceOrderUuid)) {
			commerceOrder =
				_commerceOrderLocalService.fetchCommerceOrderByUuidAndGroupId(
					commerceOrderUuid, commerceChannel.getGroupId());

			if (commerceOrder != null) {
				_commerceOrderUuidThreadLocal.set(commerceOrder);
			}
		}

		return commerceOrder;
	}

	private String _getLocalizedMessage(Locale locale, String key) {
		if (locale == null) {
			return key;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, key);
	}

	private PortletURL _getPortletURL(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		return _getPortletURL(groupId, httpServletRequest, portletId);
	}

	private PortletURL _getPortletURL(
			long groupId, HttpServletRequest httpServletRequest,
			String portletId)
		throws PortalException {

		PortletURL portletURL = null;

		long plid = _portal.getPlidFromPortletId(groupId, portletId);

		if (plid > 0) {
			portletURL = _portletURLFactory.create(
				httpServletRequest, portletId, plid,
				PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = _portletURLFactory.create(
				httpServletRequest, portletId, PortletRequest.RENDER_PHASE);
		}

		return portletURL;
	}

	private void _setGuestCommerceOrder(
			ThemeDisplay themeDisplay, CommerceOrder commerceOrder)
		throws PortalException {

		User user = themeDisplay.getUser();

		if ((user != null) && !user.isDefaultUser()) {
			return;
		}

		long commerceChannelGroupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId());

		String commerceOrderUuidWebKey = getCookieName(commerceChannelGroupId);

		Cookie cookie = new Cookie(
			commerceOrderUuidWebKey, commerceOrder.getUuid());

		String domain = CookieKeys.getDomain(themeDisplay.getRequest());

		if (Validator.isNotNull(domain)) {
			cookie.setDomain(domain);
		}

		cookie.setMaxAge(CookieKeys.MAX_AGE);
		cookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(
			themeDisplay.getRequest(), themeDisplay.getResponse(), cookie);
	}

	private void _validateCommerceOrderItemVersions(
			CommerceOrder commerceOrder, ThemeDisplay themeDisplay)
		throws PortalException {

		VersionCommerceOrderValidatorImpl versionCommerceOrderValidatorImpl =
			new VersionCommerceOrderValidatorImpl();

		versionCommerceOrderValidatorImpl.setCommerceOrderItemLocalService(
			_commerceOrderItemLocalService);

		versionCommerceOrderValidatorImpl.setCPInstanceLocalService(
			_cpInstanceLocalService);

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			versionCommerceOrderValidatorImpl.validate(
				themeDisplay.getLocale(), commerceOrderItem);
		}
	}

	private static ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;
	private static final ThreadLocal<CommerceOrder>
		_commerceOrderUuidThreadLocal = new CentralizedThreadLocal<>(
			CommerceOrderHttpHelperImpl.class.getName());

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}