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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Repository. This utility wraps
 * {@link com.liferay.portal.service.impl.RepositoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLocalService
 * @see com.liferay.portal.service.base.RepositoryLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.RepositoryLocalServiceImpl
 * @generated
 */
@ProviderType
public class RepositoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RepositoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the repository to the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was added
	*/
	public static com.liferay.portal.model.Repository addRepository(
		com.liferay.portal.model.Repository repository) {
		return getService().addRepository(repository);
	}

	/**
	* Creates a new repository with the primary key. Does not add the repository to the database.
	*
	* @param repositoryId the primary key for the new repository
	* @return the new repository
	*/
	public static com.liferay.portal.model.Repository createRepository(
		long repositoryId) {
		return getService().createRepository(repositoryId);
	}

	/**
	* Deletes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryId the primary key of the repository
	* @return the repository that was removed
	* @throws PortalException if a repository with the primary key could not be found
	*/
	public static com.liferay.portal.model.Repository deleteRepository(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteRepository(repositoryId);
	}

	/**
	* Deletes the repository from the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was removed
	*/
	public static com.liferay.portal.model.Repository deleteRepository(
		com.liferay.portal.model.Repository repository) {
		return getService().deleteRepository(repository);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.model.Repository fetchRepository(
		long repositoryId) {
		return getService().fetchRepository(repositoryId);
	}

	/**
	* Returns the repository with the matching UUID and company.
	*
	* @param uuid the repository's UUID
	* @param companyId the primary key of the company
	* @return the matching repository, or <code>null</code> if a matching repository could not be found
	*/
	public static com.liferay.portal.model.Repository fetchRepositoryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchRepositoryByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the repository matching the UUID and group.
	*
	* @param uuid the repository's UUID
	* @param groupId the primary key of the group
	* @return the matching repository, or <code>null</code> if a matching repository could not be found
	*/
	public static com.liferay.portal.model.Repository fetchRepositoryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchRepositoryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the repository with the primary key.
	*
	* @param repositoryId the primary key of the repository
	* @return the repository
	* @throws PortalException if a repository with the primary key could not be found
	*/
	public static com.liferay.portal.model.Repository getRepository(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepository(repositoryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the repository with the matching UUID and company.
	*
	* @param uuid the repository's UUID
	* @param companyId the primary key of the company
	* @return the matching repository
	* @throws PortalException if a matching repository could not be found
	*/
	public static com.liferay.portal.model.Repository getRepositoryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepositoryByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the repository matching the UUID and group.
	*
	* @param uuid the repository's UUID
	* @param groupId the primary key of the group
	* @return the matching repository
	* @throws PortalException if a matching repository could not be found
	*/
	public static com.liferay.portal.model.Repository getRepositoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepositoryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the repositories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RepositoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of repositories
	* @param end the upper bound of the range of repositories (not inclusive)
	* @return the range of repositories
	*/
	public static java.util.List<com.liferay.portal.model.Repository> getRepositories(
		int start, int end) {
		return getService().getRepositories(start, end);
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
	* Updates the repository in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was updated
	*/
	public static com.liferay.portal.model.Repository updateRepository(
		com.liferay.portal.model.Repository repository) {
		return getService().updateRepository(repository);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portal.model.Repository addRepository(
		long userId, long groupId, long classNameId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		boolean hidden, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRepository(userId, groupId, classNameId, parentFolderId,
			name, description, portletId, typeSettingsProperties, hidden,
			serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addRepository(long, long,
	long, long, String, String, String, UnicodeProperties,
	boolean, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Repository addRepository(
		long userId, long groupId, long classNameId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRepository(userId, groupId, classNameId, parentFolderId,
			name, description, portletId, typeSettingsProperties, serviceContext);
	}

	public static void checkRepository(long repositoryId) {
		getService().checkRepository(repositoryId);
	}

	public static void deleteRepositories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRepositories(groupId);
	}

	public static com.liferay.portal.model.Repository fetchRepository(
		long groupId, java.lang.String portletId) {
		return getService().fetchRepository(groupId, portletId);
	}

	public static com.liferay.portal.model.Repository fetchRepository(
		long groupId, java.lang.String name, java.lang.String portletId) {
		return getService().fetchRepository(groupId, name, portletId);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.LocalRepository> getGroupLocalRepositoryImpl(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupLocalRepositoryImpl(groupId);
	}

	public static com.liferay.portal.kernel.repository.LocalRepository getLocalRepositoryImpl(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLocalRepositoryImpl(repositoryId);
	}

	public static com.liferay.portal.kernel.repository.LocalRepository getLocalRepositoryImpl(
		long folderId, long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLocalRepositoryImpl(folderId, fileEntryId, fileVersionId);
	}

	public static java.lang.String getRegistryName() {
		return getService().getRegistryName();
	}

	public static com.liferay.portal.model.Repository getRepository(
		long groupId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepository(groupId, portletId);
	}

	public static com.liferay.portal.model.Repository getRepository(
		long groupId, java.lang.String name, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepository(groupId, name, portletId);
	}

	public static com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRepositoryImpl(repositoryId);
	}

	public static com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
		long folderId, long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getRepositoryImpl(folderId, fileEntryId, fileVersionId);
	}

	public static com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTypeSettingsProperties(repositoryId);
	}

	public static void invalidate() {
		getService().invalidate();
	}

	public static void updateRepository(long repositoryId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateRepository(repositoryId, name, description);
	}

	public static RepositoryLocalService getService() {
		if (_service == null) {
			_service = (RepositoryLocalService)PortalBeanLocatorUtil.locate(RepositoryLocalService.class.getName());

			ReferenceRegistry.registerReference(RepositoryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(RepositoryLocalService service) {
	}

	private static RepositoryLocalService _service;
}