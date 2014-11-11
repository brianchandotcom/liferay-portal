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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class LockFinderUtil {
	public static com.liferay.portal.model.Lock fetchByC_K(
		java.lang.String className, java.lang.String key,
		com.liferay.portal.kernel.dao.orm.LockMode lockMode) {
		return getFinder().fetchByC_K(className, key, lockMode);
	}

	public static LockFinder getFinder() {
		if (_finder == null) {
			_finder = (LockFinder)PortalBeanLocatorUtil.locate(LockFinder.class.getName());

			ReferenceRegistry.registerReference(LockFinderUtil.class, "_finder");
		}

		return _finder;
	}

	public void setFinder(LockFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(LockFinderUtil.class, "_finder");
	}

	private static LockFinder _finder;
}