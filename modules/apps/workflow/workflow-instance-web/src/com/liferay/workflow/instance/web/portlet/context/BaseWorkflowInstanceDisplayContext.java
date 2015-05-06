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

package com.liferay.workflow.instance.web.portlet.context;

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.workflow.instance.web.portlet.context.util.WorkflowInstanceRequestHelper;

import java.text.Format;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseWorkflowInstanceDisplayContext {

	public BaseWorkflowInstanceDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		this.dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
		this.workflowInstanceRequestHelper = new WorkflowInstanceRequestHelper(
			renderRequest);
	}

	protected final Format dateFormatDateTime;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;
	protected final WorkflowInstanceRequestHelper workflowInstanceRequestHelper;

}