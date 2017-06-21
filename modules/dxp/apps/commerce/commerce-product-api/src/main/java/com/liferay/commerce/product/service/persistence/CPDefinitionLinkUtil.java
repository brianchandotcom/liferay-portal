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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionLink;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition link service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDefinitionLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLinkPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionLinkPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionLinkUtil {
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
	public static void clearCache(CPDefinitionLink cpDefinitionLink) {
		getPersistence().clearCache(cpDefinitionLink);
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
	public static List<CPDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionLink update(CPDefinitionLink cpDefinitionLink) {
		return getPersistence().update(cpDefinitionLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionLink update(CPDefinitionLink cpDefinitionLink,
		ServiceContext serviceContext) {
		return getPersistence().update(cpDefinitionLink, serviceContext);
	}

	/**
	* Caches the cp definition link in the entity cache if it is enabled.
	*
	* @param cpDefinitionLink the cp definition link
	*/
	public static void cacheResult(CPDefinitionLink cpDefinitionLink) {
		getPersistence().cacheResult(cpDefinitionLink);
	}

	/**
	* Caches the cp definition links in the entity cache if it is enabled.
	*
	* @param cpDefinitionLinks the cp definition links
	*/
	public static void cacheResult(List<CPDefinitionLink> cpDefinitionLinks) {
		getPersistence().cacheResult(cpDefinitionLinks);
	}

	/**
	* Creates a new cp definition link with the primary key. Does not add the cp definition link to the database.
	*
	* @param CPDefinitionLinkId the primary key for the new cp definition link
	* @return the new cp definition link
	*/
	public static CPDefinitionLink create(long CPDefinitionLinkId) {
		return getPersistence().create(CPDefinitionLinkId);
	}

	/**
	* Removes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link that was removed
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink remove(long CPDefinitionLinkId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().remove(CPDefinitionLinkId);
	}

	public static CPDefinitionLink updateImpl(CPDefinitionLink cpDefinitionLink) {
		return getPersistence().updateImpl(cpDefinitionLink);
	}

	/**
	* Returns the cp definition link with the primary key or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link
	* @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink findByPrimaryKey(long CPDefinitionLinkId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException {
		return getPersistence().findByPrimaryKey(CPDefinitionLinkId);
	}

	/**
	* Returns the cp definition link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link, or <code>null</code> if a cp definition link with the primary key could not be found
	*/
	public static CPDefinitionLink fetchByPrimaryKey(long CPDefinitionLinkId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionLinkId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition links.
	*
	* @return the cp definition links
	*/
	public static List<CPDefinitionLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of cp definition links
	*/
	public static List<CPDefinitionLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition links
	*/
	public static List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition links
	*/
	public static List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition links from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition links.
	*
	* @return the number of cp definition links
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionLinkPersistence, CPDefinitionLinkPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CPDefinitionLinkPersistence.class);
}