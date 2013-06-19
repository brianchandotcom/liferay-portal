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
import com.liferay.portal.kernel.mobile.device.rulegroup.ActionHandlerManagerUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.mobile.device.rulegroup.action.impl.LayoutTemplateModificationActionHandler;
import com.liferay.portal.mobile.device.rulegroup.action.impl.SimpleRedirectActionHandler;
import com.liferay.portal.mobile.device.rulegroup.action.impl.SiteRedirectActionHandler;
import com.liferay.portal.mobile.device.rulegroup.action.impl.ThemeModificationActionHandler;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mobiledevicerules.ActionTypeException;
import com.liferay.portlet.mobiledevicerules.NoSuchActionException;
import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
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
public class EditActionAction extends EditRuleAction {

	public EditActionAction() {
		registerEditorJSP(
			LayoutTemplateModificationActionHandler.class, "layout_tpl");
		registerEditorJSP(SimpleRedirectActionHandler.class, "simple_url");
		registerEditorJSP(SiteRedirectActionHandler.class, "site_url");
		registerEditorJSP(ThemeModificationActionHandler.class, "theme");
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateAction(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAction(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.mobile_device_rules.error");
			}
			else if (e instanceof ActionTypeException ||
					 e instanceof NoSuchActionException ||
					 e instanceof NoSuchRuleGroupException) {

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

		long actionId = ParamUtil.getLong(renderRequest, "actionId");

		MDRAction action = MDRActionServiceUtil.fetchAction(actionId);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION, action);

		String type = BeanPropertiesUtil.getString(action, "type");

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_TYPE, type);

		String editorJSP = getEditorJSP(type);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_EDITOR_JSP,
			editorJSP);

		long ruleGroupInstanceId = BeanParamUtil.getLong(
			action, renderRequest, "ruleGroupInstanceId");

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.getMDRRuleGroupInstance(
				ruleGroupInstanceId);

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE, ruleGroupInstance);

		MDRRuleGroup ruleGroup = ruleGroupInstance.getRuleGroup();

		renderRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

		return actionMapping.findForward(
			"portlet.mobile_device_rules.edit_action");
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		long actionId = ParamUtil.getLong(resourceRequest, "actionId");

		MDRAction action = MDRActionServiceUtil.fetchAction(actionId);

		resourceRequest.setAttribute(
			WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION, action);

		String type = ParamUtil.getString(resourceRequest, "type");

		includeEditorJSP(
			portletConfig, resourceRequest, resourceResponse, type);
	}

	protected void deleteAction(ActionRequest actionRequest) throws Exception {
		long actionId = ParamUtil.getLong(actionRequest, "actionId");

		MDRActionServiceUtil.deleteAction(actionId);
	}

	@Override
	protected String getEditorJSP(String type) {
		ActionHandler actionHandler = ActionHandlerManagerUtil.getActionHandler(
			type);

		String editorJSP = null;

		if (actionHandler != null) {
			editorJSP = _editorJSPs.get(actionHandler.getClass());
		}

		if (editorJSP == null) {
			editorJSP = StringPool.BLANK;
		}

		return editorJSP;
	}

	protected void registerEditorJSP(Class<?> clazz, String jspPrefix) {
		_editorJSPs.put(
			clazz,
			"/html/portlet/mobile_device_rules/action/" + jspPrefix + ".jsp");
	}

	protected void updateAction(ActionRequest actionRequest) throws Exception {
		long actionId = ParamUtil.getLong(actionRequest, "actionId");

		long ruleGroupInstanceId = ParamUtil.getLong(
			actionRequest, "ruleGroupInstanceId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String type = ParamUtil.getString(actionRequest, "type");

		ActionHandler actionHandler = ActionHandlerManagerUtil.getActionHandler(
			type);

		if (actionHandler == null) {
			throw new ActionTypeException();
		}

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties(
			actionRequest, actionHandler.getPropertyNames());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (actionId <= 0) {
			MDRActionServiceUtil.addAction(
				ruleGroupInstanceId, nameMap, descriptionMap, type,
				typeSettingsProperties, serviceContext);
		}
		else {
			MDRActionServiceUtil.updateAction(
				actionId, nameMap, descriptionMap, type, typeSettingsProperties,
				serviceContext);
		}
	}

	private Map<Class<?>, String> _editorJSPs = new HashMap<Class<?>, String>();

}