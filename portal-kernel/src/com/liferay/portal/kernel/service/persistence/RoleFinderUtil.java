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
public class RoleFinderUtil {

	public static int countByGroupRoleAndTeamRole(
		long companyId, String keywords, java.util.List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId) {

		return getFinder().countByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId);
	}

	public static int countByKeywords(
		long companyId, String keywords, Integer[] types) {

		return getFinder().countByKeywords(companyId, keywords, types);
	}

	public static int countByKeywords(
		long companyId, String keywords, Integer[] types,
		java.util.LinkedHashMap<String, Object> params) {

		return getFinder().countByKeywords(companyId, keywords, types, params);
	}

	public static int countByC_N_D_T(
		long companyId, String name, String description, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().countByC_N_D_T(
			companyId, name, description, types, params, andOperator);
	}

	public static int countByC_N_D_T(
		long companyId, String[] names, String[] descriptions, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().countByC_N_D_T(
			companyId, names, descriptions, types, params, andOperator);
	}

	public static int filterCountByGroupRoleAndTeamRole(
		long companyId, String keywords, java.util.List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId) {

		return getFinder().filterCountByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId);
	}

	public static int filterCountByKeywords(
		long companyId, String keywords, Integer[] types,
		java.util.LinkedHashMap<String, Object> params) {

		return getFinder().filterCountByKeywords(
			companyId, keywords, types, params);
	}

	public static int filterCountByC_N_D_T(
		long companyId, String name, String description, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().filterCountByC_N_D_T(
			companyId, name, description, types, params, andOperator);
	}

	public static int filterCountByC_N_D_T(
		long companyId, String[] names, String[] descriptions, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().filterCountByC_N_D_T(
			companyId, names, descriptions, types, params, andOperator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		filterFindByGroupRoleAndTeamRole(
			long companyId, String keywords,
			java.util.List<String> excludedNames, int[] types,
			long excludedTeamRoleId, long teamGroupId, int start, int end) {

		return getFinder().filterFindByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		filterFindByKeywords(
			long companyId, String keywords, Integer[] types,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return getFinder().filterFindByKeywords(
			companyId, keywords, types, params, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		filterFindByC_N_D_T(
			long companyId, String name, String description, Integer[] types,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return getFinder().filterFindByC_N_D_T(
			companyId, name, description, types, params, andOperator, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		filterFindByC_N_D_T(
			long companyId, String[] names, String[] descriptions,
			Integer[] types, java.util.LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return getFinder().filterFindByC_N_D_T(
			companyId, names, descriptions, types, params, andOperator, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		findByGroupRoleAndTeamRole(
			long companyId, String keywords,
			java.util.List<String> excludedNames, int[] types,
			long excludedTeamRoleId, long teamGroupId, int start, int end) {

		return getFinder().findByGroupRoleAndTeamRole(
			companyId, keywords, excludedNames, types, excludedTeamRoleId,
			teamGroupId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		findByKeywords(
			long companyId, String keywords, Integer[] types, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return getFinder().findByKeywords(
			companyId, keywords, types, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		findByKeywords(
			long companyId, String keywords, Integer[] types,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return getFinder().findByKeywords(
			companyId, keywords, types, params, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		findByC_N_D_T(
			long companyId, String name, String description, Integer[] types,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return getFinder().findByC_N_D_T(
			companyId, name, description, types, params, andOperator, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		findByC_N_D_T(
			long companyId, String[] names, String[] descriptions,
			Integer[] types, java.util.LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Role> orderByComparator) {

		return getFinder().findByC_N_D_T(
			companyId, names, descriptions, types, params, andOperator, start,
			end, orderByComparator);
	}

	public static RoleFinder getFinder() {
		if (_finder == null) {
			_finder = (RoleFinder)PortalBeanLocatorUtil.locate(
				RoleFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(RoleFinder finder) {
		_finder = finder;
	}

	private static RoleFinder _finder;

}