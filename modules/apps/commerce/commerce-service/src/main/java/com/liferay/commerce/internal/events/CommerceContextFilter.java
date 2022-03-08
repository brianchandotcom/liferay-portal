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

package com.liferay.commerce.internal.events;

import com.liferay.commerce.internal.context.CommerceGroupThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"servlet-context-name=", "servlet-filter-name=Commerce Context Filter",
		"url-pattern=/o/headless-batch-engine/*",
		"url-pattern=/o/headless-commerce-admin-account/*",
		"url-pattern=/o/headless-commerce-admin-catalog/*",
		"url-pattern=/o/headless-commerce-admin-channel/*",
		"url-pattern=/o/headless-commerce-admin-inventory/*",
		"url-pattern=/o/headless-commerce-admin-order/*",
		"url-pattern=/o/headless-commerce-admin-pricing/*",
		"url-pattern=/o/headless-commerce-admin-shipment/*",
		"url-pattern=/o/headless-commerce-admin-site-setting/*",
		"url-pattern=/o/headless-commerce-delivery-cart/*",
		"url-pattern=/o/headless-commerce-delivery-catalog/*",
		"url-pattern=/o/headless-commerce-machine-learning/*",
		"url-pattern=/o/headless-commerce-punchout/*"
	},
	service = Filter.class
)
public class CommerceContextFilter extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		try {
			HttpSession httpSession = httpServletRequest.getSession();

			long groupId = GetterUtil.getLong(
				httpSession.getAttribute(WebKeys.VISITED_GROUP_ID_RECENT));

			CommerceGroupThreadLocal.set(
				_groupLocalService.fetchGroup(groupId));
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceContextFilter.class);

	@Reference
	private GroupLocalService _groupLocalService;

}