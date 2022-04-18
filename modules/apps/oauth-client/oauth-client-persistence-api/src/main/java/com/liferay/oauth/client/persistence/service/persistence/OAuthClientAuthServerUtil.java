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

package com.liferay.oauth.client.persistence.service.persistence;

import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the o auth client auth server service. This utility wraps <code>com.liferay.oauth.client.persistence.service.persistence.impl.OAuthClientAuthServerPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientAuthServerPersistence
 * @generated
 */
public class OAuthClientAuthServerUtil {

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
	public static void clearCache(OAuthClientAuthServer oAuthClientAuthServer) {
		getPersistence().clearCache(oAuthClientAuthServer);
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
	public static Map<Serializable, OAuthClientAuthServer> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OAuthClientAuthServer> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuthClientAuthServer> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuthClientAuthServer> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuthClientAuthServer update(
		OAuthClientAuthServer oAuthClientAuthServer) {

		return getPersistence().update(oAuthClientAuthServer);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuthClientAuthServer update(
		OAuthClientAuthServer oAuthClientAuthServer,
		ServiceContext serviceContext) {

		return getPersistence().update(oAuthClientAuthServer, serviceContext);
	}

	/**
	 * Returns all the o auth client auth servers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByCompanyId_First(
		long companyId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer[] findByCompanyId_PrevAndNext(
			long oAuthClientAuthServerId, long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByCompanyId_PrevAndNext(
			oAuthClientAuthServerId, companyId, orderByComparator);
	}

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer[] filterFindByCompanyId_PrevAndNext(
			long oAuthClientAuthServerId, long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			oAuthClientAuthServerId, companyId, orderByComparator);
	}

	/**
	 * Removes all the o auth client auth servers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client auth servers
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the o auth client auth servers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer findByUserId_First(
			long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByUserId_First(
		long userId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer findByUserId_Last(
			long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByUserId_Last(
		long userId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer[] findByUserId_PrevAndNext(
			long oAuthClientAuthServerId, long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByUserId_PrevAndNext(
			oAuthClientAuthServerId, userId, orderByComparator);
	}

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByUserId(long userId) {
		return getPersistence().filterFindByUserId(userId);
	}

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByUserId(
		long userId, int start, int end) {

		return getPersistence().filterFindByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().filterFindByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer[] filterFindByUserId_PrevAndNext(
			long oAuthClientAuthServerId, long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().filterFindByUserId_PrevAndNext(
			oAuthClientAuthServerId, userId, orderByComparator);
	}

	/**
	 * Removes all the o auth client auth servers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of o auth client auth servers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client auth servers
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	public static int filterCountByUserId(long userId) {
		return getPersistence().filterCountByUserId(userId);
	}

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or throws a <code>NoSuchAuthServerException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer findByC_I(long companyId, String issuer)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByC_I(companyId, issuer);
	}

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByC_I(
		long companyId, String issuer) {

		return getPersistence().fetchByC_I(companyId, issuer);
	}

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByC_I(
		long companyId, String issuer, boolean useFinderCache) {

		return getPersistence().fetchByC_I(companyId, issuer, useFinderCache);
	}

	/**
	 * Removes the o auth client auth server where companyId = &#63; and issuer = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the o auth client auth server that was removed
	 */
	public static OAuthClientAuthServer removeByC_I(
			long companyId, String issuer)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().removeByC_I(companyId, issuer);
	}

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63; and issuer = &#63;.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the number of matching o auth client auth servers
	 */
	public static int countByC_I(long companyId, String issuer) {
		return getPersistence().countByC_I(companyId, issuer);
	}

	/**
	 * Returns all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByC_T(
		long companyId, String type) {

		return getPersistence().findByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer findByC_T_First(
			long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer findByC_T_Last(
			long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	public static OAuthClientAuthServer fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer[] findByC_T_PrevAndNext(
			long oAuthClientAuthServerId, long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByC_T_PrevAndNext(
			oAuthClientAuthServerId, companyId, type, orderByComparator);
	}

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type) {

		return getPersistence().filterFindByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().filterFindByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	public static List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().filterFindByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer[] filterFindByC_T_PrevAndNext(
			long oAuthClientAuthServerId, long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().filterFindByC_T_PrevAndNext(
			oAuthClientAuthServerId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the o auth client auth servers where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_T(long companyId, String type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching o auth client auth servers
	 */
	public static int countByC_T(long companyId, String type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	public static int filterCountByC_T(long companyId, String type) {
		return getPersistence().filterCountByC_T(companyId, type);
	}

	/**
	 * Caches the o auth client auth server in the entity cache if it is enabled.
	 *
	 * @param oAuthClientAuthServer the o auth client auth server
	 */
	public static void cacheResult(
		OAuthClientAuthServer oAuthClientAuthServer) {

		getPersistence().cacheResult(oAuthClientAuthServer);
	}

	/**
	 * Caches the o auth client auth servers in the entity cache if it is enabled.
	 *
	 * @param oAuthClientAuthServers the o auth client auth servers
	 */
	public static void cacheResult(
		List<OAuthClientAuthServer> oAuthClientAuthServers) {

		getPersistence().cacheResult(oAuthClientAuthServers);
	}

	/**
	 * Creates a new o auth client auth server with the primary key. Does not add the o auth client auth server to the database.
	 *
	 * @param oAuthClientAuthServerId the primary key for the new o auth client auth server
	 * @return the new o auth client auth server
	 */
	public static OAuthClientAuthServer create(long oAuthClientAuthServerId) {
		return getPersistence().create(oAuthClientAuthServerId);
	}

	/**
	 * Removes the o auth client auth server with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server that was removed
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer remove(long oAuthClientAuthServerId)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().remove(oAuthClientAuthServerId);
	}

	public static OAuthClientAuthServer updateImpl(
		OAuthClientAuthServer oAuthClientAuthServer) {

		return getPersistence().updateImpl(oAuthClientAuthServer);
	}

	/**
	 * Returns the o auth client auth server with the primary key or throws a <code>NoSuchAuthServerException</code> if it could not be found.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer findByPrimaryKey(
			long oAuthClientAuthServerId)
		throws com.liferay.oauth.client.persistence.exception.
			NoSuchAuthServerException {

		return getPersistence().findByPrimaryKey(oAuthClientAuthServerId);
	}

	/**
	 * Returns the o auth client auth server with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server, or <code>null</code> if a o auth client auth server with the primary key could not be found
	 */
	public static OAuthClientAuthServer fetchByPrimaryKey(
		long oAuthClientAuthServerId) {

		return getPersistence().fetchByPrimaryKey(oAuthClientAuthServerId);
	}

	/**
	 * Returns all the o auth client auth servers.
	 *
	 * @return the o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findAll(
		int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth client auth servers
	 */
	public static List<OAuthClientAuthServer> findAll(
		int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the o auth client auth servers from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of o auth client auth servers.
	 *
	 * @return the number of o auth client auth servers
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OAuthClientAuthServerPersistence getPersistence() {
		return _persistence;
	}

	private static volatile OAuthClientAuthServerPersistence _persistence;

}