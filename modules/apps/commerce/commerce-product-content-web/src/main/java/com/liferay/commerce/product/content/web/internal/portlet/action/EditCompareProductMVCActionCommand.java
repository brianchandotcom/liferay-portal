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

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_COMPARE_CONTENT_WEB,
		"mvc.command.name=editCompareProduct"
	},
	service = MVCActionCommand.class
)
public class EditCompareProductMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String compareParam = cpDefinitionId + "Compare";

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long groupId = commerceContext.getCommerceChannelGroupId();

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalHttpServletRequest.getSession();

		boolean compare = ParamUtil.getBoolean(actionRequest, compareParam);

		if (compare) {
			_cpCompareHelper.addCompareProduct(
				groupId, commerceAccountId, cpDefinitionId, httpSession);
		}
		else {
			_cpCompareHelper.removeCompareProduct(
				groupId, commerceAccountId, cpDefinitionId, httpSession);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Reference
	private CPCompareHelper _cpCompareHelper;

	@Reference
	private Portal _portal;

}