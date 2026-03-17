/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service;

import com.liferay.launch.model.LaunchEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for LaunchEntry. This utility wraps
 * <code>com.liferay.launch.service.impl.LaunchEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntryLocalService
 * @generated
 */
public class LaunchEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.launch.service.impl.LaunchEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the launch entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntry the launch entry
	 * @return the launch entry that was added
	 */
	public static LaunchEntry addLaunchEntry(LaunchEntry launchEntry) {
		return getService().addLaunchEntry(launchEntry);
	}

	public static LaunchEntry addLaunchEntry(
			String externalReferenceCode, long userId, long launchSetId,
			long classNameId, long classPK, String classVersion)
		throws PortalException {

		return getService().addLaunchEntry(
			externalReferenceCode, userId, launchSetId, classNameId, classPK,
			classVersion);
	}

	/**
	 * Creates a new launch entry with the primary key. Does not add the launch entry to the database.
	 *
	 * @param launchEntryId the primary key for the new launch entry
	 * @return the new launch entry
	 */
	public static LaunchEntry createLaunchEntry(long launchEntryId) {
		return getService().createLaunchEntry(launchEntryId);
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
	 * Deletes the launch entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntry the launch entry
	 * @return the launch entry that was removed
	 */
	public static LaunchEntry deleteLaunchEntry(LaunchEntry launchEntry) {
		return getService().deleteLaunchEntry(launchEntry);
	}

	/**
	 * Deletes the launch entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry that was removed
	 * @throws PortalException if a launch entry with the primary key could not be found
	 */
	public static LaunchEntry deleteLaunchEntry(long launchEntryId)
		throws PortalException {

		return getService().deleteLaunchEntry(launchEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchEntryModelImpl</code>.
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

	public static LaunchEntry fetchLaunchEntry(long launchEntryId) {
		return getService().fetchLaunchEntry(launchEntryId);
	}

	public static LaunchEntry fetchLaunchEntry(
		long classNameId, long classPK, String classVersion) {

		return getService().fetchLaunchEntry(
			classNameId, classPK, classVersion);
	}

	public static LaunchEntry fetchLaunchEntryByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return getService().fetchLaunchEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch entry with the matching UUID and company.
	 *
	 * @param uuid the launch entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchLaunchEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchLaunchEntryByUuidAndCompanyId(uuid, companyId);
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
	 * Returns a range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of launch entries
	 */
	public static List<LaunchEntry> getLaunchEntries(int start, int end) {
		return getService().getLaunchEntries(start, end);
	}

	/**
	 * Returns the number of launch entries.
	 *
	 * @return the number of launch entries
	 */
	public static int getLaunchEntriesCount() {
		return getService().getLaunchEntriesCount();
	}

	/**
	 * Returns the launch entry with the primary key.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry
	 * @throws PortalException if a launch entry with the primary key could not be found
	 */
	public static LaunchEntry getLaunchEntry(long launchEntryId)
		throws PortalException {

		return getService().getLaunchEntry(launchEntryId);
	}

	public static LaunchEntry getLaunchEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getLaunchEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch entry with the matching UUID and company.
	 *
	 * @param uuid the launch entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch entry
	 * @throws PortalException if a matching launch entry could not be found
	 */
	public static LaunchEntry getLaunchEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getLaunchEntryByUuidAndCompanyId(uuid, companyId);
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
	 * Updates the launch entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchEntry the launch entry
	 * @return the launch entry that was updated
	 */
	public static LaunchEntry updateLaunchEntry(LaunchEntry launchEntry) {
		return getService().updateLaunchEntry(launchEntry);
	}

	public static LaunchEntryLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<LaunchEntryLocalService> _serviceSnapshot =
		new Snapshot<>(
			LaunchEntryLocalServiceUtil.class, LaunchEntryLocalService.class);

}
// SB-Hash:-822642840