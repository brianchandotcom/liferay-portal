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
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.mobile.device.rules.web.constants.MDRWebKeys;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MDRPortletKeys.MOBILE_DEVICE_RULES,
		"mvc.command.name=/mobile_device_rules/edit_rule_group_instance"
	},
	service = MVCRenderCommand.class
)
public class EditRuleGroupInstanceMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long ruleGroupInstanceId = ParamUtil.getLong(
			renderRequest, "ruleGroupInstanceId");

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
				ruleGroupInstanceId);

		renderRequest.setAttribute(
			MDRWebKeys.MOBILE_DEVICE_RULES_RULE_INSTANCE, ruleGroupInstance);

		long ruleGroupId = BeanParamUtil.getLong(
			ruleGroupInstance, renderRequest, "ruleGroupId");

		MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.fetchRuleGroup(
			ruleGroupId);

		renderRequest.setAttribute(
			MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

		return "/edit_rule_group_instance_priorities.jsp";
	}

}