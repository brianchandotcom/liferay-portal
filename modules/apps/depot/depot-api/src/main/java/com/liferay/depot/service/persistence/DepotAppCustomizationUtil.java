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

package com.liferay.depot.service.persistence;

import com.liferay.depot.model.DepotAppCustomization;
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
 * The persistence utility for the depot app customization service. This utility wraps <code>com.liferay.depot.service.persistence.impl.DepotAppCustomizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotAppCustomizationPersistence
 * @generated
 */
public class DepotAppCustomizationUtil {

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
	public static void clearCache(DepotAppCustomization depotAppCustomization) {
		getPersistence().clearCache(depotAppCustomization);
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
	public static Map<Serializable, DepotAppCustomization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DepotAppCustomization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DepotAppCustomization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DepotAppCustomization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DepotAppCustomization update(
		DepotAppCustomization depotAppCustomization) {

		return getPersistence().update(depotAppCustomization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DepotAppCustomization update(
		DepotAppCustomization depotAppCustomization,
		ServiceContext serviceContext) {

		return getPersistence().update(depotAppCustomization, serviceContext);
	}

	/**
	 * Returns all the depot app customizations where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot app customizations
	 */
	public static List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId) {

		return getPersistence().findByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns a range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @return the range of matching depot app customizations
	 */
	public static List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return getPersistence().findByDepotEntryId(depotEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot app customizations
	 */
	public static List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot app customizations
	 */
	public static List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	public static DepotAppCustomization findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<DepotAppCustomization> orderByComparator)
		throws com.liferay.depot.exception.NoSuchAppCustomizationException {

		return getPersistence().findByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the first depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public static DepotAppCustomization fetchByDepotEntryId_First(
		long depotEntryId,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		return getPersistence().fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the last depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	public static DepotAppCustomization findByDepotEntryId_Last(
			long depotEntryId,
			OrderByComparator<DepotAppCustomization> orderByComparator)
		throws com.liferay.depot.exception.NoSuchAppCustomizationException {

		return getPersistence().findByDepotEntryId_Last(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the last depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public static DepotAppCustomization fetchByDepotEntryId_Last(
		long depotEntryId,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		return getPersistence().fetchByDepotEntryId_Last(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the depot app customizations before and after the current depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotAppCustomizationId the primary key of the current depot app customization
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot app customization
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	public static DepotAppCustomization[] findByDepotEntryId_PrevAndNext(
			long depotAppCustomizationId, long depotEntryId,
			OrderByComparator<DepotAppCustomization> orderByComparator)
		throws com.liferay.depot.exception.NoSuchAppCustomizationException {

		return getPersistence().findByDepotEntryId_PrevAndNext(
			depotAppCustomizationId, depotEntryId, orderByComparator);
	}

	/**
	 * Removes all the depot app customizations where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public static void removeByDepotEntryId(long depotEntryId) {
		getPersistence().removeByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the number of depot app customizations where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot app customizations
	 */
	public static int countByDepotEntryId(long depotEntryId) {
		return getPersistence().countByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or throws a <code>NoSuchAppCustomizationException</code> if it could not be found.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	public static DepotAppCustomization findByD_PI(
			long depotEntryId, String portletId)
		throws com.liferay.depot.exception.NoSuchAppCustomizationException {

		return getPersistence().findByD_PI(depotEntryId, portletId);
	}

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public static DepotAppCustomization fetchByD_PI(
		long depotEntryId, String portletId) {

		return getPersistence().fetchByD_PI(depotEntryId, portletId);
	}

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	public static DepotAppCustomization fetchByD_PI(
		long depotEntryId, String portletId, boolean useFinderCache) {

		return getPersistence().fetchByD_PI(
			depotEntryId, portletId, useFinderCache);
	}

	/**
	 * Removes the depot app customization where depotEntryId = &#63; and portletId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the depot app customization that was removed
	 */
	public static DepotAppCustomization removeByD_PI(
			long depotEntryId, String portletId)
		throws com.liferay.depot.exception.NoSuchAppCustomizationException {

		return getPersistence().removeByD_PI(depotEntryId, portletId);
	}

	/**
	 * Returns the number of depot app customizations where depotEntryId = &#63; and portletId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the number of matching depot app customizations
	 */
	public static int countByD_PI(long depotEntryId, String portletId) {
		return getPersistence().countByD_PI(depotEntryId, portletId);
	}

	/**
	 * Caches the depot app customization in the entity cache if it is enabled.
	 *
	 * @param depotAppCustomization the depot app customization
	 */
	public static void cacheResult(
		DepotAppCustomization depotAppCustomization) {

		getPersistence().cacheResult(depotAppCustomization);
	}

	/**
	 * Caches the depot app customizations in the entity cache if it is enabled.
	 *
	 * @param depotAppCustomizations the depot app customizations
	 */
	public static void cacheResult(
		List<DepotAppCustomization> depotAppCustomizations) {

		getPersistence().cacheResult(depotAppCustomizations);
	}

	/**
	 * Creates a new depot app customization with the primary key. Does not add the depot app customization to the database.
	 *
	 * @param depotAppCustomizationId the primary key for the new depot app customization
	 * @return the new depot app customization
	 */
	public static DepotAppCustomization create(long depotAppCustomizationId) {
		return getPersistence().create(depotAppCustomizationId);
	}

	/**
	 * Removes the depot app customization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization that was removed
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	public static DepotAppCustomization remove(long depotAppCustomizationId)
		throws com.liferay.depot.exception.NoSuchAppCustomizationException {

		return getPersistence().remove(depotAppCustomizationId);
	}

	public static DepotAppCustomization updateImpl(
		DepotAppCustomization depotAppCustomization) {

		return getPersistence().updateImpl(depotAppCustomization);
	}

	/**
	 * Returns the depot app customization with the primary key or throws a <code>NoSuchAppCustomizationException</code> if it could not be found.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	public static DepotAppCustomization findByPrimaryKey(
			long depotAppCustomizationId)
		throws com.liferay.depot.exception.NoSuchAppCustomizationException {

		return getPersistence().findByPrimaryKey(depotAppCustomizationId);
	}

	/**
	 * Returns the depot app customization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization, or <code>null</code> if a depot app customization with the primary key could not be found
	 */
	public static DepotAppCustomization fetchByPrimaryKey(
		long depotAppCustomizationId) {

		return getPersistence().fetchByPrimaryKey(depotAppCustomizationId);
	}

	/**
	 * Returns all the depot app customizations.
	 *
	 * @return the depot app customizations
	 */
	public static List<DepotAppCustomization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @return the range of depot app customizations
	 */
	public static List<DepotAppCustomization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot app customizations
	 */
	public static List<DepotAppCustomization> findAll(
		int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot app customizations
	 */
	public static List<DepotAppCustomization> findAll(
		int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the depot app customizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of depot app customizations.
	 *
	 * @return the number of depot app customizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DepotAppCustomizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DepotAppCustomizationPersistence, DepotAppCustomizationPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DepotAppCustomizationPersistence.class);

		ServiceTracker
			<DepotAppCustomizationPersistence, DepotAppCustomizationPersistence>
				serviceTracker =
					new ServiceTracker
						<DepotAppCustomizationPersistence,
						 DepotAppCustomizationPersistence>(
							 bundle.getBundleContext(),
							 DepotAppCustomizationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}