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

package com.liferay.sync.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.sync.exception.NoSuchDLObjectException;
import com.liferay.sync.model.SyncDLObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the sync dl object service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLObjectUtil
 * @generated
 */
@ProviderType
public interface SyncDLObjectPersistence extends BasePersistence<SyncDLObject> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SyncDLObjectUtil} to access the sync dl object persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the sync dl objects where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByTreePath(String treePath);

	/**
	 * Returns a range of all the sync dl objects where treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByTreePath(
		String treePath, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByTreePath(
		String treePath, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByTreePath(
		String treePath, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByTreePath_First(
			String treePath,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByTreePath_First(
		String treePath,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByTreePath_Last(
			String treePath,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByTreePath_Last(
		String treePath,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByTreePath_PrevAndNext(
			long syncDLObjectId, String treePath,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Removes all the sync dl objects where treePath LIKE &#63; from the database.
	 *
	 * @param treePath the tree path
	 */
	public void removeByTreePath(String treePath);

	/**
	 * Returns the number of sync dl objects where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @return the number of matching sync dl objects
	 */
	public int countByTreePath(String treePath);

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId);

	/**
	 * Returns a range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByM_R_First(
			long modifiedTime, long repositoryId,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByM_R_First(
		long modifiedTime, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByM_R_Last(
			long modifiedTime, long repositoryId,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByM_R_Last(
		long modifiedTime, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByM_R_PrevAndNext(
			long syncDLObjectId, long modifiedTime, long repositoryId,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Removes all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 */
	public void removeByM_R(long modifiedTime, long repositoryId);

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @return the number of matching sync dl objects
	 */
	public int countByM_R(long modifiedTime, long repositoryId);

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId);

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_P_First(
			long repositoryId, long parentFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_P_First(
		long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_P_Last(
			long repositoryId, long parentFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_P_Last(
		long repositoryId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByR_P_PrevAndNext(
			long syncDLObjectId, long repositoryId, long parentFolderId,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 */
	public void removeByR_P(long repositoryId, long parentFolderId);

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching sync dl objects
	 */
	public int countByR_P(long repositoryId, long parentFolderId);

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_NotE(
		long repositoryId, String event);

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_NotE_First(
			long repositoryId, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_NotE_First(
		long repositoryId, String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_NotE_Last(
			long repositoryId, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_NotE_Last(
		long repositoryId, String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByR_NotE_PrevAndNext(
			long syncDLObjectId, long repositoryId, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and event &ne; &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 */
	public void removeByR_NotE(long repositoryId, String event);

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	public int countByR_NotE(long repositoryId, String event);

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_T(
		long repositoryId, String type);

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_T_First(
			long repositoryId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_T_First(
		long repositoryId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_T_Last(
			long repositoryId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_T_Last(
		long repositoryId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByR_T_PrevAndNext(
			long syncDLObjectId, long repositoryId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and type = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 */
	public void removeByR_T(long repositoryId, String type);

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	public int countByR_T(long repositoryId, String type);

	/**
	 * Returns all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByT_NotE(
		String treePath, String event);

	/**
	 * Returns a range of all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByT_NotE(
		String treePath, String event, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByT_NotE(
		String treePath, String event, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByT_NotE(
		String treePath, String event, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByT_NotE_First(
			String treePath, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByT_NotE_First(
		String treePath, String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByT_NotE_Last(
			String treePath, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByT_NotE_Last(
		String treePath, String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByT_NotE_PrevAndNext(
			long syncDLObjectId, String treePath, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Removes all the sync dl objects where treePath LIKE &#63; and event &ne; &#63; from the database.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 */
	public void removeByT_NotE(String treePath, String event);

	/**
	 * Returns the number of sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	public int countByT_NotE(String treePath, String event);

	/**
	 * Returns all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByV_T(String version, String type);

	/**
	 * Returns a range of all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByV_T(
		String version, String type, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByV_T(
		String version, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param version the version
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByV_T(
		String version, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByV_T_First(
			String version, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByV_T_First(
		String version, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByV_T_Last(
			String version, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByV_T_Last(
		String version, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByV_T_PrevAndNext(
			long syncDLObjectId, String version, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Removes all the sync dl objects where version = &#63; and type = &#63; from the database.
	 *
	 * @param version the version
	 * @param type the type
	 */
	public void removeByV_T(String version, String type);

	/**
	 * Returns the number of sync dl objects where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	public int countByV_T(String version, String type);

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or throws a <code>NoSuchDLObjectException</code> if it could not be found.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByT_T(String type, long typePK)
		throws NoSuchDLObjectException;

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByT_T(String type, long typePK);

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByT_T(
		String type, long typePK, boolean useFinderCache);

	/**
	 * Removes the sync dl object where type = &#63; and typePK = &#63; from the database.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the sync dl object that was removed
	 */
	public SyncDLObject removeByT_T(String type, long typePK)
		throws NoSuchDLObjectException;

	/**
	 * Returns the number of sync dl objects where type = &#63; and typePK = &#63;.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the number of matching sync dl objects
	 */
	public int countByT_T(String type, long typePK);

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event);

	/**
	 * Returns a range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByM_R_NotE_First(
			long modifiedTime, long repositoryId, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByM_R_NotE_First(
		long modifiedTime, long repositoryId, String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByM_R_NotE_Last(
			long modifiedTime, long repositoryId, String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByM_R_NotE_Last(
		long modifiedTime, long repositoryId, String event,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByM_R_NotE_PrevAndNext(
			long syncDLObjectId, long modifiedTime, long repositoryId,
			String event,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events);

	/**
	 * Returns a range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end);

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 */
	public void removeByM_R_NotE(
		long modifiedTime, long repositoryId, String event);

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	public int countByM_R_NotE(
		long modifiedTime, long repositoryId, String event);

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @return the number of matching sync dl objects
	 */
	public int countByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events);

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type);

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start,
		int end);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_P_T_First(
			long repositoryId, long parentFolderId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_P_T_First(
		long repositoryId, long parentFolderId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public SyncDLObject findByR_P_T_Last(
			long repositoryId, long parentFolderId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public SyncDLObject fetchByR_P_T_Last(
		long repositoryId, long parentFolderId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject[] findByR_P_T_PrevAndNext(
			long syncDLObjectId, long repositoryId, long parentFolderId,
			String type,
			com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
				orderByComparator)
		throws NoSuchDLObjectException;

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @return the matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types);

	/**
	 * Returns a range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl objects
	 */
	public java.util.List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 */
	public void removeByR_P_T(
		long repositoryId, long parentFolderId, String type);

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	public int countByR_P_T(
		long repositoryId, long parentFolderId, String type);

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @return the number of matching sync dl objects
	 */
	public int countByR_P_T(
		long repositoryId, long parentFolderId, String[] types);

	/**
	 * Caches the sync dl object in the entity cache if it is enabled.
	 *
	 * @param syncDLObject the sync dl object
	 */
	public void cacheResult(SyncDLObject syncDLObject);

	/**
	 * Caches the sync dl objects in the entity cache if it is enabled.
	 *
	 * @param syncDLObjects the sync dl objects
	 */
	public void cacheResult(java.util.List<SyncDLObject> syncDLObjects);

	/**
	 * Creates a new sync dl object with the primary key. Does not add the sync dl object to the database.
	 *
	 * @param syncDLObjectId the primary key for the new sync dl object
	 * @return the new sync dl object
	 */
	public SyncDLObject create(long syncDLObjectId);

	/**
	 * Removes the sync dl object with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object that was removed
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject remove(long syncDLObjectId)
		throws NoSuchDLObjectException;

	public SyncDLObject updateImpl(SyncDLObject syncDLObject);

	/**
	 * Returns the sync dl object with the primary key or throws a <code>NoSuchDLObjectException</code> if it could not be found.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject findByPrimaryKey(long syncDLObjectId)
		throws NoSuchDLObjectException;

	/**
	 * Returns the sync dl object with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object, or <code>null</code> if a sync dl object with the primary key could not be found
	 */
	public SyncDLObject fetchByPrimaryKey(long syncDLObjectId);

	/**
	 * Returns all the sync dl objects.
	 *
	 * @return the sync dl objects
	 */
	public java.util.List<SyncDLObject> findAll();

	/**
	 * Returns a range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @return the range of sync dl objects
	 */
	public java.util.List<SyncDLObject> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sync dl objects
	 */
	public java.util.List<SyncDLObject> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sync dl objects.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLObjectModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl objects
	 * @param end the upper bound of the range of sync dl objects (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sync dl objects
	 */
	public java.util.List<SyncDLObject> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SyncDLObject>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the sync dl objects from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of sync dl objects.
	 *
	 * @return the number of sync dl objects
	 */
	public int countAll();

}