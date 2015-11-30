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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for DLContent. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLContentLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLContentLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLContentLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLContentLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLContentLocalServiceUtil} to access the document library content local service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLContentLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.documentlibrary.model.DLContent addContent(
		long companyId, long repositoryId, java.lang.String path,
		java.lang.String version, byte[] bytes);

	public com.liferay.portlet.documentlibrary.model.DLContent addContent(
		long companyId, long repositoryId, java.lang.String path,
		java.lang.String version, java.io.InputStream inputStream, long size);

	/**
	* Adds the document library content to the database. Also notifies the appropriate model listeners.
	*
	* @param dlContent the document library content
	* @return the document library content that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLContent addDLContent(
		com.liferay.portlet.documentlibrary.model.DLContent dlContent);

	/**
	* Creates a new document library content with the primary key. Does not add the document library content to the database.
	*
	* @param contentId the primary key for the new document library content
	* @return the new document library content
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent createDLContent(
		long contentId);

	public void deleteContent(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version)
		throws PortalException;

	public void deleteContents(long companyId, long repositoryId,
		java.lang.String path);

	public void deleteContentsByDirectory(long companyId, long repositoryId,
		java.lang.String dirName);

	/**
	* Deletes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content that was removed
	* @throws PortalException if a document library content with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLContent deleteDLContent(
		long contentId) throws PortalException;

	/**
	* Deletes the document library content from the database. Also notifies the appropriate model listeners.
	*
	* @param dlContent the document library content
	* @return the document library content that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.documentlibrary.model.DLContent deleteDLContent(
		com.liferay.portlet.documentlibrary.model.DLContent dlContent);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLContent fetchDLContent(
		long contentId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLContent getContent(
		long companyId, long repositoryId, java.lang.String path)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLContent getContent(
		long companyId, long repositoryId, java.lang.String path,
		java.lang.String version)
		throws com.liferay.portlet.documentlibrary.NoSuchContentException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> getContents(
		long companyId, long repositoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> getContents(
		long companyId, long repositoryId, java.lang.String path);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> getContentsByDirectory(
		long companyId, long repositoryId, java.lang.String dirName);

	/**
	* Returns the document library content with the primary key.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content
	* @throws PortalException if a document library content with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLContent getDLContent(
		long contentId) throws PortalException;

	/**
	* Returns a range of all the document library contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of document library contents
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> getDLContents(
		int start, int end);

	/**
	* Returns the number of document library contents.
	*
	* @return the number of document library contents
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDLContentsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLContentDataBlobModel getDataBlobModel(
		java.io.Serializable primaryKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasContent(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version);

	public void updateDLContent(long companyId, long oldRepositoryId,
		long newRepositoryId, java.lang.String oldPath, java.lang.String newPath);

	/**
	* Updates the document library content in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlContent the document library content
	* @return the document library content that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.documentlibrary.model.DLContent updateDLContent(
		com.liferay.portlet.documentlibrary.model.DLContent dlContent);
}