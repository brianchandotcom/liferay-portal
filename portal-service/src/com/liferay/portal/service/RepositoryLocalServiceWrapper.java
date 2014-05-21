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

/**
 * Provides a wrapper for {@link RepositoryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLocalService
 * @generated
 */
@ProviderType
public class RepositoryLocalServiceWrapper implements RepositoryLocalService,
	ServiceWrapper<RepositoryLocalService> {
	public RepositoryLocalServiceWrapper(
		RepositoryLocalService repositoryLocalService) {
		_repositoryLocalService = repositoryLocalService;
	}

	/**
	* Adds the repository to the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository addRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.addRepository(repository);
	}

	/**
	* Creates a new repository with the primary key. Does not add the repository to the database.
	*
	* @param repositoryId the primary key for the new repository
	* @return the new repository
	*/
	@Override
	public com.liferay.portal.model.Repository createRepository(
		long repositoryId) {
		return _repositoryLocalService.createRepository(repositoryId);
	}

	/**
	* Deletes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryId the primary key of the repository
	* @return the repository that was removed
	* @throws PortalException if a repository with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository deleteRepository(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.deleteRepository(repositoryId);
	}

	/**
	* Deletes the repository from the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository deleteRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.deleteRepository(repository);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _repositoryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQuery(dynamicQuery);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQuery(dynamicQuery, start, end);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.Repository fetchRepository(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.fetchRepository(repositoryId);
	}

	/**
	* Returns the repository with the matching UUID and company.
	*
	* @param uuid the repository's UUID
	* @param companyId the primary key of the company
	* @return the matching repository, or <code>null</code> if a matching repository could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository fetchRepositoryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.fetchRepositoryByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the repository matching the UUID and group.
	*
	* @param uuid the repository's UUID
	* @param groupId the primary key of the group
	* @return the matching repository, or <code>null</code> if a matching repository could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository fetchRepositoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.fetchRepositoryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the repository with the primary key.
	*
	* @param repositoryId the primary key of the repository
	* @return the repository
	* @throws PortalException if a repository with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository getRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepository(repositoryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the repository with the matching UUID and company.
	*
	* @param uuid the repository's UUID
	* @param companyId the primary key of the company
	* @return the matching repository
	* @throws PortalException if a matching repository could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository getRepositoryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositoryByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the repository matching the UUID and group.
	*
	* @param uuid the repository's UUID
	* @param groupId the primary key of the group
	* @return the matching repository
	* @throws PortalException if a matching repository could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository getRepositoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositoryByUuidAndGroupId(uuid,
			groupId);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Repository> getRepositories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositories(start, end);
	}

	/**
	* Returns the number of repositories.
	*
	* @return the number of repositories
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getRepositoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositoriesCount();
	}

	/**
	* Updates the repository in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portal.model.Repository updateRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.updateRepository(repository);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _repositoryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_repositoryLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.Repository addRepository(long userId,
		long groupId, long classNameId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		boolean hidden, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.addRepository(userId, groupId,
			classNameId, parentFolderId, name, description, portletId,
			typeSettingsProperties, hidden, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addRepository(long, long,
	long, long, String, String, String, UnicodeProperties,
	boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.Repository addRepository(long userId,
		long groupId, long classNameId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.addRepository(userId, groupId,
			classNameId, parentFolderId, name, description, portletId,
			typeSettingsProperties, serviceContext);
	}

	@Override
	public void checkRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.checkRepository(repositoryId);
	}

	@Override
	public void deleteRepositories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.deleteRepositories(groupId);
	}

	@Override
	public com.liferay.portal.model.Repository fetchRepository(long groupId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.fetchRepository(groupId, portletId);
	}

	@Override
	public com.liferay.portal.model.Repository fetchRepository(long groupId,
		java.lang.String name, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.fetchRepository(groupId, name, portletId);
	}

	@Override
	public com.liferay.portal.kernel.repository.LocalRepository getLocalRepositoryImpl(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getLocalRepositoryImpl(repositoryId);
	}

	@Override
	public com.liferay.portal.kernel.repository.LocalRepository getLocalRepositoryImpl(
		long folderId, long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getLocalRepositoryImpl(folderId,
			fileEntryId, fileVersionId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.LocalRepository> getLocalRepositoryImplByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getLocalRepositoryImplByGroupId(groupId);
	}

	@Override
	public com.liferay.portal.model.Repository getRepository(long groupId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepository(groupId, portletId);
	}

	@Override
	public com.liferay.portal.model.Repository getRepository(long groupId,
		java.lang.String name, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepository(groupId, name, portletId);
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositoryImpl(repositoryId);
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
		long folderId, long fileEntryId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositoryImpl(folderId, fileEntryId,
			fileVersionId);
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getTypeSettingsProperties(repositoryId);
	}

	@Override
	public void updateRepository(long repositoryId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.updateRepository(repositoryId, name, description);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public RepositoryLocalService getWrappedRepositoryLocalService() {
		return _repositoryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {
		_repositoryLocalService = repositoryLocalService;
	}

	@Override
	public RepositoryLocalService getWrappedService() {
		return _repositoryLocalService;
	}

	@Override
	public void setWrappedService(RepositoryLocalService repositoryLocalService) {
		_repositoryLocalService = repositoryLocalService;
	}

	private RepositoryLocalService _repositoryLocalService;
}