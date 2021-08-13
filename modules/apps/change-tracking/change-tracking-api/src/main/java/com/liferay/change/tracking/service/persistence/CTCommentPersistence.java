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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchCommentException;
import com.liferay.change.tracking.model.CTComment;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct comment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTCommentUtil
 * @generated
 */
@ProviderType
public interface CTCommentPersistence extends BasePersistence<CTComment> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTCommentUtil} to access the ct comment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct comments where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct comments
	 */
	public java.util.List<CTComment> findByCtCollectionId(long ctCollectionId);

	/**
	 * Returns a range of all the ct comments where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @return the range of matching ct comments
	 */
	public java.util.List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the ct comments where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct comments
	 */
	public java.util.List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct comments where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct comments
	 */
	public java.util.List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public CTComment findByCtCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTComment>
				orderByComparator)
		throws NoSuchCommentException;

	/**
	 * Returns the first ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public CTComment fetchByCtCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator);

	/**
	 * Returns the last ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public CTComment findByCtCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTComment>
				orderByComparator)
		throws NoSuchCommentException;

	/**
	 * Returns the last ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public CTComment fetchByCtCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator);

	/**
	 * Returns the ct comments before and after the current ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCommentId the primary key of the current ct comment
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public CTComment[] findByCtCollectionId_PrevAndNext(
			long ctCommentId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTComment>
				orderByComparator)
		throws NoSuchCommentException;

	/**
	 * Removes all the ct comments where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCtCollectionId(long ctCollectionId);

	/**
	 * Returns the number of ct comments where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct comments
	 */
	public int countByCtCollectionId(long ctCollectionId);

	/**
	 * Returns all the ct comments where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @return the matching ct comments
	 */
	public java.util.List<CTComment> findByCtEntryId(long ctEntryId);

	/**
	 * Returns a range of all the ct comments where ctEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctEntryId the ct entry ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @return the range of matching ct comments
	 */
	public java.util.List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the ct comments where ctEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctEntryId the ct entry ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct comments
	 */
	public java.util.List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct comments where ctEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctEntryId the ct entry ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct comments
	 */
	public java.util.List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public CTComment findByCtEntryId_First(
			long ctEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CTComment>
				orderByComparator)
		throws NoSuchCommentException;

	/**
	 * Returns the first ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public CTComment fetchByCtEntryId_First(
		long ctEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator);

	/**
	 * Returns the last ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public CTComment findByCtEntryId_Last(
			long ctEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CTComment>
				orderByComparator)
		throws NoSuchCommentException;

	/**
	 * Returns the last ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public CTComment fetchByCtEntryId_Last(
		long ctEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator);

	/**
	 * Returns the ct comments before and after the current ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctCommentId the primary key of the current ct comment
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public CTComment[] findByCtEntryId_PrevAndNext(
			long ctCommentId, long ctEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CTComment>
				orderByComparator)
		throws NoSuchCommentException;

	/**
	 * Removes all the ct comments where ctEntryId = &#63; from the database.
	 *
	 * @param ctEntryId the ct entry ID
	 */
	public void removeByCtEntryId(long ctEntryId);

	/**
	 * Returns the number of ct comments where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @return the number of matching ct comments
	 */
	public int countByCtEntryId(long ctEntryId);

	/**
	 * Caches the ct comment in the entity cache if it is enabled.
	 *
	 * @param ctComment the ct comment
	 */
	public void cacheResult(CTComment ctComment);

	/**
	 * Caches the ct comments in the entity cache if it is enabled.
	 *
	 * @param ctComments the ct comments
	 */
	public void cacheResult(java.util.List<CTComment> ctComments);

	/**
	 * Creates a new ct comment with the primary key. Does not add the ct comment to the database.
	 *
	 * @param ctCommentId the primary key for the new ct comment
	 * @return the new ct comment
	 */
	public CTComment create(long ctCommentId);

	/**
	 * Removes the ct comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment that was removed
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public CTComment remove(long ctCommentId) throws NoSuchCommentException;

	public CTComment updateImpl(CTComment ctComment);

	/**
	 * Returns the ct comment with the primary key or throws a <code>NoSuchCommentException</code> if it could not be found.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public CTComment findByPrimaryKey(long ctCommentId)
		throws NoSuchCommentException;

	/**
	 * Returns the ct comment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment, or <code>null</code> if a ct comment with the primary key could not be found
	 */
	public CTComment fetchByPrimaryKey(long ctCommentId);

	/**
	 * Returns all the ct comments.
	 *
	 * @return the ct comments
	 */
	public java.util.List<CTComment> findAll();

	/**
	 * Returns a range of all the ct comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @return the range of ct comments
	 */
	public java.util.List<CTComment> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct comments
	 */
	public java.util.List<CTComment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct comments
	 */
	public java.util.List<CTComment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTComment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct comments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct comments.
	 *
	 * @return the number of ct comments
	 */
	public int countAll();

}