/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the eager blob entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.EagerBlobEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntryPersistence
 * @generated
 */
public class EagerBlobEntryUtil {

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
	public static void clearCache(EagerBlobEntry eagerBlobEntry) {
		getPersistence().clearCache(eagerBlobEntry);
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
	public static Map<Serializable, EagerBlobEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<EagerBlobEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<EagerBlobEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<EagerBlobEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<EagerBlobEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static EagerBlobEntry update(EagerBlobEntry eagerBlobEntry) {
		return getPersistence().update(eagerBlobEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static EagerBlobEntry update(
		EagerBlobEntry eagerBlobEntry, ServiceContext serviceContext) {

		return getPersistence().update(eagerBlobEntry, serviceContext);
	}

	/**
	 * Returns all the eager blob entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching eager blob entries
	 */
	public static List<EagerBlobEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the eager blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @return the range of matching eager blob entries
	 */
	public static List<EagerBlobEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the eager blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching eager blob entries
	 */
	public static List<EagerBlobEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<EagerBlobEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the eager blob entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching eager blob entries
	 */
	public static List<EagerBlobEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<EagerBlobEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a matching eager blob entry could not be found
	 */
	public static EagerBlobEntry findByUuid_First(
			String uuid, OrderByComparator<EagerBlobEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public static EagerBlobEntry fetchByUuid_First(
		String uuid, OrderByComparator<EagerBlobEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a matching eager blob entry could not be found
	 */
	public static EagerBlobEntry findByUuid_Last(
			String uuid, OrderByComparator<EagerBlobEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public static EagerBlobEntry fetchByUuid_Last(
		String uuid, OrderByComparator<EagerBlobEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the eager blob entries before and after the current eager blob entry in the ordered set where uuid = &#63;.
	 *
	 * @param eagerBlobEntryId the primary key of the current eager blob entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a eager blob entry with the primary key could not be found
	 */
	public static EagerBlobEntry[] findByUuid_PrevAndNext(
			long eagerBlobEntryId, String uuid,
			OrderByComparator<EagerBlobEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			eagerBlobEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the eager blob entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of eager blob entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching eager blob entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the eager blob entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEagerBlobEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a matching eager blob entry could not be found
	 */
	public static EagerBlobEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the eager blob entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public static EagerBlobEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the eager blob entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	public static EagerBlobEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the eager blob entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the eager blob entry that was removed
	 */
	public static EagerBlobEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of eager blob entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching eager blob entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Caches the eager blob entry in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntry the eager blob entry
	 */
	public static void cacheResult(EagerBlobEntry eagerBlobEntry) {
		getPersistence().cacheResult(eagerBlobEntry);
	}

	/**
	 * Caches the eager blob entries in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntries the eager blob entries
	 */
	public static void cacheResult(List<EagerBlobEntry> eagerBlobEntries) {
		getPersistence().cacheResult(eagerBlobEntries);
	}

	/**
	 * Creates a new eager blob entry with the primary key. Does not add the eager blob entry to the database.
	 *
	 * @param eagerBlobEntryId the primary key for the new eager blob entry
	 * @return the new eager blob entry
	 */
	public static EagerBlobEntry create(long eagerBlobEntryId) {
		return getPersistence().create(eagerBlobEntryId);
	}

	/**
	 * Removes the eager blob entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry that was removed
	 * @throws NoSuchEagerBlobEntryException if a eager blob entry with the primary key could not be found
	 */
	public static EagerBlobEntry remove(long eagerBlobEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntryException {

		return getPersistence().remove(eagerBlobEntryId);
	}

	public static EagerBlobEntry updateImpl(EagerBlobEntry eagerBlobEntry) {
		return getPersistence().updateImpl(eagerBlobEntry);
	}

	/**
	 * Returns the eager blob entry with the primary key or throws a <code>NoSuchEagerBlobEntryException</code> if it could not be found.
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry
	 * @throws NoSuchEagerBlobEntryException if a eager blob entry with the primary key could not be found
	 */
	public static EagerBlobEntry findByPrimaryKey(long eagerBlobEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntryException {

		return getPersistence().findByPrimaryKey(eagerBlobEntryId);
	}

	/**
	 * Returns the eager blob entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry, or <code>null</code> if a eager blob entry with the primary key could not be found
	 */
	public static EagerBlobEntry fetchByPrimaryKey(long eagerBlobEntryId) {
		return getPersistence().fetchByPrimaryKey(eagerBlobEntryId);
	}

	/**
	 * Returns all the eager blob entries.
	 *
	 * @return the eager blob entries
	 */
	public static List<EagerBlobEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the eager blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @return the range of eager blob entries
	 */
	public static List<EagerBlobEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the eager blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of eager blob entries
	 */
	public static List<EagerBlobEntry> findAll(
		int start, int end,
		OrderByComparator<EagerBlobEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the eager blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of eager blob entries
	 */
	public static List<EagerBlobEntry> findAll(
		int start, int end, OrderByComparator<EagerBlobEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the eager blob entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of eager blob entries.
	 *
	 * @return the number of eager blob entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static EagerBlobEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(EagerBlobEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile EagerBlobEntryPersistence _persistence;

}