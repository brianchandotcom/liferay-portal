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
 * Provides the local service utility for LazyBlobEntity. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.LazyBlobEntityLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntityLocalService
 * @generated
 */
public class LazyBlobEntityLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.LazyBlobEntityLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the lazy blob entity to the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 * @return the lazy blob entity that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			addLazyBlobEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LazyBlobEntity lazyBlobEntity) {

		return getService().addLazyBlobEntity(lazyBlobEntity);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			addLazyBlobEntity(
				long groupId, byte[] bytes,
				com.liferay.portal.kernel.service.ServiceContext
					serviceContext) {

		return getService().addLazyBlobEntity(groupId, bytes, serviceContext);
	}

	/**
	 * Creates a new lazy blob entity with the primary key. Does not add the lazy blob entity to the database.
	 *
	 * @param lazyBlobEntityId the primary key for the new lazy blob entity
	 * @return the new lazy blob entity
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			createLazyBlobEntity(long lazyBlobEntityId) {

		return getService().createLazyBlobEntity(lazyBlobEntityId);
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
	 * Deletes the lazy blob entity from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 * @return the lazy blob entity that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			deleteLazyBlobEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LazyBlobEntity lazyBlobEntity) {

		return getService().deleteLazyBlobEntity(lazyBlobEntity);
	}

	/**
	 * Deletes the lazy blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity that was removed
	 * @throws PortalException if a lazy blob entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
				deleteLazyBlobEntity(long lazyBlobEntityId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLazyBlobEntity(lazyBlobEntityId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LazyBlobEntityModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LazyBlobEntityModelImpl</code>.
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
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			fetchLazyBlobEntity(long lazyBlobEntityId) {

		return getService().fetchLazyBlobEntity(lazyBlobEntityId);
	}

	/**
	 * Returns the lazy blob entity matching the UUID and group.
	 *
	 * @param uuid the lazy blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			fetchLazyBlobEntityByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchLazyBlobEntityByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.tools.service.builder.test.model.
		LazyBlobEntityBlob1BlobModel getBlob1BlobModel(
			java.io.Serializable primaryKey) {

		return getService().getBlob1BlobModel(primaryKey);
	}

	public static com.liferay.portal.tools.service.builder.test.model.
		LazyBlobEntityBlob2BlobModel getBlob2BlobModel(
			java.io.Serializable primaryKey) {

		return getService().getBlob2BlobModel(primaryKey);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @return the range of lazy blob entities
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity>
			getLazyBlobEntities(int start, int end) {

		return getService().getLazyBlobEntities(start, end);
	}

	/**
	 * Returns the number of lazy blob entities.
	 *
	 * @return the number of lazy blob entities
	 */
	public static int getLazyBlobEntitiesCount() {
		return getService().getLazyBlobEntitiesCount();
	}

	/**
	 * Returns the lazy blob entity with the primary key.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity
	 * @throws PortalException if a lazy blob entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
				getLazyBlobEntity(long lazyBlobEntityId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLazyBlobEntity(lazyBlobEntityId);
	}

	/**
	 * Returns the lazy blob entity matching the UUID and group.
	 *
	 * @param uuid the lazy blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching lazy blob entity
	 * @throws PortalException if a matching lazy blob entity could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
				getLazyBlobEntityByUuidAndGroupId(String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLazyBlobEntityByUuidAndGroupId(uuid, groupId);
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

	public static java.io.InputStream openBlob1InputStream(
		long lazyBlobEntityId) {

		return getService().openBlob1InputStream(lazyBlobEntityId);
	}

	public static java.io.InputStream openBlob2InputStream(
		long lazyBlobEntityId) {

		return getService().openBlob2InputStream(lazyBlobEntityId);
	}

	/**
	 * Updates the lazy blob entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 * @return the lazy blob entity that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			updateLazyBlobEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LazyBlobEntity lazyBlobEntity) {

		return getService().updateLazyBlobEntity(lazyBlobEntity);
	}

	public static LazyBlobEntityLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LazyBlobEntityLocalService, LazyBlobEntityLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LazyBlobEntityLocalService.class);

		ServiceTracker<LazyBlobEntityLocalService, LazyBlobEntityLocalService>
			serviceTracker =
				new ServiceTracker
					<LazyBlobEntityLocalService, LazyBlobEntityLocalService>(
						bundle.getBundleContext(),
						LazyBlobEntityLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}