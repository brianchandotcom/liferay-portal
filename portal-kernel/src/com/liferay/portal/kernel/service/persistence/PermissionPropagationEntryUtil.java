/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.PermissionPropagationEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the permission propagation entry service. This utility wraps <code>com.liferay.portal.service.persistence.impl.PermissionPropagationEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationEntryPersistence
 * @generated
 */
public class PermissionPropagationEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		PermissionPropagationEntry permissionPropagationEntry) {

		getPersistence().clearCache(permissionPropagationEntry);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, PermissionPropagationEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PermissionPropagationEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PermissionPropagationEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PermissionPropagationEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PermissionPropagationEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PermissionPropagationEntry update(
		PermissionPropagationEntry permissionPropagationEntry) {

		return getPersistence().update(permissionPropagationEntry);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PermissionPropagationEntry update(
		PermissionPropagationEntry permissionPropagationEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			permissionPropagationEntry, serviceContext);
	}

	/**
	 * Returns the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPermissionPropagationEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching permission propagation entry
	 * @throws NoSuchPermissionPropagationEntryException if a matching permission propagation entry could not be found
	 */
	public static PermissionPropagationEntry findByG_C_C_C(
			long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.
			NoSuchPermissionPropagationEntryException {

		return getPersistence().findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	/**
	 * Returns the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching permission propagation entry, or <code>null</code> if a matching permission propagation entry could not be found
	 */
	public static PermissionPropagationEntry fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK) {

		return getPersistence().fetchByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	/**
	 * Returns the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching permission propagation entry, or <code>null</code> if a matching permission propagation entry could not be found
	 */
	public static PermissionPropagationEntry fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		boolean useFinderCache) {

		return getPersistence().fetchByG_C_C_C(
			groupId, companyId, classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the permission propagation entry that was removed
	 */
	public static PermissionPropagationEntry removeByG_C_C_C(
			long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.
			NoSuchPermissionPropagationEntryException {

		return getPersistence().removeByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	/**
	 * Returns the number of permission propagation entries where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching permission propagation entries
	 */
	public static int countByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK) {

		return getPersistence().countByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	/**
	 * Caches the permission propagation entry in the entity cache if it is enabled.
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 */
	public static void cacheResult(
		PermissionPropagationEntry permissionPropagationEntry) {

		getPersistence().cacheResult(permissionPropagationEntry);
	}

	/**
	 * Caches the permission propagation entries in the entity cache if it is enabled.
	 *
	 * @param permissionPropagationEntries the permission propagation entries
	 */
	public static void cacheResult(
		List<PermissionPropagationEntry> permissionPropagationEntries) {

		getPersistence().cacheResult(permissionPropagationEntries);
	}

	/**
	 * Creates a new permission propagation entry with the primary key. Does not add the permission propagation entry to the database.
	 *
	 * @param permissionPropagationEntryId the primary key for the new permission propagation entry
	 * @return the new permission propagation entry
	 */
	public static PermissionPropagationEntry create(
		long permissionPropagationEntryId) {

		return getPersistence().create(permissionPropagationEntryId);
	}

	/**
	 * Removes the permission propagation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry that was removed
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	public static PermissionPropagationEntry remove(
			long permissionPropagationEntryId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPermissionPropagationEntryException {

		return getPersistence().remove(permissionPropagationEntryId);
	}

	public static PermissionPropagationEntry updateImpl(
		PermissionPropagationEntry permissionPropagationEntry) {

		return getPersistence().updateImpl(permissionPropagationEntry);
	}

	/**
	 * Returns the permission propagation entry with the primary key or throws a <code>NoSuchPermissionPropagationEntryException</code> if it could not be found.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	public static PermissionPropagationEntry findByPrimaryKey(
			long permissionPropagationEntryId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPermissionPropagationEntryException {

		return getPersistence().findByPrimaryKey(permissionPropagationEntryId);
	}

	/**
	 * Returns the permission propagation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry, or <code>null</code> if a permission propagation entry with the primary key could not be found
	 */
	public static PermissionPropagationEntry fetchByPrimaryKey(
		long permissionPropagationEntryId) {

		return getPersistence().fetchByPrimaryKey(permissionPropagationEntryId);
	}

	/**
	 * Returns all the permission propagation entries.
	 *
	 * @return the permission propagation entries
	 */
	public static List<PermissionPropagationEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @return the range of permission propagation entries
	 */
	public static List<PermissionPropagationEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permission propagation entries
	 */
	public static List<PermissionPropagationEntry> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagationEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of permission propagation entries
	 */
	public static List<PermissionPropagationEntry> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagationEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the permission propagation entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of permission propagation entries.
	 *
	 * @return the number of permission propagation entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PermissionPropagationEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		PermissionPropagationEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile PermissionPropagationEntryPersistence _persistence;

}