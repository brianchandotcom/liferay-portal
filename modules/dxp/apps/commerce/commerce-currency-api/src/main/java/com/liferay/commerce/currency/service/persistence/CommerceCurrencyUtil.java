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

package com.liferay.commerce.currency.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.currency.model.CommerceCurrency;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce currency service. This utility wraps {@link com.liferay.commerce.currency.service.persistence.impl.CommerceCurrencyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyPersistence
 * @see com.liferay.commerce.currency.service.persistence.impl.CommerceCurrencyPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceCurrencyUtil {
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
	public static void clearCache(CommerceCurrency commerceCurrency) {
		getPersistence().clearCache(commerceCurrency);
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
	public static List<CommerceCurrency> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCurrency> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCurrency> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCurrency update(CommerceCurrency commerceCurrency) {
		return getPersistence().update(commerceCurrency);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCurrency update(CommerceCurrency commerceCurrency,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceCurrency, serviceContext);
	}

	/**
	* Returns all the commerce currencies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce currencies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @return the range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce currencies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce currencies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce currency in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce currency in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce currencies before and after the current commerce currency in the ordered set where uuid = &#63;.
	*
	* @param commerceCurrencyId the primary key of the current commerce currency
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce currency
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency[] findByUuid_PrevAndNext(
		long commerceCurrencyId, java.lang.String uuid,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceCurrencyId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce currencies where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce currencies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce currencies
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce currency where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCurrencyException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce currency where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce currency where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce currency where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce currency that was removed
	*/
	public static CommerceCurrency removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce currencies where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce currencies
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce currencies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @return the range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce currencies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce currencies before and after the current commerce currency in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceCurrencyId the primary key of the current commerce currency
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce currency
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency[] findByUuid_C_PrevAndNext(
		long commerceCurrencyId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceCurrencyId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce currencies where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce currencies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce currencies
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce currencies where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce currencies
	*/
	public static List<CommerceCurrency> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce currencies where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @return the range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceCurrency> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByGroupId_First(long groupId,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByGroupId_Last(long groupId,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce currencies before and after the current commerce currency in the ordered set where groupId = &#63;.
	*
	* @param commerceCurrencyId the primary key of the current commerce currency
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce currency
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency[] findByGroupId_PrevAndNext(
		long commerceCurrencyId, long groupId,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceCurrencyId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce currencies where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce currencies where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce currencies
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce currencies where groupId = &#63; and primary = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @return the matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P(long groupId, boolean primary) {
		return getPersistence().findByG_P(groupId, primary);
	}

	/**
	* Returns a range of all the commerce currencies where groupId = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @return the range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P(long groupId,
		boolean primary, int start, int end) {
		return getPersistence().findByG_P(groupId, primary, start, end);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P(long groupId,
		boolean primary, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .findByG_P(groupId, primary, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P(long groupId,
		boolean primary, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P(groupId, primary, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63; and primary = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByG_P_First(long groupId,
		boolean primary, OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_P_First(groupId, primary, orderByComparator);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63; and primary = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByG_P_First(long groupId,
		boolean primary, OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_First(groupId, primary, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63; and primary = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByG_P_Last(long groupId,
		boolean primary, OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_P_Last(groupId, primary, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63; and primary = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByG_P_Last(long groupId,
		boolean primary, OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_Last(groupId, primary, orderByComparator);
	}

	/**
	* Returns the commerce currencies before and after the current commerce currency in the ordered set where groupId = &#63; and primary = &#63;.
	*
	* @param commerceCurrencyId the primary key of the current commerce currency
	* @param groupId the group ID
	* @param primary the primary
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce currency
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency[] findByG_P_PrevAndNext(
		long commerceCurrencyId, long groupId, boolean primary,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_P_PrevAndNext(commerceCurrencyId, groupId, primary,
			orderByComparator);
	}

	/**
	* Removes all the commerce currencies where groupId = &#63; and primary = &#63; from the database.
	*
	* @param groupId the group ID
	* @param primary the primary
	*/
	public static void removeByG_P(long groupId, boolean primary) {
		getPersistence().removeByG_P(groupId, primary);
	}

	/**
	* Returns the number of commerce currencies where groupId = &#63; and primary = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @return the number of matching commerce currencies
	*/
	public static int countByG_P(long groupId, boolean primary) {
		return getPersistence().countByG_P(groupId, primary);
	}

	/**
	* Returns all the commerce currencies where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_A(long groupId, boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the commerce currencies where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @return the range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_A(long groupId,
		boolean active, int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByG_A_First(long groupId,
		boolean active, OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByG_A_First(long groupId,
		boolean active, OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByG_A_Last(long groupId,
		boolean active, OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the commerce currencies before and after the current commerce currency in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commerceCurrencyId the primary key of the current commerce currency
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce currency
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency[] findByG_A_PrevAndNext(
		long commerceCurrencyId, long groupId, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_A_PrevAndNext(commerceCurrencyId, groupId, active,
			orderByComparator);
	}

	/**
	* Removes all the commerce currencies where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of commerce currencies where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce currencies
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Returns all the commerce currencies where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @return the matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P_A(long groupId,
		boolean primary, boolean active) {
		return getPersistence().findByG_P_A(groupId, primary, active);
	}

	/**
	* Returns a range of all the commerce currencies where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @return the range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P_A(long groupId,
		boolean primary, boolean active, int start, int end) {
		return getPersistence().findByG_P_A(groupId, primary, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P_A(long groupId,
		boolean primary, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .findByG_P_A(groupId, primary, active, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce currencies where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce currencies
	*/
	public static List<CommerceCurrency> findByG_P_A(long groupId,
		boolean primary, boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_A(groupId, primary, active, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByG_P_A_First(long groupId,
		boolean primary, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_P_A_First(groupId, primary, active,
			orderByComparator);
	}

	/**
	* Returns the first commerce currency in the ordered set where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByG_P_A_First(long groupId,
		boolean primary, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_A_First(groupId, primary, active,
			orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency
	* @throws NoSuchCurrencyException if a matching commerce currency could not be found
	*/
	public static CommerceCurrency findByG_P_A_Last(long groupId,
		boolean primary, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_P_A_Last(groupId, primary, active, orderByComparator);
	}

	/**
	* Returns the last commerce currency in the ordered set where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce currency, or <code>null</code> if a matching commerce currency could not be found
	*/
	public static CommerceCurrency fetchByG_P_A_Last(long groupId,
		boolean primary, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_A_Last(groupId, primary, active,
			orderByComparator);
	}

	/**
	* Returns the commerce currencies before and after the current commerce currency in the ordered set where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* @param commerceCurrencyId the primary key of the current commerce currency
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce currency
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency[] findByG_P_A_PrevAndNext(
		long commerceCurrencyId, long groupId, boolean primary, boolean active,
		OrderByComparator<CommerceCurrency> orderByComparator)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence()
				   .findByG_P_A_PrevAndNext(commerceCurrencyId, groupId,
			primary, active, orderByComparator);
	}

	/**
	* Removes all the commerce currencies where groupId = &#63; and primary = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	*/
	public static void removeByG_P_A(long groupId, boolean primary,
		boolean active) {
		getPersistence().removeByG_P_A(groupId, primary, active);
	}

	/**
	* Returns the number of commerce currencies where groupId = &#63; and primary = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param primary the primary
	* @param active the active
	* @return the number of matching commerce currencies
	*/
	public static int countByG_P_A(long groupId, boolean primary, boolean active) {
		return getPersistence().countByG_P_A(groupId, primary, active);
	}

	/**
	* Caches the commerce currency in the entity cache if it is enabled.
	*
	* @param commerceCurrency the commerce currency
	*/
	public static void cacheResult(CommerceCurrency commerceCurrency) {
		getPersistence().cacheResult(commerceCurrency);
	}

	/**
	* Caches the commerce currencies in the entity cache if it is enabled.
	*
	* @param commerceCurrencies the commerce currencies
	*/
	public static void cacheResult(List<CommerceCurrency> commerceCurrencies) {
		getPersistence().cacheResult(commerceCurrencies);
	}

	/**
	* Creates a new commerce currency with the primary key. Does not add the commerce currency to the database.
	*
	* @param commerceCurrencyId the primary key for the new commerce currency
	* @return the new commerce currency
	*/
	public static CommerceCurrency create(long commerceCurrencyId) {
		return getPersistence().create(commerceCurrencyId);
	}

	/**
	* Removes the commerce currency with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCurrencyId the primary key of the commerce currency
	* @return the commerce currency that was removed
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency remove(long commerceCurrencyId)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().remove(commerceCurrencyId);
	}

	public static CommerceCurrency updateImpl(CommerceCurrency commerceCurrency) {
		return getPersistence().updateImpl(commerceCurrency);
	}

	/**
	* Returns the commerce currency with the primary key or throws a {@link NoSuchCurrencyException} if it could not be found.
	*
	* @param commerceCurrencyId the primary key of the commerce currency
	* @return the commerce currency
	* @throws NoSuchCurrencyException if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency findByPrimaryKey(long commerceCurrencyId)
		throws com.liferay.commerce.currency.exception.NoSuchCurrencyException {
		return getPersistence().findByPrimaryKey(commerceCurrencyId);
	}

	/**
	* Returns the commerce currency with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCurrencyId the primary key of the commerce currency
	* @return the commerce currency, or <code>null</code> if a commerce currency with the primary key could not be found
	*/
	public static CommerceCurrency fetchByPrimaryKey(long commerceCurrencyId) {
		return getPersistence().fetchByPrimaryKey(commerceCurrencyId);
	}

	public static java.util.Map<java.io.Serializable, CommerceCurrency> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce currencies.
	*
	* @return the commerce currencies
	*/
	public static List<CommerceCurrency> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce currencies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @return the range of commerce currencies
	*/
	public static List<CommerceCurrency> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce currencies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce currencies
	*/
	public static List<CommerceCurrency> findAll(int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce currencies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCurrencyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce currencies
	* @param end the upper bound of the range of commerce currencies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce currencies
	*/
	public static List<CommerceCurrency> findAll(int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce currencies from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce currencies.
	*
	* @return the number of commerce currencies
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceCurrencyPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCurrencyPersistence, CommerceCurrencyPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCurrencyPersistence.class);

		ServiceTracker<CommerceCurrencyPersistence, CommerceCurrencyPersistence> serviceTracker =
			new ServiceTracker<CommerceCurrencyPersistence, CommerceCurrencyPersistence>(bundle.getBundleContext(),
				CommerceCurrencyPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}