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
import com.liferay.account.exception.DuplicateAccountGroupExternalReferenceCodeException;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_GROUPS_ADMIN,
		"mvc.command.name=/account_admin/edit_account_group"
	},
	service = AopService.class
)
public class EditAccountGroupMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (cmd.equals(Constants.ADD)) {
				AccountGroup accountGroup = _addAccountGroup(actionRequest);

				redirect = HttpComponentsUtil.setParameter(
					redirect, actionResponse.getNamespace() + "accountGroupId",
					accountGroup.getAccountGroupId());
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_updateAccountGroup(actionRequest);
			}

			if (Validator.isNotNull(redirect)) {
				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof PrincipalException) {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/account_groups_admin/error.jsp");
			}
			else if (exception instanceof
						DuplicateAccountGroupExternalReferenceCodeException) {

				SessionErrors.add(actionRequest, exception.getClass());

				hideDefaultErrorMessage(actionRequest);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/account_admin/edit_account_group");
			}

			throw exception;
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
	}

	private AccountGroup _addAccountGroup(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String description = ParamUtil.getString(actionRequest, "description");
		String name = ParamUtil.getString(actionRequest, "name");

		AccountGroup accountGroup = _accountGroupService.addAccountGroup(
			themeDisplay.getUserId(), description, name);

		return _accountGroupLocalService.updateExternalReferenceCode(
			accountGroup.getAccountGroupId(),
			ParamUtil.getString(actionRequest, "externalReferenceCode"));
	}

	private void _updateAccountGroup(ActionRequest actionRequest)
		throws Exception {

		long accountGroupId = ParamUtil.getLong(
			actionRequest, "accountGroupId");

		String description = ParamUtil.getString(actionRequest, "description");
		String name = ParamUtil.getString(actionRequest, "name");

		AccountGroup accountGroup = _accountGroupService.updateAccountGroup(
			accountGroupId, description, name);

		_accountGroupService.updateExternalReferenceCode(
			accountGroup.getAccountGroupId(),
			ParamUtil.getString(actionRequest, "externalReferenceCode"));
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupService _accountGroupService;

}