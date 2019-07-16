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

package com.liferay.data.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link DEDataRecordQueryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataRecordQueryLocalService
 * @generated
 */
@ProviderType
public class DEDataRecordQueryLocalServiceWrapper
	implements DEDataRecordQueryLocalService,
			   ServiceWrapper<DEDataRecordQueryLocalService> {

	public DEDataRecordQueryLocalServiceWrapper(
		DEDataRecordQueryLocalService deDataRecordQueryLocalService) {

		_deDataRecordQueryLocalService = deDataRecordQueryLocalService;
	}

	/**
	 * Adds the de data record query to the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataRecordQuery the de data record query
	 * @return the de data record query that was added
	 */
	@Override
	public com.liferay.data.engine.model.DEDataRecordQuery addDEDataRecordQuery(
		com.liferay.data.engine.model.DEDataRecordQuery deDataRecordQuery) {

		return _deDataRecordQueryLocalService.addDEDataRecordQuery(
			deDataRecordQuery);
	}

	/**
	 * Creates a new de data record query with the primary key. Does not add the de data record query to the database.
	 *
	 * @param deDataRecordQueryId the primary key for the new de data record query
	 * @return the new de data record query
	 */
	@Override
	public com.liferay.data.engine.model.DEDataRecordQuery
		createDEDataRecordQuery(long deDataRecordQueryId) {

		return _deDataRecordQueryLocalService.createDEDataRecordQuery(
			deDataRecordQueryId);
	}

	/**
	 * Deletes the de data record query from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataRecordQuery the de data record query
	 * @return the de data record query that was removed
	 */
	@Override
	public com.liferay.data.engine.model.DEDataRecordQuery
		deleteDEDataRecordQuery(
			com.liferay.data.engine.model.DEDataRecordQuery deDataRecordQuery) {

		return _deDataRecordQueryLocalService.deleteDEDataRecordQuery(
			deDataRecordQuery);
	}

	/**
	 * Deletes the de data record query with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query that was removed
	 * @throws PortalException if a de data record query with the primary key could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEDataRecordQuery
			deleteDEDataRecordQuery(long deDataRecordQueryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataRecordQueryLocalService.deleteDEDataRecordQuery(
			deDataRecordQueryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataRecordQueryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _deDataRecordQueryLocalService.dynamicQuery();
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

		return _deDataRecordQueryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _deDataRecordQueryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _deDataRecordQueryLocalService.dynamicQuery(
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

		return _deDataRecordQueryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _deDataRecordQueryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.data.engine.model.DEDataRecordQuery
		fetchDEDataRecordQuery(long deDataRecordQueryId) {

		return _deDataRecordQueryLocalService.fetchDEDataRecordQuery(
			deDataRecordQueryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _deDataRecordQueryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the de data record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @return the range of de data record queries
	 */
	@Override
	public java.util.List<com.liferay.data.engine.model.DEDataRecordQuery>
		getDEDataRecordQueries(int start, int end) {

		return _deDataRecordQueryLocalService.getDEDataRecordQueries(
			start, end);
	}

	/**
	 * Returns the number of de data record queries.
	 *
	 * @return the number of de data record queries
	 */
	@Override
	public int getDEDataRecordQueriesCount() {
		return _deDataRecordQueryLocalService.getDEDataRecordQueriesCount();
	}

	/**
	 * Returns the de data record query with the primary key.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query
	 * @throws PortalException if a de data record query with the primary key could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEDataRecordQuery getDEDataRecordQuery(
			long deDataRecordQueryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataRecordQueryLocalService.getDEDataRecordQuery(
			deDataRecordQueryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _deDataRecordQueryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _deDataRecordQueryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataRecordQueryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the de data record query in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param deDataRecordQuery the de data record query
	 * @return the de data record query that was updated
	 */
	@Override
	public com.liferay.data.engine.model.DEDataRecordQuery
		updateDEDataRecordQuery(
			com.liferay.data.engine.model.DEDataRecordQuery deDataRecordQuery) {

		return _deDataRecordQueryLocalService.updateDEDataRecordQuery(
			deDataRecordQuery);
	}

	@Override
	public DEDataRecordQueryLocalService getWrappedService() {
		return _deDataRecordQueryLocalService;
	}

	@Override
	public void setWrappedService(
		DEDataRecordQueryLocalService deDataRecordQueryLocalService) {

		_deDataRecordQueryLocalService = deDataRecordQueryLocalService;
	}

	private DEDataRecordQueryLocalService _deDataRecordQueryLocalService;

}