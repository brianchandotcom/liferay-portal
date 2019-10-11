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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the o auth2 scope grant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrantUtil
 * @generated
 */
@ProviderType
public interface OAuth2ScopeGrantPersistence
	extends BasePersistence<OAuth2ScopeGrant> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2ScopeGrantUtil} to access the o auth2 scope grant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @return the matching o auth2 scope grants
	 */
	public java.util.List<OAuth2ScopeGrant>
		findByOAuth2ApplicationScopeAliasesId(
			long oAuth2ApplicationScopeAliasesId);

	/**
	 * Returns a range of all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @return the range of matching o auth2 scope grants
	 */
	public java.util.List<OAuth2ScopeGrant>
		findByOAuth2ApplicationScopeAliasesId(
			long oAuth2ApplicationScopeAliasesId, int start, int end);

	/**
	 * Returns an ordered range of all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 scope grants
	 */
	public java.util.List<OAuth2ScopeGrant>
		findByOAuth2ApplicationScopeAliasesId(
			long oAuth2ApplicationScopeAliasesId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
				orderByComparator);

	/**
	 * Returns an ordered range of all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 scope grants
	 */
	public java.util.List<OAuth2ScopeGrant>
		findByOAuth2ApplicationScopeAliasesId(
			long oAuth2ApplicationScopeAliasesId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
				orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	 */
	public OAuth2ScopeGrant findByOAuth2ApplicationScopeAliasesId_First(
			long oAuth2ApplicationScopeAliasesId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
				orderByComparator)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	 * Returns the first o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	public OAuth2ScopeGrant fetchByOAuth2ApplicationScopeAliasesId_First(
		long oAuth2ApplicationScopeAliasesId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
			orderByComparator);

	/**
	 * Returns the last o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	 */
	public OAuth2ScopeGrant findByOAuth2ApplicationScopeAliasesId_Last(
			long oAuth2ApplicationScopeAliasesId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
				orderByComparator)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	 * Returns the last o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	public OAuth2ScopeGrant fetchByOAuth2ApplicationScopeAliasesId_Last(
		long oAuth2ApplicationScopeAliasesId,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
			orderByComparator);

	/**
	 * Returns the o auth2 scope grants before and after the current o auth2 scope grant in the ordered set where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the current o auth2 scope grant
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	public OAuth2ScopeGrant[] findByOAuth2ApplicationScopeAliasesId_PrevAndNext(
			long oAuth2ScopeGrantId, long oAuth2ApplicationScopeAliasesId,
			com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
				orderByComparator)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	 * Removes all the o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63; from the database.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 */
	public void removeByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId);

	/**
	 * Returns the number of o auth2 scope grants where oAuth2ApplicationScopeAliasesId = &#63;.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @return the number of matching o auth2 scope grants
	 */
	public int countByOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId);

	/**
	 * Returns the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or throws a <code>NoSuchOAuth2ScopeGrantException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the matching o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	 */
	public OAuth2ScopeGrant findByC_O_A_B_S(
			long companyId, long oAuth2ApplicationScopeAliasesId,
			String applicationName, String bundleSymbolicName, String scope)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	 * Returns the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	public OAuth2ScopeGrant fetchByC_O_A_B_S(
		long companyId, long oAuth2ApplicationScopeAliasesId,
		String applicationName, String bundleSymbolicName, String scope);

	/**
	 * Returns the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	 */
	public OAuth2ScopeGrant fetchByC_O_A_B_S(
		long companyId, long oAuth2ApplicationScopeAliasesId,
		String applicationName, String bundleSymbolicName, String scope,
		boolean useFinderCache);

	/**
	 * Removes the o auth2 scope grant where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the o auth2 scope grant that was removed
	 */
	public OAuth2ScopeGrant removeByC_O_A_B_S(
			long companyId, long oAuth2ApplicationScopeAliasesId,
			String applicationName, String bundleSymbolicName, String scope)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	 * Returns the number of o auth2 scope grants where companyId = &#63; and oAuth2ApplicationScopeAliasesId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID
	 * @param applicationName the application name
	 * @param bundleSymbolicName the bundle symbolic name
	 * @param scope the scope
	 * @return the number of matching o auth2 scope grants
	 */
	public int countByC_O_A_B_S(
		long companyId, long oAuth2ApplicationScopeAliasesId,
		String applicationName, String bundleSymbolicName, String scope);

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
	 * @param oAuth2ScopeGrantId the primary key for the new o auth2 scope grant
	 * @return the new o auth2 scope grant
	 */
	public OAuth2ScopeGrant create(long oAuth2ScopeGrantId);

	/**
	 * Removes the o auth2 scope grant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant that was removed
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	public OAuth2ScopeGrant remove(long oAuth2ScopeGrantId)
		throws NoSuchOAuth2ScopeGrantException;

	public OAuth2ScopeGrant updateImpl(OAuth2ScopeGrant oAuth2ScopeGrant);

	/**
	 * Returns the o auth2 scope grant with the primary key or throws a <code>NoSuchOAuth2ScopeGrantException</code> if it could not be found.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant
	 * @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	 */
	public OAuth2ScopeGrant findByPrimaryKey(long oAuth2ScopeGrantId)
		throws NoSuchOAuth2ScopeGrantException;

	/**
	 * Returns the o auth2 scope grant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant, or <code>null</code> if a o auth2 scope grant with the primary key could not be found
	 */
	public OAuth2ScopeGrant fetchByPrimaryKey(long oAuth2ScopeGrantId);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 scope grants
	 */
	public java.util.List<OAuth2ScopeGrant> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
			orderByComparator);

	/**
	 * Returns an ordered range of all the o auth2 scope grants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth2 scope grants
	 */
	public java.util.List<OAuth2ScopeGrant> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
			orderByComparator,
		boolean useFinderCache);

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

	/**
	 * Returns the primaryKeys of o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @return long[] of the primaryKeys of o auth2 authorizations associated with the o auth2 scope grant
	 */
	public long[] getOAuth2AuthorizationPrimaryKeys(long pk);

	/**
	 * Returns all the o auth2 scope grant associated with the o auth2 authorization.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @return the o auth2 scope grants associated with the o auth2 authorization
	 */
	public java.util.List<OAuth2ScopeGrant>
		getOAuth2AuthorizationOAuth2ScopeGrants(long pk);

	/**
	 * Returns all the o auth2 scope grant associated with the o auth2 authorization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of o auth2 scope grants associated with the o auth2 authorization
	 */
	public java.util.List<OAuth2ScopeGrant>
		getOAuth2AuthorizationOAuth2ScopeGrants(long pk, int start, int end);

	/**
	 * Returns all the o auth2 scope grant associated with the o auth2 authorization.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 scope grants associated with the o auth2 authorization
	 */
	public java.util.List<OAuth2ScopeGrant>
		getOAuth2AuthorizationOAuth2ScopeGrants(
			long pk, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<OAuth2ScopeGrant>
				orderByComparator);

	/**
	 * Returns the number of o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @return the number of o auth2 authorizations associated with the o auth2 scope grant
	 */
	public int getOAuth2AuthorizationsSize(long pk);

	/**
	 * Returns <code>true</code> if the o auth2 authorization is associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPK the primary key of the o auth2 authorization
	 * @return <code>true</code> if the o auth2 authorization is associated with the o auth2 scope grant; <code>false</code> otherwise
	 */
	public boolean containsOAuth2Authorization(
		long pk, long oAuth2AuthorizationPK);

	/**
	 * Returns <code>true</code> if the o auth2 scope grant has any o auth2 authorizations associated with it.
	 *
	 * @param pk the primary key of the o auth2 scope grant to check for associations with o auth2 authorizations
	 * @return <code>true</code> if the o auth2 scope grant has any o auth2 authorizations associated with it; <code>false</code> otherwise
	 */
	public boolean containsOAuth2Authorizations(long pk);

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPK the primary key of the o auth2 authorization
	 */
	public void addOAuth2Authorization(long pk, long oAuth2AuthorizationPK);

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorization the o auth2 authorization
	 */
	public void addOAuth2Authorization(
		long pk,
		com.liferay.oauth2.provider.model.OAuth2Authorization
			oAuth2Authorization);

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPKs the primary keys of the o auth2 authorizations
	 */
	public void addOAuth2Authorizations(long pk, long[] oAuth2AuthorizationPKs);

	/**
	 * Adds an association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorizations the o auth2 authorizations
	 */
	public void addOAuth2Authorizations(
		long pk,
		java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization>
			oAuth2Authorizations);

	/**
	 * Clears all associations between the o auth2 scope grant and its o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant to clear the associated o auth2 authorizations from
	 */
	public void clearOAuth2Authorizations(long pk);

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPK the primary key of the o auth2 authorization
	 */
	public void removeOAuth2Authorization(long pk, long oAuth2AuthorizationPK);

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorization the o auth2 authorization
	 */
	public void removeOAuth2Authorization(
		long pk,
		com.liferay.oauth2.provider.model.OAuth2Authorization
			oAuth2Authorization);

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPKs the primary keys of the o auth2 authorizations
	 */
	public void removeOAuth2Authorizations(
		long pk, long[] oAuth2AuthorizationPKs);

	/**
	 * Removes the association between the o auth2 scope grant and the o auth2 authorizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorizations the o auth2 authorizations
	 */
	public void removeOAuth2Authorizations(
		long pk,
		java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization>
			oAuth2Authorizations);

	/**
	 * Sets the o auth2 authorizations associated with the o auth2 scope grant, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2AuthorizationPKs the primary keys of the o auth2 authorizations to be associated with the o auth2 scope grant
	 */
	public void setOAuth2Authorizations(long pk, long[] oAuth2AuthorizationPKs);

	/**
	 * Sets the o auth2 authorizations associated with the o auth2 scope grant, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param oAuth2Authorizations the o auth2 authorizations to be associated with the o auth2 scope grant
	 */
	public void setOAuth2Authorizations(
		long pk,
		java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization>
			oAuth2Authorizations);

}