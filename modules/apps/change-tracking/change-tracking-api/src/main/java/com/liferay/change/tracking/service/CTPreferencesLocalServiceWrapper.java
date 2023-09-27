/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link CTPreferencesLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTPreferencesLocalService
 * @generated
 */
public class CTPreferencesLocalServiceWrapper
	implements CTPreferencesLocalService,
			   ServiceWrapper<CTPreferencesLocalService> {

	public CTPreferencesLocalServiceWrapper() {
		this(null);
	}

	public CTPreferencesLocalServiceWrapper(
		CTPreferencesLocalService ctPreferencesLocalService) {

		_ctPreferencesLocalService = ctPreferencesLocalService;
	}

	@Override
	public com.liferay.change.tracking.model.CTPreferences addCTPreference(
		long companyId, long userId) {

		return _ctPreferencesLocalService.addCTPreference(companyId, userId);
	}

	/**
	 * Adds the ct preferences to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctPreferences the ct preferences
	 * @return the ct preferences that was added
	 */
	@Override
	public com.liferay.change.tracking.model.CTPreferences addCTPreferences(
		com.liferay.change.tracking.model.CTPreferences ctPreferences) {

		return _ctPreferencesLocalService.addCTPreferences(ctPreferences);
	}

	/**
	 * Creates a new ct preferences with the primary key. Does not add the ct preferences to the database.
	 *
	 * @param ctPreferencesId the primary key for the new ct preferences
	 * @return the new ct preferences
	 */
	@Override
	public com.liferay.change.tracking.model.CTPreferences createCTPreferences(
		long ctPreferencesId) {

		return _ctPreferencesLocalService.createCTPreferences(ctPreferencesId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctPreferencesLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the ct preferences from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctPreferences the ct preferences
	 * @return the ct preferences that was removed
	 */
	@Override
	public com.liferay.change.tracking.model.CTPreferences deleteCTPreferences(
		com.liferay.change.tracking.model.CTPreferences ctPreferences) {

		return _ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
	}

	/**
	 * Deletes the ct preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences that was removed
	 * @throws PortalException if a ct preferences with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTPreferences deleteCTPreferences(
			long ctPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctPreferencesLocalService.deleteCTPreferences(ctPreferencesId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctPreferencesLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ctPreferencesLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _ctPreferencesLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ctPreferencesLocalService.dynamicQuery();
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

		return _ctPreferencesLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTPreferencesModelImpl</code>.
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

		return _ctPreferencesLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTPreferencesModelImpl</code>.
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

		return _ctPreferencesLocalService.dynamicQuery(
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

		return _ctPreferencesLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ctPreferencesLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.change.tracking.model.CTPreferences fetchCTPreferences(
		long ctPreferencesId) {

		return _ctPreferencesLocalService.fetchCTPreferences(ctPreferencesId);
	}

	@Override
	public com.liferay.change.tracking.model.CTPreferences fetchCTPreferences(
		long companyId, long userId) {

		return _ctPreferencesLocalService.fetchCTPreferences(companyId, userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ctPreferencesLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ct preferences with the primary key.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences
	 * @throws PortalException if a ct preferences with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTPreferences getCTPreferences(
			long ctPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctPreferencesLocalService.getCTPreferences(ctPreferencesId);
	}

	@Override
	public com.liferay.change.tracking.model.CTPreferences getCTPreferences(
		long companyId, long userId) {

		return _ctPreferencesLocalService.getCTPreferences(companyId, userId);
	}

	/**
	 * Returns a range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of ct preferenceses
	 */
	@Override
	public java.util.List<com.liferay.change.tracking.model.CTPreferences>
		getCTPreferenceses(int start, int end) {

		return _ctPreferencesLocalService.getCTPreferenceses(start, end);
	}

	/**
	 * Returns the number of ct preferenceses.
	 *
	 * @return the number of ct preferenceses
	 */
	@Override
	public int getCTPreferencesesCount() {
		return _ctPreferencesLocalService.getCTPreferencesesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ctPreferencesLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctPreferencesLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctPreferencesLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void resetCTPreferences(long ctCollectionId) {
		_ctPreferencesLocalService.resetCTPreferences(ctCollectionId);
	}

	/**
	 * Updates the ct preferences in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctPreferences the ct preferences
	 * @return the ct preferences that was updated
	 */
	@Override
	public com.liferay.change.tracking.model.CTPreferences updateCTPreferences(
		com.liferay.change.tracking.model.CTPreferences ctPreferences) {

		return _ctPreferencesLocalService.updateCTPreferences(ctPreferences);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ctPreferencesLocalService.getBasePersistence();
	}

	@Override
	public CTPreferencesLocalService getWrappedService() {
		return _ctPreferencesLocalService;
	}

	@Override
	public void setWrappedService(
		CTPreferencesLocalService ctPreferencesLocalService) {

		_ctPreferencesLocalService = ctPreferencesLocalService;
	}

	private CTPreferencesLocalService _ctPreferencesLocalService;

}