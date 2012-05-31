/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.displaystyles.action;

import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.RenderRequestImpl;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Eduardo Garcia
 */
public class ViewAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {

		RenderRequestImpl renderRequestImpl = (RenderRequestImpl)renderRequest;

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)renderRequestImpl.getHttpServletRequest();

		if (Validator.isNull(
			ParamUtil.getString(renderRequest, "ddmResource"))) {
			dynamicRequest.setParameter(
				"ddmResource", portletConfig.getInitParameter("ddm-resource"));
		}

		if (Validator.isNull(
			ParamUtil.getString(renderRequest, "ddmResourceActionId"))) {
			dynamicRequest.setParameter(
				"ddmResourceActionId",
				portletConfig.getInitParameter("ddm-resource-action-id"));
		}

		return mapping.findForward("portlet.display_styles.view");
	}

}