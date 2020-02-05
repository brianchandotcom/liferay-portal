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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RoleNameException;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"mvc.command.name=/account_admin/edit_account_role"
	},
	service = MVCActionCommand.class
)
public class EditAccountRoleMVCActionCommand extends BaseMVCActionCommand {

	protected AccountRole addAccountRole(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");
		String name = ParamUtil.getString(actionRequest, "name");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		return _accountRoleLocalService.addAccountRole(
			themeDisplay.getUserId(), accountEntryId, name, titleMap,
			descriptionMap);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (cmd.equals(Constants.ADD)) {
				AccountRole accountRole = addAccountRole(actionRequest);

				redirect = _http.setParameter(
					redirect, actionResponse.getNamespace() + "accountRoleId",
					accountRole.getAccountRoleId());
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateAccountRole(actionRequest);
			}

			if (Validator.isNotNull(redirect)) {
				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception exception) {
			String mvcPath = "/account_entries_admin/edit_account_role.jsp";

			if (exception instanceof PrincipalException) {
				SessionErrors.add(actionRequest, exception.getClass());

				mvcPath = "/account_entries_admin/error.jsp";
			}
			else {
				SessionErrors.add(actionRequest, exception.getClass());
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	protected void updateAccountRole(ActionRequest actionRequest)
		throws PortalException {

		long accountRoleId = ParamUtil.getLong(actionRequest, "accountRoleId");

		AccountRole accountRole = _accountRoleLocalService.fetchAccountRole(
			accountRoleId);

		String name = ParamUtil.getString(actionRequest, "name");

		String[] invalidCharacters = StringUtil.split(
			RoleConstants.NAME_INVALID_CHARACTERS, StringPool.SPACE);

		if ((!PropsValues.ROLES_NAME_ALLOW_NUMERIC &&
			 Validator.isNumber(name)) ||
			RoleConstants.NAME_RESERVED_WORDS.equals(name) ||
			(StringUtil.indexOfAny(name, invalidCharacters) > -1)) {

			throw new RoleNameException();
		}

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		_roleLocalService.updateRole(
			accountRole.getRoleId(), name, titleMap, descriptionMap, null,
			null);
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private Http _http;

	@Reference
	private RoleLocalService _roleLocalService;

}