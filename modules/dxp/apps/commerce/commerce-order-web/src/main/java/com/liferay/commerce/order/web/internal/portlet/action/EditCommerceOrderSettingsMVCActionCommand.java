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

package com.liferay.commerce.order.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceOrderSettings"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderSettingsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			CommercePermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS);

			updateWorkflowDefinitionLinks(actionRequest, themeDisplay);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

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

	protected ObjectValuePair<Long, String> getWorkflowDefinitionOVP(
		ActionRequest actionRequest, long typePK, String typePrefix) {

		String workflowDefinition = ParamUtil.getString(
			actionRequest, typePrefix + "WorkflowDefinition");

		return new ObjectValuePair<>(typePK, workflowDefinition);
	}

	protected void updateWorkflowDefinitionLinks(
			ActionRequest actionRequest, ThemeDisplay themeDisplay)
		throws PortalException {

		List<ObjectValuePair<Long, String>> workflowDefinitionOVPs =
			new ArrayList<>(2);

		workflowDefinitionOVPs.add(
			getWorkflowDefinitionOVP(
				actionRequest, CommerceOrderConstants.TYPE_PK_APPROVAL,
				"approval"));
		workflowDefinitionOVPs.add(
			getWorkflowDefinitionOVP(
				actionRequest, CommerceOrderConstants.TYPE_PK_TRANSMISSION,
				"transmission"));

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLinks(
			themeDisplay.getUserId(), themeDisplay.getCompanyId(),
			themeDisplay.getScopeGroupId(), CommerceOrder.class.getName(), 0,
			workflowDefinitionOVPs);
	}

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}