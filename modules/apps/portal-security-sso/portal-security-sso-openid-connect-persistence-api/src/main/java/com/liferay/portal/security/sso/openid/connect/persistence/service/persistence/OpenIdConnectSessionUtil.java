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

package com.liferay.portal.security.sso.openid.connect.persistence.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the open ID connect session service. This utility wraps <code>com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.impl.OpenIdConnectSessionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see OpenIdConnectSessionPersistence
 * @generated
 */
public class OpenIdConnectSessionUtil {

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
	public static void clearCache(OpenIdConnectSession openIdConnectSession) {
		getPersistence().clearCache(openIdConnectSession);
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
	public static Map<Serializable, OpenIdConnectSession> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<OpenIdConnectSession> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OpenIdConnectSession> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OpenIdConnectSession> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OpenIdConnectSession update(
		OpenIdConnectSession openIdConnectSession) {

		return getPersistence().update(openIdConnectSession);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OpenIdConnectSession update(
		OpenIdConnectSession openIdConnectSession,
		ServiceContext serviceContext) {

		return getPersistence().update(openIdConnectSession, serviceContext);
	}

	/**
	 * Returns all the open ID connect sessions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the open ID connect sessions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @return the range of matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession findByUserId_First(
			long userId,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession fetchByUserId_First(
		long userId,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession findByUserId_Last(
			long userId,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession fetchByUserId_Last(
		long userId,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the open ID connect sessions before and after the current open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param openIdConnectSessionId the primary key of the current open ID connect session
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	public static OpenIdConnectSession[] findByUserId_PrevAndNext(
			long openIdConnectSessionId, long userId,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByUserId_PrevAndNext(
			openIdConnectSessionId, userId, orderByComparator);
	}

	/**
	 * Removes all the open ID connect sessions where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of open ID connect sessions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching open ID connect sessions
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @return the matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid) {

		return getPersistence().findByConfigurationPid(configurationPid);
	}

	/**
	 * Returns a range of all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param configurationPid the configuration pid
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @return the range of matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid, int start, int end) {

		return getPersistence().findByConfigurationPid(
			configurationPid, start, end);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param configurationPid the configuration pid
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().findByConfigurationPid(
			configurationPid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param configurationPid the configuration pid
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByConfigurationPid(
			configurationPid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession findByConfigurationPid_First(
			String configurationPid,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByConfigurationPid_First(
			configurationPid, orderByComparator);
	}

	/**
	 * Returns the first open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession fetchByConfigurationPid_First(
		String configurationPid,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().fetchByConfigurationPid_First(
			configurationPid, orderByComparator);
	}

	/**
	 * Returns the last open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession findByConfigurationPid_Last(
			String configurationPid,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByConfigurationPid_Last(
			configurationPid, orderByComparator);
	}

	/**
	 * Returns the last open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession fetchByConfigurationPid_Last(
		String configurationPid,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().fetchByConfigurationPid_Last(
			configurationPid, orderByComparator);
	}

	/**
	 * Returns the open ID connect sessions before and after the current open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param openIdConnectSessionId the primary key of the current open ID connect session
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	public static OpenIdConnectSession[] findByConfigurationPid_PrevAndNext(
			long openIdConnectSessionId, String configurationPid,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByConfigurationPid_PrevAndNext(
			openIdConnectSessionId, configurationPid, orderByComparator);
	}

	/**
	 * Removes all the open ID connect sessions where configurationPid = &#63; from the database.
	 *
	 * @param configurationPid the configuration pid
	 */
	public static void removeByConfigurationPid(String configurationPid) {
		getPersistence().removeByConfigurationPid(configurationPid);
	}

	/**
	 * Returns the number of open ID connect sessions where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @return the number of matching open ID connect sessions
	 */
	public static int countByConfigurationPid(String configurationPid) {
		return getPersistence().countByConfigurationPid(configurationPid);
	}

	/**
	 * Returns the open ID connect session where userId = &#63; and configurationPid = &#63; or throws a <code>NoSuchSessionException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession findByU_C(
			long userId, String configurationPid)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByU_C(userId, configurationPid);
	}

	/**
	 * Returns the open ID connect session where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession fetchByU_C(
		long userId, String configurationPid) {

		return getPersistence().fetchByU_C(userId, configurationPid);
	}

	/**
	 * Returns the open ID connect session where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	public static OpenIdConnectSession fetchByU_C(
		long userId, String configurationPid, boolean useFinderCache) {

		return getPersistence().fetchByU_C(
			userId, configurationPid, useFinderCache);
	}

	/**
	 * Removes the open ID connect session where userId = &#63; and configurationPid = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the open ID connect session that was removed
	 */
	public static OpenIdConnectSession removeByU_C(
			long userId, String configurationPid)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().removeByU_C(userId, configurationPid);
	}

	/**
	 * Returns the number of open ID connect sessions where userId = &#63; and configurationPid = &#63;.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the number of matching open ID connect sessions
	 */
	public static int countByU_C(long userId, String configurationPid) {
		return getPersistence().countByU_C(userId, configurationPid);
	}

	/**
	 * Caches the open ID connect session in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSession the open ID connect session
	 */
	public static void cacheResult(OpenIdConnectSession openIdConnectSession) {
		getPersistence().cacheResult(openIdConnectSession);
	}

	/**
	 * Caches the open ID connect sessions in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSessions the open ID connect sessions
	 */
	public static void cacheResult(
		List<OpenIdConnectSession> openIdConnectSessions) {

		getPersistence().cacheResult(openIdConnectSessions);
	}

	/**
	 * Creates a new open ID connect session with the primary key. Does not add the open ID connect session to the database.
	 *
	 * @param openIdConnectSessionId the primary key for the new open ID connect session
	 * @return the new open ID connect session
	 */
	public static OpenIdConnectSession create(long openIdConnectSessionId) {
		return getPersistence().create(openIdConnectSessionId);
	}

	/**
	 * Removes the open ID connect session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session that was removed
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	public static OpenIdConnectSession remove(long openIdConnectSessionId)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().remove(openIdConnectSessionId);
	}

	public static OpenIdConnectSession updateImpl(
		OpenIdConnectSession openIdConnectSession) {

		return getPersistence().updateImpl(openIdConnectSession);
	}

	/**
	 * Returns the open ID connect session with the primary key or throws a <code>NoSuchSessionException</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	public static OpenIdConnectSession findByPrimaryKey(
			long openIdConnectSessionId)
		throws com.liferay.portal.security.sso.openid.connect.persistence.
			exception.NoSuchSessionException {

		return getPersistence().findByPrimaryKey(openIdConnectSessionId);
	}

	/**
	 * Returns the open ID connect session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session, or <code>null</code> if a open ID connect session with the primary key could not be found
	 */
	public static OpenIdConnectSession fetchByPrimaryKey(
		long openIdConnectSessionId) {

		return getPersistence().fetchByPrimaryKey(openIdConnectSessionId);
	}

	/**
	 * Returns all the open ID connect sessions.
	 *
	 * @return the open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @return the range of open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findAll(
		int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of open ID connect sessions
	 */
	public static List<OpenIdConnectSession> findAll(
		int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the open ID connect sessions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of open ID connect sessions.
	 *
	 * @return the number of open ID connect sessions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OpenIdConnectSessionPersistence getPersistence() {
		return _persistence;
	}

	private static volatile OpenIdConnectSessionPersistence _persistence;

}