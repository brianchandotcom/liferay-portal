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

package com.liferay.commerce.product.catalog.rule.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPRuleTypeException;
import com.liferay.commerce.product.exception.NoSuchCPRuleException;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.service.CPRuleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_CATALOG_RULE,
		"mvc.command.name=editCPRule"
	},
	service = MVCActionCommand.class
)
public class EditCPRuleMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPRules(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCPRuleIds = null;

		long cpRuleId = ParamUtil.getLong(actionRequest, "cpRuleId");

		if (cpRuleId > 0) {
			deleteCPRuleIds = new long[] {cpRuleId};
		}
		else {
			deleteCPRuleIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPRuleIds"), 0L);
		}

		for (long deleteCPRuleId : deleteCPRuleIds) {
			_cpRuleService.deleteCPRule(deleteCPRuleId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CPRule cpRule = updateCPRule(actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, cpRule);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPRules(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPRuleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CPRuleTypeException) {
				SessionErrors.add(actionRequest, e.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndContinueRedirect(
		ActionRequest actionRequest, CPRule cpRule) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CPPortletKeys.CP_CATALOG_RULE,
			PortletRequest.RENDER_PHASE);

		if (cpRule != null) {
			portletURL.setParameter("mvcRenderCommandName", "editCPRule");
			portletURL.setParameter(
				"cpRuleId", String.valueOf(cpRule.getCPRuleId()));

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			portletURL.setParameter("redirect", redirect);
		}

		return portletURL.toString();
	}

	protected CPRule updateCPRule(ActionRequest actionRequest)
		throws Exception {

		long cpRuleId = ParamUtil.getLong(actionRequest, "cpRuleId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String type = ParamUtil.getString(actionRequest, "type");
		String typeSettings = ParamUtil.getString(
			actionRequest, "typeSettings");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPRule.class.getName(), actionRequest);

		CPRule cpRule = null;

		if (cpRuleId <= 0) {
			cpRule = _cpRuleService.addCPRule(
				name, active, type, typeSettings, serviceContext);
		}
		else {
			cpRule = _cpRuleService.updateCPRule(
				cpRuleId, name, active, type, typeSettings, serviceContext);
		}

		return cpRule;
	}

	@Reference
	private CPRuleService _cpRuleService;

	@Reference
	private Portal _portal;

}