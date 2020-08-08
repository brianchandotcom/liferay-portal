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

import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.model.CurrentAccountModel;
import com.liferay.commerce.frontend.taglib.internal.model.CurrentOrderModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class AccountSelectorTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		putValue(
			"accountsAPI",
			PortalUtil.getPortalURL(request) + "/o/commerce-ui/");

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		LayoutSet layoutSet = themeDisplay.getLayoutSet();

		try {
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
						commerceAccount.getCommerceAccountId(),
						commerceAccount.getName(), thumbnailUrl);

				putValue("currentAccount", currentAccountModel);
			}

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				CurrentOrderModel currentOrderModel = new CurrentOrderModel(
					commerceOrder.getCommerceOrderId(),
					WorkflowConstants.getStatusLabel(
						commerceOrder.getStatus()));

				putValue("currentOrder", currentOrderModel);
			}

			putValue(
				"viewAllAccountsLink",
				_getAccountManagementLayoutURL(
					layoutSet.isPrivateLayout(), themeDisplay));

			putValue(
				"createNewOrderLink", _getAddCommerceOrderURL(themeDisplay));

			putValue(
				"viewAllOrdersLink", _getViewCommerceOrdersURL(themeDisplay));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		putValue("spritemap", themeDisplay.getPathThemeImages() + "/icons.svg");

		setTemplateNamespace("AccountSelector.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = ServletContextUtil.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"commerce-frontend-taglib/account_selector/AccountSelector.es");
	}

	private String _getAccountManagementLayoutURL(
			boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			themeDisplay.getScopeGroupId(), privateLayout, "/accounts");

		if (layout == null) {
			long plid = PortalUtil.getPlidFromPortletId(
				themeDisplay.getScopeGroupId(),
				CommerceAccountPortletKeys.COMMERCE_ACCOUNT);

			if (plid > 0) {
				layout = LayoutLocalServiceUtil.fetchLayout(plid);
			}
		}

		if (layout != null) {
			return PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
		}

		return StringPool.BLANK;
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
				ActionRequest.ACTION_NAME, "editCommerceOrder");
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

	private String _getViewCommerceOrdersURL(ThemeDisplay themeDisplay)
		throws PortalException {

		long plid = PortalUtil.getPlidFromPortletId(
			themeDisplay.getScopeGroupId(),
			CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

		if (plid > 0) {
			PortletURL portletURL = _getPortletURL(
				themeDisplay.getRequest(),
				CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT);

			return portletURL.toString();
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountSelectorTag.class);

}