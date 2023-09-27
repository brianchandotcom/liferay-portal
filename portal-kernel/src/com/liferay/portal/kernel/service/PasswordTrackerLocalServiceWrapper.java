/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link PasswordTrackerLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerLocalService
 * @generated
 */
public class PasswordTrackerLocalServiceWrapper
	implements PasswordTrackerLocalService,
			   ServiceWrapper<PasswordTrackerLocalService> {

	public PasswordTrackerLocalServiceWrapper() {
		this(null);
	}

	public PasswordTrackerLocalServiceWrapper(
		PasswordTrackerLocalService passwordTrackerLocalService) {

		_passwordTrackerLocalService = passwordTrackerLocalService;
	}

	/**
	 * Adds the password tracker to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordTracker the password tracker
	 * @return the password tracker that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.PasswordTracker addPasswordTracker(
		com.liferay.portal.kernel.model.PasswordTracker passwordTracker) {

		return _passwordTrackerLocalService.addPasswordTracker(passwordTracker);
	}

	/**
	 * Creates a new password tracker with the primary key. Does not add the password tracker to the database.
	 *
	 * @param passwordTrackerId the primary key for the new password tracker
	 * @return the new password tracker
	 */
	@Override
	public com.liferay.portal.kernel.model.PasswordTracker
		createPasswordTracker(long passwordTrackerId) {

		return _passwordTrackerLocalService.createPasswordTracker(
			passwordTrackerId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordTrackerLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordTrackerId the primary key of the password tracker
	 * @return the password tracker that was removed
	 * @throws PortalException if a password tracker with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.PasswordTracker
			deletePasswordTracker(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordTrackerLocalService.deletePasswordTracker(
			passwordTrackerId);
	}

	/**
	 * Deletes the password tracker from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordTracker the password tracker
	 * @return the password tracker that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.PasswordTracker
		deletePasswordTracker(
			com.liferay.portal.kernel.model.PasswordTracker passwordTracker) {

		return _passwordTrackerLocalService.deletePasswordTracker(
			passwordTracker);
	}

	@Override
	public void deletePasswordTrackers(long userId) {
		_passwordTrackerLocalService.deletePasswordTrackers(userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordTrackerLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _passwordTrackerLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _passwordTrackerLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _passwordTrackerLocalService.dynamicQuery();
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

		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PasswordTrackerModelImpl</code>.
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

		return _passwordTrackerLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PasswordTrackerModelImpl</code>.
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

		return _passwordTrackerLocalService.dynamicQuery(
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

		return _passwordTrackerLocalService.dynamicQueryCount(dynamicQuery);
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

		return _passwordTrackerLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.PasswordTracker fetchPasswordTracker(
		long passwordTrackerId) {

		return _passwordTrackerLocalService.fetchPasswordTracker(
			passwordTrackerId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _passwordTrackerLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _passwordTrackerLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _passwordTrackerLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * Returns the password tracker with the primary key.
	 *
	 * @param passwordTrackerId the primary key of the password tracker
	 * @return the password tracker
	 * @throws PortalException if a password tracker with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.PasswordTracker getPasswordTracker(
			long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordTrackerLocalService.getPasswordTracker(
			passwordTrackerId);
	}

	/**
	 * Returns a range of all the password trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PasswordTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of password trackers
	 * @param end the upper bound of the range of password trackers (not inclusive)
	 * @return the range of password trackers
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.PasswordTracker>
		getPasswordTrackers(int start, int end) {

		return _passwordTrackerLocalService.getPasswordTrackers(start, end);
	}

	/**
	 * Returns the number of password trackers.
	 *
	 * @return the number of password trackers
	 */
	@Override
	public int getPasswordTrackersCount() {
		return _passwordTrackerLocalService.getPasswordTrackersCount();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordTrackerLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean isSameAsCurrentPassword(long userId, String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordTrackerLocalService.isSameAsCurrentPassword(
			userId, newClearTextPwd);
	}

	@Override
	public boolean isValidPassword(long userId, String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordTrackerLocalService.isValidPassword(
			userId, newClearTextPwd);
	}

	@Override
	public void trackPassword(long userId, String encPassword)
		throws com.liferay.portal.kernel.exception.PortalException {

		_passwordTrackerLocalService.trackPassword(userId, encPassword);
	}

	/**
	 * Updates the password tracker in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordTrackerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordTracker the password tracker
	 * @return the password tracker that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.PasswordTracker
		updatePasswordTracker(
			com.liferay.portal.kernel.model.PasswordTracker passwordTracker) {

		return _passwordTrackerLocalService.updatePasswordTracker(
			passwordTracker);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _passwordTrackerLocalService.getBasePersistence();
	}

	@Override
	public PasswordTrackerLocalService getWrappedService() {
		return _passwordTrackerLocalService;
	}

	@Override
	public void setWrappedService(
		PasswordTrackerLocalService passwordTrackerLocalService) {

		_passwordTrackerLocalService = passwordTrackerLocalService;
	}

	private PasswordTrackerLocalService _passwordTrackerLocalService;

}