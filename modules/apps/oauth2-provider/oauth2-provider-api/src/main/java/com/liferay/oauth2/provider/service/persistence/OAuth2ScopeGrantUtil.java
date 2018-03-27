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

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the o auth2 scope grant service. This utility wraps {@link com.liferay.oauth2.provider.service.persistence.impl.OAuth2ScopeGrantPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrantPersistence
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2ScopeGrantPersistenceImpl
 * @generated
 */
@ProviderType
public class OAuth2ScopeGrantUtil {
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
	public static void clearCache(OAuth2ScopeGrant oAuth2ScopeGrant) {
		getPersistence().clearCache(oAuth2ScopeGrant);
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
	public static List<OAuth2ScopeGrant> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuth2ScopeGrant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuth2ScopeGrant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuth2ScopeGrant update(OAuth2ScopeGrant oAuth2ScopeGrant) {
		return getPersistence().update(oAuth2ScopeGrant);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuth2ScopeGrant update(OAuth2ScopeGrant oAuth2ScopeGrant,
		ServiceContext serviceContext) {
		return getPersistence().update(oAuth2ScopeGrant, serviceContext);
	}

	/**
	* Returns all the o auth2 scope grants where oAuth2AccessTokenId = &#63;.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @return the matching o auth2 scope grants
	*/
	public static List<OAuth2ScopeGrant> findByOAuth2AccessTokenId(
		long oAuth2AccessTokenId) {
		return getPersistence().findByOAuth2AccessTokenId(oAuth2AccessTokenId);
	}

	/**
	* Returns a range of all the o auth2 scope grants where oAuth2AccessTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @return the range of matching o auth2 scope grants
	*/
	public static List<OAuth2ScopeGrant> findByOAuth2AccessTokenId(
		long oAuth2AccessTokenId, int start, int end) {
		return getPersistence()
				   .findByOAuth2AccessTokenId(oAuth2AccessTokenId, start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 scope grants where oAuth2AccessTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 scope grants
	*/
	public static List<OAuth2ScopeGrant> findByOAuth2AccessTokenId(
		long oAuth2AccessTokenId, int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		return getPersistence()
				   .findByOAuth2AccessTokenId(oAuth2AccessTokenId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the o auth2 scope grants where oAuth2AccessTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ScopeGrantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param start the lower bound of the range of o auth2 scope grants
	* @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 scope grants
	*/
	public static List<OAuth2ScopeGrant> findByOAuth2AccessTokenId(
		long oAuth2AccessTokenId, int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByOAuth2AccessTokenId(oAuth2AccessTokenId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first o auth2 scope grant in the ordered set where oAuth2AccessTokenId = &#63;.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	*/
	public static OAuth2ScopeGrant findByOAuth2AccessTokenId_First(
		long oAuth2AccessTokenId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException {
		return getPersistence()
				   .findByOAuth2AccessTokenId_First(oAuth2AccessTokenId,
			orderByComparator);
	}

	/**
	* Returns the first o auth2 scope grant in the ordered set where oAuth2AccessTokenId = &#63;.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public static OAuth2ScopeGrant fetchByOAuth2AccessTokenId_First(
		long oAuth2AccessTokenId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		return getPersistence()
				   .fetchByOAuth2AccessTokenId_First(oAuth2AccessTokenId,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 scope grant in the ordered set where oAuth2AccessTokenId = &#63;.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	*/
	public static OAuth2ScopeGrant findByOAuth2AccessTokenId_Last(
		long oAuth2AccessTokenId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException {
		return getPersistence()
				   .findByOAuth2AccessTokenId_Last(oAuth2AccessTokenId,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 scope grant in the ordered set where oAuth2AccessTokenId = &#63;.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public static OAuth2ScopeGrant fetchByOAuth2AccessTokenId_Last(
		long oAuth2AccessTokenId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		return getPersistence()
				   .fetchByOAuth2AccessTokenId_Last(oAuth2AccessTokenId,
			orderByComparator);
	}

	/**
	* Returns the o auth2 scope grants before and after the current o auth2 scope grant in the ordered set where oAuth2AccessTokenId = &#63;.
	*
	* @param oAuth2ScopeGrantId the primary key of the current o auth2 scope grant
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	*/
	public static OAuth2ScopeGrant[] findByOAuth2AccessTokenId_PrevAndNext(
		long oAuth2ScopeGrantId, long oAuth2AccessTokenId,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException {
		return getPersistence()
				   .findByOAuth2AccessTokenId_PrevAndNext(oAuth2ScopeGrantId,
			oAuth2AccessTokenId, orderByComparator);
	}

	/**
	* Removes all the o auth2 scope grants where oAuth2AccessTokenId = &#63; from the database.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	*/
	public static void removeByOAuth2AccessTokenId(long oAuth2AccessTokenId) {
		getPersistence().removeByOAuth2AccessTokenId(oAuth2AccessTokenId);
	}

	/**
	* Returns the number of o auth2 scope grants where oAuth2AccessTokenId = &#63;.
	*
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @return the number of matching o auth2 scope grants
	*/
	public static int countByOAuth2AccessTokenId(long oAuth2AccessTokenId) {
		return getPersistence().countByOAuth2AccessTokenId(oAuth2AccessTokenId);
	}

	/**
	* Returns the o auth2 scope grant where companyId = &#63; and oAuth2AccessTokenId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or throws a {@link NoSuchOAuth2ScopeGrantException} if it could not be found.
	*
	* @param companyId the company ID
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param scope the scope
	* @return the matching o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a matching o auth2 scope grant could not be found
	*/
	public static OAuth2ScopeGrant findByC_O_A_B_S(long companyId,
		long oAuth2AccessTokenId, java.lang.String applicationName,
		java.lang.String bundleSymbolicName, java.lang.String scope)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException {
		return getPersistence()
				   .findByC_O_A_B_S(companyId, oAuth2AccessTokenId,
			applicationName, bundleSymbolicName, scope);
	}

	/**
	* Returns the o auth2 scope grant where companyId = &#63; and oAuth2AccessTokenId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param scope the scope
	* @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public static OAuth2ScopeGrant fetchByC_O_A_B_S(long companyId,
		long oAuth2AccessTokenId, java.lang.String applicationName,
		java.lang.String bundleSymbolicName, java.lang.String scope) {
		return getPersistence()
				   .fetchByC_O_A_B_S(companyId, oAuth2AccessTokenId,
			applicationName, bundleSymbolicName, scope);
	}

	/**
	* Returns the o auth2 scope grant where companyId = &#63; and oAuth2AccessTokenId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param scope the scope
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 scope grant, or <code>null</code> if a matching o auth2 scope grant could not be found
	*/
	public static OAuth2ScopeGrant fetchByC_O_A_B_S(long companyId,
		long oAuth2AccessTokenId, java.lang.String applicationName,
		java.lang.String bundleSymbolicName, java.lang.String scope,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_O_A_B_S(companyId, oAuth2AccessTokenId,
			applicationName, bundleSymbolicName, scope, retrieveFromCache);
	}

	/**
	* Removes the o auth2 scope grant where companyId = &#63; and oAuth2AccessTokenId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63; from the database.
	*
	* @param companyId the company ID
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param scope the scope
	* @return the o auth2 scope grant that was removed
	*/
	public static OAuth2ScopeGrant removeByC_O_A_B_S(long companyId,
		long oAuth2AccessTokenId, java.lang.String applicationName,
		java.lang.String bundleSymbolicName, java.lang.String scope)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException {
		return getPersistence()
				   .removeByC_O_A_B_S(companyId, oAuth2AccessTokenId,
			applicationName, bundleSymbolicName, scope);
	}

	/**
	* Returns the number of o auth2 scope grants where companyId = &#63; and oAuth2AccessTokenId = &#63; and applicationName = &#63; and bundleSymbolicName = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param oAuth2AccessTokenId the o auth2 access token ID
	* @param applicationName the application name
	* @param bundleSymbolicName the bundle symbolic name
	* @param scope the scope
	* @return the number of matching o auth2 scope grants
	*/
	public static int countByC_O_A_B_S(long companyId,
		long oAuth2AccessTokenId, java.lang.String applicationName,
		java.lang.String bundleSymbolicName, java.lang.String scope) {
		return getPersistence()
				   .countByC_O_A_B_S(companyId, oAuth2AccessTokenId,
			applicationName, bundleSymbolicName, scope);
	}

	/**
	* Caches the o auth2 scope grant in the entity cache if it is enabled.
	*
	* @param oAuth2ScopeGrant the o auth2 scope grant
	*/
	public static void cacheResult(OAuth2ScopeGrant oAuth2ScopeGrant) {
		getPersistence().cacheResult(oAuth2ScopeGrant);
	}

	/**
	* Caches the o auth2 scope grants in the entity cache if it is enabled.
	*
	* @param oAuth2ScopeGrants the o auth2 scope grants
	*/
	public static void cacheResult(List<OAuth2ScopeGrant> oAuth2ScopeGrants) {
		getPersistence().cacheResult(oAuth2ScopeGrants);
	}

	/**
	* Creates a new o auth2 scope grant with the primary key. Does not add the o auth2 scope grant to the database.
	*
	* @param oAuth2ScopeGrantId the primary key for the new o auth2 scope grant
	* @return the new o auth2 scope grant
	*/
	public static OAuth2ScopeGrant create(long oAuth2ScopeGrantId) {
		return getPersistence().create(oAuth2ScopeGrantId);
	}

	/**
	* Removes the o auth2 scope grant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	* @return the o auth2 scope grant that was removed
	* @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	*/
	public static OAuth2ScopeGrant remove(long oAuth2ScopeGrantId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException {
		return getPersistence().remove(oAuth2ScopeGrantId);
	}

	public static OAuth2ScopeGrant updateImpl(OAuth2ScopeGrant oAuth2ScopeGrant) {
		return getPersistence().updateImpl(oAuth2ScopeGrant);
	}

	/**
	* Returns the o auth2 scope grant with the primary key or throws a {@link NoSuchOAuth2ScopeGrantException} if it could not be found.
	*
	* @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	* @return the o auth2 scope grant
	* @throws NoSuchOAuth2ScopeGrantException if a o auth2 scope grant with the primary key could not be found
	*/
	public static OAuth2ScopeGrant findByPrimaryKey(long oAuth2ScopeGrantId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ScopeGrantException {
		return getPersistence().findByPrimaryKey(oAuth2ScopeGrantId);
	}

	/**
	* Returns the o auth2 scope grant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	* @return the o auth2 scope grant, or <code>null</code> if a o auth2 scope grant with the primary key could not be found
	*/
	public static OAuth2ScopeGrant fetchByPrimaryKey(long oAuth2ScopeGrantId) {
		return getPersistence().fetchByPrimaryKey(oAuth2ScopeGrantId);
	}

	public static java.util.Map<java.io.Serializable, OAuth2ScopeGrant> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the o auth2 scope grants.
	*
	* @return the o auth2 scope grants
	*/
	public static List<OAuth2ScopeGrant> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<OAuth2ScopeGrant> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<OAuth2ScopeGrant> findAll(int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<OAuth2ScopeGrant> findAll(int start, int end,
		OrderByComparator<OAuth2ScopeGrant> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the o auth2 scope grants from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of o auth2 scope grants.
	*
	* @return the number of o auth2 scope grants
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OAuth2ScopeGrantPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2ScopeGrantPersistence, OAuth2ScopeGrantPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuth2ScopeGrantPersistence.class);

		ServiceTracker<OAuth2ScopeGrantPersistence, OAuth2ScopeGrantPersistence> serviceTracker =
			new ServiceTracker<OAuth2ScopeGrantPersistence, OAuth2ScopeGrantPersistence>(bundle.getBundleContext(),
				OAuth2ScopeGrantPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}