/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for LayoutPrototype. This utility wraps
 * <code>com.liferay.portal.service.impl.LayoutPrototypeServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypeService
 * @generated
 */
public class LayoutPrototypeServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.LayoutPrototypeServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static LayoutPrototype addLayoutPrototype(
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return getService().addLayoutPrototype(
			nameMap, descriptionMap, active, serviceContext);
	}

	public static void deleteLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		getService().deleteLayoutPrototype(layoutPrototypeId);
	}

	public static LayoutPrototype fetchLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		return getService().fetchLayoutPrototype(layoutPrototypeId);
	}

	public static LayoutPrototype getLayoutPrototype(long layoutPrototypeId)
		throws PortalException {

		return getService().getLayoutPrototype(layoutPrototypeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<LayoutPrototype> search(
			long companyId, Boolean active,
			OrderByComparator<LayoutPrototype> orderByComparator)
		throws PortalException {

		return getService().search(companyId, active, orderByComparator);
	}

	public static LayoutPrototype updateLayoutPrototype(
			long layoutPrototypeId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return getService().updateLayoutPrototype(
			layoutPrototypeId, nameMap, descriptionMap, active, serviceContext);
	}

	public static LayoutPrototypeService getService() {
		return _service;
	}

	public static void setService(LayoutPrototypeService service) {
		_service = service;
	}

	private static volatile LayoutPrototypeService _service;

}