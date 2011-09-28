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
import com.liferay.portal.kernel.mobile.device.rulegroup.ActionHandlerManagerUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mobile.device.rulegroup.action.impl.SimpleRedirectActionHandler;
import com.liferay.portal.mobile.device.rulegroup.action.impl.SiteRedirectActionHandler;
import com.liferay.portal.mobile.device.rulegroup.action.impl.LayoutTemplateModificationActionHandler;
import com.liferay.portal.mobile.device.rulegroup.action.impl.ThemeModificationActionHandler;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.mobiledevicerules.MDRPortletConstants;
import com.liferay.portlet.mobiledevicerules.NoSuchActionException;
import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRActionServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;

import java.util.Collection;
import java.util.HashMap;
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
public class EditActionAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.EDIT)) {
				editAction(actionRequest, cmd.equals(Constants.ADD));
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAction(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (NoSuchActionException nsae) {
			SessionErrors.add(actionRequest, nsae.getClass().getName());
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
		long actionId = ParamUtil.getLong(
			renderRequest, MDRPortletConstants.MDR_ACTION_ID);

		MDRAction mdrAction = MDRActionServiceUtil.fetchAction(actionId);

		boolean isAddAction = Validator.isNull(mdrAction);

		String type = StringPool.BLANK;

		if (!isAddAction) {
			ruleGroupInstanceId = mdrAction.getRuleGroupInstanceId();

			type = mdrAction.getType();
		}

		MDRRuleGroupInstance ruleGroupInstance =
			MDRRuleGroupInstanceLocalServiceUtil.getMDRRuleGroupInstance(
				ruleGroupInstanceId);

		String editorJSP = getEditorJSP(type);

		renderRequest.setAttribute(
			MDRPortletConstants.MDR_RULE_GROUP_INSTANCE, ruleGroupInstance);
		renderRequest.setAttribute(MDRPortletConstants.MDR_ACTION, mdrAction);
		renderRequest.setAttribute(MDRPortletConstants.TYPE, type);
		renderRequest.setAttribute(MDRPortletConstants.EDITOR_JSP, editorJSP);

		return mapping.findForward(
			"portlet.mobile_device_rules_admin.edit_action");
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		String type = ParamUtil.get(
			resourceRequest, MDRPortletConstants.TYPE,
			StringPool.BLANK);

		long actionId = ParamUtil.getLong(
			resourceRequest, MDRPortletConstants.MDR_ACTION_ID);

		if (actionId != 0) {
			MDRAction action = MDRActionServiceUtil.fetchAction(
				actionId);

			resourceRequest.setAttribute(
				MDRPortletConstants.MDR_ACTION, action);
		}

		PortletRequestDispatcher portletRequestDispatcher = null;

		String editorJSP = getEditorJSP(type);

		if (Validator.isNotNull(editorJSP)) {
			portletRequestDispatcher = portletContext.getRequestDispatcher(
				editorJSP);
		}

		if (Validator.isNotNull(portletRequestDispatcher)) {
			portletRequestDispatcher.include(resourceRequest, resourceResponse);
		}
	}

	protected void deleteAction(ActionRequest actionRequest) throws Exception {
		long actionId = ParamUtil.getLong(
			actionRequest, MDRPortletConstants.MDR_ACTION_ID);

		MDRAction action = MDRActionLocalServiceUtil.getMDRAction(actionId);

		MDRActionServiceUtil.deleteAction(action);
	}

	protected void editAction(ActionRequest actionRequest, boolean add)
		throws PortalException, SystemException {

		String type = ParamUtil.getString(
			actionRequest, MDRPortletConstants.TYPE);

		ActionHandler actionHandler = ActionHandlerManagerUtil.getActionHandler(
			type);

		if (actionHandler == null) {
			SessionErrors.add(
				actionRequest, MDRPortletConstants.ERROR_INVALID_TYPE);

			return;
		}

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties(
			actionRequest, actionHandler);

		if (typeSettingsProperties != null) {
			long ruleGroupInstanceId = ParamUtil.getLong(
				actionRequest, MDRPortletConstants.MDR_RULE_GROUP_INSTANCE_ID);

			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, MDRPortletConstants.NAME);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					actionRequest, MDRPortletConstants.DESCRIPTION);

			MDRRuleGroupInstance ruleGroupInstance =
				MDRRuleGroupInstanceLocalServiceUtil.getMDRRuleGroupInstance(
					ruleGroupInstanceId);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			if (add) {
				MDRActionServiceUtil.addMDRAction(
					ruleGroupInstance.getRuleGroupInstanceId(), nameMap,
					descriptionMap, type, typeSettingsProperties,
					serviceContext);
			}
			else {
				long actionId = ParamUtil.getLong(
					actionRequest, MDRPortletConstants.MDR_ACTION_ID);

				MDRActionServiceUtil.updateAction(
					actionId, nameMap, descriptionMap, type,
					typeSettingsProperties, serviceContext);
			}
		}
	}

	protected String getEditorJSP(String type) {
		ActionHandler handler = ActionHandlerManagerUtil.getActionHandler(type);

		String editorJSP = null;

		if (handler != null) {
			editorJSP = _handlerRegistry.get(handler.getClass());
		}

		if (Validator.isNull(editorJSP)) {
			editorJSP = StringPool.BLANK;
		}

		return editorJSP;
	}

	protected UnicodeProperties getTypeSettingsProperties(
			ActionRequest actionRequest, ActionHandler actionHandler) {

		Collection<String> propertyNames = actionHandler.getPropertyNames();

		UnicodeProperties properties = new UnicodeProperties();

		for (String propertyName : propertyNames) {
			String value = ParamUtil.get(
				actionRequest, propertyName, StringPool.BLANK);

			properties.setProperty(propertyName, value);
		}

		return properties;
	}

	private static final Map<Class, String> _handlerRegistry;

	private static final String PATH =
		"/html/portlet/mobile_device_rules_admin/action";

	static {
		_handlerRegistry = new HashMap<Class, String>(4);

		_handlerRegistry.put(
			SiteRedirectActionHandler.class,
			PATH + "/site_url_action.jsp");

		_handlerRegistry.put(
			LayoutTemplateModificationActionHandler.class,
			PATH + "/layout_tpl_action.jsp");

		_handlerRegistry.put(
			SimpleRedirectActionHandler.class,
			PATH + "/simple_url_action.jsp");

		_handlerRegistry.put(
			ThemeModificationActionHandler.class,
			PATH + "/theme_action.jsp");
	}
}