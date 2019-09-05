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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupFinderUtil {

	public static int countByKeywords(
		long companyId, String keywords,
		java.util.LinkedHashMap<String, Object> params) {

		return getFinder().countByKeywords(companyId, keywords, params);
	}

	public static int countByC_N_D(
		long companyId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().countByC_N_D(
			companyId, name, description, params, andOperator);
	}

	public static int countByC_N_D(
		long companyId, String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().countByC_N_D(
			companyId, names, descriptions, params, andOperator);
	}

	public static int filterCountByKeywords(
		long companyId, String keywords,
		java.util.LinkedHashMap<String, Object> params) {

		return getFinder().filterCountByKeywords(companyId, keywords, params);
	}

	public static int filterCountByC_N_D(
		long companyId, String name, String description,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().filterCountByC_N_D(
			companyId, name, description, params, andOperator);
	}

	public static int filterCountByC_N_D(
		long companyId, String[] names, String[] descriptions,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().filterCountByC_N_D(
			companyId, names, descriptions, params, andOperator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByKeywords(
			long companyId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc) {

		return getFinder().filterFindByKeywords(
			companyId, keywords, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByC_N_D(
			long companyId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc) {

		return getFinder().filterFindByC_N_D(
			companyId, name, description, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		filterFindByC_N_D(
			long companyId, String[] names, String[] descriptions,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc) {

		return getFinder().filterFindByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByKeywords(
			long companyId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc) {

		return getFinder().findByKeywords(
			companyId, keywords, params, start, end, obc);
	}

	public static com.liferay.portal.kernel.model.UserGroup findByC_N(
			long companyId, String name)
		throws com.liferay.portal.kernel.exception.NoSuchUserGroupException {

		return getFinder().findByC_N(companyId, name);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByC_N_D(
			long companyId, String name, String description,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc) {

		return getFinder().findByC_N_D(
			companyId, name, description, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		findByC_N_D(
			long companyId, String[] names, String[] descriptions,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> obc) {

		return getFinder().findByC_N_D(
			companyId, names, descriptions, params, andOperator, start, end,
			obc);
	}

	public static UserGroupFinder getFinder() {
		if (_finder == null) {
			_finder = (UserGroupFinder)PortalBeanLocatorUtil.locate(
				UserGroupFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(UserGroupFinder finder) {
		_finder = finder;
	}

	private static UserGroupFinder _finder;

}