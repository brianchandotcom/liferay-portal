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

import com.liferay.dynamic.data.mapping.model.DDMStorageLink;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d m storage link service. This utility wraps {@link com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStorageLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStorageLinkPersistenceImpl
 * @generated
 */
@ProviderType
public class DDMStorageLinkUtil {
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
	public static void clearCache(DDMStorageLink ddmStorageLink) {
		getPersistence().clearCache(ddmStorageLink);
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
	public static List<DDMStorageLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStorageLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStorageLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMStorageLink update(DDMStorageLink ddmStorageLink) {
		return getPersistence().update(ddmStorageLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMStorageLink update(DDMStorageLink ddmStorageLink,
		ServiceContext serviceContext) {
		return getPersistence().update(ddmStorageLink, serviceContext);
	}

	/**
	* Returns all the d d m storage links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink findByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public static DDMStorageLink[] findByUuid_PrevAndNext(long storageLinkId,
		java.lang.String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence()
				   .findByUuid_PrevAndNext(storageLinkId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the d d m storage links where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of d d m storage links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m storage links
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public static DDMStorageLink[] findByUuid_C_PrevAndNext(
		long storageLinkId, java.lang.String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(storageLinkId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the d d m storage links where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m storage links
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the d d m storage link where classPK = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStorageLinkException} if it could not be found.
	*
	* @param classPK the class p k
	* @return the matching d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink findByClassPK(long classPK)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence().findByClassPK(classPK);
	}

	/**
	* Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classPK the class p k
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByClassPK(long classPK) {
		return getPersistence().fetchByClassPK(classPK);
	}

	/**
	* Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByClassPK(long classPK,
		boolean retrieveFromCache) {
		return getPersistence().fetchByClassPK(classPK, retrieveFromCache);
	}

	/**
	* Removes the d d m storage link where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	* @return the d d m storage link that was removed
	*/
	public static DDMStorageLink removeByClassPK(long classPK)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence().removeByClassPK(classPK);
	}

	/**
	* Returns the number of d d m storage links where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching d d m storage links
	*/
	public static int countByClassPK(long classPK) {
		return getPersistence().countByClassPK(classPK);
	}

	/**
	* Returns all the d d m storage links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m storage links
	*/
	public static List<DDMStorageLink> findByStructureId(long structureId) {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	* Returns a range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByStructureId(long structureId,
		int start, int end) {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByStructureId(long structureId,
		int start, int end, OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence()
				   .findByStructureId(structureId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m storage links
	*/
	public static List<DDMStorageLink> findByStructureId(long structureId,
		int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByStructureId(structureId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink findByStructureId_First(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence()
				   .findByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the first d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByStructureId_First(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence()
				   .fetchByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the last d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink findByStructureId_Last(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence()
				   .findByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the last d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public static DDMStorageLink fetchByStructureId_Last(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence()
				   .fetchByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public static DDMStorageLink[] findByStructureId_PrevAndNext(
		long storageLinkId, long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(storageLinkId, structureId,
			orderByComparator);
	}

	/**
	* Removes all the d d m storage links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	*/
	public static void removeByStructureId(long structureId) {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	* Returns the number of d d m storage links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m storage links
	*/
	public static int countByStructureId(long structureId) {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	* Caches the d d m storage link in the entity cache if it is enabled.
	*
	* @param ddmStorageLink the d d m storage link
	*/
	public static void cacheResult(DDMStorageLink ddmStorageLink) {
		getPersistence().cacheResult(ddmStorageLink);
	}

	/**
	* Caches the d d m storage links in the entity cache if it is enabled.
	*
	* @param ddmStorageLinks the d d m storage links
	*/
	public static void cacheResult(List<DDMStorageLink> ddmStorageLinks) {
		getPersistence().cacheResult(ddmStorageLinks);
	}

	/**
	* Creates a new d d m storage link with the primary key. Does not add the d d m storage link to the database.
	*
	* @param storageLinkId the primary key for the new d d m storage link
	* @return the new d d m storage link
	*/
	public static DDMStorageLink create(long storageLinkId) {
		return getPersistence().create(storageLinkId);
	}

	/**
	* Removes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public static DDMStorageLink remove(long storageLinkId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence().remove(storageLinkId);
	}

	public static DDMStorageLink updateImpl(DDMStorageLink ddmStorageLink) {
		return getPersistence().updateImpl(ddmStorageLink);
	}

	/**
	* Returns the d d m storage link with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStorageLinkException} if it could not be found.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public static DDMStorageLink findByPrimaryKey(long storageLinkId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException {
		return getPersistence().findByPrimaryKey(storageLinkId);
	}

	/**
	* Returns the d d m storage link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link, or <code>null</code> if a d d m storage link with the primary key could not be found
	*/
	public static DDMStorageLink fetchByPrimaryKey(long storageLinkId) {
		return getPersistence().fetchByPrimaryKey(storageLinkId);
	}

	public static java.util.Map<java.io.Serializable, DDMStorageLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d m storage links.
	*
	* @return the d d m storage links
	*/
	public static List<DDMStorageLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of d d m storage links
	*/
	public static List<DDMStorageLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m storage links
	*/
	public static List<DDMStorageLink> findAll(int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m storage links
	*/
	public static List<DDMStorageLink> findAll(int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m storage links from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m storage links.
	*
	* @return the number of d d m storage links
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DDMStorageLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(DDMStorageLinkPersistence persistence) {
	}

	private static ServiceTracker<DDMStorageLinkPersistence, DDMStorageLinkPersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDMStorageLinkPersistence.class);
}