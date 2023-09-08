/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FaroPreferencesLocalService}.
 *
 * @author Matthew Kong
 * @see FaroPreferencesLocalService
 * @generated
 */
public class FaroPreferencesLocalServiceWrapper
	implements FaroPreferencesLocalService,
			   ServiceWrapper<FaroPreferencesLocalService> {

	public FaroPreferencesLocalServiceWrapper() {
		this(null);
	}

	public FaroPreferencesLocalServiceWrapper(
		FaroPreferencesLocalService faroPreferencesLocalService) {

		_faroPreferencesLocalService = faroPreferencesLocalService;
	}

	/**
	 * Adds the faro preferences to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferences the faro preferences
	 * @return the faro preferences that was added
	 */
	@Override
	public com.liferay.osb.faro.model.FaroPreferences addFaroPreferences(
		com.liferay.osb.faro.model.FaroPreferences faroPreferences) {

		return _faroPreferencesLocalService.addFaroPreferences(faroPreferences);
	}

	/**
	 * Creates a new faro preferences with the primary key. Does not add the faro preferences to the database.
	 *
	 * @param faroPreferencesId the primary key for the new faro preferences
	 * @return the new faro preferences
	 */
	@Override
	public com.liferay.osb.faro.model.FaroPreferences createFaroPreferences(
		long faroPreferencesId) {

		return _faroPreferencesLocalService.createFaroPreferences(
			faroPreferencesId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroPreferencesLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the faro preferences from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferences the faro preferences
	 * @return the faro preferences that was removed
	 */
	@Override
	public com.liferay.osb.faro.model.FaroPreferences deleteFaroPreferences(
		com.liferay.osb.faro.model.FaroPreferences faroPreferences) {

		return _faroPreferencesLocalService.deleteFaroPreferences(
			faroPreferences);
	}

	/**
	 * Deletes the faro preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences that was removed
	 * @throws PortalException if a faro preferences with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroPreferences deleteFaroPreferences(
			long faroPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroPreferencesLocalService.deleteFaroPreferences(
			faroPreferencesId);
	}

	@Override
	public com.liferay.osb.faro.model.FaroPreferences deleteFaroPreferences(
		long groupId, long ownerId) {

		return _faroPreferencesLocalService.deleteFaroPreferences(
			groupId, ownerId);
	}

	@Override
	public void deleteFaroPreferencesByGroupId(long groupId) {
		_faroPreferencesLocalService.deleteFaroPreferencesByGroupId(groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroPreferencesLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _faroPreferencesLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _faroPreferencesLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _faroPreferencesLocalService.dynamicQuery();
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

		return _faroPreferencesLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl</code>.
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

		return _faroPreferencesLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl</code>.
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

		return _faroPreferencesLocalService.dynamicQuery(
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

		return _faroPreferencesLocalService.dynamicQueryCount(dynamicQuery);
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

		return _faroPreferencesLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.faro.model.FaroPreferences fetchFaroPreferences(
		long faroPreferencesId) {

		return _faroPreferencesLocalService.fetchFaroPreferences(
			faroPreferencesId);
	}

	@Override
	public com.liferay.osb.faro.model.FaroPreferences fetchFaroPreferences(
		long groupId, long ownerId) {

		return _faroPreferencesLocalService.fetchFaroPreferences(
			groupId, ownerId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _faroPreferencesLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the faro preferences with the primary key.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences
	 * @throws PortalException if a faro preferences with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroPreferences getFaroPreferences(
			long faroPreferencesId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroPreferencesLocalService.getFaroPreferences(
			faroPreferencesId);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroPreferences>
		getFaroPreferencesByGroupId(long groupId) {

		return _faroPreferencesLocalService.getFaroPreferencesByGroupId(
			groupId);
	}

	/**
	 * Returns a range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @return the range of faro preferenceses
	 */
	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroPreferences>
		getFaroPreferenceses(int start, int end) {

		return _faroPreferencesLocalService.getFaroPreferenceses(start, end);
	}

	/**
	 * Returns the number of faro preferenceses.
	 *
	 * @return the number of faro preferenceses
	 */
	@Override
	public int getFaroPreferencesesCount() {
		return _faroPreferencesLocalService.getFaroPreferencesesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _faroPreferencesLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _faroPreferencesLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroPreferencesLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.osb.faro.model.FaroPreferences savePreferences(
			long userId, long groupId, long ownerId, String preferences)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroPreferencesLocalService.savePreferences(
			userId, groupId, ownerId, preferences);
	}

	/**
	 * Updates the faro preferences in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroPreferencesLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroPreferences the faro preferences
	 * @return the faro preferences that was updated
	 */
	@Override
	public com.liferay.osb.faro.model.FaroPreferences updateFaroPreferences(
		com.liferay.osb.faro.model.FaroPreferences faroPreferences) {

		return _faroPreferencesLocalService.updateFaroPreferences(
			faroPreferences);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _faroPreferencesLocalService.getBasePersistence();
	}

	@Override
	public FaroPreferencesLocalService getWrappedService() {
		return _faroPreferencesLocalService;
	}

	@Override
	public void setWrappedService(
		FaroPreferencesLocalService faroPreferencesLocalService) {

		_faroPreferencesLocalService = faroPreferencesLocalService;
	}

	private FaroPreferencesLocalService _faroPreferencesLocalService;

}