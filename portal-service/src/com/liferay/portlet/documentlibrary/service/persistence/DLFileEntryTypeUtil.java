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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFileEntryType;

import java.util.List;

/**
 * The persistence utility for the document library file entry type service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileEntryTypePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypePersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileEntryTypePersistenceImpl
 * @generated
 */
@ProviderType
public class DLFileEntryTypeUtil {
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
	public static void clearCache(DLFileEntryType dlFileEntryType) {
		getPersistence().clearCache(dlFileEntryType);
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
	public static List<DLFileEntryType> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileEntryType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileEntryType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileEntryType update(DLFileEntryType dlFileEntryType) {
		return getPersistence().update(dlFileEntryType);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLFileEntryType update(DLFileEntryType dlFileEntryType,
		ServiceContext serviceContext) {
		return getPersistence().update(dlFileEntryType, serviceContext);
	}

	/**
	* Returns all the document library file entry types where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the document library file entry types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file entry types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file entry type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first document library file entry type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file entry type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file entry type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set where uuid = &#63;.
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	*/
	public static DLFileEntryType[] findByUuid_PrevAndNext(
		long fileEntryTypeId, java.lang.String uuid,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence()
				   .findByUuid_PrevAndNext(fileEntryTypeId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the document library file entry types where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of document library file entry types where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file entry types
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the document library file entry type where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileEntryTypeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the document library file entry type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the document library file entry type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the document library file entry type where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library file entry type that was removed
	*/
	public static DLFileEntryType removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of document library file entry types where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library file entry types
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the document library file entry types where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the document library file entry types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file entry types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	*/
	public static DLFileEntryType[] findByUuid_C_PrevAndNext(
		long fileEntryTypeId, java.lang.String uuid, long companyId,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(fileEntryTypeId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the document library file entry types where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of document library file entry types where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file entry types
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the document library file entry types where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the document library file entry types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file entry types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DLFileEntryType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file entry type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByGroupId_First(long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first document library file entry type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByGroupId_First(long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last document library file entry type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByGroupId_Last(long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last document library file entry type in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByGroupId_Last(long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set where groupId = &#63;.
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	*/
	public static DLFileEntryType[] findByGroupId_PrevAndNext(
		long fileEntryTypeId, long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(fileEntryTypeId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library file entry types that the user has permission to view
	*/
	public static List<DLFileEntryType> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types that the user has permission to view
	*/
	public static List<DLFileEntryType> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types that the user has permission to view
	*/
	public static List<DLFileEntryType> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set of document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	*/
	public static DLFileEntryType[] filterFindByGroupId_PrevAndNext(
		long fileEntryTypeId, long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(fileEntryTypeId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching document library file entry types that the user has permission to view
	*/
	public static List<DLFileEntryType> filterFindByGroupId(long[] groupIds) {
		return getPersistence().filterFindByGroupId(groupIds);
	}

	/**
	* Returns a range of all the document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types that the user has permission to view
	*/
	public static List<DLFileEntryType> filterFindByGroupId(long[] groupIds,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types that the user has permission to view
	*/
	public static List<DLFileEntryType> filterFindByGroupId(long[] groupIds,
		int start, int end, OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns all the document library file entry types where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	* Returns a range of all the document library file entry types where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long[] groupIds,
		int start, int end) {
		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long[] groupIds,
		int start, int end, OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file entry types where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file entry types
	*/
	public static List<DLFileEntryType> findByGroupId(long[] groupIds,
		int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the document library file entry types where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of document library file entry types where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library file entry types
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of document library file entry types where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching document library file entry types
	*/
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
	}

	/**
	* Returns the number of document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library file entry types that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching document library file entry types that the user has permission to view
	*/
	public static int filterCountByGroupId(long[] groupIds) {
		return getPersistence().filterCountByGroupId(groupIds);
	}

	/**
	* Returns the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; or throws a {@link NoSuchFileEntryTypeException} if it could not be found.
	*
	* @param groupId the group ID
	* @param fileEntryTypeKey the file entry type key
	* @return the matching document library file entry type
	* @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType findByG_F(long groupId,
		java.lang.String fileEntryTypeKey)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().findByG_F(groupId, fileEntryTypeKey);
	}

	/**
	* Returns the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param fileEntryTypeKey the file entry type key
	* @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByG_F(long groupId,
		java.lang.String fileEntryTypeKey) {
		return getPersistence().fetchByG_F(groupId, fileEntryTypeKey);
	}

	/**
	* Returns the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param fileEntryTypeKey the file entry type key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	*/
	public static DLFileEntryType fetchByG_F(long groupId,
		java.lang.String fileEntryTypeKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_F(groupId, fileEntryTypeKey, retrieveFromCache);
	}

	/**
	* Removes the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fileEntryTypeKey the file entry type key
	* @return the document library file entry type that was removed
	*/
	public static DLFileEntryType removeByG_F(long groupId,
		java.lang.String fileEntryTypeKey)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().removeByG_F(groupId, fileEntryTypeKey);
	}

	/**
	* Returns the number of document library file entry types where groupId = &#63; and fileEntryTypeKey = &#63;.
	*
	* @param groupId the group ID
	* @param fileEntryTypeKey the file entry type key
	* @return the number of matching document library file entry types
	*/
	public static int countByG_F(long groupId, java.lang.String fileEntryTypeKey) {
		return getPersistence().countByG_F(groupId, fileEntryTypeKey);
	}

	/**
	* Caches the document library file entry type in the entity cache if it is enabled.
	*
	* @param dlFileEntryType the document library file entry type
	*/
	public static void cacheResult(DLFileEntryType dlFileEntryType) {
		getPersistence().cacheResult(dlFileEntryType);
	}

	/**
	* Caches the document library file entry types in the entity cache if it is enabled.
	*
	* @param dlFileEntryTypes the document library file entry types
	*/
	public static void cacheResult(List<DLFileEntryType> dlFileEntryTypes) {
		getPersistence().cacheResult(dlFileEntryTypes);
	}

	/**
	* Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	*
	* @param fileEntryTypeId the primary key for the new document library file entry type
	* @return the new document library file entry type
	*/
	public static DLFileEntryType create(long fileEntryTypeId) {
		return getPersistence().create(fileEntryTypeId);
	}

	/**
	* Removes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type that was removed
	* @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	*/
	public static DLFileEntryType remove(long fileEntryTypeId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().remove(fileEntryTypeId);
	}

	public static DLFileEntryType updateImpl(DLFileEntryType dlFileEntryType) {
		return getPersistence().updateImpl(dlFileEntryType);
	}

	/**
	* Returns the document library file entry type with the primary key or throws a {@link NoSuchFileEntryTypeException} if it could not be found.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type
	* @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	*/
	public static DLFileEntryType findByPrimaryKey(long fileEntryTypeId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException {
		return getPersistence().findByPrimaryKey(fileEntryTypeId);
	}

	/**
	* Returns the document library file entry type with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type, or <code>null</code> if a document library file entry type with the primary key could not be found
	*/
	public static DLFileEntryType fetchByPrimaryKey(long fileEntryTypeId) {
		return getPersistence().fetchByPrimaryKey(fileEntryTypeId);
	}

	public static java.util.Map<java.io.Serializable, DLFileEntryType> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the document library file entry types.
	*
	* @return the document library file entry types
	*/
	public static List<DLFileEntryType> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the document library file entry types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of document library file entry types
	*/
	public static List<DLFileEntryType> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the document library file entry types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry types
	*/
	public static List<DLFileEntryType> findAll(int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file entry types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library file entry types
	*/
	public static List<DLFileEntryType> findAll(int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the document library file entry types from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of document library file entry types.
	*
	* @return the number of document library file entry types
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of document library folders associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @return long[] of the primaryKeys of document library folders associated with the document library file entry type
	*/
	public static long[] getDLFolderPrimaryKeys(long pk) {
		return getPersistence().getDLFolderPrimaryKeys(pk);
	}

	/**
	* Returns all the document library folders associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @return the document library folders associated with the document library file entry type
	*/
	public static List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk) {
		return getPersistence().getDLFolders(pk);
	}

	/**
	* Returns a range of all the document library folders associated with the document library file entry type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the document library file entry type
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of document library folders associated with the document library file entry type
	*/
	public static List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk, int start, int end) {
		return getPersistence().getDLFolders(pk, start, end);
	}

	/**
	* Returns an ordered range of all the document library folders associated with the document library file entry type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the document library file entry type
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library folders associated with the document library file entry type
	*/
	public static List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> orderByComparator) {
		return getPersistence().getDLFolders(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of document library folders associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @return the number of document library folders associated with the document library file entry type
	*/
	public static int getDLFoldersSize(long pk) {
		return getPersistence().getDLFoldersSize(pk);
	}

	/**
	* Returns <code>true</code> if the document library folder is associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPK the primary key of the document library folder
	* @return <code>true</code> if the document library folder is associated with the document library file entry type; <code>false</code> otherwise
	*/
	public static boolean containsDLFolder(long pk, long dlFolderPK) {
		return getPersistence().containsDLFolder(pk, dlFolderPK);
	}

	/**
	* Returns <code>true</code> if the document library file entry type has any document library folders associated with it.
	*
	* @param pk the primary key of the document library file entry type to check for associations with document library folders
	* @return <code>true</code> if the document library file entry type has any document library folders associated with it; <code>false</code> otherwise
	*/
	public static boolean containsDLFolders(long pk) {
		return getPersistence().containsDLFolders(pk);
	}

	/**
	* Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPK the primary key of the document library folder
	*/
	public static void addDLFolder(long pk, long dlFolderPK) {
		getPersistence().addDLFolder(pk, dlFolderPK);
	}

	/**
	* Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolder the document library folder
	*/
	public static void addDLFolder(long pk,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder) {
		getPersistence().addDLFolder(pk, dlFolder);
	}

	/**
	* Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPKs the primary keys of the document library folders
	*/
	public static void addDLFolders(long pk, long[] dlFolderPKs) {
		getPersistence().addDLFolders(pk, dlFolderPKs);
	}

	/**
	* Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolders the document library folders
	*/
	public static void addDLFolders(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders) {
		getPersistence().addDLFolders(pk, dlFolders);
	}

	/**
	* Clears all associations between the document library file entry type and its document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type to clear the associated document library folders from
	*/
	public static void clearDLFolders(long pk) {
		getPersistence().clearDLFolders(pk);
	}

	/**
	* Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPK the primary key of the document library folder
	*/
	public static void removeDLFolder(long pk, long dlFolderPK) {
		getPersistence().removeDLFolder(pk, dlFolderPK);
	}

	/**
	* Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolder the document library folder
	*/
	public static void removeDLFolder(long pk,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder) {
		getPersistence().removeDLFolder(pk, dlFolder);
	}

	/**
	* Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPKs the primary keys of the document library folders
	*/
	public static void removeDLFolders(long pk, long[] dlFolderPKs) {
		getPersistence().removeDLFolders(pk, dlFolderPKs);
	}

	/**
	* Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolders the document library folders
	*/
	public static void removeDLFolders(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders) {
		getPersistence().removeDLFolders(pk, dlFolders);
	}

	/**
	* Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPKs the primary keys of the document library folders to be associated with the document library file entry type
	*/
	public static void setDLFolders(long pk, long[] dlFolderPKs) {
		getPersistence().setDLFolders(pk, dlFolderPKs);
	}

	/**
	* Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolders the document library folders to be associated with the document library file entry type
	*/
	public static void setDLFolders(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders) {
		getPersistence().setDLFolders(pk, dlFolders);
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DLFileEntryTypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileEntryTypePersistence)PortalBeanLocatorUtil.locate(DLFileEntryTypePersistence.class.getName());

			ReferenceRegistry.registerReference(DLFileEntryTypeUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static DLFileEntryTypePersistence _persistence;
}