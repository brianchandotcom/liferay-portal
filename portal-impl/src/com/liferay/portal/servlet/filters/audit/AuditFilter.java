/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.audit;

import com.liferay.portal.kernel.audit.AuditRequestThreadLocal;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class AuditFilter extends BasePortalFilter implements TryFilter {

	@Override
	public Object doFilterTry(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		AuditRequestThreadLocal auditRequestThreadLocal =
			AuditRequestThreadLocal.getAuditThreadLocal();

		auditRequestThreadLocal.setClientHost(request.getRemoteHost());
		auditRequestThreadLocal.setClientIP(request.getRemoteAddr());
		auditRequestThreadLocal.setQueryString(request.getQueryString());

		HttpSession session = request.getSession();

		Long userId = (Long)session.getAttribute(WebKeys.USER_ID);

		if (userId != null) {
			auditRequestThreadLocal.setRealUserId(userId.longValue());
		}

		auditRequestThreadLocal.setRequestURL(
			request.getRequestURL().toString());
		auditRequestThreadLocal.setServerName(request.getServerName());
		auditRequestThreadLocal.setServerPort(request.getServerPort());
		auditRequestThreadLocal.setSessionID(session.getId());

		return null;
	}

}