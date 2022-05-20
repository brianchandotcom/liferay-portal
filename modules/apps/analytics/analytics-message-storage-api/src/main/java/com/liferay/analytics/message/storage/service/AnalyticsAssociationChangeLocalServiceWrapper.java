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

package com.liferay.analytics.message.storage.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AnalyticsAssociationChangeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsAssociationChangeLocalService
 * @generated
 */
public class AnalyticsAssociationChangeLocalServiceWrapper
	implements AnalyticsAssociationChangeLocalService,
			   ServiceWrapper<AnalyticsAssociationChangeLocalService> {

	public AnalyticsAssociationChangeLocalServiceWrapper() {
		this(null);
	}

	public AnalyticsAssociationChangeLocalServiceWrapper(
		AnalyticsAssociationChangeLocalService
			analyticsAssociationChangeLocalService) {

		_analyticsAssociationChangeLocalService =
			analyticsAssociationChangeLocalService;
	}

	/**
	 * Adds the analytics association change to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AnalyticsAssociationChangeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param analyticsAssociationChange the analytics association change
	 * @return the analytics association change that was added
	 */
	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
			addAnalyticsAssociationChange(
				com.liferay.analytics.message.storage.model.
					AnalyticsAssociationChange analyticsAssociationChange) {

		return _analyticsAssociationChangeLocalService.
			addAnalyticsAssociationChange(analyticsAssociationChange);
	}

	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
			addAnalyticsAssociationChange(
				long companyId, java.util.Date createDate, long userId,
				String associationClassName, long associationClassPK,
				String className, long classPK) {

		return _analyticsAssociationChangeLocalService.
			addAnalyticsAssociationChange(
				companyId, createDate, userId, associationClassName,
				associationClassPK, className, classPK);
	}

	/**
	 * Creates a new analytics association change with the primary key. Does not add the analytics association change to the database.
	 *
	 * @param analyticsAssociationChangeId the primary key for the new analytics association change
	 * @return the new analytics association change
	 */
	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
			createAnalyticsAssociationChange(
				long analyticsAssociationChangeId) {

		return _analyticsAssociationChangeLocalService.
			createAnalyticsAssociationChange(analyticsAssociationChangeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsAssociationChangeLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the analytics association change from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AnalyticsAssociationChangeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param analyticsAssociationChange the analytics association change
	 * @return the analytics association change that was removed
	 */
	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
			deleteAnalyticsAssociationChange(
				com.liferay.analytics.message.storage.model.
					AnalyticsAssociationChange analyticsAssociationChange) {

		return _analyticsAssociationChangeLocalService.
			deleteAnalyticsAssociationChange(analyticsAssociationChange);
	}

	/**
	 * Deletes the analytics association change with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AnalyticsAssociationChangeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change that was removed
	 * @throws PortalException if a analytics association change with the primary key could not be found
	 */
	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
				deleteAnalyticsAssociationChange(
					long analyticsAssociationChangeId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsAssociationChangeLocalService.
			deleteAnalyticsAssociationChange(analyticsAssociationChangeId);
	}

	@Override
	public void deleteAnalyticsAssociationChanges(
		long companyId, String associationClassName, long associationClassPK) {

		_analyticsAssociationChangeLocalService.
			deleteAnalyticsAssociationChanges(
				companyId, associationClassName, associationClassPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsAssociationChangeLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _analyticsAssociationChangeLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _analyticsAssociationChangeLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _analyticsAssociationChangeLocalService.dynamicQuery();
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

		return _analyticsAssociationChangeLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsAssociationChangeModelImpl</code>.
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

		return _analyticsAssociationChangeLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsAssociationChangeModelImpl</code>.
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

		return _analyticsAssociationChangeLocalService.dynamicQuery(
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

		return _analyticsAssociationChangeLocalService.dynamicQueryCount(
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

		return _analyticsAssociationChangeLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
			fetchAnalyticsAssociationChange(long analyticsAssociationChangeId) {

		return _analyticsAssociationChangeLocalService.
			fetchAnalyticsAssociationChange(analyticsAssociationChangeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _analyticsAssociationChangeLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the analytics association change with the primary key.
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change
	 * @throws PortalException if a analytics association change with the primary key could not be found
	 */
	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
				getAnalyticsAssociationChange(long analyticsAssociationChangeId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsAssociationChangeLocalService.
			getAnalyticsAssociationChange(analyticsAssociationChangeId);
	}

	/**
	 * Returns a range of all the analytics association changes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of analytics association changes
	 */
	@Override
	public java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsAssociationChange>
			getAnalyticsAssociationChanges(int start, int end) {

		return _analyticsAssociationChangeLocalService.
			getAnalyticsAssociationChanges(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsAssociationChange>
			getAnalyticsAssociationChanges(
				long companyId, java.util.Date modifiedDate,
				String associationClassName, int start, int end) {

		return _analyticsAssociationChangeLocalService.
			getAnalyticsAssociationChanges(
				companyId, modifiedDate, associationClassName, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsAssociationChange>
			getAnalyticsAssociationChanges(
				long companyId, String associationClassName, int start,
				int end) {

		return _analyticsAssociationChangeLocalService.
			getAnalyticsAssociationChanges(
				companyId, associationClassName, start, end);
	}

	/**
	 * Returns the number of analytics association changes.
	 *
	 * @return the number of analytics association changes
	 */
	@Override
	public int getAnalyticsAssociationChangesCount() {
		return _analyticsAssociationChangeLocalService.
			getAnalyticsAssociationChangesCount();
	}

	@Override
	public int getAnalyticsAssociationChangesCount(
		long companyId, java.util.Date modifiedDate,
		String associationClassName) {

		return _analyticsAssociationChangeLocalService.
			getAnalyticsAssociationChangesCount(
				companyId, modifiedDate, associationClassName);
	}

	@Override
	public int getAnalyticsAssociationChangesCount(
		long companyId, String associationClassName) {

		return _analyticsAssociationChangeLocalService.
			getAnalyticsAssociationChangesCount(
				companyId, associationClassName);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _analyticsAssociationChangeLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _analyticsAssociationChangeLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsAssociationChangeLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the analytics association change in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AnalyticsAssociationChangeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param analyticsAssociationChange the analytics association change
	 * @return the analytics association change that was updated
	 */
	@Override
	public
		com.liferay.analytics.message.storage.model.AnalyticsAssociationChange
			updateAnalyticsAssociationChange(
				com.liferay.analytics.message.storage.model.
					AnalyticsAssociationChange analyticsAssociationChange) {

		return _analyticsAssociationChangeLocalService.
			updateAnalyticsAssociationChange(analyticsAssociationChange);
	}

	@Override
	public AnalyticsAssociationChangeLocalService getWrappedService() {
		return _analyticsAssociationChangeLocalService;
	}

	@Override
	public void setWrappedService(
		AnalyticsAssociationChangeLocalService
			analyticsAssociationChangeLocalService) {

		_analyticsAssociationChangeLocalService =
			analyticsAssociationChangeLocalService;
	}

	private AnalyticsAssociationChangeLocalService
		_analyticsAssociationChangeLocalService;

}