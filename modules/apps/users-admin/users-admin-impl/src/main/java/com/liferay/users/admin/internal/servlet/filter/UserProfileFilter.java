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

package com.liferay.users.admin.internal.servlet.filter;

import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=User Profile Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class UserProfileFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		long plid = ParamUtil.getLong(
			_portal.getOriginalServletRequest(httpServletRequest), "p_l_id");

		Layout layout = null;

		if (plid > 0) {
			layout = _layoutLocalService.fetchLayout(plid);
		}

		if (layout != null) {
			Group layoutGroup = layout.getGroup();

			if (layoutGroup.isUser()) {
				_portal.sendError(
					new NoSuchLayoutException(
						"User pages cannot be accessed via p_l_id"),
					httpServletRequest, httpServletResponse);

				return;
			}
		}

		processFilter(
			UserProfileFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}