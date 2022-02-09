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

package com.liferay.commerce.internal.events;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true, property = "key=login.events.post",
	service = LifecycleAction.class
)
public class LoginPostAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			Cookie[] cookies = httpServletRequest.getCookies();

			if (cookies == null) {
				return;
			}

			for (Cookie cookie : cookies) {
				String name = cookie.getName();

				if (name.startsWith(
						CommerceOrder.class.getName() + StringPool.POUND)) {

					HttpServletRequest originalHttpServletRequest =
						_portal.getOriginalServletRequest(httpServletRequest);

					HttpSession httpSession =
						originalHttpServletRequest.getSession();

					httpSession.setAttribute(name, cookie.getValue());

					_updateGuestCommerceOrder(
						cookie.getValue(),
						Long.valueOf(
							StringUtil.extractLast(name, StringPool.POUND)),
						httpServletRequest);

					break;
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _updateGuestCommerceOrder(
			String commerceOrderUuid, long commerceChannelGroupId,
			HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceOrder commerceOrder = null;

		try {
			commerceOrder =
				_commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(
					commerceOrderUuid, commerceChannelGroupId);
		}
		catch (Exception exception) {
			return;
		}

		if (commerceOrder.getCommerceAccountId() !=
				CommerceAccountConstants.ACCOUNT_ID_GUEST) {

			return;
		}

		long userId = _portal.getUserId(httpServletRequest);

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.getPersonalCommerceAccount(userId);

		CommerceOrder userCommerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(
				commerceAccount.getCommerceAccountId(), commerceChannelGroupId,
				userId, CommerceOrderConstants.ORDER_STATUS_OPEN);

		if (userCommerceOrder != null) {
			CommerceContext commerceContext = _commerceContextFactory.create(
				_portal.getCompanyId(httpServletRequest),
				commerceChannelGroupId, userId,
				userCommerceOrder.getCommerceOrderId(),
				commerceAccount.getCommerceAccountId());

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(
					_portal.getUser(httpServletRequest)));

			_commerceOrderLocalService.mergeGuestCommerceOrder(
				commerceOrder.getCommerceOrderId(),
				userCommerceOrder.getCommerceOrderId(), commerceContext,
				serviceContext);
		}
		else {
			_commerceOrderLocalService.updateAccount(
				commerceOrder.getCommerceOrderId(), userId,
				commerceAccount.getCommerceAccountId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoginPostAction.class);

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private Portal _portal;

}