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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TestEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TestEntityLocalService
 * @generated
 */
public class TestEntityLocalServiceWrapper
	implements ServiceWrapper<TestEntityLocalService>, TestEntityLocalService {

	public TestEntityLocalServiceWrapper(
		TestEntityLocalService testEntityLocalService) {

		_testEntityLocalService = testEntityLocalService;
	}

	/**
	 * Adds the test entity to the database. Also notifies the appropriate model listeners.
	 *
	 * @param testEntity the test entity
	 * @return the test entity that was added
	 */
	@Override
	public com.liferay.external.data.source.test.model.TestEntity addTestEntity(
		com.liferay.external.data.source.test.model.TestEntity testEntity) {

		return _testEntityLocalService.addTestEntity(testEntity);
	}

	/**
	 * Creates a new test entity with the primary key. Does not add the test entity to the database.
	 *
	 * @param id the primary key for the new test entity
	 * @return the new test entity
	 */
	@Override
	public com.liferay.external.data.source.test.model.TestEntity
		createTestEntity(long id) {

		return _testEntityLocalService.createTestEntity(id);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _testEntityLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the test entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the test entity
	 * @return the test entity that was removed
	 * @throws PortalException if a test entity with the primary key could not be found
	 */
	@Override
	public com.liferay.external.data.source.test.model.TestEntity
			deleteTestEntity(long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _testEntityLocalService.deleteTestEntity(id);
	}

	/**
	 * Deletes the test entity from the database. Also notifies the appropriate model listeners.
	 *
	 * @param testEntity the test entity
	 * @return the test entity that was removed
	 */
	@Override
	public com.liferay.external.data.source.test.model.TestEntity
		deleteTestEntity(
			com.liferay.external.data.source.test.model.TestEntity testEntity) {

		return _testEntityLocalService.deleteTestEntity(testEntity);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _testEntityLocalService.dynamicQuery();
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

		return _testEntityLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _testEntityLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _testEntityLocalService.dynamicQuery(
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

		return _testEntityLocalService.dynamicQueryCount(dynamicQuery);
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

		return _testEntityLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.external.data.source.test.model.TestEntity
		fetchTestEntity(long id) {

		return _testEntityLocalService.fetchTestEntity(id);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _testEntityLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _testEntityLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _testEntityLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _testEntityLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public java.util.List
		<com.liferay.external.data.source.test.model.TestEntity>
			getTestEntities(int start, int end) {

		return _testEntityLocalService.getTestEntities(start, end);
	}

	/**
	 * Returns the number of test entities.
	 *
	 * @return the number of test entities
	 */
	@Override
	public int getTestEntitiesCount() {
		return _testEntityLocalService.getTestEntitiesCount();
	}

	/**
	 * Returns the test entity with the primary key.
	 *
	 * @param id the primary key of the test entity
	 * @return the test entity
	 * @throws PortalException if a test entity with the primary key could not be found
	 */
	@Override
	public com.liferay.external.data.source.test.model.TestEntity getTestEntity(
			long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _testEntityLocalService.getTestEntity(id);
	}

	/**
	 * Updates the test entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param testEntity the test entity
	 * @return the test entity that was updated
	 */
	@Override
	public com.liferay.external.data.source.test.model.TestEntity
		updateTestEntity(
			com.liferay.external.data.source.test.model.TestEntity testEntity) {

		return _testEntityLocalService.updateTestEntity(testEntity);
	}

	@Override
	public TestEntityLocalService getWrappedService() {
		return _testEntityLocalService;
	}

	@Override
	public void setWrappedService(
		TestEntityLocalService testEntityLocalService) {

		_testEntityLocalService = testEntityLocalService;
	}

	private TestEntityLocalService _testEntityLocalService;

}