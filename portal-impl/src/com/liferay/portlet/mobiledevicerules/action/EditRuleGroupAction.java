/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.mobiledevicerules.MDRPortletConstants;
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
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			MDRRuleGroup ruleGroup = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.EDIT)) {
				ruleGroup = editProfile(
					actionRequest, cmd.equals(Constants.ADD));
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRuleGroup(actionRequest);
			}
			else if (cmd.equals(Constants.COPY)) {
				ruleGroup = cloneRuleGroup(actionRequest);
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
		catch (NoSuchRuleGroupException nsrge) {
			SessionErrors.add(actionRequest, nsrge.getClass().getName());
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(
			renderRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);

		MDRRuleGroup ruleGroup = MDRRuleGroupServiceUtil.fetchRuleGroup(
			ruleGroupId);

		renderRequest.setAttribute(
			MDRPortletConstants.MDR_RULE_GROUP, ruleGroup);

		return mapping.findForward(
			"portlet.mobile_device_rules_admin.edit_rule_group");
	}

	protected MDRRuleGroup cloneRuleGroup(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long ruleGroupId = ParamUtil.getLong(
			actionRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);

		long groupId = ParamUtil.getLong(
			actionRequest, MDRPortletConstants.GROUP_ID);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		return  MDRRuleGroupServiceUtil.copyRuleGroup(
			ruleGroupId, groupId, serviceContext);
	}

	protected void deleteRuleGroup(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long ruleGroupId = ParamUtil.getLong(
			actionRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);

		MDRRuleGroupServiceUtil.deleteRuleGroup(ruleGroupId);
	}

	protected MDRRuleGroup editProfile(ActionRequest actionRequest, boolean add)
		throws PortalException, SystemException {

		long groupId = ParamUtil.getLong(
			actionRequest, MDRPortletConstants.GROUP_ID);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, MDRPortletConstants.NAME);

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, MDRPortletConstants.DESCRIPTION);

		MDRRuleGroup ruleGroup = null;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (add) {
			ruleGroup = MDRRuleGroupServiceUtil.addRuleGroup(
				groupId, nameMap, descriptionMap, serviceContext);
		}
		else {
			long ruleGroupId = ParamUtil.getLong(
				actionRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);

			ruleGroup = MDRRuleGroupServiceUtil.updateRuleGroup(
				ruleGroupId, nameMap, descriptionMap, serviceContext);
		}

		return ruleGroup;
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MDRRuleGroup ruleGroup) {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (ruleGroup != null) {
			ActionResponseImpl actionResponseImpl =
				(ActionResponseImpl)actionResponse;

			PortletURL portletURL = actionResponseImpl.createRenderURL();

			portletURL.setParameter(
				"struts_action",
				"/mobile_device_rules_admin/edit_rule_group");
			portletURL.setParameter(
				MDRPortletConstants.MDR_RULE_GROUP_ID,
				String.valueOf(ruleGroup.getRuleGroupId()));
			portletURL.setParameter("redirect", redirect);

			return portletURL.toString();
		}
		else {
			return redirect;
		}
	}

}