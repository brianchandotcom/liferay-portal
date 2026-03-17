/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the basic entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat700.service.persistence.impl.BasicEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntryPersistence
 * @generated
 */
public class BasicEntryUtil {

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
	public static void clearCache(BasicEntry basicEntry) {
		getPersistence().clearCache(basicEntry);
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
	public static Map<Serializable, BasicEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BasicEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BasicEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BasicEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BasicEntry update(BasicEntry basicEntry) {
		return getPersistence().update(basicEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BasicEntry update(
		BasicEntry basicEntry, ServiceContext serviceContext) {

		return getPersistence().update(basicEntry, serviceContext);
	}

	/**
	 * Returns all the basic entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching basic entries
	 */
	public static List<BasicEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of matching basic entries
	 */
	public static List<BasicEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching basic entries
	 */
	public static List<BasicEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching basic entries
	 */
	public static List<BasicEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	public static BasicEntry findByGroupId_First(
			long groupId, OrderByComparator<BasicEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchBasicEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public static BasicEntry fetchByGroupId_First(
		long groupId, OrderByComparator<BasicEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	public static BasicEntry findByGroupId_Last(
			long groupId, OrderByComparator<BasicEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchBasicEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public static BasicEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<BasicEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the basic entries before and after the current basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param basicEntryId the primary key of the current basic entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next basic entry
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	public static BasicEntry[] findByGroupId_PrevAndNext(
			long basicEntryId, long groupId,
			OrderByComparator<BasicEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchBasicEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			basicEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the basic entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of basic entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching basic entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchBasicEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	public static BasicEntry findByC_N(long companyId, String name)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchBasicEntryException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public static BasicEntry fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public static BasicEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the basic entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the basic entry that was removed
	 */
	public static BasicEntry removeByC_N(long companyId, String name)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchBasicEntryException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of basic entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching basic entries
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Caches the basic entry in the entity cache if it is enabled.
	 *
	 * @param basicEntry the basic entry
	 */
	public static void cacheResult(BasicEntry basicEntry) {
		getPersistence().cacheResult(basicEntry);
	}

	/**
	 * Caches the basic entries in the entity cache if it is enabled.
	 *
	 * @param basicEntries the basic entries
	 */
	public static void cacheResult(List<BasicEntry> basicEntries) {
		getPersistence().cacheResult(basicEntries);
	}

	/**
	 * Creates a new basic entry with the primary key. Does not add the basic entry to the database.
	 *
	 * @param basicEntryId the primary key for the new basic entry
	 * @return the new basic entry
	 */
	public static BasicEntry create(long basicEntryId) {
		return getPersistence().create(basicEntryId);
	}

	/**
	 * Removes the basic entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry that was removed
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	public static BasicEntry remove(long basicEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchBasicEntryException {

		return getPersistence().remove(basicEntryId);
	}

	public static BasicEntry updateImpl(BasicEntry basicEntry) {
		return getPersistence().updateImpl(basicEntry);
	}

	/**
	 * Returns the basic entry with the primary key or throws a <code>NoSuchBasicEntryException</code> if it could not be found.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	public static BasicEntry findByPrimaryKey(long basicEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat700.
			exception.NoSuchBasicEntryException {

		return getPersistence().findByPrimaryKey(basicEntryId);
	}

	/**
	 * Returns the basic entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry, or <code>null</code> if a basic entry with the primary key could not be found
	 */
	public static BasicEntry fetchByPrimaryKey(long basicEntryId) {
		return getPersistence().fetchByPrimaryKey(basicEntryId);
	}

	/**
	 * Returns all the basic entries.
	 *
	 * @return the basic entries
	 */
	public static List<BasicEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of basic entries
	 */
	public static List<BasicEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of basic entries
	 */
	public static List<BasicEntry> findAll(
		int start, int end, OrderByComparator<BasicEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of basic entries
	 */
	public static List<BasicEntry> findAll(
		int start, int end, OrderByComparator<BasicEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the basic entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of basic entries.
	 *
	 * @return the number of basic entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	 * Returns the primaryKeys of mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return long[] of the primaryKeys of mapping entries associated with the basic entry
	 */
	public static long[] getMappingEntryPrimaryKeys(long pk) {
		return getPersistence().getMappingEntryPrimaryKeys(pk);
	}

	/**
	 * Returns all the mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return the mapping entries associated with the basic entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			MappingEntry> getMappingEntries(long pk) {

		return getPersistence().getMappingEntries(pk);
	}

	/**
	 * Returns a range of all the mapping entries associated with the basic entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the basic entry
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of mapping entries associated with the basic entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			MappingEntry> getMappingEntries(long pk, int start, int end) {

		return getPersistence().getMappingEntries(pk, start, end);
	}

	/**
	 * Returns an ordered range of all the mapping entries associated with the basic entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the basic entry
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mapping entries associated with the basic entry
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			MappingEntry> getMappingEntries(
				long pk, int start, int end,
				OrderByComparator
					<com.liferay.portal.tools.service.builder.test.compat700.
						model.MappingEntry> orderByComparator) {

		return getPersistence().getMappingEntries(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return the number of mapping entries associated with the basic entry
	 */
	public static int getMappingEntriesSize(long pk) {
		return getPersistence().getMappingEntriesSize(pk);
	}

	/**
	 * Returns <code>true</code> if the mapping entry is associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 * @return <code>true</code> if the mapping entry is associated with the basic entry; <code>false</code> otherwise
	 */
	public static boolean containsMappingEntry(long pk, long mappingEntryPK) {
		return getPersistence().containsMappingEntry(pk, mappingEntryPK);
	}

	/**
	 * Returns <code>true</code> if the basic entry has any mapping entries associated with it.
	 *
	 * @param pk the primary key of the basic entry to check for associations with mapping entries
	 * @return <code>true</code> if the basic entry has any mapping entries associated with it; <code>false</code> otherwise
	 */
	public static boolean containsMappingEntries(long pk) {
		return getPersistence().containsMappingEntries(pk);
	}

	/**
	 * Adds an association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 */
	public static void addMappingEntry(long pk, long mappingEntryPK) {
		getPersistence().addMappingEntry(pk, mappingEntryPK);
	}

	/**
	 * Adds an association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntry the mapping entry
	 */
	public static void addMappingEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.compat700.model.
			MappingEntry mappingEntry) {

		getPersistence().addMappingEntry(pk, mappingEntry);
	}

	/**
	 * Adds an association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries
	 */
	public static void addMappingEntries(long pk, long[] mappingEntryPKs) {
		getPersistence().addMappingEntries(pk, mappingEntryPKs);
	}

	/**
	 * Adds an association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries
	 */
	public static void addMappingEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				MappingEntry> mappingEntries) {

		getPersistence().addMappingEntries(pk, mappingEntries);
	}

	/**
	 * Clears all associations between the basic entry and its mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry to clear the associated mapping entries from
	 */
	public static void clearMappingEntries(long pk) {
		getPersistence().clearMappingEntries(pk);
	}

	/**
	 * Removes the association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 */
	public static void removeMappingEntry(long pk, long mappingEntryPK) {
		getPersistence().removeMappingEntry(pk, mappingEntryPK);
	}

	/**
	 * Removes the association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntry the mapping entry
	 */
	public static void removeMappingEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.compat700.model.
			MappingEntry mappingEntry) {

		getPersistence().removeMappingEntry(pk, mappingEntry);
	}

	/**
	 * Removes the association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries
	 */
	public static void removeMappingEntries(long pk, long[] mappingEntryPKs) {
		getPersistence().removeMappingEntries(pk, mappingEntryPKs);
	}

	/**
	 * Removes the association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries
	 */
	public static void removeMappingEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				MappingEntry> mappingEntries) {

		getPersistence().removeMappingEntries(pk, mappingEntries);
	}

	/**
	 * Sets the mapping entries associated with the basic entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries to be associated with the basic entry
	 */
	public static void setMappingEntries(long pk, long[] mappingEntryPKs) {
		getPersistence().setMappingEntries(pk, mappingEntryPKs);
	}

	/**
	 * Sets the mapping entries associated with the basic entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries to be associated with the basic entry
	 */
	public static void setMappingEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				MappingEntry> mappingEntries) {

		getPersistence().setMappingEntries(pk, mappingEntries);
	}

	public static BasicEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(BasicEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile BasicEntryPersistence _persistence;

}
// SB-Hash:1872947797