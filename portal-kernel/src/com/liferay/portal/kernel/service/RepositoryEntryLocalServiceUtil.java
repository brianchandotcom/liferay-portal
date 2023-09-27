/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for RepositoryEntry. This utility wraps
 * <code>com.liferay.portal.service.impl.RepositoryEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryLocalService
 * @generated
 */
public class RepositoryEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.RepositoryEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static RepositoryEntry addRepositoryEntry(
			long userId, long groupId, long repositoryId, String mappedId,
			ServiceContext serviceContext)
		throws PortalException {

		return getService().addRepositoryEntry(
			userId, groupId, repositoryId, mappedId, serviceContext);
	}

	/**
	 * Adds the repository entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryEntry the repository entry
	 * @return the repository entry that was added
	 */
	public static RepositoryEntry addRepositoryEntry(
		RepositoryEntry repositoryEntry) {

		return getService().addRepositoryEntry(repositoryEntry);
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
	 * Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	 *
	 * @param repositoryEntryId the primary key for the new repository entry
	 * @return the new repository entry
	 */
	public static RepositoryEntry createRepositoryEntry(
		long repositoryEntryId) {

		return getService().createRepositoryEntry(repositoryEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRepositoryEntries(
			long repositoryId, Iterable<String> mappedIds)
		throws PortalException {

		getService().deleteRepositoryEntries(repositoryId, mappedIds);
	}

	/**
	 * Deletes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry that was removed
	 * @throws PortalException if a repository entry with the primary key could not be found
	 */
	public static RepositoryEntry deleteRepositoryEntry(long repositoryEntryId)
		throws PortalException {

		return getService().deleteRepositoryEntry(repositoryEntryId);
	}

	public static void deleteRepositoryEntry(long repositoryId, String mappedId)
		throws PortalException {

		getService().deleteRepositoryEntry(repositoryId, mappedId);
	}

	/**
	 * Deletes the repository entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryEntry the repository entry
	 * @return the repository entry that was removed
	 */
	public static RepositoryEntry deleteRepositoryEntry(
		RepositoryEntry repositoryEntry) {

		return getService().deleteRepositoryEntry(repositoryEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RepositoryEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RepositoryEntryModelImpl</code>.
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

	public static RepositoryEntry fetchRepositoryEntry(long repositoryEntryId) {
		return getService().fetchRepositoryEntry(repositoryEntryId);
	}

	/**
	 * Returns the repository entry matching the UUID and group.
	 *
	 * @param uuid the repository entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching repository entry, or <code>null</code> if a matching repository entry could not be found
	 */
	public static RepositoryEntry fetchRepositoryEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchRepositoryEntryByUuidAndGroupId(uuid, groupId);
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
	 * Returns a range of all the repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RepositoryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @return the range of repository entries
	 */
	public static List<RepositoryEntry> getRepositoryEntries(
		int start, int end) {

		return getService().getRepositoryEntries(start, end);
	}

	public static List<RepositoryEntry> getRepositoryEntries(
		long repositoryId) {

		return getService().getRepositoryEntries(repositoryId);
	}

	/**
	 * Returns all the repository entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the repository entries
	 * @param companyId the primary key of the company
	 * @return the matching repository entries, or an empty list if no matches were found
	 */
	public static List<RepositoryEntry> getRepositoryEntriesByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getRepositoryEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of repository entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the repository entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of repository entries
	 * @param end the upper bound of the range of repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching repository entries, or an empty list if no matches were found
	 */
	public static List<RepositoryEntry> getRepositoryEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RepositoryEntry> orderByComparator) {

		return getService().getRepositoryEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of repository entries.
	 *
	 * @return the number of repository entries
	 */
	public static int getRepositoryEntriesCount() {
		return getService().getRepositoryEntriesCount();
	}

	/**
	 * Returns the repository entry with the primary key.
	 *
	 * @param repositoryEntryId the primary key of the repository entry
	 * @return the repository entry
	 * @throws PortalException if a repository entry with the primary key could not be found
	 */
	public static RepositoryEntry getRepositoryEntry(long repositoryEntryId)
		throws PortalException {

		return getService().getRepositoryEntry(repositoryEntryId);
	}

	public static RepositoryEntry getRepositoryEntry(
			long userId, long groupId, long repositoryId, String objectId)
		throws PortalException {

		return getService().getRepositoryEntry(
			userId, groupId, repositoryId, objectId);
	}

	public static RepositoryEntry getRepositoryEntry(String uuid, long groupId)
		throws PortalException {

		return getService().getRepositoryEntry(uuid, groupId);
	}

	/**
	 * Returns the repository entry matching the UUID and group.
	 *
	 * @param uuid the repository entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching repository entry
	 * @throws PortalException if a matching repository entry could not be found
	 */
	public static RepositoryEntry getRepositoryEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getRepositoryEntryByUuidAndGroupId(uuid, groupId);
	}

	public static RepositoryEntry updateRepositoryEntry(
			long repositoryEntryId, String mappedId)
		throws PortalException {

		return getService().updateRepositoryEntry(repositoryEntryId, mappedId);
	}

	/**
	 * Updates the repository entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryEntry the repository entry
	 * @return the repository entry that was updated
	 */
	public static RepositoryEntry updateRepositoryEntry(
		RepositoryEntry repositoryEntry) {

		return getService().updateRepositoryEntry(repositoryEntry);
	}

	public static RepositoryEntryLocalService getService() {
		return _service;
	}

	public static void setService(RepositoryEntryLocalService service) {
		_service = service;
	}

	private static volatile RepositoryEntryLocalService _service;

}