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

package com.liferay.external.data.source.test.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for TestEntity. This utility wraps
 * <code>com.liferay.external.data.source.test.service.impl.TestEntityLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TestEntityLocalService
 * @generated
 */
public class TestEntityLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.external.data.source.test.service.impl.TestEntityLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the test entity to the database. Also notifies the appropriate model listeners.
	 *
	 * @param testEntity the test entity
	 * @return the test entity that was added
	 */
	public static com.liferay.external.data.source.test.model.TestEntity
		addTestEntity(
			com.liferay.external.data.source.test.model.TestEntity testEntity) {

		return getService().addTestEntity(testEntity);
	}

	/**
	 * Creates a new test entity with the primary key. Does not add the test entity to the database.
	 *
	 * @param id the primary key for the new test entity
	 * @return the new test entity
	 */
	public static com.liferay.external.data.source.test.model.TestEntity
		createTestEntity(long id) {

		return getService().createTestEntity(id);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the test entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the test entity
	 * @return the test entity that was removed
	 * @throws PortalException if a test entity with the primary key could not be found
	 */
	public static com.liferay.external.data.source.test.model.TestEntity
			deleteTestEntity(long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTestEntity(id);
	}

	/**
	 * Deletes the test entity from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testEntity the test entity
	 * @return the test entity that was removed
	 */
	public static com.liferay.external.data.source.test.model.TestEntity
		deleteTestEntity(
			com.liferay.external.data.source.test.model.TestEntity testEntity) {

		return getService().deleteTestEntity(testEntity);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.external.data.source.test.model.impl.TestEntityModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.external.data.source.test.model.impl.TestEntityModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.external.data.source.test.model.TestEntity
		fetchTestEntity(long id) {

		return getService().fetchTestEntity(id);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the test entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.external.data.source.test.model.impl.TestEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of test entities
	 * @param end the upper bound of the range of test entities (not inclusive)
	 * @return the range of test entities
	 */
	public static java.util.List
		<com.liferay.external.data.source.test.model.TestEntity>
			getTestEntities(int start, int end) {

		return getService().getTestEntities(start, end);
	}

	/**
	 * Returns the number of test entities.
	 *
	 * @return the number of test entities
	 */
	public static int getTestEntitiesCount() {
		return getService().getTestEntitiesCount();
	}

	/**
	 * Returns the test entity with the primary key.
	 *
	 * @param id the primary key of the test entity
	 * @return the test entity
	 * @throws PortalException if a test entity with the primary key could not be found
	 */
	public static com.liferay.external.data.source.test.model.TestEntity
			getTestEntity(long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTestEntity(id);
	}

	/**
	 * Updates the test entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param testEntity the test entity
	 * @return the test entity that was updated
	 */
	public static com.liferay.external.data.source.test.model.TestEntity
		updateTestEntity(
			com.liferay.external.data.source.test.model.TestEntity testEntity) {

		return getService().updateTestEntity(testEntity);
	}

	public static TestEntityLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<TestEntityLocalService, TestEntityLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TestEntityLocalService.class);

		ServiceTracker<TestEntityLocalService, TestEntityLocalService>
			serviceTracker =
				new ServiceTracker
					<TestEntityLocalService, TestEntityLocalService>(
						bundle.getBundleContext(), TestEntityLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}