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

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayActionResponse;
import com.liferay.portlet.internal.ActionRequestImpl;
import com.liferay.portlet.internal.ActionResponseImpl;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.filter.ActionRequestWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ActionResponseFactory {

	public static LiferayActionResponse create(
			ActionRequest actionRequest,
			HttpServletResponse httpServletResponse, User user, Layout layout)
		throws PortletException {

		while (actionRequest instanceof ActionRequestWrapper) {
			ActionRequestWrapper actionRequestWrapper =
				(ActionRequestWrapper)actionRequest;

			actionRequest = actionRequestWrapper.getRequest();
		}

		ActionResponseImpl actionResponseImpl = new ActionResponseImpl();

		actionResponseImpl.init(
			(ActionRequestImpl)actionRequest, httpServletResponse, user, layout,
			true);

		return actionResponseImpl;
	}

}