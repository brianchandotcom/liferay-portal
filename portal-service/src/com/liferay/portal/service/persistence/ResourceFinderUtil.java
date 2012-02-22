/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceFinderUtil {
	public static java.util.List<com.liferay.portal.model.Resource> findByContainerResource(
		long codeId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByContainerResource(codeId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findByMissingAction(
		long codeId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByMissingAction(codeId, actionId);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByName(name);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findByC_P(
		long companyId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByC_P(companyId, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findByN_S(
		java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByN_S(name, scope);
	}

	public static ResourceFinder getFinder() {
		if (_finder == null) {
			_finder = (ResourceFinder)PortalBeanLocatorUtil.locate(ResourceFinder.class.getName());

			ReferenceRegistry.registerReference(ResourceFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ResourceFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ResourceFinderUtil.class, "_finder");
	}

	private static ResourceFinder _finder;
}