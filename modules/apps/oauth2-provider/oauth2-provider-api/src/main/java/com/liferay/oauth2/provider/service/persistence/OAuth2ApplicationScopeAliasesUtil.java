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

import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the o auth2 application scope aliases service. This utility wraps {@link com.liferay.oauth2.provider.service.persistence.impl.OAuth2ApplicationScopeAliasesPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliasesPersistence
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2ApplicationScopeAliasesPersistenceImpl
 * @generated
 */
@ProviderType
public class OAuth2ApplicationScopeAliasesUtil {
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
	public static void clearCache(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		getPersistence().clearCache(oAuth2ApplicationScopeAliases);
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
	public static List<OAuth2ApplicationScopeAliases> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuth2ApplicationScopeAliases> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuth2ApplicationScopeAliases> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuth2ApplicationScopeAliases update(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		return getPersistence().update(oAuth2ApplicationScopeAliases);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuth2ApplicationScopeAliases update(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(oAuth2ApplicationScopeAliases, serviceContext);
	}

	/**
	* Returns all the o auth2 application scope aliaseses where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByC(long companyId) {
		return getPersistence().findByC(companyId);
	}

	/**
	* Returns a range of all the o auth2 application scope aliaseses where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @return the range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByC(long companyId,
		int start, int end) {
		return getPersistence().findByC(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByC(long companyId,
		int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence().findByC(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByC(long companyId,
		int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first o auth2 application scope aliases in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases findByC_First(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence().findByC_First(companyId, orderByComparator);
	}

	/**
	* Returns the first o auth2 application scope aliases in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases fetchByC_First(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence().fetchByC_First(companyId, orderByComparator);
	}

	/**
	* Returns the last o auth2 application scope aliases in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases findByC_Last(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence().findByC_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last o auth2 application scope aliases in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases fetchByC_Last(long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence().fetchByC_Last(companyId, orderByComparator);
	}

	/**
	* Returns the o auth2 application scope aliaseses before and after the current o auth2 application scope aliases in the ordered set where companyId = &#63;.
	*
	* @param oAuth2ApplicationScopeAliasesId the primary key of the current o auth2 application scope aliases
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	*/
	public static OAuth2ApplicationScopeAliases[] findByC_PrevAndNext(
		long oAuth2ApplicationScopeAliasesId, long companyId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence()
				   .findByC_PrevAndNext(oAuth2ApplicationScopeAliasesId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the o auth2 application scope aliaseses where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByC(long companyId) {
		getPersistence().removeByC(companyId);
	}

	/**
	* Returns the number of o auth2 application scope aliaseses where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching o auth2 application scope aliaseses
	*/
	public static int countByC(long companyId) {
		return getPersistence().countByC(companyId);
	}

	/**
	* Returns all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId) {
		return getPersistence().findByOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	* Returns a range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @return the range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end) {
		return getPersistence()
				   .findByOAuth2ApplicationId(oAuth2ApplicationId, start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence()
				   .findByOAuth2ApplicationId(oAuth2ApplicationId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByOAuth2ApplicationId(oAuth2ApplicationId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases findByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence()
				   .findByOAuth2ApplicationId_First(oAuth2ApplicationId,
			orderByComparator);
	}

	/**
	* Returns the first o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases fetchByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence()
				   .fetchByOAuth2ApplicationId_First(oAuth2ApplicationId,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases findByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence()
				   .findByOAuth2ApplicationId_Last(oAuth2ApplicationId,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases fetchByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence()
				   .fetchByOAuth2ApplicationId_Last(oAuth2ApplicationId,
			orderByComparator);
	}

	/**
	* Returns the o auth2 application scope aliaseses before and after the current o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationScopeAliasesId the primary key of the current o auth2 application scope aliases
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	*/
	public static OAuth2ApplicationScopeAliases[] findByOAuth2ApplicationId_PrevAndNext(
		long oAuth2ApplicationScopeAliasesId, long oAuth2ApplicationId,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence()
				   .findByOAuth2ApplicationId_PrevAndNext(oAuth2ApplicationScopeAliasesId,
			oAuth2ApplicationId, orderByComparator);
	}

	/**
	* Removes all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; from the database.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	*/
	public static void removeByOAuth2ApplicationId(long oAuth2ApplicationId) {
		getPersistence().removeByOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	* Returns the number of o auth2 application scope aliaseses where oAuth2ApplicationId = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @return the number of matching o auth2 application scope aliaseses
	*/
	public static int countByOAuth2ApplicationId(long oAuth2ApplicationId) {
		return getPersistence().countByOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	* Returns all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @return the matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByO_S(
		long oAuth2ApplicationId, long scopeAliasesHash) {
		return getPersistence().findByO_S(oAuth2ApplicationId, scopeAliasesHash);
	}

	/**
	* Returns a range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @return the range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByO_S(
		long oAuth2ApplicationId, long scopeAliasesHash, int start, int end) {
		return getPersistence()
				   .findByO_S(oAuth2ApplicationId, scopeAliasesHash, start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByO_S(
		long oAuth2ApplicationId, long scopeAliasesHash, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence()
				   .findByO_S(oAuth2ApplicationId, scopeAliasesHash, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findByO_S(
		long oAuth2ApplicationId, long scopeAliasesHash, int start, int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByO_S(oAuth2ApplicationId, scopeAliasesHash, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases findByO_S_First(
		long oAuth2ApplicationId, long scopeAliasesHash,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence()
				   .findByO_S_First(oAuth2ApplicationId, scopeAliasesHash,
			orderByComparator);
	}

	/**
	* Returns the first o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases fetchByO_S_First(
		long oAuth2ApplicationId, long scopeAliasesHash,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence()
				   .fetchByO_S_First(oAuth2ApplicationId, scopeAliasesHash,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases findByO_S_Last(
		long oAuth2ApplicationId, long scopeAliasesHash,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence()
				   .findByO_S_Last(oAuth2ApplicationId, scopeAliasesHash,
			orderByComparator);
	}

	/**
	* Returns the last o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application scope aliases, or <code>null</code> if a matching o auth2 application scope aliases could not be found
	*/
	public static OAuth2ApplicationScopeAliases fetchByO_S_Last(
		long oAuth2ApplicationId, long scopeAliasesHash,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence()
				   .fetchByO_S_Last(oAuth2ApplicationId, scopeAliasesHash,
			orderByComparator);
	}

	/**
	* Returns the o auth2 application scope aliaseses before and after the current o auth2 application scope aliases in the ordered set where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* @param oAuth2ApplicationScopeAliasesId the primary key of the current o auth2 application scope aliases
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	*/
	public static OAuth2ApplicationScopeAliases[] findByO_S_PrevAndNext(
		long oAuth2ApplicationScopeAliasesId, long oAuth2ApplicationId,
		long scopeAliasesHash,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence()
				   .findByO_S_PrevAndNext(oAuth2ApplicationScopeAliasesId,
			oAuth2ApplicationId, scopeAliasesHash, orderByComparator);
	}

	/**
	* Removes all the o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63; from the database.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	*/
	public static void removeByO_S(long oAuth2ApplicationId,
		long scopeAliasesHash) {
		getPersistence().removeByO_S(oAuth2ApplicationId, scopeAliasesHash);
	}

	/**
	* Returns the number of o auth2 application scope aliaseses where oAuth2ApplicationId = &#63; and scopeAliasesHash = &#63;.
	*
	* @param oAuth2ApplicationId the o auth2 application ID
	* @param scopeAliasesHash the scope aliases hash
	* @return the number of matching o auth2 application scope aliaseses
	*/
	public static int countByO_S(long oAuth2ApplicationId, long scopeAliasesHash) {
		return getPersistence().countByO_S(oAuth2ApplicationId, scopeAliasesHash);
	}

	/**
	* Caches the o auth2 application scope aliases in the entity cache if it is enabled.
	*
	* @param oAuth2ApplicationScopeAliases the o auth2 application scope aliases
	*/
	public static void cacheResult(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		getPersistence().cacheResult(oAuth2ApplicationScopeAliases);
	}

	/**
	* Caches the o auth2 application scope aliaseses in the entity cache if it is enabled.
	*
	* @param oAuth2ApplicationScopeAliaseses the o auth2 application scope aliaseses
	*/
	public static void cacheResult(
		List<OAuth2ApplicationScopeAliases> oAuth2ApplicationScopeAliaseses) {
		getPersistence().cacheResult(oAuth2ApplicationScopeAliaseses);
	}

	/**
	* Creates a new o auth2 application scope aliases with the primary key. Does not add the o auth2 application scope aliases to the database.
	*
	* @param oAuth2ApplicationScopeAliasesId the primary key for the new o auth2 application scope aliases
	* @return the new o auth2 application scope aliases
	*/
	public static OAuth2ApplicationScopeAliases create(
		long oAuth2ApplicationScopeAliasesId) {
		return getPersistence().create(oAuth2ApplicationScopeAliasesId);
	}

	/**
	* Removes the o auth2 application scope aliases with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	* @return the o auth2 application scope aliases that was removed
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	*/
	public static OAuth2ApplicationScopeAliases remove(
		long oAuth2ApplicationScopeAliasesId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence().remove(oAuth2ApplicationScopeAliasesId);
	}

	public static OAuth2ApplicationScopeAliases updateImpl(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		return getPersistence().updateImpl(oAuth2ApplicationScopeAliases);
	}

	/**
	* Returns the o auth2 application scope aliases with the primary key or throws a {@link NoSuchOAuth2ApplicationScopeAliasesException} if it could not be found.
	*
	* @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	* @return the o auth2 application scope aliases
	* @throws NoSuchOAuth2ApplicationScopeAliasesException if a o auth2 application scope aliases with the primary key could not be found
	*/
	public static OAuth2ApplicationScopeAliases findByPrimaryKey(
		long oAuth2ApplicationScopeAliasesId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationScopeAliasesException {
		return getPersistence().findByPrimaryKey(oAuth2ApplicationScopeAliasesId);
	}

	/**
	* Returns the o auth2 application scope aliases with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	* @return the o auth2 application scope aliases, or <code>null</code> if a o auth2 application scope aliases with the primary key could not be found
	*/
	public static OAuth2ApplicationScopeAliases fetchByPrimaryKey(
		long oAuth2ApplicationScopeAliasesId) {
		return getPersistence()
				   .fetchByPrimaryKey(oAuth2ApplicationScopeAliasesId);
	}

	public static java.util.Map<java.io.Serializable, OAuth2ApplicationScopeAliases> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the o auth2 application scope aliaseses.
	*
	* @return the o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the o auth2 application scope aliaseses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @return the range of o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findAll(int start,
		int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the o auth2 application scope aliaseses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationScopeAliasesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 application scope aliaseses
	* @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of o auth2 application scope aliaseses
	*/
	public static List<OAuth2ApplicationScopeAliases> findAll(int start,
		int end,
		OrderByComparator<OAuth2ApplicationScopeAliases> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the o auth2 application scope aliaseses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of o auth2 application scope aliaseses.
	*
	* @return the number of o auth2 application scope aliaseses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static OAuth2ApplicationScopeAliasesPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2ApplicationScopeAliasesPersistence, OAuth2ApplicationScopeAliasesPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuth2ApplicationScopeAliasesPersistence.class);

		ServiceTracker<OAuth2ApplicationScopeAliasesPersistence, OAuth2ApplicationScopeAliasesPersistence> serviceTracker =
			new ServiceTracker<OAuth2ApplicationScopeAliasesPersistence, OAuth2ApplicationScopeAliasesPersistence>(bundle.getBundleContext(),
				OAuth2ApplicationScopeAliasesPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}