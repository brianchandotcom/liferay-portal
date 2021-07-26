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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.security.sso.openid.connect.persistence.exception.NoSuchSessionException;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the open ID connect session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @see OpenIdConnectSessionUtil
 * @generated
 */
@ProviderType
public interface OpenIdConnectSessionPersistence
	extends BasePersistence<OpenIdConnectSession> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OpenIdConnectSessionUtil} to access the open ID connect session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the open ID connect session in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSession the open ID connect session
	 */
	public void cacheResult(OpenIdConnectSession openIdConnectSession);

	/**
	 * Caches the open ID connect sessions in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSessions the open ID connect sessions
	 */
	public void cacheResult(
		java.util.List<OpenIdConnectSession> openIdConnectSessions);

	/**
	 * Creates a new open ID connect session with the primary key. Does not add the open ID connect session to the database.
	 *
	 * @param openIdConnectSessionId the primary key for the new open ID connect session
	 * @return the new open ID connect session
	 */
	public OpenIdConnectSession create(long openIdConnectSessionId);

	/**
	 * Removes the open ID connect session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session that was removed
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	public OpenIdConnectSession remove(long openIdConnectSessionId)
		throws NoSuchSessionException;

	public OpenIdConnectSession updateImpl(
		OpenIdConnectSession openIdConnectSession);

	/**
	 * Returns the open ID connect session with the primary key or throws a <code>NoSuchSessionException</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	public OpenIdConnectSession findByPrimaryKey(long openIdConnectSessionId)
		throws NoSuchSessionException;

	/**
	 * Returns the open ID connect session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session, or <code>null</code> if a open ID connect session with the primary key could not be found
	 */
	public OpenIdConnectSession fetchByPrimaryKey(long openIdConnectSessionId);

	/**
	 * Returns all the open ID connect sessions.
	 *
	 * @return the open ID connect sessions
	 */
	public java.util.List<OpenIdConnectSession> findAll();

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
	public java.util.List<OpenIdConnectSession> findAll(int start, int end);

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
	public java.util.List<OpenIdConnectSession> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OpenIdConnectSession>
			orderByComparator);

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
	public java.util.List<OpenIdConnectSession> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OpenIdConnectSession>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the open ID connect sessions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of open ID connect sessions.
	 *
	 * @return the number of open ID connect sessions
	 */
	public int countAll();

}