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

package com.liferay.portlet.mobiledevicerules.action;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupServiceUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Edward Han
 */
public class EditRuleGroupAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			MDRRuleGroup ruleGroup = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				ruleGroup = updateRuleGroup(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRuleGroup(actionRequest);
			}
			else if (cmd.equals(Constants.COPY)) {
				ruleGroup = copyRuleGroup(actionRequest);
			}

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.COPY)) {
				String redirect = getRedirect(
					actionRequest, actionResponse, ruleGroup);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.mobile_device_rules.error");
			}
			else if (e instanceof NoSuchRuleGroupException) {
				SessionErrors.add(actionRequest, e.getClass());
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

		long ruleGroupId = ParamUtil.getLong(renderRequest, "ruleGroupId");

		MDRRuleGroup ruleGroup = MDRRuleGroupServiceUtil.fetchRuleGroup(
			ruleGroupId);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

		return actionMapping.findForward(
			"portlet.mobile_device_rules.edit_rule_group");
	}

	protected MDRRuleGroup copyRuleGroup(ActionRequest actionRequest)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		return MDRRuleGroupServiceUtil.copyRuleGroup(
			ruleGroupId, groupId, serviceContext);
	}

	protected void deleteRuleGroup(ActionRequest actionRequest)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		MDRRuleGroupServiceUtil.deleteRuleGroup(ruleGroupId);
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MDRRuleGroup ruleGroup) {

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)actionResponse;

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/mobile_device_rules/edit_rule_group");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"ruleGroupId", String.valueOf(ruleGroup.getRuleGroupId()));

		return portletURL.toString();
	}

	protected MDRRuleGroup updateRuleGroup(ActionRequest actionRequest)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		MDRRuleGroup ruleGroup = null;

		if (ruleGroupId <= 0) {
			ruleGroup = MDRRuleGroupServiceUtil.addRuleGroup(
				groupId, nameMap, descriptionMap, serviceContext);
		}
		else {
			ruleGroup = MDRRuleGroupServiceUtil.updateRuleGroup(
				ruleGroupId, nameMap, descriptionMap, serviceContext);
		}

		return ruleGroup;
	}

}