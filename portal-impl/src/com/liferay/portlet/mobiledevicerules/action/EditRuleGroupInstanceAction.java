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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.mobiledevicerules.MDRPortletConstants;
import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Edward Han
 */
public class EditRuleGroupInstanceAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			MDRRuleGroupInstance ruleGroupInstance = null;

			boolean add = false;

			if (cmd.equals(Constants.EDIT)) {
				long ruleGroupId = ParamUtil.getLong(
					actionRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);

				String className = ParamUtil.getString(
					actionRequest, MDRPortletConstants.CLASS_NAME);

				long classPK = ParamUtil.getLong(
					actionRequest, MDRPortletConstants.CLASS_PK);

				ruleGroupInstance =
					MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
						className, classPK, ruleGroupId);

				add = (ruleGroupInstance == null);

				ruleGroupInstance = editRuleGroupInstance(
					actionRequest, ruleGroupId, className, classPK,
					ruleGroupInstance);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRuleGroupInstance(actionRequest);
			}

			if (add) {
				String redirect = getRedirect(
					actionRequest, actionResponse, ruleGroupInstance);

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

		long ruleGroupInstanceId = ParamUtil.getLong(
			renderRequest, MDRPortletConstants.MDR_RULE_GROUP_INSTANCE_ID);
		long ruleGroupId = 0;

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
				ruleGroupInstanceId);

		renderRequest.setAttribute(
			MDRPortletConstants.MDR_RULE_GROUP_INSTANCE, ruleGroupInstance);

		if (ruleGroupInstance != null) {
			ruleGroupId = ruleGroupInstance.getRuleGroupId();
		}
		else {
			ruleGroupId = ParamUtil.getLong(
				renderRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);
		}

		MDRRuleGroup ruleGroup =
			MDRRuleGroupLocalServiceUtil.fetchRuleGroup(ruleGroupId);

		renderRequest.setAttribute(
			MDRPortletConstants.MDR_RULE_GROUP, ruleGroup);

		return mapping.findForward(
			"portlet.mobile_device_rules_admin.edit_rule_group_instance");
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		long ruleGroupId = ParamUtil.getLong(
			resourceRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);

		String className = ParamUtil.getString(
			resourceRequest, MDRPortletConstants.CLASS_NAME);

		long classPK = ParamUtil.getLong(
			resourceRequest, MDRPortletConstants.CLASS_PK);

		if (ruleGroupId != 0) {
			MDRRuleGroup ruleGroup =
				MDRRuleGroupLocalServiceUtil.fetchRuleGroup(ruleGroupId);

			MDRRuleGroupInstance ruleGroupInstance =
				MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
					className, classPK, ruleGroupId);

			resourceRequest.setAttribute(
				MDRPortletConstants.MDR_RULE_GROUP, ruleGroup);
			resourceRequest.setAttribute(
				MDRPortletConstants.MDR_RULE_GROUP_INSTANCE, ruleGroupInstance);
		}

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(EDITOR_JSP);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void deleteRuleGroupInstance(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long ruleGroupInstanceId = ParamUtil.getLong(
			actionRequest, MDRPortletConstants.MDR_RULE_GROUP_INSTANCE_ID);

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
				ruleGroupInstanceId);

		MDRRuleGroupInstanceServiceUtil.deleteRuleGroupInstance(
			ruleGroupInstance);
	}

	protected MDRRuleGroupInstance editRuleGroupInstance(
			ActionRequest actionRequest, long ruleGroupId, String className,
			long classPK, MDRRuleGroupInstance ruleGroupInstance)
		throws PortalException, SystemException {

		int priority = ParamUtil.getInteger(
			actionRequest, MDRPortletConstants.PRIORITY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (ruleGroupInstance == null) {
			long groupId = ParamUtil.getLong(
				actionRequest, MDRPortletConstants.GROUP_ID);

			return MDRRuleGroupInstanceServiceUtil.addRuleGroupInstance(
				groupId, className, classPK, ruleGroupId, priority,
				serviceContext);
		}
		else {
			return MDRRuleGroupInstanceServiceUtil.updateRuleGroupInstance(
					ruleGroupInstance.getRuleGroupInstanceId(), priority);
		}
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MDRRuleGroupInstance ruleGroupInstance) {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (ruleGroupInstance != null) {
			ActionResponseImpl actionResponseImpl =
				(ActionResponseImpl)actionResponse;

			PortletURL portletURL = actionResponseImpl.createRenderURL();

			portletURL.setParameter(
				"struts_action",
				"/mobile_device_rules_admin/edit_rule_group_instance");
			portletURL.setParameter(
				MDRPortletConstants.MDR_RULE_GROUP_INSTANCE_ID,
				String.valueOf(ruleGroupInstance.getRuleGroupInstanceId()));
			portletURL.setParameter("redirect", redirect);

			return portletURL.toString();
		}
		else {
			return redirect;
		}
	}

	private static final String EDITOR_JSP =
		"/html/portlet/mobile_device_rules_admin/" +
			"device_rule_group_instance_editor.jsp";
}
