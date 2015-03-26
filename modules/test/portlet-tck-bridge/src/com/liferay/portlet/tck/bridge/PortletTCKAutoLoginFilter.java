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

package com.liferay.portlet.tck.bridge;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	property = {
		"servlet-context-name=", "servlet-filter-name=TCK Auto Login Filter",
		"url-pattern=/*"
	},
	service = Filter.class
)
public class PortletTCKAutoLoginFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		User tckUser = UserLocalServiceUtil.fetchUserByEmailAddress(
			PortalUtil.getCompanyId(request), "tck@liferay.com");

		if (tckUser != null) {
			request.setAttribute(WebKeys.USER_ID, tckUser.getUserId());
		}

		processFilter(
			PortletTCKAutoLoginFilter.class, request, response, filterChain);
	}

}