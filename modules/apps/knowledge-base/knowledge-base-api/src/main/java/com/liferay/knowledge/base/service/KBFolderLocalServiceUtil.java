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

package com.liferay.knowledge.base.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for KBFolder. This utility wraps
 * <code>com.liferay.knowledge.base.service.impl.KBFolderLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KBFolderLocalService
 * @generated
 */
public class KBFolderLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBFolderLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kb folder to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolder the kb folder
	 * @return the kb folder that was added
	 */
	public static com.liferay.knowledge.base.model.KBFolder addKBFolder(
		com.liferay.knowledge.base.model.KBFolder kbFolder) {

		return getService().addKBFolder(kbFolder);
	}

	public static com.liferay.knowledge.base.model.KBFolder addKBFolder(
			long userId, long groupId, long parentResourceClassNameId,
			long parentResourcePrimKey, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKBFolder(
			userId, groupId, parentResourceClassNameId, parentResourcePrimKey,
			name, description, serviceContext);
	}

	/**
	 * Creates a new kb folder with the primary key. Does not add the kb folder to the database.
	 *
	 * @param kbFolderId the primary key for the new kb folder
	 * @return the new kb folder
	 */
	public static com.liferay.knowledge.base.model.KBFolder createKBFolder(
		long kbFolderId) {

		return getService().createKBFolder(kbFolderId);
	}

	/**
	 * Deletes the kb folder from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolder the kb folder
	 * @return the kb folder that was removed
	 */
	public static com.liferay.knowledge.base.model.KBFolder deleteKBFolder(
		com.liferay.knowledge.base.model.KBFolder kbFolder) {

		return getService().deleteKBFolder(kbFolder);
	}

	/**
	 * Deletes the kb folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolderId the primary key of the kb folder
	 * @return the kb folder that was removed
	 * @throws PortalException if a kb folder with the primary key could not be found
	 */
	public static com.liferay.knowledge.base.model.KBFolder deleteKBFolder(
			long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKBFolder(kbFolderId);
	}

	public static void deleteKBFolders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKBFolders(groupId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBFolderModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBFolderModelImpl</code>.
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

	public static com.liferay.knowledge.base.model.KBFolder
			fetchFirstChildKBFolder(long groupId, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchFirstChildKBFolder(groupId, kbFolderId);
	}

	public static com.liferay.knowledge.base.model.KBFolder
			fetchFirstChildKBFolder(
				long groupId, long kbFolderId,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBFolder> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchFirstChildKBFolder(groupId, kbFolderId, obc);
	}

	public static com.liferay.knowledge.base.model.KBFolder fetchKBFolder(
		long kbFolderId) {

		return getService().fetchKBFolder(kbFolderId);
	}

	public static com.liferay.knowledge.base.model.KBFolder fetchKBFolder(
		String uuid, long groupId) {

		return getService().fetchKBFolder(uuid, groupId);
	}

	public static com.liferay.knowledge.base.model.KBFolder
			fetchKBFolderByUrlTitle(
				long groupId, long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	/**
	 * Returns the kb folder matching the UUID and group.
	 *
	 * @param uuid the kb folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb folder, or <code>null</code> if a matching kb folder could not be found
	 */
	public static com.liferay.knowledge.base.model.KBFolder
		fetchKBFolderByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchKBFolderByUuidAndGroupId(uuid, groupId);
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
	 * Returns the kb folder with the primary key.
	 *
	 * @param kbFolderId the primary key of the kb folder
	 * @return the kb folder
	 * @throws PortalException if a kb folder with the primary key could not be found
	 */
	public static com.liferay.knowledge.base.model.KBFolder getKBFolder(
			long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBFolder(kbFolderId);
	}

	public static com.liferay.knowledge.base.model.KBFolder
			getKBFolderByUrlTitle(
				long groupId, long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBFolderByUrlTitle(
			groupId, parentKbFolderId, urlTitle);
	}

	/**
	 * Returns the kb folder matching the UUID and group.
	 *
	 * @param uuid the kb folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb folder
	 * @throws PortalException if a matching kb folder could not be found
	 */
	public static com.liferay.knowledge.base.model.KBFolder
			getKBFolderByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBFolderByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the kb folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBFolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @return the range of kb folders
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBFolder>
		getKBFolders(int start, int end) {

		return getService().getKBFolders(start, end);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBFolder>
			getKBFolders(
				long groupId, long parentKBFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBFolders(groupId, parentKBFolderId, start, end);
	}

	public static java.util.List<Object> getKBFoldersAndKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> orderByComparator) {

		return getService().getKBFoldersAndKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	public static int getKBFoldersAndKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return getService().getKBFoldersAndKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	/**
	 * Returns all the kb folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb folders
	 * @param companyId the primary key of the company
	 * @return the matching kb folders, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBFolder>
		getKBFoldersByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getKBFoldersByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of kb folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb folders
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of kb folders
	 * @param end the upper bound of the range of kb folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching kb folders, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBFolder>
		getKBFoldersByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBFolder> orderByComparator) {

		return getService().getKBFoldersByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of kb folders.
	 *
	 * @return the number of kb folders
	 */
	public static int getKBFoldersCount() {
		return getService().getKBFoldersCount();
	}

	public static int getKBFoldersCount(long groupId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBFoldersCount(groupId, parentKBFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void moveKBFolder(long kbFolderId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().moveKBFolder(kbFolderId, parentKBFolderId);
	}

	/**
	 * Updates the kb folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kbFolder the kb folder
	 * @return the kb folder that was updated
	 */
	public static com.liferay.knowledge.base.model.KBFolder updateKBFolder(
		com.liferay.knowledge.base.model.KBFolder kbFolder) {

		return getService().updateKBFolder(kbFolder);
	}

	public static com.liferay.knowledge.base.model.KBFolder updateKBFolder(
			long parentResourceClassNameId, long parentResourcePrimKey,
			long kbFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateKBFolder(
			parentResourceClassNameId, parentResourcePrimKey, kbFolderId, name,
			description, serviceContext);
	}

	public static KBFolderLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KBFolderLocalService, KBFolderLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KBFolderLocalService.class);

		ServiceTracker<KBFolderLocalService, KBFolderLocalService>
			serviceTracker =
				new ServiceTracker<KBFolderLocalService, KBFolderLocalService>(
					bundle.getBundleContext(), KBFolderLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}