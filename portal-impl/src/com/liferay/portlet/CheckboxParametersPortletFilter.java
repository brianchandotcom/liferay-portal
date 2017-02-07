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

import com.liferay.portal.kernel.portlet.DynamicActionRequest;
import com.liferay.portal.kernel.portlet.DynamicResourceRequest;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Julio Camarero
 */
public class CheckboxParametersPortletFilter
	implements ActionFilter, ResourceFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		String checkboxNames = actionRequest.getParameter("checkboxNames");

		if (Validator.isNull(checkboxNames)) {
			filterChain.doFilter(actionRequest, actionResponse);

			return;
		}

		DynamicActionRequest dynamicActionRequest = new DynamicActionRequest(
			actionRequest);

		for (String checkboxName : StringUtil.split(checkboxNames)) {
			String value = actionRequest.getParameter(checkboxName);

			if (value == null) {
				dynamicActionRequest.setParameter(
					checkboxName, Boolean.FALSE.toString());
			}
			else if (Objects.equals(value, "on")) {
				dynamicActionRequest.setParameter(
					checkboxName, Boolean.TRUE.toString());
			}
		}

		filterChain.doFilter(dynamicActionRequest, actionResponse);
	}

	@Override
	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		String checkboxNames = resourceRequest.getParameter("checkboxNames");

		if (Validator.isNull(checkboxNames)) {
			filterChain.doFilter(resourceRequest, resourceResponse);

			return;
		}

		DynamicResourceRequest dynamicResourceRequest =
			new DynamicResourceRequest(resourceRequest);

		Set<String> parameterNames = SetUtil.fromEnumeration(
			resourceRequest.getParameterNames());

		for (String checkboxName : StringUtil.split(checkboxNames)) {
			if (!parameterNames.contains(checkboxName)) {
				dynamicResourceRequest.setParameter(
					checkboxName, Boolean.FALSE.toString());
			}
			else {
				String value = dynamicResourceRequest.getParameter(
					checkboxName);

				if (Objects.equals(value, "on")) {
					dynamicResourceRequest.setParameter(
						checkboxName, Boolean.TRUE.toString());
				}
			}
		}

		filterChain.doFilter(dynamicResourceRequest, resourceResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

}