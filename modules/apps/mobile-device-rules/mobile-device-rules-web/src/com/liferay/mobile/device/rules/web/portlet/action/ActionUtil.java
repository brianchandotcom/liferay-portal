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

import com.liferay.mobile.device.rules.rulegroup.action.LayoutTemplateModificationActionHandler;
import com.liferay.mobile.device.rules.rulegroup.action.SimpleRedirectActionHandler;
import com.liferay.mobile.device.rules.rulegroup.action.SiteRedirectActionHandler;
import com.liferay.mobile.device.rules.rulegroup.action.ThemeModificationActionHandler;
import com.liferay.mobile.device.rules.rulegroup.rule.SimpleRuleHandler;
import com.liferay.portal.kernel.mobile.device.rulegroup.ActionHandlerManagerUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Mate Thurzo
 */
public class ActionUtil {

	public static GetActionEditorJSPMethod getActionEditorJSPMethod =
		new GetActionEditorJSPMethod();
	public static GetRuleEditorJSPMethod getRuleEditorJSPMethod =
		new GetRuleEditorJSPMethod();

	public static UnicodeProperties getTypeSettingsProperties(
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

	public static void includeEditorJSP(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, String type,
			GetEditorJSPMethod getEditorJSPMethod)
		throws Exception {

		String editorJSP = getEditorJSPMethod.getEditorJSP(type);

		if (Validator.isNull(editorJSP)) {
			return;
		}

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(editorJSP);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	public static class GetActionEditorJSPMethod implements GetEditorJSPMethod {

		@Override
		public String getEditorJSP(String type) {
			ActionHandler actionHandler =
				ActionHandlerManagerUtil.getActionHandler(type);

			String editorJSP = null;

			if (actionHandler != null) {
				editorJSP = _actionEditorJSPs.get(actionHandler.getClass());
			}

			if (editorJSP == null) {
				editorJSP = StringPool.BLANK;
			}

			return editorJSP;
		}

	}

	public static class GetRuleEditorJSPMethod implements GetEditorJSPMethod {

		@Override
		public String getEditorJSP(String type) {
			if (type.equals(SimpleRuleHandler.getHandlerType())) {
				return _SIMPLE_RULE_EDIT_RJSP;
			}

			return StringPool.BLANK;
		}

	}

	public interface GetEditorJSPMethod {

		public String getEditorJSP(String type);

	}

	protected static void registerEditorJSP(Class<?> clazz, String jspPrefix) {
		_actionEditorJSPs.put(clazz, "/action/" + jspPrefix + ".jsp");
	}

	private static final String _SIMPLE_RULE_EDIT_RJSP =
		"/rule/simple_rule.jsp";

	private static final Map<Class<?>, String> _actionEditorJSPs =
		new HashMap<>();

	static {
		registerEditorJSP(
			LayoutTemplateModificationActionHandler.class, "layout_tpl");
		registerEditorJSP(SimpleRedirectActionHandler.class, "simple_url");
		registerEditorJSP(SiteRedirectActionHandler.class, "site_url");
		registerEditorJSP(ThemeModificationActionHandler.class, "theme");
	}

}