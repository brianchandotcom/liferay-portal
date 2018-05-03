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

package com.liferay.blogs.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseBlogsPortlet extends MVCPortlet {

	@Override
	protected boolean isAddSuccessMessage(ActionRequest actionRequest) {
		boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

		if (ajax) {
			return false;
		}

		return super.isAddSuccessMessage(actionRequest);
	}

}