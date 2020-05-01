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
 * Provides the local service utility for EagerBlobEntity. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.EagerBlobEntityLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntityLocalService
 * @generated
 */
public class EagerBlobEntityLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.EagerBlobEntityLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the eager blob entity to the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 * @return the eager blob entity that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
			addEagerBlobEntity(
				com.liferay.portal.tools.service.builder.test.model.
					EagerBlobEntity eagerBlobEntity) {

		return getService().addEagerBlobEntity(eagerBlobEntity);
	}

	/**
	 * Creates a new eager blob entity with the primary key. Does not add the eager blob entity to the database.
	 *
	 * @param eagerBlobEntityId the primary key for the new eager blob entity
	 * @return the new eager blob entity
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
			createEagerBlobEntity(long eagerBlobEntityId) {

		return getService().createEagerBlobEntity(eagerBlobEntityId);
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
	 * Deletes the eager blob entity from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 * @return the eager blob entity that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
			deleteEagerBlobEntity(
				com.liferay.portal.tools.service.builder.test.model.
					EagerBlobEntity eagerBlobEntity) {

		return getService().deleteEagerBlobEntity(eagerBlobEntity);
	}

	/**
	 * Deletes the eager blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity that was removed
	 * @throws PortalException if a eager blob entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
				deleteEagerBlobEntity(long eagerBlobEntityId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteEagerBlobEntity(eagerBlobEntityId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityModelImpl</code>.
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
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
			fetchEagerBlobEntity(long eagerBlobEntityId) {

		return getService().fetchEagerBlobEntity(eagerBlobEntityId);
	}

	/**
	 * Returns the eager blob entity matching the UUID and group.
	 *
	 * @param uuid the eager blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
			fetchEagerBlobEntityByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchEagerBlobEntityByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of eager blob entities
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity>
			getEagerBlobEntities(int start, int end) {

		return getService().getEagerBlobEntities(start, end);
	}

	/**
	 * Returns the number of eager blob entities.
	 *
	 * @return the number of eager blob entities
	 */
	public static int getEagerBlobEntitiesCount() {
		return getService().getEagerBlobEntitiesCount();
	}

	/**
	 * Returns the eager blob entity with the primary key.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity
	 * @throws PortalException if a eager blob entity with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
				getEagerBlobEntity(long eagerBlobEntityId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEagerBlobEntity(eagerBlobEntityId);
	}

	/**
	 * Returns the eager blob entity matching the UUID and group.
	 *
	 * @param uuid the eager blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching eager blob entity
	 * @throws PortalException if a matching eager blob entity could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
				getEagerBlobEntityByUuidAndGroupId(String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEagerBlobEntityByUuidAndGroupId(uuid, groupId);
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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the eager blob entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 * @return the eager blob entity that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity
			updateEagerBlobEntity(
				com.liferay.portal.tools.service.builder.test.model.
					EagerBlobEntity eagerBlobEntity) {

		return getService().updateEagerBlobEntity(eagerBlobEntity);
	}

	public static EagerBlobEntityLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<EagerBlobEntityLocalService, EagerBlobEntityLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			EagerBlobEntityLocalService.class);

		ServiceTracker<EagerBlobEntityLocalService, EagerBlobEntityLocalService>
			serviceTracker =
				new ServiceTracker
					<EagerBlobEntityLocalService, EagerBlobEntityLocalService>(
						bundle.getBundleContext(),
						EagerBlobEntityLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}