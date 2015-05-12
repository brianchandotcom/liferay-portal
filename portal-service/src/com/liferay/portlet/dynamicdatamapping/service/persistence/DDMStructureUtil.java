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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.List;

/**
 * The persistence utility for the d d m structure service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.persistence.impl.DDMStructurePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructurePersistence
 * @see com.liferay.portlet.dynamicdatamapping.service.persistence.impl.DDMStructurePersistenceImpl
 * @generated
 */
@ProviderType
public class DDMStructureUtil {
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
	public static void clearCache(DDMStructure ddmStructure) {
		getPersistence().clearCache(ddmStructure);
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
	public static List<DDMStructure> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructure> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructure> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMStructure update(DDMStructure ddmStructure) {
		return getPersistence().update(ddmStructure);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMStructure update(DDMStructure ddmStructure,
		ServiceContext serviceContext) {
		return getPersistence().update(ddmStructure, serviceContext);
	}

	/**
	* Returns all the d d m structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the d d m structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByUuid_PrevAndNext(long structureId,
		java.lang.String uuid, OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByUuid_PrevAndNext(structureId, uuid, orderByComparator);
	}

	/**
	* Removes all the d d m structures where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of d d m structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m structures
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchStructureException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the d d m structure where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m structure that was removed
	*/
	public static DDMStructure removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of d d m structures where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m structures
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByUuid_C_PrevAndNext(long structureId,
		java.lang.String uuid, long companyId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(structureId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the d d m structures where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of d d m structures where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m structures
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the d d m structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the d d m structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByGroupId_First(long groupId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByGroupId_First(long groupId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByGroupId_Last(long groupId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByGroupId_Last(long groupId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByGroupId_PrevAndNext(long structureId,
		long groupId, OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(structureId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] filterFindByGroupId_PrevAndNext(
		long structureId, long groupId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(structureId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByGroupId(long[] groupIds) {
		return getPersistence().filterFindByGroupId(groupIds);
	}

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByGroupId(long[] groupIds,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByGroupId(long[] groupIds,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	* Returns a range of all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByGroupId(long[] groupIds, int start,
		int end) {
		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Removes all the d d m structures where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of d d m structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m structures
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of d d m structures where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m structures
	*/
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
	}

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public static int filterCountByGroupId(long[] groupIds) {
		return getPersistence().filterCountByGroupId(groupIds);
	}

	/**
	* Returns all the d d m structures where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByParentStructureId(
		long parentStructureId) {
		return getPersistence().findByParentStructureId(parentStructureId);
	}

	/**
	* Returns a range of all the d d m structures where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByParentStructureId(
		long parentStructureId, int start, int end) {
		return getPersistence()
				   .findByParentStructureId(parentStructureId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByParentStructureId(
		long parentStructureId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByParentStructureId(parentStructureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByParentStructureId_First(
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByParentStructureId_First(parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByParentStructureId_First(
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByParentStructureId_First(parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByParentStructureId_Last(
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByParentStructureId_Last(parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByParentStructureId_Last(
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByParentStructureId_Last(parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where parentStructureId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByParentStructureId_PrevAndNext(
		long structureId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByParentStructureId_PrevAndNext(structureId,
			parentStructureId, orderByComparator);
	}

	/**
	* Removes all the d d m structures where parentStructureId = &#63; from the database.
	*
	* @param parentStructureId the parent structure ID
	*/
	public static void removeByParentStructureId(long parentStructureId) {
		getPersistence().removeByParentStructureId(parentStructureId);
	}

	/**
	* Returns the number of d d m structures where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @return the number of matching d d m structures
	*/
	public static int countByParentStructureId(long parentStructureId) {
		return getPersistence().countByParentStructureId(parentStructureId);
	}

	/**
	* Returns all the d d m structures where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByClassNameId(long classNameId) {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	* Returns a range of all the d d m structures where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByClassNameId(long classNameId,
		int start, int end) {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByClassNameId(long classNameId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByClassNameId_First(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByClassNameId_First(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByClassNameId_Last(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByClassNameId_Last(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByClassNameId_PrevAndNext(
		long structureId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(structureId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the d d m structures where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public static void removeByClassNameId(long classNameId) {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	* Returns the number of d d m structures where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public static int countByClassNameId(long classNameId) {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	* Returns all the d d m structures where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByStructureKey(
		java.lang.String structureKey) {
		return getPersistence().findByStructureKey(structureKey);
	}

	/**
	* Returns a range of all the d d m structures where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureKey the structure key
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByStructureKey(
		java.lang.String structureKey, int start, int end) {
		return getPersistence().findByStructureKey(structureKey, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where structureKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureKey the structure key
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByStructureKey(
		java.lang.String structureKey, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByStructureKey(structureKey, start, end,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByStructureKey_First(
		java.lang.String structureKey,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByStructureKey_First(structureKey, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByStructureKey_First(
		java.lang.String structureKey,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByStructureKey_First(structureKey, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByStructureKey_Last(
		java.lang.String structureKey,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByStructureKey_Last(structureKey, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByStructureKey_Last(
		java.lang.String structureKey,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByStructureKey_Last(structureKey, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where structureKey = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param structureKey the structure key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByStructureKey_PrevAndNext(
		long structureId, java.lang.String structureKey,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByStructureKey_PrevAndNext(structureId, structureKey,
			orderByComparator);
	}

	/**
	* Removes all the d d m structures where structureKey = &#63; from the database.
	*
	* @param structureKey the structure key
	*/
	public static void removeByStructureKey(java.lang.String structureKey) {
		getPersistence().removeByStructureKey(structureKey);
	}

	/**
	* Returns the number of d d m structures where structureKey = &#63;.
	*
	* @param structureKey the structure key
	* @return the number of matching d d m structures
	*/
	public static int countByStructureKey(java.lang.String structureKey) {
		return getPersistence().countByStructureKey(structureKey);
	}

	/**
	* Returns all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByG_P(long groupId,
		long parentStructureId) {
		return getPersistence().findByG_P(groupId, parentStructureId);
	}

	/**
	* Returns a range of all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_P(long groupId,
		long parentStructureId, int start, int end) {
		return getPersistence().findByG_P(groupId, parentStructureId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_P(long groupId,
		long parentStructureId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByG_P(groupId, parentStructureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByG_P_First(long groupId,
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_P_First(groupId, parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_P_First(long groupId,
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_First(groupId, parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByG_P_Last(long groupId,
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentStructureId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_P_Last(long groupId,
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_Last(groupId, parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByG_P_PrevAndNext(long structureId,
		long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_P_PrevAndNext(structureId, groupId,
			parentStructureId, orderByComparator);
	}

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId) {
		return getPersistence().filterFindByG_P(groupId, parentStructureId);
	}

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId, int start, int end) {
		return getPersistence()
				   .filterFindByG_P(groupId, parentStructureId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P(groupId, parentStructureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] filterFindByG_P_PrevAndNext(long structureId,
		long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(structureId, groupId,
			parentStructureId, orderByComparator);
	}

	/**
	* Removes all the d d m structures where groupId = &#63; and parentStructureId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	*/
	public static void removeByG_P(long groupId, long parentStructureId) {
		getPersistence().removeByG_P(groupId, parentStructureId);
	}

	/**
	* Returns the number of d d m structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the number of matching d d m structures
	*/
	public static int countByG_P(long groupId, long parentStructureId) {
		return getPersistence().countByG_P(groupId, parentStructureId);
	}

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public static int filterCountByG_P(long groupId, long parentStructureId) {
		return getPersistence().filterCountByG_P(groupId, parentStructureId);
	}

	/**
	* Returns all the d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByG_C(long groupId, long classNameId) {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByG_C_PrevAndNext(long structureId,
		long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_C_PrevAndNext(structureId, groupId, classNameId,
			orderByComparator);
	}

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_C(long groupId,
		long classNameId) {
		return getPersistence().filterFindByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_C(long groupId,
		long classNameId, int start, int end) {
		return getPersistence().filterFindByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] filterFindByG_C_PrevAndNext(long structureId,
		long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .filterFindByG_C_PrevAndNext(structureId, groupId,
			classNameId, orderByComparator);
	}

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId) {
		return getPersistence().filterFindByG_C(groupIds, classNameId);
	}

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId, int start, int end) {
		return getPersistence()
				   .filterFindByG_C(groupIds, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C(groupIds, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByG_C(long[] groupIds, long classNameId) {
		return getPersistence().findByG_C(groupIds, classNameId);
	}

	/**
	* Returns a range of all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_C(long[] groupIds,
		long classNameId, int start, int end) {
		return getPersistence().findByG_C(groupIds, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_C(long[] groupIds,
		long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupIds, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Removes all the d d m structures where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public static void removeByG_C(long groupId, long classNameId) {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of d d m structures where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public static int countByG_C(long groupId, long classNameId) {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of d d m structures where groupId = any &#63; and classNameId = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public static int countByG_C(long[] groupIds, long classNameId) {
		return getPersistence().countByG_C(groupIds, classNameId);
	}

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public static int filterCountByG_C(long groupId, long classNameId) {
		return getPersistence().filterCountByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public static int filterCountByG_C(long[] groupIds, long classNameId) {
		return getPersistence().filterCountByG_C(groupIds, classNameId);
	}

	/**
	* Returns all the d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByC_C(long companyId, long classNameId) {
		return getPersistence().findByC_C(companyId, classNameId);
	}

	/**
	* Returns a range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByC_C(long companyId,
		long classNameId, int start, int end) {
		return getPersistence().findByC_C(companyId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByC_C(long companyId,
		long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByC_C_First(long companyId,
		long classNameId, OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByC_C_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByC_C_First(long companyId,
		long classNameId, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByC_C_Last(long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByC_C_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByC_C_Last(long companyId,
		long classNameId, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByC_C_PrevAndNext(long structureId,
		long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByC_C_PrevAndNext(structureId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the d d m structures where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public static void removeByC_C(long companyId, long classNameId) {
		getPersistence().removeByC_C(companyId, classNameId);
	}

	/**
	* Returns the number of d d m structures where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching d d m structures
	*/
	public static int countByC_C(long companyId, long classNameId) {
		return getPersistence().countByC_C(companyId, classNameId);
	}

	/**
	* Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or throws a {@link NoSuchStructureException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().findByG_C_S(groupId, classNameId, structureKey);
	}

	/**
	* Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey) {
		return getPersistence().fetchByG_C_S(groupId, classNameId, structureKey);
	}

	/**
	* Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_S(groupId, classNameId, structureKey,
			retrieveFromCache);
	}

	/**
	* Removes the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the d d m structure that was removed
	*/
	public static DDMStructure removeByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().removeByG_C_S(groupId, classNameId, structureKey);
	}

	/**
	* Returns the number of d d m structures where groupId = &#63; and classNameId = &#63; and structureKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param structureKey the structure key
	* @return the number of matching d d m structures
	*/
	public static int countByG_C_S(long groupId, long classNameId,
		java.lang.String structureKey) {
		return getPersistence().countByG_C_S(groupId, classNameId, structureKey);
	}

	/**
	* Returns all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching d d m structures
	*/
	public static List<DDMStructure> findByG_N_D(long groupId,
		java.lang.String name, java.lang.String description) {
		return getPersistence().findByG_N_D(groupId, name, description);
	}

	/**
	* Returns a range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start, int end) {
		return getPersistence()
				   .findByG_N_D(groupId, name, description, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures
	*/
	public static List<DDMStructure> findByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .findByG_N_D(groupId, name, description, start, end,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByG_N_D_First(long groupId,
		java.lang.String name, java.lang.String description,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_N_D_First(groupId, name, description,
			orderByComparator);
	}

	/**
	* Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_N_D_First(long groupId,
		java.lang.String name, java.lang.String description,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_D_First(groupId, name, description,
			orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure
	* @throws NoSuchStructureException if a matching d d m structure could not be found
	*/
	public static DDMStructure findByG_N_D_Last(long groupId,
		java.lang.String name, java.lang.String description,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_N_D_Last(groupId, name, description,
			orderByComparator);
	}

	/**
	* Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static DDMStructure fetchByG_N_D_Last(long groupId,
		java.lang.String name, java.lang.String description,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_D_Last(groupId, name, description,
			orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] findByG_N_D_PrevAndNext(long structureId,
		long groupId, java.lang.String name, java.lang.String description,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .findByG_N_D_PrevAndNext(structureId, groupId, name,
			description, orderByComparator);
	}

	/**
	* Returns all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_N_D(long groupId,
		java.lang.String name, java.lang.String description) {
		return getPersistence().filterFindByG_N_D(groupId, name, description);
	}

	/**
	* Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start, int end) {
		return getPersistence()
				   .filterFindByG_N_D(groupId, name, description, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structures that the user has permission to view
	*/
	public static List<DDMStructure> filterFindByG_N_D(long groupId,
		java.lang.String name, java.lang.String description, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence()
				   .filterFindByG_N_D(groupId, name, description, start, end,
			orderByComparator);
	}

	/**
	* Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param structureId the primary key of the current d d m structure
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure[] filterFindByG_N_D_PrevAndNext(
		long structureId, long groupId, java.lang.String name,
		java.lang.String description,
		OrderByComparator<DDMStructure> orderByComparator)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence()
				   .filterFindByG_N_D_PrevAndNext(structureId, groupId, name,
			description, orderByComparator);
	}

	/**
	* Removes all the d d m structures where groupId = &#63; and name = &#63; and description = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	*/
	public static void removeByG_N_D(long groupId, java.lang.String name,
		java.lang.String description) {
		getPersistence().removeByG_N_D(groupId, name, description);
	}

	/**
	* Returns the number of d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching d d m structures
	*/
	public static int countByG_N_D(long groupId, java.lang.String name,
		java.lang.String description) {
		return getPersistence().countByG_N_D(groupId, name, description);
	}

	/**
	* Returns the number of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching d d m structures that the user has permission to view
	*/
	public static int filterCountByG_N_D(long groupId, java.lang.String name,
		java.lang.String description) {
		return getPersistence().filterCountByG_N_D(groupId, name, description);
	}

	/**
	* Caches the d d m structure in the entity cache if it is enabled.
	*
	* @param ddmStructure the d d m structure
	*/
	public static void cacheResult(DDMStructure ddmStructure) {
		getPersistence().cacheResult(ddmStructure);
	}

	/**
	* Caches the d d m structures in the entity cache if it is enabled.
	*
	* @param ddmStructures the d d m structures
	*/
	public static void cacheResult(List<DDMStructure> ddmStructures) {
		getPersistence().cacheResult(ddmStructures);
	}

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public static DDMStructure create(long structureId) {
		return getPersistence().create(structureId);
	}

	/**
	* Removes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure that was removed
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure remove(long structureId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().remove(structureId);
	}

	public static DDMStructure updateImpl(DDMStructure ddmStructure) {
		return getPersistence().updateImpl(ddmStructure);
	}

	/**
	* Returns the d d m structure with the primary key or throws a {@link NoSuchStructureException} if it could not be found.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure
	* @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure findByPrimaryKey(long structureId)
		throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureException {
		return getPersistence().findByPrimaryKey(structureId);
	}

	/**
	* Returns the d d m structure with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure, or <code>null</code> if a d d m structure with the primary key could not be found
	*/
	public static DDMStructure fetchByPrimaryKey(long structureId) {
		return getPersistence().fetchByPrimaryKey(structureId);
	}

	public static java.util.Map<java.io.Serializable, DDMStructure> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d m structures.
	*
	* @return the d d m structures
	*/
	public static List<DDMStructure> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of d d m structures
	*/
	public static List<DDMStructure> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structures
	*/
	public static List<DDMStructure> findAll(int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d d m structures from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m structures.
	*
	* @return the number of d d m structures
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of document library file entry types associated with the d d m structure.
	*
	* @param pk the primary key of the d d m structure
	* @return long[] of the primaryKeys of document library file entry types associated with the d d m structure
	*/
	public static long[] getDLFileEntryTypePrimaryKeys(long pk) {
		return getPersistence().getDLFileEntryTypePrimaryKeys(pk);
	}

	/**
	* Returns all the document library file entry types associated with the d d m structure.
	*
	* @param pk the primary key of the d d m structure
	* @return the document library file entry types associated with the d d m structure
	*/
	public static List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk) {
		return getPersistence().getDLFileEntryTypes(pk);
	}

	/**
	* Returns a range of all the document library file entry types associated with the d d m structure.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the d d m structure
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of document library file entry types associated with the d d m structure
	*/
	public static List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end) {
		return getPersistence().getDLFileEntryTypes(pk, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types associated with the d d m structure.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the d d m structure
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry types associated with the d d m structure
	*/
	public static List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .getDLFileEntryTypes(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of document library file entry types associated with the d d m structure.
	*
	* @param pk the primary key of the d d m structure
	* @return the number of document library file entry types associated with the d d m structure
	*/
	public static int getDLFileEntryTypesSize(long pk) {
		return getPersistence().getDLFileEntryTypesSize(pk);
	}

	/**
	* Returns <code>true</code> if the document library file entry type is associated with the d d m structure.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	* @return <code>true</code> if the document library file entry type is associated with the d d m structure; <code>false</code> otherwise
	*/
	public static boolean containsDLFileEntryType(long pk,
		long dlFileEntryTypePK) {
		return getPersistence().containsDLFileEntryType(pk, dlFileEntryTypePK);
	}

	/**
	* Returns <code>true</code> if the d d m structure has any document library file entry types associated with it.
	*
	* @param pk the primary key of the d d m structure to check for associations with document library file entry types
	* @return <code>true</code> if the d d m structure has any document library file entry types associated with it; <code>false</code> otherwise
	*/
	public static boolean containsDLFileEntryTypes(long pk) {
		return getPersistence().containsDLFileEntryTypes(pk);
	}

	/**
	* Adds an association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	*/
	public static void addDLFileEntryType(long pk, long dlFileEntryTypePK) {
		getPersistence().addDLFileEntryType(pk, dlFileEntryTypePK);
	}

	/**
	* Adds an association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryType the document library file entry type
	*/
	public static void addDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		getPersistence().addDLFileEntryType(pk, dlFileEntryType);
	}

	/**
	* Adds an association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types
	*/
	public static void addDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		getPersistence().addDLFileEntryTypes(pk, dlFileEntryTypePKs);
	}

	/**
	* Adds an association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypes the document library file entry types
	*/
	public static void addDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes) {
		getPersistence().addDLFileEntryTypes(pk, dlFileEntryTypes);
	}

	/**
	* Clears all associations between the d d m structure and its document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure to clear the associated document library file entry types from
	*/
	public static void clearDLFileEntryTypes(long pk) {
		getPersistence().clearDLFileEntryTypes(pk);
	}

	/**
	* Removes the association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypePK the primary key of the document library file entry type
	*/
	public static void removeDLFileEntryType(long pk, long dlFileEntryTypePK) {
		getPersistence().removeDLFileEntryType(pk, dlFileEntryTypePK);
	}

	/**
	* Removes the association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryType the document library file entry type
	*/
	public static void removeDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		getPersistence().removeDLFileEntryType(pk, dlFileEntryType);
	}

	/**
	* Removes the association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types
	*/
	public static void removeDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		getPersistence().removeDLFileEntryTypes(pk, dlFileEntryTypePKs);
	}

	/**
	* Removes the association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypes the document library file entry types
	*/
	public static void removeDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes) {
		getPersistence().removeDLFileEntryTypes(pk, dlFileEntryTypes);
	}

	/**
	* Sets the document library file entry types associated with the d d m structure, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypePKs the primary keys of the document library file entry types to be associated with the d d m structure
	*/
	public static void setDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		getPersistence().setDLFileEntryTypes(pk, dlFileEntryTypePKs);
	}

	/**
	* Sets the document library file entry types associated with the d d m structure, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d d m structure
	* @param dlFileEntryTypes the document library file entry types to be associated with the d d m structure
	*/
	public static void setDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes) {
		getPersistence().setDLFileEntryTypes(pk, dlFileEntryTypes);
	}

	public static DDMStructurePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DDMStructurePersistence)PortalBeanLocatorUtil.locate(DDMStructurePersistence.class.getName());

			ReferenceRegistry.registerReference(DDMStructureUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(DDMStructurePersistence persistence) {
	}

	private static DDMStructurePersistence _persistence;
}