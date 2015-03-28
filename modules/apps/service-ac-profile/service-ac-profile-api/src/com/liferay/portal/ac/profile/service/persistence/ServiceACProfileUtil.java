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

package com.liferay.portal.ac.profile.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.ac.profile.model.ServiceACProfile;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the service a c profile service. This utility wraps {@link com.liferay.portal.ac.profile.service.persistence.impl.ServiceACProfilePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceACProfilePersistence
 * @see com.liferay.portal.ac.profile.service.persistence.impl.ServiceACProfilePersistenceImpl
 * @generated
 */
@ProviderType
public class ServiceACProfileUtil {
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
	public static void clearCache(ServiceACProfile serviceACProfile) {
		getPersistence().clearCache(serviceACProfile);
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
	public static List<ServiceACProfile> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ServiceACProfile> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ServiceACProfile> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ServiceACProfile> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ServiceACProfile update(ServiceACProfile serviceACProfile) {
		return getPersistence().update(serviceACProfile);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ServiceACProfile update(ServiceACProfile serviceACProfile,
		ServiceContext serviceContext) {
		return getPersistence().update(serviceACProfile, serviceContext);
	}

	/**
	* Returns all the service a c profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the service a c profiles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the service a c profiles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the service a c profiles before and after the current service a c profile in the ordered set where uuid = &#63;.
	*
	* @param serviceACProfileId the primary key of the current service a c profile
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile[] findByUuid_PrevAndNext(
		long serviceACProfileId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence()
				   .findByUuid_PrevAndNext(serviceACProfileId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the service a c profiles where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of service a c profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching service a c profiles
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the service a c profiles before and after the current service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param serviceACProfileId the primary key of the current service a c profile
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile[] findByUuid_C_PrevAndNext(
		long serviceACProfileId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(serviceACProfileId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the service a c profiles where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching service a c profiles
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the service a c profiles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the service a c profiles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the service a c profiles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the service a c profiles before and after the current service a c profile in the ordered set where companyId = &#63;.
	*
	* @param serviceACProfileId the primary key of the current service a c profile
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile[] findByCompanyId_PrevAndNext(
		long serviceACProfileId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(serviceACProfileId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the service a c profiles where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of service a c profiles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching service a c profiles
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the service a c profile where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.ac.profile.NoSuchServiceACProfileException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns the service a c profile where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByC_N(
		long companyId, java.lang.String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	* Returns the service a c profile where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	/**
	* Removes the service a c profile where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the service a c profile that was removed
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile removeByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of service a c profiles where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching service a c profiles
	*/
	public static int countByC_N(long companyId, java.lang.String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Caches the service a c profile in the entity cache if it is enabled.
	*
	* @param serviceACProfile the service a c profile
	*/
	public static void cacheResult(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		getPersistence().cacheResult(serviceACProfile);
	}

	/**
	* Caches the service a c profiles in the entity cache if it is enabled.
	*
	* @param serviceACProfiles the service a c profiles
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> serviceACProfiles) {
		getPersistence().cacheResult(serviceACProfiles);
	}

	/**
	* Creates a new service a c profile with the primary key. Does not add the service a c profile to the database.
	*
	* @param serviceACProfileId the primary key for the new service a c profile
	* @return the new service a c profile
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile create(
		long serviceACProfileId) {
		return getPersistence().create(serviceACProfileId);
	}

	/**
	* Removes the service a c profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile that was removed
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile remove(
		long serviceACProfileId)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence().remove(serviceACProfileId);
	}

	public static com.liferay.portal.ac.profile.model.ServiceACProfile updateImpl(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile) {
		return getPersistence().updateImpl(serviceACProfile);
	}

	/**
	* Returns the service a c profile with the primary key or throws a {@link com.liferay.portal.ac.profile.NoSuchServiceACProfileException} if it could not be found.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile findByPrimaryKey(
		long serviceACProfileId)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException {
		return getPersistence().findByPrimaryKey(serviceACProfileId);
	}

	/**
	* Returns the service a c profile with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile, or <code>null</code> if a service a c profile with the primary key could not be found
	*/
	public static com.liferay.portal.ac.profile.model.ServiceACProfile fetchByPrimaryKey(
		long serviceACProfileId) {
		return getPersistence().fetchByPrimaryKey(serviceACProfileId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.ac.profile.model.ServiceACProfile> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the service a c profiles.
	*
	* @return the service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the service a c profiles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the service a c profiles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of service a c profiles
	*/
	public static java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the service a c profiles from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of service a c profiles.
	*
	* @return the number of service a c profiles
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ServiceACProfilePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ServiceACProfilePersistence persistence) {
	}

	private static ServiceTracker<ServiceACProfilePersistence, ServiceACProfilePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ServiceACProfileUtil.class);

		_serviceTracker = new ServiceTracker<ServiceACProfilePersistence, ServiceACProfilePersistence>(bundle.getBundleContext(),
				ServiceACProfilePersistence.class, null);

		_serviceTracker.open();
	}
}