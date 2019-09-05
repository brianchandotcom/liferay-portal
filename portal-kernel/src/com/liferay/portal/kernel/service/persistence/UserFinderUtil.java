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
public class UserFinderUtil {

	public static java.util.Map<Long, Integer> countByGroups(
		long companyId, int status, long[] groupIds) {

		return getFinder().countByGroups(companyId, status, groupIds);
	}

	public static int countByKeywords(
		long companyId, String keywords, int status,
		java.util.LinkedHashMap<String, Object> params) {

		return getFinder().countByKeywords(companyId, keywords, status, params);
	}

	public static int countByOrganizationsAndUserGroups(
		long[] organizationIds, long[] userGroupIds) {

		return getFinder().countByOrganizationsAndUserGroups(
			organizationIds, userGroupIds);
	}

	public static int countBySocialUsers(
		long companyId, long userId, int socialRelationType,
		String socialRelationTypeComparator, int status) {

		return getFinder().countBySocialUsers(
			companyId, userId, socialRelationType, socialRelationTypeComparator,
			status);
	}

	public static int countByUser(
		long userId, java.util.LinkedHashMap<String, Object> params) {

		return getFinder().countByUser(userId, params);
	}

	public static int countByC_FN_MN_LN_SN_EA_S(
		long companyId, String firstName, String middleName, String lastName,
		String screenName, String emailAddress, int status,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getFinder().countByC_FN_MN_LN_SN_EA_S(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, status, params, andOperator);
	}

	public static int countByC_FN_MN_LN_SN_EA_S(
		long companyId, String[] firstNames, String[] middleNames,
		String[] lastNames, String[] screenNames, String[] emailAddresses,
		int status, java.util.LinkedHashMap<String, Object> params,
		boolean andOperator) {

		return getFinder().countByC_FN_MN_LN_SN_EA_S(
			companyId, firstNames, middleNames, lastNames, screenNames,
			emailAddresses, status, params, andOperator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByKeywords(
			long companyId, String keywords, int status,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc) {

		return getFinder().findByKeywords(
			companyId, keywords, status, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByNoAnnouncementsDeliveries(String type) {

		return getFinder().findByNoAnnouncementsDeliveries(type);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByNoContacts() {

		return getFinder().findByNoContacts();
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByNoGroups() {

		return getFinder().findByNoGroups();
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findBySocialUsers(
			long companyId, long userId, int socialRelationType,
			String socialRelationTypeComparator, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc) {

		return getFinder().findBySocialUsers(
			companyId, userId, socialRelationType, socialRelationTypeComparator,
			status, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByUsersOrgsGtUserId(
			long companyId, long organizationId, long gtUserId, int size) {

		return getFinder().findByUsersOrgsGtUserId(
			companyId, organizationId, gtUserId, size);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByUsersUserGroupsGtUserId(
			long companyId, long userGroupId, long gtUserId, int size) {

		return getFinder().findByUsersUserGroupsGtUserId(
			companyId, userGroupId, gtUserId, size);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByC_FN_MN_LN_SN_EA_S(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress, int status,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc) {

		return getFinder().findByC_FN_MN_LN_SN_EA_S(
			companyId, firstName, middleName, lastName, screenName,
			emailAddress, status, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
		findByC_FN_MN_LN_SN_EA_S(
			long companyId, String[] firstNames, String[] middleNames,
			String[] lastNames, String[] screenNames, String[] emailAddresses,
			int status, java.util.LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc) {

		return getFinder().findByC_FN_MN_LN_SN_EA_S(
			companyId, firstNames, middleNames, lastNames, screenNames,
			emailAddresses, status, params, andOperator, start, end, obc);
	}

	public static UserFinder getFinder() {
		if (_finder == null) {
			_finder = (UserFinder)PortalBeanLocatorUtil.locate(
				UserFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(UserFinder finder) {
		_finder = finder;
	}

	private static UserFinder _finder;

}