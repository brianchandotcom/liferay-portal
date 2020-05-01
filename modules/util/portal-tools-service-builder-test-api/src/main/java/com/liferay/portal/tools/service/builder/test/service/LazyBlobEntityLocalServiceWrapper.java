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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LazyBlobEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntityLocalService
 * @generated
 */
public class LazyBlobEntityLocalServiceWrapper
	implements LazyBlobEntityLocalService,
			   ServiceWrapper<LazyBlobEntityLocalService> {

	public LazyBlobEntityLocalServiceWrapper(
		LazyBlobEntityLocalService lazyBlobEntityLocalService) {

		_lazyBlobEntityLocalService = lazyBlobEntityLocalService;
	}

	/**
	 * Adds the lazy blob entity to the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 * @return the lazy blob entity that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
		addLazyBlobEntity(
			com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
				lazyBlobEntity) {

		return _lazyBlobEntityLocalService.addLazyBlobEntity(lazyBlobEntity);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
		addLazyBlobEntity(
			long groupId, byte[] bytes,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _lazyBlobEntityLocalService.addLazyBlobEntity(
			groupId, bytes, serviceContext);
	}

	/**
	 * Creates a new lazy blob entity with the primary key. Does not add the lazy blob entity to the database.
	 *
	 * @param lazyBlobEntityId the primary key for the new lazy blob entity
	 * @return the new lazy blob entity
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
		createLazyBlobEntity(long lazyBlobEntityId) {

		return _lazyBlobEntityLocalService.createLazyBlobEntity(
			lazyBlobEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _lazyBlobEntityLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the lazy blob entity from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 * @return the lazy blob entity that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
		deleteLazyBlobEntity(
			com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
				lazyBlobEntity) {

		return _lazyBlobEntityLocalService.deleteLazyBlobEntity(lazyBlobEntity);
	}

	/**
	 * Deletes the lazy blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity that was removed
	 * @throws PortalException if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			deleteLazyBlobEntity(long lazyBlobEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _lazyBlobEntityLocalService.deleteLazyBlobEntity(
			lazyBlobEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _lazyBlobEntityLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _lazyBlobEntityLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _lazyBlobEntityLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _lazyBlobEntityLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _lazyBlobEntityLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _lazyBlobEntityLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _lazyBlobEntityLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _lazyBlobEntityLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
		fetchLazyBlobEntity(long lazyBlobEntityId) {

		return _lazyBlobEntityLocalService.fetchLazyBlobEntity(
			lazyBlobEntityId);
	}

	/**
	 * Returns the lazy blob entity matching the UUID and group.
	 *
	 * @param uuid the lazy blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
		fetchLazyBlobEntityByUuidAndGroupId(String uuid, long groupId) {

		return _lazyBlobEntityLocalService.fetchLazyBlobEntityByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _lazyBlobEntityLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		LazyBlobEntityBlob1BlobModel getBlob1BlobModel(
			java.io.Serializable primaryKey) {

		return _lazyBlobEntityLocalService.getBlob1BlobModel(primaryKey);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		LazyBlobEntityBlob2BlobModel getBlob2BlobModel(
			java.io.Serializable primaryKey) {

		return _lazyBlobEntityLocalService.getBlob2BlobModel(primaryKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _lazyBlobEntityLocalService.getIndexableActionableDynamicQuery();
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
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity>
			getLazyBlobEntities(int start, int end) {

		return _lazyBlobEntityLocalService.getLazyBlobEntities(start, end);
	}

	/**
	 * Returns the number of lazy blob entities.
	 *
	 * @return the number of lazy blob entities
	 */
	@Override
	public int getLazyBlobEntitiesCount() {
		return _lazyBlobEntityLocalService.getLazyBlobEntitiesCount();
	}

	/**
	 * Returns the lazy blob entity with the primary key.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity
	 * @throws PortalException if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			getLazyBlobEntity(long lazyBlobEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _lazyBlobEntityLocalService.getLazyBlobEntity(lazyBlobEntityId);
	}

	/**
	 * Returns the lazy blob entity matching the UUID and group.
	 *
	 * @param uuid the lazy blob entity's UUID
	 * @param groupId the primary key of the group
	 * @return the matching lazy blob entity
	 * @throws PortalException if a matching lazy blob entity could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
			getLazyBlobEntityByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _lazyBlobEntityLocalService.getLazyBlobEntityByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _lazyBlobEntityLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _lazyBlobEntityLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.io.InputStream openBlob1InputStream(long lazyBlobEntityId) {
		return _lazyBlobEntityLocalService.openBlob1InputStream(
			lazyBlobEntityId);
	}

	@Override
	public java.io.InputStream openBlob2InputStream(long lazyBlobEntityId) {
		return _lazyBlobEntityLocalService.openBlob2InputStream(
			lazyBlobEntityId);
	}

	/**
	 * Updates the lazy blob entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 * @return the lazy blob entity that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
		updateLazyBlobEntity(
			com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity
				lazyBlobEntity) {

		return _lazyBlobEntityLocalService.updateLazyBlobEntity(lazyBlobEntity);
	}

	@Override
	public LazyBlobEntityLocalService getWrappedService() {
		return _lazyBlobEntityLocalService;
	}

	@Override
	public void setWrappedService(
		LazyBlobEntityLocalService lazyBlobEntityLocalService) {

		_lazyBlobEntityLocalService = lazyBlobEntityLocalService;
	}

	private LazyBlobEntityLocalService _lazyBlobEntityLocalService;

}