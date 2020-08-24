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

package com.liferay.portal.tools.service.builder.test.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LargeAmountColumnEntity. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.LargeAmountColumnEntityLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LargeAmountColumnEntityLocalService
 * @generated
 */
public class LargeAmountColumnEntityLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.LargeAmountColumnEntityLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the large amount column entity to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LargeAmountColumnEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param largeAmountColumnEntity the large amount column entity
	 * @return the large amount column entity that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity addLargeAmountColumnEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LargeAmountColumnEntity largeAmountColumnEntity) {

		return getService().addLargeAmountColumnEntity(largeAmountColumnEntity);
	}

	/**
	 * Creates a new large amount column entity with the primary key. Does not add the large amount column entity to the database.
	 *
	 * @param largeAmountColumnEntityId the primary key for the new large amount column entity
	 * @return the new large amount column entity
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity createLargeAmountColumnEntity(
				long largeAmountColumnEntityId) {

		return getService().createLargeAmountColumnEntity(
			largeAmountColumnEntityId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the large amount column entity from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LargeAmountColumnEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param largeAmountColumnEntity the large amount column entity
	 * @return the large amount column entity that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity deleteLargeAmountColumnEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LargeAmountColumnEntity largeAmountColumnEntity) {

		return getService().deleteLargeAmountColumnEntity(
			largeAmountColumnEntity);
	}

	/**
	 * Deletes the large amount column entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LargeAmountColumnEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity that was removed
	 * @throws PortalException if a large amount column entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity deleteLargeAmountColumnEntity(
					long largeAmountColumnEntityId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLargeAmountColumnEntity(
			largeAmountColumnEntityId);
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

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LargeAmountColumnEntityModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LargeAmountColumnEntityModelImpl</code>.
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

	public static
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity fetchLargeAmountColumnEntity(
				long largeAmountColumnEntityId) {

		return getService().fetchLargeAmountColumnEntity(
			largeAmountColumnEntityId);
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
	 * Returns a range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @return the range of large amount column entities
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity> getLargeAmountColumnEntities(
				int start, int end) {

		return getService().getLargeAmountColumnEntities(start, end);
	}

	/**
	 * Returns the number of large amount column entities.
	 *
	 * @return the number of large amount column entities
	 */
	public static int getLargeAmountColumnEntitiesCount() {
		return getService().getLargeAmountColumnEntitiesCount();
	}

	/**
	 * Returns the large amount column entity with the primary key.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity
	 * @throws PortalException if a large amount column entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity getLargeAmountColumnEntity(
					long largeAmountColumnEntityId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLargeAmountColumnEntity(
			largeAmountColumnEntityId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the large amount column entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LargeAmountColumnEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param largeAmountColumnEntity the large amount column entity
	 * @return the large amount column entity that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity updateLargeAmountColumnEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LargeAmountColumnEntity largeAmountColumnEntity) {

		return getService().updateLargeAmountColumnEntity(
			largeAmountColumnEntity);
	}

	public static LargeAmountColumnEntityLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LargeAmountColumnEntityLocalService,
		 LargeAmountColumnEntityLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LargeAmountColumnEntityLocalService.class);

		ServiceTracker
			<LargeAmountColumnEntityLocalService,
			 LargeAmountColumnEntityLocalService> serviceTracker =
				new ServiceTracker
					<LargeAmountColumnEntityLocalService,
					 LargeAmountColumnEntityLocalService>(
						 bundle.getBundleContext(),
						 LargeAmountColumnEntityLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}