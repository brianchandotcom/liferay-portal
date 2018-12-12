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

import com.liferay.document.library.model.DLFileEntryPreview;

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
 * The persistence utility for the dl file entry preview service. This utility wraps {@link com.liferay.document.library.service.persistence.impl.DLFileEntryPreviewPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPreviewPersistence
 * @see com.liferay.document.library.service.persistence.impl.DLFileEntryPreviewPersistenceImpl
 * @generated
 */
@ProviderType
public class DLFileEntryPreviewUtil {
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
	public static void clearCache(DLFileEntryPreview dlFileEntryPreview) {
		getPersistence().clearCache(dlFileEntryPreview);
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
	public static Map<Serializable, DLFileEntryPreview> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLFileEntryPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileEntryPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLFileEntryPreview> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DLFileEntryPreview update(
		DLFileEntryPreview dlFileEntryPreview) {
		return getPersistence().update(dlFileEntryPreview);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DLFileEntryPreview update(
		DLFileEntryPreview dlFileEntryPreview, ServiceContext serviceContext) {
		return getPersistence().update(dlFileEntryPreview, serviceContext);
	}

	/**
	* Returns all the dl file entry previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching dl file entry previews
	*/
	public static List<DLFileEntryPreview> findByFileEntryId(long fileEntryId) {
		return getPersistence().findByFileEntryId(fileEntryId);
	}

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
	public static List<DLFileEntryPreview> findByFileEntryId(long fileEntryId,
		int start, int end) {
		return getPersistence().findByFileEntryId(fileEntryId, start, end);
	}

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
	public static List<DLFileEntryPreview> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end, orderByComparator);
	}

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
	public static List<DLFileEntryPreview> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileEntryId(fileEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview findByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .findByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the first dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileEntryId_First(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .findByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the last dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileEntryId_Last(fileEntryId, orderByComparator);
	}

	/**
	* Returns the dl file entry previews before and after the current dl file entry preview in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryPreviewId the primary key of the current dl file entry preview
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public static DLFileEntryPreview[] findByFileEntryId_PrevAndNext(
		long fileEntryPreviewId, long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .findByFileEntryId_PrevAndNext(fileEntryPreviewId,
			fileEntryId, orderByComparator);
	}

	/**
	* Removes all the dl file entry previews where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public static void removeByFileEntryId(long fileEntryId) {
		getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	* Returns the number of dl file entry previews where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching dl file entry previews
	*/
	public static int countByFileEntryId(long fileEntryId) {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	* Returns all the dl file entry previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching dl file entry previews
	*/
	public static List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId) {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

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
	public static List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId, int start, int end) {
		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

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
	public static List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator);
	}

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
	public static List<DLFileEntryPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview findByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .findByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the first dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview findByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .findByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the dl file entry previews before and after the current dl file entry preview in the ordered set where fileVersionId = &#63;.
	*
	* @param fileEntryPreviewId the primary key of the current dl file entry preview
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public static DLFileEntryPreview[] findByFileVersionId_PrevAndNext(
		long fileEntryPreviewId, long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .findByFileVersionId_PrevAndNext(fileEntryPreviewId,
			fileVersionId, orderByComparator);
	}

	/**
	* Removes all the dl file entry previews where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public static void removeByFileVersionId(long fileVersionId) {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	* Returns the number of dl file entry previews where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching dl file entry previews
	*/
	public static int countByFileVersionId(long fileVersionId) {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview findByF_F(long fileEntryId,
		long fileVersionId)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence().findByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByF_F(long fileEntryId,
		long fileVersionId) {
		return getPersistence().fetchByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByF_F(long fileEntryId,
		long fileVersionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByF_F(fileEntryId, fileVersionId, retrieveFromCache);
	}

	/**
	* Removes the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the dl file entry preview that was removed
	*/
	public static DLFileEntryPreview removeByF_F(long fileEntryId,
		long fileVersionId)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence().removeByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the number of dl file entry previews where fileEntryId = &#63; and fileVersionId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @return the number of matching dl file entry previews
	*/
	public static int countByF_F(long fileEntryId, long fileVersionId) {
		return getPersistence().countByF_F(fileEntryId, fileVersionId);
	}

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the matching dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview findByF_F_P(long fileEntryId,
		long fileVersionId, int previewType)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .findByF_F_P(fileEntryId, fileVersionId, previewType);
	}

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewType) {
		return getPersistence()
				   .fetchByF_F_P(fileEntryId, fileVersionId, previewType);
	}

	/**
	* Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	*/
	public static DLFileEntryPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewType, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByF_F_P(fileEntryId, fileVersionId, previewType,
			retrieveFromCache);
	}

	/**
	* Removes the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the dl file entry preview that was removed
	*/
	public static DLFileEntryPreview removeByF_F_P(long fileEntryId,
		long fileVersionId, int previewType)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence()
				   .removeByF_F_P(fileEntryId, fileVersionId, previewType);
	}

	/**
	* Returns the number of dl file entry previews where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param fileVersionId the file version ID
	* @param previewType the preview type
	* @return the number of matching dl file entry previews
	*/
	public static int countByF_F_P(long fileEntryId, long fileVersionId,
		int previewType) {
		return getPersistence()
				   .countByF_F_P(fileEntryId, fileVersionId, previewType);
	}

	/**
	* Caches the dl file entry preview in the entity cache if it is enabled.
	*
	* @param dlFileEntryPreview the dl file entry preview
	*/
	public static void cacheResult(DLFileEntryPreview dlFileEntryPreview) {
		getPersistence().cacheResult(dlFileEntryPreview);
	}

	/**
	* Caches the dl file entry previews in the entity cache if it is enabled.
	*
	* @param dlFileEntryPreviews the dl file entry previews
	*/
	public static void cacheResult(List<DLFileEntryPreview> dlFileEntryPreviews) {
		getPersistence().cacheResult(dlFileEntryPreviews);
	}

	/**
	* Creates a new dl file entry preview with the primary key. Does not add the dl file entry preview to the database.
	*
	* @param fileEntryPreviewId the primary key for the new dl file entry preview
	* @return the new dl file entry preview
	*/
	public static DLFileEntryPreview create(long fileEntryPreviewId) {
		return getPersistence().create(fileEntryPreviewId);
	}

	/**
	* Removes the dl file entry preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview that was removed
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public static DLFileEntryPreview remove(long fileEntryPreviewId)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence().remove(fileEntryPreviewId);
	}

	public static DLFileEntryPreview updateImpl(
		DLFileEntryPreview dlFileEntryPreview) {
		return getPersistence().updateImpl(dlFileEntryPreview);
	}

	/**
	* Returns the dl file entry preview with the primary key or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview
	* @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	*/
	public static DLFileEntryPreview findByPrimaryKey(long fileEntryPreviewId)
		throws com.liferay.document.library.exception.NoSuchFileEntryPreviewException {
		return getPersistence().findByPrimaryKey(fileEntryPreviewId);
	}

	/**
	* Returns the dl file entry preview with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryPreviewId the primary key of the dl file entry preview
	* @return the dl file entry preview, or <code>null</code> if a dl file entry preview with the primary key could not be found
	*/
	public static DLFileEntryPreview fetchByPrimaryKey(long fileEntryPreviewId) {
		return getPersistence().fetchByPrimaryKey(fileEntryPreviewId);
	}

	/**
	* Returns all the dl file entry previews.
	*
	* @return the dl file entry previews
	*/
	public static List<DLFileEntryPreview> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<DLFileEntryPreview> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<DLFileEntryPreview> findAll(int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<DLFileEntryPreview> findAll(int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the dl file entry previews from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of dl file entry previews.
	*
	* @return the number of dl file entry previews
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DLFileEntryPreviewPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DLFileEntryPreviewPersistence, DLFileEntryPreviewPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DLFileEntryPreviewPersistence.class);

		ServiceTracker<DLFileEntryPreviewPersistence, DLFileEntryPreviewPersistence> serviceTracker =
			new ServiceTracker<DLFileEntryPreviewPersistence, DLFileEntryPreviewPersistence>(bundle.getBundleContext(),
				DLFileEntryPreviewPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}