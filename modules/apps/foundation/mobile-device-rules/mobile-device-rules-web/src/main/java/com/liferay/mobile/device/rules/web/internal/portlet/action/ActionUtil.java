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

package com.liferay.mobile.device.rules.web.internal.portlet.action;

import com.liferay.mobile.device.rules.action.ActionHandler;
import com.liferay.mobile.device.rules.action.ActionHandlerManagerUtil;
import com.liferay.mobile.device.rules.rule.group.action.LayoutTemplateModificationActionHandler;
import com.liferay.mobile.device.rules.rule.group.action.SimpleRedirectActionHandler;
import com.liferay.mobile.device.rules.rule.group.action.SiteRedirectActionHandler;
import com.liferay.mobile.device.rules.rule.group.action.ThemeModificationActionHandler;
import com.liferay.mobile.device.rules.rule.group.rule.SimpleRuleHandler;
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

	public static String getActionEditorJSP(String type) {
		String editorJSP = null;

		ActionHandler actionHandler = ActionHandlerManagerUtil.getActionHandler(
			type);

		if (actionHandler != null) {
			editorJSP = _actionEditorJSPs.get(actionHandler.getClass());
		}

		if (editorJSP == null) {
			editorJSP = StringPool.BLANK;
		}

		return editorJSP;
	}

	public static String getRuleEditorJSP(String type) {
		if (type.equals(SimpleRuleHandler.getHandlerType())) {
			return "/rule/simple_rule.jsp";
		}

		return StringPool.BLANK;
	}

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
			ResourceResponse resourceResponse, String editorJSP)
		throws Exception {

		if (Validator.isNull(editorJSP)) {
			return;
		}

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(editorJSP);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected static void registerEditorJSP(Class<?> clazz, String fileName) {
		_actionEditorJSPs.put(clazz, "/action/" + fileName + ".jsp");
	}

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