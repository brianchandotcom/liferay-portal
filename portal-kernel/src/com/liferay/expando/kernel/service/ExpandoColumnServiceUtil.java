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

package com.liferay.expando.kernel.service;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for ExpandoColumn. This utility wraps
 * <code>com.liferay.portlet.expando.service.impl.ExpandoColumnServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnService
 * @generated
 */
public class ExpandoColumnServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.expando.service.impl.ExpandoColumnServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException {

		return getService().addColumn(tableId, name, type);
	}

	public static ExpandoColumn addColumn(
			long tableId, String name, int type, Object defaultData)
		throws PortalException {

		return getService().addColumn(tableId, name, type, defaultData);
	}

	public static void deleteColumn(long columnId) throws PortalException {
		getService().deleteColumn(columnId);
	}

	public static ExpandoColumn fetchExpandoColumn(long columnId)
		throws PortalException {

		return getService().fetchExpandoColumn(columnId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ExpandoColumn updateColumn(
			long columnId, String name, int type)
		throws PortalException {

		return getService().updateColumn(columnId, name, type);
	}

	public static ExpandoColumn updateColumn(
			long columnId, String name, int type, Object defaultData)
		throws PortalException {

		return getService().updateColumn(columnId, name, type, defaultData);
	}

	public static ExpandoColumn updateTypeSettings(
			long columnId, String typeSettings)
		throws PortalException {

		return getService().updateTypeSettings(columnId, typeSettings);
	}

	public static ExpandoColumnService getService() {
		return _service;
	}

	public static void setService(ExpandoColumnService service) {
		_service = service;
	}

	private static volatile ExpandoColumnService _service;

}