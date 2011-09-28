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
import com.liferay.portal.kernel.mobile.device.rulegroup.RuleGroupProcessorUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.UnknownRuleHandlerException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mobile.device.rulegroup.rule.impl.SimpleRuleHandler;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.mobiledevicerules.MDRPortletConstants;
import com.liferay.portlet.mobiledevicerules.NoSuchActionException;
import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleServiceUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
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
public class EditRuleAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.EDIT)) {
				editRule(actionRequest, cmd.equals(Constants.ADD));
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRule(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (NoSuchActionException nsae) {
			SessionErrors.add(actionRequest, nsae.getClass().getName());
		}
		catch (NoSuchRuleGroupException nsrge) {
			SessionErrors.add(actionRequest, nsrge.getClass().getName());
		}
		catch (UnknownRuleHandlerException urhe) {
			SessionErrors.add(actionRequest, urhe.getClass().getName());
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		long ruleGroupId = ParamUtil.get(
			renderRequest, MDRPortletConstants.MDR_RULE_GROUP_ID, 0L);

		long ruleId = ParamUtil.get(
			renderRequest, MDRPortletConstants.MDR_RULE_ID, 0L);

		MDRRule rule = MDRRuleServiceUtil.fetchRule(ruleId);

		boolean isAddAction = Validator.isNull(rule);

		if (!isAddAction) {
			ruleGroupId = rule.getRuleGroupId();
		}

		MDRRuleGroup ruleGroup = MDRRuleGroupServiceUtil.getRuleGroup(
			ruleGroupId);

		String type = StringPool.BLANK;

		if (!isAddAction) {
			type = rule.getType();
		}

		String editorJSP = getEditorJSP(type);

		renderRequest.setAttribute(
			MDRPortletConstants.MDR_RULE_GROUP, ruleGroup);
		renderRequest.setAttribute(MDRPortletConstants.MDR_RULE, rule);
		renderRequest.setAttribute(MDRPortletConstants.TYPE, type);
		renderRequest.setAttribute(MDRPortletConstants.EDITOR_JSP, editorJSP);

		return mapping.findForward(
			"portlet.mobile_device_rules_admin.edit_rule");
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		String type = ParamUtil.getString(
			resourceRequest, MDRPortletConstants.TYPE);

		long ruleId = ParamUtil.getLong(
			resourceRequest, MDRPortletConstants.MDR_RULE_ID);

		if (ruleId != 0) {
			MDRRule rule = MDRRuleServiceUtil.fetchRule(ruleId);

			resourceRequest.setAttribute(MDRPortletConstants.MDR_RULE, rule);
		}

		PortletRequestDispatcher portletRequestDispatcher = null;

		String jsp = getEditorJSP(type);

		if (Validator.isNotNull(jsp)) {
			portletRequestDispatcher = portletContext.getRequestDispatcher(jsp);
		}

		if (Validator.isNotNull(portletRequestDispatcher)) {
			portletRequestDispatcher.include(resourceRequest, resourceResponse);
		}
	}

	protected void deleteRule(ActionRequest request)
		throws PortalException, SystemException {

		long ruleId = ParamUtil.get(
			request, MDRPortletConstants.MDR_RULE_ID, 0L);

		MDRRule rule = MDRRuleServiceUtil.fetchRule(ruleId);

		if (rule != null) {
			MDRRuleServiceUtil.deleteRule(rule);
		}
	}

	protected void editRule(ActionRequest actionRequest, boolean add)
		throws PortalException, SystemException {

		long ruleGroupId = ParamUtil.getLong(
			actionRequest, MDRPortletConstants.MDR_RULE_GROUP_ID);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, MDRPortletConstants.NAME);

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, MDRPortletConstants.DESCRIPTION);

		String type = ParamUtil.getString(
			actionRequest, MDRPortletConstants.TYPE);

		RuleHandler ruleHandler = RuleGroupProcessorUtil.getRuleHandler(type);

		if (ruleHandler == null) {
			throw new UnknownRuleHandlerException(type);
		}

		UnicodeProperties typeSettings = getTypeSettings(
			actionRequest, ruleHandler);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (add) {
			MDRRuleServiceUtil.addRule(
				ruleGroupId, nameMap, descriptionMap, type, typeSettings,
				serviceContext);
		}
		else {
			long ruleId = ParamUtil.getLong(
				actionRequest, MDRPortletConstants.MDR_RULE_ID);

			MDRRuleServiceUtil.updateRule(
				ruleId, nameMap, descriptionMap, type, typeSettings,
				serviceContext);
		}
	}

	protected String getEditorJSP(String ruleType) {
		if (SimpleRuleHandler.getHandlerType().equals(ruleType)) {
			return EDITOR_JSP_SIMPLE;
		}

		return StringPool.BLANK;
	}

	protected UnicodeProperties getTypeSettings(
		ActionRequest actionRequest, RuleHandler ruleHandler) {

		UnicodeProperties properties = new UnicodeProperties();

		Collection<String> propertyNames = ruleHandler.getPropertyNames();

		for (String propertyName : propertyNames) {
			String[] values = ParamUtil.getParameterValues(
				actionRequest, propertyName);

			String merged = StringUtil.merge(values);

			properties.setProperty(propertyName, merged);
		}

		return properties;
	}

	private static final String EDITOR_JSP_SIMPLE =
		"/html/portlet/mobile_device_rules_admin/rule/simple_rule.jsp";
}