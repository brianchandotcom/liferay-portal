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

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.exception.NoSuchFileEntryPreviewException;
import com.liferay.document.library.model.DLFileEntryPreview;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the dl file entry preview service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.document.library.service.persistence.impl.DLFileEntryPreviewPersistenceImpl
 * @see DLFileEntryPreviewUtil
 * @generated
 */
@ProviderType
public interface DLFileEntryPreviewPersistence extends BasePersistence<DLFileEntryPreview> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryPreviewUtil} to access the dl file entry preview persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, DLFileEntryPreview> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	* Returns all the dl file entry previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileEntryId(
		long fileEntryId);

	/**
	* Returns a range of all the dl file entry previews where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @return the range of matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileEntryId(
		long fileEntryId, int start, int end);

	/**
	* Returns an ordered range of all the dl file entry previews where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator);

	/**
	* Returns an ordered range of all the dl file entry previews where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview findByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the first dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator);

	/**
	* Returns the last dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview findByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the last dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator);

	/**
	* Returns the dl file entry previews before and after the current dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryPreviewId the primary key of the current dl file entry preview
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public DLFileEntryPreview[] findByFileEntryId_PrevAndNext(
		long fileEntryPreviewId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException;

	/**
	* Removes all the dl file entry previews where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public void removeByFileEntryId(long fileEntryId);

	/**
	* Returns the number of dl file entry previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching dl file entry previews
	*/
	public int countByFileEntryId(long fileEntryId);

	/**
	* Returns all the dl file entry previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId);

	/**
	* Returns a range of all the dl file entry previews where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @return the range of matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId, int start, int end);

	/**
	* Returns an ordered range of all the dl file entry previews where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator);

	/**
	* Returns an ordered range of all the dl file entry previews where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview findByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the first dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByFileVersionId_First(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator);

	/**
	* Returns the last dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview findByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the last dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByFileVersionId_Last(long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator);

	/**
	* Returns the dl file entry previews before and after the current dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileEntryPreviewId the primary key of the current dl file entry preview
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public DLFileEntryPreview[] findByFileVersionId_PrevAndNext(
		long fileEntryPreviewId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException;

	/**
	* Removes all the dl file entry previews where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public void removeByFileVersionId(long fileVersionId);

	/**
	* Returns the number of dl file entry previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching dl file entry previews
	*/
	public int countByFileVersionId(long fileVersionId);

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview findByF_F(long fileEntryId, long fileVersionId)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByF_F(long fileEntryId, long fileVersionId);

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByF_F(long fileEntryId, long fileVersionId,
		boolean retrieveFromCache);

	/**
	* Removes the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the dl file entry preview that was removed
	*/
	public DLFileEntryPreview removeByF_F(long fileEntryId, long fileVersionId)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the number of dl file entry previews where fileEntryId = &#63; and fileVersionId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the number of matching dl file entry previews
	*/
	public int countByF_F(long fileEntryId, long fileVersionId);

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview findByF_F_P(long fileEntryId, long fileVersionId,
		int previewType) throws NoSuchFileEntryPreviewException;

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewType);

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public DLFileEntryPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewType, boolean retrieveFromCache);

	/**
	* Removes the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the dl file entry preview that was removed
	*/
	public DLFileEntryPreview removeByF_F_P(long fileEntryId,
		long fileVersionId, int previewType)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the number of dl file entry previews where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the number of matching dl file entry previews
	*/
	public int countByF_F_P(long fileEntryId, long fileVersionId,
		int previewType);

	/**
	* Caches the dl file entry preview in the entity cache if it is enabled.
	*
	* @param dlFileEntryPreview the dl file entry preview
	*/
	public void cacheResult(DLFileEntryPreview dlFileEntryPreview);

	/**
	* Caches the dl file entry previews in the entity cache if it is enabled.
	*
	* @param dlFileEntryPreviews the dl file entry previews
	*/
	public void cacheResult(
		java.util.List<DLFileEntryPreview> dlFileEntryPreviews);

	/**
	* Creates a new dl file entry preview with the primary key. Does not add the dl file entry preview to the database.
	*
	* @param fileEntryPreviewId the primary key for the new dl file entry preview
	* @return the new dl file entry preview
	*/
	public DLFileEntryPreview create(long fileEntryPreviewId);

	/**
	* Removes the dl file entry preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview that was removed
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public DLFileEntryPreview remove(long fileEntryPreviewId)
		throws NoSuchFileEntryPreviewException;

	public DLFileEntryPreview updateImpl(DLFileEntryPreview dlFileEntryPreview);

	/**
	* Returns the dl file entry preview with the primary key or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public DLFileEntryPreview findByPrimaryKey(long fileEntryPreviewId)
		throws NoSuchFileEntryPreviewException;

	/**
	* Returns the dl file entry preview with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview, or <code>null</code> if a dl file entry preview with the primary key could not be found
	*/
	public DLFileEntryPreview fetchByPrimaryKey(long fileEntryPreviewId);

	/**
	* Returns all the dl file entry previews.
	*
	* @return the dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findAll();

	/**
	* Returns a range of all the dl file entry previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @return the range of dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findAll(int start, int end);

	/**
	* Returns an ordered range of all the dl file entry previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator);

	/**
	* Returns an ordered range of all the dl file entry previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of dl file entry previews
	* @param end the upper bound of the range of dl file entry previews (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of dl file entry previews
	*/
	public java.util.List<DLFileEntryPreview> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the dl file entry previews from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of dl file entry previews.
	*
	* @return the number of dl file entry previews
	*/
	public int countAll();
}