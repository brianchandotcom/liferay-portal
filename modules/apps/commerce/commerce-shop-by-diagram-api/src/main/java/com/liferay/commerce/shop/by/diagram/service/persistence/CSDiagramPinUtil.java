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

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the cs diagram pin service. This utility wraps <code>com.liferay.commerce.shop.by.diagram.service.persistence.impl.CSDiagramPinPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramPinPersistence
 * @generated
 */
public class CSDiagramPinUtil {

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
	public static void clearCache(CSDiagramPin csDiagramPin) {
		getPersistence().clearCache(csDiagramPin);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CSDiagramPin> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CSDiagramPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CSDiagramPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CSDiagramPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CSDiagramPin> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CSDiagramPin update(CSDiagramPin csDiagramPin) {
		return getPersistence().update(csDiagramPin);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CSDiagramPin update(
		CSDiagramPin csDiagramPin, ServiceContext serviceContext) {

		return getPersistence().update(csDiagramPin, serviceContext);
	}

	/**
	 * Returns all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram pins
	 */
	public static List<CSDiagramPin> findByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns a range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @return the range of matching cs diagram pins
	 */
	public static List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram pins
	 */
	public static List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CSDiagramPin> orderByComparator) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram pins
	 */
	public static List<CSDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CSDiagramPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a matching cs diagram pin could not be found
	 */
	public static CSDiagramPin findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CSDiagramPin> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramPinException {

		return getPersistence().findByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram pin, or <code>null</code> if a matching cs diagram pin could not be found
	 */
	public static CSDiagramPin fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CSDiagramPin> orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a matching cs diagram pin could not be found
	 */
	public static CSDiagramPin findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CSDiagramPin> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramPinException {

		return getPersistence().findByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram pin, or <code>null</code> if a matching cs diagram pin could not be found
	 */
	public static CSDiagramPin fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CSDiagramPin> orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the cs diagram pins before and after the current cs diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CSDiagramPinId the primary key of the current cs diagram pin
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	public static CSDiagramPin[] findByCPDefinitionId_PrevAndNext(
			long CSDiagramPinId, long CPDefinitionId,
			OrderByComparator<CSDiagramPin> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramPinException {

		return getPersistence().findByCPDefinitionId_PrevAndNext(
			CSDiagramPinId, CPDefinitionId, orderByComparator);
	}

	/**
	 * Removes all the cs diagram pins where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the number of cs diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cs diagram pins
	 */
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Caches the cs diagram pin in the entity cache if it is enabled.
	 *
	 * @param csDiagramPin the cs diagram pin
	 */
	public static void cacheResult(CSDiagramPin csDiagramPin) {
		getPersistence().cacheResult(csDiagramPin);
	}

	/**
	 * Caches the cs diagram pins in the entity cache if it is enabled.
	 *
	 * @param csDiagramPins the cs diagram pins
	 */
	public static void cacheResult(List<CSDiagramPin> csDiagramPins) {
		getPersistence().cacheResult(csDiagramPins);
	}

	/**
	 * Creates a new cs diagram pin with the primary key. Does not add the cs diagram pin to the database.
	 *
	 * @param CSDiagramPinId the primary key for the new cs diagram pin
	 * @return the new cs diagram pin
	 */
	public static CSDiagramPin create(long CSDiagramPinId) {
		return getPersistence().create(CSDiagramPinId);
	}

	/**
	 * Removes the cs diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin that was removed
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	public static CSDiagramPin remove(long CSDiagramPinId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramPinException {

		return getPersistence().remove(CSDiagramPinId);
	}

	public static CSDiagramPin updateImpl(CSDiagramPin csDiagramPin) {
		return getPersistence().updateImpl(csDiagramPin);
	}

	/**
	 * Returns the cs diagram pin with the primary key or throws a <code>NoSuchCSDiagramPinException</code> if it could not be found.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin
	 * @throws NoSuchCSDiagramPinException if a cs diagram pin with the primary key could not be found
	 */
	public static CSDiagramPin findByPrimaryKey(long CSDiagramPinId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCSDiagramPinException {

		return getPersistence().findByPrimaryKey(CSDiagramPinId);
	}

	/**
	 * Returns the cs diagram pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin, or <code>null</code> if a cs diagram pin with the primary key could not be found
	 */
	public static CSDiagramPin fetchByPrimaryKey(long CSDiagramPinId) {
		return getPersistence().fetchByPrimaryKey(CSDiagramPinId);
	}

	/**
	 * Returns all the cs diagram pins.
	 *
	 * @return the cs diagram pins
	 */
	public static List<CSDiagramPin> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @return the range of cs diagram pins
	 */
	public static List<CSDiagramPin> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cs diagram pins
	 */
	public static List<CSDiagramPin> findAll(
		int start, int end, OrderByComparator<CSDiagramPin> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cs diagram pins
	 */
	public static List<CSDiagramPin> findAll(
		int start, int end, OrderByComparator<CSDiagramPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cs diagram pins from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cs diagram pins.
	 *
	 * @return the number of cs diagram pins
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CSDiagramPinPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CSDiagramPinPersistence, CSDiagramPinPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CSDiagramPinPersistence.class);

		ServiceTracker<CSDiagramPinPersistence, CSDiagramPinPersistence>
			serviceTracker =
				new ServiceTracker
					<CSDiagramPinPersistence, CSDiagramPinPersistence>(
						bundle.getBundleContext(),
						CSDiagramPinPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}