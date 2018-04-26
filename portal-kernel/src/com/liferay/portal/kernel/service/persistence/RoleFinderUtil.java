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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class RoleFinderUtil {
	public static int countByGroupRoleAndTeamRole(long companyId,
		String keywords, java.util.List<String> excludedNames, int[] types,
		long excludedTeamRoleId, long teamGroupId) {
		return getFinder()
				   .countByGroupRoleAndTeamRole(companyId, keywords,
			excludedNames, types, excludedTeamRoleId, teamGroupId);
	}

	public static int countByKeywords(long companyId, String keywords,
		Integer[] types) {
		return getFinder().countByKeywords(companyId, keywords, types);
	}

	public static int countByKeywords(long companyId, String keywords,
		Integer[] types, java.util.LinkedHashMap<String, Object> params) {
		return getFinder().countByKeywords(companyId, keywords, types, params);
	}

	public static int countByUserGroupGroupRole(long userId, long groupId) {
		return getFinder().countByUserGroupGroupRole(userId, groupId);
	}

	public static int countByR_U(long roleId, long userId) {
		return getFinder().countByR_U(roleId, userId);
	}

	public static int countByU_G_R(long userId, long groupId, long roleId) {
		return getFinder().countByU_G_R(userId, groupId, roleId);
	}

	public static int countByC_N_D_T(long companyId, String name,
		String description, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .countByC_N_D_T(companyId, name, description, types, params,
			andOperator);
	}

	public static int countByC_N_D_T(long companyId, String[] names,
		String[] descriptions, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .countByC_N_D_T(companyId, names, descriptions, types,
			params, andOperator);
	}

	public static int filterCountByGroupRoleAndTeamRole(long companyId,
		String keywords, java.util.List<String> excludedNames, int[] types,
		long excludedTeamRoleId, long teamGroupId) {
		return getFinder()
				   .filterCountByGroupRoleAndTeamRole(companyId, keywords,
			excludedNames, types, excludedTeamRoleId, teamGroupId);
	}

	public static int filterCountByKeywords(long companyId, String keywords,
		Integer[] types, java.util.LinkedHashMap<String, Object> params) {
		return getFinder()
				   .filterCountByKeywords(companyId, keywords, types, params);
	}

	public static int filterCountByC_N_D_T(long companyId, String name,
		String description, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .filterCountByC_N_D_T(companyId, name, description, types,
			params, andOperator);
	}

	public static int filterCountByC_N_D_T(long companyId, String[] names,
		String[] descriptions, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {
		return getFinder()
				   .filterCountByC_N_D_T(companyId, names, descriptions, types,
			params, andOperator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> filterFindByGroupRoleAndTeamRole(
		long companyId, String keywords, java.util.List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId, int start,
		int end) {
		return getFinder()
				   .filterFindByGroupRoleAndTeamRole(companyId, keywords,
			excludedNames, types, excludedTeamRoleId, teamGroupId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> filterFindByKeywords(
		long companyId, String keywords, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getFinder()
				   .filterFindByKeywords(companyId, keywords, types, params,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> filterFindByC_N_D_T(
		long companyId, String name, String description, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getFinder()
				   .filterFindByC_N_D_T(companyId, name, description, types,
			params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> filterFindByC_N_D_T(
		long companyId, String[] names, String[] descriptions, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getFinder()
				   .filterFindByC_N_D_T(companyId, names, descriptions, types,
			params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByGroupRoleAndTeamRole(
		long companyId, String keywords, java.util.List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId, int start,
		int end) {
		return getFinder()
				   .findByGroupRoleAndTeamRole(companyId, keywords,
			excludedNames, types, excludedTeamRoleId, teamGroupId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByKeywords(
		long companyId, String keywords, Integer[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getFinder()
				   .findByKeywords(companyId, keywords, types, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByKeywords(
		long companyId, String keywords, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getFinder()
				   .findByKeywords(companyId, keywords, types, params, start,
			end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findBySystem(
		long companyId) {
		return getFinder().findBySystem(companyId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByTeamsUser(
		long userId, long groupId) {
		return getFinder().findByTeamsUser(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByUserGroupGroupRole(
		long userId, long groupId) {
		return getFinder().findByUserGroupGroupRole(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByUserGroupGroupRole(
		long userId, long groupId, int start, int end) {
		return getFinder().findByUserGroupGroupRole(userId, groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByUserGroupRole(
		long userId, long groupId) {
		return getFinder().findByUserGroupRole(userId, groupId);
	}

	public static com.liferay.portal.kernel.model.Role findByC_N(
		long companyId, String name)
		throws com.liferay.portal.kernel.exception.NoSuchRoleException {
		return getFinder().findByC_N(companyId, name);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByU_G(
		long userId,
		java.util.List<com.liferay.portal.kernel.model.Group> groups) {
		return getFinder().findByU_G(userId, groups);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByU_G(
		long userId, long groupId) {
		return getFinder().findByU_G(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByU_G(
		long userId, long[] groupIds) {
		return getFinder().findByU_G(userId, groupIds);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByR_N_A(
		long resourceBlockId, String className, String actionId) {
		return getFinder().findByR_N_A(resourceBlockId, className, actionId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByC_N_D_T(
		long companyId, String name, String description, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getFinder()
				   .findByC_N_D_T(companyId, name, description, types, params,
			andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByC_N_D_T(
		long companyId, String[] names, String[] descriptions, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getFinder()
				   .findByC_N_D_T(companyId, names, descriptions, types,
			params, andOperator, start, end, obc);
	}

	public static java.util.Map<String, java.util.List<String>> findByC_N_S_P(
		long companyId, String name, int scope, String primKey) {
		return getFinder().findByC_N_S_P(companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> findByC_N_S_P_A(
		long companyId, String name, int scope, String primKey, String actionId) {
		return getFinder()
				   .findByC_N_S_P_A(companyId, name, scope, primKey, actionId);
	}

	public static RoleFinder getFinder() {
		if (_finder == null) {
			_finder = (RoleFinder)PortalBeanLocatorUtil.locate(RoleFinder.class.getName());

			ReferenceRegistry.registerReference(RoleFinderUtil.class, "_finder");
		}

		return _finder;
	}

	public void setFinder(RoleFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(RoleFinderUtil.class, "_finder");
	}

	private static RoleFinder _finder;
}