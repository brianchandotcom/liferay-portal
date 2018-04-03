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

import com.liferay.oauth2.provider.model.OAuth2Application;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the o auth2 application service. This utility wraps {@link com.liferay.oauth2.provider.service.persistence.impl.OAuth2ApplicationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationPersistence
 * @see com.liferay.oauth2.provider.service.persistence.impl.OAuth2ApplicationPersistenceImpl
 * @generated
 */
@ProviderType
public class OAuth2ApplicationUtil {
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
	public static void clearCache(OAuth2Application oAuth2Application) {
		getPersistence().clearCache(oAuth2Application);
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
	public static List<OAuth2Application> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OAuth2Application> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OAuth2Application> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static OAuth2Application update(OAuth2Application oAuth2Application) {
		return getPersistence().update(oAuth2Application);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static OAuth2Application update(
		OAuth2Application oAuth2Application, ServiceContext serviceContext) {
		return getPersistence().update(oAuth2Application, serviceContext);
	}

	/**
	* Returns all the o auth2 applications where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching o auth2 applications
	*/
	public static List<OAuth2Application> findByC(long companyId) {
		return getPersistence().findByC(companyId);
	}

	/**
	* Returns a range of all the o auth2 applications where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @return the range of matching o auth2 applications
	*/
	public static List<OAuth2Application> findByC(long companyId, int start,
		int end) {
		return getPersistence().findByC(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 applications
	*/
	public static List<OAuth2Application> findByC(long companyId, int start,
		int end, OrderByComparator<OAuth2Application> orderByComparator) {
		return getPersistence().findByC(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching o auth2 applications
	*/
	public static List<OAuth2Application> findByC(long companyId, int start,
		int end, OrderByComparator<OAuth2Application> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	*/
	public static OAuth2Application findByC_First(long companyId,
		OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence().findByC_First(companyId, orderByComparator);
	}

	/**
	* Returns the first o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public static OAuth2Application fetchByC_First(long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {
		return getPersistence().fetchByC_First(companyId, orderByComparator);
	}

	/**
	* Returns the last o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	*/
	public static OAuth2Application findByC_Last(long companyId,
		OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence().findByC_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public static OAuth2Application fetchByC_Last(long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {
		return getPersistence().fetchByC_Last(companyId, orderByComparator);
	}

	/**
	* Returns the o auth2 applications before and after the current o auth2 application in the ordered set where companyId = &#63;.
	*
	* @param oAuth2ApplicationId the primary key of the current o auth2 application
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public static OAuth2Application[] findByC_PrevAndNext(
		long oAuth2ApplicationId, long companyId,
		OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence()
				   .findByC_PrevAndNext(oAuth2ApplicationId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching o auth2 applications that the user has permission to view
	*/
	public static List<OAuth2Application> filterFindByC(long companyId) {
		return getPersistence().filterFindByC(companyId);
	}

	/**
	* Returns a range of all the o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @return the range of matching o auth2 applications that the user has permission to view
	*/
	public static List<OAuth2Application> filterFindByC(long companyId,
		int start, int end) {
		return getPersistence().filterFindByC(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 applications that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching o auth2 applications that the user has permission to view
	*/
	public static List<OAuth2Application> filterFindByC(long companyId,
		int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {
		return getPersistence()
				   .filterFindByC(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the o auth2 applications before and after the current o auth2 application in the ordered set of o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* @param oAuth2ApplicationId the primary key of the current o auth2 application
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public static OAuth2Application[] filterFindByC_PrevAndNext(
		long oAuth2ApplicationId, long companyId,
		OrderByComparator<OAuth2Application> orderByComparator)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence()
				   .filterFindByC_PrevAndNext(oAuth2ApplicationId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the o auth2 applications where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByC(long companyId) {
		getPersistence().removeByC(companyId);
	}

	/**
	* Returns the number of o auth2 applications where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching o auth2 applications
	*/
	public static int countByC(long companyId) {
		return getPersistence().countByC(companyId);
	}

	/**
	* Returns the number of o auth2 applications that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching o auth2 applications that the user has permission to view
	*/
	public static int filterCountByC(long companyId) {
		return getPersistence().filterCountByC(companyId);
	}

	/**
	* Returns the o auth2 application where companyId = &#63; and clientId = &#63; or throws a {@link NoSuchOAuth2ApplicationException} if it could not be found.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the matching o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	*/
	public static OAuth2Application findByC_C(long companyId,
		java.lang.String clientId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence().findByC_C(companyId, clientId);
	}

	/**
	* Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public static OAuth2Application fetchByC_C(long companyId,
		java.lang.String clientId) {
		return getPersistence().fetchByC_C(companyId, clientId);
	}

	/**
	* Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	*/
	public static OAuth2Application fetchByC_C(long companyId,
		java.lang.String clientId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(companyId, clientId, retrieveFromCache);
	}

	/**
	* Removes the o auth2 application where companyId = &#63; and clientId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the o auth2 application that was removed
	*/
	public static OAuth2Application removeByC_C(long companyId,
		java.lang.String clientId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence().removeByC_C(companyId, clientId);
	}

	/**
	* Returns the number of o auth2 applications where companyId = &#63; and clientId = &#63;.
	*
	* @param companyId the company ID
	* @param clientId the client ID
	* @return the number of matching o auth2 applications
	*/
	public static int countByC_C(long companyId, java.lang.String clientId) {
		return getPersistence().countByC_C(companyId, clientId);
	}

	/**
	* Caches the o auth2 application in the entity cache if it is enabled.
	*
	* @param oAuth2Application the o auth2 application
	*/
	public static void cacheResult(OAuth2Application oAuth2Application) {
		getPersistence().cacheResult(oAuth2Application);
	}

	/**
	* Caches the o auth2 applications in the entity cache if it is enabled.
	*
	* @param oAuth2Applications the o auth2 applications
	*/
	public static void cacheResult(List<OAuth2Application> oAuth2Applications) {
		getPersistence().cacheResult(oAuth2Applications);
	}

	/**
	* Creates a new o auth2 application with the primary key. Does not add the o auth2 application to the database.
	*
	* @param oAuth2ApplicationId the primary key for the new o auth2 application
	* @return the new o auth2 application
	*/
	public static OAuth2Application create(long oAuth2ApplicationId) {
		return getPersistence().create(oAuth2ApplicationId);
	}

	/**
	* Removes the o auth2 application with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2ApplicationId the primary key of the o auth2 application
	* @return the o auth2 application that was removed
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public static OAuth2Application remove(long oAuth2ApplicationId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence().remove(oAuth2ApplicationId);
	}

	public static OAuth2Application updateImpl(
		OAuth2Application oAuth2Application) {
		return getPersistence().updateImpl(oAuth2Application);
	}

	/**
	* Returns the o auth2 application with the primary key or throws a {@link NoSuchOAuth2ApplicationException} if it could not be found.
	*
	* @param oAuth2ApplicationId the primary key of the o auth2 application
	* @return the o auth2 application
	* @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	*/
	public static OAuth2Application findByPrimaryKey(long oAuth2ApplicationId)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException {
		return getPersistence().findByPrimaryKey(oAuth2ApplicationId);
	}

	/**
	* Returns the o auth2 application with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param oAuth2ApplicationId the primary key of the o auth2 application
	* @return the o auth2 application, or <code>null</code> if a o auth2 application with the primary key could not be found
	*/
	public static OAuth2Application fetchByPrimaryKey(long oAuth2ApplicationId) {
		return getPersistence().fetchByPrimaryKey(oAuth2ApplicationId);
	}

	public static java.util.Map<java.io.Serializable, OAuth2Application> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the o auth2 applications.
	*
	* @return the o auth2 applications
	*/
	public static List<OAuth2Application> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the o auth2 applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @return the range of o auth2 applications
	*/
	public static List<OAuth2Application> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the o auth2 applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of o auth2 applications
	*/
	public static List<OAuth2Application> findAll(int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the o auth2 applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OAuth2ApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 applications
	* @param end the upper bound of the range of o auth2 applications (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of o auth2 applications
	*/
	public static List<OAuth2Application> findAll(int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the o auth2 applications from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of o auth2 applications.
	*
	* @return the number of o auth2 applications
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static OAuth2ApplicationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2ApplicationPersistence, OAuth2ApplicationPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuth2ApplicationPersistence.class);

		ServiceTracker<OAuth2ApplicationPersistence, OAuth2ApplicationPersistence> serviceTracker =
			new ServiceTracker<OAuth2ApplicationPersistence, OAuth2ApplicationPersistence>(bundle.getBundleContext(),
				OAuth2ApplicationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}