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
 * Provides a wrapper for {@link LargeAmountColumnEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LargeAmountColumnEntityLocalService
 * @generated
 */
public class LargeAmountColumnEntityLocalServiceWrapper
	implements LargeAmountColumnEntityLocalService,
			   ServiceWrapper<LargeAmountColumnEntityLocalService> {

	public LargeAmountColumnEntityLocalServiceWrapper(
		LargeAmountColumnEntityLocalService
			largeAmountColumnEntityLocalService) {

		_largeAmountColumnEntityLocalService =
			largeAmountColumnEntityLocalService;
	}

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
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity addLargeAmountColumnEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LargeAmountColumnEntity largeAmountColumnEntity) {

		return _largeAmountColumnEntityLocalService.addLargeAmountColumnEntity(
			largeAmountColumnEntity);
	}

	/**
	 * Creates a new large amount column entity with the primary key. Does not add the large amount column entity to the database.
	 *
	 * @param largeAmountColumnEntityId the primary key for the new large amount column entity
	 * @return the new large amount column entity
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity createLargeAmountColumnEntity(
				long largeAmountColumnEntityId) {

		return _largeAmountColumnEntityLocalService.
			createLargeAmountColumnEntity(largeAmountColumnEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _largeAmountColumnEntityLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity deleteLargeAmountColumnEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LargeAmountColumnEntity largeAmountColumnEntity) {

		return _largeAmountColumnEntityLocalService.
			deleteLargeAmountColumnEntity(largeAmountColumnEntity);
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
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity deleteLargeAmountColumnEntity(
					long largeAmountColumnEntityId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _largeAmountColumnEntityLocalService.
			deleteLargeAmountColumnEntity(largeAmountColumnEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _largeAmountColumnEntityLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _largeAmountColumnEntityLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _largeAmountColumnEntityLocalService.dynamicQuery();
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

		return _largeAmountColumnEntityLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _largeAmountColumnEntityLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _largeAmountColumnEntityLocalService.dynamicQuery(
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

		return _largeAmountColumnEntityLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _largeAmountColumnEntityLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity fetchLargeAmountColumnEntity(
				long largeAmountColumnEntityId) {

		return _largeAmountColumnEntityLocalService.
			fetchLargeAmountColumnEntity(largeAmountColumnEntityId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _largeAmountColumnEntityLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _largeAmountColumnEntityLocalService.
			getIndexableActionableDynamicQuery();
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
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity> getLargeAmountColumnEntities(
				int start, int end) {

		return _largeAmountColumnEntityLocalService.
			getLargeAmountColumnEntities(start, end);
	}

	/**
	 * Returns the number of large amount column entities.
	 *
	 * @return the number of large amount column entities
	 */
	@Override
	public int getLargeAmountColumnEntitiesCount() {
		return _largeAmountColumnEntityLocalService.
			getLargeAmountColumnEntitiesCount();
	}

	/**
	 * Returns the large amount column entity with the primary key.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity
	 * @throws PortalException if a large amount column entity with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity getLargeAmountColumnEntity(
					long largeAmountColumnEntityId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _largeAmountColumnEntityLocalService.getLargeAmountColumnEntity(
			largeAmountColumnEntityId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _largeAmountColumnEntityLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _largeAmountColumnEntityLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			LargeAmountColumnEntity updateLargeAmountColumnEntity(
				com.liferay.portal.tools.service.builder.test.model.
					LargeAmountColumnEntity largeAmountColumnEntity) {

		return _largeAmountColumnEntityLocalService.
			updateLargeAmountColumnEntity(largeAmountColumnEntity);
	}

	@Override
	public LargeAmountColumnEntityLocalService getWrappedService() {
		return _largeAmountColumnEntityLocalService;
	}

	@Override
	public void setWrappedService(
		LargeAmountColumnEntityLocalService
			largeAmountColumnEntityLocalService) {

		_largeAmountColumnEntityLocalService =
			largeAmountColumnEntityLocalService;
	}

	private LargeAmountColumnEntityLocalService
		_largeAmountColumnEntityLocalService;

}