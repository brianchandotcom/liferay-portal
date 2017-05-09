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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionMedia;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition media service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDefinitionMediaPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionMediaPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionMediaPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionMediaUtil {
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
	public static void clearCache(CPDefinitionMedia cpDefinitionMedia) {
		getPersistence().clearCache(cpDefinitionMedia);
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
	public static List<CPDefinitionMedia> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionMedia> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionMedia> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionMedia update(CPDefinitionMedia cpDefinitionMedia) {
		return getPersistence().update(cpDefinitionMedia);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionMedia update(
		CPDefinitionMedia cpDefinitionMedia, ServiceContext serviceContext) {
		return getPersistence().update(cpDefinitionMedia, serviceContext);
	}

	/**
	* Returns all the cp definition medias where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp definition medias where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition medias before and after the current cp definition media in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionMediaId the primary key of the current cp definition media
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition media
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public static CPDefinitionMedia[] findByUuid_PrevAndNext(
		long CPDefinitionMediaId, java.lang.String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionMediaId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp definition medias where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition medias where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition medias
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition media where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionMediaException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition media where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition media where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition media where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition media that was removed
	*/
	public static CPDefinitionMedia removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition medias where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition medias
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp definition medias before and after the current cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionMediaId the primary key of the current cp definition media
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition media
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public static CPDefinitionMedia[] findByUuid_C_PrevAndNext(
		long CPDefinitionMediaId, java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionMediaId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition medias where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition medias
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp definition medias where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns a range of all the cp definition medias where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition medias where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition medias where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition medias
	*/
	public static List<CPDefinitionMedia> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition media in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence()
				   .findByCPDefinitionId_First(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the first cp definition media in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_First(CPDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition media in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence()
				   .findByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the last cp definition media in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public static CPDefinitionMedia fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the cp definition medias before and after the current cp definition media in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionMediaId the primary key of the current cp definition media
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition media
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public static CPDefinitionMedia[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionMediaId, long CPDefinitionId,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence()
				   .findByCPDefinitionId_PrevAndNext(CPDefinitionMediaId,
			CPDefinitionId, orderByComparator);
	}

	/**
	* Removes all the cp definition medias where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition medias where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition medias
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Caches the cp definition media in the entity cache if it is enabled.
	*
	* @param cpDefinitionMedia the cp definition media
	*/
	public static void cacheResult(CPDefinitionMedia cpDefinitionMedia) {
		getPersistence().cacheResult(cpDefinitionMedia);
	}

	/**
	* Caches the cp definition medias in the entity cache if it is enabled.
	*
	* @param cpDefinitionMedias the cp definition medias
	*/
	public static void cacheResult(List<CPDefinitionMedia> cpDefinitionMedias) {
		getPersistence().cacheResult(cpDefinitionMedias);
	}

	/**
	* Creates a new cp definition media with the primary key. Does not add the cp definition media to the database.
	*
	* @param CPDefinitionMediaId the primary key for the new cp definition media
	* @return the new cp definition media
	*/
	public static CPDefinitionMedia create(long CPDefinitionMediaId) {
		return getPersistence().create(CPDefinitionMediaId);
	}

	/**
	* Removes the cp definition media with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media that was removed
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public static CPDefinitionMedia remove(long CPDefinitionMediaId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence().remove(CPDefinitionMediaId);
	}

	public static CPDefinitionMedia updateImpl(
		CPDefinitionMedia cpDefinitionMedia) {
		return getPersistence().updateImpl(cpDefinitionMedia);
	}

	/**
	* Returns the cp definition media with the primary key or throws a {@link NoSuchCPDefinitionMediaException} if it could not be found.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public static CPDefinitionMedia findByPrimaryKey(long CPDefinitionMediaId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException {
		return getPersistence().findByPrimaryKey(CPDefinitionMediaId);
	}

	/**
	* Returns the cp definition media with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media, or <code>null</code> if a cp definition media with the primary key could not be found
	*/
	public static CPDefinitionMedia fetchByPrimaryKey(long CPDefinitionMediaId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionMediaId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionMedia> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition medias.
	*
	* @return the cp definition medias
	*/
	public static List<CPDefinitionMedia> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of cp definition medias
	*/
	public static List<CPDefinitionMedia> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition medias
	*/
	public static List<CPDefinitionMedia> findAll(int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition medias
	*/
	public static List<CPDefinitionMedia> findAll(int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition medias from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition medias.
	*
	* @return the number of cp definition medias
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionMediaPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionMediaPersistence, CPDefinitionMediaPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CPDefinitionMediaPersistence.class);
}