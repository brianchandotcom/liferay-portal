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

package com.liferay.commerce.product.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCompareUtil {

	public static void addCompareProduct(
			HttpServletRequest httpServletRequest, long cpDefinitionId)
		throws PortalException {

		List<Long> cpDefinitionIds = getCPDefinitionIds(httpServletRequest);

		if (!cpDefinitionIds.contains(cpDefinitionId)) {
			cpDefinitionIds.add(cpDefinitionId);
		}

		setCPDefinitionIds(httpServletRequest, cpDefinitionIds);
	}

	public static List<Long> getCPDefinitionIds(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		HttpServletRequest originalServletRequest =
			PortalUtil.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalServletRequest.getSession();

		List<Long> cpDefinitionIds = (List<Long>)httpSession.getAttribute(
			_getSessionAttributeKey(httpServletRequest));

		if (cpDefinitionIds == null) {
			return new ArrayList<>();
		}

		return cpDefinitionIds;
	}

	public static void removeCompareProduct(
			HttpServletRequest httpServletRequest, long cpDefinitionId)
		throws PortalException {

		List<Long> cpDefinitionIds = getCPDefinitionIds(httpServletRequest);

		if (cpDefinitionIds.contains(cpDefinitionId)) {
			cpDefinitionIds.remove(cpDefinitionId);
		}

		setCPDefinitionIds(httpServletRequest, cpDefinitionIds);
	}

	public static void setCPDefinitionIds(
			HttpServletRequest httpServletRequest, List<Long> cpDefinitionIds)
		throws PortalException {

		httpServletRequest = PortalUtil.getOriginalServletRequest(
			httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(
			_getSessionAttributeKey(httpServletRequest), cpDefinitionIds);
	}

	private static String _getSessionAttributeKey(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return _SESSION_COMPARE_CP_DEFINITION_IDS +
			PortalUtil.getScopeGroupId(httpServletRequest);
	}

	private static final String _SESSION_COMPARE_CP_DEFINITION_IDS =
		"LIFERAY_SHARED_CP_DEFINITION_IDS_";

}