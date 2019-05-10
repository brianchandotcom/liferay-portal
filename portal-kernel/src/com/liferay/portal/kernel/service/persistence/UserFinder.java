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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface UserFinder {

	public java.util.Map<Long, Integer> countByGroups(
		long companyId, int status, long[] groupIds);

	public int countByKeywords(
		long companyId, String keywords, int status,
		java.util.LinkedHashMap<String, Object> params);

	public int countByOrganizationsAndUserGroups(
		long[] organizationIds, long[] userGroupIds);

	public int countBySocialUsers(
		long companyId, long userId, int socialRelationType,
		String socialRelationTypeComparator, int status);

	public int countByUser(
		long userId, java.util.LinkedHashMap<String, Object> params);

	public int countByC_FN_MN_LN_SN_EA_S(
		long companyId, String firstName, String middleName, String lastName,
		String screenName, String emailAddress, int status,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator);

	public int countByC_FN_MN_LN_SN_EA_S(
		long companyId, String[] firstNames, String[] middleNames,
		String[] lastNames, String[] screenNames, String[] emailAddresses,
		int status, java.util.LinkedHashMap<String, Object> params,
		boolean andOperator);

	public java.util.List<com.liferay.portal.kernel.model.User> findByKeywords(
		long companyId, String keywords, int status,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.User> obc);

	public java.util.List<com.liferay.portal.kernel.model.User>
		findByNoAnnouncementsDeliveries(String type);

	public java.util.List<com.liferay.portal.kernel.model.User>
		findByNoContacts();

	public java.util.List<com.liferay.portal.kernel.model.User>
		findByNoGroups();

	public java.util.List<com.liferay.portal.kernel.model.User>
		findBySocialUsers(
			long companyId, long userId, int socialRelationType,
			String socialRelationTypeComparator, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc);

	public java.util.List<com.liferay.portal.kernel.model.User>
		findByUsersOrgsGtUserId(
			long companyId, long organizationId, long gtUserId, int size);

	public java.util.List<com.liferay.portal.kernel.model.User>
		findByUsersUserGroupsGtUserId(
			long companyId, long userGroupId, long gtUserId, int size);

	public java.util.List<com.liferay.portal.kernel.model.User>
		findByC_FN_MN_LN_SN_EA_S(
			long companyId, String firstName, String middleName,
			String lastName, String screenName, String emailAddress, int status,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc);

	public java.util.List<com.liferay.portal.kernel.model.User>
		findByC_FN_MN_LN_SN_EA_S(
			long companyId, String[] firstNames, String[] middleNames,
			String[] lastNames, String[] screenNames, String[] emailAddresses,
			int status, java.util.LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc);

}