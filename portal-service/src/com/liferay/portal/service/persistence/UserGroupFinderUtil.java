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
public class UserGroupFinderUtil {
	public static int countByKeywords(long companyId,
		java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params) {
		return getFinder().countByKeywords(companyId, keywords, params);
	}

	public static int countByC_N_D(long companyId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getFinder()
				   .countByC_N_D(companyId, name, description, params,
			andOperator);
	}

	public static int countByC_N_D(long companyId, java.lang.String[] names,
		java.lang.String[] descriptions,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getFinder()
				   .countByC_N_D(companyId, names, descriptions, params,
			andOperator);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByKeywords(
		long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> obc) {
		return getFinder()
				   .findByKeywords(companyId, keywords, params, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroup findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException {
		return getFinder().findByC_N(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_N_D(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> obc) {
		return getFinder()
				   .findByC_N_D(companyId, name, description, params,
			andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_N_D(
		long companyId, java.lang.String[] names,
		java.lang.String[] descriptions,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> obc) {
		return getFinder()
				   .findByC_N_D(companyId, names, descriptions, params,
			andOperator, start, end, obc);
	}

	public static UserGroupFinder getFinder() {
		if (_finder == null) {
			_finder = (UserGroupFinder)PortalBeanLocatorUtil.locate(UserGroupFinder.class.getName());

			ReferenceRegistry.registerReference(UserGroupFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(UserGroupFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(UserGroupFinderUtil.class, "_finder");
	}

	private static UserGroupFinder _finder;
}