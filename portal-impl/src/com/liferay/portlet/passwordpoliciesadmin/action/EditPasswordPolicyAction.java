/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.passwordpoliciesadmin.action;

import com.liferay.portal.DuplicatePasswordPolicyException;
import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.PasswordPolicyNameException;
import com.liferay.portal.RequiredPasswordPolicyException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PasswordPolicyServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Scott Lee
 */
public class EditPasswordPolicyAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updatePasswordPolicy(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deletePasswordPolicy(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(
					actionRequest, "portlet.password_policies_admin.error");
			}
			else if (e instanceof DuplicatePasswordPolicyException ||
					 e instanceof PasswordPolicyNameException ||
					 e instanceof NoSuchPasswordPolicyException ||
					 e instanceof RequiredPasswordPolicyException) {

				SessionErrors.add(actionRequest, e.getClass());

				if (cmd.equals(Constants.DELETE)) {
					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getPasswordPolicy(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPasswordPolicyException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.password_policies_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.password_policies_admin.edit_password_policy"));
	}

	protected void deletePasswordPolicy(ActionRequest actionRequest)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(
			actionRequest, "passwordPolicyId");

		PasswordPolicyServiceUtil.deletePasswordPolicy(passwordPolicyId);
	}

	protected void updatePasswordPolicy(ActionRequest actionRequest)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(
			actionRequest, "passwordPolicyId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean changeable = ParamUtil.getBoolean(actionRequest, "changeable");
		boolean changeRequired = ParamUtil.getBoolean(
			actionRequest, "changeRequired");
		long minAge = ParamUtil.getLong(actionRequest, "minAge");
		boolean checkSyntax = ParamUtil.getBoolean(
			actionRequest, "checkSyntax");
		boolean allowDictionaryWords = ParamUtil.getBoolean(
			actionRequest, "allowDictionaryWords");
		int minAlphanumeric = ParamUtil.getInteger(
			actionRequest, "minAlphanumeric");
		int minLength = ParamUtil.getInteger(actionRequest, "minLength");
		int minLowerCase = ParamUtil.getInteger(actionRequest, "minLowerCase");
		int minNumbers = ParamUtil.getInteger(actionRequest, "minNumbers");
		int minSymbols = ParamUtil.getInteger(actionRequest, "minSymbols");
		int minUpperCase = ParamUtil.getInteger(actionRequest, "minUpperCase");
		String regex = ParamUtil.getString(actionRequest, "regex");
		boolean history = ParamUtil.getBoolean(actionRequest, "history");
		int historyCount = ParamUtil.getInteger(actionRequest, "historyCount");
		boolean expireable = ParamUtil.getBoolean(actionRequest, "expireable");
		long maxAge = ParamUtil.getLong(actionRequest, "maxAge");
		long warningTime = ParamUtil.getLong(actionRequest, "warningTime");
		int graceLimit = ParamUtil.getInteger(actionRequest, "graceLimit");
		boolean lockout = ParamUtil.getBoolean(actionRequest, "lockout");
		int maxFailure = ParamUtil.getInteger(actionRequest, "maxFailure");
		long lockoutDuration = ParamUtil.getLong(
			actionRequest, "lockoutDuration");
		long resetFailureCount = ParamUtil.getLong(
			actionRequest, "resetFailureCount");
		long resetTicketMaxAge = ParamUtil.getLong(
			actionRequest, "resetTicketMaxAge");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			PasswordPolicy.class.getName(), actionRequest);

		if (passwordPolicyId <= 0) {

			// Add password policy

			PasswordPolicyServiceUtil.addPasswordPolicy(
				name, description, changeable, changeRequired, minAge,
				checkSyntax, allowDictionaryWords, minAlphanumeric, minLength,
				minLowerCase, minNumbers, minSymbols, minUpperCase, regex,
				history, historyCount, expireable, maxAge, warningTime,
				graceLimit, lockout, maxFailure, lockoutDuration,
				resetFailureCount, resetTicketMaxAge, serviceContext);
		}
		else {

			// Update password policy

			PasswordPolicyServiceUtil.updatePasswordPolicy(
				passwordPolicyId, name, description, changeable, changeRequired,
				minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
				minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
				regex, history, historyCount, expireable, maxAge, warningTime,
				graceLimit, lockout, maxFailure, lockoutDuration,
				resetFailureCount, resetTicketMaxAge, serviceContext);
		}
	}

}