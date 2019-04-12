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

/**
 * Provides the remote service utility for LayoutSetPrototype. This utility wraps
 * <code>com.liferay.portal.service.impl.LayoutSetPrototypeServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeService
 * @generated
 */
@ProviderType
public class LayoutSetPrototypeServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.LayoutSetPrototypeServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			addLayoutSetPrototype(
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean active, boolean layoutsUpdateable,
				ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addLayoutSetPrototype(
			nameMap, descriptionMap, active, layoutsUpdateable, serviceContext);
	}

	public static void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			fetchLayoutSetPrototype(long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchLayoutSetPrototype(layoutSetPrototypeId);
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			getLayoutSetPrototype(long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutSetPrototype(layoutSetPrototypeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutSetPrototype> search(
				long companyId, Boolean active,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.LayoutSetPrototype> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(companyId, active, obc);
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			updateLayoutSetPrototype(
				long layoutSetPrototypeId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean active, boolean layoutsUpdateable,
				ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutSetPrototype(
			layoutSetPrototypeId, nameMap, descriptionMap, active,
			layoutsUpdateable, serviceContext);
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			updateLayoutSetPrototype(long layoutSetPrototypeId, String settings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutSetPrototype(
			layoutSetPrototypeId, settings);
	}

	public static LayoutSetPrototypeService getService() {
		if (_service == null) {
			_service = (LayoutSetPrototypeService)PortalBeanLocatorUtil.locate(
				LayoutSetPrototypeService.class.getName());
		}

		return _service;
	}

	private static LayoutSetPrototypeService _service;

}