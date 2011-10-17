/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link RepositoryEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RepositoryEntryLocalService
 * @generated
 */
public class RepositoryEntryLocalServiceWrapper
	implements RepositoryEntryLocalService,
		ServiceWrapper<RepositoryEntryLocalService> {
	public RepositoryEntryLocalServiceWrapper(
		RepositoryEntryLocalService repositoryEntryLocalService) {
		_repositoryEntryLocalService = repositoryEntryLocalService;
	}

	/**
	* Adds the repository entry to the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @return the repository entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.RepositoryEntry addRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.addRepositoryEntry(repositoryEntry);
	}

	/**
	* Creates a new repository entry with the primary key. Does not add the repository entry to the database.
	*
	* @param repositoryEntryId the primary key for the new repository entry
	* @return the new repository entry
	*/
	public com.liferay.portal.model.RepositoryEntry createRepositoryEntry(
		long repositoryEntryId) {
		return _repositoryEntryLocalService.createRepositoryEntry(repositoryEntryId);
	}

	/**
	* Deletes the repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @throws PortalException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRepositoryEntry(long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryEntryLocalService.deleteRepositoryEntry(repositoryEntryId);
	}

	/**
	* Deletes the repository entry from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_repositoryEntryLocalService.deleteRepositoryEntry(repositoryEntry);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the repository entry with the primary key.
	*
	* @param repositoryEntryId the primary key of the repository entry
	* @return the repository entry
	* @throws PortalException if a repository entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.RepositoryEntry getRepositoryEntry(
		long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.getRepositoryEntry(repositoryEntryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the repository entry with the UUID in the group.
	*
	* @param uuid the UUID of repository entry
	* @param groupId the group id of the repository entry
	* @return the repository entry
	* @throws PortalException if a repository entry with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.RepositoryEntry getRepositoryEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.getRepositoryEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the repository entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of repository entries
	* @param end the upper bound of the range of repository entries (not inclusive)
	* @return the range of repository entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.RepositoryEntry> getRepositoryEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.getRepositoryEntries(start, end);
	}

	/**
	* Returns the number of repository entries.
	*
	* @return the number of repository entries
	* @throws SystemException if a system exception occurred
	*/
	public int getRepositoryEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.getRepositoryEntriesCount();
	}

	/**
	* Updates the repository entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @return the repository entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.RepositoryEntry updateRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.updateRepositoryEntry(repositoryEntry);
	}

	/**
	* Updates the repository entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repositoryEntry the repository entry
	* @param merge whether to merge the repository entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the repository entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.RepositoryEntry updateRepositoryEntry(
		com.liferay.portal.model.RepositoryEntry repositoryEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.updateRepositoryEntry(repositoryEntry,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _repositoryEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_repositoryEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.RepositoryEntry fetchRepositoryEntry(
		long repositoryEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryEntryLocalService.fetchRepositoryEntry(repositoryEntryId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public RepositoryEntryLocalService getWrappedRepositoryEntryLocalService() {
		return _repositoryEntryLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {
		_repositoryEntryLocalService = repositoryEntryLocalService;
	}

	public RepositoryEntryLocalService getWrappedService() {
		return _repositoryEntryLocalService;
	}

	public void setWrappedService(
		RepositoryEntryLocalService repositoryEntryLocalService) {
		_repositoryEntryLocalService = repositoryEntryLocalService;
	}

	private RepositoryEntryLocalService _repositoryEntryLocalService;
}