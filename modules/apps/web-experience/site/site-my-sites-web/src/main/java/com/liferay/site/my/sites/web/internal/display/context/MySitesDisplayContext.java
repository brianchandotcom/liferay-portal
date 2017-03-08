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

package com.liferay.site.my.sites.web.internal.display.context;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class MySitesDisplayContext {

	public MySitesDisplayContext(HttpServletRequest request) {
		_request = request;
	}

	public boolean isDefaultLayoutVisible(Group group, boolean privateLayout)
		throws Exception {

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			group.getGroupId(), privateLayout);

		return ListUtil.isNotEmpty(layouts);
	}

	private final HttpServletRequest _request;

}