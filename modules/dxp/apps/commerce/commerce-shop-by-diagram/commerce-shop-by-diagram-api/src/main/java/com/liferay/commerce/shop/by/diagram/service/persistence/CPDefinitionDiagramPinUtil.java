/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
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
 * The persistence utility for the cp definition diagram pin service. This utility wraps <code>com.liferay.commerce.shop.by.diagram.service.persistence.impl.CPDefinitionDiagramPinPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPinPersistence
 * @generated
 */
public class CPDefinitionDiagramPinUtil {

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
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		getPersistence().clearCache(cpDefinitionDiagramPin);
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
	public static Map<Serializable, CPDefinitionDiagramPin> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDefinitionDiagramPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionDiagramPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionDiagramPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionDiagramPin update(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		return getPersistence().update(cpDefinitionDiagramPin);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionDiagramPin update(
		CPDefinitionDiagramPin cpDefinitionDiagramPin,
		ServiceContext serviceContext) {

		return getPersistence().update(cpDefinitionDiagramPin, serviceContext);
	}

	/**
	 * Returns all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId) {

		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns a range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @return the range of matching cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a matching cp definition diagram pin could not be found
	 */
	public static CPDefinitionDiagramPin findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramPin> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramPinException {

		return getPersistence().findByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram pin, or <code>null</code> if a matching cp definition diagram pin could not be found
	 */
	public static CPDefinitionDiagramPin fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_First(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a matching cp definition diagram pin could not be found
	 */
	public static CPDefinitionDiagramPin findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramPin> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramPinException {

		return getPersistence().findByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram pin, or <code>null</code> if a matching cp definition diagram pin could not be found
	 */
	public static CPDefinitionDiagramPin fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		return getPersistence().fetchByCPDefinitionId_Last(
			CPDefinitionId, orderByComparator);
	}

	/**
	 * Returns the cp definition diagram pins before and after the current cp definition diagram pin in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the current cp definition diagram pin
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	public static CPDefinitionDiagramPin[] findByCPDefinitionId_PrevAndNext(
			long CPDefinitionDiagramPinId, long CPDefinitionId,
			OrderByComparator<CPDefinitionDiagramPin> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramPinException {

		return getPersistence().findByCPDefinitionId_PrevAndNext(
			CPDefinitionDiagramPinId, CPDefinitionId, orderByComparator);
	}

	/**
	 * Removes all the cp definition diagram pins where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the number of cp definition diagram pins where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram pins
	 */
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Caches the cp definition diagram pin in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramPin the cp definition diagram pin
	 */
	public static void cacheResult(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		getPersistence().cacheResult(cpDefinitionDiagramPin);
	}

	/**
	 * Caches the cp definition diagram pins in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramPins the cp definition diagram pins
	 */
	public static void cacheResult(
		List<CPDefinitionDiagramPin> cpDefinitionDiagramPins) {

		getPersistence().cacheResult(cpDefinitionDiagramPins);
	}

	/**
	 * Creates a new cp definition diagram pin with the primary key. Does not add the cp definition diagram pin to the database.
	 *
	 * @param CPDefinitionDiagramPinId the primary key for the new cp definition diagram pin
	 * @return the new cp definition diagram pin
	 */
	public static CPDefinitionDiagramPin create(long CPDefinitionDiagramPinId) {
		return getPersistence().create(CPDefinitionDiagramPinId);
	}

	/**
	 * Removes the cp definition diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin that was removed
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	public static CPDefinitionDiagramPin remove(long CPDefinitionDiagramPinId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramPinException {

		return getPersistence().remove(CPDefinitionDiagramPinId);
	}

	public static CPDefinitionDiagramPin updateImpl(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		return getPersistence().updateImpl(cpDefinitionDiagramPin);
	}

	/**
	 * Returns the cp definition diagram pin with the primary key or throws a <code>NoSuchCPDefinitionDiagramPinException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin
	 * @throws NoSuchCPDefinitionDiagramPinException if a cp definition diagram pin with the primary key could not be found
	 */
	public static CPDefinitionDiagramPin findByPrimaryKey(
			long CPDefinitionDiagramPinId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramPinException {

		return getPersistence().findByPrimaryKey(CPDefinitionDiagramPinId);
	}

	/**
	 * Returns the cp definition diagram pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin, or <code>null</code> if a cp definition diagram pin with the primary key could not be found
	 */
	public static CPDefinitionDiagramPin fetchByPrimaryKey(
		long CPDefinitionDiagramPinId) {

		return getPersistence().fetchByPrimaryKey(CPDefinitionDiagramPinId);
	}

	/**
	 * Returns all the cp definition diagram pins.
	 *
	 * @return the cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @return the range of cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram pins
	 */
	public static List<CPDefinitionDiagramPin> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp definition diagram pins from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp definition diagram pins.
	 *
	 * @return the number of cp definition diagram pins
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPDefinitionDiagramPinPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPDefinitionDiagramPinPersistence, CPDefinitionDiagramPinPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CPDefinitionDiagramPinPersistence.class);

		ServiceTracker
			<CPDefinitionDiagramPinPersistence,
			 CPDefinitionDiagramPinPersistence> serviceTracker =
				new ServiceTracker
					<CPDefinitionDiagramPinPersistence,
					 CPDefinitionDiagramPinPersistence>(
						 bundle.getBundleContext(),
						 CPDefinitionDiagramPinPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}