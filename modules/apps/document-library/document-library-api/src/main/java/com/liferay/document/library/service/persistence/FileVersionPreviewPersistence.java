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

package com.liferay.document.library.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.document.library.exception.NoSuchFileVersionPreviewException;
import com.liferay.document.library.model.FileVersionPreview;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the file version preview service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.document.library.service.persistence.impl.FileVersionPreviewPersistenceImpl
 * @see FileVersionPreviewUtil
 * @generated
 */
@ProviderType
public interface FileVersionPreviewPersistence extends BasePersistence<FileVersionPreview> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FileVersionPreviewUtil} to access the file version preview persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, FileVersionPreview> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	* Returns all the file version previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileEntryId(
		long fileEntryId);

	/**
	* Returns a range of all the file version previews where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @return the range of matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileEntryId(
		long fileEntryId, int start, int end);

	/**
	* Returns an ordered range of all the file version previews where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator);

	/**
	* Returns an ordered range of all the file version previews where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public FileVersionPreview findByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the first file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator);

	/**
	* Returns the last file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public FileVersionPreview findByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the last file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator);

	/**
	* Returns the file version previews before and after the current file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileVersionPreviewId the primary key of the current file version preview
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next file version preview
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public FileVersionPreview[] findByFileEntryId_PrevAndNext(
		long fileVersionPreviewId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException;

	/**
	* Removes all the file version previews where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public void removeByFileEntryId(long fileEntryId);

	/**
	* Returns the number of file version previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching file version previews
	*/
	public int countByFileEntryId(long fileEntryId);

	/**
	* Returns all the file version previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileVersionId(
		long fileVersionId);

	/**
	* Returns a range of all the file version previews where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @return the range of matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end);

	/**
	* Returns an ordered range of all the file version previews where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator);

	/**
	* Returns an ordered range of all the file version previews where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching file version previews
	*/
	public java.util.List<FileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public FileVersionPreview findByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the first file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator);

	/**
	* Returns the last file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public FileVersionPreview findByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the last file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator);

	/**
	* Returns the file version previews before and after the current file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionPreviewId the primary key of the current file version preview
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next file version preview
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public FileVersionPreview[] findByFileVersionId_PrevAndNext(
		long fileVersionPreviewId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException;

	/**
	* Removes all the file version previews where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public void removeByFileVersionId(long fileVersionId);

	/**
	* Returns the number of file version previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching file version previews
	*/
	public int countByFileVersionId(long fileVersionId);

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileVersionPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public FileVersionPreview findByF_F(long fileEntryId, long fileVersionId)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByF_F(long fileEntryId, long fileVersionId);

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByF_F(long fileEntryId, long fileVersionId,
		boolean retrieveFromCache);

	/**
	* Removes the file version preview where fileEntryId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the file version preview that was removed
	*/
	public FileVersionPreview removeByF_F(long fileEntryId, long fileVersionId)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the number of file version previews where fileEntryId = &#63; and fileVersionId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the number of matching file version previews
	*/
	public int countByF_F(long fileEntryId, long fileVersionId);

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or throws a {@link NoSuchFileVersionPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public FileVersionPreview findByF_F_P(long fileEntryId, long fileVersionId,
		int previewStatus) throws NoSuchFileVersionPreviewException;

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewStatus);

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public FileVersionPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewStatus, boolean retrieveFromCache);

	/**
	* Removes the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the file version preview that was removed
	*/
	public FileVersionPreview removeByF_F_P(long fileEntryId,
		long fileVersionId, int previewStatus)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the number of file version previews where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the number of matching file version previews
	*/
	public int countByF_F_P(long fileEntryId, long fileVersionId,
		int previewStatus);

	/**
	* Caches the file version preview in the entity cache if it is enabled.
	*
	* @param fileVersionPreview the file version preview
	*/
	public void cacheResult(FileVersionPreview fileVersionPreview);

	/**
	* Caches the file version previews in the entity cache if it is enabled.
	*
	* @param fileVersionPreviews the file version previews
	*/
	public void cacheResult(
		java.util.List<FileVersionPreview> fileVersionPreviews);

	/**
	* Creates a new file version preview with the primary key. Does not add the file version preview to the database.
	*
	* @param fileVersionPreviewId the primary key for the new file version preview
	* @return the new file version preview
	*/
	public FileVersionPreview create(long fileVersionPreviewId);

	/**
	* Removes the file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview that was removed
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public FileVersionPreview remove(long fileVersionPreviewId)
		throws NoSuchFileVersionPreviewException;

	public FileVersionPreview updateImpl(FileVersionPreview fileVersionPreview);

	/**
	* Returns the file version preview with the primary key or throws a {@link NoSuchFileVersionPreviewException} if it could not be found.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public FileVersionPreview findByPrimaryKey(long fileVersionPreviewId)
		throws NoSuchFileVersionPreviewException;

	/**
	* Returns the file version preview with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview, or <code>null</code> if a file version preview with the primary key could not be found
	*/
	public FileVersionPreview fetchByPrimaryKey(long fileVersionPreviewId);

	/**
	* Returns all the file version previews.
	*
	* @return the file version previews
	*/
	public java.util.List<FileVersionPreview> findAll();

	/**
	* Returns a range of all the file version previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @return the range of file version previews
	*/
	public java.util.List<FileVersionPreview> findAll(int start, int end);

	/**
	* Returns an ordered range of all the file version previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of file version previews
	*/
	public java.util.List<FileVersionPreview> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator);

	/**
	* Returns an ordered range of all the file version previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FileVersionPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of file version previews
	* @param end the upper bound of the range of file version previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of file version previews
	*/
	public java.util.List<FileVersionPreview> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FileVersionPreview> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the file version previews from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of file version previews.
	*
	* @return the number of file version previews
	*/
	public int countAll();
}