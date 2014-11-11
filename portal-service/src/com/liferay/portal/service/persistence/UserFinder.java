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

/**
 * @generated
 */
@ProviderType
public interface UserFinder {
	public int countBySocialUsers(long companyId, long userId,
		int socialRelationType, java.lang.String socialRelationTypeComparator,
		int status);

	public int countByUser(long userId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params);

	public int countByKeywords(long companyId, java.lang.String keywords,
		int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params);

	public int countByC_FN_MN_LN_SN_EA_S(long companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator);

	public int countByC_FN_MN_LN_SN_EA_S(long companyId,
		java.lang.String[] firstNames, java.lang.String[] middleNames,
		java.lang.String[] lastNames, java.lang.String[] screenNames,
		java.lang.String[] emailAddresses, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator);

	public java.util.List<com.liferay.portal.model.User> findByKeywords(
		long companyId, java.lang.String keywords, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.User> obc);

	public java.util.List<com.liferay.portal.model.User> findByNoAnnouncementsDeliveries(
		java.lang.String type);

	public java.util.List<com.liferay.portal.model.User> findByNoContacts();

	public java.util.List<com.liferay.portal.model.User> findByNoGroups();

	public java.util.List<com.liferay.portal.model.User> findBySocialUsers(
		long companyId, long userId, int socialRelationType,
		java.lang.String socialRelationTypeComparator, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.User> obc);

	public java.util.List<com.liferay.portal.model.User> findByC_FN_MN_LN_SN_EA_S(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.User> obc);

	public java.util.List<com.liferay.portal.model.User> findByC_FN_MN_LN_SN_EA_S(
		long companyId, java.lang.String[] firstNames,
		java.lang.String[] middleNames, java.lang.String[] lastNames,
		java.lang.String[] screenNames, java.lang.String[] emailAddresses,
		int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.User> obc);
}