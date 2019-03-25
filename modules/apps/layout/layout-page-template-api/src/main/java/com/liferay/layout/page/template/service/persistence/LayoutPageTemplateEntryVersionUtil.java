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

package com.liferay.layout.page.template.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion;
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
 * The persistence utility for the layout page template entry version service. This utility wraps <code>com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplateEntryVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryVersionPersistence
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryVersionUtil {

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
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		getPersistence().clearCache(layoutPageTemplateEntryVersion);
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
	public static Map<Serializable, LayoutPageTemplateEntryVersion>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutPageTemplateEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutPageTemplateEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutPageTemplateEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutPageTemplateEntryVersion update(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		return getPersistence().update(layoutPageTemplateEntryVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutPageTemplateEntryVersion update(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(
			layoutPageTemplateEntryVersion, serviceContext);
	}

	/**
	 * Returns all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {

		return getPersistence().findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
	}

	/**
	 * Returns a range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @return the range of matching layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end) {

		return getPersistence().findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end,
			OrderByComparator<LayoutPageTemplateEntryVersion>
				orderByComparator) {

		return getPersistence().findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end,
			OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator,
			boolean retrieveFromCache) {

		return getPersistence().findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a matching layout page template entry version could not be found
	 */
	public static LayoutPageTemplateEntryVersion
			findBylayoutPageTemplateEntryId_First(
				long layoutPageTemplateEntryId,
				OrderByComparator<LayoutPageTemplateEntryVersion>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateEntryVersionException {

		return getPersistence().findBylayoutPageTemplateEntryId_First(
			layoutPageTemplateEntryId, orderByComparator);
	}

	/**
	 * Returns the first layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry version, or <code>null</code> if a matching layout page template entry version could not be found
	 */
	public static LayoutPageTemplateEntryVersion
		fetchBylayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateEntryVersion>
				orderByComparator) {

		return getPersistence().fetchBylayoutPageTemplateEntryId_First(
			layoutPageTemplateEntryId, orderByComparator);
	}

	/**
	 * Returns the last layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a matching layout page template entry version could not be found
	 */
	public static LayoutPageTemplateEntryVersion
			findBylayoutPageTemplateEntryId_Last(
				long layoutPageTemplateEntryId,
				OrderByComparator<LayoutPageTemplateEntryVersion>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateEntryVersionException {

		return getPersistence().findBylayoutPageTemplateEntryId_Last(
			layoutPageTemplateEntryId, orderByComparator);
	}

	/**
	 * Returns the last layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry version, or <code>null</code> if a matching layout page template entry version could not be found
	 */
	public static LayoutPageTemplateEntryVersion
		fetchBylayoutPageTemplateEntryId_Last(
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateEntryVersion>
				orderByComparator) {

		return getPersistence().fetchBylayoutPageTemplateEntryId_Last(
			layoutPageTemplateEntryId, orderByComparator);
	}

	/**
	 * Returns the layout page template entry versions before and after the current layout page template entry version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the current layout page template entry version
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	public static LayoutPageTemplateEntryVersion[]
			findBylayoutPageTemplateEntryId_PrevAndNext(
				long layoutPageTemplateEntryVersionId,
				long layoutPageTemplateEntryId,
				OrderByComparator<LayoutPageTemplateEntryVersion>
					orderByComparator)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateEntryVersionException {

		return getPersistence().findBylayoutPageTemplateEntryId_PrevAndNext(
			layoutPageTemplateEntryVersionId, layoutPageTemplateEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the layout page template entry versions where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	public static void removeBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		getPersistence().removeBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
	}

	/**
	 * Returns the number of layout page template entry versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching layout page template entry versions
	 */
	public static int countBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return getPersistence().countBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
	}

	/**
	 * Caches the layout page template entry version in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntryVersion the layout page template entry version
	 */
	public static void cacheResult(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		getPersistence().cacheResult(layoutPageTemplateEntryVersion);
	}

	/**
	 * Caches the layout page template entry versions in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntryVersions the layout page template entry versions
	 */
	public static void cacheResult(
		List<LayoutPageTemplateEntryVersion> layoutPageTemplateEntryVersions) {

		getPersistence().cacheResult(layoutPageTemplateEntryVersions);
	}

	/**
	 * Creates a new layout page template entry version with the primary key. Does not add the layout page template entry version to the database.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key for the new layout page template entry version
	 * @return the new layout page template entry version
	 */
	public static LayoutPageTemplateEntryVersion create(
		long layoutPageTemplateEntryVersionId) {

		return getPersistence().create(layoutPageTemplateEntryVersionId);
	}

	/**
	 * Removes the layout page template entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version that was removed
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	public static LayoutPageTemplateEntryVersion remove(
			long layoutPageTemplateEntryVersionId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateEntryVersionException {

		return getPersistence().remove(layoutPageTemplateEntryVersionId);
	}

	public static LayoutPageTemplateEntryVersion updateImpl(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		return getPersistence().updateImpl(layoutPageTemplateEntryVersion);
	}

	/**
	 * Returns the layout page template entry version with the primary key or throws a <code>NoSuchPageTemplateEntryVersionException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version
	 * @throws NoSuchPageTemplateEntryVersionException if a layout page template entry version with the primary key could not be found
	 */
	public static LayoutPageTemplateEntryVersion findByPrimaryKey(
			long layoutPageTemplateEntryVersionId)
		throws com.liferay.layout.page.template.exception.
			NoSuchPageTemplateEntryVersionException {

		return getPersistence().findByPrimaryKey(
			layoutPageTemplateEntryVersionId);
	}

	/**
	 * Returns the layout page template entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version, or <code>null</code> if a layout page template entry version with the primary key could not be found
	 */
	public static LayoutPageTemplateEntryVersion fetchByPrimaryKey(
		long layoutPageTemplateEntryVersionId) {

		return getPersistence().fetchByPrimaryKey(
			layoutPageTemplateEntryVersionId);
	}

	/**
	 * Returns all the layout page template entry versions.
	 *
	 * @return the layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @return the range of layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template entry versions
	 */
	public static List<LayoutPageTemplateEntryVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntryVersion> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the layout page template entry versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout page template entry versions.
	 *
	 * @return the number of layout page template entry versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutPageTemplateEntryVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutPageTemplateEntryVersionPersistence,
		 LayoutPageTemplateEntryVersionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutPageTemplateEntryVersionPersistence.class);

		ServiceTracker
			<LayoutPageTemplateEntryVersionPersistence,
			 LayoutPageTemplateEntryVersionPersistence> serviceTracker =
				new ServiceTracker
					<LayoutPageTemplateEntryVersionPersistence,
					 LayoutPageTemplateEntryVersionPersistence>(
						 bundle.getBundleContext(),
						 LayoutPageTemplateEntryVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}