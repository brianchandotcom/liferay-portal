/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.object.model.ObjectView;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for ObjectView. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectViewServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectViewService
 * @generated
 */
public class ObjectViewServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectViewServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectView addObjectView(
			long objectDefinitionId, boolean defaultObjectView,
			Map<java.util.Locale, String> nameMap,
			List<com.liferay.object.model.ObjectViewColumn> objectViewColumns,
			List<com.liferay.object.model.ObjectViewFilterColumn>
				objectViewFilterColumns,
			List<com.liferay.object.model.ObjectViewSortColumn>
				objectViewSortColumns)
		throws PortalException {

		return getService().addObjectView(
			objectDefinitionId, defaultObjectView, nameMap, objectViewColumns,
			objectViewFilterColumns, objectViewSortColumns);
	}

	public static ObjectView deleteObjectView(long objectViewId)
		throws PortalException {

		return getService().deleteObjectView(objectViewId);
	}

	public static ObjectView getObjectView(long objectViewId)
		throws PortalException {

		return getService().getObjectView(objectViewId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectView updateObjectView(
			long objectViewId, boolean defaultObjectView,
			Map<java.util.Locale, String> nameMap,
			List<com.liferay.object.model.ObjectViewColumn> objectViewColumns,
			List<com.liferay.object.model.ObjectViewFilterColumn>
				objectViewFilterColumns,
			List<com.liferay.object.model.ObjectViewSortColumn>
				objectViewSortColumns)
		throws PortalException {

		return getService().updateObjectView(
			objectViewId, defaultObjectView, nameMap, objectViewColumns,
			objectViewFilterColumns, objectViewSortColumns);
	}

	public static ObjectViewService getService() {
		return _service;
	}

	public static void setService(ObjectViewService service) {
		_service = service;
	}

	private static volatile ObjectViewService _service;

}