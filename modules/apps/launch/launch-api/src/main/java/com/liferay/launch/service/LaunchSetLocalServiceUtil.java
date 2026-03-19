/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service;

import com.liferay.launch.model.LaunchSet;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for LaunchSet. This utility wraps
 * <code>com.liferay.launch.service.impl.LaunchSetLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchSetLocalService
 * @generated
 */
public class LaunchSetLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.launch.service.impl.LaunchSetLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the launch set to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was added
	 */
	public static LaunchSet addLaunchSet(LaunchSet launchSet) {
		return getService().addLaunchSet(launchSet);
	}

	public static LaunchSet addLaunchSet(
			String externalReferenceCode, long userId, String description,
			String name)
		throws PortalException {

		return getService().addLaunchSet(
			externalReferenceCode, userId, description, name);
	}

	/**
	 * Creates a new launch set with the primary key. Does not add the launch set to the database.
	 *
	 * @param launchSetId the primary key for the new launch set
	 * @return the new launch set
	 */
	public static LaunchSet createLaunchSet(long launchSetId) {
		return getService().createLaunchSet(launchSetId);
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
	 * Deletes the launch set from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was removed
	 */
	public static LaunchSet deleteLaunchSet(LaunchSet launchSet) {
		return getService().deleteLaunchSet(launchSet);
	}

	/**
	 * Deletes the launch set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set that was removed
	 * @throws PortalException if a launch set with the primary key could not be found
	 */
	public static LaunchSet deleteLaunchSet(long launchSetId)
		throws PortalException {

		return getService().deleteLaunchSet(launchSetId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
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

	public static LaunchSet fetchLaunchSet(long launchSetId) {
		return getService().fetchLaunchSet(launchSetId);
	}

	public static LaunchSet fetchLaunchSetByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return getService().fetchLaunchSetByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch set with the matching UUID and company.
	 *
	 * @param uuid the launch set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	public static LaunchSet fetchLaunchSetByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchLaunchSetByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the launch set with the primary key.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set
	 * @throws PortalException if a launch set with the primary key could not be found
	 */
	public static LaunchSet getLaunchSet(long launchSetId)
		throws PortalException {

		return getService().getLaunchSet(launchSetId);
	}

	public static LaunchSet getLaunchSetByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getLaunchSetByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch set with the matching UUID and company.
	 *
	 * @param uuid the launch set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch set
	 * @throws PortalException if a matching launch set could not be found
	 */
	public static LaunchSet getLaunchSetByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getLaunchSetByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the launch sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of launch sets
	 */
	public static List<LaunchSet> getLaunchSets(int start, int end) {
		return getService().getLaunchSets(start, end);
	}

	/**
	 * Returns the number of launch sets.
	 *
	 * @return the number of launch sets
	 */
	public static int getLaunchSetsCount() {
		return getService().getLaunchSetsCount();
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
	 * Updates the launch set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was updated
	 */
	public static LaunchSet updateLaunchSet(LaunchSet launchSet) {
		return getService().updateLaunchSet(launchSet);
	}

	public static LaunchSetLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<LaunchSetLocalService> _serviceSnapshot =
		new Snapshot<>(
			LaunchSetLocalServiceUtil.class, LaunchSetLocalService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-601942292