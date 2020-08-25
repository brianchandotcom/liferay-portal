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

package com.liferay.journal.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for JournalFolder. This utility wraps
 * <code>com.liferay.journal.service.impl.JournalFolderLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderLocalService
 * @generated
 */
public class JournalFolderLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.journal.service.impl.JournalFolderLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.journal.model.JournalFolder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFolder(
			userId, groupId, parentFolderId, name, description, serviceContext);
	}

	/**
	 * Adds the journal folder to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JournalFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param journalFolder the journal folder
	 * @return the journal folder that was added
	 */
	public static com.liferay.journal.model.JournalFolder addJournalFolder(
		com.liferay.journal.model.JournalFolder journalFolder) {

		return getService().addJournalFolder(journalFolder);
	}

	/**
	 * Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	 *
	 * @param folderId the primary key for the new journal folder
	 * @return the new journal folder
	 */
	public static com.liferay.journal.model.JournalFolder createJournalFolder(
		long folderId) {

		return getService().createJournalFolder(folderId);
	}

	public static com.liferay.journal.model.JournalFolder deleteFolder(
			com.liferay.journal.model.JournalFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFolder(folder);
	}

	public static com.liferay.journal.model.JournalFolder deleteFolder(
			com.liferay.journal.model.JournalFolder folder,
			boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFolder(folder, includeTrashedEntries);
	}

	public static com.liferay.journal.model.JournalFolder deleteFolder(
			long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFolder(folderId);
	}

	public static com.liferay.journal.model.JournalFolder deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFolder(folderId, includeTrashedEntries);
	}

	public static void deleteFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFolders(groupId);
	}

	/**
	 * Deletes the journal folder from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JournalFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param journalFolder the journal folder
	 * @return the journal folder that was removed
	 */
	public static com.liferay.journal.model.JournalFolder deleteJournalFolder(
		com.liferay.journal.model.JournalFolder journalFolder) {

		return getService().deleteJournalFolder(journalFolder);
	}

	/**
	 * Deletes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JournalFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder that was removed
	 * @throws PortalException if a journal folder with the primary key could not be found
	 */
	public static com.liferay.journal.model.JournalFolder deleteJournalFolder(
			long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteJournalFolder(folderId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.journal.model.JournalFolder fetchFolder(
		long folderId) {

		return getService().fetchFolder(folderId);
	}

	public static com.liferay.journal.model.JournalFolder fetchFolder(
		long groupId, long parentFolderId, String name) {

		return getService().fetchFolder(groupId, parentFolderId, name);
	}

	public static com.liferay.journal.model.JournalFolder fetchFolder(
		long groupId, String name) {

		return getService().fetchFolder(groupId, name);
	}

	public static com.liferay.journal.model.JournalFolder fetchJournalFolder(
		long folderId) {

		return getService().fetchJournalFolder(folderId);
	}

	/**
	 * Returns the journal folder matching the UUID and group.
	 *
	 * @param uuid the journal folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 */
	public static com.liferay.journal.model.JournalFolder
		fetchJournalFolderByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchJournalFolderByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getCompanyFolders(long companyId, int start, int end) {

		return getService().getCompanyFolders(companyId, start, end);
	}

	public static int getCompanyFoldersCount(long companyId) {
		return getService().getCompanyFoldersCount(companyId);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getDDMStructures(
				long[] groupIds, long folderId, int restrictionType)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMStructures(
			groupIds, folderId, restrictionType);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.journal.model.JournalFolder getFolder(
			long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFolder(folderId);
	}

	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getFolders(long groupId) {

		return getService().getFolders(groupId);
	}

	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getFolders(long groupId, long parentFolderId) {

		return getService().getFolders(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getFolders(long groupId, long parentFolderId, int status) {

		return getService().getFolders(groupId, parentFolderId, status);
	}

	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getFolders(long groupId, long parentFolderId, int start, int end) {

		return getService().getFolders(groupId, parentFolderId, start, end);
	}

	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getFolders(
			long groupId, long parentFolderId, int status, int start, int end) {

		return getService().getFolders(
			groupId, parentFolderId, status, start, end);
	}

	public static java.util.List<Object> getFoldersAndArticles(
		long groupId, long folderId) {

		return getService().getFoldersAndArticles(groupId, folderId);
	}

	public static java.util.List<Object> getFoldersAndArticles(
		long groupId, long folderId, int status) {

		return getService().getFoldersAndArticles(groupId, folderId, status);
	}

	public static java.util.List<Object> getFoldersAndArticles(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {

		return getService().getFoldersAndArticles(
			groupId, folderId, status, start, end, obc);
	}

	public static java.util.List<Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {

		return getService().getFoldersAndArticles(
			groupId, folderId, start, end, obc);
	}

	public static int getFoldersAndArticlesCount(
		long groupId, java.util.List<Long> folderIds, int status) {

		return getService().getFoldersAndArticlesCount(
			groupId, folderIds, status);
	}

	public static int getFoldersAndArticlesCount(long groupId, long folderId) {
		return getService().getFoldersAndArticlesCount(groupId, folderId);
	}

	public static int getFoldersAndArticlesCount(
		long groupId, long folderId, int status) {

		return getService().getFoldersAndArticlesCount(
			groupId, folderId, status);
	}

	public static int getFoldersCount(long groupId, long parentFolderId) {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersCount(
		long groupId, long parentFolderId, int status) {

		return getService().getFoldersCount(groupId, parentFolderId, status);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static long getInheritedWorkflowFolderId(long folderId)
		throws com.liferay.journal.exception.NoSuchFolderException {

		return getService().getInheritedWorkflowFolderId(folderId);
	}

	/**
	 * Returns the journal folder with the primary key.
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder
	 * @throws PortalException if a journal folder with the primary key could not be found
	 */
	public static com.liferay.journal.model.JournalFolder getJournalFolder(
			long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getJournalFolder(folderId);
	}

	/**
	 * Returns the journal folder matching the UUID and group.
	 *
	 * @param uuid the journal folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching journal folder
	 * @throws PortalException if a matching journal folder could not be found
	 */
	public static com.liferay.journal.model.JournalFolder
			getJournalFolderByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getJournalFolderByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the journal folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of journal folders
	 */
	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getJournalFolders(int start, int end) {

		return getService().getJournalFolders(start, end);
	}

	/**
	 * Returns all the journal folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the journal folders
	 * @param companyId the primary key of the company
	 * @return the matching journal folders, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getJournalFoldersByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getJournalFoldersByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of journal folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the journal folders
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching journal folders, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getJournalFoldersByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalFolder> orderByComparator) {

		return getService().getJournalFoldersByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of journal folders.
	 *
	 * @return the number of journal folders
	 */
	public static int getJournalFoldersCount() {
		return getService().getJournalFoldersCount();
	}

	public static java.util.List<com.liferay.journal.model.JournalFolder>
		getNoAssetFolders() {

		return getService().getNoAssetFolders();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static long getOverridedDDMStructuresFolderId(long folderId)
		throws com.liferay.journal.exception.NoSuchFolderException {

		return getService().getOverridedDDMStructuresFolderId(folderId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void getSubfolderIds(
		java.util.List<Long> folderIds, long groupId, long folderId) {

		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static com.liferay.portal.kernel.service.SubscriptionLocalService
		getSubscriptionLocalService() {

		return getService().getSubscriptionLocalService();
	}

	public static String getUniqueFolderName(
		String uuid, long groupId, long parentFolderId, String name,
		int count) {

		return getService().getUniqueFolderName(
			uuid, groupId, parentFolderId, name, count);
	}

	public static com.liferay.journal.model.JournalFolder moveFolder(
			long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFolder(
			folderId, parentFolderId, serviceContext);
	}

	public static com.liferay.journal.model.JournalFolder moveFolderFromTrash(
			long userId, long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFolderFromTrash(
			userId, folderId, parentFolderId, serviceContext);
	}

	public static com.liferay.journal.model.JournalFolder moveFolderToTrash(
			long userId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFolderToTrash(userId, folderId);
	}

	public static void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().rebuildTree(companyId);
	}

	public static void rebuildTree(
			long companyId, long parentFolderId, String parentTreePath,
			boolean reindex)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().rebuildTree(
			companyId, parentFolderId, parentTreePath, reindex);
	}

	public static void restoreFolderFromTrash(long userId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().restoreFolderFromTrash(userId, folderId);
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure>
				searchDDMStructures(
					long companyId, long[] groupIds, long folderId,
					int restrictionType, String keywords, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.DDMStructure>
							obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchDDMStructures(
			companyId, groupIds, folderId, restrictionType, keywords, start,
			end, obc);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void setSubscriptionLocalService(
		com.liferay.portal.kernel.service.SubscriptionLocalService
			subscriptionLocalService) {

		getService().setSubscriptionLocalService(subscriptionLocalService);
	}

	public static void subscribe(long userId, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribe(userId, groupId, folderId);
	}

	public static void unsubscribe(long userId, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribe(userId, groupId, folderId);
	}

	public static void updateAsset(
			long userId, com.liferay.journal.model.JournalFolder folder,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds, Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateAsset(
			userId, folder, assetCategoryIds, assetTagNames, assetLinkEntryIds,
			priority);
	}

	public static com.liferay.journal.model.JournalFolder updateFolder(
			long userId, long groupId, long folderId, long parentFolderId,
			String name, String description, boolean mergeWithParentFolder,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFolder(
			userId, groupId, folderId, parentFolderId, name, description,
			mergeWithParentFolder, serviceContext);
	}

	public static com.liferay.journal.model.JournalFolder updateFolder(
			long userId, long groupId, long folderId, long parentFolderId,
			String name, String description, long[] ddmStructureIds,
			int restrictionType, boolean mergeWithParentFolder,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFolder(
			userId, groupId, folderId, parentFolderId, name, description,
			ddmStructureIds, restrictionType, mergeWithParentFolder,
			serviceContext);
	}

	public static void updateFolderDDMStructures(
			com.liferay.journal.model.JournalFolder folder,
			long[] ddmStructureIdsArray)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateFolderDDMStructures(folder, ddmStructureIdsArray);
	}

	/**
	 * Updates the journal folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect JournalFolderLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param journalFolder the journal folder
	 * @return the journal folder that was updated
	 */
	public static com.liferay.journal.model.JournalFolder updateJournalFolder(
		com.liferay.journal.model.JournalFolder journalFolder) {

		return getService().updateJournalFolder(journalFolder);
	}

	public static com.liferay.journal.model.JournalFolder updateStatus(
			long userId, com.liferay.journal.model.JournalFolder folder,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(userId, folder, status);
	}

	public static void validateFolderDDMStructures(
			long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().validateFolderDDMStructures(folderId, parentFolderId);
	}

	public static JournalFolderLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<JournalFolderLocalService, JournalFolderLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			JournalFolderLocalService.class);

		ServiceTracker<JournalFolderLocalService, JournalFolderLocalService>
			serviceTracker =
				new ServiceTracker
					<JournalFolderLocalService, JournalFolderLocalService>(
						bundle.getBundleContext(),
						JournalFolderLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}