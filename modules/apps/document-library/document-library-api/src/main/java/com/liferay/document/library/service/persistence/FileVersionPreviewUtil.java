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

import com.liferay.document.library.model.FileVersionPreview;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the file version preview service. This utility wraps {@link com.liferay.document.library.service.persistence.impl.FileVersionPreviewPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FileVersionPreviewPersistence
 * @see com.liferay.document.library.service.persistence.impl.FileVersionPreviewPersistenceImpl
 * @generated
 */
public class FileVersionPreviewUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(FileVersionPreview fileVersionPreview) {
		getPersistence().clearCache(fileVersionPreview);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, FileVersionPreview> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FileVersionPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FileVersionPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FileVersionPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FileVersionPreview update(
		FileVersionPreview fileVersionPreview) {
		return getPersistence().update(fileVersionPreview);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FileVersionPreview update(
		FileVersionPreview fileVersionPreview, ServiceContext serviceContext) {
		return getPersistence().update(fileVersionPreview, serviceContext);
	}

	/**
	* Returns all the file version previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching file version previews
	*/
	public static List<FileVersionPreview> findByFileEntryId(long fileEntryId) {
		return getPersistence().findByFileEntryId(fileEntryId);
	}

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
	public static List<FileVersionPreview> findByFileEntryId(long fileEntryId,
		int start, int end) {
		return getPersistence().findByFileEntryId(fileEntryId, start, end);
	}

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
	public static List<FileVersionPreview> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end, orderByComparator);
	}

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
	public static List<FileVersionPreview> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<FileVersionPreview> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public static FileVersionPreview findByFileEntryId_First(long fileEntryId,
		OrderByComparator<FileVersionPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .findByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the first file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public static FileVersionPreview findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<FileVersionPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .findByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the file version previews before and after the current file version preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileVersionPreviewId the primary key of the current file version preview
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next file version preview
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public static FileVersionPreview[] findByFileEntryId_PrevAndNext(
		long fileVersionPreviewId, long fileEntryId,
		OrderByComparator<FileVersionPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .findByFileEntryId_PrevAndNext(fileVersionPreviewId,
			fileEntryId, orderByComparator);
	}

	/**
	* Removes all the file version previews where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public static void removeByFileEntryId(long fileEntryId) {
		getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	* Returns the number of file version previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching file version previews
	*/
	public static int countByFileEntryId(long fileEntryId) {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	* Returns all the file version previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching file version previews
	*/
	public static List<FileVersionPreview> findByFileVersionId(
		long fileVersionId) {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

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
	public static List<FileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end) {
		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

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
	public static List<FileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator);
	}

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
	public static List<FileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<FileVersionPreview> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public static FileVersionPreview findByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<FileVersionPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .findByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the first file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public static FileVersionPreview findByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<FileVersionPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .findByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the file version previews before and after the current file version preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionPreviewId the primary key of the current file version preview
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next file version preview
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public static FileVersionPreview[] findByFileVersionId_PrevAndNext(
		long fileVersionPreviewId, long fileVersionId,
		OrderByComparator<FileVersionPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .findByFileVersionId_PrevAndNext(fileVersionPreviewId,
			fileVersionId, orderByComparator);
	}

	/**
	* Removes all the file version previews where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public static void removeByFileVersionId(long fileVersionId) {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	* Returns the number of file version previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching file version previews
	*/
	public static int countByFileVersionId(long fileVersionId) {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileVersionPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public static FileVersionPreview findByF_F(long fileEntryId,
		long fileVersionId)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence().findByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByF_F(long fileEntryId,
		long fileVersionId) {
		return getPersistence().fetchByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByF_F(long fileEntryId,
		long fileVersionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByF_F(fileEntryId, fileVersionId, retrieveFromCache);
	}

	/**
	* Removes the file version preview where fileEntryId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the file version preview that was removed
	*/
	public static FileVersionPreview removeByF_F(long fileEntryId,
		long fileVersionId)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence().removeByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the number of file version previews where fileEntryId = &#63; and fileVersionId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the number of matching file version previews
	*/
	public static int countByF_F(long fileEntryId, long fileVersionId) {
		return getPersistence().countByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or throws a {@link NoSuchFileVersionPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the matching file version preview
	* @throws NoSuchFileVersionPreviewException if a matching file version preview could not be found
	*/
	public static FileVersionPreview findByF_F_P(long fileEntryId,
		long fileVersionId, int previewStatus)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .findByF_F_P(fileEntryId, fileVersionId, previewStatus);
	}

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewStatus) {
		return getPersistence()
				   .fetchByF_F_P(fileEntryId, fileVersionId, previewStatus);
	}

	/**
	* Returns the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching file version preview, or <code>null</code> if a matching file version preview could not be found
	*/
	public static FileVersionPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewStatus, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByF_F_P(fileEntryId, fileVersionId, previewStatus,
			retrieveFromCache);
	}

	/**
	* Removes the file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the file version preview that was removed
	*/
	public static FileVersionPreview removeByF_F_P(long fileEntryId,
		long fileVersionId, int previewStatus)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence()
				   .removeByF_F_P(fileEntryId, fileVersionId, previewStatus);
	}

	/**
	* Returns the number of file version previews where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewStatus the preview status
	* @return the number of matching file version previews
	*/
	public static int countByF_F_P(long fileEntryId, long fileVersionId,
		int previewStatus) {
		return getPersistence()
				   .countByF_F_P(fileEntryId, fileVersionId, previewStatus);
	}

	/**
	* Caches the file version preview in the entity cache if it is enabled.
	*
	* @param fileVersionPreview the file version preview
	*/
	public static void cacheResult(FileVersionPreview fileVersionPreview) {
		getPersistence().cacheResult(fileVersionPreview);
	}

	/**
	* Caches the file version previews in the entity cache if it is enabled.
	*
	* @param fileVersionPreviews the file version previews
	*/
	public static void cacheResult(List<FileVersionPreview> fileVersionPreviews) {
		getPersistence().cacheResult(fileVersionPreviews);
	}

	/**
	* Creates a new file version preview with the primary key. Does not add the file version preview to the database.
	*
	* @param fileVersionPreviewId the primary key for the new file version preview
	* @return the new file version preview
	*/
	public static FileVersionPreview create(long fileVersionPreviewId) {
		return getPersistence().create(fileVersionPreviewId);
	}

	/**
	* Removes the file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview that was removed
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public static FileVersionPreview remove(long fileVersionPreviewId)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence().remove(fileVersionPreviewId);
	}

	public static FileVersionPreview updateImpl(
		FileVersionPreview fileVersionPreview) {
		return getPersistence().updateImpl(fileVersionPreview);
	}

	/**
	* Returns the file version preview with the primary key or throws a {@link NoSuchFileVersionPreviewException} if it could not be found.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview
	* @throws NoSuchFileVersionPreviewException if a file version preview with the primary key could not be found
	*/
	public static FileVersionPreview findByPrimaryKey(long fileVersionPreviewId)
		throws com.liferay.document.library.exception.NoSuchFileVersionPreviewException {
		return getPersistence().findByPrimaryKey(fileVersionPreviewId);
	}

	/**
	* Returns the file version preview with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileVersionPreviewId the primary key of the file version preview
	* @return the file version preview, or <code>null</code> if a file version preview with the primary key could not be found
	*/
	public static FileVersionPreview fetchByPrimaryKey(
		long fileVersionPreviewId) {
		return getPersistence().fetchByPrimaryKey(fileVersionPreviewId);
	}

	/**
	* Returns all the file version previews.
	*
	* @return the file version previews
	*/
	public static List<FileVersionPreview> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<FileVersionPreview> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<FileVersionPreview> findAll(int start, int end,
		OrderByComparator<FileVersionPreview> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<FileVersionPreview> findAll(int start, int end,
		OrderByComparator<FileVersionPreview> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the file version previews from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of file version previews.
	*
	* @return the number of file version previews
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FileVersionPreviewPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FileVersionPreviewPersistence, FileVersionPreviewPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FileVersionPreviewPersistence.class);

		ServiceTracker<FileVersionPreviewPersistence, FileVersionPreviewPersistence> serviceTracker =
			new ServiceTracker<FileVersionPreviewPersistence, FileVersionPreviewPersistence>(bundle.getBundleContext(),
				FileVersionPreviewPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}