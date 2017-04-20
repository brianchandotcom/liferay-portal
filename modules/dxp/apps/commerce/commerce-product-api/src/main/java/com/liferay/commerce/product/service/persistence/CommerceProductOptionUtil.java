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

import com.liferay.commerce.product.model.CommerceProductOption;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce product option service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CommerceProductOptionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductOptionPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductOptionPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceProductOptionUtil {
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
	public static void clearCache(CommerceProductOption commerceProductOption) {
		getPersistence().clearCache(commerceProductOption);
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
	public static List<CommerceProductOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceProductOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceProductOption> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceProductOption update(
		CommerceProductOption commerceProductOption) {
		return getPersistence().update(commerceProductOption);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceProductOption update(
		CommerceProductOption commerceProductOption,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceProductOption, serviceContext);
	}

	/**
	* Returns all the commerce product options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce product options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product options where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce product option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set where uuid = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption[] findByUuid_PrevAndNext(
		long commerceProductOptionId, java.lang.String uuid,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceProductOptionId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce product options where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce product options where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce product options
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce product option where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductOptionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce product option where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce product option where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce product option that was removed
	*/
	public static CommerceProductOption removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce product options where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce product options
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce product options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce product options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product options where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce product option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption[] findByUuid_C_PrevAndNext(
		long commerceProductOptionId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceProductOptionId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product options where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce product options where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce product options
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce product options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product options
	*/
	public static List<CommerceProductOption> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set where groupId = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption[] findByGroupId_PrevAndNext(
		long commerceProductOptionId, long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceProductOptionId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the commerce product options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product options that the user has permission to view
	*/
	public static List<CommerceProductOption> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce product options that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options that the user has permission to view
	*/
	public static List<CommerceProductOption> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product options that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options that the user has permission to view
	*/
	public static List<CommerceProductOption> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set of commerce product options that the user has permission to view where groupId = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption[] filterFindByGroupId_PrevAndNext(
		long commerceProductOptionId, long groupId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(commerceProductOptionId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce product options where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product options
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of commerce product options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product options that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the commerce product options where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product options
	*/
	public static List<CommerceProductOption> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce product options where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce product options where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product options where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product options
	*/
	public static List<CommerceProductOption> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public static CommerceProductOption findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static CommerceProductOption fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set where companyId = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption[] findByCompanyId_PrevAndNext(
		long commerceProductOptionId, long companyId,
		OrderByComparator<CommerceProductOption> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceProductOptionId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce product options where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce product options where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product options
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Caches the commerce product option in the entity cache if it is enabled.
	*
	* @param commerceProductOption the commerce product option
	*/
	public static void cacheResult(CommerceProductOption commerceProductOption) {
		getPersistence().cacheResult(commerceProductOption);
	}

	/**
	* Caches the commerce product options in the entity cache if it is enabled.
	*
	* @param commerceProductOptions the commerce product options
	*/
	public static void cacheResult(
		List<CommerceProductOption> commerceProductOptions) {
		getPersistence().cacheResult(commerceProductOptions);
	}

	/**
	* Creates a new commerce product option with the primary key. Does not add the commerce product option to the database.
	*
	* @param commerceProductOptionId the primary key for the new commerce product option
	* @return the new commerce product option
	*/
	public static CommerceProductOption create(long commerceProductOptionId) {
		return getPersistence().create(commerceProductOptionId);
	}

	/**
	* Removes the commerce product option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option that was removed
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption remove(long commerceProductOptionId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().remove(commerceProductOptionId);
	}

	public static CommerceProductOption updateImpl(
		CommerceProductOption commerceProductOption) {
		return getPersistence().updateImpl(commerceProductOption);
	}

	/**
	* Returns the commerce product option with the primary key or throws a {@link NoSuchProductOptionException} if it could not be found.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption findByPrimaryKey(
		long commerceProductOptionId)
		throws com.liferay.commerce.product.exception.NoSuchProductOptionException {
		return getPersistence().findByPrimaryKey(commerceProductOptionId);
	}

	/**
	* Returns the commerce product option with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option, or <code>null</code> if a commerce product option with the primary key could not be found
	*/
	public static CommerceProductOption fetchByPrimaryKey(
		long commerceProductOptionId) {
		return getPersistence().fetchByPrimaryKey(commerceProductOptionId);
	}

	public static java.util.Map<java.io.Serializable, CommerceProductOption> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce product options.
	*
	* @return the commerce product options
	*/
	public static List<CommerceProductOption> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of commerce product options
	*/
	public static List<CommerceProductOption> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product options
	*/
	public static List<CommerceProductOption> findAll(int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product options
	*/
	public static List<CommerceProductOption> findAll(int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce product options from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce product options.
	*
	* @return the number of commerce product options
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceProductOptionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductOptionPersistence, CommerceProductOptionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductOptionPersistence.class);
}