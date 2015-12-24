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

import com.liferay.portlet.documentlibrary.model.DLFileVersion;

import java.util.List;

/**
 * The persistence utility for the document library file version service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class DLFileVersionUtil {
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
	public static void clearCache(DLFileVersion dlFileVersion) {
		getPersistence().clearCache(dlFileVersion);
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
	public static List<DLFileVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileVersion update(DLFileVersion dlFileVersion) {
		return getPersistence().update(dlFileVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DLFileVersion update(DLFileVersion dlFileVersion,
		ServiceContext serviceContext) {
		return getPersistence().update(dlFileVersion, serviceContext);
	}

	/**
	* Returns all the document library file versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the document library file versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByUuid_PrevAndNext(long fileVersionId,
		java.lang.String uuid,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(fileVersionId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the document library file versions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of document library file versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file versions
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the document library file version where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileVersionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the document library file version where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library file version that was removed
	*/
	public static DLFileVersion removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of document library file versions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library file versions
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByUuid_C_PrevAndNext(long fileVersionId,
		java.lang.String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(fileVersionId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the document library file versions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file versions
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the document library file versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the document library file versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByCompanyId_First(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByCompanyId_First(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByCompanyId_Last(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByCompanyId_Last(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByCompanyId_PrevAndNext(
		long fileVersionId, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(fileVersionId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the document library file versions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of document library file versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching document library file versions
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the document library file versions where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByFileEntryId(long fileEntryId) {
		return getPersistence().findByFileEntryId(fileEntryId);
	}

	/**
	* Returns a range of all the document library file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByFileEntryId(long fileEntryId,
		int start, int end) {
		return getPersistence().findByFileEntryId(fileEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByFileEntryId(long fileEntryId,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByFileEntryId(long fileEntryId,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByFileEntryId_PrevAndNext(
		long fileVersionId, long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByFileEntryId_PrevAndNext(fileVersionId, fileEntryId,
			orderByComparator);
	}

	/**
	* Removes all the document library file versions where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public static void removeByFileEntryId(long fileEntryId) {
		getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	* Returns the number of document library file versions where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file versions
	*/
	public static int countByFileEntryId(long fileEntryId) {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	* Returns all the document library file versions where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByMimeType(java.lang.String mimeType) {
		return getPersistence().findByMimeType(mimeType);
	}

	/**
	* Returns a range of all the document library file versions where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByMimeType(
		java.lang.String mimeType, int start, int end) {
		return getPersistence().findByMimeType(mimeType, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByMimeType(
		java.lang.String mimeType, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByMimeType(mimeType, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByMimeType(
		java.lang.String mimeType, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByMimeType(mimeType, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByMimeType_First(
		java.lang.String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByMimeType_First(mimeType, orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByMimeType_First(
		java.lang.String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByMimeType_First(mimeType, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByMimeType_Last(java.lang.String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByMimeType_Last(mimeType, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByMimeType_Last(
		java.lang.String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence().fetchByMimeType_Last(mimeType, orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where mimeType = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByMimeType_PrevAndNext(
		long fileVersionId, java.lang.String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByMimeType_PrevAndNext(fileVersionId, mimeType,
			orderByComparator);
	}

	/**
	* Removes all the document library file versions where mimeType = &#63; from the database.
	*
	* @param mimeType the mime type
	*/
	public static void removeByMimeType(java.lang.String mimeType) {
		getPersistence().removeByMimeType(mimeType);
	}

	/**
	* Returns the number of document library file versions where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @return the number of matching document library file versions
	*/
	public static int countByMimeType(java.lang.String mimeType) {
		return getPersistence().countByMimeType(mimeType);
	}

	/**
	* Returns all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByC_NotS(long companyId, int status) {
		return getPersistence().findByC_NotS(companyId, status);
	}

	/**
	* Returns a range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByC_NotS(long companyId, int status,
		int start, int end) {
		return getPersistence().findByC_NotS(companyId, status, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_NotS(companyId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByC_NotS_First(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByC_NotS_First(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByC_NotS_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByC_NotS_PrevAndNext(long fileVersionId,
		long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByC_NotS_PrevAndNext(fileVersionId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the document library file versions where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_NotS(long companyId, int status) {
		getPersistence().removeByC_NotS(companyId, status);
	}

	/**
	* Returns the number of document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching document library file versions
	*/
	public static int countByC_NotS(long companyId, int status) {
		return getPersistence().countByC_NotS(companyId, status);
	}

	/**
	* Returns the document library file version where fileEntryId = &#63; and version = &#63; or throws a {@link NoSuchFileVersionException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByF_V(long fileEntryId,
		java.lang.String version)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByF_V(fileEntryId, version);
	}

	/**
	* Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByF_V(long fileEntryId,
		java.lang.String version) {
		return getPersistence().fetchByF_V(fileEntryId, version);
	}

	/**
	* Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByF_V(long fileEntryId,
		java.lang.String version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByF_V(fileEntryId, version, retrieveFromCache);
	}

	/**
	* Removes the document library file version where fileEntryId = &#63; and version = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the document library file version that was removed
	*/
	public static DLFileVersion removeByF_V(long fileEntryId,
		java.lang.String version)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().removeByF_V(fileEntryId, version);
	}

	/**
	* Returns the number of document library file versions where fileEntryId = &#63; and version = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the number of matching document library file versions
	*/
	public static int countByF_V(long fileEntryId, java.lang.String version) {
		return getPersistence().countByF_V(fileEntryId, version);
	}

	/**
	* Returns all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByF_S(long fileEntryId, int status) {
		return getPersistence().findByF_S(fileEntryId, status);
	}

	/**
	* Returns a range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end) {
		return getPersistence().findByF_S(fileEntryId, status, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByF_S(fileEntryId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByF_S(fileEntryId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByF_S_First(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByF_S_First(fileEntryId, status, orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByF_S_First(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByF_S_First(fileEntryId, status, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByF_S_Last(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByF_S_Last(fileEntryId, status, orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByF_S_Last(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByF_S_Last(fileEntryId, status, orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByF_S_PrevAndNext(long fileVersionId,
		long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByF_S_PrevAndNext(fileVersionId, fileEntryId, status,
			orderByComparator);
	}

	/**
	* Removes all the document library file versions where fileEntryId = &#63; and status = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	*/
	public static void removeByF_S(long fileEntryId, int status) {
		getPersistence().removeByF_S(fileEntryId, status);
	}

	/**
	* Returns the number of document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @return the number of matching document library file versions
	*/
	public static int countByF_S(long fileEntryId, int status) {
		return getPersistence().countByF_S(fileEntryId, status);
	}

	/**
	* Returns all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status) {
		return getPersistence().findByG_F_S(groupId, folderId, status);
	}

	/**
	* Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end) {
		return getPersistence()
				   .findByG_F_S(groupId, folderId, status, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByG_F_S(groupId, folderId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_F_S(groupId, folderId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByG_F_S_First(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_S_First(groupId, folderId, status,
			orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByG_F_S_First(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_S_First(groupId, folderId, status,
			orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByG_F_S_Last(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_S_Last(groupId, folderId, status,
			orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByG_F_S_Last(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_S_Last(groupId, folderId, status,
			orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByG_F_S_PrevAndNext(long fileVersionId,
		long groupId, long folderId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_S_PrevAndNext(fileVersionId, groupId, folderId,
			status, orderByComparator);
	}

	/**
	* Removes all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	*/
	public static void removeByG_F_S(long groupId, long folderId, int status) {
		getPersistence().removeByG_F_S(groupId, folderId, status);
	}

	/**
	* Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching document library file versions
	*/
	public static int countByG_F_S(long groupId, long folderId, int status) {
		return getPersistence().countByG_F_S(groupId, folderId, status);
	}

	/**
	* Returns all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @return the matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version) {
		return getPersistence().findByG_F_T_V(groupId, folderId, title, version);
	}

	/**
	* Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		int start, int end) {
		return getPersistence()
				   .findByG_F_T_V(groupId, folderId, title, version, start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .findByG_F_T_V(groupId, folderId, title, version, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public static List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_F_T_V(groupId, folderId, title, version, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByG_F_T_V_First(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_T_V_First(groupId, folderId, title, version,
			orderByComparator);
	}

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByG_F_T_V_First(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_T_V_First(groupId, folderId, title, version,
			orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public static DLFileVersion findByG_F_T_V_Last(long groupId, long folderId,
		java.lang.String title, java.lang.String version,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_T_V_Last(groupId, folderId, title, version,
			orderByComparator);
	}

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public static DLFileVersion fetchByG_F_T_V_Last(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_T_V_Last(groupId, folderId, title, version,
			orderByComparator);
	}

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion[] findByG_F_T_V_PrevAndNext(
		long fileVersionId, long groupId, long folderId,
		java.lang.String title, java.lang.String version,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_T_V_PrevAndNext(fileVersionId, groupId, folderId,
			title, version, orderByComparator);
	}

	/**
	* Removes all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	*/
	public static void removeByG_F_T_V(long groupId, long folderId,
		java.lang.String title, java.lang.String version) {
		getPersistence().removeByG_F_T_V(groupId, folderId, title, version);
	}

	/**
	* Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @return the number of matching document library file versions
	*/
	public static int countByG_F_T_V(long groupId, long folderId,
		java.lang.String title, java.lang.String version) {
		return getPersistence().countByG_F_T_V(groupId, folderId, title, version);
	}

	/**
	* Caches the document library file version in the entity cache if it is enabled.
	*
	* @param dlFileVersion the document library file version
	*/
	public static void cacheResult(DLFileVersion dlFileVersion) {
		getPersistence().cacheResult(dlFileVersion);
	}

	/**
	* Caches the document library file versions in the entity cache if it is enabled.
	*
	* @param dlFileVersions the document library file versions
	*/
	public static void cacheResult(List<DLFileVersion> dlFileVersions) {
		getPersistence().cacheResult(dlFileVersions);
	}

	/**
	* Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	*
	* @param fileVersionId the primary key for the new document library file version
	* @return the new document library file version
	*/
	public static DLFileVersion create(long fileVersionId) {
		return getPersistence().create(fileVersionId);
	}

	/**
	* Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version that was removed
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion remove(long fileVersionId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().remove(fileVersionId);
	}

	public static DLFileVersion updateImpl(DLFileVersion dlFileVersion) {
		return getPersistence().updateImpl(dlFileVersion);
	}

	/**
	* Returns the document library file version with the primary key or throws a {@link NoSuchFileVersionException} if it could not be found.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion findByPrimaryKey(long fileVersionId)
		throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByPrimaryKey(fileVersionId);
	}

	/**
	* Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	*/
	public static DLFileVersion fetchByPrimaryKey(long fileVersionId) {
		return getPersistence().fetchByPrimaryKey(fileVersionId);
	}

	public static java.util.Map<java.io.Serializable, DLFileVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the document library file versions.
	*
	* @return the document library file versions
	*/
	public static List<DLFileVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the document library file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of document library file versions
	*/
	public static List<DLFileVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the document library file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file versions
	*/
	public static List<DLFileVersion> findAll(int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the document library file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library file versions
	*/
	public static List<DLFileVersion> findAll(int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the document library file versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of document library file versions.
	*
	* @return the number of document library file versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DLFileVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileVersionPersistence)PortalBeanLocatorUtil.locate(DLFileVersionPersistence.class.getName());

			ReferenceRegistry.registerReference(DLFileVersionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static DLFileVersionPersistence _persistence;
}