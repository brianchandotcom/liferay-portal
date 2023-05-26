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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link PortalPreferenceValueLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferenceValueLocalService
 * @generated
 */
public class PortalPreferenceValueLocalServiceWrapper
	implements PortalPreferenceValueLocalService,
			   ServiceWrapper<PortalPreferenceValueLocalService> {

	public PortalPreferenceValueLocalServiceWrapper() {
		this(null);
	}

	public PortalPreferenceValueLocalServiceWrapper(
		PortalPreferenceValueLocalService portalPreferenceValueLocalService) {

		_portalPreferenceValueLocalService = portalPreferenceValueLocalService;
	}

	/**
	 * Adds the portal preference value to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortalPreferenceValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portalPreferenceValue the portal preference value
	 * @return the portal preference value that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.PortalPreferenceValue
		addPortalPreferenceValue(
			com.liferay.portal.kernel.model.PortalPreferenceValue
				portalPreferenceValue) {

		return _portalPreferenceValueLocalService.addPortalPreferenceValue(
			portalPreferenceValue);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portalPreferenceValueLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new portal preference value with the primary key. Does not add the portal preference value to the database.
	 *
	 * @param portalPreferenceValueId the primary key for the new portal preference value
	 * @return the new portal preference value
	 */
	@Override
	public com.liferay.portal.kernel.model.PortalPreferenceValue
		createPortalPreferenceValue(long portalPreferenceValueId) {

		return _portalPreferenceValueLocalService.createPortalPreferenceValue(
			portalPreferenceValueId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portalPreferenceValueLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the portal preference value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortalPreferenceValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portalPreferenceValueId the primary key of the portal preference value
	 * @return the portal preference value that was removed
	 * @throws PortalException if a portal preference value with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.PortalPreferenceValue
			deletePortalPreferenceValue(long portalPreferenceValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portalPreferenceValueLocalService.deletePortalPreferenceValue(
			portalPreferenceValueId);
	}

	/**
	 * Deletes the portal preference value from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortalPreferenceValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portalPreferenceValue the portal preference value
	 * @return the portal preference value that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.PortalPreferenceValue
		deletePortalPreferenceValue(
			com.liferay.portal.kernel.model.PortalPreferenceValue
				portalPreferenceValue) {

		return _portalPreferenceValueLocalService.deletePortalPreferenceValue(
			portalPreferenceValue);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _portalPreferenceValueLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _portalPreferenceValueLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _portalPreferenceValueLocalService.dynamicQuery();
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

		return _portalPreferenceValueLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortalPreferenceValueModelImpl</code>.
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

		return _portalPreferenceValueLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortalPreferenceValueModelImpl</code>.
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

		return _portalPreferenceValueLocalService.dynamicQuery(
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

		return _portalPreferenceValueLocalService.dynamicQueryCount(
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

		return _portalPreferenceValueLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.PortalPreferenceValue
		fetchPortalPreferenceValue(long portalPreferenceValueId) {

		return _portalPreferenceValueLocalService.fetchPortalPreferenceValue(
			portalPreferenceValueId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _portalPreferenceValueLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _portalPreferenceValueLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _portalPreferenceValueLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portalPreferenceValueLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.portlet.PortalPreferences
		getPortalPreferences(
			com.liferay.portal.kernel.model.PortalPreferences portalPreferences,
			boolean signedIn) {

		return _portalPreferenceValueLocalService.getPortalPreferences(
			portalPreferences, signedIn);
	}

	/**
	 * Returns the portal preference value with the primary key.
	 *
	 * @param portalPreferenceValueId the primary key of the portal preference value
	 * @return the portal preference value
	 * @throws PortalException if a portal preference value with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.PortalPreferenceValue
			getPortalPreferenceValue(long portalPreferenceValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _portalPreferenceValueLocalService.getPortalPreferenceValue(
			portalPreferenceValueId);
	}

	/**
	 * Returns a range of all the portal preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @return the range of portal preference values
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.PortalPreferenceValue>
		getPortalPreferenceValues(int start, int end) {

		return _portalPreferenceValueLocalService.getPortalPreferenceValues(
			start, end);
	}

	/**
	 * Returns the number of portal preference values.
	 *
	 * @return the number of portal preference values
	 */
	@Override
	public int getPortalPreferenceValuesCount() {
		return _portalPreferenceValueLocalService.
			getPortalPreferenceValuesCount();
	}

	/**
	 * Updates the portal preference value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PortalPreferenceValueLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param portalPreferenceValue the portal preference value
	 * @return the portal preference value that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.PortalPreferenceValue
		updatePortalPreferenceValue(
			com.liferay.portal.kernel.model.PortalPreferenceValue
				portalPreferenceValue) {

		return _portalPreferenceValueLocalService.updatePortalPreferenceValue(
			portalPreferenceValue);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _portalPreferenceValueLocalService.getBasePersistence();
	}

	@Override
	public PortalPreferenceValueLocalService getWrappedService() {
		return _portalPreferenceValueLocalService;
	}

	@Override
	public void setWrappedService(
		PortalPreferenceValueLocalService portalPreferenceValueLocalService) {

		_portalPreferenceValueLocalService = portalPreferenceValueLocalService;
	}

	private PortalPreferenceValueLocalService
		_portalPreferenceValueLocalService;

}