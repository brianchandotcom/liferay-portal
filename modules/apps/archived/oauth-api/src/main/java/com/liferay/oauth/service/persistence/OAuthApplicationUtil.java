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

package com.liferay.oauth.service.persistence;

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the o auth application service. This utility wraps <code>com.liferay.oauth.service.persistence.impl.OAuthApplicationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthApplicationPersistence
 * @generated
 */
public class OAuthApplicationUtil {

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
	public static void clearCache(OAuthApplication oAuthApplication) {
		getPersistence().clearCache(oAuthApplication);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, OAuthApplication> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OAuthApplication> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuthApplication> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuthApplication> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuthApplication update(OAuthApplication oAuthApplication) {
		return getPersistence().update(oAuthApplication);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuthApplication update(
		OAuthApplication oAuthApplication, ServiceContext serviceContext) {

		return getPersistence().update(oAuthApplication, serviceContext);
	}

	/**
	 * Returns all the o auth applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth applications
	 */
	public static List<OAuthApplication> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public static List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByCompanyId_First(
		long companyId, OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByCompanyId_Last(
		long companyId, OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] findByCompanyId_PrevAndNext(
			long oAuthApplicationId, long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByCompanyId_PrevAndNext(
			oAuthApplicationId, companyId, orderByComparator);
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] filterFindByCompanyId_PrevAndNext(
			long oAuthApplicationId, long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			oAuthApplicationId, companyId, orderByComparator);
	}

	/**
	 * Removes all the o auth applications where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth applications
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the o auth applications where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth applications
	 */
	public static List<OAuthApplication> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public static List<OAuthApplication> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByUserId_First(
			long userId, OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByUserId_First(
		long userId, OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByUserId_Last(
			long userId, OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByUserId_Last(
		long userId, OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where userId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] findByUserId_PrevAndNext(
			long oAuthApplicationId, long userId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByUserId_PrevAndNext(
			oAuthApplicationId, userId, orderByComparator);
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByUserId(long userId) {
		return getPersistence().filterFindByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByUserId(
		long userId, int start, int end) {

		return getPersistence().filterFindByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().filterFindByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] filterFindByUserId_PrevAndNext(
			long oAuthApplicationId, long userId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().filterFindByUserId_PrevAndNext(
			oAuthApplicationId, userId, orderByComparator);
	}

	/**
	 * Removes all the o auth applications where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of o auth applications where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth applications
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns the number of o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	public static int filterCountByUserId(long userId) {
		return getPersistence().filterCountByUserId(userId);
	}

	/**
	 * Returns the o auth application where consumerKey = &#63; or throws a <code>NoSuchApplicationException</code> if it could not be found.
	 *
	 * @param consumerKey the consumer key
	 * @return the matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByConsumerKey(String consumerKey)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByConsumerKey(consumerKey);
	}

	/**
	 * Returns the o auth application where consumerKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param consumerKey the consumer key
	 * @return the matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByConsumerKey(String consumerKey) {
		return getPersistence().fetchByConsumerKey(consumerKey);
	}

	/**
	 * Returns the o auth application where consumerKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param consumerKey the consumer key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByConsumerKey(
		String consumerKey, boolean useFinderCache) {

		return getPersistence().fetchByConsumerKey(consumerKey, useFinderCache);
	}

	/**
	 * Removes the o auth application where consumerKey = &#63; from the database.
	 *
	 * @param consumerKey the consumer key
	 * @return the o auth application that was removed
	 */
	public static OAuthApplication removeByConsumerKey(String consumerKey)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().removeByConsumerKey(consumerKey);
	}

	/**
	 * Returns the number of o auth applications where consumerKey = &#63;.
	 *
	 * @param consumerKey the consumer key
	 * @return the number of matching o auth applications
	 */
	public static int countByConsumerKey(String consumerKey) {
		return getPersistence().countByConsumerKey(consumerKey);
	}

	/**
	 * Returns all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching o auth applications
	 */
	public static List<OAuthApplication> findByC_LikeN(
		long companyId, String name) {

		return getPersistence().findByC_LikeN(companyId, name);
	}

	/**
	 * Returns a range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public static List<OAuthApplication> findByC_LikeN(
		long companyId, String name, int start, int end) {

		return getPersistence().findByC_LikeN(companyId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().findByC_LikeN(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LikeN(
			companyId, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByC_LikeN_First(
			long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByC_LikeN_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByC_LikeN_First(
		long companyId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByC_LikeN_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByC_LikeN_Last(
			long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByC_LikeN_Last(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByC_LikeN_Last(
		long companyId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByC_LikeN_Last(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] findByC_LikeN_PrevAndNext(
			long oAuthApplicationId, long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByC_LikeN_PrevAndNext(
			oAuthApplicationId, companyId, name, orderByComparator);
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByC_LikeN(
		long companyId, String name) {

		return getPersistence().filterFindByC_LikeN(companyId, name);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByC_LikeN(
		long companyId, String name, int start, int end) {

		return getPersistence().filterFindByC_LikeN(
			companyId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().filterFindByC_LikeN(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] filterFindByC_LikeN_PrevAndNext(
			long oAuthApplicationId, long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().filterFindByC_LikeN_PrevAndNext(
			oAuthApplicationId, companyId, name, orderByComparator);
	}

	/**
	 * Removes all the o auth applications where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public static void removeByC_LikeN(long companyId, String name) {
		getPersistence().removeByC_LikeN(companyId, name);
	}

	/**
	 * Returns the number of o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching o auth applications
	 */
	public static int countByC_LikeN(long companyId, String name) {
		return getPersistence().countByC_LikeN(companyId, name);
	}

	/**
	 * Returns the number of o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	public static int filterCountByC_LikeN(long companyId, String name) {
		return getPersistence().filterCountByC_LikeN(companyId, name);
	}

	/**
	 * Returns all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the matching o auth applications
	 */
	public static List<OAuthApplication> findByU_LikeN(
		long userId, String name) {

		return getPersistence().findByU_LikeN(userId, name);
	}

	/**
	 * Returns a range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	public static List<OAuthApplication> findByU_LikeN(
		long userId, String name, int start, int end) {

		return getPersistence().findByU_LikeN(userId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByU_LikeN(
		long userId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().findByU_LikeN(
			userId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	public static List<OAuthApplication> findByU_LikeN(
		long userId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_LikeN(
			userId, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByU_LikeN_First(
			long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByU_LikeN_First(
			userId, name, orderByComparator);
	}

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByU_LikeN_First(
		long userId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByU_LikeN_First(
			userId, name, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	public static OAuthApplication findByU_LikeN_Last(
			long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByU_LikeN_Last(
			userId, name, orderByComparator);
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	public static OAuthApplication fetchByU_LikeN_Last(
		long userId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().fetchByU_LikeN_Last(
			userId, name, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] findByU_LikeN_PrevAndNext(
			long oAuthApplicationId, long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByU_LikeN_PrevAndNext(
			oAuthApplicationId, userId, name, orderByComparator);
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByU_LikeN(
		long userId, String name) {

		return getPersistence().filterFindByU_LikeN(userId, name);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByU_LikeN(
		long userId, String name, int start, int end) {

		return getPersistence().filterFindByU_LikeN(userId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	public static List<OAuthApplication> filterFindByU_LikeN(
		long userId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().filterFindByU_LikeN(
			userId, name, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication[] filterFindByU_LikeN_PrevAndNext(
			long oAuthApplicationId, long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().filterFindByU_LikeN_PrevAndNext(
			oAuthApplicationId, userId, name, orderByComparator);
	}

	/**
	 * Removes all the o auth applications where userId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param name the name
	 */
	public static void removeByU_LikeN(long userId, String name) {
		getPersistence().removeByU_LikeN(userId, name);
	}

	/**
	 * Returns the number of o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the number of matching o auth applications
	 */
	public static int countByU_LikeN(long userId, String name) {
		return getPersistence().countByU_LikeN(userId, name);
	}

	/**
	 * Returns the number of o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	public static int filterCountByU_LikeN(long userId, String name) {
		return getPersistence().filterCountByU_LikeN(userId, name);
	}

	/**
	 * Caches the o auth application in the entity cache if it is enabled.
	 *
	 * @param oAuthApplication the o auth application
	 */
	public static void cacheResult(OAuthApplication oAuthApplication) {
		getPersistence().cacheResult(oAuthApplication);
	}

	/**
	 * Caches the o auth applications in the entity cache if it is enabled.
	 *
	 * @param oAuthApplications the o auth applications
	 */
	public static void cacheResult(List<OAuthApplication> oAuthApplications) {
		getPersistence().cacheResult(oAuthApplications);
	}

	/**
	 * Creates a new o auth application with the primary key. Does not add the o auth application to the database.
	 *
	 * @param oAuthApplicationId the primary key for the new o auth application
	 * @return the new o auth application
	 */
	public static OAuthApplication create(long oAuthApplicationId) {
		return getPersistence().create(oAuthApplicationId);
	}

	/**
	 * Removes the o auth application with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application that was removed
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication remove(long oAuthApplicationId)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().remove(oAuthApplicationId);
	}

	public static OAuthApplication updateImpl(
		OAuthApplication oAuthApplication) {

		return getPersistence().updateImpl(oAuthApplication);
	}

	/**
	 * Returns the o auth application with the primary key or throws a <code>NoSuchApplicationException</code> if it could not be found.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication findByPrimaryKey(long oAuthApplicationId)
		throws com.liferay.oauth.exception.NoSuchApplicationException {

		return getPersistence().findByPrimaryKey(oAuthApplicationId);
	}

	/**
	 * Returns the o auth application with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application, or <code>null</code> if a o auth application with the primary key could not be found
	 */
	public static OAuthApplication fetchByPrimaryKey(long oAuthApplicationId) {
		return getPersistence().fetchByPrimaryKey(oAuthApplicationId);
	}

	/**
	 * Returns all the o auth applications.
	 *
	 * @return the o auth applications
	 */
	public static List<OAuthApplication> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of o auth applications
	 */
	public static List<OAuthApplication> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth applications
	 */
	public static List<OAuthApplication> findAll(
		int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth applications
	 */
	public static List<OAuthApplication> findAll(
		int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the o auth applications from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of o auth applications.
	 *
	 * @return the number of o auth applications
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OAuthApplicationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<OAuthApplicationPersistence, OAuthApplicationPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			OAuthApplicationPersistence.class);

		ServiceTracker<OAuthApplicationPersistence, OAuthApplicationPersistence>
			serviceTracker =
				new ServiceTracker
					<OAuthApplicationPersistence, OAuthApplicationPersistence>(
						bundle.getBundleContext(),
						OAuthApplicationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}