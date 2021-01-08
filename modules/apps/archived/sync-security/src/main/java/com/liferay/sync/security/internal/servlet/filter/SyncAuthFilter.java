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

package com.liferay.sync.security.internal.servlet.filter;

import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicy;
import com.liferay.sync.security.internal.service.access.policy.SyncSAPEntryActivator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = {
		"servlet-context-name=", "servlet-filter-name=Sync Auth Filter",
		"url-pattern=/api/jsonws/*"
	},
	service = Filter.class
)
public class SyncAuthFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker != null) && permissionChecker.isSignedIn()) {
			AccessControlContext accessControlContext =
				AccessControlUtil.getAccessControlContext();

			AuthVerifierResult authVerifierResult =
				accessControlContext.getAuthVerifierResult();

			if (authVerifierResult != null) {
				Map<String, Object> settings = authVerifierResult.getSettings();

				List<String> serviceAccessPolicyNames =
					(List<String>)settings.computeIfAbsent(
						ServiceAccessPolicy.SERVICE_ACCESS_POLICY_NAMES,
						value -> new ArrayList<>());

				serviceAccessPolicyNames.add(
					String.valueOf(
						SyncSAPEntryActivator.SAP_ENTRY_OBJECT_ARRAYS[1][0]));
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

}