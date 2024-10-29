/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the index entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.IndexEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntryPersistence
 * @generated
 */
public class IndexEntryUtil {

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
	public static void clearCache(IndexEntry indexEntry) {
		getPersistence().clearCache(indexEntry);
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
	public static Map<Serializable, IndexEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<IndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<IndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<IndexEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static IndexEntry update(IndexEntry indexEntry) {
		return getPersistence().update(indexEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static IndexEntry update(
		IndexEntry indexEntry, ServiceContext serviceContext) {

		return getPersistence().update(indexEntry, serviceContext);
	}

	/**
	 * Returns all the index entries where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByOwnerId(long ownerId) {
		return getPersistence().findByOwnerId(ownerId);
	}

	/**
	 * Returns a range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end) {

		return getPersistence().findByOwnerId(ownerId, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByOwnerId(
			ownerId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOwnerId(
			ownerId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByOwnerId_First(
			long ownerId, OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByOwnerId_First(ownerId, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByOwnerId_First(
		long ownerId, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByOwnerId_First(
			ownerId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByOwnerId_Last(
			long ownerId, OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByOwnerId_Last(ownerId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByOwnerId_Last(
		long ownerId, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByOwnerId_Last(ownerId, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByOwnerId_PrevAndNext(
			long indexEntryId, long ownerId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByOwnerId_PrevAndNext(
			indexEntryId, ownerId, orderByComparator);
	}

	/**
	 * Removes all the index entries where ownerId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 */
	public static void removeByOwnerId(long ownerId) {
		getPersistence().removeByOwnerId(ownerId);
	}

	/**
	 * Returns the number of index entries where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the number of matching index entries
	 */
	public static int countByOwnerId(long ownerId) {
		return getPersistence().countByOwnerId(ownerId);
	}

	/**
	 * Returns all the index entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns a range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByPlid(long plid, int start, int end) {
		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByPlid(
		long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByPlid(
		long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPlid(
			plid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByPlid_First(
			long plid, OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByPlid_First(
		long plid, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByPlid_Last(
			long plid, OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByPlid_Last(
		long plid, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where plid = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByPlid_PrevAndNext(
			long indexEntryId, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPlid_PrevAndNext(
			indexEntryId, plid, orderByComparator);
	}

	/**
	 * Removes all the index entries where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	 * Returns the number of index entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching index entries
	 */
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	 * Returns all the index entries where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByPortletId(String portletId) {
		return getPersistence().findByPortletId(portletId);
	}

	/**
	 * Returns a range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByPortletId(
		String portletId, int start, int end) {

		return getPersistence().findByPortletId(portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByPortletId(
			portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPortletId(
			portletId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByPortletId_First(
			String portletId, OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPortletId_First(
			portletId, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByPortletId_First(
		String portletId, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByPortletId_First(
			portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByPortletId_Last(
			String portletId, OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPortletId_Last(
			portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByPortletId_Last(
		String portletId, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByPortletId_Last(
			portletId, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByPortletId_PrevAndNext(
			long indexEntryId, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPortletId_PrevAndNext(
			indexEntryId, portletId, orderByComparator);
	}

	/**
	 * Removes all the index entries where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 */
	public static void removeByPortletId(String portletId) {
		getPersistence().removeByPortletId(portletId);
	}

	/**
	 * Returns the number of index entries where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public static int countByPortletId(String portletId) {
		return getPersistence().countByPortletId(portletId);
	}

	/**
	 * Returns all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByO_P(int ownerType, String portletId) {
		return getPersistence().findByO_P(ownerType, portletId);
	}

	/**
	 * Returns a range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end) {

		return getPersistence().findByO_P(ownerType, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByO_P(
			ownerType, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_P(
			ownerType, portletId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_P_First(
			int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_P_First(
			ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_P_First(
		int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_P_First(
			ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_P_Last(
			int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_P_Last(
			ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_P_Last(
		int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_P_Last(
			ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByO_P_PrevAndNext(
			long indexEntryId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_P_PrevAndNext(
			indexEntryId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Removes all the index entries where ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public static void removeByO_P(int ownerType, String portletId) {
		getPersistence().removeByO_P(ownerType, portletId);
	}

	/**
	 * Returns the number of index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public static int countByO_P(int ownerType, String portletId) {
		return getPersistence().countByO_P(ownerType, portletId);
	}

	/**
	 * Returns all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByP_P(long plid, String portletId) {
		return getPersistence().findByP_P(plid, portletId);
	}

	/**
	 * Returns a range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end) {

		return getPersistence().findByP_P(plid, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByP_P(
			plid, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByP_P(
			plid, portletId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByP_P_First(
			long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByP_P_First(
			plid, portletId, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByP_P_First(
		long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByP_P_First(
			plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByP_P_Last(
			long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByP_P_Last(
			plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByP_P_Last(
		long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByP_P_Last(
			plid, portletId, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByP_P_PrevAndNext(
			long indexEntryId, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByP_P_PrevAndNext(
			indexEntryId, plid, portletId, orderByComparator);
	}

	/**
	 * Removes all the index entries where plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public static void removeByP_P(long plid, String portletId) {
		getPersistence().removeByP_P(plid, portletId);
	}

	/**
	 * Returns the number of index entries where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public static int countByP_P(long plid, String portletId) {
		return getPersistence().countByP_P(plid, portletId);
	}

	/**
	 * Returns all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid) {

		return getPersistence().findByO_O_P(ownerId, ownerType, plid);
	}

	/**
	 * Returns a range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end) {

		return getPersistence().findByO_O_P(
			ownerId, ownerType, plid, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByO_O_P(
			ownerId, ownerType, plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_O_P(
			ownerId, ownerType, plid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_O_P_First(
			long ownerId, int ownerType, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_O_P_First(
			ownerId, ownerType, plid, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_O_P_First(
		long ownerId, int ownerType, long plid,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_O_P_First(
			ownerId, ownerType, plid, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_O_P_Last(
			long ownerId, int ownerType, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_O_P_Last(
			ownerId, ownerType, plid, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_O_P_Last(
			ownerId, ownerType, plid, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByO_O_P_PrevAndNext(
			long indexEntryId, long ownerId, int ownerType, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_O_P_PrevAndNext(
			indexEntryId, ownerId, ownerType, plid, orderByComparator);
	}

	/**
	 * Removes all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 */
	public static void removeByO_O_P(long ownerId, int ownerType, long plid) {
		getPersistence().removeByO_O_P(ownerId, ownerType, plid);
	}

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the number of matching index entries
	 */
	public static int countByO_O_P(long ownerId, int ownerType, long plid) {
		return getPersistence().countByO_O_P(ownerId, ownerType, plid);
	}

	/**
	 * Returns all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId) {

		return getPersistence().findByO_O_PI(ownerId, ownerType, portletId);
	}

	/**
	 * Returns a range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end) {

		return getPersistence().findByO_O_PI(
			ownerId, ownerType, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByO_O_PI(
			ownerId, ownerType, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_O_PI(
			ownerId, ownerType, portletId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_O_PI_First(
			long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_O_PI_First(
			ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_O_PI_First(
		long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_O_PI_First(
			ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_O_PI_Last(
			long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_O_PI_Last(
			ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_O_PI_Last(
		long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_O_PI_Last(
			ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByO_O_PI_PrevAndNext(
			long indexEntryId, long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_O_PI_PrevAndNext(
			indexEntryId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Removes all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public static void removeByO_O_PI(
		long ownerId, int ownerType, String portletId) {

		getPersistence().removeByO_O_PI(ownerId, ownerType, portletId);
	}

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public static int countByO_O_PI(
		long ownerId, int ownerType, String portletId) {

		return getPersistence().countByO_O_PI(ownerId, ownerType, portletId);
	}

	/**
	 * Returns all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId) {

		return getPersistence().findByO_P_P(ownerType, plid, portletId);
	}

	/**
	 * Returns a range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end) {

		return getPersistence().findByO_P_P(
			ownerType, plid, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByO_P_P(
			ownerType, plid, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_P_P(
			ownerType, plid, portletId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_P_P_First(
			int ownerType, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_P_P_First(
			ownerType, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_P_P_First(
		int ownerType, long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_P_P_First(
			ownerType, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_P_P_Last(
			int ownerType, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_P_P_Last(
			ownerType, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_P_P_Last(
		int ownerType, long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByO_P_P_Last(
			ownerType, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByO_P_P_PrevAndNext(
			long indexEntryId, int ownerType, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_P_P_PrevAndNext(
			indexEntryId, ownerType, plid, portletId, orderByComparator);
	}

	/**
	 * Removes all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public static void removeByO_P_P(
		int ownerType, long plid, String portletId) {

		getPersistence().removeByO_P_P(ownerType, plid, portletId);
	}

	/**
	 * Returns the number of index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public static int countByO_P_P(int ownerType, long plid, String portletId) {
		return getPersistence().countByO_P_P(ownerType, plid, portletId);
	}

	/**
	 * Returns all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	public static List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Returns a range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	public static List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	public static List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end, OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByC_O_O_LikeP_First(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByC_O_O_LikeP_First(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the first index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByC_O_O_LikeP_First(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByC_O_O_LikeP_First(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByC_O_O_LikeP_Last(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByC_O_O_LikeP_Last(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByC_O_O_LikeP_Last(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().fetchByC_O_O_LikeP_Last(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry[] findByC_O_O_LikeP_PrevAndNext(
			long indexEntryId, long companyId, long ownerId, int ownerType,
			String portletId, OrderByComparator<IndexEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByC_O_O_LikeP_PrevAndNext(
			indexEntryId, companyId, ownerId, ownerType, portletId,
			orderByComparator);
	}

	/**
	 * Removes all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public static void removeByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		getPersistence().removeByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Returns the number of index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public static int countByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		return getPersistence().countByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByO_O_P_P(
			ownerId, ownerType, plid, portletId);
	}

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId) {

		return getPersistence().fetchByO_O_P_P(
			ownerId, ownerType, plid, portletId);
	}

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId,
		boolean useFinderCache) {

		return getPersistence().fetchByO_O_P_P(
			ownerId, ownerType, plid, portletId, useFinderCache);
	}

	/**
	 * Removes the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the index entry that was removed
	 */
	public static IndexEntry removeByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().removeByO_O_P_P(
			ownerId, ownerType, plid, portletId);
	}

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	public static int countByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId) {

		return getPersistence().countByO_O_P_P(
			ownerId, ownerType, plid, portletId);
	}

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	public static IndexEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	public static IndexEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the index entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the index entry that was removed
	 */
	public static IndexEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of index entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching index entries
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the index entry in the entity cache if it is enabled.
	 *
	 * @param indexEntry the index entry
	 */
	public static void cacheResult(IndexEntry indexEntry) {
		getPersistence().cacheResult(indexEntry);
	}

	/**
	 * Caches the index entries in the entity cache if it is enabled.
	 *
	 * @param indexEntries the index entries
	 */
	public static void cacheResult(List<IndexEntry> indexEntries) {
		getPersistence().cacheResult(indexEntries);
	}

	/**
	 * Creates a new index entry with the primary key. Does not add the index entry to the database.
	 *
	 * @param indexEntryId the primary key for the new index entry
	 * @return the new index entry
	 */
	public static IndexEntry create(long indexEntryId) {
		return getPersistence().create(indexEntryId);
	}

	/**
	 * Removes the index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry that was removed
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry remove(long indexEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().remove(indexEntryId);
	}

	public static IndexEntry updateImpl(IndexEntry indexEntry) {
		return getPersistence().updateImpl(indexEntry);
	}

	/**
	 * Returns the index entry with the primary key or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	public static IndexEntry findByPrimaryKey(long indexEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchIndexEntryException {

		return getPersistence().findByPrimaryKey(indexEntryId);
	}

	/**
	 * Returns the index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry, or <code>null</code> if a index entry with the primary key could not be found
	 */
	public static IndexEntry fetchByPrimaryKey(long indexEntryId) {
		return getPersistence().fetchByPrimaryKey(indexEntryId);
	}

	/**
	 * Returns all the index entries.
	 *
	 * @return the index entries
	 */
	public static List<IndexEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of index entries
	 */
	public static List<IndexEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of index entries
	 */
	public static List<IndexEntry> findAll(
		int start, int end, OrderByComparator<IndexEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of index entries
	 */
	public static List<IndexEntry> findAll(
		int start, int end, OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the index entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of index entries.
	 *
	 * @return the number of index entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static IndexEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(IndexEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile IndexEntryPersistence _persistence;

}