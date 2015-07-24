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

package com.liferay.mobile.device.rules.web.portlet.action;

import com.liferay.mobile.device.rules.constants.MDRPortletKeys;
import com.liferay.mobile.device.rules.web.constants.MDRWebKeys;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MDRPortletKeys.MOBILE_DEVICE_SITE_ADMIN,
		"mvc.command.name=/mobile_device_rules/edit_action"
	},
	service = MVCRenderCommand.class
)
public class EditActionMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long actionId = ParamUtil.getLong(renderRequest, "actionId");

			MDRAction action = MDRActionServiceUtil.fetchAction(actionId);

			renderRequest.setAttribute(
				MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION, action);

			String type = BeanPropertiesUtil.getString(action, "type");

			renderRequest.setAttribute(
				MDRWebKeys.
					MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_TYPE,
				type);

			String editorJSP = ActionUtil.getActionEditorJSPMethod.getEditorJSP(
				type);

			renderRequest.setAttribute(
				MDRWebKeys.
					MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_EDITOR_JSP,
				editorJSP);

			long ruleGroupInstanceId = BeanParamUtil.getLong(
				action, renderRequest, "ruleGroupInstanceId");

			MDRRuleGroupInstance ruleGroupInstance =
				MDRRuleGroupInstanceLocalServiceUtil.getMDRRuleGroupInstance(
					ruleGroupInstanceId);

			renderRequest.setAttribute(
				MDRWebKeys.
					MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE,
				ruleGroupInstance);

			MDRRuleGroup ruleGroup = ruleGroupInstance.getRuleGroup();

			renderRequest.setAttribute(
				MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

			return "/edit_action.jsp";
		}
		catch (PortalException pe) {
			return "/error.jsp";
		}
	}

}