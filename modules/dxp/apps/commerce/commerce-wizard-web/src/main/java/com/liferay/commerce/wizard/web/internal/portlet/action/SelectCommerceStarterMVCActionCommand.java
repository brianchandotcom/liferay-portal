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

package com.liferay.commerce.wizard.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.util.CommerceStarter;
import com.liferay.commerce.product.util.CommerceStarterRegistry;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_WIZARD,
		"mvc.command.name=selectCommerceStarter"
	},
	service = MVCActionCommand.class
)
public class SelectCommerceStarterMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String commerceStarterKey = ParamUtil.getString(
			actionRequest, "commerceStarterKey");

		CommerceStarter commerceStarter = null;

		if (cmd.equals("nextCommerceStarter")) {
			commerceStarter = _commerceStarterRegistry.getNextCommerceStarter(
				commerceStarterKey, true, httpServletRequest);
		}
		else if (cmd.equals("previousCommerceStarter")) {
			commerceStarter =
				_commerceStarterRegistry.getPreviousCommerceStarter(
					commerceStarterKey, true, httpServletRequest);
		}

		String redirect = StringPool.BLANK;

		if (commerceStarter != null) {
			redirect = getViewCommerceStarterDetailsURL(
				actionRequest, commerceStarter.getKey());
		}

		sendRedirect(actionRequest, actionResponse, redirect);

		hideDefaultSuccessMessage(actionRequest);
	}

	protected String getViewCommerceStarterDetailsURL(
		ActionRequest actionRequest, String commerceStarterKey) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CPPortletKeys.COMMERCE_WIZARD,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceStarterDetails");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		if (Validator.isNotNull(commerceStarterKey)) {
			portletURL.setParameter("commerceStarterKey", commerceStarterKey);
		}

		return portletURL.toString();
	}

	@Reference
	private CommerceStarterRegistry _commerceStarterRegistry;

	@Reference
	private Portal _portal;

}