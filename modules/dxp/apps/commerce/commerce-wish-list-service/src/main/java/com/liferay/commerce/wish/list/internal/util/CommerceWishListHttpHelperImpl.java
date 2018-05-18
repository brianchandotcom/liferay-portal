/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.wish.list.internal.util;

import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListLocalService;
import com.liferay.commerce.wish.list.util.CommerceWishListHttpHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceWishListHttpHelperImpl
	implements CommerceWishListHttpHelper {

	@Override
	public PortletURL getCommerceWishListPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = null;

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		String portletId =
			CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT;

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

	@Override
	public CommerceWishList getCurrentCommerceWishList(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			long companyId = _portal.getCompanyId(httpServletRequest);

			user = _userLocalService.getDefaultUser(companyId);
		}

		String cookieName = _getCookieName(groupId);

		String guestUuid = CookieKeys.getCookie(httpServletRequest, cookieName);

		CommerceWishList commerceWishList =
			_commerceWishListLocalService.getDefaultCommerceWishList(
				groupId, user.getUserId(), guestUuid);

		if (user.isDefaultUser()) {
			if (Validator.isNull(guestUuid)) {
				Cookie cookie = new Cookie(
					cookieName, commerceWishList.getUuid());

				cookie.setMaxAge(CookieKeys.MAX_AGE);
				cookie.setPath(StringPool.SLASH);

				CookieKeys.addCookie(
					httpServletRequest, httpServletResponse, cookie);
			}
		}
		else {
			if (Validator.isNotNull(guestUuid)) {
				CookieKeys.deleteCookies(
					httpServletRequest, httpServletResponse,
					CookieKeys.getDomain(httpServletRequest), cookieName);
			}
		}

		return commerceWishList;
	}

	@Override
	public int getCurrentCommerceWishListItemsCount(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		CommerceWishList commerceWishList = getCurrentCommerceWishList(
			httpServletRequest, httpServletResponse);

		return _commerceWishListItemService.getCommerceWishListItemsCount(
			commerceWishList.getCommerceWishListId());
	}

	private String _getCookieName(long groupId) {
		return CommerceWishList.class.getName() + StringPool.POUND + groupId;
	}

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListLocalService _commerceWishListLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference
	private UserLocalService _userLocalService;

}