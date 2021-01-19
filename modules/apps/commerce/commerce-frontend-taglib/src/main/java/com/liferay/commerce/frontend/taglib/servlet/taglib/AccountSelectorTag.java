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
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.model.CurrentAccountModel;
import com.liferay.commerce.frontend.taglib.internal.model.CurrentOrderModel;
import com.liferay.commerce.frontend.taglib.internal.model.OrderStatusModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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

			CommerceContext commerceContext =
				(CommerceContext)request.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			if (commerceAccount != null) {
				String thumbnailUrl = null;

				if (commerceAccount.getLogoId() == 0) {
					thumbnailUrl =
						themeDisplay.getPathImage() +
							"/organization_logo?img_id=0";
				}
				else {
					thumbnailUrl = StringBundler.concat(
						themeDisplay.getPathImage(),
						"/organization_logo?img_id=",
						commerceAccount.getLogoId(), "&t=",
						WebServerServletTokenUtil.getToken(
							commerceAccount.getLogoId()));
				}

				CurrentAccountModel currentAccountModel =
					new CurrentAccountModel(
						commerceAccount.getCommerceAccountId(), thumbnailUrl,
						commerceAccount.getName());

				httpServletRequest.setAttribute(
					"liferay-commerce:account-selector:currentAccount",
					currentAccountModel);
			}

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				String orderStatusInfoLabel = WorkflowConstants.getStatusLabel(
					commerceOrder.getStatus());

				OrderStatusModel orderStatusModel = new OrderStatusModel(
					commerceOrder.getOrderStatus(), orderStatusInfoLabel,
					LanguageUtil.get(
						themeDisplay.getLocale(), orderStatusInfoLabel));

				CurrentOrderModel currentOrderModel = new CurrentOrderModel(
					commerceOrder.getCommerceOrderId(), orderStatusModel);

				httpServletRequest.setAttribute(
					"liferay-commerce:account-selector:currentOrder",
					currentOrderModel);
			}

			httpServletRequest.setAttribute(
				"liferay-commerce:account-selector:createNewOrderURL",
				_getAddCommerceOrderURL(themeDisplay));
			httpServletRequest.setAttribute(
				"liferay-commerce:account-selector:selectOrderURL",
				_getEditOrderURL(themeDisplay));

			String setCurrentAccountURL =
				PortalUtil.getPortalURL(request) +
					"/o/commerce-ui/set-current-account";

			httpServletRequest.setAttribute(
				"liferay-commerce:account-selector:setCurrentAccountURL",
				setCurrentAccountURL);

			httpServletRequest.setAttribute(
				"liferay-commerce:account-selector:spritemap", _spritemap);
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

	private String _getEditOrderURL(ThemeDisplay themeDisplay)
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
			portletURL.setParameter(Constants.CMD, "setCurrent");
			portletURL.setParameter("commerceOrderId", "{id}");

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