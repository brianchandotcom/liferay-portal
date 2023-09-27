/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lily Chi
 */
public class CPDefinitionLocalServiceCircularDependencyUtil {

	public static CPDefinition copyCPDefinition(long cpDefinitionId)
		throws PortalException {

		return _cpDefinitionLocalService.copyCPDefinition(cpDefinitionId);
	}

	public static boolean isVersionable(long cpDefinitionId) {
		return _cpDefinitionLocalService.isVersionable(cpDefinitionId);
	}

	public static boolean isVersionable(
		long cpDefinitionId, HttpServletRequest httpServletRequest) {

		return _cpDefinitionLocalService.isVersionable(
			cpDefinitionId, httpServletRequest);
	}

	public static CPDefinition updateCPDefinitionIgnoreSKUCombinations(
			long cpDefinitionId, boolean ignoreSKUCombinations,
			ServiceContext serviceContext)
		throws PortalException {

		return _cpDefinitionLocalService.
			updateCPDefinitionIgnoreSKUCombinations(
				cpDefinitionId, ignoreSKUCombinations, serviceContext);
	}

	private static volatile CPDefinitionLocalService _cpDefinitionLocalService =
		ServiceProxyFactory.newServiceTrackedInstance(
			CPDefinitionLocalService.class,
			CPDefinitionLocalServiceCircularDependencyUtil.class,
			"_cpDefinitionLocalService", true);

}