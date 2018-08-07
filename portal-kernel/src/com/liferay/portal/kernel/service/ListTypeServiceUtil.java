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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for ListType. This utility wraps
 * {@link com.liferay.portal.service.impl.ListTypeServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ListTypeService
 * @see com.liferay.portal.service.base.ListTypeServiceBaseImpl
 * @see com.liferay.portal.service.impl.ListTypeServiceImpl
 * @generated
 */
@ProviderType
public class ListTypeServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ListTypeServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.ListType getListType(
		long listTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getListType(listTypeId);
	}

	public static com.liferay.portal.kernel.model.ListType getListType(
		String name, String type) {
		return getService().getListType(name, type);
	}

	public static java.util.List<com.liferay.portal.kernel.model.ListType> getListTypes(
		String type) {
		return getService().getListTypes(type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void validate(long listTypeId, long classNameId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(listTypeId, classNameId, type);
	}

	public static void validate(long listTypeId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(listTypeId, type);
	}

	public static ListTypeService getService() {
		if (_service == null) {
			_service = (ListTypeService)PortalBeanLocatorUtil.locate(ListTypeService.class.getName());

			ReferenceRegistry.registerReference(ListTypeServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ListTypeService _service;
}