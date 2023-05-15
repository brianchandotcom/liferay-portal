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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ListType;

import java.util.List;

/**
 * Provides the remote service utility for ListType. This utility wraps
 * <code>com.liferay.portal.service.impl.ListTypeServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ListTypeService
 * @generated
 */
public class ListTypeServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.ListTypeServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ListType getListType(long listTypeId) throws PortalException {
		return getService().getListType(listTypeId);
	}

	public static ListType getListType(String name, String type) {
		return getService().getListType(name, type);
	}

	public static List<ListType> getListTypes(String type) {
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
		throws PortalException {

		getService().validate(listTypeId, classNameId, type);
	}

	public static void validate(long listTypeId, String type)
		throws PortalException {

		getService().validate(listTypeId, type);
	}

	public static ListTypeService getService() {
		return _service;
	}

	public static void setService(ListTypeService service) {
		_service = service;
	}

	private static volatile ListTypeService _service;

}