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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the portlet service. This utility wraps {@link PortletPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPersistence
 * @see PortletPersistenceImpl
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
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Portlet portlet) {
		getPersistence().clearCache(portlet);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Portlet> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Portlet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Portlet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Portlet update(Portlet portlet) {
		return getPersistence().update(portlet);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
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
	public static java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @return the range of matching portlets
	*/
	public static java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlets
	*/
	public static java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	*/
	public static com.liferay.portal.model.Portlet findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException {
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
	public static com.liferay.portal.model.Portlet fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last portlet in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	*/
	public static com.liferay.portal.model.Portlet findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException {
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
	public static com.liferay.portal.model.Portlet fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
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
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public static com.liferay.portal.model.Portlet[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException {
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
	* Returns the portlet where companyId = &#63; and portletId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletException} if it could not be found.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	*/
	public static com.liferay.portal.model.Portlet findByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException {
		return getPersistence().findByC_P(companyId, portletId);
	}

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public static com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId) {
		return getPersistence().fetchByC_P(companyId, portletId);
	}

	/**
	* Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	*/
	public static com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId, boolean retrieveFromCache) {
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
	public static com.liferay.portal.model.Portlet removeByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException {
		return getPersistence().removeByC_P(companyId, portletId);
	}

	/**
	* Returns the number of portlets where companyId = &#63; and portletId = &#63;.
	*
	* @param companyId the company ID
	* @param portletId the portlet ID
	* @return the number of matching portlets
	*/
	public static int countByC_P(long companyId, java.lang.String portletId) {
		return getPersistence().countByC_P(companyId, portletId);
	}

	/**
	* Caches the portlet in the entity cache if it is enabled.
	*
	* @param portlet the portlet
	*/
	public static void cacheResult(com.liferay.portal.model.Portlet portlet) {
		getPersistence().cacheResult(portlet);
	}

	/**
	* Caches the portlets in the entity cache if it is enabled.
	*
	* @param portlets the portlets
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Portlet> portlets) {
		getPersistence().cacheResult(portlets);
	}

	/**
	* Creates a new portlet with the primary key. Does not add the portlet to the database.
	*
	* @param id the primary key for the new portlet
	* @return the new portlet
	*/
	public static com.liferay.portal.model.Portlet create(long id) {
		return getPersistence().create(id);
	}

	/**
	* Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the portlet
	* @return the portlet that was removed
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public static com.liferay.portal.model.Portlet remove(long id)
		throws com.liferay.portal.NoSuchPortletException {
		return getPersistence().remove(id);
	}

	public static com.liferay.portal.model.Portlet updateImpl(
		com.liferay.portal.model.Portlet portlet) {
		return getPersistence().updateImpl(portlet);
	}

	/**
	* Returns the portlet with the primary key or throws a {@link com.liferay.portal.NoSuchPortletException} if it could not be found.
	*
	* @param id the primary key of the portlet
	* @return the portlet
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	*/
	public static com.liferay.portal.model.Portlet findByPrimaryKey(long id)
		throws com.liferay.portal.NoSuchPortletException {
		return getPersistence().findByPrimaryKey(id);
	}

	/**
	* Returns the portlet with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the portlet
	* @return the portlet, or <code>null</code> if a portlet with the primary key could not be found
	*/
	public static com.liferay.portal.model.Portlet fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.Portlet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the portlets.
	*
	* @return the portlets
	*/
	public static java.util.List<com.liferay.portal.model.Portlet> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @return the range of portlets
	*/
	public static java.util.List<com.liferay.portal.model.Portlet> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlets
	* @param end the upper bound of the range of portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portlets
	*/
	public static java.util.List<com.liferay.portal.model.Portlet> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
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

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(PortletPersistence persistence) {
	}

	private static PortletPersistence _persistence;
}