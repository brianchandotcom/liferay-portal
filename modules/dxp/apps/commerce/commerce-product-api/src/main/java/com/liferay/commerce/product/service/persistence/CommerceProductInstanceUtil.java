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

import com.liferay.commerce.product.model.CommerceProductInstance;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce product instance service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CommerceProductInstancePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductInstancePersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductInstancePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceProductInstanceUtil {
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
		CommerceProductInstance commerceProductInstance) {
		getPersistence().clearCache(commerceProductInstance);
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
	public static List<CommerceProductInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceProductInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceProductInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceProductInstance update(
		CommerceProductInstance commerceProductInstance) {
		return getPersistence().update(commerceProductInstance);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceProductInstance update(
		CommerceProductInstance commerceProductInstance,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceProductInstance, serviceContext);
	}

	/**
	* Returns all the commerce product instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce product instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where uuid = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance[] findByUuid_PrevAndNext(
		long commerceProductInstanceId, java.lang.String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceProductInstanceId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce product instances where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce product instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product instances
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce product instance where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductInstanceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce product instance where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product instance that was removed
	*/
	public static CommerceProductInstance removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce product instances where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product instances
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance[] findByUuid_C_PrevAndNext(
		long commerceProductInstanceId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceProductInstanceId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product instances where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce product instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product instances
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce product instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where groupId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance[] findByGroupId_PrevAndNext(
		long commerceProductInstanceId, long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceProductInstanceId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce product instances where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product instances
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce product instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce product instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where companyId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance[] findByCompanyId_PrevAndNext(
		long commerceProductInstanceId, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceProductInstanceId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product instances where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce product instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product instances
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @return the matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		return getPersistence()
				   .findByCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	/**
	* Returns a range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end) {
		return getPersistence()
				   .findByCommerceProductDefinitionId(commerceProductDefinitionId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .findByCommerceProductDefinitionId(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product instances
	*/
	public static List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceProductDefinitionId(commerceProductDefinitionId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByCommerceProductDefinitionId_First(commerceProductDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the first commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceProductDefinitionId_First(commerceProductDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByCommerceProductDefinitionId_Last(commerceProductDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceProductDefinitionId_Last(commerceProductDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the commerce product instances before and after the current commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductInstanceId the primary key of the current commerce product instance
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance[] findByCommerceProductDefinitionId_PrevAndNext(
		long commerceProductInstanceId, long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence()
				   .findByCommerceProductDefinitionId_PrevAndNext(commerceProductInstanceId,
			commerceProductDefinitionId, orderByComparator);
	}

	/**
	* Removes all the commerce product instances where commerceProductDefinitionId = &#63; from the database.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	*/
	public static void removeByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		getPersistence()
			.removeByCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	/**
	* Returns the number of commerce product instances where commerceProductDefinitionId = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @return the number of matching commerce product instances
	*/
	public static int countByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		return getPersistence()
				   .countByCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	/**
	* Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or throws a {@link NoSuchProductInstanceException} if it could not be found.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the matching commerce product instance
	* @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance findByC_S(
		long commerceProductDefinitionId, java.lang.String sku)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().findByC_S(commerceProductDefinitionId, sku);
	}

	/**
	* Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByC_S(
		long commerceProductDefinitionId, java.lang.String sku) {
		return getPersistence().fetchByC_S(commerceProductDefinitionId, sku);
	}

	/**
	* Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	*/
	public static CommerceProductInstance fetchByC_S(
		long commerceProductDefinitionId, java.lang.String sku,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_S(commerceProductDefinitionId, sku,
			retrieveFromCache);
	}

	/**
	* Removes the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; from the database.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the commerce product instance that was removed
	*/
	public static CommerceProductInstance removeByC_S(
		long commerceProductDefinitionId, java.lang.String sku)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().removeByC_S(commerceProductDefinitionId, sku);
	}

	/**
	* Returns the number of commerce product instances where commerceProductDefinitionId = &#63; and sku = &#63;.
	*
	* @param commerceProductDefinitionId the commerce product definition ID
	* @param sku the sku
	* @return the number of matching commerce product instances
	*/
	public static int countByC_S(long commerceProductDefinitionId,
		java.lang.String sku) {
		return getPersistence().countByC_S(commerceProductDefinitionId, sku);
	}

	/**
	* Caches the commerce product instance in the entity cache if it is enabled.
	*
	* @param commerceProductInstance the commerce product instance
	*/
	public static void cacheResult(
		CommerceProductInstance commerceProductInstance) {
		getPersistence().cacheResult(commerceProductInstance);
	}

	/**
	* Caches the commerce product instances in the entity cache if it is enabled.
	*
	* @param commerceProductInstances the commerce product instances
	*/
	public static void cacheResult(
		List<CommerceProductInstance> commerceProductInstances) {
		getPersistence().cacheResult(commerceProductInstances);
	}

	/**
	* Creates a new commerce product instance with the primary key. Does not add the commerce product instance to the database.
	*
	* @param commerceProductInstanceId the primary key for the new commerce product instance
	* @return the new commerce product instance
	*/
	public static CommerceProductInstance create(long commerceProductInstanceId) {
		return getPersistence().create(commerceProductInstanceId);
	}

	/**
	* Removes the commerce product instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance that was removed
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance remove(long commerceProductInstanceId)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().remove(commerceProductInstanceId);
	}

	public static CommerceProductInstance updateImpl(
		CommerceProductInstance commerceProductInstance) {
		return getPersistence().updateImpl(commerceProductInstance);
	}

	/**
	* Returns the commerce product instance with the primary key or throws a {@link NoSuchProductInstanceException} if it could not be found.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance
	* @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance findByPrimaryKey(
		long commerceProductInstanceId)
		throws com.liferay.commerce.product.exception.NoSuchProductInstanceException {
		return getPersistence().findByPrimaryKey(commerceProductInstanceId);
	}

	/**
	* Returns the commerce product instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductInstanceId the primary key of the commerce product instance
	* @return the commerce product instance, or <code>null</code> if a commerce product instance with the primary key could not be found
	*/
	public static CommerceProductInstance fetchByPrimaryKey(
		long commerceProductInstanceId) {
		return getPersistence().fetchByPrimaryKey(commerceProductInstanceId);
	}

	public static java.util.Map<java.io.Serializable, CommerceProductInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce product instances.
	*
	* @return the commerce product instances
	*/
	public static List<CommerceProductInstance> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce product instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @return the range of commerce product instances
	*/
	public static List<CommerceProductInstance> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce product instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product instances
	*/
	public static List<CommerceProductInstance> findAll(int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product instances
	* @param end the upper bound of the range of commerce product instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product instances
	*/
	public static List<CommerceProductInstance> findAll(int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce product instances from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce product instances.
	*
	* @return the number of commerce product instances
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceProductInstancePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductInstancePersistence, CommerceProductInstancePersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductInstancePersistence.class);
}