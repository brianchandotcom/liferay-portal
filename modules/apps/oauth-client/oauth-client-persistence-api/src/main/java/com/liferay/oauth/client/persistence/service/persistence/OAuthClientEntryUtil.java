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

package com.liferay.oauth.client.persistence.service.persistence;

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the o auth client entry service. This utility wraps <code>com.liferay.oauth.client.persistence.service.persistence.impl.OAuthClientEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientEntryPersistence
 * @generated
 */
public class OAuthClientEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(OAuthClientEntry oAuthClientEntry) {
		getPersistence().clearCache(oAuthClientEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, OAuthClientEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OAuthClientEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuthClientEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuthClientEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuthClientEntry update(OAuthClientEntry oAuthClientEntry) {
		return getPersistence().update(oAuthClientEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuthClientEntry update(
		OAuthClientEntry oAuthClientEntry, ServiceContext serviceContext) {

		return getPersistence().update(oAuthClientEntry, serviceContext);
	}

	/**
	 * Returns all the o auth client entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry[] findByCompanyId_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			oAuthClientEntryId, companyId, orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set of o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry[] filterFindByCompanyId_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			oAuthClientEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the o auth client entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth client entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the o auth client entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry findByUserId_First(
			long userId, OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByUserId_First(
		long userId, OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry findByUserId_Last(
			long userId, OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByUserId_Last(
		long userId, OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set where userId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry[] findByUserId_PrevAndNext(
			long oAuthClientEntryId, long userId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByUserId_PrevAndNext(
			oAuthClientEntryId, userId, orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByUserId(long userId) {
		return getPersistence().filterFindByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByUserId(
		long userId, int start, int end) {

		return getPersistence().filterFindByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().filterFindByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set of o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry[] filterFindByUserId_PrevAndNext(
			long oAuthClientEntryId, long userId,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().filterFindByUserId_PrevAndNext(
			oAuthClientEntryId, userId, orderByComparator);
	}

	/**
	 * Removes all the o auth client entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of o auth client entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client entries
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	public static int filterCountByUserId(long userId) {
		return getPersistence().filterCountByUserId(userId);
	}

	/**
	 * Returns all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI) {

		return getPersistence().findByC_A(companyId, authServerWellKnownURI);
	}

	/**
	 * Returns a range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end) {

		return getPersistence().findByC_A(
			companyId, authServerWellKnownURI, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, authServerWellKnownURI, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client entries
	 */
	public static List<OAuthClientEntry> findByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, authServerWellKnownURI, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry findByC_A_First(
			long companyId, String authServerWellKnownURI,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByC_A_First(
			companyId, authServerWellKnownURI, orderByComparator);
	}

	/**
	 * Returns the first o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByC_A_First(
		long companyId, String authServerWellKnownURI,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, authServerWellKnownURI, orderByComparator);
	}

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry findByC_A_Last(
			long companyId, String authServerWellKnownURI,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByC_A_Last(
			companyId, authServerWellKnownURI, orderByComparator);
	}

	/**
	 * Returns the last o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByC_A_Last(
		long companyId, String authServerWellKnownURI,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, authServerWellKnownURI, orderByComparator);
	}

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry[] findByC_A_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			String authServerWellKnownURI,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByC_A_PrevAndNext(
			oAuthClientEntryId, companyId, authServerWellKnownURI,
			orderByComparator);
	}

	/**
	 * Returns all the o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI) {

		return getPersistence().filterFindByC_A(
			companyId, authServerWellKnownURI);
	}

	/**
	 * Returns a range of all the o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI, int start, int end) {

		return getPersistence().filterFindByC_A(
			companyId, authServerWellKnownURI, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client entries that the user has permissions to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client entries that the user has permission to view
	 */
	public static List<OAuthClientEntry> filterFindByC_A(
		long companyId, String authServerWellKnownURI, int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().filterFindByC_A(
			companyId, authServerWellKnownURI, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth client entries before and after the current o auth client entry in the ordered set of o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param oAuthClientEntryId the primary key of the current o auth client entry
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry[] filterFindByC_A_PrevAndNext(
			long oAuthClientEntryId, long companyId,
			String authServerWellKnownURI,
			OrderByComparator<OAuthClientEntry> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().filterFindByC_A_PrevAndNext(
			oAuthClientEntryId, companyId, authServerWellKnownURI,
			orderByComparator);
	}

	/**
	 * Removes all the o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 */
	public static void removeByC_A(
		long companyId, String authServerWellKnownURI) {

		getPersistence().removeByC_A(companyId, authServerWellKnownURI);
	}

	/**
	 * Returns the number of o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the number of matching o auth client entries
	 */
	public static int countByC_A(
		long companyId, String authServerWellKnownURI) {

		return getPersistence().countByC_A(companyId, authServerWellKnownURI);
	}

	/**
	 * Returns the number of o auth client entries that the user has permission to view where companyId = &#63; and authServerWellKnownURI = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @return the number of matching o auth client entries that the user has permission to view
	 */
	public static int filterCountByC_A(
		long companyId, String authServerWellKnownURI) {

		return getPersistence().filterCountByC_A(
			companyId, authServerWellKnownURI);
	}

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or throws a <code>NoSuchOAuthClientEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the matching o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry findByC_A_C(
			long companyId, String authServerWellKnownURI, String clientId)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByC_A_C(
			companyId, authServerWellKnownURI, clientId);
	}

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId) {

		return getPersistence().fetchByC_A_C(
			companyId, authServerWellKnownURI, clientId);
	}

	/**
	 * Returns the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client entry, or <code>null</code> if a matching o auth client entry could not be found
	 */
	public static OAuthClientEntry fetchByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_A_C(
			companyId, authServerWellKnownURI, clientId, useFinderCache);
	}

	/**
	 * Removes the o auth client entry where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the o auth client entry that was removed
	 */
	public static OAuthClientEntry removeByC_A_C(
			long companyId, String authServerWellKnownURI, String clientId)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().removeByC_A_C(
			companyId, authServerWellKnownURI, clientId);
	}

	/**
	 * Returns the number of o auth client entries where companyId = &#63; and authServerWellKnownURI = &#63; and clientId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param authServerWellKnownURI the auth server well known uri
	 * @param clientId the client ID
	 * @return the number of matching o auth client entries
	 */
	public static int countByC_A_C(
		long companyId, String authServerWellKnownURI, String clientId) {

		return getPersistence().countByC_A_C(
			companyId, authServerWellKnownURI, clientId);
	}

	/**
	 * Caches the o auth client entry in the entity cache if it is enabled.
	 *
	 * @param oAuthClientEntry the o auth client entry
	 */
	public static void cacheResult(OAuthClientEntry oAuthClientEntry) {
		getPersistence().cacheResult(oAuthClientEntry);
	}

	/**
	 * Caches the o auth client entries in the entity cache if it is enabled.
	 *
	 * @param oAuthClientEntries the o auth client entries
	 */
	public static void cacheResult(List<OAuthClientEntry> oAuthClientEntries) {
		getPersistence().cacheResult(oAuthClientEntries);
	}

	/**
	 * Creates a new o auth client entry with the primary key. Does not add the o auth client entry to the database.
	 *
	 * @param oAuthClientEntryId the primary key for the new o auth client entry
	 * @return the new o auth client entry
	 */
	public static OAuthClientEntry create(long oAuthClientEntryId) {
		return getPersistence().create(oAuthClientEntryId);
	}

	/**
	 * Removes the o auth client entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry that was removed
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry remove(long oAuthClientEntryId)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().remove(oAuthClientEntryId);
	}

	public static OAuthClientEntry updateImpl(
		OAuthClientEntry oAuthClientEntry) {

		return getPersistence().updateImpl(oAuthClientEntry);
	}

	/**
	 * Returns the o auth client entry with the primary key or throws a <code>NoSuchOAuthClientEntryException</code> if it could not be found.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry
	 * @throws NoSuchOAuthClientEntryException if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry findByPrimaryKey(long oAuthClientEntryId)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchOAuthClientEntryException {

		return getPersistence().findByPrimaryKey(oAuthClientEntryId);
	}

	/**
	 * Returns the o auth client entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthClientEntryId the primary key of the o auth client entry
	 * @return the o auth client entry, or <code>null</code> if a o auth client entry with the primary key could not be found
	 */
	public static OAuthClientEntry fetchByPrimaryKey(long oAuthClientEntryId) {
		return getPersistence().fetchByPrimaryKey(oAuthClientEntryId);
	}

	/**
	 * Returns all the o auth client entries.
	 *
	 * @return the o auth client entries
	 */
	public static List<OAuthClientEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @return the range of o auth client entries
	 */
	public static List<OAuthClientEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth client entries
	 */
	public static List<OAuthClientEntry> findAll(
		int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client entries
	 * @param end the upper bound of the range of o auth client entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth client entries
	 */
	public static List<OAuthClientEntry> findAll(
		int start, int end,
		OrderByComparator<OAuthClientEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the o auth client entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of o auth client entries.
	 *
	 * @return the number of o auth client entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OAuthClientEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(OAuthClientEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile OAuthClientEntryPersistence _persistence;

}