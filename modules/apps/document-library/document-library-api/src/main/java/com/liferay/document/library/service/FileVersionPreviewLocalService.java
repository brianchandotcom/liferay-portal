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

package com.liferay.document.library.service;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.document.library.model.FileVersionPreview;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for FileVersionPreview. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see FileVersionPreviewLocalServiceUtil
 * @see com.liferay.document.library.service.base.FileVersionPreviewLocalServiceBaseImpl
 * @see com.liferay.document.library.service.impl.FileVersionPreviewLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FileVersionPreviewLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FileVersionPreviewLocalServiceUtil} to access the file version preview local service. Add custom service methods to {@link com.liferay.document.library.service.impl.FileVersionPreviewLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the file version preview to the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreview the file version preview
	* @return the file version preview that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FileVersionPreview addFileVersionPreview(
		FileVersionPreview fileVersionPreview);

	public void addFileVersionPreview(long fileEntryId, long fileVersionId,
		int previewStatus) throws PortalException;

	/**
	* Creates a new file version preview with the primary key. Does not add the file version preview to the database.
	*
	* @param fileVersionPreviewId the primary key for the new file version preview
	* @return the new file version preview
	*/
	@Transactional(enabled = false)
	public FileVersionPreview createFileVersionPreview(
		long fileVersionPreviewId);

	public void deleteFileEntryFileVersionPreviews(long fileEntryId);

	/**
	* Deletes the file version preview from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreview the file version preview
	* @return the file version preview that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public FileVersionPreview deleteFileVersionPreview(
		FileVersionPreview fileVersionPreview);

	/**
	* Deletes the file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview that was removed
	* @throws PortalException if a file version preview with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public FileVersionPreview deleteFileVersionPreview(
		long fileVersionPreviewId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileVersionPreview fetchFileVersionPreview(long fileVersionPreviewId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileVersionPreview fetchFileVersionPreview(long fileEntryId,
		long fileVersionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileVersionPreview fetchFileVersionPreview(long fileEntryId,
		long fileVersionId, int previewStatus);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FileVersionPreview> getFileEntryFileVersionPreviews(
		long fileEntryId);

	/**
	* Returns the file version preview with the primary key.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview
	* @throws PortalException if a file version preview with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileVersionPreview getFileVersionPreview(long fileVersionPreviewId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileVersionPreview getFileVersionPreview(long fileEntryId,
		long fileVersionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FileVersionPreview getFileVersionPreview(long fileEntryId,
		long fileVersionId, int previewStatus) throws PortalException;

	/**
	* Returns a range of all the file version previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.document.library.model.impl.FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @return the range of file version previews
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FileVersionPreview> getFileVersionPreviews(int start, int end);

	/**
	* Returns the number of file version previews.
	*
	* @return the number of file version previews
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileVersionPreviewsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasFileVersionPreview(long fileEntryId, long fileVersionId,
		int previewStatus);

	/**
	* Updates the file version preview in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreview the file version preview
	* @return the file version preview that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FileVersionPreview updateFileVersionPreview(
		FileVersionPreview fileVersionPreview);

	public void updateFileVersionPreview(long fileVersionPreviewId,
		int previewStatus) throws PortalException;
}