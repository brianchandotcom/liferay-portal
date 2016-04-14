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

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for Repository. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLocalServiceUtil
 * @see com.liferay.portal.service.base.RepositoryLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.RepositoryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface RepositoryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RepositoryLocalServiceUtil} to access the repository local service. Add custom service methods to {@link com.liferay.portal.service.impl.RepositoryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Adds the repository to the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Repository addRepository(Repository repository);

	public Repository addRepository(long userId, long groupId,
		long classNameId, long parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String portletId,
		UnicodeProperties typeSettingsProperties, boolean hidden,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new repository with the primary key. Does not add the repository to the database.
	*
	* @param repositoryId the primary key for the new repository
	* @return the new repository
	*/
	public Repository createRepository(long repositoryId);

	/**
	* Deletes the repository from the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public Repository deleteRepository(Repository repository);

	/**
	* Deletes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryId the primary key of the repository
	* @return the repository that was removed
	* @throws PortalException if a repository with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public Repository deleteRepository(long repositoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository fetchRepository(long groupId, java.lang.String name,
		java.lang.String portletId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository fetchRepository(long groupId, java.lang.String portletId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository fetchRepository(long repositoryId);

	/**
	* Returns the repository matching the UUID and group.
	*
	* @param uuid the repository's UUID
	* @param groupId the primary key of the group
	* @return the matching repository, or <code>null</code> if a matching repository could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository fetchRepositoryByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository getRepository(long groupId, java.lang.String name,
		java.lang.String portletId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository getRepository(long groupId, java.lang.String portletId)
		throws PortalException;

	/**
	* Returns the repository with the primary key.
	*
	* @param repositoryId the primary key of the repository
	* @return the repository
	* @throws PortalException if a repository with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository getRepository(long repositoryId)
		throws PortalException;

	/**
	* Returns the repository matching the UUID and group.
	*
	* @param uuid the repository's UUID
	* @param groupId the primary key of the group
	* @return the matching repository
	* @throws PortalException if a matching repository could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Repository getRepositoryByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Updates the repository in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param repository the repository
	* @return the repository that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Repository updateRepository(Repository repository);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException;

	/**
	* Returns the number of repositories.
	*
	* @return the number of repositories
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRepositoriesCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Repository> getGroupRepositories(long groupId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Repository> getRepositories(int start, int end);

	/**
	* Returns all the repositories matching the UUID and company.
	*
	* @param uuid the UUID of the repositories
	* @param companyId the primary key of the company
	* @return the matching repositories, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Repository> getRepositoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Repository> getRepositoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<Repository> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void checkRepository(long repositoryId);

	public void deleteRepositories(long groupId) throws PortalException;

	public void updateRepository(long repositoryId,
		UnicodeProperties typeSettingsProperties) throws PortalException;

	public void updateRepository(long repositoryId, java.lang.String name,
		java.lang.String description) throws PortalException;
}