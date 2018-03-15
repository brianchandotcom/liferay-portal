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

package com.liferay.oauth2.provider.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the o auth2 scope grant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2ScopeGrantPersistenceImpl
 * @see OAuth2ScopeGrantUtil
 * @generated
 */
@ProviderType
public interface OAuth2ScopeGrantPersistence extends BasePersistence<OAuth2ScopeGrant> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2ScopeGrantUtil} to access the o auth2 scope grant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the o auth2 scope grants where oAuth2TokenId = &#63;.
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @return the matching o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findByToken(long oAuth2TokenId);

	/**
	* Returns a range of all the o auth2 scope grants where oAuth2TokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @return the range of matching o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findByToken(long oAuth2TokenId,
		int start, int end);

	/**
	* Returns an ordered range of all the o auth2 scope grants where oAuth2TokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findByToken(long oAuth2TokenId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 scope grants where oAuth2TokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findByToken(long oAuth2TokenId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first o auth2 scope grant in the ordered set where oAuth2TokenId = &#63;.
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	*/
	public OAuth2ScopeGrant findByToken_First(long oAuth2TokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	* Returns the first o auth2 scope grant in the ordered set where oAuth2TokenId = &#63;.
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public OAuth2ScopeGrant fetchByToken_First(long oAuth2TokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator);

	/**
	* Returns the last o auth2 scope grant in the ordered set where oAuth2TokenId = &#63;.
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	*/
	public OAuth2ScopeGrant findByToken_Last(long oAuth2TokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	* Returns the last o auth2 scope grant in the ordered set where oAuth2TokenId = &#63;.
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public OAuth2ScopeGrant fetchByToken_Last(long oAuth2TokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator);

	/**
	* Returns the o auth2 scope grants before and after the current o auth2 scope grant in the ordered set where oAuth2TokenId = &#63;.
	*
	* @param oAuth2ScopeGrantPK the primary key of the current o auth2 scope grant
	* @param oAuth2TokenId the o auth2 token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	*/
	public OAuth2ScopeGrant[] findByToken_PrevAndNext(
		OAuth2ScopeGrantPK oAuth2ScopeGrantPK, long oAuth2TokenId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	* Removes all the o auth2 scope grants where oAuth2TokenId = &#63; from the database.
	*
	* @param oAuth2TokenId the o auth2 token ID
	*/
	public void removeByToken(long oAuth2TokenId);

	/**
	* Returns the number of o auth2 scope grants where oAuth2TokenId = &#63;.
	*
	* @param oAuth2TokenId the o auth2 token ID
	* @return the number of matching o auth2 scope grants
	*/
	public int countByToken(long oAuth2TokenId);

	/**
	* Returns the o auth2 scope grant where applicationName = &#63; and bundleSymbolicName = &#63; and companyId = &#63; and oAuth2ScopeName = &#63; and oAuth2TokenId = &#63; or throws a {@link NoSuchOAuth2ScopeGrantException} if it could not be found.
	*
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param companyId the company ID
	* @param oAuth2ScopeName the o auth2 scope name
	* @param oAuth2TokenId the o auth2 token ID
	* @return the matching o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	*/
	public OAuth2ScopeGrant findByA_BSN_C_O_T(
		java.lang.String applicationName, java.lang.String bundleSymbolicName,
		long companyId, java.lang.String oAuth2ScopeName, long oAuth2TokenId)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	* Returns the o auth2 scope grant where applicationName = &#63; and bundleSymbolicName = &#63; and companyId = &#63; and oAuth2ScopeName = &#63; and oAuth2TokenId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param companyId the company ID
	* @param oAuth2ScopeName the o auth2 scope name
	* @param oAuth2TokenId the o auth2 token ID
	* @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public OAuth2ScopeGrant fetchByA_BSN_C_O_T(
		java.lang.String applicationName, java.lang.String bundleSymbolicName,
		long companyId, java.lang.String oAuth2ScopeName, long oAuth2TokenId);

	/**
	* Returns the o auth2 scope grant where applicationName = &#63; and bundleSymbolicName = &#63; and companyId = &#63; and oAuth2ScopeName = &#63; and oAuth2TokenId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param companyId the company ID
	* @param oAuth2ScopeName the o auth2 scope name
	* @param oAuth2TokenId the o auth2 token ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public OAuth2ScopeGrant fetchByA_BSN_C_O_T(
		java.lang.String applicationName, java.lang.String bundleSymbolicName,
		long companyId, java.lang.String oAuth2ScopeName, long oAuth2TokenId,
		boolean retrieveFromCache);

	/**
	* Removes the o auth2 scope grant where applicationName = &#63; and bundleSymbolicName = &#63; and companyId = &#63; and oAuth2ScopeName = &#63; and oAuth2TokenId = &#63; from the database.
	*
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param companyId the company ID
	* @param oAuth2ScopeName the o auth2 scope name
	* @param oAuth2TokenId the o auth2 token ID
	* @return the o auth2 scope grant that was removed
	*/
	public OAuth2ScopeGrant removeByA_BSN_C_O_T(
		java.lang.String applicationName, java.lang.String bundleSymbolicName,
		long companyId, java.lang.String oAuth2ScopeName, long oAuth2TokenId)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	* Returns the number of o auth2 scope grants where applicationName = &#63; and bundleSymbolicName = &#63; and companyId = &#63; and oAuth2ScopeName = &#63; and oAuth2TokenId = &#63;.
	*
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param companyId the company ID
	* @param oAuth2ScopeName the o auth2 scope name
	* @param oAuth2TokenId the o auth2 token ID
	* @return the number of matching o auth2 scope grants
	*/
	public int countByA_BSN_C_O_T(java.lang.String applicationName,
		java.lang.String bundleSymbolicName, long companyId,
		java.lang.String oAuth2ScopeName, long oAuth2TokenId);

	/**
	* Caches the o auth2 scope grant in the entity cache if it is enabled.
	*
	* @param oAuth2ScopeGrant the o auth2 scope grant
	*/
	public void cacheResult(OAuth2ScopeGrant oAuth2ScopeGrant);

	/**
	* Caches the o auth2 scope grants in the entity cache if it is enabled.
	*
	* @param oAuth2ScopeGrants the o auth2 scope grants
	*/
	public void cacheResult(java.util.List<OAuth2ScopeGrant> oAuth2ScopeGrants);

	/**
	* Creates a new o auth2 scope grant with the primary key. Does not add the o auth2 scope grant to the database.
	*
	* @param oAuth2ScopeGrantPK the primary key for the new o auth2 scope grant
	* @return the new o auth2 scope grant
	*/
	public OAuth2ScopeGrant create(OAuth2ScopeGrantPK oAuth2ScopeGrantPK);

	/**
	* Removes the o auth2 scope grant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	* @return the o auth2 scope grant that was removed
	* @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	*/
	public OAuth2ScopeGrant remove(OAuth2ScopeGrantPK oAuth2ScopeGrantPK)
		throws NoSuchOAuth2ScopeGrantException;

	public OAuth2ScopeGrant updateImpl(OAuth2ScopeGrant oAuth2ScopeGrant);

	/**
	* Returns the o auth2 scope grant with the primary key or throws a {@link NoSuchOAuth2ScopeGrantException} if it could not be found.
	*
	* @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	* @return the o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	*/
	public OAuth2ScopeGrant findByPrimaryKey(
		OAuth2ScopeGrantPK oAuth2ScopeGrantPK)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	* Returns the o auth2 scope grant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	* @return the o auth2 scope grant, or <code>null</code> if a o auth2 scope grant with the primary key could not be found
	*/
	public OAuth2ScopeGrant fetchByPrimaryKey(
		OAuth2ScopeGrantPK oAuth2ScopeGrantPK);

	@Override
	public java.util.Map<java.io.Serializable, OAuth2ScopeGrant> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the o auth2 scope grants.
	*
	* @return the o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findAll();

	/**
	* Returns a range of all the o auth2 scope grants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @return the range of o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findAll(int start, int end);

	/**
	* Returns an ordered range of all the o auth2 scope grants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator);

	/**
	* Returns an ordered range of all the o auth2 scope grants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of o auth2 scope grants
	*/
	public java.util.List<OAuth2ScopeGrant> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the o auth2 scope grants from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of o auth2 scope grants.
	*
	* @return the number of o auth2 scope grants
	*/
	public int countAll();

	public java.util.Set<java.lang.String> getCompoundPKColumnNames();
}