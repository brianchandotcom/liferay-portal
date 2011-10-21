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

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalServiceUtil;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				ruleGroupInstance = updateRuleGroupInstance(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRuleGroupInstance(actionRequest);
			}

			if (cmd.equals(Constants.ADD)) {
				String redirect = getRedirect(
					actionRequest, actionResponse, ruleGroupInstance);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(
					actionRequest, "portlet.mobile_device_rules_admin.error");
			}
			else if (e instanceof NoSuchRuleGroupException) {
				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		long ruleGroupInstanceId = ParamUtil.getLong(
			renderRequest, "ruleGroupInstanceId");

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
				ruleGroupInstanceId);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_INSTANCE, ruleGroupInstance);

		long ruleGroupId = BeanParamUtil.getLong(
			ruleGroupInstance, renderRequest, "ruleGroupId");

		MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.fetchRuleGroup(
			ruleGroupId);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

		return mapping.findForward(
			"portlet.mobile_device_rules_admin.edit_rule_group_instance");
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long ruleGroupId = ParamUtil.getLong(resourceRequest, "ruleGroupId");

		MDRRuleGroup ruleGroup = MDRRuleGroupLocalServiceUtil.fetchRuleGroup(
			ruleGroupId);

		resourceRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

		String className = ParamUtil.getString(resourceRequest, "className");
		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
				className, classPK, ruleGroupId);

		resourceRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE,
			ruleGroupInstance);

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(_EDITOR_JSP);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void deleteRuleGroupInstance(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long ruleGroupInstanceId = ParamUtil.getLong(
			actionRequest, "ruleGroupInstanceId");

		MDRRuleGroupInstanceServiceUtil.deleteRuleGroupInstance(
			ruleGroupInstanceId);
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MDRRuleGroupInstance ruleGroupInstance) {

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)actionResponse;

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action",
			"/mobile_device_rules_admin/edit_rule_group_instance");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"ruleGroupInstanceId",
			String.valueOf(ruleGroupInstance.getRuleGroupInstanceId()));

		return portletURL.toString();
	}

	protected MDRRuleGroupInstance updateRuleGroupInstance(
			ActionRequest actionRequest)
		throws PortalException, SystemException {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");
		int priority = ParamUtil.getInteger(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(
				className, classPK, ruleGroupId);

		if (ruleGroupInstance == null) {
			return MDRRuleGroupInstanceServiceUtil.addRuleGroupInstance(
				groupId, className, classPK, ruleGroupId, priority,
				serviceContext);
		}
		else {
			return MDRRuleGroupInstanceServiceUtil.updateRuleGroupInstance(
				ruleGroupInstance.getRuleGroupInstanceId(), priority);
		}
	}

	private static final String _EDITOR_JSP =
		"/html/portlet/mobile_device_rules_admin/" +
			"device_rule_group_instance_editor.jsp";

}