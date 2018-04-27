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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCountry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce country service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceCountryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceCountryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceCountryUtil {
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
	public static void clearCache(CommerceCountry commerceCountry) {
		getPersistence().clearCache(commerceCountry);
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
	public static List<CommerceCountry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCountry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCountry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCountry update(CommerceCountry commerceCountry) {
		return getPersistence().update(commerceCountry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCountry update(CommerceCountry commerceCountry,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceCountry, serviceContext);
	}

	/**
	* Returns all the commerce countries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce countries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid(String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByUuid_First(String uuid,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByUuid_First(String uuid,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByUuid_Last(String uuid,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry[] findByUuid_PrevAndNext(
		long commerceCountryId, String uuid,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceCountryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce countries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce countries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce countries
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce country where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce country where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce country where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce country where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce country that was removed
	*/
	public static CommerceCountry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce countries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce countries
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByUuid_C_First(String uuid,
		long companyId, OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry[] findByUuid_C_PrevAndNext(
		long commerceCountryId, String uuid, long companyId,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceCountryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce countries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce countries
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce countries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce countries
	*/
	public static List<CommerceCountry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce countries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public static List<CommerceCountry> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByGroupId_First(long groupId,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry[] findByGroupId_PrevAndNext(
		long commerceCountryId, long groupId,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceCountryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce countries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce countries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce countries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the commerce country where groupId = &#63; and numericISOCode = &#63; or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByG_N(long groupId, int numericISOCode)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().findByG_N(groupId, numericISOCode);
	}

	/**
	* Returns the commerce country where groupId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_N(long groupId, int numericISOCode) {
		return getPersistence().fetchByG_N(groupId, numericISOCode);
	}

	/**
	* Returns the commerce country where groupId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_N(long groupId, int numericISOCode,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_N(groupId, numericISOCode, retrieveFromCache);
	}

	/**
	* Removes the commerce country where groupId = &#63; and numericISOCode = &#63; from the database.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the commerce country that was removed
	*/
	public static CommerceCountry removeByG_N(long groupId, int numericISOCode)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().removeByG_N(groupId, numericISOCode);
	}

	/**
	* Returns the number of commerce countries where groupId = &#63; and numericISOCode = &#63;.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the number of matching commerce countries
	*/
	public static int countByG_N(long groupId, int numericISOCode) {
		return getPersistence().countByG_N(groupId, numericISOCode);
	}

	/**
	* Returns all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce countries
	*/
	public static List<CommerceCountry> findByG_A(long groupId, boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_A(long groupId, boolean active,
		int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByG_A_First(long groupId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_A_First(long groupId,
		boolean active, OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry[] findByG_A_PrevAndNext(
		long commerceCountryId, long groupId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_A_PrevAndNext(commerceCountryId, groupId, active,
			orderByComparator);
	}

	/**
	* Removes all the commerce countries where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of commerce countries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce countries
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Returns all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @return the matching commerce countries
	*/
	public static List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active) {
		return getPersistence().findByG_B_A(groupId, billingAllowed, active);
	}

	/**
	* Returns a range of all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active, int start, int end) {
		return getPersistence()
				   .findByG_B_A(groupId, billingAllowed, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .findByG_B_A(groupId, billingAllowed, active, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_B_A(groupId, billingAllowed, active, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByG_B_A_First(long groupId,
		boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_B_A_First(groupId, billingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_B_A_First(long groupId,
		boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByG_B_A_First(groupId, billingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByG_B_A_Last(long groupId,
		boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_B_A_Last(groupId, billingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_B_A_Last(long groupId,
		boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByG_B_A_Last(groupId, billingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry[] findByG_B_A_PrevAndNext(
		long commerceCountryId, long groupId, boolean billingAllowed,
		boolean active, OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_B_A_PrevAndNext(commerceCountryId, groupId,
			billingAllowed, active, orderByComparator);
	}

	/**
	* Removes all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	*/
	public static void removeByG_B_A(long groupId, boolean billingAllowed,
		boolean active) {
		getPersistence().removeByG_B_A(groupId, billingAllowed, active);
	}

	/**
	* Returns the number of commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @return the number of matching commerce countries
	*/
	public static int countByG_B_A(long groupId, boolean billingAllowed,
		boolean active) {
		return getPersistence().countByG_B_A(groupId, billingAllowed, active);
	}

	/**
	* Returns all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @return the matching commerce countries
	*/
	public static List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active) {
		return getPersistence().findByG_S_A(groupId, shippingAllowed, active);
	}

	/**
	* Returns a range of all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active, int start, int end) {
		return getPersistence()
				   .findByG_S_A(groupId, shippingAllowed, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .findByG_S_A(groupId, shippingAllowed, active, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public static List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_S_A(groupId, shippingAllowed, active, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByG_S_A_First(long groupId,
		boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_S_A_First(groupId, shippingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_S_A_First(long groupId,
		boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_A_First(groupId, shippingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public static CommerceCountry findByG_S_A_Last(long groupId,
		boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_S_A_Last(groupId, shippingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public static CommerceCountry fetchByG_S_A_Last(long groupId,
		boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence()
				   .fetchByG_S_A_Last(groupId, shippingAllowed, active,
			orderByComparator);
	}

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry[] findByG_S_A_PrevAndNext(
		long commerceCountryId, long groupId, boolean shippingAllowed,
		boolean active, OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence()
				   .findByG_S_A_PrevAndNext(commerceCountryId, groupId,
			shippingAllowed, active, orderByComparator);
	}

	/**
	* Removes all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	*/
	public static void removeByG_S_A(long groupId, boolean shippingAllowed,
		boolean active) {
		getPersistence().removeByG_S_A(groupId, shippingAllowed, active);
	}

	/**
	* Returns the number of commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @return the number of matching commerce countries
	*/
	public static int countByG_S_A(long groupId, boolean shippingAllowed,
		boolean active) {
		return getPersistence().countByG_S_A(groupId, shippingAllowed, active);
	}

	/**
	* Caches the commerce country in the entity cache if it is enabled.
	*
	* @param commerceCountry the commerce country
	*/
	public static void cacheResult(CommerceCountry commerceCountry) {
		getPersistence().cacheResult(commerceCountry);
	}

	/**
	* Caches the commerce countries in the entity cache if it is enabled.
	*
	* @param commerceCountries the commerce countries
	*/
	public static void cacheResult(List<CommerceCountry> commerceCountries) {
		getPersistence().cacheResult(commerceCountries);
	}

	/**
	* Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	*
	* @param commerceCountryId the primary key for the new commerce country
	* @return the new commerce country
	*/
	public static CommerceCountry create(long commerceCountryId) {
		return getPersistence().create(commerceCountryId);
	}

	/**
	* Removes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country that was removed
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry remove(long commerceCountryId)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().remove(commerceCountryId);
	}

	public static CommerceCountry updateImpl(CommerceCountry commerceCountry) {
		return getPersistence().updateImpl(commerceCountry);
	}

	/**
	* Returns the commerce country with the primary key or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry findByPrimaryKey(long commerceCountryId)
		throws com.liferay.commerce.exception.NoSuchCountryException {
		return getPersistence().findByPrimaryKey(commerceCountryId);
	}

	/**
	* Returns the commerce country with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country, or <code>null</code> if a commerce country with the primary key could not be found
	*/
	public static CommerceCountry fetchByPrimaryKey(long commerceCountryId) {
		return getPersistence().fetchByPrimaryKey(commerceCountryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceCountry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce countries.
	*
	* @return the commerce countries
	*/
	public static List<CommerceCountry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of commerce countries
	*/
	public static List<CommerceCountry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce countries
	*/
	public static List<CommerceCountry> findAll(int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce countries
	*/
	public static List<CommerceCountry> findAll(int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce countries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce countries.
	*
	* @return the number of commerce countries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceCountryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCountryPersistence, CommerceCountryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCountryPersistence.class);

		ServiceTracker<CommerceCountryPersistence, CommerceCountryPersistence> serviceTracker =
			new ServiceTracker<CommerceCountryPersistence, CommerceCountryPersistence>(bundle.getBundleContext(),
				CommerceCountryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}