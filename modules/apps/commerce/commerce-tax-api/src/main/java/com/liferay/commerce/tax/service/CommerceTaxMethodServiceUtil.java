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

package com.liferay.commerce.tax.service;

import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CommerceTaxMethod. This utility wraps
 * <code>com.liferay.commerce.tax.service.impl.CommerceTaxMethodServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceTaxMethodService
 * @generated
 */
public class CommerceTaxMethodServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.tax.service.impl.CommerceTaxMethodServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceTaxMethod addCommerceTaxMethod(
			long groupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String engineKey,
			boolean percentage, boolean active)
		throws PortalException {

		return getService().addCommerceTaxMethod(
			groupId, nameMap, descriptionMap, engineKey, percentage, active);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceTaxMethod addCommerceTaxMethod(
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String engineKey,
			boolean percentage, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceTaxMethod(
			nameMap, descriptionMap, engineKey, percentage, active,
			serviceContext);
	}

	public static CommerceTaxMethod createCommerceTaxMethod(
			long groupId, long commerceTaxMethodId)
		throws PortalException {

		return getService().createCommerceTaxMethod(
			groupId, commerceTaxMethodId);
	}

	public static void deleteCommerceTaxMethod(long commerceTaxMethodId)
		throws PortalException {

		getService().deleteCommerceTaxMethod(commerceTaxMethodId);
	}

	public static CommerceTaxMethod fetchCommerceTaxMethod(
			long groupId, String engineKey)
		throws PortalException {

		return getService().fetchCommerceTaxMethod(groupId, engineKey);
	}

	public static CommerceTaxMethod getCommerceTaxMethod(
			long commerceTaxMethodId)
		throws PortalException {

		return getService().getCommerceTaxMethod(commerceTaxMethodId);
	}

	public static List<CommerceTaxMethod> getCommerceTaxMethods(long groupId)
		throws PortalException {

		return getService().getCommerceTaxMethods(groupId);
	}

	public static List<CommerceTaxMethod> getCommerceTaxMethods(
			long groupId, boolean active)
		throws PortalException {

		return getService().getCommerceTaxMethods(groupId, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceTaxMethod setActive(
			long commerceTaxMethodId, boolean active)
		throws PortalException {

		return getService().setActive(commerceTaxMethodId, active);
	}

	public static CommerceTaxMethod updateCommerceTaxMethod(
			long commerceTaxMethodId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean percentage,
			boolean active)
		throws PortalException {

		return getService().updateCommerceTaxMethod(
			commerceTaxMethodId, nameMap, descriptionMap, percentage, active);
	}

	public static CommerceTaxMethodService getService() {
		return _service;
	}

	private static volatile CommerceTaxMethodService _service;

}