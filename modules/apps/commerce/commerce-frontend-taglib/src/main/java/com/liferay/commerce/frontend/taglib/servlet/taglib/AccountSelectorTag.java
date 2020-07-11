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

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Fabio Diego Mastrorilli
 */
public class AccountSelectorTag extends IncludeTag {

	public String getSpritemap() {
		return _spritemap;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_spritemap = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (Validator.isNull(_spritemap)) {
				_spritemap =
					themeDisplay.getPathThemeImages() + "/clay/icons.svg";
			}

			httpServletRequest.setAttribute(
				"liferay-commerce:account-selector:spritemap", _spritemap);

			httpServletRequest.setAttribute(
				"liferay-commerce:account-selector:createNewOrderUrl",
				_getAddCommerceOrderURL(themeDisplay));
			httpServletRequest.setAttribute(
				"liferay-commerce:account-selector:viewOrderUrl",
				"/order-detail/{id}");
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private String _getAddCommerceOrderURL(ThemeDisplay themeDisplay)
		throws PortalException {

		long plid = PortalUtil.getPlidFromPortletId(
			themeDisplay.getScopeGroupId(),
			CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

		if (plid > 0) {
			PortletURL portletURL = _getPortletURL(
				themeDisplay.getRequest(),
				CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

			portletURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/commerce_open_order_content/edit_commerce_order");
			portletURL.setParameter(Constants.CMD, Constants.ADD);

			return portletURL.toString();
		}

		return StringPool.BLANK;
	}

	private PortletURL _getPortletURL(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException {

		long groupId = PortalUtil.getScopeGroupId(httpServletRequest);

		long plid = PortalUtil.getPlidFromPortletId(groupId, portletId);

		if (plid > 0) {
			return PortletURLFactoryUtil.create(
				httpServletRequest, portletId, plid,
				PortletRequest.ACTION_PHASE);
		}

		return PortletURLFactoryUtil.create(
			httpServletRequest, portletId, PortletRequest.ACTION_PHASE);
	}

	private static final String _PAGE = "/account_selector/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountSelectorTag.class);

	private String _spritemap;

}