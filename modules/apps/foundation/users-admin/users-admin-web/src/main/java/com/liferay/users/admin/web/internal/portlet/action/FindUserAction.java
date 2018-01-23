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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.struts.FindAction;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class FindUserAction extends FindAction {

	@Override
	protected long getGroupId(long primaryKey) throws Exception {
		return 0;
	}

	@Override
	protected String getPrimaryKeyParameterName() {
		return "p_u_i_d";
	}

	@Override
	protected String getStrutsAction(
		HttpServletRequest request, String portletId) {

		return "/directory/view_user";
	}

	@Override
	protected String[] initPortletIds() {
		return new String[] {PortletKeys.DIRECTORY};
	}

}