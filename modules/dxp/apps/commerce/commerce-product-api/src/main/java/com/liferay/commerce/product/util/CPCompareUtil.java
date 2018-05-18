/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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