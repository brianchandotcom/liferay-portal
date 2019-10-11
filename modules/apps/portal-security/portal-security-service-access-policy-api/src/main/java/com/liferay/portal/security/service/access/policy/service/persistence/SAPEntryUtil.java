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

package com.liferay.portal.security.service.access.policy.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the sap entry service. This utility wraps <code>com.liferay.portal.security.service.access.policy.service.persistence.impl.SAPEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryPersistence
 * @generated
 */
public class SAPEntryUtil {

	/**
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
	public static void clearCache(SAPEntry sapEntry) {
		getPersistence().clearCache(sapEntry);
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
	public static Map<Serializable, SAPEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SAPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SAPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SAPEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SAPEntry update(SAPEntry sapEntry) {
		return getPersistence().update(sapEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SAPEntry update(
		SAPEntry sapEntry, ServiceContext serviceContext) {

		return getPersistence().update(sapEntry, serviceContext);
	}

	/**
	 * Returns all the sap entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sap entries
	 */
	public static List<SAPEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the sap entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public static List<SAPEntry> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByUuid_First(
			String uuid, OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByUuid_First(
		String uuid, OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByUuid_Last(
			String uuid, OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByUuid_Last(
		String uuid, OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where uuid = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] findByUuid_PrevAndNext(
			long sapEntryId, String uuid,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			sapEntryId, uuid, orderByComparator);
	}

	/**
	 * Returns all the sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] filterFindByUuid_PrevAndNext(
			long sapEntryId, String uuid,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			sapEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the sap entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of sap entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sap entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of sap entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sap entries
	 */
	public static List<SAPEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public static List<SAPEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] findByUuid_C_PrevAndNext(
			long sapEntryId, String uuid, long companyId,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			sapEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] filterFindByUuid_C_PrevAndNext(
			long sapEntryId, String uuid, long companyId,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			sapEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the sap entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of sap entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sap entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of sap entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the sap entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sap entries
	 */
	public static List<SAPEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the sap entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public static List<SAPEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByCompanyId_First(
			long companyId, OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByCompanyId_Last(
			long companyId, OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] findByCompanyId_PrevAndNext(
			long sapEntryId, long companyId,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			sapEntryId, companyId, orderByComparator);
	}

	/**
	 * Returns all the sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] filterFindByCompanyId_PrevAndNext(
			long sapEntryId, long companyId,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			sapEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the sap entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of sap entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sap entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of sap entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the matching sap entries
	 */
	public static List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry) {

		return getPersistence().findByC_D(companyId, defaultSAPEntry);
	}

	/**
	 * Returns a range of all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries
	 */
	public static List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end) {

		return getPersistence().findByC_D(
			companyId, defaultSAPEntry, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().findByC_D(
			companyId, defaultSAPEntry, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sap entries
	 */
	public static List<SAPEntry> findByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_D(
			companyId, defaultSAPEntry, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByC_D_First(
			long companyId, boolean defaultSAPEntry,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByC_D_First(
			companyId, defaultSAPEntry, orderByComparator);
	}

	/**
	 * Returns the first sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByC_D_First(
		long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByC_D_First(
			companyId, defaultSAPEntry, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByC_D_Last(
			long companyId, boolean defaultSAPEntry,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByC_D_Last(
			companyId, defaultSAPEntry, orderByComparator);
	}

	/**
	 * Returns the last sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByC_D_Last(
		long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().fetchByC_D_Last(
			companyId, defaultSAPEntry, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] findByC_D_PrevAndNext(
			long sapEntryId, long companyId, boolean defaultSAPEntry,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByC_D_PrevAndNext(
			sapEntryId, companyId, defaultSAPEntry, orderByComparator);
	}

	/**
	 * Returns all the sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByC_D(
		long companyId, boolean defaultSAPEntry) {

		return getPersistence().filterFindByC_D(companyId, defaultSAPEntry);
	}

	/**
	 * Returns a range of all the sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end) {

		return getPersistence().filterFindByC_D(
			companyId, defaultSAPEntry, start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries that the user has permissions to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sap entries that the user has permission to view
	 */
	public static List<SAPEntry> filterFindByC_D(
		long companyId, boolean defaultSAPEntry, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().filterFindByC_D(
			companyId, defaultSAPEntry, start, end, orderByComparator);
	}

	/**
	 * Returns the sap entries before and after the current sap entry in the ordered set of sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param sapEntryId the primary key of the current sap entry
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry[] filterFindByC_D_PrevAndNext(
			long sapEntryId, long companyId, boolean defaultSAPEntry,
			OrderByComparator<SAPEntry> orderByComparator)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().filterFindByC_D_PrevAndNext(
			sapEntryId, companyId, defaultSAPEntry, orderByComparator);
	}

	/**
	 * Removes all the sap entries where companyId = &#63; and defaultSAPEntry = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 */
	public static void removeByC_D(long companyId, boolean defaultSAPEntry) {
		getPersistence().removeByC_D(companyId, defaultSAPEntry);
	}

	/**
	 * Returns the number of sap entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the number of matching sap entries
	 */
	public static int countByC_D(long companyId, boolean defaultSAPEntry) {
		return getPersistence().countByC_D(companyId, defaultSAPEntry);
	}

	/**
	 * Returns the number of sap entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default sap entry
	 * @return the number of matching sap entries that the user has permission to view
	 */
	public static int filterCountByC_D(
		long companyId, boolean defaultSAPEntry) {

		return getPersistence().filterCountByC_D(companyId, defaultSAPEntry);
	}

	/**
	 * Returns the sap entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching sap entry
	 * @throws NoSuchEntryException if a matching sap entry could not be found
	 */
	public static SAPEntry findByC_N(long companyId, String name)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the sap entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the sap entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static SAPEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the sap entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the sap entry that was removed
	 */
	public static SAPEntry removeByC_N(long companyId, String name)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of sap entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching sap entries
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Caches the sap entry in the entity cache if it is enabled.
	 *
	 * @param sapEntry the sap entry
	 */
	public static void cacheResult(SAPEntry sapEntry) {
		getPersistence().cacheResult(sapEntry);
	}

	/**
	 * Caches the sap entries in the entity cache if it is enabled.
	 *
	 * @param sapEntries the sap entries
	 */
	public static void cacheResult(List<SAPEntry> sapEntries) {
		getPersistence().cacheResult(sapEntries);
	}

	/**
	 * Creates a new sap entry with the primary key. Does not add the sap entry to the database.
	 *
	 * @param sapEntryId the primary key for the new sap entry
	 * @return the new sap entry
	 */
	public static SAPEntry create(long sapEntryId) {
		return getPersistence().create(sapEntryId);
	}

	/**
	 * Removes the sap entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry that was removed
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry remove(long sapEntryId)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().remove(sapEntryId);
	}

	public static SAPEntry updateImpl(SAPEntry sapEntry) {
		return getPersistence().updateImpl(sapEntry);
	}

	/**
	 * Returns the sap entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry
	 * @throws NoSuchEntryException if a sap entry with the primary key could not be found
	 */
	public static SAPEntry findByPrimaryKey(long sapEntryId)
		throws com.liferay.portal.security.service.access.policy.exception.
			NoSuchEntryException {

		return getPersistence().findByPrimaryKey(sapEntryId);
	}

	/**
	 * Returns the sap entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry, or <code>null</code> if a sap entry with the primary key could not be found
	 */
	public static SAPEntry fetchByPrimaryKey(long sapEntryId) {
		return getPersistence().fetchByPrimaryKey(sapEntryId);
	}

	/**
	 * Returns all the sap entries.
	 *
	 * @return the sap entries
	 */
	public static List<SAPEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of sap entries
	 */
	public static List<SAPEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sap entries
	 */
	public static List<SAPEntry> findAll(
		int start, int end, OrderByComparator<SAPEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sap entries
	 */
	public static List<SAPEntry> findAll(
		int start, int end, OrderByComparator<SAPEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the sap entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of sap entries.
	 *
	 * @return the number of sap entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SAPEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SAPEntryPersistence, SAPEntryPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SAPEntryPersistence.class);

		ServiceTracker<SAPEntryPersistence, SAPEntryPersistence>
			serviceTracker =
				new ServiceTracker<SAPEntryPersistence, SAPEntryPersistence>(
					bundle.getBundleContext(), SAPEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}