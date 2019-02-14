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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the portlet service. This utility wraps <code>com.liferay.portal.service.persistence.impl.PortletPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPersistence
 * @generated
 */
@ProviderType
public class PortletUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Portlet portlet) {
		getPersistence().clearCache(portlet);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Portlet> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Portlet> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Portlet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Portlet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Portlet> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Portlet update(Portlet portlet) {
		return getPersistence().update(portlet);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Portlet update(Portlet portlet, ServiceContext serviceContext) {
		return getPersistence().update(portlet, serviceContext);
	}

	/**
	* Returns all the portlets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching portlets
	*/
	public static List<Portlet> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @return the range of matching portlets
	*/
	public static List<Portlet> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlets
	*/
	public static List<Portlet> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Portlet> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlets
	*/
	public static List<Portlet> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Portlet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet
	* @throws NoSuchPortletException if a matching portlet could not be found
	*/
	public static Portlet findByCompanyId_First(long companyId,
		OrderByComparator<Portlet> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchPortletException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public static Portlet fetchByCompanyId_First(long companyId,
		OrderByComparator<Portlet> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet
	* @throws NoSuchPortletException if a matching portlet could not be found
	*/
	public static Portlet findByCompanyId_Last(long companyId,
		OrderByComparator<Portlet> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchPortletException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public static Portlet fetchByCompanyId_Last(long companyId,
		OrderByComparator<Portlet> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the portlets before and after the current portlet in the ordered set where companyId = &#63;.
	*
	* @param id the primary key of the current portlet
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet
	* @throws NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public static Portlet[] findByCompanyId_PrevAndNext(long id,
		long companyId, OrderByComparator<Portlet> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchPortletException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(id, companyId, orderByComparator);
	}

	/**
	* Removes all the portlets where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of portlets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching portlets
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or throws a <code>NoSuchPortletException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the matching portlet
	* @throws NoSuchPortletException if a matching portlet could not be found
	*/
	public static Portlet findByC_P(long companyId, String portletId)
		throws com.liferay.portal.kernel.exception.NoSuchPortletException {
		return getPersistence().findByC_P(companyId, portletId);
	}

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public static Portlet fetchByC_P(long companyId, String portletId) {
		return getPersistence().fetchByC_P(companyId, portletId);
	}

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public static Portlet fetchByC_P(long companyId, String portletId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_P(companyId, portletId, retrieveFromCache);
	}

	/**
	* Removes the portlet where companyId = &#63; and portletId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the portlet that was removed
	*/
	public static Portlet removeByC_P(long companyId, String portletId)
		throws com.liferay.portal.kernel.exception.NoSuchPortletException {
		return getPersistence().removeByC_P(companyId, portletId);
	}

	/**
	* Returns the number of portlets where companyId = &#63; and portletId = &#63;.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the number of matching portlets
	*/
	public static int countByC_P(long companyId, String portletId) {
		return getPersistence().countByC_P(companyId, portletId);
	}

	/**
	* Caches the portlet in the entity cache if it is enabled.
	*
	* @param portlet the portlet
	*/
	public static void cacheResult(Portlet portlet) {
		getPersistence().cacheResult(portlet);
	}

	/**
	* Caches the portlets in the entity cache if it is enabled.
	*
	* @param portlets the portlets
	*/
	public static void cacheResult(List<Portlet> portlets) {
		getPersistence().cacheResult(portlets);
	}

	/**
	* Creates a new portlet with the primary key. Does not add the portlet to the database.
	*
	* @param id the primary key for the new portlet
	* @return the new portlet
	*/
	public static Portlet create(long id) {
		return getPersistence().create(id);
	}

	/**
	* Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the portlet
	* @return the portlet that was removed
	* @throws NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public static Portlet remove(long id)
		throws com.liferay.portal.kernel.exception.NoSuchPortletException {
		return getPersistence().remove(id);
	}

	public static Portlet updateImpl(Portlet portlet) {
		return getPersistence().updateImpl(portlet);
	}

	/**
	* Returns the portlet with the primary key or throws a <code>NoSuchPortletException</code> if it could not be found.
	*
	* @param id the primary key of the portlet
	* @return the portlet
	* @throws NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public static Portlet findByPrimaryKey(long id)
		throws com.liferay.portal.kernel.exception.NoSuchPortletException {
		return getPersistence().findByPrimaryKey(id);
	}

	/**
	* Returns the portlet with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the portlet
	* @return the portlet, or <code>null</code> if a portlet with the primary key could not be found
	*/
	public static Portlet fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
	}

	/**
	* Returns all the portlets.
	*
	* @return the portlets
	*/
	public static List<Portlet> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @return the range of portlets
	*/
	public static List<Portlet> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portlets
	*/
	public static List<Portlet> findAll(int start, int end,
		OrderByComparator<Portlet> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of portlets
	*/
	public static List<Portlet> findAll(int start, int end,
		OrderByComparator<Portlet> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the portlets from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of portlets.
	*
	* @return the number of portlets
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PortletPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortletPersistence)PortalBeanLocatorUtil.locate(PortletPersistence.class.getName());

			ReferenceRegistry.registerReference(PortletUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static PortletPersistence _persistence;
}