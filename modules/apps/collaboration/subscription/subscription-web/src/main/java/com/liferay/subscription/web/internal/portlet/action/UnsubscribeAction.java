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

package com.liferay.subscription.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.subscription.web.internal.constants.SubscriptionPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "path=/portal/unsubscribe",
	service = StrutsAction.class
)
public class UnsubscribeAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long userId = ParamUtil.getLong(request, "userId");
		String key = ParamUtil.getString(request, "key");

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, SubscriptionPortletKeys.UNSUBSCRIBE,
			PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameter(
			ActionRequest.ACTION_NAME, "/subscription/unsubscribe");

		liferayPortletURL.setWindowState(WindowState.MAXIMIZED);

		liferayPortletURL.setParameter("userId", String.valueOf(userId));
		liferayPortletURL.setParameter("key", key);

		response.sendRedirect(liferayPortletURL.toString());

		return null;
	}

}