/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.saved.content.model.SavedContentEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for SavedContentEntry. This utility wraps
 * <code>com.liferay.saved.content.service.impl.SavedContentEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryLocalService
 * @generated
 */
public class SavedContentEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.saved.content.service.impl.SavedContentEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SavedContentEntry addSavedContentEntry(
			long userId, long groupId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSavedContentEntry(
			userId, groupId, className, classPK, serviceContext);
	}

	/**
	 * Adds the saved content entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was added
	 */
	public static SavedContentEntry addSavedContentEntry(
		SavedContentEntry savedContentEntry) {

		return getService().addSavedContentEntry(savedContentEntry);
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
	 * Creates a new saved content entry with the primary key. Does not add the saved content entry to the database.
	 *
	 * @param savedContentEntryId the primary key for the new saved content entry
	 * @return the new saved content entry
	 */
	public static SavedContentEntry createSavedContentEntry(
		long savedContentEntryId) {

		return getService().createSavedContentEntry(savedContentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteSavedContentEntries(
		long groupId, long classNameId, long classPK) {

		getService().deleteSavedContentEntries(groupId, classNameId, classPK);
	}

	public static void deleteSavedContentEntriesByUserId(long userId) {
		getService().deleteSavedContentEntriesByUserId(userId);
	}

	/**
	 * Deletes the saved content entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry that was removed
	 * @throws PortalException if a saved content entry with the primary key could not be found
	 */
	public static SavedContentEntry deleteSavedContentEntry(
			long savedContentEntryId)
		throws PortalException {

		return getService().deleteSavedContentEntry(savedContentEntryId);
	}

	/**
	 * Deletes the saved content entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was removed
	 */
	public static SavedContentEntry deleteSavedContentEntry(
		SavedContentEntry savedContentEntry) {

		return getService().deleteSavedContentEntry(savedContentEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
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

	public static SavedContentEntry fetchSavedContentEntry(
		long savedContentEntryId) {

		return getService().fetchSavedContentEntry(savedContentEntryId);
	}

	public static SavedContentEntry fetchSavedContentEntry(
		long userId, long groupId, String className, long classPK) {

		return getService().fetchSavedContentEntry(
			userId, groupId, className, classPK);
	}

	/**
	 * Returns the saved content entry matching the UUID and group.
	 *
	 * @param uuid the saved content entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	public static SavedContentEntry fetchSavedContentEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchSavedContentEntryByUuidAndGroupId(
			uuid, groupId);
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
	 * Returns a range of all the saved content entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of saved content entries
	 */
	public static List<SavedContentEntry> getSavedContentEntries(
		int start, int end) {

		return getService().getSavedContentEntries(start, end);
	}

	/**
	 * Returns all the saved content entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the saved content entries
	 * @param companyId the primary key of the company
	 * @return the matching saved content entries, or an empty list if no matches were found
	 */
	public static List<SavedContentEntry>
		getSavedContentEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getSavedContentEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of saved content entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the saved content entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching saved content entries, or an empty list if no matches were found
	 */
	public static List<SavedContentEntry>
		getSavedContentEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<SavedContentEntry> orderByComparator) {

		return getService().getSavedContentEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of saved content entries.
	 *
	 * @return the number of saved content entries
	 */
	public static int getSavedContentEntriesCount() {
		return getService().getSavedContentEntriesCount();
	}

	/**
	 * Returns the saved content entry with the primary key.
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry
	 * @throws PortalException if a saved content entry with the primary key could not be found
	 */
	public static SavedContentEntry getSavedContentEntry(
			long savedContentEntryId)
		throws PortalException {

		return getService().getSavedContentEntry(savedContentEntryId);
	}

	public static SavedContentEntry getSavedContentEntry(
			long userId, long groupId, String className, long classPK)
		throws com.liferay.saved.content.exception.
			NoSuchSavedContentEntryException {

		return getService().getSavedContentEntry(
			userId, groupId, className, classPK);
	}

	/**
	 * Returns the saved content entry matching the UUID and group.
	 *
	 * @param uuid the saved content entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching saved content entry
	 * @throws PortalException if a matching saved content entry could not be found
	 */
	public static SavedContentEntry getSavedContentEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getSavedContentEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Updates the saved content entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was updated
	 */
	public static SavedContentEntry updateSavedContentEntry(
		SavedContentEntry savedContentEntry) {

		return getService().updateSavedContentEntry(savedContentEntry);
	}

	public static SavedContentEntryLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<SavedContentEntryLocalService>
		_serviceSnapshot = new Snapshot<>(
			SavedContentEntryLocalServiceUtil.class,
			SavedContentEntryLocalService.class);

}