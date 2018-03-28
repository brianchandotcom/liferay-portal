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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.saml.persistence.model.SamlSpIdpConnection;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the saml sp idp connection service. This utility wraps {@link com.liferay.saml.persistence.service.persistence.impl.SamlSpIdpConnectionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionPersistence
 * @see com.liferay.saml.persistence.service.persistence.impl.SamlSpIdpConnectionPersistenceImpl
 * @generated
 */
@ProviderType
public class SamlSpIdpConnectionUtil {
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
	public static void clearCache(SamlSpIdpConnection samlSpIdpConnection) {
		getPersistence().clearCache(samlSpIdpConnection);
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
	public static List<SamlSpIdpConnection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SamlSpIdpConnection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SamlSpIdpConnection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SamlSpIdpConnection update(
		SamlSpIdpConnection samlSpIdpConnection) {
		return getPersistence().update(samlSpIdpConnection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SamlSpIdpConnection update(
		SamlSpIdpConnection samlSpIdpConnection, ServiceContext serviceContext) {
		return getPersistence().update(samlSpIdpConnection, serviceContext);
	}

	/**
	* Returns all the saml sp idp connections where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the saml sp idp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @return the range of matching saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the saml sp idp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the saml sp idp connections where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	*/
	public static SamlSpIdpConnection findByCompanyId_First(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public static SamlSpIdpConnection fetchByCompanyId_First(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	*/
	public static SamlSpIdpConnection findByCompanyId_Last(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public static SamlSpIdpConnection fetchByCompanyId_Last(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the saml sp idp connections before and after the current saml sp idp connection in the ordered set where companyId = &#63;.
	*
	* @param samlSpIdpConnectionId the primary key of the current saml sp idp connection
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	*/
	public static SamlSpIdpConnection[] findByCompanyId_PrevAndNext(
		long samlSpIdpConnectionId, long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(samlSpIdpConnectionId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the saml sp idp connections where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of saml sp idp connections where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching saml sp idp connections
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or throws a {@link NoSuchSpIdpConnectionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the matching saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	*/
	public static SamlSpIdpConnection findByC_SIEI(long companyId,
		java.lang.String samlIdpEntityId)
		throws com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException {
		return getPersistence().findByC_SIEI(companyId, samlIdpEntityId);
	}

	/**
	* Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public static SamlSpIdpConnection fetchByC_SIEI(long companyId,
		java.lang.String samlIdpEntityId) {
		return getPersistence().fetchByC_SIEI(companyId, samlIdpEntityId);
	}

	/**
	* Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	*/
	public static SamlSpIdpConnection fetchByC_SIEI(long companyId,
		java.lang.String samlIdpEntityId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_SIEI(companyId, samlIdpEntityId, retrieveFromCache);
	}

	/**
	* Removes the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the saml sp idp connection that was removed
	*/
	public static SamlSpIdpConnection removeByC_SIEI(long companyId,
		java.lang.String samlIdpEntityId)
		throws com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException {
		return getPersistence().removeByC_SIEI(companyId, samlIdpEntityId);
	}

	/**
	* Returns the number of saml sp idp connections where companyId = &#63; and samlIdpEntityId = &#63;.
	*
	* @param companyId the company ID
	* @param samlIdpEntityId the saml idp entity ID
	* @return the number of matching saml sp idp connections
	*/
	public static int countByC_SIEI(long companyId,
		java.lang.String samlIdpEntityId) {
		return getPersistence().countByC_SIEI(companyId, samlIdpEntityId);
	}

	/**
	* Caches the saml sp idp connection in the entity cache if it is enabled.
	*
	* @param samlSpIdpConnection the saml sp idp connection
	*/
	public static void cacheResult(SamlSpIdpConnection samlSpIdpConnection) {
		getPersistence().cacheResult(samlSpIdpConnection);
	}

	/**
	* Caches the saml sp idp connections in the entity cache if it is enabled.
	*
	* @param samlSpIdpConnections the saml sp idp connections
	*/
	public static void cacheResult(
		List<SamlSpIdpConnection> samlSpIdpConnections) {
		getPersistence().cacheResult(samlSpIdpConnections);
	}

	/**
	* Creates a new saml sp idp connection with the primary key. Does not add the saml sp idp connection to the database.
	*
	* @param samlSpIdpConnectionId the primary key for the new saml sp idp connection
	* @return the new saml sp idp connection
	*/
	public static SamlSpIdpConnection create(long samlSpIdpConnectionId) {
		return getPersistence().create(samlSpIdpConnectionId);
	}

	/**
	* Removes the saml sp idp connection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection that was removed
	* @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	*/
	public static SamlSpIdpConnection remove(long samlSpIdpConnectionId)
		throws com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException {
		return getPersistence().remove(samlSpIdpConnectionId);
	}

	public static SamlSpIdpConnection updateImpl(
		SamlSpIdpConnection samlSpIdpConnection) {
		return getPersistence().updateImpl(samlSpIdpConnection);
	}

	/**
	* Returns the saml sp idp connection with the primary key or throws a {@link NoSuchSpIdpConnectionException} if it could not be found.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection
	* @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	*/
	public static SamlSpIdpConnection findByPrimaryKey(
		long samlSpIdpConnectionId)
		throws com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException {
		return getPersistence().findByPrimaryKey(samlSpIdpConnectionId);
	}

	/**
	* Returns the saml sp idp connection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	* @return the saml sp idp connection, or <code>null</code> if a saml sp idp connection with the primary key could not be found
	*/
	public static SamlSpIdpConnection fetchByPrimaryKey(
		long samlSpIdpConnectionId) {
		return getPersistence().fetchByPrimaryKey(samlSpIdpConnectionId);
	}

	public static java.util.Map<java.io.Serializable, SamlSpIdpConnection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the saml sp idp connections.
	*
	* @return the saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @return the range of saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findAll(int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the saml sp idp connections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp idp connections
	* @param end the upper bound of the range of saml sp idp connections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of saml sp idp connections
	*/
	public static List<SamlSpIdpConnection> findAll(int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the saml sp idp connections from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of saml sp idp connections.
	*
	* @return the number of saml sp idp connections
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SamlSpIdpConnectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SamlSpIdpConnectionPersistence, SamlSpIdpConnectionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SamlSpIdpConnectionPersistence.class);

		ServiceTracker<SamlSpIdpConnectionPersistence, SamlSpIdpConnectionPersistence> serviceTracker =
			new ServiceTracker<SamlSpIdpConnectionPersistence, SamlSpIdpConnectionPersistence>(bundle.getBundleContext(),
				SamlSpIdpConnectionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}