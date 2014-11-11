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
public class PasswordPolicyFinderUtil {
	public static int countByC_N(long companyId, java.lang.String name) {
		return getFinder().countByC_N(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicy> findByC_N(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.PasswordPolicy> obc) {
		return getFinder().findByC_N(companyId, name, start, end, obc);
	}

	public static PasswordPolicyFinder getFinder() {
		if (_finder == null) {
			_finder = (PasswordPolicyFinder)PortalBeanLocatorUtil.locate(PasswordPolicyFinder.class.getName());

			ReferenceRegistry.registerReference(PasswordPolicyFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(PasswordPolicyFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(PasswordPolicyFinderUtil.class,
			"_finder");
	}

	private static PasswordPolicyFinder _finder;
}