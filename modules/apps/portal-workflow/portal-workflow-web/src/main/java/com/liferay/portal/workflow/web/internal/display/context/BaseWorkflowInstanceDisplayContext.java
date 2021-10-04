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

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.workflow.web.internal.display.context.util.WorkflowInstanceRequestHelper;

import java.text.Format;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseWorkflowInstanceDisplayContext {

	public BaseWorkflowInstanceDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		this.liferayPortletRequest = liferayPortletRequest;
		this.liferayPortletResponse = liferayPortletResponse;

		httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		workflowInstanceRequestHelper = new WorkflowInstanceRequestHelper(
			httpServletRequest);
	}

	public String getStatus(WorkflowInstance workflowInstance) {
		return Stream.of(
			workflowInstance.getCurrentNodeNames()
		).flatMap(
			List::stream
		).map(
			currentNodeName -> HtmlUtil.escape(
				LanguageUtil.get(
					workflowInstanceRequestHelper.getRequest(),
					currentNodeName))
		).collect(
			Collectors.joining(StringPool.COMMA_AND_SPACE)
		);
	}

	protected final Format dateFormatDateTime;
	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final PortalPreferences portalPreferences;
	protected final WorkflowInstanceRequestHelper workflowInstanceRequestHelper;

}