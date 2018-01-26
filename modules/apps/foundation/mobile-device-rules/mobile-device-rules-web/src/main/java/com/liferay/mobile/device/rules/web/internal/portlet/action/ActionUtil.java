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
import com.liferay.mobile.device.rules.rule.RuleGroupProcessorUtil;
import com.liferay.mobile.device.rules.rule.RuleHandler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;

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
		ActionHandler actionHandler = ActionHandlerManagerUtil.getActionHandler(
			type);

		if (actionHandler == null) {
			return StringPool.BLANK;
		}

		return actionHandler.getEditorJSP();
	}

	public static String getRuleEditorJSP(String type) {
		RuleHandler ruleHandler = RuleGroupProcessorUtil.getRuleHandler(type);

		if (ruleHandler == null) {
			return StringPool.BLANK;
		}

		return ruleHandler.getEditorJSP();
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

}