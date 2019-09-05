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

import com.liferay.app.builder.model.AppBuilderAppDeployment;
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
 * The persistence utility for the app builder app deployment service. This utility wraps <code>com.liferay.app.builder.service.persistence.impl.AppBuilderAppDeploymentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeploymentPersistence
 * @generated
 */
public class AppBuilderAppDeploymentUtil {

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
	public static void clearCache(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		getPersistence().clearCache(appBuilderAppDeployment);
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
	public static Map<Serializable, AppBuilderAppDeployment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AppBuilderAppDeployment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AppBuilderAppDeployment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AppBuilderAppDeployment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AppBuilderAppDeployment update(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		return getPersistence().update(appBuilderAppDeployment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AppBuilderAppDeployment update(
		AppBuilderAppDeployment appBuilderAppDeployment,
		ServiceContext serviceContext) {

		return getPersistence().update(appBuilderAppDeployment, serviceContext);
	}

	/**
	 * Returns all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId) {

		return getPersistence().findByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns a range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @return the range of matching app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	public static AppBuilderAppDeployment findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDeployment> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppDeploymentException {

		return getPersistence().findByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the first app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public static AppBuilderAppDeployment fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	public static AppBuilderAppDeployment findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDeployment> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppDeploymentException {

		return getPersistence().findByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public static AppBuilderAppDeployment fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the app builder app deployments before and after the current app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the current app builder app deployment
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app deployment
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	public static AppBuilderAppDeployment[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppDeploymentId, long appBuilderAppId,
			OrderByComparator<AppBuilderAppDeployment> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppDeploymentException {

		return getPersistence().findByAppBuilderAppId_PrevAndNext(
			appBuilderAppDeploymentId, appBuilderAppId, orderByComparator);
	}

	/**
	 * Removes all the app builder app deployments where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public static void removeByAppBuilderAppId(long appBuilderAppId) {
		getPersistence().removeByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns the number of app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app deployments
	 */
	public static int countByAppBuilderAppId(long appBuilderAppId) {
		return getPersistence().countByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or throws a <code>NoSuchAppDeploymentException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	public static AppBuilderAppDeployment findByA_T(
			long appBuilderAppId, String type)
		throws com.liferay.app.builder.exception.NoSuchAppDeploymentException {

		return getPersistence().findByA_T(appBuilderAppId, type);
	}

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public static AppBuilderAppDeployment fetchByA_T(
		long appBuilderAppId, String type) {

		return getPersistence().fetchByA_T(appBuilderAppId, type);
	}

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	public static AppBuilderAppDeployment fetchByA_T(
		long appBuilderAppId, String type, boolean useFinderCache) {

		return getPersistence().fetchByA_T(
			appBuilderAppId, type, useFinderCache);
	}

	/**
	 * Removes the app builder app deployment where appBuilderAppId = &#63; and type = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the app builder app deployment that was removed
	 */
	public static AppBuilderAppDeployment removeByA_T(
			long appBuilderAppId, String type)
		throws com.liferay.app.builder.exception.NoSuchAppDeploymentException {

		return getPersistence().removeByA_T(appBuilderAppId, type);
	}

	/**
	 * Returns the number of app builder app deployments where appBuilderAppId = &#63; and type = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the number of matching app builder app deployments
	 */
	public static int countByA_T(long appBuilderAppId, String type) {
		return getPersistence().countByA_T(appBuilderAppId, type);
	}

	/**
	 * Caches the app builder app deployment in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 */
	public static void cacheResult(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		getPersistence().cacheResult(appBuilderAppDeployment);
	}

	/**
	 * Caches the app builder app deployments in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDeployments the app builder app deployments
	 */
	public static void cacheResult(
		List<AppBuilderAppDeployment> appBuilderAppDeployments) {

		getPersistence().cacheResult(appBuilderAppDeployments);
	}

	/**
	 * Creates a new app builder app deployment with the primary key. Does not add the app builder app deployment to the database.
	 *
	 * @param appBuilderAppDeploymentId the primary key for the new app builder app deployment
	 * @return the new app builder app deployment
	 */
	public static AppBuilderAppDeployment create(
		long appBuilderAppDeploymentId) {

		return getPersistence().create(appBuilderAppDeploymentId);
	}

	/**
	 * Removes the app builder app deployment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment that was removed
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	public static AppBuilderAppDeployment remove(long appBuilderAppDeploymentId)
		throws com.liferay.app.builder.exception.NoSuchAppDeploymentException {

		return getPersistence().remove(appBuilderAppDeploymentId);
	}

	public static AppBuilderAppDeployment updateImpl(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		return getPersistence().updateImpl(appBuilderAppDeployment);
	}

	/**
	 * Returns the app builder app deployment with the primary key or throws a <code>NoSuchAppDeploymentException</code> if it could not be found.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	public static AppBuilderAppDeployment findByPrimaryKey(
			long appBuilderAppDeploymentId)
		throws com.liferay.app.builder.exception.NoSuchAppDeploymentException {

		return getPersistence().findByPrimaryKey(appBuilderAppDeploymentId);
	}

	/**
	 * Returns the app builder app deployment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment, or <code>null</code> if a app builder app deployment with the primary key could not be found
	 */
	public static AppBuilderAppDeployment fetchByPrimaryKey(
		long appBuilderAppDeploymentId) {

		return getPersistence().fetchByPrimaryKey(appBuilderAppDeploymentId);
	}

	/**
	 * Returns all the app builder app deployments.
	 *
	 * @return the app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @return the range of app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app deployments
	 */
	public static List<AppBuilderAppDeployment> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the app builder app deployments from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of app builder app deployments.
	 *
	 * @return the number of app builder app deployments
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AppBuilderAppDeploymentPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AppBuilderAppDeploymentPersistence, AppBuilderAppDeploymentPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AppBuilderAppDeploymentPersistence.class);

		ServiceTracker
			<AppBuilderAppDeploymentPersistence,
			 AppBuilderAppDeploymentPersistence> serviceTracker =
				new ServiceTracker
					<AppBuilderAppDeploymentPersistence,
					 AppBuilderAppDeploymentPersistence>(
						 bundle.getBundleContext(),
						 AppBuilderAppDeploymentPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}