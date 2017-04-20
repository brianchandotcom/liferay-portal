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

import com.liferay.commerce.product.model.CommerceProductOptionValue;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce product option value service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CommerceProductOptionValuePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductOptionValuePersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductOptionValuePersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceProductOptionValueUtil {
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
		CommerceProductOptionValue commerceProductOptionValue) {
		getPersistence().clearCache(commerceProductOptionValue);
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
	public static List<CommerceProductOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceProductOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceProductOptionValue> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceProductOptionValue update(
		CommerceProductOptionValue commerceProductOptionValue) {
		return getPersistence().update(commerceProductOptionValue);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceProductOptionValue update(
		CommerceProductOptionValue commerceProductOptionValue,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceProductOptionValue, serviceContext);
	}

	/**
	* Returns all the commerce product option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce product option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product option values where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce product option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where uuid = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue[] findByUuid_PrevAndNext(
		long commerceProductOptionValueId, java.lang.String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceProductOptionValueId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce product option values where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce product option values where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product option values
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce product option value where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductOptionValueException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByUUID_G(
		java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce product option value where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product option value that was removed
	*/
	public static CommerceProductOptionValue removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce product option values where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product option values
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce product option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce product option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product option values where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue[] findByUuid_C_PrevAndNext(
		long commerceProductOptionValueId, java.lang.String uuid,
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceProductOptionValueId,
			uuid, companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product option values where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce product option values where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product option values
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce product option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue[] findByGroupId_PrevAndNext(
		long commerceProductOptionValueId, long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceProductOptionValueId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce product option values where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product option values
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce product option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce product option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue[] findByCompanyId_PrevAndNext(
		long commerceProductOptionValueId, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceProductOptionValueId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product option values where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce product option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product option values
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @return the matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId) {
		return getPersistence()
				   .findByCommerceProductOptionId(commerceProductOptionId);
	}

	/**
	* Returns a range of all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end) {
		return getPersistence()
				   .findByCommerceProductOptionId(commerceProductOptionId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .findByCommerceProductOptionId(commerceProductOptionId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public static List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceProductOptionId(commerceProductOptionId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByCommerceProductOptionId_First(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByCommerceProductOptionId_First(commerceProductOptionId,
			orderByComparator);
	}

	/**
	* Returns the first commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByCommerceProductOptionId_First(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceProductOptionId_First(commerceProductOptionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue findByCommerceProductOptionId_Last(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByCommerceProductOptionId_Last(commerceProductOptionId,
			orderByComparator);
	}

	/**
	* Returns the last commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public static CommerceProductOptionValue fetchByCommerceProductOptionId_Last(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceProductOptionId_Last(commerceProductOptionId,
			orderByComparator);
	}

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue[] findByCommerceProductOptionId_PrevAndNext(
		long commerceProductOptionValueId, long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence()
				   .findByCommerceProductOptionId_PrevAndNext(commerceProductOptionValueId,
			commerceProductOptionId, orderByComparator);
	}

	/**
	* Removes all the commerce product option values where commerceProductOptionId = &#63; from the database.
	*
	* @param commerceProductOptionId the commerce product option ID
	*/
	public static void removeByCommerceProductOptionId(
		long commerceProductOptionId) {
		getPersistence().removeByCommerceProductOptionId(commerceProductOptionId);
	}

	/**
	* Returns the number of commerce product option values where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @return the number of matching commerce product option values
	*/
	public static int countByCommerceProductOptionId(
		long commerceProductOptionId) {
		return getPersistence()
				   .countByCommerceProductOptionId(commerceProductOptionId);
	}

	/**
	* Caches the commerce product option value in the entity cache if it is enabled.
	*
	* @param commerceProductOptionValue the commerce product option value
	*/
	public static void cacheResult(
		CommerceProductOptionValue commerceProductOptionValue) {
		getPersistence().cacheResult(commerceProductOptionValue);
	}

	/**
	* Caches the commerce product option values in the entity cache if it is enabled.
	*
	* @param commerceProductOptionValues the commerce product option values
	*/
	public static void cacheResult(
		List<CommerceProductOptionValue> commerceProductOptionValues) {
		getPersistence().cacheResult(commerceProductOptionValues);
	}

	/**
	* Creates a new commerce product option value with the primary key. Does not add the commerce product option value to the database.
	*
	* @param commerceProductOptionValueId the primary key for the new commerce product option value
	* @return the new commerce product option value
	*/
	public static CommerceProductOptionValue create(
		long commerceProductOptionValueId) {
		return getPersistence().create(commerceProductOptionValueId);
	}

	/**
	* Removes the commerce product option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value that was removed
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue remove(
		long commerceProductOptionValueId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().remove(commerceProductOptionValueId);
	}

	public static CommerceProductOptionValue updateImpl(
		CommerceProductOptionValue commerceProductOptionValue) {
		return getPersistence().updateImpl(commerceProductOptionValue);
	}

	/**
	* Returns the commerce product option value with the primary key or throws a {@link NoSuchProductOptionValueException} if it could not be found.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue findByPrimaryKey(
		long commerceProductOptionValueId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionValueException {
		return getPersistence().findByPrimaryKey(commerceProductOptionValueId);
	}

	/**
	* Returns the commerce product option value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value, or <code>null</code> if a commerce product option value with the primary key could not be found
	*/
	public static CommerceProductOptionValue fetchByPrimaryKey(
		long commerceProductOptionValueId) {
		return getPersistence().fetchByPrimaryKey(commerceProductOptionValueId);
	}

	public static java.util.Map<java.io.Serializable, CommerceProductOptionValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce product option values.
	*
	* @return the commerce product option values
	*/
	public static List<CommerceProductOptionValue> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce product option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of commerce product option values
	*/
	public static List<CommerceProductOptionValue> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce product option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product option values
	*/
	public static List<CommerceProductOptionValue> findAll(int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product option values
	*/
	public static List<CommerceProductOptionValue> findAll(int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce product option values from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce product option values.
	*
	* @return the number of commerce product option values
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceProductOptionValuePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductOptionValuePersistence, CommerceProductOptionValuePersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductOptionValuePersistence.class);
}