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
 * Provides a wrapper for {@link ManyColumnsEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ManyColumnsEntityLocalService
 * @generated
 */
public class ManyColumnsEntityLocalServiceWrapper
	implements ManyColumnsEntityLocalService,
			   ServiceWrapper<ManyColumnsEntityLocalService> {

	public ManyColumnsEntityLocalServiceWrapper(
		ManyColumnsEntityLocalService manyColumnsEntityLocalService) {

		_manyColumnsEntityLocalService = manyColumnsEntityLocalService;
	}

	/**
	 * Adds the many columns entity to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntity the many columns entity
	 * @return the many columns entity that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity
		addManyColumnsEntity(
			com.liferay.portal.tools.service.builder.test.model.
				ManyColumnsEntity manyColumnsEntity) {

		return _manyColumnsEntityLocalService.addManyColumnsEntity(
			manyColumnsEntity);
	}

	/**
	 * Creates a new many columns entity with the primary key. Does not add the many columns entity to the database.
	 *
	 * @param manyColumnsEntityId the primary key for the new many columns entity
	 * @return the new many columns entity
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity
		createManyColumnsEntity(long manyColumnsEntityId) {

		return _manyColumnsEntityLocalService.createManyColumnsEntity(
			manyColumnsEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntityLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the many columns entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntityId the primary key of the many columns entity
	 * @return the many columns entity that was removed
	 * @throws PortalException if a many columns entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity
			deleteManyColumnsEntity(long manyColumnsEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntityLocalService.deleteManyColumnsEntity(
			manyColumnsEntityId);
	}

	/**
	 * Deletes the many columns entity from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntity the many columns entity
	 * @return the many columns entity that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity
		deleteManyColumnsEntity(
			com.liferay.portal.tools.service.builder.test.model.
				ManyColumnsEntity manyColumnsEntity) {

		return _manyColumnsEntityLocalService.deleteManyColumnsEntity(
			manyColumnsEntity);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntityLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _manyColumnsEntityLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _manyColumnsEntityLocalService.dynamicQuery();
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

		return _manyColumnsEntityLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntityModelImpl</code>.
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

		return _manyColumnsEntityLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntityModelImpl</code>.
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

		return _manyColumnsEntityLocalService.dynamicQuery(
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

		return _manyColumnsEntityLocalService.dynamicQueryCount(dynamicQuery);
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

		return _manyColumnsEntityLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity
		fetchManyColumnsEntity(long manyColumnsEntityId) {

		return _manyColumnsEntityLocalService.fetchManyColumnsEntity(
			manyColumnsEntityId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _manyColumnsEntityLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _manyColumnsEntityLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the many columns entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entities
	 * @param end the upper bound of the range of many columns entities (not inclusive)
	 * @return the range of many columns entities
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity>
			getManyColumnsEntities(int start, int end) {

		return _manyColumnsEntityLocalService.getManyColumnsEntities(
			start, end);
	}

	/**
	 * Returns the number of many columns entities.
	 *
	 * @return the number of many columns entities
	 */
	@Override
	public int getManyColumnsEntitiesCount() {
		return _manyColumnsEntityLocalService.getManyColumnsEntitiesCount();
	}

	/**
	 * Returns the many columns entity with the primary key.
	 *
	 * @param manyColumnsEntityId the primary key of the many columns entity
	 * @return the many columns entity
	 * @throws PortalException if a many columns entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity
			getManyColumnsEntity(long manyColumnsEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntityLocalService.getManyColumnsEntity(
			manyColumnsEntityId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _manyColumnsEntityLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the many columns entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntity the many columns entity
	 * @return the many columns entity that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity
		updateManyColumnsEntity(
			com.liferay.portal.tools.service.builder.test.model.
				ManyColumnsEntity manyColumnsEntity) {

		return _manyColumnsEntityLocalService.updateManyColumnsEntity(
			manyColumnsEntity);
	}

	@Override
	public ManyColumnsEntityLocalService getWrappedService() {
		return _manyColumnsEntityLocalService;
	}

	@Override
	public void setWrappedService(
		ManyColumnsEntityLocalService manyColumnsEntityLocalService) {

		_manyColumnsEntityLocalService = manyColumnsEntityLocalService;
	}

	private ManyColumnsEntityLocalService _manyColumnsEntityLocalService;

}