/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service;

import com.liferay.cookies.model.ConsentPreference;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ConsentPreference. This utility wraps
 * <code>com.liferay.cookies.service.impl.ConsentPreferenceLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreferenceLocalService
 * @generated
 */
public class ConsentPreferenceLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.cookies.service.impl.ConsentPreferenceLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static ConsentPreference addConsentPreference(
		ConsentPreference consentPreference) {

		return getService().addConsentPreference(consentPreference);
	}

	public static ConsentPreference addConsentPreference(
			long userId, String domain, java.util.Date expirationDate,
			String name, String value)
		throws PortalException {

		return getService().addConsentPreference(
			userId, domain, expirationDate, name, value);
	}

	/**
	 * Creates a new consent preference with the primary key. Does not add the consent preference to the database.
	 *
	 * @param consentPreferenceId the primary key for the new consent preference
	 * @return the new consent preference
	 */
	public static ConsentPreference createConsentPreference(
		long consentPreferenceId) {

		return getService().createConsentPreference(consentPreferenceId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static ConsentPreference deleteConsentPreference(
		ConsentPreference consentPreference) {

		return getService().deleteConsentPreference(consentPreference);
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
	public static ConsentPreference deleteConsentPreference(
			long consentPreferenceId)
		throws PortalException {

		return getService().deleteConsentPreference(consentPreferenceId);
	}

	public static void deleteConsentPreference(
			long userId, String domain, String name)
		throws PortalException {

		getService().deleteConsentPreference(userId, domain, name);
	}

	public static void deleteConsentPreferences(long userId) {
		getService().deleteConsentPreferences(userId);
	}

	public static void deleteConsentPreferences(long userId, String domain) {
		getService().deleteConsentPreferences(userId, domain);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static ConsentPreference fetchConsentPreference(
		long consentPreferenceId) {

		return getService().fetchConsentPreference(consentPreferenceId);
	}

	public static ConsentPreference fetchConsentPreference(
		long userId, String domain, String name) {

		return getService().fetchConsentPreference(userId, domain, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the consent preference with the primary key.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference
	 * @throws PortalException if a consent preference with the primary key could not be found
	 */
	public static ConsentPreference getConsentPreference(
			long consentPreferenceId)
		throws PortalException {

		return getService().getConsentPreference(consentPreferenceId);
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
	public static List<ConsentPreference> getConsentPreferences(
		int start, int end) {

		return getService().getConsentPreferences(start, end);
	}

	public static List<ConsentPreference> getConsentPreferences(
		long userId, String domain) {

		return getService().getConsentPreferences(userId, domain);
	}

	/**
	 * Returns the number of consent preferences.
	 *
	 * @return the number of consent preferences
	 */
	public static int getConsentPreferencesCount() {
		return getService().getConsentPreferencesCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static ConsentPreference updateConsentPreference(
		ConsentPreference consentPreference) {

		return getService().updateConsentPreference(consentPreference);
	}

	public static ConsentPreferenceLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<ConsentPreferenceLocalService>
		_serviceSnapshot = new Snapshot<>(
			ConsentPreferenceLocalServiceUtil.class,
			ConsentPreferenceLocalService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-202141043