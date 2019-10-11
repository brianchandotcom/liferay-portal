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

package com.liferay.html.preview.service.persistence;

import com.liferay.html.preview.model.HtmlPreviewEntry;
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
 * The persistence utility for the html preview entry service. This utility wraps <code>com.liferay.html.preview.service.persistence.impl.HtmlPreviewEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntryPersistence
 * @generated
 */
public class HtmlPreviewEntryUtil {

	/**
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
	public static void clearCache(HtmlPreviewEntry htmlPreviewEntry) {
		getPersistence().clearCache(htmlPreviewEntry);
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
	public static Map<Serializable, HtmlPreviewEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<HtmlPreviewEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<HtmlPreviewEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<HtmlPreviewEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<HtmlPreviewEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static HtmlPreviewEntry update(HtmlPreviewEntry htmlPreviewEntry) {
		return getPersistence().update(htmlPreviewEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static HtmlPreviewEntry update(
		HtmlPreviewEntry htmlPreviewEntry, ServiceContext serviceContext) {

		return getPersistence().update(htmlPreviewEntry, serviceContext);
	}

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchHtmlPreviewEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview entry
	 * @throws NoSuchHtmlPreviewEntryException if a matching html preview entry could not be found
	 */
	public static HtmlPreviewEntry findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws com.liferay.html.preview.exception.
			NoSuchHtmlPreviewEntryException {

		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview entry, or <code>null</code> if a matching html preview entry could not be found
	 */
	public static HtmlPreviewEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().fetchByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching html preview entry, or <code>null</code> if a matching html preview entry could not be found
	 */
	public static HtmlPreviewEntry fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache) {

		return getPersistence().fetchByG_C_C(
			groupId, classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the html preview entry that was removed
	 */
	public static HtmlPreviewEntry removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws com.liferay.html.preview.exception.
			NoSuchHtmlPreviewEntryException {

		return getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the number of html preview entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching html preview entries
	 */
	public static int countByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Caches the html preview entry in the entity cache if it is enabled.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 */
	public static void cacheResult(HtmlPreviewEntry htmlPreviewEntry) {
		getPersistence().cacheResult(htmlPreviewEntry);
	}

	/**
	 * Caches the html preview entries in the entity cache if it is enabled.
	 *
	 * @param htmlPreviewEntries the html preview entries
	 */
	public static void cacheResult(List<HtmlPreviewEntry> htmlPreviewEntries) {
		getPersistence().cacheResult(htmlPreviewEntries);
	}

	/**
	 * Creates a new html preview entry with the primary key. Does not add the html preview entry to the database.
	 *
	 * @param htmlPreviewEntryId the primary key for the new html preview entry
	 * @return the new html preview entry
	 */
	public static HtmlPreviewEntry create(long htmlPreviewEntryId) {
		return getPersistence().create(htmlPreviewEntryId);
	}

	/**
	 * Removes the html preview entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry that was removed
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	public static HtmlPreviewEntry remove(long htmlPreviewEntryId)
		throws com.liferay.html.preview.exception.
			NoSuchHtmlPreviewEntryException {

		return getPersistence().remove(htmlPreviewEntryId);
	}

	public static HtmlPreviewEntry updateImpl(
		HtmlPreviewEntry htmlPreviewEntry) {

		return getPersistence().updateImpl(htmlPreviewEntry);
	}

	/**
	 * Returns the html preview entry with the primary key or throws a <code>NoSuchHtmlPreviewEntryException</code> if it could not be found.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	public static HtmlPreviewEntry findByPrimaryKey(long htmlPreviewEntryId)
		throws com.liferay.html.preview.exception.
			NoSuchHtmlPreviewEntryException {

		return getPersistence().findByPrimaryKey(htmlPreviewEntryId);
	}

	/**
	 * Returns the html preview entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry, or <code>null</code> if a html preview entry with the primary key could not be found
	 */
	public static HtmlPreviewEntry fetchByPrimaryKey(long htmlPreviewEntryId) {
		return getPersistence().fetchByPrimaryKey(htmlPreviewEntryId);
	}

	/**
	 * Returns all the html preview entries.
	 *
	 * @return the html preview entries
	 */
	public static List<HtmlPreviewEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @return the range of html preview entries
	 */
	public static List<HtmlPreviewEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of html preview entries
	 */
	public static List<HtmlPreviewEntry> findAll(
		int start, int end,
		OrderByComparator<HtmlPreviewEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of html preview entries
	 */
	public static List<HtmlPreviewEntry> findAll(
		int start, int end,
		OrderByComparator<HtmlPreviewEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the html preview entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of html preview entries.
	 *
	 * @return the number of html preview entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static HtmlPreviewEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<HtmlPreviewEntryPersistence, HtmlPreviewEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			HtmlPreviewEntryPersistence.class);

		ServiceTracker<HtmlPreviewEntryPersistence, HtmlPreviewEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<HtmlPreviewEntryPersistence, HtmlPreviewEntryPersistence>(
						bundle.getBundleContext(),
						HtmlPreviewEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}