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

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d m structure layout service. This utility wraps {@link com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureLayoutPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureLayoutPersistenceImpl
 * @generated
 */
@ProviderType
public class DDMStructureLayoutUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(DDMStructureLayout ddmStructureLayout) {
		getPersistence().clearCache(ddmStructureLayout);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMStructureLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMStructureLayout update(
		DDMStructureLayout ddmStructureLayout) {
		return getPersistence().update(ddmStructureLayout);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMStructureLayout update(
		DDMStructureLayout ddmStructureLayout, ServiceContext serviceContext) {
		return getPersistence().update(ddmStructureLayout, serviceContext);
	}

	/**
	* Returns all the d d m structure layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the d d m structure layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @return the range of matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout findByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the d d m structure layouts before and after the current d d m structure layout in the ordered set where uuid = &#63;.
	*
	* @param structureLayoutId the primary key of the current d d m structure layout
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public static DDMStructureLayout[] findByUuid_PrevAndNext(
		long structureLayoutId, java.lang.String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence()
				   .findByUuid_PrevAndNext(structureLayoutId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the d d m structure layouts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of d d m structure layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m structure layouts
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the d d m structure layout where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m structure layout that was removed
	*/
	public static DDMStructureLayout removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of d d m structure layouts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m structure layouts
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @return the range of matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure layouts
	*/
	public static List<DDMStructureLayout> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the d d m structure layouts before and after the current d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param structureLayoutId the primary key of the current d d m structure layout
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public static DDMStructureLayout[] findByUuid_C_PrevAndNext(
		long structureLayoutId, java.lang.String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(structureLayoutId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the d d m structure layouts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of d d m structure layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m structure layouts
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the d d m structure layout where structureVersionId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException} if it could not be found.
	*
	* @param structureVersionId the structure version ID
	* @return the matching d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout findByStructureVersionId(
		long structureVersionId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().findByStructureVersionId(structureVersionId);
	}

	/**
	* Returns the d d m structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param structureVersionId the structure version ID
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId) {
		return getPersistence().fetchByStructureVersionId(structureVersionId);
	}

	/**
	* Returns the d d m structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param structureVersionId the structure version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	*/
	public static DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByStructureVersionId(structureVersionId,
			retrieveFromCache);
	}

	/**
	* Removes the d d m structure layout where structureVersionId = &#63; from the database.
	*
	* @param structureVersionId the structure version ID
	* @return the d d m structure layout that was removed
	*/
	public static DDMStructureLayout removeByStructureVersionId(
		long structureVersionId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().removeByStructureVersionId(structureVersionId);
	}

	/**
	* Returns the number of d d m structure layouts where structureVersionId = &#63;.
	*
	* @param structureVersionId the structure version ID
	* @return the number of matching d d m structure layouts
	*/
	public static int countByStructureVersionId(long structureVersionId) {
		return getPersistence().countByStructureVersionId(structureVersionId);
	}

	/**
	* Caches the d d m structure layout in the entity cache if it is enabled.
	*
	* @param ddmStructureLayout the d d m structure layout
	*/
	public static void cacheResult(DDMStructureLayout ddmStructureLayout) {
		getPersistence().cacheResult(ddmStructureLayout);
	}

	/**
	* Caches the d d m structure layouts in the entity cache if it is enabled.
	*
	* @param ddmStructureLayouts the d d m structure layouts
	*/
	public static void cacheResult(List<DDMStructureLayout> ddmStructureLayouts) {
		getPersistence().cacheResult(ddmStructureLayouts);
	}

	/**
	* Creates a new d d m structure layout with the primary key. Does not add the d d m structure layout to the database.
	*
	* @param structureLayoutId the primary key for the new d d m structure layout
	* @return the new d d m structure layout
	*/
	public static DDMStructureLayout create(long structureLayoutId) {
		return getPersistence().create(structureLayoutId);
	}

	/**
	* Removes the d d m structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLayoutId the primary key of the d d m structure layout
	* @return the d d m structure layout that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public static DDMStructureLayout remove(long structureLayoutId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().remove(structureLayoutId);
	}

	public static DDMStructureLayout updateImpl(
		DDMStructureLayout ddmStructureLayout) {
		return getPersistence().updateImpl(ddmStructureLayout);
	}

	/**
	* Returns the d d m structure layout with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException} if it could not be found.
	*
	* @param structureLayoutId the primary key of the d d m structure layout
	* @return the d d m structure layout
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	*/
	public static DDMStructureLayout findByPrimaryKey(long structureLayoutId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException {
		return getPersistence().findByPrimaryKey(structureLayoutId);
	}

	/**
	* Returns the d d m structure layout with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureLayoutId the primary key of the d d m structure layout
	* @return the d d m structure layout, or <code>null</code> if a d d m structure layout with the primary key could not be found
	*/
	public static DDMStructureLayout fetchByPrimaryKey(long structureLayoutId) {
		return getPersistence().fetchByPrimaryKey(structureLayoutId);
	}

	public static java.util.Map<java.io.Serializable, DDMStructureLayout> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d m structure layouts.
	*
	* @return the d d m structure layouts
	*/
	public static List<DDMStructureLayout> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @return the range of d d m structure layouts
	*/
	public static List<DDMStructureLayout> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure layouts
	*/
	public static List<DDMStructureLayout> findAll(int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure layouts
	* @param end the upper bound of the range of d d m structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m structure layouts
	*/
	public static List<DDMStructureLayout> findAll(int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m structure layouts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m structure layouts.
	*
	* @return the number of d d m structure layouts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DDMStructureLayoutPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMStructureLayoutPersistence, DDMStructureLayoutPersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDMStructureLayoutPersistence.class);
}