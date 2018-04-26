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

package com.liferay.saml.persistence.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;

import java.util.Date;

/**
 * The persistence interface for the saml idp sso session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see com.liferay.saml.persistence.service.persistence.impl.SamlIdpSsoSessionPersistenceImpl
 * @see SamlIdpSsoSessionUtil
 * @generated
 */
@ProviderType
public interface SamlIdpSsoSessionPersistence extends BasePersistence<SamlIdpSsoSession> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlIdpSsoSessionUtil} to access the saml idp sso session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @return the matching saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findByCreateDate(Date createDate);

	/**
	* Returns a range of all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @return the range of matching saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findByCreateDate(Date createDate,
		int start, int end);

	/**
	* Returns an ordered range of all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findByCreateDate(Date createDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator);

	/**
	* Returns an ordered range of all the saml idp sso sessions where createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findByCreateDate(Date createDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	*/
	public SamlIdpSsoSession findByCreateDate_First(Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws NoSuchIdpSsoSessionException;

	/**
	* Returns the first saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public SamlIdpSsoSession fetchByCreateDate_First(Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator);

	/**
	* Returns the last saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	*/
	public SamlIdpSsoSession findByCreateDate_Last(Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws NoSuchIdpSsoSessionException;

	/**
	* Returns the last saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public SamlIdpSsoSession fetchByCreateDate_Last(Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator);

	/**
	* Returns the saml idp sso sessions before and after the current saml idp sso session in the ordered set where createDate &lt; &#63;.
	*
	* @param samlIdpSsoSessionId the primary key of the current saml idp sso session
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	*/
	public SamlIdpSsoSession[] findByCreateDate_PrevAndNext(
		long samlIdpSsoSessionId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws NoSuchIdpSsoSessionException;

	/**
	* Removes all the saml idp sso sessions where createDate &lt; &#63; from the database.
	*
	* @param createDate the create date
	*/
	public void removeByCreateDate(Date createDate);

	/**
	* Returns the number of saml idp sso sessions where createDate &lt; &#63;.
	*
	* @param createDate the create date
	* @return the number of matching saml idp sso sessions
	*/
	public int countByCreateDate(Date createDate);

	/**
	* Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or throws a {@link NoSuchIdpSsoSessionException} if it could not be found.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the matching saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	*/
	public SamlIdpSsoSession findBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey) throws NoSuchIdpSsoSessionException;

	/**
	* Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public SamlIdpSsoSession fetchBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey);

	/**
	* Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	*/
	public SamlIdpSsoSession fetchBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey, boolean retrieveFromCache);

	/**
	* Removes the saml idp sso session where samlIdpSsoSessionKey = &#63; from the database.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the saml idp sso session that was removed
	*/
	public SamlIdpSsoSession removeBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey) throws NoSuchIdpSsoSessionException;

	/**
	* Returns the number of saml idp sso sessions where samlIdpSsoSessionKey = &#63;.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key
	* @return the number of matching saml idp sso sessions
	*/
	public int countBySamlIdpSsoSessionKey(String samlIdpSsoSessionKey);

	/**
	* Caches the saml idp sso session in the entity cache if it is enabled.
	*
	* @param samlIdpSsoSession the saml idp sso session
	*/
	public void cacheResult(SamlIdpSsoSession samlIdpSsoSession);

	/**
	* Caches the saml idp sso sessions in the entity cache if it is enabled.
	*
	* @param samlIdpSsoSessions the saml idp sso sessions
	*/
	public void cacheResult(
		java.util.List<SamlIdpSsoSession> samlIdpSsoSessions);

	/**
	* Creates a new saml idp sso session with the primary key. Does not add the saml idp sso session to the database.
	*
	* @param samlIdpSsoSessionId the primary key for the new saml idp sso session
	* @return the new saml idp sso session
	*/
	public SamlIdpSsoSession create(long samlIdpSsoSessionId);

	/**
	* Removes the saml idp sso session with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session that was removed
	* @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	*/
	public SamlIdpSsoSession remove(long samlIdpSsoSessionId)
		throws NoSuchIdpSsoSessionException;

	public SamlIdpSsoSession updateImpl(SamlIdpSsoSession samlIdpSsoSession);

	/**
	* Returns the saml idp sso session with the primary key or throws a {@link NoSuchIdpSsoSessionException} if it could not be found.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session
	* @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	*/
	public SamlIdpSsoSession findByPrimaryKey(long samlIdpSsoSessionId)
		throws NoSuchIdpSsoSessionException;

	/**
	* Returns the saml idp sso session with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session, or <code>null</code> if a saml idp sso session with the primary key could not be found
	*/
	public SamlIdpSsoSession fetchByPrimaryKey(long samlIdpSsoSessionId);

	@Override
	public java.util.Map<java.io.Serializable, SamlIdpSsoSession> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the saml idp sso sessions.
	*
	* @return the saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findAll();

	/**
	* Returns a range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @return the range of saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findAll(int start, int end);

	/**
	* Returns an ordered range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator);

	/**
	* Returns an ordered range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of saml idp sso sessions
	*/
	public java.util.List<SamlIdpSsoSession> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlIdpSsoSession> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the saml idp sso sessions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of saml idp sso sessions.
	*
	* @return the number of saml idp sso sessions
	*/
	public int countAll();
}