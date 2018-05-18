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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersion;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the lv entry version service. This utility wraps {@link com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersionPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class LVEntryVersionUtil {
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
	public static void clearCache(LVEntryVersion lvEntryVersion) {
		getPersistence().clearCache(lvEntryVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LVEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LVEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LVEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LVEntryVersion update(LVEntryVersion lvEntryVersion) {
		return getPersistence().update(lvEntryVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LVEntryVersion update(LVEntryVersion lvEntryVersion,
		ServiceContext serviceContext) {
		return getPersistence().update(lvEntryVersion, serviceContext);
	}

	/**
	* Returns all the lv entry versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching lv entry versions
	*/
	public static List<LVEntryVersion> findByEntryId(long entryId) {
		return getPersistence().findByEntryId(entryId);
	}

	/**
	* Returns a range of all the lv entry versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByEntryId(long entryId, int start,
		int end) {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	/**
	* Returns an ordered range of all the lv entry versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByEntryId(long entryId, int start,
		int end, OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entry versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByEntryId(long entryId, int start,
		int end, OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first lv entry version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public static LVEntryVersion findByEntryId_First(long entryId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the first lv entry version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByEntryId_First(long entryId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence().fetchByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the last lv entry version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public static LVEntryVersion findByEntryId_Last(long entryId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the last lv entry version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByEntryId_Last(long entryId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence().fetchByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the lv entry versions before and after the current lv entry version in the ordered set where entryId = &#63;.
	*
	* @param lvEntryVersionId the primary key of the current lv entry version
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public static LVEntryVersion[] findByEntryId_PrevAndNext(
		long lvEntryVersionId, long entryId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(lvEntryVersionId, entryId,
			orderByComparator);
	}

	/**
	* Removes all the lv entry versions where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public static void removeByEntryId(long entryId) {
		getPersistence().removeByEntryId(entryId);
	}

	/**
	* Returns the number of lv entry versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching lv entry versions
	*/
	public static int countByEntryId(long entryId) {
		return getPersistence().countByEntryId(entryId);
	}

	/**
	* Returns the lv entry version where entryId = &#63; and version = &#63; or throws a {@link NoSuchLVEntryVersionException} if it could not be found.
	*
	* @param entryId the entry ID
	* @param version the version
	* @return the matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public static LVEntryVersion findByEntryId_Version(long entryId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().findByEntryId_Version(entryId, version);
	}

	/**
	* Returns the lv entry version where entryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param entryId the entry ID
	* @param version the version
	* @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByEntryId_Version(long entryId,
		int version) {
		return getPersistence().fetchByEntryId_Version(entryId, version);
	}

	/**
	* Returns the lv entry version where entryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param entryId the entry ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByEntryId_Version(long entryId,
		int version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByEntryId_Version(entryId, version, retrieveFromCache);
	}

	/**
	* Removes the lv entry version where entryId = &#63; and version = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param version the version
	* @return the lv entry version that was removed
	*/
	public static LVEntryVersion removeByEntryId_Version(long entryId,
		int version)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().removeByEntryId_Version(entryId, version);
	}

	/**
	* Returns the number of lv entry versions where entryId = &#63; and version = &#63;.
	*
	* @param entryId the entry ID
	* @param version the version
	* @return the number of matching lv entry versions
	*/
	public static int countByEntryId_Version(long entryId, int version) {
		return getPersistence().countByEntryId_Version(entryId, version);
	}

	/**
	* Returns all the lv entry versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the lv entry versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public static LVEntryVersion findByGroupId_First(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByGroupId_First(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public static LVEntryVersion findByGroupId_Last(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByGroupId_Last(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63;.
	*
	* @param lvEntryVersionId the primary key of the current lv entry version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public static LVEntryVersion[] findByGroupId_PrevAndNext(
		long lvEntryVersionId, long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(lvEntryVersionId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the lv entry versions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of lv entry versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching lv entry versions
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version) {
		return getPersistence().findByGroupId_Version(groupId, version);
	}

	/**
	* Returns a range of all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end) {
		return getPersistence()
				   .findByGroupId_Version(groupId, version, start, end);
	}

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence()
				   .findByGroupId_Version(groupId, version, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param version the version
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entry versions
	*/
	public static List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId_Version(groupId, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public static LVEntryVersion findByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence()
				   .findByGroupId_Version_First(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence()
				   .fetchByGroupId_Version_First(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version
	* @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	*/
	public static LVEntryVersion findByGroupId_Version_Last(long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence()
				   .findByGroupId_Version_Last(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	*/
	public static LVEntryVersion fetchByGroupId_Version_Last(long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence()
				   .fetchByGroupId_Version_Last(groupId, version,
			orderByComparator);
	}

	/**
	* Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	*
	* @param lvEntryVersionId the primary key of the current lv entry version
	* @param groupId the group ID
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public static LVEntryVersion[] findByGroupId_Version_PrevAndNext(
		long lvEntryVersionId, long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence()
				   .findByGroupId_Version_PrevAndNext(lvEntryVersionId,
			groupId, version, orderByComparator);
	}

	/**
	* Removes all the lv entry versions where groupId = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param version the version
	*/
	public static void removeByGroupId_Version(long groupId, int version) {
		getPersistence().removeByGroupId_Version(groupId, version);
	}

	/**
	* Returns the number of lv entry versions where groupId = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param version the version
	* @return the number of matching lv entry versions
	*/
	public static int countByGroupId_Version(long groupId, int version) {
		return getPersistence().countByGroupId_Version(groupId, version);
	}

	/**
	* Caches the lv entry version in the entity cache if it is enabled.
	*
	* @param lvEntryVersion the lv entry version
	*/
	public static void cacheResult(LVEntryVersion lvEntryVersion) {
		getPersistence().cacheResult(lvEntryVersion);
	}

	/**
	* Caches the lv entry versions in the entity cache if it is enabled.
	*
	* @param lvEntryVersions the lv entry versions
	*/
	public static void cacheResult(List<LVEntryVersion> lvEntryVersions) {
		getPersistence().cacheResult(lvEntryVersions);
	}

	/**
	* Creates a new lv entry version with the primary key. Does not add the lv entry version to the database.
	*
	* @param lvEntryVersionId the primary key for the new lv entry version
	* @return the new lv entry version
	*/
	public static LVEntryVersion create(long lvEntryVersionId) {
		return getPersistence().create(lvEntryVersionId);
	}

	/**
	* Removes the lv entry version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntryVersionId the primary key of the lv entry version
	* @return the lv entry version that was removed
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public static LVEntryVersion remove(long lvEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().remove(lvEntryVersionId);
	}

	public static LVEntryVersion updateImpl(LVEntryVersion lvEntryVersion) {
		return getPersistence().updateImpl(lvEntryVersion);
	}

	/**
	* Returns the lv entry version with the primary key or throws a {@link NoSuchLVEntryVersionException} if it could not be found.
	*
	* @param lvEntryVersionId the primary key of the lv entry version
	* @return the lv entry version
	* @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	*/
	public static LVEntryVersion findByPrimaryKey(long lvEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException {
		return getPersistence().findByPrimaryKey(lvEntryVersionId);
	}

	/**
	* Returns the lv entry version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param lvEntryVersionId the primary key of the lv entry version
	* @return the lv entry version, or <code>null</code> if a lv entry version with the primary key could not be found
	*/
	public static LVEntryVersion fetchByPrimaryKey(long lvEntryVersionId) {
		return getPersistence().fetchByPrimaryKey(lvEntryVersionId);
	}

	public static java.util.Map<java.io.Serializable, LVEntryVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the lv entry versions.
	*
	* @return the lv entry versions
	*/
	public static List<LVEntryVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the lv entry versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @return the range of lv entry versions
	*/
	public static List<LVEntryVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the lv entry versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of lv entry versions
	*/
	public static List<LVEntryVersion> findAll(int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entry versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry versions
	* @param end the upper bound of the range of lv entry versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of lv entry versions
	*/
	public static List<LVEntryVersion> findAll(int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the lv entry versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of lv entry versions.
	*
	* @return the number of lv entry versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LVEntryVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LVEntryVersionPersistence, LVEntryVersionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LVEntryVersionPersistence.class);

		ServiceTracker<LVEntryVersionPersistence, LVEntryVersionPersistence> serviceTracker =
			new ServiceTracker<LVEntryVersionPersistence, LVEntryVersionPersistence>(bundle.getBundleContext(),
				LVEntryVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}