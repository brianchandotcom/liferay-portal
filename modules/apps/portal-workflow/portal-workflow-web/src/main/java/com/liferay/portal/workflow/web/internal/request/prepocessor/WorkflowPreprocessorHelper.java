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

package com.liferay.portal.workflow.web.internal.request.prepocessor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = WorkflowPreprocessorHelper.class)
public class WorkflowPreprocessorHelper {

	public String getMVCPathAttributeName(String namespace) {
		return namespace.concat(
			StringPool.PERIOD
		).concat(
			MVCRenderConstants.MVC_PATH_REQUEST_ATTRIBUTE_NAME
		);
	}

	public String getPath(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String mvcPath = portletRequest.getParameter("mvcPath");

		if (mvcPath == null) {
			mvcPath = (String)portletRequest.getAttribute(
				getMVCPathAttributeName(portletResponse.getNamespace()));
		}

		// Check deprecated parameter

		if (mvcPath == null) {
			mvcPath = portletRequest.getParameter("jspPage");
		}

		return mvcPath;
	}

	public void hideDefaultErrorMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			_portal.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	public void hideDefaultSuccessMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			_portal.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
	}

	public boolean isSessionErrorException(Throwable cause) {
		if (_log.isDebugEnabled()) {
			_log.debug(cause, cause);
		}

		if (cause instanceof PortalException) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowPreprocessorHelper.class);

	@Reference
	private Portal _portal;

}