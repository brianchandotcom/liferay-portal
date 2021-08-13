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

import com.liferay.change.tracking.model.CTComment;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ct comment service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTCommentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTCommentPersistence
 * @generated
 */
public class CTCommentUtil {

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
	public static void clearCache(CTComment ctComment) {
		getPersistence().clearCache(ctComment);
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
	public static Map<Serializable, CTComment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTComment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTComment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTComment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTComment update(CTComment ctComment) {
		return getPersistence().update(ctComment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTComment update(
		CTComment ctComment, ServiceContext serviceContext) {

		return getPersistence().update(ctComment, serviceContext);
	}

	/**
	 * Returns all the ct comments where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct comments
	 */
	public static List<CTComment> findByCtCollectionId(long ctCollectionId) {
		return getPersistence().findByCtCollectionId(ctCollectionId);
	}

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
	public static List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end);
	}

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
	public static List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

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
	public static List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTComment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public static CTComment findByCtCollectionId_First(
			long ctCollectionId, OrderByComparator<CTComment> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().findByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public static CTComment fetchByCtCollectionId_First(
		long ctCollectionId, OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public static CTComment findByCtCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTComment> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().findByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public static CTComment fetchByCtCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct comments before and after the current ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCommentId the primary key of the current ct comment
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public static CTComment[] findByCtCollectionId_PrevAndNext(
			long ctCommentId, long ctCollectionId,
			OrderByComparator<CTComment> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().findByCtCollectionId_PrevAndNext(
			ctCommentId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct comments where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCtCollectionId(long ctCollectionId) {
		getPersistence().removeByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct comments where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct comments
	 */
	public static int countByCtCollectionId(long ctCollectionId) {
		return getPersistence().countByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns all the ct comments where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @return the matching ct comments
	 */
	public static List<CTComment> findByCtEntryId(long ctEntryId) {
		return getPersistence().findByCtEntryId(ctEntryId);
	}

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
	public static List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end) {

		return getPersistence().findByCtEntryId(ctEntryId, start, end);
	}

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
	public static List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end,
		OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().findByCtEntryId(
			ctEntryId, start, end, orderByComparator);
	}

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
	public static List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end,
		OrderByComparator<CTComment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCtEntryId(
			ctEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public static CTComment findByCtEntryId_First(
			long ctEntryId, OrderByComparator<CTComment> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().findByCtEntryId_First(
			ctEntryId, orderByComparator);
	}

	/**
	 * Returns the first ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public static CTComment fetchByCtEntryId_First(
		long ctEntryId, OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().fetchByCtEntryId_First(
			ctEntryId, orderByComparator);
	}

	/**
	 * Returns the last ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	public static CTComment findByCtEntryId_Last(
			long ctEntryId, OrderByComparator<CTComment> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().findByCtEntryId_Last(
			ctEntryId, orderByComparator);
	}

	/**
	 * Returns the last ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	public static CTComment fetchByCtEntryId_Last(
		long ctEntryId, OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().fetchByCtEntryId_Last(
			ctEntryId, orderByComparator);
	}

	/**
	 * Returns the ct comments before and after the current ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctCommentId the primary key of the current ct comment
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public static CTComment[] findByCtEntryId_PrevAndNext(
			long ctCommentId, long ctEntryId,
			OrderByComparator<CTComment> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().findByCtEntryId_PrevAndNext(
			ctCommentId, ctEntryId, orderByComparator);
	}

	/**
	 * Removes all the ct comments where ctEntryId = &#63; from the database.
	 *
	 * @param ctEntryId the ct entry ID
	 */
	public static void removeByCtEntryId(long ctEntryId) {
		getPersistence().removeByCtEntryId(ctEntryId);
	}

	/**
	 * Returns the number of ct comments where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @return the number of matching ct comments
	 */
	public static int countByCtEntryId(long ctEntryId) {
		return getPersistence().countByCtEntryId(ctEntryId);
	}

	/**
	 * Caches the ct comment in the entity cache if it is enabled.
	 *
	 * @param ctComment the ct comment
	 */
	public static void cacheResult(CTComment ctComment) {
		getPersistence().cacheResult(ctComment);
	}

	/**
	 * Caches the ct comments in the entity cache if it is enabled.
	 *
	 * @param ctComments the ct comments
	 */
	public static void cacheResult(List<CTComment> ctComments) {
		getPersistence().cacheResult(ctComments);
	}

	/**
	 * Creates a new ct comment with the primary key. Does not add the ct comment to the database.
	 *
	 * @param ctCommentId the primary key for the new ct comment
	 * @return the new ct comment
	 */
	public static CTComment create(long ctCommentId) {
		return getPersistence().create(ctCommentId);
	}

	/**
	 * Removes the ct comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment that was removed
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public static CTComment remove(long ctCommentId)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().remove(ctCommentId);
	}

	public static CTComment updateImpl(CTComment ctComment) {
		return getPersistence().updateImpl(ctComment);
	}

	/**
	 * Returns the ct comment with the primary key or throws a <code>NoSuchCommentException</code> if it could not be found.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	public static CTComment findByPrimaryKey(long ctCommentId)
		throws com.liferay.change.tracking.exception.NoSuchCommentException {

		return getPersistence().findByPrimaryKey(ctCommentId);
	}

	/**
	 * Returns the ct comment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment, or <code>null</code> if a ct comment with the primary key could not be found
	 */
	public static CTComment fetchByPrimaryKey(long ctCommentId) {
		return getPersistence().fetchByPrimaryKey(ctCommentId);
	}

	/**
	 * Returns all the ct comments.
	 *
	 * @return the ct comments
	 */
	public static List<CTComment> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CTComment> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CTComment> findAll(
		int start, int end, OrderByComparator<CTComment> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CTComment> findAll(
		int start, int end, OrderByComparator<CTComment> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct comments from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct comments.
	 *
	 * @return the number of ct comments
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTCommentPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTCommentPersistence, CTCommentPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTCommentPersistence.class);

		ServiceTracker<CTCommentPersistence, CTCommentPersistence>
			serviceTracker =
				new ServiceTracker<CTCommentPersistence, CTCommentPersistence>(
					bundle.getBundleContext(), CTCommentPersistence.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}