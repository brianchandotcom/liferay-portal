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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for Repository. This utility wraps
 * <code>com.liferay.portal.service.impl.RepositoryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLocalService
 * @generated
 */
public class RepositoryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.RepositoryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Repository addRepository(
			long userId, long groupId, long classNameId, long parentFolderId,
			String name, String description, String portletId,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			boolean hidden, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRepository(
			userId, groupId, classNameId, parentFolderId, name, description,
			portletId, typeSettingsProperties, hidden, serviceContext);
	}

	/**
	 * Adds the repository to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repository the repository
	 * @return the repository that was added
	 */
	public static com.liferay.portal.kernel.model.Repository addRepository(
		com.liferay.portal.kernel.model.Repository repository) {

		return getService().addRepository(repository);
	}

	public static void checkRepository(long repositoryId) {
		getService().checkRepository(repositoryId);
	}

	/**
	 * Creates a new repository with the primary key. Does not add the repository to the database.
	 *
	 * @param repositoryId the primary key for the new repository
	 * @return the new repository
	 */
	public static com.liferay.portal.kernel.model.Repository createRepository(
		long repositoryId) {

		return getService().createRepository(repositoryId);
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

	public static void deleteRepositories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteRepositories(groupId);
	}

	/**
	 * Deletes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repositoryId the primary key of the repository
	 * @return the repository that was removed
	 * @throws PortalException if a repository with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Repository deleteRepository(
			long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRepository(repositoryId);
	}

	/**
	 * Deletes the repository from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repository the repository
	 * @return the repository that was removed
	 */
	public static com.liferay.portal.kernel.model.Repository deleteRepository(
		com.liferay.portal.kernel.model.Repository repository) {

		return getService().deleteRepository(repository);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RepositoryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RepositoryModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.Repository fetchRepository(
		long repositoryId) {

		return getService().fetchRepository(repositoryId);
	}

	public static com.liferay.portal.kernel.model.Repository fetchRepository(
		long groupId, String portletId) {

		return getService().fetchRepository(groupId, portletId);
	}

	public static com.liferay.portal.kernel.model.Repository fetchRepository(
		long groupId, String name, String portletId) {

		return getService().fetchRepository(groupId, name, portletId);
	}

	/**
	 * Returns the repository matching the UUID and group.
	 *
	 * @param uuid the repository's UUID
	 * @param groupId the primary key of the group
	 * @return the matching repository, or <code>null</code> if a matching repository could not be found
	 */
	public static com.liferay.portal.kernel.model.Repository
		fetchRepositoryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchRepositoryByUuidAndGroupId(uuid, groupId);
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

	public static java.util.List<com.liferay.portal.kernel.model.Repository>
		getGroupRepositories(long groupId) {

		return getService().getGroupRepositories(groupId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the repositories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RepositoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @return the range of repositories
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Repository>
		getRepositories(int start, int end) {

		return getService().getRepositories(start, end);
	}

	/**
	 * Returns all the repositories matching the UUID and company.
	 *
	 * @param uuid the UUID of the repositories
	 * @param companyId the primary key of the company
	 * @return the matching repositories, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Repository>
		getRepositoriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getRepositoriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of repositories matching the UUID and company.
	 *
	 * @param uuid the UUID of the repositories
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of repositories
	 * @param end the upper bound of the range of repositories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching repositories, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Repository>
		getRepositoriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Repository>
					orderByComparator) {

		return getService().getRepositoriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of repositories.
	 *
	 * @return the number of repositories
	 */
	public static int getRepositoriesCount() {
		return getService().getRepositoriesCount();
	}

	/**
	 * Returns the repository with the primary key.
	 *
	 * @param repositoryId the primary key of the repository
	 * @return the repository
	 * @throws PortalException if a repository with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Repository getRepository(
			long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRepository(repositoryId);
	}

	public static com.liferay.portal.kernel.model.Repository getRepository(
			long groupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRepository(groupId, portletId);
	}

	public static com.liferay.portal.kernel.model.Repository getRepository(
			long groupId, String name, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRepository(groupId, name, portletId);
	}

	/**
	 * Returns the repository matching the UUID and group.
	 *
	 * @param uuid the repository's UUID
	 * @param groupId the primary key of the group
	 * @return the matching repository
	 * @throws PortalException if a matching repository could not be found
	 */
	public static com.liferay.portal.kernel.model.Repository
			getRepositoryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRepositoryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.util.UnicodeProperties
			getTypeSettingsProperties(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTypeSettingsProperties(repositoryId);
	}

	public static void updateRepository(
			long repositoryId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateRepository(repositoryId, name, description);
	}

	public static void updateRepository(
			long repositoryId,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateRepository(repositoryId, typeSettingsProperties);
	}

	/**
	 * Updates the repository in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RepositoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param repository the repository
	 * @return the repository that was updated
	 */
	public static com.liferay.portal.kernel.model.Repository updateRepository(
		com.liferay.portal.kernel.model.Repository repository) {

		return getService().updateRepository(repository);
	}

	public static RepositoryLocalService getService() {
		if (_service == null) {
			_service = (RepositoryLocalService)PortalBeanLocatorUtil.locate(
				RepositoryLocalService.class.getName());
		}

		return _service;
	}

	private static RepositoryLocalService _service;

}