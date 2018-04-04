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

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition option value rel service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDefinitionOptionValueRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionOptionValueRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelUtil {
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
	public static void clearCache(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		getPersistence().clearCache(cpDefinitionOptionValueRel);
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
	public static List<CPDefinitionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionOptionValueRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionOptionValueRel update(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		return getPersistence().update(cpDefinitionOptionValueRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionOptionValueRel update(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(cpDefinitionOptionValueRel, serviceContext);
	}

	/**
	* Returns all the cp definition option value rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp definition option value rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel[] findByUuid_PrevAndNext(
		long CPDefinitionOptionValueRelId, java.lang.String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionOptionValueRelId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp definition option value rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition option value rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition option value rels
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition option value rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionOptionValueRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition option value rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition option value rel that was removed
	*/
	public static CPDefinitionOptionValueRel removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition option value rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition option value rels
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel[] findByUuid_C_PrevAndNext(
		long CPDefinitionOptionValueRelId, java.lang.String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionOptionValueRelId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition option value rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition option value rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition option value rels
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp definition option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where groupId = &#63;.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel[] findByGroupId_PrevAndNext(
		long CPDefinitionOptionValueRelId, long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPDefinitionOptionValueRelId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the cp definition option value rels where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp definition option value rels where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp definition option value rels
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the cp definition option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the cp definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByCompanyId_First(
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByCompanyId_Last(
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where companyId = &#63;.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel[] findByCompanyId_PrevAndNext(
		long CPDefinitionOptionValueRelId, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(CPDefinitionOptionValueRelId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition option value rels where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of cp definition option value rels where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching cp definition option value rels
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @return the matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {
		return getPersistence()
				   .findByCPDefinitionOptionRelId(CPDefinitionOptionRelId);
	}

	/**
	* Returns a range of all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end) {
		return getPersistence()
				   .findByCPDefinitionOptionRelId(CPDefinitionOptionRelId,
			start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionOptionRelId(CPDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionOptionRelId(CPDefinitionOptionRelId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByCPDefinitionOptionRelId_First(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCPDefinitionOptionRelId_First(CPDefinitionOptionRelId,
			orderByComparator);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByCPDefinitionOptionRelId_First(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionOptionRelId_First(CPDefinitionOptionRelId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByCPDefinitionOptionRelId_Last(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCPDefinitionOptionRelId_Last(CPDefinitionOptionRelId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByCPDefinitionOptionRelId_Last(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionOptionRelId_Last(CPDefinitionOptionRelId,
			orderByComparator);
	}

	/**
	* Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel[] findByCPDefinitionOptionRelId_PrevAndNext(
		long CPDefinitionOptionValueRelId, long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findByCPDefinitionOptionRelId_PrevAndNext(CPDefinitionOptionValueRelId,
			CPDefinitionOptionRelId, orderByComparator);
	}

	/**
	* Removes all the cp definition option value rels where CPDefinitionOptionRelId = &#63; from the database.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	*/
	public static void removeByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {
		getPersistence().removeByCPDefinitionOptionRelId(CPDefinitionOptionRelId);
	}

	/**
	* Returns the number of cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @return the number of matching cp definition option value rels
	*/
	public static int countByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {
		return getPersistence()
				   .countByCPDefinitionOptionRelId(CPDefinitionOptionRelId);
	}

	/**
	* Returns all the cp definition option value rels where key = &#63;.
	*
	* @param key the key
	* @return the matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findBykey(
		java.lang.String key) {
		return getPersistence().findBykey(key);
	}

	/**
	* Returns a range of all the cp definition option value rels where key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param key the key
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findBykey(
		java.lang.String key, int start, int end) {
		return getPersistence().findBykey(key, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param key the key
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findBykey(
		java.lang.String key, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().findBykey(key, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels where key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param key the key
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findBykey(
		java.lang.String key, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBykey(key, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findBykey_First(
		java.lang.String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findBykey_First(key, orderByComparator);
	}

	/**
	* Returns the first cp definition option value rel in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchBykey_First(
		java.lang.String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchBykey_First(key, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findBykey_Last(
		java.lang.String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findBykey_Last(key, orderByComparator);
	}

	/**
	* Returns the last cp definition option value rel in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchBykey_Last(
		java.lang.String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().fetchBykey_Last(key, orderByComparator);
	}

	/**
	* Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where key = &#63;.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel[] findBykey_PrevAndNext(
		long CPDefinitionOptionValueRelId, java.lang.String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence()
				   .findBykey_PrevAndNext(CPDefinitionOptionValueRelId, key,
			orderByComparator);
	}

	/**
	* Removes all the cp definition option value rels where key = &#63; from the database.
	*
	* @param key the key
	*/
	public static void removeBykey(java.lang.String key) {
		getPersistence().removeBykey(key);
	}

	/**
	* Returns the number of cp definition option value rels where key = &#63;.
	*
	* @param key the key
	* @return the number of matching cp definition option value rels
	*/
	public static int countBykey(java.lang.String key) {
		return getPersistence().countBykey(key);
	}

	/**
	* Returns the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; or throws a {@link NoSuchCPDefinitionOptionValueRelException} if it could not be found.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param key the key
	* @return the matching cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel findByC_K(
		long CPDefinitionOptionRelId, java.lang.String key)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findByC_K(CPDefinitionOptionRelId, key);
	}

	/**
	* Returns the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param key the key
	* @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByC_K(
		long CPDefinitionOptionRelId, java.lang.String key) {
		return getPersistence().fetchByC_K(CPDefinitionOptionRelId, key);
	}

	/**
	* Returns the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByC_K(
		long CPDefinitionOptionRelId, java.lang.String key,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_K(CPDefinitionOptionRelId, key, retrieveFromCache);
	}

	/**
	* Removes the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; from the database.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param key the key
	* @return the cp definition option value rel that was removed
	*/
	public static CPDefinitionOptionValueRel removeByC_K(
		long CPDefinitionOptionRelId, java.lang.String key)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().removeByC_K(CPDefinitionOptionRelId, key);
	}

	/**
	* Returns the number of cp definition option value rels where CPDefinitionOptionRelId = &#63; and key = &#63;.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID
	* @param key the key
	* @return the number of matching cp definition option value rels
	*/
	public static int countByC_K(long CPDefinitionOptionRelId,
		java.lang.String key) {
		return getPersistence().countByC_K(CPDefinitionOptionRelId, key);
	}

	/**
	* Caches the cp definition option value rel in the entity cache if it is enabled.
	*
	* @param cpDefinitionOptionValueRel the cp definition option value rel
	*/
	public static void cacheResult(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		getPersistence().cacheResult(cpDefinitionOptionValueRel);
	}

	/**
	* Caches the cp definition option value rels in the entity cache if it is enabled.
	*
	* @param cpDefinitionOptionValueRels the cp definition option value rels
	*/
	public static void cacheResult(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels) {
		getPersistence().cacheResult(cpDefinitionOptionValueRels);
	}

	/**
	* Creates a new cp definition option value rel with the primary key. Does not add the cp definition option value rel to the database.
	*
	* @param CPDefinitionOptionValueRelId the primary key for the new cp definition option value rel
	* @return the new cp definition option value rel
	*/
	public static CPDefinitionOptionValueRel create(
		long CPDefinitionOptionValueRelId) {
		return getPersistence().create(CPDefinitionOptionValueRelId);
	}

	/**
	* Removes the cp definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	* @return the cp definition option value rel that was removed
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel remove(
		long CPDefinitionOptionValueRelId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().remove(CPDefinitionOptionValueRelId);
	}

	public static CPDefinitionOptionValueRel updateImpl(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		return getPersistence().updateImpl(cpDefinitionOptionValueRel);
	}

	/**
	* Returns the cp definition option value rel with the primary key or throws a {@link NoSuchCPDefinitionOptionValueRelException} if it could not be found.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	* @return the cp definition option value rel
	* @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel findByPrimaryKey(
		long CPDefinitionOptionValueRelId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException {
		return getPersistence().findByPrimaryKey(CPDefinitionOptionValueRelId);
	}

	/**
	* Returns the cp definition option value rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	* @return the cp definition option value rel, or <code>null</code> if a cp definition option value rel with the primary key could not be found
	*/
	public static CPDefinitionOptionValueRel fetchByPrimaryKey(
		long CPDefinitionOptionValueRelId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionOptionValueRelId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionOptionValueRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition option value rels.
	*
	* @return the cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition option value rels
	*/
	public static List<CPDefinitionOptionValueRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition option value rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition option value rels.
	*
	* @return the number of cp definition option value rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionOptionValueRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionOptionValueRelPersistence, CPDefinitionOptionValueRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionOptionValueRelPersistence.class);

		ServiceTracker<CPDefinitionOptionValueRelPersistence, CPDefinitionOptionValueRelPersistence> serviceTracker =
			new ServiceTracker<CPDefinitionOptionValueRelPersistence, CPDefinitionOptionValueRelPersistence>(bundle.getBundleContext(),
				CPDefinitionOptionValueRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}