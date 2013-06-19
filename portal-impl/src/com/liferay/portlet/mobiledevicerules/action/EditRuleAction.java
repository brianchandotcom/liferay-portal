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

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
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
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.WebKeys;
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
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateRule(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRule(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.mobile_device_rules.error");
			}
			else if (e instanceof NoSuchActionException ||
					 e instanceof NoSuchRuleGroupException ||
					 e instanceof UnknownRuleHandlerException) {

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

		long ruleId = ParamUtil.getLong(renderRequest, "ruleId");

		MDRRule rule = MDRRuleServiceUtil.fetchRule(ruleId);

		renderRequest.setAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE, rule);

		String type = BeanPropertiesUtil.getString(rule, "type");

		renderRequest.setAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_TYPE, type);

		String editorJSP = getEditorJSP(type);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_EDITOR_JSP, editorJSP);

		long ruleGroupId = BeanParamUtil.getLong(
			rule, renderRequest, "ruleGroupId");

		MDRRuleGroup ruleGroup = MDRRuleGroupServiceUtil.getRuleGroup(
			ruleGroupId);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

		return actionMapping.findForward(
			"portlet.mobile_device_rules.edit_rule");
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		long ruleId = ParamUtil.getLong(resourceRequest, "ruleId");

		if (ruleId > 0) {
			MDRRule rule = MDRRuleServiceUtil.fetchRule(ruleId);

			resourceRequest.setAttribute(
				WebKeys.MOBILE_DEVICE_RULES_RULE, rule);
		}

		String type = ParamUtil.getString(resourceRequest, "type");

		includeEditorJSP(
			portletConfig, resourceRequest, resourceResponse, type);
	}

	protected void deleteRule(ActionRequest request) throws Exception {
		long ruleId = ParamUtil.getLong(request, "ruleId");

		MDRRuleServiceUtil.deleteRule(ruleId);
	}

	protected String getEditorJSP(String ruleType) {
		if (ruleType.equals(SimpleRuleHandler.getHandlerType())) {
			return _SIMPLE_RULE_EDIT_RJSP;
		}

		return StringPool.BLANK;
	}

	protected UnicodeProperties getTypeSettingsProperties(
		ActionRequest actionRequest, Collection<String> propertyNames) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		for (String propertyName : propertyNames) {
			String[] values = ParamUtil.getParameterValues(
				actionRequest, propertyName);

			String merged = StringUtil.merge(values);

			typeSettingsProperties.setProperty(propertyName, merged);
		}

		return typeSettingsProperties;
	}

	protected void includeEditorJSP(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, String type)
		throws Exception {

		String editorJSP = getEditorJSP(type);

		if (Validator.isNull(editorJSP)) {
			return;
		}

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(editorJSP);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void updateRule(ActionRequest actionRequest) throws Exception {
		long ruleId = ParamUtil.getLong(actionRequest, "ruleId");

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String type = ParamUtil.getString(actionRequest, "type");

		RuleHandler ruleHandler = RuleGroupProcessorUtil.getRuleHandler(type);

		if (ruleHandler == null) {
			throw new UnknownRuleHandlerException(type);
		}

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties(
			actionRequest, ruleHandler.getPropertyNames());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (ruleId <= 0) {
			MDRRuleServiceUtil.addRule(
				ruleGroupId, nameMap, descriptionMap, type,
				typeSettingsProperties, serviceContext);
		}
		else {
			MDRRuleServiceUtil.updateRule(
				ruleId, nameMap, descriptionMap, type, typeSettingsProperties,
				serviceContext);
		}
	}

	private static final String _SIMPLE_RULE_EDIT_RJSP =
		"/html/portlet/mobile_device_rules/rule/simple_rule.jsp";

}