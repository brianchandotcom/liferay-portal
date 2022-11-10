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