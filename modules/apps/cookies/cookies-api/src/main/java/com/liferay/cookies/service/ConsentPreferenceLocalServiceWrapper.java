/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ConsentPreferenceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreferenceLocalService
 * @generated
 */
public class ConsentPreferenceLocalServiceWrapper
	implements ConsentPreferenceLocalService,
			   ServiceWrapper<ConsentPreferenceLocalService> {

	public ConsentPreferenceLocalServiceWrapper() {
		this(null);
	}

	public ConsentPreferenceLocalServiceWrapper(
		ConsentPreferenceLocalService consentPreferenceLocalService) {

		_consentPreferenceLocalService = consentPreferenceLocalService;
	}

	/**
	 * Adds the consent preference to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConsentPreferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param consentPreference the consent preference
	 * @return the consent preference that was added
	 */
	@Override
	public com.liferay.cookies.model.ConsentPreference addConsentPreference(
		com.liferay.cookies.model.ConsentPreference consentPreference) {

		return _consentPreferenceLocalService.addConsentPreference(
			consentPreference);
	}

	/**
	 * Creates a new consent preference with the primary key. Does not add the consent preference to the database.
	 *
	 * @param consentPreferenceId the primary key for the new consent preference
	 * @return the new consent preference
	 */
	@Override
	public com.liferay.cookies.model.ConsentPreference createConsentPreference(
		long consentPreferenceId) {

		return _consentPreferenceLocalService.createConsentPreference(
			consentPreferenceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _consentPreferenceLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the consent preference from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConsentPreferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param consentPreference the consent preference
	 * @return the consent preference that was removed
	 */
	@Override
	public com.liferay.cookies.model.ConsentPreference deleteConsentPreference(
		com.liferay.cookies.model.ConsentPreference consentPreference) {

		return _consentPreferenceLocalService.deleteConsentPreference(
			consentPreference);
	}

	/**
	 * Deletes the consent preference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConsentPreferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference that was removed
	 * @throws PortalException if a consent preference with the primary key could not be found
	 */
	@Override
	public com.liferay.cookies.model.ConsentPreference deleteConsentPreference(
			long consentPreferenceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _consentPreferenceLocalService.deleteConsentPreference(
			consentPreferenceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _consentPreferenceLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _consentPreferenceLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _consentPreferenceLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _consentPreferenceLocalService.dynamicQuery();
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

		return _consentPreferenceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.cookies.model.impl.ConsentPreferenceModelImpl</code>.
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

		return _consentPreferenceLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.cookies.model.impl.ConsentPreferenceModelImpl</code>.
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

		return _consentPreferenceLocalService.dynamicQuery(
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

		return _consentPreferenceLocalService.dynamicQueryCount(dynamicQuery);
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

		return _consentPreferenceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.cookies.model.ConsentPreference fetchConsentPreference(
		long consentPreferenceId) {

		return _consentPreferenceLocalService.fetchConsentPreference(
			consentPreferenceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _consentPreferenceLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the consent preference with the primary key.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference
	 * @throws PortalException if a consent preference with the primary key could not be found
	 */
	@Override
	public com.liferay.cookies.model.ConsentPreference getConsentPreference(
			long consentPreferenceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _consentPreferenceLocalService.getConsentPreference(
			consentPreferenceId);
	}

	/**
	 * Returns a range of all the consent preferences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.cookies.model.impl.ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of consent preferences
	 */
	@Override
	public java.util.List<com.liferay.cookies.model.ConsentPreference>
		getConsentPreferences(int start, int end) {

		return _consentPreferenceLocalService.getConsentPreferences(start, end);
	}

	/**
	 * Returns the number of consent preferences.
	 *
	 * @return the number of consent preferences
	 */
	@Override
	public int getConsentPreferencesCount() {
		return _consentPreferenceLocalService.getConsentPreferencesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _consentPreferenceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _consentPreferenceLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _consentPreferenceLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the consent preference in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConsentPreferenceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param consentPreference the consent preference
	 * @return the consent preference that was updated
	 */
	@Override
	public com.liferay.cookies.model.ConsentPreference updateConsentPreference(
		com.liferay.cookies.model.ConsentPreference consentPreference) {

		return _consentPreferenceLocalService.updateConsentPreference(
			consentPreference);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _consentPreferenceLocalService.getBasePersistence();
	}

	@Override
	public ConsentPreferenceLocalService getWrappedService() {
		return _consentPreferenceLocalService;
	}

	@Override
	public void setWrappedService(
		ConsentPreferenceLocalService consentPreferenceLocalService) {

		_consentPreferenceLocalService = consentPreferenceLocalService;
	}

	private ConsentPreferenceLocalService _consentPreferenceLocalService;

}