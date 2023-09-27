/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.sequence.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.sequence.model.SequenceEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the sequence entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.sequence.service.persistence.impl.SequenceEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntryPersistence
 * @generated
 */
public class SequenceEntryUtil {

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
	public static void clearCache(SequenceEntry sequenceEntry) {
		getPersistence().clearCache(sequenceEntry);
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
	public static Map<Serializable, SequenceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SequenceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SequenceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SequenceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SequenceEntry update(SequenceEntry sequenceEntry) {
		return getPersistence().update(sequenceEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SequenceEntry update(
		SequenceEntry sequenceEntry, ServiceContext serviceContext) {

		return getPersistence().update(sequenceEntry, serviceContext);
	}

	/**
	 * Returns all the sequence entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public static SequenceEntry findByUuid_First(
			String uuid, OrderByComparator<SequenceEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public static SequenceEntry fetchByUuid_First(
		String uuid, OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public static SequenceEntry findByUuid_Last(
			String uuid, OrderByComparator<SequenceEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public static SequenceEntry fetchByUuid_Last(
		String uuid, OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the sequence entries before and after the current sequence entry in the ordered set where uuid = &#63;.
	 *
	 * @param sequenceEntryId the primary key of the current sequence entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public static SequenceEntry[] findByUuid_PrevAndNext(
			long sequenceEntryId, String uuid,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			sequenceEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the sequence entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of sequence entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sequence entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sequence entries
	 */
	public static List<SequenceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public static SequenceEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public static SequenceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry
	 * @throws NoSuchSequenceEntryException if a matching sequence entry could not be found
	 */
	public static SequenceEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public static SequenceEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the sequence entries before and after the current sequence entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sequenceEntryId the primary key of the current sequence entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public static SequenceEntry[] findByUuid_C_PrevAndNext(
			long sequenceEntryId, String uuid, long companyId,
			OrderByComparator<SequenceEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			sequenceEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the sequence entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of sequence entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sequence entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the sequence entry in the entity cache if it is enabled.
	 *
	 * @param sequenceEntry the sequence entry
	 */
	public static void cacheResult(SequenceEntry sequenceEntry) {
		getPersistence().cacheResult(sequenceEntry);
	}

	/**
	 * Caches the sequence entries in the entity cache if it is enabled.
	 *
	 * @param sequenceEntries the sequence entries
	 */
	public static void cacheResult(List<SequenceEntry> sequenceEntries) {
		getPersistence().cacheResult(sequenceEntries);
	}

	/**
	 * Creates a new sequence entry with the primary key. Does not add the sequence entry to the database.
	 *
	 * @param sequenceEntryId the primary key for the new sequence entry
	 * @return the new sequence entry
	 */
	public static SequenceEntry create(long sequenceEntryId) {
		return getPersistence().create(sequenceEntryId);
	}

	/**
	 * Removes the sequence entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry that was removed
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public static SequenceEntry remove(long sequenceEntryId)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().remove(sequenceEntryId);
	}

	public static SequenceEntry updateImpl(SequenceEntry sequenceEntry) {
		return getPersistence().updateImpl(sequenceEntry);
	}

	/**
	 * Returns the sequence entry with the primary key or throws a <code>NoSuchSequenceEntryException</code> if it could not be found.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry
	 * @throws NoSuchSequenceEntryException if a sequence entry with the primary key could not be found
	 */
	public static SequenceEntry findByPrimaryKey(long sequenceEntryId)
		throws com.liferay.portal.tools.service.builder.test.sequence.exception.
			NoSuchSequenceEntryException {

		return getPersistence().findByPrimaryKey(sequenceEntryId);
	}

	/**
	 * Returns the sequence entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry, or <code>null</code> if a sequence entry with the primary key could not be found
	 */
	public static SequenceEntry fetchByPrimaryKey(long sequenceEntryId) {
		return getPersistence().fetchByPrimaryKey(sequenceEntryId);
	}

	/**
	 * Returns all the sequence entries.
	 *
	 * @return the sequence entries
	 */
	public static List<SequenceEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of sequence entries
	 */
	public static List<SequenceEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sequence entries
	 */
	public static List<SequenceEntry> findAll(
		int start, int end,
		OrderByComparator<SequenceEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sequence entries
	 */
	public static List<SequenceEntry> findAll(
		int start, int end, OrderByComparator<SequenceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the sequence entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of sequence entries.
	 *
	 * @return the number of sequence entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SequenceEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(SequenceEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile SequenceEntryPersistence _persistence;

}