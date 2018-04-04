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

import com.liferay.commerce.product.model.CPDefinitionLink;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition link service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDefinitionLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLinkPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionLinkPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionLinkUtil {
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
	public static void clearCache(CPDefinitionLink cpDefinitionLink) {
		getPersistence().clearCache(cpDefinitionLink);
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
	public static List<CPDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionLink update(CPDefinitionLink cpDefinitionLink) {
		return getPersistence().update(cpDefinitionLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionLink update(CPDefinitionLink cpDefinitionLink,
		ServiceContext serviceContext) {
		return getPersistence().update(cpDefinitionLink, serviceContext);
	}

	/**
	* Returns all the cp definition links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp definition links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink[] findByUuid_PrevAndNext(
		long CPDefinitionLinkId, java.lang.String uuid,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionLinkId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp definition links where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition links
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition link where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition link where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition link that was removed
	*/
	public static CPDefinitionLink removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition links where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition links
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink[] findByUuid_C_PrevAndNext(
		long CPDefinitionLinkId, java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionLinkId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition links where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition links
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @return the matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1) {
		return getPersistence().findByCPDefinitionId1(CPDefinitionId1);
	}

	/**
	* Returns a range of all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1, int start, int end) {
		return getPersistence()
				   .findByCPDefinitionId1(CPDefinitionId1, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId1(CPDefinitionId1, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId1(
		long CPDefinitionId1, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId1(CPDefinitionId1, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByCPDefinitionId1_First(
		long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByCPDefinitionId1_First(CPDefinitionId1,
			orderByComparator);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByCPDefinitionId1_First(
		long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId1_First(CPDefinitionId1,
			orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByCPDefinitionId1_Last(
		long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByCPDefinitionId1_Last(CPDefinitionId1,
			orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByCPDefinitionId1_Last(
		long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId1_Last(CPDefinitionId1,
			orderByComparator);
	}

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId1 the cp definition id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink[] findByCPDefinitionId1_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByCPDefinitionId1_PrevAndNext(CPDefinitionLinkId,
			CPDefinitionId1, orderByComparator);
	}

	/**
	* Removes all the cp definition links where CPDefinitionId1 = &#63; from the database.
	*
	* @param CPDefinitionId1 the cp definition id1
	*/
	public static void removeByCPDefinitionId1(long CPDefinitionId1) {
		getPersistence().removeByCPDefinitionId1(CPDefinitionId1);
	}

	/**
	* Returns the number of cp definition links where CPDefinitionId1 = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @return the number of matching cp definition links
	*/
	public static int countByCPDefinitionId1(long CPDefinitionId1) {
		return getPersistence().countByCPDefinitionId1(CPDefinitionId1);
	}

	/**
	* Returns all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @return the matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2) {
		return getPersistence().findByCPDefinitionId2(CPDefinitionId2);
	}

	/**
	* Returns a range of all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2, int start, int end) {
		return getPersistence()
				   .findByCPDefinitionId2(CPDefinitionId2, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId2(CPDefinitionId2, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByCPDefinitionId2(
		long CPDefinitionId2, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId2(CPDefinitionId2, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByCPDefinitionId2_First(
		long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByCPDefinitionId2_First(CPDefinitionId2,
			orderByComparator);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByCPDefinitionId2_First(
		long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId2_First(CPDefinitionId2,
			orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByCPDefinitionId2_Last(
		long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByCPDefinitionId2_Last(CPDefinitionId2,
			orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByCPDefinitionId2_Last(
		long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId2_Last(CPDefinitionId2,
			orderByComparator);
	}

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId2 the cp definition id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink[] findByCPDefinitionId2_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByCPDefinitionId2_PrevAndNext(CPDefinitionLinkId,
			CPDefinitionId2, orderByComparator);
	}

	/**
	* Removes all the cp definition links where CPDefinitionId2 = &#63; from the database.
	*
	* @param CPDefinitionId2 the cp definition id2
	*/
	public static void removeByCPDefinitionId2(long CPDefinitionId2) {
		getPersistence().removeByCPDefinitionId2(CPDefinitionId2);
	}

	/**
	* Returns the number of cp definition links where CPDefinitionId2 = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @return the number of matching cp definition links
	*/
	public static int countByCPDefinitionId2(long CPDefinitionId2) {
		return getPersistence().countByCPDefinitionId2(CPDefinitionId2);
	}

	/**
	* Returns all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @return the matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type) {
		return getPersistence().findByC1_T(CPDefinitionId1, type);
	}

	/**
	* Returns a range of all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type, int start, int end) {
		return getPersistence().findByC1_T(CPDefinitionId1, type, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByC1_T(CPDefinitionId1, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC1_T(long CPDefinitionId1,
		java.lang.String type, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC1_T(CPDefinitionId1, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByC1_T_First(long CPDefinitionId1,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByC1_T_First(CPDefinitionId1, type, orderByComparator);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByC1_T_First(long CPDefinitionId1,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByC1_T_First(CPDefinitionId1, type, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByC1_T_Last(long CPDefinitionId1,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByC1_T_Last(CPDefinitionId1, type, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByC1_T_Last(long CPDefinitionId1,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByC1_T_Last(CPDefinitionId1, type, orderByComparator);
	}

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink[] findByC1_T_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId1, java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByC1_T_PrevAndNext(CPDefinitionLinkId, CPDefinitionId1,
			type, orderByComparator);
	}

	/**
	* Removes all the cp definition links where CPDefinitionId1 = &#63; and type = &#63; from the database.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	*/
	public static void removeByC1_T(long CPDefinitionId1, java.lang.String type) {
		getPersistence().removeByC1_T(CPDefinitionId1, type);
	}

	/**
	* Returns the number of cp definition links where CPDefinitionId1 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param type the type
	* @return the number of matching cp definition links
	*/
	public static int countByC1_T(long CPDefinitionId1, java.lang.String type) {
		return getPersistence().countByC1_T(CPDefinitionId1, type);
	}

	/**
	* Returns all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type) {
		return getPersistence().findByC2_T(CPDefinitionId2, type);
	}

	/**
	* Returns a range of all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type, int start, int end) {
		return getPersistence().findByC2_T(CPDefinitionId2, type, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByC2_T(CPDefinitionId2, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition links
	*/
	public static List<CPDefinitionLink> findByC2_T(long CPDefinitionId2,
		java.lang.String type, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC2_T(CPDefinitionId2, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByC2_T_First(long CPDefinitionId2,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByC2_T_First(CPDefinitionId2, type, orderByComparator);
	}

	/**
	* Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByC2_T_First(long CPDefinitionId2,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByC2_T_First(CPDefinitionId2, type, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByC2_T_Last(long CPDefinitionId2,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByC2_T_Last(CPDefinitionId2, type, orderByComparator);
	}

	/**
	* Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByC2_T_Last(long CPDefinitionId2,
		java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByC2_T_Last(CPDefinitionId2, type, orderByComparator);
	}

	/**
	* Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionLinkId the primary key of the current cp definition link
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink[] findByC2_T_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId2, java.lang.String type,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByC2_T_PrevAndNext(CPDefinitionLinkId, CPDefinitionId2,
			type, orderByComparator);
	}

	/**
	* Removes all the cp definition links where CPDefinitionId2 = &#63; and type = &#63; from the database.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	*/
	public static void removeByC2_T(long CPDefinitionId2, java.lang.String type) {
		getPersistence().removeByC2_T(CPDefinitionId2, type);
	}

	/**
	* Returns the number of cp definition links where CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the number of matching cp definition links
	*/
	public static int countByC2_T(long CPDefinitionId2, java.lang.String type) {
		return getPersistence().countByC2_T(CPDefinitionId2, type);
	}

	/**
	* Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the matching cp definition link
	* @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink findByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .findByC1_C2_T(CPDefinitionId1, CPDefinitionId2, type);
	}

	/**
	* Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type) {
		return getPersistence()
				   .fetchByC1_C2_T(CPDefinitionId1, CPDefinitionId2, type);
	}

	/**
	* Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	public static CPDefinitionLink fetchByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC1_C2_T(CPDefinitionId1, CPDefinitionId2, type,
			retrieveFromCache);
	}

	/**
	* Removes the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; from the database.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the cp definition link that was removed
	*/
	public static CPDefinitionLink removeByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence()
				   .removeByC1_C2_T(CPDefinitionId1, CPDefinitionId2, type);
	}

	/**
	* Returns the number of cp definition links where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63;.
	*
	* @param CPDefinitionId1 the cp definition id1
	* @param CPDefinitionId2 the cp definition id2
	* @param type the type
	* @return the number of matching cp definition links
	*/
	public static int countByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, java.lang.String type) {
		return getPersistence()
				   .countByC1_C2_T(CPDefinitionId1, CPDefinitionId2, type);
	}

	/**
	* Caches the cp definition link in the entity cache if it is enabled.
	*
	* @param cpDefinitionLink the cp definition link
	*/
	public static void cacheResult(CPDefinitionLink cpDefinitionLink) {
		getPersistence().cacheResult(cpDefinitionLink);
	}

	/**
	* Caches the cp definition links in the entity cache if it is enabled.
	*
	* @param cpDefinitionLinks the cp definition links
	*/
	public static void cacheResult(List<CPDefinitionLink> cpDefinitionLinks) {
		getPersistence().cacheResult(cpDefinitionLinks);
	}

	/**
	* Creates a new cp definition link with the primary key. Does not add the cp definition link to the database.
	*
	* @param CPDefinitionLinkId the primary key for the new cp definition link
	* @return the new cp definition link
	*/
	public static CPDefinitionLink create(long CPDefinitionLinkId) {
		return getPersistence().create(CPDefinitionLinkId);
	}

	/**
	* Removes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link that was removed
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink remove(long CPDefinitionLinkId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().remove(CPDefinitionLinkId);
	}

	public static CPDefinitionLink updateImpl(CPDefinitionLink cpDefinitionLink) {
		return getPersistence().updateImpl(cpDefinitionLink);
	}

	/**
	* Returns the cp definition link with the primary key or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink findByPrimaryKey(long CPDefinitionLinkId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().findByPrimaryKey(CPDefinitionLinkId);
	}

	/**
	* Returns the cp definition link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link, or <code>null</code> if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink fetchByPrimaryKey(long CPDefinitionLinkId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionLinkId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition links.
	*
	* @return the cp definition links
	*/
	public static List<CPDefinitionLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of cp definition links
	*/
	public static List<CPDefinitionLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition links
	*/
	public static List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition links
	*/
	public static List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition links from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition links.
	*
	* @return the number of cp definition links
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionLinkPersistence, CPDefinitionLinkPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionLinkPersistence.class);

		ServiceTracker<CPDefinitionLinkPersistence, CPDefinitionLinkPersistence> serviceTracker =
			new ServiceTracker<CPDefinitionLinkPersistence, CPDefinitionLinkPersistence>(bundle.getBundleContext(),
				CPDefinitionLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}