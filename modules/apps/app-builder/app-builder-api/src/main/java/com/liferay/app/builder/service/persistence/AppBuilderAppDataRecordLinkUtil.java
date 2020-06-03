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

package com.liferay.app.builder.service.persistence;

import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
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
 * The persistence utility for the app builder app data record link service. This utility wraps <code>com.liferay.app.builder.service.persistence.impl.AppBuilderAppDataRecordLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLinkPersistence
 * @generated
 */
public class AppBuilderAppDataRecordLinkUtil {

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
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		getPersistence().clearCache(appBuilderAppDataRecordLink);
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
	public static Map<Serializable, AppBuilderAppDataRecordLink>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AppBuilderAppDataRecordLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AppBuilderAppDataRecordLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AppBuilderAppDataRecordLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AppBuilderAppDataRecordLink update(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		return getPersistence().update(appBuilderAppDataRecordLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AppBuilderAppDataRecordLink update(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink,
		ServiceContext serviceContext) {

		return getPersistence().update(
			appBuilderAppDataRecordLink, serviceContext);
	}

	/**
	 * Returns all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId) {

		return getPersistence().findByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns a range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of matching app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	public static AppBuilderAppDataRecordLink findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws com.liferay.app.builder.exception.
			NoSuchAppDataRecordLinkException {

		return getPersistence().findByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public static AppBuilderAppDataRecordLink fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	public static AppBuilderAppDataRecordLink findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws com.liferay.app.builder.exception.
			NoSuchAppDataRecordLinkException {

		return getPersistence().findByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public static AppBuilderAppDataRecordLink fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the app builder app data record links before and after the current app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the current app builder app data record link
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	public static AppBuilderAppDataRecordLink[]
			findByAppBuilderAppId_PrevAndNext(
				long appBuilderAppDataRecordLinkId, long appBuilderAppId,
				OrderByComparator<AppBuilderAppDataRecordLink>
					orderByComparator)
		throws com.liferay.app.builder.exception.
			NoSuchAppDataRecordLinkException {

		return getPersistence().findByAppBuilderAppId_PrevAndNext(
			appBuilderAppDataRecordLinkId, appBuilderAppId, orderByComparator);
	}

	/**
	 * Removes all the app builder app data record links where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public static void removeByAppBuilderAppId(long appBuilderAppId) {
		getPersistence().removeByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns the number of app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app data record links
	 */
	public static int countByAppBuilderAppId(long appBuilderAppId) {
		return getPersistence().countByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or throws a <code>NoSuchAppDataRecordLinkException</code> if it could not be found.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	public static AppBuilderAppDataRecordLink findByDDLRecordId(
			long ddlRecordId)
		throws com.liferay.app.builder.exception.
			NoSuchAppDataRecordLinkException {

		return getPersistence().findByDDLRecordId(ddlRecordId);
	}

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public static AppBuilderAppDataRecordLink fetchByDDLRecordId(
		long ddlRecordId) {

		return getPersistence().fetchByDDLRecordId(ddlRecordId);
	}

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	public static AppBuilderAppDataRecordLink fetchByDDLRecordId(
		long ddlRecordId, boolean useFinderCache) {

		return getPersistence().fetchByDDLRecordId(ddlRecordId, useFinderCache);
	}

	/**
	 * Removes the app builder app data record link where ddlRecordId = &#63; from the database.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the app builder app data record link that was removed
	 */
	public static AppBuilderAppDataRecordLink removeByDDLRecordId(
			long ddlRecordId)
		throws com.liferay.app.builder.exception.
			NoSuchAppDataRecordLinkException {

		return getPersistence().removeByDDLRecordId(ddlRecordId);
	}

	/**
	 * Returns the number of app builder app data record links where ddlRecordId = &#63;.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the number of matching app builder app data record links
	 */
	public static int countByDDLRecordId(long ddlRecordId) {
		return getPersistence().countByDDLRecordId(ddlRecordId);
	}

	/**
	 * Caches the app builder app data record link in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 */
	public static void cacheResult(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		getPersistence().cacheResult(appBuilderAppDataRecordLink);
	}

	/**
	 * Caches the app builder app data record links in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDataRecordLinks the app builder app data record links
	 */
	public static void cacheResult(
		List<AppBuilderAppDataRecordLink> appBuilderAppDataRecordLinks) {

		getPersistence().cacheResult(appBuilderAppDataRecordLinks);
	}

	/**
	 * Creates a new app builder app data record link with the primary key. Does not add the app builder app data record link to the database.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key for the new app builder app data record link
	 * @return the new app builder app data record link
	 */
	public static AppBuilderAppDataRecordLink create(
		long appBuilderAppDataRecordLinkId) {

		return getPersistence().create(appBuilderAppDataRecordLinkId);
	}

	/**
	 * Removes the app builder app data record link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link that was removed
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	public static AppBuilderAppDataRecordLink remove(
			long appBuilderAppDataRecordLinkId)
		throws com.liferay.app.builder.exception.
			NoSuchAppDataRecordLinkException {

		return getPersistence().remove(appBuilderAppDataRecordLinkId);
	}

	public static AppBuilderAppDataRecordLink updateImpl(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		return getPersistence().updateImpl(appBuilderAppDataRecordLink);
	}

	/**
	 * Returns the app builder app data record link with the primary key or throws a <code>NoSuchAppDataRecordLinkException</code> if it could not be found.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	public static AppBuilderAppDataRecordLink findByPrimaryKey(
			long appBuilderAppDataRecordLinkId)
		throws com.liferay.app.builder.exception.
			NoSuchAppDataRecordLinkException {

		return getPersistence().findByPrimaryKey(appBuilderAppDataRecordLinkId);
	}

	/**
	 * Returns the app builder app data record link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link, or <code>null</code> if a app builder app data record link with the primary key could not be found
	 */
	public static AppBuilderAppDataRecordLink fetchByPrimaryKey(
		long appBuilderAppDataRecordLinkId) {

		return getPersistence().fetchByPrimaryKey(
			appBuilderAppDataRecordLinkId);
	}

	/**
	 * Returns all the app builder app data record links.
	 *
	 * @return the app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the app builder app data record links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of app builder app data record links.
	 *
	 * @return the number of app builder app data record links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AppBuilderAppDataRecordLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AppBuilderAppDataRecordLinkPersistence,
		 AppBuilderAppDataRecordLinkPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AppBuilderAppDataRecordLinkPersistence.class);

		ServiceTracker
			<AppBuilderAppDataRecordLinkPersistence,
			 AppBuilderAppDataRecordLinkPersistence> serviceTracker =
				new ServiceTracker
					<AppBuilderAppDataRecordLinkPersistence,
					 AppBuilderAppDataRecordLinkPersistence>(
						 bundle.getBundleContext(),
						 AppBuilderAppDataRecordLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}