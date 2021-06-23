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

package com.liferay.dispatch.web.internal.display.context;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogService;
import com.liferay.dispatch.web.internal.display.context.util.DispatchRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
public class DispatchLogDisplayContext {

	public DispatchLogDisplayContext(
		DispatchLogService dispatchLogService, RenderRequest renderRequest) {

		_dispatchLogService = dispatchLogService;

		_dispatchRequestHelper = new DispatchRequestHelper(renderRequest);
	}

	public DispatchLog getDispatchLog() throws PortalException {
		return _dispatchLogService.getDispatchLog(
			ParamUtil.getLong(
				_dispatchRequestHelper.getRequest(), "dispatchLogId"));
	}

	public DispatchTrigger getDispatchTrigger() {
		return _dispatchRequestHelper.getDispatchTrigger();
	}

	public long getExecutionTimeMills() throws PortalException {
		DispatchLog dispatchLog = getDispatchLog();

		DispatchTaskStatus dispatchTaskStatus = DispatchTaskStatus.valueOf(
			dispatchLog.getStatus());

		Date startDate = dispatchLog.getStartDate();

		if (dispatchTaskStatus == DispatchTaskStatus.IN_PROGRESS) {
			return System.currentTimeMillis() - startDate.getTime();
		}

		Date endDate = dispatchLog.getEndDate();

		return endDate.getTime() - startDate.getTime();
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dispatchRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String dispatchTriggerId = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "dispatchTriggerId");

		if (Validator.isNotNull(dispatchTriggerId)) {
			portletURL.setParameter("dispatchTriggerId", dispatchTriggerId);
		}

		portletURL.setParameter(
			"mvcRenderCommandName", "/dispatch/edit_dispatch_trigger");

		String redirect = ParamUtil.getString(
			_dispatchRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			DispatchConstants.CATEGORY_KEY_DISPATCH_LOGS);

		return portletURL;
	}

	private final DispatchLogService _dispatchLogService;
	private final DispatchRequestHelper _dispatchRequestHelper;

}